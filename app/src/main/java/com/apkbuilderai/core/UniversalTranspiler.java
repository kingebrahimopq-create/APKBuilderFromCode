package com.apkbuilderai.core;

import android.util.Log;

public class UniversalTranspiler {
    private static final String TAG = "UniversalTranspiler";

    /**
     * ترجمة الكود من لغة برمجية إلى Java
     * @param code الكود المراد ترجمته
     * @param language لغة البرمجة الأصلية
     * @return الكود المترجم إلى Java
     */
    public static String translateToJava(String code, String language) {
        if (code == null || code.trim().isEmpty()) {
            Log.w(TAG, "الكود المدخل فارغ");
            return code;
        }

        Log.d(TAG, "ترجمة الكود من " + language + " إلى Java");

        switch (language) {
            case "Python":
                return translateFromPython(code);
            case "JavaScript":
                return translateFromJavaScript(code);
            case "Kotlin":
                return translateFromKotlin(code);
            case "Dart":
                return translateFromDart(code);
            case "Java":
                return code; // لا حاجة للترجمة
            default:
                Log.w(TAG, "لغة غير معروفة: " + language + "، سيتم استخدام الكود كما هو");
                return code;
        }
    }

    /**
     * كشف لغة البرمجة من الكود
     * @param code الكود المراد كشف لغته
     * @return لغة البرمجة المكتشفة
     */
    public static String detectLanguage(String code) {
        if (code == null || code.trim().isEmpty()) {
            Log.w(TAG, "الكود المدخل فارغ، سيتم افتراض Java");
            return "Java";
        }

        // البحث عن مؤشرات لغة Python
        if (isPythonCode(code)) {
            Log.d(TAG, "تم كشف لغة Python");
            return "Python";
        }

        // البحث عن مؤشرات لغة JavaScript
        if (isJavaScriptCode(code)) {
            Log.d(TAG, "تم كشف لغة JavaScript");
            return "JavaScript";
        }

        // البحث عن مؤشرات لغة Kotlin
        if (isKotlinCode(code)) {
            Log.d(TAG, "تم كشف لغة Kotlin");
            return "Kotlin";
        }

        // البحث عن مؤشرات لغة Dart
        if (isDartCode(code)) {
            Log.d(TAG, "تم كشف لغة Dart");
            return "Dart";
        }

        // الافتراضي: Java
        Log.d(TAG, "تم افتراض لغة Java");
        return "Java";
    }

    /**
     * التحقق من أن الكود هو كود Python
     */
    private static boolean isPythonCode(String code) {
        return code.contains("def ") || 
               code.contains("import ") && !code.contains("import java") ||
               code.contains("if __name__") ||
               code.contains("class ") && code.contains("self") ||
               code.contains("print(") ||
               code.contains("for ") && code.contains(" in ");
    }

    /**
     * التحقق من أن الكود هو كود JavaScript
     */
    private static boolean isJavaScriptCode(String code) {
        return code.contains("console.log") ||
               code.contains("function ") ||
               code.contains("const ") ||
               code.contains("let ") ||
               code.contains("var ") ||
               code.contains("=>") ||
               code.contains("document.") ||
               code.contains("require(");
    }

    /**
     * التحقق من أن الكود هو كود Kotlin
     */
    private static boolean isKotlinCode(String code) {
        return code.contains("fun ") ||
               code.contains("val ") ||
               code.contains("var ") && code.contains("fun") ||
               code.contains("class ") && code.contains("fun") ||
               code.contains("println(");
    }

    /**
     * التحقق من أن الكود هو كود Dart
     */
    private static boolean isDartCode(String code) {
        return code.contains("void main()") ||
               code.contains("void main(List") ||
               code.contains("class ") && code.contains("void") ||
               code.contains("print(") && code.contains("void main");
    }

    /**
     * ترجمة كود Python إلى Java
     */
    private static String translateFromPython(String code) {
        String translated = code;
        
        // ترجمة دوال الطباعة
        translated = translated.replace("print(", "System.out.println(");
        
        // ترجمة التعريفات
        translated = translated.replaceAll("def\\s+(\\w+)\\s*\\(", "public static void $1(");
        
        // ترجمة المتغيرات
        translated = translated.replaceAll("^\\s*([a-zA-Z_]\\w*)\\s*=", "String $1 =");
        
        // ترجمة الحلقات
        translated = translated.replaceAll("for\\s+(\\w+)\\s+in\\s+", "for (String $1 : ");
        
        Log.d(TAG, "تم ترجمة كود Python بنجاح");
        return translated;
    }

    /**
     * ترجمة كود JavaScript إلى Java
     */
    private static String translateFromJavaScript(String code) {
        String translated = code;
        
        // ترجمة دوال الطباعة
        translated = translated.replace("console.log(", "System.out.println(");
        
        // ترجمة التعريفات
        translated = translated.replaceAll("function\\s+(\\w+)\\s*\\(", "public static void $1(");
        translated = translated.replaceAll("const\\s+(\\w+)\\s*=", "final String $1 =");
        translated = translated.replaceAll("let\\s+(\\w+)\\s*=", "String $1 =");
        translated = translated.replaceAll("var\\s+(\\w+)\\s*=", "String $1 =");
        
        // ترجمة الدوال السهمية
        translated = translated.replaceAll("\\(.*?\\)\\s*=>", "->"); // تبسيط
        
        Log.d(TAG, "تم ترجمة كود JavaScript بنجاح");
        return translated;
    }

    /**
     * ترجمة كود Kotlin إلى Java
     */
    private static String translateFromKotlin(String code) {
        String translated = code;
        
        // ترجمة دوال الطباعة
        translated = translated.replace("println(", "System.out.println(");
        
        // ترجمة التعريفات
        translated = translated.replaceAll("fun\\s+(\\w+)\\s*\\(", "public static void $1(");
        translated = translated.replaceAll("val\\s+(\\w+)\\s*=", "final String $1 =");
        translated = translated.replaceAll("var\\s+(\\w+)\\s*=", "String $1 =");
        
        Log.d(TAG, "تم ترجمة كود Kotlin بنجاح");
        return translated;
    }

    /**
     * ترجمة كود Dart إلى Java
     */
    private static String translateFromDart(String code) {
        String translated = code;
        
        // ترجمة دوال الطباعة
        translated = translated.replace("print(", "System.out.println(");
        
        // ترجمة الدالة الرئيسية
        translated = translated.replaceAll("void main\\(\\)", "public static void main(String[] args)");
        translated = translated.replaceAll("void main\\(List.*?\\)", "public static void main(String[] args)");
        
        // ترجمة التعريفات
        translated = translated.replaceAll("class\\s+(\\w+)\\s*\\{", "public class $1 {");
        
        Log.d(TAG, "تم ترجمة كود Dart بنجاح");
        return translated;
    }
}
