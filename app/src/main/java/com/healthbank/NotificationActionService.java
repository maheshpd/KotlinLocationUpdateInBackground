package com.healthbank;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;

public class NotificationActionService extends IntentService {
    public NotificationActionService() {
        super("NotificationActionService");
    }

    public NotificationActionService(String name) {
        super(name);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // TODO Auto-generated method stub
        try {
            Bundle b = intent.getExtras();
            int id = b.getInt("notificationid");

            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.cancel(id);

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
