package com.apkbuilderai.utils;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * FileManager: مدير الملفات
 * يقوم بإدارة حفظ وتحميل الملفات
 */
public class FileManager {
    private static final String TAG = "FileManager";
    private static final String BUILD_DIR = "apk_builds";
    private static final String TEMP_DIR = "temp_build";

    private Context context;
    private File buildDirectory;
    private File tempDirectory;

    public FileManager(Context context) {
        this.context = context;
        this.buildDirectory = new File(context.getExternalFilesDir(null), BUILD_DIR);
        this.tempDirectory = new File(context.getCacheDir(), TEMP_DIR);

        // إنشاء المجلدات إذا لم تكن موجودة
        if (!buildDirectory.exists()) {
            buildDirectory.mkdirs();
            Log.d(TAG, "تم إنشاء مجلد البناء");
        }

        if (!tempDirectory.exists()) {
            tempDirectory.mkdirs();
            Log.d(TAG, "تم إنشاء المجلد المؤقت");
        }
    }

    /**
     * حفظ ملف DEX
     */
    public File saveDexFile(File sourceDexFile, String fileName) throws IOException {
        if (!sourceDexFile.exists()) {
            throw new IOException("ملف DEX المصدر غير موجود");
        }

        File destinationFile = new File(buildDirectory, fileName);

        try (FileInputStream fis = new FileInputStream(sourceDexFile);
             FileOutputStream fos = new FileOutputStream(destinationFile)) {

            byte[] buffer = new byte[1024];
            int length;

            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }

            Log.d(TAG, "تم حفظ ملف DEX: " + destinationFile.getAbsolutePath());
            return destinationFile;

        } catch (IOException e) {
            Log.e(TAG, "خطأ في حفظ ملف DEX: " + e.getMessage());
            throw e;
        }
    }

    /**
     * حفظ ملف الكود المصدر
     */
    public File saveSourceCode(String code, String fileName) throws IOException {
        File sourceFile = new File(buildDirectory, fileName);

        try (FileOutputStream fos = new FileOutputStream(sourceFile)) {
            fos.write(code.getBytes());
            fos.flush();

            Log.d(TAG, "تم حفظ ملف الكود: " + sourceFile.getAbsolutePath());
            return sourceFile;

        } catch (IOException e) {
            Log.e(TAG, "خطأ في حفظ ملف الكود: " + e.getMessage());
            throw e;
        }
    }

    /**
     * قراءة ملف
     */
    public String readFile(File file) throws IOException {
        if (!file.exists()) {
            throw new IOException("الملف غير موجود: " + file.getAbsolutePath());
        }

        try {
            byte[] encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
            return new String(encoded);

        } catch (IOException e) {
            Log.e(TAG, "خطأ في قراءة الملف: " + e.getMessage());
            throw e;
        }
    }

    /**
     * حذف ملف
     */
    public boolean deleteFile(File file) {
        if (file.exists()) {
            boolean deleted = file.delete();
            if (deleted) {
                Log.d(TAG, "تم حذف الملف: " + file.getAbsolutePath());
            } else {
                Log.w(TAG, "فشل في حذف الملف: " + file.getAbsolutePath());
            }
            return deleted;
        }
        return false;
    }

    /**
     * مسح المجلد المؤقت
     */
    public void clearTempDirectory() {
        if (tempDirectory.exists()) {
            File[] files = tempDirectory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        deleteFile(file);
                    }
                }
            }
            Log.d(TAG, "تم مسح المجلد المؤقت");
        }
    }

    /**
     * الحصول على حجم مجلد البناء
     */
    public long getBuildDirectorySize() {
        long size = 0;
        if (buildDirectory.exists()) {
            File[] files = buildDirectory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        size += file.length();
                    }
                }
            }
        }
        return size;
    }

    /**
     * الحصول على قائمة الملفات المحفوظة
     */
    public File[] getSavedFiles() {
        if (buildDirectory.exists()) {
            return buildDirectory.listFiles();
        }
        return new File[0];
    }

    /**
     * الحصول على مجلد البناء
     */
    public File getBuildDirectory() {
        return buildDirectory;
    }

    /**
     * الحصول على المجلد المؤقت
     */
    public File getTempDirectory() {
        return tempDirectory;
    }

    /**
     * حذف ملف قديم (بناءً على الوقت)
     */
    public void deleteOldFiles(long maxAgeMillis) {
        if (buildDirectory.exists()) {
            File[] files = buildDirectory.listFiles();
            if (files != null) {
                long currentTime = System.currentTimeMillis();
                for (File file : files) {
                    if (currentTime - file.lastModified() > maxAgeMillis) {
                        deleteFile(file);
                    }
                }
            }
        }
    }

    /**
     * حساب عدد الملفات المحفوظة
     */
    public int getSavedFileCount() {
        if (buildDirectory.exists()) {
            File[] files = buildDirectory.listFiles();
            return files != null ? files.length : 0;
        }
        return 0;
    }
}
