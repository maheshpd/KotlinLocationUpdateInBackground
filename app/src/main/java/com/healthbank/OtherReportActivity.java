package com.healthbank;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;

import com.healthbank.adapter.OtherReportAdpater;
import com.squareup.okhttp.MediaType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
public class OtherReportActivity extends ActivityCommon {
    private static final MediaType MEDIA_TYPE_PNG = MediaType
            .parse("image/png");
    private static final MediaType MEDIA_TYPE_JPEG = MediaType
            .parse("image/jpeg");
    private static final MediaType MEDIA_TYPE_PDF = MediaType
            .parse("application/pdf");
    private static final MediaType MEDIA_TYPE_WORD = MediaType
            .parse("application/msword");
    private static final MediaType MEDIA_TYPE_TEXT_TXT = MediaType
            .parse("text/txt");
    private static final MediaType MEDIA_TYPE_TEXT_CSV = MediaType
            .parse("text/csv");
    private static final MediaType MEDIA_TYPE_video = MediaType
            .parse("video/*");
    private static final MediaType MEDIA_TYPE_XL = MediaType
            .parse("application/vnd.ms-excel");
    private static final MediaType MEDIA_TYPE_PPT = MediaType
            .parse("application/vnd.ms-powerpoint");
    RecyclerView recyclerView1;
    LinearLayoutManager mLayoutmanager;
    OtherReportAdpater adapter;
    ArrayList<String> mdatset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_report);
        setmaterialDesign();
        setTitle("Other Report");
        back();
        attachUI();
    }

    public void attachUI() {
        StructureClass.defineContext(getApplicationContext());
        mdatset = new ArrayList<>();
        mdatset.add("Other1");
        mdatset.add("Other2");
        mdatset.add("Other3");
        mdatset.add("Other4");
        recyclerView1 = findViewById(R.id.recyclerview_1);
        recyclerView1.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(OtherReportActivity.this, R.drawable.recycler_divider)));
        mLayoutmanager = new LinearLayoutManager(this);
        recyclerView1.setLayoutManager(mLayoutmanager);
        adapter = new OtherReportAdpater(this, mdatset);
        recyclerView1.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.attach_file, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_attach:
                SelectFile();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void SelectFile() {
        final CharSequence[] items = {
                "Camera", "Photos", "Files"
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(OtherReportActivity.this);
        builder.setTitle("Make your selection");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    takePicture();
                } else if (item == 1) {
                    gotoGallary();
                } else if (item == 2) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("*/*");
                    startActivityForResult(intent, 100);
                 /*   String folderPath = Environment.getExternalStorageDirectory() + "";
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    Uri myUri = Uri.parse(folderPath);
                    intent.setDataAndType(myUri, "**///*");
                   // startActivityForResult(intent, 100);*/
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
                File ch = new File(GlobalValues.RUNNING_IMAGE_PATH);
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
            // showMsg(""+e.toString());
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
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent intent) {
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
                case 100:
                    Uri uri1 = intent.getData();
                    String uriString = uri1.toString();
                    File myFile = new File(uriString);
                    String path = myFile.getAbsolutePath();
                    String displayName = null;

                    if (uriString.startsWith("content://")) {
                        Cursor cursor1 = null;
                        try {
                            cursor1 = OtherReportActivity.this.getContentResolver().query(uri1, null, null, null, null);
                            if (cursor1 != null && cursor1.moveToFirst()) {
                                displayName = cursor1.getString(cursor1.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                mdatset.add(displayName);
                                adapter.notifyDataSetChanged();
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
                        } finally {
                            cursor1.close();
                        }
                    } else if(uriString.startsWith("file://")) {
                        displayName = myFile.getName();
                        mdatset.add(displayName);
                        adapter.notifyDataSetChanged();
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
                            Log.e("path ", "path " + file_path);

                            int lastDotPosition = file_name.lastIndexOf('.');
                            if (lastDotPosition > 0) {
                                String string3 = file_name.substring(lastDotPosition + 1);
                                extension = string3.toLowerCase();
                                mdatset.add(string3);
                                adapter.notifyDataSetChanged();
                                Log.e("extension", "extension" + extension);
                            }
                            cursor1.close();
/*
                            mediaContent = ""+ Calendar.getInstance(Locale.getDefault()).getTimeInMillis()+"."+extension;

                            MediaType mediatype = null;

                            if(extension.equalsIgnoreCase("xls")||extension.equalsIgnoreCase("xlsx"))
                            {  mediatype=MEDIA_TYPE_XL;

                            }else if(extension.equalsIgnoreCase("docx")||extension.equalsIgnoreCase("doc"))
                            {
                                mediatype=MEDIA_TYPE_WORD;
                            }else if(extension.equalsIgnoreCase("txt"))
                            {
                                mediatype=MEDIA_TYPE_TEXT_TXT;

                            }else if(extension.equalsIgnoreCase("csv"))
                            {
                                mediatype=MEDIA_TYPE_TEXT_CSV;
                            }
                            else if(extension.equalsIgnoreCase("ppt")||extension.equalsIgnoreCase("pptx")||extension.equalsIgnoreCase("pps"))
                            {
                                mediatype=MEDIA_TYPE_PPT;
                            }
                            else if(extension.equalsIgnoreCase("pdf"))
                            {
                                mediatype=MEDIA_TYPE_PDF;
                            }
                            else if(extension.equalsIgnoreCase("tif")||extension.equalsIgnoreCase("tiff"))
                            {


                            }
                            else if(extension.equalsIgnoreCase("mp3")||extension.equalsIgnoreCase("mp4")||extension.equalsIgnoreCase("avi"))
                            {
                                mediatype=MEDIA_TYPE_video;
                            }
                            else
                            {
                                Log.e("else","else");
                                Toast.makeText(getApplicationContext(), "File format is not supported", Toast.LENGTH_SHORT).show();
                            }
                            File Destfile = new File(getDirPath(), mediaContent);
                            File sourcefile=new File(file_path);
                            try {
                                if (!Destfile.exists()) {
                                    Destfile.createNewFile();
                                }
                                copyDirectoryOneLocationToAnotherLocation(sourcefile,Destfile);
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            setTitle("Sending...");
                            ConnectionManager.getInstance(getApplicationContext()).submitImage(mediaContent, file_path,mediatype);*/
                        }
                    } else if (uri.getScheme().compareTo("file") == 0) {
                        try {
                            file_path = intent.getData().getPath();
                            String file_name = intent.getData().getLastPathSegment();

                            int lastDotPosition = file_name.lastIndexOf('.');
                            if (lastDotPosition > 0) {
                                String string3 = file_name.substring(lastDotPosition + 1);
                                extension = string3.toLowerCase();
                                mdatset.add(string3);
                                adapter.notifyDataSetChanged();
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

    public void copyDirectoryOneLocationToAnotherLocation(File sourceLocation, File targetLocation)
            throws IOException {

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
            //Log.e("fori ","fori "+fori.getName()+" "+fori.getAbsolutePath());
            mdatset.add(fori.getName());
            adapter.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getSimpleName(), "" + e.toString());
        }
    }

    public  Bitmap decodeSampledBitmap(final String filepath,
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

    public  int calculateInSampleSize(BitmapFactory.Options options,
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

    private class filename{
        String filename_Without_Ext = "";
        String ext = "";

        filename(String file){
            int dotposition= file.lastIndexOf(".");
            filename_Without_Ext = file.substring(0,dotposition);
            ext = file.substring(dotposition + 1);
        }

        String getFilename_Without_Ext(){
            return filename_Without_Ext;
        }

        String getExt(){
            return ext;
        }
    }
}
