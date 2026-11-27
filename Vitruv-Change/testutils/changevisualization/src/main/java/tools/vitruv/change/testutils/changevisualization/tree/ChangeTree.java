package tools.vitruv.change.testutils.changevisualization.tree;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import lombok.Getter;
import tools.vitruv.change.testutils.changevisualization.ChangeVisualizationUI;
import tools.vitruv.change.testutils.changevisualization.common.ChangeDataSet;
import tools.vitruv.change.testutils.changevisualization.ui.ChangeComponent;
import tools.vitruv.change.testutils.changevisualization.ui.LabelValuePanel;

/**
 * A ChangeTree visualizes propagation results in the form of a tree.
 *
 * @author Andreas Loeffler
 */
@Getter
public class ChangeTree extends ChangeComponent {

  /**
   * Needed for eclipse to stop warning about serialVersionIds. This feature will never been used.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Enum of all different default expand states of newly added change data sets.
   *
   * @author Andreas Loeffler
   */
  private enum ExpandBehavior {
    EXPAND_PROPAGATED_CHANGES,
    EXPAND_VCHANGES,
    EXPAND_ECHANGES
  }

  /** The default setting for the simple echange text checkbox. */
  private static final boolean SIMPLE_ECHANGE_TEXT_DEFAULT_VALUE = false;

  /** The default setting for the show id checkbox. */
  private static final boolean SHOW_ID_DEFAULT_VALUE = false;

  /** Key used to store the viewport rectangle of the treeScroller in the changeDataSet. */
  private static final String VIEWPORT_RECTANGLE_KEY = "VIEWPORT_RECTANGLE";

  /** The JTree that actually visualizes the changeDataSet. */
  private JTree treeUI;

  /**
   * When details of an selected node are just text details, they are displayed in this JTextArea.
   */
  private JTextArea detailsUI;

  /** The actual default expand behaviour. */
  private ExpandBehavior expandBehavior = ExpandBehavior.EXPAND_PROPAGATED_CHANGES;

  /** The renderer that renders the nodes of the tree. */
  private final ChangeTreeNodeRenderer changeEventTreeRenderer;

  /** The splitpane splitting tree from details . */
  private JSplitPane detailsSplitpane;

  /** The JScrollPane used to display detail-component bigger than the viewport available . */
  private JScrollPane detailsScroller;

  /** The TreeMouseListener responding to mouse interaction . */
  private final TreeMouseListener ml;

  /** The listener processing tree selection events of the JTree component. */
  private final TreeSelectionListener tsl =
      new TreeSelectionListener() {
        @Override
        public void valueChanged(TreeSelectionEvent e) {
          // reset details display to default behavior
          detailsUI.setText("");
          int divLocation = detailsSplitpane.getDividerLocation();
          detailsSplitpane.setRightComponent(detailsScroller);

          // We have single selection mode ==> only one selected path
          TreePath path = e.getNewLeadSelectionPath();

          // Update the detailsUI (if necessary)
          updateDetailsUI(path);

          // Restore the divider location, changing of the displayed components could have changed
          // it
          detailsSplitpane.setDividerLocation(divLocation);
        }

        /**
         * Updates the detailsUI, if necessary.
         *
         * @param path The path pointing to the selected node
         */
        private void updateDetailsUI(TreePath path) {
          if (path != null) {
            DefaultMutableTreeNode selectedNode =
                (DefaultMutableTreeNode) path.getLastPathComponent();
            if (selectedNode != null) {
              Object userObj = selectedNode.getUserObject();
              if (userObj != null) {
                if (userObj instanceof FeatureNode) {
                  String details = ((FeatureNode) userObj).getDetails();
                  String[][] detailsArray = ((FeatureNode) userObj).getDetailsArray();
                  Component detailsComp = ((FeatureNode) userObj).getDetailsUI();
                  if (details != null) {
                    detailsUI.setText(details);
                  } else if (detailsArray != null) {
                    detailsSplitpane.setRightComponent(new LabelValuePanel(detailsArray));
                  } else if (detailsComp != null) {
                    detailsSplitpane.setRightComponent(detailsComp);
                  }
                } else if (userObj instanceof ChangeNode) {
                  // ChangeNode creates the component on the fly for us
                  Component detailsComp = ((ChangeNode) userObj).getDetailsUI();
                  detailsSplitpane.setRightComponent(detailsComp);
                }
              }
            }
          }
        }
      };

  /** The actual changeDataSet. */
  private TreeChangeDataSet actualChangeDataSet = null;

  /** The JScrollPane used to scroll the tree, if necessary. */
  private JScrollPane treeScroller;

  /** The checkbox to select the simple echange text mode. */
  private JCheckBox simpleEChangeTextBox;

  /** The checkbox to enable/disable id showing. */
  private JCheckBox showIDTextBox;

  /** Constructs a ChangeTree UI visualizing change events in the form of a tree. */
  public ChangeTree(TabHighlighting tabHighlighting) {
    super(new BorderLayout());

    createUI();
    this.changeEventTreeRenderer = new ChangeTreeNodeRenderer(tabHighlighting);

    // Add listeners and renderers to the tree UI and scroll pane.
    ml = new TreeMouseListener(tabHighlighting);
    treeUI.addMouseListener(ml);

    treeUI.getSelectionModel().addTreeSelectionListener(tsl);

    treeUI.setCellRenderer(changeEventTreeRenderer);

    treeScroller.addMouseWheelListener(
        mouseWheelEvent -> {
          // Implements the usual Ctrl + mouse wheel behavior for zooming.
          if ((mouseWheelEvent.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) == 0) {
            return;
          }
          if (mouseWheelEvent.getWheelRotation() <= -1) {
            float newSize = treeUI.getFont().getSize() + 2.0f;
            if (newSize > 30f) {
              newSize = 30f;
            }
            treeUI.setFont(treeUI.getFont().deriveFont(newSize));
            treeUI.setRowHeight((int) (newSize + 10f));
          } else if (mouseWheelEvent.getWheelRotation() >= 1) {
            float newSize = treeUI.getFont().getSize() - 2f;
            if (newSize < 5f) {
              newSize = 5f;
            }
            treeUI.setFont(treeUI.getFont().deriveFont(newSize));
            treeUI.setRowHeight((int) (newSize + 10f));
          }
        });
  }

  /** Sets the enabled state of the ChangeTree. */
  @Override
  public void setEnabled(boolean isEnabled) {
    changeEventTreeRenderer.setEnabled(isEnabled);
    if (isEnabled) {
      treeUI.addMouseListener(ml);
    } else {
      treeUI.removeMouseListener(ml);
    }
  }

  /** Creates the UI . */
  private void createUI() {
    detailsSplitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

    treeUI = new JTree();
    treeUI.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    treeUI.setFont(ChangeVisualizationUI.DEFAULT_TREE_FONT);

    treeScroller = new JScrollPane(treeUI);
    detailsSplitpane.add(treeScroller);

    detailsUI = new JTextArea();
    detailsUI.setFont(ChangeVisualizationUI.DEFAULT_TEXTAREA_FONT);
    detailsUI.setEditable(false);
    detailsScroller = new JScrollPane(detailsUI);

    TitledBorder detailsTitleBorder = BorderFactory.createTitledBorder("Details");
    detailsTitleBorder.setTitleFont(ChangeVisualizationUI.DEFAULT_TITLED_BORDER_FONT);
    detailsScroller.setBorder(detailsTitleBorder);
    detailsSplitpane.add(detailsScroller);
    detailsSplitpane.setDividerLocation(1100);

    add(detailsSplitpane, BorderLayout.CENTER);

    // Create south == the toolbar
    add(createToolbar(), BorderLayout.SOUTH);
  }

  /**
   * Creates the toolbar.
   *
   * @return The toolbar
   */
  private Component createToolbar() {
    JPanel toolbar = new JPanel(new FlowLayout());

    addDefaultExpansionBehaviour(toolbar);

    toolbar.add(new JLabel("     ")); // White space
    addEChangeClassColors(toolbar);

    toolbar.add(new JLabel("     ")); // White space
    addStateChangeBoxes(toolbar);

    return toolbar;
  }

  /**
   * Adds the state change boxes to the toolbar.
   *
   * @param toolbar The toolbar to add the boxes to
   */
  private void addStateChangeBoxes(JPanel toolbar) {
    simpleEChangeTextBox = new JCheckBox("Simple EChange text", SIMPLE_ECHANGE_TEXT_DEFAULT_VALUE);
    simpleEChangeTextBox.setFont(ChangeVisualizationUI.DEFAULT_CHECKBOX_FONT);
    simpleEChangeTextBox.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            boolean simpleEChangeText = ((JCheckBox) e.getSource()).isSelected();
            updateSimpleEChangeText(simpleEChangeText);
          }
        });
    toolbar.add(simpleEChangeTextBox);

    showIDTextBox = new JCheckBox("Show IDs", SHOW_ID_DEFAULT_VALUE);
    showIDTextBox.setFont(ChangeVisualizationUI.DEFAULT_CHECKBOX_FONT);
    showIDTextBox.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            boolean showID = ((JCheckBox) e.getSource()).isSelected();
            updateShowID(showID);
          }
        });
    toolbar.add(showIDTextBox);
  }

  /**
   * Update the simpleEChangeText state for all nodes in the model.
   *
   * @param simpleEChangeText The simpleEChangeText state
   */
  private void updateSimpleEChangeText(boolean simpleEChangeText) {
    DefaultTreeModel model = (DefaultTreeModel) treeUI.getModel();
    DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
    if (root != null) {
      Enumeration<TreeNode> nodes = root.depthFirstEnumeration();
      while (nodes.hasMoreElements()) {
        TreeNode node = nodes.nextElement();
        if (node instanceof DefaultMutableTreeNode) {
          DefaultMutableTreeNode mutableNode = (DefaultMutableTreeNode) node;
          if (mutableNode.getUserObject() instanceof ChangeNode) {
            // Only EChange nodes are affected and change their size
            ((ChangeNode) mutableNode.getUserObject()).setSimpleEChangeText(simpleEChangeText);
            model.nodeChanged(node);
          }
        }
      }
    }
  }

  /**
   * Update the showId state for all nodes in the model.
   *
   * @param showID The showID state
   */
  private void updateShowID(boolean showID) {
    DefaultTreeModel model = (DefaultTreeModel) treeUI.getModel();
    DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
    if (root != null) {
      Enumeration<TreeNode> nodes = root.depthFirstEnumeration();
      while (nodes.hasMoreElements()) {
        TreeNode node = nodes.nextElement();
        if (node instanceof DefaultMutableTreeNode) {
          DefaultMutableTreeNode mutableNode = (DefaultMutableTreeNode) node;
          if (mutableNode.getUserObject() instanceof ChangeNode) {
            // Only EChange nodes are affected and change their size
            ((ChangeNode) mutableNode.getUserObject()).setShowID(showID);
            model.nodeChanged(node);
          }
        }
      }
    }
  }

  /**
   * Adds labels to the toolbar visualizing the colors used for the different eChange types.
   *
   * @param toolbar The toolbar to add the labels to
   */
  private void addEChangeClassColors(JPanel toolbar) {

    JLabel changesLabel = new JLabel("EChange-Types");
    changesLabel.setFont(ChangeVisualizationUI.DEFAULT_LABEL_FONT);
    toolbar.add(changesLabel);

    Icon[] icons =
        new Icon[] {
          ChangeTreeNodeRenderer.ATTRIBUTE_ECHANGE_OPEN_ICON,
          ChangeTreeNodeRenderer.REFERENCE_ECHANGE_OPEN_ICON,
          ChangeTreeNodeRenderer.EXISTENCE_ECHANGE_OPEN_ICON,
          ChangeTreeNodeRenderer.ROOT_ECHANGE_OPEN_ICON
        };
    String[] names = new String[] {"Attribute", "Reference", "Existence", "Root"};

    for (int n = 0; n < icons.length; n++) {
      JLabel label = new JLabel(names[n], icons[n], JLabel.RIGHT);
      label.setFont(ChangeVisualizationUI.DEFAULT_LABEL_FONT);
      toolbar.add(label);
    }
  }

  /**
   * Adds checkboxes to select the default expansion behavior to the toolbar.
   *
   * @param toolbar The toolbar to add the boxes to
   */
  private void addDefaultExpansionBehaviour(JPanel toolbar) {

    JLabel defaultExpandLabel = new JLabel(" Default expand behavior : ");
    defaultExpandLabel.setFont(ChangeVisualizationUI.DEFAULT_LABEL_FONT);
    toolbar.add(defaultExpandLabel);

    // create expand behavior selectors
    final JCheckBox expandPChange =
        new JCheckBox("Root", expandBehavior == ExpandBehavior.EXPAND_PROPAGATED_CHANGES);
    expandPChange.setFont(ChangeVisualizationUI.DEFAULT_CHECKBOX_FONT);
    final JCheckBox expandVChange =
        new JCheckBox("vChanges", expandBehavior == ExpandBehavior.EXPAND_VCHANGES);
    expandVChange.setFont(ChangeVisualizationUI.DEFAULT_CHECKBOX_FONT);
    final JCheckBox expandEChange =
        new JCheckBox("eChanges", expandBehavior == ExpandBehavior.EXPAND_ECHANGES);
    expandEChange.setFont(ChangeVisualizationUI.DEFAULT_CHECKBOX_FONT);

    // Assures only one is selected
    ButtonGroup bg1 = new ButtonGroup();
    bg1.add(expandPChange);
    bg1.add(expandVChange);
    bg1.add(expandEChange);

    // Create listener
    ActionListener behaviorListener =
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            if (((JCheckBox) e.getSource()).isSelected()) {
              if (expandPChange == e.getSource()) {
                expandBehavior = ExpandBehavior.EXPAND_PROPAGATED_CHANGES;
              } else if (expandVChange == e.getSource()) {
                expandBehavior = ExpandBehavior.EXPAND_VCHANGES;
              } else if (expandEChange == e.getSource()) {
                expandBehavior = ExpandBehavior.EXPAND_ECHANGES;
              }
              expandNodes();
            } else {
              // we react only to the selection
            }
          }
        };

    // add listener
    expandPChange.addActionListener(behaviorListener);
    expandVChange.addActionListener(behaviorListener);
    expandEChange.addActionListener(behaviorListener);

    // add to ui
    toolbar.add(expandPChange);
    toolbar.add(expandVChange);
    toolbar.add(expandEChange);
  }

  @Override
  public void setData(ChangeDataSet changeDataSet) {
    if (!(changeDataSet instanceof TreeChangeDataSet)) {
      throw new RuntimeException("Invalid ChangeDataSet");
    }

    // If something visualized so far, store its state
    if (actualChangeDataSet != null) {
      actualChangeDataSet.storeLayoutInfo(treeUI);
      actualChangeDataSet.setUserInfo(
          VIEWPORT_RECTANGLE_KEY, treeScroller.getViewport().getViewRect());
    }

    // Update actualChangeDataSet to the new one and set it as the tree model
    actualChangeDataSet = (TreeChangeDataSet) changeDataSet;
    treeUI.setModel(new DefaultTreeModel((TreeNode) actualChangeDataSet.getData()));

    // applyLayout returns true if successful, false is no saved layout exists
    if (!actualChangeDataSet.applyLayout(treeUI)) {
      // If no layout information exists, apply the default setting of node expansion
      expandNodes();
    }

    // if this information is present, reset the scrollpanes view to the one before
    if (actualChangeDataSet.hasUserInfo(VIEWPORT_RECTANGLE_KEY)) {
      treeScroller
          .getViewport()
          .scrollRectToVisible(
              (java.awt.Rectangle) actualChangeDataSet.getUserInfo(VIEWPORT_RECTANGLE_KEY));
    }

    // Update the nodes state to the one active in the checkboxes
    updateSimpleEChangeText(this.simpleEChangeTextBox.isSelected());
    updateShowID(this.showIDTextBox.isSelected());
  }

  /** Expand nodes depending on selected default behavior. */
  private void expandNodes() {
    TreeNode rootNode = (TreeNode) treeUI.getModel().getRoot();

    if (rootNode == null) {
      return;
    }

    // Store the selected path to reselect it afterwards
    TreePath selPath = treeUI.getSelectionModel().getSelectionPath();

    // Collapse all
    collapseAll();

    if (expandBehavior == ExpandBehavior.EXPAND_PROPAGATED_CHANGES) {
      // this means expand the root
      TreePath path = new TreePath(new TreeNode[] {rootNode});
      treeUI.expandPath(path);
    } else {
      Enumeration<? extends TreeNode> rootChildren = rootNode.children();
      while (rootChildren.hasMoreElements()) {
        TreeNode propChangeNode = rootChildren.nextElement();
        if (expandBehavior == ExpandBehavior.EXPAND_VCHANGES) {
          // this means expand all propagated changes
          TreePath path = new TreePath(new TreeNode[] {rootNode, propChangeNode});
          treeUI.expandPath(path);
        } else {
          Enumeration<? extends TreeNode> propChangeChildren = propChangeNode.children();
          while (propChangeChildren.hasMoreElements()) {
            TreeNode vChangeNode = propChangeChildren.nextElement();
            TreePath path = new TreePath(new TreeNode[] {rootNode, propChangeNode, vChangeNode});
            treeUI.expandPath(path);
          }
        }
      }
    }

    // reapply selection
    if (selPath != null) {
      // Assure it is visible, which may not be the case depending on the selected expansion
      // behaviour
      treeUI.expandPath(selPath);
      // make it selected
      treeUI.getSelectionModel().setSelectionPath(selPath);
    }
  }

  /** Collapse all nodes in the tree. */
  private void collapseAll() {
    int row = treeUI.getRowCount() - 1;
    while (row >= 0) {
      treeUI.collapseRow(row);
      row--;
    }
  }

  @Override
  public List<String> determineHighlightedCdsIds(
      String highlightID, List<ChangeDataSet> changeDataSets) {
    if (highlightID == null) {
      return null;
    }
    if (changeDataSets == null) {
      return null;
    }
    List<String> highlightedCdsIds = new Vector<String>();

    // Walk all changeDataSets and store their if if any of their nodes should be highlighted
    for (ChangeDataSet cds : changeDataSets) {
      if (cds == null || !(cds instanceof TreeChangeDataSet)) {
        continue;
      }
      TreeChangeDataSet tcds = (TreeChangeDataSet) cds;
      DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) tcds.getData();
      String cdsID = rootNode.toString();

      // Rely on the renderers decision upon highlighting a given id
      if (ChangeTreeNodeRenderer.shouldHighlightNode(highlightID, rootNode)) {
        highlightedCdsIds.add(cdsID);
      }
    }
    return highlightedCdsIds;
  }
}
