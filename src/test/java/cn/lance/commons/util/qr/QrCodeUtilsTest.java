package cn.lance.commons.util.qr;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Base64;

@Slf4j
@SuppressWarnings("LoggingSimilarMessage")
public class QrCodeUtilsTest {

    @Test
    public void testEncode() throws WriterException {
        String content = "Hello, QR Code!";
        log.info("QR Code content: {}", content);

        String imageBase64 = QrCodeUtils.encode(content);
        log.info("QR Code image base64: {}", imageBase64);
    }

    @Test
    public void testDecodeBase64() throws WriterException, NotFoundException, IOException {
        String content = "Hello, QR Code!";
        log.info("QR Code content: {}", content);

        String imageBase64 = QrCodeUtils.encode(content);
        log.info("QR Code image base64: {}", imageBase64);

        String decodedText = QrCodeUtils.decode(imageBase64);
        log.info("QR Code base64 decoded text: {}", decodedText);
        Assertions.assertEquals(content, decodedText);
    }

    @Test
    public void testDecodeBytes() throws WriterException, NotFoundException, IOException {
        String content = "Hello, QR Code!";
        log.info("QR Code content: {}", content);

        String imageBase64 = QrCodeUtils.encode(content);
        log.info("QR Code image base64: {}", imageBase64);

        byte[] imageBytes = Base64.getDecoder().decode(imageBase64);
        String decodedText = QrCodeUtils.decode(imageBytes);
        log.info("QR Code bytes decoded text: {}", decodedText);
        Assertions.assertEquals(content, decodedText);
    }

}
