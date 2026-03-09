# دليل التثبيت والإعداد - APKBuilder AI

## المتطلبات الأساسية

قبل البدء، تأكد من توفر المتطلبات التالية:

### 1. Java Development Kit (JDK)
- **الإصدار المطلوب:** Java 11 أو أحدث
- **التحميل:** https://www.oracle.com/java/technologies/downloads/
- **التحقق من التثبيت:**
  ```bash
  java -version
  javac -version
  ```

### 2. Android Studio
- **الإصدار المطلوب:** Android Studio 2021.1 أو أحدث
- **التحميل:** https://developer.android.com/studio
- **الحد الأدنى للمساحة:** 4 GB

### 3. Android SDK
- **الإصدار المطلوب:** API 34 (Android 14)
- **الحد الأدنى المدعوم:** API 21 (Android 5.0)
- **يتم التثبيت تلقائياً مع Android Studio**

### 4. Gradle
- **الإصدار المطلوب:** Gradle 8.0 أو أحدث
- **يتم التثبيت تلقائياً مع المشروع**

### 5. Git
- **التحميل:** https://git-scm.com/
- **التحقق من التثبيت:**
  ```bash
  git --version
  ```

## خطوات التثبيت

### الخطوة 1: استنساخ المستودع

```bash
git clone https://github.com/kingebrahimopq-create/APKBuilderFromCode.git
cd APKBuilderFromCode
```

### الخطوة 2: فتح المشروع في Android Studio

1. افتح Android Studio
2. اختر **File** → **Open**
3. اختر مجلد المشروع `APKBuilderFromCode`
4. انتظر حتى ينتهي Gradle من التحميل والمزامنة

### الخطوة 3: تحميل التبعيات

عند فتح المشروع لأول مرة، سيقوم Android Studio تلقائياً بـ:
- تحميل Gradle Wrapper
- تحميل جميع التبعيات المطلوبة
- مزامنة ملفات المشروع

**إذا لم يحدث ذلك تلقائياً:**
```bash
./gradlew build
```

### الخطوة 4: إعداد جهاز Android

#### الخيار الأول: استخدام جهاز فعلي

1. قم بتوصيل جهازك عبر USB
2. فعّل وضع المطور:
   - اذهب إلى **الإعدادات** → **حول الهاتف**
   - اضغط على **رقم الإصدار** 7 مرات
   - ارجع إلى **الإعدادات** → **خيارات المطور**
   - فعّل **تصحيح USB**

#### الخيار الثاني: استخدام محاكي Android

1. في Android Studio، اذهب إلى **Tools** → **AVD Manager**
2. اختر **Create Virtual Device**
3. اختر جهازاً (مثل Pixel 4)
4. اختر صورة النظام (Android 14 أو أحدث)
5. أكمل الإعداد وأنشئ المحاكي

### الخطوة 5: بناء المشروع

#### من سطر الأوامر:
```bash
./gradlew build
```

#### من Android Studio:
1. اذهب إلى **Build** → **Build Bundle(s) / APK(s)** → **Build APK(s)**
2. انتظر حتى ينتهي البناء
3. ستجد ملف APK في: `app/build/outputs/apk/debug/`

### الخطوة 6: تشغيل التطبيق

#### من سطر الأوامر:
```bash
./gradlew installDebug
./gradlew run
```

#### من Android Studio:
1. اختر جهازك من قائمة الأجهزة
2. اضغط على زر **Run** (أو اضغط Shift + F10)
3. انتظر حتى يتم تثبيت وتشغيل التطبيق

## التحقق من التثبيت

بعد تشغيل التطبيق، تحقق من:

1. **واجهة المستخدم تظهر بشكل صحيح**
2. **يمكنك إدخال الكود**
3. **زر البناء يعمل**
4. **الرسائل تظهر باللغة العربية**

## استكشاف الأخطاء

### المشكلة: "SDK location not found"

**الحل:**
```bash
./gradlew --version
```

إذا لم ينجح، قم بإنشاء ملف `local.properties`:
```properties
sdk.dir=/path/to/android/sdk
```

### المشكلة: "Gradle sync failed"

**الحل:**
1. اذهب إلى **File** → **Sync Now**
2. أو من سطر الأوامر:
   ```bash
   ./gradlew clean
   ./gradlew build
   ```

### المشكلة: "Build failed"

**الحل:**
1. تحقق من إصدار Java:
   ```bash
   java -version
   ```
   يجب أن يكون Java 11 أو أحدث

2. امسح ذاكرة التخزين المؤقتة:
   ```bash
   ./gradlew clean
   ```

3. أعد بناء المشروع:
   ```bash
   ./gradlew build
   ```

### المشكلة: "Device not found"

**الحل:**
1. تحقق من توصيل الجهاز:
   ```bash
   adb devices
   ```

2. إذا لم يظهر الجهاز، قم بـ:
   - إعادة تشغيل الجهاز
   - إعادة تشغيل ADB:
     ```bash
     adb kill-server
     adb start-server
     ```

## الإعدادات المتقدمة

### تغيير إصدار الهدف

في ملف `app/build.gradle`:
```gradle
android {
    compileSdk 34  // غيّر هنا
    
    defaultConfig {
        targetSdk 34  // وهنا
    }
}
```

### تغيير حجم الذاكرة

في ملف `gradle.properties`:
```properties
org.gradle.jvmargs=-Xmx2048m
```

### تفعيل ProGuard

في ملف `app/build.gradle`:
```gradle
buildTypes {
    release {
        minifyEnabled true
        proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
    }
}
```

## الخطوات التالية

بعد التثبيت الناجح:

1. **اقرأ التوثيق:** README_ENHANCED.md
2. **استكشف الأمثلة:** BUILD_LOG.md
3. **جرّب الميزات:** ابدأ بإدخال أكواد بسيطة
4. **ساهم في التطوير:** أرسل Pull Requests

## الدعم والمساعدة

إذا واجهت مشاكل:

1. **تحقق من الأسئلة الشائعة:** FAQ.md (قريباً)
2. **ابحث في Issues:** https://github.com/kingebrahimopq-create/APKBuilderFromCode/issues
3. **أنشئ Issue جديد** إذا لم تجد حلاً

## الموارد الإضافية

- [Android Developer Documentation](https://developer.android.com/docs)
- [Gradle Documentation](https://gradle.org/documentation/)
- [Java Documentation](https://docs.oracle.com/javase/)

---

**آخر تحديث:** مارس 2026
**الإصدار:** 2.0
