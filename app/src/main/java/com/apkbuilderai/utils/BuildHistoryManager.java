package com.apkbuilderai.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * BuildHistoryManager: مدير سجل البناء
 * يقوم بحفظ واسترجاع سجل عمليات البناء السابقة
 */
public class BuildHistoryManager {
    private static final String TAG = "BuildHistoryManager";
    private static final String PREF_NAME = "build_history";
    private static final String PREF_KEY_COUNT = "build_count";
    private static final String PREF_KEY_PREFIX = "build_";

    private Context context;
    private SharedPreferences preferences;
    private List<BuildHistoryItem> history;

    public BuildHistoryManager(Context context) {
        this.context = context;
        this.preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.history = new ArrayList<>();
        loadHistory();
    }

    /**
     * إضافة عملية بناء إلى السجل
     */
    public void addBuild(String code, String language, boolean success, String errorMessage) {
        BuildHistoryItem item = new BuildHistoryItem(
            code,
            language,
            success,
            errorMessage,
            System.currentTimeMillis()
        );

        history.add(0, item); // إضافة في البداية

        // الاحتفاظ بآخر 100 عملية بناء فقط
        if (history.size() > 100) {
            history.remove(history.size() - 1);
        }

        saveHistory();
        Log.d(TAG, "تمت إضافة عملية بناء إلى السجل");
    }

    /**
     * الحصول على سجل البناء
     */
    public List<BuildHistoryItem> getHistory() {
        return new ArrayList<>(history);
    }

    /**
     * الحصول على عملية بناء محددة
     */
    public BuildHistoryItem getBuild(int index) {
        if (index >= 0 && index < history.size()) {
            return history.get(index);
        }
        return null;
    }

    /**
     * مسح السجل
     */
    public void clearHistory() {
        history.clear();
        preferences.edit().clear().apply();
        Log.d(TAG, "تم مسح سجل البناء");
    }

    /**
     * الحصول على عدد عمليات البناء
     */
    public int getHistoryCount() {
        return history.size();
    }

    /**
     * حفظ السجل
     */
    private void saveHistory() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(PREF_KEY_COUNT, history.size());

        for (int i = 0; i < history.size(); i++) {
            BuildHistoryItem item = history.get(i);
            String key = PREF_KEY_PREFIX + i;
            // حفظ البيانات الأساسية (يمكن تحسينها باستخدام JSON)
            editor.putString(key + "_lang", item.getLanguage());
            editor.putBoolean(key + "_success", item.isSuccess());
            editor.putLong(key + "_time", item.getTimestamp());
        }

        editor.apply();
        Log.d(TAG, "تم حفظ السجل");
    }

    /**
     * تحميل السجل
     */
    private void loadHistory() {
        int count = preferences.getInt(PREF_KEY_COUNT, 0);

        for (int i = 0; i < count; i++) {
            String key = PREF_KEY_PREFIX + i;
            String language = preferences.getString(key + "_lang", "Unknown");
            boolean success = preferences.getBoolean(key + "_success", false);
            long timestamp = preferences.getLong(key + "_time", 0);

            BuildHistoryItem item = new BuildHistoryItem(
                "",
                language,
                success,
                "",
                timestamp
            );

            history.add(item);
        }

        Log.d(TAG, "تم تحميل " + count + " عملية بناء من السجل");
    }

    /**
     * فئة تمثل عملية بناء واحدة في السجل
     */
    public static class BuildHistoryItem implements Serializable {
        private String code;
        private String language;
        private boolean success;
        private String errorMessage;
        private long timestamp;

        public BuildHistoryItem(String code, String language, boolean success, 
                               String errorMessage, long timestamp) {
            this.code = code;
            this.language = language;
            this.success = success;
            this.errorMessage = errorMessage;
            this.timestamp = timestamp;
        }

        // Getters
        public String getCode() { return code; }
        public String getLanguage() { return language; }
        public boolean isSuccess() { return success; }
        public String getErrorMessage() { return errorMessage; }
        public long getTimestamp() { return timestamp; }

        /**
         * الحصول على وقت البناء بصيغة مقروءة
         */
        public String getFormattedTime() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            return sdf.format(new Date(timestamp));
        }

        /**
         * الحصول على ملخص العملية
         */
        public String getSummary() {
            return String.format("اللغة: %s | الحالة: %s | الوقت: %s",
                language,
                success ? "نجح" : "فشل",
                getFormattedTime()
            );
        }
    }
}
