package cn.lance.commons.util.uuid;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class SnowflakeIdUtilsTest {

    @Test
    public void testNextId() {
        SnowflakeIdUtils snowflakeIdUtils = new SnowflakeIdUtils(31, 31);
        for (int i = 0; i < 10; i++) {
            long nextId = snowflakeIdUtils.nextId();
            log.info("Decimal: {}  Binary: {}", nextId, Long.toBinaryString(nextId));
        }
    }

}
