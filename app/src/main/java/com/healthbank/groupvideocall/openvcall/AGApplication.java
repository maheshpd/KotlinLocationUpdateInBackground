package com.healthbank.groupvideocall.openvcall;

import android.app.Application;
import android.util.Log;

import com.healthbank.R;
import com.healthbank.groupvideocall.openvcall.model.CurrentUserSettings;
import com.healthbank.groupvideocall.openvcall.model.WorkerThread;

import io.agora.AgoraAPIOnlySignal;

public class AGApplication extends Application {
    public static final CurrentUserSettings mVideoSettings = new CurrentUserSettings();
    private static AGApplication mInstance;
    private final String TAG = AGApplication.class.getSimpleName();
    private WorkerThread mWorkerThread;
    private AgoraAPIOnlySignal m_agoraAPI;

    public AGApplication() {
        mInstance = this;
    }

    public static AGApplication the() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        setupAgoraEngine();
    }

    public AgoraAPIOnlySignal getmAgoraAPI() {
        return m_agoraAPI;
    }

    private void setupAgoraEngine() {
        String appID = getString(R.string.private_app_id);

        try {
            m_agoraAPI = AgoraAPIOnlySignal.getInstance(this, appID);


        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
            throw new RuntimeException("NEED TO check rtc sdk init fatal error\n" + Log.getStackTraceString(e));
        }
    }

    public synchronized void initWorkerThread() {
        if (mWorkerThread == null) {
            mWorkerThread = new WorkerThread(getApplicationContext());
            mWorkerThread.start();
            mWorkerThread.waitForReady();
        }
    }

    public synchronized WorkerThread getWorkerThread() {
        return mWorkerThread;
    }

    public synchronized void deInitWorkerThread() {
        mWorkerThread.exit();
        try {
            mWorkerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mWorkerThread = null;
    }
}
