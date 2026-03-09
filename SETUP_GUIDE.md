# دليل إعداد مشروع APKBuilderFromCode

## نظرة عامة
مشروع تطبيق أندرويد يسمح ببناء ملفات APK من الأكواد مباشرة على الهاتف.

## الملفات المضافة

### 1. ملفات Gradle Wrapper
- `gradlew` - سكريبت تشغيل Gradle على Linux/Mac
- `gradlew.bat` - سكريبت تشغيل Gradle على Windows
- `gradle/wrapper/gradle-wrapper.jar` - ملف Gradle Wrapper
- `gradle/wrapper/gradle-wrapper.properties` - إعدادات Gradle Wrapper

### 2. ملفات الإعدادات
- `settings.gradle` - إعدادات المشروع الرئيسية
- `build.gradle` - ملف البناء الجذري
- `app/build.gradle` - ملف بناء وحدة التطبيق
- `local.properties` - الإعدادات المحلية

### 3. ملفات GitHub Actions
- `.github/workflows/main.yml` - سير العمل التلقائي لبناء APK

### 4. ملفات الموارد (Resources)
- `app/src/main/res/values/strings.xml` - النصوص والرسائل
- `app/src/main/res/values/colors.xml` - تعريف الألوان
- `app/src/main/res/values/styles.xml` - تعريف الأنماط
- `app/src/main/res/layout/activity_main.xml` - تخطيط الشاشة الرئيسية

### 5. ملفات Java
- `app/src/main/java/com/apkbuilderai/ui/MainActivity.java` - النشاط الرئيسي
- `app/src/main/java/com/apkbuilderai/R.java` - تعريف الموارد
- `app/src/main/AndroidManifest.xml` - بيان التطبيق

## المتطلبات

- **Android SDK**: الإصدار 34 أو أحدث
- **Java**: الإصدار 11 أو أحدث
- **Gradle**: الإصدار 8.1.1 (يتم تحميله تلقائياً عبر Gradle Wrapper)

## خطوات البناء المحلي

### على Linux/Mac:
```bash
cd APKBuilderFromCode
./gradlew assembleDebug
```

### على Windows:
```bash
cd APKBuilderFromCode
gradlew.bat assembleDebug
```

## البناء التلقائي عبر GitHub Actions

عند دفع التغييرات إلى فرع `main`، سيتم بناء APK تلقائياً عبر GitHub Actions.

### خطوات GitHub Actions:
1. استنساخ المستودع
2. إعداد JDK 17
3. منح صلاحيات التنفيذ لـ gradlew
4. بناء التطبيق باستخدام Gradle
5. تحميل ملف APK كـ artifact

## الملف الناتج

بعد البناء الناجح، سيكون ملف APK متاحاً في:
```
app/build/outputs/apk/debug/app-debug.apk
```

## التثبيت على الهاتف

### الطريقة 1: عبر ADB
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

### الطريقة 2: نقل الملف يدوياً
1. انسخ ملف APK إلى الهاتف
2. افتح مدير الملفات على الهاتف
3. ابحث عن الملف وانقر عليه لتثبيته

## ميزات التطبيق

- ✅ إدخال الأكواد مباشرة
- ✅ بناء APK من الأكواد
- ✅ دعم اللغة العربية
- ✅ واجهة مستخدم بسيطة وسهلة الاستخدام
- ✅ رسائل حالة واضحة

## الملفات الأساسية للتطبيق

| الملف | الوصف |
|------|-------|
| `MainActivity.java` | النشاط الرئيسي للتطبيق |
| `activity_main.xml` | تخطيط الشاشة الرئيسية |
| `strings.xml` | النصوص والرسائل |
| `colors.xml` | الألوان المستخدمة |
| `styles.xml` | الأنماط والتنسيقات |
| `AndroidManifest.xml` | بيان التطبيق |

## استكشاف الأخطاء

### خطأ: `chmod: cannot access './gradlew': No such file or directory`
**الحل**: تأكد من وجود ملف `gradlew` في المجلد الرئيسي

### خطأ: `JAVA_HOME is not set`
**الحل**: قم بتعيين متغير البيئة `JAVA_HOME` إلى مسار تثبيت Java

### خطأ: `Build failed`
**الحل**: تحقق من السجلات وتأكد من أن جميع التبعيات مثبتة بشكل صحيح

## الدعم والمساعدة

للمزيد من المعلومات، راجع:
- [توثيق Android](https://developer.android.com/)
- [توثيق Gradle](https://gradle.org/guides/)
- [GitHub Actions Documentation](https://docs.github.com/en/actions)

---

**آخر تحديث**: 8 مارس 2026
**الإصدار**: 1.0
