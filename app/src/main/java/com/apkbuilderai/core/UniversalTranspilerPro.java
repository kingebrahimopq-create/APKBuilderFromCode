package com.apkbuilderai.core;

import android.util.Log;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * UniversalTranspilerPro - محرك ترجمة متقدم
 * يوفر ترجمة دقيقة وشاملة من لغات برمجية متعددة إلى Java
 */
public class UniversalTranspilerPro {
    private static final String TAG = "UniversalTranspilerPro";

    // أنماط التعرف على اللغات
    private static final Map<String, Pattern[]> LANGUAGE_PATTERNS = new HashMap<>();

    static {
        // أنماط Python
        LANGUAGE_PATTERNS.put("Python", new Pattern[]{
                Pattern.compile("\\bdef\\s+\\w+\\s*\\("),
                Pattern.compile("\\bif\\s+__name__\\s*==\\s*['\"]__main__['\"]"),
                Pattern.compile("\\bclass\\s+\\w+\\s*:"),
                Pattern.compile("\\bprint\\s*\\("),
                Pattern.compile("\\bfor\\s+\\w+\\s+in\\s+"),
                Pattern.compile("\\bimport\\s+\\w+"),
                Pattern.compile("\\bfrom\\s+\\w+\\s+import"),
                Pattern.compile("\\breturn\\s+")
        });

        // أنماط JavaScript
        LANGUAGE_PATTERNS.put("JavaScript", new Pattern[]{
                Pattern.compile("\\bconsole\\.log\\s*\\("),
                Pattern.compile("\\bfunction\\s+\\w+\\s*\\("),
                Pattern.compile("\\bconst\\s+\\w+\\s*="),
                Pattern.compile("\\blet\\s+\\w+\\s*="),
                Pattern.compile("\\bvar\\s+\\w+\\s*="),
                Pattern.compile("\\b=>\\s*"),
                Pattern.compile("\\bdocument\\."),
                Pattern.compile("\\brequire\\s*\\("),
                Pattern.compile("\\bmodule\\.exports")
        });

        // أنماط Kotlin
        LANGUAGE_PATTERNS.put("Kotlin", new Pattern[]{
                Pattern.compile("\\bfun\\s+\\w+\\s*\\("),
                Pattern.compile("\\bval\\s+\\w+\\s*:"),
                Pattern.compile("\\bvar\\s+\\w+\\s*:"),
                Pattern.compile("\\bprintln\\s*\\("),
                Pattern.compile("\\bdata\\s+class\\s+"),
                Pattern.compile("\\bcompanion\\s+object"),
                Pattern.compile("\\blambda\\s*\\{")
        });

        // أنماط Dart
        LANGUAGE_PATTERNS.put("Dart", new Pattern[]{
                Pattern.compile("\\bvoid\\s+main\\s*\\("),
                Pattern.compile("\\bclass\\s+\\w+\\s*\\{"),
                Pattern.compile("\\bfinal\\s+\\w+\\s*="),
                Pattern.compile("\\bvar\\s+\\w+\\s*="),
                Pattern.compile("\\bprint\\s*\\("),
                Pattern.compile("\\bimport\\s+['\"]package:"),
                Pattern.compile("\\basync\\s+\\{")
        });

        // أنماط Rust
        LANGUAGE_PATTERNS.put("Rust", new Pattern[]{
                Pattern.compile("\\bfn\\s+\\w+\\s*\\("),
                Pattern.compile("\\blet\\s+\\w+\\s*="),
                Pattern.compile("\\bmut\\s+\\w+"),
                Pattern.compile("\\bprintln!\\s*\\("),
                Pattern.compile("\\bimpl\\s+\\w+"),
                Pattern.compile("\\btrait\\s+\\w+")
        });
    }

    /**
     * كشف لغة البرمجة من الكود بدقة عالية
     */
    public static String detectLanguageAdvanced(String code) {
        if (code == null || code.trim().isEmpty()) {
            Log.w(TAG, "الكود المدخل فارغ");
            return "Java";
        }

        Map<String, Integer> scores = new HashMap<>();

        // حساب درجات لكل لغة
        for (String language : LANGUAGE_PATTERNS.keySet()) {
            Pattern[] patterns = LANGUAGE_PATTERNS.get(language);
            int score = 0;

            for (Pattern pattern : patterns) {
                if (pattern.matcher(code).find()) {
                    score++;
                }
            }

            if (score > 0) {
                scores.put(language, score);
            }
        }

        // إرجاع اللغة بأعلى درجة
        if (scores.isEmpty()) {
            Log.d(TAG, "لم يتم التعرف على لغة محددة، سيتم افتراض Java");
            return "Java";
        }

        String detectedLanguage = scores.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Java");

        Log.d(TAG, "تم كشف اللغة: " + detectedLanguage + " بدرجة: " + scores.get(detectedLanguage));
        return detectedLanguage;
    }

    /**
     * ترجمة الكود إلى Java
     */
    public static String translateToJavaAdvanced(String code, String language) {
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
            case "Rust":
                return translateFromRustAdvanced(code);
            case "Java":
                return code;
            default:
                Log.w(TAG, "لغة غير معروفة: " + language);
                return code;
        }
    }

    /**
     * ترجمة متقدمة من Python
     */
    private static String translateFromPythonAdvanced(String code) {
        String translated = code;

        // ترجمة الدوال
        translated = translated.replaceAll("def\\s+(\\w+)\\s*\\(([^)]*)\\)\\s*:", 
                "public static void $1($2) {");

        // ترجمة الطباعة
        translated = translated.replace("print(", "System.out.println(");

        // ترجمة المتغيرات
        translated = translated.replaceAll("^\\s*(\\w+)\\s*=\\s*(['\"])(.+?)\\2",
                "String $1 = \"$3\";");

        // ترجمة الحلقات
        translated = translated.replaceAll("for\\s+(\\w+)\\s+in\\s+range\\s*\\(([^)]*)\\)\\s*:",
                "for (int $1 = 0; $1 < $2; $1++) {");

        // ترجمة الشروط
        translated = translated.replaceAll("if\\s+(.+?)\\s*:", "if ($1) {");
        translated = translated.replaceAll("elif\\s+(.+?)\\s*:", "} else if ($1) {");
        translated = translated.replaceAll("else\\s*:", "} else {");

        // إغلاق الأقواس
        translated = addClosingBraces(translated);

        Log.d(TAG, "تم ترجمة كود Python بنجاح");
        return translated;
    }

    /**
     * ترجمة متقدمة من JavaScript
     */
    private static String translateFromJavaScriptAdvanced(String code) {
        String translated = code;

        // ترجمة الدوال
        translated = translated.replaceAll("function\\s+(\\w+)\\s*\\(([^)]*)\\)\\s*\\{",
                "public static void $1($2) {");

        // ترجمة الدوال السهمية
        translated = translated.replaceAll("\\(([^)]*)\\)\\s*=>\\s*\\{",
                "($1) -> {");

        // ترجمة console.log
        translated = translated.replace("console.log(", "System.out.println(");

        // ترجمة المتغيرات
        translated = translated.replaceAll("const\\s+(\\w+)\\s*=",
                "final String $1 =");
        translated = translated.replaceAll("let\\s+(\\w+)\\s*=",
                "String $1 =");
        translated = translated.replaceAll("var\\s+(\\w+)\\s*=",
                "String $1 =");

        // ترجمة الشروط
        translated = translated.replaceAll("if\\s*\\((.+?)\\)\\s*\\{",
                "if ($1) {");

        Log.d(TAG, "تم ترجمة كود JavaScript بنجاح");
        return translated;
    }

    /**
     * ترجمة متقدمة من Kotlin
     */
    private static String translateFromKotlinAdvanced(String code) {
        String translated = code;

        // ترجمة الدوال
        translated = translated.replaceAll("fun\\s+(\\w+)\\s*\\(([^)]*)\\)\\s*:\\s*Unit\\s*\\{",
                "public static void $1($2) {");
        translated = translated.replaceAll("fun\\s+(\\w+)\\s*\\(([^)]*)\\)\\s*\\{",
                "public static void $1($2) {");

        // ترجمة println
        translated = translated.replace("println(", "System.out.println(");

        // ترجمة المتغيرات
        translated = translated.replaceAll("val\\s+(\\w+)\\s*:\\s*String\\s*=",
                "final String $1 =");
        translated = translated.replaceAll("var\\s+(\\w+)\\s*=",
                "String $1 =");

        Log.d(TAG, "تم ترجمة كود Kotlin بنجاح");
        return translated;
    }

    /**
     * ترجمة متقدمة من Dart
     */
    private static String translateFromDartAdvanced(String code) {
        String translated = code;

        // ترجمة الدالة الرئيسية
        translated = translated.replaceAll("void\\s+main\\s*\\(\\s*\\)\\s*\\{",
                "public static void main(String[] args) {");

        // ترجمة print
        translated = translated.replace("print(", "System.out.println(");

        // ترجمة الفئات
        translated = translated.replaceAll("class\\s+(\\w+)\\s*\\{",
                "public class $1 {");

        // ترجمة final
        translated = translated.replaceAll("final\\s+(\\w+)\\s+(\\w+)\\s*=",
                "final String $2 =");

        Log.d(TAG, "تم ترجمة كود Dart بنجاح");
        return translated;
    }

    /**
     * ترجمة متقدمة من Rust
     */
    private static String translateFromRustAdvanced(String code) {
        String translated = code;

        // ترجمة الدوال
        translated = translated.replaceAll("fn\\s+(\\w+)\\s*\\(([^)]*)\\)\\s*\\{",
                "public static void $1($2) {");

        // ترجمة println!
        translated = translated.replace("println!(", "System.out.println(");

        // ترجمة let
        translated = translated.replaceAll("let\\s+mut\\s+(\\w+)\\s*=",
                "String $1 =");
        translated = translated.replaceAll("let\\s+(\\w+)\\s*=",
                "final String $1 =");

        Log.d(TAG, "تم ترجمة كود Rust بنجاح");
        return translated;
    }

    /**
     * إضافة أقواس الإغلاق تلقائياً
     */
    private static String addClosingBraces(String code) {
        int openBraces = 0;
        for (char c : code.toCharArray()) {
            if (c == '{') openBraces++;
            if (c == '}') openBraces--;
        }

        StringBuilder result = new StringBuilder(code);
        for (int i = 0; i < openBraces; i++) {
            result.append("\n}");
        }

        return result.toString();
    }

    /**
     * التحقق من صحة الكود المترجم
     */
    public static boolean validateTranslatedCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            return false;
        }

        // التحقق من تطابق الأقواس
        int braces = 0;
        for (char c : code.toCharArray()) {
            if (c == '{') braces++;
            if (c == '}') braces--;
            if (braces < 0) return false;
        }

        return braces == 0;
    }

    /**
     * الحصول على معلومات الترجمة
     */
    public static String getTranslationInfo(String originalCode, String translatedCode, String language) {
        int originalLines = originalCode.split("\n").length;
        int translatedLines = translatedCode.split("\n").length;
        int originalChars = originalCode.length();
        int translatedChars = translatedCode.length();

        return "معلومات الترجمة:\n" +
                "اللغة الأصلية: " + language + "\n" +
                "عدد الأسطر الأصلية: " + originalLines + "\n" +
                "عدد الأسطر المترجمة: " + translatedLines + "\n" +
                "عدد الأحرف الأصلية: " + originalChars + "\n" +
                "عدد الأحرف المترجمة: " + translatedChars;
    }
}
