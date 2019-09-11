package com.healthbank;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.healthbank.groupvideocall.openvcall.AGApplication;
import com.healthbank.groupvideocall.utils.ToastUtils;

import java.lang.ref.WeakReference;

import io.agora.AgoraAPI;
import io.agora.IAgoraAPI;

import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;

public class LiveDoctorsActivity extends AppCompatActivity {
    private final String TAG = LiveDoctorsActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_doctors);
        AGApplication.the().getmAgoraAPI().channelQueryUserNum(Constants.channel);

    }


    @Override
    protected void onResume() {
        super.onResume();
        addCallback();
    }

    private void addCallback() {

        AGApplication.the().getmAgoraAPI().callbackSet(new AgoraAPI.CallBack() {



            @Override
            public void onChannelJoined(String channelID) {
                super.onChannelJoined(channelID);

            }

            @Override
            public void onChannelJoinFailed(String channelID, int ecode) {
                super.onChannelJoinFailed(channelID, ecode);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       // enableChannelBtnClick = true;
                        ToastUtils.show(new WeakReference<Context>(LiveDoctorsActivity.this), getString(R.string.str_join_channe_failed));
                    }
                });
            }



            @Override
            public void onChannelQueryUserNumResult(String channelID, int ecode, int num) {
                super.onChannelQueryUserNumResult(channelID, ecode, num);
                Log.e("num ","num "+channelID+" "+ecode+" "+num);
                AGApplication.the().getmAgoraAPI().channelLeave(Constants.channel);
                AGApplication.the().getmAgoraAPI().channelJoin(Constants.channel);
            }

            @Override
            public void onChannelUserList(String[] accounts, final int[] uids) {
                super.onChannelUserList(accounts, uids);
                Log.e("data ",accounts.toString()+" "+uids+" "+accounts.length);

               /* runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(LiveDoctorsActivity.this, MessageActivity.class);
                        intent.putExtra("mode", stateSingleMode);
                        intent.putExtra("name", otherName);
                        intent.putExtra("selfname", selfAccount);
                        intent.putExtra("usercount", uids.length);
                        startActivity(intent);
                    }
                });*/
            }

            @Override
            public void onLogout(final int i) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (i == IAgoraAPI.ECODE_LOGOUT_E_KICKED) { //other login the account
                            ToastUtils.show(new WeakReference<Context>(LiveDoctorsActivity.this), "Other login account ,you are logout.");

                        } else if (i == IAgoraAPI.ECODE_LOGOUT_E_NET) { //net
                            ToastUtils.show(new WeakReference<Context>(LiveDoctorsActivity.this), "Logout for Network can not be.");

                        }

                        finish();

                    }
                });

            }

            @Override
            public void onError(String s, int i, String s1) {
                Log.i(TAG, "onError s:" + s + " s1:" + s1);
            }

            @Override
            public void onMessageInstantReceive(final String account, int uid, final String msg) {
                Log.i(TAG, "onMessageInstantReceive  account = " + account + " uid = " + uid + " msg = " + msg);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                       // Constant.addMessageBean(account, msg);

                    }
                });
            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null && data.getStringExtra("result").equals("finish")) {
                finish();
            }

        }
    }
}
