package cn.lance.commons.util.semver;

import lombok.Data;

    /**
     * 语义化版本号
     *
     * <p>格式: MAJOR.MINOR.PATCH[-preRelease][+buildMetadata]</p>
     * <p>遵循 <a href="https://semver.org/lang/zh-CN/">Semantic Versioning 2.0.0</a> 规范。</p>
     */
@Data
public class SemVersion implements Comparable<SemVersion> {

    private final int major;
    private final int minor;
    private final int patch;
    private final String preRelease;
    private final String buildMetadata;

    public SemVersion(int major, int minor, int patch) {
        this(major, minor, patch, null, null);
    }

    public SemVersion(int major, int minor, int patch, String preRelease, String buildMetadata) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.preRelease = preRelease;
        this.buildMetadata = buildMetadata;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                .append(major).append('.').append(minor).append('.').append(patch);
        if (preRelease != null) {
            sb.append('-').append(preRelease);
        }
        if (buildMetadata != null) {
            sb.append('+').append(buildMetadata);
        }
        return sb.toString();
    }

    @Override
    public int compareTo(SemVersion other) {
        int cmp = Integer.compare(this.major, other.major);
        if (cmp != 0) return cmp;

        cmp = Integer.compare(this.minor, other.minor);
        if (cmp != 0) return cmp;

        cmp = Integer.compare(this.patch, other.patch);
        if (cmp != 0) return cmp;

        if (this.preRelease == null && other.preRelease == null) return 0;
        if (this.preRelease == null) return 1;
        if (other.preRelease == null) return -1;

        return comparePreRelease(this.preRelease, other.preRelease);
    }

    private static int comparePreRelease(String a, String b) {
        String[] aParts = a.split("\\.");
        String[] bParts = b.split("\\.");
        int minLen = Math.min(aParts.length, bParts.length);

        for (int i = 0; i < minLen; i++) {
            int cmp = comparePreReleasePart(aParts[i], bParts[i]);
            if (cmp != 0) return cmp;
        }

        return Integer.compare(aParts.length, bParts.length);
    }

    private static int comparePreReleasePart(String a, String b) {
        boolean aNumeric = a.matches("\\d+");
        boolean bNumeric = b.matches("\\d+");

        if (aNumeric && bNumeric) {
            return Long.compare(Long.parseLong(a), Long.parseLong(b));
        }
        if (aNumeric) return -1;
        if (bNumeric) return 1;
        return a.compareTo(b);
    }

}
