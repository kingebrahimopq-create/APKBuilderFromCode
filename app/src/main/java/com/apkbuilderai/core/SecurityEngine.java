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
     * @return مصفوفة تحتوي على [0] الكود المشفر و [1] المفتاح المشفر Base64
     */
    public static String[] encryptCode(String code) {
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
            String encryptedCode = Base64.getEncoder().encodeToString(encryptedData);

            // تحويل المفتاح إلى Base64 للتخزين
            String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());

            // إرجاع كل من الكود المشفر والمفتاح
            return new String[] {encryptedCode, encodedKey};

        } catch (Exception e) {
            android.util.Log.e("SecurityEngine", "Encryption error: " + e.getMessage());
            return new String[] {code, ""};
        }
    }

    /**
     * فك تشفير الكود
     * @param encryptedCode الكود المشفر
     * @param encodedKey المفتاح المشفر Base64
     * @return الكود المفكوك
     */
    public static String decryptCode(String encryptedCode, String encodedKey) {
        try {
            // تحويل المفتاح من Base64
            byte[] keyBytes = Base64.getDecoder().decode(encodedKey);
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, ALGORITHM);

            // فك تشفير البيانات
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

            byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedCode));
            return new String(decryptedData);

        } catch (Exception e) {
            android.util.Log.e("SecurityEngine", "Decryption error: " + e.getMessage());
            return encryptedCode;
        }
    }

    /**
     * حساب بصمة الكود باستخدام SHA-256
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

    public static boolean verifyCodeIntegrity(String code, String hash) {
        return hashCode(code).equals(hash);
    }
}
