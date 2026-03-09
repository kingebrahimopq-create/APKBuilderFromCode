package com.apkbuilderai.core;

import android.util.Log;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * UniversalTranspilerEnhanced: نسخة محسّنة من محول اللغات
 * توفر كشفاً أدق للغة وترجمة أفضل بين اللغات المختلفة
 */
public class UniversalTranspilerEnhanced {
    private static final String TAG = "UniversalTranspilerEnhanced";

    // خريطة النقاط لكل لغة
    private static final Map<String, Integer> LANGUAGE_SCORES = new HashMap<>();

    static {
        LANGUAGE_SCORES.put("Python", 0);
        LANGUAGE_SCORES.put("JavaScript", 0);
        LANGUAGE_SCORES.put("Kotlin", 0);
        LANGUAGE_SCORES.put("Dart", 0);
        LANGUAGE_SCORES.put("Java", 0);
    }

    /**
     * كشف اللغة البرمجية بدقة عالية
     */
    public static LanguageDetectionResult detectLanguageAdvanced(String code) {
        if (code == null || code.trim().isEmpty()) {
            Log.w(TAG, "الكود المدخل فارغ");
            return new LanguageDetectionResult("Java", 0.0);
        }

        Map<String, Double> scores = new HashMap<>();
        scores.put("Python", calculatePythonScore(code));
        scores.put("JavaScript", calculateJavaScriptScore(code));
        scores.put("Kotlin", calculateKotlinScore(code));
        scores.put("Dart", calculateDartScore(code));
        scores.put("Java", calculateJavaScore(code));

        String detectedLanguage = "Java";
        double maxScore = 0.0;

        for (Map.Entry<String, Double> entry : scores.entrySet()) {
            if (entry.getValue() > maxScore) {
                maxScore = entry.getValue();
                detectedLanguage = entry.getKey();
            }
        }

        Log.d(TAG, "اللغة المكتشفة: " + detectedLanguage + " (الثقة: " + maxScore + ")");
        return new LanguageDetectionResult(detectedLanguage, maxScore);
    }

    /**
     * حساب درجة احتمالية أن يكون الكود Python
     */
    private static double calculatePythonScore(String code) {
        double score = 0.0;

        if (code.contains("def ")) score += 30;
        if (code.contains("import ") && !code.contains("import java")) score += 20;
        if (code.contains("if __name__")) score += 40;
        if (code.contains("class ") && code.contains("self")) score += 25;
        if (code.contains("print(")) score += 15;
        if (code.contains("for ") && code.contains(" in ")) score += 20;
        if (code.contains("elif ")) score += 25;
        if (code.contains("try:") || code.contains("except:")) score += 20;
        if (code.contains("with ")) score += 15;
        if (code.contains("lambda")) score += 15;
        if (code.contains("@")) score += 10; // Decorators

        return Math.min(score, 100.0);
    }

    /**
     * حساب درجة احتمالية أن يكون الكود JavaScript
     */
    private static double calculateJavaScriptScore(String code) {
        double score = 0.0;

        if (code.contains("console.log")) score += 30;
        if (code.contains("function ")) score += 20;
        if (code.contains("const ")) score += 15;
        if (code.contains("let ")) score += 15;
        if (code.contains("var ")) score += 10;
        if (code.contains("=>")) score += 25; // Arrow function
        if (code.contains("document.")) score += 35;
        if (code.contains("require(")) score += 20;
        if (code.contains("module.exports")) score += 30;
        if (code.contains("async ") || code.contains("await ")) score += 20;
        if (code.contains("Promise")) score += 15;
        if (code.contains("this.")) score += 10;

        return Math.min(score, 100.0);
    }

    /**
     * حساب درجة احتمالية أن يكون الكود Kotlin
     */
    private static double calculateKotlinScore(String code) {
        double score = 0.0;

        if (code.contains("fun ")) score += 35;
        if (code.contains("val ")) score += 20;
        if (code.contains("var ") && code.contains("fun")) score += 15;
        if (code.contains("class ") && code.contains("fun")) score += 20;
        if (code.contains("println(")) score += 20;
        if (code.contains("data class")) score += 30;
        if (code.contains("companion object")) score += 25;
        if (code.contains("?.")) score += 15; // Safe call
        if (code.contains("!!")) score += 10; // Not-null assertion
        if (code.contains("when ")) score += 20;
        if (code.contains("::")) score += 15; // Reference

        return Math.min(score, 100.0);
    }

    /**
     * حساب درجة احتمالية أن يكون الكود Dart
     */
    private static double calculateDartScore(String code) {
        double score = 0.0;

        if (code.contains("void main()") || code.contains("void main(List")) score += 40;
        if (code.contains("class ") && code.contains("void")) score += 20;
        if (code.contains("print(") && code.contains("void main")) score += 25;
        if (code.contains("final ")) score += 15;
        if (code.contains("const ")) score += 15;
        if (code.contains("import '")) score += 20;
        if (code.contains("library ")) score += 20;
        if (code.contains("async ") || code.contains("await ")) score += 15;
        if (code.contains("=>")) score += 10;
        if (code.contains("Future")) score += 15;

        return Math.min(score, 100.0);
    }

    /**
     * حساب درجة احتمالية أن يكون الكود Java
     */
    private static double calculateJavaScore(String code) {
        double score = 0.0;

        if (code.contains("public class")) score += 30;
        if (code.contains("import java")) score += 35;
        if (code.contains("public static void main")) score += 40;
        if (code.contains("System.out.println")) score += 25;
        if (code.contains("new ")) score += 15;
        if (code.contains("@Override")) score += 20;
        if (code.contains("try-catch") || code.contains("catch")) score += 15;
        if (code.contains("interface ")) score += 15;
        if (code.contains("extends ")) score += 15;
        if (code.contains("implements ")) score += 15;

        return Math.min(score, 100.0);
    }

    /**
     * ترجمة الكود من لغة إلى Java
     */
    public static String translateToJava(String code, String language) {
        if (code == null || code.trim().isEmpty()) {
            return code;
        }

        Log.d(TAG, "ترجمة الكود من " + language + " إلى Java");

        switch (language) {
            case "Python":
                return translateFromPythonAdvanced(code);
            case "JavaScript":
                return translateFromJavaScriptAdvanced(code);
            case "Kotlin":
                return translateFromKotlinAdvanced(code);
            case "Dart":
                return translateFromDartAdvanced(code);
            case "Java":
                return code;
            default:
                Log.w(TAG, "لغة غير معروفة: " + language);
                return code;
        }
    }

    /**
     * ترجمة محسّنة من Python إلى Java
     */
    private static String translateFromPythonAdvanced(String code) {
        String translated = code;

        // ترجمة دوال الطباعة
        translated = translated.replaceAll("print\\s*\\(", "System.out.println(");

        // ترجمة التعريفات
        translated = translated.replaceAll("def\\s+(\\w+)\\s*\\(", "public static void $1(");

        // ترجمة المتغيرات
        translated = translated.replaceAll("^\\s*([a-zA-Z_]\\w*)\\s*=\\s*(['\"].*?['\"])", "String $1 = $2");
        translated = translated.replaceAll("^\\s*([a-zA-Z_]\\w*)\\s*=\\s*(\\d+)", "int $1 = $2");

        // ترجمة الحلقات
        translated = translated.replaceAll("for\\s+(\\w+)\\s+in\\s+range\\s*\\(", "for (int $1 = 0; $1 < ");
        translated = translated.replaceAll("for\\s+(\\w+)\\s+in\\s+", "for (String $1 : ");

        // ترجمة الشروط
        translated = translated.replaceAll("if\\s+", "if (");
        translated = translated.replaceAll("elif\\s+", "} else if (");
        translated = translated.replaceAll("else\\s*:", "} else {");

        // ترجمة try-except
        translated = translated.replaceAll("try\\s*:", "try {");
        translated = translated.replaceAll("except\\s+", "} catch (");

        Log.d(TAG, "تم ترجمة كود Python بنجاح");
        return translated;
    }

    /**
     * ترجمة محسّنة من JavaScript إلى Java
     */
    private static String translateFromJavaScriptAdvanced(String code) {
        String translated = code;

        // ترجمة دوال الطباعة
        translated = translated.replaceAll("console\\.log\\s*\\(", "System.out.println(");

        // ترجمة التعريفات
        translated = translated.replaceAll("function\\s+(\\w+)\\s*\\(", "public static void $1(");
        translated = translated.replaceAll("const\\s+(\\w+)\\s*=", "final String $1 =");
        translated = translated.replaceAll("let\\s+(\\w+)\\s*=", "String $1 =");
        translated = translated.replaceAll("var\\s+(\\w+)\\s*=", "String $1 =");

        // ترجمة الدوال السهمية
        translated = translated.replaceAll("\\(.*?\\)\\s*=>\\s*\\{", "->\\n{");
        translated = translated.replaceAll("\\(.*?\\)\\s*=>", "->");

        // ترجمة async/await
        translated = translated.replaceAll("async\\s+function", "public static");
        translated = translated.replaceAll("await\\s+", "");

        Log.d(TAG, "تم ترجمة كود JavaScript بنجاح");
        return translated;
    }

    /**
     * ترجمة محسّنة من Kotlin إلى Java
     */
    private static String translateFromKotlinAdvanced(String code) {
        String translated = code;

        // ترجمة دوال الطباعة
        translated = translated.replaceAll("println\\s*\\(", "System.out.println(");

        // ترجمة التعريفات
        translated = translated.replaceAll("fun\\s+(\\w+)\\s*\\(", "public static void $1(");
        translated = translated.replaceAll("val\\s+(\\w+)\\s*=", "final String $1 =");
        translated = translated.replaceAll("var\\s+(\\w+)\\s*=", "String $1 =");

        // ترجمة when
        translated = translated.replaceAll("when\\s*\\(", "switch(");
        translated = translated.replaceAll("->", ":");

        // ترجمة Safe call
        translated = translated.replaceAll("\\?\\.", ".");

        Log.d(TAG, "تم ترجمة كود Kotlin بنجاح");
        return translated;
    }

    /**
     * ترجمة محسّنة من Dart إلى Java
     */
    private static String translateFromDartAdvanced(String code) {
        String translated = code;

        // ترجمة دوال الطباعة
        translated = translated.replaceAll("print\\s*\\(", "System.out.println(");

        // ترجمة الدالة الرئيسية
        translated = translated.replaceAll("void main\\s*\\(\\s*\\)", "public static void main(String[] args)");
        translated = translated.replaceAll("void main\\s*\\(List.*?\\)", "public static void main(String[] args)");

        // ترجمة التعريفات
        translated = translated.replaceAll("class\\s+(\\w+)\\s*\\{", "public class $1 {");
        translated = translated.replaceAll("final\\s+(\\w+)\\s+(\\w+)", "final String $2");
        translated = translated.replaceAll("var\\s+(\\w+)", "String $1");

        // ترجمة async/await
        translated = translated.replaceAll("async\\s+", "");
        translated = translated.replaceAll("await\\s+", "");

        Log.d(TAG, "تم ترجمة كود Dart بنجاح");
        return translated;
    }

    /**
     * فئة لتخزين نتائج الكشف عن اللغة
     */
    public static class LanguageDetectionResult {
        private String language;
        private double confidence;

        public LanguageDetectionResult(String language, double confidence) {
            this.language = language;
            this.confidence = confidence;
        }

        public String getLanguage() { return language; }
        public double getConfidence() { return confidence; }
        public boolean isConfident() { return confidence >= 50.0; }
    }
}
