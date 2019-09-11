package com.healthbank;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.healthbank.adapter.FileAdapter;
import com.healthbank.classes.Connection;
import com.healthbank.classes.FileData;
import com.healthbank.classes.Test;
import com.healthbank.database.DatabaseHelper;

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

public class AddnewRecordActivity extends ActivityCommon {
    AutoCompleteTextView autotxt1;
    Button bt1;
    RecyclerView mRecyclerview;
    GridLayoutManager mLayoutmanager;
    FileAdapter fileAdapter;
    ArrayList<FileData> mdataset;
    String title = "";
    ArrayList<Test> masterdata;
    ArrayAdapter<Test> adapterggeneric;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew_record);
        setmaterialDesign();
        back();
       attachUI();
    }

    public void attachUI() {
        masterdata = new ArrayList<>();
        Bundle b = getIntent().getExtras();
        if (b != null) {
            if (b.containsKey("flag"))
                title = b.getString("flag");

            if (title.equalsIgnoreCase("Diagnostic")) {
                setTitle("Add Lab details");
                masterdata = parsemaster(DatabaseHelper.getInstance(AddnewRecordActivity.this).getMasterdata("Diagnostic"));
            } else {
                setTitle("Add " + title);
                masterdata = parsemaster(DatabaseHelper.getInstance(AddnewRecordActivity.this).getMasterdata("Pathology"));
            }
        }

        img = findViewById(R.id.imageview_1);


        mdataset = new ArrayList<>();
        autotxt1 = findViewById(R.id.autotxt_1);
        adapterggeneric = new ArrayAdapter<Test>(AddnewRecordActivity.this, android.R.layout.simple_list_item_1, masterdata);
        autotxt1.setAdapter(adapterggeneric);
        autotxt1.setThreshold(0);
        bt1 = findViewById(R.id.button_1);
        mRecyclerview = findViewById(R.id.recyclerview_1);
        mLayoutmanager = new GridLayoutManager(this, 2);
        mRecyclerview.setLayoutManager(mLayoutmanager);
        fileAdapter = new FileAdapter(this, mdataset);
        mRecyclerview.setAdapter(fileAdapter);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(AddnewRecordActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AddnewRecordActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 102);
                    return;
                } else {
                    Intent intent = new Intent();
                    intent.setType("*/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, 103);
                }
            }
        });
        if (masterdata.size() == 0) {
            genloading("loading");
            ConnectionManager.getInstance(AddnewRecordActivity.this).getInvestigationmaster(Integer.toString(GlobalValues.branchid), title);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 102: {
                Intent intent = new Intent();
                intent.setType("*/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 103);
            }
        }
    }

    public ArrayList<Test> parsemaster(String data) {
        ArrayList<Test> masterdata = new ArrayList<>();
        try {

            JSONObject obj = new JSONObject(data).getJSONObject("root");
            JSONArray array = obj.optJSONArray("subroot");
            if (array != null) {
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<Test>>() {
                }.getType();
                ArrayList<Test> dataset = gson.fromJson(array.toString(), type);
                if (dataset != null) {
                    return dataset;
                }
            } else {
                JSONObject obj1 = obj.optJSONObject("subroot");
                if (obj1 != null) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<Test>() {
                    }.getType();
                    Test m = gson.fromJson(obj1.toString(), type);
                    masterdata.add(m);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return masterdata;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                if (requestCode == 103) {
                    final Uri uri = data.getData();
                    String filePath = PathUtil.getPath(AddnewRecordActivity.this, uri);
                    File f = new File(filePath);
                    FileData fdata = new FileData();
                    fdata.setFilename(f.getName());
                    fdata.setFilepath(f.getAbsolutePath());
                    mdataset.add(fdata);
                    fileAdapter.notifyDataSetChanged();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_save:
                if (autotxt1.getText().toString().trim().length() > 0) {
                    if (InternetUtils.getInstance(AddnewRecordActivity.this).available()) {
                        if (mdataset.size() > 0) {
                            genloading("loading..");
                            new upload().execute();
                        } else {
                            updatedata("", "0", autotxt1.getText().toString());
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "No Network", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter name", Toast.LENGTH_LONG).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updatedata(String path, String id, String name) {
        try {
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
            ConnectionManager.getInstance(AddnewRecordActivity.this).savepathology(obj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResponsed(int statuscode, int accesscode, String data) {
        super.onResponsed(statuscode, accesscode, data);
        if (statuscode == Constants.STATUS_OK) {
            if (accesscode == Connection.savepathology.ordinal()) {
                finish();
            } else if (accesscode == Connection.GET_Investigationmaster.ordinal()) {
                try {
                    JSONArray array = new JSONArray(GlobalValues.TEMP_STR);
                    if (array.length() > 0) {
                        JSONArray array1 = array.optJSONArray(0);
                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<Test>>() {
                        }.getType();
                        masterdata = gson.fromJson(array1.toString(), type);

                    } else {
                        JSONObject obj = array.optJSONObject(0);
                        Gson gson = new Gson();
                        Type type = new TypeToken<Test>() {
                        }.getType();
                        Test m = gson.fromJson(obj.toString(), type);
                        masterdata.add(m);
                    }

                    Log.e("size ", "size" + masterdata.size());
                    adapterggeneric = new ArrayAdapter<Test>(AddnewRecordActivity.this, android.R.layout.simple_list_item_1, masterdata);
                    autotxt1.setAdapter(adapterggeneric);
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
        //String filepath = "";
        boolean isuploaded = true;
        String testName = "";
        String path = "";
        boolean finalresult = false;

        @Override
        protected String doInBackground(Void... params) {
            // TODO Auto-generated method stub
            FTPClient ftpclient = new FTPClient();
            FileInputStream fis = null;
            boolean result;
            try {
                for (int i = 0; i < mdataset.size(); i++) {
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
                    File file = new File(mdataset.get(i).getFilepath());


                    String extension = "";
                    int lastDotPosition = mdataset.get(i).getFilepath().lastIndexOf('.');
                    if (lastDotPosition > 0) {
                        String string3 = mdataset.get(i).getFilepath().substring(lastDotPosition + 1);
                        extension = string3.toLowerCase();
                    }
                    testName = System.currentTimeMillis() + "_test" + "." + extension;
                    fis = new FileInputStream(file);
                    ftpclient.enterLocalPassiveMode();
                    result = ftpclient.storeFile(testName, fis);
                    if (result) {
                        if (path.length() > 0)
                            path = path + "," + "http://theclinic.techama.in/EMRDocuments/Lab/" + testName;
                        else
                            path = "http://theclinic.techama.in/EMRDocuments/Lab/" + testName;
                    }

                    int reply = ftpclient.getReplyCode();
                    if (!FTPReply.isPositiveCompletion(reply)) {
                        System.err.println("FTP server refused connection.");
                    }

                    if (result == true) {
                        Log.e("res", "File is uploaded successfully ");
                    } else {
                        isuploaded = false;
                        System.out.println("File uploading failed");
                        Log.e("res", "File uploading failed ");
                    }
                    ftpclient.disconnect();
                }
            } catch (FTPConnectionClosedException e) {
                Log.e("error ", "error " + e);
                e.printStackTrace();
            } catch (Exception e) {
                Log.e("error ", "error " + e);
                e.printStackTrace();
            } finally {
                try {
                    ftpclient.disconnect();
                } catch (FTPConnectionClosedException e) {
                    Log.e("error ", "error " + e);
                    System.out.println(e);
                } catch (Exception e) {
                    Log.e("error ", "error " + e);
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            try {
                if (path.length() > 0) {
                    updatedata(path, "0", autotxt1.getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong please try later", Toast.LENGTH_LONG).show();
                }
                dismissLoading();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
