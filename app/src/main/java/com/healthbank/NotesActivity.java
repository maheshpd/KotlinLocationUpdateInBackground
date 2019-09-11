package com.healthbank;

import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class NotesActivity extends ActivityCommon {
    EditText e1;
    TextView txt;
    ImageView img1, img2, img3;
    View.OnClickListener clk = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int tag = (Integer) view.getTag();
            String text = Html.toHtml(e1.getText());
            int startSelection = e1.getSelectionStart();
            int endSelection = e1.getSelectionEnd();
            String selectedText = e1.getText().toString().substring(startSelection, endSelection);
            switch (tag) {
                case 1:
                    text = text.replace(selectedText, "<b>" + (selectedText + "</b>"));
                    e1.setText(Html.fromHtml(text));
                    txt.setText(Html.fromHtml(text));
                    break;
                case 2:
                    text = text.replace(selectedText, "<i>" + (selectedText + "</i>"));
                    e1.setText(Html.fromHtml(text));
                    txt.setText(Html.fromHtml(text));
                    break;
                case 3:
                    text = text.replace(selectedText, "<u>" + (selectedText + "</u>"));
                    e1.setText(Html.fromHtml(text));
                    txt.setText(Html.fromHtml(text));
                    e1.requestFocus();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        setmaterialDesign();
        back();

        e1 = findViewById(R.id.edittext_1);
        txt = findViewById(R.id.textview_1);
        e1.setText(Html.fromHtml("kirti" + "<br>"));
        img1 = findViewById(R.id.imageview_1);
        img2 = findViewById(R.id.imageview_2);
        img3 = findViewById(R.id.imageview_3);

        img1.setTag(1);
        img2.setTag(2);
        img3.setTag(3);
        img1.setOnClickListener(clk);
        img2.setOnClickListener(clk);
        img3.setOnClickListener(clk);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_text, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
       // Log.e("key code ", "key code " + keyCode);
        switch (keyCode) {
            case 66:
              /*  if (e1.hasFocus()) {
                    String text = e1.getText().toString();
                    e1.setText(Html.fromHtml(text+"<br>"));
                    txt.setText(Html.fromHtml(text+"<br>"));
                    int pos = e1.getText().length();
                    e1.setSelection(pos);
                }*/
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String text = Html.toHtml(e1.getText());
       // Log.e("text ", "text " + text);
        int startSelection = e1.getSelectionStart();
        int endSelection = e1.getSelectionEnd();
        String selectedText = e1.getText().toString().substring(startSelection, endSelection);
        switch (item.getItemId()) {
            case R.id.menu_action_bold:
                text = text.replace(selectedText, "<b>" + (selectedText + "</b>"));
                e1.setText(Html.fromHtml(text));
                txt.setText(Html.fromHtml(text));
                break;
            case R.id.menu_action_italic:
                text = text.replace(selectedText, "<i>" + (selectedText + "</i>"));
                e1.setText(Html.fromHtml(text));
                txt.setText(Html.fromHtml(text));
                break;
            case R.id.menu_action_underline:
                text = text.replace(selectedText, "<u>" + (selectedText + "</u>"));
                e1.setText(Html.fromHtml(text));
                txt.setText(Html.fromHtml(text));
                e1.requestFocus();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}


