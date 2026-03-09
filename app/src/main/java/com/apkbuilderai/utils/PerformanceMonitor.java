package com.apkbuilderai.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug;
import android.util.Log;

/**
 * PerformanceMonitor: أداة مراقبة الأداء
 * توفر معلومات عن أداء التطبيق واستهلاك الموارد
 */
public class PerformanceMonitor {
    private static final String TAG = "PerformanceMonitor";

    private Context context;
    private long startTime;
    private long startMemory;

    public PerformanceMonitor(Context context) {
        this.context = context;
    }

    /**
     * بدء مراقبة الأداء
     */
    public void startMonitoring() {
        startTime = System.currentTimeMillis();
        startMemory = getUsedMemory();
        Log.d(TAG, "بدء مراقبة الأداء");
    }

    /**
     * إنهاء مراقبة الأداء
     */
    public PerformanceMetrics stopMonitoring() {
        long endTime = System.currentTimeMillis();
        long endMemory = getUsedMemory();

        long duration = endTime - startTime;
        long memoryUsed = endMemory - startMemory;

        PerformanceMetrics metrics = new PerformanceMetrics(
            duration,
            memoryUsed,
            getAvailableMemory(),
            getTotalMemory(),
            getCPUUsage()
        );

        Log.d(TAG, "انتهت مراقبة الأداء - المدة: " + duration + "ms، الذاكرة: " + memoryUsed + "KB");
        return metrics;
    }

    /**
     * الحصول على الذاكرة المستخدمة
     */
    private long getUsedMemory() {
        Runtime runtime = Runtime.getRuntime();
        return (runtime.totalMemory() - runtime.freeMemory()) / 1024;
    }

    /**
     * الحصول على الذاكرة المتاحة
     */
    private long getAvailableMemory() {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo.availMem / 1024;
    }

    /**
     * الحصول على إجمالي الذاكرة
     */
    private long getTotalMemory() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() / 1024;
    }

    /**
     * الحصول على استخدام CPU (تقريبي)
     */
    private double getCPUUsage() {
        try {
            int availableProcessors = Runtime.getRuntime().availableProcessors();
            long nativeHeapAllocatedSize = Debug.getNativeHeapAllocatedSize();
            return (double) nativeHeapAllocatedSize / (1024 * 1024);
        } catch (Exception e) {
            Log.e(TAG, "خطأ في حساب CPU: " + e.getMessage());
            return 0.0;
        }
    }

    /**
     * فئة مقاييس الأداء
     */
    public static class PerformanceMetrics {
        private long duration; // المدة بالميلي ثانية
        private long memoryUsed; // الذاكرة المستخدمة بـ KB
        private long availableMemory; // الذاكرة المتاحة بـ KB
        private long totalMemory; // إجمالي الذاكرة بـ KB
        private double cpuUsage; // استخدام CPU

        public PerformanceMetrics(long duration, long memoryUsed, long availableMemory, 
                                 long totalMemory, double cpuUsage) {
            this.duration = duration;
            this.memoryUsed = memoryUsed;
            this.availableMemory = availableMemory;
            this.totalMemory = totalMemory;
            this.cpuUsage = cpuUsage;
        }

        public long getDuration() { return duration; }
        public long getMemoryUsed() { return memoryUsed; }
        public long getAvailableMemory() { return availableMemory; }
        public long getTotalMemory() { return totalMemory; }
        public double getCPUUsage() { return cpuUsage; }

        /**
         * الحصول على ملخص المقاييس
         */
        public String getSummary() {
            return String.format(
                "مقاييس الأداء:\n" +
                "- المدة: %d ms\n" +
                "- الذاكرة المستخدمة: %d KB\n" +
                "- الذاكرة المتاحة: %d KB\n" +
                "- إجمالي الذاكرة: %d KB\n" +
                "- استخدام CPU: %.2f MB",
                duration, memoryUsed, availableMemory, totalMemory, cpuUsage
            );
        }

        /**
         * التحقق من الأداء الجيدة
         */
        public boolean isGoodPerformance() {
            return duration < 5000 && memoryUsed < 50000; // أقل من 5 ثوان و 50 MB
        }
    }
}
