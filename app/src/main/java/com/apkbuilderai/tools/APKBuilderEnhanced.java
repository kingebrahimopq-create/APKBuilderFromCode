package com.apkbuilderai.tools;

import android.content.Context;
import android.util.Log;

import com.apkbuilderai.core.DEXGeneratorEnhanced;
import com.apkbuilderai.core.NeuralCompilerEnhanced;
import com.apkbuilderai.core.SecurityEngine;
import com.apkbuilderai.core.UniversalTranspilerEnhanced;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * APKBuilderEnhanced: نسخة محسّنة من منسق البناء الرئيسي
 * توفر معالجة أفضل وتقارير مفصلة عن عملية البناء
 */
public class APKBuilderEnhanced {
    private static final String TAG = "APKBuilderEnhanced";

    private Context context;
    private BuildProgress buildProgress;
    private List<BuildLog> buildLogs;

    public APKBuilderEnhanced(Context context) {
        this.context = context;
        this.buildProgress = new BuildProgress();
        this.buildLogs = new ArrayList<>();
    }

    /**
     * بناء APK من الكود المدخل مع تقارير مفصلة
     */
    public BuildResult build(String code) {
        BuildResult result = new BuildResult();

        try {
            if (context == null) {
                throw new IllegalArgumentException("السياق (Context) لا يمكن أن يكون فارغاً");
            }

            if (code == null || code.trim().isEmpty()) {
                throw new IllegalArgumentException("الكود المدخل فارغ");
            }

            Log.d(TAG, "بدء عملية بناء APK محسّنة");
            addLog("بدء عملية البناء", LogLevel.INFO);

            // الخطوة 1: تحليل الكود
            addLog("الخطوة 1: تحليل الكود...", LogLevel.INFO);
            updateProgress(10, "تحليل الكود");

            NeuralCompilerEnhanced compiler = new NeuralCompilerEnhanced(context);
            NeuralCompilerEnhanced.CodeAnalysisResult analysisResult = compiler.analyzeCode(code);

            if (!analysisResult.isValid()) {
                List<String> errors = analysisResult.getErrors();
                StringBuilder errorMsg = new StringBuilder("أخطاء في الكود:\n");
                for (String error : errors) {
                    errorMsg.append("- ").append(error).append("\n");
                    addLog("خطأ: " + error, LogLevel.ERROR);
                }
                throw new Exception(errorMsg.toString());
            }

            // تسجيل المقاييس
            for (String warning : analysisResult.getWarnings()) {
                addLog("تحذير: " + warning, LogLevel.WARNING);
            }

            addLog("تم تحليل الكود بنجاح", LogLevel.INFO);
            addLog("المقاييس - أسطر: " + analysisResult.getLineCount() + 
                   "، أحرف: " + analysisResult.getCharCount() + 
                   "، دوال: " + analysisResult.getFunctionCount(), LogLevel.DEBUG);

            // الخطوة 2: كشف اللغة والترجمة
            addLog("الخطوة 2: كشف اللغة والترجمة...", LogLevel.INFO);
            updateProgress(30, "كشف اللغة والترجمة");

            UniversalTranspilerEnhanced.LanguageDetectionResult langResult = 
                UniversalTranspilerEnhanced.detectLanguageAdvanced(code);
            
            String detectedLanguage = langResult.getLanguage();
            double confidence = langResult.getConfidence();

            addLog("اللغة المكتشفة: " + detectedLanguage + 
                   " (الثقة: " + String.format("%.1f", confidence) + "%)", LogLevel.INFO);

            if (!langResult.isConfident()) {
                addLog("تحذير: ثقة الكشف منخفضة", LogLevel.WARNING);
            }

            String translatedCode = UniversalTranspilerEnhanced.translateToJava(code, detectedLanguage);
            addLog("تم ترجمة الكود بنجاح", LogLevel.INFO);

            // الخطوة 3: تشفير الكود
            addLog("الخطوة 3: تشفير الكود...", LogLevel.INFO);
            updateProgress(50, "تشفير الكود");

            String encryptedCode = SecurityEngine.encryptCode(translatedCode);
            String codeHash = SecurityEngine.hashCode(translatedCode);

            addLog("تم تشفير الكود بنجاح", LogLevel.INFO);
            addLog("بصمة الكود (SHA-256): " + codeHash, LogLevel.DEBUG);

            // الخطوة 4: توليد ملف DEX
            addLog("الخطوة 4: توليد ملف DEX...", LogLevel.INFO);
            updateProgress(70, "توليد ملف DEX");

            File tempDir = new File(context.getCacheDir(), "temp_build_" + System.currentTimeMillis());
            if (!tempDir.exists()) {
                tempDir.mkdirs();
            }

            DEXGeneratorEnhanced.DEXGenerationResult dexResult = 
                DEXGeneratorEnhanced.generateFromJava(translatedCode, tempDir);

            if (!dexResult.isSuccess()) {
                String errorMsg = dexResult.getErrorMessage();
                addLog("خطأ في توليد DEX: " + errorMsg, LogLevel.ERROR);
                throw new Exception("فشل في توليد ملف DEX: " + errorMsg);
            }

            addLog("تم توليد ملف DEX بنجاح (الحجم: " + dexResult.getFileSize() + " بايت)", LogLevel.INFO);

            // الخطوة 5: التحقق من نجاح البناء
            addLog("الخطوة 5: التحقق من نجاح البناء...", LogLevel.INFO);
            updateProgress(90, "التحقق من البناء");

            File dexFile = dexResult.getDexFile();
            if (dexFile.exists() && dexFile.length() > 0) {
                addLog("تم بناء APK بنجاح!", LogLevel.INFO);
                addLog("حجم ملف DEX: " + dexFile.length() + " بايت", LogLevel.INFO);

                result.setSuccess(true);
                result.setDexFile(dexFile);
                result.setCodeHash(codeHash);
                result.setDetectedLanguage(detectedLanguage);
                result.setLogs(new ArrayList<>(buildLogs));

                updateProgress(100, "اكتمل البناء بنجاح");

            } else {
                throw new Exception("فشل في توليد ملف DEX");
            }

        } catch (IllegalArgumentException e) {
            Log.e(TAG, "خطأ في المدخلات: " + e.getMessage());
            addLog("خطأ في المدخلات: " + e.getMessage(), LogLevel.ERROR);
            result.setSuccess(false);
            result.setErrorMessage(e.getMessage());

        } catch (Exception e) {
            Log.e(TAG, "خطأ أثناء بناء APK: " + e.getMessage(), e);
            addLog("خطأ: " + e.getMessage(), LogLevel.ERROR);
            result.setSuccess(false);
            result.setErrorMessage("فشل في بناء APK: " + e.getMessage());
        }

        result.setLogs(new ArrayList<>(buildLogs));
        return result;
    }

    /**
     * تحديث تقدم البناء
     */
    private void updateProgress(int percentage, String status) {
        buildProgress.setPercentage(percentage);
        buildProgress.setStatus(status);
        Log.d(TAG, "التقدم: " + percentage + "% - " + status);
    }

    /**
     * إضافة سجل البناء
     */
    private void addLog(String message, LogLevel level) {
        BuildLog log = new BuildLog(message, level, System.currentTimeMillis());
        buildLogs.add(log);
        Log.d(TAG, "[" + level.name() + "] " + message);
    }

    public BuildProgress getBuildProgress() {
        return buildProgress;
    }

    public List<BuildLog> getBuildLogs() {
        return buildLogs;
    }

    /**
     * فئة لتخزين نتائج البناء
     */
    public static class BuildResult {
        private boolean success;
        private File dexFile;
        private String codeHash;
        private String detectedLanguage;
        private String errorMessage;
        private List<BuildLog> logs;

        public BuildResult() {
            this.logs = new ArrayList<>();
        }

        public boolean isSuccess() { return success; }
        public void setSuccess(boolean success) { this.success = success; }

        public File getDexFile() { return dexFile; }
        public void setDexFile(File dexFile) { this.dexFile = dexFile; }

        public String getCodeHash() { return codeHash; }
        public void setCodeHash(String codeHash) { this.codeHash = codeHash; }

        public String getDetectedLanguage() { return detectedLanguage; }
        public void setDetectedLanguage(String detectedLanguage) { this.detectedLanguage = detectedLanguage; }

        public String getErrorMessage() { return errorMessage; }
        public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

        public List<BuildLog> getLogs() { return logs; }
        public void setLogs(List<BuildLog> logs) { this.logs = logs; }
    }

    /**
     * فئة لتخزين تقدم البناء
     */
    public static class BuildProgress {
        private int percentage;
        private String status;

        public int getPercentage() { return percentage; }
        public void setPercentage(int percentage) { this.percentage = percentage; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    /**
     * فئة لتخزين سجلات البناء
     */
    public static class BuildLog {
        private String message;
        private LogLevel level;
        private long timestamp;

        public BuildLog(String message, LogLevel level, long timestamp) {
            this.message = message;
            this.level = level;
            this.timestamp = timestamp;
        }

        public String getMessage() { return message; }
        public LogLevel getLevel() { return level; }
        public long getTimestamp() { return timestamp; }
    }

    /**
     * مستويات السجل
     */
    public enum LogLevel {
        DEBUG, INFO, WARNING, ERROR
    }
}
