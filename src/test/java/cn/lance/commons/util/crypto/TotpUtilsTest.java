package cn.lance.commons.util.crypto;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@SuppressWarnings("LoggingSimilarMessage")
public class TotpUtilsTest {

    @Test
    public void testGenerate() {
        String issuer = "Foo";
        String accountName = "bar";

        String uri = TotpUtils.generate(issuer, accountName);
        log.info("OTP URI: {}", uri);
    }

    @Test
    public void testGetSecretFromUri() {
        String issuer = "Foo";
        String accountName = "bar";

        String uri = TotpUtils.generate(issuer, accountName);
        log.info("OTP URI: {}", uri);

        String secret = TotpUtils.getSecretFromUri(uri);
        log.info("OTP Secret: {}", secret);
    }

    @Test
    public void testGetSecretFromUriWithoutQuery() {
        Assertions.assertThrows(RuntimeException.class,
                () -> TotpUtils.getSecretFromUri("otpauth://totp/foo"));
    }

    @Test
    public void testGetSecretFromUriWithoutSecret() {
        Assertions.assertThrows(RuntimeException.class,
                () -> TotpUtils.getSecretFromUri("otpauth://totp/foo?issuer=bar&digits=6"));
    }

    @Test
    public void testGenerateCode() {
        String issuer = "Foo";
        String accountName = "bar";

        String uri = TotpUtils.generate(issuer, accountName);
        log.info("OTP URI: {}", uri);

        String secret = TotpUtils.getSecretFromUri(uri);
        log.info("OTP secret: {}", secret);

        String passcode = TotpUtils.generateCode(secret);
        log.info("OTP passcode: {}", passcode);
    }

    @Test
    public void testVerify() {
        String issuer = "Foo";
        String accountName = "bar";

        String uri = TotpUtils.generate(issuer, accountName);
        log.info("OTP URI: {}", uri);

        String secret = TotpUtils.getSecretFromUri(uri);
        log.info("OTP secret: {}", secret);

        String passcode = TotpUtils.generateCode(secret);
        log.info("OTP passcode: {}", passcode);

        boolean verified = TotpUtils.verify(secret, passcode);
        log.info("OTP verified: {}", verified);
        Assertions.assertTrue(verified);

        boolean verifiedInterval = TotpUtils.verify(secret, passcode, 0);
        log.info("OTP verified with interval: {}", verifiedInterval);
        Assertions.assertTrue(verifiedInterval);
    }

    @Test
    public void testVerifyWithWrongPasscode() {
        String issuer = "Foo";
        String accountName = "bar";

        String uri = TotpUtils.generate(issuer, accountName);
        String secret = TotpUtils.getSecretFromUri(uri);

        boolean result = TotpUtils.verify(secret, "000000");
        Assertions.assertFalse(result);
    }

    @Test
    public void testVerifyWithOverlongPasscode() {
        String issuer = "Foo";
        String accountName = "bar";

        String uri = TotpUtils.generate(issuer, accountName);
        String secret = TotpUtils.getSecretFromUri(uri);

        Assertions.assertThrows(RuntimeException.class,
                () -> TotpUtils.verify(secret, "1234567890"));
    }

    @Test
    public void testVerifyWithLargeInterval() {
        String issuer = "Foo";
        String accountName = "bar";

        String uri = TotpUtils.generate(issuer, accountName);
        String secret = TotpUtils.getSecretFromUri(uri);

        String passcode = TotpUtils.generateCode(secret);
        boolean verified = TotpUtils.verify(secret, passcode, 5);
        Assertions.assertTrue(verified);
    }

}
