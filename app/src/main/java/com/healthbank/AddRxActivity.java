package com.healthbank;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.healthbank.adapter.DrugAdapter;
import com.healthbank.adapter.MasterAdapter;
import com.healthbank.classes.Drug;
import com.healthbank.classes.Master;
import com.healthbank.classes.MasterData;
import com.healthbank.database.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

public class AddRxActivity extends ActivityCommon {
    private static final int REQUEST_CODE = 100;
    ImageView img1, img2, img3, img4, img5;
    AutoCompleteTextView autotxt1, autotxt2, autotxt3, autotxt4, autotxt5, autotxt6, autotxt7, autotxt8, autotxt9, autotxt10;
    ArrayList<Master> generic, instruction, quantity, unit, bodypart, doses, duration, lifestyle, lab;//frequency
    JSONObject lifestyleobj;
    TextView txt1;
    RecyclerView mRecyclerview;
    LinearLayoutManager mLayoutmanager;
    DrugAdapter adapter;
    LinearLayout mLayout;
    boolean isbodypart = false;
    int fontsize = 16;
    Typeface typeface;
    int padding = 20;
    String visitid="";
     String       visitdate="";
     String templatename="";
    int popupheight=600;
    View.OnClickListener clk = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startVoiceRecognitionActivity();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rx);
        setmaterialDesign();
        back();
        typeface = Fonter.getTypefaceregular(AddRxActivity.this);
        mRecyclerview = findViewById(R.id.recyclerview_1);
        mLayout = findViewById(R.id.layout_1);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("isbodypart"))
                isbodypart = bundle.getBoolean("isbodypart");

            if (bundle.containsKey("visitid"))
                visitid = bundle.getString("visitid");

            if (bundle.containsKey("visitdate"))
                visitdate = bundle.getString("visitdate");

            if (bundle.containsKey("templatename"))
                templatename = bundle.getString("templatename");
        }

        if (isbodypart)
            mLayout.setVisibility(View.VISIBLE);
        else
            mLayout.setVisibility(View.GONE);

        mLayoutmanager = new LinearLayoutManager(AddRxActivity.this);
        mRecyclerview.setLayoutManager(mLayoutmanager);
        adapter = new DrugAdapter(AddRxActivity.this, GlobalValues.drugdata, 0);
        mRecyclerview.setAdapter(adapter);
        mRecyclerview.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(AddRxActivity.this, R.drawable.recycler_divider)));
        generic = new ArrayList<>();
        // frequency = new ArrayList<>();
        instruction = new ArrayList<>();
        quantity = new ArrayList<>();
        unit = new ArrayList<>();
        bodypart = new ArrayList<>();
        doses = new ArrayList<>();
        duration = new ArrayList<>();
        lab = new ArrayList<>();
        lifestyle = new ArrayList<>();

        generic.addAll(getmaster(Constants.Drug));
        instruction.addAll(getmaster(Constants.Instruction));
        unit.addAll(getmaster(Constants.Unit));
        bodypart.addAll(getmaster(Constants.BodyPart));
        doses.addAll(getmaster(Constants.Doses));
        duration.addAll(getmaster(Constants.Duration));
        lifestyle.addAll(getmaster(Constants.LifeStyle));
        lab.addAll(getmaster(Constants.Lab));

        LinearLayout layout = findViewById(R.id.layout1);
        layout.setVerticalScrollBarEnabled(true);
        autotxt1 = findViewById(R.id.autotxt1);
        ImageView img = findViewById(R.id.imageview_1);
        txt1 = findViewById(R.id.textview_1);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  opendialogadddosage();
            }
        });

        img1 = findViewById(R.id.imageview_1);
        img2 = findViewById(R.id.imageview_2);
        img3 = findViewById(R.id.imageview_3);
        img4 = findViewById(R.id.imageview_4);
        img5 = findViewById(R.id.imageview_5);

        img2.setTag(2);
        img3.setTag(3);
        img4.setTag(4);
        img5.setTag(5);

        img2.setOnClickListener(clk);
        img3.setOnClickListener(clk);
        img4.setOnClickListener(clk);
        img5.setOnClickListener(clk);

        int brandid = DatabaseHelper.getInstance(this).getId("Brand");
        final ArrayList<MasterData> drugnamedata = new ArrayList<>();
        drugnamedata.addAll(DatabaseHelper.getInstance(this).getmstexamination(brandid));
        final ArrayAdapter<MasterData> adaptergdrug = new ArrayAdapter<MasterData>(this, android.R.layout.simple_list_item_1, drugnamedata);
        autotxt1.setAdapter(adaptergdrug);
        autotxt1.setThreshold(0);

        autotxt2 = findViewById(R.id.autotxt2);
        autotxt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowPopup(v, generic, Constants.Drug);
            }
        });

        final ArrayAdapter<Master> adapterggeneric = new ArrayAdapter<Master>(this, android.R.layout.simple_list_item_1, generic);
        autotxt2.setAdapter(adapterggeneric);
        autotxt2.setThreshold(0);

        autotxt2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        autotxt6 = findViewById(R.id.autotxt6);
        final ArrayAdapter<Master> adapterinstruction = new ArrayAdapter<Master>(this, android.R.layout.simple_list_item_1, instruction);
        autotxt6.setAdapter(adapterinstruction);
        autotxt6.setThreshold(0);
        autotxt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowPopup(v, instruction, Constants.Instruction);
            }
        });

        autotxt7 = findViewById(R.id.autotxt7);
        final ArrayAdapter<Master> adapterquantity = new ArrayAdapter<Master>(this, android.R.layout.simple_list_item_1, quantity);
        autotxt7.setAdapter(adapterquantity);
        autotxt7.setThreshold(0);

        autotxt7.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        autotxt3 = findViewById(R.id.autotxt3);
        autotxt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowPopup(v, doses, Constants.Doses);
            }
        });

        autotxt4 = findViewById(R.id.autotxt4);
        final ArrayAdapter<Master> adapterunit = new ArrayAdapter<Master>(this, android.R.layout.simple_list_item_1, unit);
        autotxt4.setAdapter(adapterunit);
        autotxt4.setThreshold(0);
        autotxt4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        autotxt5 = findViewById(R.id.autotxt5);
        final ArrayAdapter<Master> adapterbodypart = new ArrayAdapter<Master>(this, android.R.layout.simple_list_item_1, bodypart);
        autotxt5.setAdapter(adapterbodypart);
        autotxt5.setThreshold(0);
        autotxt5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    lifestyleobj.put("BodyPart", autotxt5.getText());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        autotxt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowPopup(v, bodypart, Constants.BodyPart);
            }
        });
        autotxt8 = findViewById(R.id.autotxt8);
        ArrayAdapter<Master> adapterlifestyle = new ArrayAdapter<Master>(this, android.R.layout.simple_list_item_1, lifestyle);
        autotxt8.setAdapter(adapterlifestyle);
        autotxt8.setThreshold(0);
        autotxt8.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    lifestyleobj.put("LifeStyle", autotxt8.getText().toString());
                    lifestyleobj.put("LifeStyleId", ((Master) (parent.getAdapter().getItem(position))).getCategoryid());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        autotxt9 = findViewById(R.id.autotxt9);


        autotxt9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowPopup(v, lab, Constants.LAB);
            }
        });

        autotxt10 = findViewById(R.id.autotxt10);


        autotxt10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowPopup(v, duration, Constants.Duration);
            }
        });

        final Spinner sp1 = findViewById(R.id.spinner1);
        ArrayAdapter<Master> spinnerArrayAdapter = new ArrayAdapter<Master>(this, android.R.layout.simple_spinner_item, duration); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(spinnerArrayAdapter);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final Spinner sp2 = findViewById(R.id.spinner2);
        final EditText e2 = findViewById(R.id.editText_2);
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddRxActivity.this,
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

        autotxt1.setTextSize(fontsize);
        autotxt1.setTypeface(typeface);
        autotxt1.setTextColor(getResources().getColor(R.color.textcolor));


        autotxt2.setTextSize(fontsize);
        autotxt2.setTypeface(typeface);
        autotxt2.setTextColor(getResources().getColor(R.color.textcolor));


        autotxt3.setTextSize(fontsize);
        autotxt3.setTypeface(typeface);
        autotxt3.setTextColor(getResources().getColor(R.color.textcolor));

        autotxt4.setTextSize(fontsize);
        autotxt4.setTypeface(typeface);
        autotxt4.setTextColor(getResources().getColor(R.color.textcolor));

        autotxt5.setTextSize(fontsize);
        autotxt5.setTypeface(typeface);
        autotxt5.setTextColor(getResources().getColor(R.color.textcolor));

        autotxt6.setTextSize(fontsize);
        autotxt6.setTypeface(typeface);
        autotxt6.setTextColor(getResources().getColor(R.color.textcolor));

        autotxt7.setTextSize(fontsize);
        autotxt7.setTypeface(typeface);
        autotxt7.setTextColor(getResources().getColor(R.color.textcolor));

        autotxt8.setTextSize(fontsize);
        autotxt8.setTypeface(typeface);
        autotxt8.setTextColor(getResources().getColor(R.color.textcolor));

        autotxt9.setTextSize(fontsize);
        autotxt9.setTypeface(typeface);
        autotxt9.setTextColor(getResources().getColor(R.color.textcolor));

        autotxt10.setTextSize(fontsize);
        autotxt10.setTypeface(typeface);
        autotxt10.setTextColor(getResources().getColor(R.color.textcolor));

        Button bt1 = findViewById(R.id.button_1);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONObject obj = new JSONObject();
                    JSONObject rootobj = new JSONObject();
                    JSONObject subroot = new JSONObject();
                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(AddRxActivity.this);
                    obj.put("root", rootobj);
                    rootobj.put("subroot", subroot);
                    subroot.put("Patientid", GlobalValues.selectedpt.getPatientid());
                    subroot.put("DoctorId", GlobalValues.docid);
                    subroot.put("PracticeId", pref.getString("CustomerId", "0"));
                    subroot.put("VisitNo", visitid);
                    subroot.put("Note", "");
                    subroot.put("AddmitionNo", GlobalValues.selectedpt.getAddmitionno());
                    subroot.put("Precriptionid", "");
                    subroot.put("DepartmentId", pref.getString("DepartmentId", "0"));
                    subroot.put("VisitDate", visitdate);
                    subroot.put("UserId", "");
                    subroot.put("providerid", pref.getString("ProviderId", "0"));
                    subroot.put("Assistantid", "");
                    subroot.put("PresType", "");
                    JSONObject drugobj = new JSONObject();
                    drugobj.put("DrugName", autotxt2.getText().toString());
                    drugobj.put("DrugId", getId(autotxt2.getText().toString(), generic));
                    drugobj.put("Instruction", autotxt6.getText().toString());
                    drugobj.put("Instructionid", getId(autotxt6.getText().toString(), instruction));
                    drugobj.put("DrugQuantity", autotxt7.getText().toString());
                    drugobj.put("Doses", autotxt3.getText().toString());
                    drugobj.put("Dosesid", getId(autotxt3.getText().toString(), doses));
                    drugobj.put("DrugUnit", autotxt4.getText().toString());
                    drugobj.put("BodyPart", autotxt5.getText().toString());
                    drugobj.put("BodyPartId", getId(autotxt5.getText().toString(), bodypart));
                    drugobj.put("Duration", ((Master) sp1.getAdapter().getItem(sp1.getSelectedItemPosition())).getName());
                    drugobj.put("Durationid", ((Master) sp1.getAdapter().getItem(sp1.getSelectedItemPosition())).getCategoryid());


                    drugobj.put("patient_id", GlobalValues.selectedpt.getPatientid());

                    drugobj.put("practice_id",pref.getString("CustomerId","0"));

                    drugobj.put("department_id", pref.getString("DepartmentId","0"));

                    drugobj.put("UserId",pref.getString("ProviderId","0"));

                    drugobj.put("providerid", pref.getString("ProviderId","0"));

                    drugobj.put("Assistantid",pref.getString("ProviderId","0"));
                  //  drugobj.put("GeneralId", "");

                    drugobj.put("IPDNo","");
                    drugobj.put("TemplateName",templatename);
                   // drugobj.put("GeneralId", "");
                    drugobj.put("presdrugid", "0");
                    drugobj.put("PresType","Discharge");
                    subroot.put("Drugs", drugobj);
                    subroot.put("LifeStyle", lifestyleobj);
                    JSONObject object = new JSONObject();
                    object.put("data", obj.toString());
                    drugobj.put("DrugQuantity", autotxt7.getText().toString());
                    Gson gson = new Gson();
                    Type type = new TypeToken<Drug>() {
                    }.getType();
                    Drug d = gson.fromJson(drugobj.toString(), type);
                    d.setVisitdate(visitdate);
                    d.setVisit_no(Integer.parseInt(visitid));
                    autotxt1.setText("");
                    autotxt2.setText("");
                    autotxt3.setText("");
                    autotxt4.setText("");
                    autotxt5.setText("");
                    autotxt6.setText("");
                    autotxt7.setText("");
                    autotxt8.setText("");
                    autotxt9.setText("");
                    autotxt10.setText("");
                    hideKeypad();
                    GlobalValues.drugdata.add(d);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "Thank You", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Voice searching...");
        startActivityForResult(intent, REQUEST_CODE);
    }

    public ArrayList<Master> parsemaster(String data) {
        lifestyleobj = new JSONObject();
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

    public String getId(String name, ArrayList<Master> masterdata) {
        for (int i = 0; i < masterdata.size(); i++) {
            if (name.equalsIgnoreCase(masterdata.get(i).getName()))
                return masterdata.get(i).getCategoryid();
        }
        return "0";
    }

    public void onShowPopup(final View anchorView, final ArrayList<Master> mdataset, final String masterkey) {
        hideKeypad();
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflatedView = layoutInflater.inflate(R.layout.list_item_popup, null, false);
        final EditText edit = inflatedView.findViewById(R.id.edittext_1);
        ImageView img = inflatedView.findViewById(R.id.imageview_1);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(AddRxActivity.this);
        final PopupWindow popWindow = new PopupWindow(AddRxActivity.this);
        RecyclerView mRecyclerview = inflatedView.findViewById(R.id.recyclerview_1);
        mRecyclerview.setLayoutManager(mLinearLayoutManager);
        final MasterAdapter adapter = new MasterAdapter(AddRxActivity.this, mdataset, new MasterAdapter.OnItemClickListener() {
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

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (masterkey.equalsIgnoreCase(Constants.Drug)) {
                        showPopupDrug(anchorView, mdataset);
                        popWindow.dismiss();
                    } else if (masterkey.equalsIgnoreCase(Constants.Doses)) {
                        onShowPopupdoses(anchorView, mdataset);
                        popWindow.dismiss();
                    } else if (masterkey.equalsIgnoreCase(Constants.Duration)) {
                        onShowPopupfrequency(anchorView, mdataset);
                        popWindow.dismiss();
                    } else {
                        if(edit.getText().toString().trim().length()>0) {
                        long index = DatabaseHelper.getInstance(AddRxActivity.this).savemaster(edit.getText().toString(), "0", 0, masterkey);
                        Master master = new Master(edit.getText().toString());
                        master.setTestId("0");
                        master.setLabId("0");
                        mdataset.add(master);
                        adapter.notifyDataSetChanged();
                        ((EditText) anchorView).setText(edit.getText().toString().trim());
                        popWindow.dismiss();
                        }else {
                            Toast.makeText(getApplicationContext(),"please enter details",Toast.LENGTH_LONG).show();
                        }
                    }
            }
        });
    }

    public void showPopupDrug(final View anchorView, ArrayList<Master> mdataset) {
        hideKeypad();
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflatedView = layoutInflater.inflate(R.layout.list_item_popupdrug, null, false);
        final EditText e1 = inflatedView.findViewById(R.id.edittext_1);
        final EditText e2 = inflatedView.findViewById(R.id.edittext_2);
        final EditText e3 = inflatedView.findViewById(R.id.edittext_3);
        final EditText e4 = inflatedView.findViewById(R.id.edittext_4);
        final EditText e5 = inflatedView.findViewById(R.id.edittext_5);
        final EditText e6 = inflatedView.findViewById(R.id.edittext_6);

        TextView txt1 = inflatedView.findViewById(R.id.textview_1);
        TextView txt2 = inflatedView.findViewById(R.id.textview_2);
        TextView txt3 = inflatedView.findViewById(R.id.textview_3);
        TextView txt4 = inflatedView.findViewById(R.id.textview_4);
        TextView txt5 = inflatedView.findViewById(R.id.textview_5);
        TextView txt6 = inflatedView.findViewById(R.id.textview_6);
        TextView txt7 = inflatedView.findViewById(R.id.textview_7);

        e1.setTypeface(typeface);
        e2.setTypeface(typeface);
        e3.setTypeface(typeface);
        e4.setTypeface(typeface);
        e5.setTypeface(typeface);
        e6.setTypeface(typeface);
        txt1.setTypeface(typeface);
        txt2.setTypeface(typeface);
        txt3.setTypeface(typeface);
        txt4.setTypeface(typeface);
        txt5.setTypeface(typeface);
        txt6.setTypeface(typeface);
        txt7.setTypeface(typeface);

        final PopupWindow popWindow = new PopupWindow(AddRxActivity.this);
        popWindow.setContentView(inflatedView);

        popWindow.setBackgroundDrawable(new BitmapDrawable());
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(true);

        popWindow.setWidth(anchorView.getWidth());
        popWindow.setHeight(800);

        popWindow.showAsDropDown(anchorView);
        popWindow.setFocusable(true);
        txt7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = e1.getText().toString() + " " + e3.getText().toString() + " " + e5.getText().toString().trim() + "" + e6.getText().toString();
                if (e2.getText().toString().length() > 0) {
                    data = data + "(" + e2.getText().toString() + ")";
                }
                ((EditText) anchorView).setText(data);
                popWindow.dismiss();
                try {
                    JSONObject obj = new JSONObject();
                    obj.put("Drugtype", e1.getText().toString());
                    obj.put("DrugName",e3.getText().toString());
                    obj.put("Generic", e2.getText().toString());
                    obj.put("Manufacture", e4.getText().toString());
                    obj.put("Weight", e5.getText().toString());
                    obj.put("Unit", e6.getText().toString());
                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(AddRxActivity.this);
                    obj.put("PracticeId", pref.getString("CustomerId", "0"));
                    obj.put("DepartmentId", pref.getString("DepartmentId", "0"));
                    long index = DatabaseHelper.getInstance(AddRxActivity.this).savemaster(data, "0", 0, Constants.Drug, obj.toString());
                    if (index != 0 && InternetUtils.getInstance(AddRxActivity.this).available()) {
                        obj.put("id", index);
                        ConnectionManager.getInstance(AddRxActivity.this).savedrugmaster(obj.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }


    public void onShowPopupfrequency(final View anchorView, final ArrayList<Master> mdataset) {
        hideKeypad();
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflatedView = layoutInflater.inflate(R.layout.list_item_frequency, null, false);
        final EditText edit = inflatedView.findViewById(R.id.edittext_1);
        final Spinner spinner = inflatedView.findViewById(R.id.spinner_1);
        TextView txt1 = inflatedView.findViewById(R.id.textview_1);
        TextView txt2 = inflatedView.findViewById(R.id.textview_2);
        txt1.setTypeface(typeface);
        txt2.setTypeface(typeface);
        edit.setTypeface(typeface);

      /*  ArrayList<String> dataset=new ArrayList<>();
        dataset.add("Days");
        dataset.add("Weeks");
        dataset.add("Months");
        dataset.add("Years");

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (getApplicationContext(), android.R.mainLayout.simple_spinner_item,
                        dataset);
        spinnerArrayAdapter.setDropDownViewResource(android.R.mainLayout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);*/
        txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EditText) anchorView).setText(edit.getText().toString() + " " + spinner.getSelectedItem().toString());
            }
        });

        final PopupWindow popWindow = new PopupWindow(AddRxActivity.this);
        popWindow.setContentView(inflatedView);
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(true);
        popWindow.setWidth(anchorView.getWidth());
        popWindow.setHeight(popupheight);
        popWindow.showAsDropDown(anchorView);
        popWindow.setFocusable(true);
    }


    public void onShowPopupdoses(final View anchorView, final ArrayList<Master> mdataset) {
        hideKeypad();
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflatedView = layoutInflater.inflate(R.layout.list_item_popupdoses, null, false);
        final EditText e1 = inflatedView.findViewById(R.id.edittext_1);
        final EditText e2 = inflatedView.findViewById(R.id.edittext_2);
        final EditText e3 = inflatedView.findViewById(R.id.edittext_3);
        final EditText e4 = inflatedView.findViewById(R.id.edittext_4);

        TextView txt1 = inflatedView.findViewById(R.id.textview_1);
        TextView txt2 = inflatedView.findViewById(R.id.textview_2);
        TextView txt3 = inflatedView.findViewById(R.id.textview_3);
        TextView txt4 = inflatedView.findViewById(R.id.textview_4);
        TextView txt5 = inflatedView.findViewById(R.id.textview_5);

        e1.setTypeface(typeface);
        e2.setTypeface(typeface);
        e3.setTypeface(typeface);
        e4.setTypeface(typeface);

        txt1.setTypeface(typeface);
        txt2.setTypeface(typeface);
        txt3.setTypeface(typeface);
        txt4.setTypeface(typeface);
        txt5.setTypeface(typeface);

        final PopupWindow popWindow = new PopupWindow(AddRxActivity.this);

        popWindow.setContentView(inflatedView);

        popWindow.setBackgroundDrawable(new BitmapDrawable());
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(true);

        popWindow.setWidth(anchorView.getWidth());
        popWindow.setHeight(200);

        popWindow.showAsDropDown(anchorView);
        popWindow.setFocusable(true);
        txt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ans = "";
                if (e1.getText().toString().trim().length() > 0)
                    ans = e1.getText().toString().trim() + "-";
                else
                    ans = 0 + "-";

                if (e2.getText().toString().trim().length() > 0)
                    ans = ans + e2.getText().toString().trim() + "-";
                else
                    ans = ans + 0 + "-";

                if (e3.getText().toString().trim().length() > 0)
                    ans = ans + e3.getText().toString().trim() + "-";
                else
                    ans = ans + 0 + "-";

                if (e4.getText().toString().trim().length() > 0)
                    ans = ans + e4.getText().toString().trim();
                else
                    ans = ans + 0;

                ((EditText) anchorView).setText(ans);
                popWindow.dismiss();
            }
        });
    }

    public ArrayList<Master> getmaster(String mastekey) {
        JSONArray array1 = DatabaseHelper.getInstance(AddRxActivity.this).getMaster(mastekey);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Master>>() {
        }.getType();
        ArrayList<Master> dataset = gson.fromJson(array1.toString(), type);
        if (dataset == null)
            dataset = new ArrayList<>();
        return dataset;
    }
}
