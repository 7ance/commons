package cn.lance.commons.util.crypto;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Objects;

/**
 * Advanced Encryption Standard
 *
 * @implNote Support two modes:
 * 1. Electronic Codebook Mode (For security reasons, not recommended)
 * 2. Cipher Block Chaining Mode
 */
public class AesUtils {

    private final static String ALGORITHM = "AES";

    /**
     * algorithm/mode/padding
     */
    private final static String TRANSFORMATION = "AES/CBC/PKCS5Padding";

    private final static Integer KEY_SIZE = 256;

    private AesUtils() {
    }

    /**
     * 生成AES密钥
     *
     * @return AES密钥（十六进制）
     */
    public static String generateKey() {
        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        keyGenerator.init(KEY_SIZE);
        SecretKey secretKey = keyGenerator.generateKey();

        return new String(Hex.encodeHex(secretKey.getEncoded()));
    }

    /**
     * 生成AES初始向量
     *
     * @return AES初始向量（十六进制）
     */
    public static String generateIv() {
        // AES块大小是128位，即16字节
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new String(Hex.encodeHex(iv));
    }

    /**
     * AES加密（ECB模式）
     *
     * @param key       密钥（十六进制）
     * @param plaintext 原文
     * @return 密文（十六进制）
     */
    public static String encrypt(String key, String plaintext) throws DecoderException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Objects.requireNonNull(key, "key must not be null");
        Objects.requireNonNull(plaintext, "plaintext must not be null");

        // init cipher step1
        Cipher cipher;
        try {
            cipher = Cipher.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }

        // process key
        byte[] byteKey;
        byteKey = Hex.decodeHex(key);

        // init cipher step2
        SecretKeySpec secretKeySpec = new SecretKeySpec(byteKey, ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

        // encrypt
        byte[] bytes;
        bytes = cipher.doFinal(plaintext.getBytes());

        // encode result
        return new String(Hex.encodeHex(bytes));
    }

    /**
     * AES解密（ECB模式）
     *
     * @param key        密钥（十六进制）
     * @param ciphertext 密文（十六进制）
     * @return 原文
     */
    public static String decrypt(String key, String ciphertext) throws DecoderException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Objects.requireNonNull(key, "key must not be null");
        Objects.requireNonNull(ciphertext, "ciphertext must not be null");

        // init cipher step1
        Cipher cipher;
        try {
            cipher = Cipher.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }

        // process key
        byte[] byteKey;
        byteKey = Hex.decodeHex(key);

        // init cipher step2
        SecretKeySpec secretKeySpec = new SecretKeySpec(byteKey, ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

        // process encryptedStr
        byte[] byteEncryptedStr;
        byteEncryptedStr = Hex.decodeHex(ciphertext);

        byte[] bytes;
        bytes = cipher.doFinal(byteEncryptedStr);

        return new String(bytes);
    }

    /**
     * AES加密（CBC模式）
     *
     * @param key       密钥（十六进制）
     * @param iv        初始向量（十六进制）
     * @param plaintext 原文
     * @return 密文
     */
    public static String encrypt(String key, String iv, String plaintext) throws DecoderException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Objects.requireNonNull(key, "key must not be null");
        Objects.requireNonNull(key, "iv must not be null");
        Objects.requireNonNull(plaintext, "plaintext must not be null");

        // init cipher step1
        Cipher cipher;
        try {
            cipher = Cipher.getInstance(TRANSFORMATION);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }

        // process key
        byte[] byteKey = Hex.decodeHex(key);
        byte[] ivKey = Hex.decodeHex(iv);

        // init cipher step2
        SecretKeySpec secretKeySpec = new SecretKeySpec(byteKey, ALGORITHM);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivKey);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

        // encrypt
        byte[] bytes;
        bytes = cipher.doFinal(plaintext.getBytes());

        // encode result
        return new String(Hex.encodeHex(bytes));
    }

    /**
     * AES解密（CBC模式）
     *
     * @param key        密钥（十六进制）
     * @param iv         初始向量（十六进制）
     * @param ciphertext 密文
     * @return 原文
     */
    public static String decrypt(String key, String iv, String ciphertext) throws DecoderException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Objects.requireNonNull(key, "key must not be null");
        Objects.requireNonNull(key, "iv must not be null");
        Objects.requireNonNull(ciphertext, "ciphertext must not be null");

        // init cipher step1
        Cipher cipher;
        try {
            cipher = Cipher.getInstance(TRANSFORMATION);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }

        // process key
        byte[] byteKey = Hex.decodeHex(key);
        byte[] ivKey = Hex.decodeHex(iv);

        // init cipher step2
        SecretKeySpec secretKeySpec = new SecretKeySpec(byteKey, ALGORITHM);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivKey);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

        // process encryptedStr
        byte[] byteEncryptedStr;
        byteEncryptedStr = Hex.decodeHex(ciphertext);

        byte[] bytes;
        bytes = cipher.doFinal(byteEncryptedStr);

        return new String(bytes);
    }

}
