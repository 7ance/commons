package cn.lance.commons.util.uuid;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
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

    @Test
    public void testNextIdBoundaryMin() {
        SnowflakeIdUtils snowflakeIdUtils = new SnowflakeIdUtils(0, 0);
        long nextId = snowflakeIdUtils.nextId();
        Assertions.assertTrue(nextId > 0);
    }

    @Test
    public void testNextIdBoundaryMax() {
        SnowflakeIdUtils snowflakeIdUtils = new SnowflakeIdUtils(31, 31);
        long nextId = snowflakeIdUtils.nextId();
        Assertions.assertTrue(nextId > 0);
    }

    @Test
    public void testInvalidWorkerIdNegative() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new SnowflakeIdUtils(-1, 0));
    }

    @Test
    public void testInvalidWorkerIdTooLarge() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new SnowflakeIdUtils(32, 0));
    }

    @Test
    public void testInvalidDatacenterIdNegative() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new SnowflakeIdUtils(0, -1));
    }

    @Test
    public void testInvalidDatacenterIdTooLarge() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new SnowflakeIdUtils(0, 32));
    }

    @Test
    public void testSequenceExhaustion() {
        SnowflakeIdUtils snowflakeIdUtils = new SnowflakeIdUtils(0, 0);
        for (int i = 0; i < 5000; i++) {
            long nextId = snowflakeIdUtils.nextId();
            Assertions.assertTrue(nextId > 0);
        }
    }

    @Test
    public void testUniqueIds() {
        SnowflakeIdUtils snowflakeIdUtils = new SnowflakeIdUtils(1, 2);
        long prevId = snowflakeIdUtils.nextId();
        for (int i = 0; i < 1000; i++) {
            long nextId = snowflakeIdUtils.nextId();
            Assertions.assertNotEquals(prevId, nextId);
            prevId = nextId;
        }
    }

}
