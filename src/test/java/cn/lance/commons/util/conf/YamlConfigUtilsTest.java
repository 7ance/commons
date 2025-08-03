package cn.lance.commons.util.conf;

import cn.lance.commons.entity.YamlConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Properties;

@Slf4j
public class YamlConfigUtilsTest {

    @Test
    public void testLoadProperties() {
        Properties props = ConfigUtils.loadProperties("sample.properties");
        log.info("Properties: {}", props);
        log.info("Properties prefix1.key1: {}", props.getProperty("prefix1.key1"));
        log.info("Properties prefix1.key2: {}", props.getProperty("prefix1.key2"));
        log.info("Properties prefix2.list: {}", props.getProperty("prefix2.list"));
    }

    @Test
    public void testLoadYaml() {
        Map<String, Object> configMap = ConfigUtils.loadYaml("sample.yml");
        log.info("YAML(plain): {}", configMap);
        log.info("YAML(plain) prefix1.key1: {}", configMap.get("prefix1"));
        log.info("YAML(plain) prefix1.key2: {}", configMap.get("prefix1.key2"));
        log.info("YAML(plain) prefix2.list: {}", configMap.get("prefix2.list"));
    }

    @Test
    public void testLoadYamlObject() {
        YamlConfig yamlConfig = ConfigUtils.loadYaml("sample.yml", YamlConfig.class);
        log.info("YAML(object): {}", yamlConfig);
        log.info("YAML(object) prefix1.key1: {}", yamlConfig.getPrefix1().getKey1());
        log.info("YAML(object) prefix1.key2: {}", yamlConfig.getPrefix1().getKey2());
        log.info("YAML(object) prefix2.list: {}", yamlConfig.getPrefix2().getList());
    }
}
