package com.healthbank;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
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
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.healthbank.adapter.PendingReportAdapter;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Calendar;

public class UploadReportActivity extends ActivityCommon {
    Spinner sp1;
    EditText e1;
    Button bt1;
    ArrayList<String> mdataset;
    LinearLayoutManager mLayoutmanager;
    RecyclerView mrecyclerview;
    PendingReportAdapter adapter;

    public static String getPath(Context context, Uri uri) {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {"_data"};
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static Boolean copyFile(File sourceFile, File destFile)
            throws IOException {
        // if (!destFile.exists()) {
        destFile.createNewFile();

        FileChannel source = null;
        FileChannel destination = null;
        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        } finally {
            if (source != null)
                source.close();
            if (destination != null)
                destination.close();
        }
        return true;
        //  }
        //return false;
    }

    /**
     * Read a text file into a String.
     *
     * @param file File to read (will not seek, so things like /proc files are
     *             OK).
     * @return The contents of the file as a String.
     * @throws IOException
     */
    public static String readTextFile(File file) throws IOException {
        byte[] buffer = new byte[(int) file.length()];
        BufferedInputStream stream = new BufferedInputStream(
                new FileInputStream(file));
        stream.read(buffer);
        stream.close();

        return new String(buffer);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_report);
        setmaterialDesign();
        back();
        setTitle("Upload Report");
        attachUI();
    }

    public void attachUI() {
        StructureClass.defineContext(getApplicationContext());
        sp1 = findViewById(R.id.spinner1);
        e1 = findViewById(R.id.editText_1);
        bt1 = findViewById(R.id.button_1);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectFile();
            }
        });

        mdataset=new ArrayList<>();
        mdataset.add("data1");
        mdataset.add("data2");
        mdataset.add("data3");
        mrecyclerview= findViewById(R.id.recyclerview_1);
        mLayoutmanager=new LinearLayoutManager(UploadReportActivity.this);
        mrecyclerview.setLayoutManager(mLayoutmanager);
        adapter=new PendingReportAdapter(UploadReportActivity.this,mdataset,true);
        mrecyclerview.setAdapter(adapter);
        mrecyclerview.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(UploadReportActivity.this, R.drawable.recycler_divider)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_action_save:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void SelectFile() {
        final CharSequence[] items = {
                "Camera", "Photos", "Files"
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(UploadReportActivity.this);
        builder.setTitle("Make your selection");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    takePicture();
                } else if (item == 1) {
                    gotoGallary();
                } else if (item == 2) {
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.setType("*/*");
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    try {
                        startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), 900);
                    } catch (android.content.ActivityNotFoundException ex) {
                        // Potentially direct the user to the Market with a Dialog
                        ex.printStackTrace();
                    }
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
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
                        Constants.REQUEST_CAMERA);
            }
        } catch (Exception e) {
            Log.i(this.getClass().getSimpleName(), "" + e.toString());
            return;
        }
    }

    private File createNewFileName() {
        try {
            if (GlobalValues.RUNNING_IMAGE_PATH.length() > 0) {
                Calendar cal=Calendar.getInstance();
                File ch = new File(cal.getTimeInMillis()+Constants.FILE_NAME_EXT);
                if (ch.exists()) {
                    ch.delete();
                }
            }
            File f = File.createTempFile(Constants.FILE_TEMP_NAME,
                    Constants.FILE_NAME_EXT,
                    new File(StructureClass.generate()));
            GlobalValues.RUNNING_IMAGE_PATH = f.getAbsolutePath();
            return f;
        } catch (Exception e) {
            Log.i("FILE NAME:", e.toString());
            return null;
        }
    }

    private void gotoGallary() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                Constants.REQUEST_GALLARY);
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constants.REQUEST_CAMERA:
                    showImage();
                    break;
                case Constants.REQUEST_GALLARY:
                    Uri selectedImageUri = intent.getData();
                    String[] projection = {MediaStore.MediaColumns.DATA};
                    Cursor cursor = managedQuery(selectedImageUri, projection,
                            null, null, null);
                    int column_index = cursor
                            .getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                    cursor.moveToFirst();
                    String selectedImagePath = cursor.getString(column_index);
                    GlobalValues.RUNNING_IMAGE_PATH = selectedImagePath;
                    showImage();
                    break;
                case 900:
                    try {
                        Uri uri = intent.getData();
                        final String id = DocumentsContract.getDocumentId(uri);
                        final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                        String[] projection1 = {MediaStore.Images.Media.DATA};
                        Cursor cursor1 = getContentResolver().query(contentUri, projection1, null, null, null);
                        int column_index1 = cursor1.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        cursor1.moveToFirst();
                        String path = cursor1.getString(column_index1);
                        File sourcefile = new File(path);
                        File Destfile = new File(StructureClass.generate(), sourcefile.getName());
                        copyFile(sourcefile, Destfile);
                        mdataset.add(sourcefile.getName());
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 100:
                    Uri selectedImageUri11 = intent.getData();
                    String[] projection11 = {MediaStore.MediaColumns.DATA};
                    Cursor cursor111 = managedQuery(selectedImageUri11, projection11,
                            null, null, null);
                    int column_index11 = cursor111
                            .getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                    cursor111.moveToFirst();
                    String selectedImagePath11 = cursor111.getString(column_index11);
                    Uri uri1 = intent.getData();
                    String uriString = uri1.toString();
                    File myFile = new File(uriString);
                    String path = myFile.getPath();
                    File sourcefile = new File(path);
                    String displayName = null;
                    if (uriString.startsWith("content://")) {
                        Uri uri = intent.getData();
                        String file_path;
                        String extension = null;
                        if (uri.getScheme().compareTo("content") == 0) {
                            String[] filePathColumn = {MediaStore.Images.Media.DATA};
                            Cursor cursor11 = UploadReportActivity.this.getContentResolver().query(uri, filePathColumn, null, null, null);
                            cursor11.moveToFirst();
                            int columnIndex = cursor11.getColumnIndex(filePathColumn[0]);
                            String filePath = cursor11.getString(columnIndex);
                            cursor11.close();
                            String[] projection1 = {
                                    MediaStore.Images.Media.DATA
                            };
                            Cursor cursor1 = UploadReportActivity.this.getContentResolver().query(uri1, null, null, null, null);
                            //   Cursor cursor1 = CategorisedotherReportActivity.this.getContentResolver().query(uri, projection1, null, null, null);
                            if (cursor1.moveToFirst()) {
                                try {
                                    int column_index1 = cursor1.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME);
                                    final Uri filePathUri = Uri.parse(cursor1.getString(column_index1));
                                    final String file_name = filePathUri.getLastPathSegment();
                                    file_path = filePathUri.getPath();
                                    mdataset.add(file_name);
                                    adapter.notifyDataSetChanged();
                                  /*  listDataChild.get(this.listDataHeader.get(0)).add(file_name);
                                    listAdapter.notifyDataSetInvalidated();*/
                                    File s = new File("content://com.android.providers.downloads.documents/document/" + file_name);
                                    File Destfile = new File(StructureClass.generate(), file_name);
                                    copyFile(s, Destfile);
                                    Log.e("file path ", "file path " + file_path);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                       /* Cursor cursor1 = null;
                        try {
                            cursor1 = CategorisedotherReportActivity.this.getContentResolver().query(uri1, null, null, null, null);
                            if (cursor1 != null && cursor1.moveToFirst()) {
                                displayName = cursor1.getString(cursor1.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                cursor1.
                                listDataChild.get(this.listDataHeader.get(0)).add(displayName);
                                listAdapter.notifyDataSetInvalidated();
                                //  adapter.notifyDataSetChanged();
                                final Uri filePathUri = Uri.parse(displayName);
                                File sourcefile = new File(filePathUri.getPath());
                                File Destfile = new File(StructureClass.generate(), displayName);
                                try {
                                    if (!Destfile.exists()) {
                                        Destfile.createNewFile();
                                    }

                                    Log.e("copied ","copied "+filePathUri.getPath());
                               copyFile(sourcefile, Destfile);
                                 //   copyDirectoryOneLocationToAnotherLocation(sourcefile, Destfile);
                                } catch (IOException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        } finally {
                            cursor1.close();
                        }*/
                    } else if (uriString.startsWith("file://")) {
                        displayName = myFile.getName();
                      /*  listDataChild.get(this.listDataHeader.get(0)).add(displayName);
                        listAdapter.notifyDataSetInvalidated();*/
                        // Log.e("path ","path "+displayName);
                    }
               /*     int lastPos = FilePath.length() - FileName.length();
                    String Folder = FilePath.substring(0, lastPos);
                    filename thisFile = new filename(FileName);
                    Log.e("path ", "path" + FileName+" "+FilePath+" "+Folder+" "+thisFile.getFilename_Without_Ext()+" "+ thisFile.getExt());*/
                  /*  input = new File(path);
                    try {
                        txtParser = new TxtFileParser(input);
                    } catch (FileNotFoundException e) {
            *//* Exception handler must be here! *//*
                    }*/
                    break;
                case 500:
                    Uri uri = intent.getData();
                    String file_path;
                    String extension = null;
                    if (uri.getScheme().compareTo("content") == 0) {
                        String[] projection1 = {MediaStore.Images.Media.DATA};
                        Cursor cursor1 = getContentResolver().query(uri, projection1, null, null, null);
                        if (cursor1.moveToFirst()) {
                            int column_index1 = cursor1.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                            final Uri filePathUri = Uri.parse(cursor1.getString(column_index1));
                            final String file_name = filePathUri.getLastPathSegment();
                            file_path = filePathUri.getPath();
                            int lastDotPosition = file_name.lastIndexOf('.');
                            if (lastDotPosition > 0) {
                                String string3 = file_name.substring(lastDotPosition + 1);
                                extension = string3.toLowerCase();
                                /*listDataChild.get(this.listDataHeader.get(0)).add(string3);
                                listAdapter.notifyDataSetInvalidated();*/
                                Log.e("extension", "extension" + extension);
                            }
                            cursor1.close();

                        }
                    } else if (uri.getScheme().compareTo("file") == 0) {
                        try {
                            file_path = intent.getData().getPath();
                            String file_name = intent.getData().getLastPathSegment();

                            int lastDotPosition = file_name.lastIndexOf('.');
                            if (lastDotPosition > 0) {
                                String string3 = file_name.substring(lastDotPosition + 1);
                                extension = string3.toLowerCase();
                                mdataset.add(string3);
                                adapter.notifyDataSetChanged();
                                /*listDataChild.get(this.listDataHeader.get(0)).add(string3);
                                listAdapter.notifyDataSetInvalidated();*/
                                Log.e("extension", "extension" + extension);
                                //Extention.setText(extension);
                                //filename.setText(file_name);
                            }
                            return;
                        } catch (Exception e) {
                            Log.e("error ", "error " + e);
                        }
                    } else {

                    }
                    break;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private String getUriRealPathAboveKitkat(Context ctx, Uri uri) {
        String ret = "";

        if (ctx != null && uri != null) {

            if (isContentUri(uri)) {
                if (isGooglePhotoDoc(uri.getAuthority())) {
                    ret = uri.getLastPathSegment();
                } else {
                    ret = getImageRealPath(getContentResolver(), uri, null);
                }
            } else if (isFileUri(uri)) {
                ret = uri.getPath();
            } else if (isDocumentUri(ctx, uri)) {

                // Get uri related document id.
                String documentId = DocumentsContract.getDocumentId(uri);

                // Get uri authority.
                String uriAuthority = uri.getAuthority();

                if (isMediaDoc(uriAuthority)) {
                    String[] idArr = documentId.split(":");
                    if (idArr.length == 2) {
                        // First item is document type.
                        String docType = idArr[0];

                        // Second item is document real id.
                        String realDocId = idArr[1];

                        // Get content uri by document type.
                        Uri mediaContentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                        if ("image".equals(docType)) {
                            mediaContentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                        } else if ("video".equals(docType)) {
                            mediaContentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                        } else if ("audio".equals(docType)) {
                            mediaContentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                        }
                        // Get where clause with real document id.
                        String whereClause = MediaStore.Images.Media._ID + " = " + realDocId;
                        ret = getImageRealPath(getContentResolver(), mediaContentUri, whereClause);
                    }

                } else if (isDownloadDoc(uriAuthority)) {
                    // Build download uri.
                    Uri downloadUri = Uri.parse("content://downloads/public_downloads");
                    // Append download document id at uri end.
                    Uri downloadUriAppendId = ContentUris.withAppendedId(downloadUri, Long.valueOf(documentId));
                    ret = getImageRealPath(getContentResolver(), downloadUriAppendId, null);
                } else if (isExternalStoreDoc(uriAuthority)) {
                    String[] idArr = documentId.split(":");
                    if (idArr.length == 2) {
                        String type = idArr[0];
                        String realDocId = idArr[1];

                        if ("primary".equalsIgnoreCase(type)) {
                            ret = Environment.getExternalStorageDirectory() + "/" + realDocId;
                        }
                    }
                }
            }
        }

        return ret;
    }

    /* Check whether current android os version is bigger than kitkat or not. */
    private boolean isAboveKitKat() {
        boolean ret = false;
        ret = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        return ret;
    }

    /* Check whether this uri represent a document or not. */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private boolean isDocumentUri(Context ctx, Uri uri) {
        boolean ret = false;
        if (ctx != null && uri != null) {
            ret = DocumentsContract.isDocumentUri(ctx, uri);
        }
        return ret;
    }

    /* Check whether this uri is a content uri or not.
    *  content uri like content://media/external/images/media/1302716
    *  */
    private boolean isContentUri(Uri uri) {
        boolean ret = false;
        if (uri != null) {
            String uriSchema = uri.getScheme();
            if ("content".equalsIgnoreCase(uriSchema)) {
                ret = true;
            }
        }
        return ret;
    }

    /* Check whether this uri is a file uri or not.
    *  file uri like file:///storage/41B7-12F1/DCIM/Camera/IMG_20180211_095139.jpg
    * */
    private boolean isFileUri(Uri uri) {
        boolean ret = false;
        if (uri != null) {
            String uriSchema = uri.getScheme();
            if ("file".equalsIgnoreCase(uriSchema)) {
                ret = true;
            }
        }
        return ret;
    }

    /* Check whether this document is provided by ExternalStorageProvider. */
    private boolean isExternalStoreDoc(String uriAuthority) {
        boolean ret = false;

        if ("com.android.externalstorage.documents".equals(uriAuthority)) {
            ret = true;
        }

        return ret;
    }

    /* Check whether this document is provided by DownloadsProvider. */
    private boolean isDownloadDoc(String uriAuthority) {
        boolean ret = false;

        if ("com.android.providers.downloads.documents".equals(uriAuthority)) {
            ret = true;
        }

        return ret;
    }

    /* Check whether this document is provided by MediaProvider. */
    private boolean isMediaDoc(String uriAuthority) {
        boolean ret = false;

        if ("com.android.providers.media.documents".equals(uriAuthority)) {
            ret = true;
        }

        return ret;
    }

    /* Check whether this document is provided by google photos. */
    private boolean isGooglePhotoDoc(String uriAuthority) {
        boolean ret = false;

        if ("com.google.android.apps.photos.content".equals(uriAuthority)) {
            ret = true;
        }

        return ret;
    }

    /* Return uri represented document file real local path.*/
    private String getImageRealPath(ContentResolver contentResolver, Uri uri, String whereClause) {
        String ret = "";
        // Query the uri with condition.
        Cursor cursor = contentResolver.query(uri, null, whereClause, null, null);
        if (cursor != null) {
            boolean moveToFirst = cursor.moveToFirst();
            if (moveToFirst) {
                // Get columns name by uri type.
                String columnName = MediaStore.Images.Media.DATA;
                if (uri == MediaStore.Images.Media.EXTERNAL_CONTENT_URI) {
                    columnName = MediaStore.Images.Media.DATA;
                } else if (uri == MediaStore.Audio.Media.EXTERNAL_CONTENT_URI) {
                    columnName = MediaStore.Audio.Media.DATA;
                } else if (uri == MediaStore.Video.Media.EXTERNAL_CONTENT_URI) {
                    columnName = MediaStore.Video.Media.DATA;
                }
                // Get column index.
                int imageColumnIndex = cursor.getColumnIndex(columnName);
                // Get column value which is the uri related file local path.
                ret = cursor.getString(imageColumnIndex);
            }
        }
        return ret;
    }

    public void copyDirectoryOneLocationToAnotherLocation(File sourceLocation, File targetLocation) {

/*
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
        }*/
    }

    private void showImage() {
        generateThumbnail();
       /* Bitmap bmp = BitmapFactory.decodeFile(StructureClass.generate()
                + Constants.FILE_NAME_CURRENT);
        Point p = getRequiredSize();
        p = imageProperties(p, new Point(bmp.getWidth(), bmp.getHeight()));

        Bitmap attachment = decodeSampledBitmap(StructureClass.generate()
                + Constants.FILE_NAME_CURRENT, p.x, p.y);

        mediaContent = ""+ Calendar.getInstance(Locale.getDefault()).getTimeInMillis()+".jpeg";
        mediaType = ChatMediaType.Image.ordinal();

        //mediaType = ChatMediaType.Document.ordinal();
        Log.i(this.getClass().getSimpleName(), "File Name->"+Constants.FILE_NAME_CURRENT);
        genloading("sending");
        final String imagePath = StructureClass.generate()
                + Constants.FILE_NAME_CURRENT;
        ConnectionManager.getInstance(getApplicationContext()).submitImage(mediaContent, imagePath,MEDIA_TYPE_PNG);*/
    }

    private void generateThumbnail() {
        try {

            File f1=new File(GlobalValues.RUNNING_IMAGE_PATH);
            mdataset.add(f1.getName());
            adapter.notifyDataSetChanged();


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


            File fori = new File(StructureClass.generate()
                    + cal.getTime() + Constants.FILE_NAME_EXT);
            //  + Constants.FILE_NAME_CURRENT);
            if (fori.exists()) {
                fori.delete();
            }

            FileOutputStream fosori = new FileOutputStream(fori);
            original.compress(Constants.FILE_FORMAT,
                    Constants.FILE_FORMAT_QUALITY, fosori);
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
        /*    //Log.e("fori ","fori "+fori.getName()+" "+fori.getAbsolutePath());
            listAdapter.getGroup(0).add(fori.getName());
            adapter.notifyDataSetChanged();*/
           /* listDataChild.get(this.listDataHeader.get(0)).add(fori.getName());
            for (int i=0;i<listDataChild.get(this.listDataHeader.get(0)).size();i++)
            {
                Log.e("data ",""+listDataChild.get(this.listDataHeader.get(0)).get(i));
            }
            listAdapter.notifyDataSetInvalidated();*/
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getSimpleName(), "" + e.toString());
        }
    }

    public Bitmap decodeSampledBitmap(final String filepath,
                                      int reqWidth, int reqHeight) {
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
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            Log.i(this.getClass().getSimpleName(), "ORIENTATION " + orientation);

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

    public Point getRequiredSize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        size.set(display.getWidth(), display.getHeight());
        // size.set(1280, 720);
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

    public Point getPostShortSize() {
        Point size = getRequiredSize();
        size.y = size.y / 3;
        Log.i("DEFAULT->", "[" + size.x + "," + size.y + "]");
        return size;
    }

    public Point getHighResSize() {
        Point size = new Point();
        size.set(1500, 1500);
        Log.i("DEFAULT->", "[" + size.x + "," + size.y + "]");
        return size;
    }

    private class filename {
        String filename_Without_Ext = "";
        String ext = "";

        filename(String file) {
            int dotposition = file.lastIndexOf(".");
            filename_Without_Ext = file.substring(0, dotposition);
            ext = file.substring(dotposition + 1);
        }

        String getFilename_Without_Ext() {
            return filename_Without_Ext;
        }

        String getExt() {
            return ext;
        }
    }
}
