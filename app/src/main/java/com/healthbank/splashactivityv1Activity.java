package com.healthbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class splashactivityv1Activity extends AppCompatActivity {
Bundle bundle ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashactivityv1);
        bundle=getIntent().getExtras();
        if(bundle!=null) {
            if (bundle.containsKey("isdirectvideocall")) {
                Intent intent = new Intent(this, IncomingcallActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, SplashActivity.class);
                startActivity(intent);
            }
        }else {
            Intent intent = new Intent(this, SplashActivity.class);
            startActivity(intent);
        }
    }
}
