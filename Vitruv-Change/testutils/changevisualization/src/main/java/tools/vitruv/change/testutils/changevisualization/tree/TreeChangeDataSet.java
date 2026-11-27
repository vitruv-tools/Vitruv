package tools.vitruv.change.testutils.changevisualization.tree;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Pattern;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import tools.vitruv.change.testutils.changevisualization.common.ChangeDataSet;

/**
 * TreeChangeDataSet holds the data necessary for visualization as a tree. It also stores layout
 * information and applies existing layout data to trees.
 *
 * @author Andreas Loeffler
 */
public class TreeChangeDataSet extends ChangeDataSet {

  /** The serialVersionID for java object serialization. */
  private static final long serialVersionUID = 1956649507076783962L;

  /**
   * Identifies a TreeNode[] (==TreePath) of a given JTree with the help of a pathString.
   *
   * @param treeUI The JTree
   * @param pathString The pathString
   * @return TreeNode[] identified by the pathString
   */
  private static TreeNode[] getPath(JTree treeUI, String pathString) {
    String[] parts = pathString.split(Pattern.quote("|"));
    TreeNode[] path = new TreeNode[parts.length];
    TreeNode parent = (TreeNode) treeUI.getModel().getRoot();
    path[0] = parent;
    for (int n = 1; n < parts.length; n++) {
      int index = Integer.parseInt(parts[n]);
      TreeNode node = (TreeNode) treeUI.getModel().getChild(parent, index);
      path[n] = node;
      parent = node;
    }
    return path;
  }

  /**
   * Converts a given TreePath of a JTree to a String. This String identifies the path and is used
   * as the key to store and reset layout information.
   *
   * @param path The TreePath
   * @param treeUI The JTree
   * @return String identifying the path
   */
  private static String getPathString(TreePath path, JTree treeUI) {
    TreeNode[] pathNodes = new TreeNode[path.getPathCount()];
    for (int n = 0; n < pathNodes.length; n++) {
      pathNodes[n] = (TreeNode) path.getPathComponent(n);
    }
    return getPathString(pathNodes, treeUI);
  }

  /**
   * Converts a given TreeNode[] of a JTree to a String. This String identifies the path and is used
   * as the key to store and reset layout information.
   *
   * @param path The TreePath in form of a TreeNode[]
   * @param treeUI The JTree
   * @return String identifying the path
   */
  private static String getPathString(TreeNode[] path, JTree treeUI) {
    if (path == null || path.length == 0 || treeUI == null || treeUI.getModel() == null) {
      return "";
    }

    TreeNode parent = path[0];
    StringBuilder pathString = new StringBuilder("0"); // root is always index 0

    for (int n = 1; n < path.length; n++) {
      TreeNode child = path[n];
      int index = treeUI.getModel().getIndexOfChild(parent, child);

      if (index < 0) {
        // If index is invalid, stop and return what we have so far
        break;
      }

      pathString.append("|").append(index);
      parent = child;
    }

    return pathString.toString();
  }

  // Instance methods and variables

  /** The root node of the tree model. */
  private final DefaultMutableTreeNode rootNode;

  /**
   * Stores if a given Node is expanded in the ui. The Node is identified by a TreePath, and that by
   * its pathString.
   */
  private final Map<String, Boolean> pathString2expanded = new HashMap<>();

  /** List of all registered pathStrings. */
  private final List<String> pathStrings = new LinkedList<>();

  /** Stores the actual selected Node, if any. */
  private String selectedPathString = null;

  /**
   * Constructs a new TreeChangeDataSet.
   *
   * @param id The ID of the changeDataSet
   * @param rootNode The root node holding the whole tree
   */
  public TreeChangeDataSet(String id, DefaultMutableTreeNode rootNode) {
    super(id);
    this.rootNode = rootNode;
  }

  @Override
  public Object getData() {
    return rootNode;
  }

  // All methods below are needed for layout processing

  /**
   * Applies layout information to a given jtree's node, if this information exists.
   *
   * @param treeUI The JTree
   * @return True if layout information existed (and has been applied), false otherwise
   */
  public boolean applyLayout(JTree treeUI) {
    if (!hasLayoutInfo()) {
      return false;
    }

    // Walk all path strings and reset the expansion state
    for (String pathString : pathStrings) {
      boolean expanded = isExpanded(pathString);
      if (expanded) {
        treeUI.expandPath(new TreePath(getPath(treeUI, pathString)));
      }
    }

    // Set the selected path, if any
    if (selectedPathString != null) {
      treeUI
          .getSelectionModel()
          .setSelectionPath(new TreePath(getPath(treeUI, selectedPathString)));
    }

    return true;
  }

  /**
   * Stores the layout information for the nodes in the given JTree.
   *
   * @param treeUI The JTree
   */
  public void storeLayoutInfo(JTree treeUI) {
    // remove old layout information, if existent
    resetLayoutInfo();

    DefaultTreeModel model = (DefaultTreeModel) treeUI.getModel();
    if (model == null || model.getRoot() == null) {
      // Nothing there to store
    } else {
      // Store layout information for the nodes
      DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
      storeLayoutInfo(root, treeUI);

      // Store the selected path, if existent
      TreePath selectedPath = treeUI.getSelectionPath();
      if (selectedPath != null) {
        selectedPathString = TreeChangeDataSet.getPathString(selectedPath, treeUI);
      }
    }
  }

  /**
   * Stores the layout information for a given node of the jtree.
   *
   * @param node The node
   * @param treeUI The jtree
   */
  private void storeLayoutInfo(DefaultMutableTreeNode node, JTree treeUI) {
    TreeNode[] path = node.getPath();
    String pathString = TreeChangeDataSet.getPathString(path, treeUI);
    boolean expanded = treeUI.isExpanded(new TreePath(path));
    storeLayout(pathString, expanded);
    for (int n = 0; n < node.getChildCount(); n++) {
      DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(n);
      storeLayoutInfo(child, treeUI);
    }
  }

  /** Resets the layout iformation. This method is called when new information gets stored. */
  private void resetLayoutInfo() {
    pathString2expanded.clear();
    pathStrings.clear();
    selectedPathString = null;
  }

  /**
   * Returns whether layout information for this cds exists.
   *
   * @return True if layout information exists
   */
  private boolean hasLayoutInfo() {
    return pathString2expanded.size() > 0;
  }

  /**
   * Stores the information if the given pathString is expanded in the ui.
   *
   * @param pathString The pathString identifying a node
   * @param expanded Expansion state of the Node
   */
  private void storeLayout(String pathString, boolean expanded) {
    pathString2expanded.put(pathString, expanded);
    pathStrings.add(pathString);
  }

  /**
   * Checks whether a given pathString was expanded in the ui.
   *
   * @param pathString The pathString
   * @return True if expanded
   */
  private boolean isExpanded(String pathString) {
    Boolean expanded = pathString2expanded.get(pathString);
    if (expanded == null) {
      expanded = false;
    }
    return expanded;
  }
}
