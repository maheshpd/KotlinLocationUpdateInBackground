package com.healthbank;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class AddVitalsActivity extends ActivityCommon {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vitals);
        setmaterialDesign();
        setTitle("Add Vitals");
        back();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_save:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
