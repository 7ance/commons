package cn.lance.commons.util.crypto;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidKeyException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

@Slf4j
@SuppressWarnings("LoggingSimilarMessage")
public class RsaUtilsTest {

    @Test
    public void testGenerateKeyPair() {
        Pair<String, String> keyPair = RsaUtils.generateKeyPair();
        log.info("RSA public key: {}", keyPair.getLeft());
        log.info("RSA private key: {}", keyPair.getRight());
    }

    @Test
    public void testGenerateKeyPairPem() {
        Pair<String, String> keyPair = RsaUtils.generateKeyPairPem();
        log.info("PEM RSA public key: \n{}", keyPair.getLeft());
        log.info("PEM RSA private key: \n{}", keyPair.getRight());
    }

    @Test
    public void testSign() throws InvalidKeySpecException, SignatureException, InvalidKeyException {
        Pair<String, String> keyPair = RsaUtils.generateKeyPair();
        log.info("RSA public key: {}", keyPair.getLeft());
        log.info("RSA private key: {}", keyPair.getRight());

        String plaintext = "Hello, RSA!";
        log.info("Plaintext: {}", plaintext);

        String sign = RsaUtils.sign(keyPair.getRight(), plaintext);
        log.info("RSA Sign: {}", sign);
    }

    @Test
    public void testVerify() throws InvalidKeySpecException, SignatureException, InvalidKeyException {
        Pair<String, String> keyPair = RsaUtils.generateKeyPair();
        log.info("RSA public key: {}", keyPair.getLeft());
        log.info("RSA private key: {}", keyPair.getRight());

        String plaintext = "Hello, RSA!";
        log.info("Plaintext: {}", plaintext);

        String sign = RsaUtils.sign(keyPair.getRight(), plaintext);
        log.info("RSA Sign: {}", sign);

        boolean verified = RsaUtils.verify(keyPair.getLeft(), sign, plaintext);
        log.info("Verify result: {}", verified);
        Assertions.assertTrue(verified);
    }

    @Test
    public void testSignPem() throws InvalidKeySpecException, SignatureException, InvalidKeyException {
        Pair<String, String> keyPair = RsaUtils.generateKeyPairPem();
        log.info("PEM RSA public key: \n{}", keyPair.getLeft());
        log.info("PEM RSA private key: \n{}", keyPair.getRight());

        String plaintext = "Hello, RSA!";
        log.info("Plaintext: {}", plaintext);

        String sign = RsaUtils.sign(keyPair.getRight(), plaintext);
        log.info("RSA Sign: {}", sign);
    }

    @Test
    public void testVerifyPem() throws InvalidKeySpecException, SignatureException, InvalidKeyException {
        Pair<String, String> keyPair = RsaUtils.generateKeyPairPem();
        log.info("PEM RSA public key: \n{}", keyPair.getLeft());
        log.info("PEM RSA private key: \n{}", keyPair.getRight());

        String plaintext = "Hello, RSA!";
        log.info("Plaintext: {}", plaintext);

        String sign = RsaUtils.sign(keyPair.getRight(), plaintext);
        log.info("RSA Sign: {}", sign);

        boolean verified = RsaUtils.verify(keyPair.getLeft(), sign, plaintext);
        log.info("Verify result: {}", verified);
        Assertions.assertTrue(verified);
    }

    @Test
    public void testEncrypt() throws IllegalBlockSizeException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        Pair<String, String> keyPair = RsaUtils.generateKeyPair();
        log.info("RSA public key: {}", keyPair.getLeft());
        log.info("RSA private key: {}", keyPair.getRight());

        String plaintext = "Hello, RSA!";
        log.info("Plaintext: {}", plaintext);

        String ciphertext = RsaUtils.encrypt(keyPair.getLeft(), plaintext);
        log.info("Ciphertext: {}", ciphertext);
    }

    @Test
    public void testDecrypt() throws IllegalBlockSizeException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        Pair<String, String> keyPair = RsaUtils.generateKeyPair();
        log.info("RSA public key: {}", keyPair.getLeft());
        log.info("RSA private key: {}", keyPair.getRight());

        String plaintext = "Hello, RSA!";
        log.info("Plaintext: {}", plaintext);

        String ciphertext = RsaUtils.encrypt(keyPair.getLeft(), plaintext);
        log.info("Ciphertext: {}", ciphertext);

        String generatedPlaintext = RsaUtils.decrypt(keyPair.getRight(), ciphertext);
        log.info("Generated plaintext: {}", generatedPlaintext);
        Assertions.assertEquals(plaintext, generatedPlaintext);
    }

    @Test
    public void testEncryptPem() throws IllegalBlockSizeException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        Pair<String, String> keyPair = RsaUtils.generateKeyPairPem();
        log.info("PEM RSA public key: \n{}", keyPair.getLeft());
        log.info("PEM RSA private key: \n{}", keyPair.getRight());

        String plaintext = "Hello, RSA!";
        log.info("Plaintext: {}", plaintext);

        String ciphertext = RsaUtils.encrypt(keyPair.getLeft(), plaintext);
        log.info("Ciphertext: {}", ciphertext);
    }

    @Test
    public void testDecryptPem() throws IllegalBlockSizeException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        Pair<String, String> keyPair = RsaUtils.generateKeyPairPem();
        log.info("PEM RSA public key: \n{}", keyPair.getLeft());
        log.info("PEM RSA private key: \n{}", keyPair.getRight());

        String plaintext = "Hello, RSA!";
        log.info("Plaintext: {}", plaintext);

        String ciphertext = RsaUtils.encrypt(keyPair.getLeft(), plaintext);
        log.info("Ciphertext: {}", ciphertext);

        String generatedPlaintext = RsaUtils.decrypt(keyPair.getRight(), ciphertext);
        log.info("Generated plaintext: {}", generatedPlaintext);
        Assertions.assertEquals(plaintext, generatedPlaintext);
    }

    @Test
    public void testCheckKey() {
        Pair<String, String> keyPair = RsaUtils.generateKeyPair();
        log.info("RSA public key: {}", keyPair.getLeft());
        log.info("RSA private key: {}", keyPair.getRight());

        boolean isPublic = RsaUtils.isPublicKey(keyPair.getLeft());
        boolean isPrivate = RsaUtils.isPrivateKey(keyPair.getRight());
        log.info("isPublic: {}", isPublic);
        log.info("isPrivate: {}", isPrivate);
        Assertions.assertTrue(isPublic);
        Assertions.assertTrue(isPrivate);
    }

    @Test
    public void testConvertBase64ToPem() {
        Pair<String, String> keyPair = RsaUtils.generateKeyPair();
        log.info("RSA public key: {}", keyPair.getLeft());
        log.info("RSA private key: {}", keyPair.getRight());

        String publicKeyPem = RsaUtils.convertBase64ToPem(keyPair.getLeft());
        String privateKeyPem = RsaUtils.convertBase64ToPem(keyPair.getRight());
        log.info("PEM public key: \n{}", publicKeyPem);
        log.info("PEM private key: \n{}", privateKeyPem);
    }

    @Test
    public void testConvertPemToBase64() {
        Pair<String, String> keyPair = RsaUtils.generateKeyPairPem();
        log.info("PEM RSA public key: \n{}", keyPair.getLeft());
        log.info("PEM RSA private key: \n{}", keyPair.getRight());

        String publicKey = RsaUtils.convertPemToBase64(keyPair.getLeft());
        String privateKey = RsaUtils.convertPemToBase64(keyPair.getRight());
        log.info("RSA public key: {}", publicKey);
        log.info("RSA private key: {}", privateKey);
    }

    @Test
    public void testDataSize() {
        String plaintext = "Hello, RSA!";
        log.info("Plaintext: {}", plaintext);
        int length = plaintext.getBytes().length;
        log.info("Text bytes length: {}", length);
    }

}
