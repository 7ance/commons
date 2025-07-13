package cn.lance.commons.util.crypto;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPPublicKeyRing;
import org.bouncycastle.openpgp.PGPSecretKeyRing;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;


@Slf4j
@SuppressWarnings("LoggingSimilarMessage")
public class PgpUtilsTest {

    @Test
    public void testGenerateKeyPair() throws Exception {
        String userId = "Foo";
        String email = "bar@gmail.com";
        String passphrase = "700101";

        Pair<String, String> keyPair = PgpUtils.generateKeyPair(userId, email, passphrase);
        log.info("PGP public key: \n{}", keyPair.getLeft());
        log.info("PGP secret key: \n{}", keyPair.getRight());
    }

    @Test
    public void testExtract() throws Exception {
        String userId = "Foo";
        String email = "bar@gmail.com";
        String passphrase = "700101";

        Pair<String, String> keyPair = PgpUtils.generateKeyPair(userId, email, passphrase);
        log.info("PGP public key: \n{}", keyPair.getLeft());
        log.info("PGP secret key: \n{}", keyPair.getRight());

        String publicKey = PgpUtils.extract(keyPair.getRight());
        log.info("PGP public key: \n{}", publicKey);

        boolean equals = keyPair.getLeft().equals(publicKey);
        log.info("Comparison result: {}", equals);
        Assertions.assertTrue(equals);
    }

    @Test
    public void testReadKeyPair()
            throws PGPException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IOException {
        String userId = "Foo";
        String email = "bar@gmail.com";
        String passphrase = "700101";

        Pair<String, String> keyPair = PgpUtils.generateKeyPair(userId, email, passphrase);
        log.info("PGP public key: \n{}", keyPair.getLeft());
        log.info("PGP secret key: \n{}", keyPair.getRight());

        PGPPublicKeyRing pgpPublicKey = PgpUtils.readPublicKey(keyPair.getLeft());
        PGPSecretKeyRing pgpSecretKey = PgpUtils.readSecretKey(keyPair.getRight());
        Assertions.assertNotNull(pgpPublicKey);
        Assertions.assertNotNull(pgpSecretKey);

        log.info("PGP public keyring size: {}", pgpPublicKey.size());
        log.info("PGP secret keyring size: {}", pgpSecretKey.size());
    }

    @Test
    public void testSign()
            throws PGPException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IOException {
        String userId = "Foo";
        String email = "bar@gmail.com";
        String passphrase = "700101";

        Pair<String, String> keyPair = PgpUtils.generateKeyPair(userId, email, passphrase);
        log.info("PGP public key: \n{}", keyPair.getLeft());
        log.info("PGP secret key: \n{}", keyPair.getRight());

        String plaintext = "Hello, OpenPGP!";
        log.info("Plaintext: {}", plaintext);

        String signedMessage = PgpUtils.sign(keyPair.getRight(), passphrase, plaintext);
        log.info("Signed message: \n{}", signedMessage);
    }

    @Test
    public void testVerify()
            throws PGPException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IOException {
        String userId = "Foo";
        String email = "bar@gmail.com";
        String passphrase = "700101";

        Pair<String, String> keyPair = PgpUtils.generateKeyPair(userId, email, passphrase);
        log.info("PGP public key: \n{}", keyPair.getLeft());
        log.info("PGP secret key: \n{}", keyPair.getRight());

        String plaintext = "Hello, OpenPGP!";
        log.info("Plaintext: {}", plaintext);

        String signedMessage = PgpUtils.sign(keyPair.getRight(), passphrase, plaintext);
        log.info("Signed message: \n{}", signedMessage);

        boolean verified = PgpUtils.verify(keyPair.getLeft(), signedMessage);
        log.info("Verify result: {}", verified);
        Assertions.assertTrue(verified);

        String decodedText = PgpUtils.decodeMessage(signedMessage);
        log.info("Decoded text: {}", decodedText);

        boolean equals = plaintext.equals(decodedText);
        log.info("Text comparison result: {}", equals);
        Assertions.assertTrue(equals);
    }

    @Test
    public void testEncrypt()
            throws PGPException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IOException {
        String userId = "Foo";
        String email = "bar@gmail.com";
        String passphrase = "700101";

        Pair<String, String> keyPair = PgpUtils.generateKeyPair(userId, email, passphrase);
        log.info("PGP public key: \n{}", keyPair.getLeft());
        log.info("PGP secret key: \n{}", keyPair.getRight());

        String plaintext = "Hello, OpenPGP!";
        log.info("Plaintext: {}", plaintext);

        String encryptedMessage = PgpUtils.encrypt(keyPair.getLeft(), plaintext);
        log.info("Encrypted message: \n{}", encryptedMessage);
    }

    @Test
    public void testDecrypt()
            throws PGPException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IOException {
        String userId = "Foo";
        String email = "bar@gmail.com";
        String passphrase = "700101";

        Pair<String, String> keyPair = PgpUtils.generateKeyPair(userId, email, passphrase);
        log.info("PGP public key: \n{}", keyPair.getLeft());
        log.info("PGP secret key: \n{}", keyPair.getRight());

        String plaintext = "Hello, OpenPGP!";
        log.info("Plaintext: {}", plaintext);

        String encryptedMessage = PgpUtils.encrypt(keyPair.getLeft(), plaintext);
        log.info("Encrypted message: \n{}", encryptedMessage);

        String decryptedText = PgpUtils.decrypt(keyPair.getRight(), passphrase, encryptedMessage);
        log.info("Decrypted text: {}", decryptedText);
        Assertions.assertEquals(plaintext, decryptedText);
    }

    @Test
    public void testEncryptAndSign()
            throws PGPException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IOException {
        String userIdAlpha = "Alpha";
        String emailAlpha = "alpha@gmail.com";
        String passphraseAlpha = "700101";

        Pair<String, String> keyPairAlpha = PgpUtils.generateKeyPair(userIdAlpha, emailAlpha, passphraseAlpha);
        log.info("PGP public key Alpha: \n{}", keyPairAlpha.getLeft());
        log.info("PGP secret key Alpha: \n{}", keyPairAlpha.getRight());

        String userIdBeta = "Beta";
        String emailBeta = "beta@gmail.com";
        String passphraseBeta = "700101";

        Pair<String, String> keyPairBeta = PgpUtils.generateKeyPair(userIdBeta, emailBeta, passphraseBeta);
        log.info("PGP public key Beta: \n{}", keyPairBeta.getLeft());
        log.info("PGP secret key Beta: \n{}", keyPairBeta.getRight());

        String plaintext = "Hello, OpenPGP!";
        log.info("Plaintext: {}", plaintext);

        // Alpha -> Beta
        String signedMessage = PgpUtils.encryptAndSign(
                keyPairBeta.getLeft(),
                keyPairAlpha.getRight(),
                passphraseAlpha,
                plaintext
        );
        log.info("Signed Message: \n{}", signedMessage);
    }

    @Test
    public void testDecryptAndVerify()
            throws PGPException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IOException {
        String userIdAlpha = "Alpha";
        String emailAlpha = "alpha@gmail.com";
        String passphraseAlpha = "700101";

        Pair<String, String> keyPairAlpha = PgpUtils.generateKeyPair(userIdAlpha, emailAlpha, passphraseAlpha);
        log.info("PGP public key Alpha: \n{}", keyPairAlpha.getLeft());
        log.info("PGP secret key Alpha: \n{}", keyPairAlpha.getRight());

        String userIdBeta = "Beta";
        String emailBeta = "beta@gmail.com";
        String passphraseBeta = "700101";

        Pair<String, String> keyPairBeta = PgpUtils.generateKeyPair(userIdBeta, emailBeta, passphraseBeta);
        log.info("PGP public key Beta: \n{}", keyPairBeta.getLeft());
        log.info("PGP secret key Beta: \n{}", keyPairBeta.getRight());

        String plaintext = "Hello, OpenPGP!";
        log.info("Plaintext: {}", plaintext);

        // Alpha -> Beta
        String signedMessage = PgpUtils.encryptAndSign(
                keyPairBeta.getLeft(),
                keyPairAlpha.getRight(),
                passphraseAlpha,
                plaintext);
        log.info("Signed Message: \n{}", signedMessage);

        String decryptedText = PgpUtils.decryptAndVerify(
                keyPairAlpha.getLeft(),
                keyPairBeta.getRight(),
                passphraseBeta,
                signedMessage
        );
        log.info("Decrypted text: {}", decryptedText);

        Assertions.assertEquals(plaintext, decryptedText);
    }

}
