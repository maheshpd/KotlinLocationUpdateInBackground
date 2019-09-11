package com.healthbank;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.healthbank.classes.Appointmentdata;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TodaysAppointmentFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Appointmentdata> data;
    TextView txt1,txt2;
    public TodaysAppointmentFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TodaysAppointmentFragment newInstance() {
        TodaysAppointmentFragment fragment = new TodaysAppointmentFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //  mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        //if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = view.findViewById(R.id.list);
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sd = new SimpleDateFormat("MM/dd/yy", Locale.US);
            Date date = cal.getTime();
        txt1= view.findViewById(R.id.textview_1);
        txt2= view.findViewById(R.id.textview_2);

        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt1.setTextColor(getResources().getColor(R.color.white));
                txt1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                txt2.setTextColor(getResources().getColor(R.color.textcolor));
                txt2.setBackgroundColor(getResources().getColor(R.color.Graylight));
            }
        });

        txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt2.setTextColor(getResources().getColor(R.color.white));
                txt2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                txt1.setTextColor(getResources().getColor(R.color.textcolor));
                txt1.setBackgroundColor(getResources().getColor(R.color.Graylight));
            }
        });

            // data = DatabaseHelper.getInstance(getActivity()).getappointmentData("table_appointment", sd.format(date));
            data = new ArrayList<>();
            data.add(new Appointmentdata());
            data.add(new Appointmentdata());
            data.add(new Appointmentdata());
            data.add(new Appointmentdata());
            if (data.size() > 0)
                data.get(0).setIssection(true);
            Log.e("data ","data "+data.size());
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new TodaysAppointmentAdapter(data, TodaysAppointmentFragment.this));
          //  recyclerView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.divider)));
       // }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
      //  mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
   /* public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyContent.DummyItem item);
    }

    CustomBottomSheetDialogFragment f;

    public void showdialog(int position) {
        f = CustomBottomSheetDialogFragment.newInstance(data.get(position));
        f.show(getActivity().getSupportFragmentManager(), "Dialog");
    }*/
}
