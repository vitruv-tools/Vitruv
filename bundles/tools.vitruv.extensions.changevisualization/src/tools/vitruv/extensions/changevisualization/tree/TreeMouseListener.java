package tools.vitruv.extensions.changevisualization.tree;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import tools.vitruv.extensions.changevisualization.ui.ChangeVisualizationUI;

/**
 * This listener is used to react to mouse events for a {@link ChangeTree}
 * 
 * @author Andreas Loeffler 
 */
public class TreeMouseListener extends MouseAdapter {
	
	/**
	 * A listener implementing the reaction to clicks on the tree-nodes	  
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
	 * Process a right click.
	 * Displays a menu adapted to the actual state of the tree and the node clicked on.
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
		}
			
		addHighlightItem(popupMenu,node,treeUI);			
				
		addSearchItem(popupMenu,treeUI);
		
		addResetSearchItem(popupMenu);
		
		addCopyToClipboardItem(popupMenu);
		
		addInfoItem(popupMenu);
		
		popupMenu.show(treeUI, e.getPoint().x, e.getPoint().y);		
	}		

	/**
	 * Adds an item displaying the actual highlighted id
	 * 
	 * @param popupMenu The menu to add the item to
	 */
	private void addInfoItem(JPopupMenu popupMenu) {
		if(ChangeVisualizationUI.getInstance().getActiveChangesTab().getHighlightID()!=null) {
			popupMenu.addSeparator();
			JLabel info=new JLabel(" Highlighted ID = "+ChangeVisualizationUI.getInstance().getActiveChangesTab().getHighlightID());
			info.setFont(ChangeVisualizationUI.DEFAULT_MENUITEM_FONT);
			popupMenu.add(info);
		}
	}

	/**
	 * Adds an item used to copy the highlighted id to the System Clipboard
	 * 
	 * @param popupMenu The menu to add the item to
	 */
	private void addCopyToClipboardItem(JPopupMenu popupMenu) {
		JMenuItem copySearchItem=new JMenuItem("Copy highlight ID to clipboard");
		copySearchItem.setFont(ChangeVisualizationUI.DEFAULT_MENUITEM_FONT);
		copySearchItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				StringSelection stringSelection = new StringSelection(ChangeVisualizationUI.getInstance().getActiveChangesTab().getHighlightID());
			    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			    clipboard.setContents(stringSelection, null);
			} 					
		});
		copySearchItem.setEnabled(ChangeVisualizationUI.getInstance().getActiveChangesTab().getHighlightID()!=null);
		popupMenu.add(copySearchItem);
	}

	/**
	 * Adds an item to reset the actual highlighted id
	 * 
	 * @param popupMenu The menu to add the item to
	 */
	private void addResetSearchItem(JPopupMenu popupMenu) {
		JMenuItem resetSearchItem=new JMenuItem("Reset highlight ID");
		resetSearchItem.setFont(ChangeVisualizationUI.DEFAULT_MENUITEM_FONT);
		resetSearchItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				ChangeVisualizationUI.getInstance().getActiveChangesTab().setHighlightID(null);				
			} 					
		});
		resetSearchItem.setEnabled(ChangeVisualizationUI.getInstance().getActiveChangesTab().getHighlightID()!=null);
		popupMenu.add(resetSearchItem);
	}

	/**
	 * Adds an item displaying the actual highlighted id
	 * 
	 * @param popupMenu The menu to add the item to
	 * @param treeUI The tree that this listener observes
	 */
	private void addSearchItem(JPopupMenu popupMenu, final JTree treeUI) {
		JMenuItem searchItem=new JMenuItem("Enter manual highlight ID..");
		searchItem.setFont(ChangeVisualizationUI.DEFAULT_MENUITEM_FONT);
		searchItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String input=JOptionPane.showInputDialog(treeUI, "Please input the ID to search :");
				if(input==null) return;
				input=input.trim();
				ChangeVisualizationUI.getInstance().getActiveChangesTab().setHighlightID(input);				
			} 					
		});
		popupMenu.add(searchItem);
		popupMenu.addSeparator();
	}

	/**
	 * Adds an item displaying the actual highlighted id
	 * 
	 * @param popupMenu The menu to add the item to
	 * @param node The node clicked on, may be null
	 * @param treeUI The tree that this listener observes
	 */
	private void addHighlightItem(JPopupMenu popupMenu, DefaultMutableTreeNode node, JTree treeUI) {
		if(node==null) {
			//Nothing to add
			return;
		}		
		
		Object userObject = node.getUserObject();
		if(userObject!=null&& userObject instanceof ChangeNode) {
			ChangeNode changeNode=(ChangeNode)node.getUserObject();
			
			final String highlightID=changeNode.getEObjectID();		
			JMenuItem menuItem=new JMenuItem("Highlight ID : "+highlightID);
			menuItem.setFont(ChangeVisualizationUI.DEFAULT_MENUITEM_FONT);
			menuItem.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					ChangeVisualizationUI.getInstance().getActiveChangesTab().setHighlightID(highlightID);								
				} 					
			});
			popupMenu.add(menuItem);
			popupMenu.addSeparator();
		}
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
	 * @param point The clicked point
	 * @return The clicked node, null if no node exists at the given point
	 */
	private DefaultMutableTreeNode determineNode(Point point,JTree treeUI) {
		TreePath selPath = treeUI.getPathForLocation(point.x, point.y);
		if (selPath!=null) {
			DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)selPath.getLastPathComponent();
			return selectedNode;
		}else {        
			return null;
		}
	}


}
