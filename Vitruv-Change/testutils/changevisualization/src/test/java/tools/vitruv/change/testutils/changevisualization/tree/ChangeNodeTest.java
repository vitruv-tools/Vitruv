package tools.vitruv.change.testutils.changevisualization.tree;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Method;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test class for testing the private static method {@code getPathString}.
 * in {@code TreeChangeDataSet} using reflection.
 */
class ChangeNodeTest {

  private DefaultMutableTreeNode root;
  private JTree tree;

  /**
   * Initializes a tree structure for testing.
   *
   * <pre>
   * root
   * ├── child1
   * │   └── grandchild
   * └── child2
   * </pre>
   */
  @BeforeEach
  void setup() {
    root = new DefaultMutableTreeNode("root");
    DefaultMutableTreeNode child1 = new DefaultMutableTreeNode("child1");
    DefaultMutableTreeNode child2 = new DefaultMutableTreeNode("child2");
    DefaultMutableTreeNode grandchild = new DefaultMutableTreeNode("grandchild");

    child1.add(grandchild);
    root.add(child1);
    root.add(child2);

    tree = new JTree(root);
  }

  /**
   * Invokes the private static method {@code getPathString(TreeNode[], JTree)}
   * from {@code TreeChangeDataSet} via reflection.
   *
   * @param path the path array of TreeNode elements
   * @return the index-based string representation of the path
   * @throws Exception if reflection or invocation fails
   */
  private String invokeGetPathString(TreeNode[] path) throws Exception {
    Class<?> clazz = Class.forName(
            "tools.vitruv.change.testutils.changevisualization.tree.TreeChangeDataSet"
    );

    Method method = clazz.getDeclaredMethod(
            "getPathString",
            TreeNode[].class,
            JTree.class
    );

    method.setAccessible(true);
    return (String) method.invoke(null, path, tree);
  }

  /**
   * Tests {@code getPathString} for a valid path to a direct child of root.
   */
  @Test
  void testValidPath() throws Exception {
    TreeNode[] path = ((DefaultMutableTreeNode) root.getChildAt(0)).getPath();
    String result = invokeGetPathString(path);
    assertEquals("0|0", result);
  }

  /**
   * Tests {@code getPathString} for a deeper path to a grandchild node.
   */
  @Test
  void testDeeperPath() throws Exception {
    TreeNode[] path = ((DefaultMutableTreeNode)
            root.getChildAt(0).getChildAt(0)).getPath();
    String result = invokeGetPathString(path);
    assertEquals("0|0|0", result);
  }

  /**
   * Tests {@code getPathString} with an empty TreeNode array.
   */
  @Test
  void testEmptyPath() throws Exception {
    String result = invokeGetPathString(new TreeNode[0]);
    assertEquals("", result);
  }

  /**
   * Tests {@code getPathString} with a null path.
   */
  @Test
  void testNullPath() throws Exception {
    String result = invokeGetPathString(null);
    assertEquals("", result);
  }
}
