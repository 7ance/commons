package cn.lance.commons.util.tlv;

import cn.lance.commons.util.json.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TlvUtilsTest {

    @Test
    public void testParse() throws JsonProcessingException {
        String text = "00020101110001A0102BB";
        List<TlvNode> result = TlvUtils.parse(text);
        System.out.println(JsonUtils.write(result));
    }

}
