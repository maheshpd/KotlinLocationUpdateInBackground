package com.healthbank;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.healthbank.adapter.CombinedDataAdapter;
import com.healthbank.adapter.DoctorAdapter;
import com.healthbank.adapter.DrugAdapter;
import com.healthbank.adapter.FileAdapter;
import com.healthbank.adapter.MasterAdapter;
import com.healthbank.classes.Combineddata;
import com.healthbank.classes.Connection;
import com.healthbank.classes.Doctor;
import com.healthbank.classes.Drug;
import com.healthbank.classes.FileData;
import com.healthbank.classes.Group;
import com.healthbank.classes.Master;
import com.healthbank.classes.Option;
import com.healthbank.classes.OptionAnswer;
import com.healthbank.classes.Questions;
import com.healthbank.classes.SaveAnswerdata;
import com.healthbank.classes.SubGroups;
import com.healthbank.classes.Template;
import com.healthbank.classes.TestAdapter;
import com.healthbank.classes.Visits;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class TemplateQuestionActivity extends ActivityCommon {
    Spinner sp1, sp2;
    ArrayList<Template> mdataset;
    ArrayAdapter<Template> adapter;
    ArrayAdapter<Visits> adaptervisit;
    LinearLayout mLayoutmanager;
    LinearLayout layout;
    ScrollView scrollView;
    ArrayList<Drug> prescriptiondata;
    DrugAdapter drugadapter;
    ArrayList<FileData> filedata;
    FileAdapter fileadapter;
    Button bt1;
    Group group;
    ArrayList<Group> grouparray = new ArrayList<>();
    String visitid = "0";
    int dbvisitid = 0;
    String visitdate = "";
    ArrayList<Visits> visitdata = new ArrayList<>();
    LinearLayout layout2;
    Button bt2, bt3;
    int fontsize = 16;
    Typeface typeface;
    int padding = 10;
    ImageView img1;
    boolean isrefill = false;
    int currentdbvisitid = 0;
    boolean isprint = false;
    int type = 0;
    String cat = "";
    int popupheight = 600;
    JSONObject answerobjdata = new JSONObject();
    RecyclerView mRecyclerviewdrug;
    RecyclerView mRecyclerviewtest;
    RecyclerView mRecyclerviewdiagnosis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_question);
//        setContentView(R.layout.activity_template_question);
        setmaterialDesign();
        setTitle(GlobalValues.selectedpt.getFirstName());
        back();
        attachUI();
    }

    public String gettodaysdate() {
        Calendar cal = Calendar.getInstance();
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        return sdf.format(cal.getTime());
    }

    private void attachUI() {
        typeface = Fonter.getTypefaceregular(TemplateQuestionActivity.this);
        Bundle b = getIntent().getExtras();
        visitdate = gettodaysdate();
        visitdate = gettodaysdate();
        if (b != null) {
            visitid = b.getString("visitid");
            visitdate = b.getString("visitdate");

            if (b.containsKey("category")) {
                cat = b.getString("category");
            }
        }

        filedata = new ArrayList<>();
        prescriptiondata = new ArrayList<>();
        sp1 = findViewById(R.id.spinner_1);
        sp2 = findViewById(R.id.spinner_2);
        scrollView = findViewById(R.id.scrollview);
        layout = findViewById(R.id.layout_1);
        layout2 = findViewById(R.id.layout_2);
        bt1 = findViewById(R.id.button_1);
        bt2 = findViewById(R.id.button_2);
        bt3 = findViewById(R.id.button_3);

        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    isrefill = true;
                    currentdbvisitid = dbvisitid;
                    Calendar cal = Calendar.getInstance();
                    String myFormat = "yyyy-MM-dd"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    JSONObject obj = new JSONObject();
                    obj.put("VisitDate", sdf.format(cal.getTime()));
                    obj.put("Patientid", GlobalValues.selectedpt.getPatientid());
                    obj.put("UserId", GlobalValues.docid);
                    obj.put("practiceid", GlobalValues.branchid);
                    obj.put("connectionid", Constants.projectid);
                    if (InternetUtils.getInstance(TemplateQuestionActivity.this).available()) {
                        ConnectionManager.getInstance(TemplateQuestionActivity.this).AddVisit(obj.toString(), 0);
                    } else {
                        addcurrentvisit(Calendar.getInstance());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        img1 = findViewById(R.id.imageview_1);
        mdataset = new ArrayList<>();
        adapter = new ArrayAdapter<Template>(this, android.R.layout.simple_spinner_item, mdataset);
        sp1.setAdapter(adapter);
        adaptervisit = new ArrayAdapter<Visits>(this, android.R.layout.simple_spinner_item, visitdata);
        sp2.setAdapter(adaptervisit);

        if (InternetUtils.getInstance(TemplateQuestionActivity.this).available()) {
            ConnectionManager.getInstance(TemplateQuestionActivity.this).getvisits(Integer.toString(GlobalValues.selectedpt.getPatientid()));
        } else {
            try {
                JSONArray array = DatabaseHelper.getInstance(TemplateQuestionActivity.this).getvisitdata(Integer.toString(GlobalValues.selectedpt.getId()), Integer.toString(GlobalValues.selectedpt.getPatientid()));
                if (array.length() > 0) {
                    parsevisit(array, false);
                } else {
                    addcurrentvisit(Calendar.getInstance());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adddata();
            }
        });


        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adddata();
            }
        });

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filedata.size() > 0) {
                    new upload().execute();
                } else {
                    submitdata();
                }
            }
        });

        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    if (visitdata.size() > 0) {
                        grouparray.clear();
                        visitid = "0";
                        dbvisitid = 0;
                        visitdate = gettodaysdate();
                        // if (((Template) parent.getAdapter().getItem(position)).getTemplateName().equalsIgnoreCase("Visit Record")) {
                        layout2.setVisibility(View.VISIBLE);
                        try {
                            if (adaptervisit != null) {
                                if (visitdata.size() > sp2.getSelectedItemPosition()) {
                                    visitid = Integer.toString(visitdata.get(sp2.getSelectedItemPosition()).getVisitid());
                                    dbvisitid = visitdata.get(sp2.getSelectedItemPosition()).getId();
                                    visitdate = DateUtils.parseDateNew(visitdata.get(sp2.getSelectedItemPosition()).getVisitdate(), "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd");
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                      /*  } else {
                            layout2.setVisibility(View.GONE);
                            for (int i = (visitdata.size() - 1); i >= 0; i--) {
                                if (visitdata.get(i).getVisit() == 1) {
                                    visitid = Integer.toString(visitdata.get(i).getVisitid());
                                    dbvisitid = visitdata.get(i).getId();
                                    visitdate = DateUtils.parseDateNew(visitdata.get(i).getVisitdate(), "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd");
                                    break;
                                }
                            }
                        }*/
                        Log.e("dbvisitid ", "dbvisitid " + dbvisitid);
                        refreshdata();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    visitid = Integer.toString(visitdata.get(position).getVisitid());
                    dbvisitid = visitdata.get(position).getId();
                    visitdate = DateUtils.parseDateNew(visitdata.get(position).getVisitdate(), "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd");
                    // if (mdataset.get(sp1.getSelectedItemPosition()).getTemplateName().equalsIgnoreCase("Visit Record")) {
                    layout2.setVisibility(View.VISIBLE);
                    try {
                        if (adaptervisit != null) {
                            if (visitdata.size() > sp2.getSelectedItemPosition()) {
                                visitid = Integer.toString(visitdata.get(sp2.getSelectedItemPosition()).getVisitid());
                                dbvisitid = visitdata.get(sp2.getSelectedItemPosition()).getId();
                                visitdate = DateUtils.parseDateNew(visitdata.get(sp2.getSelectedItemPosition()).getVisitdate(), "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd");
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    /*} else {
                      //  layout2.setVisibility(View.GONE);
                        for (int i = (visitdata.size() - 1); i >= 0; i--) {
                            if (visitdata.get(i).getVisit() == 1) {
                                visitid = Integer.toString(visitdata.get(i).getVisitid());
                                dbvisitid = visitdata.get(i).getId();
                                visitdate = DateUtils.parseDateNew(visitdata.get(i).getVisitdate(), "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd");
                                break;
                            }
                        }
                    }*/
                    //   Log.e("dbvisitid ", "dbvisitid " + dbvisitid);
                    if (isrefill) {
                        try {
                            JSONArray array2 = DatabaseHelper.getInstance(TemplateQuestionActivity.this).gettemplatedata(Integer.toString(currentdbvisitid), Integer.toString(GlobalValues.selectedpt.getId()), Integer.toString(mdataset.get(sp1.getSelectedItemPosition()).getTemplateId()));
                            if (array2.length() > 0) {
                                answerobjdata = new JSONObject(array2.getJSONObject(0).getString("jsondata"));
                                setdata();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else
                        refreshdata();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        JSONArray templatearray = DatabaseHelper.getInstance(TemplateQuestionActivity.this).gettemplatenamedata(cat);
        if (templatearray.length() > 0) {
            parsetemplatenames(templatearray, false);
        }
    }

    public void refreshdata() {
        try {
            if (InternetUtils.getInstance(TemplateQuestionActivity.this).available()) {
                JSONObject obj = new JSONObject();
                obj.put("TemplateId", mdataset.get(sp1.getSelectedItemPosition()).getTemplateId());
                obj.put("patientid", GlobalValues.selectedpt.getPatientid());
                obj.put("VisitNo", visitid);
                obj.put("connectionid", Constants.projectid);
                ConnectionManager.getInstance(TemplateQuestionActivity.this).GetTemplateQuestionAnswer(obj.toString());
            } else {
                JSONArray array2 = DatabaseHelper.getInstance(TemplateQuestionActivity.this).gettemplatedata(Integer.toString(dbvisitid), Integer.toString(GlobalValues.selectedpt.getId()), Integer.toString(mdataset.get(sp1.getSelectedItemPosition()).getTemplateId()));
                if (array2.length() > 0) {
                    answerobjdata = new JSONObject(array2.getJSONObject(0).getString("jsondata"));
                    setdata();
                } else {
                    int tempid = mdataset.get(sp1.getSelectedItemPosition()).getTemplateId();
                    JSONArray array = DatabaseHelper.getInstance(TemplateQuestionActivity.this).getfreshtemplatedata(Integer.toString(tempid));
                    if (array.length() > 0) {
                        answerobjdata = new JSONObject(array.getJSONObject(0).getString("jsondata"));
                        setdata();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void submitdata() {
        JSONArray array = new JSONArray();
        for (int l = 0; l < grouparray.size(); l++) {
            if (group != null) {
                ArrayList<SubGroups> subGroups = grouparray.get(l).getSubGroups();
                for (int i = 0; i < subGroups.size(); i++) {
                    ArrayList<Questions> ques = subGroups.get(i).getQuestions();
                    for (int j = 0; j < ques.size(); j++) {
                        ArrayList<Option> qdata = ques.get(j).getOption();
                        String ans = "";
                        for (int k = 0; k < qdata.size(); k++) {
                            ques.get(j).setOptionType(qdata.get(k).getOptionType());
                            if (qdata.get(k).getOptionType().equalsIgnoreCase("CustomAutoComplete") || qdata.get(k).getOptionType().equalsIgnoreCase("AutoComplete") || qdata.get(k).getOptionType().equalsIgnoreCase("Life Style")) {
                                try {
                                    String ansdata = qdata.get(k).getOptionValue();
                                    Gson gson = new Gson();
                                    Type type = new TypeToken<ArrayList<Master>>() {
                                    }.getType();
                                    ArrayList<Master> mdataset = gson.fromJson(ansdata, type);
                                    String data = "";
                                    if (mdataset != null) {
                                        for (int m = 0; m < mdataset.size(); m++) {
                                            if (data.length() > 0)
                                                data = data + "," + mdataset.get(m).getName();
                                            else
                                                data = mdataset.get(m).getName();
                                        }
                                    }

                                    if (data.length() > 0)
                                        ans = qdata.get(k).getOptionId() + "^" + data;

                                    Log.e("answer ", "answerauto " + ans);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else if (qdata.get(k).getOptionType().equalsIgnoreCase("CheckBox")) {
                                if (qdata.get(k).isIsselected()) {
                                    if (ans.length() == 0)
                                        ans = qdata.get(k).getOptionId() + "^" + qdata.get(k).getOptionName();
                                    else
                                        ans = ans + "~" + qdata.get(k).getOptionId() + "^" + qdata.get(k).getOptionName();
                                }
                            } else if (qdata.get(k).getOptionType().equalsIgnoreCase("FileUpload")) {
                                String data = "";
                                if (filedata.size() > 0) {

                                    for (int m = 0; m < filedata.size(); m++) {
                                        if (data.length() > 0) {
                                            data = data + "," + filedata.get(m).getFilepath();
                                        } else {
                                            data = filedata.get(m).getFilepath();
                                        }
                                    }
                                    if (data.length() > 0) {
                                        if (ans.length() == 0)
                                            ans = qdata.get(k).getOptionId() + "^" + data;
                                        else
                                            ans = ans + "~" + qdata.get(k).getOptionId() + "^" + data;
                                    }
                                }
                                Log.e("fileupload answer ", "answer " + ans);
                            } else if (qdata.get(k).getOptionType().equalsIgnoreCase("CheckBoxList")) {
                                ArrayList<Option> optdata = ques.get(j).getSuboption();
                                String ansdata = "";
                                for (int m = 0; m < optdata.size(); m++) {
                                    if (optdata.get(m).isIsselected()) {
                                        if (ansdata.length() == 0)
                                            ansdata = optdata.get(m).getOptionName();
                                        else
                                            ansdata = ansdata + "," + optdata.get(m).getOptionName();
                                    }

                                }
                                if (ansdata.length() > 0) {
                                    if (ans.length() == 0)
                                        ans = qdata.get(k).getOptionId() + "^" + ansdata;
                                    else
                                        ans = ans + "~" + qdata.get(k).getOptionId() + "^" + ansdata;
                                }
                            } else {
                                if (ans.length() == 0) {
                                    if (qdata.get(k).getOptionValue().contains("^"))
                                        ans = qdata.get(k).getOptionValue();
                                } else {
                                    if (qdata.get(k).getOptionValue().contains("^"))
                                        ans = ans + "~" + qdata.get(k).getOptionValue();
                                }

                                Log.e("in else ", "in elserd " + ans + " " + qdata.get(k).getOptionType());
                            }

                            if (qdata.get(k).getSubQuestions().size() > 0) {
                                JSONObject obj = getobj(qdata.get(k).getSubQuestions());
                                Log.e("insubq ", "objnew " + obj.toString());
                                array.put(obj);
                            }
                        }
                        try {
                            if (ques.get(j).getOptionType().equalsIgnoreCase("MedicationsWithBodypart") || ques.get(j).getOptionType().equalsIgnoreCase("Medications")) {
                                JSONObject obj = new JSONObject();
                                obj.put("ColumnName", "Que" + ques.get(j).getQid());
                                obj.put("Ans", "PrescriptionControl");
                                obj.put("Qid", ques.get(j).getQid());
                                obj.put("OptionType", ques.get(j).getOptionType());
                                obj.put("XmlObj", "");
                                JSONArray array1 = new JSONArray();
                                Gson gson = new Gson();
                                Type type = new TypeToken<ArrayList<Drug>>() {
                                }.getType();
                                String data = gson.toJson(GlobalValues.drugdata);
                                JSONArray array2 = new JSONArray(data);
                                JSONObject obj1 = new JSONObject();
                                obj1.put("row", array2);
                                obj.put("XmlObj", obj1.toString());
                                array.put(obj);
                            } else if (ques.get(j).getOptionType().equalsIgnoreCase("Test")) {
                                String ansdata = ques.get(j).getAnswer();
                                Gson gson = new Gson();
                                Type type = new TypeToken<ArrayList<Master>>() {
                                }.getType();
                                ArrayList<Master> mdataset = gson.fromJson(ansdata, type);
                                JSONArray testarray = new JSONArray();
                                JSONObject obj1 = new JSONObject();
                                obj1.put("ColumnName", "Que" + ques.get(j).getQid());
                                obj1.put("Ans", "PrescriptionControl");
                                obj1.put("Qid", ques.get(j).getQid());
                                obj1.put("OptionType", ques.get(j).getOptionType());
                                if (mdataset != null)
                                    for (int m = 0; m < mdataset.size(); m++) {
                                        JSONObject obj = new JSONObject();
                                        obj.put("LabId", mdataset.get(m).getLabId());
                                        obj.put("TestId", mdataset.get(m).getTestId());
                                        obj.put("LabName", "");
                                        obj.put("TestName", mdataset.get(m).getName());
                                        obj.put("filepath", mdataset.get(m).getFilepath());
                                        obj.put("Id", "");
                                        testarray.put(obj);
                                    }
                                JSONObject obj2 = new JSONObject();
                                obj2.put("row", testarray);
                                obj1.put("XmlObj", obj2.toString());
                                array.put(obj1);
                                Log.e("obj test ", "objTest " + obj1.toString());
                            } else if (ques.get(j).getOptionType().equalsIgnoreCase("Diagnosis")) {
                            /*    Option opt=new Option();
                                if(ques.get(j).getOption().size()>0)
                                    opt=ques.get(j).getOption().get(0);*/
                                String ansdata = ques.get(j).getAnswer();
                                Log.e("diagnosisi ans ", "" + ques.get(j).getQid() + " " + ansdata);
                                Gson gson = new Gson();
                                Type type = new TypeToken<ArrayList<Master>>() {
                                }.getType();
                                ArrayList<Master> mdataset = gson.fromJson(ansdata, type);
                                JSONArray testarray = new JSONArray();
                                JSONObject obj1 = new JSONObject();
                                obj1.put("ColumnName", "Que" + ques.get(j).getQid());
                                obj1.put("Ans", "PrescriptionControl");
                                obj1.put("Qid", ques.get(j).getQid());
                                obj1.put("OptionType", ques.get(j).getOptionType());
                                if (mdataset != null)
                                    for (int m = 0; m < mdataset.size(); m++) {
                                        JSONObject obj = new JSONObject();
                                        obj.put("DiagnosisId", mdataset.get(m).getCategoryid());
                                        obj.put("DiagnosisName", mdataset.get(m).getName());
                                        obj.put("Id", "0");
                                        obj.put("BodyPart", "");
                                        obj.put("Id", "");
                                        testarray.put(obj);
                                    }
                                JSONObject obj2 = new JSONObject();
                                obj2.put("row", testarray);
                                obj1.put("XmlObj", obj2.toString());
                                array.put(obj1);
                                Log.e("obj diagnosis ", "objdiagmosis " + obj1.toString());
                            } else if (ques.get(j).getOptionType().equalsIgnoreCase("TreatmentProcedure")) {
                            /*    Option opt=new Option();
                                if(ques.get(j).getOption().size()>0)
                                    opt=ques.get(j).getOption().get(0);*/
                                String ansdata = ques.get(j).getAnswer();
                                Log.e("TreatmentProcedure ans ", "" + ques.get(j).getQid() + " " + ansdata);
                                Gson gson = new Gson();
                                Type type = new TypeToken<ArrayList<Master>>() {
                                }.getType();
                                ArrayList<Master> mdataset = gson.fromJson(ansdata, type);
                                JSONArray testarray = new JSONArray();
                                JSONObject obj1 = new JSONObject();
                                obj1.put("ColumnName", "Que" + ques.get(j).getQid());
                                obj1.put("Ans", "PrescriptionControl");
                                obj1.put("Qid", ques.get(j).getQid());
                                obj1.put("OptionType", ques.get(j).getOptionType());
                                if (mdataset != null)
                                    for (int m = 0; m < mdataset.size(); m++) {
                                        JSONObject obj = new JSONObject();
                                        obj.put("TreatmentProcedureId", mdataset.get(m).getCategoryid());
                                        obj.put("TreatmentProcedureName", mdataset.get(m).getName());
                                        obj.put("Id", "0");
                                        testarray.put(obj);
                                    }
                                JSONObject obj2 = new JSONObject();
                                obj2.put("row", testarray);
                                obj1.put("XmlObj", obj2.toString());
                                array.put(obj1);
                                //   Log.e("obj diagnosis ", "objdiagmosis " + obj1.toString());
                            } else if (ques.get(j).getOptionType().equalsIgnoreCase("TestWithLab") || ques.get(j).getOptionType().equalsIgnoreCase("TreatmentProcedureWithBodypart") || ques.get(j).getOptionType().equalsIgnoreCase("DiagnosisWithBodypart")) {
                                try {
                                    String ansdata = ques.get(j).getAnswer();
                                    Log.e("TreatmentProcedure ans ", "" + ques.get(j).getQid() + " " + ansdata);
                                    Gson gson = new Gson();
                                    Type type = new TypeToken<ArrayList<Combineddata>>() {
                                    }.getType();
                                    ArrayList<Combineddata> mdataset = gson.fromJson(ansdata, type);
                                    JSONArray testarray = new JSONArray();
                                    JSONObject obj1 = new JSONObject();
                                    obj1.put("ColumnName", "Que" + ques.get(j).getQid());
                                    obj1.put("Ans", "PrescriptionControl");
                                    obj1.put("Qid", ques.get(j).getQid());
                                    obj1.put("OptionType", ques.get(j).getOptionType());
                                    if (mdataset != null)
                                        for (int m = 0; m < mdataset.size(); m++) {
                                            JSONObject obj = new JSONObject();
                                            if (ques.get(j).getOptionType().equalsIgnoreCase("DiagnosisWithBodypart")) {
                                                obj.put("DiagnosisId", mdataset.get(m).getCatid());
                                                obj.put("BodyPart", mdataset.get(m).getBodypart());
                                                obj.put("DiagnosisName", mdataset.get(m).getName());
                                            } else if (ques.get(j).getOptionType().equalsIgnoreCase("TestWithLab")) {
                                                obj.put("LabId", mdataset.get(m).getCatid());
                                                obj.put("TestName", mdataset.get(m).getBodypart());
                                                obj.put("LabName", mdataset.get(m).getName());
                                            } else if (ques.get(j).getOptionType().equalsIgnoreCase("TreatmentProcedureWithBodypart")) {
                                                obj.put("TreatmentProcedureId", mdataset.get(m).getCatid());
                                                obj.put("BodyPart", mdataset.get(m).getBodypart());
                                                obj.put("TreatmentProcedureName", mdataset.get(m).getName());
                                            }
                                            testarray.put(obj);
                                        }
                                    JSONObject obj2 = new JSONObject();
                                    obj2.put("row", testarray);
                                    obj1.put("XmlObj", obj2.toString());
                                    array.put(obj1);

                                /*    JSONObject objans = new JSONObject(ques.get(j).getOptionValue());
                                    JSONObject obj = new JSONObject();
                                    obj.put("ColumnName", "Que" + ques.get(j).getQid());
                                    obj.put("Ans", "PrescriptionControl");
                                    obj.put("Qid", ques.get(j).getQid());
                                    obj.put("OptionType", ques.get(j).getOptionType());
                                    JSONObject obj1 = new JSONObject();
                                    obj1.put("row", objans);
                                    obj.put("XmlObj", obj1.toString());
                                    array.put(obj);*/
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                JSONObject obj = new JSONObject();
                                obj.put("ColumnName", "Que" + ques.get(j).getQid());
                                obj.put("Ans", ans);
                                obj.put("Qid", ques.get(j).getQid());
                                obj.put("OptionType", ques.get(j).getOptionType());
                                obj.put("XmlObj", "");
                                array.put(obj);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            try {
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(TemplateQuestionActivity.this);
                JSONObject mainobj = new JSONObject();
                Log.e("output ", "output " + visitid + "             " + array.toString());
                mainobj.put("data", array.toString());
                mainobj.put("deptid", pref.getString("DepartmentId", "0"));
                mainobj.put("locid", GlobalValues.branchid);
                mainobj.put("VisitNo", visitid);
                mainobj.put("VisitDate", visitdate);
                mainobj.put("DoctorId", GlobalValues.docid);
                mainobj.put("searchid", GlobalValues.selectedpt.getPatientid());
                mainobj.put("Group", "");
                mainobj.put("TemplateId", mdataset.get(sp1.getSelectedItemPosition()).getTemplateId());
                mainobj.put("TempName", mdataset.get(sp1.getSelectedItemPosition()).getTemplateName());
                mainobj.put("AddmitionNo", 0);
                answerobjdata.put("deptid", pref.getString("DepartmentId", "0"));
                answerobjdata.put("locid", GlobalValues.branchid);
                answerobjdata.put("VisitNo", visitid);
                answerobjdata.put("VisitDate", visitdate);
                answerobjdata.put("DoctorId", GlobalValues.docid);
                answerobjdata.put("searchid", GlobalValues.selectedpt.getPatientid());
                answerobjdata.put("Group", "");
                answerobjdata.put("TemplateId", mdataset.get(sp1.getSelectedItemPosition()).getTemplateId());
                answerobjdata.put("TempName", mdataset.get(sp1.getSelectedItemPosition()).getTemplateName());
                answerobjdata.put("AddmitionNo", 0);
                answerobjdata.put("QuestionData", array);
                Log.e("data", "data " + mainobj.toString());
                JSONArray array1 = answerobjdata.getJSONArray("QuestionData");
                String array2 = mainobj.getString("data");
                // DatabaseHelper.getInstance(TemplateQuestionActivity.this).savetemplatedata(visitid, Integer.toString(GlobalValues.selectedpt.getPatientid()), Integer.toString(GlobalValues.selectedpt.getId()), answerobjdata.toString(), 1, GlobalValues.selectedpt.getId(), dbvisitid, Integer.toString(mdataset.get(sp1.getSelectedItemPosition()).getTemplateId()));

          /*      Log.e("datachk  ", "datachk " + array1.toString());
                Log.e("datachk1", "datachk1" + array2);
                Log.e("savetemplatedata data ", "data " + answerobjdata.toString());
                Log.e("savetemplatedata ", "serverdata " + mainobj.toString());*/
                if (InternetUtils.getInstance(TemplateQuestionActivity.this).available()) {
                    DatabaseHelper.getInstance(TemplateQuestionActivity.this).savetemplatedata(visitid, Integer.toString(GlobalValues.selectedpt.getPatientid()), Integer.toString(GlobalValues.selectedpt.getId()), answerobjdata.toString(), 1, GlobalValues.selectedpt.getId(), dbvisitid, Integer.toString(mdataset.get(sp1.getSelectedItemPosition()).getTemplateId()));
                    ConnectionManager.getInstance(TemplateQuestionActivity.this).saveQuestionTemplates(mainobj.toString(), 0);
                } else {
                    isrefill = false;
                    Toast.makeText(getApplicationContext(), "Data is Offline saved", Toast.LENGTH_LONG).show();
                    DatabaseHelper.getInstance(TemplateQuestionActivity.this).savetemplatedata(visitid, Integer.toString(GlobalValues.selectedpt.getPatientid()), Integer.toString(GlobalValues.selectedpt.getId()), answerobjdata.toString(), 0, GlobalValues.selectedpt.getId(), dbvisitid, Integer.toString(mdataset.get(sp1.getSelectedItemPosition()).getTemplateId()));
                    if (isprint)
                        gotoprint();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setdata() {
        try {
            layout.removeAllViews();
            GlobalValues.drugdata.clear();
            mRecyclerviewtest = null;
            mRecyclerviewdiagnosis = null;
            mRecyclerviewdrug = null;
            grouparray.clear();
            JSONObject answerobj = answerobjdata.optJSONObject("AnswerData");
            if (answerobj != null) {
                group = new Group(answerobj);
                grouparray.add(group);
                if (!group.getGroupName().equalsIgnoreCase("null"))
                    addtextviewheader(group.getGroupName(), 0);
                ArrayList<SubGroups> subGroups = group.getSubGroups();
                //  Log.e("subgroup","subgroup "+subGroups.size());
                for (int i = 0; i < subGroups.size(); i++) {
                    if (!subGroups.get(i).getGroupName().equalsIgnoreCase("null"))
                        addtextviewheader(subGroups.get(i).getGroupName(), 0);
                    ArrayList<Questions> questions = subGroups.get(i).getQuestions();

//                    for (int j = 0; j <questions.size() ; j++) {
//
//                        if (!questions.get(i).getName().equalsIgnoreCase("null"))
//                            addtextviewheader(questions.get(i).getName(),0);
//                    }

                    JSONArray array = new JSONArray();
                    array = answerobjdata.optJSONArray("QuestionData");
                    if (array == null) {
                        array = new JSONArray();
                        JSONObject object = answerobjdata.optJSONObject("QuestionData");
                        if (object != null) {
                            array.put(object);
                        }
                    }
                    addquestions(questions, i, array);
                }
            } else {
                JSONArray array = answerobjdata.optJSONArray("AnswerData");
                if (array != null) {
                    for (int m = 0; m < array.length(); m++) {
                        answerobj = array.getJSONObject(m);
                        group = new Group(answerobj);
                        grouparray.add(group);
                        if (!group.getGroupName().equalsIgnoreCase("null"))
                            addtextviewheader(group.getGroupName(), 0);
                        ArrayList<SubGroups> subGroups = group.getSubGroups();
                        for (int i = 0; i < subGroups.size(); i++) {
                            if (!subGroups.get(i).getGroupName().equalsIgnoreCase("null"))
                                addtextviewheader(subGroups.get(i).getGroupName(), 0);
                            ArrayList<Questions> questions = subGroups.get(i).getQuestions();
                            JSONArray array1 = new JSONArray();
                            array1 = answerobjdata.optJSONArray("QuestionData");
                            if (array1 != null)
                                addquestions(questions, i, array1);
                            else {
                                JSONObject obj1 = answerobjdata.optJSONObject("QuestionData");
                                if (obj1 != null) {
                                    array1 = new JSONArray();
                                    array1.put(obj1);
                                    addquestions(questions, i, array1);
                                }
                            }
                        }
                    }
                }
            }
            if (isrefill)
                submitdata();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JSONObject getobj(ArrayList<Questions> ques) {
        JSONObject obj = new JSONObject();
        for (int j = 0; j < ques.size(); j++) {
            ArrayList<Option> qdata = ques.get(j).getOption();
            String ans = "";
            for (int k = 0; k < qdata.size(); k++) {
                if (qdata.get(k).getOptionType().equalsIgnoreCase("CustomAutoComplete") || qdata.get(k).getOptionType().equalsIgnoreCase("AutoComplete") || qdata.get(k).getOptionType().equalsIgnoreCase("TreatmentProcedure")) {
                    try {
                        String ansdata = qdata.get(k).getOptionValue();
                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<Master>>() {
                        }.getType();
                        ArrayList<Master> mdataset = gson.fromJson(ansdata, type);
                        String data = "";
                        if (mdataset != null) {
                            for (int m = 0; m < mdataset.size(); m++) {
                                if (data.length() > 0)
                                    data = data + "," + mdataset.get(m).getName();
                                else
                                    data = mdataset.get(m).getName();
                            }
                        }

                        if (data.length() > 0)
                            ans = qdata.get(k).getOptionId() + "^" + data;

                        Log.e("answer ", "answer " + ans);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (qdata.get(k).getOptionType().equalsIgnoreCase("CheckBox")) {
                    if (qdata.get(k).isIsselected()) {
                        if (ans.length() == 0)
                            ans = qdata.get(k).getOptionId() + "^" + qdata.get(k).getOptionName();
                        else
                            ans = ans + "~" + qdata.get(k).getOptionId() + "^" + qdata.get(k).getOptionName();
                    }
                } else {
                    if (ans.length() == 0)
                        ans = qdata.get(k).getOptionValue();
                    else
                        ans = ans + "~" + qdata.get(k).getOptionValue();
                }

                if (qdata.get(k).getSubQuestions().size() > 0) {
                    getobj(qdata.get(k).getSubQuestions());
                }
            }

            try {
                if (ques.get(j).getOptionType().equalsIgnoreCase("MedicationsWithBodypart") || ques.get(j).getOptionType().equalsIgnoreCase("Medications")) {
                    obj.put("ColumnName", "Que" + ques.get(j).getQid());
                    obj.put("Ans", "PrescriptionControl");
                    obj.put("Qid", ques.get(j).getQid());
                    obj.put("OptionType", ques.get(j).getOptionType());
                    obj.put("XmlObj", "");
                    JSONArray array1 = new JSONArray();
                    Gson gson = new Gson();
                    Type type = new TypeToken<ArrayList<Drug>>() {
                    }.getType();
                    String data = gson.toJson(GlobalValues.drugdata);
                    JSONArray array2 = new JSONArray(data);
                    JSONObject obj1 = new JSONObject();
                    obj1.put("row", array2);
                    obj.put("XmlObj", obj1.toString());
                } else if (ques.get(j).getOptionType().equalsIgnoreCase("Test")) {
                    try {
                        String ansdata = ques.get(j).getAnswer();
                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<Master>>() {
                        }.getType();
                        ArrayList<Master> mdataset = gson.fromJson(ansdata, type);
                        JSONArray testarray = new JSONArray();
                        JSONObject obj1 = new JSONObject();
                        obj1.put("ColumnName", "Que" + ques.get(j).getQid());
                        obj1.put("Ans", "PrescriptionControl");
                        obj1.put("Qid", ques.get(j).getQid());
                        obj1.put("OptionType", ques.get(j).getOptionType());
                        for (int m = 0; m < mdataset.size(); m++) {
                            JSONObject obj3 = new JSONObject();
                            obj3.put("LabId", "0");
                            obj3.put("LabName", "");
                            obj3.put("TestName", mdataset.get(m).getName());
                            obj3.put("filepath", mdataset.get(m).getFilepath());
                            obj3.put("Id", mdataset.get(m).getTestId());
                            testarray.put(obj3);
                        }
                        JSONObject obj2 = new JSONObject();
                        obj2.put("row", testarray);
                        obj1.put("XmlObj", obj2.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (ques.get(j).getOptionType().equalsIgnoreCase("Diagnosis")) {
                    Option opt = new Option();
                  /*  if(ques.get(j).getOption().size()>0)
                        opt=ques.get(j).getOption().get(0);*/
                    String ansdata = ques.get(j).getAnswer();
                    Gson gson = new Gson();
                    Type type = new TypeToken<ArrayList<Master>>() {
                    }.getType();
                    ArrayList<Master> mdataset = gson.fromJson(ansdata, type);
                    JSONArray testarray = new JSONArray();
                    JSONObject obj1 = new JSONObject();
                    obj1.put("ColumnName", "Que" + ques.get(j).getQid());
                    obj1.put("Ans", "PrescriptionControl");
                    obj1.put("Qid", ques.get(j).getQid());
                    obj1.put("OptionType", ques.get(j).getOptionType());
                    for (int m = 0; m < mdataset.size(); m++) {
                        JSONObject obj3 = new JSONObject();
                        obj3.put("DiagnosisId", mdataset.get(m).getCategoryid());
                        obj3.put("DiagnosisName", mdataset.get(m).getName());
                        obj3.put("Id", "0");
                        obj3.put("BodyPart", "");
                        obj3.put("Id", "");
                        testarray.put(obj3);
                    }
                    JSONObject obj2 = new JSONObject();
                    obj2.put("row", testarray);
                    obj1.put("XmlObj", obj2.toString());
                } else if (ques.get(j).getOptionType().equalsIgnoreCase("TestWithLab") || ques.get(j).getOptionType().equalsIgnoreCase("TreatmentProcedureWithBodypart") || ques.get(j).getOptionType().equalsIgnoreCase("DiagnosisWithBodypart")) {
                    try {
                        JSONArray objans = new JSONArray(ques.get(j).getAnswer());
                        obj.put("ColumnName", "Que" + ques.get(j).getQid());
                        obj.put("Ans", "PrescriptionControl");
                        obj.put("Qid", ques.get(j).getQid());
                        obj.put("OptionType", ques.get(j).getOptionType());
                        JSONObject obj1 = new JSONObject();
                        obj1.put("row", objans);
                        obj.put("XmlObj", obj1.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    obj.put("ColumnName", "Que" + ques.get(j).getQid());
                    obj.put("Ans", ans);
                    obj.put("Qid", ques.get(j).getQid());
                    obj.put("OptionType", ques.get(j).getOptionType());
                    obj.put("XmlObj", "");
                    Log.e("obj data", "obj " + obj.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

    public void parsetemplatenames(JSONArray array, boolean isonline) {
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Template>>() {
            }.getType();
            mdataset = gson.fromJson(array.toString(), type);
            if (mdataset == null)
                mdataset = new ArrayList<>();
            adapter = new ArrayAdapter<Template>(this, android.R.layout.simple_dropdown_item_1line, mdataset);
            sp1.setAdapter(adapter);
            if (isonline)
                for (int i = 0; i < mdataset.size(); i++) {
                    DatabaseHelper.getInstance(TemplateQuestionActivity.this).savetemplatenamedata(mdataset.get(i).getContentValues(), Integer.toString(mdataset.get(i).getTemplateId()));
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResponsed(int statuscode, int accesscode, String data) {
        super.onResponsed(statuscode, accesscode, data);
        if (statuscode == Constants.STATUS_OK) {
            if (accesscode == Connection.getQuestionTemplates.ordinal()) {
                try {
                    JSONArray array = new JSONArray(GlobalValues.TEMP_STR);
                    parsetemplatenames(array, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (accesscode == Connection.GetTemplateQuestionAnswer.ordinal()) {
                try {
                    answerobjdata = new JSONObject(GlobalValues.TEMP_STR).getJSONObject("root").getJSONObject("subroot");
                    setdata();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (accesscode == Connection.GET_VISITS.ordinal()) {
                try {
                    visitdata.clear();
                    JSONArray array = new JSONArray(GlobalValues.TEMP_STR);
                    if (array.length() > 0) {
                        JSONArray array1 = array.getJSONArray(0);
                        parsevisit(array1, true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (accesscode == Connection.SaveTemplateQuestionAnswer.ordinal()) {
                DatabaseHelper.getInstance(TemplateQuestionActivity.this).savetemplatedata(visitid, Integer.toString(GlobalValues.selectedpt.getPatientid()), Integer.toString(GlobalValues.selectedpt.getId()), answerobjdata.toString(), 0, GlobalValues.selectedpt.getId(), dbvisitid, Integer.toString(mdataset.get(sp1.getSelectedItemPosition()).getTemplateId()));
                Log.e("data ", "SaveTemplateQuestionAnswer data " + GlobalValues.TEMP_STR);
                if (isrefill) {
                    isrefill = false;
                } else if (isprint) {
                    gotoprint();
                } else {
                    Toast.makeText(getApplicationContext(), "Thank You", Toast.LENGTH_LONG).show();
                }
            } else if (accesscode == Connection.AddVisit.ordinal()) {
                Log.e("data ", "data " + GlobalValues.TEMP_STR);
                try {
                    JSONArray array = new JSONArray(GlobalValues.TEMP_STR);
                    if (array.length() > 0) {
                        if (array.getJSONArray(0).length() > 0) {
                            JSONObject obj = array.getJSONArray(0).getJSONObject(0);
                            if (obj != null) {
                                Visits v = new Visits();
                                v.setVisit(obj.getInt("visit"));
                                v.setVisitid(obj.getInt("id"));
                                v.setVisitdate(obj.getString("visitdate"));
                                v.setDBPatientid(GlobalValues.selectedpt.getId());
                                v.setPatientid(GlobalValues.selectedpt.getPatientid());

                                long id = DatabaseHelper.getInstance(TemplateQuestionActivity.this).savevisitdata(Integer.toString(v.getDBPatientid()), 0, v.getcontentvalues());
                                v.setId((int) id);

                                visitdata.add(0, v);

                                if (visitdata.size() > 1)
                                    bt3.setVisibility(View.VISIBLE);
                                else bt3.setVisibility(View.GONE);
                                adaptervisit = new ArrayAdapter<Visits>(this, android.R.layout.simple_dropdown_item_1line, visitdata);
                                sp2.setAdapter(adaptervisit);
                                if (visitdata.size() > 0)
                                    sp2.setSelection(0);


                                submitdata();
                            }
                        }
                    }
                } catch (Exception e) {
                    Log.e("error ", "error " + e.toString());
                    e.printStackTrace();
                }
            }
        }
    }

    public void parsevisit(JSONArray array, boolean isonline) {
        try {
            if (array.length() > 0) {
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<Visits>>() {
                }.getType();
                ArrayList<Visits> dataset = gson.fromJson(array.toString(), type);
                visitdata.addAll(dataset);

                if (isonline) {
                    for (int i = 0; i < visitdata.size(); i++) {
                        visitdata.get(i).setPatientid(GlobalValues.selectedpt.getPatientid());
                        visitdata.get(i).setDBPatientid(GlobalValues.selectedpt.getId());
                        int id = (int) DatabaseHelper.getInstance(TemplateQuestionActivity.this).savevisitdata(Integer.toString(GlobalValues.selectedpt.getId()), visitdata.get(i).getVisitid(), visitdata.get(i).getcontentvalues());
                        visitdata.get(i).setId(id);
                        Log.e("dbid ", "dbid " + id);
                    }
                } else {

                }
                adaptervisit.notifyDataSetChanged();
                adaptervisit = new ArrayAdapter<Visits>(this, android.R.layout.simple_dropdown_item_1line, visitdata);
                sp2.setAdapter(adaptervisit);
                if (visitdata.size() > 1)
                    bt3.setVisibility(View.VISIBLE);
                else bt3.setVisibility(View.GONE);
            } else {
                try {
                    Calendar cal = Calendar.getInstance();
                    String myFormat = "yyyy-MM-dd"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    JSONObject obj = new JSONObject();
                    obj.put("VisitDate", sdf.format(cal.getTime()));
                    obj.put("Patientid", GlobalValues.selectedpt.getPatientid());
                    obj.put("UserId", GlobalValues.docid);
                    obj.put("practiceid", GlobalValues.branchid);
                    obj.put("connectionid", Constants.projectid);
                    if (InternetUtils.getInstance(TemplateQuestionActivity.this).available()) {
                        ConnectionManager.getInstance(TemplateQuestionActivity.this).AddVisit(obj.toString(), 0);
                    } else {
                        addcurrentvisit(Calendar.getInstance());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addquestions(ArrayList<Questions> questions, int i, JSONArray array) {
        ArrayList<SaveAnswerdata> data = new ArrayList<>();
        if (array != null) {
            for (int j = 0; j < array.length(); j++) {
                try {
                    data.add(new SaveAnswerdata(array.getJSONObject(j)));
                    Log.e("data1", "data1" + array.getJSONObject(j).toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        Log.e("qsize ", "qsize " + questions.size());
        for (int j = 0; j < questions.size(); j++) {
            boolean isradiobtadded = false;
            String ans = SaveAnswerdata.getAnswer(data, Integer.toString(questions.get(j).getQid()));
            ArrayList<OptionAnswer> optans = new ArrayList<>();
            if (ans != null)
                if (!ans.equalsIgnoreCase("null") || !ans.equalsIgnoreCase("")) {
                    Log.e("data ", "data " + ans + " " + questions.get(j).getAnswer());
                    String[] optanswers = ans.split("~");
                    for (int k = 0; k < optanswers.length; k++) {
                        try {
                            String[] suboptans = optanswers[k].split("\\^");
                            if (suboptans.length > 1) {
                                OptionAnswer opt = new OptionAnswer(suboptans[0], suboptans[1]);
                                optans.add(opt);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            View v;
            if (!questions.get(j).getName().equalsIgnoreCase("null"))
                v = addtextview((j + 1) + "." + questions.get(j).getName());
            else {
                v = addtextview("" + (j + 1));
            }

            ArrayList<Option> optdata = questions.get(j).getOption();
            for (int k = 0; k < optdata.size(); k++) {
                Log.e("optid1 ", "optid " + optdata.get(k).getOptionType() + " " + optdata.get(k).getOptionName() + "  " + OptionAnswer.getAnswer(optans, Integer.toString(optdata.get(k).getOptionId())));
                optdata.get(k).setOptionValue(OptionAnswer.getAnswer(optans, Integer.toString(optdata.get(k).getOptionId())));
                questions.get(j).setAnswer(OptionAnswer.getAnswer(optans, Integer.toString(optdata.get(k).getOptionId())));
                if (optdata.get(k).getOptionType().equalsIgnoreCase("CheckBox")) {
                       /* if(optdata.get(k).getOptionValue().length()>0)
                        {
                            String[] dataarray=optdata.get(k).getOptionValue().split("^");
                           if(dataarray.length>1)
                           {

                           }
                        }*/
                    if (optdata.get(k).getOptionName().equalsIgnoreCase(optdata.get(k).getOptionValue())) {
                        optdata.get(k).setIsselected(true);
                    }
                    addCheckBox(optdata.get(k));
                }
                else if (optdata.get(k).getOptionType().equalsIgnoreCase("CheckBoxList")) {
                    Log.e("anscheckboxlist ", "" + optdata.get(k).getOptionValue());
                    String[] ansarray = new String[0];
                    if (optdata.get(k).getOptionValue().length() > 0) {
                        ansarray = optdata.get(k).getOptionValue().split(",");
                          /*  if(dataarray.length>1)
                            {
                                ansarray=dataarray[1].split(",");
                            }*/
                    }
                    String[] splitarray = optdata.get(k).getOptionList().split(",");
                    ArrayList<Option> optdata1 = new ArrayList<>();
                    for (int l = 0; l < splitarray.length; l++) {
                        Option opt = new Option();
                        opt.setOptionList(optdata.get(k).getOptionList());
                        opt.setSubQuestions(optdata.get(k).getSubQuestions());
                        opt.setOptionType(optdata.get(k).getOptionType());
                        opt.setOptionId(optdata.get(k).getOptionId());
                        opt.setGrpId(optdata.get(k).getGrpId());
                        opt.setTemplateId(optdata.get(k).getTemplateId());
                        opt.setQid(optdata.get(k).getQid());
                        opt.setOptionName(splitarray[l]);
                        opt.setSortorder(optdata.get(k).getSortorder());
                        opt.setOptionValue(optdata.get(k).getOptionValue());
                        if (ansarray != null) {
                            for (int m = 0; m < ansarray.length; m++) {
                                Log.e("anscheckboxlist data", "data " + ansarray[m] + "" + splitarray[l]);
                                if (ansarray[m].equalsIgnoreCase(splitarray[l])) {
                                    opt.setIsselected(true);
                                    break;
                                }
                            }
                        }
                        optdata1.add(opt);
                        addCheckBox(opt);
                    }
                    questions.get(j).setSuboption(optdata1);
                } else if (optdata.get(k).getOptionType().equalsIgnoreCase("RadioButton")) {
                    Log.e("in answer radiobutton ", "in ans " + questions.get(j).getAnswer());
                    if (!isradiobtadded) {
                        isradiobtadded = true;
                        ArrayList<Option> optdata1 = new ArrayList<>();
                        for (int l = k; l < optdata.size(); l++) {
                            if (optdata.get(l).getOptionType().equalsIgnoreCase("RadioButton")) {
                                optdata.get(l).setOptionValue(OptionAnswer.getAnswer(optans, Integer.toString(optdata.get(l).getOptionId())));
                                Log.e("in answer radiobutton11", "in ans " + optdata.get(l).getOptionValue() + " " + questions.get(j).getAnswer() + " " + optdata.get(l).getOptionName());
                                //   Log.e("in answer radiobutton ","in ans "+questions.get(j).getOptionValue());
                                optdata1.add(optdata.get(l));
                            }
                        }
                        addradioGroup(optdata1, questions.get(j), optdata.get(k));
                    }
                } else if (optdata.get(k).getOptionType().equalsIgnoreCase("RadioButtonList")) {
                    String[] splitarray = optdata.get(k).getOptionList().split(",");
                    ArrayList<Option> optdata1 = new ArrayList<>();
                    for (int l = 0; l < splitarray.length; l++) {
                        Option opt = new Option();
                        opt.setOptionId(optdata.get(k).getOptionId());
                        opt.setQid(optdata.get(k).getQid());
                        opt.setTemplateId(optdata.get(k).getTemplateId());
                        opt.setDeleted(optdata.get(k).getDeleted());
                        opt.setGrpId(optdata.get(k).getGrpId());
                        opt.setSortorder(optdata.get(k).getSortorder());
                        opt.setOptionType(optdata.get(k).getOptionType());
                        opt.setOptionList(optdata.get(k).getOptionList());
                        opt.setSubQuestions(optdata.get(k).getSubQuestions());
                        opt.setOptionValue(optdata.get(k).getOptionValue());
                        opt.setOptionName(splitarray[l]);
                        optdata1.add(opt);
                    }
                    addradioGroup(optdata1, questions.get(j), optdata.get(k));
                } else if (optdata.get(k).getOptionType().equalsIgnoreCase("TextBox") || optdata.get(k).getOptionType().equalsIgnoreCase("Editor")) {
                    addEditText(i, optdata.get(k).getOptionName(), optdata.get(k), false, questions.get(j));
                }else if ( optdata.get(k).getOptionType().equalsIgnoreCase("Description")) {
                    addEditText(i, optdata.get(k).getOptionValue(), optdata.get(k), false, questions.get(j));
                }

                else if (optdata.get(k).getOptionType().equalsIgnoreCase("NoTextBox")) {
                    addEditText(i, questions.get(j).getName(), optdata.get(k), true, questions.get(j));
                } else if (optdata.get(k).getOptionType().equalsIgnoreCase("Date")) {
                    adddateview(optdata.get(k), questions.get(j));
                } else if (optdata.get(k).getOptionType().equalsIgnoreCase("Life Style")) {
                    //ArrayList<Master> masterdata = parsemaster(DatabaseHelper.getInstance(TemplateQuestionActivity.this).getMasterdata("Life Style"));
                    JSONArray array1 = DatabaseHelper.getInstance(TemplateQuestionActivity.this).getMaster(Constants.LifeStyle);
                    ArrayList<Master> masterdata = new ArrayList<>();
                    Gson gson = new Gson();
                    Type type = new TypeToken<ArrayList<Master>>() {
                    }.getType();
                    ArrayList<Master> dataset = gson.fromJson(array1.toString(), type);
                    if (dataset != null)
                        masterdata.addAll(dataset);
                    addcustomautocompletetextview(optdata.get(k), questions.get(j), masterdata, Constants.LifeStyle);
                } else if (optdata.get(k).getOptionType().equalsIgnoreCase("TreatmentProcedure")) {
                    optdata.get(k).setXmlObj(SaveAnswerdata.getXMLobj(data, Integer.toString(questions.get(j).getQid())));
                    //   ArrayList<Master> masterdata = parsemaster(DatabaseHelper.getInstance(TemplateQuestionActivity.this).getMasterdata("treatment procedures"));
                    JSONArray array1 = DatabaseHelper.getInstance(TemplateQuestionActivity.this).getMaster("treatment procedures");
                    ArrayList<Master> masterdata = new ArrayList<>();
                    Gson gson = new Gson();
                    Type type = new TypeToken<ArrayList<Master>>() {
                    }.getType();
                    ArrayList<Master> dataset = gson.fromJson(array1.toString(), type);
                    if (dataset != null)
                        masterdata.addAll(dataset);
                    addautocompletetextviewtest(masterdata, questions.get(j), optdata.get(k), Constants.apikey);
                } else if (optdata.get(k).getOptionType().equalsIgnoreCase("Diagnosis")) {
                    optdata.get(k).setXmlObj(SaveAnswerdata.getXMLobj(data, Integer.toString(questions.get(j).getQid())));
                    //Log.e("find test ",""+optdata.get(k).getOptionType());
                    //    ArrayList<Master> masterdata = parsemaster(DatabaseHelper.getInstance(TemplateQuestionActivity.this).getMasterdata("Diagnosis"));
                    JSONArray array1 = DatabaseHelper.getInstance(TemplateQuestionActivity.this).getMaster(Constants.Diagnosis);
                    ArrayList<Master> masterdata = new ArrayList<>();
                    Gson gson = new Gson();
                    Type type = new TypeToken<ArrayList<Master>>() {
                    }.getType();
                    ArrayList<Master> dataset = gson.fromJson(array1.toString(), type);
                    if (dataset != null)
                        masterdata.addAll(dataset);
                    addautocompletetextviewtest(masterdata, questions.get(j), optdata.get(k), Constants.DIAGNOSIS);
                } else if (optdata.get(k).getOptionType().equalsIgnoreCase("Test")) {
                    //  ArrayList<Master> masterdata = parsemaster(DatabaseHelper.getInstance(TemplateQuestionActivity.this).getMasterdata("Test"));
                    JSONArray array1 = DatabaseHelper.getInstance(TemplateQuestionActivity.this).getMaster(Constants.Test);
                    ArrayList<Master> masterdata = new ArrayList<>();
                    Gson gson = new Gson();
                    Type type = new TypeToken<ArrayList<Master>>() {
                    }.getType();
                    ArrayList<Master> dataset = gson.fromJson(array1.toString(), type);
                    if (dataset != null)
                        masterdata.addAll(dataset);
                    Log.e("testarray ", "testarray " + masterdata.size() + " " + array1.toString());
                    optdata.get(k).setXmlObj(SaveAnswerdata.getXMLobj(data, Integer.toString(questions.get(j).getQid())));
                    //   Log.e("find test ",""+optdata.get(k).getOptionType()+" "+questions.get(j).getQid()+" "+ optdata.get(k).getXmlObj().toString());
                    addautocompletetextviewtest(masterdata, questions.get(j), optdata.get(k), Constants.Test);
                } else if (optdata.get(k).getOptionType().equalsIgnoreCase("Packages")) {
                    // ArrayList<Master> masterdata = parsemaster(DatabaseHelper.getInstance(TemplateQuestionActivity.this).getMasterdata("Packages"));
                    JSONArray array1 = DatabaseHelper.getInstance(TemplateQuestionActivity.this).getMaster(Constants.Packages);
                    ArrayList<Master> masterdata = new ArrayList<>();
                    Gson gson = new Gson();
                    Type type = new TypeToken<ArrayList<Master>>() {
                    }.getType();
                    ArrayList<Master> dataset = gson.fromJson(array1.toString(), type);
                    if (dataset != null)
                        masterdata.addAll(dataset);
                    addautocompletetextview(masterdata, questions.get(j), optdata.get(k));
                } else if (optdata.get(k).getOptionType().equalsIgnoreCase("Medications")) {
                    if (v != null)
                        layout.removeView(v);
                    adddrug(true, (j + 1) + "." + questions.get(j).getName());
                } else if (optdata.get(k).getOptionType().equalsIgnoreCase("Drug")) {
                                        /*ArrayList<Master> masterdata = parsemaster(DatabaseHelper.getInstance(TemplateQuestionActivity.this).getMasterdata("Drug"));
                                        addautocompletetextview(masterdata);*/
                    adddrug(true, (j + 1) + "." + questions.get(j).getName());
                } else if (optdata.get(k).getOptionType().equalsIgnoreCase("MedicationsWithBodypart")) {
                    adddrug(true, (j + 1) + "." + questions.get(j).getName());
                } else if (optdata.get(k).getOptionType().equalsIgnoreCase("TreatmentProcedureWithBodypart")) {
                    optdata.get(k).setXmlObj(SaveAnswerdata.getXMLobj(data, Integer.toString(questions.get(j).getQid())));
                    ArrayList<Master> bodypartmasterdata = new ArrayList<Master>();// = parsemaster(DatabaseHelper.getInstance(TemplateQuestionActivity.this).getMasterdata("Body Part"));
                    //  ArrayList<Master> masterdata = parsemaster(DatabaseHelper.getInstance(TemplateQuestionActivity.this).getMasterdata("treatment procedures"));
                    JSONArray array1 = DatabaseHelper.getInstance(TemplateQuestionActivity.this).getMaster(Constants.treatmentprocedures);
                    ArrayList<Master> masterdata = new ArrayList<>();
                    Gson gson = new Gson();
                    Type type = new TypeToken<ArrayList<Master>>() {
                    }.getType();
                    ArrayList<Master> dataset = gson.fromJson(array1.toString(), type);
                    if (dataset != null)
                        masterdata.addAll(dataset);

                    JSONArray array2 = DatabaseHelper.getInstance(TemplateQuestionActivity.this).getMaster("Body Part");
                    ArrayList<Master> dataset1 = gson.fromJson(array2.toString(), type);
                    if (dataset != null)
                        bodypartmasterdata.addAll(dataset1);
                    addautocompletetextviewwitbodypartv1(masterdata, "Treatment Procedure", optdata.get(k), questions.get(j), bodypartmasterdata);
                    // addautocompletetextviewwitbodypart(masterdata, "Treatment Procedure", optdata.get(k), questions.get(j));
                } else if (optdata.get(k).getOptionType().equalsIgnoreCase("DiagnosisWithBodypart")) {
                    optdata.get(k).setXmlObj(SaveAnswerdata.getXMLobj(data, Integer.toString(questions.get(j).getQid())));
                    // ArrayList<Master> masterdata = parsemaster(DatabaseHelper.getInstance(TemplateQuestionActivity.this).getMasterdata("Diagnosis"));
                    //  ArrayList<Master> bodypartmasterdata = parsemaster(DatabaseHelper.getInstance(TemplateQuestionActivity.this).getMasterdata("Body Part"));

                    ArrayList<Master> bodypartmasterdata = new ArrayList<Master>();
                    JSONArray array1 = DatabaseHelper.getInstance(TemplateQuestionActivity.this).getMaster("Diagnosis");
                    ArrayList<Master> masterdata = new ArrayList<>();
                    Gson gson = new Gson();
                    Type type = new TypeToken<ArrayList<Master>>() {
                    }.getType();
                    ArrayList<Master> dataset = gson.fromJson(array1.toString(), type);
                    if (dataset != null)
                        masterdata.addAll(dataset);

                    JSONArray array2 = DatabaseHelper.getInstance(TemplateQuestionActivity.this).getMaster("Body Part");
                    ArrayList<Master> dataset1 = gson.fromJson(array2.toString(), type);
                    if (dataset != null)
                        bodypartmasterdata.addAll(dataset1);

                    addautocompletetextviewwitbodypartv1(masterdata, "Diagnosis", optdata.get(k), questions.get(j), bodypartmasterdata);
                } else if (optdata.get(k).getOptionType().equalsIgnoreCase("TestWithLab")) {
                    optdata.get(k).setXmlObj(SaveAnswerdata.getXMLobj(data, Integer.toString(questions.get(j).getQid())));
                   /* ArrayList<Master> masterdata = parsemaster(DatabaseHelper.getInstance(TemplateQuestionActivity.this).getMasterdata("Test"));
                    ArrayList<Master> bodypartmasterdata = parsemaster(DatabaseHelper.getInstance(TemplateQuestionActivity.this).getMasterdata("Lab"));*/
                    //  addautocompletetextviewwitlab(masterdata, "Test", optdata.get(k), questions.get(j));
                    // addautocompletetextviewtest(masterdata, questions.get(j), optdata.get(k), 1);

                    ArrayList<Master> bodypartmasterdata = new ArrayList<Master>();
                    JSONArray array1 = DatabaseHelper.getInstance(TemplateQuestionActivity.this).getMaster("Test");
                    ArrayList<Master> masterdata = new ArrayList<>();
                    Gson gson = new Gson();
                    Type type = new TypeToken<ArrayList<Master>>() {
                    }.getType();
                    ArrayList<Master> dataset = gson.fromJson(array1.toString(), type);
                    if (dataset != null)
                        masterdata.addAll(dataset);

                    JSONArray array2 = DatabaseHelper.getInstance(TemplateQuestionActivity.this).getMaster("Lab");
                    ArrayList<Master> dataset1 = gson.fromJson(array2.toString(), type);
                    if (dataset != null)
                        bodypartmasterdata.addAll(dataset1);

                    addautocompletetextviewwitbodypartv1(masterdata, "TestWithLab", optdata.get(k), questions.get(j), bodypartmasterdata);
                } else if (optdata.get(k).getOptionType().equalsIgnoreCase("DropDownList") || optdata.get(k).getOptionName().equalsIgnoreCase("null")) {

                    String[] arraydata = optdata.get(k).getOptionList().split(",");
                    addspinner(arraydata, optdata.get(k), questions.get(j));

                }
                else if (optdata.get(k).getOptionType().equalsIgnoreCase("FileUpload")) {
                    filedata.clear();
                    String ansdata = optdata.get(k).getOptionValue();
                    if (ansdata.length() > 0) {
                        String[] ansarray = ansdata.split("^");
                        if (ansarray.length > 1) {
                            String[] arraydata = ansarray[1].split(",");
                            Log.e("filedata", "data " + ansarray[1]);
                            for (int m = 0; m < arraydata.length; m++) {
                                FileData f = new FileData();
                                f.setFilepath(arraydata[m]);
                                int lastDotPosition = arraydata[m].lastIndexOf('.');
                                int lastSlashPosition = arraydata[m].lastIndexOf('/');
                                if (lastDotPosition > 0) {
                                    String string3 = arraydata[m].substring((lastSlashPosition + 1), (lastDotPosition - 1));
                                    f.setFilename(string3);
                                }
                                filedata.add(f);
                            }
                        }
                    }

                    browse();
                } else if (optdata.get(k).getOptionType().equalsIgnoreCase("CustomAutoComplete") || optdata.get(k).getOptionType().equalsIgnoreCase("AutoComplete")) {
                    ArrayList<Master> custommasterdata = new ArrayList<>();//DatabaseHelper.getInstance(TemplateQuestionActivity.this).getCustommasterdata();
                    JSONArray array1 = DatabaseHelper.getInstance(TemplateQuestionActivity.this).getMaster(questions.get(j).getName());
                    Gson gson = new Gson();
                    Type type = new TypeToken<ArrayList<Master>>() {
                    }.getType();
                    ArrayList<Master> dataset = gson.fromJson(array1.toString(), type);
                    if (dataset != null)
                        custommasterdata.addAll(dataset);
                            addcustomautocompletetextview(optdata.get(k), questions.get(j), questions.get(j).getName(), custommasterdata);
                } else if (optdata.get(k).getOptionType().equalsIgnoreCase("Doctor list")) {
                    ArrayList<Doctor> mdataset = new ArrayList<>();
                    JSONArray array1 = DatabaseHelper.getInstance(TemplateQuestionActivity.this).getdata(DatabaseHelper.TABLE_DEPARTMENT);
                    Log.e("doctorlist ", "doctorlist " + array1.toString());
                    if (array1.length() > 0) {
                        try {
                            JSONObject obj = array1.getJSONObject(0);
                            Log.e("doctorlist2", "doctorlist " + obj.toString());
                            JSONArray array2 = new JSONArray(obj.getString("jsondata")).getJSONObject(0).getJSONArray("doctor");
                            Log.e("doctorlist1", "doctorlist " + array2.toString());
                            for (int l = 0; l < array2.length(); l++) {
                                Doctor d = new Doctor(array2.getJSONObject(l));
                                mdataset.add(d);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    adddoc(optdata.get(k), questions.get(j), mdataset);
                }

                if (optdata.get(k).getSubQuestions().size() > 0) {
                    JSONArray array1 = new JSONArray();
                    addquestions(optdata.get(k).getSubQuestions(), i, array1);
                }
                try {
                    for (int k1 = 0; k1 < data.size(); k1++) {
                        if (data.get(k1).getAns() != null)
                            if (data.get(k1).getAns().equalsIgnoreCase("PrescriptionControl")) {
                                Log.e("in PrescriptionControl ", "in PrescriptionControl " + data.get(k1).getOptionType());
                                JSONObject obj = data.get(k1).getXmlObj();
                                if (obj != null) {
                                    if (obj.has("row")) {
                                        JSONObject rowobj = obj.optJSONObject("row");
                                        if (rowobj != null) {
                                            if (optdata.get(k).getOptionType().equalsIgnoreCase("Medications") && data.get(k1).getOptionType().equalsIgnoreCase("Medications")) {
                                                //   Log.e("in Medication ", "in Medication");
                                                Gson gson1 = new Gson();
                                                Type type1 = new TypeToken<Drug>() {
                                                }.getType();
                                                Drug d = gson1.fromJson(rowobj.toString(), type1);
                                                GlobalValues.drugdata.add(d);
                                                //   Log.e("size111 ", "sizze" + GlobalValues.drugdata.size());
                                                if (mRecyclerviewdrug != null) {
                                                    drugadapter.notifyDataSetChanged();
                                                    drugadapter = new DrugAdapter(TemplateQuestionActivity.this, GlobalValues.drugdata, 0);
                                                    mRecyclerviewdrug.setAdapter(drugadapter);
                                                } else {
                                                    Log.e("DrugAdapter is null", "DrugAdapter is null");
                                                }
                                            }/* else if (optdata.get(k).getOptionType().equalsIgnoreCase("Diagnosis") && data.get(k1).getOptionType().equalsIgnoreCase("Diagnosis")) {
                                                    Log.e("in Diagnosis ", "in Diagnosis");

                                                    Master master = new Master();
                                                    master.setCategoryid(rowobj.getString("DiagnosisId"));
                                                    master.setName(rowobj.getString("DiagnosisName"));
                                                    GlobalValues.diagnosis.add(master);
                                                    // Log.e("size111 ", "sizze" + GlobalValues.drugdata.size());
                                                    if (mRecyclerviewdiagnosis != null) {
                                                        diagnosisadapter.notifyDataSetChanged();
                                                        diagnosisadapter = new TestAdapter(TemplateQuestionActivity.this, GlobalValues.diagnosis, 0);
                                                        mRecyclerviewdiagnosis.setAdapter(diagnosisadapter);
                                                    } else {
                                                        Log.e("DrugAdapter is null", "DrugAdapter is null");
                                                    }
                                                } else if (optdata.get(k).getOptionType().equalsIgnoreCase("Test") && data.get(k1).getOptionType().equalsIgnoreCase("Test")) {
                                                    Log.e("in Diagnosis ", "in Diagnosis");

                                                    Master master = new Master();
                                                    //  master.setCategoryid(rowobj.getString("TestName"));
                                                    master.setName(rowobj.getString("TestName"));
                                                    master.setTestId(rowobj.getString("TestId"));
                                                    master.setLabId(rowobj.getString("LabId"));
                                                    master.setFilepath(rowobj.getString("filepath"));

                                                    GlobalValues.test.add(master);
                                                    Log.e("size111 ", "sizze" + GlobalValues.test.size());
                                                    if (mRecyclerviewtest != null) {
                                                        testadapter.notifyDataSetChanged();
                                                        testadapter = new TestAdapter(TemplateQuestionActivity.this, GlobalValues.test, 0);
                                                        mRecyclerviewtest.setAdapter(testadapter);
                                                    } else {
                                                        Log.e("DrugAdapter is null", "DrugAdapter is null");
                                                    }
                                                }*/
                                        } else {
                                            Log.e("rowobj  null", "info ");
                                            JSONArray rowarray = obj.optJSONArray("row");
                                            if (optdata.get(k).getOptionType().equalsIgnoreCase("Medications") && data.get(k1).getOptionType().equalsIgnoreCase("Medications")) {
                                                Log.e("in Medication1 ", "in Medication1");
                                                Gson gson1 = new Gson();
                                                Type type1 = new TypeToken<ArrayList<Drug>>() {
                                                }.getType();
                                                GlobalValues.drugdata = gson1.fromJson(rowarray.toString(), type1);
                                                // Log.e("size11 ", "sizze" + GlobalValues.drugdata.size());
                                                if (mRecyclerviewdrug != null) {
                                                    drugadapter.notifyDataSetChanged();
                                                    drugadapter = new DrugAdapter(TemplateQuestionActivity.this, GlobalValues.drugdata, 0);
                                                    mRecyclerviewdrug.setAdapter(drugadapter);
                                                } else {
                                                    // Log.e("DrugAdapter is null", "DrugAdapter is null");
                                                }
                                            }/* else if (optdata.get(k).getOptionType().equalsIgnoreCase("Diagnosis") && data.get(k1).getOptionType().equalsIgnoreCase("Diagnosis")) {
                                                    Log.e("in Diagnosis ", "in Diagnosis");
                                                    for (int l = 0; l < rowarray.length(); l++) {
                                                        Master master = new Master();
                                                        master.setCategoryid(rowarray.getJSONObject(l).getString("DiagnosisId"));
                                                        master.setName(rowarray.getJSONObject(l).getString("DiagnosisName"));
                                                        GlobalValues.diagnosis.add(master);
                                                    }
                                                    Log.e("size111 ", "sizze" + GlobalValues.drugdata.size());
                                                    if (mRecyclerviewdiagnosis != null) {
                                                        diagnosisadapter.notifyDataSetChanged();
                                                        diagnosisadapter = new TestAdapter(TemplateQuestionActivity.this, GlobalValues.diagnosis, 0);
                                                        mRecyclerviewdiagnosis.setAdapter(diagnosisadapter);
                                                    } else {
                                                        Log.e("DrugAdapter is null", "DrugAdapter is null");
                                                    }
                                                } else if (optdata.get(k).getOptionType().equalsIgnoreCase("Test") && data.get(k1).getOptionType().equalsIgnoreCase("Test")) {
                                                    Log.e("in Test ", "in Test");
                                                    try {
                                                        Log.e("row array ", "row array " + rowarray.toString());
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    };
                                                    for (int l = 0; l < rowarray.length(); l++) {
                                                        Master master = new Master();
                                                        master.setName(rowarray.getJSONObject(l).getString("TestName"));
                                                        master.setTestId(rowarray.getJSONObject(l).getString("TestId"));
                                                        master.setLabId(rowarray.getJSONObject(l).getString("LabId"));
                                                        master.setFilepath(rowarray.getJSONObject(l).getString("filepath"));
                                                        GlobalValues.test.add(master);
                                                    }
                                                    //  Log.e("size111 ", "sizze" + GlobalValues.drugdata.size());
                                                    if (mRecyclerviewtest != null) {
                                                        testadapter.notifyDataSetChanged();
                                                        testadapter = new TestAdapter(TemplateQuestionActivity.this, GlobalValues.test, 0);
                                                        mRecyclerviewtest.setAdapter(testadapter);
                                                    } else {
                                                        Log.e("DrugAdapter is null", "DrugAdapter is null");
                                                    }
                                                }*/
                                        }
                                    } else {
                                        Log.e("obj not row ", "info");
                                    }
                                } else {
                                    Log.e("obj  null", "info");
                                }
                            }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            devider();
        }
    }

    public ArrayList<Master> parsemaster(String data) {
        ArrayList<Master> masterdata = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(data).getJSONObject("root");
            JSONArray array = obj.optJSONArray("subroot");
            if (array != null) {
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<Master>>() {
                }.getType();
                ArrayList<Master> dataset = gson.fromJson(array.toString(), type);
                if (dataset != null) {
                    return dataset;
                }
            } else {
                JSONObject obj1 = obj.optJSONObject("subroot");
                if (obj1 != null) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<Master>() {
                    }.getType();
                    Master m = gson.fromJson(array.toString(), type);
                    masterdata.add(m);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return masterdata;
    }

    private void addtextviewheader(String Text, int position) {
        try {
            TextView txt = new TextView(getApplicationContext());
            txt.setText(Text);
            txt.setId(position + 1);
            txt.setGravity(Gravity.CENTER);
           /* if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                txt.setBackgroundDrawable(getResources().getDrawable(R.drawable.roundedtextview));
            } else {
                txt.setBackground(getResources().getDrawable(R.drawable.roundedtextview));
            }*/
            txt.setTextColor(getResources().getColor(R.color.colorPrimary));
            txt.setPadding(10, 10, 10, 5);
            txt.setTypeface(Fonter.getTypefacebold(TemplateQuestionActivity.this));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.bottomMargin = 5;
            params.topMargin = 10;
            // params.gravity = Gravity.CENTER;
            txt.setLayoutParams(params);
            layout.addView(txt);
        } catch (Exception e) {
            e.printStackTrace();
            reporterror(tag, e.toString());
        }
    }

    private View addtextview(String Text) {
        TextView txt = new TextView(getApplicationContext());
        try {
            txt.setText(Html.fromHtml(Text));
            txt.setTextSize(fontsize);
            txt.setTypeface(Fonter.getTypefacesemibold(TemplateQuestionActivity.this));
            txt.setTextColor(getResources().getColor(R.color.textcolor));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 5, 0, 10);
            txt.setLayoutParams(params);
            layout.addView(txt);
        } catch (Exception e) {
            e.printStackTrace();
            reporterror(tag, e.toString());
        }
        return txt;
    }

    private void devider() {
     /*   try {
            View v = new View(getApplicationContext());
            v.setBackgroundColor(getResources().getColor(R.color.Gray));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
            params.setMargins(0, 20, 0, 0);
            v.setLayoutParams(params);
            layout.addView(v);
        } catch (Exception e) {
            e.printStackTrace();
            reporterror(tag, e.toString());
        }*/
    }

    private void addCheckBox(final Option qopt) {
        try {

            HorizontalScrollView mScrollView = new HorizontalScrollView(getApplicationContext());
            mScrollView.setLayoutParams(new ViewGroup.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
            mScrollView.setFillViewport(true);

            LinearLayout attrControlsSubContainer = new LinearLayout(getBaseContext());
            attrControlsSubContainer.setOrientation(LinearLayout.HORIZONTAL);

            CheckBox cb = new CheckBox(TemplateQuestionActivity.this);
            cb.setChecked(qopt.isIsselected());
            cb.setText(qopt.getOptionName());
            cb.setTag(qopt);
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    hideKeypad();
                    qopt.setIsselected(b);
                }
            });
            cb.setTextColor(getResources().getColor(R.color.Black));
            attrControlsSubContainer.addView(cb);
            layout.addView(attrControlsSubContainer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addCheckBoxlist(final Option qopt) {
        try {
            CheckBox cb = new CheckBox(TemplateQuestionActivity.this);
            cb.setText(qopt.getOptionName());
            cb.setTag(qopt);
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    hideKeypad();
                    qopt.setIsselected(b);
                }
            });
            cb.setTextColor(getResources().getColor(R.color.Black));
            layout.addView(cb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addradioGroup(final ArrayList<Option> dataset, final Questions que, final Option opt1) {
        try {
            String ans = "";
            Log.e("radioanswer ", " " + opt1.getOptionValue() + "##" + que.getAnswer());
            RadioGroup rg = new RadioGroup(this);
            rg.setOrientation(LinearLayout.HORIZONTAL);
            for (int i = 0; i < dataset.size(); i++) {
                Log.e("radioanswer ", " " + dataset.get(i).getOptionValue() + "##" + que.getAnswer());
                final RadioButton rdbtn = new RadioButton(this);
                final Option opt = dataset.get(i);
                rdbtn.setId(i);
                rdbtn.setText(dataset.get(i).getOptionName());
                Log.e("radioanswercheck", "radioanswer " + dataset.get(i).getOptionName() + " " + opt.getOptionValue() + " " + que.getAnswer() + " " + opt1.getOptionValue());
                if (dataset.get(i).getOptionName().equalsIgnoreCase(opt.getOptionValue())) {
                    rdbtn.setChecked(true);
                    ans = opt.getOptionId() + "^" + opt.getOptionValue();
                    dataset.get(i).setOptionValue(opt.getOptionId() + "^" + opt.getOptionValue());
                }
                rg.addView(rdbtn);
                rdbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        hideKeypad();
                        try {
                            for (int i = 0; i < dataset.size(); i++) {
                                dataset.get(i).setIsselected(false);
                            }
                            opt.setIsselected(((RadioButton) view).isChecked());
                            opt1.setOptionValue(opt.getOptionId() + "^" + opt.getOptionName());
                            que.setAnswer(opt.getOptionId() + "^" + opt.getOptionName());
                            Log.e("radioans ", "ans" + que.getAnswer());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //  Log.e("data","data "+que.getOptionValue());
                    }
                });
            }
            opt1.setOptionValue(ans);
            que.setAnswer(ans);
            Log.e("radioanswer1 ", " " + opt1.getOptionValue() + "##" + que.getAnswer());
            layout.addView(rg);
        } catch (Exception e) {
            e.printStackTrace();
            reporterror(tag, e.toString());
        }
    }

    private void addEditText(final int position, final String hint, final Option opt, boolean isno, final Questions q) {
        try {
            //  Log.e("Description", "Description " + opt.getOptionValue());

            TextInputLayout textInputLayout = new TextInputLayout(this);
            textInputLayout.setHint(hint);
            TextInputEditText editText = new TextInputEditText(textInputLayout.getContext());

            if (opt.getOptionType().equalsIgnoreCase("Description")) {
                editText.setSingleLine(false);
                editText.setMaxLines(5);
                editText.setText(hint);
                editText.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
            }else {
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
            }


            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence.toString().trim().length() > 0) {
                        q.setAnswer(opt.getOptionId() + "^" + charSequence.toString());
                        opt.setOptionValue(opt.getOptionId() + "^" + charSequence.toString());
                    } else {
                        q.setAnswer("");
                        opt.setOptionValue("");
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });


            textInputLayout.addView(editText);


          /*  EditText e1 = new EditText(getApplicationContext());
            e1.setTextSize(fontsize);
            e1.setTypeface(typeface);
            e1.setTextColor(getResources().getColor(R.color.textcolor));
            e1.setMinLines(2);
            if (opt.getOptionType().equalsIgnoreCase("editor"))
                e1.setMinLines(4);
            e1.setPadding(10, 10, 10, 10);
            e1.setGravity(Gravity.TOP);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.bottomMargin = 5;
            params.topMargin = 10;
            e1.setTextSize(fontsize);
            e1.setTypeface(typeface);
            e1.setTextColor(getResources().getColor(R.color.textcolor));
            params.leftMargin = padding;
            if (isno)
                e1.setInputType(InputType.TYPE_CLASS_NUMBER);

            e1.setMaxLines(200);

            e1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence.toString().trim().length() > 0) {
                        q.setOptionValue(opt.getOptionId() + "^" + charSequence.toString());
                        opt.setOptionValue(opt.getOptionId() + "^" + charSequence.toString());
                    } else {
                        q.setOptionValue("");
                        opt.setOptionValue("");
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            e1.setLayoutParams(params);
            if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                e1.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittextshape_grayborder));
            } else {
                e1.setBackground(getResources().getDrawable(R.drawable.edittextshape_grayborder));
            }*/
            layout.addView(textInputLayout);
//            e1.setText(Html.fromHtml(opt.getOptionValue()));
            Log.e("final opt ", "final opt " + opt.getOptionValue());
           /* if(opt.getOptionValue().length()>0)
            {
                if(opt.getOptionValue().contains("^"))
                {
                    String[] array=  opt.getOptionValue().split("^");
                    e1.setText(array[1]);
                }
            }*/
        } catch (Exception e) {
            e.printStackTrace();
            reporterror(tag, e.toString());
        }
    }

    private void adddoc(final Option opt, final Questions q, final ArrayList<Doctor> mdataset) {
        try {
            final TextView editdoc = new TextView(getApplicationContext());
            editdoc.setPadding(10, 10, 10, 10);
            editdoc.setTextColor(getResources().getColor(R.color.Black));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.bottomMargin = 5;
            params.topMargin = 10;
            editdoc.setInputType(InputType.TYPE_CLASS_TEXT);
            editdoc.setFocusable(false);

            editdoc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ShowPopupdoctor(editdoc, mdataset, opt, q);
                }
            });

            editdoc.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                   /* if(charSequence.toString().length()>0) {
                        opt.setOptionValue(opt.getOptionId() + "^" + charSequence.toString());
                        q.setOptionValue(opt.getOptionId() + "^" + charSequence.toString());
                    }else {
                        opt.setOptionValue("");
                        q.setOptionValue("");
                    }*/
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            editdoc.setLayoutParams(params);
//            if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//                editdoc.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittextshape_grayborder));
//            } else {
//                editdoc.setBackground(getResources().getDrawable(R.drawable.edittextshape_grayborder));
//            }

            layout.addView(editdoc);
            if (opt.getOptionValue().length() > 0) {
                String[] array = opt.getOptionValue().split("^");
                if (array.length > 0) {
                    editdoc.setText(array[1]);
                    q.setAnswer(opt.getOptionId() + "^" + array[1]);
                    opt.setOptionValue(opt.getOptionId() + "^" + array[1]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            reporterror(tag, e.toString());
        }
    }

    private void addEditTexteditor(final int position, final String hint, final Option opt, boolean isno) {
        try {
            if (hint != null && !hint.equalsIgnoreCase("null") && hint.length() > 0)
                addtextview(hint);

            EditText e1 = new EditText(getApplicationContext());
            e1.setPadding(10, 10, 10, 10);
            e1.setTextColor(getResources().getColor(R.color.Black));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.bottomMargin = 5;
            params.topMargin = 10;
            e1.setInputType(InputType.TYPE_CLASS_TEXT);
            if (isno)
                e1.setInputType(InputType.TYPE_CLASS_NUMBER);

            e1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    opt.setOptionValue(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            e1.setLayoutParams(params);
            if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                e1.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittextshape_grayborder));
            } else {
                e1.setBackground(getResources().getDrawable(R.drawable.edittextshape_grayborder));
            }
            layout.addView(e1);
        } catch (Exception e) {
            e.printStackTrace();
            reporterror(tag, e.toString());
        }
    }

    private final void focusOnView(final View v) {
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.scrollTo(0, v.getTop());
            }
        });
    }

    public void adddateview(final Option opt, final Questions q) {
        try {
            final EditText e1 = new EditText(getApplicationContext());
            e1.setPadding(10, 10, 10, 10);
            e1.setTextColor(getResources().getColor(R.color.Black));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.bottomMargin = 5;
            params.topMargin = 10;
            params.leftMargin = padding;
            e1.setTextSize(fontsize);
            e1.setTypeface(typeface);
            e1.setTextColor(getResources().getColor(R.color.textcolor));
            e1.setPadding(10, 10, 10, 10);
            e1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence.toString().length() > 0) {
                        q.setAnswer(opt.getOptionId() + "^" + charSequence.toString());
                        opt.setOptionValue(opt.getOptionId() + "^" + charSequence.toString());
                    } else {
                        q.setAnswer("");
                        opt.setOptionValue("");
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            e1.setLayoutParams(params);
//            if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//                e1.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittextshape_grayborder));
//            } else {
//                e1.setBackground(getResources().getDrawable(R.drawable.edittextshape_grayborder));
//            }
            layout.addView(e1);
            e1.setFocusable(false);

            final Calendar myCalendar = Calendar.getInstance();
            final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    // TODO Auto-generated method stub
                    try {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String myFormat = "yyyy-MM-dd"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        e1.setText(sdf.format(myCalendar.getTime()));
                    } catch (Exception e) {
                        e.printStackTrace();
                        reporterror(tag, e.toString());
                    }
                }
            };

            e1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    try {
                        new DatePickerDialog(TemplateQuestionActivity.this, date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        reporterror(tag, e.toString());
                    }
                }
            });
            e1.setText(opt.getOptionValue());
        } catch (Exception e) {
            e.printStackTrace();
            reporterror(tag, e.toString());
        }
    }

    public void addautocompletetextview(ArrayList<Master> mdataset, final Questions q, final Option opt) {
        final AutoCompleteTextView autotxt = new AutoCompleteTextView(this);
//        if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//            autotxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittextshape_grayborder));
//        } else {
//            autotxt.setBackground(getResources().getDrawable(R.drawable.edittextshape_grayborder));
//        }
        final ArrayAdapter<Master> adapterggeneric = new ArrayAdapter<Master>(TemplateQuestionActivity.this, android.R.layout.simple_list_item_1, mdataset);
        autotxt.setAdapter(adapterggeneric);
        autotxt.setThreshold(0);
        layout.addView(autotxt);
        autotxt.setTextSize(fontsize);
        autotxt.setTypeface(typeface);
        autotxt.setTextColor(getResources().getColor(R.color.textcolor));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.leftMargin = padding;
        params.topMargin = 10;
        autotxt.setPadding(10, 10, 10, 10);
        autotxt.setLayoutParams(params);

        autotxt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("called ", "setOnItemSelectedListener");
                q.setAnswer(opt.getOptionId() + "^" + autotxt.getText().toString());
                opt.setOptionValue(opt.getOptionValue() + "^" + autotxt.getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        autotxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("called ", "setOnItemClickListener");
                q.setAnswer(opt.getOptionId() + "^" + autotxt.getText().toString());
                opt.setOptionValue(opt.getOptionId() + "^" + autotxt.getText().toString());
            }
        });
        autotxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e("called ", "addTextChangedListener");
                q.setAnswer(opt.getOptionId() + "^" + s.toString());
                opt.setOptionValue(opt.getOptionValue() + "^" + s.toString());
            }
        });
    }

    public void onShowPopup(final View anchorView, ArrayList<Master> mdataset, Questions q) {
        hideKeypad();
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflatedView = layoutInflater.inflate(R.layout.list_item_popup, null, false);
        EditText edit = inflatedView.findViewById(R.id.edittext_1);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(TemplateQuestionActivity.this);
        final PopupWindow popWindow = new PopupWindow(TemplateQuestionActivity.this);
        RecyclerView mRecyclerview = inflatedView.findViewById(R.id.recyclerview_1);
        mRecyclerview.setLayoutManager(mLinearLayoutManager);

        final MasterAdapter adapter = new MasterAdapter(TemplateQuestionActivity.this, mdataset, new MasterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Master item) {
                ((AutoCompleteTextView) anchorView).setText(item.getName());
                popWindow.dismiss();
            }
        });
        mRecyclerview.setAdapter(adapter);
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                adapter.filterdata(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        popWindow.setContentView(inflatedView);
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(true);

        popWindow.setWidth(anchorView.getWidth());
        popWindow.setHeight(popupheight);

        popWindow.showAsDropDown(anchorView);
        popWindow.setFocusable(true);
    }

    public void Showcombineddata(final View anchorView, final ArrayList<Master> mdataset, final Option opt, final CombinedDataAdapter selectedmasteradapter, final ArrayList<Combineddata> selectedmaster, final int flag, final Questions q, final ArrayList<Master> secondmaster) {
        hideKeypad();
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflatedView = layoutInflater.inflate(R.layout.list_item_combinedpopup, null, true);
        final EditText edit1 = inflatedView.findViewById(R.id.edittext_1);
        edit1.setTextSize(fontsize);
        edit1.setTypeface(typeface);
        edit1.setTag("0");
        final EditText edit2 = inflatedView.findViewById(R.id.edittext_2);
        edit2.setTextSize(fontsize);
        edit2.setTypeface(typeface);
        ImageView img = inflatedView.findViewById(R.id.imageview_1);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(TemplateQuestionActivity.this);
        LinearLayoutManager mLinearLayoutManager1 = new LinearLayoutManager(TemplateQuestionActivity.this);
        final PopupWindow popWindow = new PopupWindow(TemplateQuestionActivity.this);
        RecyclerView mRecyclerview = inflatedView.findViewById(R.id.recyclerview_1);
        RecyclerView mRecyclerview2 = inflatedView.findViewById(R.id.recyclerview_2);
        mRecyclerview.setLayoutManager(mLinearLayoutManager);
        mRecyclerview2.setLayoutManager(mLinearLayoutManager1);

        final MasterAdapter adapter = new MasterAdapter(TemplateQuestionActivity.this, mdataset, new MasterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Master item) {
                edit1.setText(item.getName());
                edit1.setTag(item.getCategoryid());
            }
        });
        mRecyclerview.setAdapter(adapter);

        final MasterAdapter secondadapter = new MasterAdapter(TemplateQuestionActivity.this, secondmaster, new MasterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Master item) {
                edit2.setText(item.getName());
            }
        });
        mRecyclerview2.setAdapter(secondadapter);

        edit1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                adapter.filterdata(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edit2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                secondadapter.filterdata(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        popWindow.setContentView(inflatedView);
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(true);

        popWindow.setWidth(anchorView.getWidth());
        popWindow.setHeight(popupheight);

        popWindow.showAsDropDown(anchorView);
        popWindow.setFocusable(true);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit1.getText().toString().length() > 0 && edit2.getText().toString().length() > 0) {
                    Combineddata combinedData = new Combineddata();
                    combinedData.setName(edit1.getText().toString());
                    combinedData.setBodypart(edit2.getText().toString());
                    combinedData.setCatid((String) edit1.getTag());
                    selectedmaster.add(combinedData);
                    selectedmasteradapter.notifyDataSetChanged();
                    edit1.setText("");
                    edit2.setText("");
                    adapter.filterdata("");
                    secondadapter.filterdata("");

                    Gson gson = new Gson();
                    String ans = gson.toJson(selectedmaster);
                    opt.setOptionValue(ans);
                    q.setAnswer(ans);
                    Log.e("answer ", "answer " + ans);
                  /*  String data = edit.getText().toString();
                    //hideKeypad();
                    DatabaseHelper.getInstance(TemplateQuestionActivity.this).SaveCustommaster(edit.getText().toString().trim());
                    Master master = new Master(data);
                    master.setTestId("0");
                    master.setLabId("0");
                    mdataset.add(master);
                    adapter.notifyDataSetChanged();
                    selectedmaster.add(master);
                    selectedmasteradapter.notifyDataSetChanged();
                    edit.setText("");
                    ((AutoCompleteTextView) anchorView).setText("");
                    adapter.filterdata("");*/
                }
            }
        });
    }

    public void ShowPopup(final View anchorView, final ArrayList<Master> mdataset, final Option opt, final TestAdapter selectedmasteradapter, final ArrayList<Master> selectedmaster, final int flag, final Questions q, final String masterkey) {
        hideKeypad();
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflatedView = layoutInflater.inflate(R.layout.list_item_popup, null, false);
        final EditText edit = inflatedView.findViewById(R.id.edittext_1);
        ImageView img = inflatedView.findViewById(R.id.imageview_1);
        edit.setFocusableInTouchMode(true);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(TemplateQuestionActivity.this);
        final PopupWindow popWindow = new PopupWindow(TemplateQuestionActivity.this);
        RecyclerView mRecyclerview = inflatedView.findViewById(R.id.recyclerview_1);
        mRecyclerview.setLayoutManager(mLinearLayoutManager);

        final MasterAdapter adapter = new MasterAdapter(TemplateQuestionActivity.this, mdataset, new MasterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Master item) {
                ((AutoCompleteTextView) anchorView).setText(item.getName());
                selectedmaster.add(item);
                selectedmasteradapter.notifyDataSetChanged();
                Gson gson = new Gson();
                String ans = gson.toJson(selectedmaster);
                opt.setOptionValue(ans);
                q.setAnswer(ans);
                Log.e("ans ", "ans " + q.getQid() + "  " + q.getAnswer());
                ((AutoCompleteTextView) anchorView).setText("");
            }
        });
        mRecyclerview.setAdapter(adapter);

        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                adapter.filterdata(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        popWindow.setContentView(inflatedView);
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(true);

        popWindow.setWidth(anchorView.getWidth());
        popWindow.setHeight(popupheight);

        popWindow.showAsDropDown(anchorView);
        popWindow.setFocusable(true);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit.getText().toString().length() > 0) {
                    String data = edit.getText().toString();
                    long index = DatabaseHelper.getInstance(TemplateQuestionActivity.this).savemaster(data, "0", 0, masterkey);
                    Master master = new Master(data);
                    master.setTestId("0");
                    master.setLabId("0");
                    mdataset.add(master);
                    adapter.notifyDataSetChanged();
                    selectedmaster.add(master);
                    selectedmasteradapter.notifyDataSetChanged();
                    edit.setText("");
                    ((AutoCompleteTextView) anchorView).setText("");
                    adapter.filterdata("");


                    if (index == -1) {

                    }
                }
            }
        });
    }

    public void ShowPopupdoctor(final View anchorView, final ArrayList<Doctor> mdataset, final Option opt, final Questions q) {
        hideKeypad();
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflatedView = layoutInflater.inflate(R.layout.list_item_popup, null, false);
        final EditText edit = inflatedView.findViewById(R.id.edittext_1);
        ImageView img = inflatedView.findViewById(R.id.imageview_1);
        edit.setFocusableInTouchMode(true);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(TemplateQuestionActivity.this);
        final PopupWindow popWindow = new PopupWindow(TemplateQuestionActivity.this);
        RecyclerView mRecyclerview = inflatedView.findViewById(R.id.recyclerview_1);
        mRecyclerview.setLayoutManager(mLinearLayoutManager);

        final DoctorAdapter adapter = new DoctorAdapter(TemplateQuestionActivity.this, mdataset, new DoctorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Doctor item) {
                ((EditText) anchorView).setText(item.getName());
                opt.setOptionValue(opt.getOptionId() + "^" + item.getName());
                q.setAnswer(opt.getOptionId() + "^" + item.getName());
                popWindow.dismiss();
            }
        });
        mRecyclerview.setAdapter(adapter);

        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                adapter.filter(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        popWindow.setContentView(inflatedView);
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(true);
        popWindow.setWidth(anchorView.getWidth());
        popWindow.setHeight(popupheight);
        popWindow.showAsDropDown(anchorView);
        popWindow.setFocusable(true);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit.getText().toString().length() > 0) {
                   /* String data = edit.getText().toString();
                    //hideKeypad();
                    DatabaseHelper.getInstance(TemplateQuestionActivity.this).SaveCustommaster(edit.getText().toString().trim());
                    Master master = new Master(data);
                    master.setTestId("0");
                    master.setLabId("0");
                    mdataset.add(master);
                    adapter.notifyDataSetChanged();
                    selectedmaster.add(master);
                    selectedmasteradapter.notifyDataSetChanged();
                    edit.setText("");
                    ((AutoCompleteTextView) anchorView).setText("");
                    adapter.filterdata("");*/
                }
            }
        });
    }

    public void addautocompletetextviewtest(final ArrayList<Master> mdataset, final Questions q, final Option opt, final String masterkey) {
        final AutoCompleteTextView autotxt = new AutoCompleteTextView(this);
//        if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//            autotxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittextshape_grayborder));
//        } else {
//            autotxt.setBackground(getResources().getDrawable(R.drawable.edittextshape_grayborder));
//        }
        autotxt.setThreshold(0);
        autotxt.setTextSize(fontsize);
        autotxt.setTypeface(typeface);
        autotxt.setFocusable(false);
        autotxt.setHint("Please select...");
        autotxt.setTextColor(getResources().getColor(R.color.textcolor));
        LinearLayout.LayoutParams paramsautotxt = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        paramsautotxt.leftMargin = padding;
        paramsautotxt.topMargin = 10;
        autotxt.setPadding(padding, 10, 10, 10);
        autotxt.setLayoutParams(paramsautotxt);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);

        final ArrayList<Master> selectedmasterdata = new ArrayList<>();
        try {
            JSONObject obj = opt.getXmlObj();
            Log.e("find test ", "obj " + obj.toString());
            if (obj != null) {
                //    Log.e("obj nt null", "info" + obj.toString());
                if (obj.has("row")) {
                    //  Log.e("obj has row ", "info");
                    JSONObject rowobj = obj.optJSONObject("row");
                    if (rowobj != null) {
                        if (opt.getOptionType().equalsIgnoreCase("Diagnosis")) {
                            Log.e("in Diagnosis ", "in Diagnosis");
                            Master master = new Master();
                            master.setCategoryid(rowobj.getString("DiagnosisId"));
                            master.setName(rowobj.getString("DiagnosisName"));
                            selectedmasterdata.add(master);
                        } else if (opt.getOptionType().equalsIgnoreCase("Test")) {
                            Log.e("in Diagnosis ", "in Diagnosis");

                            Master master = new Master();
                            //  master.setCategoryid(rowobj.getString("TestName"));
                            master.setName(rowobj.getString("TestName"));
                            master.setTestId(rowobj.getString("TestId"));
                            master.setLabId(rowobj.getString("LabId"));
                            if (rowobj.has("filepath"))
                                master.setFilepath(rowobj.getString("filepath"));
                            selectedmasterdata.add(master);
                        } else if (opt.getOptionType().equalsIgnoreCase("TreatmentProcedure")) {
                            Log.e("in TreatmentProcedure ", "in TreatmentProcedure");

                            Master master = new Master();
                            //  master.setCategoryid(rowobj.getString("TestName"));
                            master.setName(rowobj.getString("TreatmentProcedureName"));
                            master.setCategoryid(rowobj.getString("TreatmentProcedureId"));
                                   /* master.setLabId(rowobj.getString("LabId"));
                                    if(rowobj.has("filepath"))
                                        master.setFilepath(rowobj.getString("filepath"));*/
                            selectedmasterdata.add(master);
                        }
                    } else {
                        Log.e("rowobj  null", "info ");
                        JSONArray rowarray = obj.optJSONArray("row");
                        if (opt.getOptionType().equalsIgnoreCase("Diagnosis")) {
                            Log.e("in Diagnosis ", "in Diagnosis");
                            for (int l = 0; l < rowarray.length(); l++) {
                                Master master = new Master();
                                master.setCategoryid(rowarray.getJSONObject(l).getString("DiagnosisId"));
                                master.setName(rowarray.getJSONObject(l).getString("DiagnosisName"));
                                selectedmasterdata.add(master);
                            }
                        } else if (opt.getOptionType().equalsIgnoreCase("Test")) {
                            Log.e("in Test ", "in Test");
                            try {
                                Log.e("row array ", "row array " + rowarray.toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            for (int l = 0; l < rowarray.length(); l++) {
                                Master master = new Master();
                                master.setName(rowarray.getJSONObject(l).getString("TestName"));
                                master.setTestId(rowarray.getJSONObject(l).getString("TestId"));
                                master.setLabId(rowarray.getJSONObject(l).getString("LabId"));
                                if (rowarray.getJSONObject(l).has("filepath"))
                                    master.setFilepath(rowarray.getJSONObject(l).getString("filepath"));
                                selectedmasterdata.add(master);
                            }
                        } else if (opt.getOptionType().equalsIgnoreCase("Life Style")) {

                        }

                        else if (opt.getOptionType().equalsIgnoreCase("TreatmentProcedure")) {
                            Log.e("in Test ", "in Test");
                            try {
                                Log.e("row array ", "row array " + rowarray.toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            for (int l = 0; l < rowarray.length(); l++) {
                                Master master = new Master();
                                master.setName(rowarray.getJSONObject(l).getString("TreatmentProcedureName"));
                                master.setCategoryid(rowarray.getJSONObject(l).getString("TreatmentProcedureId"));
                                selectedmasterdata.add(master);
                            }
                        }
                    }
                } else {
                    Log.e("obj not row ", "info");
                }
            } else {
                Log.e("obj  null", "info");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        String ans = gson.toJson(selectedmasterdata);
        opt.setOptionValue(ans);
        q.setAnswer(ans);

        //  mRecyclerviewdiagnosis.setAdapter(customadapter);
        RecyclerView mRecyclerviewcustom = new RecyclerView(TemplateQuestionActivity.this);
        LinearLayoutManager mLyaoutmanager = new LinearLayoutManager(TemplateQuestionActivity.this);
        mRecyclerviewcustom.setLayoutManager(mLyaoutmanager);
        final TestAdapter customadapter = new TestAdapter(TemplateQuestionActivity.this, selectedmasterdata, 0, new TestAdapter.OnItemClickListener() {
            @Override
            public void onItemClick() {
                Gson gson = new Gson();
                String ans = gson.toJson(selectedmasterdata);
                opt.setOptionValue(ans);
                q.setAnswer(ans);
            }
        });
        mRecyclerviewcustom.setAdapter(customadapter);
        layout.addView(mRecyclerviewcustom);
        layout.addView(autotxt);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) mRecyclerviewcustom.getLayoutParams();
        marginLayoutParams.setMargins(padding, 0, 0, 20);
        mRecyclerviewcustom.setLayoutParams(marginLayoutParams);

        autotxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // onShowPopup(v, mdataset, q);
                ShowPopup(autotxt, mdataset, opt, customadapter, selectedmasterdata, 1, q, masterkey);
            }
        });
    }

    public void addcustomautocompletetextview(final Option opt, final Questions q, final ArrayList<Master> mdataset, final String masterkey) {

        final AutoCompleteTextView autotxt = new AutoCompleteTextView(this);
        autotxt.setFocusable(false);
        autotxt.setMinLines(2);
//        if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//            autotxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittextshape_grayborder));
//        } else {
//            autotxt.setBackground(getResources().getDrawable(R.drawable.edittextshape_grayborder));
//        }

        autotxt.setFocusable(false);
        autotxt.setThreshold(0);
        autotxt.setTextSize(fontsize);
        autotxt.setTypeface(typeface);
        autotxt.setHint("Please select...");
        autotxt.setTextColor(getResources().getColor(R.color.textcolor));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.leftMargin = padding;
        autotxt.setLayoutParams(params);
        params.topMargin = 10;
        autotxt.setPadding(10, 10, 10, 10);

        final ArrayList<Master> selectedmasterdata = new ArrayList<>();
        String[] ansdata = opt.getOptionValue().split("^");
        Log.e("testanswer ", "answer " + opt.getOptionValue());
        if (ansdata.length > 1) {
            String[] ans = ansdata[1].split(",");
            for (int i = 0; i < ans.length; i++) {
                selectedmasterdata.add(new Master(ans[i]));
            }
        }

        Gson gson = new Gson();
        String ans = gson.toJson(selectedmasterdata);
        opt.setOptionValue(ans);
        q.setAnswer(ans);

        RecyclerView mRecyclerview = new RecyclerView(TemplateQuestionActivity.this);
        LinearLayoutManager mLyaoutmanager = new LinearLayoutManager(TemplateQuestionActivity.this);
        mRecyclerview.setLayoutManager(mLyaoutmanager);

        final TestAdapter testadapter = new TestAdapter(TemplateQuestionActivity.this, selectedmasterdata, 0, new TestAdapter.OnItemClickListener() {
            @Override
            public void onItemClick() {
                Gson gson = new Gson();
                String ans = gson.toJson(selectedmasterdata);
                opt.setOptionValue(ans);
                q.setAnswer(ans);
            }
        });
        mRecyclerview.setAdapter(testadapter);
        layout.addView(mRecyclerview);
        layout.addView(autotxt);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) mRecyclerview.getLayoutParams();
        marginLayoutParams.setMargins(padding, 0, 0, 20);
        mRecyclerview.setLayoutParams(marginLayoutParams);
        autotxt.setTag(selectedmasterdata);

        autotxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPopup(autotxt, mdataset, opt, testadapter, selectedmasterdata, 3, q, masterkey);
            }
        });
    }

    public void addcustomautocompletetextview(final Option opt, final Questions q, final String masterkey, final ArrayList<Master> mdataset) {
        final AutoCompleteTextView autotxt = new AutoCompleteTextView(this);
        autotxt.setFocusable(false);
        autotxt.setMinLines(2);
//        if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//            autotxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittextshape_grayborder));
//        } else {
//            autotxt.setBackground(getResources().getDrawable(R.drawable.edittextshape_grayborder));
//        }

        autotxt.setFocusable(false);
        autotxt.setThreshold(0);
        autotxt.setTextSize(fontsize);
        autotxt.setTypeface(typeface);
        autotxt.setHint("Please select...");
        autotxt.setTextColor(getResources().getColor(R.color.textcolor));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.leftMargin = padding;
        autotxt.setLayoutParams(params);
        params.topMargin = 10;
        autotxt.setPadding(10, 10, 10, 10);
        Log.e("custom autocomplet ", "custom autocomplete ans " + opt.getOptionValue());
        final ArrayList<Master> selectedmasterdata = new ArrayList<>();
      /*  String[] ansdata = opt.getOptionValue().split("^");
        if (ansdata.length > 1) {
            String[] ans = ansdata[1].split(",");
            for (int i = 0; i < ans.length; i++) {
                selectedmasterdata.add(new Master(ans[i]));
            }
        }*/
        // String[] ansdata = opt.getOptionValue().split("^");
        // if (ansdata.length > 1) {
        String[] ans1 = opt.getOptionValue().split(",");
        for (int i = 0; i < ans1.length; i++) {
            if (ans1[i].length() > 0)
                selectedmasterdata.add(new Master(ans1[i]));
        }
        //}
        Gson gson = new Gson();
        String ans = gson.toJson(selectedmasterdata);
        opt.setOptionValue(ans);
        q.setAnswer(ans);

        RecyclerView mRecyclerview = new RecyclerView(TemplateQuestionActivity.this);
        LinearLayoutManager mLyaoutmanager = new LinearLayoutManager(TemplateQuestionActivity.this);
        mRecyclerview.setLayoutManager(mLyaoutmanager);

        final TestAdapter testadapter = new TestAdapter(TemplateQuestionActivity.this, selectedmasterdata, 0, new TestAdapter.OnItemClickListener() {
            @Override
            public void onItemClick() {
                Gson gson = new Gson();
                String ans = gson.toJson(selectedmasterdata);
                opt.setOptionValue(ans);
                q.setAnswer(ans);
            }
        });

        mRecyclerview.setAdapter(testadapter);
        layout.addView(mRecyclerview);
        layout.addView(autotxt);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) mRecyclerview.getLayoutParams();
        marginLayoutParams.setMargins(padding, 0, 0, 20);
        mRecyclerview.setLayoutParams(marginLayoutParams);
        autotxt.setTag(selectedmasterdata);

        autotxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPopup(autotxt, mdataset, opt, testadapter, selectedmasterdata, 3, q, masterkey);
            }
        });
    }

    public void addautocompletetextviewwitbodypartv1(final ArrayList<Master> mdataset, final String name, final Option opt, final Questions q, final ArrayList<Master> secondmater) {
        final AutoCompleteTextView autotxt = new AutoCompleteTextView(this);
        autotxt.setFocusable(false);
        autotxt.setMinLines(2);
//        if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//            autotxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittextshape_grayborder));
//        } else {
//            autotxt.setBackground(getResources().getDrawable(R.drawable.edittextshape_grayborder));
//        }

        autotxt.setFocusable(false);
        autotxt.setThreshold(0);
        autotxt.setTextSize(fontsize);
        autotxt.setTypeface(typeface);
        autotxt.setHint("Please select...");
        autotxt.setTextColor(getResources().getColor(R.color.textcolor));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.leftMargin = padding;
        autotxt.setLayoutParams(params);
        params.topMargin = 10;
        autotxt.setPadding(10, 10, 10, 10);

        final ArrayList<Combineddata> selectedmasterdata = new ArrayList<>();
        try {
            JSONObject obj = opt.getXmlObj();
            Log.e("find test ", "obj " + obj.toString());
            if (obj != null) {
                Log.e("obj nt null", "info" + obj.toString());
                if (obj.has("row")) {
                    Log.e("obj has row ", "info");
                    JSONObject rowobj = obj.optJSONObject("row");
                    if (rowobj != null) {
                        if (opt.getOptionType().equalsIgnoreCase("TreatmentProcedureWithBodypart")) {
                            Log.e("in Diagnosis ", "in Diagnosis");
                            Combineddata combineddata = new Combineddata();
                            combineddata.setCatid(rowobj.getString("TreatmentProcedureId"));
                            combineddata.setName(rowobj.getString("TreatmentProcedureName"));
                            combineddata.setBodypart(rowobj.getString("BodyPart"));
                            selectedmasterdata.add(combineddata);
                        } else if (opt.getOptionType().equalsIgnoreCase("DiagnosisWithBodypart")) {
                            Log.e("in Diagnosis ", "in Diagnosis");

                            Combineddata combineddata = new Combineddata();
                            combineddata.setCatid(rowobj.getString("DiagnosisId"));
                            combineddata.setName(rowobj.getString("DiagnosisName"));
                            combineddata.setBodypart(rowobj.getString("BodyPart"));
                            selectedmasterdata.add(combineddata);
                        } else if (opt.getOptionType().equalsIgnoreCase("TestWithLab")) {
                            Log.e("in TestWithLab ", "in TestWithLab");

                            Combineddata combineddata = new Combineddata();
                            combineddata.setCatid(rowobj.getString("LabId"));
                            combineddata.setName(rowobj.getString("LabName"));
                            combineddata.setBodypart(rowobj.getString("TestName"));
                            selectedmasterdata.add(combineddata);
                        }
                    } else {
                        Log.e("rowobj  null", "info ");
                        JSONArray rowarray = obj.optJSONArray("row");
                        if (opt.getOptionType().equalsIgnoreCase("DiagnosisWithBodypart")) {
                            Log.e("in Diagnosis ", "in Diagnosis");
                            for (int l = 0; l < rowarray.length(); l++) {
                                Combineddata combinedData = new Combineddata();
                                combinedData.setCatid(rowarray.getJSONObject(l).getString("DiagnosisId"));
                                combinedData.setName(rowarray.getJSONObject(l).getString("DiagnosisName"));
                                combinedData.setBodypart(rowarray.getJSONObject(l).getString("BodyPart"));
                                selectedmasterdata.add(combinedData);
                            }
                        } else if (opt.getOptionType().equalsIgnoreCase("TreatmentProcedureWithBodypart")) {
                            Log.e("in Test ", "in Test");
                            try {
                                Log.e("row array ", "row array " + rowarray.toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            for (int l = 0; l < rowarray.length(); l++) {
                                Combineddata combinedData = new Combineddata();
                                combinedData.setCatid(rowarray.getJSONObject(l).getString("TreatmentProcedureId"));
                                combinedData.setName(rowarray.getJSONObject(l).getString("TreatmentProcedureName"));
                                combinedData.setBodypart(rowarray.getJSONObject(l).getString("BodyPart"));
                                selectedmasterdata.add(combinedData);
                            }
                        } else if (opt.getOptionType().equalsIgnoreCase("TestWithLab")) {
                            Log.e("in Test ", "in Test");
                            try {
                                Log.e("row array ", "row array " + rowarray.toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            for (int l = 0; l < rowarray.length(); l++) {
                                Combineddata combinedData = new Combineddata();
                                combinedData.setCatid(rowarray.getJSONObject(l).getString("LabId"));
                                combinedData.setName(rowarray.getJSONObject(l).getString("LabName"));
                                combinedData.setBodypart(rowarray.getJSONObject(l).getString("TestName"));
                                selectedmasterdata.add(combinedData);
                            }
                        }
                    }
                } else {
                    Log.e("obj not row ", "info");
                }
            } else {
                Log.e("obj  null", "info");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        String ans = gson.toJson(selectedmasterdata);
        opt.setOptionValue(ans);
        q.setAnswer(ans);

        RecyclerView mRecyclerview = new RecyclerView(TemplateQuestionActivity.this);
        LinearLayoutManager mLyaoutmanager = new LinearLayoutManager(TemplateQuestionActivity.this);
        mRecyclerview.setLayoutManager(mLyaoutmanager);

        final CombinedDataAdapter testadapter = new CombinedDataAdapter(TemplateQuestionActivity.this, selectedmasterdata, 0, new CombinedDataAdapter.OnItemClickListener() {
            @Override
            public void onItemClick() {
                Gson gson = new Gson();
                String ans = gson.toJson(selectedmasterdata);
                opt.setOptionValue(ans);
                q.setAnswer(ans);
            }
        });

        mRecyclerview.setAdapter(testadapter);
        layout.addView(mRecyclerview);
        layout.addView(autotxt);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) mRecyclerview.getLayoutParams();
        marginLayoutParams.setMargins(padding, 0, 0, 20);
        mRecyclerview.setLayoutParams(marginLayoutParams);
        autotxt.setTag(selectedmasterdata);

        autotxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Showcombineddata(autotxt, mdataset, opt, testadapter, selectedmasterdata, 3, q, secondmater);
            }
        });
    }

    public void addautocompletetextviewwitbodypart(final ArrayList<Master> mdataset, final String name, final Option opt, final Questions q) {
        LinearLayout layout1 = new LinearLayout(TemplateQuestionActivity.this);
        layout1.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        layout1.setLayoutParams(params);

        LinearLayout layout2 = new LinearLayout(TemplateQuestionActivity.this);
        layout2.setOrientation(LinearLayout.VERTICAL);
        layout2.setLayoutParams(params);
        TextView txt = new TextView(getApplicationContext());
        txt.setText(Html.fromHtml(name));
        txt.setTextColor(getResources().getColor(R.color.Black));
        layout2.addView(txt);

        final AutoCompleteTextView autotxt = new AutoCompleteTextView(this);
//        if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//            autotxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittextshape_grayborder));
//        } else {
//            autotxt.setBackground(getResources().getDrawable(R.drawable.edittextshape_grayborder));
//        }
        final ArrayAdapter<Master> adapterggeneric = new ArrayAdapter<Master>(TemplateQuestionActivity.this, android.R.layout.simple_list_item_1, mdataset);
        autotxt.setAdapter(adapterggeneric);
        autotxt.setThreshold(0);
        autotxt.setLayoutParams(params);
        autotxt.setTextSize(fontsize);
        autotxt.setTypeface(typeface);
        autotxt.setTextColor(getResources().getColor(R.color.textcolor));
        LinearLayout.LayoutParams paramsautotxt = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        paramsautotxt.leftMargin = padding;
        paramsautotxt.topMargin = 10;
        autotxt.setPadding(10, 10, 10, 10);
        autotxt.setLayoutParams(paramsautotxt);
        final AutoCompleteTextView autotxt1 = new AutoCompleteTextView(this);
//
//        if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//            autotxt1.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittextshape_grayborder));
//        } else {
//            autotxt1.setBackground(getResources().getDrawable(R.drawable.edittextshape_grayborder));
//        }

        final ArrayList<Master> masterdata = parsemaster(DatabaseHelper.getInstance(TemplateQuestionActivity.this).getMasterdata("Body Part"));
        autotxt1.setTextSize(fontsize);
        autotxt1.setTypeface(typeface);
        autotxt1.setTextColor(getResources().getColor(R.color.textcolor));
        autotxt1.setLayoutParams(paramsautotxt);
        autotxt1.setPadding(10, 10, 10, 10);
        final ArrayAdapter<Master> adapterggeneric1 = new ArrayAdapter<Master>(TemplateQuestionActivity.this, android.R.layout.simple_list_item_1, masterdata);
        autotxt1.setAdapter(adapterggeneric1);
        autotxt1.setThreshold(0);
        autotxt1.setLayoutParams(params);
        layout2.addView(autotxt);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        LinearLayout layout3 = new LinearLayout(TemplateQuestionActivity.this);
        layout3.setOrientation(LinearLayout.VERTICAL);

        TextView txt1 = new TextView(getApplicationContext());
        txt1.setText(Html.fromHtml("Route"));
        txt1.setTextColor(getResources().getColor(R.color.Black));
        layout3.addView(txt1);
        layout3.addView(autotxt1);
        params1.setMargins(10, 10, 0, 0);

        layout3.setLayoutParams(params1);
        layout1.addView(layout2);
        layout1.addView(layout3);
        layout.addView(layout1);

        JSONObject obj1 = new JSONObject();
        q.setAnswer(obj1.toString());
        autotxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                try {
                    JSONObject obj = new JSONObject(q.getAnswer());
                    if (name.equalsIgnoreCase("Diagnosis")) {
                        obj.put("DiagnosisId", getId(autotxt.getText().toString(), mdataset));
                        obj.put("DiagnosisName", autotxt.getText().toString());
                    } else {
                        obj.put("TreatmentProcedureId", getId(autotxt.getText().toString(), mdataset));
                        obj.put("TreatmentProcedureName", autotxt.getText().toString());
                    }

                    q.setAnswer(obj.toString());
                    opt.setOptionValue(obj.toString());
                    Log.e("ans", "ans " + q.getAnswer());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        autotxt1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                try {
                    JSONObject obj = new JSONObject(q.getAnswer());
                    obj.put("ID", getId(autotxt1.getText().toString(), mdataset));
                    obj.put("BodyPart", autotxt1.getText().toString());
                    q.setAnswer(obj.toString());
                    opt.setOptionValue(obj.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public String getId(String name, ArrayList<Master> masterdata) {
        for (int i = 0; i < masterdata.size(); i++) {
            if (name.equalsIgnoreCase(masterdata.get(i).getName()))
                return masterdata.get(i).getCategoryid();
        }
        return "0";
    }

    public void addautocompletetextviewwitlab(final ArrayList<Master> mdataset, String name, final Option opt, final Questions q) {
        LinearLayout layout1 = new LinearLayout(TemplateQuestionActivity.this);
        layout1.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        layout1.setLayoutParams(params);
        LinearLayout layout2 = new LinearLayout(TemplateQuestionActivity.this);
        layout2.setOrientation(LinearLayout.VERTICAL);
        layout2.setLayoutParams(params);
        TextView txt = new TextView(getApplicationContext());
        txt.setText(Html.fromHtml(name));
        txt.setTextColor(getResources().getColor(R.color.Black));
        layout2.addView(txt);

        final AutoCompleteTextView autotxt = new AutoCompleteTextView(this);
//        if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//            autotxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittextshape_grayborder));
//        } else {
//            autotxt.setBackground(getResources().getDrawable(R.drawable.edittextshape_grayborder));
//        }

        final ArrayAdapter<Master> adapterggeneric = new ArrayAdapter<Master>(TemplateQuestionActivity.this, android.R.layout.simple_list_item_1, mdataset);
        autotxt.setAdapter(adapterggeneric);
        autotxt.setThreshold(0);
        autotxt.setLayoutParams(params);
        autotxt.setTextSize(fontsize);
        autotxt.setTypeface(typeface);
        autotxt.setTextColor(getResources().getColor(R.color.textcolor));
        LinearLayout.LayoutParams paramsautotxt = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        paramsautotxt.leftMargin = padding;
        autotxt.setLayoutParams(params);
        paramsautotxt.topMargin = 10;
        autotxt.setPadding(10, 10, 10, 10);
        AutoCompleteTextView autotxt1 = new AutoCompleteTextView(this);
//        if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//            autotxt1.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittextshape_grayborder));
//        } else {
//            autotxt1.setBackground(getResources().getDrawable(R.drawable.edittextshape_grayborder));
//        }
        autotxt1.setTextSize(fontsize);
        autotxt1.setTypeface(typeface);
        autotxt1.setTextColor(getResources().getColor(R.color.textcolor));
        autotxt.setPadding(10, 10, 10, 10);
        autotxt1.setLayoutParams(paramsautotxt);

        final ArrayList<Master> masterdata = new ArrayList<>();// parsemaster(DatabaseHelper.getInstance(TemplateQuestionActivity.this).getMasterdata("Lab"));
        JSONArray array1 = DatabaseHelper.getInstance(TemplateQuestionActivity.this).getMaster(Constants.LAB);

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Master>>() {
        }.getType();
        ArrayList<Master> dataset = gson.fromJson(array1.toString(), type);
        if (dataset != null)
            masterdata.addAll(dataset);

        final ArrayAdapter<Master> adapterggeneric1 = new ArrayAdapter<Master>(TemplateQuestionActivity.this, android.R.layout.simple_list_item_1, masterdata);
        autotxt1.setAdapter(adapterggeneric1);
        autotxt1.setThreshold(0);
        autotxt1.setLayoutParams(params);
        layout2.addView(autotxt);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);

        LinearLayout layout3 = new LinearLayout(TemplateQuestionActivity.this);
        layout3.setOrientation(LinearLayout.VERTICAL);

        TextView txt1 = new TextView(getApplicationContext());
        txt1.setText(Html.fromHtml("Lab"));
        txt1.setTextColor(getResources().getColor(R.color.Black));
        layout3.addView(txt1);
        layout3.addView(autotxt1);
        params1.setMargins(10, 0, 0, 0);

        layout3.setLayoutParams(params1);
        layout1.addView(layout2);
        layout1.addView(layout3);
        layout.addView(layout1);

        JSONObject obj = new JSONObject();
        q.setAnswer(obj.toString());
      /*  autotxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    JSONObject obj = new JSONObject(q.getOptionValue());

                    obj.put("Id", ((Master) parent.getAdapter().getItem(position)).getTestId());
                    obj.put("TestName", ((Master) parent.getAdapter().getItem(position)).getName());
                    q.setOptionValue(obj.toString());
                    opt.setOptionValue(obj.toString());
                    Log.e("ans", "ans " + q.getOptionValue());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });*/
        autotxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                try {
                    JSONObject obj = new JSONObject(q.getAnswer());
                    obj.put("Id", 0);
                    for (int i = 0; i < mdataset.size(); i++) {
                        if (autotxt.getText().toString().equalsIgnoreCase(mdataset.get(i).getName())) {
                            obj.put("Id", mdataset.get(i).getTestId());
                            break;
                        }
                    }
                    obj.put("TestName", autotxt.getText().toString());
                    q.setAnswer(obj.toString());
                    opt.setOptionValue(obj.toString());
                    Log.e("ans", "ans " + q.getAnswer());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

      /*  autotxt1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    JSONObject obj = new JSONObject(q.getOptionValue());
                    obj.put("LabId", ((Master) parent.getAdapter().getItem(position)).getLabId());
                    obj.put("LabName", ((Master) parent.getAdapter().getItem(position)).getName());

                    q.setOptionValue(obj.toString());
                    opt.setOptionValue(obj.toString());
                    Log.e("ans", "ans " + q.getOptionValue());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });*/

        autotxt1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                try {
                    JSONObject obj = new JSONObject(q.getAnswer());
                    obj.put("Id", 0);
                    for (int i = 0; i < masterdata.size(); i++) {
                        if (autotxt.getText().toString().equalsIgnoreCase(masterdata.get(i).getName())) {
                            obj.put("Id", masterdata.get(i).getLabId());
                            break;
                        }
                    }
                    obj.put("TestName", autotxt.getText().toString());
                    q.setAnswer(obj.toString());
                    opt.setOptionValue(obj.toString());
                    Log.e("ans", "ans " + q.getAnswer());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        /*autotxt1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    JSONObject obj = new JSONObject(q.getOptionValue());
                    obj.put("LabId", masterdata.get(position).getCategoryid());
                    obj.put("LabName", masterdata.get(position).getName());
                    q.setOptionValue(obj.toString());
                    opt.setOptionValue(obj.toString());
                    Log.e("ans", "ans " + q.getOptionValue());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
    }

    public void addspinner(final String[] data, final Option opt, final Questions q) {
        final Spinner sp = new Spinner(this);
        sp.setPrompt("Select one");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        sp.setAdapter(adapter);
        layout.addView(sp);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                q.setAnswer(opt.getOptionId() + "^" + data[sp.getSelectedItemPosition()]);
                opt.setOptionValue(opt.getOptionId() + "^" + data[sp.getSelectedItemPosition()]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                if (requestCode == 101) {
                    final Uri uri = data.getData();
                    String filePath = PathUtil.getPath(context, uri);
                    File f = new File(filePath);
                    FileData fdata = new FileData();
                    fdata.setFilename(f.getName());
                    fdata.setFilepath(f.getAbsolutePath());
                    filedata.add(fdata);
                    fileadapter.notifyDataSetChanged();
                    Log.e("path ", "path " + " " + filePath);
                }
            } catch (Exception e) {
                e.printStackTrace();
                e.printStackTrace();
            }
        }
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            //HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            //THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else return null;
    }

    public void browse() {
        Button bt = new Button(this);
        if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            bt.setBackgroundDrawable(getResources().getDrawable(R.drawable.roundedtextview));
        } else {
            bt.setBackground(getResources().getDrawable(R.drawable.roundedtextview));
        }
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("*/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 101);
            }
        });
        RecyclerView mRecyclerview = new RecyclerView(TemplateQuestionActivity.this);
        GridLayoutManager mLyaoutmanager = new GridLayoutManager(TemplateQuestionActivity.this, 2);
        mRecyclerview.setLayoutManager(mLyaoutmanager);
        fileadapter = new FileAdapter(TemplateQuestionActivity.this, filedata);
        mRecyclerview.setAdapter(fileadapter);
        layout.addView(mRecyclerview);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 50, 1);
        params.gravity = Gravity.RIGHT;
        bt.setPadding(0, 0, 0, 0);
        bt.setLayoutParams(params);
        bt.setText("Browse");
        bt.setTextColor(Color.WHITE);
        layout.addView(bt);
    }

    public void adddrug(final boolean isbodypart, String q) {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        LinearLayout layout3 = new LinearLayout(TemplateQuestionActivity.this);
        layout3.setOrientation(LinearLayout.HORIZONTAL);
        layout3.setLayoutParams(params);
        layout3.setPadding(0, 0, 10, 0);

        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ImageView img = new ImageView(TemplateQuestionActivity.this);
        img.setImageDrawable(getResources().getDrawable(R.drawable.ic_addcolor));
        params1.setMargins(10, 0, 10, 0);
  /*      params1.leftMargin=5;
        params1.rightMargin=10;
        params1.topMargin=10;*/
        params1.gravity = Gravity.CENTER;
        img.setLayoutParams(params1);

        try {
            TextView txt = new TextView(getApplicationContext());
            txt.setText(Html.fromHtml(q));
            txt.setTextSize(fontsize);
            txt.setTypeface(Fonter.getTypefacesemibold(TemplateQuestionActivity.this));
            txt.setTextColor(getResources().getColor(R.color.textcolor));
            //  LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            // params.setMargins(0, 10, 0, 10);
            txt.setLayoutParams(params);
            layout3.addView(txt);
        } catch (Exception e) {
            e.printStackTrace();
            reporterror(tag, e.toString());
        }
       /* try {
            TextView txt = new TextView(getApplicationContext());
            txt.setText(Html.fromHtml(q));
            txt.setTextSize(fontsize);
            txt.setLayoutParams(params);
            txt.setTextColor(getResources().getColor(R.color.red));
            layout3.addView(txt);
        } catch (Exception e) {
            e.printStackTrace();
            reporterror(tag, e.toString());
        }*/
        layout3.addView(img);
        layout.addView(layout3);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TemplateQuestionActivity.this, AddRxActivity.class);
                intent.putExtra("isbodypart", isbodypart);
                intent.putExtra("visitid", visitid);
                intent.putExtra("visitdate", visitdate);
                intent.putExtra("templatename", mdataset.get(sp1.getSelectedItemPosition()).getTemplateName());
                startActivity(intent);
            }
        });
      /*  Button bt = new Button(this);
        if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            bt.setBackgroundDrawable(getResources().getDrawable(R.drawable.roundedtextview));
        } else {
            bt.setBackground(getResources().getDrawable(R.drawable.roundedtextview));
        }

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TemplateQuestionActivity.this, AddRxActivity.class);
                intent.putExtra("isbodypart", isbodypart);
                startActivity(intent);
            }
        });*/

    /*    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 50, 1);
        params.gravity = Gravity.RIGHT;
        bt.setLayoutParams(params);
        bt.setText("Add Rx");
        bt.setTextColor(Color.WHITE);
        bt.setPadding(0, 0, 0, 0);*/
        mRecyclerviewdrug = new RecyclerView(TemplateQuestionActivity.this);
        LinearLayoutManager mLyaoutmanager = new LinearLayoutManager(TemplateQuestionActivity.this);
        mRecyclerviewdrug.setLayoutManager(mLyaoutmanager);
        drugadapter = new DrugAdapter(TemplateQuestionActivity.this, GlobalValues.drugdata, 0);
        mRecyclerviewdrug.setAdapter(drugadapter);
        mRecyclerviewdrug.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(TemplateQuestionActivity.this, R.drawable.recycler_divider)));
        layout.addView(mRecyclerviewdrug);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) mRecyclerviewdrug.getLayoutParams();
        marginLayoutParams.setMargins(padding, 0, 0, 20);
        mRecyclerviewdrug.setLayoutParams(marginLayoutParams);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (drugadapter != null) {
            // Log.e("drug", "drug adapter" + GlobalValues.drugdata.size());
            drugadapter.notifyDataSetChanged();
        } else {
            // Log.e("drug", "drug adapter null");
        }

      /*  if (GlobalValues.selectedoctor != null && editdoc != null) {
            editdoc.setText(GlobalValues.selectedoctor.getName());
        }*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GlobalValues.drugdata.clear();
      /*  GlobalValues.diagnosis.clear();
        GlobalValues.test.clear();*/
    }

    public void addcurrentvisit(Calendar myCalendar) {
        // Log.e("current visit callled ", "currentvisitcalled ");
        Visits v = new Visits();
        v.setPatientid(GlobalValues.selectedpt.getPatientid());
        v.setDBPatientid(GlobalValues.selectedpt.getId());
        Calendar cal = Calendar.getInstance();
        String myFormat = "yyyy-MM-dd'T'HH:mm:ss"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        v.setVisitdate(sdf.format(myCalendar.getTime()));
        v.setVisitid(0);
        int visit = 1;
        if (visitdata.size() > 0) {
            visit = DatabaseHelper.getInstance(TemplateQuestionActivity.this).getmaxvisitcount(GlobalValues.selectedpt.getId()) + 1;
        }

        v.setVisit(visit);
        v.setDrname(GlobalValues.DrName);
        v.setSync(0);

        long id = DatabaseHelper.getInstance(TemplateQuestionActivity.this).savevisitdata(Integer.toString(v.getDBPatientid()), 0, v.getcontentvalues());
        v.setId((int) id);
        visitdata.add(0, v);
      /*  adaptervisit.notifyDataSetChanged();
        if (visitdata.size() > 0)
            sp2.setSelection(0);*/

        adaptervisit = new ArrayAdapter<Visits>(this, android.R.layout.simple_dropdown_item_1line, visitdata);
        sp2.setAdapter(adaptervisit);
        if (visitdata.size() > 0)
            sp2.setSelection(0);

        if (visitdata.size() > 1)
            bt3.setVisibility(View.VISIBLE);
        else bt3.setVisibility(View.GONE);

    }

    public void adddata() {
        try {
            AlertDialog alertDialog = new AlertDialog.Builder(TemplateQuestionActivity.this).create(); //Read Update
            alertDialog.setTitle("Add Visit");
            View v = LayoutInflater.from(TemplateQuestionActivity.this).inflate(R.layout.list_item_dialog_edittext, null);
            final EditText e1 = v.findViewById(R.id.edittext_1);
            e1.setPadding(10, 10, 10, 10);

            e1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            e1.setFocusable(false);

            final Calendar myCalendar = Calendar.getInstance();
            final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    // TODO Auto-generated method stub
                    try {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String myFormat = "yyyy-MM-dd"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        e1.setText(sdf.format(myCalendar.getTime()));
                    } catch (Exception e) {
                        e.printStackTrace();
                        reporterror(tag, e.toString());
                    }
                }
            };

            e1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    try {
                        new DatePickerDialog(TemplateQuestionActivity.this, date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        reporterror(tag, e.toString());
                    }
                }
            });

            alertDialog.setView(v);
            alertDialog.setButton("Add", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        if (InternetUtils.getInstance(TemplateQuestionActivity.this).available()) {
                            JSONObject obj = new JSONObject();
                            obj.put("VisitDate", e1.getText().toString());
                            obj.put("Patientid", GlobalValues.selectedpt.getPatientid());
                            obj.put("UserId", GlobalValues.docid);
                            obj.put("practiceid", GlobalValues.branchid);
                            obj.put("connectionid", Constants.projectid);
                            genloading("loading...");
                            ConnectionManager.getInstance(TemplateQuestionActivity.this).AddVisit(obj.toString(), 0);
                        } else {
                            addcurrentvisit(myCalendar);
                          /*  Visits v = new Visits();
                            v.setPatientid(GlobalValues.selectedpt.getPatientid());
                            v.setDBPatientid(GlobalValues.selectedpt.getId());
                            v.setDBPatientid(GlobalValues.selectedpt.getId());
                            Calendar cal = Calendar.getInstance();
                            String myFormat = "yyyy-MM-dd'T'HH:mm:ss"; //In which you need put here
                            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                            v.setVisitdate(sdf.format(myCalendar.getTime()));
                            //  Log.e("visitdate ","visitdate "+v.getVisitdate());
                            v.setVisitid(0);
                            int visit = 1;
                            if (visitdata.size() > 0)
                                visit = (visitdata.get((visitdata.size() - 1)).getVisit() + 1);

                            v.setVisit(visit);
                            v.setDrname(GlobalValues.DrName);
                            v.setSync(0);
                            visitdata.add(0, v);
                            adaptervisit.notifyDataSetChanged();
                            DatabaseHelper.getInstance(TemplateQuestionActivity.this).savevisitdata(Integer.toString(v.getPatientid()), 0, v.getcontentvalues());*/
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
            reporterror(tag, e.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_print:
                isprint = true;
                type = 3;
                submitdata();
                break;
            case R.id.menu_action_whatsapp:
                isprint = true;
                type = 2;
                submitdata();
                break;
            case R.id.menu_action_mail:
                isprint = true;
                type = 1;
                submitdata();
                break;
            case R.id.menu_action_setting:
                isprint = true;
                type = 4;
                submitdata();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void gotoprint() {
        if (isprint) {
            isprint = false;
            if (DatabaseHelper.getInstance(TemplateQuestionActivity.this).issettingupdated(mdataset.get(sp1.getSelectedItemPosition()).getTemplateId()) == 0) {
                Intent intent = new Intent(TemplateQuestionActivity.this, PrintActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                intent.putExtra("dbvisitid", dbvisitid);
                intent.putExtra("visitdate", visitdate);
                intent.putExtra("type", type);
                intent.putExtra("templateid", Integer.toString(mdataset.get(sp1.getSelectedItemPosition()).getTemplateId()));
                startActivity(intent);
            } else {
                if (type == 4) {
                    Intent intent = new Intent(TemplateQuestionActivity.this, PrintActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    intent.putExtra("dbvisitid", dbvisitid);
                    intent.putExtra("visitdate", visitdate);
                    intent.putExtra("templateid", Integer.toString(mdataset.get(sp1.getSelectedItemPosition()).getTemplateId()));
                    startActivity(intent);
                } else {
                    initializefonts();
                    createvisitdataPdf(visitdate, type, answerobjdata);
                }
            }
        }
    }

    public void addfollowup() {

    }

    class upload extends AsyncTask<Void, Void, String>  {
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

                for (int i = 0; i < filedata.size(); i++) {
                    String filepath = filedata.get(i).getFilepath();
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
                        filedata.get(i).setFilepath("http://theclinic.techama.in/EMRDocuments/Lab/" + testName);
                        System.out.println("File is uploaded successfully");
                        Log.e("res", "File is uploaded successfully ");
                    } else {
                        isuploaded = false;
                        System.out.println("File uploading failed");
                        Log.e("res", "File uploading failed ");
                    }
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
                    submitdata();
                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong please try later", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
