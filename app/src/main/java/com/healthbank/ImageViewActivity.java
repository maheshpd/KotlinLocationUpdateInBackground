package com.healthbank;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.File;

public class ImageViewActivity extends ActivityCommon {
    String filename = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        if (getIntent().getExtras() != null) {
            filename = getIntent().getExtras().getString("filename");
        }
        StructureClass.defineContext(this);
        //File imgFile = new File(StructureClass.generate() + "/" + filename);
        File imgFile = new File( filename);

        if (imgFile.exists()) {

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            ImageView myImage = findViewById(R.id.imageview_1);

            myImage.setImageBitmap(myBitmap);

        }
    }


}
