package com.healthbank;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.healthbank.classes.Connection;
import com.healthbank.classes.Drug;
import com.healthbank.classes.Master;
import com.healthbank.classes.MasterData;
import com.healthbank.classes.Visits;
import com.healthbank.database.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class AddRXFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQUEST_CODE = 100;
    public static ArrayList<Drug> drugdata;
    ImageView img1, img2, img3, img4, img5;
    AutoCompleteTextView autotxt1, autotxt2, autotxt3, autotxt4, autotxt5, autotxt6, autotxt7, autotxt8, autotxt9, autotxt10;
    ArrayList<Master> generic, instruction, quantity, unit, bodypart, doses, duration, lifestyle, lab;//frequency
    JSONObject lifestyleobj;
    TextView txt1,txt2;
    RecyclerView mRecyclerview;
    LinearLayoutManager mLayoutmanager;
    DrugAdapter adapter;
    LinearLayout mLayout;
    boolean isbodypart = false;
    int fontsize = 16;
    Typeface typeface;
    int padding = 20;
    Spinner spinner;
    Button bt2;
    ArrayAdapter<Visits> adaptervisit;
    ArrayList<Visits> visitdata = new ArrayList<>();
    int popupheight=600;
    int tag = 0;
    View.OnClickListener clk = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            tag = (int) view.getTag();
            startVoiceRecognitionActivity();
        }
    };
    private BroadcastReceiver responseRec = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            try {
                Bundle b = intent.getExtras();
                int statuscode = b.getInt(Constants.BROADCAST_RESPONSE_CODE);
                int urlaccess = b.getInt(Constants.BROADCAST_URL_ACCESS);

                if (urlaccess == Connection.SaveRX.ordinal()) {
                    Toast.makeText(getActivity(), "Thank You", Toast.LENGTH_LONG).show();
                    Intent intentdrug = new Intent("drugupdated");
                    intentdrug.putExtra("visitid", visitdata.get(spinner.getSelectedItemPosition()).getId());
                    intentdrug.putExtra("drugdata", drugdata);
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intentdrug);
                    getActivity().finish();
                } else if (urlaccess == Connection.GET_VISITS.ordinal()) {
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
                } else if (urlaccess == Connection.AddVisit.ordinal()) {
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

                                    long id = DatabaseHelper.getInstance(getActivity()).savevisitdata(Integer.toString(v.getDBPatientid()), 0, v.getcontentvalues());
                                    v.setId((int) id);
                                    visitdata.add(0, v);

                                 /*   if (visitdata.size() > 1)
                                        refitBtn.setVisibility(View.VISIBLE);
                                    else refitBtn.setVisibility(View.GONE);*/
                                    adaptervisit = new ArrayAdapter<Visits>(getActivity(), android.R.layout.simple_dropdown_item_1line, visitdata);
                                    spinner.setAdapter(adaptervisit);
                                    if (visitdata.size() > 0)
                                        spinner.setSelection(0);
                                    //submitdata();
                                }
                            }
                        }
                    } catch (Exception e) {
                        Log.e("error ", "error " + e.toString());
                        e.printStackTrace();
                    }
                } else if (urlaccess == Connection.GET_PRESCRIPTION.ordinal()) {
                    JSONArray array = DatabaseHelper.getInstance(getActivity()).getdrugdatavisit(visitdata.get(spinner.getSelectedItemPosition()).getId());
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = new JSONObject(array.getJSONObject(i).getString("jsondata"));
                        Gson gson = new Gson();
                        Type type = new TypeToken<Drug>() {
                        }.getType();
                        Drug drug = gson.fromJson(obj.toString(), type);
                        drugdata.add(drug);
                    }
                    adapter = new DrugAdapter(getActivity(), drugdata, 0);
                    mRecyclerview.setAdapter(adapter);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    private BroadcastReceiver res = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            try {
                Bundle b = intent.getExtras();
                int statuscode = b.getInt(Constants.BROADCAST_RESPONSE_CODE);
                int urlaccess = b.getInt(Constants.BROADCAST_URL_ACCESS);
                if (urlaccess == Connection.SaveRX.ordinal()) {
                    Toast.makeText(getActivity(), "Thank You", Toast.LENGTH_LONG).show();
                    getActivity().finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    public AddRXFragment() {
        // Required empty public constructor
    }

    public static AddRXFragment newInstance(String param1, String param2) {
        AddRXFragment fragment = new AddRXFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    /* public ArrayList<Master> parsemaster(String data) {
         drugobj = new JSONObject();
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
 */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_rx, container, false);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(responseRec, new IntentFilter(Constants.BROADCAST_WIZARD));
        drugdata = new ArrayList<>();
        typeface = Fonter.getTypefaceregular(getActivity());
        mRecyclerview = view.findViewById(R.id.recyclerview_1);
        mLayout = view.findViewById(R.id.layout_1);

        if (isbodypart)
            mLayout.setVisibility(View.VISIBLE);
        else
            mLayout.setVisibility(View.GONE);

        bt2 = view.findViewById(R.id.button_2);

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject obj = new JSONObject();
                    JSONObject rootobj = new JSONObject();
                    JSONObject subroot = new JSONObject();
                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    obj.put("root", rootobj);
                    rootobj.put("subroot", subroot);
                    subroot.put("Patientid", GlobalValues.selectedpt.getPatientid());
                    subroot.put("DoctorId", GlobalValues.docid);
                    subroot.put("PracticeId", pref.getString("CustomerId", "0"));
                    subroot.put("VisitNo", visitdata.get(spinner.getSelectedItemPosition()).getVisitid());
                    subroot.put("Note", "");
                    subroot.put("AddmitionNo", GlobalValues.selectedpt.getAddmitionno());
                    subroot.put("Precriptionid", "");
                    subroot.put("DepartmentId", pref.getString("DepartmentId", "0"));
                    String visitdate = ((Visits) spinner.getSelectedItem()).getVisitdate();
                    subroot.put("VisitDate", DateUtils.parseDateNew(visitdate, "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd"));
                    subroot.put("UserId", "");
                    subroot.put("providerid", pref.getString("ProviderId", "0"));
                    subroot.put("Assistantid", "");
                    subroot.put("PresType", "");
                    subroot.put("deleted", 0);
                    subroot.put("status", "On going");
                    subroot.put("FromDate", null);
                    subroot.put("ToDate", null);
                    Gson gson = new Gson();
                    String data = gson.toJson(drugdata);
                    subroot.put("Drugs", new JSONArray(data));
                    subroot.put("LifeStyle", lifestyleobj);
                    JSONObject object = new JSONObject();
                    object.put("data", obj.toString());
                    object.put("connectionid", Constants.projectid);
                    ConnectionManager.getInstance(getActivity()).SaveRx(object.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        mLayoutmanager = new LinearLayoutManager(getActivity());
        spinner = view.findViewById(R.id.spinner_1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                drugdata.clear();
                adapter.notifyDataSetChanged();
                try {
                    JSONArray array = DatabaseHelper.getInstance(getActivity()).getdrugdatavisit(visitdata.get(spinner.getSelectedItemPosition()).getId());
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = new JSONObject(array.getJSONObject(i).getString("jsondata"));
                        Gson gson = new Gson();
                        Type type = new TypeToken<Drug>() {
                        }.getType();
                        Drug drug = gson.fromJson(obj.toString(), type);
                        drugdata.add(drug);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // ConnectionManager.getInstance(getActivity()).getprescription(Integer.toString(GlobalValues.selectedpt.getPatientid()), Integer.toString(visitdata.get(position).getVisitid()), 0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mRecyclerview.setLayoutManager(mLayoutmanager);
        adapter = new DrugAdapter(getActivity(), drugdata, 0);
        mRecyclerview.setAdapter(adapter);
        mRecyclerview.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getActivity(), R.drawable.recycler_divider)));
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

        LinearLayout layout = view.findViewById(R.id.layout1);
        layout.setVerticalScrollBarEnabled(true);
        autotxt1 = view.findViewById(R.id.autotxt1);
        ImageView img = view.findViewById(R.id.imageview_1);
        txt1 = view.findViewById(R.id.textview_1);
        txt2 = view.findViewById(R.id.textview_2);
        txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adddata();
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  opendialogadddosage();
            }
        });

        img1 = view.findViewById(R.id.imageview_1);
        img2 = view.findViewById(R.id.imageview_2);
        img3 = view.findViewById(R.id.imageview_3);
        img4 = view.findViewById(R.id.imageview_4);
        img5 = view.findViewById(R.id.imageview_5);

        img2.setTag(2);
        img3.setTag(3);
        img4.setTag(4);
        img5.setTag(5);

        img2.setOnClickListener(clk);
        img3.setOnClickListener(clk);
        img4.setOnClickListener(clk);
        img5.setOnClickListener(clk);

        int brandid = DatabaseHelper.getInstance(getActivity()).getId("Brand");
        final ArrayList<MasterData> drugnamedata = new ArrayList<>();
        drugnamedata.addAll(DatabaseHelper.getInstance(getActivity()).getmstexamination(brandid));
        final ArrayAdapter<MasterData> adaptergdrug = new ArrayAdapter<MasterData>(getActivity(), android.R.layout.simple_list_item_1, drugnamedata);
        autotxt1.setAdapter(adaptergdrug);
        autotxt1.setThreshold(0);

        autotxt2 = view.findViewById(R.id.autotxt2);
        autotxt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowPopup(v, generic, Constants.Drug);
            }
        });

        final ArrayAdapter<Master> adapterggeneric = new ArrayAdapter<Master>(getActivity(), android.R.layout.simple_list_item_1, generic);
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

        autotxt6 = view.findViewById(R.id.autotxt6);
        final ArrayAdapter<Master> adapterinstruction = new ArrayAdapter<Master>(getActivity(), android.R.layout.simple_list_item_1, instruction);
        autotxt6.setAdapter(adapterinstruction);
        autotxt6.setThreshold(0);
        autotxt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowPopup(v, instruction, Constants.Instruction);
            }
        });

        autotxt7 = view.findViewById(R.id.autotxt7);
        final ArrayAdapter<Master> adapterquantity = new ArrayAdapter<Master>(getActivity(), android.R.layout.simple_list_item_1, quantity);
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

        autotxt3 = view.findViewById(R.id.autotxt3);
        autotxt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowPopup(v, doses, Constants.Doses);
            }
        });

        autotxt4 = view.findViewById(R.id.autotxt4);
        final ArrayAdapter<Master> adapterunit = new ArrayAdapter<Master>(getActivity(), android.R.layout.simple_list_item_1, unit);
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

        autotxt5 = view.findViewById(R.id.autotxt5);
        final ArrayAdapter<Master> adapterbodypart = new ArrayAdapter<Master>(getActivity(), android.R.layout.simple_list_item_1, bodypart);
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

        autotxt8 = view.findViewById(R.id.autotxt8);
        ArrayAdapter<Master> adapterlifestyle = new ArrayAdapter<Master>(getActivity(), android.R.layout.simple_list_item_1, lifestyle);
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

        autotxt9 = view.findViewById(R.id.autotxt9);
        autotxt9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowPopup(v, lab, Constants.LAB);
            }
        });

        autotxt10 = view.findViewById(R.id.autotxt10);


        autotxt10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowPopup(v, duration, Constants.Duration);
            }
        });

        final Spinner sp1 = view.findViewById(R.id.spinner1);
        ArrayAdapter<Master> spinnerArrayAdapter = new ArrayAdapter<Master>(getActivity(), android.R.layout.simple_spinner_item, duration); //selected item will look like a spinner set from XML
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

        final Spinner sp2 = view.findViewById(R.id.spinner2);
        final EditText e2 = view.findViewById(R.id.editText_2);
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
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

        Button bt1 = view.findViewById(R.id.button_1);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONObject obj = new JSONObject();
                    JSONObject rootobj = new JSONObject();
                    JSONObject subroot = new JSONObject();
                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    obj.put("root", rootobj);
                    rootobj.put("subroot", subroot);
                    subroot.put("Patientid", GlobalValues.selectedpt.getPatientid());
                    subroot.put("DoctorId", GlobalValues.docid);
                    subroot.put("PracticeId", pref.getString("CustomerId", "0"));
                    subroot.put("VisitNo", "0");
                    subroot.put("Note", "");
                    subroot.put("AddmitionNo", GlobalValues.selectedpt.getAddmitionno());
                    subroot.put("Precriptionid", "");
                    subroot.put("DepartmentId", pref.getString("DepartmentId", "0"));
                    subroot.put("VisitDate", txt1.getText().toString());
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
                    subroot.put("Drugs", drugobj);
                    subroot.put("LifeStyle", lifestyleobj);
                    JSONObject object = new JSONObject();
                    object.put("data", obj.toString());
                    drugobj.put("DrugQuantity", autotxt7.getText().toString());
                    object.put("connectionid", Constants.projectid);

                    Gson gson = new Gson();
                    Type type = new TypeToken<Drug>() {
                    }.getType();
                    Drug d = gson.fromJson(drugobj.toString(), type);

                    drugdata.add(d);
                    adapter.notifyDataSetChanged();

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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        if (InternetUtils.getInstance(getActivity()).available()) {
            ConnectionManager.getInstance(getActivity()).getvisits(Integer.toString(GlobalValues.selectedpt.getPatientid()));
        } else {
            try {
                JSONArray array = DatabaseHelper.getInstance(getActivity()).getvisitdata(Integer.toString(GlobalValues.selectedpt.getId()), Integer.toString(GlobalValues.selectedpt.getPatientid()));
                if (array.length() > 0) {
                    parsevisit(array, false);
                } else {
                    addcurrentvisit(Calendar.getInstance());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return view;
    }

    private void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Voice searching...");
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            final ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (!matches.isEmpty()) {
                String Query = matches.get(0);
                try {
                    switch (tag) {
                        case 2:
                            autotxt1.setText(Query);
                            break;
                        case 3:
                            autotxt2.setText(Query);
                            break;
                        case 4:
                            autotxt6.setText(Query);
                            break;
                        case 5:
                            autotxt5.setText(Query);
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        try {
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(responseRec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    public ArrayList<Master> getmaster(String mastekey) {
        JSONArray array1 = DatabaseHelper.getInstance(getActivity()).getMaster(mastekey);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Master>>() {
        }.getType();
        ArrayList<Master> dataset = gson.fromJson(array1.toString(), type);
        if (dataset == null)
            dataset = new ArrayList<>();
        return dataset;
    }

    public void onShowPopup(final View anchorView, final ArrayList<Master> mdataset, final String masterkey) {
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflatedView = layoutInflater.inflate(R.layout.list_item_popup, null, false);
        final EditText edit = inflatedView.findViewById(R.id.edittext_1);
        ImageView img = inflatedView.findViewById(R.id.imageview_1);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        final PopupWindow popWindow = new PopupWindow(getActivity());
        RecyclerView mRecyclerview = inflatedView.findViewById(R.id.recyclerview_1);
        mRecyclerview.setLayoutManager(mLinearLayoutManager);
        final MasterAdapter adapter = new MasterAdapter(getActivity(), mdataset, new MasterAdapter.OnItemClickListener() {
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
                    showPopupDrug(anchorView, mdataset,masterkey);
                    popWindow.dismiss();
                } else if (masterkey.equalsIgnoreCase(Constants.Doses)) {
                    onShowPopupdoses(anchorView, mdataset,masterkey);
                    popWindow.dismiss();
                } else if (masterkey.equalsIgnoreCase(Constants.Duration)) {
                    onShowPopupfrequency(anchorView, mdataset,masterkey);
                    popWindow.dismiss();
                } else {
                    if (edit.getText().toString().trim().length() > 0) {
                        long index = DatabaseHelper.getInstance(getActivity()).savemaster(edit.getText().toString(), "0", 0, masterkey);
                        Master master = new Master(edit.getText().toString());
                        master.setTestId("0");
                        master.setLabId("0");
                        mdataset.add(master);
                        adapter.notifyDataSetChanged();
                        ((EditText) anchorView).setText(edit.getText().toString().trim());
                        popWindow.dismiss();
                    } else {
                        Toast.makeText(getActivity(), "please enter details", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public void showPopupDrug(final View anchorView, ArrayList<Master> mdataset,final  String masterkey) {
        // hideKeypad();
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        final PopupWindow popWindow = new PopupWindow(getActivity());
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
                    obj.put("DrugName", e3.getText().toString());
                    obj.put("Generic", e2.getText().toString());
                    obj.put("Manufacture", e4.getText().toString());
                    obj.put("Weight", e5.getText().toString());
                    obj.put("Unit", e6.getText().toString());
                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    obj.put("PracticeId", pref.getString("CustomerId", "0"));
                    obj.put("DepartmentId", pref.getString("DepartmentId", "0"));
                   // Log.e("master ","master "+obj.toString());
                    long index = DatabaseHelper.getInstance(getActivity()).savemaster(data, "0", 0, Constants.Drug, obj.toString());
                    if (index != 0 && InternetUtils.getInstance(getActivity()).available()) {
                        obj.put("id", index);
                        ConnectionManager.getInstance(getActivity()).savedrugmaster(obj.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void onShowPopupfrequency(final View anchorView, final ArrayList<Master> mdataset,final  String masterkey) {
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                mdataset.add(new Master(edit.getText().toString() + " " + spinner.getSelectedItem().toString()));
                adapter.notifyDataSetChanged();
                long index = DatabaseHelper.getInstance(getActivity()).savemaster(edit.getText().toString() + " " + spinner.getSelectedItem().toString(), "0", 0, masterkey);

            }
        });

        final PopupWindow popWindow = new PopupWindow(getActivity());
        popWindow.setContentView(inflatedView);
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(true);
        popWindow.setWidth(anchorView.getWidth());
        popWindow.setHeight(popupheight);
        popWindow.showAsDropDown(anchorView);
        popWindow.setFocusable(true);
    }

    public void onShowPopupdoses(final View anchorView, final ArrayList<Master> mdataset, final String masterkey) {
        // hideKeypad();
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        final PopupWindow popWindow = new PopupWindow(getActivity());

        popWindow.setContentView(inflatedView);

        popWindow.setBackgroundDrawable(new BitmapDrawable());
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(true);

        popWindow.setWidth(anchorView.getWidth());
       /* popWindow.setHeight(200);*/

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

                Log.e("ans ","ans "+ans);

                ((EditText) anchorView).setText(ans);
                mdataset.add(new Master(ans));
                adapter.notifyDataSetChanged();
                DatabaseHelper.getInstance(getActivity()).savemaster(ans,"0",0,masterkey);
                popWindow.dismiss();
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
                        int id = (int) DatabaseHelper.getInstance(getActivity()).savevisitdata(Integer.toString(GlobalValues.selectedpt.getId()), visitdata.get(i).getVisitid(), visitdata.get(i).getcontentvalues());
                        visitdata.get(i).setId(id);
                        Log.e("dbid ", "dbid " + id);
                    }
                } else {

                }
                Log.e("visistsize ", "visistsize " + visitdata.size());
                adaptervisit = new ArrayAdapter<Visits>(getActivity(), android.R.layout.simple_dropdown_item_1line, visitdata);
                spinner.setAdapter(adaptervisit);
              /*  if (visitdata.size() > 1)
                    refitBtn.setVisibility(View.VISIBLE);
                else refitBtn.setVisibility(View.GONE);*/
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
                    if (InternetUtils.getInstance(getActivity()).available()) {
                        ConnectionManager.getInstance(getActivity()).AddVisit(obj.toString(), 0);
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

    public void addcurrentvisit(Calendar myCalendar) {
     //   Log.e("current visit callled ", "currentvisitcalled ");
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
            visit = DatabaseHelper.getInstance(getActivity()).getmaxvisitcount(GlobalValues.selectedpt.getId()) + 1;
        }

        v.setVisit(visit);
        v.setDrname(GlobalValues.DrName);
        v.setSync(0);

        long id = DatabaseHelper.getInstance(getActivity()).savevisitdata(Integer.toString(v.getDBPatientid()), 0, v.getcontentvalues());
        v.setId((int) id);
        visitdata.add(0, v);
      /*  adaptervisit.notifyDataSetChanged();
        if (visitdata.size() > 0)
            visitSelection.setSelection(0);*/

        adaptervisit = new ArrayAdapter<Visits>(getActivity(), android.R.layout.simple_dropdown_item_1line, visitdata);
        spinner.setAdapter(adaptervisit);
        if (visitdata.size() > 0)
            spinner.setSelection(0);

    }

    public void adddata() {
        try {
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create(); //Read Update
            alertDialog.setTitle("Add Visit");
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.list_item_dialog_edittext, null);
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
                       // reporterror(tag, e.toString());
                    }
                }
            };

            e1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    try {
                        new DatePickerDialog(getActivity(), date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                      //  reporterror(tag, e.toString());
                    }
                }
            });

            alertDialog.setView(v);
            alertDialog.setButton("Add", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        if (InternetUtils.getInstance(getActivity()).available()) {
                            JSONObject obj = new JSONObject();
                            obj.put("VisitDate", e1.getText().toString());
                            obj.put("Patientid", GlobalValues.selectedpt.getPatientid());
                            obj.put("UserId", GlobalValues.docid);
                            obj.put("practiceid", GlobalValues.branchid);
                            obj.put("connectionid", Constants.projectid);
                           // genloading("loading...");
                            ConnectionManager.getInstance(getActivity()).AddVisit(obj.toString(), 0);
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
                            DatabaseHelper.getInstance(getActivity().savevisitdata(Integer.toString(v.getPatientid()), 0, v.getcontentvalues());*/
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
            //reporterror(tag, e.toString());
        }
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
