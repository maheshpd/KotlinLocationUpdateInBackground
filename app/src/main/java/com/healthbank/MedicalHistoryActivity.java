package com.healthbank;

import android.os.Bundle;

public class MedicalHistoryActivity extends ActivityCommon {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_history);

        setmaterialDesign();
        back();
        setTitle("Medical History");
    }
}
