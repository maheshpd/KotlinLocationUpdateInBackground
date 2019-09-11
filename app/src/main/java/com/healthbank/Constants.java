package com.healthbank;

import android.graphics.Bitmap;

import com.squareup.okhttp.MediaType;

public class Constants {
    public final static String BROADCAST_WIZARD = "healthbank_broadcast_wizard";
    public final static String BROADCAST_RESPONSE_CODE = "healthbank_broadcast_response_code";
    public final static String BROADCAST_URL_ACCESS = "healthbank_broadcast_url_access";
    public final static String BROADCAST_DATA = "healthbank_broadcast_broadcastdata";
    public static final int STATUS_OK = 200;
    public static final int NO_INTERNET = 1000;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public final static String PM = "pm";
    public final static String AM = "am";

    public final static int DEFAULT_ANIMATION = 400;
    public final static String BROADCAST_DATA1 = "ida_org_in_broadcast_broadcastdata1";
    public final static String APP_NAME = "INSURANCE ALERTSS";
    public final static String NOTIFICATION_TOKEN = "ida_news_app";
    //public final static String NOTIFICATION_TOKEN = "ida_news_app1";
    public static final String NO_INTERNET_MSG = "Please check the internet connention";
    public static final String PACK_NAME = "com_ida_main_app";
    public static final int REQUEST_CAMERA = 45;
    public static final int REQUEST_CROP = 46;
    public static final int REQUEST_GALLARY = 41;
    public static final String FILE_NAME_THUMB = "_thumb_chat.jpeg";
    public static final String FILE_NAME_EXT = ".jpeg";
    public static final String FILE_NAME_CURRENT = "current_chat.jpeg";
    public static final String FILE_NAME_CURRENT_CROP = "current_crop_chat.jpeg";
    public static final String FILE_NAME_CURRENT_THUMB = "current_thumb_chat.jpeg";
    public static final String FILE_TEMP_NAME = "temp_img_chat_";
    public static final Bitmap.CompressFormat FILE_FORMAT = Bitmap.CompressFormat.JPEG;
    public static final int FILE_FORMAT_QUALITY = 100;
    public static final int FILE_FORMAT_QUALITY_THUMB = 100;
    public static final String CHIEFCOMPLAINT = "Chief Complaint";
    public static final String SYMPTOMS = "Symptoms";
    public static final String ADVICE = "Advice";
    public static final String ALLERGY = "Allergy";
    public static final String DIAGNOSIS = "Diagnosis";
    public static final String VACCINES = "Vaccines";
    public static final String LAB = "Lab";
    public static final String offlinedatasavemsg="Data saved offline";
    public static final String projectid = "1";
    public static final String Drug = "Drug";
    public static final String Diagnosis = "Diagnosis";
    public static final String title = "title";
    public static final String purpose = "purpose";
    public static final String Unit = "Unit";
    public static final String Doses = "Doses";
    public static final String Duration = "Duration";
    public static final String Instruction = "Instruction";
    public static final String BodyPart = "Body Part";
    public static final String LifeStyle = "Life Style";
    public static final String Packages = "Packages";
    public static final String Lab = "Lab";
    public static final String Test = "Test";
    public static final String treatmentprocedures = "treatment procedures";
    public static final String channel="HMIS";
    public static String apikey = "AAAAdKIocEI:APA91bG8nKeatMzZWaCP_9JVrMBueOgNieTqihiK8ooNqPQfvl-0od6M-TTVDR4sKcU9HO887UToH-JEV9Qjx_s9D53pO43p0qItG3LsGjqvqjsAFBZ5PabByjvjO5WiiToMGUy0yQ5r";
    public static int MAXTABCOUNT = 4;
}
