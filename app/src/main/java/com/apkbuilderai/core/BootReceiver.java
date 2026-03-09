package com.apkbuilderai.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {
    private static final String TAG = "BootReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "استلام بث النظام: " + intent.getAction());
        
        // تشغيل الخدمة في الخلفية بمجرد استلام بث النظام
        Intent serviceIntent = new Intent(context, BackgroundService.class);
        try {
            context.startService(serviceIntent);
            Log.i(TAG, "تم تشغيل BackgroundService بنجاح");
        } catch (Exception e) {
            Log.e(TAG, "فشل تشغيل الخدمة: " + e.getMessage());
        }
    }
}
