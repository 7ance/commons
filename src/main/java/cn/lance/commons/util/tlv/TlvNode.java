package cn.lance.commons.util.tlv;

import lombok.Data;

import java.util.List;

@Data
public class TlvNode {

    private String tag;

    private int length;

    private String value;

    private List<TlvNode> subTags;

}