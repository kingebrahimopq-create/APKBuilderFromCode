package com.apkbuilderai.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.apkbuilderai.R;
import com.apkbuilderai.tools.APKBuilder;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private EditText codeInput;
    private Button buildButton;
    private Button settingsButton;
    private TextView statusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "تهيئة MainActivity");

        // تهيئة العناصر
        initializeViews();

        // إعداد مستمعي الأحداث
        setupListeners();

        // عرض رسالة الترحيب
        statusText.setText("جاهز لبناء APK من الكود");
    }

    /**
     * تهيئة عناصر واجهة المستخدم
     */
    private void initializeViews() {
        codeInput = findViewById(R.id.code_input);
        buildButton = findViewById(R.id.build_button);
        settingsButton = findViewById(R.id.settings_button);
        statusText = findViewById(R.id.status_text);

        // التحقق من أن جميع العناصر موجودة
        if (codeInput == null || buildButton == null || settingsButton == null || statusText == null) {
            Log.e(TAG, "فشل في العثور على أحد العناصر في الـ layout");
            Toast.makeText(this, "خطأ في تحميل واجهة المستخدم", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * إعداد مستمعي الأحداث
     */
    private void setupListeners() {
        // مستمع زر البناء
        buildButton.setOnClickListener(v -> buildAPK());

        // مستمع زر الإعدادات
        settingsButton.setOnClickListener(v -> openSettings());

        // مستمع تغيير النص (اختياري)
        codeInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                statusText.setText("أدخل الكود الخاص بك هنا");
            }
        });
    }

    /**
     * بناء APK من الكود المدخل
     */
    private void buildAPK() {
        Log.d(TAG, "بدء عملية بناء APK");

        String code = codeInput.getText().toString().trim();

        // التحقق من أن الكود ليس فارغاً
        if (code.isEmpty()) {
            Log.w(TAG, "محاولة بناء APK بدون إدخال كود");
            Toast.makeText(this, "الرجاء إدخال الكود", Toast.LENGTH_SHORT).show();
            statusText.setText("خطأ: الكود فارغ");
            return;
        }

        // التحقق من أن الكود ليس قصيراً جداً
        if (code.length() < 10) {
            Log.w(TAG, "الكود قصير جداً: " + code.length() + " أحرف");
            Toast.makeText(this, "الرجاء إدخال كود أطول", Toast.LENGTH_SHORT).show();
            statusText.setText("خطأ: الكود قصير جداً");
            return;
        }

        // تحديث حالة الواجهة
        updateUIForBuilding(true);
        statusText.setText("جاري البناء...");

        try {
            Log.d(TAG, "استدعاء APKBuilder.build()");
            APKBuilder.build(this, code);

            // نجاح البناء
            Log.i(TAG, "تم بناء APK بنجاح");
            statusText.setText("✓ تم البناء بنجاح!");
            Toast.makeText(this, "تم بناء APK بنجاح", Toast.LENGTH_LONG).show();

        } catch (IllegalArgumentException e) {
            // خطأ في المدخلات
            Log.e(TAG, "خطأ في المدخلات: " + e.getMessage());
            statusText.setText("خطأ: " + e.getMessage());
            Toast.makeText(this, "خطأ: " + e.getMessage(), Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            // خطأ عام
            Log.e(TAG, "خطأ أثناء بناء APK: " + e.getMessage(), e);
            statusText.setText("✗ خطأ: " + e.getMessage());
            Toast.makeText(this, "خطأ: " + e.getMessage(), Toast.LENGTH_LONG).show();

        } finally {
            // استعادة حالة الواجهة
            updateUIForBuilding(false);
        }
    }

    /**
     * فتح نشاط الإعدادات
     */
    private void openSettings() {
        Log.d(TAG, "فتح الإعدادات");
        Toast.makeText(this, "الإعدادات قريباً", Toast.LENGTH_SHORT).show();
        statusText.setText("الإعدادات قيد التطوير");
        // TODO: تطبيق نشاط الإعدادات
    }

    /**
     * تحديث حالة واجهة المستخدم أثناء البناء
     * @param isBuilding true إذا كان البناء جاري، false إذا انتهى
     */
    private void updateUIForBuilding(boolean isBuilding) {
        buildButton.setEnabled(!isBuilding);
        settingsButton.setEnabled(!isBuilding);
        codeInput.setEnabled(!isBuilding);

        if (isBuilding) {
            buildButton.setAlpha(0.5f);
            settingsButton.setAlpha(0.5f);
            codeInput.setAlpha(0.5f);
        } else {
            buildButton.setAlpha(1.0f);
            settingsButton.setAlpha(1.0f);
            codeInput.setAlpha(1.0f);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "تدمير MainActivity");
    }
}
