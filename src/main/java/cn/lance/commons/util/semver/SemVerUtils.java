package cn.lance.commons.util.semver;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 语义化版本号工具
 *
 * <p>遵循 <a href="https://semver.org/lang/zh-CN/">Semantic Versioning 2.0.0</a> 规范。</p>
 */
public class SemVerUtils {

    private static final Pattern PATTERN = Pattern.compile(
            "^(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)" +
            "(?:-((?:0|[1-9]\\d*|\\d*[a-zA-Z-][0-9a-zA-Z-]*)(?:\\.(?:0|[1-9]\\d*|\\d*[a-zA-Z-][0-9a-zA-Z-]*))*))?" +
            "(?:\\+([0-9a-zA-Z-]+(?:\\.[0-9a-zA-Z-]+)*))?$");

    private SemVerUtils() {
    }

    /**
     * 解析语义化版本号字符串
     *
     * @param version 版本号字符串
     * @return SemVersion 对象
     * @throws IllegalArgumentException 版本号不合法
     */
    public static SemVersion parse(String version) {
        Objects.requireNonNull(version, "version must not be null");

        Matcher matcher = PATTERN.matcher(version);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid semver: " + version);
        }

        return new SemVersion(
                Integer.parseInt(matcher.group(1)),
                Integer.parseInt(matcher.group(2)),
                Integer.parseInt(matcher.group(3)),
                matcher.group(4),
                matcher.group(5)
        );
    }

    /**
     * 判断版本号字符串是否合法
     *
     * @param version 版本号字符串
     * @return true=合法 false=不合法
     */
    public static boolean isValid(String version) {
        if (version == null) {
            return false;
        }
        return PATTERN.matcher(version).matches();
    }

    /**
     * 比较两个版本号
     *
     * @param v1 版本号1
     * @param v2 版本号2
     * @return 负数(v1<v2) 0(相等) 正数(v1>v2)
     */
    public static int compare(String v1, String v2) {
        return parse(v1).compareTo(parse(v2));
    }

}
