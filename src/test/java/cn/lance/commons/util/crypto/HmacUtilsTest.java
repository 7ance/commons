package cn.lance.commons.util.crypto;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@SuppressWarnings("LoggingSimilarMessage")
public class HmacUtilsTest {

    @Test
    public void testGenerateKeySha256() throws NoSuchAlgorithmException {
        String algoSha256 = "HmacSHA256";
        String keySha256 = HmacUtils.generateKey(algoSha256, 256);
        log.info("SHA256 key: {}", keySha256);
    }

    @Test
    public void testGenerateKeyMd5() throws NoSuchAlgorithmException {
        String algoMd5 = "HmacMD5";
        String keyMd5 = HmacUtils.generateKey(algoMd5, 256);
        log.info("MD5 key: {}", keyMd5);
    }

    @Test
    public void testSignSha256() throws NoSuchAlgorithmException, InvalidKeyException {
        String algoSha256 = "HmacSHA256";
        String keySha256 = HmacUtils.generateKey(algoSha256, 256);
        log.info("SHA256 key: {}", keySha256);

        String plaintext = "Hello, HMAC!";
        log.info("Plaintext: {}", plaintext);

        String sign = HmacUtils.sign(algoSha256, keySha256, plaintext);
        log.info("SHA256 sign: {}", sign);
    }

    @Test
    public void testSignMd5() throws NoSuchAlgorithmException, InvalidKeyException {
        String algoMd5 = "HmacMD5";
        String keyMd5 = HmacUtils.generateKey(algoMd5, 256);
        log.info("MD5 key: {}", keyMd5);

        String plaintext = "Hello, HMAC!";
        log.info("Plaintext: {}", plaintext);

        String sign = HmacUtils.sign(algoMd5, keyMd5, plaintext);
        log.info("MD5 sign: {}", sign);
    }

    @Test
    public void testVerifySha256() throws NoSuchAlgorithmException, InvalidKeyException {
        String algoSha256 = "HmacSHA256";
        String keySha256 = HmacUtils.generateKey(algoSha256, 256);
        log.info("SHA256 key: {}", keySha256);

        String plaintext = "Hello, HMAC!";
        log.info("Plaintext: {}", plaintext);

        String sign = HmacUtils.sign(algoSha256, keySha256, plaintext);
        log.info("SHA256 sign: {}", sign);

        boolean verified = HmacUtils.verify(algoSha256, keySha256, plaintext, sign);
        log.info("Verify result: {}", verified);
        Assertions.assertTrue(verified);
    }

    @Test
    public void testVerifyMd5() throws NoSuchAlgorithmException, InvalidKeyException {
        String algoMd5 = "HmacMD5";
        String keyMd5 = HmacUtils.generateKey(algoMd5, 256);
        log.info("MD5 key: {}", keyMd5);

        String plaintext = "Hello, HMAC!";
        log.info("Plaintext: {}", plaintext);

        String sign = HmacUtils.sign(algoMd5, keyMd5, plaintext);
        log.info("MD5 sign: {}", sign);

        boolean verified = HmacUtils.verify(algoMd5, keyMd5, plaintext, sign);
        log.info("Verify result: {}", verified);
        Assertions.assertTrue(verified);
    }

}
