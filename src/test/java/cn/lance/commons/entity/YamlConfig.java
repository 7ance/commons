package cn.lance.commons.entity;

import lombok.Data;

import java.util.List;

@Data
public class YamlConfig {

    private Prefix1 prefix1;

    private Prefix2 prefix2;

    @Data
    public static class Prefix1 {

        private String key1;

        private String key2;

    }

    @Data
    public static class Prefix2 {

        private List<String> list;

    }

}
