package cn.lance.commons.util.tlv;

import cn.lance.commons.util.json.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
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

    @Test
    public void testParseNull() {
        List<TlvNode> result = TlvUtils.parse(null);
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testParseEmpty() {
        List<TlvNode> result = TlvUtils.parse("");
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testParseTooShort() {
        List<TlvNode> result = TlvUtils.parse("0");
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testParseNonNumericTag() {
        List<TlvNode> result = TlvUtils.parse("AA0101");
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testParseNonNumericLength() {
        List<TlvNode> result = TlvUtils.parse("00XX01");
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testParseValueLengthExceedsRemaining() {
        List<TlvNode> result = TlvUtils.parse("0003AB");
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testParseZeroLength() {
        List<TlvNode> result = TlvUtils.parse("0100");
        Assertions.assertEquals(1, result.size());
        TlvNode node = result.get(0);
        Assertions.assertEquals("01", node.getTag());
        Assertions.assertEquals(0, node.getLength());
        Assertions.assertEquals("", node.getValue());
        Assertions.assertNull(node.getSubTags());
    }

    @Test
    public void testParseDepthOne() {
        List<TlvNode> result = TlvUtils.parse("00020101", 1);
        Assertions.assertEquals(1, result.size());
        TlvNode node = result.get(0);
        Assertions.assertEquals("00", node.getTag());
        Assertions.assertEquals(2, node.getLength());
        Assertions.assertEquals("01", node.getValue());
        Assertions.assertNull(node.getSubTags());
    }

    @Test
    public void testParseWithDefaultDepth() {
        List<TlvNode> result = TlvUtils.parse("00020101");
        Assertions.assertEquals(1, result.size());
    }

    @Test
    public void testParseIncompleteTagBoundary() {
        List<TlvNode> result = TlvUtils.parse("010");
        Assertions.assertTrue(result.isEmpty());
    }

}
