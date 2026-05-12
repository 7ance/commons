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

public class JsonUtils {

    private static final JsonMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = JsonMapper.builder()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .build();
    }

    private JsonUtils() {
    }

    public static ObjectMapper getDefaultObjectMapper() {
        return OBJECT_MAPPER;
    }

    public static ObjectMapper getNewObjectMapper() {
        return OBJECT_MAPPER.rebuild().build();
    }

    public static boolean isValidJson(String json) {
        try {
            OBJECT_MAPPER.readTree(json);
            return true;
        } catch (JacksonException e) {
            return false;
        }
    }

    public static String write(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }
    }

    public static String writePretty(Object obj) {
        try {
            return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }
    }

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

    public static <T> T read(String json, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }
    }

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

    public static <T> List<T> readList(String json, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readerForListOf(clazz).readValue(json);
        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Object> readMap(String json) {
        try {
            return OBJECT_MAPPER.readValue(json, new TypeReference<>() {
            });
        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }
    }

    public static JsonNode readTree(String json) {
        try {
            return OBJECT_MAPPER.readTree(json);
        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }
    }

    public static String minify(String json) {
        try {
            JsonNode node = OBJECT_MAPPER.readTree(json);
            return OBJECT_MAPPER.writeValueAsString(node);
        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }
    }

    public static String pretty(String json) {
        try {
            JsonNode node = OBJECT_MAPPER.readTree(json);
            return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(node);
        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }
    }

}
