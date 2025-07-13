package cn.lance.commons.util.jwt;

import cn.lance.commons.util.crypto.HmacUtils;
import cn.lance.commons.util.crypto.RsaUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaimsBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.UUID;

@Slf4j
@SuppressWarnings("LoggingSimilarMessage")
public class JwtUtilsTest {

    @Test
    public void testGenerateHs256Key() throws NoSuchAlgorithmException {
        String algoSha256 = "HmacSHA256";
        String keySha256 = HmacUtils.generateKey(algoSha256, 256);
        log.info("SHA256 key: {}", keySha256);
    }

    @Test
    public void testSignHs256() throws NoSuchAlgorithmException {
        String algoSha256 = "HmacSHA256";
        String keySha256 = HmacUtils.generateKey(algoSha256, 256);
        log.info("SHA256 key: {}", keySha256);

        Claims claims = new DefaultClaimsBuilder()
                .add("str", "foobar")
                .add("number", 30)
                .add("bool", true)
                .id(UUID.randomUUID().toString())
                .subject("foo")
                .issuer("Bar")
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + 1000 * 60 * 60 * 24))
                .build();

        String token = JwtUtils.signHs256(keySha256, claims);
        log.info("Token: {}", token);
    }

    @Test
    public void testParseAndVerifyHs256() throws NoSuchAlgorithmException {
        String algoSha256 = "HmacSHA256";
        String keySha256 = HmacUtils.generateKey(algoSha256, 256);
        log.info("SHA256 key: {}", keySha256);

        Claims claims = new DefaultClaimsBuilder()
                .add("str", "foobar")
                .add("number", 30)
                .add("bool", true)
                .id(UUID.randomUUID().toString())
                .subject("foo")
                .issuer("Bar")
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + 1000 * 60 * 60 * 24))
                .build();

        String token = JwtUtils.signHs256(keySha256, claims);
        log.info("Token: {}", token);

        Claims verifiedClaims = JwtUtils.parseAndVerifyHs256(keySha256, token);
        log.info("Verified claims: {}", verifiedClaims);
    }

    @Test
    public void testGenerateRs256Key() {
        Pair<String, String> keyPair = RsaUtils.generateKeyPair();
        log.info("RSA public key: {}", keyPair.getLeft());
        log.info("RSA private key: {}", keyPair.getRight());
    }

    @Test
    public void testSignRs256() throws InvalidKeySpecException {
        Pair<String, String> keyPair = RsaUtils.generateKeyPair();
        log.info("RSA public key: {}", keyPair.getLeft());
        log.info("RSA private key: {}", keyPair.getRight());

        Claims claims = new DefaultClaimsBuilder()
                .add("str", "foobar")
                .add("number", 30)
                .add("bool", true)
                .id(UUID.randomUUID().toString())
                .subject("foo")
                .issuer("Bar")
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + 1000 * 60 * 60 * 24))
                .build();

        String token = JwtUtils.signRs256(keyPair.getRight(), claims);
        log.info("Token: {}", token);
    }

    @Test
    public void testParseAndVerifyRs256() throws InvalidKeySpecException {
        Pair<String, String> keyPair = RsaUtils.generateKeyPair();
        log.info("RSA public key: {}", keyPair.getLeft());
        log.info("RSA private key: {}", keyPair.getRight());

        Claims claims = new DefaultClaimsBuilder()
                .add("str", "foobar")
                .add("number", 30)
                .add("bool", true)
                .id(UUID.randomUUID().toString())
                .subject("foo")
                .issuer("Bar")
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + 1000 * 60 * 60 * 24))
                .build();

        String token = JwtUtils.signRs256(keyPair.getRight(), claims);
        log.info("Token: {}", token);

        Claims verifiedClaims = JwtUtils.parseAndVerifyRs256(keyPair.getLeft(), token);
        log.info("Verified claims: {}", verifiedClaims);
    }

}
