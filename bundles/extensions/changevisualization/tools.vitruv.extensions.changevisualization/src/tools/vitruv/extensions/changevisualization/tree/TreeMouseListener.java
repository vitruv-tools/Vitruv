/**
 * 
 */
package tools.vitruv.extensions.changevisualization.tree;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import java.awt.datatransfer.Clipboard;

import tools.vitruv.extensions.changevisualization.ChangeVisualization;
import tools.vitruv.extensions.changevisualization.ui.ChangeVisualizationUI;

/**
 * This MouseListener is used to react to Mouse Events for a ChangeTree
 * 
 * @author Andreas Loeffler 
 */
public class TreeMouseListener extends MouseAdapter {
	
	/**
	 * A MouseListener implementing the reaction to clicks on the tree-nodes	  
	 */
	public TreeMouseListener() {		
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
		if(e.getClickCount()!=1) return;
		JTree treeUI=getTreeUI(e);
		
		JPopupMenu popupMenu=new JPopupMenu();
		
		DefaultMutableTreeNode node=determineNode(e.getPoint(),treeUI);
		if(node!=null) {
			if(treeUI.getSelectionPath()==null||(treeUI.getSelectionPath().getLastPathComponent()!=node)) {
				//If the clicked node is not the selected one, select it
				treeUI.setSelectionPath(new TreePath(node.getPath()));
			}
			
			Object userObject = node.getUserObject();
			if(userObject!=null&& userObject instanceof ChangeNode) {
				ChangeNode changeNode=(ChangeNode)node.getUserObject();
				
				//double click left selects the object value
				final String highlightID=changeNode.getEObjectID();		
				JMenuItem menuItem=new JMenuItem("Highlight ID : "+highlightID);
				menuItem.setFont(ChangeVisualizationUI.DEFAULT_MENUITEM_FONT);
				menuItem.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						ChangeVisualization.getChangeVisualizationUI().getActiveChangesTab().setHighlightID(highlightID);								
					} 					
				});
				popupMenu.add(menuItem);
				popupMenu.addSeparator();
			}
		}
		
		JMenuItem searchItem=new JMenuItem("Enter manual highlight ID..");
		searchItem.setFont(ChangeVisualizationUI.DEFAULT_MENUITEM_FONT);
		searchItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String input=JOptionPane.showInputDialog(treeUI, "Please input the ID to search :");
				if(input==null) return;
				input=input.trim();
				ChangeVisualization.getChangeVisualizationUI().getActiveChangesTab().setHighlightID(input);				
			} 					
		});
		popupMenu.add(searchItem);
		popupMenu.addSeparator();
		
		JMenuItem resetSearchItem=new JMenuItem("Reset highlight ID");
		resetSearchItem.setFont(ChangeVisualizationUI.DEFAULT_MENUITEM_FONT);
		resetSearchItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				ChangeVisualization.getChangeVisualizationUI().getActiveChangesTab().setHighlightID(null);				
			} 					
		});
		resetSearchItem.setEnabled(ChangeVisualization.getChangeVisualizationUI().getActiveChangesTab().getHighlightID()!=null);
		popupMenu.add(resetSearchItem);
		
		JMenuItem copySearchItem=new JMenuItem("Copy highlight ID to clipboard");
		copySearchItem.setFont(ChangeVisualizationUI.DEFAULT_MENUITEM_FONT);
		copySearchItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				StringSelection stringSelection = new StringSelection(ChangeVisualization.getChangeVisualizationUI().getActiveChangesTab().getHighlightID());
			    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			    clipboard.setContents(stringSelection, null);
			} 					
		});
		copySearchItem.setEnabled(ChangeVisualization.getChangeVisualizationUI().getActiveChangesTab().getHighlightID()!=null);
		popupMenu.add(copySearchItem);
		
		popupMenu.show(treeUI, e.getPoint().x, e.getPoint().y);		
	}		

	/**
	 * Process a left click
	 * 
	 * @param e The MouseEvent associated with the click
	 */
	private void processLeftclick(MouseEvent e) {
		//Leftclicks not processed so far
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
