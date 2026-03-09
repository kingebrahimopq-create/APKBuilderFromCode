package com.apkbuilderai.core;

import android.content.Context;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class NeuralCompiler {
    private static final String TAG = "NeuralCompiler";
    private Context context;
    private List<String> errors;
    private List<String> warnings;

    public NeuralCompiler(Context ctx) {
        this.context = ctx;
        this.errors = new ArrayList<>();
        this.warnings = new ArrayList<>();
    }

    /**
     * تحليل الكود والتحقق من صحته
     * @param code الكود المراد تحليله
     */
    public void analyzeCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            Log.e(TAG, "الكود المدخل فارغ");
            errors.add("الكود المدخل فارغ");
            return;
        }

        Log.d(TAG, "بدء تحليل الكود");
        errors.clear();
        warnings.clear();

        // تحليل الكود
        String result = parseCode(code);
        
        // التحقق من الأقواس والأقواس المعقوفة
        checkBrackets(code);
        
        // التحقق من الكلمات المفتاحية
        checkKeywords(code);
        
        // التحقق من التعليقات
        checkComments(code);

        Log.d(TAG, "انتهى التحليل - النتيجة: " + result);
        Log.d(TAG, "عدد الأخطاء: " + errors.size() + "، عدد التحذيرات: " + warnings.size());
    }

    /**
     * تحليل الكود وإرجاع نتيجة التحليل
     * @param inputCode الكود المراد تحليله
     * @return نتيجة التحليل
     */
    public String parseCode(String inputCode) {
        if (inputCode == null || inputCode.trim().isEmpty()) {
            return "فارغ";
        }

        Log.d(TAG, "تحليل الكود: " + inputCode.substring(0, Math.min(50, inputCode.length())));

        StringBuilder analysis = new StringBuilder();
        analysis.append("تحليل الكود:\n");
        analysis.append("- عدد الأسطر: ").append(countLines(inputCode)).append("\n");
        analysis.append("- عدد الأحرف: ").append(inputCode.length()).append("\n");
        analysis.append("- عدد الكلمات: ").append(countWords(inputCode)).append("\n");

        return analysis.toString();
    }

    /**
     * التحقق من تطابق الأقواس والأقواس المعقوفة
     * @param code الكود المراد التحقق منه
     */
    private void checkBrackets(String code) {
        int braces = 0;
        int brackets = 0;
        int parentheses = 0;

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
                errors.add("أقواس غير متطابقة في السطر " + getLineNumber(code, i));
                return;
            }
        }

        if (braces != 0) {
            errors.add("أقواس معقوفة {} غير متطابقة");
        }
        if (brackets != 0) {
            errors.add("أقواس مربعة [] غير متطابقة");
        }
        if (parentheses != 0) {
            errors.add("أقواس دائرية () غير متطابقة");
        }
    }

    /**
     * التحقق من الكلمات المفتاحية والبنى الأساسية
     * @param code الكود المراد التحقق منه
     */
    private void checkKeywords(String code) {
        // التحقق من وجود دالة main
        if (!code.contains("main") && !code.contains("def ") && !code.contains("function")) {
            warnings.add("لم يتم العثور على دالة رئيسية (main)");
        }

        // التحقق من وجود كلمات مفتاحية أساسية
        if (code.contains("if") && !code.contains("else")) {
            warnings.add("يوجد شرط if بدون else");
        }

        // التحقق من الدوال المعرفة
        int functionCount = countOccurrences(code, "def ") + 
                           countOccurrences(code, "function ") +
                           countOccurrences(code, "fun ");
        
        if (functionCount > 0) {
            Log.d(TAG, "تم العثور على " + functionCount + " دالة");
        }
    }

    /**
     * التحقق من التعليقات في الكود
     * @param code الكود المراد التحقق منه
     */
    private void checkComments(String code) {
        int singleLineComments = countOccurrences(code, "//");
        int multiLineComments = countOccurrences(code, "/*");

        if (singleLineComments > 0 || multiLineComments > 0) {
            Log.d(TAG, "تم العثور على " + singleLineComments + " تعليق سطر واحد و" + 
                      multiLineComments + " تعليق متعدد الأسطر");
        } else {
            warnings.add("الكود لا يحتوي على تعليقات توضيحية");
        }
    }

    /**
     * الحصول على قائمة الأخطاء
     */
    public List<String> getErrors() {
        return errors;
    }

    /**
     * الحصول على قائمة التحذيرات
     */
    public List<String> getWarnings() {
        return warnings;
    }

    /**
     * التحقق من وجود أخطاء
     */
    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    // ============ طرق مساعدة ============

    private int countLines(String code) {
        return code.split("\n").length;
    }

    private int countWords(String code) {
        return code.split("\\s+").length;
    }

    private int countOccurrences(String code, String pattern) {
        int count = 0;
        int index = 0;
        while ((index = code.indexOf(pattern, index)) != -1) {
            count++;
            index += pattern.length();
        }
        return count;
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

    private int getLineNumber(String code, int charIndex) {
        int lineNumber = 1;
        for (int i = 0; i < charIndex && i < code.length(); i++) {
            if (code.charAt(i) == '\n') {
                lineNumber++;
            }
        }
        return lineNumber;
    }
}
