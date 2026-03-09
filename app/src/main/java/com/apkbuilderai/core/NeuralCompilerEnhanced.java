package com.apkbuilderai.core;

import android.content.Context;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * NeuralCompilerEnhanced: نسخة محسّنة من محلل الأكواد
 * توفر تحليلاً أعمق وأكثر دقة للأكواد البرمجية
 */
public class NeuralCompilerEnhanced {
    private static final String TAG = "NeuralCompilerEnhanced";
    private Context context;
    private List<String> errors;
    private List<String> warnings;
    private Map<String, Integer> codeMetrics;
    private CodeAnalysisResult analysisResult;

    public NeuralCompilerEnhanced(Context ctx) {
        this.context = ctx;
        this.errors = new ArrayList<>();
        this.warnings = new ArrayList<>();
        this.codeMetrics = new HashMap<>();
        this.analysisResult = new CodeAnalysisResult();
    }

    /**
     * تحليل شامل للكود
     */
    public CodeAnalysisResult analyzeCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            Log.e(TAG, "الكود المدخل فارغ");
            errors.add("الكود المدخل فارغ");
            analysisResult.setValid(false);
            return analysisResult;
        }

        Log.d(TAG, "بدء التحليل الشامل للكود");
        errors.clear();
        warnings.clear();
        codeMetrics.clear();

        try {
            // 1. حساب المقاييس الأساسية
            calculateMetrics(code);

            // 2. التحقق من الأقواس والأقواس المعقوفة
            checkBracketsAdvanced(code);

            // 3. التحقق من الكلمات المفتاحية
            checkKeywordsAdvanced(code);

            // 4. التحقق من التعليقات
            checkComments(code);

            // 5. التحقق من الدوال والفئات
            checkFunctionsAndClasses(code);

            // 6. التحقق من الأمان
            checkSecurityIssues(code);

            // تعيين النتائج
            analysisResult.setErrors(new ArrayList<>(errors));
            analysisResult.setWarnings(new ArrayList<>(warnings));
            analysisResult.setMetrics(new HashMap<>(codeMetrics));
            analysisResult.setValid(errors.isEmpty());

            Log.d(TAG, "انتهى التحليل - أخطاء: " + errors.size() + "، تحذيرات: " + warnings.size());

        } catch (Exception e) {
            Log.e(TAG, "خطأ أثناء التحليل: " + e.getMessage());
            errors.add("خطأ في التحليل: " + e.getMessage());
            analysisResult.setValid(false);
        }

        return analysisResult;
    }

    /**
     * حساب مقاييس الكود
     */
    private void calculateMetrics(String code) {
        int lines = code.split("\n").length;
        int chars = code.length();
        int words = code.split("\\s+").length;
        int functions = countPattern(code, "def |function |fun |void ");
        int classes = countPattern(code, "class ");
        int comments = countPattern(code, "//|/\\*|\\*");

        codeMetrics.put("lines", lines);
        codeMetrics.put("chars", chars);
        codeMetrics.put("words", words);
        codeMetrics.put("functions", functions);
        codeMetrics.put("classes", classes);
        codeMetrics.put("comments", comments);

        Log.d(TAG, "المقاييس - أسطر: " + lines + "، أحرف: " + chars + "، كلمات: " + words);
    }

    /**
     * التحقق المتقدم من الأقواس
     */
    private void checkBracketsAdvanced(String code) {
        int braces = 0, brackets = 0, parentheses = 0;
        int lineNum = 1;

        for (int i = 0; i < code.length(); i++) {
            char c = code.charAt(i);

            // تجاهل الأحرف داخل النصوص والتعليقات
            if (c == '"' || c == '\'') {
                i = skipString(code, i);
                continue;
            }

            if (c == '\n') lineNum++;

            switch (c) {
                case '{': braces++; break;
                case '}': braces--; break;
                case '[': brackets++; break;
                case ']': brackets--; break;
                case '(': parentheses++; break;
                case ')': parentheses--; break;
            }

            if (braces < 0 || brackets < 0 || parentheses < 0) {
                errors.add("أقواس غير متطابقة في السطر " + lineNum);
                return;
            }
        }

        if (braces != 0) errors.add("أقواس معقوفة {} غير متطابقة (عدد: " + braces + ")");
        if (brackets != 0) errors.add("أقواس مربعة [] غير متطابقة (عدد: " + brackets + ")");
        if (parentheses != 0) errors.add("أقواس دائرية () غير متطابقة (عدد: " + parentheses + ")");
    }

    /**
     * التحقق المتقدم من الكلمات المفتاحية
     */
    private void checkKeywordsAdvanced(String code) {
        boolean hasMainFunction = code.contains("main") || code.contains("def main") || 
                                 code.contains("function main") || code.contains("fun main") ||
                                 code.contains("void main");
        
        if (!hasMainFunction) {
            warnings.add("لم يتم العثور على دالة رئيسية (main)");
        }

        // التحقق من الاستخدام الآمن للمتغيرات
        if (code.contains("var ") && !code.contains("final ")) {
            warnings.add("يُنصح باستخدام final للمتغيرات الثابتة");
        }

        // التحقق من الدوال المعرفة
        int functionCount = countPattern(code, "def |function |fun ");
        if (functionCount > 50) {
            warnings.add("عدد الدوال كبير جداً (" + functionCount + ")");
        }
    }

    /**
     * التحقق من التعليقات
     */
    private void checkComments(String code) {
        int singleLineComments = countPattern(code, "//");
        int multiLineComments = countPattern(code, "/\\*");

        if (singleLineComments == 0 && multiLineComments == 0) {
            warnings.add("الكود لا يحتوي على تعليقات توضيحية");
        } else {
            Log.d(TAG, "تعليقات - أسطر: " + singleLineComments + "، متعدد: " + multiLineComments);
        }
    }

    /**
     * التحقق من الدوال والفئات
     */
    private void checkFunctionsAndClasses(String code) {
        int classCount = countPattern(code, "class ");
        int functionCount = countPattern(code, "def |function |fun ");

        if (classCount > 0) {
            Log.d(TAG, "تم العثور على " + classCount + " فئة");
        }

        if (functionCount > 0) {
            Log.d(TAG, "تم العثور على " + functionCount + " دالة");
        }

        if (classCount == 0 && functionCount == 0) {
            warnings.add("لم يتم العثور على أي دوال أو فئات");
        }
    }

    /**
     * التحقق من مشاكل الأمان
     */
    private void checkSecurityIssues(String code) {
        // التحقق من الكلمات المشبوهة
        if (code.contains("eval(") || code.contains("exec(")) {
            warnings.add("تحذير أمان: استخدام eval أو exec قد يكون خطيراً");
        }

        if (code.contains("System.exit") || code.contains("Runtime.getRuntime")) {
            warnings.add("تحذير أمان: محاولة الوصول إلى موارد النظام");
        }
    }

    // ============ طرق مساعدة ============

    private int countPattern(String code, String pattern) {
        try {
            return code.split(pattern, -1).length - 1;
        } catch (Exception e) {
            return 0;
        }
    }

    private int skipString(String code, int startIndex) {
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

    public List<String> getErrors() {
        return errors;
    }

    public List<String> getWarnings() {
        return warnings;
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public CodeAnalysisResult getAnalysisResult() {
        return analysisResult;
    }

    /**
     * فئة لتخزين نتائج التحليل
     */
    public static class CodeAnalysisResult {
        private boolean valid;
        private List<String> errors;
        private List<String> warnings;
        private Map<String, Integer> metrics;

        public CodeAnalysisResult() {
            this.errors = new ArrayList<>();
            this.warnings = new ArrayList<>();
            this.metrics = new HashMap<>();
        }

        public boolean isValid() { return valid; }
        public void setValid(boolean valid) { this.valid = valid; }

        public List<String> getErrors() { return errors; }
        public void setErrors(List<String> errors) { this.errors = errors; }

        public List<String> getWarnings() { return warnings; }
        public void setWarnings(List<String> warnings) { this.warnings = warnings; }

        public Map<String, Integer> getMetrics() { return metrics; }
        public void setMetrics(Map<String, Integer> metrics) { this.metrics = metrics; }

        public int getLineCount() { return metrics.getOrDefault("lines", 0); }
        public int getCharCount() { return metrics.getOrDefault("chars", 0); }
        public int getWordCount() { return metrics.getOrDefault("words", 0); }
        public int getFunctionCount() { return metrics.getOrDefault("functions", 0); }
        public int getClassCount() { return metrics.getOrDefault("classes", 0); }
    }
}
