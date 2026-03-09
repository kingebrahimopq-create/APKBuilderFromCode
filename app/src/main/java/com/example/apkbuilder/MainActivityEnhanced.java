package com.example.apkbuilder;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Arrays;
import java.util.List;

public class MainActivityEnhanced extends AppCompatActivity {
    
    private EditText codeInput;
    private Spinner languageSpinner;
    private TextView statusText;
    private TextView metricsText;
    private Button buildButton;
    private Button clearButton;
    private Button settingsButton;
    
    // قائمة اللغات المدعومة
    private final List<String> SUPPORTED_LANGUAGES = Arrays.asList(
        "Java", "Kotlin", "Python", "JavaScript", "Dart"
    );
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_enhanced);
        
        // ربط العناصر
        codeInput = findViewById(R.id.code_input);
        languageSpinner = findViewById(R.id.language_spinner);
        statusText = findViewById(R.id.status_text);
        metricsText = findViewById(R.id.metrics_text);
        buildButton = findViewById(R.id.build_button);
        clearButton = findViewById(R.id.clear_button);
        settingsButton = findViewById(R.id.settings_button);
        
        // إعداد قائمة اللغات الأساسية
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.languages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapter);
        
        // زر البناء
        buildButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildAPK();
            }
        });
        
        // زر المسح
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeInput.setText("");
                metricsText.setText(R.string.status_ready);
                statusText.setText(R.string.status_ready);
            }
        });
        
        // زر الإعدادات
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivityEnhanced.this, 
                    "اللغات المدعومة: Java, Kotlin, Python, JavaScript, Dart", 
                    Toast.LENGTH_LONG).show();
            }
        });
    }
    
    private void buildAPK() {
        String code = codeInput.getText().toString().trim();
        String language = languageSpinner.getSelectedItem().toString();
        
        // التحقق من الإدخال
        if (code.isEmpty()) {
            Toast.makeText(this, R.string.error_empty_code, Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (code.length() < 10) {
            Toast.makeText(this, R.string.error_code_too_short, Toast.LENGTH_SHORT).show();
            return;
        }
        
        // التحقق من دعم اللغة
        if (!SUPPORTED_LANGUAGES.contains(language)) {
            Toast.makeText(this, R.string.error_unsupported_language, Toast.LENGTH_LONG).show();
            return;
        }
        
        // بدء عملية البناء
        startBuildProcess(code, language);
    }
    
    private void startBuildProcess(String code, String language) {
        // تحديث الحالة
        statusText.setText(R.string.status_analyzing);
        
        // حساب مقاييس الكود
        updateMetrics(code);
        
        // محاكاة عملية البناء (يتم استبدالها بالمنطق الفعلي)
        simulateBuildProcess(language);
    }
    
    private void updateMetrics(String code) {
        int lines = code.split("\n").length;
        int chars = code.length();
        int words = code.split("\\s+").length;
        
        // تقدير عدد الدوال (تقدير بسيط)
        int functions = countFunctions(code);
        
        String metrics = String.format(
            "📊 المقاييس:\nالسطور: %d | الأحرف: %d | الكلمات: %d | الدوال: ~%d",
            lines, chars, words, functions
        );
        metricsText.setText(metrics);
    }
    
    private int countFunctions(String code) {
        // تقدير بسيط لعدد الدوال
        String[] keywords = {"function", "def", "public", "private", "protected"};
        int count = 0;
        for (String keyword : keywords) {
            count += code.split(keyword).length - 1;
        }
        return Math.max(1, count);
    }
    
    private void simulateBuildProcess(String language) {
        // محاكاة خطوات البناء
        statusText.setText(R.string.status_detecting_language);
        
        // تأخير بسيط للمحاكاة (في التطبيق الفعلي، هذا يكون عمليات حقيقية)
        statusText.setText(R.string.status_translating);
        
        statusText.setText(R.string.status_generating_dex);
        
        statusText.setText(R.string.status_verifying);
        
        // اكتمال البناء
        statusText.setText(R.string.status_success);
        Toast.makeText(this, R.string.build_success, Toast.LENGTH_SHORT).show();
    }
  }
