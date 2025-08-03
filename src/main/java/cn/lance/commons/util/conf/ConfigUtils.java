package cn.lance.commons.util.conf;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public class ConfigUtils {

    /**
     * 加载 .properties 配置文件
     *
     * @param filename 配置文件路径
     * @return Properties 对象
     */
    public static Properties loadProperties(String filename) {
        Objects.requireNonNull(filename);

        Properties props = new Properties();
        try (InputStream input = ConfigUtils.class.getClassLoader().getResourceAsStream(filename)) {
            if (input == null) {
                throw new RuntimeException("Unable to find " + filename);
            }
            props.load(input);
            return props;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 加载 .yaml 配置文件
     *
     * @param filename 配置文件路径
     * @return Map 对象
     */
    public static Map<String, Object> loadYaml(String filename) {
        Objects.requireNonNull(filename);

        try (InputStream input = ConfigUtils.class.getClassLoader().getResourceAsStream(filename)) {
            if (input == null) {
                throw new RuntimeException("Unable to find " + filename);
            }
            Yaml yaml = new Yaml();
            return yaml.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 加载 .yaml 配置文件
     *
     * @param filename 配置文件路径
     * @param clazz    配置类
     * @param <T>      配置类
     * @return 配置对象
     */
    public static <T> T loadYaml(String filename, Class<T> clazz) {
        Objects.requireNonNull(filename);
        Objects.requireNonNull(clazz);

        try (InputStream input = ConfigUtils.class.getClassLoader().getResourceAsStream(filename)) {
            if (input == null) {
                throw new RuntimeException("Unable to find " + filename);
            }
            Yaml yaml = new Yaml();
            return yaml.loadAs(input, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
