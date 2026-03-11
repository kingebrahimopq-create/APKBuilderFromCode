package com.apkbuilderai.core;

import android.util.Log;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * DEXGenerator: المسؤول عن تحويل كود Java إلى ملفات DEX قابلة للتنفيذ على Android.
 * ملاحظة: في هذه النسخة، يتم توليد هيكل ملف DEX. للبناء الفعلي، يتطلب الأمر أدوات مثل d8/dx
 * التي تكون مدمجة في بيئة تطوير Android.
 */
public class DEXGenerator {
    private static final String TAG = "DEXGenerator";

    public static void generateFromJava(String javaCode, File outputDir) throws IOException {
        if (!outputDir.exists()) {
            if (!outputDir.mkdirs()) {
                throw new IOException("فشل في إنشاء مجلد الإخراج: " + outputDir.getAbsolutePath());
            }
        }

        try {
            if (!isValidJavaCode(javaCode)) {
                throw new IllegalArgumentException("كود Java غير صحيح برمجياً (أخطاء في الأقواس)");
            }

            Log.d(TAG, "بدء عملية تحويل الكود المترجم إلى تنسيق DEX...");

            // إنشاء ملف المصدر الوسيط
            File javaFile = new File(outputDir, "GeneratedClass.java");
            writeCodeToFile(javaFile, javaCode);
            
            // إنشاء ملف DEX (هيكل الملف)
            File dexFile = new File(outputDir, "classes.dex");
            
            // في البيئة الحقيقية، يتم استدعاء ProcessBuilder لتشغيل d8:
            // Runtime.getRuntime().exec("d8 GeneratedClass.class --output .");
            
            simulateDexGeneration(dexFile, javaCode);

            if (dexFile.exists() && dexFile.length() > 0) {
                Log.i(TAG, "تم توليد ملف DEX بنجاح. الحجم: " + dexFile.length() + " بايت");
            } else {
                throw new IOException("فشل في توليد ملف DEX النهائي");
            }

        } catch (Exception e) {
            Log.e(TAG, "خطأ في DEXGenerator: " + e.getMessage());
            throw new IOException("فشل توليد APK: " + e.getMessage(), e);
        }
    }

    private static boolean isValidJavaCode(String code) {
        if (code == null || code.trim().isEmpty()) return false;
        int count = 0;
        for (char c : code.toCharArray()) {
            if (c == '{') count++;
            if (c == '}') count--;
            if (count < 0) return false;
        }
        return count == 0;
    }

    private static void writeCodeToFile(File file, String code) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(code);
        }
    }

    private static void simulateDexGeneration(File dexFile, String javaCode) throws IOException {
        try (FileWriter writer = new FileWriter(dexFile)) {
            // كتابة ترويسة ملف DEX حقيقية (محاكاة للبيئة التعليمية)
            writer.write("dex\n035\n"); 
            writer.write("SHA-1: " + SecurityEngine.hashCode(javaCode).substring(0, 20) + "\n");
            writer.write("تنبيه: هذا الملف تم توليده برمجياً كجزء من عملية البناء الآلي.\n");
            writer.flush();
        }
    }
}
