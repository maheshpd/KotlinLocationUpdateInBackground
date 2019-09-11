package com.healthbank;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.healthbank.classes.Advice;
import com.healthbank.classes.AdviceHeader;
import com.healthbank.classes.BillingClass;
import com.healthbank.classes.BillingDetails;
import com.healthbank.classes.DetailPrescriptionData;
import com.healthbank.classes.Detailedrx;
import com.healthbank.classes.Drug;
import com.healthbank.classes.ExaminationHeader;
import com.healthbank.classes.Group;
import com.healthbank.classes.LabHeader;
import com.healthbank.classes.Master;
import com.healthbank.classes.Option;
import com.healthbank.classes.OptionAnswer;
import com.healthbank.classes.Questions;
import com.healthbank.classes.Radiology;
import com.healthbank.classes.RadiologyHeader;
import com.healthbank.classes.SaveAnswerdata;
import com.healthbank.classes.SubGroups;
import com.healthbank.database.DatabaseHelper;
import com.healthbank.groupvideocall.openvcall.AGApplication;
import com.healthbank.groupvideocall.openvcall.model.ConstantApp;
import com.healthbank.groupvideocall.openvcall.ui.ChatActivity;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.healthbank.LogUtils.LOGE;
import static com.healthbank.StructureClass.getSecondaryStorage;

public class ActivityCommon extends AppCompatActivity {
    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private final int MEGABYTE = 1024 * 1024;
    Toolbar toolbar;
    Context context;
    ProgressDialog dialog = null;
    String tag = "";
    BaseColor mColorAccent;
    float mHeadingFontSize = 20.0f;
    float mValueFontSize = 20.0f;
    BaseFont urName;
    LineSeparator lineSeparator;
    Font TitleFont;
    Font normalFont;
    Font headingFont;
    Font bluenormalFont;
    Context mContext;
    Font.FontFamily fontmily = Font.FontFamily.valueOf("TIMES_ROMAN");
    int font = Font.ITALIC;
    private BroadcastReceiver responseRec = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            try {
                Bundle b = intent.getExtras();
                int statuscode = b.getInt(Constants.BROADCAST_RESPONSE_CODE);
                int urlaccess = b.getInt(Constants.BROADCAST_URL_ACCESS);
                String data = "";
                if (b.containsKey(Constants.BROADCAST_DATA))
                    data = b.getString(Constants.BROADCAST_DATA);
                onResponsed(statuscode, urlaccess, data);
            /*    if (urlaccess == Connection.GET_PRESCRIPTION.ordinal()) {
                    Intent intentrx = new Intent(ActivityCommon.this, RxActivityv1.class);
                    intentrx.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    startActivity(intentrx);
                }*/
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public static void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        StructureClass.defineContext(this);
        mContext = this;
    }

    public void initializefonts() {
        try {
            mColorAccent = new BaseColor(0, 153, 204, 255);
            lineSeparator = new LineSeparator();
            urName = BaseFont.createFont("assets/fonts/brandon_medium.otf", "UTF-8", BaseFont.EMBEDDED);
            lineSeparator.setLineColor(new BaseColor(0, 0, 0, 68));
            bluenormalFont = new Font(urName, mValueFontSize, Font.NORMAL, BaseColor.BLACK);

            TitleFont = new Font(urName, 24.0f, Font.NORMAL, BaseColor.BLACK);
            normalFont = new Font(urName, mValueFontSize, Font.NORMAL, BaseColor.DARK_GRAY);
            headingFont = new Font(urName, mHeadingFontSize, Font.NORMAL, mColorAccent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gotoptdetails() {
        Intent intent = new Intent(this, PatientDetailsActivity.class);
        startActivity(intent);
    }

    public void gotoptdetails(Bundle bundle) {
        Intent intent = new Intent(this, PatientDetailsActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void browsefile() {
        try {
            Intent intent = new Intent();
            intent.setType("*/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 101);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openurlfile(String url) {
        genloading("loading");
        new DownloadFile().execute(url, "hmis.pdf");
    }

    public void downloadFile(String fileUrl, File directory, String ext) {
        try {
            //  Log.e("url ", "url " + fileUrl);
            URL url = new URL(fileUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(directory);
            int totalSize = urlConnection.getContentLength();

            byte[] buffer = new byte[MEGABYTE];
            int bufferLength = 0;
            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, bufferLength);
            }
            fileOutputStream.close();
            view(ext, fileUrl);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void view(String extension, String fileUrl) {
        dismissLoading();
        try {
            File file = new File(Environment.getExternalStorageDirectory() + "/HMIS/" + "hmis." + extension);  // -> filename = hmis.pdf
            Uri photoURI = FileProvider.getUriForFile(context, "com.healthbank.fileprovider", file);
            if (extension.equalsIgnoreCase("xls") || extension.equalsIgnoreCase("xlsx")) {
                Log.e("xls", "xls");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(photoURI, "application/vnd.ms-excel");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivityForResult(intent, 101);
            } else if (extension.equalsIgnoreCase("docx") || extension.equalsIgnoreCase("doc")) {
                Log.e("docx", "docx");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(photoURI, "application/msword");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                //progressDialog.dismiss();
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivityForResult(intent, 101);
            } else if (extension.equalsIgnoreCase("txt") || extension.equalsIgnoreCase("csv")) {
                //Log.e("txt", "txt");
                final Intent intent = new Intent();
                intent.setAction(android.content.Intent.ACTION_VIEW);
                final MimeTypeMap mime = MimeTypeMap.getSingleton();
                final String ext = file.getName().substring(
                        file.getName().indexOf(".") + 1);
                final String type = mime.getMimeTypeFromExtension(ext);
                intent.setDataAndType(photoURI, type);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivityForResult(intent, 101);
            } else if (extension.equalsIgnoreCase("ppt") || extension.equalsIgnoreCase("pptx") || extension.equalsIgnoreCase("pps")) {
                Log.e("ppt", "ppt");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(photoURI, "application/vnd.ms-powerpoint");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NO_HISTORY);
                //progressDialog.dismiss();
                startActivityForResult(intent, 101);
            } else if (extension.equalsIgnoreCase("pdf")) {
                Log.e("image", "image");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(photoURI, "application/pdf");
                startActivityForResult(intent, 101);
            } else if (extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg") || extension.equalsIgnoreCase("png")) {
                gototoshowfile(fileUrl, extension);
            } else if (extension.equalsIgnoreCase("tif") || extension.equalsIgnoreCase("tiff")) {
                Intent picIntent = new Intent();
                picIntent.setAction(android.content.Intent.ACTION_VIEW);
                picIntent.setDataAndType(photoURI, "image/*");
                picIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(picIntent);
            } else if (extension.equalsIgnoreCase("mp3") || extension.equalsIgnoreCase("mp4") || extension.equalsIgnoreCase("avi")) {
                Log.e("video", "video");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(photoURI, "video/*");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivityForResult(intent, 101);
            } else {
                Log.e("else", "else");
                Toast.makeText(getApplicationContext(), "File format is not supported", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

/*
    public void gotoTemplateActivity(String visitid, String visitdate) {
        Intent intent = new Intent(this, TemplateQuestionActivity.class);
        intent.putExtra("visitid", visitid);
        intent.putExtra("visitdate", visitdate);
        startActivity(intent);
    }
*/

    public void gototoshowfile(String url, String extension) {
        Intent intent = new Intent(this, ActivityShowFile.class);
        intent.putExtra("url", url);
        intent.putExtra("extension", extension);
        startActivity(intent);
    }

    public void openfile(String filename) {
        try {
            File file = new File(filename);
            Uri photoURI = FileProvider.getUriForFile(context, "com.healthbank.fileprovider", file);
            String extension = "";
            int lastDotPosition = filename.lastIndexOf('.');
            if (lastDotPosition > 0) {
                String string3 = filename.substring(lastDotPosition + 1);
                extension = string3.toLowerCase();
            }
            //  Log.e("exe ","exe "+extension+" "+file.getAbsolutePath());
            if (extension.equalsIgnoreCase("xls") || extension.equalsIgnoreCase("xlsx")) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(photoURI, "application/vnd.ms-excel");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivityForResult(intent, 101);
            } else if (extension.equalsIgnoreCase("docx") || extension.equalsIgnoreCase("doc")) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(photoURI, "application/msword");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivityForResult(intent, 101);
            } else if (extension.equalsIgnoreCase("txt") || extension.equalsIgnoreCase("csv")) {
                final Intent intent = new Intent();
                intent.setAction(android.content.Intent.ACTION_VIEW);
                final MimeTypeMap mime = MimeTypeMap.getSingleton();
                final String ext = file.getName().substring(file.getName().indexOf(".") + 1);
                final String type = mime.getMimeTypeFromExtension(ext);
                intent.setDataAndType(photoURI, type);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivityForResult(intent, 101);
            } else if (extension.equalsIgnoreCase("ppt") || extension.equalsIgnoreCase("pptx") || extension.equalsIgnoreCase("pps")) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(photoURI, "application/vnd.ms-powerpoint");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivityForResult(intent, 101);
            } else if (extension.equalsIgnoreCase("pdf")) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(photoURI, "application/pdf");
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivityForResult(intent, 101);
            } else if (extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg") || extension.equalsIgnoreCase("png")) {
                Intent intent = new Intent(this, ImageViewActivity.class);
                intent.putExtra("filename", filename);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(intent);
            } else if (extension.equalsIgnoreCase("tif") || extension.equalsIgnoreCase("tiff")) {
                Intent picIntent = new Intent();
                picIntent.setAction(android.content.Intent.ACTION_VIEW);
                picIntent.setDataAndType(photoURI, "image/*");
                picIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(picIntent);
            } else if (extension.equalsIgnoreCase("mp3") || extension.equalsIgnoreCase("mp4") || extension.equalsIgnoreCase("avi")) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(photoURI, "video/*");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivityForResult(intent, 101);
            } else {
                Toast.makeText(getApplicationContext(), "File format is not supported", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void genloading(String msg) {
        try {
            if (!this.isFinishing()) {
                dialog = ProgressDialog.show(this, msg, "Please wait");
                dialog.setCancelable(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismissLoading() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public void gotoexamination(int pos) {
        switch (pos) {
            case 0:
                Intent eyespecialist = new Intent(this, EyeSpecialistActivity.class);
                startActivity(eyespecialist);
                break;
            case 1:
                break;
        }
    }

    public void gotoupdateeyedetails(long id) {
        Intent intent = new Intent(this, UpdateeyecheckupdataActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    public void gotonext(int pos) {
        switch (pos) {
            case R.drawable.ic_labdetails:
                Intent intentlab = new Intent(this, PathalogyActivity.class);
                intentlab.putExtra("flag", "Pathology");
                startActivity(intentlab);
                break;
            case R.drawable.icon_notes:
                Intent intentdiagnostic = new Intent(this, PathalogyActivity.class);
                intentdiagnostic.putExtra("flag", "Diagnostic");
                startActivity(intentdiagnostic);
                break;
            case R.drawable.icon_medicinebox:
                // if (InternetUtils.getInstance(this).available()) {
                Intent intenttemplate = new Intent(this, TemplateQuestionActivity.class);

                startActivity(intenttemplate);
              /*  } else {
                    Toast.makeText(this, "No Network", Toast.LENGTH_LONG).show();
                }*/
                break;
            case R.drawable.icon_medicinebox1:
                Intent bookappointment = new Intent(this, BookAppointmentActivity.class);
                startActivity(bookappointment);
                break;
            case R.drawable.icon_medicinebox2:
                Intent intentvitals = new Intent(this, Vital.class);
                startActivity(intentvitals);
                break;
            case R.drawable.icon_medicinebox3:
                if (InternetUtils.getInstance(this).available()) {
                    ConnectionManager.getInstance(this).getprescription(Integer.toString(GlobalValues.selectedpt.getPatientid()), "all", 0);
                } else {
                    Intent intentrx = new Intent(this, RxActivityv1.class);
                    intentrx.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intentrx);
                }
                break;
            case R.drawable.icon_medicinebox4:
                Intent intentlab1 = new Intent(this, Lab.class);
                startActivity(intentlab1);
                break;
            case R.drawable.icon_medicinebox5:
                Intent intentexamination = new Intent(this, Examination.class);
                startActivity(intentexamination);
                break;
            case R.drawable.icon_medicinebox6:
                Intent intentadvice = new Intent(this, AdviceActivity.class);
                startActivity(intentadvice);
                break;
            case R.drawable.icon_medicinebox7:
                Intent intentexaminationv1 = new Intent(this, ExaminationvActivityv1.class);
                startActivity(intentexaminationv1);
                break;
            case R.drawable.icon_medicinebox8:
                Intent intentbloodreport = new Intent(this, BloodReportActivity.class);
                startActivity(intentbloodreport);
                break;
            case R.drawable.icon_medicinebox10:
                Intent intentotherreport = new Intent(this, RadiologyActivity.class);
                startActivity(intentotherreport);
                break;
            case R.drawable.icon_medicinebox11:
                Intent intentuploadreport = new Intent(this, UploadReportActivity.class);
                startActivity(intentuploadreport);
                break;

            case R.drawable.icon_medicinebox12:
                Intent intentnotes = new Intent(this, NotesActivity.class);
                startActivity(intentnotes);
                break;

            case R.drawable.icon_history:
                if (InternetUtils.getInstance(this).available()) {
                    Intent intent = new Intent(this, WebViewActivity.class);
                    intent.putExtra("url", URLS.gethistoryurl(Integer.toString(GlobalValues.selectedpt.getPatientid())));
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "No Network", Toast.LENGTH_LONG).show();
                }
                break;

     /*       case 0:
                Intent bookappointment = new Intent(this, BookAppointmentActivity.class);
                startActivity(bookappointment);
                break;
            case 1:
                Intent medicalhistory = new Intent(this, MedicalHistoryActivity.class);
                startActivity(medicalhistory);
                break;
            case 2:
                Intent intentvitals = new Intent(this, Vital.class);
                startActivity(intentvitals);
                break;
            case 3:
                Intent intent = new Intent(this, RxActivityv1.class);
                startActivity(intent);
                break;
            case 4:
                Intent intentlab = new Intent(this, Lab.class);
                startActivity(intentlab);
                break;
            case 5:
                Intent intentexamination = new Intent(this, Examination.class);
                startActivity(intentexamination);
                break;
            case 6:
                Intent intentexaminationv1 = new Intent(this, ExaminationvActivityv1.class);
                startActivity(intentexaminationv1);
                break;
            case 7:
                Intent intentadvice = new Intent(this, AdviceActivity.class);
                startActivity(intentadvice);
                break;
            case 8:
                Intent intentpayment = new Intent(this, BillingActivity.class);
                startActivity(intentpayment);
              *//*  Intent intentpayment = new Intent(this, PaymentActivity.class);
                startActivity(intentpayment);*//*
                break;
            *//*case 8:
                Intent intentbloodreport = new Intent(this, BloodReportActivity.class);
                startActivity(intentbloodreport);
                break;*//*
            case 9:
                Intent intentotherreport = new Intent(this, RadiologyActivity.class);
                startActivity(intentotherreport);
                break;
           *//* case 10:
                Intent intentuploadreport = new Intent(this, UploadReportActivity.class);
                startActivity(intentuploadreport);
                break;*//*
            case 10:
                Intent intentnotes = new Intent(this, NotesActivity.class);
                startActivity(intentnotes);
                break;
            case 11:
                Intent intenttemplate = new Intent(this, VisitsActivity.class);
                startActivity(intenttemplate);
                break;
            default:
                break;*/
        }
    }

    public void gotonext(String s) {
        Log.e("selected data ","selected data "+s);
        if (s.equalsIgnoreCase("Lab Details")) {
            Intent intentlab = new Intent(this, PathalogyActivity.class);
            intentlab.putExtra("flag", "Pathology");
            startActivity(intentlab);
        } else if (s.equalsIgnoreCase("Radiology")) {
            Intent intentdiagnostic = new Intent(this, PathalogyActivity.class);
            intentdiagnostic.putExtra("flag", "Diagnostic");
            startActivity(intentdiagnostic);
        } /*else if (s.equalsIgnoreCase("Medical History")) {
            Intent intenttemplate = new Intent(this, TemplateQuestionActivity.class);
            intenttemplate.putExtra("category","Medical History");
            startActivity(intenttemplate);
        }*//* else if (s.equalsIgnoreCase("History")) {
            Intent intentlab = new Intent(this, PathalogyActivity.class);
            intentlab.putExtra("flag", "Pathology");
            startActivity(intentlab);
        }*/ else if (s.equalsIgnoreCase("Appointment/Medical Visit")) {
            Intent bookappointment = new Intent(this, BookAppointmentActivity.class);
            startActivity(bookappointment);
        } else if (s.equalsIgnoreCase("Vitals")) {
            Intent intentvitals = new Intent(this, Vital.class);
            startActivity(intentvitals);
        } else if (s.equalsIgnoreCase("Upload Files")) {
            Intent intentuploadreport = new Intent(this, UploadReportActivity.class);
            startActivity(intentuploadreport);
        } else if (s.equalsIgnoreCase("Rx")) {
            if (InternetUtils.getInstance(this).available()) {
                ConnectionManager.getInstance(this).getprescription(Integer.toString(GlobalValues.selectedpt.getPatientid()), "all", 0);
            } else {
                Intent intentrx = new Intent(this, RxActivityv1.class);
                intentrx.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intentrx);
            }
        } /*else if (s.equalsIgnoreCase("Billing")) {
            Intent intentlab = new Intent(this, PathalogyActivity.class);
            intentlab.putExtra("flag", "Pathology");
            startActivity(intentlab);
        }*/ /*else if (s.equalsIgnoreCase("Counselling")) {
            Intent intentlab = new Intent(this, PathalogyActivity.class);
            intentlab.putExtra("flag", "Pathology");
            startActivity(intentlab);
        } else if (s.equalsIgnoreCase("eDocument")) {
            Intent intentlab = new Intent(this, PathalogyActivity.class);
            intentlab.putExtra("flag", "Pathology");
            startActivity(intentlab);
        } */else if (s.equalsIgnoreCase("Initial Assessment")) {
            Intent intenttemplate = new Intent(this, TemplateQuestionActivity.class);
            intenttemplate.putExtra("category","Initial Assessment");
            startActivity(intenttemplate);
        }/* else if (s.equalsIgnoreCase("Immunization")) {
            Intent intentlab = new Intent(this, PathalogyActivity.class);
            intentlab.putExtra("flag", "Pathology");
            startActivity(intentlab);
        }*/ /*else if (s.equalsIgnoreCase("Payment")) {
            Intent intentlab = new Intent(this, PathalogyActivity.class);
            intentlab.putExtra("flag", "Pathology");
            startActivity(intentlab);
        }*/ else if (s.equalsIgnoreCase("Prescription")) {
            Intent intentlab = new Intent(this, PathalogyActivity.class);
            intentlab.putExtra("flag", "Pathology");
            startActivity(intentlab);
        } /*else if (s.equalsIgnoreCase("Refund")) {
            Intent intentlab = new Intent(this, PathalogyActivity.class);
            intentlab.putExtra("flag", "Pathology");
            startActivity(intentlab);
        } else if (s.equalsIgnoreCase("Refund")) {
            Intent intentlab = new Intent(this, PathalogyActivity.class);
            intentlab.putExtra("flag", "Pathology");
            startActivity(intentlab);
        }*/
        else if (s.equalsIgnoreCase("Ros")) {
            Intent intenttemplate = new Intent(this, TemplateQuestionActivity.class);
            intenttemplate.putExtra("category","Ros");
            startActivity(intenttemplate);
        }else {
            Intent intenttemplate = new Intent(this, TemplateQuestionActivity.class);
            intenttemplate.putExtra("category",s);
            startActivity(intenttemplate);
        }
       /* else if (s.equalsIgnoreCase("Notes")) {
            Intent intentlab = new Intent(this, PathalogyActivity.class);
            intentlab.putExtra("flag", "Pathology");
            startActivity(intentlab);
        }
        else if (s.equalsIgnoreCase("Surgery Note")) {
            Intent intentlab = new Intent(this, PathalogyActivity.class);
            intentlab.putExtra("flag", "Pathology");
            startActivity(intentlab);
        }*/
       /* switch (pos) {
            case R.drawable.ic_labdetails:
                Intent intentlab = new Intent(this, PathalogyActivity.class);
                intentlab.putExtra("flag", "Pathology");
                startActivity(intentlab);
                break;
            case R.drawable.icon_notes:
                Intent intentdiagnostic = new Intent(this, PathalogyActivity.class);
                intentdiagnostic.putExtra("flag", "Diagnostic");
                startActivity(intentdiagnostic);
                break;
            case R.drawable.icon_medicinebox:
                // if (InternetUtils.getInstance(this).available()) {
                Intent intenttemplate = new Intent(this, TemplateQuestionActivity.class);
                startActivity(intenttemplate);
              *//*  } else {
                    Toast.makeText(this, "No Network", Toast.LENGTH_LONG).show();
                }*//*
                break;
            case R.drawable.icon_medicinebox1:
                Intent bookappointment = new Intent(this, BookAppointmentActivity.class);
                startActivity(bookappointment);
                break;
            case R.drawable.icon_medicinebox2:
                Intent intentvitals = new Intent(this, Vital.class);
                startActivity(intentvitals);
                break;
            case R.drawable.icon_medicinebox3:
                if (InternetUtils.getInstance(this).available()) {
                    ConnectionManager.getInstance(this).getprescription(Integer.toString(GlobalValues.selectedpt.getPatientid()), "all", 0);
                } else {
                    Intent intentrx = new Intent(this, RxActivityv1.class);
                    intentrx.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT );
                    startActivity(intentrx);
                }
                break;
            case R.drawable.icon_medicinebox4:
                Intent intentlab1 = new Intent(this, Lab.class);
                startActivity(intentlab1);
                break;
            case R.drawable.icon_medicinebox5:
                Intent intentexamination = new Intent(this, Examination.class);
                startActivity(intentexamination);
                break;
            case R.drawable.icon_medicinebox6:
                Intent intentadvice = new Intent(this, AdviceActivity.class);
                startActivity(intentadvice);
                break;
            case R.drawable.icon_medicinebox7:
                Intent intentexaminationv1 = new Intent(this, ExaminationvActivityv1.class);
                startActivity(intentexaminationv1);
                break;
            case R.drawable.icon_medicinebox8:
                Intent intentbloodreport = new Intent(this, BloodReportActivity.class);
                startActivity(intentbloodreport);
                break;
            case R.drawable.icon_medicinebox10:
                Intent intentotherreport = new Intent(this, RadiologyActivity.class);
                startActivity(intentotherreport);
                break;
            case R.drawable.icon_medicinebox11:
                Intent intentuploadreport = new Intent(this, UploadReportActivity.class);
                startActivity(intentuploadreport);
                break;

            case R.drawable.icon_medicinebox12:
                Intent intentnotes = new Intent(this, NotesActivity.class);
                startActivity(intentnotes);
                break;

            case R.drawable.icon_history:
                if (InternetUtils.getInstance(this).available()) {
                    Intent intent = new Intent(this, WebViewActivity.class);
                    intent.putExtra("url", URLS.gethistoryurl(Integer.toString(GlobalValues.selectedpt.getPatientid())));
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "No Network", Toast.LENGTH_LONG).show();
                }
                break;
        }*/
    }

    public void editmaster(int position, String mastername) {
        switch (position) {
            case 0:
                Intent intentbillbook = new Intent(this, AddBillBookActivity.class);
                startActivity(intentbillbook);
                break;
            case 2:
                Intent intent = new Intent(this, AddProcedureActivity.class);
                intent.putExtra("mastername", mastername);
                startActivity(intent);
                break;
            default:
                Intent intentaddmaster = new Intent(this, MasterActivity.class);
                intentaddmaster.putExtra("mastername", mastername);
                startActivity(intentaddmaster);
                break;
        }
    }

    public void gotopttimelime() {
        Intent intent = new Intent(this, TimelineActivity.class);
        startActivity(intent);
    }

    public void setmaterialDesign() {
        try {
            tag = "ActivityCommon";
            toolbar = findViewById(R.id.toolbar);
            toolbar.setTitleTextColor(getResources().getColor(R.color.White));
            setSupportActionBar(toolbar);
            LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(responseRec, new IntentFilter(Constants.BROADCAST_WIZARD));
            context = this;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registerreceiver() {
        try {
            context = this;
            LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(responseRec, new IntentFilter(Constants.BROADCAST_WIZARD));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gotourl(String url) {
        Intent viewIntent =
                new Intent("android.intent.action.VIEW",
                        Uri.parse(url));
        startActivity(viewIntent);
    }

    protected void back() {
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        try {
            LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(responseRec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    protected void onResponsed(int statuscode, int accesscode, String data) {
        try {
            if (dialog != null) {
                dialog.dismiss();
            }
            dismissLoading();
        } catch (Exception e) {
            e.printStackTrace();
            // reporterror(tag,e.toString() );
        }
    }

    public void gototableactivity() {
        Intent intent = new Intent(context, VitalsActivity.class);
        startActivity(intent);
    }

    public void gotocategarisedotherreports() {
        Intent intent = new Intent(context, CategorisedotherReportActivity.class);
        startActivity(intent);
    }

    public void gotodatedetails() {
        Intent intent = new Intent(context, DatesReportActivity.class);
        startActivity(intent);
    }

    public void createPdf(DetailPrescriptionData prescription, int type) {
        String dest = FileUtils.getAppPath(this) + "Prescription from " + GlobalValues.DrName + " " + prescription.getDate() + ".pdf";
        File f = new File(dest);
        if (f.exists()) {
            f.delete();
        }
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(dest));
            document.open();

            document.setPageSize(PageSize.A4);
            document.addCreationDate();

            BaseColor mColorAccent = new BaseColor(0, 153, 204, 255);
            float mHeadingFontSize = 20.0f;
            float mValueFontSize = 20.0f;

            BaseFont urName = BaseFont.createFont("assets/fonts/brandon_medium.otf", "UTF-8", BaseFont.EMBEDDED);
            LineSeparator lineSeparator = new LineSeparator();
            lineSeparator.setLineColor(new BaseColor(0, 0, 0, 68));

            Font TitleFont = new Font(urName, 24.0f, Font.NORMAL, BaseColor.BLACK);
            Font normalFont = new Font(urName, mValueFontSize, Font.NORMAL, BaseColor.BLACK);
            Font headingFont = new Font(urName, mHeadingFontSize, Font.NORMAL, mColorAccent);

            Chunk TitleChunk = new Chunk(GlobalValues.selectedpt.getFirstName().toUpperCase(), TitleFont);
            Paragraph TitleParagraph = new Paragraph(TitleChunk);
            TitleParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(TitleParagraph);
            PdfPTable tableheader = new PdfPTable(new float[]{1});
            tableheader.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            tableheader.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            Chunk msrnoChunk = new Chunk("\nSr.No. " + GlobalValues.selectedpt.getSrno(), normalFont);
            Phrase srno = new Phrase(msrnoChunk);
            tableheader.addCell(srno);

            Chunk dateChunk = new Chunk(prescription.getDate(), normalFont);
            Phrase date = new Phrase(dateChunk);
            tableheader.addCell(date);
            document.add(tableheader);

            String labdata = "";
            if (prescription.getLabs().size() > 0) {
                for (int i = 0; i < prescription.getLabs().size(); i++) {
                    labdata = labdata + prescription.getLabs().get(i) + ",";
                }

                Chunk labChunk = new Chunk("Labs:", headingFont);
                Paragraph mOrderIdParagraph = new Paragraph(labChunk);
                document.add(mOrderIdParagraph);

                Chunk labValueChunk = new Chunk(labdata.substring(0, labdata.length() - 1), normalFont);
                Paragraph labValueParagraph = new Paragraph(labValueChunk);
                document.add(labValueParagraph);

                // Adding Line Breakable Space....
                document.add(new Paragraph(""));
                // Adding Horizontal Line...
                document.add(new Chunk(lineSeparator));
                // Adding Line Breakable Space....
                document.add(new Paragraph(""));
            }

            // Fields of Order Details...
            if (prescription.getDiagnostics().size() > 0) {
                String diagnosticdata = "";
                for (int i = 0; i < prescription.getDiagnostics().size(); i++) {
                    diagnosticdata = diagnosticdata + prescription.getDiagnostics().get(i) + ",";
                }

                Chunk DiagnosticsChunk = new Chunk("Diagnostics:", headingFont);
                Paragraph DiagnosticsParagraph = new Paragraph(DiagnosticsChunk);
                document.add(DiagnosticsParagraph);

                Chunk diagnosisChunk = new Chunk(diagnosticdata.substring(0, diagnosticdata.length() - 1), normalFont);
                Paragraph diagnosisParagraph = new Paragraph(diagnosisChunk);
                document.add(diagnosisParagraph);
                document.add(new Paragraph(""));
                document.add(new Chunk(lineSeparator));
                document.add(new Paragraph(""));
            }

            if (prescription.getPrescriptiondata().size() > 0) {
                Chunk mdrugChunk = new Chunk("Drug:", headingFont);
                Paragraph mdrugParagraph = new Paragraph(mdrugChunk);
                document.add(mdrugParagraph);

                PdfPTable table = new PdfPTable(new float[]{2, 1, 1, 2});
                table.setWidthPercentage(100.0f);

                table.getDefaultCell().setPadding(10);

                table.setSpacingBefore(20);
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                Chunk mdrugheaderChunk = new Chunk("Drug", headingFont);
                Phrase p1 = new Phrase(mdrugheaderChunk);
                table.addCell(p1);

                Chunk mheader3Chunk = new Chunk("Doses", headingFont);
                Phrase p3 = new Phrase(mheader3Chunk);
                table.addCell(p3);

                Chunk mheader4Chunk = new Chunk("Days", headingFont);
                Phrase p4 = new Phrase(mheader4Chunk);
                table.addCell(p4);
                Chunk mheader2Chunk = new Chunk("Instructions", headingFont);
                Phrase p2 = new Phrase(mheader2Chunk);
                table.addCell(p2);

                for (int i = 0; i < prescription.getPrescriptiondata().size(); i++) {
                    Drug p = prescription.getPrescriptiondata().get(i);
                    table.addCell(p.getDrugName() + " (" + p.getDrugUnit() + ")\n" + p.getBrand() + "\n");
                    table.addCell(p.getDuration());
                    table.addCell(p.getDoses());
                    table.addCell(p.getInstruction());
                }

                document.add(table);
                document.add(new Paragraph(""));
                document.add(new Chunk(lineSeparator));
                document.add(new Paragraph(""));
            }

            if (prescription.getAdvice().size() > 0) {
                String advicedata = "";
                for (int i = 0; i < prescription.getAdvice().size(); i++) {
                    advicedata = advicedata + prescription.getAdvice().get(i) + ",";
                }
                // Fields of Order Details...

                Chunk AdviceChunk = new Chunk("Advice", headingFont);
                Paragraph AdviceParagraph = new Paragraph(AdviceChunk);
                document.add(AdviceParagraph);


                Chunk AdvicevalueChunk = new Chunk(advicedata.substring(0, advicedata.length() - 1), normalFont);
                Paragraph AdviceValueParagraph = new Paragraph(AdvicevalueChunk);
                document.add(AdviceValueParagraph);

                document.add(new Paragraph(""));
                document.add(new Chunk(lineSeparator));
                document.add(new Paragraph(""));
            }
            document.close();

            if (type == 1) {
                FileUtils.openFile(this, new File(dest));
            } else if (type == 2) {
                sharefileonwhatsapp(new File(dest));
            } else {
                printDocument(new File(dest));
            }
        } catch (IOException | DocumentException ie) {
            LOGE("createPdf: Error " + ie.getLocalizedMessage());
        } catch (ActivityNotFoundException ae) {
            Toast.makeText(this, "No application found to open this file.", Toast.LENGTH_SHORT).show();
        }
    }

    public void createRxPdf(Detailedrx prescription, int type) {
        Date date1 = DateUtils.parseDate(prescription.getTitle(), "dd-MM-yyyy");
        String d = DateUtils.formatDate(date1, "dd MMM yyyy");

        String dest = FileUtils.getAppPath(this) + "Prescription from " + GlobalValues.DrName + " " + d + ".pdf";
        File f = new File(dest);
        if (f.exists()) {
            f.delete();
        }
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(dest));
            document.open();

            document.setPageSize(PageSize.A4);
            document.addCreationDate();

            BaseColor mColorAccent = new BaseColor(0, 153, 204, 255);
            float mHeadingFontSize = 20.0f;
            float mValueFontSize = 20.0f;

            BaseFont urName = BaseFont.createFont("assets/fonts/brandon_medium.otf", "UTF-8", BaseFont.EMBEDDED);
            LineSeparator lineSeparator = new LineSeparator();
            lineSeparator.setLineColor(new BaseColor(0, 0, 0, 68));

            Font TitleFont = new Font(urName, 24.0f, Font.NORMAL, BaseColor.BLACK);
            Font normalFont = new Font(urName, mValueFontSize, Font.NORMAL, BaseColor.BLACK);
            Font headingFont = new Font(urName, mHeadingFontSize, Font.NORMAL, mColorAccent);

            Chunk TitleChunk = new Chunk(GlobalValues.selectedpt.getFirstName().toUpperCase(), TitleFont);
            Paragraph TitleParagraph = new Paragraph(TitleChunk);
            TitleParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(TitleParagraph);
            PdfPTable tableheader = new PdfPTable(new float[]{1});
            tableheader.getDefaultCell().setHorizontalAlignment(
                    Element.ALIGN_RIGHT);
            tableheader.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            Chunk msrnoChunk = new Chunk("\nSr.No. " + GlobalValues.selectedpt.getSrno(), normalFont);
            Phrase srno = new Phrase(msrnoChunk);
            tableheader.addCell(srno);
            if (d != null) {
                Chunk dateChunk = new Chunk(d, normalFont);
                Phrase date = new Phrase(dateChunk);
                tableheader.addCell(date);
                document.add(tableheader);
            }
            if (prescription.getMdataset().size() > 0) {
                Chunk mdrugChunk = new Chunk("Rx:", headingFont);
                Paragraph mdrugParagraph = new Paragraph(mdrugChunk);
                document.add(mdrugParagraph);

                PdfPTable table = new PdfPTable(new float[]{2, 1, 1, 2});
                table.setWidthPercentage(100.0f);

                table.getDefaultCell().setPadding(10);

                table.setSpacingBefore(20);
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                Chunk mdrugheaderChunk = new Chunk("Drug", headingFont);
                Phrase p1 = new Phrase(mdrugheaderChunk);
                table.addCell(p1);

                Chunk mheader3Chunk = new Chunk("Doses", headingFont);
                Phrase p3 = new Phrase(mheader3Chunk);
                table.addCell(p3);

                Chunk mheader4Chunk = new Chunk("Days", headingFont);
                Phrase p4 = new Phrase(mheader4Chunk);
                table.addCell(p4);
                Chunk mheader2Chunk = new Chunk("Instructions", headingFont);
                Phrase p2 = new Phrase(mheader2Chunk);
                table.addCell(p2);

                for (int i = 0; i < prescription.getMdataset().size(); i++) {
                    Drug p = prescription.getMdataset().get(i);
                    table.addCell(p.getDrugName());// + " (" + p.getDrugUnit() + ")\n" + p.getBrand() + "\n"
                    table.addCell(p.getDuration());
                    table.addCell(p.getDoses());
                    table.addCell(p.getInstruction());
                }

                document.add(table);
                document.add(new Paragraph(""));
                document.add(new Chunk(lineSeparator));
                document.add(new Paragraph(""));
            }

            document.close();

            if (type == 1) {
                FileUtils.openFile(this, new File(dest));
            } else if (type == 2) {
                sharefileonwhatsapp(new File(dest));
            } else {
                printDocument(new File(dest));
            }
        } catch (IOException | DocumentException ie) {
            LOGE("createPdf: Error " + ie.getLocalizedMessage());
        } catch (ActivityNotFoundException ae) {
            Toast.makeText(this, "No application found to open this file.", Toast.LENGTH_SHORT).show();
        }
    }

    public void createBillingPdf(BillingClass bill, int type) {
        Date date1 = DateUtils.parseDate(bill.getCreatedate().split(" ")[0], "yyyy-MM-dd");
        String d = DateUtils.formatDate(date1, "dd MMM yyyy");

        String dest = FileUtils.getAppPath(this) + "Bill from " + GlobalValues.DrName + " " + d + ".pdf";
        File f = new File(dest);
        if (f.exists()) {
            f.delete();
        }
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(dest));
            document.open();

            document.setPageSize(PageSize.A4);
            document.addCreationDate();

            BaseColor mColorAccent = new BaseColor(0, 153, 204, 255);
            float mHeadingFontSize = 20.0f;
            float mValueFontSize = 20.0f;
            float smallValueFontSize = 18.0f;
            BaseFont urName = BaseFont.createFont("assets/fonts/brandon_medium.otf", "UTF-8", BaseFont.EMBEDDED);
            LineSeparator lineSeparator = new LineSeparator();
            lineSeparator.setLineColor(new BaseColor(0, 0, 0, 68));

            Font TitleFont = new Font(urName, 24.0f, Font.NORMAL, BaseColor.BLACK);
            Font normalFont = new Font(urName, mValueFontSize, Font.NORMAL, BaseColor.BLACK);
            Font smallFont = new Font(urName, smallValueFontSize, Font.NORMAL, BaseColor.DARK_GRAY);
            Font headingFont = new Font(urName, mHeadingFontSize, Font.NORMAL, mColorAccent);

            Chunk TitleChunk = new Chunk(GlobalValues.selectedpt.getFirstName().toUpperCase(), TitleFont);
            Paragraph TitleParagraph = new Paragraph(TitleChunk);
            TitleParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(TitleParagraph);
            PdfPTable tableheader = new PdfPTable(new float[]{1});
            tableheader.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            tableheader.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            Chunk msrnoChunk = new Chunk("\nSr.No. " + GlobalValues.selectedpt.getSrno(), normalFont);
            Phrase srno = new Phrase(msrnoChunk);
            tableheader.addCell(srno);

            Chunk dateChunk = new Chunk(d, normalFont);
            Phrase date = new Phrase(dateChunk);
            tableheader.addCell(date);
            document.add(tableheader);

            JSONArray array = DatabaseHelper.getInstance(this).getbillingdetails(bill.getId());
            Gson gson = new Gson();
            Type token = new TypeToken<ArrayList<BillingDetails>>() {
            }.getType();
            ArrayList<BillingDetails> mdataset = new ArrayList<>();
            mdataset = gson.fromJson(array.toString(), token);
            if (mdataset.size() > 0) {
                Chunk mdrugChunk = new Chunk("", headingFont);
                Paragraph mdrugParagraph = new Paragraph(mdrugChunk);
                document.add(mdrugParagraph);

                PdfPTable table = new PdfPTable(new float[]{2, 1, 1, 2});
                table.setWidthPercentage(100.0f);

                table.getDefaultCell().setPadding(10);

                table.setSpacingBefore(20);
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                Chunk mdrugheaderChunk = new Chunk("Name", headingFont);
                Phrase p1 = new Phrase(mdrugheaderChunk);
                table.addCell(p1);

                Chunk mheader3Chunk = new Chunk("Unit/Day", headingFont);
                Phrase p3 = new Phrase(mheader3Chunk);
                table.addCell(p3);

                Chunk mheader4Chunk = new Chunk("Amount", headingFont);
                Phrase p4 = new Phrase(mheader4Chunk);
                table.addCell(p4);
                Chunk mheader2Chunk = new Chunk("Sub Total", headingFont);
                Phrase p2 = new Phrase(mheader2Chunk);
                table.addCell(p2);

                for (int i = 0; i < mdataset.size(); i++) {
                    BillingDetails billingdata = mdataset.get(i);
                    table.addCell(billingdata.getItem());
                    table.addCell(billingdata.getUnit());
                    table.addCell(billingdata.getUnitcost());
                    table.addCell("" + (Double.parseDouble(billingdata.getUnit()) * Double.parseDouble(billingdata.getUnitcost())));
                }
                document.add(table);

                PdfPTable tableTotalamount = new PdfPTable(new float[]{1});
                tableTotalamount.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
                tableTotalamount.getDefaultCell().setBorder(PdfPCell.NO_BORDER);

                Chunk totalamountChunk = new Chunk("Total Amount : " + this.getResources().getString(R.string.char_rupees) + bill.getTotalamt(), smallFont);
                Phrase totalamount = new Phrase(totalamountChunk);
                tableTotalamount.addCell(totalamount);

                Chunk discountChunk = new Chunk("Discount : " + this.getResources().getString(R.string.char_rupees) + bill.getDiscount(), smallFont);
                Phrase discount = new Phrase(discountChunk);
                tableTotalamount.addCell(discount);

                Chunk taxChunk = new Chunk("Tax : " + this.getResources().getString(R.string.char_rupees) + bill.getTaxamt(), smallFont);
                Phrase taxamount = new Phrase(taxChunk);
                tableTotalamount.addCell(taxamount);

                Chunk totalpayableamountChunk = new Chunk("Total Payable Amount : " + this.getResources().getString(R.string.char_rupees) + bill.getFinalamount(), smallFont);
                Phrase totalpayableamount = new Phrase(totalpayableamountChunk);
                tableTotalamount.addCell(totalpayableamount);

                Chunk paidamountChunk = new Chunk("Total Paid : " + this.getResources().getString(R.string.char_rupees) + bill.getPaidamt(), smallFont);
                Phrase paidamount = new Phrase(paidamountChunk);
                tableTotalamount.addCell(paidamount);

                Chunk totalbalanceChunk = new Chunk("Total Balance: " + this.getResources().getString(R.string.char_rupees) + bill.getDueamt(), smallFont);
                Phrase totalbalanceamount = new Phrase(totalbalanceChunk);
                tableTotalamount.addCell(totalbalanceamount);
                document.add(tableTotalamount);

                document.add(new Paragraph(""));
                document.add(new Chunk(lineSeparator));
                document.add(new Paragraph(""));
            }

            document.close();

            if (type == 1) {
                FileUtils.openFile(this, new File(dest));
            } else if (type == 2) {
                sharefileonwhatsapp(new File(dest));
            } else {
                printDocument(new File(dest));
            }

        } catch (IOException | DocumentException ie) {
            LOGE("createPdf: Error " + ie.getLocalizedMessage());
        } catch (ActivityNotFoundException ae) {
            Toast.makeText(this, "No application found to open this file.", Toast.LENGTH_SHORT).show();
        }
    }

    public void createRadiologyPdf(RadiologyHeader radiologyHeader, int type) {
        Date date1 = DateUtils.parseDate(radiologyHeader.getTitle(), "dd-MM-yyyy");
        String d = DateUtils.formatDate(date1, "dd MMM yyyy");

        String dest = FileUtils.getAppPath(this) + "Radiologyreport from " + GlobalValues.DrName + " " + d + ".pdf";
        File f = new File(dest);
        if (f.exists()) {
            f.delete();
        }
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(dest));
            document.open();

            document.setPageSize(PageSize.A4);
            document.addCreationDate();

            BaseColor mColorAccent = new BaseColor(0, 153, 204, 255);
            float mHeadingFontSize = 20.0f;
            float mValueFontSize = 20.0f;

            BaseFont urName = BaseFont.createFont("assets/fonts/brandon_medium.otf", "UTF-8", BaseFont.EMBEDDED);
            LineSeparator lineSeparator = new LineSeparator();
            lineSeparator.setLineColor(new BaseColor(0, 0, 0, 68));

            Font TitleFont = new Font(urName, 24.0f, Font.NORMAL, BaseColor.BLACK);
            Font normalFont = new Font(urName, mValueFontSize, Font.NORMAL, BaseColor.BLACK);
            Font headingFont = new Font(urName, mHeadingFontSize, Font.NORMAL, mColorAccent);

            Chunk TitleChunk = new Chunk(GlobalValues.selectedpt.getFirstName().toUpperCase(), TitleFont);
            Paragraph TitleParagraph = new Paragraph(TitleChunk);
            TitleParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(TitleParagraph);
            PdfPTable tableheader = new PdfPTable(new float[]{1});
            tableheader.getDefaultCell().setHorizontalAlignment(
                    Element.ALIGN_RIGHT);
            tableheader.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            Chunk msrnoChunk = new Chunk("\nSr.No. " + GlobalValues.selectedpt.getSrno(), normalFont);
            Phrase srno = new Phrase(msrnoChunk);
            tableheader.addCell(srno);

            Chunk dateChunk = new Chunk(d, normalFont);
            Phrase date = new Phrase(dateChunk);
            tableheader.addCell(date);
            document.add(tableheader);

            if (radiologyHeader.getMdataset().size() > 0) {
                Chunk mdrugChunk = new Chunk("Radiology Report:", headingFont);
                Paragraph mdrugParagraph = new Paragraph(mdrugChunk);
                document.add(mdrugParagraph);

                PdfPTable table = new PdfPTable(new float[]{1, 1});
                table.setWidthPercentage(100.0f);

                table.getDefaultCell().setPadding(10);

                table.setSpacingBefore(20);
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                Chunk mdrugheaderChunk = new Chunk("Title", headingFont);
                Phrase p1 = new Phrase(mdrugheaderChunk);
                table.addCell(p1);

                Chunk mheader3Chunk = new Chunk("Result", headingFont);
                Phrase p3 = new Phrase(mheader3Chunk);
                table.addCell(p3);


                for (int i = 0; i < radiologyHeader.getMdataset().size(); i++) {
                    Radiology p = radiologyHeader.getMdataset().get(i);

                    table.addCell(p.getTitle());
                    table.addCell(p.getResult());
                }

                document.add(table);
                document.add(new Paragraph(""));
                document.add(new Chunk(lineSeparator));
                document.add(new Paragraph(""));
            }

            document.close();
            if (type == 1) {
                FileUtils.openFile(this, new File(dest));
            } else if (type == 2) {
                sharefileonwhatsapp(new File(dest));
            } else {
                printDocument(new File(dest));
            }

        } catch (IOException | DocumentException ie) {
            LOGE("createPdf: Error " + ie.getLocalizedMessage());
        } catch (ActivityNotFoundException ae) {
            Toast.makeText(this, "No application found to open this file.", Toast.LENGTH_SHORT).show();
        }
    }

    public void createLabPdf(LabHeader prescription, int type) {
        Date date1 = DateUtils.parseDate(prescription.getTitle(), "dd-MM-yyyy");
        String d = DateUtils.formatDate(date1, "dd MMM yyyy");

        String dest = FileUtils.getAppPath(this) + "Lab Test Report from " + GlobalValues.DrName + " " + d + ".pdf";
        File f = new File(dest);
        if (f.exists()) {
            f.delete();
        }
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(dest));
            document.open();

            document.setPageSize(PageSize.A4);
            document.addCreationDate();

            BaseColor mColorAccent = new BaseColor(0, 153, 204, 255);
            float mHeadingFontSize = 20.0f;
            float mValueFontSize = 20.0f;

            BaseFont urName = BaseFont.createFont("assets/fonts/brandon_medium.otf", "UTF-8", BaseFont.EMBEDDED);
            LineSeparator lineSeparator = new LineSeparator();
            lineSeparator.setLineColor(new BaseColor(0, 0, 0, 68));

            Font TitleFont = new Font(urName, 24.0f, Font.NORMAL, BaseColor.BLACK);
            Font normalFont = new Font(urName, mValueFontSize, Font.NORMAL, BaseColor.BLACK);
            Font headingFont = new Font(urName, mHeadingFontSize, Font.NORMAL, mColorAccent);

            Chunk TitleChunk = new Chunk(GlobalValues.selectedpt.getFirstName().toUpperCase(), TitleFont);
            Paragraph TitleParagraph = new Paragraph(TitleChunk);

            TitleParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(TitleParagraph);
            PdfPTable tableheader = new PdfPTable(new float[]{1});
            tableheader.getDefaultCell().setHorizontalAlignment(
                    Element.ALIGN_RIGHT);
            tableheader.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            Chunk msrnoChunk = new Chunk("\nSr.No. " + GlobalValues.selectedpt.getSrno(), normalFont);
            Phrase srno = new Phrase(msrnoChunk);
            tableheader.addCell(srno);

            Chunk dateChunk = new Chunk(d, normalFont);
            Phrase date = new Phrase(dateChunk);
            tableheader.addCell(date);
            document.add(tableheader);

            if (prescription.getMdataset().size() > 0) {
                Chunk mdrugChunk = new Chunk("Lab Test Report", headingFont);
                Paragraph mdrugParagraph = new Paragraph(mdrugChunk);
                document.add(mdrugParagraph);

                PdfPTable table = new PdfPTable(new float[]{1, 1, 1});
                table.setWidthPercentage(100.0f);

                table.getDefaultCell().setPadding(10);

                table.setSpacingBefore(20);
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                Chunk mdrugheaderChunk = new Chunk("Test", headingFont);
                Phrase p1 = new Phrase(mdrugheaderChunk);
                table.addCell(p1);

                Chunk mheader3Chunk = new Chunk("Result", headingFont);
                Phrase p3 = new Phrase(mheader3Chunk);
                table.addCell(p3);

                Chunk mheader4Chunk = new Chunk("Normal", headingFont);
                Phrase p4 = new Phrase(mheader4Chunk);
                table.addCell(p4);

                for (int i = 0; i < prescription.getMdataset().size(); i++) {
                    com.healthbank.classes.Lab p = prescription.getMdataset().get(i);
                    table.addCell(p.getTest());
                    table.addCell(p.getResult());
                    table.addCell(p.getNormal());
                }

                document.add(table);
                document.add(new Paragraph(""));
                document.add(new Chunk(lineSeparator));
                document.add(new Paragraph(""));
            }
            document.close();
            if (type == 1) {
                FileUtils.openFile(this, new File(dest));
            } else if (type == 2) {
                sharefileonwhatsapp(new File(dest));
            } else {
                printDocument(new File(dest));
            }

        } catch (IOException | DocumentException ie) {
            LOGE("createPdf: Error " + ie.getLocalizedMessage());
        } catch (ActivityNotFoundException ae) {
            Toast.makeText(this, "No application found to open this file.", Toast.LENGTH_SHORT).show();
        }
    }

    public void createadvicePdf(AdviceHeader prescription, int type) {
        Date date1 = DateUtils.parseDate(prescription.getTitle(), "dd-MM-yyyy");
        String d = DateUtils.formatDate(date1, "dd MMM yyyy");
        String dest = FileUtils.getAppPath(this) + "Lab Test Report from " + GlobalValues.DrName + " " + d + ".pdf";
        File f = new File(dest);
        if (f.exists()) {
            f.delete();
        }
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(dest));
            document.open();

            document.setPageSize(PageSize.A4);
            document.addCreationDate();

            BaseColor mColorAccent = new BaseColor(0, 153, 204, 255);
            float mHeadingFontSize = 20.0f;
            float mValueFontSize = 20.0f;

            BaseFont urName = BaseFont.createFont("assets/fonts/brandon_medium.otf", "UTF-8", BaseFont.EMBEDDED);
            LineSeparator lineSeparator = new LineSeparator();
            lineSeparator.setLineColor(new BaseColor(0, 0, 0, 68));

            Font TitleFont = new Font(urName, 24.0f, Font.NORMAL, BaseColor.BLACK);
            Font normalFont = new Font(urName, mValueFontSize, Font.NORMAL, BaseColor.BLACK);
            Font headingFont = new Font(urName, mHeadingFontSize, Font.NORMAL, mColorAccent);

            Chunk TitleChunk = new Chunk(GlobalValues.selectedpt.getFirstName().toUpperCase(), TitleFont);
            Paragraph TitleParagraph = new Paragraph(TitleChunk);
            TitleParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(TitleParagraph);
            PdfPTable tableheader = new PdfPTable(new float[]{1});
            tableheader.getDefaultCell().setHorizontalAlignment(
                    Element.ALIGN_RIGHT);
            tableheader.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            Chunk msrnoChunk = new Chunk("\nSr.No. " + GlobalValues.selectedpt.getSrno(), normalFont);
            Phrase srno = new Phrase(msrnoChunk);
            tableheader.addCell(srno);

            Chunk dateChunk = new Chunk(d, normalFont);
            Phrase date = new Phrase(dateChunk);
            tableheader.addCell(date);
            document.add(tableheader);
            Font zapfdingbats = new Font();
            zapfdingbats.setSize(20.0f);
            Chunk bullet = new Chunk("\u2022", zapfdingbats);
            Paragraph titleparagraph = new Paragraph();
            titleparagraph.add(new Phrase(" " + "Advice" + " ", headingFont));
            document.add(titleparagraph);
            List list = new List(List.UNORDERED);
            list.setListSymbol(bullet);
            for (Advice item : prescription.getMdataset()) {
                list.add(new ListItem(" " + item.getName(), zapfdingbats));
            }
            document.add(list);
            document.close();
            if (type == 1) {
                FileUtils.openFile(this, new File(dest));
            } else if (type == 2) {
                sharefileonwhatsapp(new File(dest));
            } else {
                printDocument(new File(dest));
            }

        } catch (IOException | DocumentException ie) {
            LOGE("createPdf: Error " + ie.getLocalizedMessage());
        } catch (ActivityNotFoundException ae) {
            Toast.makeText(this, "No application found to open this file.", Toast.LENGTH_SHORT).show();
        }
    }

    public void createexaminationPdf(ExaminationHeader examination, int type) {
        Date date1 = DateUtils.parseDate(examination.getTitle(), "dd-MM-yyyy");
        String d = DateUtils.formatDate(date1, "dd MMM yyyy");

        String dest = FileUtils.getAppPath(this) + "Examination from " + GlobalValues.DrName + " " + d + ".pdf";
        File f = new File(dest);
        if (f.exists()) {
            f.delete();
        }
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(dest));
            document.open();

            document.setPageSize(PageSize.A4);
            document.addCreationDate();

            BaseColor mColorAccent = new BaseColor(0, 153, 204, 255);
            float mHeadingFontSize = 20.0f;
            float mValueFontSize = 20.0f;

            BaseFont urName = BaseFont.createFont("assets/fonts/brandon_medium.otf", "UTF-8", BaseFont.EMBEDDED);
            LineSeparator lineSeparator = new LineSeparator();
            lineSeparator.setLineColor(new BaseColor(0, 0, 0, 68));
            Font TitleFont = new Font(urName, 24.0f, Font.NORMAL, BaseColor.BLACK);
            Font normalFont = new Font(urName, mValueFontSize, Font.NORMAL, BaseColor.BLACK);
            Font headingFont = new Font(urName, mHeadingFontSize, Font.NORMAL, mColorAccent);

            Chunk TitleChunk = new Chunk(GlobalValues.selectedpt.getFirstName().toUpperCase(), TitleFont);
            Paragraph TitleParagraph = new Paragraph(TitleChunk);
            TitleParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(TitleParagraph);
            PdfPTable tableheader = new PdfPTable(new float[]{1});
            tableheader.getDefaultCell().setHorizontalAlignment(
                    Element.ALIGN_RIGHT);
            tableheader.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            Chunk msrnoChunk = new Chunk("\nSr.No. " + GlobalValues.selectedpt.getSrno(), normalFont);
            Phrase srno = new Phrase(msrnoChunk);
            tableheader.addCell(srno);

            Chunk dateChunk = new Chunk(d, normalFont);
            Phrase date = new Phrase(dateChunk);
            tableheader.addCell(date);
            document.add(tableheader);
            Font zapfdingbats = new Font();
            zapfdingbats.setSize(20.0f);
            Chunk bullet = new Chunk("\u2022", zapfdingbats);

            if (examination.getChiefcomplaints().size() > 0) {
                Paragraph titleparagraph = new Paragraph();
                titleparagraph.add(new Phrase("" + "Chief Complaints" + " ", headingFont));
                document.add(titleparagraph);
                List list = new List(List.UNORDERED);
                list.setListSymbol(bullet);
                for (Advice item : examination.getChiefcomplaints()) {
                    list.add(new ListItem(" " + item.getName(), zapfdingbats));
                }
                document.add(list);
            }

            if (examination.getSymptoms().size() > 0) {
                Paragraph titleparagraph = new Paragraph();
                titleparagraph.add(new Phrase("\n\n" + "Symptoms" + " ", headingFont));
                document.add(titleparagraph);
                List list = new List(List.UNORDERED);
                list.setListSymbol(bullet);
                for (Advice item : examination.getSymptoms()) {
                    list.add(new ListItem(" " + item.getName(), zapfdingbats));
                }
                document.add(list);
            }


            if (examination.getVaccines().size() > 0) {
                Paragraph titleparagraph = new Paragraph();
                titleparagraph.add(new Phrase("\n\n" + "Vaccines" + " ", headingFont));
                document.add(titleparagraph);
                List list = new List(List.UNORDERED);
                list.setListSymbol(bullet);
                for (Advice item : examination.getVaccines()) {
                    list.add(new ListItem(" " + item.getName(), zapfdingbats));
                }
                document.add(list);
            }


            if (examination.getAllergy().size() > 0) {
                Paragraph titleparagraph = new Paragraph();
                titleparagraph.add(new Phrase("\n\n" + "Allergy" + " ", headingFont));
                document.add(titleparagraph);
                List list = new List(List.UNORDERED);
                list.setListSymbol(bullet);
                for (Advice item : examination.getAllergy()) {
                    list.add(new ListItem(" " + item.getName(), zapfdingbats));

                }
                document.add(list);
            }

            if (examination.getDiagnosis().size() > 0) {
                Paragraph titleparagraph = new Paragraph();
                titleparagraph.add(new Phrase("\n\n" + "Diagnosis" + " ", headingFont));
                document.add(titleparagraph);
                List list = new List(List.UNORDERED);
                list.setListSymbol(bullet);
                for (Advice item : examination.getDiagnosis()) {
                    list.add(new ListItem(" " + item.getName(), zapfdingbats));
                }
                document.add(list);
            }
            document.close();

            if (type == 1) {
                FileUtils.openFile(this, new File(dest));
            } else if (type == 2) {
                sharefileonwhatsapp(new File(dest));
            } else {
                printDocument(new File(dest));
            }

        } catch (IOException | DocumentException ie) {
            LOGE("createPdf: Error " + ie.getLocalizedMessage());
        } catch (ActivityNotFoundException ae) {
            Toast.makeText(this, "No application found to open this file.", Toast.LENGTH_SHORT).show();
        }
    }

    public void createpdfofclinicalnote(DetailPrescriptionData data1) {
        verifyStoragePermissions(this);
        Document doc = new Document();
        doc.setMargins(50.0f, 50.0f, 50.0f, 50.0f);

        try {
            String path = getSecondaryStorage() + "/download";
            Log.e("path ", "path " + path);
            File dir = new File(path);
            if (!dir.exists())
                dir.mkdirs();

            Log.d("PDFCreator", "PDF Path: " + path);
            File file = new File(dir, "drug.pdf");
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream fOut = new FileOutputStream(file);
            PdfWriter.getInstance(doc, fOut);

            doc.open();

            Paragraph name = new Paragraph();
            PdfPTable tableheader1 = new PdfPTable(new float[]{1});
            tableheader1.addCell(getCell("\n Clinical Notes", PdfPCell.ALIGN_CENTER));
            doc.add(tableheader1);
            PdfPTable tableheader = new PdfPTable(new float[]{1, 1});
            tableheader.setWidthPercentage(100);
            Calendar cal = Calendar.getInstance();
            tableheader.addCell(getCell("\nSr.No. " + GlobalValues.selectedpt.getSrno(), PdfPCell.ALIGN_RIGHT));
            tableheader.addCell(getCell("\n" + DateUtils.formatDate(cal.getTime(), "dd/MM/yyyy"), PdfPCell.ALIGN_RIGHT));
            tableheader.addCell(getCell("\nFor Patient: " + GlobalValues.selectedpt.getFirstName(), PdfPCell.ALIGN_LEFT));
            tableheader.addCell(getCell("\n", PdfPCell.ALIGN_RIGHT));
            //tableheader.addCell(getCell("\nFollow Up Date: NA", PdfPCell.ALIGN_RIGHT));
            doc.add(tableheader);
            PdfPTable table = new PdfPTable(new float[]{1, 1, 1, 2});
            table.setWidthPercentage(100.0f);
            table.getDefaultCell().setPadding(10);
            table.setSpacingBefore(20);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell("Date");
            table.addCell("Dr. Name");
            table.addCell("Chif Complaints");
            table.addCell("Notes");


          /*  for (int i = 0; i < data1.size(); i++) {
                table.addCell(data1.get(i).getDate());
                table.addCell(data1.get(i).getDocname());
                String str = "";
                ArrayList<ClinicalNoteHistory> list = data1.get(i).getData();
                for (int j = 0; j < list.size(); j++) {
                    str = str + list.get(j).getName() + ",";
                }

                table.addCell(str.substring(0, str.length() - 1));
                table.addCell(data1.get(i).getRemark());
            }*/

            doc.add(table);
            sendfileonmail(file);
           /* if (wheretogo == 1) {
                sharefileonwhatsapp(file);

            } else if (wheretogo == 3) {
                printDocument(file);
            } else {
                sendfileonmail(file);
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            doc.close();
        }
    }

    public void printDocument(File file) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);
            String jobName = this.getString(R.string.app_name) + " Document";
            final File printfile = file;
            final String filename = "clinicalnote.pdf";

            PrintDocumentAdapter pda = new PrintDocumentAdapter() {

                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onWrite(PageRange[] pages, ParcelFileDescriptor destination, CancellationSignal cancellationSignal, WriteResultCallback callback) {
                    FileInputStream input = null;
                    FileOutputStream output = null;

                    try {

                        input = new FileInputStream(printfile);
                        output = new FileOutputStream(destination.getFileDescriptor());

                        byte[] buf = new byte[1024];
                        int bytesRead;

                        while ((bytesRead = input.read(buf)) > 0) {
                            output.write(buf, 0, bytesRead);
                        }

                        callback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});

                    } catch (FileNotFoundException ee) {
                        Log.e("error ", "error " + ee);
                        //Catch exception
                    } catch (Exception e) {
                        Log.e("error ", "error " + e);
                        //Catch exception
                    } finally {
                        try {
                            input.close();
                            output.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.e("error ", "error " + e);
                        }
                    }
                }

                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes, CancellationSignal cancellationSignal, LayoutResultCallback callback, Bundle extras) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        if (cancellationSignal.isCanceled()) {
                            callback.onLayoutCancelled();
                            return;
                        }
                    }

                    PrintDocumentInfo pdi = new PrintDocumentInfo.Builder(filename).setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT).build();

                    callback.onLayoutFinished(pdi, true);
                }

            };
            printManager.print(jobName, pda, null);
        }
    }

    public void sharefileonwhatsapp(File file) {
        Uri fileuri = FileProvider.getUriForFile(context, "com.healthbank.fileprovider", file);
        Intent shareintent = new Intent();
        shareintent.setAction(Intent.ACTION_SEND);
        shareintent.putExtra(Intent.EXTRA_STREAM, fileuri);
        shareintent.setType("application/pdf");
        shareintent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NO_HISTORY);
        try {
            startActivity(shareintent);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Please kindly install whatsapp", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public PdfPCell getCell(String text, int alignment) {
        try {
            BaseFont urName = BaseFont.createFont("assets/fonts/brandon_medium.otf", "UTF-8", BaseFont.EMBEDDED);
            Font mOrderDetailsTitleFont = new Font(urName, 20.0f, Font.BOLD, BaseColor.BLACK);
            PdfPCell cell = new PdfPCell(new Phrase(text, mOrderDetailsTitleFont));
            cell.setHorizontalAlignment(alignment);
            cell.setBorder(PdfPCell.NO_BORDER);
            return cell;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void sendfileonmail(File file) {
        // Uri fileuri = Uri.fromFile(file);
        Uri fileuri = FileProvider.getUriForFile(context, "com.healthbank.fileprovider", file);
        Intent sendintent = new Intent(Intent.ACTION_VIEW);
        sendintent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
        sendintent.putExtra(Intent.EXTRA_STREAM, fileuri);
        sendintent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NO_HISTORY);
        sendintent.putExtra(Intent.EXTRA_SUBJECT, "Visit Data");
        try {
            startActivity(sendintent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Pease check your google account", Toast.LENGTH_LONG).show();
        }
    }

    public void gotopartialpayment(BillingClass billing) {
        Intent intent = new Intent(this, PartialPaymentActivity.class);
        intent.putExtra("data", billing);
        startActivity(intent);
    }

    public void reporterror(String classname, String error) {
        try {
            JSONObject obj = new JSONObject();
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            obj.put("email", preferences.getString("email", "news"));
            obj.put("osversion", getmodel() + " " + getos());
            obj.put("errordetail", error.replaceAll("'", ""));
            obj.put("appname", Constants.APP_NAME);
            obj.put("activityname", classname);
            //  Log.e("error ", "error " + error.toString() + " " + obj.toString());
            // ConnectionManager.getInstance(this).reporterror(obj.toString());
        } catch (Exception e) {
            Log.e("error", "error " + e);
            e.printStackTrace();
        }
    }

    public int getos() {
        return Build.VERSION.SDK_INT;
    }

    public String getmodel() {
        return Build.MODEL + " " + Build.BRAND;
    }

    public void showMsg(String text) {
        try {
            Snackbar.make(
                    getWindow().getDecorView().findViewById(android.R.id.content),
                    text, Snackbar.LENGTH_LONG).show();
        } catch (Exception e) {
            reporterror("ActivityCommon", e.toString());
        }
    }

    public String toHtm(String d1, String d2, String d3) {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("<strong><font color=\"#4B5258\">").append(d1).append("</strong>");
            sb.append("<br>");
            sb.append("<small>").append(d3).append("</small>");
        } catch (Exception e) {
            e.printStackTrace();
            reporterror("ActivityCommon toHtm", e.toString());
        }
        return sb.toString();
    }

    /*
       public void gotoNewsDetail(DBClassNews branch) {
           try {
               Type type = new TypeToken<DBClassNews>() {
               }.getType();
               String str = new Gson().toJson(branch, type);
               Intent intent = new Intent(this, ActivityNewsDetail.class);
               intent.putExtra(ActivityNewsDetail.KEY_JSON, str);
               startActivity(intent);
           } catch (Exception e) {
               e.printStackTrace();
               reporterror(tag,e.toString() );
           }
       }

       public void gotoBlogDetail(Blog branch) {
           try {
               Type type = new TypeToken<Blog>() {
               }.getType();
               String str = new Gson().toJson(branch, type);
               Intent intent = new Intent(this, BlogDetailsActivity.class);
               intent.putExtra(BlogDetailsActivity.KEY_JSON, str);
               startActivity(intent);
           } catch (Exception e) {
               e.printStackTrace();
               reporterror(tag,e.toString() );
           }
       }

       public void doShare(String txt) {
           try {
               Intent intent = new Intent(Intent.ACTION_SEND);
               intent.setType("text/plain");
               intent.putExtra(Intent.EXTRA_TEXT, txt);
               startActivity(intent);
           } catch (Exception e) {
               Log.e("MainActivityCommon", e.toString());
               reporterror(tag,e.toString() );
           }
       }

       public void gotoYoutube(String videoid) {
           try {
               Intent intent = new Intent(Intent.ACTION_VIEW,
                       Uri.parse("http://www.youtube.com/watch?v=" + videoid));
               ((context)).startActivity(intent);
           } catch (Exception e) {
               e.printStackTrace();
               reporterror(tag,e.toString() );
           }
       }

       public void sharelinkusingintent(String text) {
           try {
               Intent intent = new Intent(Intent.ACTION_SEND);
               intent.setType("text/plain");
               intent.putExtra(Intent.EXTRA_TEXT, text);
               startActivity(Intent.createChooser(intent, "Share Link!"));
           } catch (Exception e) {
               e.printStackTrace();
               reporterror(tag,e.toString() );
           }
       }
       protected boolean validateEmail(String email) {
           if (email.length() > 0) {
               return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
           } else {
               return false;
           }
       }
       protected boolean validateMobile(String phone) {
           if (phone.length() > 0) {
               return android.util.Patterns.PHONE.matcher(phone).matches();
           } else {
               return false;
           }
       }
   */
    protected void hideKeypad() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void addheader(Document document, String text) {
        try {
            Chunk headerchunk = new Chunk(text, TitleFont);
            Paragraph TitleParagraph = new Paragraph(headerchunk);
            TitleParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(TitleParagraph);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addquestiontext(Document document, String text) {
        try {
            Chunk headerchunk = new Chunk(text, bluenormalFont);
            Paragraph TitleParagraph = new Paragraph(headerchunk);

            TitleParagraph.setAlignment(Element.ALIGN_LEFT);
            document.add(TitleParagraph);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addanswertext(Document document, String text) {
        try {
            Chunk headerchunk = new Chunk(text, normalFont);
            Paragraph TitleParagraph = new Paragraph(headerchunk);
            TitleParagraph.setAlignment(Element.ALIGN_LEFT);
            TitleParagraph.setIndentationLeft(20);
            document.add(TitleParagraph);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void adddrug(Document document, ArrayList<Drug> drugdata) {
        try {
        /*    Chunk mdrugChunk = new Chunk("Rx:", headingFont);
            Paragraph mdrugParagraph = new Paragraph(mdrugChunk);
            document.add(mdrugParagraph);*/

            PdfPTable table = new PdfPTable(new float[]{2, 1, 1, 2});
            table.setWidthPercentage(100.0f);
            table.getDefaultCell().setPadding(10);
            table.setSpacingBefore(20);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
            Chunk mdrugheaderChunk = new Chunk("Drug", headingFont);
            Phrase p1 = new Phrase(mdrugheaderChunk);
            table.addCell(p1);

            Chunk mheader3Chunk = new Chunk("Doses", headingFont);
            Phrase p3 = new Phrase(mheader3Chunk);
            table.addCell(p3);

            Chunk mheader4Chunk = new Chunk("Days", headingFont);
            Phrase p4 = new Phrase(mheader4Chunk);
            table.addCell(p4);
            Chunk mheader2Chunk = new Chunk("Instructions", headingFont);
            Phrase p2 = new Phrase(mheader2Chunk);
            table.addCell(p2);

            for (int i = 0; i < drugdata.size(); i++) {
                Drug drug = drugdata.get(i);
                table.addCell(drug.getDrugName());
                table.addCell(drug.getDoses());
                table.addCell(drug.getDuration());
                table.addCell(drug.getInstruction());
            }

            document.add(table);
            document.add(new Paragraph(""));
            document.add(new Chunk(lineSeparator));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addMaster(Document document, ArrayList<Master> master) {
        try {
        /*    Chunk mdrugChunk = new Chunk("Rx:", headingFont);
            Paragraph mdrugParagraph = new Paragraph(mdrugChunk);
            document.add(mdrugParagraph);*/

            PdfPTable table = new PdfPTable(new float[]{1});
            table.setWidthPercentage(100.0f);
            table.getDefaultCell().setPadding(10);
            table.setSpacingBefore(20);
        /*    table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
            Chunk mdrugheaderChunk = new Chunk("Drug", headingFont);
            Phrase p1 = new Phrase(mdrugheaderChunk);
            table.addCell(p1);

            Chunk mheader3Chunk = new Chunk("Doses", headingFont);
            Phrase p3 = new Phrase(mheader3Chunk);
            table.addCell(p3);

            Chunk mheader4Chunk = new Chunk("Days", headingFont);
            Phrase p4 = new Phrase(mheader4Chunk);
            table.addCell(p4);
            Chunk mheader2Chunk = new Chunk("Instructions", headingFont);
            Phrase p2 = new Phrase(mheader2Chunk);
            table.addCell(p2);*/

            for (int i = 0; i < master.size(); i++) {
                table.addCell(master.get(i).getName());
            }

            document.add(table);
            document.add(new Paragraph(""));
            document.add(new Chunk(lineSeparator));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addquestions(Document document, ArrayList<Questions> questions, JSONArray array) {
        try {
            ArrayList<SaveAnswerdata> data = new ArrayList<>();
            for (int j = 0; j < array.length(); j++) {
                try {
                    data.add(new SaveAnswerdata(array.getJSONObject(j)));
                    Log.e("data1", "data1" + array.getJSONObject(j).toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            for (int j = 0; j < questions.size(); j++) {
                int isprint = DatabaseHelper.getInstance(this).isprint(Integer.toString(questions.get(j).getQid()), Integer.toString(questions.get(j).getTemplateId()));
                Log.e("isprint reply", "" + isprint + " " + questions.get(j).getName());
                if (isprint == 1) {
                    boolean isqadded = false;
                    String ans = SaveAnswerdata.getAnswer(data, Integer.toString(questions.get(j).getQid()));
                    ArrayList<OptionAnswer> optans = new ArrayList<>();
                    if (ans != null)
                        if (!ans.equalsIgnoreCase("null") || !ans.equalsIgnoreCase("")) {

                            String[] optanswers = ans.split("~");
                            for (int k = 0; k < optanswers.length; k++) {
                                try {
                                    Log.e("id11 ", "data " + optanswers[k]);
                                    String[] suboptans = optanswers[k].split("\\^");
                                    if (suboptans.length > 1) {
                                        if (!isqadded) {
                                            isqadded = true;
                                            addquestiontext(document, (j + 1) + "." + questions.get(j).getName());
                                        }
                                        addanswertext(document, suboptans[1]);
                                        OptionAnswer opt = new OptionAnswer(suboptans[0], suboptans[1]);
                                        optans.add(opt);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    ArrayList<Option> optdata = questions.get(j).getOption();
                    for (int k = 0; k < optdata.size(); k++) {
                        try {
                            for (int k1 = 0; k1 < data.size(); k1++) {
                                if (data.get(k1).getAns() != null)
                                    if (data.get(k1).getAns().equalsIgnoreCase("PrescriptionControl")) {
                                        Log.e("in PrescriptionControl ", "in PrescriptionControl " + data.get(k1).getOptionType());
                                        JSONObject obj = data.get(k1).getXmlObj();
                                        if (obj != null) {
                                            Log.e("obj nt null", "info" + obj.toString());
                                            if (obj.has("row")) {
                                                Log.e("obj has row ", "info");
                                                JSONObject rowobj = obj.optJSONObject("row");
                                                if (rowobj != null) {
                                                    Log.e("rowobj nt null", "info " + optdata.get(k).getOptionType());
                                                    if (optdata.get(k).getOptionType().equalsIgnoreCase("Medications") && data.get(k1).getOptionType().equalsIgnoreCase("Medications")) {
                                                        ArrayList<Drug> drugdata = new ArrayList<>();
                                                        Log.e("in Medication ", "in Medication");
                                                        Gson gson1 = new Gson();
                                                        Type type1 = new TypeToken<Drug>() {
                                                        }.getType();
                                                        Drug d = gson1.fromJson(rowobj.toString(), type1);
                                                        drugdata.add(d);
                                                        if (drugdata.size() > 0) {
                                                            addquestiontext(document, (j + 1) + "." + questions.get(j).getName());
                                                            adddrug(document, drugdata);
                                                        }

                                                    } else if (optdata.get(k).getOptionType().equalsIgnoreCase("Diagnosis") && data.get(k1).getOptionType().equalsIgnoreCase("Diagnosis")) {
                                                        Log.e("in Diagnosis ", "in Diagnosis");
                                                        ArrayList<Master> diagnosis = new ArrayList<>();
                                                        try {
                                                            Master master = new Master();
                                                            master.setCategoryid(rowobj.getString("DiagnosisId"));
                                                            master.setName(rowobj.getString("DiagnosisName"));
                                                            diagnosis.add(master);
                                                            if (diagnosis.size() > 0) {
                                                                addquestiontext(document, (j + 1) + "." + questions.get(j).getName());
                                                                addMaster(document, diagnosis);
                                                            }
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                    } else if (optdata.get(k).getOptionType().equalsIgnoreCase("Test") && data.get(k1).getOptionType().equalsIgnoreCase("Test")) {
                                                        Log.e("in Diagnosis ", "in Diagnosis");
                                                        ArrayList<Master> test = new ArrayList<>();
                                                        try {
                                                            Master master = new Master();
                                                            master.setName(rowobj.getString("TestName"));
                                                            master.setTestId(rowobj.getString("TestId"));
                                                            master.setLabId(rowobj.getString("LabId"));
                                                            test.add(master);
                                                            if (test.size() > 0) {
                                                                addquestiontext(document, (j + 1) + "." + questions.get(j).getName());
                                                                addMaster(document, test);
                                                            }
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                    }

                                                } else {
                                                    JSONArray rowarray = obj.optJSONArray("row");
                                                    if (optdata.get(k).getOptionType().equalsIgnoreCase("Medications") && data.get(k1).getOptionType().equalsIgnoreCase("Medications")) {
                                                        ArrayList<Drug> drugdata = new ArrayList<>();
                                                        Log.e("in Medication1 ", "in Medication1");
                                                        Gson gson1 = new Gson();
                                                        Type type1 = new TypeToken<ArrayList<Drug>>() {
                                                        }.getType();
                                                        drugdata = gson1.fromJson(rowarray.toString(), type1);
                                                        if (drugdata.size() > 0) {
                                                            addquestiontext(document, (j + 1) + "." + questions.get(j).getName());
                                                            adddrug(document, drugdata);
                                                        }

                                                    } else if (optdata.get(k).getOptionType().equalsIgnoreCase("Diagnosis") && data.get(k1).getOptionType().equalsIgnoreCase("Diagnosis")) {
                                                        Log.e("in Diagnosis ", "in Diagnosis");
                                                        ArrayList<Master> diagnosis = new ArrayList<>();
                                                        try {
                                                            for (int l = 0; l < rowarray.length(); l++) {
                                                                Master master = new Master();
                                                                master.setCategoryid(rowarray.getJSONObject(l).getString("DiagnosisId"));
                                                                master.setName(rowarray.getJSONObject(l).getString("DiagnosisName"));
                                                                diagnosis.add(master);
                                                            }
                                                            if (diagnosis.size() > 0) {
                                                                addquestiontext(document, (j + 1) + "." + questions.get(j).getName());
                                                                addMaster(document, diagnosis);
                                                            }
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                    } else if (optdata.get(k).getOptionType().equalsIgnoreCase("Test") && data.get(k1).getOptionType().equalsIgnoreCase("Test")) {
                                                        Log.e("in Test ", "in Test");
                                                        ArrayList<Master> test = new ArrayList<>();
                                                        try {
                                                            for (int l = 0; l < rowarray.length(); l++) {
                                                                Master master = new Master();
                                                                master.setName(rowarray.getJSONObject(l).getString("TestName"));
                                                                master.setTestId(rowarray.getJSONObject(l).getString("TestId"));
                                                                master.setLabId(rowarray.getJSONObject(l).getString("LabId"));
                                                                test.add(master);
                                                            }
                                                            if (test.size() > 0) {
                                                                addquestiontext(document, (j + 1) + "." + questions.get(j).getName());
                                                                addMaster(document, test);
                                                            }
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }
                                            } else {
                                                Log.e("obj not row ", "info");
                                            }
                                        }
                                    } else {
                                        Log.e("obj  null", "info");
                                    }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createvisitdataPdf(String visitdate, int type, JSONObject answerobjdata) {

        String dest = FileUtils.getAppPath(this) + "Prescription " + visitdate + ".pdf";
        File f = new File(dest);
        if (f.exists()) {
            f.delete();
        }
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(dest));
            document.open();

            document.setPageSize(PageSize.A4);
            document.addCreationDate();

            Chunk TitleChunk = new Chunk(GlobalValues.selectedpt.getFirstName().toUpperCase(), TitleFont);
            Paragraph TitleParagraph = new Paragraph(TitleChunk);
            TitleParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(TitleParagraph);
            PdfPTable tableheader = new PdfPTable(new float[]{1});
            tableheader.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            tableheader.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            Chunk msrnoChunk = new Chunk("\nSr.No. " + GlobalValues.selectedpt.getSrno(), normalFont);
            Phrase srno = new Phrase(msrnoChunk);
            tableheader.addCell(srno);

            Chunk dateChunk = new Chunk(visitdate, normalFont);
            Phrase date = new Phrase(dateChunk);
            tableheader.addCell(date);
            document.add(tableheader);

            try {
                JSONObject answerobj = answerobjdata.optJSONObject("AnswerData");
                if (answerobj != null) {
                    Group group = new Group(answerobj);
                    if (!group.getGroupName().equalsIgnoreCase("null"))
                        addheader(document, group.getGroupName());
                    ArrayList<SubGroups> subGroups = group.getSubGroups();
                    for (int i = 0; i < subGroups.size(); i++) {
                        if (!subGroups.get(i).getGroupName().equalsIgnoreCase("null"))
                            addheader(document, subGroups.get(i).getGroupName());
                        ArrayList<Questions> questions = subGroups.get(i).getQuestions();
                        JSONArray array = new JSONArray();
                        array = answerobjdata.optJSONArray("QuestionData");
                        addquestions(document, questions, array);
                    }
                } else {
                    JSONArray array = answerobjdata.optJSONArray("AnswerData");
                    Log.e("array111111 ", "array " + array.toString());
                    if (array != null) {
                        for (int m = 0; m < array.length(); m++) {
                            answerobj = array.getJSONObject(m);
                            Group group = new Group(answerobj);
                            if (!group.getGroupName().equalsIgnoreCase("null"))
                                addheader(document, group.getGroupName());
                            ArrayList<SubGroups> subGroups = group.getSubGroups();
                            for (int i = 0; i < subGroups.size(); i++) {
                                if (!subGroups.get(i).getGroupName().equalsIgnoreCase("null"))
                                    addheader(document, subGroups.get(i).getGroupName());
                                ArrayList<Questions> questions = subGroups.get(i).getQuestions();
                                JSONArray array1 = new JSONArray();
                                array1 = answerobjdata.optJSONArray("QuestionData");
                                addquestions(document, questions, array1);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
         /*   if (prescription.getMdataset().size() > 0) {
                Chunk mdrugChunk = new Chunk("Rx:", headingFont);
                Paragraph mdrugParagraph = new Paragraph(mdrugChunk);
                document.add(mdrugParagraph);

                PdfPTable table = new PdfPTable(new float[]{2, 1, 1, 2});
                table.setWidthPercentage(100.0f);
                table.getDefaultCell().setPadding(10);
                table.setSpacingBefore(20);
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                Chunk mdrugheaderChunk = new Chunk("Drug", headingFont);
                Phrase p1 = new Phrase(mdrugheaderChunk);
                table.addCell(p1);

                Chunk mheader3Chunk = new Chunk("Doses", headingFont);
                Phrase p3 = new Phrase(mheader3Chunk);
                table.addCell(p3);

                Chunk mheader4Chunk = new Chunk("Days", headingFont);
                Phrase p4 = new Phrase(mheader4Chunk);
                table.addCell(p4);
                Chunk mheader2Chunk = new Chunk("Instructions", headingFont);
                Phrase p2 = new Phrase(mheader2Chunk);
                table.addCell(p2);

                for (int i = 0; i < prescription.getMdataset().size(); i++) {
                    Prescriptiondata p = prescription.getMdataset().get(i);
                    table.addCell(p.getDrugName() + " (" + p.getDrugUnit() + ")\n" + p.getBrand() + "\n");
                    table.addCell(p.getDoses());
                    table.addCell(p.getDuration());
                    table.addCell(p.getInstruction());
                }

                document.add(table);
                document.add(new Paragraph(""));
                document.add(new Chunk(lineSeparator));

            }*/
            document.close();

            if (type == 1) {
                sendfileonmail(new File(dest));
                // FileUtils.openFile(this, new File(dest));
            } else if (type == 2) {
                sharefileonwhatsapp(new File(dest));
            } else if (type == 3) {
                printDocument(new File(dest));
            }
        } catch (IOException | DocumentException ie) {
            LOGE("createPdf: Error " + ie.getLocalizedMessage());
        } catch (ActivityNotFoundException ae) {
            Toast.makeText(this, "No application found to open this file.", Toast.LENGTH_SHORT).show();
        }
    }

    public void sendnotification() {

    }

    public void gotogroupvideocall(String channel) {
        Log.e("channel ", "channel " + channel);
        AGApplication.mVideoSettings.mChannelName = channel;
        String encryption = "AES-128-XTS";
        AGApplication.mVideoSettings.mEncryptionKey = encryption;

        Intent i = new Intent(this, ChatActivity.class);
        i.putExtra(ConstantApp.ACTION_KEY_CHANNEL_NAME, channel);
        i.putExtra(ConstantApp.ACTION_KEY_ENCRYPTION_KEY, encryption);
        i.putExtra(ConstantApp.ACTION_KEY_ENCRYPTION_MODE, getResources().getStringArray(R.array.encryption_mode_values)[AGApplication.mVideoSettings.mEncryptionModeIndex]);
        startActivity(i);
    }

    private class DownloadFile extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];   // -> http://hmis.apache.org/hmis-1.x/hmis.pdf
            //  String fileName = strings[1];  // -> hmis.pdf
            try {
                int index = fileUrl.lastIndexOf(".");
                String ext = fileUrl.substring((index + 1));
                String extStorageDirectory = Environment.getExternalStorageDirectory().toString();

                File folder = new File(extStorageDirectory, "HMIS");
                if (!folder.exists())
                    folder.mkdir();

                File pdfFile = new File(folder, "HMIS." + ext);

                try {
                    pdfFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                downloadFile(fileUrl, pdfFile, ext);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
