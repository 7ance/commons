package cn.lance.commons.util.semver;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class SemVerUtilsTest {

    @Test
    public void testParseSimple() {
        SemVersion v = SemVerUtils.parse("1.2.3");
        Assertions.assertEquals(1, v.getMajor());
        Assertions.assertEquals(2, v.getMinor());
        Assertions.assertEquals(3, v.getPatch());
        Assertions.assertNull(v.getPreRelease());
        Assertions.assertNull(v.getBuildMetadata());
    }

    @Test
    public void testParseWithPreRelease() {
        SemVersion v = SemVerUtils.parse("1.2.3-alpha.1");
        Assertions.assertEquals(1, v.getMajor());
        Assertions.assertEquals(2, v.getMinor());
        Assertions.assertEquals(3, v.getPatch());
        Assertions.assertEquals("alpha.1", v.getPreRelease());
        Assertions.assertNull(v.getBuildMetadata());
    }

    @Test
    public void testParseWithBuildMetadata() {
        SemVersion v = SemVerUtils.parse("1.2.3+build.123");
        Assertions.assertEquals(1, v.getMajor());
        Assertions.assertEquals(2, v.getMinor());
        Assertions.assertEquals(3, v.getPatch());
        Assertions.assertNull(v.getPreRelease());
        Assertions.assertEquals("build.123", v.getBuildMetadata());
    }

    @Test
    public void testParseWithPreReleaseAndBuild() {
        SemVersion v = SemVerUtils.parse("1.2.3-beta+build.456");
        Assertions.assertEquals(1, v.getMajor());
        Assertions.assertEquals(2, v.getMinor());
        Assertions.assertEquals(3, v.getPatch());
        Assertions.assertEquals("beta", v.getPreRelease());
        Assertions.assertEquals("build.456", v.getBuildMetadata());
    }

    @Test
    public void testParseZeroVersions() {
        SemVersion v = SemVerUtils.parse("0.0.0");
        Assertions.assertEquals(0, v.getMajor());
        Assertions.assertEquals(0, v.getMinor());
        Assertions.assertEquals(0, v.getPatch());
    }

    @Test
    public void testParseLargeVersion() {
        SemVersion v = SemVerUtils.parse("999.888.777");
        Assertions.assertEquals(999, v.getMajor());
        Assertions.assertEquals(888, v.getMinor());
        Assertions.assertEquals(777, v.getPatch());
    }

    @Test
    public void testIsValidTrue() {
        Assertions.assertTrue(SemVerUtils.isValid("1.2.3"));
        Assertions.assertTrue(SemVerUtils.isValid("0.0.0"));
        Assertions.assertTrue(SemVerUtils.isValid("10.20.30-alpha+build"));
    }

    @Test
    public void testIsValidFalse() {
        Assertions.assertFalse(SemVerUtils.isValid(null));
        Assertions.assertFalse(SemVerUtils.isValid(""));
        Assertions.assertFalse(SemVerUtils.isValid("1.2"));
        Assertions.assertFalse(SemVerUtils.isValid("v1.2.3"));
        Assertions.assertFalse(SemVerUtils.isValid("1.2.3.4"));
    }

    @Test
    public void testParseInvalid() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> SemVerUtils.parse("invalid"));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> SemVerUtils.parse("1.2"));
    }

    @Test
    public void testParseNull() {
        Assertions.assertThrows(NullPointerException.class,
                () -> SemVerUtils.parse(null));
    }

    @Test
    public void testCompareMajor() {
        Assertions.assertTrue(SemVerUtils.compare("2.0.0", "1.9.9") > 0);
        Assertions.assertTrue(SemVerUtils.compare("1.0.0", "2.0.0") < 0);
    }

    @Test
    public void testCompareMinor() {
        Assertions.assertTrue(SemVerUtils.compare("1.3.0", "1.2.0") > 0);
        Assertions.assertTrue(SemVerUtils.compare("1.0.0", "1.9.0") < 0);
    }

    @Test
    public void testComparePatch() {
        Assertions.assertTrue(SemVerUtils.compare("1.0.5", "1.0.4") > 0);
        Assertions.assertTrue(SemVerUtils.compare("1.0.1", "1.0.9") < 0);
    }

    @Test
    public void testCompareEqual() {
        Assertions.assertEquals(0, SemVerUtils.compare("1.2.3", "1.2.3"));
    }

    @Test
    public void testComparePreRelease() {
        Assertions.assertTrue(SemVerUtils.compare("1.0.0", "1.0.0-alpha") > 0);
        Assertions.assertTrue(SemVerUtils.compare("1.0.0-beta", "1.0.0-alpha") > 0);
    }

    @Test
    public void testComparePreReleaseDotNumeric() {
        Assertions.assertTrue(SemVerUtils.compare("1.0.0-alpha.10", "1.0.0-alpha.2") > 0);
        Assertions.assertTrue(SemVerUtils.compare("1.0.0-alpha.2", "1.0.0-alpha.10") < 0);
    }

    @Test
    public void testComparePreReleaseNumericLowerThanAlpha() {
        Assertions.assertTrue(SemVerUtils.compare("1.0.0-alpha", "1.0.0-1") > 0);
        Assertions.assertTrue(SemVerUtils.compare("1.0.0-1", "1.0.0-alpha") < 0);
    }

    @Test
    public void testComparePreReleaseLength() {
        Assertions.assertTrue(SemVerUtils.compare("1.0.0-alpha", "1.0.0-alpha.1") < 0);
        Assertions.assertTrue(SemVerUtils.compare("1.0.0-alpha.1", "1.0.0-alpha") > 0);
        Assertions.assertTrue(SemVerUtils.compare("1.0.0-alpha.1", "1.0.0-alpha.1.1") < 0);
    }

    @Test
    public void testToString() {
        SemVersion v1 = new SemVersion(1, 2, 3);
        Assertions.assertEquals("1.2.3", v1.toString());

        SemVersion v2 = new SemVersion(1, 2, 3, "alpha", null);
        Assertions.assertEquals("1.2.3-alpha", v2.toString());

        SemVersion v3 = new SemVersion(1, 2, 3, null, "build.1");
        Assertions.assertEquals("1.2.3+build.1", v3.toString());

        SemVersion v4 = new SemVersion(1, 2, 3, "beta", "build.2");
        Assertions.assertEquals("1.2.3-beta+build.2", v4.toString());
    }

    @Test
    public void testRoundtrip() {
        String[] versions = {"1.2.3", "0.0.0", "10.20.30-alpha", "1.0.0+build.1", "2.0.0-rc.1+build.42"};
        for (String v : versions) {
            Assertions.assertEquals(v, SemVerUtils.parse(v).toString());
        }
    }

}
