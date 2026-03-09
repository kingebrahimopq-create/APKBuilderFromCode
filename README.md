# APKBuilderAI - محول الأكواد إلى APK

تطبيق Android متقدم يقوم بتحويل الأكواس البرمجية من لغات متعددة إلى ملفات APK قابلة للتنفيذ تلقائياً.

## المميزات الرئيسية

- **دعم لغات برمجية متعددة**: Java, Python, JavaScript, Kotlin, Dart
- **كشف ذكي للغة**: تحديد تلقائي للغة البرمجية المستخدمة
- **ترجمة آلية**: تحويل الكود إلى Java قبل البناء
- **تحليل متقدم للكود**: فحص شامل لصحة الكود قبل البناء
- **تشفير آمن**: حماية الكود باستخدام تشفير AES
- **توليد ملفات DEX**: إنشاء ملفات DEX من الكود المترجم
- **واجهة مستخدم سهلة**: تصميم بسيط وسهل الاستخدام

## اللغات المدعومة

| اللغة | الحالة | ملاحظات |
|-------|--------|---------|
| Java | ✓ مدعومة | اللغة الأساسية |
| Python | ✓ مدعومة | ترجمة آلية إلى Java |
| JavaScript | ✓ مدعومة | ترجمة آلية إلى Java |
| Kotlin | ✓ مدعومة | ترجمة آلية إلى Java |
| Dart | ✓ مدعومة | ترجمة آلية إلى Java |

## متطلبات النظام

- **نظام التشغيل**: Android 5.0 (API 21) فما فوق
- **الذاكرة**: 2 GB على الأقل
- **مساحة التخزين**: 100 MB على الأقل

## البنية المعمارية

```
APKBuilderFromCode/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/apkbuilderai/
│   │       │   ├── core/
│   │       │   │   ├── NeuralCompiler.java      (تحليل الكود)
│   │       │   │   ├── UniversalTranspiler.java (ترجمة الكود)
│   │       │   │   ├── DEXGenerator.java        (توليد DEX)
│   │       │   │   └── SecurityEngine.java      (التشفير والأمان)
│   │       │   ├── tools/
│   │       │   │   └── APKBuilder.java          (منسق البناء الرئيسي)
│   │       │   └── ui/
│   │       │       └── MainActivity.java        (واجهة المستخدم)
│   │       └── res/
│   │           ├── layout/
│   │           │   └── activity_main.xml
│   │           └── values/
│   │               ├── colors.xml
│   │               ├── strings.xml
│   │               └── styles.xml
│   └── build.gradle
├── build.gradle
├── settings.gradle
└── README.md
```

## المكونات الرئيسية

### 1. NeuralCompiler
يقوم بتحليل الكود وفحص صحته:
- عد الأسطر والكلمات والأحرف
- التحقق من تطابق الأقواس
- فحص الكلمات المفتاحية
- تحديد الأخطاء والتحذيرات

### 2. UniversalTranspiler
يقوم بترجمة الكود من لغة إلى أخرى:
- كشف ذكي للغة البرمجية
- ترجمة تلقائية إلى Java
- دعم 5 لغات برمجية

### 3. SecurityEngine
يقوم بحماية الكود:
- تشفير AES-256 للكود
- حساب بصمة SHA-256
- التحقق من سلامة الكود

### 4. DEXGenerator
يقوم بتوليد ملفات DEX:
- التحقق من صحة الكود
- كتابة الملفات الوسيطة
- توليد ملفات DEX

### 5. APKBuilder
منسق البناء الرئيسي:
- إدارة عملية البناء الكاملة
- معالجة الأخطاء الشاملة
- تسجيل العمليات (Logging)

### 6. MainActivity
واجهة المستخدم الرئيسية:
- إدخال الكود
- عرض حالة البناء
- معالجة أحداث الأزرار

## الإصلاحات والتحسينات

### الأخطاء المصححة

1. **خطأ في activity_main.xml**
   - المشكلة: عنصر `status_text` مفقود من الـ layout
   - الحل: إضافة TextView لعرض حالة البناء

2. **خطأ في SecurityEngine.java**
   - المشكلة: استخدام SHA-256 (hash) بدلاً من التشفير الفعلي
   - الحل: استخدام AES-256 للتشفير الفعلي وSHA-256 للبصمة

3. **خطأ في DEXGenerator.java**
   - المشكلة: الدالة فارغة تماماً
   - الحل: إضافة منطق فعلي لتوليد ملفات DEX

4. **خطأ في UniversalTranspiler.java**
   - المشكلة: كشف اللغة بسيط وغير دقيق
   - الحل: تحسين خوارزمية الكشف وإضافة ترجمات أفضل

5. **خطأ في NeuralCompiler.java**
   - المشكلة: الدالة parseCode مجرد placeholder
   - الحل: إضافة تحليل فعلي للكود

6. **خطأ في APKBuilder.java**
   - المشكلة: عدم وجود معالجة شاملة للأخطاء
   - الحل: إضافة معالجة أخطاء شاملة وتسجيل العمليات

7. **خطأ في MainActivity.java**
   - المشكلة: معالجة بسيطة للأخطاء
   - الحل: تحسين معالجة الأخطاء وتحديث واجهة المستخدم

## كيفية الاستخدام

### 1. إدخال الكود
اكتب أو الصق الكود الخاص بك في حقل الإدخال. يمكنك استخدام أي من اللغات المدعومة.

### 2. بناء APK
انقر على زر "بناء APK" لبدء عملية البناء.

### 3. مراقبة التقدم
ستظهر رسائل حالة توضح مراحل البناء المختلفة.

### 4. استخدام APK
بعد نجاح البناء، سيكون ملف APK جاهزاً للاستخدام.

## مثال على الكود

```python
# مثال على كود Python
def hello_world():
    print("Hello from APKBuilder!")

if __name__ == "__main__":
    hello_world()
```

سيتم تحويل هذا الكود تلقائياً إلى:

```java
// كود Java المترجم
public static void hello_world() {
    System.out.println("Hello from APKBuilder!");
}

public static void main(String[] args) {
    hello_world();
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

## الإعدادات

| الإعداد | القيمة |
|--------|--------|
| compileSdk | 34 |
| minSdk | 21 |
| targetSdk | 34 |
| Java Version | 11 |

## تسجيل الأخطاء (Logging)

يتم تسجيل جميع العمليات والأخطاء باستخدام Android Logger:

```
D/NeuralCompiler: تحليل الكود
D/UniversalTranspiler: ترجمة الكود من Python إلى Java
D/SecurityEngine: تشفير الكود
D/DEXGenerator: توليد ملف DEX
I/APKBuilder: تم بناء APK بنجاح
```

## الأمان

- **تشفير الكود**: جميع الأكواد المدخلة يتم تشفيرها باستخدام AES-256
- **التحقق من السلامة**: يتم حساب بصمة SHA-256 للتحقق من سلامة الكود
- **الأذونات**: التطبيق يطلب الأذونات الضرورية فقط

## الأداء

- **سرعة البناء**: عملية البناء تتم في ثوان معدودة
- **استهلاك الذاكرة**: محسّن لاستخدام أقل قدر من الذاكرة
- **حجم APK**: ملفات APK صغيرة الحجم نسبياً

## المساهمة

نرحب بالمساهمات! يرجى:
1. عمل Fork للمستودع
2. إنشاء فرع جديد للميزة
3. إرسال Pull Request

## الترخيص

هذا المشروع مرخص تحت MIT License.

## الدعم

للإبلاغ عن الأخطاء أو طلب ميزات جديدة، يرجى فتح Issue في المستودع.

## المؤلف

تم تطوير هذا المشروع بواسطة فريق APKBuilder AI.

---

**آخر تحديث**: مارس 2026
**الإصدار**: 1.0
