/**
 * 
 */
package tools.vitruv.extensions.changevisualization.tree;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 * This MouseListener is used to react to Mouse Events for a ChangeTree
 * 
 * @author Andreas Loeffler 
 */
public class TreeMouseListener extends MouseAdapter {
	
	/**
	 * The renderer displaying the nodes
	 */
	private ChangeNodeRenderer changeEventTreeRenderer;

	/**
	 * A MouseListener implementing the reaction to clicks on the tree-nodes 
	 * @param changeEventTreeRenderer 
	 */
	public TreeMouseListener(ChangeNodeRenderer changeEventTreeRenderer) {
		this.changeEventTreeRenderer=changeEventTreeRenderer;
	}

	/**
	 * {@inheritDoc}
	 */
	public void mouseClicked(MouseEvent e) {
		if(e.getButton()==MouseEvent.BUTTON1) {
			processLeftclick(e);
		}else if(e.getButton()==MouseEvent.BUTTON3) {
			processRightclick(e);
		} 
	}

	/**
	 * Process a right click
	 * 
	 * @param e The MouseEvent associated with the click
	 */
	private void processRightclick(MouseEvent e) {
		//right click behaviour not implemented yet
	}		

	/**
	 * Process a left click
	 * 
	 * @param e The MouseEvent associated with the click
	 */
	private void processLeftclick(MouseEvent e) {
		if(e.getClickCount()!=2) return;
		JTree treeUI=getTreeUI(e);
		DefaultMutableTreeNode node=determineNode(e.getPoint(),treeUI);
		if(node==null) return;
		Object userObject = node.getUserObject();
		if(userObject==null) return;
		if(!(userObject instanceof FeatureNode)) return;
		FeatureNode featureNode=(FeatureNode)node.getUserObject();

		//double click left selects the object value
		String featureValue=featureNode.getValue();
		if(featureValue.length()==0) {
			changeEventTreeRenderer.resetHighligthedNodes();
			Toolkit.getDefaultToolkit().beep();
		}else {
			changeEventTreeRenderer.highlightNode(node);
		}

		//cause a repaint
		treeUI.repaint();
	}

	/**
	 * Determines the JTree that fired the given event
	 * @param e The MouseEvent fired
	 * @return The firing JTree
	 */
	private JTree getTreeUI(MouseEvent e) {
		return (JTree)e.getSource();
	}

	/**
	 * Determines the node at the given click point identified by the TreePath leading to it
	 * 
	 * @param p The clicked point
	 * @return The TreePath of the clicked node, null if no node exists at the given point
	 */
	private DefaultMutableTreeNode determineNode(Point p,JTree treeUI) {
		TreePath selPath = treeUI.getPathForLocation(p.x, p.y);
		if (selPath!=null) {
			DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) ((DefaultMutableTreeNode) selPath.getLastPathComponent());
			return selectedNode;
		}else {        
			return null;
		}
	}


}
