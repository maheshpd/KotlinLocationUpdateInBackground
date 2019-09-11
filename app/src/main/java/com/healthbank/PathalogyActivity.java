package com.healthbank;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.healthbank.adapter.PathalogyAdapter;
import com.healthbank.classes.Connection;
import com.healthbank.classes.Pathalogy;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPReply;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class PathalogyActivity extends ActivityCommon {
    public static int selectedpos = 0;
    public static String path = "";
    String title = "";
    RecyclerView mRecyclerview;
    LinearLayoutManager mLayoutmanager;
    PathalogyAdapter adapter;
    ArrayList<Pathalogy> mdataset;
    String filepath = "";
    boolean isdatafetch = false;

    public static boolean hasPermissions(Context context, String... permissions) {
        try {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
                for (String permission : permissions) {
                    if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pathalogy);
        setmaterialDesign();
        back();
        setTitle(title);


        attachUI();
    }

    public void attachUI() {

        Bundle b = getIntent().getExtras();
        if (b != null) {
            if (b.containsKey("flag"))
                title = b.getString("flag");

            if (title.equalsIgnoreCase("Diagnostic")) {
                setTitle("Lab details");
            } else {
                setTitle(title);
            }
        }

        mdataset = new ArrayList<>();
        mRecyclerview = findViewById(R.id.recyclerview_1);
        mLayoutmanager = new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(mLayoutmanager);
        adapter = new PathalogyAdapter(this, mdataset);
        mRecyclerview.setAdapter(adapter);
        if (InternetUtils.getInstance(PathalogyActivity.this).available()) {
            genloading("loading..");
            ConnectionManager.getInstance(PathalogyActivity.this).getpathalogy(Integer.toString(GlobalValues.branchid), Integer.toString(GlobalValues.selectedpt.getPatientid()), title);
        } else {
            Toast.makeText(getApplicationContext(), "No Network", Toast.LENGTH_LONG).show();
        }
        checkpermission();


    }

    public void checkpermission() {
        try {
            int PERMISSION_ALL = 1;
            String[] PERMISSIONS = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (!hasPermissions(this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                if (requestCode == 101) {
                    final Uri uri = data.getData();
                    filepath = PathUtil.getPath(context, uri);

                    new upload().execute();
                   // Log.e("path ", "path " + " " + filepath);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // getMenuInflater().inflate(R.menu.uploadpathlogy, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_patholgy:
                Intent intent = new Intent(PathalogyActivity.this, AddnewRecordActivity.class);
                intent.putExtra("flag", title);
                PathalogyActivity.this.startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 201: {
                try {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        browsefile();
                    } else {
                        Log.e("request code ", "request code in else  " + requestCode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }
            case 202: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openurlfile(path);
                } else {
                    Toast.makeText(PathalogyActivity.this, "No Network", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isdatafetch) {
            if (InternetUtils.getInstance(PathalogyActivity.this).available()) {
                genloading("loading..");
                ConnectionManager.getInstance(PathalogyActivity.this).getpathalogy(Integer.toString(GlobalValues.branchid), Integer.toString(GlobalValues.selectedpt.getPatientid()), title);
            }
        }
    }

    public void updatedata(ArrayList<String> data, String id, String name) {
        try {
            String path = "";
            for (int i = 0; i < data.size(); i++) {
                if (path.length() == 0)
                    path = data.get(i);
                else
                    path = path + "," + data.get(i);
            }
            JSONObject obj = new JSONObject();
            obj.put("id", id);
            obj.put("file", path);
            obj.put("name", name);
            obj.put("DoctorId", GlobalValues.docid);
            obj.put("VisitNo", 0);
            obj.put("Patientid", GlobalValues.selectedpt.getPatientid());
            obj.put("PracticeId", GlobalValues.branchid);
            obj.put("type", title);
            obj.put("connectionid", Constants.projectid);
            ConnectionManager.getInstance(PathalogyActivity.this).savepathology(obj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResponsed(int statuscode, int accesscode, String data) {
        super.onResponsed(statuscode, accesscode, data);
        if (statuscode == Constants.STATUS_OK) {
            if (accesscode == Connection.GET_Pathalogy.ordinal()) {
                try {
                    isdatafetch = true;
                    mdataset.clear();
                    JSONArray array = new JSONArray(GlobalValues.TEMP_STR);
                    if (array.length() > 0) {
                        JSONArray array1 = array.optJSONArray(0);
                        if (array1 != null) {
                            Gson gson = new Gson();
                            Type type = new TypeToken<ArrayList<Pathalogy>>() {
                            }.getType();
                            mdataset = gson.fromJson(array1.toString(), type);
                            for (int i = 0; i < mdataset.size(); i++) {
                                ArrayList<String> filepath = new ArrayList<>();
                                if (mdataset.get(i).getFilepath() != null) {
                                    if (mdataset.get(i).getFilepath().length() > 0) {
                                        String[] dataarray = mdataset.get(i).getFilepath().split(",");
                                        for (int j = 0; j < dataarray.length; j++) {
                                            if (dataarray[j].length() > 0 && !dataarray[j].equalsIgnoreCase("null"))
                                                filepath.add(dataarray[j]);
                                        }
                                        mdataset.get(i).setFilepathdata(filepath);
                                    }
                                }
                            }
                            adapter.notifyDataSetChanged();
                            adapter = new PathalogyAdapter(PathalogyActivity.this, mdataset);
                            mRecyclerview.setAdapter(adapter);
                        } else {
                            JSONObject obj = array.optJSONObject(0);
                            Gson gson = new Gson();
                            Type type = new TypeToken<Pathalogy>() {
                            }.getType();
                            mdataset.add((Pathalogy) gson.fromJson(obj.toString(), type));
                            for (int i = 0; i < mdataset.size(); i++) {
                                ArrayList<String> filepath = new ArrayList<>();
                                if (mdataset.get(i).getFilepath() != null) {
                                    if (mdataset.get(i).getFilepath().length() > 0) {
                                        String[] dataarray = mdataset.get(i).getFilepath().split(",");
                                        for (int j = 0; j < dataarray.length; j++) {
                                            if (dataarray[j].length() > 0 && !dataarray[j].equalsIgnoreCase("null"))
                                                filepath.add(dataarray[j]);
                                        }
                                        mdataset.get(i).setFilepathdata(filepath);
                                    }
                                }
                            }
                            adapter.notifyDataSetChanged();
                            adapter = new PathalogyAdapter(PathalogyActivity.this, mdataset);
                            mRecyclerview.setAdapter(adapter);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class upload extends AsyncTask<Void, Void, String> {
        long stime = System.currentTimeMillis();
        String ftpServerAddress = "theclinic.techama.in";
        String userName = "theclinic";
        String password = "thE334IncCN";
        boolean isuploaded = true;
        String testName = "";
        boolean finalresult = false;

        @Override
        protected String doInBackground(Void... params) {
            // TODO Auto-generated method stub
            FTPClient ftpclient = new FTPClient();
            FileInputStream fis = null;
            boolean result;
            try {
                ftpclient.connect(ftpServerAddress, 2112);
                result = ftpclient.login(userName, password);
                ftpclient.setBufferSize(1024000);
                if (result == true) {
                    Log.e("logged", "Logged in Successfully !");
                } else {
                    Log.e("logged fail", "Login Fail!");
                    return null;
                }

                ftpclient.setFileType(FTP.BINARY_FILE_TYPE);
                ftpclient.changeWorkingDirectory("/www/EMRDocuments/Lab/");
                File file = new File(filepath);

                String extension = "";
                int lastDotPosition = filepath.lastIndexOf('.');
                if (lastDotPosition > 0) {
                    String string3 = filepath.substring(lastDotPosition + 1);
                    extension = string3.toLowerCase();
                }

                testName = System.currentTimeMillis() + "_test" + "." + extension;
                fis = new FileInputStream(file);
                ftpclient.enterLocalPassiveMode();

                result = ftpclient.storeFile(testName, fis);
                finalresult = result;
                int reply = ftpclient.getReplyCode();
                if (!FTPReply.isPositiveCompletion(reply)) {
                    System.err.println("FTP server refused connection.");
                }

                if (result == true) {
                    System.out.println("File is uploaded successfully");
                    Log.e("res", "File is uploaded successfully ");
                } else {
                    isuploaded = false;
                    System.out.println("File uploading failed");
                    Log.e("res", "File uploading failed ");
                }
                ftpclient.disconnect();
                //   }
            } catch (FTPConnectionClosedException e) {
                // Log.e("error ", "error " + e);
                e.printStackTrace();
            } catch (Exception e) {
                //Log.e("error ", "error " + e);
                e.printStackTrace();
            } finally {
                try {
                    ftpclient.disconnect();
                } catch (FTPConnectionClosedException e) {
                    //  Log.e("error ", "error " + e);
                    System.out.println(e);
                } catch (Exception e) {
                    //  Log.e("error ", "error " + e);
                    e.printStackTrace();
                }
            }
            dismissLoading();
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            try {
                /*long memid = 0;
                String memtype = "Non Member";
                String mobile = "";*/
                if (finalresult) {
                    Log.e("loading dismiss ", "loading dismiss");
                    //  String fname = "http://activities.ida.org.in/uploads/" + testName;
                    String fname = "http://theclinic.techama.in/EMRDocuments/Lab/" + testName;
                    Log.e("path", "path " + filepath);
                    mdataset.get(selectedpos).getFilepathdata().add(fname);
                    adapter.notifyDataSetChanged();
                    updatedata(mdataset.get(selectedpos).getFilepathdata(), mdataset.get(selectedpos).getId(), mdataset.get(selectedpos).getTestName());
                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong please try later", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

     /*  byte[] fileData = new byte[(int) f.length()];
                    DataInputStream dis = new DataInputStream(new FileInputStream(f));
                    dis.readFully(fileData);
                    ContentValues c = new ContentValues();
                    c.put(DatabaseHelper.BINARY_DATA, fileData);
                    Log.e("sizebefore ", "sizebefore " + " " + DatabaseHelper.getInstance(AddnewRecordActivity.this).getsize());
                    long index = DatabaseHelper.getInstance(AddnewRecordActivity.this).savefiledata(c);
                    Log.e("index ", "index " + " " + filePath);
                    Log.e("sizeafter ", "sizeafter " + " " + DatabaseHelper.getInstance(AddnewRecordActivity.this).getsize());*/
}
