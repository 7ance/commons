package cn.lance.commons.util.uuid;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * Universally unique identifier
 */
public class UuidUtils {

    private static final String CHARACTERS = "0123456789abcdefghijklmnopqrstuvwxyz";

    private static final SecureRandom RANDOM = new SecureRandom();

    private UuidUtils() {
    }

    /**
     * 生成UUID
     *
     * @return UUID（不带连字符）
     */
    public static String randomUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 生成UUID
     *
     * @return UUID（带连字符）
     */
    public static String randomUuidWithHyphen() {
        return UUID.randomUUID().toString();
    }

    /**
     * 生成UUID
     *
     * @param length UUID长度
     * @return UUID（不带连字符）
     */
    public static String randomUuid(int length) {
        if (length <= 0) {
            length = 16;
        }

        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            builder.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return builder.toString();
    }

}
