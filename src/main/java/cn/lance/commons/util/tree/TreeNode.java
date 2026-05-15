package cn.lance.commons.util.tree;

import lombok.Data;

import java.util.List;

/**
 * 树节点
 */
@Data
public class TreeNode {

    /**
     * 节点 ID
     */
    private Long id;

    /**
     * 父节点 ID (null 表示根节点)
     */
    private Long parentId;

    /**
     * 节点名称
     */
    private String name;

    /**
     * 子节点列表
     */
    private List<TreeNode> children;

    public TreeNode() {
    }

    public TreeNode(Long id, Long parentId, String name) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }

}
