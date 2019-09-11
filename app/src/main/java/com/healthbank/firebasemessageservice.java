package com.healthbank;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.util.Random;

public class firebasemessageservice extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.e("received ", "received");
        try {
            String msg = remoteMessage.getData().get("msgdata");
            Log.e("notificationdata ", "notidata " + msg);
            createNotification(msg);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("error ", "error " + e);
        }
    }

    private void createNotification(String msg) {
        Log.e("create notification", "notification");
        try {
            JSONObject obj = new JSONObject(msg);
            int notificationid = (new Random()).nextInt();
            Context context = getBaseContext();
            String body = obj.getString("request");

            Intent resultIntent = new Intent(this, SplashActivity.class);
            resultIntent.putExtra("isnotified", true);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(SplashActivity.class);

            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            Uri defaultRintoneUri = RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder mBuilder;
            int requestCode = (int) System.currentTimeMillis();
            PendingIntent pendingIntent = PendingIntent.getService(context, requestCode, resultIntent, 0);
            //   Uri defaultRintoneUri = RingtoneManager.getActualDefaultRingtoneUri(context,RingtoneManager.TYPE_NOTIFICATION);
            String id = getString(R.string.default_notification_channel_id); // default_channel_id
            String title1 = getString(R.string.default_notification_channel_title);

            NotificationManager notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = notifManager.getNotificationChannel(id);
                if (mChannel == null) {
                    mChannel = new NotificationChannel(id, "Test", importance);
                    mChannel.enableVibration(true);
                    mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                    notifManager.createNotificationChannel(mChannel);
                }
                mBuilder = new NotificationCompat.Builder(context, id);
                mBuilder.setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                        .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                        .setContentIntent(pendingIntent)
                        .setContentTitle(getResources().getString(R.string.app_name))
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setAutoCancel(true).setSound(defaultRintoneUri);
            } else {
                mBuilder = new NotificationCompat.Builder(context).setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                        .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                        .setContentIntent(pendingIntent)
                        .setContentTitle(getResources().getString(R.string.app_name))
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setAutoCancel(true).setSound(defaultRintoneUri);
            }
            Intent callactivityIntent = new Intent(this, SplashActivity.class);
            callactivityIntent.putExtra("isnotified", true);
            callactivityIntent.putExtra("isvideocall", true);
            callactivityIntent.putExtra("patientid", obj.getInt("patientid"));
            callactivityIntent.putExtra("channel", obj.getString("channel"));
            PendingIntent pendingIntentCancel = PendingIntent.getActivity(this, 0, callactivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.addAction(R.drawable.ic_video, "Video call", pendingIntentCancel);
            NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
            bigText.bigText(body);
            mBuilder.setStyle(bigText);
            Notification notification = mBuilder.build();
            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(notificationid, notification);
            boolean isappopen = false;
            SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(context);

           if(obj.getString("channel").equalsIgnoreCase(preferences.getString("ProviderId","")))
           {
               isappopen=true;
           }
            if(!isappopen) {
                Intent intent = new Intent(context, IncomingcallActivity.class);
                intent.putExtra("isnotified", true);
                intent.putExtra("isvideocall", true);
                intent.putExtra("patientid", obj.getInt("patientid"));
                intent.putExtra("channel", obj.getString("channel"));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                pendingIntent = PendingIntent.getActivity(this, Integer.valueOf(1) /* Request code */, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                startActivity(intent);
            }
        } catch (Exception e) {
            Log.e("error ", "error mNotificationManager" + e);
        }
    }

}
