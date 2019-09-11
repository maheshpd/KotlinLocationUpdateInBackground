package com.healthbank;

import com.healthbank.classes.Advice;
import com.healthbank.classes.Advicemasterdata;
import com.healthbank.classes.Doctor;
import com.healthbank.classes.Drug;
import com.healthbank.classes.Patient;
import com.healthbank.classes.ProcedureData;
import com.healthbank.classes.Reading;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class GlobalValues {
    public static LinkedHashMap<String, ArrayList<Reading>> mdataset = new LinkedHashMap<>();
    public static String RUNNING_IMAGE_PATH = "";
    public static ArrayList<ProcedureData> treatmentdata = new ArrayList<>();
    public static ArrayList<Drug> drugdata=new ArrayList<>();
   /* public static ArrayList<Master> test=new ArrayList<>();
    public static ArrayList<Master> diagnosis=new ArrayList<>();*/
    public static int practiceid = 0;
    public static int docid = 0;
    public static int assistantid = 0;
    public static String department = "IT";
    public static Doctor d ;
    public static String DrName = "";
    public static Patient selectedpt;
    public static ArrayList<Advice> advicedata=new ArrayList<>();
    public static ArrayList<Advicemasterdata> adviceselectedmasterdata=new ArrayList<>();
    public static String TEMP_STR = "";
    public static String name = "";
  //  public static Department d;
    public static String billingname = "";
    public static int branchid = 103;
    public static int custid = 0;
    //public static ArrayList<VitalHeader> vitalsdata = new ArrayList<>();
    public static int height = 0;
    public static int width = 0;
    public static int id = 0;
    public static Doctor selectedoctor;
    public static boolean isappopen=false;
    public static ArrayList<Doctor> doctordata=new ArrayList<>();
  //  public static PatientProfile profile;
}
