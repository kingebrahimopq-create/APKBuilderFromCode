# APKBuilder AI - محول الأكواد إلى APK

تطبيق Android متقدم وقوي يقوم بتحويل الأكواس البرمجية من لغات متعددة إلى ملفات APK قابلة للتنفيذ تلقائياً. يوفر التطبيق واجهة سهلة الاستخدام وميزات متقدمة لتحليل وترجمة وتشفير الأكواد.

## المميزات الرئيسية

### دعم لغات برمجية متعددة

يدعم التطبيق خمس لغات برمجية رئيسية مع كشف ذكي وترجمة آلية:

| اللغة | الحالة | الملاحظات |
|-------|--------|---------|
| Java | ✓ مدعومة | اللغة الأساسية |
| Python | ✓ مدعومة | ترجمة آلية إلى Java |
| JavaScript | ✓ مدعومة | ترجمة آلية إلى Java |
| Kotlin | ✓ مدعومة | ترجمة آلية إلى Java |
| Dart | ✓ مدعومة | ترجمة آلية إلى Java |

### المميزات الأساسية

**كشف ذكي للغة:** يقوم التطبيق بكشف تلقائي للغة البرمجية المستخدمة باستخدام نظام نقاط ذكي يحقق دقة عالية.

**ترجمة آلية:** يترجم الكود من اللغة الأصلية إلى Java تلقائياً مع الحفاظ على الوظائف الأساسية.

**تحليل متقدم للكود:** يقوم بفحص شامل للكود يتضمن التحقق من الأقواس والكلمات المفتاحية والدوال والفئات.

**تشفير آمن:** يحمي الكود باستخدام تشفير AES-256 وحساب بصمة SHA-256 للتحقق من السلامة.

**توليد ملفات DEX:** ينشئ ملفات DEX من الكود المترجم قابلة للتنفيذ على Android.

**واجهة مستخدم سهلة:** تصميم بسيط وحديث يسهل على المستخدمين استخدام التطبيق.

## متطلبات النظام

**نظام التشغيل:** Android 5.0 (API 21) فما فوق

**الذاكرة:** 2 GB على الأقل

**مساحة التخزين:** 100 MB على الأقل

**Java:** Java 11 أو أحدث

**Gradle:** Gradle 7.0 أو أحدث

## البنية المعمارية

```
APKBuilderFromCode/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/apkbuilderai/
│   │       │   ├── core/
│   │       │   │   ├── NeuralCompiler.java
│   │       │   │   ├── NeuralCompilerEnhanced.java
│   │       │   │   ├── UniversalTranspiler.java
│   │       │   │   ├── UniversalTranspilerEnhanced.java
│   │       │   │   ├── SecurityEngine.java
│   │       │   │   ├── DEXGenerator.java
│   │       │   │   └── DEXGeneratorEnhanced.java
│   │       │   ├── tools/
│   │       │   │   ├── APKBuilder.java
│   │       │   │   └── APKBuilderEnhanced.java
│   │       │   └── ui/
│   │       │       ├── MainActivity.java
│   │       │       └── MainActivityEnhanced.java
│   │       └── res/
│   │           ├── layout/
│   │           │   ├── activity_main.xml
│   │           │   └── activity_main_enhanced.xml
│   │           └── values/
│   │               ├── colors.xml
│   │               ├── colors_enhanced.xml
│   │               ├── strings.xml
│   │               ├── strings_enhanced.xml
│   │               ├── styles.xml
│   │               └── styles_enhanced.xml
│   └── build.gradle
├── build.gradle
├── settings.gradle
├── README.md
├── README_ENHANCED.md
└── BUILD_LOG.md
```

## المكونات الرئيسية

### NeuralCompiler / NeuralCompilerEnhanced

يقوم بتحليل الكود وفحص صحته. يتضمن:

- عد الأسطر والكلمات والأحرف
- التحقق من تطابق الأقواس
- فحص الكلمات المفتاحية
- تحديد الأخطاء والتحذيرات
- كشف الدوال والفئات
- فحص مشاكل الأمان المحتملة

### UniversalTranspiler / UniversalTranspilerEnhanced

يقوم بترجمة الكود من لغة إلى أخرى. يتضمن:

- كشف ذكي للغة البرمجية
- ترجمة تلقائية إلى Java
- دعم 5 لغات برمجية
- نظام نقاط لحساب ثقة الكشف

### SecurityEngine

يقوم بحماية الكود. يتضمن:

- تشفير AES-256 للكود
- حساب بصمة SHA-256
- التحقق من سلامة الكود

### DEXGenerator / DEXGeneratorEnhanced

يقوم بتوليد ملفات DEX. يتضمن:

- التحقق من صحة الكود
- كتابة الملفات الوسيطة
- توليد ملفات DEX
- حساب معلومات الملف

### APKBuilder / APKBuilderEnhanced

منسق البناء الرئيسي. يتضمن:

- إدارة عملية البناء الكاملة
- معالجة الأخطاء الشاملة
- تسجيل العمليات (Logging)
- تتبع التقدم
- إرجاع نتائج مفصلة

## كيفية الاستخدام

### 1. إدخال الكود

اكتب أو الصق الكود الخاص بك في حقل الإدخال. يمكنك استخدام أي من اللغات المدعومة.

### 2. اختيار اللغة (اختياري)

يمكنك اختيار لغة البرمجة يدويًا أو ترك التطبيق يكتشفها تلقائياً.

### 3. بناء APK

انقر على زر "بناء APK" لبدء عملية البناء.

### 4. مراقبة التقدم

ستظهر رسائل حالة توضح مراحل البناء المختلفة:

- تحليل الكود
- كشف اللغة والترجمة
- تشفير الكود
- توليد ملف DEX
- التحقق من البناء

### 5. استخدام APK

بعد نجاح البناء، سيكون ملف APK جاهزاً للاستخدام.

## أمثلة على الاستخدام

### مثال Python

```python
def hello_world():
    print("Hello from APKBuilder!")

if __name__ == "__main__":
    hello_world()
```

سيتم تحويله إلى:

```java
public static void hello_world() {
    System.out.println("Hello from APKBuilder!");
}

public static void main(String[] args) {
    hello_world();
}
```

### مثال JavaScript

```javascript
function greet(name) {
    console.log("Hello, " + name + "!");
}

greet("APKBuilder");
```

سيتم تحويله إلى:

```java
public static void greet(String name) {
    System.out.println("Hello, " + name + "!");
}

public static void main(String[] args) {
    greet("APKBuilder");
}
```

## المتطلبات والتبعيات

### Gradle Dependencies

```gradle
// AndroidX
implementation 'androidx.appcompat:appcompat:1.6.1'
implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
implementation 'androidx.core:core:1.10.1'

// Material Design
implementation 'com.google.android.material:material:1.10.0'

// Utilities
implementation 'org.apache.commons:commons-lang3:3.12.0'
implementation 'commons-io:commons-io:2.11.0'
```

### إعدادات البناء

| الإعداد | القيمة |
|--------|--------|
| compileSdk | 34 |
| minSdk | 21 |
| targetSdk | 34 |
| Java Version | 11 |

## الأمان

**تشفير الكود:** جميع الأكواد المدخلة يتم تشفيرها باستخدام AES-256

**التحقق من السلامة:** يتم حساب بصمة SHA-256 للتحقق من سلامة الكود

**الأذونات:** التطبيق يطلب الأذونات الضرورية فقط

## الأداء

**سرعة البناء:** عملية البناء تتم في ثوان معدودة

**استهلاك الذاكرة:** محسّن لاستخدام أقل قدر من الذاكرة

**حجم APK:** ملفات APK صغيرة الحجم نسبياً

## التطوير والمساهمة

### إعداد بيئة التطوير

1. استنساخ المستودع
2. فتح المشروع في Android Studio
3. تحميل التبعيات
4. بناء المشروع

### المساهمة

نرحب بالمساهمات! يرجى:

1. عمل Fork للمستودع
2. إنشاء فرع جديد للميزة
3. إرسال Pull Request

## الترخيص

هذا المشروع مرخص تحت MIT License.

## الدعم

للإبلاغ عن الأخطاء أو طلب ميزات جديدة، يرجى فتح Issue في المستودع.

## الملاحظات والتحسينات المستقبلية

### التحسينات المخطط لها

- إضافة المزيد من اللغات المدعومة
- تحسين واجهة المستخدم
- إضافة ميزة حفظ المشاريع
- إضافة سجل البناء
- تحسين الأداء

### المعروف من المشاكل

- قد تحتاج الأكواد الكبيرة جداً إلى وقت أطول للبناء
- بعض الميزات المتقدمة قد تتطلب معالجة يدوية

## المؤلفون

تم تطوير هذا المشروع بواسطة فريق APKBuilder AI.

---

**آخر تحديث:** مارس 2026
**الإصدار:** 2.0
