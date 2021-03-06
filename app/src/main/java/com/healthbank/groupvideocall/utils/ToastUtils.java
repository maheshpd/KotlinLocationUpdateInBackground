package com.healthbank.groupvideocall.utils;

import android.content.Context;
import androidx.annotation.NonNull;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class ToastUtils {
    private static Toast mToast;

    public static void show(WeakReference<Context> mContext, @NonNull String str) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext.get(), str, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(str);
        }

        mToast.show();
    }
}
