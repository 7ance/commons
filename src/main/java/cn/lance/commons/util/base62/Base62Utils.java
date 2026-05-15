package cn.lance.commons.util.base62;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Base62 编解码工具 (0-9, A-Z, a-z)
 *
 * <p>适合生成 URL 友好的短标识符。</p>
 */
public class Base62Utils {

    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final BigInteger BASE = BigInteger.valueOf(62);

    private Base62Utils() {
    }

    /**
     * 编码字节数组为 Base62 字符串
     *
     * @param data 原始字节
     * @return Base62 字符串
     */
    public static String encode(byte[] data) {
        Objects.requireNonNull(data, "data must not be null");

        if (data.length == 0) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        BigInteger value = new BigInteger(1, data);
        while (value.compareTo(BigInteger.ZERO) > 0) {
            BigInteger[] div = value.divideAndRemainder(BASE);
            result.append(ALPHABET.charAt(div[1].intValue()));
            value = div[0];
        }

        for (byte b : data) {
            if (b == 0) {
                result.append(ALPHABET.charAt(0));
            } else {
                break;
            }
        }

        return result.reverse().toString();
    }

    /**
     * 编码字符串为 Base62 字符串
     *
     * @param text 原始字符串
     * @return Base62 字符串
     */
    public static String encode(String text) {
        Objects.requireNonNull(text, "text must not be null");
        return encode(text.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 解码 Base62 字符串为字节数组
     *
     * @param base62 Base62 字符串
     * @return 原始字节
     * @throws IllegalArgumentException 包含非法字符
     */
    public static byte[] decode(String base62) {
        Objects.requireNonNull(base62, "base62 must not be null");

        if (base62.isEmpty()) {
            return new byte[0];
        }

        for (char c : base62.toCharArray()) {
            if (ALPHABET.indexOf(c) == -1) {
                throw new IllegalArgumentException("Invalid Base62 character: " + c);
            }
        }

        int leadingZeros = 0;
        for (char c : base62.toCharArray()) {
            if (c == ALPHABET.charAt(0)) {
                leadingZeros++;
            } else {
                break;
            }
        }

        BigInteger value = BigInteger.ZERO;
        for (char c : base62.toCharArray()) {
            value = value.multiply(BASE).add(BigInteger.valueOf(ALPHABET.indexOf(c)));
        }

        byte[] bytes = value.toByteArray();
        if (bytes[0] == 0 && bytes.length > 1) {
            byte[] trimmed = new byte[bytes.length - 1];
            System.arraycopy(bytes, 1, trimmed, 0, trimmed.length);
            bytes = trimmed;
        }

        if (leadingZeros > 0) {
            int extraZeros = value.signum() == 0 ? leadingZeros - 1 : leadingZeros;
            if (extraZeros > 0) {
                byte[] result = new byte[bytes.length + extraZeros];
                System.arraycopy(bytes, 0, result, extraZeros, bytes.length);
                return result;
            }
        }

        return bytes;
    }

    /**
     * 解码 Base62 字符串为原始字符串
     *
     * @param base62 Base62 字符串
     * @return 原始字符串
     */
    public static String decodeToString(String base62) {
        return new String(decode(base62), StandardCharsets.UTF_8);
    }

}
