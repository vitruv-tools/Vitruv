package tools.vitruv.extensions.changevisualization.tree;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Enumeration;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;

import tools.vitruv.extensions.changevisualization.ui.ChangesTab;

/**
 * Used by the {@link ChangeTree} to visualize individual nodes.
 * 
 * @author Andreas Loeffler
 *
 */
public class ChangeTreeNodeRenderer extends DefaultTreeCellRenderer {
	/**
	 * Needed for eclipse to stop warning about serialVersionIds. This feature will
	 * never been used.
	 */
	private static final long serialVersionUID = 1L;

	// Different colors for the different echange types
	private static final Color EXISTENCE_ECHANGE_COLOR = Color.BLUE;
	private static final Color REFERENCE_ECHANGE_COLOR = Color.GREEN;
	private static final Color ATTRIBUTE_ECHANGE_COLOR = Color.ORANGE;
	private static final Color ROOT_ECHANGE_COLOR = Color.YELLOW;

	/**
	 * Creates an icon with a given color for open/close state
	 * 
	 * @param open  True if opened, false otherwise
	 * @param color The color for the icon
	 * @return The generated icon
	 */
	private static Icon createIcon(boolean open, Color color) {
		// open/close is not used so far
		Image image = new BufferedImage(16, 18, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) image.getGraphics();
		g2d.setColor(color);
		g2d.fillRoundRect(2, 2, 12, 14, 4, 4);
		g2d.setColor(Color.BLACK);
		g2d.drawRoundRect(2, 2, 12, 14, 4, 4);
		Icon icon = new ImageIcon(image);
		return icon;
	}

	// Icons visualizing the open state for the echange types
	// These icons are also used in the toolbar and are therefore public
	public static final Icon EXISTENCE_ECHANGE_OPEN_ICON = createIcon(true, EXISTENCE_ECHANGE_COLOR);
	public static final Icon REFERENCE_ECHANGE_OPEN_ICON = createIcon(true, REFERENCE_ECHANGE_COLOR);
	public static final Icon ATTRIBUTE_ECHANGE_OPEN_ICON = createIcon(true, ATTRIBUTE_ECHANGE_COLOR);
	public static final Icon ROOT_ECHANGE_OPEN_ICON = createIcon(true, ROOT_ECHANGE_COLOR);

	// Icons visualizing the closed state for the echange types
	private static final Icon EXISTENCE_ECHANGE_CLOSED_ICON = createIcon(false, EXISTENCE_ECHANGE_COLOR);
	private static final Icon REFERENCE_ECHANGE_CLOSED_ICON = createIcon(false, REFERENCE_ECHANGE_COLOR);
	private static final Icon ATTRIBUTE_ECHANGE_CLOSED_ICON = createIcon(false, ATTRIBUTE_ECHANGE_COLOR);
	private static final Icon ROOT_ECHANGE_CLOSED_ICON = createIcon(false, ROOT_ECHANGE_COLOR);

	/**
	 * Returns true if any sibling of the given node should be highlighted
	 * 
	 * A sibling should be highlighted if it is a change-node and its eObjectID is
	 * the actual highlighted one or if a sibling is a feature node that has the
	 * highlighted id in its valueString
	 * 
	 * @param highlightID The id to highlight
	 * @param node        The node to look at its siblings to determine the
	 *                    highlighted state
	 * @return True if we should be highlighted
	 */
	public static boolean shouldHighlightNode(String highlightID, DefaultMutableTreeNode node) {
		Enumeration<TreeNode> children = node.breadthFirstEnumeration();
		while (children.hasMoreElements()) {
			TreeNode child = children.nextElement();
			if (child instanceof DefaultMutableTreeNode) {
				DefaultMutableTreeNode mutableChild = (DefaultMutableTreeNode) child;
				if (mutableChild.getUserObject() != null) {
					if (mutableChild.getUserObject() instanceof ChangeNode) {
						if (highlightID.equals(((ChangeNode) mutableChild.getUserObject()).getEObjectID())) {
							return true;
						}
					} else if (mutableChild.getUserObject() instanceof FeatureNode) {
						if (mutableChild.getUserObject().toString().indexOf(highlightID) != -1) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * The default opened icon
	 */
	private Icon defaultOpenIcon;

	/**
	 * The default closed icon
	 */
	private Icon defaultClosedIcon;

	private TabHighlighting tabHighlighting;
	
	private boolean isActive = false;
	
	public ChangeTreeNodeRenderer(TabHighlighting tabHighlighting) {
		this.tabHighlighting = tabHighlighting;
	}
	
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
			int row, boolean hasFocus) {

		// On first call, save the default icons and default font
		if (defaultOpenIcon == null) {
			defaultOpenIcon = this.getDefaultOpenIcon();
			defaultClosedIcon = this.getDefaultClosedIcon();
		} else {
			// Reset Icons and font at any further call
			setOpenIcon(defaultOpenIcon);
			setClosedIcon(defaultClosedIcon);
			setForeground(tree.getForeground());
		}

		// Change icon if necessary
		updateIcons(tree, (DefaultMutableTreeNode) value, sel, expanded, leaf, row, hasFocus);

		// Get default visualization
		Component comp = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		// Just as an info: comp==this

		// if the node is highlighted, set the color after super.getTreeCell...
		// to overwrite any potential coloring of the superclasses implementation
		if (shouldHighlight(tree, row, value)) {
			comp.setForeground(ChangesTab.HIGHLIGHT_COLOR);
		}

		return comp;
	}

	/**
	 * Update the icon for the visualized node, if necessary
	 * 
	 * @param tree     The jtree that uses us to display the node
	 * @param node     The node displayed
	 * @param sel      Are we selected
	 * @param expanded Are we expanded
	 * @param leaf     Are we a leaf
	 * @param row      The row in the jtree's model
	 * @param hasFocus Do we have focus
	 */
	private void updateIcons(JTree tree, DefaultMutableTreeNode node, boolean sel, boolean expanded, boolean leaf,
			int row, boolean hasFocus) {
		if (node == null || node.getUserObject() == null) {
			return;// Use default icons
		}

		Object userObject = node.getUserObject();

		if (userObject instanceof ChangeNode) {
			ChangeNode changeNode = (ChangeNode) userObject;
			switch (changeNode.getChangeClass()) {
			case REFERENCE_ECHANGE:
				setOpenIcon(REFERENCE_ECHANGE_OPEN_ICON);
				setClosedIcon(REFERENCE_ECHANGE_CLOSED_ICON);
				break;
			case EXISTENCE_ECHANGE:
				setOpenIcon(EXISTENCE_ECHANGE_OPEN_ICON);
				setClosedIcon(EXISTENCE_ECHANGE_CLOSED_ICON);
				break;
			case ATTRIBUTE_ECHANGE:
				setOpenIcon(ATTRIBUTE_ECHANGE_OPEN_ICON);
				setClosedIcon(ATTRIBUTE_ECHANGE_CLOSED_ICON);
				break;
			case ROOT_ECHANGE:
				setOpenIcon(ROOT_ECHANGE_OPEN_ICON);
				setClosedIcon(ROOT_ECHANGE_CLOSED_ICON);
				break;
			default:
				// unknown class, nothing to do
				break;
			}
		}
	}

	/**
	 * Should we be highlighted?
	 * 
	 * True if we are visualizing a echange-node and its eObjectID is the actual
	 * highlighted one or if we visualize a feature node that has the highlighted id
	 * in its valueString or if we visualize a rootNode, propagatedChange,
	 * originalChange or consequentialChange node where any of the sibling nodes
	 * should be highlighted
	 * 
	 * @param tree  The jtree that uses us to display the node
	 * @param row   The row in the jtree's model
	 * @param value The value visualized (usually the tree-node)
	 * @return true if we should be highlighted
	 */
	private boolean shouldHighlight(JTree tree, int row, Object value) {
		if (!isActive) {
			return false;
		}
		
		String highlightID = tabHighlighting.getHighlightID();
		if (highlightID == null) {
			return false;
		}

		// We dont highlight null values or those who are not defaultMutableTreeNodes
		if (value == null || !(value instanceof DefaultMutableTreeNode)) {
			return false;
		}

		Object userObject = ((DefaultMutableTreeNode) value).getUserObject();

		// We dont highlight null userObjects
		if (userObject == null) {
			return false;
		}

		// highlight FeatureNodes if they contain the id
		if (userObject instanceof FeatureNode) {
			return userObject.toString().indexOf(highlightID) != -1;
		}

		// It should be highlighted if it is a ChangeNode with the highlightID
		if (userObject instanceof ChangeNode) {
			return highlightID.equals(((ChangeNode) userObject).getEObjectID());
		} else {
			// In this case we are a rootnode, a propagatedChange or original/consequential
			// Change nodes
			// we highlight if any of our siblings is highlighted
			return shouldHighlightNode(highlightID, (DefaultMutableTreeNode) value);
		}
	}

}
