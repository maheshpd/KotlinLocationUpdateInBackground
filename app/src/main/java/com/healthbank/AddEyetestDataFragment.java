package com.healthbank;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.healthbank.database.DatabaseHelper;

import org.json.JSONObject;
/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddEyetestDataFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddEyetestDataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEyetestDataFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button bt1;
    EditText edt1, edt2, edt3, edt4, edt5, edt6, edt7, edt8, edt9, edt10, edt11, edt12, edt13, edt14, edt15, edt16, edt17, edt18, edt19, edt20, edt21, edt22, edt23, edt24, edt25, edt26, edt27, edt28, edt29, edt30, edt31, edt32, edt33, edt34, edt35, edt36, edt37, edt38, edt39, edt40, edt41, edt42, edt43, edt44, edt45, edt46, edt47, edt48, edt49, edt50, edt51, edt52, edt53, edt54, edt55, edt56, edt57, edt58, edtedit;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddEyetestDataFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AddEyetestDataFragment newInstance(String param1, String param2) {
        AddEyetestDataFragment fragment = new AddEyetestDataFragment();
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
        final View v = inflater.inflate(R.layout.fragment_add_eyetest_data, container, false);
        bt1 = v.findViewById(R.id.button_1);
        edt1 = v.findViewById(R.id.editText_1);
        edt2 = v.findViewById(R.id.editText_2);
        edt3 = v.findViewById(R.id.editText_3);
        edt4 = v.findViewById(R.id.editText_4);
        edt5 = v.findViewById(R.id.editText_5);
        edt6 = v.findViewById(R.id.editText_6);
        edt7 = v.findViewById(R.id.editText_7);
        edt8 = v.findViewById(R.id.editText_8);
        edt9 = v.findViewById(R.id.editText_9);
        edt10 = v.findViewById(R.id.editText_10);
        edt11 = v.findViewById(R.id.editText_11);
        edt12 = v.findViewById(R.id.editText_12);
        edt13 = v.findViewById(R.id.editText_13);
        edt14 = v.findViewById(R.id.editText_14);
        edt15 = v.findViewById(R.id.editText_15);
        edt16 = v.findViewById(R.id.editText_16);
        edt17 = v.findViewById(R.id.editText_17);
        edt18 = v.findViewById(R.id.editText_18);
        edt19 = v.findViewById(R.id.editText_19);
        edt20 = v.findViewById(R.id.editText_20);

        edt21 = v.findViewById(R.id.editText_21);
        edt22 = v.findViewById(R.id.editText_22);
        edt23 = v.findViewById(R.id.editText_23);
        edt24 = v.findViewById(R.id.editText_24);
        edt25 = v.findViewById(R.id.editText_25);
        edt26 = v.findViewById(R.id.editText_26);
        edt27 = v.findViewById(R.id.editText_27);
        edt28 = v.findViewById(R.id.editText_28);
        edt29 = v.findViewById(R.id.editText_29);
        edt30 = v.findViewById(R.id.editText_30);

        edt31 = v.findViewById(R.id.editText_31);
        edt32 = v.findViewById(R.id.editText_32);
        edt33 = v.findViewById(R.id.editText_33);
        edt34 = v.findViewById(R.id.editText_34);
        edt35 = v.findViewById(R.id.editText_35);
        edt36 = v.findViewById(R.id.editText_36);
        edt37 = v.findViewById(R.id.editText_37);
        edt38 = v.findViewById(R.id.editText_38);
        edt39 = v.findViewById(R.id.editText_39);
        edt40 = v.findViewById(R.id.editText_40);

        edt41 = v.findViewById(R.id.editText_41);
        edt42 = v.findViewById(R.id.editText_42);
        edt43 = v.findViewById(R.id.editText_43);
        edt44 = v.findViewById(R.id.editText_44);
        edt45 = v.findViewById(R.id.editText_45);
        edt46 = v.findViewById(R.id.editText_46);
        edt47 = v.findViewById(R.id.editText_47);
        edt48 = v.findViewById(R.id.editText_48);
        edt49 = v.findViewById(R.id.editText_49);
        edt50 = v.findViewById(R.id.editText_50);

        edt51 = v.findViewById(R.id.editText_51);
        edt52 = v.findViewById(R.id.editText_52);
        edt53 = v.findViewById(R.id.editText_53);
        edt54 = v.findViewById(R.id.editText_54);
        edt55 = v.findViewById(R.id.editText_55);
        edt56 = v.findViewById(R.id.editText_56);
        edt57 = v.findViewById(R.id.editText_57);
        edt58 = v.findViewById(R.id.editText_58);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONObject mainobjobj = new JSONObject();
                    JSONObject rootobj = new JSONObject();
                    JSONObject subrootobj = new JSONObject();

                    JSONObject odobj = new JSONObject();
                    JSONObject UCVAobj = new JSONObject();
                    UCVAobj.put("D", ((EditText) (v.findViewById(R.id.editText_1))).getText().toString().trim());
                    UCVAobj.put("N", ((EditText) (v.findViewById(R.id.editText_2))).getText().toString().trim());
                    UCVAobj.put("PH", ((EditText) (v.findViewById(R.id.editText_3))).getText().toString().trim());
                    UCVAobj.put("VN", ((EditText) (v.findViewById(R.id.editText_4))).getText().toString().trim());
                    UCVAobj.put("Add", ((EditText) (v.findViewById(R.id.editText_5))).getText().toString().trim());
                    UCVAobj.put("NV", ((EditText) (v.findViewById(R.id.editText_6))).getText().toString().trim());
                    odobj.put("UCVA", UCVAobj);

                    JSONObject PGPobj = new JSONObject();
                    PGPobj.put("Sph", ((EditText) (v.findViewById(R.id.editText_13))).getText().toString().trim());
                    PGPobj.put("Cyl", ((EditText) (v.findViewById(R.id.editText_14))).getText().toString().trim());
                    PGPobj.put("Axis", ((EditText) (v.findViewById(R.id.editText_15))).getText().toString().trim());
                    PGPobj.put("VN", ((EditText) (v.findViewById(R.id.editText_16))).getText().toString().trim());
                    PGPobj.put("Add", ((EditText) (v.findViewById(R.id.editText_17))).getText().toString().trim());
                    PGPobj.put("NV", ((EditText) (v.findViewById(R.id.editText_18))).getText().toString().trim());
                    odobj.put("PGP", PGPobj);

                    JSONObject MRobj = new JSONObject();
                    MRobj.put("Sph", ((EditText) (v.findViewById(R.id.editText_25))).getText().toString().trim());
                    MRobj.put("Cyl", ((EditText) (v.findViewById(R.id.editText_26))).getText().toString().trim());
                    MRobj.put("Axis", ((EditText) (v.findViewById(R.id.editText_27))).getText().toString().trim());
                    MRobj.put("VN", ((EditText) (v.findViewById(R.id.editText_28))).getText().toString().trim());
                    MRobj.put("Add", ((EditText) (v.findViewById(R.id.editText_29))).getText().toString().trim());
                    MRobj.put("NV", ((EditText) (v.findViewById(R.id.editText_30))).getText().toString().trim());
                    odobj.put("MR", MRobj);

                    JSONObject CRobj = new JSONObject();
                    CRobj.put("Sph", ((EditText) (v.findViewById(R.id.editText_37))).getText().toString().trim());
                    CRobj.put("Cyl", ((EditText) (v.findViewById(R.id.editText_38))).getText().toString().trim());
                    CRobj.put("Axis", ((EditText) (v.findViewById(R.id.editText_39))).getText().toString().trim());
                    CRobj.put("VN", ((EditText) (v.findViewById(R.id.editText_40))).getText().toString().trim());
                    CRobj.put("Add", ((EditText) (v.findViewById(R.id.editText_41))).getText().toString().trim());
                    CRobj.put("NV", ((EditText) (v.findViewById(R.id.editText_42))).getText().toString().trim());

                    odobj.put("CR", CRobj);
                    odobj.put("PUPIL", ((EditText) (v.findViewById(R.id.editText_49))).getText().toString().trim());
                    odobj.put("IOP", ((EditText) (v.findViewById(R.id.editText_51))).getText().toString().trim());
                    odobj.put("EXTEXM", ((EditText) (v.findViewById(R.id.editText_53))).getText().toString().trim());
                    odobj.put("ANTSEG", ((EditText) (v.findViewById(R.id.editText_55))).getText().toString().trim());
                    odobj.put("FUNDUS", ((EditText) (v.findViewById(R.id.editText_57))).getText().toString().trim());
                    subrootobj.put("OD", odobj);

                    JSONObject OSobj = new JSONObject();
                    JSONObject OSUCVAobj = new JSONObject();
                    OSUCVAobj.put("D", ((EditText) (v.findViewById(R.id.editText_7))).getText().toString().trim());
                    OSUCVAobj.put("N", ((EditText) (v.findViewById(R.id.editText_8))).getText().toString().trim());
                    OSUCVAobj.put("PH", ((EditText) (v.findViewById(R.id.editText_9))).getText().toString().trim());
                    OSUCVAobj.put("VN", ((EditText) (v.findViewById(R.id.editText_10))).getText().toString().trim());
                    OSUCVAobj.put("Add", ((EditText) (v.findViewById(R.id.editText_11))).getText().toString().trim());
                    OSUCVAobj.put("NV", ((EditText) (v.findViewById(R.id.editText_12))).getText().toString().trim());
                    OSobj.put("UCVA", OSUCVAobj);

                    JSONObject OSPGPobj = new JSONObject();
                    OSPGPobj.put("Sph", ((EditText) (v.findViewById(R.id.editText_19))).getText().toString().trim());
                    OSPGPobj.put("Cyl", ((EditText) (v.findViewById(R.id.editText_20))).getText().toString().trim());
                    OSPGPobj.put("Axis", ((EditText) (v.findViewById(R.id.editText_21))).getText().toString().trim());
                    OSPGPobj.put("VN", ((EditText) (v.findViewById(R.id.editText_22))).getText().toString().trim());
                    OSPGPobj.put("Add", ((EditText) (v.findViewById(R.id.editText_23))).getText().toString().trim());
                    OSPGPobj.put("NV", ((EditText) (v.findViewById(R.id.editText_24))).getText().toString().trim());
                    OSobj.put("PGP", OSPGPobj);

                    JSONObject OSMRobj = new JSONObject();
                    OSMRobj.put("Sph", ((EditText) (v.findViewById(R.id.editText_31))).getText().toString().trim());
                    OSMRobj.put("Cyl", ((EditText) (v.findViewById(R.id.editText_32))).getText().toString().trim());
                    OSMRobj.put("Axis", ((EditText) (v.findViewById(R.id.editText_33))).getText().toString().trim());
                    OSMRobj.put("VN", ((EditText) (v.findViewById(R.id.editText_34))).getText().toString().trim());
                    OSMRobj.put("Add", ((EditText) (v.findViewById(R.id.editText_35))).getText().toString().trim());
                    OSMRobj.put("NV", ((EditText) (v.findViewById(R.id.editText_36))).getText().toString().trim());
                    OSobj.put("MR", OSMRobj);

                    JSONObject OSCRobj = new JSONObject();
                    OSCRobj.put("Sph", ((EditText) (v.findViewById(R.id.editText_43))).getText().toString().trim());
                    OSCRobj.put("Cyl", ((EditText) (v.findViewById(R.id.editText_44))).getText().toString().trim());
                    OSCRobj.put("Axis", ((EditText) (v.findViewById(R.id.editText_45))).getText().toString().trim());
                    OSCRobj.put("VN", ((EditText) (v.findViewById(R.id.editText_46))).getText().toString().trim());
                    OSCRobj.put("Add", ((EditText) (v.findViewById(R.id.editText_47))).getText().toString().trim());
                    OSCRobj.put("NV", ((EditText) (v.findViewById(R.id.editText_48))).getText().toString().trim());

                    OSobj.put("CR", OSCRobj);
                    OSobj.put("PUPIL", ((EditText) (v.findViewById(R.id.editText_50))).getText().toString().trim());
                    OSobj.put("IOP", ((EditText) (v.findViewById(R.id.editText_52))).getText().toString().trim());
                    OSobj.put("EXTEXM", ((EditText) (v.findViewById(R.id.editText_54))).getText().toString().trim());
                    OSobj.put("ANTSEG", ((EditText) (v.findViewById(R.id.editText_56))).getText().toString().trim());
                    OSobj.put("FUNDUS", ((EditText) (v.findViewById(R.id.editText_58))).getText().toString().trim());
                    subrootobj.put("OS", OSobj);
                    rootobj.put("subroot", subrootobj);
                    mainobjobj.put("root", rootobj);

                    ContentValues c = new ContentValues();
                    c.put(DatabaseHelper.JSONDATA, mainobjobj.toString());
                    c.put(DatabaseHelper.DoctorId, GlobalValues.docid);
                    c.put(DatabaseHelper.Patientid, GlobalValues.selectedpt.getPatientid());
                    c.put(DatabaseHelper.SAVEDTIME, DateUtils.getSqliteTime());
                    DatabaseHelper.getInstance(getActivity()).saveeyespecialitydata(c);
                    //ArrayList<String> mdataset = DatabaseHelper.getInstance(getActivity()).geteyespecialitydata(GlobalValues.selectedpt.getPatientid());

                    Intent intent = new Intent("eyedataupdated");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                    resetallvalues();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return v;
    }

    public void resetallvalues() {
        edt1.setText("");
        edt2.setText("");
        edt3.setText("");
        edt4.setText("");
        edt5.setText("");
        edt6.setText("");

        edt7.setText("");
        edt8.setText("");
        edt9.setText("");
        edt10.setText("");
        edt11.setText("");
        edt12.setText("");

        edt13.setText("");
        edt14.setText("");
        edt15.setText("");
        edt16.setText("");
        edt17.setText("");
        edt18.setText("");

        edt19.setText("");
        edt20.setText("");
        edt21.setText("");
        edt22.setText("");
        edt23.setText("");
        edt24.setText("");

        edt25.setText("");
        edt26.setText("");
        edt27.setText("");
        edt28.setText("");
        edt29.setText("");
        edt30.setText("");

        edt31.setText("");
        edt32.setText("");
        edt33.setText("");
        edt34.setText("");
        edt35.setText("");
        edt36.setText("");

        edt37.setText("");
        edt38.setText("");
        edt39.setText("");
        edt40.setText("");
        edt41.setText("");
        edt42.setText("");

        edt43.setText("");
        edt44.setText("");
        edt45.setText("");
        edt46.setText("");
        edt47.setText("");
        edt48.setText("");

        edt49.setText("");
        edt51.setText("");
        edt53.setText("");
        edt55.setText("");
        edt57.setText("");

        edt50.setText("");
        edt52.setText("");
        edt53.setText("");
        edt56.setText("");
        edt58.setText("");
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
