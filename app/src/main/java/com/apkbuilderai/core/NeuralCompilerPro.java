package com.apkbuilderai.core;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * NeuralCompilerPro - محرك تحليل متقدم للكود
 * يوفر تحليلاً شاملاً وكشفاً ذكياً للأخطاء والتحذيرات
 */
public class NeuralCompilerPro {
    private static final String TAG = "NeuralCompilerPro";
    private Context context;
    private List<CompileError> errors;
    private List<CompileWarning> warnings;
    private Map<String, Integer> codeMetrics;

    public NeuralCompilerPro(Context context) {
        this.context = context;
        this.errors = new ArrayList<>();
        this.warnings = new ArrayList<>();
        this.codeMetrics = new HashMap<>();
    }

    /**
     * تحليل شامل للكود
     */
    public CompileResult analyzeCodeAdvanced(String code) {
        Log.d(TAG, "بدء التحليل الشامل للكود");

        errors.clear();
        warnings.clear();
        codeMetrics.clear();

        if (code == null || code.trim().isEmpty()) {
            errors.add(new CompileError("الكود المدخل فارغ", 0));
            return buildResult();
        }

        // تحليل متعدد المستويات
        analyzeStructure(code);
        analyzeSyntax(code);
        analyzeNaming(code);
        analyzePerformance(code);
        calculateMetrics(code);

        Log.d(TAG, "انتهى التحليل - الأخطاء: " + errors.size() + "، التحذيرات: " + warnings.size());
        return buildResult();
    }

    /**
     * تحليل هيكل الكود
     */
    private void analyzeStructure(String code) {
        Log.d(TAG, "تحليل هيكل الكود");

        // التحقق من الفئات
        Pattern classPattern = Pattern.compile("\\bclass\\s+(\\w+)");
        Matcher classMatcher = classPattern.matcher(code);
        int classCount = 0;
        while (classMatcher.find()) {
            classCount++;
        }

        if (classCount == 0) {
            warnings.add(new CompileWarning("لم يتم العثور على أي فئات في الكود", 0));
        }

        // التحقق من الدوال
        Pattern methodPattern = Pattern.compile("\\b(public|private|protected)?\\s*(static)?\\s*\\w+\\s+(\\w+)\\s*\\(");
        Matcher methodMatcher = methodPattern.matcher(code);
        int methodCount = 0;
        while (methodMatcher.find()) {
            methodCount++;
        }

        codeMetrics.put("classes", classCount);
        codeMetrics.put("methods", methodCount);
    }

    /**
     * تحليل الصيغة النحوية
     */
    private void analyzeSyntax(String code) {
        Log.d(TAG, "تحليل الصيغة النحوية");

        // التحقق من الأقواس
        checkBrackets(code);

        // التحقق من الفواصل المنقوطة
        checkSemicolons(code);

        // التحقق من علامات الاقتباس
        checkQuotes(code);

        // التحقق من التعليقات
        checkComments(code);
    }

    /**
     * تحليل أسماء المتغيرات والدوال
     */
    private void analyzeNaming(String code) {
        Log.d(TAG, "تحليل أسماء المتغيرات والدوال");

        // البحث عن متغيرات بأسماء سيئة
        Pattern badNamePattern = Pattern.compile("\\b([a-z]|[a-z]{1,2})\\s*=");
        Matcher badNameMatcher = badNamePattern.matcher(code);
        int badNameCount = 0;
        while (badNameMatcher.find()) {
            badNameCount++;
        }

        if (badNameCount > 0) {
            warnings.add(new CompileWarning(
                    "تم العثور على " + badNameCount + " متغيرات بأسماء غير واضحة",
                    0
            ));
        }

        // التحقق من اتباع camelCase
        Pattern camelCasePattern = Pattern.compile("\\b([a-z]+_[a-z]+)\\b");
        Matcher camelCaseMatcher = camelCasePattern.matcher(code);
        int snakeCaseCount = 0;
        while (camelCaseMatcher.find()) {
            snakeCaseCount++;
        }

        if (snakeCaseCount > 0) {
            warnings.add(new CompileWarning(
                    "استخدام snake_case بدلاً من camelCase في " + snakeCaseCount + " موضع",
                    0
            ));
        }
    }

    /**
     * تحليل الأداء المحتمل
     */
    private void analyzePerformance(String code) {
        Log.d(TAG, "تحليل الأداء");

        // البحث عن حلقات متداخلة
        int nestedLoopCount = countNestedLoops(code);
        if (nestedLoopCount > 2) {
            warnings.add(new CompileWarning(
                    "تم العثور على " + nestedLoopCount + " حلقات متداخلة - قد تؤثر على الأداء",
                    0
            ));
        }

        // البحث عن عمليات متكررة
        if (code.contains("new ") && countOccurrences(code, "new ") > 10) {
            warnings.add(new CompileWarning(
                    "عدد كبير من عمليات الإنشاء الجديدة - قد تؤثر على الذاكرة",
                    0
            ));
        }
    }

    /**
     * حساب مقاييس الكود
     */
    private void calculateMetrics(String code) {
        Log.d(TAG, "حساب مقاييس الكود");

        // عدد الأسطر
        int lineCount = code.split("\n").length;
        codeMetrics.put("lines", lineCount);

        // عدد الأحرف
        int charCount = code.length();
        codeMetrics.put("characters", charCount);

        // عدد الكلمات
        int wordCount = code.split("\\s+").length;
        codeMetrics.put("words", wordCount);

        // عدد التعليقات
        int commentCount = countOccurrences(code, "//") + countOccurrences(code, "/*");
        codeMetrics.put("comments", commentCount);

        // التعقيد الدوري
        int cyclomaticComplexity = calculateCyclomaticComplexity(code);
        codeMetrics.put("complexity", cyclomaticComplexity);

        if (cyclomaticComplexity > 10) {
            warnings.add(new CompileWarning(
                    "التعقيد الدوري مرتفع (" + cyclomaticComplexity + ") - يفضل تقسيم الدالة",
                    0
            ));
        }
    }

    /**
     * التحقق من الأقواس
     */
    private void checkBrackets(String code) {
        int braces = 0, brackets = 0, parentheses = 0;
        int line = 1;

        for (int i = 0; i < code.length(); i++) {
            char c = code.charAt(i);

            if (c == '\n') line++;

            // تجاهل الأحرف داخل النصوص
            if (c == '"' || c == '\'') {
                char quote = c;
                i++;
                while (i < code.length() && code.charAt(i) != quote) {
                    if (code.charAt(i) == '\\') i++;
                    i++;
                }
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
                errors.add(new CompileError("أقواس غير متطابقة في السطر " + line, line));
                return;
            }
        }

        if (braces != 0) {
            errors.add(new CompileError("عدم توازن الأقواس المعقوفة {}", code.split("\n").length));
        }
        if (brackets != 0) {
            errors.add(new CompileError("عدم توازن الأقواس المربعة []", code.split("\n").length));
        }
        if (parentheses != 0) {
            errors.add(new CompileError("عدم توازن الأقواس العادية ()", code.split("\n").length));
        }
    }

    /**
     * التحقق من الفواصل المنقوطة
     */
    private void checkSemicolons(String code) {
        String[] lines = code.split("\n");
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.isEmpty() || line.startsWith("//")) continue;

            // التحقق من الأسطر التي تحتاج على فاصلة منقوطة
            if ((line.contains("=") || line.contains("return")) && !line.endsWith(";") && !line.endsWith("{")) {
                warnings.add(new CompileWarning("قد تكون هناك فاصلة منقوطة مفقودة في السطر " + (i + 1), i + 1));
            }
        }
    }

    /**
     * التحقق من علامات الاقتباس
     */
    private void checkQuotes(String code) {
        int singleQuotes = 0, doubleQuotes = 0;
        for (char c : code.toCharArray()) {
            if (c == '\'') singleQuotes++;
            if (c == '"') doubleQuotes++;
        }

        if (singleQuotes % 2 != 0) {
            errors.add(new CompileError("علامات اقتباس مفردة غير متطابقة", 0));
        }
        if (doubleQuotes % 2 != 0) {
            errors.add(new CompileError("علامات اقتباس مزدوجة غير متطابقة", 0));
        }
    }

    /**
     * التحقق من التعليقات
     */
    private void checkComments(String code) {
        // التحقق من التعليقات متعددة الأسطر غير المغلقة
        if (code.contains("/*") && !code.contains("*/")) {
            errors.add(new CompileError("تعليق متعدد الأسطر غير مغلق", 0));
        }
    }

    /**
     * عد الحلقات المتداخلة
     */
    private int countNestedLoops(String code) {
        int maxNesting = 0;
        int currentNesting = 0;

        for (char c : code.toCharArray()) {
            if (c == '{') {
                currentNesting++;
                maxNesting = Math.max(maxNesting, currentNesting);
            }
            if (c == '}') {
                currentNesting--;
            }
        }

        return maxNesting;
    }

    /**
     * حساب التعقيد الدوري
     */
    private int calculateCyclomaticComplexity(String code) {
        int complexity = 1;
        complexity += countOccurrences(code, "if ");
        complexity += countOccurrences(code, "else ");
        complexity += countOccurrences(code, "for ");
        complexity += countOccurrences(code, "while ");
        complexity += countOccurrences(code, "case ");
        complexity += countOccurrences(code, "catch ");
        return complexity;
    }

    /**
     * عد عدد مرات ظهور نص معين
     */
    private int countOccurrences(String text, String pattern) {
        int count = 0;
        int index = 0;
        while ((index = text.indexOf(pattern, index)) != -1) {
            count++;
            index += pattern.length();
        }
        return count;
    }

    /**
     * بناء نتيجة التحليل
     */
    private CompileResult buildResult() {
        return new CompileResult(
                errors,
                warnings,
                codeMetrics,
                errors.isEmpty()
        );
    }

    /**
     * فئة نتيجة التحليل
     */
    public static class CompileResult {
        public List<CompileError> errors;
        public List<CompileWarning> warnings;
        public Map<String, Integer> metrics;
        public boolean isValid;

        public CompileResult(List<CompileError> errors, List<CompileWarning> warnings,
                           Map<String, Integer> metrics, boolean isValid) {
            this.errors = errors;
            this.warnings = warnings;
            this.metrics = metrics;
            this.isValid = isValid;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("نتيجة التحليل:\n");
            sb.append("الحالة: ").append(isValid ? "صحيح ✓" : "خطأ ✗").append("\n");
            sb.append("الأخطاء: ").append(errors.size()).append("\n");
            sb.append("التحذيرات: ").append(warnings.size()).append("\n");
            sb.append("المقاييس:\n");
            for (Map.Entry<String, Integer> entry : metrics.entrySet()) {
                sb.append("  ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
            return sb.toString();
        }
    }

    /**
     * فئة خطأ التحليل
     */
    public static class CompileError {
        public String message;
        public int line;

        public CompileError(String message, int line) {
            this.message = message;
            this.line = line;
        }

        @Override
        public String toString() {
            return "خطأ (السطر " + line + "): " + message;
        }
    }

    /**
     * فئة تحذير التحليل
     */
    public static class CompileWarning {
        public String message;
        public int line;

        public CompileWarning(String message, int line) {
            this.message = message;
            this.line = line;
        }

        @Override
        public String toString() {
            return "تحذير (السطر " + line + "): " + message;
        }
    }
}
