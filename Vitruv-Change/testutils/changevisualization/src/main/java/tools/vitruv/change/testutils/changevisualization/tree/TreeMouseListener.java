package tools.vitruv.change.testutils.changevisualization.tree;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import tools.vitruv.change.testutils.changevisualization.ChangeVisualizationUI;

/**
 * This listener is used to react to mouse events for a {@link ChangeTree}.
 *
 * @author Andreas Loeffler
 */
public class TreeMouseListener extends MouseAdapter {
  private final TabHighlighting tabHighlighting;

  /** A listener implementing the reaction to clicks on the tree-nodes. */
  public TreeMouseListener(TabHighlighting tabHighlighting) {
    this.tabHighlighting = tabHighlighting;
  }

  /** {@inheritDoc} */
  @Override
  public void mouseClicked(MouseEvent e) {
    if (e.getButton() == MouseEvent.BUTTON3) {
      displayMenuForTreeNode(e);
    }
  }

  /**
   * Displays a menu adapted to the actual state of the tree and the node. This happens whenever a
   * node is right-clicked on.
   *
   * @param e The MouseEvent associated with the click
   */
  private void displayMenuForTreeNode(MouseEvent e) {
    if (e.getClickCount() != 1) {
      return;
    }

    JTree treeUI = getTreeUI(e);
    JPopupMenu popupMenu = new JPopupMenu();
    DefaultMutableTreeNode node = determineNode(e.getPoint(), treeUI);
    if (node != null && (treeUI.getSelectionPath() == null) 
        || (treeUI.getSelectionPath().getLastPathComponent() != node)) {
        // If the clicked node is not the selected one, select it
        treeUI.setSelectionPath(new TreePath(node.getPath()));
      }
  

    addHighlightItem(popupMenu, node);
    addSearchItem(popupMenu, treeUI);
    addResetSearchItem(popupMenu);
    addCopyToClipboardItem(popupMenu);
    addInfoItem(popupMenu);
    popupMenu.show(treeUI, e.getPoint().x, e.getPoint().y);
  }

  /**
   * Adds an item displaying the actual highlighted id.
   *
   * @param popupMenu The menu to add the item to
   */
  private void addInfoItem(JPopupMenu popupMenu) {
    if (tabHighlighting.getHighlightID() != null) {
      popupMenu.addSeparator();
      JLabel info = new JLabel(" Highlighted ID = " + tabHighlighting.getHighlightID());
      info.setFont(ChangeVisualizationUI.DEFAULT_MENUITEM_FONT);
      popupMenu.add(info);
    }
  }

  /**
   * Adds an item used to copy the highlighted id to the System Clipboard.
   *
   * @param popupMenu The menu to add the item to
   */
  private void addCopyToClipboardItem(JPopupMenu popupMenu) {
    JMenuItem copySearchItem = new JMenuItem("Copy highlight ID to clipboard");
    copySearchItem.setFont(ChangeVisualizationUI.DEFAULT_MENUITEM_FONT);
    copySearchItem.addActionListener(
        e -> {
          StringSelection stringSelection = new StringSelection(tabHighlighting.getHighlightID());
          Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
          clipboard.setContents(stringSelection, null);
        });
    copySearchItem.setEnabled(tabHighlighting.getHighlightID() != null);
    popupMenu.add(copySearchItem);
  }

  /**
   * Adds an item to reset the actual highlighted id.
   *
   * @param popupMenu The menu to add the item to
   */
  private void addResetSearchItem(JPopupMenu popupMenu) {
    JMenuItem resetSearchItem = new JMenuItem("Reset highlight ID");
    resetSearchItem.setFont(ChangeVisualizationUI.DEFAULT_MENUITEM_FONT);
    resetSearchItem.addActionListener(e -> tabHighlighting.setHighlightID(null));
    resetSearchItem.setEnabled(tabHighlighting.getHighlightID() != null);
    popupMenu.add(resetSearchItem);
  }

  /**
   * Adds an item displaying the actual highlighted id.
   *
   * @param popupMenu The menu to add the item to
   * @param treeUI The tree that this listener observes
   */
  private void addSearchItem(JPopupMenu popupMenu, final JTree treeUI) {
    JMenuItem searchItem = new JMenuItem("Enter manual highlight ID..");
    searchItem.setFont(ChangeVisualizationUI.DEFAULT_MENUITEM_FONT);
    searchItem.addActionListener(
        e -> {
          String input = JOptionPane.showInputDialog(treeUI, "Please input the ID to search :");
          if (input == null) {
            return;
          }
          input = input.trim();
          tabHighlighting.setHighlightID(input);
        });
    popupMenu.add(searchItem);
    popupMenu.addSeparator();
  }

  /**
   * Adds an item displaying the actual highlighted id.
   *
   * @param popupMenu The menu to add the item to
   * @param node The node clicked on, may be null
   * @param treeUI The tree that this listener observes
   */
  private void addHighlightItem(JPopupMenu popupMenu, DefaultMutableTreeNode node) {
    if (node == null) {
      // Nothing to add
      return;
    }

    Object userObject = node.getUserObject();
    if (userObject instanceof ChangeNode) {
      ChangeNode changeNode = (ChangeNode) node.getUserObject();
      final String highlightID = changeNode.getEObjectID();
      JMenuItem menuItem = new JMenuItem("Highlight ID : " + highlightID);
      menuItem.setFont(ChangeVisualizationUI.DEFAULT_MENUITEM_FONT);
      menuItem.addActionListener(e -> tabHighlighting.setHighlightID(highlightID));
      popupMenu.add(menuItem);
      popupMenu.addSeparator();
    }
  }

  /**
   * Determines the JTree that fired the given event.
   *
   * @param e The MouseEvent fired
   * @return The firing JTree
   */
  private JTree getTreeUI(MouseEvent e) {
    return (JTree) e.getSource();
  }

  /**
   * Determines the node at the given click point identified by the TreePath leading to it.
   *
   * @param point The clicked point
   * @return The clicked node, null if no node exists at the given point
   */
  private DefaultMutableTreeNode determineNode(Point point, JTree treeUI) {
    TreePath selPath = treeUI.getPathForLocation(point.x, point.y);
    if (selPath != null) {
      return (DefaultMutableTreeNode) selPath.getLastPathComponent();
    } 
    return null;
  }
}
