package cn.lance.commons.util.tlv;

import cn.lance.commons.util.json.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;

@Slf4j
public class TlvUtilsTest {

    @Test
    public void testParse() {
        String text = "00020101110001A0102BB";
        List<TlvNode> result = TlvUtils.parse(text);
        log.info(JsonUtils.write(result));
    }

}
