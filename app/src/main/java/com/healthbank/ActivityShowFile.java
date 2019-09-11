package com.healthbank;

import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ActivityShowFile extends ActivityCommon {
    ImageView img;
    boolean editable = false;
    String s = "{examid:10,ip:\"http://192.168.1.59:8095\"}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_file);
        setmaterialDesign();
        back();

        img = findViewById(R.id.imageView1);
        Bundle bundle = getIntent().getExtras();
        String url = bundle.getString("url");
        String extension = bundle.getString("extension");
        if (bundle.containsKey("iseditable"))
            editable = bundle.getBoolean("iseditable");

        Picasso.with(this).load(url)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher) // optional
                //.transform(new CropSquareTransformation(this, ""+1041,false))
                .into(img);
    }
}
