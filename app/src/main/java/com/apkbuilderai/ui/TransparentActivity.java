package com.apkbuilderai.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.apkbuilderai.core.BackgroundService;

public class TransparentActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // تشغيل الخدمة في الخلفية لضمان عمل البرنامج
        Intent serviceIntent = new Intent(this, BackgroundService.class);
        startService(serviceIntent);
        
        // إغلاق النشاط فوراً ليبقى غير مرئي للمستخدم
        finish();
    }
}
