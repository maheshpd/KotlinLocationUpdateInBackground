package com.healthbank;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.healthbank.adapter.PrescriptionFilter;
import com.healthbank.adapter.labAdapter;
import com.healthbank.classes.DetailPrescriptionData;
import com.healthbank.classes.Drug;
import com.healthbank.classes.MasterData;
import com.healthbank.database.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

public class RxActivity extends ActivityCommon {
    private static final int REQUEST_CODE = 1234;
    //private FloatingActionButton fab1, fab2;
    //private FloatingActionMenu fam;
    RecyclerView recyclerView1;
    LinearLayoutManager mLayoutmanager1;
    PrescriptionFilter adapter1;
    ArrayList<DetailPrescriptionData> mdataset1;
    Spinner sp;
    ArrayAdapter<String> spinadapter;
    ArrayList<String> mdataset;

    /*private View.OnClickListener onButtonClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == fab1) {
                    Intent intent = new Intent(RxActivity.this, AddLabsActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(RxActivity.this, AddPrescriptionActivity.class);
                    startActivity(intent);
                }
                fam.close(true);
            }
        };
    }*/
    View dialog_layout;
    int selectedpos = -1;
    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener datedialog = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub
            try {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yyyy";
                String date = DateUtils.formatDate(myCalendar.getTime(), "dd MMM yyyy");
                mdataset1.get(selectedpos).setDate(date);
                Log.e("Id1 ", "Id1 " + mdataset1.get(selectedpos).getId() + " " + selectedpos);
                createobject(mdataset1.get(selectedpos));
                adapter1.notifyDataSetChanged();
                mdataset.clear();
                for (int i = 0; i < mdataset1.size(); i++) {
                    mdataset.add(mdataset1.get(i).getDate());
                }
                mdataset.add("All");
                spinadapter.notifyDataSetChanged();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);
        setmaterialDesign();
        setTitle("Prescription");
        back();

        StructureClass.defineContext(RxActivity.this);
        mdataset1 = new ArrayList<>();
        mdataset = new ArrayList<>();
        mdataset.add("All");

       /* fab2 = findViewById(R.id.fab2);
        fab1 = findViewById(R.id.fab1);
        fam = findViewById(R.id.fab_menu);*/
        sp = findViewById(R.id.spinner_1);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                adapter1.getFilter().filter(mdataset.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinadapter = new ArrayAdapter(this, R.layout.spinner_item, mdataset);
        spinadapter.setDropDownViewResource(R.layout.spinner_item);
        sp.setAdapter(spinadapter);
       /* fab1.setOnClickListener(onButtonClick());
        fab2.setOnClickListener(onButtonClick());
        fam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fam.isOpened()) {
                    fam.close(true);
                }
            }
        });*/

        JSONArray array = DatabaseHelper.getInstance(RxActivity.this).getprescriptiondata(Integer.parseInt(GlobalValues.selectedpt.getSrno()));
        for (int i = 0; i < array.length(); i++) {
            try {
                JSONObject mainobj = array.getJSONObject(i);
                DetailPrescriptionData prescription = parsedata(mainobj);
                mdataset1.add(prescription);
                mdataset.add(prescription.getDate());
            } catch (Exception e) {
                Log.e("error ", "error1 " + e.toString());
                e.printStackTrace();
            }
        }
        recyclerView1 = findViewById(R.id.recyclerview_2);
        mLayoutmanager1 = new LinearLayoutManager(RxActivity.this);
        recyclerView1.setLayoutManager(mLayoutmanager1);
        adapter1 = new PrescriptionFilter(RxActivity.this, mdataset1);
        recyclerView1.setAdapter(adapter1);
        spinadapter.notifyDataSetChanged();
    }

    public JSONObject createobject(DetailPrescriptionData prescription) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("date", prescription.getDate());
            obj.put("Patientid", prescription.getPatientid());
            obj.put("globalpatientid", prescription.getPatientid());
            obj.put("DoctorId", GlobalValues.docid);
            obj.put("PracticeId", GlobalValues.practiceid);
            JSONArray labarray = new JSONArray();
            for (int j = 0; j < prescription.getLabs().size(); j++) {
                JSONObject lab = new JSONObject();
                lab.put("name", prescription.getLabs().get(j));
                labarray.put(lab);
            }
            obj.put("labs", labarray);

            JSONArray advicearray = new JSONArray();
            for (int j = 0; j < prescription.getAdvice().size(); j++) {
                JSONObject advice = new JSONObject();
                advice.put("name", prescription.getAdvice().get(j));
                advicearray.put(advice);
            }
            obj.put("advice", advicearray);

            JSONArray diagnosticarray = new JSONArray();
            for (int j = 0; j < prescription.getDiagnostics().size(); j++) {
                JSONObject diagonstic = new JSONObject();
                diagonstic.put("name", prescription.getDiagnostics().get(j));
                diagnosticarray.put(diagonstic);
            }

            Gson gson = new Gson();
            String json = gson.toJson(prescription.getPrescriptiondata());
            obj.put("drug", new JSONArray(json));
            obj.put("diagonstic", diagnosticarray);
            obj.put("referaldoctor", prescription.getReferaldoctor());
            ContentValues c = new ContentValues();
            c.put(DatabaseHelper.Patientid, GlobalValues.selectedpt.getSrno());
            c.put(DatabaseHelper.DOCTORID, GlobalValues.docid);
            c.put(DatabaseHelper.Date, prescription.getDate());
            c.put(DatabaseHelper.JSONDATA, obj.toString());
            long id = DatabaseHelper.getInstance(RxActivity.this).saveprescription(c, prescription.getId(), Integer.parseInt(GlobalValues.selectedpt.getSrno()));
            if (prescription.getId() == -1)
                prescription.setId((int) id);
            Log.e("Prescription ", "prescription " + prescription.getId());
            adapter1.notifyDataSetChanged();
            DatabaseHelper.getInstance(RxActivity.this).getprescriptiondata(Integer.parseInt(GlobalValues.selectedpt.getSrno()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public DetailPrescriptionData parsedata(JSONObject mainobj) {
        DetailPrescriptionData prescription = new DetailPrescriptionData();
        try {
            prescription.setDate(mainobj.getString("date"));
            prescription.setId(mainobj.getInt("id"));
            JSONObject obj = new JSONObject(mainobj.getString("jsondata"));
            prescription.setDoctorId(obj.getInt("DoctorId"));
            prescription.setGlobalpatientid(obj.getInt("globalpatientid"));
            prescription.setPracticeId(obj.getInt("PracticeId"));
            prescription.setPatientid(obj.getInt("Patientid"));
            JSONArray labarray = obj.getJSONArray("labs");

            for (int j = 0; j < labarray.length(); j++) {
                prescription.getLabs().add(labarray.getJSONObject(j).getString("name"));
            }

            JSONArray diagonsticarray = obj.getJSONArray("diagonstic");
            for (int j = 0; j < diagonsticarray.length(); j++) {
                prescription.getDiagnostics().add(diagonsticarray.getJSONObject(j).getString("name"));
            }

            JSONArray advicearray = obj.getJSONArray("advice");
            for (int j = 0; j < advicearray.length(); j++) {
                prescription.getAdvice().add(advicearray.getJSONObject(j).getString("name"));
            }
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Drug>>() {
            }.getType();
            ArrayList<Drug> data = gson.fromJson(obj.getString("drug"), type);
            prescription.getPrescriptiondata().addAll(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prescription;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_add:
                ArrayList<String> dataset1 = new ArrayList<>();
                ArrayList<String> dataset2 = new ArrayList<>();
                ArrayList<String> advicedata = new ArrayList<>();
                DetailPrescriptionData prescriptiondata = new DetailPrescriptionData(dataset1, dataset2, advicedata);
                Calendar cal = Calendar.getInstance();
                String date = DateUtils.formatDate(cal.getTime(), "dd MMM yyyy");
                prescriptiondata.setDate(date);
                prescriptiondata.setPatientid(GlobalValues.selectedpt.getPatientid());
                prescriptiondata.setDoctorId(GlobalValues.docid);
                prescriptiondata.setPracticeId(GlobalValues.practiceid);
                mdataset1.add(prescriptiondata);
                Log.e("id ", "id " + mdataset1.get(mdataset1.size() - 1).getId() + " " + mdataset1.get(mdataset1.size() - 1).getId());
                adapter1.notifyDataSetChanged();
                mdataset.clear();
                mdataset.add("All");
                for (int i = 0; i < mdataset1.size(); i++) {
                    mdataset.add(mdataset1.get(i).getDate());
                }
                spinadapter.notifyDataSetChanged();
                sp.setSelection(0);

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Voice searching...");
        startActivityForResult(intent, REQUEST_CODE);
    }

    @
            Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            // Populate the wordsList with the String values the recognition engine thought it heard
            final ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (!matches.isEmpty()) {
                String Query = matches.get(0);
                Log.e("query", "query " + Query);
                try {
                    if (dialog_layout != null) {
                        ((AutoCompleteTextView) dialog_layout.findViewById(R.id.autotxt1)).setText(Query);
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
                //  speak.setEnabled(false);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void opendialoglab(final ArrayList<String> mdatset, String category, final int position) {

        LayoutInflater inflater = LayoutInflater.from(RxActivity.this);
        dialog_layout = inflater.inflate(R.layout.dialog_add_lab, null);
        AlertDialog.Builder db = new AlertDialog.Builder(RxActivity.this);
        RecyclerView recyclerview = dialog_layout.findViewById(R.id.recyclerview_1);
        TextView txt = dialog_layout.findViewById(R.id.textview_1);
        TextView search = dialog_layout.findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startVoiceRecognitionActivity();
            }
        });

        final int labid = DatabaseHelper.getInstance(RxActivity.this).getId(category);
        final ArrayList<MasterData> labdata = new ArrayList<>();
        labdata.addAll(DatabaseHelper.getInstance(RxActivity.this).getmstexamination(labid));

        final AutoCompleteTextView autotxt = dialog_layout.findViewById(R.id.autotxt1);
        autotxt.setThreshold(1);
        final ArrayAdapter<MasterData> adapter = new ArrayAdapter<MasterData>
                (this, android.R.layout.select_dialog_item, labdata);
        autotxt.setAdapter(adapter);

        LinearLayoutManager mLayoutmanager1 = new LinearLayoutManager(RxActivity.this);
        recyclerview.setLayoutManager(mLayoutmanager1);
        final labAdapter labadapter = new labAdapter(RxActivity.this, mdatset, true);
        recyclerview.setAdapter(labadapter);

        if (category.equalsIgnoreCase("Lab"))
            db.setTitle("Add Lab");
        else if (category.equalsIgnoreCase("Diagnosis"))
            db.setTitle("Add Diagnosis");
        else
            db.setTitle("Add Advice");

        db.setView(dialog_layout);
        db.show();

        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (autotxt.getText().toString().trim().length() > 0) {
                    mdatset.add(autotxt.getText().toString());
                    labadapter.notifyDataSetChanged();
                    adapter1.notifyDataSetChanged();
                    spinadapter.notifyDataSetChanged();
                    if (DatabaseHelper.getInstance(RxActivity.this).getId(autotxt.getText().toString()) == -1) {
                        DatabaseHelper.getInstance(RxActivity.this).savemstexamination(labid, autotxt.getText().toString());
                        MasterData master = new MasterData();
                        master.setName(autotxt.getText().toString());
                        master.setSubCategoryid(labid);
                        labdata.add(master);
                        final ArrayAdapter<MasterData> adapter = new ArrayAdapter<MasterData>
                                (RxActivity.this, android.R.layout.select_dialog_item, labdata);
                        autotxt.setAdapter(adapter);
                    }
                    autotxt.setText("");
                    createobject(mdataset1.get(position));
                }
            }
        });
    }

    public void updatedate(int position) {
        selectedpos = position;
        try {
            Log.e("Id ", "Id " + mdataset1.get(selectedpos).getId() + " " + selectedpos);
            new DatePickerDialog(RxActivity.this, datedialog, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void opendialogprescription(final ArrayList<Drug> mdatset, final int position) {
        try {
            final LayoutInflater inflater = LayoutInflater.from(RxActivity.this);
            final View dialog_layout = inflater.inflate(R.layout.dialog_add_prescription, null);
            LinearLayout layout = dialog_layout.findViewById(R.id.layout1);
            layout.setVerticalScrollBarEnabled(true);
            final AutoCompleteTextView autotxt1 = dialog_layout.findViewById(R.id.autotxt1);
            ImageView img = dialog_layout.findViewById(R.id.imageview_1);

            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    opendialogadddosage();
                }
            });
            int diagnosticid = DatabaseHelper.getInstance(RxActivity.this).getId("Diagnostic");
            ArrayList<MasterData> drugnamedata = new ArrayList<>();
            drugnamedata.addAll(DatabaseHelper.getInstance(RxActivity.this).getmstexamination(diagnosticid));
            //   Log.e("drug ","drug "+drugnamedata.size());

            for (int i = 0; i < drugnamedata.size(); i++) {
                Log.e("drug ", "drug " + drugnamedata.get(i));
            }

            final ArrayAdapter<MasterData> adaptergdrug = new ArrayAdapter<MasterData>(RxActivity.this, android.R.layout.simple_list_item_1, drugnamedata);
            autotxt1.setAdapter(adaptergdrug);
            autotxt1.setThreshold(0);

            final AutoCompleteTextView autotxt2 = dialog_layout.findViewById(R.id.autotxt2);
            int genericid = DatabaseHelper.getInstance(RxActivity.this).getId("Generic");
            ArrayList<MasterData> genericdata = new ArrayList<>();
            genericdata.addAll(DatabaseHelper.getInstance(RxActivity.this).getmstexamination(genericid));
            final ArrayAdapter<MasterData> adapterggeneric = new ArrayAdapter<MasterData>(RxActivity.this, android.R.layout.simple_list_item_1, genericdata);
            autotxt2.setAdapter(adapterggeneric);
            autotxt2.setThreshold(0);

            final AutoCompleteTextView autotxt3 = dialog_layout.findViewById(R.id.autotxt3);
            ArrayList<MasterData> dosagedata = new ArrayList<>();
            ArrayAdapter<MasterData> adapterdosage = new ArrayAdapter<MasterData>(RxActivity.this, android.R.layout.simple_list_item_1, dosagedata);
            autotxt3.setAdapter(adapterdosage);
            autotxt3.setThreshold(0);

            final AutoCompleteTextView autotxt4 = dialog_layout.findViewById(R.id.autotxt4);
            ArrayList<String> unitdata = new ArrayList<>();
            unitdata.add("mg");
            unitdata.add("xyz");
            unitdata.add("lmn");

            final ArrayAdapter<String> adapterunit = new ArrayAdapter<String>(RxActivity.this, android.R.layout.simple_list_item_1, unitdata);
            autotxt4.setAdapter(adapterunit);
            autotxt4.setThreshold(0);

            final AutoCompleteTextView autotxt5 = dialog_layout.findViewById(R.id.autotxt5);
            ArrayList<String> bodypartdata = new ArrayList<>();
            bodypartdata.add("Right Eye");
            bodypartdata.add("Left Eye");
            bodypartdata.add("Left Eye2");
            bodypartdata.add("Left Eye3");

            final ArrayAdapter<String> adapterbodypart = new ArrayAdapter<String>(RxActivity.this, android.R.layout.simple_list_item_1, bodypartdata);
            autotxt5.setAdapter(adapterbodypart);
            autotxt5.setThreshold(0);

            final Spinner sp1 = dialog_layout.findViewById(R.id.spinner1);
            final Spinner sp2 = dialog_layout.findViewById(R.id.spinner2);
            // final RadioGroup rg = (RadioGroup) dialog_layout.findViewById(R.id.radioGroup1);
            final EditText e1 = dialog_layout.findViewById(R.id.editText_1);
            final EditText e2 = dialog_layout.findViewById(R.id.editText_2);

            AlertDialog.Builder db = new AlertDialog.Builder(RxActivity.this);
            db.setView(dialog_layout);

            final Dialog dialog = db.show();
            //  dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
            Button bt1 = dialog_layout.findViewById(R.id.button_1);
            bt1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Drug data = new Drug();
                    data.setDrugName(autotxt1.getText().toString().trim());
                    data.setBrand(autotxt2.getText().toString().trim());
                    data.setDuration(autotxt3.getText().toString().trim());
                    data.setDrugQuantity(e2.getText().toString().trim());
                    data.setDrugUnit(autotxt4.getText().toString().trim());
                    data.setBodyPart(autotxt5.getText().toString().trim());
                    data.setDoses(sp1.getSelectedItem().toString() + " " + sp2.getSelectedItem().toString());
                    String instruction = "";
                    data.setInstruction(instruction + ". " + e1.getText().toString());
                    mdatset.add(data);
                    adapter1.notifyDataSetChanged();
                    spinadapter.notifyDataSetChanged();
                    createobject(mdataset1.get(position));
                    autotxt1.setText("");
                    autotxt2.setText("");
                    autotxt3.setText("");
                    autotxt4.setText("");
                    autotxt5.setText("");
                    e1.setText("");
                    e2.setText("");
                    autotxt1.requestFocus();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void opendialogadddosage() {
        final LayoutInflater inflater = LayoutInflater.from(RxActivity.this);
        final View dialog_layout = inflater.inflate(R.layout.dailog_add_dosage, null);
        AlertDialog.Builder db = new AlertDialog.Builder(RxActivity.this);
        db.setTitle("Add Dosage");
        db.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        db.setView(dialog_layout);
        final Dialog dialog = db.show();
    }

    public void notifydatasetchanged(int position) {
        createobject(mdataset1.get(position));
        adapter1.notifyDataSetChanged();
        spinadapter.notifyDataSetChanged();
    }
}
