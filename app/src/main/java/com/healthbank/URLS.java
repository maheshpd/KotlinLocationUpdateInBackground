package com.healthbank;
import android.util.Log;

public class URLS {
       //emrservice
        //emrserviceswiggy
 //  public static final String BASE_URL = "http://192.168.1.59/emrserviceswiggy/data.svc/";
    public static final String BASE_URL = "http://192.168.1.59/emrservice/data.svc/";
 //  public static final String BASE_URL = "http://services.techama.in/data.svc/";

    // public static final String BASE_URL = "http://theclinicservices.techama.in/data.svc/";
    public static String GET_BRANCHINFO(int branchid) {
        return BASE_URL + "getbranchinfo/" + branchid+"/"+Constants.projectid;
    }

    public static String GET_DEPARTMENT(int branchid, String flag) {
        return BASE_URL + "getdepartment/" + branchid + "/" + flag+"/"+Constants.projectid;
    }

    public static String GET_HEALTHRECORDS(String patientid, String visits) {
        return BASE_URL + "getnotedataopth/" + patientid + "/0/" + visits+"/"+Constants.projectid;
    }

    public static String GET_PATIENTLABRESULT(String patid) {
        return BASE_URL + "getpatientlabresult/" + patid+"/"+Constants.projectid;
    }

    public static String GET_PAYMENTHISTORY(int flag, String patid, String billid) {
        return BASE_URL + "patientbill/" + flag + "/" + patid+"/"+billid+"/"+Constants.projectid;
    }

    public static String GET_VITALS(String patid, int branchid) {
        return BASE_URL + "getvitals/" + patid + "/" + branchid+"/"+Constants.projectid;
    }

   /* public static String GET_PRESCRIPTION(String patid, String visits) {
        return BASE_URL + "getrx/" + patid + "/" + visits+"/"+Constants.projectid;
    }
*/
    public static String GET_VISITS(String patid) {
        return BASE_URL + "getvisit/" + patid+"/"+Constants.projectid;
    }

    public static String Login() {
        return BASE_URL + "doctorlogin";
    }

    public static String searchpatient(String searchkey,String from,String to,String userid,String deptid,String locid) {
        Log.e("url","url "+BASE_URL + "GetSearchpatient/"+"all/"+"all/"+from+"/"+to+"/"+Constants.projectid+"/"+userid+"/"+deptid+"/"+locid+"?searchstr="+searchkey);
       // return BASE_URL + "GetSearchpatient/"+"all/"+"all/"+from+"/"+to+"/"+Constants.projectid+"?searchstr="+searchkey;
        return BASE_URL + "GetSearchpatient/"+"all/"+"all/"+from+"/"+to+"/"+Constants.projectid+"/"+userid+"/"+deptid+"/"+locid+"?searchstr="+searchkey;
    }

    public static String Bookappointment() {
        return BASE_URL + "SaveSchedule";
    }

    public static String getmedia(int branchid) {
        return BASE_URL + "getMediaData/" + branchid+"/"+Constants.projectid;
    }

    public static String Getappointmenthistory(String ptid, String identifire) {
        return BASE_URL + "Getpatientapphisoty/" + ptid+"/"+identifire+"/"+Constants.projectid;
    }

    public static String GET_Master(String master, String search) {
        return BASE_URL + "GetSearchEmrMaster/" + master + "/" + search+"/"+Constants.projectid;
    }

    public static String Get_Remaning_Master_data() {
        return BASE_URL + "GetCustomMasters";
    }

    public static String SaveRx() {
        return BASE_URL + "saverx";
    }

    public static String getQuestionTemplates(){
        return  BASE_URL+"getQuestionTemplates";
    }

    public static String GetTemplateQuestionAnswer(){
        return  BASE_URL+"GetTemplateQuestionAnswer";
    }
    public static String AddVisit(){
        return  BASE_URL+"SaveVisit_Android";
    }
    public static String SaveTemplateQuestionAnswer(){
        return BASE_URL+"savedynamicdata";
    }

    public static String GET_PATHALOGY(String branchid,String patid,String flag) {
        return BASE_URL + "GetInvestigation/" +branchid+"/"+ patid+"/"+flag+"/"+Constants.projectid;
    }

    public static String SavePathology(){
        return BASE_URL+"UploadInvestigation";
    }
    public static String GET_Investigationmaster(String branchid,String type) {
        return BASE_URL + "SearchTest/" +branchid+"/"+ type+"/"+Constants.projectid;
    }
    public static String Save_Patient() {
        return BASE_URL + "SavePatient" ;
    }

    public static String Save_drugmaster() {
        return BASE_URL + "SaveDrug" ;
    }

    public static String gethistoryurl(String ptid){
        return "http://theclinic.techama.in/partial/AllQuestionaryPrint.html?PaitentId="+ptid;
    }

    public static String GET_PRESCRIPTION(String patid, String visits, int flag) {// 1 for latest
        return BASE_URL + "getrx/" + patid + "/" + visits + "/" + flag+"/"+Constants.projectid;
    }


    public static String Save_patient() {
        return BASE_URL + "SavePatient_Ophthalmology" ;
    }

    public static String GetMenubyspeciality(String speciality) {
        return BASE_URL + "GetEmrMenuBySpeciality/"+speciality+"/"+ Constants.projectid;
    }
}
