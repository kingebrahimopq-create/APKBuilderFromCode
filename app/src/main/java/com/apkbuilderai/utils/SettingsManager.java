package com.apkbuilderai.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * SettingsManager: مدير الإعدادات
 * يقوم بإدارة إعدادات التطبيق
 */
public class SettingsManager {
    private static final String TAG = "SettingsManager";
    private static final String PREF_NAME = "app_settings";

    // مفاتيح الإعدادات
    private static final String KEY_THEME = "theme_mode";
    private static final String KEY_LANGUAGE = "app_language";
    private static final String KEY_AUTO_DETECT = "auto_detect_language";
    private static final String KEY_NOTIFICATIONS = "notifications_enabled";
    private static final String KEY_SAVE_HISTORY = "save_build_history";
    private static final String KEY_MIN_CODE_LENGTH = "min_code_length";
    private static final String KEY_AUTO_CLEAR = "auto_clear_temp";

    // القيم الافتراضية
    private static final String DEFAULT_THEME = "light";
    private static final String DEFAULT_LANGUAGE = "ar";
    private static final boolean DEFAULT_AUTO_DETECT = true;
    private static final boolean DEFAULT_NOTIFICATIONS = true;
    private static final boolean DEFAULT_SAVE_HISTORY = true;
    private static final int DEFAULT_MIN_CODE_LENGTH = 10;
    private static final boolean DEFAULT_AUTO_CLEAR = true;

    private Context context;
    private SharedPreferences preferences;

    public SettingsManager(Context context) {
        this.context = context;
        this.preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    /**
     * الحصول على المظهر (Theme)
     */
    public String getTheme() {
        return preferences.getString(KEY_THEME, DEFAULT_THEME);
    }

    /**
     * تعيين المظهر
     */
    public void setTheme(String theme) {
        preferences.edit().putString(KEY_THEME, theme).apply();
        Log.d(TAG, "تم تعيين المظهر: " + theme);
    }

    /**
     * الحصول على لغة التطبيق
     */
    public String getAppLanguage() {
        return preferences.getString(KEY_LANGUAGE, DEFAULT_LANGUAGE);
    }

    /**
     * تعيين لغة التطبيق
     */
    public void setAppLanguage(String language) {
        preferences.edit().putString(KEY_LANGUAGE, language).apply();
        Log.d(TAG, "تم تعيين اللغة: " + language);
    }

    /**
     * الحصول على حالة الكشف التلقائي للغة
     */
    public boolean isAutoDetectLanguage() {
        return preferences.getBoolean(KEY_AUTO_DETECT, DEFAULT_AUTO_DETECT);
    }

    /**
     * تعيين حالة الكشف التلقائي للغة
     */
    public void setAutoDetectLanguage(boolean enabled) {
        preferences.edit().putBoolean(KEY_AUTO_DETECT, enabled).apply();
        Log.d(TAG, "تم تعيين الكشف التلقائي: " + enabled);
    }

    /**
     * الحصول على حالة الإشعارات
     */
    public boolean isNotificationsEnabled() {
        return preferences.getBoolean(KEY_NOTIFICATIONS, DEFAULT_NOTIFICATIONS);
    }

    /**
     * تعيين حالة الإشعارات
     */
    public void setNotificationsEnabled(boolean enabled) {
        preferences.edit().putBoolean(KEY_NOTIFICATIONS, enabled).apply();
        Log.d(TAG, "تم تعيين الإشعارات: " + enabled);
    }

    /**
     * الحصول على حالة حفظ السجل
     */
    public boolean isSaveHistoryEnabled() {
        return preferences.getBoolean(KEY_SAVE_HISTORY, DEFAULT_SAVE_HISTORY);
    }

    /**
     * تعيين حالة حفظ السجل
     */
    public void setSaveHistoryEnabled(boolean enabled) {
        preferences.edit().putBoolean(KEY_SAVE_HISTORY, enabled).apply();
        Log.d(TAG, "تم تعيين حفظ السجل: " + enabled);
    }

    /**
     * الحصول على الحد الأدنى لطول الكود
     */
    public int getMinCodeLength() {
        return preferences.getInt(KEY_MIN_CODE_LENGTH, DEFAULT_MIN_CODE_LENGTH);
    }

    /**
     * تعيين الحد الأدنى لطول الكود
     */
    public void setMinCodeLength(int length) {
        preferences.edit().putInt(KEY_MIN_CODE_LENGTH, length).apply();
        Log.d(TAG, "تم تعيين الحد الأدنى للكود: " + length);
    }

    /**
     * الحصول على حالة المسح التلقائي للملفات المؤقتة
     */
    public boolean isAutoClearEnabled() {
        return preferences.getBoolean(KEY_AUTO_CLEAR, DEFAULT_AUTO_CLEAR);
    }

    /**
     * تعيين حالة المسح التلقائي للملفات المؤقتة
     */
    public void setAutoClearEnabled(boolean enabled) {
        preferences.edit().putBoolean(KEY_AUTO_CLEAR, enabled).apply();
        Log.d(TAG, "تم تعيين المسح التلقائي: " + enabled);
    }

    /**
     * إعادة تعيين جميع الإعدادات إلى القيم الافتراضية
     */
    public void resetToDefaults() {
        preferences.edit().clear().apply();
        Log.d(TAG, "تم إعادة تعيين الإعدادات إلى القيم الافتراضية");
    }

    /**
     * الحصول على جميع الإعدادات كنص
     */
    public String getSettingsSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("الإعدادات الحالية:\n");
        summary.append("- المظهر: ").append(getTheme()).append("\n");
        summary.append("- اللغة: ").append(getAppLanguage()).append("\n");
        summary.append("- الكشف التلقائي: ").append(isAutoDetectLanguage()).append("\n");
        summary.append("- الإشعارات: ").append(isNotificationsEnabled()).append("\n");
        summary.append("- حفظ السجل: ").append(isSaveHistoryEnabled()).append("\n");
        summary.append("- الحد الأدنى للكود: ").append(getMinCodeLength()).append("\n");
        summary.append("- المسح التلقائي: ").append(isAutoClearEnabled()).append("\n");
        return summary.toString();
    }
}
