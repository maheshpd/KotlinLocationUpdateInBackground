package com.healthbank;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.healthbank.groupvideocall.openvcall.AGApplication;
import com.healthbank.groupvideocall.openvcall.model.ConstantApp;
import com.healthbank.groupvideocall.openvcall.ui.BaseActivity;
import com.healthbank.groupvideocall.openvcall.ui.ChatActivity;

public class IncomingcallActivity extends BaseActivity {
    ImageView img;
    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        setContentView(R.layout.activity_incomingcall);
        b = getIntent().getExtras();

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(IncomingcallActivity.this);
        GlobalValues.docid = Integer.parseInt(pref.getString("ProviderId", "0"));
        GlobalValues.branchid = Integer.parseInt(pref.getString("CustomerId", "0"));
        GlobalValues.DrName = pref.getString("DrName", "");
        AGApplication.the().getmAgoraAPI().channelJoin(Constants.channel);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        final Ringtone ringtone = RingtoneManager.getRingtone(IncomingcallActivity.this, uri);
        ringtone.play();
        final Vibrator vibrate = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrate.vibrate(VibrationEffect.createOneShot(50000000, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            vibrate.vibrate(50000000);
        }
        img = findViewById(R.id.imageview_1);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ringtone.stop();
                vibrate.cancel();
                AGApplication.mVideoSettings.mChannelName = "2099";
                String encryption = "AES-128-XTS";
                AGApplication.mVideoSettings.mEncryptionKey = encryption;
                Intent i = new Intent(IncomingcallActivity.this, ChatActivity.class);
                i.putExtra(ConstantApp.ACTION_KEY_CHANNEL_NAME, "2099");
                i.putExtra(ConstantApp.ACTION_KEY_ENCRYPTION_KEY, encryption);
                i.putExtra(ConstantApp.ACTION_KEY_ENCRYPTION_MODE, getResources().getStringArray(R.array.encryption_mode_values)[AGApplication.mVideoSettings.mEncryptionModeIndex]);
                startActivity(i);
            }
        });

    }

    @Override
    protected void initUIandEvent() {

    }

    @Override
    protected void deInitUIandEvent() {

    }
}
