package com.apkbuilderai.core;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class SecurityEngine {
    private static final String ALGORITHM = "AES";
    private static final int KEY_SIZE = 256;

    /**
     * تشفير الكود باستخدام AES
     * @param code الكود المراد تشفيره
     * @return الكود المشفر
     */
    public static String encryptCode(String code) {
        try {
            // توليد مفتاح عشوائي آمن
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
            keyGenerator.init(KEY_SIZE, new SecureRandom());
            SecretKey secretKey = keyGenerator.generateKey();

            // إنشاء Cipher
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            // تشفير البيانات
            byte[] encryptedData = cipher.doFinal(code.getBytes());

            // تحويل البيانات المشفرة إلى Base64
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            android.util.Log.e("SecurityEngine", "Encryption error: " + e.getMessage());
            return code;
        }
    }

    /**
     * حساب بصمة الكود باستخدام SHA-256 (للتحقق من السلامة)
     * @param code الكود المراد حساب بصمته
     * @return بصمة الكود
     */
    public static String hashCode(String code) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(code.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            android.util.Log.e("SecurityEngine", "Hashing error: " + e.getMessage());
            return code;
        }
    }

    /**
     * التحقق من سلامة الكود
     * @param code الكود الأصلي
     * @param hash البصمة المحفوظة
     * @return true إذا كانت البصمة متطابقة
     */
    public static boolean verifyCodeIntegrity(String code, String hash) {
        return hashCode(code).equals(hash);
    }
}
