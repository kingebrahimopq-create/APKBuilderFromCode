package com.apkbuilderai.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * CodeValidator: أداة التحقق من صحة الأكواد
 * توفر التحقق من صحة الأكواد قبل البناء
 */
public class CodeValidator {
    private static final String TAG = "CodeValidator";

    /**
     * التحقق من صحة الكود
     */
    public static ValidationResult validate(String code) {
        ValidationResult result = new ValidationResult();

        if (code == null || code.trim().isEmpty()) {
            result.addError("الكود فارغ");
            return result;
        }

        // التحقق من الطول
        if (code.length() < 10) {
            result.addError("الكود قصير جداً (الحد الأدنى 10 أحرف)");
        }

        if (code.length() > 1000000) {
            result.addError("الكود طويل جداً (الحد الأقصى 1 مليون حرف)");
        }

        // التحقق من الأقواس
        if (!validateBrackets(code)) {
            result.addError("الأقواس غير متطابقة");
        }

        // التحقق من الكلمات المفتاحية
        validateKeywords(code, result);

        // التحقق من الأمان
        validateSecurity(code, result);

        // التحقق من الصيغة
        validateFormat(code, result);

        result.setValid(result.getErrors().isEmpty());
        return result;
    }

    /**
     * التحقق من تطابق الأقواس
     */
    private static boolean validateBrackets(String code) {
        int braces = 0, brackets = 0, parentheses = 0;

        for (int i = 0; i < code.length(); i++) {
            char c = code.charAt(i);

            // تجاهل الأحرف داخل النصوص
            if (c == '"' || c == '\'') {
                i = skipString(code, i);
                continue;
            }

            switch (c) {
                case '{': braces++; break;
                case '}': braces--; break;
                case '[': brackets++; break;
                case ']': brackets--; break;
                case '(': parentheses++; break;
                case ')': parentheses--; break;
            }

            if (braces < 0 || brackets < 0 || parentheses < 0) {
                return false;
            }
        }

        return braces == 0 && brackets == 0 && parentheses == 0;
    }

    /**
     * التحقق من الكلمات المفتاحية
     */
    private static void validateKeywords(String code, ValidationResult result) {
        // التحقق من وجود كلمات مفتاحية صحيحة
        String[] validKeywords = {"def", "function", "fun", "class", "if", "else", 
                                 "for", "while", "try", "catch", "return", "public", 
                                 "private", "protected", "static", "void", "int", 
                                 "String", "boolean", "double", "float"};

        boolean hasValidKeyword = false;
        for (String keyword : validKeywords) {
            if (code.contains(keyword)) {
                hasValidKeyword = true;
                break;
            }
        }

        if (!hasValidKeyword) {
            result.addWarning("الكود لا يحتوي على كلمات مفتاحية معروفة");
        }

        // التحقق من الكلمات المشبوهة
        if (code.contains("eval(") || code.contains("exec(")) {
            result.addWarning("تحذير أمان: استخدام eval أو exec");
        }
    }

    /**
     * التحقق من الأمان
     */
    private static void validateSecurity(String code, ValidationResult result) {
        // التحقق من محاولات الوصول إلى الملفات
        if (code.contains("Runtime.getRuntime()") || code.contains("Process")) {
            result.addWarning("تحذير أمان: محاولة الوصول إلى موارد النظام");
        }

        // التحقق من محاولات الوصول إلى الشبكة
        if (code.contains("Socket") || code.contains("HttpClient")) {
            result.addWarning("تحذير أمان: محاولة الوصول إلى الشبكة");
        }

        // التحقق من محاولات الوصول إلى قاعدة البيانات
        if (code.contains("SQL") || code.contains("Database")) {
            result.addWarning("تحذير أمان: محاولة الوصول إلى قاعدة البيانات");
        }
    }

    /**
     * التحقق من الصيغة
     */
    private static void validateFormat(String code, ValidationResult result) {
        // التحقق من وجود أسطر فارغة كثيرة
        int emptyLines = 0;
        String[] lines = code.split("\n");
        for (String line : lines) {
            if (line.trim().isEmpty()) {
                emptyLines++;
            }
        }

        if (emptyLines > lines.length / 2) {
            result.addWarning("الكود يحتوي على أسطر فارغة كثيرة");
        }

        // التحقق من الترجمات
        if (code.contains("print") && code.contains("System.out")) {
            result.addWarning("الكود يحتوي على خليط من اللغات");
        }
    }

    /**
     * تخطي النصوص
     */
    private static int skipString(String code, int startIndex) {
        char quote = code.charAt(startIndex);
        int i = startIndex + 1;
        while (i < code.length()) {
            if (code.charAt(i) == quote && code.charAt(i - 1) != '\\') {
                return i;
            }
            i++;
        }
        return code.length() - 1;
    }

    /**
     * فئة نتائج التحقق
     */
    public static class ValidationResult {
        private boolean valid;
        private List<String> errors;
        private List<String> warnings;

        public ValidationResult() {
            this.errors = new ArrayList<>();
            this.warnings = new ArrayList<>();
            this.valid = true;
        }

        public boolean isValid() { return valid; }
        public void setValid(boolean valid) { this.valid = valid; }

        public List<String> getErrors() { return errors; }
        public void addError(String error) { 
            this.errors.add(error);
            Log.e(TAG, "خطأ: " + error);
        }

        public List<String> getWarnings() { return warnings; }
        public void addWarning(String warning) { 
            this.warnings.add(warning);
            Log.w(TAG, "تحذير: " + warning);
        }

        public String getErrorMessage() {
            if (errors.isEmpty()) return "";
            StringBuilder sb = new StringBuilder();
            for (String error : errors) {
                sb.append("• ").append(error).append("\n");
            }
            return sb.toString();
        }

        public String getWarningMessage() {
            if (warnings.isEmpty()) return "";
            StringBuilder sb = new StringBuilder();
            for (String warning : warnings) {
                sb.append("⚠ ").append(warning).append("\n");
            }
            return sb.toString();
        }
    }
}
