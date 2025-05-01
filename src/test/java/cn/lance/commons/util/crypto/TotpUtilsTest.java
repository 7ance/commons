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
    }

}
