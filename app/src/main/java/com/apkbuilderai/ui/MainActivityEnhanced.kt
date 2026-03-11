package com.apkbuilderai.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class MainActivityEnhanced : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // إنشاء واجهة بسيطة برمجياً للتأكد من نجاح البناء
        val textView = TextView(this)
        textView.text = "APK Builder AI is Running!"
        setContentView(textView)
    }
}
