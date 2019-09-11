package com.healthbank;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.SparseArray;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

public class BarcodescanActivity extends AppCompatActivity  implements BarcodeReader.BarcodeReaderListener{
    private static final String TAG = BarcodescanActivity.class.getSimpleName();
    int type = 0;
    int categoryid = 0;
    private BarcodeReader barcodeReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcodescan);
    /*    Bundle b = getIntent().getExtras();
        if (b != null) {
            type = b.getInt("type");
            categoryid = b.getInt("categoryid");
        }
        // getting barcode instance*/
        barcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_fragment);
    }


   @Override
    public void onScanned(final Barcode barcode1) {
       //Log.e(TAG, "onScanned: " + barcode1.displayValue);
//        barcodeReader.playBeep();

       runOnUiThread(new Runnable() {
           @Override
           public void run() {
               // Toast.makeText(getApplicationContext(), "Barcode: " + barcode.displayValue, Toast.LENGTH_SHORT).show();
               String barcode = barcode1.displayValue;
               Intent intent=new Intent(BarcodescanActivity.this,WebViewActivity.class);
               intent.putExtra("url",barcode);
               startActivity(intent);
               finish();
           }
       });
   }


    @Override
    public void onScannedMultiple(List<Barcode> barcodes) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String errorMessage) {

    }

    @Override
    public void onCameraPermissionDenied() {
        Toast.makeText(getApplicationContext(), "Camera permission denied!", Toast.LENGTH_LONG).show();
        finish();
    }
}
