package cn.lance.commons.util.crypto;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;

@Slf4j
public class AesUtilsTest {

    @Test
    public void testGenerateKey() {
        String key = AesUtils.generateKey();
        log.info("AES key: {}", key);
    }

    @Test
    public void testGenerateIv() {
        String iv = AesUtils.generateIv();
        log.info("AES iv: {}", iv);
    }

    @Test
    public void testEncryptWithECB() throws DecoderException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        String key = AesUtils.generateKey();
        log.info("AES key: {}", key);

        String plaintext = "Hello, AES!";
        log.info("Plaintext: {}", plaintext);

        String ciphertext = AesUtils.encrypt(key, plaintext);
        log.info("AES ECB ciphertext: {}", ciphertext);
    }

    @Test
    public void textDecryptWithECB() throws DecoderException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        String key = AesUtils.generateKey();
        log.info("AES key: {}", key);

        String plaintext = "Hello, AES!";
        log.info("Plaintext: {}", plaintext);

        String ciphertext = AesUtils.encrypt(key, plaintext);
        log.info("AES ECB ciphertext: {}", ciphertext);

        String decryptedText = AesUtils.decrypt(key, ciphertext);
        log.info("AES ECB decryptedText: {}", decryptedText);

        boolean result = plaintext.equals(decryptedText);
        log.info("ECB Plaintext equals to decryptedText? {}", result);
        Assertions.assertTrue(result);
    }

    @Test
    public void testEncryptWithCBC() throws DecoderException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
        String key = AesUtils.generateKey();
        log.info("AES key: {}", key);

        String iv = AesUtils.generateIv();
        log.info("AES iv: {}", iv);

        String plaintext = "Hello, AES!";
        log.info("Plaintext: {}", plaintext);

        String ciphertext = AesUtils.encrypt(key, iv, plaintext);
        log.info("AES CBC ciphertext: {}", ciphertext);
    }

    @Test
    public void textDecryptWithCBC() throws DecoderException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
        String key = AesUtils.generateKey();
        log.info("AES key: {}", key);

        String iv = AesUtils.generateIv();
        log.info("AES iv: {}", iv);

        String plaintext = "Hello, AES!";
        log.info("Plaintext: {}", plaintext);

        String ciphertext = AesUtils.encrypt(key, iv, plaintext);
        log.info("AES CBC ciphertext: {}", ciphertext);

        String decryptedText = AesUtils.decrypt(key, iv, ciphertext);
        log.info("AES CBC decryptedText: {}", decryptedText);

        boolean result = plaintext.equals(decryptedText);
        log.info("CBC Plaintext equals to decryptedText? {}", result);
        Assertions.assertTrue(result);
    }

}
