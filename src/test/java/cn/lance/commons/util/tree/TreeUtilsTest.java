package cn.lance.commons.util.tree;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
public class TreeUtilsTest {

    @Test
    public void testBuildSimple() {
        TreeNode root = new TreeNode(1L, null, "root");
        TreeNode child1 = new TreeNode(2L, 1L, "child1");
        TreeNode child2 = new TreeNode(3L, 1L, "child2");

        List<TreeNode> flat = Arrays.asList(root, child1, child2);
        List<TreeNode> roots = TreeUtils.build(flat);

        Assertions.assertEquals(1, roots.size());
        Assertions.assertEquals("root", roots.get(0).getName());
        Assertions.assertNotNull(roots.get(0).getChildren());
        Assertions.assertEquals(2, roots.get(0).getChildren().size());
    }

    @Test
    public void testBuildMultiRoot() {
        TreeNode root1 = new TreeNode(1L, null, "root1");
        TreeNode root2 = new TreeNode(2L, null, "root2");

        List<TreeNode> flat = Arrays.asList(root1, root2);
        List<TreeNode> roots = TreeUtils.build(flat);

        Assertions.assertEquals(2, roots.size());
        Assertions.assertNotNull(roots.get(0).getChildren());
        Assertions.assertTrue(roots.get(0).getChildren().isEmpty());
        Assertions.assertNotNull(roots.get(1).getChildren());
        Assertions.assertTrue(roots.get(1).getChildren().isEmpty());
    }

    @Test
    public void testBuildDeepNesting() {
        TreeNode n1 = new TreeNode(1L, null, "level1");
        TreeNode n2 = new TreeNode(2L, 1L, "level2");
        TreeNode n3 = new TreeNode(3L, 2L, "level3");
        TreeNode n4 = new TreeNode(4L, 3L, "level4");

        List<TreeNode> flat = Arrays.asList(n1, n2, n3, n4);
        List<TreeNode> roots = TreeUtils.build(flat);

        Assertions.assertEquals(1, roots.size());
        TreeNode current = roots.get(0);
        for (int i = 2; i <= 4; i++) {
            Assertions.assertNotNull(current.getChildren());
            Assertions.assertEquals(1, current.getChildren().size());
            current = current.getChildren().get(0);
        }
    }

    @Test
    public void testBuildEmptyList() {
        List<TreeNode> roots = TreeUtils.build(Collections.emptyList());
        Assertions.assertTrue(roots.isEmpty());
    }

    @Test
    public void testBuildNullInput() {
        Assertions.assertThrows(NullPointerException.class,
                () -> TreeUtils.build(null));
    }

    @Test
    public void testBuildOrphanNode() {
        TreeNode orphan = new TreeNode(1L, 999L, "orphan");
        List<TreeNode> nodes = new ArrayList<>();
        nodes.add(orphan);
        List<TreeNode> roots = TreeUtils.build(nodes);
        Assertions.assertTrue(roots.isEmpty());
    }

    @Test
    public void testBuildMixedOrphanAndRoot() {
        TreeNode root = new TreeNode(1L, null, "root");
        TreeNode orphan = new TreeNode(2L, 999L, "orphan");
        TreeNode child = new TreeNode(3L, 1L, "child");

        List<TreeNode> roots = TreeUtils.build(Arrays.asList(root, orphan, child));
        Assertions.assertEquals(1, roots.size());
        Assertions.assertEquals("root", roots.get(0).getName());
        Assertions.assertEquals(1, roots.get(0).getChildren().size());
    }

    @Test
    public void testTreeNodeConstructor() {
        TreeNode node = new TreeNode(1L, null, "test");
        Assertions.assertEquals(1L, node.getId());
        Assertions.assertNull(node.getParentId());
        Assertions.assertEquals("test", node.getName());
        Assertions.assertNull(node.getChildren());
    }

    @Test
    public void testTreeNodeSetters() {
        TreeNode node = new TreeNode();
        node.setId(10L);
        node.setParentId(5L);
        node.setName("test");
        List<TreeNode> children = new ArrayList<>();
        node.setChildren(children);

        Assertions.assertEquals(10L, node.getId());
        Assertions.assertEquals(5L, node.getParentId());
        Assertions.assertEquals("test", node.getName());
        Assertions.assertSame(children, node.getChildren());
    }

}
