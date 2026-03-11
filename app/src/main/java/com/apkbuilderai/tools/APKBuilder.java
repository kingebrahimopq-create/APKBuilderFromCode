package com.apkbuilderai.tools;

import android.content.Context;
import android.util.Log;

import com.apkbuilderai.core.DEXGenerator;
import com.apkbuilderai.core.NeuralCompiler;
import com.apkbuilderai.core.SecurityEngine;
import com.apkbuilderai.core.UniversalTranspiler;

import java.io.File;
import java.util.List;

public class APKBuilder {
    private static final String TAG = "APKBuilder";
    private static final long MAX_CODE_SIZE = 1024 * 1024; // 1MB maximum

    public static void build(Context context, String code) throws Exception {
        if (context == null) {
            throw new IllegalArgumentException("السياق (Context) لا يمكن أن يكون فارغاً");
        }

        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("الكود المدخل فارغ");
        }

        // التحقق من حجم الكود
        if (code.length() > MAX_CODE_SIZE) {
            throw new IllegalArgumentException("حجم الكود كبير جداً (الحد الأقصى: 1MB)");
        }

        Log.d(TAG, "بدء عملية بناء APK");

        File tempDir = null;
        try {
            // الخطوة 1: تحليل الكود
            Log.d(TAG, "الخطوة 1: تحليل الكود...");
            NeuralCompiler neuralCompiler = new NeuralCompiler(context);
            neuralCompiler.analyzeCode(code);

            if (neuralCompiler.hasErrors()) {
                List<String> errors = neuralCompiler.getErrors();
                StringBuilder errorMessage = new StringBuilder("أخطاء في الكود:\n");
                for (String error : errors) {
                    errorMessage.append("- ").append(error).append("\n");
                }
                throw new Exception(errorMessage.toString());
            }

            List<String> warnings = neuralCompiler.getWarnings();
            if (!warnings.isEmpty()) {
                Log.w(TAG, "تحذيرات: " + warnings.toString());
            }

            // الخطوة 2: كشف لغة البرمجة والترجمة
            Log.d(TAG, "الخطوة 2: كشف لغة البرمجة والترجمة...");
            String language = UniversalTranspiler.detectLanguage(code);
            Log.d(TAG, "اللغة المكتشفة: " + language);

            String translatedCode = UniversalTranspiler.translateToJava(code, language);
            Log.d(TAG, "تم ترجمة الكود بنجاح");

            // الخطوة 3: تشفير الكود
            Log.d(TAG, "الخطوة 3: تشفير الكود...");
            String[] encryptionResult = SecurityEngine.encryptCode(translatedCode);
            String encryptionKey = encryptionResult[1];
            String codeHash = SecurityEngine.hashCode(translatedCode);
            Log.d(TAG, "تم تشفير الكود بنجاح");
            Log.d(TAG, "بصمة الكود (Hash): " + codeHash);
            Log.d(TAG, "مفتاح التشفير: " + (encryptionKey.isEmpty() ? "فشل" : "تم"));

            // الخطوة 4: توليد ملف DEX
            Log.d(TAG, "الخطوة 4: توليد ملف DEX...");
            tempDir = new File(context.getCacheDir(), "temp_build");
            if (!tempDir.exists()) {
                if (!tempDir.mkdirs()) {
                    throw new IOException("فشل في إنشاء المجلد المؤقت");
                }
            }

            DEXGenerator.generateFromJava(translatedCode, tempDir);
            Log.d(TAG, "تم توليد ملف DEX بنجاح");

            // الخطوة 5: التحقق
            Log.d(TAG, "الخطوة 5: التحقق من نجاح البناء...");
            File dexFile = new File(tempDir, "classes.dex");
            if (dexFile.exists() && dexFile.length() > 0) {
                Log.i(TAG, "تم بناء APK بنجاح!");
                Log.i(TAG, "حجم ملف DEX: " + dexFile.length() + " بايت");
            } else {
                throw new Exception("فشل في توليد ملف DEX");
            }

        } catch (IllegalArgumentException e) {
            Log.e(TAG, "خطأ في المدخلات: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            Log.e(TAG, "خطأ أثناء بناء APK: " + e.getMessage(), e);
            throw new Exception("فشل في بناء APK: " + e.getMessage(), e);
        } finally {
            // تنظيف المجلد المؤقت
            if (tempDir != null && tempDir.exists()) {
                deleteDirectory(tempDir);
            }
        }
    }

    private static void deleteDirectory(File dir) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteDirectory(file);
                }
            }
        }
        dir.delete();
    }

    private static class IOException extends Exception {
        public IOException(String message) {
            super(message);
        }
    }

    public static String getBuildInfo() {
        StringBuilder info = new StringBuilder();
        info.append("معلومات البناء:\n");
        info.append("- الإصدار: 1.0\n");
        info.append("- نوع البناء: Release\n");
        info.append("- الحد الأدنى للـ SDK: 21\n");
        info.append("- الحد الأقصى للـ SDK: 34\n");
        return info.toString();
    }
}
