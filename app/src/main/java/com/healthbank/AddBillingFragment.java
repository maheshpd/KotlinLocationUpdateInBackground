package com.healthbank;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.healthbank.adapter.SelectedTreatmentAdapter;
import com.healthbank.classes.Advicemasterdata;
import com.healthbank.classes.Connection;
import com.healthbank.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.Calendar;

public class AddBillingFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    TextView txt1, txt2, txt3, txt4, txt5, txt6, txt7, txt8, txt9;
    EditText e1, e2, e3, e4, e5, e6, e7, e8, e9, e10;
    RecyclerView recyclerview;
    LinearLayoutManager mlayoutmanager;
    SelectedTreatmentAdapter adapter;
    ArrayList<Advicemasterdata> data;
    Button b1;
    Spinner sp2, sp3, sp4;
    Double tax = 0.0;
    int discounttype = 0;
    int taxtype = 0;
    Double price = 0.0;
    Double Paybaleamount = 0.0;
    Double discount = 0.0;
    Double Dueamount = 0.0;
    Double paidamount = 0.0;

    public AddBillingFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AddBillingFragment newInstance(String param1, String param2) {
        AddBillingFragment fragment = new AddBillingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_billing, container, false);
        txt1 = v.findViewById(R.id.textview_1);
        txt2 = v.findViewById(R.id.textview_2);
        txt3 = v.findViewById(R.id.textview_3);
        txt4 = v.findViewById(R.id.textview_4);
        txt5 = v.findViewById(R.id.textview_5);
        txt6 = v.findViewById(R.id.textview_6);
        txt7 = v.findViewById(R.id.textview_7);
        txt8 = v.findViewById(R.id.textview_8);
        txt9 = v.findViewById(R.id.textview_9);
        sp3 = v.findViewById(R.id.spinner_3);
        sp4 = v.findViewById(R.id.spinner_4);

        sp3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                taxtype = i;
                calculatepayableamount();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp2 = v.findViewById(R.id.spinner_2);
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                discounttype = i;
                calculatepayableamount();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        e1 = v.findViewById(R.id.edittext_1);
        e2 = v.findViewById(R.id.edittext_2);
        e3 = v.findViewById(R.id.edittext_3);
        e4 = v.findViewById(R.id.edittext_4);
        e5 = v.findViewById(R.id.edittext_5);
        e6 = v.findViewById(R.id.edittext_6);
        e7 = v.findViewById(R.id.edittext_7);
        e8 = v.findViewById(R.id.edittext_8);
        e9 = v.findViewById(R.id.edittext_9);
        e10 = v.findViewById(R.id.edittext_10);

        e1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                paidamount = 0.0;
                if (e2.getText().toString().length() > 0) {
                    paidamount = paidamount + Double.parseDouble(e2.getText().toString().trim());
                }
                if (e1.getText().toString().length() > 0) {
                    paidamount = paidamount + Double.parseDouble(e1.getText().toString().trim());
                }
                Dueamount = (Paybaleamount - paidamount);
                if (Dueamount >= 0) {
                    txt6.setText(getActivity().getResources().getString(R.string.char_rupees) + Dueamount);
                    txt9.setText("Due After Payment");
                } else {
                    txt6.setText(getActivity().getResources().getString(R.string.char_rupees) + Double.toString(Dueamount).replace("-", ""));
                    txt9.setText("Advance After Payment");
                }
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        e5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                calculatepayableamount();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        e6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                calculatepayableamount();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        e2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                paidamount = 0.0;
                if (e2.getText().toString().length() > 0) {
                    paidamount = Double.parseDouble(e2.getText().toString().trim());
                }

                if (e1.getText().toString().length() > 0) {
                    paidamount = paidamount + Double.parseDouble(e1.getText().toString().trim());
                }

                Dueamount = (Paybaleamount - paidamount);
                if (Dueamount >= 0) {
                    txt6.setText(getActivity().getResources().getString(R.string.char_rupees) + Dueamount);
                    txt9.setText("Due After Payment");
                } else {
                    txt6.setText(getActivity().getResources().getString(R.string.char_rupees) + Double.toString(Dueamount).replace("-", ""));
                    txt9.setText("Advance After Payment");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        b1 = v.findViewById(R.id.button_1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                String Billno = "Bill_" + cal.get(Calendar.YEAR) + "-" + DatabaseHelper.getInstance(getActivity()).getlastIndex(DatabaseHelper.Table_billing);
                String recno = "Rec_" + cal.get(Calendar.YEAR) + "-" + DatabaseHelper.getInstance(getActivity()).getlastIndex(DatabaseHelper.TABLE_RECIPTEMR);

                ContentValues c = new ContentValues();
                c.put(DatabaseHelper.BILL_NO, Billno);
                c.put(DatabaseHelper.TOTALAMT, Paybaleamount);
                c.put(DatabaseHelper.PAIDAMT, paidamount);
                c.put(DatabaseHelper.DUEAMT, Dueamount);
                c.put(DatabaseHelper.CREATEDDATE, DateUtils.getSqliteTime());
                c.put(DatabaseHelper.MODDATE, DateUtils.getSqliteTime());
                c.put(DatabaseHelper.DELETED, 0);
                c.put(DatabaseHelper.DOCTORID, GlobalValues.docid);
                c.put(DatabaseHelper.Patientid, GlobalValues.practiceid);
                c.put(DatabaseHelper.PRACTICEID, GlobalValues.practiceid);
                c.put(DatabaseHelper.TAXAMT, tax);
                c.put(DatabaseHelper.DESCRIPTION, e7.getText().toString().trim());
                c.put(DatabaseHelper.STATUS, "");
                c.put(DatabaseHelper.REFERID, "");
                c.put(DatabaseHelper.VISITNO, "");
                c.put(DatabaseHelper.REMARK, e4.getText().toString().trim());
                c.put(DatabaseHelper.DISCOUNT, discount);
                c.put(DatabaseHelper.BILLDATE, DateUtils.getSqliteTime());
                c.put(DatabaseHelper.FINALAMOUNT, Paybaleamount);
                c.put(DatabaseHelper.XML, "");
                c.put(DatabaseHelper.RECIEPTIDS, "");
                c.put(DatabaseHelper.RECIEPTNO, recno);
                c.put(DatabaseHelper.CANCELREMARK, "");
                c.put(DatabaseHelper.CANCELDATE, "");
                c.put(DatabaseHelper.REFUNDAMOUNT, "");
                c.put(DatabaseHelper.ITEMDETAIL, "");
                c.put(DatabaseHelper.ITEMALL, "");
                c.put(DatabaseHelper.ADDMITIONNO, "");
                c.put(DatabaseHelper.ENTRYFROM, "");
                c.put(DatabaseHelper.EXTRADISCOUNT, discount);
                c.put(DatabaseHelper.DEPARTMENTID, GlobalValues.department);
                c.put(DatabaseHelper.PRESCRIPTIONPATH, "");
                c.put(DatabaseHelper.PREFERREDDATE, DateUtils.getSqliteTime());
                c.put(DatabaseHelper.PREFERREDTIME, "");
                c.put(DatabaseHelper.LABSTATUS, "");
                c.put(DatabaseHelper.REFERFROM, "");
                c.put(DatabaseHelper.UPDATEBY, "");
                c.put(DatabaseHelper.UPDATEDATE, "");
                c.put(DatabaseHelper.CANCELBY, "");
                c.put(DatabaseHelper.BILLBOOKID, "");
                Toast.makeText(getActivity(), "Thank you", Toast.LENGTH_LONG).show();
                long id = DatabaseHelper.getInstance(getActivity()).savedata(c, DatabaseHelper.Table_billing);

                ContentValues recvalues = new ContentValues();
                recvalues.put(DatabaseHelper.BILL_ID, id);
                recvalues.put(DatabaseHelper.RECEIPT_NO, recno);
                recvalues.put(DatabaseHelper.PAIDAMOUNT, paidamount);
                recvalues.put(DatabaseHelper.CREATED_DATE, DateUtils.getSqliteTime());
                recvalues.put(DatabaseHelper.CREATED_BY, GlobalValues.docid);
                recvalues.put(DatabaseHelper.PRACID, GlobalValues.practiceid);
                recvalues.put(DatabaseHelper.DD_CHEQUE_NO, e10.getText().toString());
                recvalues.put(DatabaseHelper.DD_CHEQUE_DATE, e9.getText().toString());
                recvalues.put(DatabaseHelper.PAID_VIVA, sp4.getSelectedItem().toString());
                recvalues.put(DatabaseHelper.TRANSACTION_ID, "");
                recvalues.put(DatabaseHelper.BANKNAME, e9.getText().toString());
                recvalues.put(DatabaseHelper.TOTALPAIDAMOUNT, paidamount);
                recvalues.put(DatabaseHelper.AMOUNTFROMADVANCE, e1.getText().toString().trim());
                recvalues.put(DatabaseHelper.RECEIPT_DATE, DateUtils.getSqliteTime());
                recvalues.put(DatabaseHelper.PATEINT_ID, GlobalValues.selectedpt.getSrno());
                recvalues.put(DatabaseHelper.PAYREMARK, e4.getText().toString().trim());
                recvalues.put(DatabaseHelper.BILLN0, Billno);
                recvalues.put(DatabaseHelper.ADMITIONNO, "");
                recvalues.put(DatabaseHelper.TRANSCTIONNO, "");
                recvalues.put(DatabaseHelper.TILLLBALANCE, "");
                recvalues.put(DatabaseHelper.RECEPTFROM, "OPD");
                recvalues.put(DatabaseHelper.DDDATE, e8.getText().toString());
                recvalues.put(DatabaseHelper.PRACTICE_ID, GlobalValues.practiceid);
                recvalues.put(DatabaseHelper.DEPARTMENT_ID, GlobalValues.department);

                long recid = DatabaseHelper.getInstance(getActivity()).savedata(recvalues, DatabaseHelper.TABLE_RECIPTEMR);
                c.put(DatabaseHelper.RECIEPTIDS, recid);
                DatabaseHelper.getInstance(getActivity()).updatereceiptid(c, id);
                Double available_amount = paidamount;
                for (int i = 0; i < data.size(); i++) {
                    Double totalitemamount = Double.parseDouble(data.get(i).getAmount()) * data.get(i).getItemcount();
                    Double paiditemamount = 0.0;
                    Double dueitemamount = 0.0;
                    if (available_amount > totalitemamount) {
                        paiditemamount = totalitemamount;
                        available_amount = available_amount - totalitemamount;
                    } else {
                        paiditemamount = available_amount;
                        dueitemamount = totalitemamount - paiditemamount;
                    }

                    ContentValues itemvalues = new ContentValues();
                    itemvalues.put(DatabaseHelper.ITEM, data.get(i).getTitle());
                    itemvalues.put(DatabaseHelper.BILLINGID, id);
                    c.put(DatabaseHelper.CREATEDDATE,DateUtils.getSqliteTime());
                    itemvalues.put(DatabaseHelper.OFFERID, "");
                    itemvalues.put(DatabaseHelper.TAX, "");
                    itemvalues.put(DatabaseHelper.TAXID, "");
                    itemvalues.put(DatabaseHelper.PRODUCTTYPE, data.get(i).getType());
                    itemvalues.put(DatabaseHelper.STOCKID, "");
                    itemvalues.put(DatabaseHelper.ASSIGNBY, GlobalValues.docid);
                    itemvalues.put(DatabaseHelper.ITEM_ID, data.get(i).getId());
                    itemvalues.put(DatabaseHelper.UNIT, data.get(i).getItemcount());
                    itemvalues.put(DatabaseHelper.UNITCOST, data.get(i).getAmount());
                    itemvalues.put(DatabaseHelper.DISAMOUNT, "0");
                    itemvalues.put(DatabaseHelper.TAXAMOUNT, "0");
                    itemvalues.put(DatabaseHelper.TAXTYPE, "");
                    itemvalues.put(DatabaseHelper.DISTYPE, "");
                    itemvalues.put(DatabaseHelper.TREATMENTDATE, DateUtils.getSqliteTime());
                    itemvalues.put(DatabaseHelper.PACK_ID, "");
                    itemvalues.put(DatabaseHelper.INDEXTEMPCOL, "");
                    itemvalues.put(DatabaseHelper.COUNSELINGID, "");
                    itemvalues.put(DatabaseHelper.PROFILEID, "");
                    itemvalues.put(DatabaseHelper.TOTALAMT, totalitemamount);
                    itemvalues.put(DatabaseHelper.PAIDAMT, paiditemamount);
                    itemvalues.put(DatabaseHelper.DUEAMT, dueitemamount);
                    itemvalues.put(DatabaseHelper.Remark, e4.getText().toString());

                    c.put(DatabaseHelper.FINALAMOUNT, totalitemamount);
                    DatabaseHelper.getInstance(getActivity()).savedata(itemvalues, DatabaseHelper.TABLE_BILLDETAILS);

                    Intent intent = new Intent("fragmentlabadded");
                    intent.putExtra(Constants.BROADCAST_URL_ACCESS, Connection.appointmentadded.ordinal());
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }

                DatabaseHelper.getInstance(getActivity()).getdata(DatabaseHelper.Table_billing);
                DatabaseHelper.getInstance(getActivity()).getdata(DatabaseHelper.TABLE_BILLDETAILS);
                DatabaseHelper.getInstance(getActivity()).getdata(DatabaseHelper.TABLE_RECIPTEMR);
            }
        });

        txt7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TreatmentActivity.class);
                startActivity(intent);
            }
        });

        txt1.setText(" Dr. Kirti");
        txt2.setText(" Dr. Arnav");
        data = new ArrayList<>();
        data.addAll(GlobalValues.adviceselectedmasterdata);
        recyclerview = v.findViewById(R.id.recyclerview_1);
        recyclerview.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getActivity(), R.drawable.recycler_divider)));
        LinearLayoutManager mLayoutmanager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(mLayoutmanager);
        adapter = new SelectedTreatmentAdapter(getActivity(), data, this);
        recyclerview.setAdapter(adapter);
        txt4.setText(getActivity().getResources().getString(R.string.char_rupees) + 0);
        return v;
    }

    public void updatetotalcount() {
        price = 0.0;
        for (int i = 0; i < data.size(); i++) {
            price = price + (Double.parseDouble(data.get(i).getAmount()) * data.get(i).getItemcount());
        }
        txt3.setText(getActivity().getResources().getString(R.string.char_rupees) + price);
        calculatepayableamount();
    }

    @Override
    public void onResume() {
        super.onResume();
        data.clear();
        data.addAll(GlobalValues.adviceselectedmasterdata);
        adapter.notifyDataSetChanged();
        updatetotalcount();
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

    public void calculatepayableamount() {
        discount = 0.0;
        Paybaleamount = price;
        if (e5.getText().toString().trim().length() > 0 && price > 0) {
            switch (discounttype) {
                case 0:
                    discount = Double.parseDouble(e5.getText().toString().trim());
                    Paybaleamount = Paybaleamount - discount;
                    break;
                case 1:
                    discount = (Double.parseDouble(e5.getText().toString().trim()) * Paybaleamount) / 100;
                    Paybaleamount = Paybaleamount - discount;
                    break;
            }
        }

        tax = 0.0;
        if (e6.getText().toString().trim().length() > 0 && price > 0) {
            switch (taxtype) {
                case 0:
                    tax = Double.parseDouble(e6.getText().toString().trim());
                    Paybaleamount = Paybaleamount + tax;
                    break;
                case 1:
                    tax = (Double.parseDouble(e6.getText().toString().trim()) * Paybaleamount) / 100;
                    Paybaleamount = Paybaleamount + tax;
                    break;
            }
        }

        txt8.setText(getActivity().getResources().getString(R.string.char_rupees) + Paybaleamount);
        if (Paybaleamount > 0 && e2.getText().toString().trim().length() > 0) {
            Dueamount = (Paybaleamount - Double.parseDouble(e2.getText().toString().trim()));
            if (Dueamount >= 0) {
                txt6.setText(getActivity().getResources().getString(R.string.char_rupees) + Dueamount);
                txt9.setText("Due After Payment");
            } else {
                txt6.setText(getActivity().getResources().getString(R.string.char_rupees) + Double.toString(Dueamount).replace("-", ""));
                txt9.setText("Advance After Payment");
            }
        }


        paidamount = 0.0;
        if (e2.getText().toString().length() > 0) {
            paidamount = Double.parseDouble(e2.getText().toString().trim());
        }

        if (e1.getText().toString().length() > 0) {
            paidamount = paidamount + Double.parseDouble(e1.getText().toString().trim());
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
