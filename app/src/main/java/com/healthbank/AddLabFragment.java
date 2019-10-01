package com.healthbank;

import android.app.DatePickerDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.healthbank.adapter.AdapterImages;
import com.healthbank.classes.Connection;
import com.healthbank.classes.LabMasterData;
import com.healthbank.database.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;
public class AddLabFragment extends Fragment {
    public static final int REQUEST_GALLARY = 102;
    public static final int REQUEST_CAMERA = 101;
    private final int PICK_IMAGE_MULTIPLE = 1;
    TextView txt1;
    Button b1;
    ImageView img1, img2, img3;
    RecyclerView mbuilder;
    LinearLayoutManager mLinearlayoutmanager;
    ArrayList<String> images;
    AdapterImages adapterimages;
    LinearLayout layout;
    ArrayList<LabMasterData> labmaster;
    ArrayAdapter<LabMasterData> labadapter;

    public AddLabFragment() {

    }

    // TODO: Rename and change types and number of parameters
    public static AddLabFragment newInstance(String param1, String param2) {
        AddLabFragment fragment = new AddLabFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_lab, container, false);
        StructureClass.defineContext(getActivity());
        images = new ArrayList<>();
        mLinearlayoutmanager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        labmaster = new ArrayList<>();
        labmaster = DatabaseHelper.getInstance(getActivity()).getlabmasterdata();
        labadapter = new ArrayAdapter<LabMasterData>(getActivity(), android.R.layout.simple_list_item_1, labmaster);

        layout = v.findViewById(R.id.layout_1);
        mbuilder = v.findViewById(R.id.recyclerview_2);
        mbuilder.setLayoutManager(mLinearlayoutmanager);
        adapterimages = new AdapterImages(getActivity(), images, true);
        mbuilder.setAdapter(adapterimages);

        img1 = v.findViewById(R.id.imageview_1);
        img2 = v.findViewById(R.id.imageview_2);
        img3 = v.findViewById(R.id.imageview_3);

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoGallary();
            }
        });

        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("application/pdf");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                try {
                    startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), 900);
                } catch (android.content.ActivityNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

   /*   labdata = new ArrayList<>();
        labdata.add(new Lab());
        recyclerview = (RecyclerView) v.findViewById(R.id.recyclerview_1);
        mLayoutmanager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(mLayoutmanager);
        templateadapter = new LabLayoutAdapter(getActivity(), labdata, "Lab");
        recyclerview.setAdapter(templateadapter); */

        DatabaseHelper.getInstance(getActivity()).getlabdata(GlobalValues.selectedpt.getPatientid(), DatabaseHelper.lab);
        txt1 = v.findViewById(R.id.textview_1);
        b1 = v.findViewById(R.id.button_1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ContentValues c = new ContentValues();
                    JSONArray array = new JSONArray();
                    for (int i = 0; i < layout.getChildCount(); i++) {
                        View v = layout.getChildAt(i);
                        AutoCompleteTextView autoCompleteTextView1 = v.findViewById(R.id.autocompletetextview_1);
                        AutoCompleteTextView autoCompleteTextView2 = v.findViewById(R.id.autocompletetextview_2);
                        AutoCompleteTextView autoCompleteTextView3 = v.findViewById(R.id.autocompletetextview_3);

                        JSONObject obj = new JSONObject();
                        obj.put("Test", autoCompleteTextView1.getText().toString());
                        obj.put("Result", autoCompleteTextView2.getText().toString());
                        obj.put("Normal", autoCompleteTextView3.getText().toString());
                        array.put(obj);

                        ContentValues cvalues = new ContentValues();
                        cvalues.put(DatabaseHelper.Title, autoCompleteTextView1.getText().toString());
                        cvalues.put(DatabaseHelper.Description, "");
                        cvalues.put(DatabaseHelper.type, Constants.ADVICE);
                        cvalues.put(DatabaseHelper.DOCTORID, GlobalValues.docid);
                        cvalues.put(DatabaseHelper.CODE, "0");
                        cvalues.put(DatabaseHelper.PRACTICEID, GlobalValues.practiceid);
                        cvalues.put(DatabaseHelper.SAVEDTIME, DateUtils.getSqliteTime());
                        cvalues.put(DatabaseHelper.NORMALVALUE, autoCompleteTextView2.getText().toString());
                        long id = DatabaseHelper.getInstance(getActivity()).savelabmaster(Constants.LAB, cvalues);
                        if (id != -1) {
                            LabMasterData masterData = new LabMasterData();
                            masterData.setTitle(autoCompleteTextView1.getText().toString());
                            masterData.setDescription("");
                            masterData.setType(Constants.ADVICE);
                            masterData.setDoctorId(GlobalValues.docid);
                            masterData.setCode("0");
                            masterData.setPracticeId(GlobalValues.practiceid);
                            masterData.setNormalvalue(autoCompleteTextView3.getText().toString());
                            labmaster.add(masterData);
                            labadapter.notifyDataSetChanged();
                        }
                    }

                    JSONArray imagearray = new JSONArray();
                    for (int j = 0; j < images.size(); j++) {
                        JSONObject obj1 = new JSONObject();
                        obj1.put("imagepath", images.get(j));
                        imagearray.put(obj1);
                    }

                    c.put(DatabaseHelper.Patientid, GlobalValues.selectedpt.getPatientid());
                    c.put(DatabaseHelper.DOCTORID, GlobalValues.docid);
                    c.put(DatabaseHelper.Date, txt1.getText().toString());
                    c.put(DatabaseHelper.JSONDATA, array.toString());
                    c.put(DatabaseHelper.Imagepath, imagearray.toString());
                    DatabaseHelper.getInstance(getActivity()).savelab(c, txt1.getText().toString(), GlobalValues.selectedpt.getPatientid(), DatabaseHelper.lab);

                    images.clear();
                    adapterimages.notifyDataSetChanged();
                    Intent intent = new Intent("fragmentlabadded");
                    intent.putExtra(Constants.BROADCAST_URL_ACCESS, Connection.appointmentadded.ordinal());
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                    layout.removeAllViews();
                    addview();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Calendar cal = Calendar.getInstance();
        final int mYear = cal.get(Calendar.YEAR);
        int mMonth = cal.get(Calendar.MONTH);
        int mDay = cal.get(Calendar.DAY_OF_MONTH);
        String m = "" + (mMonth + 1);
        String d = "" + mDay;
        if ((mMonth + 1) < 10)
            m = "0" + m;

        if (mDay < 10)
            d = "0" + d;

        txt1.setText(d + "-" + m + "-" + mYear);

        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                final int mYear = cal.get(Calendar.YEAR);
                int mMonth = cal.get(Calendar.MONTH);
                int mDay = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String m = "" + monthOfYear;
                                String d = "" + dayOfMonth;
                                if ((monthOfYear + 1) < 10)
                                    m = "0" + (monthOfYear + 1);

                                if (dayOfMonth < 10)
                                    d = "0" + dayOfMonth;

                                txt1.setText(d + "-" + m + "-" + mYear);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        addview();
        return v;
    }

    /* public void addview() {
         try {
             View view = LayoutInflater.from(getActivity()).inflate(R.mainLayout.layout_addadvice, null);
          //   AutoCompleteTextView autotxt = (AutoCompleteTextView) v.findViewById(R.id.autocompletetextview_1);
             // autotxt.setAdapter(adviceadapter);
            // autotxt.setThreshold(0);
             ImageView img = (ImageView) view.findViewById(R.id.imageview_1);


         if (mainLayout.getChildCount() == 0) {
             img.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_plus));
         } else {
             img.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_minus));
         }

             img.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     try {
                         int pos = (int) view.getTag();
                         Log.e("click ","click" +pos);
                         if (pos == 0) {
                             addview();
                         } else {
                             mainLayout.removeViewAt(pos);
                         }
                     } catch (Exception e) {
                         e.printStackTrace();
                     }
                 }
             });
             img.setTag(mainLayout.getChildCount());
             mainLayout.addView(view);
         }catch (Exception e)
         {
             e.printStackTrace();
         }
     }*/

    public void addview() {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.list_item_lablayoutv1, null);
        AutoCompleteTextView autotxt1 = v.findViewById(R.id.autocompletetextview_1);
        AutoCompleteTextView autotxt2 = v.findViewById(R.id.autocompletetextview_2);
        final AutoCompleteTextView autotxt3 = v.findViewById(R.id.autocompletetextview_3);
        autotxt1.setAdapter(labadapter);
        autotxt1.setThreshold(0);
        autotxt1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LabMasterData lab = (LabMasterData) adapterView.getAdapter().getItem(i);
                autotxt3.setText(lab.getNormalvalue());
            }
        });

        autotxt1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                LabMasterData lab = (LabMasterData) adapterView.getAdapter().getItem(i);
                autotxt3.setText(lab.getNormalvalue());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ImageView img = v.findViewById(R.id.imageview_1);
        img.setTag(layout.getChildCount());

        if (layout.getChildCount() == 0) {
            img.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_plus));
        } else {
            img.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_minus));
        }

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int pos = (int) view.getTag();
                    if (pos == 0) {
                        addview();
                    } else {
                        layout.removeViewAt(pos);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                   // Log.e("error ", "error " + e.toString());
                }
            }
        });
        layout.addView(v);
    }

    private void gotoGallary() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                REQUEST_GALLARY);
    }

    private void takePicture() {
        try {
            Intent takePictureIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);
            File photoFile = createNewFileName();
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent,
                        REQUEST_CAMERA);
            }
        } catch (Exception e) {
            Log.i(this.getClass().getSimpleName(), "" + e.toString());
            return;
        }
    }

    private File createNewFileName() {
        try {
            if (GlobalValues.RUNNING_IMAGE_PATH.length() > 0) {
                File ch = new File(GlobalValues.RUNNING_IMAGE_PATH);
                if (ch.exists()) {
                    ch.delete();
                }
            }
            File f = File.createTempFile(Constants.FILE_TEMP_NAME,
                    Constants.FILE_NAME_EXT,
                    new File(StructureClass.generate()));
            GlobalValues.RUNNING_IMAGE_PATH = f.getAbsolutePath();
              Log.e("FILE NAME:", GlobalValues.RUNNING_IMAGE_PATH);
            // showMsg(""+Globals.RUNNING_IMAGE_PATH);
            return f;
        } catch (Exception e) {
            Log.e("FILE NAME:", e.toString());
            // showMsg(""+e.toString());
            return null;
        }
    }

    public int calculateInSampleSize(BitmapFactory.Options options,
                                     int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public Bitmap decodeSampledBitmap(final String filepath, int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filepath, options);
        //Log.i("DECODING", "1");
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        // return BitmapFactory.decodeResource(res, resId, options);
        // Log.i("DECODING", "2");
        return BitmapFactory.decodeFile(filepath, options);
    }

    private void generateThumbnail() {
        try {
            Point originalSize = getImageSize(GlobalValues.RUNNING_IMAGE_PATH);
            Matrix m = checkAngle(GlobalValues.RUNNING_IMAGE_PATH);

            originalSize = imageProperties(originalSize, true);
            Bitmap bmp = decodeSampledBitmap(GlobalValues.RUNNING_IMAGE_PATH,
                    originalSize.x, originalSize.y);

            Point thumbsize = new Point(bmp.getWidth(), bmp.getHeight());
            thumbsize = imageProperties(thumbsize, false);

            Bitmap thumbnailme = Bitmap.createScaledBitmap(bmp, thumbsize.x,
                    thumbsize.y, true);
            Bitmap thumbnail = Bitmap.createBitmap(thumbnailme, 0, 0,
                    thumbnailme.getWidth(), thumbnailme.getHeight(), m, true);

            File f = new File(StructureClass.generate()
                    + Constants.FILE_NAME_CURRENT_THUMB);

            if (f.exists()) {
                f.delete();
            }
            FileOutputStream fos = new FileOutputStream(f);
            thumbnail.compress(Constants.FILE_FORMAT,
                    Constants.FILE_FORMAT_QUALITY_THUMB, fos);
            fos.flush();
            fos.close();

            Point orisize = new Point(bmp.getWidth(), bmp.getHeight());
            orisize = imageProperties(orisize, true);

            Bitmap originalme = Bitmap.createScaledBitmap(bmp, orisize.x,
                    orisize.y, true);
            Bitmap original = Bitmap.createBitmap(originalme, 0, 0,
                    originalme.getWidth(), originalme.getHeight(), m, true);

            Calendar cal = Calendar.getInstance();
            File fori = new File(StructureClass.generate() + cal.getTime() + Constants.FILE_NAME_EXT);
            //  + Constants.FILE_NAME_CURRENT);

            if (fori.exists()) {
                fori.delete();
            }

            FileOutputStream fosori = new FileOutputStream(fori);
            original.compress(Constants.FILE_FORMAT, Constants.FILE_FORMAT_QUALITY, fosori);
            fosori.flush();
            fosori.close();

            thumbnail.recycle();
            thumbnail = null;
            original.recycle();
            original = null;
            thumbnailme.recycle();
            thumbnailme = null;
            originalme.recycle();
            originalme = null;
            bmp.recycle();
            bmp = null;
            images.add(fori.getPath());
            adapterimages.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getSimpleName(), "" + e.toString());
        }
    }

    private Point getImageSize(final String fileName) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(fileName, options);
        Point p = new Point(options.outWidth, options.outHeight);
        return p;
    }

    private Matrix checkAngle(final String path) {
        Matrix mat = new Matrix();
        try {
            ExifInterface exif = new ExifInterface(path);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            int angle = 0;
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    angle = 90;
                    break;
                case ExifInterface.ORIENTATION_NORMAL:
                    angle = 0;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    angle = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    angle = 270;
                    break;
                default:
                    angle = 0;
                    break;
            }
            mat.postRotate(angle);
            return mat;
        } catch (Exception e) {
            mat.postRotate(0);
        }
        return mat;
    }

    public Point getHighResSize() {
        Point size = new Point();
        size.set(1500, 1500);
        Log.i("DEFAULT->", "[" + size.x + "," + size.y + "]");
        return size;
    }

    protected Point imageProperties(Point p, boolean high) {
        Point ori = getThumbnailSize();
        if (high) {
            ori = getHighResSize();
        }
        Log.i("ORI_SIZE", p.x + " " + p.y);
        if (p.x > ori.x || p.y > ori.y) {
            if (p.x > p.y) {
                p.y = (p.y * ori.x) / p.x;
                p.x = ori.x;
            } else {
                p.x = (p.x * ori.y) / p.y;
                p.y = ori.y;
            }
        }
        Log.i("ORI_SIZE", p.x + " " + p.y);
        return p;
    }

    protected Point imageProperties(Point p, Point ori) {
        if (p.x > ori.x || p.y > ori.y) {
            if (p.x > p.y) {
                p.y = (p.y * ori.x) / p.x;
                p.x = ori.x;
            } else {
                p.x = (p.x * ori.y) / p.y;
                p.y = ori.y;
            }
        }
        return p;
    }

    public Point getThumbnailSize() {
        Point size = new Point();
        size.set(500, 500);
        Log.i("DEFAULT->", "[" + size.x + "," + size.y + "]");
        return size;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Log.e("requestCode ", "requestCode " + requestCode + " " + REQUEST_CAMERA);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CAMERA:
                    generateThumbnail();
                    /*images.add(GlobalValues.RUNNING_IMAGE_PATH);
                    adapterimages.notifyDataSetChanged();*/
                    break;
                case 900:
                    try {
                        Uri uri = intent.getData();
                        final String id = DocumentsContract.getDocumentId(uri);
                        final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                        String[] projection1 = {MediaStore.Images.Media.DATA};
                        Cursor cursor1 = getActivity().getContentResolver().query(contentUri, projection1, null, null, null);
                        int column_index1 = cursor1.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        cursor1.moveToFirst();
                        String path = cursor1.getString(column_index1);
                        File sourcefile = new File(path);
                        File Destfile = new File(StructureClass.generate(), sourcefile.getName());
                        //    copyFile(sourcefile, Destfile);
                        images.add(sourcefile.getAbsolutePath());
                        adapterimages.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 100:
                    Uri uri1 = intent.getData();
                    String uriString = uri1.toString();
                    File myFile = new File(uriString);
                    String path = myFile.getAbsolutePath();
                    String displayName = null;
                    // Log.e("path", "path " + path);
                    if (uriString.startsWith("content://")) {
                        Cursor cursor1 = null;
                        try {
                            cursor1 = getActivity().getContentResolver().query(uri1, null, null, null, null);
                            if (cursor1 != null && cursor1.moveToFirst()) {
                                displayName = cursor1.getString(cursor1.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                final Uri filePathUri = Uri.parse(displayName);
                                File sourcefile = new File(filePathUri.getPath());
                                File Destfile = new File(StructureClass.generate(), displayName);
                                try {
                                    if (!Destfile.exists()) {
                                        Destfile.createNewFile();
                                    }
                                    copyDirectoryOneLocationToAnotherLocation(sourcefile, Destfile);
                                } catch (IOException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            cursor1.close();
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName = myFile.getName();
                        images.add(myFile.getPath());
                        adapterimages.notifyDataSetChanged();
                    }
                    break;
                case REQUEST_GALLARY:
                    Uri selectedImageUri = intent.getData();
                    String[] projection = {MediaStore.MediaColumns.DATA};
                    Cursor cursor = getActivity().managedQuery(selectedImageUri, projection,
                            null, null, null);
                    int column_index = cursor
                            .getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                    cursor.moveToFirst();
                    String selectedImagePath = cursor.getString(column_index);
                    GlobalValues.RUNNING_IMAGE_PATH = selectedImagePath;
                    Log.e("path ", "path " + GlobalValues.RUNNING_IMAGE_PATH);
                    generateThumbnail();
                /*    try {
                        Uri selectedImageUri = intent.getData();
                        String[] projection = {MediaStore.MediaColumns.DATA};
                        Cursor cursor1 = managedQuery(selectedImageUri, projection,
                                null, null, null);
                        int column_index = cursor1
                                .getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                        cursor1.moveToFirst();
                        String selectedImagePath = cursor1.getString(column_index);
                        images.add(selectedImagePath);
                        txt2.setVisibility(View.VISIBLE);
                        txt2.setText("(" + images.size() + ")");
                        templateadapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/
                    break;
                case PICK_IMAGE_MULTIPLE:
                  /*  try {
                        String[] imagesPath = intent.getStringExtra("data").split("\\|");

                        for (String anImagesPath : imagesPath) {
                            images.add(anImagesPath);
                            Log.e("path ", "path " + anImagesPath);
                        }
                        txt2.setVisibility(View.VISIBLE);
                        txt2.setText("(" + images.size() + ")");
                        templateadapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;*/

            }
        }
    }


    public void copyDirectoryOneLocationToAnotherLocation(File sourceLocation, File targetLocation) throws IOException {

        if (sourceLocation.isDirectory()) {
            if (!targetLocation.exists()) {
                targetLocation.mkdir();
            }
            String[] children = sourceLocation.list();
            for (int i = 0; i < sourceLocation.listFiles().length; i++) {

                copyDirectoryOneLocationToAnotherLocation(new File(sourceLocation, children[i]),
                        new File(targetLocation, children[i]));
            }
        } else {
            InputStream in = new FileInputStream(sourceLocation);

            OutputStream out = new FileOutputStream(targetLocation);

            // Copy the bits from instream to outstream
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        }
        images.add(targetLocation.getAbsolutePath());
        adapterimages.notifyDataSetChanged();
    }
    /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CAMERA:
                    images.add(Globals.RUNNING_IMAGE_PATH);
                    txt2.setVisibility(View.VISIBLE);
                    txt2.setText("(" + images.size() + ")");
                    templateadapter.notifyDataSetChanged();
                    break;
                case REQUEST_GALLARY:
                    try {
                        Uri selectedImageUri = intent.getData();
                        String[] projection = {MediaStore.MediaColumns.DATA};
                        Cursor cursor1 = managedQuery(selectedImageUri, projection,
                                null, null, null);
                        int column_index = cursor1
                                .getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                        cursor1.moveToFirst();
                        String selectedImagePath = cursor1.getString(column_index);
                        images.add(selectedImagePath);
                        txt2.setVisibility(View.VISIBLE);
                        txt2.setText("(" + images.size() + ")");
                        templateadapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case PICK_IMAGE_MULTIPLE:
                    try {
                        String[] imagesPath = intent.getStringExtra("data").split("\\|");

                        for (String anImagesPath : imagesPath) {
                            images.add(anImagesPath);
                            Log.e("path ", "path " + anImagesPath);
                        }
                        txt2.setVisibility(View.VISIBLE);
                        txt2.setText("(" + images.size() + ")");
                        templateadapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case PLACE_PICKER_REQUEST:
                    break;
            }
        }
    }*/


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        /*if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }*/
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //  mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
