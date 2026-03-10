package com.apkbuilderai.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.apkbuilderai.R;
import com.apkbuilderai.tools.APKBuilderEnhanced;

/**
 * MainActivityEnhanced: نسخة محسّنة من نشاط الرئيسي
 * توفر واجهة محسّنة وتجربة مستخدم أفضل
 */
public class MainActivityEnhanced extends AppCompatActivity {
    private static final String TAG = "MainActivityEnhanced";

    // عناصر واجهة المستخدم
    private EditText codeInput;
    private Button buildButton;
    private Button clearButton;
    private Button settingsButton;
    private TextView statusText;
    private ProgressBar buildProgress;
    private Spinner languageSpinner;

    // متغيرات التحكم
    private APKBuilderEnhanced apkBuilder;
    private Handler mainHandler;
    private boolean isBuilding = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_enhanced);

        Log.d(TAG, "تهيئة MainActivityEnhanced");

        // تهيئة المتغيرات
        mainHandler = new Handler(Looper.getMainLooper());
        apkBuilder = new APKBuilderEnhanced(this);

        // تهيئة عناصر واجهة المستخدم
        initializeViews();

        // إعداد مستمعي الأحداث
        setupListeners();

        // عرض رسالة الترحيب
        statusText.setText(R.string.status_ready);
        Log.d(TAG, "تم تهيئة MainActivity بنجاح");
    }

    /**
     * تهيئة عناصر واجهة المستخدم
     */
    private void initializeViews() {
        codeInput = findViewById(R.id.code_input);
        buildButton = findViewById(R.id.build_button);
        clearButton = findViewById(R.id.clear_button);
        settingsButton = findViewById(R.id.settings_button);
        statusText = findViewById(R.id.status_text);
        buildProgress = findViewById(R.id.build_progress);
        languageSpinner = findViewById(R.id.language_spinner);

        // التحقق من أن جميع العناصر موجودة
        if (codeInput == null || buildButton == null || statusText == null) {
            Log.e(TAG, "فشل في العثور على أحد العناصر في الـ layout");
            Toast.makeText(this, "خطأ في تحميل واجهة المستخدم", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * إعداد مستمعي الأحداث
     */
    private void setupListeners() {
        // مستمع زر البناء
        buildButton.setOnClickListener(v -> {
            if (!isBuilding) {
                buildAPK();
            }
        });

        // مستمع زر المسح
        if (clearButton != null) {
            clearButton.setOnClickListener(v -> clearCode());
        }

        // مستمع زر الإعدادات
        if (settingsButton != null) {
            settingsButton.setOnClickListener(v -> openSettings());
        }

        // مستمع تغيير النص
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
            Toast.makeText(this, R.string.error_empty_code, Toast.LENGTH_SHORT).show();
            statusText.setText(R.string.error_empty_code);
            return;
        }

        // التحقق من أن الكود ليس قصيراً جداً
        if (code.length() < 10) {
            Log.w(TAG, "الكود قصير جداً: " + code.length() + " أحرف");
            Toast.makeText(this, R.string.error_code_too_short, Toast.LENGTH_SHORT).show();
            statusText.setText(R.string.error_code_too_short);
            return;
        }

        // تحديث حالة الواجهة
        updateUIForBuilding(true);
        statusText.setText(R.string.status_analyzing);
        buildProgress.setVisibility(ProgressBar.VISIBLE);
        buildProgress.setProgress(0);

        // تشغيل البناء في thread منفصل
        new Thread(() -> {
            try {
                APKBuilderEnhanced.BuildResult result = apkBuilder.build(code);

                // تحديث الواجهة في main thread
                mainHandler.post(() -> {
                    if (result.isSuccess()) {
                        statusText.setText(R.string.status_success);
                        Toast.makeText(MainActivityEnhanced.this, 
                            "تم بناء APK بنجاح!\nحجم الملف: " + result.getDexFile().length() + " بايت", 
                            Toast.LENGTH_LONG).show();

                        // عرض معلومات البناء
                        showBuildInfo(result);

                        buildProgress.setProgress(100);

                    } else {
                        statusText.setText(R.string.status_error);
                        Toast.makeText(MainActivityEnhanced.this, 
                            "خطأ: " + result.getErrorMessage(), 
                            Toast.LENGTH_LONG).show();

                        buildProgress.setProgress(0);
                    }

                    // استعادة حالة الواجهة
                    updateUIForBuilding(false);
                    buildProgress.setVisibility(ProgressBar.GONE);
                });

            } catch (Exception e) {
                Log.e(TAG, "خطأ أثناء بناء APK: " + e.getMessage(), e);
                mainHandler.post(() -> {
                    statusText.setText("خطأ: " + e.getMessage());
                    Toast.makeText(MainActivityEnhanced.this, 
                        "خطأ: " + e.getMessage(), 
                        Toast.LENGTH_LONG).show();
                    updateUIForBuilding(false);
                    buildProgress.setVisibility(ProgressBar.GONE);
                });
            }
        }).start();
    }

    /**
     * عرض معلومات البناء
     */
    private void showBuildInfo(APKBuilderEnhanced.BuildResult result) {
        StringBuilder info = new StringBuilder();
        info.append("معلومات البناء:\n");
        info.append("اللغة المكتشفة: ").append(result.getDetectedLanguage()).append("\n");
        info.append("حجم الملف: ").append(result.getDexFile().length()).append(" بايت\n");
        info.append("بصمة الكود: ").append(result.getCodeHash().substring(0, 16)).append("...\n");

        Log.d(TAG, info.toString());
    }

    /**
     * مسح الكود المدخل
     */
    private void clearCode() {
        codeInput.setText("");
        statusText.setText(R.string.status_ready);
        buildProgress.setProgress(0);
        buildProgress.setVisibility(ProgressBar.GONE);
        Toast.makeText(this, "تم مسح الكود", Toast.LENGTH_SHORT).show();
    }

    /**
     * فتح نشاط الإعدادات
     */
    private void openSettings() {
        Log.d(TAG, "فتح الإعدادات");
        android.content.Intent intent = new android.content.Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    /**
     * تحديث حالة واجهة المستخدم أثناء البناء
     */
    private void updateUIForBuilding(boolean isBuilding) {
        this.isBuilding = isBuilding;

        buildButton.setEnabled(!isBuilding);
        if (clearButton != null) clearButton.setEnabled(!isBuilding);
        if (settingsButton != null) settingsButton.setEnabled(!isBuilding);
        codeInput.setEnabled(!isBuilding);
        if (languageSpinner != null) languageSpinner.setEnabled(!isBuilding);

        float alpha = isBuilding ? 0.5f : 1.0f;
        buildButton.setAlpha(alpha);
        if (clearButton != null) clearButton.setAlpha(alpha);
        if (settingsButton != null) settingsButton.setAlpha(alpha);
        codeInput.setAlpha(alpha);
        if (languageSpinner != null) languageSpinner.setAlpha(alpha);

        if (isBuilding) {
            buildButton.setText("جاري البناء...");
        } else {
            buildButton.setText(R.string.button_build);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "تدمير MainActivityEnhanced");
    }
}
