package cn.lance.commons.util.tree;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 树形结构构建工具
 *
 * <p>将包含 parentId 的扁平节点列表构建为树形结构。</p>
 */
public class TreeUtils {

    private TreeUtils() {
    }

    /**
     * 构建树，返回根节点列表
     *
     * @param nodes 扁平节点列表
     * @return 根节点列表
     */
    public static List<TreeNode> build(List<TreeNode> nodes) {
        Objects.requireNonNull(nodes, "nodes must not be null");

        if (nodes.isEmpty()) {
            return Collections.emptyList();
        }

        Map<Long, TreeNode> nodeMap = nodes.stream()
                .collect(Collectors.toMap(TreeNode::getId, node -> node, (a, b) -> a, LinkedHashMap::new));

        List<TreeNode> roots = new ArrayList<>();

        for (TreeNode node : nodes) {
            if (node.getParentId() == null) {
                if (node.getChildren() == null) {
                    node.setChildren(new ArrayList<>());
                }
                roots.add(node);
            } else {
                TreeNode parent = nodeMap.get(node.getParentId());
                if (parent != null) {
                    if (parent.getChildren() == null) {
                        parent.setChildren(new ArrayList<>());
                    }
                    parent.getChildren().add(node);
                }
            }
        }

        return roots;
    }

}
