package cn.lance.commons.util.str;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class StringUtilsTest {

    @Test
    public void testFormat() {
        String template = "Today is a {} day.";
        String str1 = StringUtils.format(template, "good", "best", "bad");
        log.info("str1: {}", str1);

        String str2 = StringUtils.format(template);
        log.info("str2: {}", str2);

        String template2 = "This month have {} days.";
        String str3 = StringUtils.format(template2, 31);
        log.info("str3: {}", str3);
    }

}
