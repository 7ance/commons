package cn.lance.commons.util.uuid;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class UuidUtilsTest {

    @Test
    public void testRandomUuid() {
        String uuid = UuidUtils.randomUuid();
        log.info(uuid);
        Assertions.assertEquals(32, uuid.length());
    }

    @Test
    public void testRandomUuidWithHyphen() {
        String uuid = UuidUtils.randomUuidWithHyphen();
        log.info(uuid);
        Assertions.assertEquals(36, uuid.length());
    }

    @Test
    public void testRandomUuidWithZeroLength() {
        String uuid = UuidUtils.randomUuid(0);
        log.info(uuid);
        Assertions.assertEquals(16, uuid.length());
    }

    @Test
    public void testRandomUuidWithLength() {
        String uuid = UuidUtils.randomUuid(64);
        log.info(uuid);
        Assertions.assertEquals(64, uuid.length());
    }

}
