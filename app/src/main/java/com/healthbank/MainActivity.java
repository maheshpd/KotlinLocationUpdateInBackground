package com.healthbank;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends ActivityCommon {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent=new Intent(this,RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}
