package tools.vitruv.extensions.changevisualization.tree;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.plaf.UIResource;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import tools.vitruv.extensions.changevisualization.ChangeVisualization;
import tools.vitruv.extensions.changevisualization.ui.ChangeVisualizationUI;
import tools.vitruv.extensions.changevisualization.ui.ChangesTab;
import tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange;
import tools.vitruv.framework.change.echange.root.RootEChange;

/**
 * Used by the JTree to visualize individual nodes. The implementation is regarded as a quick hack so far and will be replaced
 * soon. Do not consider during code review.
 * 
 * @author Andreas Loeffler
 *
 */
public class ChangeTreeNodeRenderer extends DefaultTreeCellRenderer{
	/**
	 * Needed for eclipse to stop warning about serialVersionIds. This feature will never been used. 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Color NAME_HIGHLIGHT_FOREGROUND_COLOR=Color.BLACK;
	private static final Color NAME_HIGHLIGHT_BACKGROUND_COLOR=new Color(200,200,255);
	private static final Color SEARCH_HIGHLIGHT_COLOR=new Color(200,255,200);
	
	public static final Color EXISTENCE_ECHANGE_COLOR=Color.BLUE;//new Color(255,255,200);
	public static final Color REFERENCE_ECHANGE_COLOR=Color.GREEN;//new Color(200,255,255);
	public static final Color ATTRIBUTE_ECHANGE_COLOR=Color.ORANGE;//new Color(255,200,255);
	public static final Color ROOT_ECHANGE_COLOR=Color.YELLOW;//new Color(200,255,200);
	
	private static Icon createIcon(boolean open, Color color) {
		Image image=new BufferedImage(16,18,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d=(Graphics2D) image.getGraphics();
		g2d.setColor(color);
		g2d.fillRoundRect(2, 2, 12, 14,4,4);
		g2d.setColor(Color.BLACK);
		g2d.drawRoundRect(2, 2, 12, 14,4,4);
		Icon icon=new ImageIcon(image);
		return icon;
	}
	
	public static final Icon EXISTENCE_ECHANGE_OPEN_ICON=createIcon(true,EXISTENCE_ECHANGE_COLOR);
	public static final Icon REFERENCE_ECHANGE_OPEN_ICON=createIcon(true,REFERENCE_ECHANGE_COLOR);
	public static final Icon ATTRIBUTE_ECHANGE_OPEN_ICON=createIcon(true,ATTRIBUTE_ECHANGE_COLOR);
	public static final Icon ROOT_ECHANGE_OPEN_ICON=createIcon(true,ROOT_ECHANGE_COLOR);
	
	public static final Icon EXISTENCE_ECHANGE_CLOSED_ICON=createIcon(false,EXISTENCE_ECHANGE_COLOR);
	public static final Icon REFERENCE_ECHANGE_CLOSED_ICON=createIcon(false,REFERENCE_ECHANGE_COLOR);
	public static final Icon ATTRIBUTE_ECHANGE_CLOSED_ICON=createIcon(false,ATTRIBUTE_ECHANGE_COLOR);
	public static final Icon ROOT_ECHANGE_CLOSED_ICON=createIcon(false,ROOT_ECHANGE_COLOR);
			
	private Icon defaultOpenIcon;
	private Icon defaultClosedIcon;
		
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel,
			boolean expanded,
			boolean leaf, int row,
			boolean hasFocus) {
		
		//On first call, save the default icons and default font
		if(defaultOpenIcon==null) {
			defaultOpenIcon=this.getDefaultOpenIcon();
			defaultClosedIcon=this.getDefaultClosedIcon();			
		}else {		
			//Reset Icons and font
			setOpenIcon(defaultOpenIcon);
			setClosedIcon(defaultClosedIcon);		
			setForeground(tree.getForeground());
		}
		
		//Change icon if necessary
		updateIcons(tree,(DefaultMutableTreeNode)value,sel,expanded,leaf,row,hasFocus);
				
		//Get default visualization
		Component comp=super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		//Just as an info: comp==this
		
		//if the node is highlighted, set the color after super.getTreeCell...
		//to overwrite any potential coloring of the superclasses implementation
		if(shouldHighlight(tree,row,value)) {
			comp.setForeground(ChangesTab.HIGHLIGHT_COLOR);
		}
		
		return comp;
	}

	private void updateIcons(JTree tree, DefaultMutableTreeNode node, boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		if(node==null||node.getUserObject()==null) {
			return;//Use default icons
		}
		
		Object userObject=node.getUserObject();		
		
		if(userObject instanceof ChangeNode) {
			ChangeNode changeNode=(ChangeNode) userObject;
			switch(changeNode.getChangeClass()) {
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
				//unknown class, nothing to do
				break;
			}
		}
	}
	
	private boolean shouldHighlight(JTree tree, int row, Object value) {
		//We dont highlight if there is nothing to highlight
		ChangesTab activeTab=ChangeVisualization.getChangeVisualizationUI().getActiveChangesTab();
		String highlightID=activeTab==null?null:activeTab.getHighlightID();
		if(highlightID==null ) {
			return false;
		}
		
		//We dont highlight null values or those who are not defaultMutableTreeNodes
		if(value==null ||!(value instanceof DefaultMutableTreeNode)) {
			return false;
		}
		
		Object userObject=((DefaultMutableTreeNode)value).getUserObject();
		
		//We dont highlight null userObjects
		if(userObject==null) {
			return false;
		}
		
		//highlight FeatureNodes if they contain the id
		if(userObject instanceof FeatureNode) {
			return userObject.toString().indexOf(highlightID)!=-1;
		}
				
		//It should be highlighted if it is a ChangeNode with the highlightID
		if(userObject instanceof ChangeNode) {
			return highlightID.equals(((ChangeNode)userObject).getEObjectID());
		}else {
			//In this case we are propagatedChange or original/consequential Change nodes
			//we highlight if any of our siblings is highlighted
			return shouldHighlightNode(highlightID,(DefaultMutableTreeNode)value);			
		}
	}

	private boolean shouldHighlightNode(String highlightID,DefaultMutableTreeNode node) {
		Enumeration children = node.children();
		while(children.hasMoreElements()) {
			DefaultMutableTreeNode child=(DefaultMutableTreeNode) children.nextElement();
			if(child.getUserObject()!=null &&child.getUserObject() instanceof ChangeNode) {
				if(highlightID.equals(((ChangeNode)child.getUserObject()).getEObjectID())) {
					return true;
				}
			}else if(shouldHighlightNode(highlightID,child)) {
				return true;
			}
		}
		return false;
	}

}
