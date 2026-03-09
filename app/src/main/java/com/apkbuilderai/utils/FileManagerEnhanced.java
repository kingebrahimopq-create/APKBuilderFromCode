package com.apkbuilderai.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * FileManagerEnhanced - إدارة متقدمة للملفات والمشاريع
 * يوفر وظائف شاملة لإنشاء هيكل المشروع الكامل
 */
public class FileManagerEnhanced {
    private static final String TAG = "FileManagerEnhanced";
    private Context context;
    private File projectBaseDir;

    public FileManagerEnhanced(Context context) {
        this.context = context;
        this.projectBaseDir = new File(Environment.getExternalStorageDirectory(), "APKBuilder/Projects");
    }

    /**
     * إنشاء هيكل مشروع كامل
     */
    public File createCompleteProjectStructure(String packageName, String appName, String language) throws IOException {
        Log.d(TAG, "بدء إنشاء هيكل المشروع: " + appName);

        // إنشاء مجلد المشروع الرئيسي
        String projectName = "App_" + System.currentTimeMillis();
        File projectDir = new File(projectBaseDir, projectName);
        projectDir.mkdirs();

        // إنشاء المجلدات الأساسية
        createDirectoryStructure(projectDir, packageName);

        // إنشاء ملفات Gradle
        createGradleFiles(projectDir, packageName, appName);

        // إنشاء AndroidManifest.xml
        createAndroidManifest(projectDir, packageName, appName);

        // إنشاء ملفات الموارد
        createResourceFiles(projectDir, appName);

        // إنشاء ملفات الكود الأساسية
        createSourceFiles(projectDir, packageName, language);

        Log.d(TAG, "تم إنشاء هيكل المشروع بنجاح في: " + projectDir.getAbsolutePath());
        return projectDir;
    }

    /**
     * إنشاء هيكل المجلدات
     */
    private void createDirectoryStructure(File projectDir, String packageName) throws IOException {
        String[] directories = {
                "app/src/main/java/" + packageName.replace(".", "/"),
                "app/src/main/res/layout",
                "app/src/main/res/values",
                "app/src/main/res/drawable",
                "app/src/main/res/mipmap",
                "app/src/test/java/" + packageName.replace(".", "/"),
                "gradle/wrapper",
                ".github/workflows"
        };

        for (String dir : directories) {
            File newDir = new File(projectDir, dir);
            newDir.mkdirs();
            Log.d(TAG, "تم إنشاء المجلد: " + dir);
        }
    }

    /**
     * إنشاء ملفات Gradle
     */
    private void createGradleFiles(File projectDir, String packageName, String appName) throws IOException {
        // build.gradle (Root)
        String rootBuildGradle = "plugins {\n" +
                "    id 'com.android.application' apply false\n" +
                "}\n" +
                "\n" +
                "task clean(type: Delete) {\n" +
                "    delete rootProject.buildDir\n" +
                "}";
        writeFile(new File(projectDir, "build.gradle"), rootBuildGradle);

        // build.gradle (App)
        String appBuildGradle = "plugins {\n" +
                "    id 'com.android.application'\n" +
                "}\n" +
                "\n" +
                "android {\n" +
                "    namespace \"" + packageName + "\"\n" +
                "    compileSdk 34\n" +
                "    \n" +
                "    defaultConfig {\n" +
                "        applicationId \"" + packageName + "\"\n" +
                "        minSdk 21\n" +
                "        targetSdk 34\n" +
                "        versionCode 1\n" +
                "        versionName \"1.0\"\n" +
                "    }\n" +
                "    \n" +
                "    buildTypes {\n" +
                "        release {\n" +
                "            minifyEnabled false\n" +
                "        }\n" +
                "    }\n" +
                "    \n" +
                "    compileOptions {\n" +
                "        sourceCompatibility JavaVersion.VERSION_17\n" +
                "        targetCompatibility JavaVersion.VERSION_17\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "dependencies {\n" +
                "    implementation 'androidx.appcompat:appcompat:1.6.1'\n" +
                "    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'\n" +
                "    implementation 'com.google.android.material:material:1.10.0'\n" +
                "    implementation 'org.apache.commons:commons-lang3:3.12.0'\n" +
                "}";
        writeFile(new File(projectDir, "app/build.gradle"), appBuildGradle);

        // settings.gradle
        String settingsGradle = "pluginManagement {\n" +
                "    repositories {\n" +
                "        google()\n" +
                "        mavenCentral()\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "dependencyResolutionManagement {\n" +
                "    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)\n" +
                "    repositories {\n" +
                "        google()\n" +
                "        mavenCentral()\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "rootProject.name = \"" + appName + "\"\n" +
                "include ':app'";
        writeFile(new File(projectDir, "settings.gradle"), settingsGradle);

        // gradle.properties
        String gradleProperties = "org.gradle.jvmargs=-Xmx2048m -XX:MaxPermSize=512m\n" +
                "org.gradle.parallel=true\n" +
                "android.useAndroidX=true\n" +
                "android.enableJetifier=true";
        writeFile(new File(projectDir, "gradle.properties"), gradleProperties);

        Log.d(TAG, "تم إنشاء ملفات Gradle");
    }

    /**
     * إنشاء AndroidManifest.xml
     */
    private void createAndroidManifest(File projectDir, String packageName, String appName) throws IOException {
        String manifest = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<manifest xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
                "    package=\"" + packageName + "\">\n" +
                "\n" +
                "    <uses-permission android:name=\"android.permission.INTERNET\" />\n" +
                "    <uses-permission android:name=\"android.permission.WRITE_EXTERNAL_STORAGE\" />\n" +
                "    <uses-permission android:name=\"android.permission.READ_EXTERNAL_STORAGE\" />\n" +
                "\n" +
                "    <application\n" +
                "        android:allowBackup=\"true\"\n" +
                "        android:label=\"@string/app_name\"\n" +
                "        android:theme=\"@style/Theme.AppCompat.Light\">\n" +
                "\n" +
                "        <activity\n" +
                "            android:name=\"." + extractMainActivityName(packageName) + "\"\n" +
                "            android:exported=\"true\">\n" +
                "            <intent-filter>\n" +
                "                <action android:name=\"android.intent.action.MAIN\" />\n" +
                "                <category android:name=\"android.intent.category.LAUNCHER\" />\n" +
                "            </intent-filter>\n" +
                "        </activity>\n" +
                "\n" +
                "    </application>\n" +
                "\n" +
                "</manifest>";

        writeFile(new File(projectDir, "app/src/main/AndroidManifest.xml"), manifest);
        Log.d(TAG, "تم إنشاء AndroidManifest.xml");
    }

    /**
     * إنشاء ملفات الموارد (Resources)
     */
    private void createResourceFiles(File projectDir, String appName) throws IOException {
        // strings.xml
        String stringsXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<resources>\n" +
                "    <string name=\"app_name\">" + appName + "</string>\n" +
                "    <string name=\"hello_world\">مرحبا بك في التطبيق!</string>\n" +
                "</resources>";
        writeFile(new File(projectDir, "app/src/main/res/values/strings.xml"), stringsXml);

        // colors.xml
        String colorsXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<resources>\n" +
                "    <color name=\"primary\">#2196F3</color>\n" +
                "    <color name=\"primary_dark\">#1976D2</color>\n" +
                "    <color name=\"accent\">#FF4081</color>\n" +
                "    <color name=\"background\">#FFFFFF</color>\n" +
                "    <color name=\"text_primary\">#212121</color>\n" +
                "    <color name=\"text_secondary\">#757575</color>\n" +
                "</resources>";
        writeFile(new File(projectDir, "app/src/main/res/values/colors.xml"), colorsXml);

        // styles.xml
        String stylesXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<resources>\n" +
                "    <style name=\"AppTheme\" parent=\"Theme.AppCompat.Light.DarkActionBar\">\n" +
                "        <item name=\"colorPrimary\">@color/primary</item>\n" +
                "        <item name=\"colorPrimaryDark\">@color/primary_dark</item>\n" +
                "        <item name=\"colorAccent\">@color/accent</item>\n" +
                "    </style>\n" +
                "</resources>";
        writeFile(new File(projectDir, "app/src/main/res/values/styles.xml"), stylesXml);

        // activity_main.xml
        String activityMainXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<LinearLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
                "    android:layout_width=\"match_parent\"\n" +
                "    android:layout_height=\"match_parent\"\n" +
                "    android:orientation=\"vertical\"\n" +
                "    android:padding=\"16dp\">\n" +
                "\n" +
                "    <TextView\n" +
                "        android:layout_width=\"match_parent\"\n" +
                "        android:layout_height=\"wrap_content\"\n" +
                "        android:text=\"@string/hello_world\"\n" +
                "        android:textSize=\"18sp\"\n" +
                "        android:textColor=\"@color/text_primary\" />\n" +
                "\n" +
                "</LinearLayout>";
        writeFile(new File(projectDir, "app/src/main/res/layout/activity_main.xml"), activityMainXml);

        Log.d(TAG, "تم إنشاء ملفات الموارد");
    }

    /**
     * إنشاء ملفات الكود الأساسية
     */
    private void createSourceFiles(File projectDir, String packageName, String language) throws IOException {
        String packagePath = packageName.replace(".", "/");
        
        // MainActivity.java
        String mainActivity = "package " + packageName + ";\n" +
                "\n" +
                "import android.os.Bundle;\n" +
                "import androidx.appcompat.app.AppCompatActivity;\n" +
                "\n" +
                "public class MainActivity extends AppCompatActivity {\n" +
                "\n" +
                "    @Override\n" +
                "    protected void onCreate(Bundle savedInstanceState) {\n" +
                "        super.onCreate(savedInstanceState);\n" +
                "        setContentView(R.layout.activity_main);\n" +
                "    }\n" +
                "}\n";
        
        writeFile(new File(projectDir, "app/src/main/java/" + packagePath + "/MainActivity.java"), mainActivity);
        Log.d(TAG, "تم إنشاء MainActivity.java");
    }

    /**
     * كتابة محتوى في ملف
     */
    private void writeFile(File file, String content) throws IOException {
        file.getParentFile().mkdirs();
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
        }
    }

    /**
     * استخراج اسم النشاط الرئيسي من اسم الحزمة
     */
    private String extractMainActivityName(String packageName) {
        String[] parts = packageName.split("\\.");
        return parts.length > 0 ? parts[parts.length - 1] : "MainActivity";
    }

    /**
     * حذف مشروع
     */
    public boolean deleteProject(File projectDir) {
        if (projectDir == null || !projectDir.exists()) {
            return false;
        }
        return deleteRecursive(projectDir);
    }

    /**
     * حذف ملف أو مجلد بشكل متكرر
     */
    private boolean deleteRecursive(File file) {
        if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children != null) {
                for (File child : children) {
                    if (!deleteRecursive(child)) {
                        return false;
                    }
                }
            }
        }
        return file.delete();
    }

    /**
     * الحصول على قائمة المشاريع
     */
    public File[] getProjectsList() {
        if (!projectBaseDir.exists()) {
            projectBaseDir.mkdirs();
        }
        return projectBaseDir.listFiles(File::isDirectory);
    }

    /**
     * الحصول على حجم المشروع
     */
    public long getProjectSize(File projectDir) {
        if (projectDir == null || !projectDir.exists()) {
            return 0;
        }
        return calculateSize(projectDir);
    }

    /**
     * حساب حجم المجلد
     */
    private long calculateSize(File file) {
        long size = 0;
        if (file.isFile()) {
            size = file.length();
        } else if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children != null) {
                for (File child : children) {
                    size += calculateSize(child);
                }
            }
        }
        return size;
    }

    /**
     * الحصول على معلومات المشروع
     */
    public String getProjectInfo(File projectDir) {
        if (projectDir == null || !projectDir.exists()) {
            return "المشروع غير موجود";
        }

        long size = getProjectSize(projectDir);
        long lastModified = projectDir.lastModified();
        Date date = new Date(lastModified);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        return "اسم المشروع: " + projectDir.getName() + "\n" +
                "الحجم: " + formatSize(size) + "\n" +
                "آخر تعديل: " + format.format(date);
    }

    /**
     * تنسيق حجم الملف
     */
    private String formatSize(long size) {
        if (size <= 0) return "0 B";
        final String[] units = new String[]{"B", "KB", "MB", "GB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return String.format("%.2f %s", size / Math.pow(1024, digitGroups), units[digitGroups]);
    }
}
