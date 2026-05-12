package cn.lance.commons.util.json;

import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

import java.util.List;
import java.util.Map;

/**
 * JSON 工具类，基于 Jackson 3.x
 *
 * <p>默认配置：日期时间使用 ISO 8601 格式、忽略未知属性。</p>
 */
public class JsonUtils {

    private static final JsonMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = JsonMapper.builder()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .build();
    }

    private JsonUtils() {
    }

    /**
     * 获取默认 ObjectMapper 实例
     *
     * @return 默认 ObjectMapper
     */
    public static ObjectMapper getDefaultObjectMapper() {
        return OBJECT_MAPPER;
    }

    /**
     * 获取新 ObjectMapper 实例（基于默认配置的副本）
     *
     * @return 新 ObjectMapper 实例
     */
    public static ObjectMapper getNewObjectMapper() {
        return OBJECT_MAPPER.rebuild().build();
    }

    /**
     * 判断字符串是否为合法 JSON
     *
     * @param json JSON 字符串
     * @return 合法返回 true，否则 false
     */
    public static boolean isValidJson(String json) {
        try {
            OBJECT_MAPPER.readTree(json);
            return true;
        } catch (JacksonException e) {
            return false;
        }
    }

    /**
     * 将对象序列化为 JSON 字符串
     *
     * @param obj 对象
     * @return JSON 字符串
     */
    public static String write(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将对象序列化为格式化（缩进）JSON 字符串
     *
     * @param obj 对象
     * @return 格式化 JSON 字符串
     */
    public static String writePretty(Object obj) {
        try {
            return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将对象序列化为 JSON 字符串，支持自定义开启或关闭序列化特性
     *
     * @param obj     对象
     * @param enable  需要开启的 SerializationFeature
     * @param disable 需要关闭的 SerializationFeature
     * @return JSON 字符串
     */
    public static String writeWithFeature(
            Object obj,
            SerializationFeature[] enable,
            SerializationFeature[] disable
    ) {
        JsonMapper.Builder builder = OBJECT_MAPPER.rebuild();

        if (enable != null) {
            for (SerializationFeature feature : enable) {
                builder.enable(feature);
            }
        }
        if (disable != null) {
            for (SerializationFeature feature : disable) {
                builder.disable(feature);
            }
        }

        try {
            return builder.build().writeValueAsString(obj);
        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将 JSON 字符串反序列化为指定类型对象
     *
     * @param <T>   目标类型
     * @param json  JSON 字符串
     * @param clazz 目标类型 Class
     * @return 反序列化后的对象
     */
    public static <T> T read(String json, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将 JSON 字符串反序列化为指定类型对象，支持自定义开启或关闭反序列化特性
     *
     * @param <T>     目标类型
     * @param json    JSON 字符串
     * @param clazz   目标类型 Class
     * @param enable  需要开启的 DeserializationFeature
     * @param disable 需要关闭的 DeserializationFeature
     * @return 反序列化后的对象
     */
    public static <T> T readWithFeature(
            String json,
            Class<T> clazz,
            DeserializationFeature[] enable,
            DeserializationFeature[] disable
    ) {
        JsonMapper.Builder builder = OBJECT_MAPPER.rebuild();
        if (enable != null) {
            for (DeserializationFeature feature : enable) {
                builder.enable(feature);
            }
        }
        if (disable != null) {
            for (DeserializationFeature feature : disable) {
                builder.disable(feature);
            }
        }
        try {
            return builder.build().readValue(json, clazz);
        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将 JSON 数组字符串反序列化为指定类型的 List
     *
     * @param <T>   目标类型
     * @param json  JSON 数组字符串
     * @param clazz 目标类型 Class
     * @return List
     */
    public static <T> List<T> readList(String json, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readerForListOf(clazz).readValue(json);
        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将 JSON 字符串反序列化为 Map
     *
     * @param json JSON 字符串
     * @return Map
     */
    public static Map<String, Object> readMap(String json) {
        try {
            return OBJECT_MAPPER.readValue(json, new TypeReference<>() {
            });
        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将 JSON 字符串解析为 JsonNode 树结构
     *
     * @param json JSON 字符串
     * @return JsonNode
     */
    public static JsonNode readTree(String json) {
        try {
            return OBJECT_MAPPER.readTree(json);
        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 压缩 JSON（去除空格和换行）
     *
     * @param json JSON 字符串
     * @return 压缩后的 JSON 字符串
     */
    public static String minify(String json) {
        try {
            JsonNode node = OBJECT_MAPPER.readTree(json);
            return OBJECT_MAPPER.writeValueAsString(node);
        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 格式化 JSON（缩进美化）
     *
     * @param json JSON 字符串
     * @return 格式化后的 JSON 字符串
     */
    public static String pretty(String json) {
        try {
            JsonNode node = OBJECT_MAPPER.readTree(json);
            return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(node);
        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }
    }

}
