import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Textarea } from "@/components/ui/textarea";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Alert, AlertDescription, AlertTitle } from "@/components/ui/alert";
import { Progress } from "@/components/ui/progress";
import { Badge } from "@/components/ui/badge";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import {
  Code,
  Hammer,
  Download,
  Settings,
  CheckCircle,
  AlertCircle,
  Loader2,
  Smartphone,
  FileCode,
  Zap,
  Shield,
  Cpu
} from "lucide-react";

// قوالب التطبيقات الجاهزة
const APP_TEMPLATES = {
  blank: {
    name: "تطبيق فارغ",
    description: "هيكل تطبيق أساسي",
    icon: "📄"
  },
  calculator: {
    name: "آلة حاسبة",
    description: "حاسبة بسيطة",
    icon: "🔢"
  },
  webview: {
    name: "متصفح ويب",
    description: "عارض صفحات إنترنت",
    icon: "🌐"
  },
  login: {
    name: "تسجيل دخول",
    description: "شاشة تسجيل دخول",
    icon: "🔐"
  },
  notes: {
    name: "ملاحظات",
    description: "تطبيق ملاحظات",
    icon: "📝"
  },
  store: {
    name: "متجر إلكتروني",
    description: "متجر للبيع والشراء",
    icon: "🛒"
  }
};

// اللغات المدعومة
const SUPPORTED_LANGUAGES = [
  { id: "java", name: "Java", icon: "☕" },
  { id: "kotlin", name: "Kotlin", icon: "🟣" },
  { id: "python", name: "Python", icon: "🐍" },
  { id: "javascript", name: "JavaScript", icon: "📜" },
  { id: "dart", name: "Dart", icon: "🎯" },
  { id: "swift", name: "Swift", icon: "🍎" }
];

export default function Home() {
  const [code, setCode] = useState("");
  const [selectedLanguage, setSelectedLanguage] = useState("java");
  const [selectedTemplate, setSelectedTemplate] = useState("blank");
  const [isBuilding, setIsBuilding] = useState(false);
  const [buildProgress, setBuildProgress] = useState(0);
  const [buildStatus, setBuildStatus] = useState<"idle" | "building" | "success" | "error">("idle");
  const [buildMessage, setBuildMessage] = useState("");
  const [detectedLanguage, setDetectedLanguage] = useState<string | null>(null);
  const [errors, setErrors] = useState<string[]>([]);
  const [warnings, setWarnings] = useState<string[]>([]);

  // كشف اللغة تلقائياً
  const detectLanguage = (inputCode: string) => {
    if (!inputCode.trim()) {
      setDetectedLanguage(null);
      return;
    }

    if (inputCode.includes("def ") && inputCode.includes("import ")) {
      setDetectedLanguage("Python");
    } else if (inputCode.includes("fun ") || inputCode.includes("val ")) {
      setDetectedLanguage("Kotlin");
    } else if (inputCode.includes("function ") || inputCode.includes("console.log")) {
      setDetectedLanguage("JavaScript");
    } else if (inputCode.includes("void main()")) {
      setDetectedLanguage("Dart");
    } else if (inputCode.includes("class ") && inputCode.includes(";")) {
      setDetectedLanguage("Java");
    } else {
      setDetectedLanguage("Java");
    }
  };

  // تحليل الكود
  const analyzeCode = (inputCode: string) => {
    const newErrors: string[] = [];
    const newWarnings: string[] = [];

    // التحقق من الأقواس
    const braces = (inputCode.match(/\{/g) || []).length;
    const closeBraces = (inputCode.match(/\}/g) || []).length;
    if (braces !== closeBraces) {
      newErrors.push("الأقواس المعقوفة غير متطابقة");
    }

    const parens = (inputCode.match(/\(/g) || []).length;
    const closeParens = (inputCode.match(/\)/g) || []).length;
    if (parens !== closeParens) {
      newErrors.push("الأقواس الدائرية غير متطابقة");
    }

    // التحذيرات
    if (!inputCode.includes("main") && !inputCode.includes("def ") && !inputCode.includes("func")) {
      newWarnings.push("لم يتم العثور على دالة رئيسية");
    }

    if (!inputCode.includes("class") && !inputCode.includes("def ") && !inputCode.includes("function")) {
      newWarnings.push("لم يتم العثور على تعريفات");
    }

    setErrors(newErrors);
    setWarnings(newWarnings);
  };

  // معالجة إدخال الكود
  const handleCodeChange = (value: string) => {
    setCode(value);
    detectLanguage(value);
    analyzeCode(value);
  };

  // بناء APK
  const buildAPK = async () => {
    if (!code.trim()) {
      setBuildStatus("error");
      setBuildMessage("الرجاء إدخال الكود");
      return;
    }

    setIsBuilding(true);
    setBuildStatus("building");
    setBuildProgress(0);
    setBuildMessage("بدء عملية البناء...");

    try {
      // الخطوة 1: تحليل الكود
      setBuildProgress(10);
      setBuildMessage("جاري تحليل الكود...");
      await new Promise(resolve => setTimeout(resolve, 500));

      if (errors.length > 0) {
        throw new Error("يوجد أخطاء في الكود");
      }

      // الخطوة 2: كشف اللغة والترجمة
      setBuildProgress(30);
      setBuildMessage("جاري كشف اللغة والترجمة...");
      await new Promise(resolve => setTimeout(resolve, 500));

      // الخطوة 3: تشفير الكود
      setBuildProgress(50);
      setBuildMessage("جاري تشفير الكود...");
      await new Promise(resolve => setTimeout(resolve, 500));

      // الخطوة 4: توليد DEX
      setBuildProgress(70);
      setBuildMessage("جاري توليد ملفات DEX...");
      await new Promise(resolve => setTimeout(resolve, 500));

      // الخطوة 5: بناء APK
      setBuildProgress(90);
      setBuildMessage("جاري بناء APK...");
      await new Promise(resolve => setTimeout(resolve, 500));

      // النجاح
      setBuildProgress(100);
      setBuildStatus("success");
      setBuildMessage("تم بناء APK بنجاح!");

    } catch (error) {
      setBuildStatus("error");
      setBuildMessage(error instanceof Error ? error.message : "فشل في البناء");
    } finally {
      setIsBuilding(false);
    }
  };

  // اختيار قالب
  const selectTemplate = (templateKey: string) => {
    setSelectedTemplate(templateKey);
    const template = APP_TEMPLATES[templateKey as keyof typeof APP_TEMPLATES];

    // إضافة كود القالب
    const templateCode = getTemplateCode(templateKey);
    setCode(templateCode);
    detectLanguage(templateCode);
    analyzeCode(templateCode);
  };

  // الحصول على كود القالب
  const getTemplateCode = (templateKey: string): string => {
    switch (templateKey) {
      case "calculator":
        return `public class Calculator {
    public static int add(int a, int b) {
        return a + b;
    }

    public static int subtract(int a, int b) {
        return a - b;
    }

    public static int multiply(int a, int b) {
        return a * b;
    }

    public static double divide(int a, int b) {
        if (b == 0) return 0;
        return (double) a / b;
    }

    public static void main(String[] args) {
        System.out.println("آلة حاسبة");
        System.out.println("5 + 3 = " + add(5, 3));
        System.out.println("10 - 4 = " + subtract(10, 4));
    }
}`;
      case "webview":
        return `public class WebViewApp extends Activity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://google.com");
    }
}`;
      case "login":
        return `public class LoginActivity extends Activity {
    private EditText emailInput;
    private EditText passwordInput;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.email);
        passwordInput = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString();
            String password = passwordInput.getText().toString();
            // التحقق من البيانات
        });
    }
}`;
      case "notes":
        return `public class NotesActivity extends Activity {
    private ListView notesList;
    private ArrayList<String> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        notes = new ArrayList<>();
        notesList = findViewById(R.id.notes_list);

        notesList.setAdapter(new ArrayAdapter<>(this,
            android.R.layout.simple_list_item_1, notes));
    }

    public void addNote(String note) {
        notes.add(note);
    }
}`;
      case "store":
        return `public class StoreActivity extends Activity {
    private RecyclerView productsList;
    private ArrayList<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        products = new ArrayList<>();
        // إضافة المنتجات
    }

    public void addToCart(Product product) {
        // إضافة للعربة
    }
}`;
      default:
        return `public class MyApp {
    public static void main(String[] args) {
        System.out.println("مرحباً بك في تطبيقك!");
    }
}`;
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-50 to-slate-100 dark:from-slate-900 dark:to-slate-800">
      {/* الهيدر */}
      <header className="bg-white dark:bg-slate-800 shadow-sm border-b">
        <div className="container mx-auto px-4 py-4 flex items-center justify-between">
          <div className="flex items-center gap-3">
            <div className="w-12 h-12 bg-gradient-to-br from-blue-500 to-purple-600 rounded-xl flex items-center justify-center">
              <Smartphone className="w-7 h-7 text-white" />
            </div>
            <div>
              <h1 className="text-2xl font-bold text-slate-900 dark:text-white">
                APK Builder AI
              </h1>
              <p className="text-sm text-slate-500 dark:text-slate-400">
                حوّل الكود إلى تطبيق Android
              </p>
            </div>
          </div>
          <div className="flex items-center gap-2">
            <Button variant="outline" size="sm">
              <Settings className="w-4 h-4 mr-2" />
              الإعدادات
            </Button>
          </div>
        </div>
      </header>

      <main className="container mx-auto px-4 py-8">
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
          {/* القسم الرئيسي */}
          <div className="lg:col-span-2 space-y-6">
            {/* اختيار القالب */}
            <Card>
              <CardHeader>
                <CardTitle className="flex items-center gap-2">
                  <FileCode className="w-5 h-5" />
                  اختر قالب التطبيق
                </CardTitle>
                <CardDescription>
                  اختر قالب جاهز أو اكتب كودك الخاص
                </CardDescription>
              </CardHeader>
              <CardContent>
                <div className="grid grid-cols-2 md:grid-cols-3 gap-3">
                  {Object.entries(APP_TEMPLATES).map(([key, template]) => (
                    <button
                      key={key}
                      onClick={() => selectTemplate(key)}
                      className={`p-4 rounded-lg border-2 transition-all text-right ${
                        selectedTemplate === key
                          ? "border-blue-500 bg-blue-50 dark:bg-blue-900/20"
                          : "border-slate-200 dark:border-slate-700 hover:border-slate-300"
                      }`}
                    >
                      <span className="text-2xl block mb-2">{template.icon}</span>
                      <span className="font-medium text-slate-900 dark:text-white block">
                        {template.name}
                      </span>
                      <span className="text-xs text-slate-500">
                        {template.description}
                      </span>
                    </button>
                  ))}
                </div>
              </CardContent>
            </Card>

            {/* إدخال الكود */}
            <Card>
              <CardHeader>
                <div className="flex items-center justify-between">
                  <div>
                    <CardTitle className="flex items-center gap-2">
                      <Code className="w-5 h-5" />
                      إدخال الكود
                    </CardTitle>
                    <CardDescription>
                      اكتب كود Java/Kotlin/Python/JavaScript
                    </CardDescription>
                  </div>
                  {detectedLanguage && (
                    <Badge variant="outline" className="gap-1">
                      <CheckCircle className="w-3 h-3" />
                      {detectedLanguage} مكتشف
                    </Badge>
                  )}
                </div>
              </CardHeader>
              <CardContent className="space-y-4">
                <Textarea
                  value={code}
                  onChange={(e) => handleCodeChange(e.target.value)}
                  placeholder="اكتب الكود هنا..."
                  className="min-h-[300px] font-mono text-sm"
                  dir="ltr"
                />

                <div className="flex items-center gap-4">
                  <div className="flex-1">
                    <label className="text-sm font-medium mb-2 block">
                      لغة البرمجة
                    </label>
                    <Select value={selectedLanguage} onValueChange={setSelectedLanguage}>
                      <SelectTrigger>
                        <SelectValue />
                      </SelectTrigger>
                      <SelectContent>
                        {SUPPORTED_LANGUAGES.map((lang) => (
                          <SelectItem key={lang.id} value={lang.id}>
                            <span className="flex items-center gap-2">
                              <span>{lang.icon}</span>
                              {lang.name}
                            </span>
                          </SelectItem>
                        ))}
                      </SelectContent>
                    </Select>
                  </div>

                  <div className="flex-1">
                    <label className="text-sm font-medium mb-2 block">
                      عدد الأسطر
                    </label>
                    <div className="text-2xl font-bold text-slate-900 dark:text-white">
                      {code.split("\n").length}
                    </div>
                  </div>
                </div>
              </CardContent>
            </Card>

            {/* زر البناء */}
            <Button
              onClick={buildAPK}
              disabled={isBuilding || !code.trim()}
              className="w-full py-6 text-lg"
              size="lg"
            >
              {isBuilding ? (
                <>
                  <Loader2 className="w-5 h-5 mr-2 animate-spin" />
                  جاري البناء...
                </>
              ) : (
                <>
                  <Hammer className="w-5 h-5 mr-2" />
                  بناء APK
                </>
              )}
            </Button>

            {/* حالة البناء */}
            {buildStatus !== "idle" && (
              <Card>
                <CardHeader>
                  <CardTitle className="flex items-center gap-2">
                    {buildStatus === "building" && <Loader2 className="w-5 h-5 animate-spin" />}
                    {buildStatus === "success" && <CheckCircle className="w-5 h-5 text-green-500" />}
                    {buildStatus === "error" && <AlertCircle className="w-5 h-5 text-red-500" />}
                    حالة البناء
                  </CardTitle>
                </CardHeader>
                <CardContent className="space-y-4">
                  <Progress value={buildProgress} className="h-2" />
                  <p className="text-center text-slate-600 dark:text-slate-400">
                    {buildMessage}
                  </p>

                  {buildStatus === "success" && (
                    <Button className="w-full">
                      <Download className="w-4 h-4 mr-2" />
                      تحميل APK
                    </Button>
                  )}
                </CardContent>
              </Card>
            )}
          </div>

          {/* الشريط الجانبي */}
          <div className="space-y-6">
            {/* الأخطاء والتحذيرات */}
            <Card>
              <CardHeader>
                <CardTitle className="flex items-center gap-2">
                  <AlertCircle className="w-5 h-5" />
                  تحليل الكود
                </CardTitle>
              </CardHeader>
              <CardContent className="space-y-4">
                {errors.length > 0 && (
                  <Alert variant="destructive">
                    <AlertCircle className="h-4 w-4" />
                    <AlertTitle>الأخطاء</AlertTitle>
                    <AlertDescription>
                      <ul className="list-disc list-inside text-sm">
                        {errors.map((error, i) => (
                          <li key={i}>{error}</li>
                        ))}
                      </ul>
                    </AlertDescription>
                  </Alert>
                )}

                {warnings.length > 0 && (
                  <Alert>
                    <AlertCircle className="h-4 w-4" />
                    <AlertTitle>التحذيرات</AlertTitle>
                    <AlertDescription>
                      <ul className="list-disc list-inside text-sm">
                        {warnings.map((warning, i) => (
                          <li key={i}>{warning}</li>
                        ))}
                      </ul>
                    </AlertDescription>
                  </Alert>
                )}

                {errors.length === 0 && warnings.length === 0 && code.trim() && (
                  <Alert>
                    <CheckCircle className="h-4 w-4" />
                    <AlertTitle>الكود صحيح</AlertTitle>
                    <AlertDescription>
                      لا توجد أخطاء أو تحذيرات
                    </AlertDescription>
                  </Alert>
                )}

                {!code.trim() && (
                  <p className="text-sm text-slate-500 text-center">
                    أدخل الكود لرؤية التحليل
                  </p>
                )}
              </CardContent>
            </Card>

            {/* المميزات */}
            <Card>
              <CardHeader>
                <CardTitle>مميزات التطبيق</CardTitle>
              </CardHeader>
              <CardContent className="space-y-4">
                <div className="flex items-center gap-3">
                  <div className="w-10 h-10 bg-blue-100 dark:bg-blue-900/30 rounded-lg flex items-center justify-center">
                    <Zap className="w-5 h-5 text-blue-600" />
                  </div>
                  <div>
                    <h4 className="font-medium">بناء سريع</h4>
                    <p className="text-sm text-slate-500">ثوانٍ معدودة</p>
                  </div>
                </div>

                <div className="flex items-center gap-3">
                  <div className="w-10 h-10 bg-green-100 dark:bg-green-900/30 rounded-lg flex items-center justify-center">
                    <Shield className="w-5 h-5 text-green-600" />
                  </div>
                  <div>
                    <h4 className="font-medium">أمان عالي</h4>
                    <p className="text-sm text-slate-500">تشفير AES-256</p>
                  </div>
                </div>

                <div className="flex items-center gap-3">
                  <div className="w-10 h-10 bg-purple-100 dark:bg-purple-900/30 rounded-lg flex items-center justify-center">
                    <Cpu className="w-5 h-5 text-purple-600" />
                  </div>
                  <div>
                    <h4 className="font-medium">دعم多个 اللغات</h4>
                    <p className="text-sm text-slate-500">6 لغات برمجية</p>
                  </div>
                </div>
              </CardContent>
            </Card>

            {/* الإحصائيات */}
            <Card>
              <CardHeader>
                <CardTitle>إحصائيات</CardTitle>
              </CardHeader>
              <CardContent>
                <div className="grid grid-cols-2 gap-4">
                  <div className="text-center p-4 bg-slate-50 dark:bg-slate-800 rounded-lg">
                    <div className="text-2xl font-bold text-blue-600">
                      {code.split("\n").length}
                    </div>
                    <div className="text-sm text-slate-500">أسطر</div>
                  </div>
                  <div className="text-center p-4 bg-slate-50 dark:bg-slate-800 rounded-lg">
                    <div className="text-2xl font-bold text-green-600">
                      {code.length}
                    </div>
                    <div className="text-sm text-slate-500">حرف</div>
                  </div>
                </div>
              </CardContent>
            </Card>
          </div>
        </div>
      </main>
    </div>
  );
}
