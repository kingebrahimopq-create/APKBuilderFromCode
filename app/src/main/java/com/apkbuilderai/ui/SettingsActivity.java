package com.apkbuilderai.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.apkbuilderai.R;
import com.apkbuilderai.utils.SettingsManager;

public class SettingsActivity extends AppCompatActivity {
    private static final String TAG = "SettingsActivity";

    private Spinner themeSpinner;
    private Spinner appLanguageSpinner;
    private CheckBox autoDetectCheckbox;
    private CheckBox notificationsCheckbox;
    private CheckBox saveHistoryCheckbox;
    private Button saveButton;

    private SettingsManager settingsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settingsManager = new SettingsManager(this);

        initializeViews();
        loadSettings();
        setupListeners();
    }

    private void initializeViews() {
        themeSpinner = findViewById(R.id.theme_spinner);
        appLanguageSpinner = findViewById(R.id.app_language_spinner);
        autoDetectCheckbox = findViewById(R.id.auto_detect_checkbox);
        notificationsCheckbox = findViewById(R.id.notifications_checkbox);
        saveHistoryCheckbox = findViewById(R.id.save_history_checkbox);
        saveButton = findViewById(R.id.save_settings_button);

        // إعداد قوائم Spinner
        String[] themes = {"light", "dark"};
        ArrayAdapter<String> themeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, themes);
        themeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        themeSpinner.setAdapter(themeAdapter);

        String[] languages = {"ar", "en"};
        ArrayAdapter<String> languageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, languages);
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        appLanguageSpinner.setAdapter(languageAdapter);
    }

    private void loadSettings() {
        // تحميل المظهر
        String currentTheme = settingsManager.getTheme();
        if (currentTheme.equals("light")) themeSpinner.setSelection(0);
        else themeSpinner.setSelection(1);

        // تحميل اللغة
        String currentLang = settingsManager.getAppLanguage();
        if (currentLang.equals("ar")) appLanguageSpinner.setSelection(0);
        else appLanguageSpinner.setSelection(1);

        // تحميل الخيارات الأخرى
        autoDetectCheckbox.setChecked(settingsManager.isAutoDetectLanguage());
        notificationsCheckbox.setChecked(settingsManager.isNotificationsEnabled());
        saveHistoryCheckbox.setChecked(settingsManager.isSaveHistoryEnabled());
    }

    private void setupListeners() {
        saveButton.setOnClickListener(v -> {
            saveSettings();
        });
    }

    private void saveSettings() {
        try {
            settingsManager.setTheme(themeSpinner.getSelectedItem().toString());
            settingsManager.setAppLanguage(appLanguageSpinner.getSelectedItem().toString());
            settingsManager.setAutoDetectLanguage(autoDetectCheckbox.isChecked());
            settingsManager.setNotificationsEnabled(notificationsCheckbox.isChecked());
            settingsManager.setSaveHistoryEnabled(saveHistoryCheckbox.isChecked());

            Toast.makeText(this, "تم حفظ الإعدادات بنجاح", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Log.e(TAG, "خطأ في حفظ الإعدادات", e);
            Toast.makeText(this, "فشل في حفظ الإعدادات", Toast.LENGTH_SHORT).show();
        }
    }
}
