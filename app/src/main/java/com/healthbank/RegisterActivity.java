package com.healthbank;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.healthbank.database.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity {
    TextView txt;
    EditText e1, e2;
    LinearLayout layout2, layout3;
    Button b1;

    public static boolean hasPermissions(Context context, String... permissions) {
        try {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
                for (String permission : permissions) {
                    if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        attachUI();
    }

    private void attachUI() {
        if (DatabaseHelper.getInstance(RegisterActivity.this).getallmstexamination().length() <= 0) {
            DatabaseHelper.getInstance(RegisterActivity.this).savemstexamination(0, "Diagnosis");
            DatabaseHelper.getInstance(RegisterActivity.this).savemstexamination(0, "Treatement Procedure");
            DatabaseHelper.getInstance(RegisterActivity.this).savemstexamination(0, "Lab");
            DatabaseHelper.getInstance(RegisterActivity.this).savemstexamination(0, "Generic");
            DatabaseHelper.getInstance(RegisterActivity.this).savemstexamination(0, "Brand");
            DatabaseHelper.getInstance(RegisterActivity.this).savemstexamination(0, "Unit");
            DatabaseHelper.getInstance(RegisterActivity.this).savemstexamination(0, "Instruction");
            DatabaseHelper.getInstance(RegisterActivity.this).savemstexamination(0, "Body Part");
            DatabaseHelper.getInstance(RegisterActivity.this).savemstexamination(0, "Dosages");
        }
        checkpermission();
        txt = findViewById(R.id.textview_1);
        // txt.setTypeface(Fonter.getTypefacesemibold(RegisterActivity.this));
        e1 = findViewById(R.id.edittext_1);
        e2 = findViewById(R.id.edittext_2);
        layout2 = findViewById(R.id.layout2);
        layout3 = findViewById(R.id.layout3);
        b1 = findViewById(R.id.button_1);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        e1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    e1.setTextColor(getResources().getColor(R.color.colorPrimary));
                    e2.setTextColor(getResources().getColor(R.color.Black));
                    layout2.setVisibility(View.VISIBLE);
                    layout3.setVisibility(View.GONE);
                } else {
                    e1.setTextColor(getResources().getColor(R.color.Black));
                    e2.setTextColor(getResources().getColor(R.color.colorPrimary));
                    layout2.setVisibility(View.GONE);
                    layout3.setVisibility(View.VISIBLE);
                }
            }
        });
        checkpermission();
    }

    public void checkpermission() {
        try {
            int PERMISSION_ALL = 1;
            String[] PERMISSIONS = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, android.Manifest.permission.INTERNET};
            if (!hasPermissions(this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
