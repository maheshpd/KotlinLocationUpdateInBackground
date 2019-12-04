package com.healthbank.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.healthbank.DateUtils;
import com.healthbank.classes.Advicemasterdata;
import com.healthbank.classes.Appointmentdatav1;
import com.healthbank.classes.Drug;
import com.healthbank.classes.LabMasterData;
import com.healthbank.classes.Master;
import com.healthbank.classes.MasterData;
import com.healthbank.classes.MasterDataClinicalExamination;
import com.healthbank.classes.Patient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "com.healthbank.classes";
    public static final String TABLE_MSTEXAMINATION = "mst_examination";
    public static final String TABLE_EYESPECIALISTDATA = "tblspecialist";
    public static final String CATEGORYID = "Categoryid";
    public static final String NAME = "Name";
    public static final String SUBCATEGORYID = "SubCategoryid";
    public static final String SORTORDER = "sortorder";
    public static final String DOCTORID = "DoctorId";
    public static final String AMOUNT = "Amount";
    public static final String CODE = "Code";
    public static final String PRACTICEID = "PracticeId";
    public static final String PARENTID = "parent_id";
    public static final String TYPE = "type";
    public static final String ABBR = "abbr";
    public static final String ID = "id";
    public static final String VISITID = "visitid";
    public static final String visit_no = "visit_no";
    public static final String TEMPLATEID = "templateid";
    public static final String TemplateId = "TemplateId";
    public static final String Qid = "Qid";
    public static final String Category = "Category";
    public static final String TemplateName = "TemplateName";
    public static final String Speciality = "Speciality";
    public static final String VISIT = "visit";
    public static final String VISITDATE = "visitdate";
    public static final String DRNAME = "drname";
    public static final String JSONDATA = "jsondata";
    public static final String DELETED = "deleted";
    public static final String SAVEDTIME = "savetime";
    public static final String CATID = "catid";
    public static final String PATIENT = "tbl_patient";
    public static final String Patientid = "Patientid";
    public static final String DBPatientid = "DBPatientid";
    public static final String DBVISITID = "DBvivisitid";
    public static final String ServerPatientid = "ServerPatientid";
    public static final String FName = "FName";
    public static final String FirstName = "FirstName";
    public static final String MiddleName = "MiddleName";
    public static final String LastName = "LastName";
    public static final String Address = "Address1";
    public static final String address = "Address";
    public static final String CountryId = "CountryId";
    public static final String StateId = "StateId";
    public static final String CityId = "CityId";
    public static final String Area = "Area";
    public static final String DOB = "DOB";
    public static final String Gender = "Gender";
    public static final String Age = "Age";
    public static final String PinCode = "PinCode";
    public static final String Title = "Title";
    public static final String Mobile = "Mobile";
    public static final String EmailId = "EmailId";
    public static final String AlternateEmailId = "AlternateEmailId";
    public static final String BloodGroup = "BloodGroup";
    public static final String Customerid = "Customerid";
    public static final String create_date = "create_date";
    public static final String update_date = "update_date";
    public static final String deleted = "deleted";
    public static final String Srno = "srno";
    public static final String providerid = "providerid";
    public static final String Assistantid = "Assistantid";
    public static final String password = "password";
    public static final String IsActive = "IsActive";
    public static final String Department = "Department";
    public static final String Language = "Language";
    public static final String Religion = "Religion";
    public static final String BillingIn = "BillingIn";
    public static final String Phone = "Phone";
    public static final String Occupation = "Occupation";
    public static final String EmployeeNo = "EmployeeNo";
    public static final String EmployeeName = "EmployeeName";
    public static final String Remark = "Remark";
    public static final String Tags = "Tags";
    public static final String ReferFrom = "ReferFrom";
    public static final String ReferTo = "ReferTo";
    public static final String AlternateMobile = "AlternateMobile";
    public static final String profileimage = "profileimage";
    public static final String Height = "Height";
    public static final String aadharno = "aadharno";
    public static final String pattype = "pattype";
    public static final String Maritalstatus = "Maritalstatus";
    public static final String WeddingDate = "WeddingDate";
    public static final String DateOfjoining = "DateOfjoining";
    public static final String panno = "panno";
    public static final String AgeYear = "AgeYear";
    public static final String AgeMonth = "AgeMonth";
    public static final String appointment = "tbl_appointment";
    public static final String identifier = "identifier";
    public static final String isAllDay = "isAllDay";
    public static final String start = "start";
    public static final String end = "end";
    public static final String calendar = "calendar";
    public static final String tag = "tag";
    public static final String title = "title";
    public static final String description = "description";
    public static final String status = "status";
    public static final String catagory = "catagory";
    public static final String name = "name";
    public static final String DoctorId = "DoctorId";
    public static final String RoomId = "RoomId";
    public static final String OperatoryId = "OperatoryId";
    public static final String ipdid = "ipdid";
    public static final String purpose = "purpose";
    public static final String type = "type";
    public static final String CancelRemark = "CancelRemark";
    public static final String Cancelreason = "Cancelreason";
    public static final String DepartmentId = "DepartmentId";
    public static final String PracticeId = "PracticeId";
    public static final String UserId = "UserId";
    public static final String generalid = "generalid";
    public static final String Canceldate = "Canceldate";
    public static final String starttime = "starttime";
    public static final String aptdate = "aptdate";
    public static final String prescription = "tbl_prescription";
    public static final String lab = "tbl_lab";
    public static final String Radiology = "tbl_radiology";
    public static final String vitals = "tbl_vitals";
    public static final String rx = "tbl_rx";
    public static final String Advice = "tbl_advice";
    public static final String Examination = "tbl_examination";
    public static final String Date = "date";
    public static final String Code = "code";
    public static final String Type = "type";
    public static final String Description = "description";
    public static final String Imagepath = "imagepath";
    public static final String ClinicalExamination = "mst_clinicalexamination";
    public static final String AdviceMaster = "mst_advice";
    public static final String LABMASTER = "mst_lab";
    public static final String NORMALVALUE = "normalvalue";
    public static final String Table_billing = "tbl_billing";
    public static final String Table_custommaster = "tbl_custommaster";
    public static final String BILL_NO = "billno";
    public static final String TOTALAMT = "Totalamt";
    public static final String PAIDAMT = "paidamt";
    public static final String DUEAMT = "dueamt";
    public static final String CREATEDDATE = "createdate";
    public static final String MODDATE = "moddate";
    public static final String TAXAMT = "taxamt";
    public static final String DESCRIPTION = "description";
    public static final String STATUS = "status";
    public static final String REFERID = "referId";
    public static final String VISITNO = "visitno";
    public static final String REMARK = "remark";
    public static final String DISCOUNT = "discount";
    public static final String BILLDATE = "billdate";
    public static final String FINALAMOUNT = "finalamount";
    public static final String XML = "xml";
    public static final String RECIEPTIDS = "reciptids";
    public static final String RECIEPTNO = "recieptno";
    public static final String CANCELREMARK = "cancleremark";
    public static final String CANCELDATE = "cancledate";
    public static final String REFUNDAMOUNT = "Refundamount";
    public static final String ITEMDETAIL = "item_detail";
    public static final String ITEMALL = "item_all";
    public static final String ADDMITIONNO = "AddmitionNo";
    public static final String ENTRYFROM = "entryfrom";
    public static final String EXTRADISCOUNT = "extradiscount";
    public static final String DEPARTMENTID = "department_id";
    public static final String PRESCRIPTIONPATH = "prescription_path";
    public static final String PREFERREDDATE = "preferreddate";
    public static final String PREFERREDTIME = "preferredtime";
    public static final String LABSTATUS = "labstatus";
    public static final String REFERFROM = "referfrom";
    public static final String UPDATEBY = "updateby";
    public static final String UPDATEDATE = "updatedate";
    public static final String CANCELBY = "cancelby";
    public static final String BILLBOOKID = "billbookid";
    public static final String TABLE_BILLDETAILS = "tbl_billdetail";
    public static final String ITEM = "item";
    public static final String BILLINGID = "billingId";
    public static final String OFFERID = "offerid";
    public static final String TAX = "tax";
    public static final String TAXID = "taxid";
    public static final String PRODUCTTYPE = "producttype";
    public static final String STOCKID = "stockid";
    public static final String ASSIGNBY = "assignby";
    public static final String ITEM_ID = "item_id";
    public static final String UNIT = "unit";
    public static final String UNITCOST = "unitcost";
    public static final String DISAMOUNT = "disamount";
    public static final String TAXAMOUNT = "taxamount";
    public static final String TAXTYPE = "taxtype";
    public static final String DISTYPE = "distype";
    public static final String TREATMENTDATE = "treatmentdate";
    public static final String PACK_ID = "pack_id";
    public static final String INDEXTEMPCOL = "indextempcol";
    public static final String COUNSELINGID = "counselingid";
    public static final String PROFILEID = "profileid";
    public static final String TABLE_RECIPTEMR = "tbl_recieptemr";
    public static final String BILL_ID = "bill_id";
    public static final String RECIEPT_ID = "reciept_id";
    public static final String RECEIPT_NO = "reciept_no";
    public static final String PAIDAMOUNT = "paidamount";
    public static final String CREATED_DATE = "created_date";
    public static final String CREATED_BY = "created_by";
    public static final String PRACID = "pracid";
    public static final String DD_CHEQUE_NO = "dd_cheque_No";
    public static final String DD_CHEQUE_DATE = "dd_Cheque_Date";
    public static final String PAID_VIVA = "Paid_Viva";
    public static final String TRANSACTION_ID = "transcation_ID";
    public static final String BANKNAME = "bankname";
    public static final String TOTALPAIDAMOUNT = "totalPaidamout";
    public static final String AMOUNTFROMADVANCE = "amountfromadvance";
    public static final String RECEIPT_DATE = "reciept_date";
    public static final String PATEINT_ID = "pateint_id";
    public static final String PAYREMARK = "PayRemark";
    public static final String BILLN0 = "billno";
    public static final String ADMITIONNO = "AddmitionNo";
    public static final String TRANSCTIONNO = "Transctionno";
    public static final String TILLLBALANCE = "tillbalance";
    public static final String RECEPTFROM = "ReceptFrom";
    public static final String DDDATE = "DDDate";
    public static final String PRACTICE_ID = "practice_id";
    public static final String DEPARTMENT_ID = "department_id";
    public static final String TABLE_MASTER = "table_master";
    public static final String TABLE_MASTERV1 = "table_masterv1";
    public static final String MASTER_KEY = "master_key";
    public static final String TABLE_DEPARTMENT = "table_department";
    public static final String TABLE_TEMPLATEQUESTIONS = "table_templatequestions";
    public static final String TABLE_TEMPLATEQUESTIONSFRESHDATA = "table_templatequestionsfreshdata";
    public static final String TABLE_VISIT = "table_visit";
    public static final String TABLE_TEMPLATE = "table_template";
    public static final String SYNC = "sync";
    public static final String TABLE_PRINT_SETTING = "table_printsetting";
    public static final String ISPRINT = "isprint";
    public static final String AppoinmentSource = "AppoinmentSource";
    public static final String CatagoryId = "CatagoryId";
    public static final String endDate = "endDate";
    public static final String startDate = "startDate";
    public static final String mobile = "mobile";
    public static final String TABLE_DRUG = "table_drug";
    public static final String TABLE_FILE = "table_file";
    public static final String BINARY_DATA = "BINARY_DATA";
    public  static final String PatientContactGroup="PatientContactGroup";
    public  static final String UID="UID";
    public  static final String Tel1="Tel1";
    public static String Titl = "Titl";
    public static String AppoinmentType = "AppoinmentType";
    public static String Aptid = "Aptid";
    public static String docname = "docname";
static  Context mcontext;
    private static DatabaseHelper mInstance = null;
    public DatabaseHelper(Context context) {
        
        super(context, DATABASE_NAME, null, 1);
        mcontext=context;
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        mcontext=context;
        if (mInstance == null) {
            mInstance = new DatabaseHelper(context);
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query_mstexamination = "CREATE TABLE IF NOT EXISTS " + TABLE_MSTEXAMINATION + "("
                + CATEGORYID + " INTEGER PRIMARY KEY AUTOINCREMENT," + NAME + " TEXT," + SUBCATEGORYID + " INTEGER,"
                + SORTORDER + " INTEGER," + DOCTORID + " INTEGER," + AMOUNT + " TEXT," + CODE + " TEXT," + PRACTICEID + " INTEGER," + PARENTID + " INTEGER,"
                + TYPE + " TEXT," + ABBR + " TEXT," + SAVEDTIME + " TEXT)";
        db.execSQL(query_mstexamination);


        String query_eyedetailsdata = "CREATE TABLE IF NOT EXISTS " + TABLE_EYESPECIALISTDATA + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + JSONDATA + " TEXT," + Patientid + " INTEGER,"
                + DOCTORID + " INTEGER," + PRACTICEID + " INTEGER,"
                + SAVEDTIME + " TEXT)";
        db.execSQL(query_eyedetailsdata);

        String querytpatient = "CREATE TABLE IF NOT EXISTS " + PATIENT + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Srno + " TEXT," + Patientid + " INTEGER," + ServerPatientid + " INTEGER," + Title + " TEXT," + FirstName
                + " TEXT," + FName + " TEXT,"
                + MiddleName + " TEXT," + LastName + " TEXT," + Address + " TEXT," + CountryId + " INTEGER," + StateId + " INTEGER," + CityId + " INTEGER,"
                + Area + " TEXT," + DOB + " TEXT," + Gender + " TEXT," + PinCode + " TEXT," + Mobile + " TEXT," + AlternateMobile + " TEXT," + EmailId + " TEXT,"
                + AlternateEmailId + " TEXT," + BloodGroup + " TEXT," + Customerid + " TEXT," + create_date + " TEXT," + update_date + " TEXT," + deleted + " TEXT,"
                + providerid + " INTEGER," + Assistantid + " INTEGER," + password + " TEXT," + IsActive + " INTEGER," + Department + " TEXT," + Language + " TEXT,"
                + Religion + " TEXT," + BillingIn + " TEXT," + Phone + " TEXT," + Occupation + " TEXT," + EmployeeNo + " TEXT," + EmployeeName + " TEXT,"
                + DateOfjoining + " TEXT," + Remark + " TEXT," + Tags + " TEXT," + ReferFrom + " TEXT," + ReferTo + " TEXT," + profileimage + " TEXT," + Height + " TEXT,"
                + aadharno + " TEXT," + pattype + " TEXT," + Maritalstatus + " TEXT," + WeddingDate + " TEXT," + panno + " TEXT," + SYNC + " INTEGER," + Age + " TEXT,"
                + AgeYear + " TEXT," + Aptid + " TEXT," + DESCRIPTION + " TEXT," + Tel1 + " TEXT," + UID + " TEXT," + PatientContactGroup + " TEXT,"+ AgeMonth + " TEXT)";
        db.execSQL(querytpatient);

      /*  String querytpatient = "CREATE TABLE IF NOT EXISTS " + PATIENT + "("
                + Srno + " INTEGER PRIMARY KEY AUTOINCREMENT," + Patientid + " INTEGER," + FirstName + " TEXT,"  + Gender + " TEXT," + Address + " TEXT," + Mobile + " TEXT)";
        db.execSQL(querytpatient);*/

      /*  String querytappointment = "CREATE TABLE IF NOT EXISTS " + appointment + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + identifier + " TEXT," + isAllDay + " INTEGER," + start + " TEXT, " + end + "TEXT,"
                + calendar + " TEXT," + tag + " TEXT," + title + " TEXT," + description + " TEXT," + status + " TEXT," + Patientid + " INTEGER,"
                + catagory + " TEXT," + name + " TEXT," + DoctorId + " INTEGER," + RoomId + " INTEGER," + OperatoryId + " INTEGER," + ipdid + " INTEGER," + purpose + " TEXT,"
                + type + " TEXT," + CancelRemark + " TEXT," + Cancelreason + " TEXT," + DepartmentId + " TEXT," + PracticeId + " TEXT," + UserId + " TEXT,"
                + generalid + " INTEGER," + Canceldate + " TEXT," + starttime + " REAL," + aptdate + " TEXT,"+DBPatientid+" INTEGER,"+SYNC+" INTEGER," + SAVEDTIME + " TEXT)";
        db.execSQL(querytappointment);
*/
        String querytappointment = "CREATE TABLE IF NOT EXISTS " + appointment + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + title + " TEXT," + MiddleName + " TEXT," + LastName + " TEXT," + Area + " TEXT,"
                + mobile + " TEXT," + EmailId + " TEXT," + address + " TEXT," + startDate + " TEXT," + endDate + " TEXT," + purpose + " TEXT,"
                + DoctorId + " TEXT," + type + " TEXT," + CatagoryId + " TEXT," + PracticeId + " TEXT," + AppoinmentSource + " TEXT," + AppoinmentType + " TEXT,"
                + Titl + " TEXT," + Patientid + " TEXT," + DBPatientid + " TEXT," + SYNC + " INTEGER," + docname + " TEXT," + description + " TEXT," + Aptid + " TEXT," + status + " TEXT,"+aptdate+" TEXT,"+starttime+" TEXT," + SAVEDTIME + " TEXT)";
        db.execSQL(querytappointment);


        String query_prescription = "CREATE TABLE IF NOT EXISTS " + prescription + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Patientid + " INTEGER," + Date + " TEXT,"
                + DOCTORID + " INTEGER," + JSONDATA + " TEXT," + SAVEDTIME + " TEXT)";
        db.execSQL(query_prescription);

        String query_Lab = "CREATE TABLE IF NOT EXISTS " + lab + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Patientid + " INTEGER," + Date + " TEXT,"
                + DOCTORID + " INTEGER," + JSONDATA + " TEXT," + Imagepath + " TEXT," + SAVEDTIME + " TEXT)";
        db.execSQL(query_Lab);

        String query_mstclinicalexamination = "CREATE TABLE IF NOT EXISTS " + ClinicalExamination + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Title + " TEXT," + CODE + " TEXT,"
                + TYPE + " TEXT," + Description + " TEXT," + DOCTORID + " INTEGER," + PRACTICEID + " INTEGER," + SAVEDTIME + " TEXT)";
        db.execSQL(query_mstclinicalexamination);

        String query_mstlab = "CREATE TABLE IF NOT EXISTS " + LABMASTER + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Title + " TEXT," + CODE + " TEXT,"
                + TYPE + " TEXT," + Description + " TEXT," + NORMALVALUE + " TEXT," + DOCTORID + " INTEGER," + PRACTICEID + " INTEGER," + SAVEDTIME + " TEXT)";
        db.execSQL(query_mstlab);

        String query_mstadvice = "CREATE TABLE IF NOT EXISTS " + AdviceMaster + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Title + " TEXT," + CODE + " TEXT,"
                + TYPE + " TEXT," + Description + " TEXT," + AMOUNT + " TEXT," + DOCTORID + " INTEGER," + PRACTICEID + " INTEGER," + SAVEDTIME + " TEXT)";
        db.execSQL(query_mstadvice);

        String querytbl_billing = "CREATE TABLE IF NOT EXISTS " + Table_billing + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + BILL_NO + " TEXT," + TOTALAMT + " TEXT," + PAIDAMT + " TEXT," + DUEAMT + " TEXT,"
                + CREATEDDATE + " TEXT," + MODDATE + " TEXT," + DELETED + " INTEGER," + DOCTORID + " INTEGER," + Patientid + " INTEGER," + PRACTICEID + " INTEGER,"
                + TAXAMT + " TEXT," + DESCRIPTION + " TEXT," + STATUS + " TEXT," + REFERID + " TEXT," + VISITNO + " TEXT," + REMARK + " TEXT," + DISCOUNT + " TEXT,"
                + BILLDATE + " TEXT," + FINALAMOUNT + " TEXT," + XML + " TEXT," + RECIEPTIDS + " TEXT," + RECIEPTNO + " TEXT," + CANCELREMARK + " TEXT,"
                + CANCELDATE + " TEXT," + REFUNDAMOUNT + " TEXT," + ITEMDETAIL + " TEXT," + ITEMALL + " INTEGER," + ADDMITIONNO + " TEXT," + ENTRYFROM + " TEXT,"
                + EXTRADISCOUNT + " TEXT," + DEPARTMENTID + " TEXT," + PRESCRIPTIONPATH + " TEXT," + PREFERREDDATE + " TEXT," + PREFERREDTIME + " TEXT," + LABSTATUS + " TEXT,"
                + REFERFROM + " TEXT," + UPDATEBY + " TEXT," + UPDATEDATE + " TEXT," + CANCELBY + " TEXT," + BILLBOOKID + " TEXT)";
        db.execSQL(querytbl_billing);

        String querytbl_billdetail = "CREATE TABLE IF NOT EXISTS " + TABLE_BILLDETAILS + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + ITEM + " TEXT," + REMARK + " TEXT," + BILLINGID + " INTEGER," + TOTALAMT + " TEXT,"
                + PAIDAMT + " TEXT," + DUEAMT + " TEXT," + DELETED + " INTEGER," + CREATEDDATE + " TEXT," + DISCOUNT + " TEXT," + OFFERID + " TEXT,"
                + TAX + " TEXT," + TAXID + " TEXT," + PRODUCTTYPE + " TEXT," + STOCKID + " TEXT," + ASSIGNBY + " TEXT," + ITEM_ID + " INTEGER," + UNIT + " INTEGER,"
                + UNITCOST + " TEXT," + DISAMOUNT + " TEXT," + TAXAMOUNT + " TEXT," + TAXTYPE + " TEXT," + DISTYPE + " TEXT," + FINALAMOUNT + " TEXT,"
                + TREATMENTDATE + " TEXT," + PACK_ID + " TEXT," + COUNSELINGID + " TEXTID," + INDEXTEMPCOL + " TEXT," + PROFILEID + " TEXT," + SAVEDTIME + " TEXT)";
        db.execSQL(querytbl_billdetail);

        String querytbl_recieptemr = "CREATE TABLE IF NOT EXISTS " + TABLE_RECIPTEMR + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + BILL_ID + " TEXT," + RECIEPT_ID + " TEXT," + RECEIPT_NO + " TEXT," + PAIDAMOUNT + " TEXT,"
                + CREATED_DATE + " TEXT," + CREATED_BY + " TEXT," + PRACID + " TEXT," + DD_CHEQUE_NO + " TEXT," + DD_CHEQUE_DATE + " TEXT," + PAID_VIVA + " TEXT,"
                + TRANSACTION_ID + " TEXT," + BANKNAME + " TEXT," + TAXAMOUNT + " TEXT," + TOTALPAIDAMOUNT + " TEXT," + AMOUNTFROMADVANCE + " TEXT," + RECEIPT_DATE + " TEXT," + PATEINT_ID + " TEXT,"
                + PAYREMARK + " TEXT," + status + " TEXT," + BILL_NO + " TEXT," + REFUNDAMOUNT + " TEXT," + ADMITIONNO + " TEXT," + TRANSCTIONNO + " TEXT,"
                + TILLLBALANCE + " TEXT," + RECEPTFROM + " TEXT," + DDDATE + " REAL," + VISITNO + " TEXT," + PRACTICE_ID + " TEXT," + DEPARTMENT_ID + " TEXT," + SAVEDTIME + " TEXT)";
        db.execSQL(querytbl_recieptemr);

        String master = "CREATE TABLE IF NOT EXISTS " + TABLE_MASTER + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + JSONDATA + " TEXT," + MASTER_KEY + " TEXT," + SAVEDTIME + " TEXT)";
        db.execSQL(master);

        String custommaster = "CREATE TABLE IF NOT EXISTS " + Table_custommaster + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + MASTER_KEY + " TEXT," + SAVEDTIME + " TEXT)";
        db.execSQL(custommaster);

        String templatedata = "CREATE TABLE IF NOT EXISTS " + TABLE_TEMPLATEQUESTIONS + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + VISITID + " INTEGER," + Patientid + " INTEGER," + DBPatientid + " INTEGER," + TemplateId + " INTEGER," + DBVISITID + " INTEGER," + JSONDATA + " TEXT," + SYNC + " INTEGER," + SAVEDTIME + " TEXT)";
        db.execSQL(templatedata);


        String templatedatafresh = "CREATE TABLE IF NOT EXISTS " + TABLE_TEMPLATEQUESTIONSFRESHDATA + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TEMPLATEID + " INTEGER," + JSONDATA + " TEXT," + SYNC + " INTEGER," + SAVEDTIME + " TEXT)";
        db.execSQL(templatedatafresh);

        String template = "CREATE TABLE IF NOT EXISTS " + TABLE_TEMPLATE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TemplateId + " INTEGER," + TemplateName + " INTEGER," + Category + " TEXT," + PracticeId + " INTEGER," + DepartmentId + " INTEGER," + Speciality + " TEXT," + SAVEDTIME + " TEXT)";
        db.execSQL(template);

        String visits = "CREATE TABLE IF NOT EXISTS " + TABLE_VISIT + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Patientid + " INTEGER," + DBPatientid + " INTEGER," + VISITID + " INTEGER," + VISIT + " INTEGER," + SYNC + " INTEGER," + DRNAME + " TEXT," + VISITDATE + " TEXT," + SAVEDTIME + " TEXT)";
        db.execSQL(visits);

        String print = "CREATE TABLE IF NOT EXISTS " + TABLE_PRINT_SETTING + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TemplateId + " INTEGER," + Qid + " INTEGER," + ISPRINT + " INTEGER," + SAVEDTIME + " TEXT)";
        db.execSQL(print);

        String querymaster = "CREATE TABLE IF NOT EXISTS " + TABLE_MASTERV1 + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + MASTER_KEY + " TEXT," + CATEGORYID + " INTEGER," + NAME + " TEXT," + JSONDATA + " TEXT," + SYNC + " INTEGER, " + SAVEDTIME + " TEXT)";
        db.execSQL(querymaster);

        String querydrug = "CREATE TABLE IF NOT EXISTS " + TABLE_DRUG + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + JSONDATA + " TEXT," + SYNC + " INTEGER, " + Patientid + " TEXT," + DBPatientid + " INTEGER," + VISITID + " TEXT," + DBVISITID + " INTEGER," + SAVEDTIME + " TEXT)";
        db.execSQL(querydrug);
        String queryFILE = "CREATE TABLE IF NOT EXISTS " + TABLE_FILE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + BINARY_DATA + " BLOB," + SAVEDTIME + " TEXT)";
        db.execSQL(queryFILE);
    }

    public void querybinarydata() {
        SQLiteDatabase db = getWritableDatabase();
        String queryFILE = "CREATE TABLE IF NOT EXISTS " + TABLE_FILE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + BINARY_DATA + " BLOB," + SAVEDTIME + " TEXT)";
        db.execSQL(queryFILE);
    }
    public  long savefiledata(ContentValues c){
        querybinarydata();
        SQLiteDatabase db = getWritableDatabase();
        return db.insertWithOnConflict(TABLE_FILE, null, c, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public void createtabledrug() {
        SQLiteDatabase db = getWritableDatabase();
        String querydrug = "CREATE TABLE IF NOT EXISTS " + TABLE_DRUG + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + JSONDATA + " TEXT," + SYNC + " INTEGER, " + Patientid + " TEXT," + DBPatientid + " INTEGER," + VISITID + " TEXT," + DBVISITID + " INTEGER," + SAVEDTIME + " TEXT)";
        db.execSQL(querydrug);
    }

    public long savealldrug(JSONArray array, int ptid, int dbid) {
        createtabledrug();
        long index = 0;
        try {
            SQLiteDatabase db = getWritableDatabase();
            int count = db.delete(TABLE_DRUG, DBPatientid + "= ? AND  " + SYNC + "=?", new String[]{Integer.toString(dbid), "1"});
            Log.e("count ", "count " + count);
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Drug>>() {
            }.getType();
            ArrayList<Drug> drugdata = gson.fromJson(array.toString(), type);
            for (int i = 0; i < drugdata.size(); i++) {
                long dbvisitid = dbvisitid(drugdata.get(i).getVisit_no());
                drugdata.get(i).setVisit_no(dbvisitid);
                Gson gson1 = new Gson();
                ContentValues c = new ContentValues();
                c.put(DatabaseHelper.JSONDATA, gson1.toJson(drugdata.get(i)));
                c.put(DatabaseHelper.DBPatientid, DBPatientid);
                c.put(DatabaseHelper.SYNC, 1);
                c.put(DatabaseHelper.Patientid, ptid);
                c.put(DatabaseHelper.SAVEDTIME, DateUtils.getSqliteTime());
                c.put(Patientid, ptid);
                c.put(DBPatientid, dbid);
                c.put(VISITID, drugdata.get(i).getVisit_no());
                c.put(DBVISITID, dbvisitid);
                Log.e("savedata ", "savedata " + c.toString());
                index = db.insertWithOnConflict(TABLE_DRUG, null, c, SQLiteDatabase.CONFLICT_REPLACE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return index;
    }


    public JSONArray getdrugdata(int dbpatientid) {
        createtabledrug();
        SQLiteDatabase db = getWritableDatabase();
        String searchQuery = "SELECT  * FROM " + TABLE_DRUG + " WHERE " + DBPatientid + "=" + dbpatientid;
        Log.e("query ", "query " + searchQuery);
        Cursor cursor = db.rawQuery(searchQuery, null);

        JSONArray resultSet = new JSONArray();
        cursor.moveToFirst();
        Log.e("data ", "data " + cursor.getCount());
        resultSet = convertcursorvaltoJSOnArray(cursor);
        cursor.close();
        return resultSet;
    }


    public JSONArray getdrugdatavisit(int dbvisitid) {
        createtabledrug();
        SQLiteDatabase db = getWritableDatabase();
        String searchQuery = "SELECT  * FROM " + TABLE_DRUG + " WHERE " + DBVISITID + "=" + dbvisitid;
        Log.e("query ", "query " + searchQuery);
        Cursor cursor = db.rawQuery(searchQuery, null);
        JSONArray resultSet = new JSONArray();
        cursor.moveToFirst();
        Log.e("data ", "data " + cursor.getCount());
        resultSet = convertcursorvaltoJSOnArray(cursor);
        cursor.close();
        return resultSet;
    }

    public void createtablemaster() {
        SQLiteDatabase db = getWritableDatabase();
        String querymaster = "CREATE TABLE IF NOT EXISTS " + TABLE_MASTERV1 + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + MASTER_KEY + " TEXT," + CATEGORYID + " INTEGER," + NAME + " TEXT," + JSONDATA + " TEXT," + SYNC + " INTEGER, " + SAVEDTIME + " TEXT)";
        db.execSQL(querymaster);
    }


    public long savemaster(String Name, String catid, int issync, String masterkey) {
        createtablemaster();
        long index = 0;
        try {
            createprinttable();
            SQLiteDatabase db = getWritableDatabase();
            String query = "select * from " + TABLE_MASTERV1 + " where " + NAME + " = '" + Name + "' AND " + MASTER_KEY + " = '" + masterkey + "' ";
            Cursor cursor = db.rawQuery(query, null);
            try {
                if (cursor.moveToFirst()) {
                } else {
                    ContentValues c = new ContentValues();
                    c.put(NAME, Name);
                    c.put(SYNC, issync);
                    c.put(MASTER_KEY, masterkey);
                    c.put(CATEGORYID, catid);
                    Log.e("cdata ", "TABLE_MASTERV1  cdata " + c.toString());
                    index = db.insertWithOnConflict(TABLE_MASTERV1, null, c, SQLiteDatabase.CONFLICT_REPLACE);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return index;
    }

    public  long saveRemaningMasterData(String masterKey,String catid, String issync, String value){
       createlabmaster();
       long index = 0;
       try {
           createprinttable();
           SQLiteDatabase db = getWritableDatabase();
           String query = "select * from " + TABLE_MASTERV1 + " where " + NAME + " = '" + value + "' AND " + MASTER_KEY + " = '" + masterKey + "' ";
           Cursor cursor = db.rawQuery(query, null);
           if (cursor.moveToFirst()) {

           }else {

               String val = value.replace("$$","UUID");
               String[] valu = val.split("UUID");

               for (int i = 0; i <value.length() ; i++) {

                   String finalAnswer = valu[i];
                   ContentValues c = new ContentValues();
                   c.put(NAME,finalAnswer);
                   c.put(SYNC,issync);
                   c.put(MASTER_KEY,masterKey);
                   c.put(CATEGORYID,catid);
                   index = db.insertWithOnConflict(TABLE_MASTERV1,null,c,SQLiteDatabase.CONFLICT_REPLACE);
               }


           }
       } catch (Exception e) {
           e.printStackTrace();
       }
       return index;

    }

    public long savemaster(String Name, String catid, int issync, String masterkey, String json) {
        createtablemaster();
        long index = 0;
        try {
            createprinttable();
            SQLiteDatabase db = getWritableDatabase();
            String query = "select * from " + TABLE_MASTERV1 + " where " + NAME + " = '" + Name + "' AND " + MASTER_KEY + " = '" + masterkey + "' ";
            Cursor cursor = db.rawQuery(query, null);
            try {
                if (cursor.moveToFirst()) {
                } else {
                    ContentValues c = new ContentValues();
                    c.put(NAME, Name);
                    c.put(SYNC, issync);
                    c.put(MASTER_KEY, masterkey);
                    c.put(CATEGORYID, catid);
                    c.put(JSONDATA, json);
                    //  Log.e("cdata ","cdata "+c.toString());
                    index = db.insertWithOnConflict(TABLE_MASTERV1, null, c, SQLiteDatabase.CONFLICT_REPLACE);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return index;
    }

    public JSONArray getMaster(String masterkey) {
        createtablemaster();
        SQLiteDatabase db = getWritableDatabase();
        createmastertable();
        String query = "SELECT * FROM " + TABLE_MASTERV1 + " WHERE " + MASTER_KEY + " ='" + masterkey + "'";
        Log.e("masterquery ","master querty "+query);
        Cursor cursor = db.rawQuery(query, null);
        JSONArray array = convertcursorvaltoJSOnArray(cursor);
        return array;
    }

    public void createprinttable() {
        SQLiteDatabase db = getWritableDatabase();
        String print = "CREATE TABLE IF NOT EXISTS " + TABLE_PRINT_SETTING + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TemplateId + " INTEGER," + Qid + " INTEGER," + ISPRINT + " INTEGER," + SAVEDTIME + " TEXT)";
        db.execSQL(print);
    }

    public int issettingupdated(int templateid) {
        createprinttable();
        SQLiteDatabase db = getWritableDatabase();
        String query = "select * from " + TABLE_PRINT_SETTING + " where " + TemplateId + " = " + templateid;
        Cursor cursor = db.rawQuery(query, null);
        return cursor.getCount();
    }

    public long saveprintsetting(final String qid, final String templateid, int isprint) {
        long index = 0;
        try {
            ContentValues c = new ContentValues();
            c.put(TemplateId, templateid);
            c.put(Qid, qid);
            c.put(ISPRINT, isprint);
            c.put(SAVEDTIME, DateUtils.getSqliteTime());

            createprinttable();
            SQLiteDatabase db = getWritableDatabase();
            String query = "select * from " + TABLE_PRINT_SETTING + " where " + TemplateId + " = " + templateid + " AND " + Qid + " = " + qid;
            Cursor cursor = db.rawQuery(query, null);
            try {
                if (cursor.moveToFirst()) {
                    int i = db.update(TABLE_PRINT_SETTING, c, TemplateId + " = ? And " + Qid + " = ? ", new String[]{templateid, qid});
                } else {
                    index = db.insertWithOnConflict(TABLE_PRINT_SETTING, null, c, SQLiteDatabase.CONFLICT_REPLACE);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return index;
    }

    public int isprint(final String qid, final String templateid) {
        SQLiteDatabase db = getWritableDatabase();
        int isprint = 1;
        createprinttable();

        String query = "select * from " + TABLE_PRINT_SETTING + " where " + TemplateId + " = " + templateid + " AND " + Qid + " = " + qid;
        Cursor cursor = db.rawQuery(query, null);

        try {
            if (cursor.moveToFirst()) {
                isprint = cursor.getInt(cursor.getColumnIndex(ISPRINT));
            } else {
                ContentValues c = new ContentValues();
                c.put(TemplateId, templateid);
                c.put(Qid, qid);
                c.put(ISPRINT, isprint);
                c.put(SAVEDTIME, DateUtils.getSqliteTime());
                Log.e("isprintdata ", "isprintdata " + isprint);
                db.insertWithOnConflict(TABLE_PRINT_SETTING, null, c, SQLiteDatabase.CONFLICT_REPLACE);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return isprint;
    }

    public void createtemplatefreshdata() {
        SQLiteDatabase db = getWritableDatabase();
        String templatedatafresh = "CREATE TABLE IF NOT EXISTS " + TABLE_TEMPLATEQUESTIONSFRESHDATA + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TEMPLATEID + " INTEGER," + JSONDATA + " TEXT," + SYNC + " INTEGER," + SAVEDTIME + " TEXT)";
        db.execSQL(templatedatafresh);
    }

    public void savefreshtemplatedata(int templateId, String jsondata) {
        try {
            Log.e("save fresh data ", " " + templateId + " " + jsondata);
            ContentValues c = new ContentValues();
            c.put(JSONDATA, jsondata);
            c.put(SAVEDTIME, DateUtils.getSqliteTime());
            c.put(TEMPLATEID, templateId);
            SQLiteDatabase db = getWritableDatabase();
            createtemplatefreshdata();
            String query = "select * from " + TABLE_TEMPLATEQUESTIONSFRESHDATA + " where " + TEMPLATEID + " = " + templateId;
            Cursor c1 = db.rawQuery(query, null);
            if (c1.moveToFirst()) {
                int i = db.update(TABLE_TEMPLATEQUESTIONSFRESHDATA, c, TEMPLATEID + "=?", new String[]{Integer.toString(templateId)});
            } else {
                long index = db.insertWithOnConflict(TABLE_TEMPLATEQUESTIONSFRESHDATA, null, c, SQLiteDatabase.CONFLICT_REPLACE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JSONArray getfreshtemplatedata(String templateid) {
        createtemplatefreshdata();
        SQLiteDatabase db = getWritableDatabase();
        String query = "select * from " + TABLE_TEMPLATEQUESTIONSFRESHDATA + " where " + TEMPLATEID + " = " + templateid;
        Cursor c1 = db.rawQuery(query, null);
        JSONArray array = convertcursorvaltoJSOnArray(c1);
        Log.e("data ", "data " + array.toString());
        return array;
    }

    public void createtemplateqdatatable() {
        SQLiteDatabase db = getWritableDatabase();
        String templatedata = "CREATE TABLE IF NOT EXISTS " + TABLE_TEMPLATEQUESTIONS + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + VISITID + " INTEGER," + Patientid + " INTEGER," + DBPatientid + " INTEGER," + TemplateId + " INTEGER," + DBVISITID + " INTEGER," + JSONDATA + " TEXT," + SYNC + " INTEGER," + SAVEDTIME + " TEXT)";
        db.execSQL(templatedata);
    }

    public void savetemplatedata(String visitid, String patientid, String dbpatientid, String jsondata, int issync, int dbptid, int dbvisitid, String templateid) {
        try {
           // Log.e("templatedata ", "data " + jsondata);
            ContentValues c = new ContentValues();
            c.put(VISITID, visitid);
            c.put(DBPatientid, dbptid);
            c.put(DBVISITID, dbvisitid);
            c.put(Patientid, patientid);
            c.put(JSONDATA, jsondata);
            c.put(SAVEDTIME, DateUtils.getSqliteTime());
            c.put(SYNC, issync);
            c.put(TemplateId, templateid);
            SQLiteDatabase db = getWritableDatabase();
            createtemplateqdatatable();
            Log.e("visitdata ", "visitdata " + c.toString());
            String query = "select * from " + TABLE_TEMPLATEQUESTIONS + " where " + DBVISITID + " = " + dbvisitid + " AND " + DBPatientid + " = " + dbpatientid + " AND " + TemplateId + " = " + templateid;
            Cursor c1 = db.rawQuery(query, null);
            Log.e("templatequery ", "visitquery " + query);
            if (c1.moveToFirst()) {
                int i = db.update(TABLE_TEMPLATEQUESTIONS, c, DBPatientid + " = ? AND " + DBVISITID + " = ? AND " + TemplateId + " = ?", new String[]{dbpatientid, Integer.toString(dbvisitid), templateid});
                //Log.e("update  ", "update " + i);
            } else {
                long index = db.insertWithOnConflict(TABLE_TEMPLATEQUESTIONS, null, c, SQLiteDatabase.CONFLICT_REPLACE);
              //  Log.e("insert  ", "insert ");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JSONArray gettemplatedata(String dbvisitid, String dbpatientid, String templateid) {
        createtemplateqdatatable();
        SQLiteDatabase db = getWritableDatabase();
        String query = "select * from " + TABLE_TEMPLATEQUESTIONS + " where " + DBVISITID + " = " + dbvisitid + " AND " + DBPatientid + " = " + dbpatientid + " AND " + TemplateId + " = " + templateid;
        Log.e("template templatett", "query " + query);
        Cursor c1 = db.rawQuery(query, null);
        JSONArray array = convertcursorvaltoJSOnArray(c1);
        Log.e("data templatett ", "data " + array.toString());
        return array;
    }

    public void updatetemplatedatasync(String visitid, String patientid) {
        createtemplateqdatatable();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(SYNC, 1);
        db.update(TABLE_TEMPLATEQUESTIONS, c, Patientid + " = ? AND " + VISITID + " = ?", new String[]{patientid, visitid});
    }

    public JSONArray getnotsync() {
        createtemplateqdatatable();
        SQLiteDatabase db = getWritableDatabase();
        String query = "select * from " + TABLE_TEMPLATEQUESTIONS + " where " + SYNC + " = " + 0;
        Cursor c1 = db.rawQuery(query, null);
        JSONArray array = convertcursorvaltoJSOnArray(c1);
        Log.e("data ", "data " + array.toString());
        return array;
    }

    public JSONArray getofflinepatient() {
        createtablepatient();
        SQLiteDatabase db = getWritableDatabase();
        String query = "select * from " + PATIENT + " where " + SYNC + " = " + 0;
        Cursor c1 = db.rawQuery(query, null);
        JSONArray array = convertcursorvaltoJSOnArray(c1);
        Log.e("data ", "data " + array.toString());
        return array;
    }


    public JSONArray getofflineappointment() {
        createtableappointment();
        SQLiteDatabase db = getWritableDatabase();
        String query = "select * from " + appointment + " where " + SYNC + " = " + 0;
        Cursor c1 = db.rawQuery(query, null);
        JSONArray array = convertcursorvaltoJSOnArray(c1);
        Log.e("data ", "data " + array.toString());
        return array;
    }

    public int getofflinepatientcount() {
        createtablepatient();
        SQLiteDatabase db = getWritableDatabase();
        String query = "select * from " + PATIENT + " where " + SYNC + " = " + 0;
        Cursor c1 = db.rawQuery(query, null);
        return c1.getCount();
    }

    public int getofflinevisitcount() {
        createvisittable();
        SQLiteDatabase db = getWritableDatabase();
        String query = "select * from " + TABLE_VISIT + " where " + SYNC + " = " + 0;
        Cursor c1 = db.rawQuery(query, null);
        return c1.getCount();
    }

    public int getofflineappointmentcount() {
        createvisittable();
        SQLiteDatabase db = getWritableDatabase();
        String query = "select * from " + appointment + " where " + SYNC + " = " + 0;
        Cursor c1 = db.rawQuery(query, null);
        return c1.getCount();
    }

    public int getmaxvisitcount(int dbptid) {
        int id = 0;
        createvisittable();
        SQLiteDatabase db = getWritableDatabase();
        String query = "select max(" + VISIT + ") from " + TABLE_VISIT + " where " + DBPatientid + " = " + dbptid;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
            id = cursor.getInt(0);
        }
        return id;
    }


    public int getofflinetemplatecount() {
        createtablepatient();
        SQLiteDatabase db = getWritableDatabase();
        String query = "select * from " + TABLE_TEMPLATEQUESTIONS + " where " + SYNC + " = " + 0;
        Cursor c1 = db.rawQuery(query, null);
        return c1.getCount();
    }

    public int getofflinemastercount() {
        createtablemaster();
        SQLiteDatabase db = getWritableDatabase();
        String query = "select * from " + TABLE_MASTERV1 + " where " + SYNC + " = " + 0;
        Cursor c1 = db.rawQuery(query, null);
        return c1.getCount();
    }

    public JSONArray getofflinevivisits() {
        createvisittable();
        SQLiteDatabase db = getWritableDatabase();
        String query = "select * from " + TABLE_VISIT + " where " + SYNC + " = " + 0;
        Cursor c1 = db.rawQuery(query, null);
        JSONArray array = convertcursorvaltoJSOnArray(c1);
        Log.e("data ", "data " + array.toString());
        return array;
    }

    public void createvisittable() {
        SQLiteDatabase db = getWritableDatabase();
        String visits = "CREATE TABLE IF NOT EXISTS " + TABLE_VISIT + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Patientid + " INTEGER," + DBPatientid + " INTEGER," + VISITID + " INTEGER," + VISIT + " INTEGER," + SYNC + " INTEGER," + DRNAME + " TEXT," + VISITDATE + " TEXT," + SAVEDTIME + " TEXT)";
        db.execSQL(visits);
    }

    public long savevisitdata(String dbpatientid, int visitid, ContentValues c) {
        long index = 0;
        try {
            SQLiteDatabase db = getWritableDatabase();
            createvisittable();
            Log.e("savevisit ", "visit " + visitid + " " + dbpatientid + " " + c.toString());
            if (visitid == 0) {
                c.put(SAVEDTIME, DateUtils.getSqliteTime());
                index = db.insertWithOnConflict(TABLE_VISIT, null, c, SQLiteDatabase.CONFLICT_REPLACE);
                //Log.e("insert  visit", "insert ");
            } else {
                String query = "select * from " + TABLE_VISIT + " where " + DBPatientid + " = " + dbpatientid + " AND " + VISITID + " = " + visitid;
                Cursor c1 = db.rawQuery(query, null);
                Log.e("query ", " " + query + " " + c1.getCount());
                if (c1.moveToFirst()) {
                    index = c1.getLong(0);
                    c.put(SAVEDTIME, DateUtils.getSqliteTime());
                    db.update(TABLE_VISIT, c, DBPatientid + " = ? AND " + VISITID + " = ? ", new String[]{DBPatientid, Integer.toString(visitid)});
                  //  Log.e("query update  visit", "update " + index + " " + c.toString());
                } else {
                    c.put(SAVEDTIME, DateUtils.getSqliteTime());
                    index = db.insertWithOnConflict(TABLE_VISIT, null, c, SQLiteDatabase.CONFLICT_REPLACE);
                   // Log.e("query insert visit ", "insert " + index);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return index;
    }

    public JSONArray getvisitdata(String dbpatientid, String ptid) {
        //Log.e("data dbid", "data " + dbpatientid);
        createvisittable();
        SQLiteDatabase db = getWritableDatabase();
        String query = "select * from " + TABLE_VISIT + " where " + DBPatientid + " = " + dbpatientid + " AND " + Patientid + " = " + ptid;
       // Log.e("query ", "query " + query);
        Cursor c1 = db.rawQuery(query, null);
        JSONArray array = convertcursorvaltoJSOnArray(c1);
       // Log.e("query data ", "data " + array.toString());
        return array;
    }

    public long dbvisitid(long visitid) {
        long dbvisitid = 0;
        SQLiteDatabase db = getWritableDatabase();
        String query = "Select * from " + TABLE_VISIT + " where " + VISITID + " = " + visitid;
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            dbvisitid = c.getLong(c.getColumnIndex(ID));
        }
        return dbvisitid;
    }

    public void createtemplatetable() {
        SQLiteDatabase db = getWritableDatabase();
        String template = "CREATE TABLE IF NOT EXISTS " + TABLE_TEMPLATE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TemplateId + " INTEGER," + TemplateName + " INTEGER," + Category + " TEXT," + PracticeId + " INTEGER," + DepartmentId + " INTEGER," + Speciality + " TEXT," + SAVEDTIME + " TEXT)";
        db.execSQL(template);
    }

    public void savetemplatenamedata(ContentValues c, String templateid) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            createtemplatetable();

            String query = "select * from " + TABLE_TEMPLATE + " where " + TemplateId + " = " + templateid;
            Cursor c1 = db.rawQuery(query, null);

            if (c1.moveToFirst()) {
               // Log.e("update  ", "update ");
                int i = db.update(TABLE_TEMPLATE, c, TemplateId + "=?", new String[]{templateid});
            } else {
                long index = db.insertWithOnConflict(TABLE_TEMPLATE, null, c, SQLiteDatabase.CONFLICT_REPLACE);
               // Log.e("insert  ", "insert ");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JSONArray gettemplatenamedata(String cat) {
        createtemplatetable();
        SQLiteDatabase db = getWritableDatabase();
        String query = "select * from " + TABLE_TEMPLATE+ " where " + Category + " = '" + cat+"'";
        Cursor c1 = db.rawQuery(query, null);
        JSONArray array = convertcursorvaltoJSOnArray(c1);
        Log.e("data ", "data " + array.toString());
        return array;
    }


    public void createmastertable() {
        SQLiteDatabase db = getWritableDatabase();
        String master = "CREATE TABLE IF NOT EXISTS " + TABLE_MASTER + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + JSONDATA + " TEXT," + MASTER_KEY + " TEXT," + SAVEDTIME + " TEXT)";
        db.execSQL(master);
    }

    public void createcustommastertable() {
        SQLiteDatabase db = getWritableDatabase();
        String custommaster = "CREATE TABLE IF NOT EXISTS " + Table_custommaster + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + MASTER_KEY + " TEXT," + SAVEDTIME + " TEXT)";
        db.execSQL(custommaster);
    }

    public ArrayList<Master> getCustommasterdata() {
        createcustommastertable();
        ArrayList<Master> dataset = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        createTablemstexamination();
        String query = "select * from " + Table_custommaster;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            dataset.add(new Master(cursor.getString(cursor.getColumnIndex(MASTER_KEY))));
            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();
            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {
                        if (cursor.getString(i) != null) {
                            Log.d("TAG_NAME", cursor.getString(i));
                            rowObject.put(cursor.getColumnName(i), cursor.getString(i));
                        } else {
                            rowObject.put(cursor.getColumnName(i), "");
                        }
                    } catch (Exception e) {
                        Log.d("TAG_NAME", e.getMessage());
                    }
                }
            }
            cursor.moveToNext();
        }
        cursor.close();
        return dataset;
    }

    public boolean SaveCustommaster(String key) {
        try {
            createcustommastertable();
            SQLiteDatabase db = getWritableDatabase();
            String query = "select * from " + Table_custommaster + " where " + MASTER_KEY + " == '" + key + "'";
            Cursor c1 = db.rawQuery(query, null);

            if (c1.moveToFirst()) {
                Log.e("custommaster ", "if");
                return false;
            } else {
                Log.e("custommaster ", "master null");
                ContentValues c = new ContentValues();
                c.put(MASTER_KEY, key);
                c.put(SAVEDTIME, DateUtils.getSqliteTime());
                long index = db.insertWithOnConflict(Table_custommaster, null, c, SQLiteDatabase.CONFLICT_REPLACE);
                Log.e("index ", "index " + index);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public long getlastIndex(String tablename) {
        if (tablename.equalsIgnoreCase(Table_billing)) {
            createtablebilling();
        } else if (tablename.equalsIgnoreCase(TABLE_BILLDETAILS)) {
            createtablebillingdetails();
        } else if (tablename.equalsIgnoreCase(TABLE_RECIPTEMR))
            createtablereceiptemr();

        long lastid = -1;
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT " + ID + " from " + tablename + " order by " + ID + " DESC limit 1";
        Cursor c = db.rawQuery(query, null);
        if (c != null && c.moveToFirst()) {
            lastid = c.getLong(0); //The 0 is the column index, we only have 1 column, so the index is 0
        }
        return lastid;
    }

    public void createtablereceiptemr() {
        SQLiteDatabase db = getWritableDatabase();
        String querytbl_recieptemr = "CREATE TABLE IF NOT EXISTS " + TABLE_RECIPTEMR + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + BILL_ID + " TEXT," + RECIEPT_ID + " TEXT," + RECEIPT_NO + " TEXT," + PAIDAMOUNT + " TEXT,"
                + CREATED_DATE + " TEXT," + CREATED_BY + " TEXT," + PRACID + " TEXT," + DD_CHEQUE_NO + " TEXT," + DD_CHEQUE_DATE + " TEXT," + PAID_VIVA + " TEXT,"
                + TRANSACTION_ID + " TEXT," + BANKNAME + " TEXT," + TAXAMOUNT + " TEXT," + TOTALPAIDAMOUNT + " TEXT," + AMOUNTFROMADVANCE + " TEXT," + RECEIPT_DATE + " TEXT," + PATEINT_ID + " TEXT,"
                + PAYREMARK + " TEXT," + status + " TEXT," + BILL_NO + " TEXT," + REFUNDAMOUNT + " TEXT," + ADMITIONNO + " TEXT," + TRANSCTIONNO + " TEXT,"
                + TILLLBALANCE + " TEXT," + RECEPTFROM + " TEXT," + DDDATE + " REAL," + VISITNO + " TEXT," + PRACTICE_ID + " TEXT," + DEPARTMENT_ID + " TEXT," + SAVEDTIME + " TEXT)";
        db.execSQL(querytbl_recieptemr);
    }

    public void createtablebilling() {
        SQLiteDatabase db = getWritableDatabase();
        String querytbl_billing = "CREATE TABLE IF NOT EXISTS " + Table_billing + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + BILL_NO + " TEXT," + TOTALAMT + " TEXT," + PAIDAMT + " TEXT," + DUEAMT + " TEXT,"
                + CREATEDDATE + " TEXT," + MODDATE + " TEXT," + DELETED + " INTEGER," + DOCTORID + " INTEGER," + Patientid + " INTEGER," + PRACTICEID + " INTEGER,"
                + TAXAMT + " TEXT," + DESCRIPTION + " TEXT," + STATUS + " TEXT," + REFERID + " TEXT," + VISITNO + " TEXT," + REMARK + " TEXT," + DISCOUNT + " TEXT,"
                + BILLDATE + " TEXT," + FINALAMOUNT + " TEXT," + XML + " TEXT," + RECIEPTIDS + " TEXT," + RECIEPTNO + " TEXT," + CANCELREMARK + " TEXT,"
                + CANCELDATE + " TEXT," + REFUNDAMOUNT + " INTEGER," + ITEMDETAIL + " TEXT," + ITEMALL + " INTEGER," + ADDMITIONNO + " TEXT," + ENTRYFROM + " TEXT,"
                + EXTRADISCOUNT + " TEXT," + DEPARTMENTID + " TEXT," + PRESCRIPTIONPATH + " TEXT," + PREFERREDDATE + " TEXT," + PREFERREDTIME + " TEXT," + LABSTATUS + " TEXT,"
                + REFERFROM + " TEXT," + UPDATEBY + " TEXT," + UPDATEDATE + " TEXT," + CANCELBY + " TEXT," + BILLBOOKID + " TEXT," + SAVEDTIME + " TEXT)";
        db.execSQL(querytbl_billing);
    }

    public void createtablebillingdetails() {
        SQLiteDatabase db = getWritableDatabase();
        String querytbl_billdetail = "CREATE TABLE IF NOT EXISTS " + TABLE_BILLDETAILS + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + ITEM + " TEXT," + REMARK + " INTEGER," + BILLINGID + " TEXT," + TOTALAMT + " TEXT,"
                + PAIDAMT + " TEXT," + DUEAMT + " TEXT," + DELETED + " TEXT," + CREATEDDATE + " TEXT," + DISCOUNT + " TEXT," + OFFERID + " TEXT,"
                + TAX + " TEXT," + TAXID + " TEXT," + PRODUCTTYPE + " TEXT," + STOCKID + " TEXT," + ASSIGNBY + " TEXT," + ITEM_ID + " INTEGER," + UNIT + " TEXT,"
                + UNITCOST + " TEXT," + DISAMOUNT + " TEXT," + TAXAMOUNT + " TEXT," + TAXTYPE + " TEXT," + DISTYPE + " TEXT," + FINALAMOUNT + " TEXT,"
                + TREATMENTDATE + " INTEGER," + PACK_ID + " TEXT," + COUNSELINGID + " REAL," + INDEXTEMPCOL + " TEXT," + PROFILEID + " TEXT," + SAVEDTIME + " TEXT)";
        db.execSQL(querytbl_billdetail);
    }

    public JSONArray getdata(String tablename) {
        JSONArray array = new JSONArray();
        try {
            createtable(tablename);
            SQLiteDatabase db = getWritableDatabase();
            if (tablename.equalsIgnoreCase(Table_billing)) {
                createtablebilling();
            } else if (tablename.equalsIgnoreCase(TABLE_BILLDETAILS)) {
                createtablebillingdetails();
            } else if (tablename.equalsIgnoreCase(TABLE_RECIPTEMR))
                createtablereceiptemr();

            String query = "select * from " + tablename;
            Cursor cursor = db.rawQuery(query, null);
            array = convertcursorvaltoJSOnArray(cursor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return array;
    }

    public JSONArray getbillingdetails(long billingid) {
        JSONArray array = new JSONArray();
        try {
            SQLiteDatabase db = getWritableDatabase();
            createtablebillingdetails();
            String query = "select * from " + TABLE_BILLDETAILS + " where " + BILLINGID + "=" + billingid;
            Cursor cursor = db.rawQuery(query, null);
            array = convertcursorvaltoJSOnArray(cursor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return array;
    }

    public void updatereceiptid(ContentValues c, long id) {
        SQLiteDatabase db = getWritableDatabase();
        db.update(Table_billing, c, ID + " =?", new String[]{Long.toString(id)});
    }

    public void updatepatient(ContentValues c, String dbid) {
        Log.e("updatepatient id", "id" + dbid);
        createtablepatient();
        SQLiteDatabase db = getWritableDatabase();
        db.update(PATIENT, c, ID + " =?", new String[]{dbid});
    }

    public void updatevisit(ContentValues c, String dbid) {
        //Log.e("updatepatient id","id"+dbid);
        createvisittable();
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_VISIT, c, ID + " =?", new String[]{dbid});
    }

    public void updatevisitpatientid(ContentValues c, String ptdbid) {
        //Log.e("updatepatient id","id"+dbid);
        createvisittable();
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_VISIT, c, DBPatientid + " =?", new String[]{ptdbid});
    }

    public void updatetemplatepatientid(ContentValues c, String ptdbid) {
        createtemplateqdatatable();
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_TEMPLATEQUESTIONS, c, DBPatientid + " = ?", new String[]{ptdbid});
    }

    public void updateappointment(ContentValues c, String id) {
        Log.e("updateappointmentdata ", "data " + id);
        createtableappointment();
        SQLiteDatabase db = getWritableDatabase();
        db.update(appointment, c, ID + " = ?", new String[]{id});
    }

    public void updatetemplatevisitid(ContentValues c, String visitdbid) {
        createtemplateqdatatable();
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_TEMPLATEQUESTIONS, c, DBVISITID + " =?", new String[]{visitdbid});
    }

    public void updateappointmentdbptid(ContentValues c, String ptdbid) {
        createtableappointment();
        SQLiteDatabase db = getWritableDatabase();
        db.update(appointment, c, DBPatientid + " =?", new String[]{ptdbid});
    }

    public void updatemaster(ContentValues c, String dbid) {
        createvisittable();
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_VISIT, c, ID + " =?", new String[]{dbid});
    }


    public long savedata(ContentValues c, String tablename) {
        if (tablename.equalsIgnoreCase(Table_billing)) {
            createtablebilling();
        } else if (tablename.equalsIgnoreCase(TABLE_BILLDETAILS)) {
            createtablebillingdetails();
        } else if (tablename.equalsIgnoreCase(TABLE_RECIPTEMR))
            createtablereceiptemr();

        long index = 0;
        SQLiteDatabase db = getWritableDatabase();
        try {
            index = db.insertWithOnConflict(tablename, null, c, SQLiteDatabase.CONFLICT_REPLACE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return index;
    }

    public void savedata(String tablename, String jsondata) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            createtable(tablename);
            ContentValues c = new ContentValues();
            c.put(JSONDATA, jsondata);
            c.put(SAVEDTIME, DateUtils.getSqliteTime());
            c.put(DELETED, 0);
            Log.e("dept ","dept "+c.toString());
            db.insertWithOnConflict(tablename, null, c, SQLiteDatabase.CONFLICT_REPLACE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createadvicemaster() {
        SQLiteDatabase db = getWritableDatabase();
        String query_mstlab = "CREATE TABLE IF NOT EXISTS " + AdviceMaster + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Title + " TEXT," + CODE + " TEXT,"
                + TYPE + " TEXT," + Description + " TEXT," + AMOUNT + " TEXT," + DOCTORID + " INTEGER," + PRACTICEID + " INTEGER," + SAVEDTIME + " TEXT)";
        db.execSQL(query_mstlab);
    }

    public long saveadvicemaster(final String type, ContentValues c) {
        long index = 0;
        try {
            createadvicemaster();
            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor = db.query(AdviceMaster, new String[]{ID}, Title + " LIKE ? ", new String[]{c.getAsString(Title)}, null, null, null);
            try {
                if (cursor.getCount() > 0) {
                    return -1;
                } else {
                    c.put(SAVEDTIME, DateUtils.getSqliteTime());
                    index = db.insertWithOnConflict(AdviceMaster, null, c, SQLiteDatabase.CONFLICT_REPLACE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return index;
    }

    public ArrayList<Advicemasterdata> getadvicemasterdata() {
        ArrayList<Advicemasterdata> masterData = new ArrayList<>();
        try {
            SQLiteDatabase db = getWritableDatabase();
            createadvicemaster();
            String query = "select * from " + AdviceMaster;//+ " WHERE " + TYPE + " LIKE '" + type+"'";
            Cursor cursor = db.rawQuery(query, null);
            JSONArray array = convertcursorvaltoJSOnArray(cursor);
            for (int i = 0; i < array.length(); i++) {
                masterData.add(new Advicemasterdata(array.getJSONObject(i)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return masterData;
    }


    public void createlabmaster() {
        SQLiteDatabase db = getWritableDatabase();
        String query_mstlab = "CREATE TABLE IF NOT EXISTS " + LABMASTER + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Title + " TEXT," + CODE + " TEXT,"
                + TYPE + " TEXT," + Description + " TEXT," + NORMALVALUE + " TEXT," + DOCTORID + " INTEGER," + PRACTICEID + " INTEGER," + SAVEDTIME + " TEXT)";
        db.execSQL(query_mstlab);
    }

    public long savelabmaster(final String type, ContentValues c) {
        long index = 0;
        try {
            createlabmaster();
            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor = db.query(LABMASTER, new String[]{ID},
                    TYPE + " LIKE ? And " + Title + " LIKE ? ", new String[]{"" + type + "", c.getAsString(Title)},
                    null, null, null);
            try {
                if (cursor.getCount() > 0) {
                    Log.e("allready exist ", "inserted " + index);
                    return -1;
                } else {
                    c.put(SAVEDTIME, DateUtils.getSqliteTime());
                    index = db.insertWithOnConflict(LABMASTER, null, c, SQLiteDatabase.CONFLICT_REPLACE);
                    Log.e("insereted111111 ", "inserted " + index);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return index;
    }

    public ArrayList<LabMasterData> getlabmasterdata() {
        ArrayList<LabMasterData> masterData = new ArrayList<>();
        try {
            SQLiteDatabase db = getWritableDatabase();
            createlabmaster();
            String query = "select * from " + LABMASTER;//+ " WHERE " + TYPE + " LIKE '" + type+"'";
            Cursor cursor = db.rawQuery(query, null);
            JSONArray array = convertcursorvaltoJSOnArray(cursor);
            // Log.e("array", "array " + array.toString());
            for (int i = 0; i < array.length(); i++) {
                masterData.add(new LabMasterData(array.getJSONObject(i)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return masterData;
    }

    public void createtableclinicalexamination() {
        SQLiteDatabase db = getWritableDatabase();
        String query_mstclinicalexamination = "CREATE TABLE IF NOT EXISTS " + ClinicalExamination + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Title + " TEXT," + CODE + " TEXT,"
                + TYPE + " TEXT," + Description + " TEXT," + DOCTORID + " INTEGER," + PRACTICEID + " INTEGER," + SAVEDTIME + " TEXT)";
        db.execSQL(query_mstclinicalexamination);
    }

    public long saveclinicalExamination(final String type, ContentValues c) {
        long index = 0;
        try {
            createtableclinicalexamination();
            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor = db.query(ClinicalExamination, new String[]{ID},
                    TYPE + " LIKE ? And " + Title + " LIKE ? ", new String[]{"" + type + "", c.getAsString(Title)},
                    null, null, null);
            try {
                if (cursor.getCount() > 0) {
                    return -1;
                } else {
                    c.put(SAVEDTIME, DateUtils.getSqliteTime());
                    index = db.insertWithOnConflict(ClinicalExamination, null, c, SQLiteDatabase.CONFLICT_REPLACE);
                    Log.e("insereted111111 ", "inserted " + index);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return index;
    }

    public ArrayList<MasterDataClinicalExamination> getclinicalexamination(String type) {
        ArrayList<MasterDataClinicalExamination> masterData = new ArrayList<>();
        try {
            SQLiteDatabase db = getWritableDatabase();
            createtableclinicalexamination();
            String query = "select * from " + ClinicalExamination + " WHERE " + TYPE + " LIKE '" + type + "'";
            Cursor cursor = db.rawQuery(query, null);
            JSONArray array = convertcursorvaltoJSOnArray(cursor);
            for (int i = 0; i < array.length(); i++) {
                masterData.add(new MasterDataClinicalExamination(array.getJSONObject(i)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return masterData;
    }

    public void createtablelab(String tablename) {
        SQLiteDatabase db = getWritableDatabase();
        String query_Lab = "CREATE TABLE IF NOT EXISTS " + tablename + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Patientid + " INTEGER," + Date + " TEXT,"
                + DOCTORID + " INTEGER," + JSONDATA + " TEXT," + Imagepath + " TEXT," + SAVEDTIME + " TEXT)";
        db.execSQL(query_Lab);
    }

    public long savelab(ContentValues c, String date, int srno, String tablename) {
        createtablelab(tablename);
        long index = -1;
        try {
            SQLiteDatabase db = getWritableDatabase();
            String searchQuery = "SELECT  * FROM " + tablename + " WHERE " + Date + " LIKE '" + date + "' AND " + Patientid + "=" + srno;
            Cursor cursor = db.rawQuery(searchQuery, null);
            Log.e("query ", "query " + cursor.getCount() + " " + searchQuery);
            Log.e("cursor ", "cursor " + cursor.getColumnName(0));
            if (cursor.getCount() > 0) {
                do {
                    cursor.moveToNext();
                    String s = cursor.getString(cursor.getColumnIndex(JSONDATA));
                    Log.e("s ", "s" + s);
                    JSONArray array = new JSONArray(s);
                    JSONArray array1 = new JSONArray(c.getAsString(JSONDATA));
                    for (int i = 0; i < array1.length(); i++) {
                        array.put(array1.getJSONObject(i));
                    }

                    c.put(JSONDATA, array.toString());
                    try {
                        String imagedata = cursor.getString(cursor.getColumnIndex(Imagepath));
                        JSONArray imagearray1 = new JSONArray(c.getAsString(Imagepath));
                        JSONArray imagearray = new JSONArray(imagedata);
                        for (int i = 0; i < imagearray1.length(); i++) {
                            imagearray.put(imagearray1.getJSONObject(i));
                        }
                        c.put(Imagepath, imagearray.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    db.update(tablename, c, Date + " Like ? AND " + Patientid + " =?", new String[]{date, Integer.toString(srno)});
                } while (cursor.moveToNext());
            } else {
                index = db.insertWithOnConflict(tablename, null, c, SQLiteDatabase.CONFLICT_REPLACE);
            }
            Log.e("labdata", "labdata " + c.toString() + " " + index);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return index;
    }

    public long saveexamination(ContentValues c, String date, int srno, String tablename) {
        createtablelab(tablename);
        long index = -1;
        try {
            SQLiteDatabase db = getWritableDatabase();
            String searchQuery = "SELECT  * FROM " + tablename + " WHERE " + Date + " LIKE '" + date + "' AND " + Patientid + "=" + srno;
            Cursor cursor = db.rawQuery(searchQuery, null);
            Log.e("query ", "query " + cursor.getCount() + " " + searchQuery);
            Log.e("cursor ", "cursor " + cursor.getColumnName(0));
            if (cursor.moveToNext()) {
                do {
                    String s = cursor.getString(cursor.getColumnIndex(JSONDATA));
                    JSONArray arrayprev = new JSONArray(s);

                    JSONArray array1 = new JSONArray(c.getAsString(JSONDATA));

                    Log.e("obj", "arrayprev" + arrayprev.toString());
                    Log.e("obj", "obj" + array1.toString());
                    JSONObject obj = new JSONObject(arrayprev.getString(0));

                    for (int i = 0; i < array1.length(); i++) {
                        JSONObject obj2 = new JSONObject(array1.getString(i));

                        JSONArray chiefcomplaints = new JSONArray(obj2.getString("chiefcomplaints"));
                        JSONArray prevarray = new JSONArray(obj.getString("chiefcomplaints"));
                        for (int j = 0; j < chiefcomplaints.length(); j++) {

                            prevarray.put(chiefcomplaints.getJSONObject(j));
                        }
                        obj.put("chiefcomplaints", prevarray);

                        JSONArray symptoms = new JSONArray(obj2.getString("symptoms"));
                        JSONArray symptomsaray = new JSONArray(obj.getString("symptoms"));
                        for (int j = 0; j < symptoms.length(); j++) {

                            symptomsaray.put(symptoms.getJSONObject(j));
                        }
                        obj.put("symptoms", symptomsaray);

                        JSONArray vaccines = new JSONArray(obj2.getString("vaccines"));
                        JSONArray vaccinesarray = new JSONArray(obj.getString("vaccines"));
                        for (int j = 0; j < vaccines.length(); j++) {
                            vaccinesarray.put(vaccines.getJSONObject(j));
                        }
                        obj.put("vaccines", vaccinesarray);

                        JSONArray allergy = new JSONArray(obj2.getString("allergy"));
                        JSONArray allergyarray = new JSONArray(obj.getString("allergy"));
                        for (int j = 0; j < allergy.length(); j++) {
                            allergyarray.put(allergy.getJSONObject(j));
                        }
                        obj.put("allergy", allergyarray);

                        JSONArray diagnosis = new JSONArray(obj2.getString("diagnosis"));
                        JSONArray diagnosisarray = new JSONArray(obj.getString("diagnosis"));
                        for (int j = 0; j < diagnosis.length(); j++) {

                            diagnosisarray.put(diagnosis.getJSONObject(j));
                        }
                        obj.put("diagnosis", diagnosisarray);
/*
                        JSONArray symptoms = new JSONArray(obj2.getString("symptoms"));
                        for (int j = 0; j < symptoms.length(); j++) {

                            new JSONArray(obj.getString("symptoms")).put(symptoms.getJSONObject(j));
                        }

                        JSONArray vaccines = new JSONArray(obj2.getString("vaccines"));
                        for (int j = 0; j < vaccines.length(); j++) {

                            new JSONArray(obj.getString("vaccines")).put(vaccines.getJSONObject(j));
                        }

                        JSONArray allergy = new JSONArray(obj2.getString("allergy"));
                        for (int j = 0; j < allergy.length(); j++) {

                            new JSONArray(obj.getString("allergy")).put(allergy.getJSONObject(j));
                        }

                        JSONArray diagnosis = new JSONArray(obj2.getString("diagnosis"));
                        for (int j = 0; j < diagnosis.length(); j++) {

                            new JSONArray(obj.getString("diagnosis")).put(diagnosis.getJSONObject(j));
                        }*/
                        Log.e("arrayprev ", "arrayprev " + arrayprev.toString());
                        Log.e("array ", "array " + array1.toString());
                        Log.e("array ", "obj " + obj.toString());
                     /*   JSONArray symptoms = obj.getJSONArray("symptoms");
                        for (int j = 0; j < symptoms.length(); j++) {
                            new JSONArray(array.getJSONObject(0).getString("symptoms")).put(symptoms.getJSONObject(j));
                        }

                        JSONArray vaccines = obj.getJSONArray("vaccines");
                        for (int j = 0; j < vaccines.length(); j++) {
                            new JSONArray(array.getJSONObject(0).getString("vaccines")).put(vaccines.getJSONObject(j));
                        }

                        JSONArray allergy = obj.getJSONArray("allergy");
                        for (int j = 0; j < allergy.length(); j++) {
                            new JSONArray(array.getJSONObject(0).getString("allergy")).put(allergy.getJSONObject(j));
                        }

                        JSONArray diagnosis = obj.getJSONArray("diagnosis");
                        for (int j = 0; j < diagnosis.length(); j++) {
                            new JSONArray(array.getJSONObject(0).getString("diagnosis")).put(chiefcomplaints.getJSONObject(j));
                        }*/
                    }
                    JSONArray arraynew = new JSONArray();
                    arraynew.put(obj.toString());
                    c.put(JSONDATA, arraynew.toString());
                    db.update(tablename, c, Date + " Like ? AND " + Patientid + " =?", new String[]{date, Integer.toString(srno)});
                    Log.e("in if ", "in if");
                } while (cursor.moveToNext());
            } else {
                index = db.insertWithOnConflict(tablename, null, c, SQLiteDatabase.CONFLICT_REPLACE);
                Log.e("in else ", "in else " + index);
            }
            //  Log.e("id ", "id " + index + " " + c.getAsString(Date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return index;
    }

    public JSONArray getlabdata(int srno, String tablename) {
        createtablelab(tablename);
        SQLiteDatabase db = getWritableDatabase();
        String searchQuery = "SELECT  * FROM " + tablename + " where " + Patientid + " = " + srno;
        Cursor cursor = db.rawQuery(searchQuery, null);
        Log.e("query", "query " + searchQuery);
        JSONArray resultSet = new JSONArray();

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();
            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {
                        if (cursor.getString(i) != null) {
                            Log.d("TAG_NAME", cursor.getString(i));
                            rowObject.put(cursor.getColumnName(i), cursor.getString(i));
                        } else {
                            rowObject.put(cursor.getColumnName(i), "");
                        }
                    } catch (Exception e) {
                        Log.d("TAG_NAME", e.getMessage());
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        Log.e("TAG_NAME", resultSet.toString());
        return resultSet;
    }

    public void createTablemstexamination() {
        SQLiteDatabase db = getWritableDatabase();
        String query_mstexamination = "CREATE TABLE IF NOT EXISTS " + TABLE_MSTEXAMINATION + "("
                + CATEGORYID + " INTEGER PRIMARY KEY AUTOINCREMENT," + NAME + " TEXT," + SUBCATEGORYID + " INTEGER,"
                + SORTORDER + " INTEGER," + DOCTORID + " INTEGER," + AMOUNT + " TEXT," + CODE + " TEXT," + PRACTICEID + " INTEGER," + PARENTID + " INTEGER,"
                + TYPE + " TEXT," + ABBR + " TEXT," + SAVEDTIME + " TEXT)";
        db.execSQL(query_mstexamination);
    }

    public void createtablepatient() {
        SQLiteDatabase db = getWritableDatabase();
        String querytpatient = "CREATE TABLE IF NOT EXISTS " + PATIENT + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Srno + " TEXT," + Patientid + " INTEGER," + ServerPatientid + " INTEGER," + Title + " TEXT," + FirstName
                + " TEXT," + FName + " TEXT,"
                + MiddleName + " TEXT," + LastName + " TEXT," + Address + " TEXT," + CountryId + " INTEGER," + StateId + " INTEGER," + CityId + " INTEGER,"
                + Area + " TEXT," + DOB + " TEXT," + Gender + " TEXT," + PinCode + " TEXT," + Mobile + " TEXT," + AlternateMobile + " TEXT," + EmailId + " TEXT,"
                + AlternateEmailId + " TEXT," + BloodGroup + " TEXT," + Customerid + " TEXT," + create_date + " TEXT," + update_date + " TEXT," + deleted + " TEXT,"
                + providerid + " INTEGER," + Assistantid + " INTEGER," + password + " TEXT," + IsActive + " INTEGER," + Department + " TEXT," + Language + " TEXT,"
                + Religion + " TEXT," + BillingIn + " TEXT," + Phone + " TEXT," + Occupation + " TEXT," + EmployeeNo + " TEXT," + EmployeeName + " TEXT,"
                + DateOfjoining + " TEXT," + Remark + " TEXT," + Tags + " TEXT," + ReferFrom + " TEXT," + ReferTo + " TEXT," + profileimage + " TEXT," + Height + " TEXT,"
                + aadharno + " TEXT," + pattype + " TEXT," + Maritalstatus + " TEXT," + WeddingDate + " TEXT," + panno + " TEXT," + SYNC + " INTEGER," + Age + " TEXT,"
                + AgeYear + " TEXT," + Aptid + " TEXT," + DESCRIPTION + " TEXT," + Tel1 + " TEXT," + UID + " TEXT," + PatientContactGroup + " TEXT,"+ AgeMonth + " TEXT)";
        db.execSQL(querytpatient);
    }

    public void createtableprescription() {
        SQLiteDatabase db = getWritableDatabase();
        String query_prescription = "CREATE TABLE IF NOT EXISTS " + prescription + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Patientid + " INTEGER," + Date + " TEXT,"
                + DOCTORID + " INTEGER," + JSONDATA + " TEXT," + SAVEDTIME + " TEXT)";
        db.execSQL(query_prescription);
    }

    public long saveprescription(ContentValues c, int id, int srno) {
        createtableprescription();
        long index = -1;
        try {
            SQLiteDatabase db = getWritableDatabase();
            Log.e("id ", "id " + id + " " + c.getAsString(Date));
            try {
                if (id != -1) {
                    db.update(prescription, c, ID + " =? AND " + Patientid + " =?", new String[]{Integer.toString(id), Integer.toString(srno)});
                } else
                    index = db.insertWithOnConflict(prescription, null, c, SQLiteDatabase.CONFLICT_REPLACE);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return index;
    }

    public JSONArray getprescriptiondata(int srno) {
        createtableprescription();
        SQLiteDatabase db = getWritableDatabase();
        String searchQuery = "SELECT  * FROM " + prescription + " where " + Patientid + " = " + srno;
        Cursor cursor = db.rawQuery(searchQuery, null);
        JSONArray resultSet = new JSONArray();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();
            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {
                        if (cursor.getString(i) != null) {
                            Log.d("TAG_NAME", cursor.getString(i));
                            rowObject.put(cursor.getColumnName(i), cursor.getString(i));
                        } else {
                            rowObject.put(cursor.getColumnName(i), "");
                        }
                    } catch (Exception e) {
                        Log.d("TAG_NAME", e.getMessage());
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
      //  Log.e("TAG_NAME", resultSet.toString());
        return resultSet;
    }

    public void createtableappointment() {
        SQLiteDatabase db = getWritableDatabase();
        String querytappointment = "CREATE TABLE IF NOT EXISTS " + appointment + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + title + " TEXT," + MiddleName + " TEXT," + LastName + " TEXT," + Area + " TEXT,"
                + mobile + " TEXT," + EmailId + " TEXT," + address + " TEXT," + startDate + " TEXT," + endDate + " TEXT," + purpose + " TEXT,"
                + DoctorId + " TEXT," + type + " TEXT," + CatagoryId + " TEXT," + PracticeId + " TEXT," + AppoinmentSource + " TEXT," + AppoinmentType + " TEXT,"
                + Titl + " TEXT," + Patientid + " TEXT," + DBPatientid + " TEXT," + SYNC + " INTEGER," + docname + " TEXT," + description + " TEXT," + Aptid + " TEXT,"
                + status + " TEXT," +aptdate+" TEXT,"+starttime+" TEXT," + SAVEDTIME + " TEXT)";
        db.execSQL(querytappointment);
    }

    public void createtableeyespeciality() {
        SQLiteDatabase db = getWritableDatabase();
        String query_eyedetailsdata = "CREATE TABLE IF NOT EXISTS " + TABLE_EYESPECIALISTDATA + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + JSONDATA + " TEXT," + Patientid + " INTEGER,"
                + DOCTORID + " INTEGER," + PRACTICEID + " INTEGER,"
                + SAVEDTIME + " TEXT)";
        db.execSQL(query_eyedetailsdata);
    }

    public long saveeyespecialitydata(ContentValues c) {
        createtableeyespeciality();
        long index = -1;
        try {
            SQLiteDatabase db = getWritableDatabase();
            index = db.insertWithOnConflict(TABLE_EYESPECIALISTDATA, null, c, SQLiteDatabase.CONFLICT_REPLACE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return index;
    }

    public long updateeyespecialitydata(ContentValues c, long id) {
        createtableeyespeciality();
        long index = -1;
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.update(TABLE_EYESPECIALISTDATA, c, ID + " =?", new String[]{Long.toString(id)});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return index;
    }

    public ArrayList<String> geteyespecialitydata(int patientid) {
        createtableeyespeciality();
        ArrayList<String> mdataset = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String searchQuery = "SELECT  * FROM " + TABLE_EYESPECIALISTDATA + " WHERE " + Patientid + " = " + patientid;
        Cursor cursor = db.rawQuery(searchQuery, null);
        if (cursor.moveToFirst()) {
            do {
                try {
                    if (cursor.getCount() > 0) {
                        JSONObject obj = new JSONObject(cursor.getString(cursor.getColumnIndex(JSONDATA)));
                        obj.put("id", cursor.getLong(0));
                        obj.put("Date", cursor.getString(cursor.getColumnIndex(SAVEDTIME)));
                        mdataset.add(obj.toString());
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        return mdataset;
    }

    public String geteyespecialitydatabyid(long id) {
        createtableeyespeciality();
        SQLiteDatabase db = getWritableDatabase();
        String searchQuery = "SELECT  * FROM " + TABLE_EYESPECIALISTDATA + " WHERE " + ID + " = " + id;
        Cursor cursor = db.rawQuery(searchQuery, null);
        if (cursor.moveToFirst()) {
            do {
                try {
                    if (cursor.getCount() > 0) {
                        JSONObject obj = new JSONObject(cursor.getString(cursor.getColumnIndex(JSONDATA)));
                        obj.put("id", cursor.getLong(0));
                        Log.e("data ", "data " + obj.toString());
                        return obj.toString();
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        return "";
    }

    public long savepatient(ContentValues c) {
        createtablepatient();
        long index = 0;
        try {
            SQLiteDatabase db = getWritableDatabase();
            index = db.insertWithOnConflict(PATIENT, null, c,
                    SQLiteDatabase.CONFLICT_REPLACE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return index;
    }


    public long saveappointment(ContentValues c) {
        createtableappointment();
        long index = 0;
        try {
            SQLiteDatabase db = getWritableDatabase();
            index = db.insertWithOnConflict(appointment, null, c,
                    SQLiteDatabase.CONFLICT_REPLACE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return index;
    }

    public long saveallappointment(ArrayList<Appointmentdatav1> data, String ptid, String dbid) {
        createtableappointment();
        long index = 0;
        try {
            SQLiteDatabase db = getWritableDatabase();
            String  query ="DELETE FROM " + appointment + " WHERE "+DBPatientid+" = " + dbid + " AND " + SYNC + "=" + 1;
            Log.e("query ","query "+query);
            db.execSQL("DELETE FROM " + appointment + " WHERE "+DBPatientid+" = " + dbid + " AND " + SYNC + "=" + 1);
            for (int i = 0; i < data.size(); i++) {
                ContentValues c = new ContentValues();
                c.put(DatabaseHelper.Aptid, data.get(i).getAptid());
                c.put(DatabaseHelper.docname, data.get(i).getDocname());
                c.put(DatabaseHelper.title, data.get(i).getFirstName());
                c.put(DatabaseHelper.startDate, data.get(i).getAptdate());
                c.put(DatabaseHelper.status, data.get(i).getStatusmsg());
                c.put(DatabaseHelper.purpose, data.get(i).getPurpose());
                c.put(DatabaseHelper.DESCRIPTION, data.get(i).getDescription());
                c.put(DatabaseHelper.SAVEDTIME, DateUtils.getSqliteTime());
                c.put(Patientid, ptid);
                c.put(DBPatientid, dbid);
               c.put(SYNC,1);

                index = db.insertWithOnConflict(appointment, null, c,
                        SQLiteDatabase.CONFLICT_REPLACE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return index;
    }

    public long isexist(String name) {
        createtablepatient();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(PATIENT, new String[]{Srno},
                FirstName + " LIKE ?", new String[]{"" + name + ""},
                null, null, null);

        if (cursor.moveToFirst()) {
            do {
                try {
                    if (cursor.getCount() > 0) {
                        return cursor.getLong(0);
                    } else
                        return -1;
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        return -1;
    }

    public JSONArray getptdata() {
        createtablepatient();
        SQLiteDatabase db = getWritableDatabase();
        String searchQuery = "SELECT  * FROM " + PATIENT;
        Cursor cursor = db.rawQuery(searchQuery, null);
        JSONArray resultSet = convertcursorvaltoJSOnArray(cursor);

       /* cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {
                        if (cursor.getString(i) != null) {
                            Log.d("TAG_NAME", cursor.getString(i));
                            rowObject.put(cursor.getColumnName(i), cursor.getString(i));
                        } else {
                            rowObject.put(cursor.getColumnName(i), "");
                        }
                    } catch (Exception e) {
                        Log.d("TAG_NAME", e.getMessage());
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        Log.e("TAG_NAME", resultSet.toString());*/
        return resultSet;
    }

    public JSONArray getptdata(long ptid) {
        createtablepatient();
        SQLiteDatabase db = getWritableDatabase();
        String searchQuery = "SELECT  * FROM " + PATIENT + " WHERE " + Srno + " = " + ptid;
        Cursor cursor = db.rawQuery(searchQuery, null);
        JSONArray resultSet = new JSONArray();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();
            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {
                        if (cursor.getString(i) != null) {
                            Log.d("TAG_NAME", cursor.getString(i));
                            rowObject.put(cursor.getColumnName(i), cursor.getString(i));
                        } else {
                            rowObject.put(cursor.getColumnName(i), "");
                        }
                    } catch (Exception e) {
                        Log.d("TAG_NAME", e.getMessage());
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        Log.e("TAG_NAME", resultSet.toString());
        return resultSet;
    }

    public ArrayList<Patient> getfilteredaptdata(String searchkey) {
        createtablepatient();
        JSONArray resultSet = new JSONArray();
        ArrayList<Patient> mdataset = new ArrayList<>();
        String[] data = searchkey.split(" ");
        String query = "";
      /*  for (int i = 0; i < data.length; i++) {
            if (query.length() > 0)
                query = query + " OR " + FirstName + " LIKE '%" + data[i] + "%'";
            else {
                query = FirstName + " LIKE '%" + data[i] + "%'";
            }
    }*/

        SQLiteDatabase db = getWritableDatabase();
        for (int i = 0; i < data.length; i++) {
            String searchQuery = "SELECT  * FROM " + PATIENT + " WHERE " + FirstName + " LIKE '%" + data[i] + "%' OR " + Mobile + " LIKE '%" + data[i] + "%' OR " + Address + " LIKE '%" + data[i] + "%'";
            Cursor cursor = db.rawQuery(searchQuery, null);
            resultSet = convertcursorvaltoJSOnArray(cursor);
            if (resultSet.length() > 0) {
                Gson gson = new Gson();
                java.lang.reflect.Type type = new TypeToken<ArrayList<Patient>>() {
                }.getType();
                ArrayList<Patient> ptdata = gson.fromJson(resultSet.toString(), type);
                if (ptdata != null) {
                    mdataset.addAll(ptdata);
                }
                //     Log.e("mdataset","mdataset "+mdataset.size());
            }
            cursor.close();
        }

        Log.e("TAG_NAME", resultSet.toString());
        return mdataset;
    }

    public JSONArray getaptdata(String date) {
        createtableappointment();
        SQLiteDatabase db = getWritableDatabase();
        String searchQuery = "SELECT  * FROM " + appointment+ " WHERE " + aptdate + " LIKE '" + date + "' ORDER BY " + starttime + " ASC";
        Log.e("query ", "query " + searchQuery);
        Cursor cursor = db.rawQuery(searchQuery, null);

        JSONArray resultSet = new JSONArray();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();
            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {
                        if (cursor.getString(i) != null) {
                            Log.d("TAG_NAME", cursor.getString(i));
                            rowObject.put(cursor.getColumnName(i), cursor.getString(i));
                        } else {
                            rowObject.put(cursor.getColumnName(i), "");
                        }
                    } catch (Exception e) {
                     //   Log.d("TAG_NAME", e.getMessage());
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        Log.e("TAG_NAME", resultSet.toString());
        return resultSet;
    }

    public JSONArray getaptdata(int patientid) {
        createtableappointment();
        SQLiteDatabase db = getWritableDatabase();
        String searchQuery = "SELECT  * FROM " + appointment + " WHERE " + DBPatientid + "=" + patientid;
        Log.e("query ", "query " + searchQuery);
        Cursor cursor = db.rawQuery(searchQuery, null);

        JSONArray resultSet = new JSONArray();
        cursor.moveToFirst();
        Log.e("data ", "data " + cursor.getCount());
        resultSet = convertcursorvaltoJSOnArray(cursor);
        cursor.close();
        return resultSet;
    }

    public JSONArray getaptdata(long id) {
        createtableappointment();
        SQLiteDatabase db = getWritableDatabase();
        String searchQuery = "SELECT  * FROM " + appointment + " WHERE " + ID + "=" + id;
        Log.e("query ", "query " + searchQuery);
        Cursor cursor = db.rawQuery(searchQuery, null);

        JSONArray resultSet = new JSONArray();
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();
            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {
                        if (cursor.getString(i) != null) {
                            Log.d("TAG_NAME", cursor.getString(i));
                            rowObject.put(cursor.getColumnName(i), cursor.getString(i));
                        } else {
                            rowObject.put(cursor.getColumnName(i), "");
                        }
                    } catch (Exception e) {
                        Log.d("TAG_NAME", e.getMessage());
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        Log.e("TAG_NAME", resultSet.toString());
        return resultSet;
    }

    public long savemstexamination(final int subcategoryid, final String name) {
        long index = 0;
        try {
            createTablemstexamination();
            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor = db.query(TABLE_MSTEXAMINATION, new String[]{CATEGORYID},
                    NAME + " LIKE ? And " + SUBCATEGORYID + "=?", new String[]{"" + name + "", Integer.toString(subcategoryid)},
                    null, null, null);
            //String query="Select * from "+TABLE_MSTEXAMINATION+" where "+NAME+" like %'"+name+"'% AND "+SUBCATEGORYID;
            Log.e("query ", "query " + name + " " + subcategoryid);
            try {
                Log.e("count", "count " + cursor.getCount());
                if (cursor.getCount() > 0) {
                    Log.e("allready exist ", "inserted " + index);
                    return -1;
                } else {
                    ContentValues c = new ContentValues();
                    c.put(SUBCATEGORYID, subcategoryid);
                    c.put(NAME, name);
                    c.put(SORTORDER, 0);
                    c.put(DOCTORID, 0);
                    c.put(AMOUNT, "0");
                    c.put(CODE, "0");
                    c.put(PRACTICEID, 0);
                    c.put(PARENTID, 0);
                    c.put(TYPE, "0");
                    c.put(ABBR, "0");
                    c.put(SAVEDTIME, DateUtils.getSqliteTime());
                    index = db.insertWithOnConflict(TABLE_MSTEXAMINATION, null, c,
                            SQLiteDatabase.CONFLICT_REPLACE);
                    //  Log.e("insereted111111 ", "inserted " + index);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
         /*   Log.e("cursor111111111 ", "cursor " + cursor.getColumnName(0));
            if (cursor.moveToN) {
                do {
                    try {
                        if (cursor.getCount() > 0) {
                            Log.e("allready exist ","inserted "+index);

                            return cursor.getLong(0);
                        } else {
                            ContentValues c = new ContentValues();
                            c.put(SUBCATEGORYID, subcategoryid);
                            c.put(NAME, name);
                            c.put(SORTORDER, 0);
                            c.put(DOCTORID, 0);
                            c.put(AMOUNT, "0");
                            c.put(CODE, "0");
                            c.put(PRACTICEID, 0);
                            c.put(PARENTID, 0);
                            c.put(TYPE, "0");
                            c.put(ABBR, "0");
                            c.put(SAVEDTIME, DateUtils.getSqliteTime());
                            index = db.insertWithOnConflict(TABLE_MSTEXAMINATION, null, c,
                                    SQLiteDatabase.CONFLICT_REPLACE);
                            Log.e("insereted111111 ","inserted "+index);
                        }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } while (cursor.moveToNext());
            }else {
                ContentValues c = new ContentValues();
                c.put(SUBCATEGORYID, subcategoryid);
                c.put(NAME, name);
                c.put(SORTORDER, 0);
                c.put(DOCTORID, 0);
                c.put(AMOUNT, "0");
                c.put(CODE, "0");
                c.put(PRACTICEID, 0);
                c.put(PARENTID, 0);
                c.put(TYPE, "0");
                c.put(ABBR, "0");
                c.put(SAVEDTIME, DateUtils.getSqliteTime());
                index = db.insertWithOnConflict(TABLE_MSTEXAMINATION, null, c,
                        SQLiteDatabase.CONFLICT_REPLACE);
                Log.e("insereted222222 ","inserted "+index);
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        return index;
    }

    public long savemstexamination(final int subcategoryid, final String name, final String amount) {
        long index = 0;
        try {
            createTablemstexamination();
            SQLiteDatabase db = getWritableDatabase();
            ContentValues c = new ContentValues();
            c.put(SUBCATEGORYID, subcategoryid);
            c.put(NAME, name);
            c.put(SORTORDER, 0);
            c.put(DOCTORID, 0);
            c.put(AMOUNT, amount);
            c.put(CODE, "0");
            c.put(PRACTICEID, 0);
            c.put(PARENTID, 0);
            c.put(TYPE, "0");
            c.put(ABBR, "0");
            c.put(SAVEDTIME, DateUtils.getSqliteTime());
            index = db.insertWithOnConflict(TABLE_MSTEXAMINATION, null, c,
                    SQLiteDatabase.CONFLICT_REPLACE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return index;
    }

    public ArrayList<MasterData> getmstexamination(int catid) {
        ArrayList<MasterData> masterData = new ArrayList<>();
        try {
            SQLiteDatabase db = getWritableDatabase();
            createTablemstexamination();
            String query = "select * from " + TABLE_MSTEXAMINATION;// + " WHERE " + SUBCATEGORYID + " = " + catid;
            Cursor cursor = db.rawQuery(query, null);
            JSONArray array = convertcursorvaltoJSOnArray(cursor);
            Log.e("arra ", "array " + array.toString());
            for (int i = 0; i < array.length(); i++) {
                masterData.add(new MasterData(array.getJSONObject(i)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return masterData;
    }

    public JSONArray getallmstexamination() {
        createcustommastertable();
        SQLiteDatabase db = getWritableDatabase();
        createTablemstexamination();
        String query = "select * from " + TABLE_MSTEXAMINATION;
        Cursor cursor = db.rawQuery(query, null);
        return convertcursorvaltoJSOnArray(cursor);
    }

    public JSONArray convertcursorvaltoJSOnArray(Cursor cursor) {
        JSONArray resultSet = new JSONArray();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();
            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {
                        if (cursor.getString(i) != null) {
                            Log.d("TAG_NAME", cursor.getString(i));
                            rowObject.put(cursor.getColumnName(i), cursor.getString(i));
                        } else {
                            rowObject.put(cursor.getColumnName(i), "");
                        }
                    } catch (Exception e) {
                        Log.d("TAG_NAME", e.getMessage());
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        Log.e("data ", resultSet.toString());
        return resultSet;
    }


    public int getId(String name) {
        int id = -1;
        SQLiteDatabase db = getWritableDatabase();
        createTablemstexamination();
        String query = "Select * from " + TABLE_MSTEXAMINATION + " where " + NAME + "='" + name + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            id = cursor.getInt(0);
        }
        return id;
    }

    public void createtable(String tablename) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            String query = "CREATE TABLE IF NOT EXISTS " + tablename + "("
                    + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + JSONDATA
                    + " TEXT," + DELETED + " INTEGER," + SAVEDTIME + " TEXT)";
            db.execSQL(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletebyid(final String tableName, Long id) {
        try {
            createtable(tableName);
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("DELETE FROM " + tableName + " WHERE ID = " + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

    public void delete(final String tableName) {
        try {
            createtable(tableName);
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("DELETE FROM " + tableName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(final long deleteid) {
        try {
            createtableeyespeciality();
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("DELETE FROM " + TABLE_EYESPECIALISTDATA + " WHERE " + ID + " = " + deleteid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void savemasterdata(String key, String data) {
        createmastertable();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(JSONDATA, data);
        c.put(MASTER_KEY, key);
        long id = db.insertWithOnConflict(TABLE_MASTER, null, c, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public String getMasterdata(String mastername) {
        String data = "";
        try {
            SQLiteDatabase db = getWritableDatabase();
            String query = "SELECT * FROM " + TABLE_MASTER + " WHERE " + MASTER_KEY + " == '" + mastername + "'";
            Cursor c = db.rawQuery(query, null);
            JSONArray array = convertcursorvaltoJSOnArray(c);
            if (array.length() > 0) {
                data = array.getJSONObject(0).getString("jsondata");
                if (mastername.equalsIgnoreCase("Lab") || mastername.equalsIgnoreCase("Test"))
                    Log.e("sssssssss", "sssss" + mastername + " " + data);
            }
            JSONArray array1 = new JSONArray(data);
            Log.e("mastername ", "" + mastername + " " + array1.length());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public long getsize() {
        File dbpath = mcontext.getDatabasePath(DATABASE_NAME);
        SQLiteDatabase db = getWritableDatabase();
        Log.e("dbpath ","dbpath "+dbpath+ "  "+db.getPath());
        return new File(db.getPath()).length();
    }

}
