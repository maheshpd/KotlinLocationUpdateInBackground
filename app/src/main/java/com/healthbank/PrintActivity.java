package com.healthbank;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.healthbank.adapter.CombinedDataAdapter;
import com.healthbank.adapter.DrugAdapter;
import com.healthbank.adapter.FileAdapter;
import com.healthbank.classes.Combineddata;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class PrintActivity extends ActivityCommon {
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
    Group group;
    ArrayList<String> custommasterdata;
    ArrayAdapter<String> custommasteradapter;
    ArrayList<Group> grouparray = new ArrayList<>();
    String visitid = "0";
    String visitdate = "";
    ArrayList<Visits> visitdata = new ArrayList<>();
    LinearLayout layout2;
    TestAdapter testadapter;
    TestAdapter diagnosisadapter;
    int fontsize = 16;
    Typeface typeface;
    int padding = 40;
    int dbvisitid = 0;
    String  templateid="0";
    ArrayList<Drug> drugdata;
    ArrayList<Master> test;
    ArrayList<Master> diagnosis;
    ArrayList<Questions> qdata;
    int type = 3;
    JSONObject answerobjdata = new JSONObject();
    EditText editdoc = null;
    RecyclerView mRecyclerviewdrug;
    RecyclerView mRecyclerviewtest;
    RecyclerView mRecyclerviewdiagnosis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);
        setmaterialDesign();
        back();
        initializefonts();
        attachUI();
    }

    public String gettodaysdate() {
        Calendar cal = Calendar.getInstance();
        String myFormat = "yyyy-mm-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        return sdf.format(cal.getTime());
    }

    private void attachUI() {
        qdata = new ArrayList<>();
        drugdata = new ArrayList<>();
        test = new ArrayList<>();
        diagnosis = new ArrayList<>();
        Bundle b = getIntent().getExtras();
        if (b != null) {
            if (b.containsKey("dbvisitid"))
                dbvisitid = b.getInt("dbvisitid");

            if (b.containsKey("type"))
                type = b.getInt("type");

            if (b.containsKey("templateid"))
                templateid = b.getString("templateid");

            if (b.containsKey("visitdate")) {
                visitdate = b.getString("visitdate");
                setTitle(GlobalValues.selectedpt.getFirstName() + " " + visitdate);
            }
        }

        typeface = Fonter.getTypefaceregular(PrintActivity.this);
      /*  Bundle b = getIntent().getExtras();
        visitdate = gettodaysdate();
        if (b != null) {
            visitid = b.getString("visitid");
            visitdate = b.getString("visitdate");
        }*/

        custommasterdata = new ArrayList<>();
        filedata = new ArrayList<>();
        prescriptiondata = new ArrayList<>();
       /* templateSelection = (Spinner) findViewById(R.id.spinner_1);
        visitSelection = (Spinner) findViewById(R.id.spinner_2);*/
        scrollView = findViewById(R.id.scrollview);
        layout = findViewById(R.id.layout_1);
        layout2 = findViewById(R.id.layout_2);
      /*  templateQuestionSubmitBtn = (Button) findViewById(R.id.button_1);
        bt2 = (Button) findViewById(R.id.button_2);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  adddata();
            }
        });*/

        mdataset = new ArrayList<>();
        refreshdata();
       /* templateadapter = new ArrayAdapter<Template>(this, android.R.mainLayout.simple_dropdown_item_1line, mdataset);
        templateSelection.setAdapter(templateadapter);

        adaptervisit = new ArrayAdapter<Visits>(this, android.R.mainLayout.simple_dropdown_item_1line, visitdata);
        visitSelection.setAdapter(adaptervisit);*/

   /*     if (InternetUtils.getInstance(PrintActivity.this).available()) {
            try {
                genloading("loading");
                JSONObject obj = new JSONObject();
                obj.put("PracticeId", GlobalValues.branchid);
                obj.put("Grouptype", "");
                obj.put("Speciality", "");
                obj.put("TempName", "");
                obj.put("pagenumber", "1");
                obj.put("pagesize", "100");
                ConnectionManager.getInstance(PrintActivity.this).getQuestionTemplates(obj.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/

      /*  templateSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    mainLayout.removeAllViews();
                    grouparray.clear();
                    visitid = "0";
                    visitdate = gettodaysdate();
                    if (((Template) parent.getAdapter().getItem(position)).getTemplateName().equalsIgnoreCase("Visit Record")) {
                        layout2.setVisibility(View.VISIBLE);
                        try {
                            if (adaptervisit != null) {
                                if (visitdata.size() > visitSelection.getSelectedItemPosition()) {
                                    visitid = Integer.toString(visitdata.get(visitSelection.getSelectedItemPosition()).getVisitid());
                                    visitdate = DateUtils.parseDateNew(visitdata.get(position).getVisitdate(), "yyyy-MM-dd'T'HH:mm:ss", "yyyy-mm-dd");
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        layout2.setVisibility(View.GONE);
                        for (int i=(visitdata.size()-1);i>=0;i--){
                            if (visitdata.get(i).getVisit() == 1) {
                            {
                                visitid = Integer.toString(visitdata.get(i).getVisitid());
                                visitdate = DateUtils.parseDateNew(visitdata.get(i).getVisitdate(), "yyyy-MM-dd'T'HH:mm:ss", "yyyy-mm-dd");
                                break;
                            }
                        }
                    }
                    try {
                        genloading("loading");
                        JSONObject obj = new JSONObject();
                        obj.put("TemplateId", ((Template) parent.getAdapter().getItem(position)).getTemplateId());
                        obj.put("patientid", GlobalValues.selectedpt.getPatientid());
                        obj.put("VisitNo", visitid);
                        ConnectionManager.getInstance(PrintActivity.this).GetTemplateQuestionAnswer(obj.toString());
                    }catch (Exception e)
                    {
                    e.printStackTrace();
                    }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });*/

/*
        visitSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Log.e("url click ", "click ");
                    mainLayout.removeAllViews();
                    //   grouparray.clear();
                    //  visitid=((Visits)parent.getAdapter().getItem(position)).getVisit();

                    //   visitdate = DateUtils.parseDateNew(((Visits)parent.getAdapter().getItem(position)).getVisitdate(), "yyyy-MM-dd'T'HH:mm:ss", "dd MMM yyyy");
                    visitid = Integer.toString(visitdata.get(position).getVisitid());
                    visitdate = DateUtils.parseDateNew(visitdata.get(position).getVisitdate(), "yyyy-MM-dd'T'HH:mm:ss", "yyyy-mm-dd");

                    //  visitdate=((Visits)parent.getAdapter().getItem(position)).getVisitdate();
                   *//* if (((Template) parent.getAdapter().getItem(position)).getTemplateName().equalsIgnoreCase("Visit Record")) {
                        layout2.setVisibility(View.VISIBLE);

                    } else {
                        layout2.setVisibility(View.GONE);

                    }*//*
                    genloading("loading");
                    JSONObject obj = new JSONObject();
                    obj.put("TemplateId", mdataset.get(templateSelection.getSelectedItemPosition()).getTemplateId());
                    obj.put("patientid", GlobalValues.selectedpt.getPatientid());
                    obj.put("VisitNo", visitid);
                    ConnectionManager.getInstance(PrintActivity.this).GetTemplateQuestionAnswer(obj.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });*/
    }

    public void setdata() {
        try {
            layout.removeAllViews();
            drugdata.clear();
            test.clear();
            diagnosis.clear();
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
                for (int i = 0; i < subGroups.size(); i++) {
                    if (!subGroups.get(i).getGroupName().equalsIgnoreCase("null"))
                        addtextviewheader(subGroups.get(i).getGroupName(), 0);
                    ArrayList<Questions> questions = subGroups.get(i).getQuestions();
                    JSONArray array = new JSONArray();
                    array = answerobjdata.optJSONArray("QuestionData");
                    //   Log.e("array2222222222 ", "array " + array.toString());
                    addquestions(questions, i, array);
                }
            } else {
                JSONArray array = answerobjdata.optJSONArray("AnswerData");
                Log.e("array111111 ", "array " + array.toString());
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
                            addquestions(questions, i, array1);
                        }
                    }
                }
            }

            /*if(isrefill)
                submitdata();*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refreshdata() {
        try {
            Log.e("refreshdata ", "in refreshdata ");
            JSONArray array2 = DatabaseHelper.getInstance(PrintActivity.this).gettemplatedata(Integer.toString(dbvisitid), Integer.toString(GlobalValues.selectedpt.getId()),templateid);
            if (array2.length() > 0) {
                answerobjdata = new JSONObject(array2.getJSONObject(0).getString("jsondata"));
                setdata();
            } else {
              //  int tempid = mdataset.get(templateSelection.getSelectedItemPosition()).getTemplateId();
                JSONArray array = DatabaseHelper.getInstance(PrintActivity.this).getfreshtemplatedata(templateid);
                if (array.length() > 0) {
                    answerobjdata = new JSONObject(array.getJSONObject(0).getString("jsondata"));
                    setdata();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addquestions(ArrayList<Questions> questions, int i, JSONArray array) {

        {
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
                        Log.e("data ", "data " + ans);
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
                addtextview((j + 1) + "." + questions.get(j).getName(), questions.get(j));

                ArrayList<Option> optdata = questions.get(j).getOption();
                for (int k = 0; k < optdata.size(); k++) {
                    Log.e("optid ", "optid " + optdata.get(k).getOptionType() + "  " + OptionAnswer.getAnswer(optans, Integer.toString(optdata.get(k).getOptionId())));
                    optdata.get(k).setOptionValue(OptionAnswer.getAnswer(optans, Integer.toString(optdata.get(k).getOptionId())));
                    if (optdata.get(k).getOptionType().equalsIgnoreCase("CheckBox")) {

                        if (optdata.get(k).getOptionName().equalsIgnoreCase(optdata.get(k).getOptionValue())) {
                            optdata.get(k).setIsselected(true);
                        }

                        addCheckBox(optdata.get(k));
                    } else if (optdata.get(k).getOptionType().equalsIgnoreCase("CheckBoxList")) {
                        Log.e("anscheckboxlist ", "" + optdata.get(k).getOptionValue());
                        String[] ansarray = new String[0];
                        if (optdata.get(k).getOptionValue().length() > 0) {
                            ansarray = optdata.get(k).getOptionValue().split(",");

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
                        if (!isradiobtadded) {
                            isradiobtadded = true;
                            ArrayList<Option> optdata1 = new ArrayList<>();
                            for (int l = k; l < optdata.size(); l++) {
                                if (optdata.get(l).getOptionType().equalsIgnoreCase("RadioButton")) {
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
                            opt.setOptionName(splitarray[l]);
                            optdata1.add(opt);
                        }
                        addradioGroup(optdata1, questions.get(j), optdata.get(k));
                    } else if (optdata.get(k).getOptionType().equalsIgnoreCase("TextBox") || optdata.get(k).getOptionType().equalsIgnoreCase("Description") || optdata.get(k).getOptionType().equalsIgnoreCase("Editor")) {
                        addEditText(i, questions.get(j).getName(), optdata.get(k), false, questions.get(j));
                    } else if (optdata.get(k).getOptionType().equalsIgnoreCase("NoTextBox")) {
                        addEditText(i, questions.get(j).getName(), optdata.get(k), true, questions.get(j));
                    } else if (optdata.get(k).getOptionType().equalsIgnoreCase("Date")) {
                        adddateview(optdata.get(k), questions.get(j));
                    } else if (optdata.get(k).getOptionType().equalsIgnoreCase("Life Style")) {
                        //ArrayList<Master> masterdata = parsemaster(DatabaseHelper.getInstance(PrintActivity.this).getMasterdata("Life Style"));
                        JSONArray array1 = DatabaseHelper.getInstance(PrintActivity.this).getMaster(Constants.LifeStyle);
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
                        //   ArrayList<Master> masterdata = parsemaster(DatabaseHelper.getInstance(PrintActivity.this).getMasterdata("treatment procedures"));
                        JSONArray array1 = DatabaseHelper.getInstance(PrintActivity.this).getMaster("treatment procedures");
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
                        //    ArrayList<Master> masterdata = parsemaster(DatabaseHelper.getInstance(PrintActivity.this).getMasterdata("Diagnosis"));
                        JSONArray array1 = DatabaseHelper.getInstance(PrintActivity.this).getMaster(Constants.Diagnosis);
                        ArrayList<Master> masterdata = new ArrayList<>();
                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<Master>>() {
                        }.getType();
                        ArrayList<Master> dataset = gson.fromJson(array1.toString(), type);
                        if (dataset != null)
                            masterdata.addAll(dataset);
                        addautocompletetextviewtest(masterdata, questions.get(j), optdata.get(k), Constants.DIAGNOSIS);
                    } else if (optdata.get(k).getOptionType().equalsIgnoreCase("Test")) {
                        //  ArrayList<Master> masterdata = parsemaster(DatabaseHelper.getInstance(PrintActivity.this).getMasterdata("Test"));
                        JSONArray array1 = DatabaseHelper.getInstance(PrintActivity.this).getMaster(Constants.Test);

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

                    } else if (optdata.get(k).getOptionType().equalsIgnoreCase("Medications")) {
                /*    if (v != null)
                        mainLayout.removeView(v);*/
                        adddrug(true, (j + 1) + "." + questions.get(j).getName());
                    } else if (optdata.get(k).getOptionType().equalsIgnoreCase("Drug")) {
                                        /*ArrayList<Master> masterdata = parsemaster(DatabaseHelper.getInstance(PrintActivity.this).getMasterdata("Drug"));
                                        addautocompletetextview(masterdata);*/
                        adddrug(true, (j + 1) + "." + questions.get(j).getName());
                    } else if (optdata.get(k).getOptionType().equalsIgnoreCase("MedicationsWithBodypart")) {
                        adddrug(true, (j + 1) + "." + questions.get(j).getName());
                    } else if (optdata.get(k).getOptionType().equalsIgnoreCase("TreatmentProcedureWithBodypart")) {
                        optdata.get(k).setXmlObj(SaveAnswerdata.getXMLobj(data, Integer.toString(questions.get(j).getQid())));
                        ArrayList<Master> bodypartmasterdata = new ArrayList<Master>();// = parsemaster(DatabaseHelper.getInstance(PrintActivity.this).getMasterdata("Body Part"));
                        JSONArray array1 = DatabaseHelper.getInstance(PrintActivity.this).getMaster(Constants.treatmentprocedures);
                        ArrayList<Master> masterdata = new ArrayList<>();
                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<Master>>() {
                        }.getType();
                        ArrayList<Master> dataset = gson.fromJson(array1.toString(), type);
                        if (dataset != null)
                            masterdata.addAll(dataset);

                        JSONArray array2 = DatabaseHelper.getInstance(PrintActivity.this).getMaster("Body Part");
                        ArrayList<Master> dataset1 = gson.fromJson(array2.toString(), type);
                        if (dataset != null)
                            bodypartmasterdata.addAll(dataset1);
                        addautocompletetextviewwitbodypartv1(masterdata, "Treatment Procedure", optdata.get(k), questions.get(j), bodypartmasterdata);
                    } else if (optdata.get(k).getOptionType().equalsIgnoreCase("DiagnosisWithBodypart")) {
                        optdata.get(k).setXmlObj(SaveAnswerdata.getXMLobj(data, Integer.toString(questions.get(j).getQid())));
                        ArrayList<Master> bodypartmasterdata = new ArrayList<Master>();
                        ArrayList<Master> masterdata = new ArrayList<>();

                        addautocompletetextviewwitbodypartv1(masterdata, "Diagnosis", optdata.get(k), questions.get(j), bodypartmasterdata);
                    } else if (optdata.get(k).getOptionType().equalsIgnoreCase("TestWithLab")) {
                        optdata.get(k).setXmlObj(SaveAnswerdata.getXMLobj(data, Integer.toString(questions.get(j).getQid())));
                        ArrayList<Master> bodypartmasterdata = new ArrayList<Master>();
                        ArrayList<Master> masterdata = new ArrayList<>();
                        addautocompletetextviewwitbodypartv1(masterdata, "TestWithLab", optdata.get(k), questions.get(j), bodypartmasterdata);
                    } else if (optdata.get(k).getOptionType().equalsIgnoreCase("DropDownList")) {
                        String[] arraydata = optdata.get(k).getOptionList().split(",");
                        addspinner(arraydata, optdata.get(k), questions.get(j));
                    } else if (optdata.get(k).getOptionType().equalsIgnoreCase("FileUpload")) {
                        browse();
                    } else if (optdata.get(k).getOptionType().equalsIgnoreCase("CustomAutoComplete") || optdata.get(k).getOptionType().equalsIgnoreCase("AutoComplete")) {
                        custommasterdata = new ArrayList<>();//DatabaseHelper.getInstance(PrintActivity.this).getCustommasterdata();
                        addcustomautocompletetextview(optdata.get(k), questions.get(j), "custom");
                    } else if (optdata.get(k).getOptionType().equalsIgnoreCase("Doctor list")) {
                        ArrayList<Doctor> mdataset = new ArrayList<>();
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
                                                        drugadapter = new DrugAdapter(PrintActivity.this, GlobalValues.drugdata,1);
                                                        mRecyclerviewdrug.setAdapter(drugadapter);
                                                    } else {
                                                        Log.e("DrugAdapter is null", "DrugAdapter is null");
                                                    }
                                                }
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
                                                        drugadapter = new DrugAdapter(PrintActivity.this, GlobalValues.drugdata,1);
                                                        mRecyclerviewdrug.setAdapter(drugadapter);
                                                    } else {
                                                        // Log.e("DrugAdapter is null", "DrugAdapter is null");
                                                    }
                                                }
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

    }

       /* ArrayList<SaveAnswerdata> data = new ArrayList<>();
        for (int j = 0; j < array.length(); j++) {
            try {
                data.add(new SaveAnswerdata(array.getJSONObject(j)));
                Log.e("data1", "data1" + array.getJSONObject(j).toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (int j = 0; j < questions.size(); j++) {
            boolean isradiobtadded = false;
            String ans = SaveAnswerdata.getOptionValue(data, Integer.toString(questions.get(j).getQid()));
            ArrayList<OptionAnswer> optans = new ArrayList<>();
            if (ans != null)
                if (!ans.equalsIgnoreCase("null") || !ans.equalsIgnoreCase("")) {
                    Log.e("data ", "data " + ans);
                    String[] optanswers = ans.split("~");
                    for (int k = 0; k < optanswers.length; k++) {
                        try {
                            Log.e("id11 ", "data " + optanswers[k]);
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
            addtextview((j + 1) + "." + questions.get(j).getName(), questions.get(j));
            ArrayList<Option> optdata = questions.get(j).getOption();
            for (int k = 0; k < optdata.size(); k++) {
                Log.e("optid ", "optid " + optdata.get(k).getOptionType() + "  " + OptionAnswer.getOptionValue(optans, Integer.toString(optdata.get(k).getOptionId())));
                optdata.get(k).setOptionValue(OptionAnswer.getOptionValue(optans, Integer.toString(optdata.get(k).getOptionId())));
                if (optdata.get(k).getOptionType().equalsIgnoreCase("CheckBox")) {
                    addCheckBox(optdata.get(k));
                } else if (optdata.get(k).getOptionType().equalsIgnoreCase("CheckBoxList")) {
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
                        optdata1.add(opt);
                        addCheckBox(opt);
                    }
                    questions.get(j).setOption(optdata1);
                } else if (optdata.get(k).getOptionType().equalsIgnoreCase("RadioButton")) {
                    if (!isradiobtadded) {
                        isradiobtadded = true;
                        ArrayList<Option> optdata1 = new ArrayList<>();
                        for (int l = k; l < optdata.size(); l++) {
                            if (optdata.get(l).getOptionType().equalsIgnoreCase("RadioButton")) {
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
                        opt.setOptionName(splitarray[l]);
                        optdata1.add(opt);
                    }
                    addradioGroup(optdata1, questions.get(j), optdata.get(k));
                } else if (optdata.get(k).getOptionType().equalsIgnoreCase("TextBox") || optdata.get(k).getOptionType().equalsIgnoreCase("Description") || optdata.get(k).getOptionType().equalsIgnoreCase("Editor")) {
                    addEditText(i, questions.get(j).getName(), optdata.get(k), false, questions.get(j));
                } else if (optdata.get(k).getOptionType().equalsIgnoreCase("NoTextBox")) {
                    addEditText(i, questions.get(j).getName(), optdata.get(k), true, questions.get(j));
                } else if (optdata.get(k).getOptionType().equalsIgnoreCase("Date")) {
                    adddateview(optdata.get(k), questions.get(j));
                } else if (optdata.get(k).getOptionType().equalsIgnoreCase("Life Style")) {
                    //   ArrayList<Master> masterdata = parsemaster(DatabaseHelper.getInstance(PrintActivity.this).getMasterdata("Life Style"));
                    ArrayList<Master> masterdata = new ArrayList<>();
                    addautocompletetextview(masterdata, questions.get(j), optdata.get(k));
                } else if (optdata.get(k).getOptionType().equalsIgnoreCase("TreatmentProcedure")) {
                    // ArrayList<Master> masterdata = parsemaster(DatabaseHelper.getInstance(PrintActivity.this).getMasterdata("treatment procedures"));
                    ArrayList<Master> masterdata = new ArrayList<>();
                    addautocompletetextview(masterdata, questions.get(j), optdata.get(k));
                } else if (optdata.get(k).getOptionType().equalsIgnoreCase("Diagnosis")) {
                    ArrayList<Master> masterdata = new ArrayList<>();
                    //   ArrayList<Master> masterdata = parsemaster(DatabaseHelper.getInstance(PrintActivity.this).getMasterdata("Diagnosis"));
                    addautocompletetextviewtest(masterdata, questions.get(j), optdata.get(k), 2);
                } else if (optdata.get(k).getOptionType().equalsIgnoreCase("Packages")) {
                    //  ArrayList<Master> masterdata = parsemaster(DatabaseHelper.getInstance(PrintActivity.this).getMasterdata("Packages"));
                    ArrayList<Master> masterdata = new ArrayList<>();
                    addautocompletetextview(masterdata, questions.get(j), optdata.get(k));
                } else if (optdata.get(k).getOptionType().equalsIgnoreCase("Medications")) {
                    adddrug(true);
                } else if (optdata.get(k).getOptionType().equalsIgnoreCase("Drug")) {
                                        *//*ArrayList<Master> masterdata = parsemaster(DatabaseHelper.getInstance(PrintActivity.this).getMasterdata("Drug"));
                                        addautocompletetextview(masterdata);*//*
                    adddrug(true);
                } else if (optdata.get(k).getOptionType().equalsIgnoreCase("Test")) {
                    //  ArrayList<Master> masterdata = parsemaster(DatabaseHelper.getInstance(PrintActivity.this).getMasterdata("Test"));
                    ArrayList<Master> masterdata = new ArrayList<>();
                    addautocompletetextviewtest(masterdata, questions.get(j), optdata.get(k), 1);
                } else if (optdata.get(k).getOptionType().equalsIgnoreCase("MedicationsWithBodypart")) {
                                        *//*ArrayList<Master> masterdata = parsemaster(DatabaseHelper.getInstance(PrintActivity.this).getMasterdata("Drug"));
                                        addautocompletetextviewwitbodypart(masterdata, "Drug");*//*
                    adddrug(true);
                } else if (optdata.get(k).getOptionType().equalsIgnoreCase("TreatmentProcedureWithBodypart")) {
                    //ArrayList<Master> masterdata = parsemaster(DatabaseHelper.getInstance(PrintActivity.this).getMasterdata("treatment procedures"));
                    ArrayList<Master> masterdata = new ArrayList<>();
                    addautocompletetextviewwitbodypart(masterdata, "Treatment Procedure", optdata.get(k), questions.get(j));
                } else if (optdata.get(k).getOptionType().equalsIgnoreCase("DiagnosisWithBodypart")) {
                    // ArrayList<Master> masterdata = parsemaster(DatabaseHelper.getInstance(PrintActivity.this).getMasterdata("Diagnosis"));
                    ArrayList<Master> masterdata = new ArrayList<>();
                    addautocompletetextviewwitbodypart(masterdata, "Diagnosis", optdata.get(k), questions.get(j));
                } else if (optdata.get(k).getOptionType().equalsIgnoreCase("TestWithLab")) {
                    // ArrayList<Master> masterdata = parsemaster(DatabaseHelper.getInstance(PrintActivity.this).getMasterdata("Test"));
                    ArrayList<Master> masterdata = new ArrayList<>();
                    addautocompletetextviewwitlab(masterdata, "Test", optdata.get(k), questions.get(j));
                } else if (optdata.get(k).getOptionType().equalsIgnoreCase("DropDownList")) {
                    String[] arraydata = optdata.get(k).getOptionList().split(",");
                    addspinner(arraydata, optdata.get(k), questions.get(j));
                } else if (optdata.get(k).getOptionType().equalsIgnoreCase("FileUpload")) {
                    browse();
                } else if (optdata.get(k).getOptionType().equalsIgnoreCase("CustomAutoComplete") || optdata.get(k).getOptionType().equalsIgnoreCase("AutoComplete")) {
                    custommasterdata = new ArrayList<>();
                    addcustomautocompletetextview(optdata.get(k), questions.get(j));
                } else if (optdata.get(k).getOptionType().equalsIgnoreCase("Doctor list")) {
                    adddoc(optdata.get(k), questions.get(j));
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
                                    Log.e("obj nt null", "info" + obj.toString());
                                    if (obj.has("row")) {
                                        Log.e("obj has row ", "info");
                                        JSONObject rowobj = obj.optJSONObject("row");
                                        if (rowobj != null) {
                                            Log.e("rowobj nt null", "info " + optdata.get(k).getOptionType());
                                            if (optdata.get(k).getOptionType().equalsIgnoreCase("Medications") && data.get(k1).getOptionType().equalsIgnoreCase("Medications")) {
                                                Log.e("in Medication ", "in Medication");
                                                Gson gson1 = new Gson();
                                                Type type1 = new TypeToken<Drug>() {
                                                }.getType();
                                                Drug d = gson1.fromJson(rowobj.toString(), type1);
                                                drugdata.add(d);
                                                Log.e("size111 ", "sizze drug " + drugdata.size());
                                                if (mRecyclerviewdrug != null) {
                                                    drugadapter.notifyDataSetChanged();
                                                    drugadapter = new DrugAdapter(PrintActivity.this, drugdata, 0);
                                                    mRecyclerviewdrug.setAdapter(drugadapter);
                                                    Log.e("DrugAdapter is null", " mRecyclerviewdrug DrugAdapter is not null" + drugdata.size());

                                                } else {
                                                    Log.e("DrugAdapter is null", " mRecyclerviewdrug DrugAdapter is null");
                                                }
                                            } else if (optdata.get(k).getOptionType().equalsIgnoreCase("Diagnosis") && data.get(k1).getOptionType().equalsIgnoreCase("Diagnosis")) {
                                                Log.e("in Diagnosis ", "in Diagnosis");
                                                Master master = new Master();
                                                master.setCategoryid(rowobj.getString("DiagnosisId"));
                                                master.setName(rowobj.getString("DiagnosisName"));
                                                diagnosis.add(master);
                                                Log.e("size111 ", "sizze" + drugdata.size());
                                                if (mRecyclerviewdiagnosis != null) {
                                                    diagnosisadapter.notifyDataSetChanged();
                                                    diagnosisadapter = new TestAdapter(PrintActivity.this, diagnosis, 1);
                                                    mRecyclerviewdiagnosis.setAdapter(diagnosisadapter);
                                                } else {
                                                    Log.e("DrugAdapter is null", "DrugAdapter is null");
                                                }
                                            } else if (optdata.get(k).getOptionType().equalsIgnoreCase("Test") && data.get(k1).getOptionType().equalsIgnoreCase("Test")) {
                                                Log.e("in Diagnosis ", "in Diagnosis");
                                                Master master = new Master();
                                                master.setName(rowobj.getString("TestName"));
                                                master.setTestId(rowobj.getString("TestId"));
                                                master.setLabId(rowobj.getString("LabId"));

                                                test.add(master);
                                                Log.e("size111 ", "sizze" + test.size());
                                                if (mRecyclerviewtest != null) {
                                                    testadapter.notifyDataSetChanged();
                                                    testadapter = new TestAdapter(PrintActivity.this, test, 1);
                                                    mRecyclerviewtest.setAdapter(testadapter);
                                                } else {
                                                    Log.e("DrugAdapter is null", "DrugAdapter is null");
                                                }
                                            }
                                        } else {
                                            Log.e("rowobj  null", "info ");
                                            JSONArray rowarray = obj.optJSONArray("row");
                                            if (optdata.get(k).getOptionType().equalsIgnoreCase("Medications") && data.get(k1).getOptionType().equalsIgnoreCase("Medications")) {
                                                Log.e("in Medication1 ", "in Medication1");
                                                Gson gson1 = new Gson();
                                                Type type1 = new TypeToken<ArrayList<Drug>>() {
                                                }.getType();
                                                drugdata = gson1.fromJson(rowarray.toString(), type1);
                                                Log.e("size11 ", "sizze" + drugdata.size());
                                                if (mRecyclerviewdrug != null) {
                                                    drugadapter.notifyDataSetChanged();
                                                    drugadapter = new DrugAdapter(PrintActivity.this, drugdata, 1);
                                                    mRecyclerviewdrug.setAdapter(drugadapter);
                                                } else {
                                                    Log.e("DrugAdapter is null", "DrugAdapter is null");
                                                }
                                            } else if (optdata.get(k).getOptionType().equalsIgnoreCase("Diagnosis") && data.get(k1).getOptionType().equalsIgnoreCase("Diagnosis")) {
                                                Log.e("in Diagnosis ", "in Diagnosis");
                                                for (int l = 0; l < rowarray.length(); l++) {
                                                    Master master = new Master();
                                                    master.setCategoryid(rowarray.getJSONObject(l).getString("DiagnosisId"));
                                                    master.setName(rowarray.getJSONObject(l).getString("DiagnosisName"));
                                                    diagnosis.add(master);
                                                }
                                                Log.e("size111 ", "sizze" + drugdata.size());
                                                if (mRecyclerviewdiagnosis != null) {
                                                    diagnosisadapter.notifyDataSetChanged();
                                                    diagnosisadapter = new TestAdapter(PrintActivity.this, diagnosis, 1);
                                                    mRecyclerviewdiagnosis.setAdapter(diagnosisadapter);
                                                } else {
                                                    Log.e("DrugAdapter is null", "DrugAdapter is null");
                                                }
                                            } else if (optdata.get(k).getOptionType().equalsIgnoreCase("Test") && data.get(k1).getOptionType().equalsIgnoreCase("Test")) {
                                                Log.e("in Test ", "in Test");
                                                for (int l = 0; l < rowarray.length(); l++) {
                                                    Master master = new Master();
                                                    master.setName(rowarray.getJSONObject(l).getString("TestName"));
                                                    master.setTestId(rowarray.getJSONObject(l).getString("TestId"));
                                                    master.setLabId(rowarray.getJSONObject(l).getString("LabId"));
                                                    test.add(master);
                                                }
                                                Log.e("size111 ", "sizze" + drugdata.size());
                                                if (mRecyclerviewtest != null) {
                                                    testadapter.notifyDataSetChanged();
                                                    testadapter = new TestAdapter(PrintActivity.this, test, 1);
                                                    mRecyclerviewtest.setAdapter(testadapter);
                                                } else {
                                                    Log.e("DrugAdapter is null", "DrugAdapter is null");
                                                }
                                            }
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
            qdata = questions;
        }
    }*/
       public void addautocompletetextviewwitbodypartv1(final ArrayList<Master> mdataset, final String name, final Option opt, final Questions q, final ArrayList<Master> secondmater) {
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

           RecyclerView mRecyclerview = new RecyclerView(PrintActivity.this);
           LinearLayoutManager mLyaoutmanager = new LinearLayoutManager(PrintActivity.this);
           mRecyclerview.setLayoutManager(mLyaoutmanager);

           final CombinedDataAdapter testadapter = new CombinedDataAdapter(PrintActivity.this, selectedmasterdata,1);

           mRecyclerview.setAdapter(testadapter);
           layout.addView(mRecyclerview);

           ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) mRecyclerview.getLayoutParams();
           marginLayoutParams.setMargins(padding, 0, 0, 20);
           mRecyclerview.setLayoutParams(marginLayoutParams);
       }

    private void addtextviewheader(String Text, int position) {
        try {
            TextView txt = new TextView(getApplicationContext());
            txt.setText(Text);
            txt.setId(position + 1);
            txt.setGravity(Gravity.CENTER);
            if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                txt.setBackgroundDrawable(getResources().getDrawable(R.drawable.roundedtextview));
            } else {
                txt.setBackground(getResources().getDrawable(R.drawable.roundedtextview));
            }
            txt.setTextColor(getResources().getColor(R.color.White));
            txt.setPadding(10, 10, 10, 10);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.bottomMargin = 5;
            params.topMargin = 10;
            params.gravity = Gravity.CENTER;
            txt.setLayoutParams(params);
            layout.addView(txt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addtextview(String text, final Questions q) {
        try {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            LinearLayout layout1 = new LinearLayout(PrintActivity.this);
            layout1.setOrientation(LinearLayout.HORIZONTAL);
            layout1.setLayoutParams(params);
            layout1.setPadding(padding, 10, 10, 0);
            TextView txt = new TextView(getApplicationContext());
            txt.setText(Html.fromHtml(text));
            txt.setTextColor(getResources().getColor(R.color.red));
            txt.setLayoutParams(params);
            CheckBox cb = new CheckBox(PrintActivity.this);
            int isprint = DatabaseHelper.getInstance(PrintActivity.this).isprint(Integer.toString(q.getQid()), Integer.toString(q.getTemplateId()));
            Log.e("isprint reply", "" + isprint + " " + q.getName());
            if (isprint == 1)
                cb.setChecked(true);
            else
                cb.setChecked(false);
            layout1.addView(txt);
            layout1.addView(cb);

            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Log.e("checked ", "checked " + isChecked);
                    if (isChecked) {
                        DatabaseHelper.getInstance(PrintActivity.this).saveprintsetting(Integer.toString(q.getQid()), Integer.toString(q.getTemplateId()), 1);
                    } else {
                        DatabaseHelper.getInstance(PrintActivity.this).saveprintsetting(Integer.toString(q.getQid()), Integer.toString(q.getTemplateId()), 0);
                    }
                }
            });

            layout.addView(layout1);
        } catch (Exception e) {
            e.printStackTrace();
            // reporterror(tag, e.toString());
        }
    }

    private void devider() {
        try {
            View v = new View(getApplicationContext());
            v.setBackgroundColor(getResources().getColor(R.color.Gray));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
            params.setMargins(0, 5, 0, 0);
            v.setLayoutParams(params);
            layout.addView(v);
        } catch (Exception e) {
            e.printStackTrace();
            // reporterror(tag, e.toString());
        }
    }

    private void addCheckBox(final Option qopt) {
        try {
            if(qopt.isIsselected())
            {
                addtextviewblack(qopt.getOptionName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
     /*   try {
            CheckBox cb = new CheckBox(PrintActivity.this);
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
            mainLayout.addView(cb);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    private View addtextviewblack(String Text) {
        TextView txt = new TextView(getApplicationContext());
        try {
            txt.setText(Html.fromHtml(Text));
            txt.setTextSize(fontsize);
            txt.setTypeface(typeface);
            txt.setTextColor(getResources().getColor(R.color.textcolor));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = padding;
            txt.setLayoutParams(params);
            layout.addView(txt);
        } catch (Exception e) {
            e.printStackTrace();
            //reporterror(tag, e.toString());
        }
        return txt;
    }

    private void addradioGroup(final ArrayList<Option> dataset, final Questions que, final Option opt1) {
        addtextviewblack(opt1.getOptionValue());
    /*    try {
            RadioGroup rg = new RadioGroup(this);
            rg.setOrientation(LinearLayout.VERTICAL);
            for (int i = 0; i < dataset.size(); i++) {
                final RadioButton rdbtn = new RadioButton(this);
                final Option opt = dataset.get(i);
                rdbtn.setId(i);
                rdbtn.setText(dataset.get(i).getOptionName());
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
                            que.setOptionValue(opt.getOptionId() + "^" + opt.getOptionName());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //  Log.e("data","data "+que.getOptionValue());
                    }
                });
            }
            mainLayout.addView(rg);
        } catch (Exception e) {
            e.printStackTrace();
            //   reporterror(tag, e.toString());
        }*/
    }

    private void addEditText(final int position, final String hint, final Option opt, boolean isno, final Questions q) {
        try {
            if (opt.getOptionValue().length() > 0) {
                EditText e1 = new EditText(getApplicationContext());
                e1.setTextSize(fontsize);
                e1.setTypeface(typeface);
                e1.setTextColor(getResources().getColor(R.color.textcolor));
                e1.setPadding(padding, 10, 10, 10);
                e1.setBackgroundColor(getResources().getColor(R.color.white));
                e1.setEnabled(false);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.bottomMargin = 5;
                params.topMargin = 10;

                if (isno)
                    e1.setInputType(InputType.TYPE_CLASS_NUMBER);

               // e1.setMaxLines(200);

                e1.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        q.setAnswer(opt.getOptionId() + "^" + charSequence.toString());
                        opt.setOptionValue(opt.getOptionId() + "^" + charSequence.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

                layout.addView(e1);
                e1.setText(opt.getOptionValue());
            }
            } catch(Exception e){
                e.printStackTrace();
                //  reporterror(tag, e.toString());
            }

    }

    private void adddoc(final Option opt, final Questions q) {
        try {
            editdoc = new EditText(getApplicationContext());
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
                    Intent intent = new Intent(PrintActivity.this, DoctorActivity.class);
                    startActivity(intent);
                }
            });

            editdoc.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    opt.setOptionValue(opt.getOptionId() + "^" + charSequence.toString());
                    q.setAnswer(opt.getOptionId() + "^" + charSequence.toString());

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            editdoc.setLayoutParams(params);
            if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                editdoc.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittextshape));
            } else {
                editdoc.setBackground(getResources().getDrawable(R.drawable.edittextshape));
            }
            layout.addView(editdoc);
        } catch (Exception e) {
            e.printStackTrace();
            // reporterror(tag, e.toString());
        }
    }

    private void addEditTexteditor(final int position, final String hint, final Option opt, boolean isno) {
        try {
        /*    if (hint != null && !hint.equalsIgnoreCase("null") && hint.length() > 0)
                addtextview(hint);*/

            EditText e1 = new EditText(getApplicationContext());
            e1.setPadding(10, 10, 10, 10);
         //   e1.setGravity(Gravity.TOP);
            e1.setTextColor(getResources().getColor(R.color.Black));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.bottomMargin = 5;
            e1.setBackgroundColor(getResources().getColor(R.color.white));
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
                e1.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittextshape));
            } else {
                e1.setBackground(getResources().getDrawable(R.drawable.edittextshape));
            }
            layout.addView(e1);
        } catch (Exception e) {
            e.printStackTrace();
            //  reporterror(tag, e.toString());
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
            e1.setEnabled(false);
            //    e1.setPadding(10, 10, 10, 10);
            e1.setTextColor(getResources().getColor(R.color.Black));
            e1.setTextSize(fontsize);
            e1.setTypeface(typeface);
            e1.setTextColor(getResources().getColor(R.color.textcolor));
            e1.setPadding(50, 10, 10, 10);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.bottomMargin = 5;
            params.topMargin = 10;
            e1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    q.setAnswer(opt.getOptionId() + "^" + charSequence.toString());
                    opt.setOptionValue(opt.getOptionId() + "^" + charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            e1.setLayoutParams(params);
         /*   if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                e1.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittextshape));
            } else {
                e1.setBackground(getResources().getDrawable(R.drawable.edittextshape));
            }*/
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
                        String myFormat = "yyyy-mm-dd"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        e1.setText(sdf.format(myCalendar.getTime()));
                    } catch (Exception e) {
                        e.printStackTrace();
                        // reporterror(tag, e.toString());
                    }
                }
            };

            e1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    try {
                        new DatePickerDialog(PrintActivity.this, date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        // reporterror(tag, e.toString());
                    }
                }
            });
            e1.setText(opt.getOptionValue());
        } catch (Exception e) {
            e.printStackTrace();
            //  reporterror(tag, e.toString());
        }
    }

    public void addautocompletetextview(ArrayList<Master> mdataset, final Questions q, final Option opt) {
        final AutoCompleteTextView autotxt = new AutoCompleteTextView(this);
        autotxt.setTextSize(fontsize);
        autotxt.setTypeface(typeface);
        autotxt.setTextColor(getResources().getColor(R.color.textcolor));
        autotxt.setPadding(padding, 10, 10, 10);
        autotxt.setEnabled(false);
        if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            autotxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittextshape));
        } else {
            autotxt.setBackground(getResources().getDrawable(R.drawable.edittextshape));
        }
        final ArrayAdapter<Master> adapterggeneric = new ArrayAdapter<Master>(PrintActivity.this, android.R.layout.simple_list_item_1, mdataset);
        autotxt.setAdapter(adapterggeneric);
        autotxt.setThreshold(0);
        layout.addView(autotxt);

        autotxt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("called ", "setOnItemSelectedListener");
                q.setAnswer(opt.getOptionId() + "^" + autotxt.getText().toString());
                opt.setOptionValue(opt.getOptionId() + "^" + autotxt.getText().toString());
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
                opt.setOptionValue(opt.getOptionId() + "^" + s.toString());
            }
        });
    }

    public void addautocompletetextviewtest(final ArrayList<Master> mdataset, final Questions q, final Option opt, final int flag) {
        final AutoCompleteTextView autotxt = new AutoCompleteTextView(this);
        autotxt.setTextSize(fontsize);
        autotxt.setTypeface(typeface);
        autotxt.setTextColor(getResources().getColor(R.color.textcolor));
        if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            autotxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittextshape));
        } else {
            autotxt.setBackground(getResources().getDrawable(R.drawable.edittextshape));
        }
        final ArrayAdapter<Master> adapterggeneric = new ArrayAdapter<Master>(PrintActivity.this, android.R.layout.simple_list_item_1, mdataset);
        autotxt.setAdapter(adapterggeneric);
        autotxt.setThreshold(0);


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
                opt.setOptionValue(opt.getOptionId() + "^" + s.toString());
            }
        });

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        LinearLayout layout2 = new LinearLayout(PrintActivity.this);
        layout2.setOrientation(LinearLayout.VERTICAL);
        layout2.setLayoutParams(params);
        layout2.addView(autotxt);

        Button bt = new Button(this);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 40, 1);
        params1.gravity = Gravity.RIGHT;
        if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            bt.setBackgroundDrawable(getResources().getDrawable(R.drawable.roundedtextview));
        } else {
            bt.setBackground(getResources().getDrawable(R.drawable.roundedtextview));
        }
        bt.setPadding(0, 0, 0, 0);
        params1.setMargins(0, 5, 0, 0);
        bt.setLayoutParams(params1);
        bt.setText("Add");
        bt.setTextColor(Color.WHITE);
        bt.setVisibility(View.GONE);
        autotxt.setVisibility(View.GONE);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = 0;
                for (i = 0; i < mdataset.size(); i++) {
                    if (autotxt.getText().toString().equalsIgnoreCase(mdataset.get(i).getName())) {
                        if (flag == 1)
                            test.add(mdataset.get(i));
                        else
                            diagnosis.add(mdataset.get(i));
                        break;
                    }
                }
                if (i == mdataset.size()) {
                    Master m = new Master();
                    m.setName(autotxt.getText().toString());
                    m.setTestId("0");
                    m.setCategoryid("0");
                    m.setLabId("0");
                    if (flag == 1)
                        test.add(m);
                    else
                        diagnosis.add(m);
                }
                if (flag == 1)
                    testadapter.notifyDataSetChanged();
                else
                    diagnosisadapter.notifyDataSetChanged();

                autotxt.setText("");
            }
        });
        layout2.addView(bt);
        if (flag == 1) {
            mRecyclerviewtest = new RecyclerView(PrintActivity.this);
            LinearLayoutManager mLyaoutmanager = new LinearLayoutManager(PrintActivity.this);
            mRecyclerviewtest.setLayoutManager(mLyaoutmanager);

            testadapter = new TestAdapter(PrintActivity.this, test, 1);
            mRecyclerviewtest.setAdapter(testadapter);
            layout.addView(mRecyclerviewtest);
            ViewGroup.MarginLayoutParams marginLayoutParams =
                    (ViewGroup.MarginLayoutParams) mRecyclerviewtest.getLayoutParams();
            marginLayoutParams.setMargins(padding, 0, 0, 20);
            mRecyclerviewtest.setLayoutParams(marginLayoutParams);
            mRecyclerviewtest.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(PrintActivity.this, R.drawable.recycler_divider)));

        } else {
            mRecyclerviewdiagnosis = new RecyclerView(PrintActivity.this);

            LinearLayoutManager mLyaoutmanager = new LinearLayoutManager(PrintActivity.this);
            mRecyclerviewdiagnosis.setLayoutManager(mLyaoutmanager);
            diagnosisadapter = new TestAdapter(PrintActivity.this, diagnosis, 1);
            mRecyclerviewdiagnosis.setAdapter(diagnosisadapter);
            mRecyclerviewdiagnosis.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(PrintActivity.this, R.drawable.recycler_divider)));
            layout.addView(mRecyclerviewdiagnosis);
            ViewGroup.MarginLayoutParams marginLayoutParams =
                    (ViewGroup.MarginLayoutParams) mRecyclerviewdiagnosis.getLayoutParams();
            marginLayoutParams.setMargins(padding, 0, 0, 20);
            mRecyclerviewdiagnosis.setLayoutParams(marginLayoutParams);
        }
        layout.addView(layout2);

    }

    public void addcustomautocompletetextview(final Option opt, final Questions q, final ArrayList<Master> mdataset, final String masterkey) {
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

        RecyclerView mRecyclerview = new RecyclerView(PrintActivity.this);
        LinearLayoutManager mLyaoutmanager = new LinearLayoutManager(PrintActivity.this);
        mRecyclerview.setLayoutManager(mLyaoutmanager);

        final TestAdapter testadapter = new TestAdapter(PrintActivity.this, selectedmasterdata,1);
        mRecyclerview.setAdapter(testadapter);

        layout.addView(mRecyclerview);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) mRecyclerview.getLayoutParams();
        marginLayoutParams.setMargins(padding, 0, 0, 20);
        mRecyclerview.setLayoutParams(marginLayoutParams);

    }

    public void addcustomautocompletetextview(final Option opt, final Questions q) {
        final AutoCompleteTextView autotxt = new AutoCompleteTextView(this);
        autotxt.setTextSize(fontsize);
        autotxt.setTypeface(typeface);
        autotxt.setTextColor(getResources().getColor(R.color.textcolor));
        autotxt.setPadding(padding, 10, 10, 10);

        Log.e("AutoCompleteTextview ", "AutoCompleteTextview");
        autotxt.setEnabled(false);
      /*  if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            autotxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittextshape));
        } else {
            autotxt.setBackground(getResources().getDrawable(R.drawable.edittextshape));
        }*/

        custommasteradapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, custommasterdata);
        autotxt.setAdapter(custommasteradapter);
        autotxt.setThreshold(0);
        layout.addView(autotxt);

        autotxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("textchange ", "setOnItemClickListener");
                opt.setOptionValue(opt.getOptionId() + "^" + autotxt.getText().toString());
                q.setAnswer(opt.getOptionId() + "^" + autotxt.getText().toString());
            }
        });
        autotxt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Log.e("textchange ", "setOnItemSelectedListener");
                opt.setOptionValue(opt.getOptionId() + "^" + autotxt.getText().toString());
                q.setAnswer(opt.getOptionId() + "^" + autotxt.getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        autotxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.e("textchange ", "textchangecalled");
                opt.setOptionValue(opt.getOptionId() + "^" + charSequence.toString());
                q.setAnswer(opt.getOptionId() + "^" + charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        autotxt.setText(opt.getOptionValue());
        autotxt.setTextColor(getResources().getColor(R.color.textcolor));
        Log.e("optans ", "optans " + opt.getOptionValue());
    }

    public void addautocompletetextviewwitbodypart(final ArrayList<Master> mdataset, final String name, final Option opt, final Questions q) {
        LinearLayout layout1 = new LinearLayout(PrintActivity.this);
        layout1.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        layout1.setLayoutParams(params);

        LinearLayout layout2 = new LinearLayout(PrintActivity.this);
        layout2.setOrientation(LinearLayout.VERTICAL);
        layout2.setLayoutParams(params);
        TextView txt = new TextView(getApplicationContext());
        txt.setText(Html.fromHtml(name));
        txt.setTextColor(getResources().getColor(R.color.Black));
        layout2.addView(txt);

        final AutoCompleteTextView autotxt = new AutoCompleteTextView(this);
        if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            autotxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittextshape));
        } else {
            autotxt.setBackground(getResources().getDrawable(R.drawable.edittextshape));
        }
        final ArrayAdapter<Master> adapterggeneric = new ArrayAdapter<Master>(PrintActivity.this, android.R.layout.simple_list_item_1, mdataset);
        autotxt.setAdapter(adapterggeneric);
        autotxt.setThreshold(0);
        autotxt.setLayoutParams(params);
        autotxt.setTextSize(fontsize);
        autotxt.setTypeface(typeface);
        autotxt.setTextColor(getResources().getColor(R.color.textcolor));
        final AutoCompleteTextView autotxt1 = new AutoCompleteTextView(this);
        autotxt1.setTextSize(fontsize);
        autotxt1.setTypeface(typeface);
        autotxt1.setTextColor(getResources().getColor(R.color.textcolor));
       /* if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            autotxt1.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittextshape));
        } else {
            autotxt1.setBackground(getResources().getDrawable(R.drawable.edittextshape));
        }*/
        // final ArrayList<Master> masterdata = parsemaster(DatabaseHelper.getInstance(PrintActivity.this).getMasterdata("Body Part"));
        final ArrayList<Master> masterdata = new ArrayList<>();
        final ArrayAdapter<Master> adapterggeneric1 = new ArrayAdapter<Master>(PrintActivity.this, android.R.layout.simple_list_item_1, masterdata);
        autotxt1.setAdapter(adapterggeneric1);
        autotxt1.setThreshold(0);
        autotxt1.setLayoutParams(params);
        layout2.addView(autotxt);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        LinearLayout layout3 = new LinearLayout(PrintActivity.this);
        layout3.setOrientation(LinearLayout.VERTICAL);

        TextView txt1 = new TextView(getApplicationContext());
        txt1.setText(Html.fromHtml("Body Part"));
        txt1.setTextColor(getResources().getColor(R.color.Black));
        layout3.addView(txt1);
        layout3.addView(autotxt1);
        params1.setMargins(10, 0, 0, 0);

        layout3.setLayoutParams(params1);
        layout1.addView(layout2);
        layout1.addView(layout3);
        layout.addView(layout1);

        JSONObject obj1 = new JSONObject();
        q.setAnswer(obj1.toString());
       /* autotxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    JSONObject obj = new JSONObject(q.getOptionValue());
                    if (name.equalsIgnoreCase("Diagnosis")) {
                        obj.put("DiagnosisId", ((Master) parent.getAdapter().getItem(position)).getCategoryid());
                        obj.put("DiagnosisName", ((Master) parent.getAdapter().getItem(position)).getName());
                    } else {
                        obj.put("TreatmentProcedureId", ((Master) parent.getAdapter().getItem(position)).getCategoryid());
                        obj.put("TreatmentProcedureName", ((Master) parent.getAdapter().getItem(position)).getName());
                    }
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
 /*       autotxt1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    JSONObject obj = new JSONObject(q.getOptionValue());
                    obj.put("ID", "");
                    obj.put("BodyPart", ((Master) parent.getAdapter().getItem(position)).getName());


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
        LinearLayout layout1 = new LinearLayout(PrintActivity.this);
        layout1.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        layout1.setLayoutParams(params);
        LinearLayout layout2 = new LinearLayout(PrintActivity.this);
        layout2.setOrientation(LinearLayout.VERTICAL);
        layout2.setLayoutParams(params);
        TextView txt = new TextView(getApplicationContext());
        txt.setText(Html.fromHtml(name));
        txt.setTextColor(getResources().getColor(R.color.Black));
        layout2.addView(txt);

        final AutoCompleteTextView autotxt = new AutoCompleteTextView(this);
        if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            autotxt.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittextshape));
        } else {
            autotxt.setBackground(getResources().getDrawable(R.drawable.edittextshape));
        }

        final ArrayAdapter<Master> adapterggeneric = new ArrayAdapter<Master>(PrintActivity.this, android.R.layout.simple_list_item_1, mdataset);
        autotxt.setAdapter(adapterggeneric);
        autotxt.setThreshold(0);
        autotxt.setLayoutParams(params);
        autotxt.setTextSize(fontsize);
        autotxt.setTypeface(typeface);
        autotxt.setTextColor(getResources().getColor(R.color.textcolor));
        AutoCompleteTextView autotxt1 = new AutoCompleteTextView(this);
        if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            autotxt1.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittextshape));
        } else {
            autotxt1.setBackground(getResources().getDrawable(R.drawable.edittextshape));
        }
        final ArrayList<Master> masterdata = new ArrayList<Master>();//= parsemaster(DatabaseHelper.getInstance(PrintActivity.this).getMasterdata("Lab"));
        final ArrayAdapter<Master> adapterggeneric1 = new ArrayAdapter<Master>(PrintActivity.this, android.R.layout.simple_list_item_1, masterdata);
        autotxt1.setAdapter(adapterggeneric1);
        autotxt1.setThreshold(0);
        autotxt1.setLayoutParams(params);
        autotxt1.setTextSize(fontsize);
        autotxt1.setTypeface(typeface);
        autotxt1.setTextColor(getResources().getColor(R.color.textcolor));
        layout2.addView(autotxt);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);

        LinearLayout layout3 = new LinearLayout(PrintActivity.this);
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
        bt.setVisibility(View.GONE);
       /* if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            bt.setBackgroundDrawable(getResources().getDrawable(R.drawable.roundedtextview));
        } else {
            bt.setBackground(getResources().getDrawable(R.drawable.roundedtextview));
        }*/
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("*/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 101);
            }
        });
        RecyclerView mRecyclerview = new RecyclerView(PrintActivity.this);
        GridLayoutManager mLyaoutmanager = new GridLayoutManager(PrintActivity.this, 2);
        mRecyclerview.setLayoutManager(mLyaoutmanager);
        fileadapter = new FileAdapter(PrintActivity.this, filedata);
        mRecyclerview.setAdapter(fileadapter);
        layout.addView(mRecyclerview);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 40, 1);
        params.gravity = Gravity.RIGHT;
        bt.setPadding(0, 0, 0, 0);
        bt.setLayoutParams(params);
        bt.setText("Browse");
        bt.setTextColor(Color.WHITE);
        layout.addView(bt);
    }

    public void adddrug(final boolean isbodypart) {
        try {
            Button bt = new Button(this);
            if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                bt.setBackgroundDrawable(getResources().getDrawable(R.drawable.roundedtextview));
            } else {
                bt.setBackground(getResources().getDrawable(R.drawable.roundedtextview));
            }
            bt.setVisibility(View.GONE);
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 40, 1);
            params.gravity = Gravity.RIGHT;
            bt.setLayoutParams(params);
            bt.setText("Add Rx");
            bt.setTextColor(Color.WHITE);
            bt.setPadding(0, 0, 0, 0);
            mRecyclerviewdrug = new RecyclerView(PrintActivity.this);
            LinearLayoutManager mLyaoutmanager = new LinearLayoutManager(PrintActivity.this);
            mRecyclerviewdrug.setLayoutManager(mLyaoutmanager);
            drugadapter = new DrugAdapter(PrintActivity.this, drugdata, 1);
            mRecyclerviewdrug.setAdapter(drugadapter);
            mRecyclerviewdrug.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(PrintActivity.this, R.drawable.recycler_divider)));
            mRecyclerviewdrug.setPadding(30, 0, 10, 10);
            layout.addView(mRecyclerviewdrug);

            Log.e("Global drug ", "Global Derug " + drugdata.size());
        } catch (Exception e) {
            Log.e("drug error ", "drug error " + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (drugadapter != null) {
            Log.e("drug", "drug templateadapter" + drugdata.size());
            drugadapter.notifyDataSetChanged();
        } else {
            Log.e("drug", "drug templateadapter null");
        }

        if (GlobalValues.selectedoctor != null && editdoc != null) {
            editdoc.setText(GlobalValues.selectedoctor.getName());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.print, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_print:
                createvisitdataPdf(visitdate, type, answerobjdata);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addautocompletetextviewtest(final ArrayList<Master> mdataset, final Questions q, final Option opt, final String masterkey) {
        Log.e("in addautocomplete","addautocomplete "+opt.getOptionType());
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
                        } else if (opt.getOptionType().equalsIgnoreCase("TreatmentProcedure")) {
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
        RecyclerView mRecyclerviewcustom = new RecyclerView(PrintActivity.this);
        LinearLayoutManager mLyaoutmanager = new LinearLayoutManager(PrintActivity.this);
        mRecyclerviewcustom.setLayoutManager(mLyaoutmanager);
        final TestAdapter customadapter = new TestAdapter(PrintActivity.this, selectedmasterdata,1);
        mRecyclerviewcustom.setAdapter(customadapter);
        layout.addView(mRecyclerviewcustom);

        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) mRecyclerviewcustom.getLayoutParams();
        marginLayoutParams.setMargins(padding, 0, 0, 20);
        mRecyclerviewcustom.setLayoutParams(marginLayoutParams);
    }
    public void addcustomautocompletetextview(final Option opt, final Questions q,final String masterkey) {

        final ArrayList<Master> selectedmasterdata = new ArrayList<>();
        String[] ansdata = opt.getOptionValue().split("^");
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

        RecyclerView mRecyclerview = new RecyclerView(PrintActivity.this);
        LinearLayoutManager mLyaoutmanager = new LinearLayoutManager(PrintActivity.this);
        mRecyclerview.setLayoutManager(mLyaoutmanager);

        final TestAdapter testadapter = new TestAdapter(PrintActivity.this, selectedmasterdata,1);
        mRecyclerview.setAdapter(testadapter);
        layout.addView(mRecyclerview);

        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) mRecyclerview.getLayoutParams();
        marginLayoutParams.setMargins(padding, 0, 0, 20);
        mRecyclerview.setLayoutParams(marginLayoutParams);

    }
    /*public void adddrug(final boolean isbodypart) {
        try {
            Button bt = new Button(this);
            if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                bt.setBackgroundDrawable(getResources().getDrawable(R.drawable.roundedtextview));
            } else {
                bt.setBackground(getResources().getDrawable(R.drawable.roundedtextview));
            }
            bt.setVisibility(View.GONE);
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 40, 1);
            params.gravity = Gravity.RIGHT;
            bt.setLayoutParams(params);
            bt.setText("Add Rx");
            bt.setTextColor(Color.WHITE);
            bt.setPadding(0, 0, 0, 0);
            mRecyclerviewdrug = new RecyclerView(PrintActivity.this);
            LinearLayoutManager mLyaoutmanager = new LinearLayoutManager(PrintActivity.this);
            mRecyclerviewdrug.setLayoutManager(mLyaoutmanager);
            drugadapter = new DrugAdapter(PrintActivity.this, drugdata,1);
            mRecyclerviewdrug.setAdapter(drugadapter);
            mRecyclerviewdrug.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(PrintActivity.this, R.drawable.recycler_divider)));
            mRecyclerviewdrug.setPadding(30, 0, 10, 10);
            mainLayout.addView(mRecyclerviewdrug);

            Log.e("Global drug ", "Global Derug " + drugdata.size());
        } catch (Exception e) {
            Log.e("drug error ", "drug error " + e.toString());
            e.printStackTrace();
        }
    }*/

    public void adddrug(final boolean isbodypart, String q) {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        LinearLayout layout3 = new LinearLayout(PrintActivity.this);
        layout3.setOrientation(LinearLayout.HORIZONTAL);
        layout3.setLayoutParams(params);
        layout3.setPadding(0, 0, 10, 0);

        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ImageView img = new ImageView(PrintActivity.this);
        //   img.setImageDrawable(getResources().getDrawable(R.drawable.ic_addcolor));
        params1.setMargins(10, 0, 10, 0);
  /*      params1.leftMargin=5;
        params1.rightMargin=10;
        params1.topMargin=10;*/
        params1.gravity = Gravity.CENTER;
     /*   img.setLayoutParams(params1);

        try {
            TextView txt = new TextView(getApplicationContext());
            txt.setText(Html.fromHtml(q));
            txt.setTextSize(fontsize);
            txt.setTypeface(Fonter.getTypefacesemibold(PrintActivity.this));
            txt.setTextColor(getResources().getColor(R.color.textcolor));
            //  LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            // params.setMargins(0, 10, 0, 10);
            txt.setLayoutParams(params);
            layout3.addView(txt);
        } catch (Exception e) {
            e.printStackTrace();
        }

        layout3.addView(img);
        mainLayout.addView(layout3);*/

    /*    img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             *//*   Intent intent = new Intent(PrintActivity.this, AddRxActivity.class);
                intent.putExtra("isbodypart", isbodypart);
                startActivity(intent);*//*
            }
        });*/

        mRecyclerviewdrug = new RecyclerView(PrintActivity.this);
        LinearLayoutManager mLyaoutmanager = new LinearLayoutManager(PrintActivity.this);
        mRecyclerviewdrug.setLayoutManager(mLyaoutmanager);
        drugadapter = new DrugAdapter(PrintActivity.this, GlobalValues.drugdata,1);
        mRecyclerviewdrug.setAdapter(drugadapter);
        mRecyclerviewdrug.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(PrintActivity.this, R.drawable.recycler_divider)));
        layout.addView(mRecyclerviewdrug);

        ViewGroup.MarginLayoutParams marginLayoutParams =
                (ViewGroup.MarginLayoutParams) mRecyclerviewdrug.getLayoutParams();
        marginLayoutParams.setMargins(padding, 0, 0, 20);
        mRecyclerviewdrug.setLayoutParams(marginLayoutParams);
    }
    private void adddoc(final Option opt, final Questions q, final ArrayList<Doctor> mdataset) {
        try {
            final EditText editdoc = new EditText(getApplicationContext());
            editdoc.setTypeface(typeface);
            editdoc.setTextSize(fontsize);
            editdoc.setTextColor(getResources().getColor(R.color.textcolor));
            editdoc.setPadding(padding, 10, 10, 10);
            editdoc.setTextColor(getResources().getColor(R.color.Black));
            editdoc.setBackgroundColor(getResources().getColor(R.color.white));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.bottomMargin = 5;
            params.topMargin = 10;
            editdoc.setInputType(InputType.TYPE_CLASS_TEXT);
            editdoc.setFocusable(false);

            editdoc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //ShowPopupdoctor(editdoc, mdataset, opt, q);
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
           /* if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                editdoc.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittextshape_grayborder));
            } else {
                editdoc.setBackground(getResources().getDrawable(R.drawable.edittextshape_grayborder));
            }
*/
            layout.addView(editdoc);
            if(opt.getOptionValue().length()>0)
            {
                String[] array=opt.getOptionValue().split("^");
                if(array.length>0)
                {
                    editdoc.setText(array[1]);
                    q.setAnswer(opt.getOptionId()+"^"+array[1]);
                    opt.setOptionValue(opt.getOptionId()+"^"+array[1]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // reporterror(tag, e.toString());
        }
    }
}
