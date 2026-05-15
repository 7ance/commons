package cn.lance.commons.util.crypto;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

@Slf4j
@SuppressWarnings("LoggingSimilarMessage")
public class EdDsaUtilsTest {

    @Test
    public void testGenerateKeyPair() {
        Pair<String, String> keyPair = EdDsaUtils.generateKeyPair();
        log.info("EdDSA public key: {}", keyPair.getLeft());
        log.info("EdDSA private key: {}", keyPair.getRight());
    }

    @Test
    public void testGenerateKeyPairPem() {
        Pair<String, String> keyPair = EdDsaUtils.generateKeyPairPem();
        log.info("PEM EdDSA public key: \n{}", keyPair.getLeft());
        log.info("PEM EdDSA private key: \n{}", keyPair.getRight());
    }

    @Test
    public void testSign() throws InvalidKeySpecException, SignatureException, InvalidKeyException {
        Pair<String, String> keyPair = EdDsaUtils.generateKeyPair();
        log.info("EdDSA public key: {}", keyPair.getLeft());
        log.info("EdDSA private key: {}", keyPair.getRight());

        String plaintext = "Hello, EdDSA!";
        log.info("Plaintext: {}", plaintext);

        String sign = EdDsaUtils.sign(keyPair.getRight(), plaintext);
        log.info("EdDSA Sign: {}", sign);
    }

    @Test
    public void testVerify() throws InvalidKeySpecException, SignatureException, InvalidKeyException {
        Pair<String, String> keyPair = EdDsaUtils.generateKeyPair();
        log.info("EdDSA public key: {}", keyPair.getLeft());
        log.info("EdDSA private key: {}", keyPair.getRight());

        String plaintext = "Hello, EdDSA!";
        log.info("Plaintext: {}", plaintext);

        String sign = EdDsaUtils.sign(keyPair.getRight(), plaintext);
        log.info("EdDSA Sign: {}", sign);

        boolean verified = EdDsaUtils.verify(keyPair.getLeft(), sign, plaintext);
        log.info("Verify result: {}", verified);
        Assertions.assertTrue(verified);
    }

    @Test
    public void testSignPem() throws InvalidKeySpecException, SignatureException, InvalidKeyException {
        Pair<String, String> keyPair = EdDsaUtils.generateKeyPairPem();
        log.info("PEM EdDSA public key: \n{}", keyPair.getLeft());
        log.info("PEM EdDSA private key: \n{}", keyPair.getRight());

        String plaintext = "Hello, EdDSA!";
        log.info("Plaintext: {}", plaintext);

        String sign = EdDsaUtils.sign(keyPair.getRight(), plaintext);
        log.info("EdDSA Sign: {}", sign);
    }

    @Test
    public void testVerifyPem() throws InvalidKeySpecException, SignatureException, InvalidKeyException {
        Pair<String, String> keyPair = EdDsaUtils.generateKeyPairPem();
        log.info("PEM EdDSA public key: \n{}", keyPair.getLeft());
        log.info("PEM EdDSA private key: \n{}", keyPair.getRight());

        String plaintext = "Hello, EdDSA!";
        log.info("Plaintext: {}", plaintext);

        String sign = EdDsaUtils.sign(keyPair.getRight(), plaintext);
        log.info("EdDSA Sign: {}", sign);

        boolean verified = EdDsaUtils.verify(keyPair.getLeft(), sign, plaintext);
        log.info("Verify result: {}", verified);
        Assertions.assertTrue(verified);
    }

    @Test
    public void testCheckKey() {
        Pair<String, String> keyPair = EdDsaUtils.generateKeyPair();
        log.info("EdDSA public key: {}", keyPair.getLeft());
        log.info("EdDSA private key: {}", keyPair.getRight());

        boolean isPublic = EdDsaUtils.isPublicKey(keyPair.getLeft());
        boolean isPrivate = EdDsaUtils.isPrivateKey(keyPair.getRight());
        log.info("isPublic: {}", isPublic);
        log.info("isPrivate: {}", isPrivate);
        Assertions.assertTrue(isPublic);
        Assertions.assertTrue(isPrivate);
    }

    @Test
    public void testConvertBase64ToPem() {
        Pair<String, String> keyPair = EdDsaUtils.generateKeyPair();
        log.info("EdDSA public key: {}", keyPair.getLeft());
        log.info("EdDSA private key: {}", keyPair.getRight());

        String publicKeyPem = EdDsaUtils.convertBase64ToPem(keyPair.getLeft());
        String privateKeyPem = EdDsaUtils.convertBase64ToPem(keyPair.getRight());
        log.info("PEM public key: \n{}", publicKeyPem);
        log.info("PEM private key: \n{}", privateKeyPem);
    }

    @Test
    public void testConvertPemToBase64() {
        Pair<String, String> keyPair = EdDsaUtils.generateKeyPairPem();
        log.info("PEM EdDSA public key: \n{}", keyPair.getLeft());
        log.info("PEM EdDSA private key: \n{}", keyPair.getRight());

        String publicKey = EdDsaUtils.convertPemToBase64(keyPair.getLeft());
        String privateKey = EdDsaUtils.convertPemToBase64(keyPair.getRight());
        log.info("EdDSA public key: {}", publicKey);
        log.info("EdDSA private key: {}", privateKey);
    }

    @Test
    public void testDataSize() {
        String plaintext = "Hello, EdDSA!";
        log.info("Plaintext: {}", plaintext);
        int length = plaintext.getBytes(StandardCharsets.UTF_8).length;
        log.info("Text bytes length: {}", length);
    }

    @Test
    public void testOpenSslGeneratedKeyPair() throws InvalidKeySpecException, SignatureException, InvalidKeyException {
        // test-only throwaway key pair, not associated with any real system
        String publicKey = "MCowBQYDK2VwAyEAms/tLnGF3o+WJ0S0L5bqeS+HYI1qMbvgkzdZsoR/dbk=";
        String privateKey = "MC4CAQAwBQYDK2VwBCIEICQ0HGN+I+kUOv7dAJok13qFYIrDE565DIPnoyIIrXzM";

        String plaintext = "Hello, EdDSA!";
        log.info("Plaintext: {}", plaintext);

        String sign = EdDsaUtils.sign(privateKey, plaintext);
        log.info("EdDSA Sign: {}", sign);

        boolean verified = EdDsaUtils.verify(publicKey, sign, plaintext);
        log.info("Verify result: {}", verified);
        Assertions.assertTrue(verified);
    }

    @Test
    public void testConvertBase64ToPemInvalidKey() {
        Assertions.assertThrows(RuntimeException.class,
                () -> EdDsaUtils.convertBase64ToPem("dGVzdA=="));
    }

    @Test
    public void testConvertPemToBase64NonPem() {
        String result = EdDsaUtils.convertPemToBase64("some-plain-base64-string");
        Assertions.assertEquals("some-plain-base64-string", result);
    }

    @Test
    public void testIsPublicKeyWithInvalidKey() {
        Assertions.assertFalse(EdDsaUtils.isPublicKey("dGVzdA=="));
    }

    @Test
    public void testIsPrivateKeyWithInvalidKey() {
        Assertions.assertFalse(EdDsaUtils.isPrivateKey("dGVzdA=="));
    }

    @Test
    public void testIsPublicKeyWithPrivateKey() {
        Pair<String, String> keyPair = EdDsaUtils.generateKeyPair();
        Assertions.assertFalse(EdDsaUtils.isPublicKey(keyPair.getRight()));
    }

    @Test
    public void testIsPrivateKeyWithPublicKey() {
        Pair<String, String> keyPair = EdDsaUtils.generateKeyPair();
        Assertions.assertFalse(EdDsaUtils.isPrivateKey(keyPair.getLeft()));
    }

    @Test
    public void testVerifyWithWrongPublicKey() throws InvalidKeySpecException, InvalidKeyException, SignatureException {
        Pair<String, String> keyPair1 = EdDsaUtils.generateKeyPair();
        Pair<String, String> keyPair2 = EdDsaUtils.generateKeyPair();

        String plaintext = "Hello, EdDSA!";
        String sign = EdDsaUtils.sign(keyPair1.getRight(), plaintext);

        boolean result = EdDsaUtils.verify(keyPair2.getLeft(), sign, plaintext);
        Assertions.assertFalse(result);
    }

}
