package cn.lance.commons.util.base62;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

@Slf4j
public class Base62UtilsTest {

    @Test
    public void testEncodeDecodeBytes() {
        byte[] original = "Hello, Base62!".getBytes(StandardCharsets.UTF_8);
        String encoded = Base62Utils.encode(original);
        log.info("Encoded: {}", encoded);
        byte[] decoded = Base62Utils.decode(encoded);
        Assertions.assertArrayEquals(original, decoded);
    }

    @Test
    public void testEncodeDecodeString() {
        String original = "Hello, Base62!";
        String encoded = Base62Utils.encode(original);
        log.info("Encoded: {}", encoded);
        String decoded = Base62Utils.decodeToString(encoded);
        Assertions.assertEquals(original, decoded);
    }

    @Test
    public void testEncodeEmpty() {
        String result = Base62Utils.encode(new byte[0]);
        Assertions.assertEquals("", result);
    }

    @Test
    public void testDecodeEmpty() {
        byte[] result = Base62Utils.decode("");
        Assertions.assertEquals(0, result.length);
    }

    @Test
    public void testEncodeDecodeEmptyString() {
        String result = Base62Utils.decodeToString("");
        Assertions.assertEquals("", result);
    }

    @Test
    public void testRoundtripComplexData() {
        byte[] original = new byte[256];
        for (int i = 0; i < 256; i++) {
            original[i] = (byte) i;
        }
        String encoded = Base62Utils.encode(original);
        byte[] decoded = Base62Utils.decode(encoded);
        Assertions.assertArrayEquals(original, decoded);
    }

    @Test
    public void testUniqueness() {
        String a = Base62Utils.encode("Hello");
        String b = Base62Utils.encode("World");
        Assertions.assertNotEquals(a, b);
    }

    @Test
    public void testNullEncodeBytes() {
        Assertions.assertThrows(NullPointerException.class,
                () -> Base62Utils.encode((byte[]) null));
    }

    @Test
    public void testNullEncodeString() {
        Assertions.assertThrows(NullPointerException.class,
                () -> Base62Utils.encode((String) null));
    }

    @Test
    public void testNullDecode() {
        Assertions.assertThrows(NullPointerException.class,
                () -> Base62Utils.decode(null));
    }

    @Test
    public void testLeadingZeroBytes() {
        byte[] original = new byte[]{0, 0, 1, 2, 3};
        String encoded = Base62Utils.encode(original);
        byte[] decoded = Base62Utils.decode(encoded);
        Assertions.assertArrayEquals(original, decoded);
    }

    @Test
    public void testSingleZeroByte() {
        byte[] original = new byte[]{0};
        String encoded = Base62Utils.encode(original);
        byte[] decoded = Base62Utils.decode(encoded);
        Assertions.assertArrayEquals(original, decoded);
    }

    @Test
    public void testBigIntegerSignByteEdgeCase() {
        byte[] original = new byte[]{1, 2, 3, 4, 5};
        String encoded = Base62Utils.encode(original);
        byte[] decoded = Base62Utils.decode(encoded);
        Assertions.assertArrayEquals(original, decoded);
    }

    @Test
    public void testDecodeInvalidCharacter() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> Base62Utils.decode("abc!def"));
    }

}
