package com.healthbank;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.healthbank.classes.Connection;
import com.healthbank.classes.MasterDataClinicalExamination;
import com.healthbank.database.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class AddExaminationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView txt1;
    Button b1;
    LinearLayout layout1;
    ArrayAdapter<MasterDataClinicalExamination> chiefcomplaintsadapter;
    ArrayList<MasterDataClinicalExamination> chiefcomplaintsmasterdata;
    LinearLayout layout2;
    ArrayAdapter<MasterDataClinicalExamination> symptomsadapter;
    ArrayList<MasterDataClinicalExamination> symptomsmasterdata;
    LinearLayout layout3;
    ArrayAdapter<MasterDataClinicalExamination> vaccinesadapter;
    ArrayList<MasterDataClinicalExamination> vaccinesmasterdata;
    LinearLayout layout4;
    ArrayAdapter<MasterDataClinicalExamination> allergyadapter;
    ArrayList<MasterDataClinicalExamination> allergymasterdata;
    LinearLayout layout5;
    ArrayAdapter<MasterDataClinicalExamination> diagnosisadapter;
    ArrayList<MasterDataClinicalExamination> diagnosismasterdata;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddExaminationFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AddExaminationFragment newInstance(String param1, String param2) {
        AddExaminationFragment fragment = new AddExaminationFragment();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_examination, container, false);
        layout1= v.findViewById(R.id.layout_1);
        chiefcomplaintsmasterdata=new ArrayList<>();
        chiefcomplaintsmasterdata=DatabaseHelper.getInstance(getActivity()).getclinicalexamination(Constants.CHIEFCOMPLAINT);
        chiefcomplaintsadapter=new ArrayAdapter<MasterDataClinicalExamination>(getActivity(),android.R.layout.simple_list_item_1,chiefcomplaintsmasterdata);
        addview(layout1,chiefcomplaintsadapter);

        layout2= v.findViewById(R.id.layout_2);
        symptomsmasterdata=new ArrayList<>();
        symptomsmasterdata=DatabaseHelper.getInstance(getActivity()).getclinicalexamination(Constants.SYMPTOMS);
        symptomsadapter=new ArrayAdapter<MasterDataClinicalExamination>(getActivity(),android.R.layout.simple_list_item_1,symptomsmasterdata);
        addview(layout2,symptomsadapter);

        layout3= v.findViewById(R.id.layout_3);
        vaccinesmasterdata=new ArrayList<>();
        vaccinesmasterdata=DatabaseHelper.getInstance(getActivity()).getclinicalexamination(Constants.VACCINES);
        vaccinesadapter=new ArrayAdapter<MasterDataClinicalExamination>(getActivity(),android.R.layout.simple_list_item_1,vaccinesmasterdata);
        addview(layout3,vaccinesadapter);

        layout4= v.findViewById(R.id.layout_4);
        allergymasterdata=new ArrayList<>();
        allergymasterdata=DatabaseHelper.getInstance(getActivity()).getclinicalexamination(Constants.ALLERGY);
        allergyadapter=new ArrayAdapter<MasterDataClinicalExamination>(getActivity(),android.R.layout.simple_list_item_1,allergymasterdata);
        addview(layout4,allergyadapter);

        layout5= v.findViewById(R.id.layout_5);
        diagnosismasterdata=new ArrayList<>();
        diagnosismasterdata=DatabaseHelper.getInstance(getActivity()).getclinicalexamination(Constants.DIAGNOSIS);
        diagnosisadapter=new ArrayAdapter<MasterDataClinicalExamination>(getActivity(),android.R.layout.simple_list_item_1,diagnosismasterdata);
        addview(layout5,diagnosisadapter);

        DatabaseHelper.getInstance(getActivity()).getlabdata(GlobalValues.selectedpt.getPatientid(), DatabaseHelper.Examination);
        txt1 = v.findViewById(R.id.textview_1);
        b1 = v.findViewById(R.id.button_1);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONObject obj = new JSONObject();
                    JSONArray chiefcomplaintsarray = new JSONArray();
                    for (int i = 0; i < layout1.getChildCount(); i++) {
                        View v = layout1.getChildAt(i);
                        JSONObject chiefcomplaintsobj = new JSONObject();
                        AutoCompleteTextView autotxt = v.findViewById(R.id.autocompletetextview_1);
                        chiefcomplaintsobj.put("Name", autotxt.getText().toString());
                        chiefcomplaintsarray.put(chiefcomplaintsobj);

                        ContentValues cvalues=new ContentValues();
                        cvalues.put(DatabaseHelper.Title, autotxt.getText().toString());
                        cvalues.put(DatabaseHelper.Description, "");
                        cvalues.put(DatabaseHelper.type,Constants.CHIEFCOMPLAINT);
                        cvalues.put(DatabaseHelper.DOCTORID, GlobalValues.docid);
                        cvalues.put(DatabaseHelper.CODE, "0");
                        cvalues.put(DatabaseHelper.PRACTICEID, GlobalValues.practiceid);
                        cvalues.put(DatabaseHelper.SAVEDTIME, DateUtils.getSqliteTime());
                        long id= DatabaseHelper.getInstance(getActivity()).saveclinicalExamination(Constants.CHIEFCOMPLAINT,cvalues);
                        if (id != -1) {
                            MasterDataClinicalExamination masterData = new MasterDataClinicalExamination();
                            masterData.setTitle(autotxt.getText().toString());
                            masterData.setDescription("");
                            masterData.setType(Constants.CHIEFCOMPLAINT);
                            masterData.setDoctorId(GlobalValues.docid);
                            masterData.setCode("0");
                            masterData.setPracticeId(GlobalValues.practiceid);
                            chiefcomplaintsmasterdata.add(masterData);
                            chiefcomplaintsadapter=new ArrayAdapter<MasterDataClinicalExamination>(getActivity(),android.R.layout.simple_list_item_1,chiefcomplaintsmasterdata);
                            chiefcomplaintsadapter.notifyDataSetChanged();
                        }
                    }
                    obj.put("chiefcomplaints", chiefcomplaintsarray);
                    JSONArray symptomsarray = new JSONArray();
                    for (int i = 0; i < layout2.getChildCount(); i++) {
                        View v = layout2.getChildAt(i);
                        JSONObject symptomsobj = new JSONObject();
                        AutoCompleteTextView autotxt = v.findViewById(R.id.autocompletetextview_1);
                        symptomsobj.put("Name", autotxt.getText().toString());
                        symptomsarray.put(symptomsobj);
                        ContentValues cvalues=new ContentValues();
                        cvalues.put(DatabaseHelper.Title, autotxt.getText().toString());
                        cvalues.put(DatabaseHelper.Description, "");
                        cvalues.put(DatabaseHelper.type,Constants.SYMPTOMS);
                        cvalues.put(DatabaseHelper.DOCTORID, GlobalValues.docid);
                        cvalues.put(DatabaseHelper.CODE, "0");
                        cvalues.put(DatabaseHelper.PRACTICEID, GlobalValues.practiceid);
                        cvalues.put(DatabaseHelper.SAVEDTIME, DateUtils.getSqliteTime());
                        long id= DatabaseHelper.getInstance(getActivity()).saveclinicalExamination(Constants.SYMPTOMS,cvalues);
                        if (id != -1) {
                            MasterDataClinicalExamination masterData = new MasterDataClinicalExamination();
                            masterData.setTitle(autotxt.getText().toString());
                            masterData.setDescription("");
                            masterData.setType(Constants.SYMPTOMS);
                            masterData.setDoctorId(GlobalValues.docid);
                            masterData.setCode("0");
                            masterData.setPracticeId(GlobalValues.practiceid);
                            symptomsmasterdata.add(masterData);
                            symptomsadapter=new ArrayAdapter<MasterDataClinicalExamination>(getActivity(),android.R.layout.simple_list_item_1,symptomsmasterdata);
                            symptomsadapter.notifyDataSetChanged();
                        }
                    }
                    obj.put("symptoms", symptomsarray);
                    JSONArray vaccinesarray = new JSONArray();
                    for (int i = 0; i < layout3.getChildCount(); i++) {
                        View v = layout3.getChildAt(i);
                        JSONObject vaccinesobj = new JSONObject();
                        AutoCompleteTextView autotxt = v.findViewById(R.id.autocompletetextview_1);
                        vaccinesobj.put("Name", autotxt.getText().toString());
                        vaccinesarray.put(vaccinesobj);

                        ContentValues cvalues=new ContentValues();
                        cvalues.put(DatabaseHelper.Title, autotxt.getText().toString());
                        cvalues.put(DatabaseHelper.Description, "");
                        cvalues.put(DatabaseHelper.type,Constants.VACCINES);
                        cvalues.put(DatabaseHelper.DOCTORID, GlobalValues.docid);
                        cvalues.put(DatabaseHelper.CODE, "0");
                        cvalues.put(DatabaseHelper.PRACTICEID, GlobalValues.practiceid);
                        cvalues.put(DatabaseHelper.SAVEDTIME, DateUtils.getSqliteTime());
                        long id= DatabaseHelper.getInstance(getActivity()).saveclinicalExamination(Constants.VACCINES,cvalues);
                        if (id != -1) {
                            MasterDataClinicalExamination masterData = new MasterDataClinicalExamination();
                            masterData.setTitle(autotxt.getText().toString());
                            masterData.setDescription("");
                            masterData.setType(Constants.VACCINES);
                            masterData.setDoctorId(GlobalValues.docid);
                            masterData.setCode("0");
                            masterData.setPracticeId(GlobalValues.practiceid);
                            vaccinesmasterdata.add(masterData);
                            vaccinesadapter=new ArrayAdapter<MasterDataClinicalExamination>(getActivity(),android.R.layout.simple_list_item_1,vaccinesmasterdata);
                            vaccinesadapter.notifyDataSetChanged();
                        }
                    }
                    obj.put("vaccines", vaccinesarray);
                    JSONArray allergyarray = new JSONArray();
                    for (int i = 0; i < layout4.getChildCount(); i++) {
                        View v = layout4.getChildAt(i);
                        JSONObject allergyobj = new JSONObject();
                        AutoCompleteTextView autotxt = v.findViewById(R.id.autocompletetextview_1);
                        allergyobj.put("Name", autotxt.getText().toString());
                        allergyarray.put(allergyobj);

                        ContentValues cvalues=new ContentValues();
                        cvalues.put(DatabaseHelper.Title, autotxt.getText().toString());
                        cvalues.put(DatabaseHelper.Description, "");
                        cvalues.put(DatabaseHelper.type,Constants.ALLERGY);
                        cvalues.put(DatabaseHelper.DOCTORID, GlobalValues.docid);
                        cvalues.put(DatabaseHelper.CODE, "0");
                        cvalues.put(DatabaseHelper.PRACTICEID, GlobalValues.practiceid);
                        cvalues.put(DatabaseHelper.SAVEDTIME, DateUtils.getSqliteTime());
                        long id= DatabaseHelper.getInstance(getActivity()).saveclinicalExamination(Constants.ALLERGY,cvalues);
                        if (id != -1) {
                            MasterDataClinicalExamination masterData = new MasterDataClinicalExamination();
                            masterData.setTitle(autotxt.getText().toString());
                            masterData.setDescription("");
                            masterData.setType(Constants.ALLERGY);
                            masterData.setDoctorId(GlobalValues.docid);
                            masterData.setCode("0");
                            masterData.setPracticeId(GlobalValues.practiceid);
                            allergymasterdata.add(masterData);
                            allergyadapter=new ArrayAdapter<MasterDataClinicalExamination>(getActivity(),android.R.layout.simple_list_item_1,allergymasterdata);
                            allergyadapter.notifyDataSetChanged();
                        }
                    }
                    obj.put("allergy", allergyarray);
                    JSONArray diagnosisarray = new JSONArray();

                    for (int i = 0; i < layout5.getChildCount(); i++) {
                        View v = layout5.getChildAt(i);
                        JSONObject diagnosisobj = new JSONObject();
                        AutoCompleteTextView autotxt = v.findViewById(R.id.autocompletetextview_1);
                        diagnosisobj.put("Name", autotxt.getText().toString());
                        diagnosisarray.put(diagnosisobj);
                        ContentValues cvalues=new ContentValues();
                        cvalues.put(DatabaseHelper.Title, autotxt.getText().toString());
                        cvalues.put(DatabaseHelper.Description, "");
                        cvalues.put(DatabaseHelper.type,Constants.DIAGNOSIS);
                        cvalues.put(DatabaseHelper.DOCTORID, GlobalValues.docid);
                        cvalues.put(DatabaseHelper.CODE, "0");
                        cvalues.put(DatabaseHelper.PRACTICEID, GlobalValues.practiceid);
                        cvalues.put(DatabaseHelper.SAVEDTIME, DateUtils.getSqliteTime());
                        long id= DatabaseHelper.getInstance(getActivity()).saveclinicalExamination(Constants.DIAGNOSIS,cvalues);
                        if (id != -1) {
                            MasterDataClinicalExamination masterData = new MasterDataClinicalExamination();
                            masterData.setTitle(autotxt.getText().toString());
                            masterData.setDescription("");
                            masterData.setType(Constants.DIAGNOSIS);
                            masterData.setDoctorId(GlobalValues.docid);
                            masterData.setCode("0");
                            masterData.setPracticeId(GlobalValues.practiceid);
                            diagnosismasterdata.add(masterData);
                            diagnosisadapter=new ArrayAdapter<MasterDataClinicalExamination>(getActivity(),android.R.layout.simple_list_item_1,diagnosismasterdata);
                            diagnosisadapter.notifyDataSetChanged();
                        }
                    }
                    obj.put("diagnosis", diagnosisarray);
                    JSONArray array = new JSONArray();
                    array.put(obj.toString());
                    ContentValues c = new ContentValues();
                    c.put(DatabaseHelper.Patientid, GlobalValues.selectedpt.getPatientid());
                    c.put(DatabaseHelper.DOCTORID, GlobalValues.docid);
                    c.put(DatabaseHelper.Date, txt1.getText().toString());
                    c.put(DatabaseHelper.JSONDATA, array.toString());
                    DatabaseHelper.getInstance(getActivity()).saveexamination(c, txt1.getText().toString(), GlobalValues.selectedpt.getPatientid(), DatabaseHelper.Examination);
                    DatabaseHelper.getInstance(getActivity()).getlabdata(GlobalValues.selectedpt.getPatientid(), DatabaseHelper.Examination);
                    Intent intent = new Intent("fragmentlabadded");
                    intent.putExtra(Constants.BROADCAST_URL_ACCESS, Connection.appointmentadded.ordinal());
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);

                    layout1.removeAllViews();
                    addview(layout1,chiefcomplaintsadapter);
                    layout2.removeAllViews();
                    addview(layout2,symptomsadapter);
                    layout3.removeAllViews();
                    addview(layout3,vaccinesadapter);
                    layout4.removeAllViews();
                    addview(layout4,allergyadapter);
                    layout5.removeAllViews();
                    addview(layout5,diagnosisadapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Calendar cal = Calendar.getInstance();
        final int mYear = cal.get(Calendar.YEAR);
        int mMonth = cal.get(Calendar.MONTH);
        int mDay = cal.get(Calendar.DAY_OF_MONTH);
        String m = "" + (mMonth+1);
        String d = "" + mDay;
        if ((mMonth + 1) < 10)
            m = "0" + m;

        if (mDay <10)
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
                                    m = "0" + m;

                                if (dayOfMonth > 0)
                                    d = "0" + d;

                                txt1.setText(d + "-" + m + "-" + mYear);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        return v;
    }
    public void addview(final LinearLayout layout, final ArrayAdapter<MasterDataClinicalExamination> adapter) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.layout_addadvice, null);
        AutoCompleteTextView autotxt = v.findViewById(R.id.autocompletetextview_1);
        autotxt.setAdapter(adapter);
        autotxt.setThreshold(0);
        ImageView img = v.findViewById(R.id.imageview_1);
        img.setTag(layout.getChildCount());

        if (layout.getChildCount() == 0) {
            img.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_plus));
        } else {
            img.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_minus));
        }

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int pos = (int) view.getTag();
                    if (pos == 0) {
                        addview(layout,adapter);
                    } else {
                        layout.removeViewAt(pos);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        layout.addView(v);
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        /*if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }*/
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       /* if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
