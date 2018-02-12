package tools.vitruv.extensions.changevisualization.tree;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import tools.vitruv.extensions.changevisualization.ChangeDataSet;
import tools.vitruv.extensions.changevisualization.ui.ChangeComponent;

/**
 * A ChangeTree visualizes propagation results in the form of a tree.
 * 
 * @author Andreas Loeffler
 */
public class ChangeTree extends ChangeComponent {

	/**
	 * Needed for eclipse to stop warning about serialVersionIds. This feature will never been used. 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Enumeration of all different default expand states of newly added cds
	 * 
	 * @author Andreas Loeffler
	 */
	private static enum ExpandBehavior{
		EXPAND_PROPAGATED_CHANGES,
		EXPAND_VCHANGES,
		EXPAND_ECHANGES
	}

	/**
	 * Key used to store the viewport rectangle of the treeScroller in the cds
	 */
	private static final String VIEWPORT_RECTANGLE_KEY="VIEWPORT_RECTANGLE";

	/**
	 * The JTree that actually visualizes the cds
	 */
	private JTree treeUI;

	/**
	 * When details of an selected node are just text details, they are displayed in this JTextArea
	 */
	private JTextArea detailsUI;

	/**
	 * The actual default expand behaviour
	 */
	private ExpandBehavior expandBehavior=ExpandBehavior.EXPAND_PROPAGATED_CHANGES;

	/**
	 * The renderer that renders the nodes of the tree
	 */
	private final ChangeNodeRenderer changeEventTreeRenderer=new ChangeNodeRenderer();

	/**
	 * The splitpane splitting tree from details
	 */
	private JSplitPane detailsSplitpane;

	/**
	 * The JScrollPane used to display detail-component bigger than the viewport available 
	 */
	private JScrollPane detailsScroller;

	/**
	 * The TreeMouseListener responding to mouse interaction
	 */
	private final TreeMouseListener ml;

	/**
	 * The listener processing tree selection events of the JTree component
	 */
	private final TreeSelectionListener tsl=new TreeSelectionListener() {
		@Override
		public void valueChanged(TreeSelectionEvent e) {
			//reset details display to default behavior
			detailsUI.setText("");
			int divLocation=detailsSplitpane.getDividerLocation();
			detailsSplitpane.setRightComponent(detailsScroller);

			//We have single selection mode ==> only one selected path
			TreePath path = e.getNewLeadSelectionPath();				

			//Update the detailsUI (if necessary)
			updateDetailsUI(path);

			//Restore the divider location, changing of the displayed components could have changed it
			detailsSplitpane.setDividerLocation(divLocation);
		}	

		/**
		 * Updates the detailsUI, if necessary
		 * 
		 * @param path The path pointing to the selected node
		 */
		private void updateDetailsUI(TreePath path){
			if(path!=null) {							
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
				if(selectedNode!=null) {
					Object userObj=selectedNode.getUserObject();
					if(userObj!=null) {
						if(userObj instanceof FeatureNode) {
							String details=((FeatureNode)userObj).getDetails();
							Component detailsComp=((FeatureNode)userObj).getDetailsUI();
							if(details!=null) {
								detailsUI.setText(details);
							}else if(detailsComp!=null) {								
								detailsSplitpane.setRightComponent(detailsComp);
							}
						}else if(userObj instanceof ChangeNode) {
							Component detailsComp=((ChangeNode)userObj).getDetailsUI();
							detailsSplitpane.setRightComponent(detailsComp);							
						}
					}
				}
			}
		}
	};

	/**
	 * The actual cds
	 */
	private TreeChangeDataSet actualCds=null;

	/**
	 * The JScrollPane used to scroll the tree, if necessary
	 */
	private JScrollPane treeScroller;

	/**
	 * Constructs a ChangeTree UI visualizing change events in the form of a tree
	 */
	public ChangeTree() {
		super(new BorderLayout());

		ml=new TreeMouseListener(changeEventTreeRenderer);
		createUI();

		treeUI.addMouseListener(ml);

		treeUI.getSelectionModel().addTreeSelectionListener(tsl);

		treeUI.setCellRenderer(changeEventTreeRenderer);

	}	

	/**
	 * Creates the UI
	 */
	private void createUI() {
		//Create the center
		detailsSplitpane=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

		treeUI=new JTree();
		treeUI.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		treeScroller=new JScrollPane(treeUI);
		detailsSplitpane.add(treeScroller);

		detailsUI=new JTextArea();	
		detailsUI.setEditable(false);
		detailsScroller=new JScrollPane(detailsUI);

		detailsScroller.setBorder(BorderFactory.createTitledBorder("Details"));
		detailsSplitpane.add(detailsScroller);		
		detailsSplitpane.setDividerLocation(1100);

		add(detailsSplitpane,BorderLayout.CENTER);

		//Create south == the toolbar
		add(createToolbar(),BorderLayout.SOUTH);
	}

	/**
	 * Creates the toolbar
	 * 
	 * @return The toolbar
	 */
	private Component createToolbar() {
		JPanel toolbar=new JPanel(new FlowLayout());

		toolbar.add(new JLabel(" Default expand behavior : "));

		//create expand behavior selectors
		final JCheckBox expandPChange=new JCheckBox("Expand root",expandBehavior==ExpandBehavior.EXPAND_PROPAGATED_CHANGES);
		final JCheckBox expandVChange=new JCheckBox("Expand vChanges",expandBehavior==ExpandBehavior.EXPAND_VCHANGES);
		final JCheckBox expandEChange=new JCheckBox("Expand eChanges",expandBehavior==ExpandBehavior.EXPAND_ECHANGES);

		//Assures only one is selected
		ButtonGroup bg1=new ButtonGroup();
		bg1.add(expandPChange);
		bg1.add(expandVChange);
		bg1.add(expandEChange);

		//Create listener
		ActionListener behaviorListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(((JCheckBox)e.getSource()).isSelected()){
					if(expandPChange==e.getSource()) {
						expandBehavior=ExpandBehavior.EXPAND_PROPAGATED_CHANGES;
					}else if(expandVChange==e.getSource()) {
						expandBehavior=ExpandBehavior.EXPAND_VCHANGES;
					}else if(expandEChange==e.getSource()) {
						expandBehavior=ExpandBehavior.EXPAND_ECHANGES;
					}	
					expandNodes();
				}else {
					//we react only to the selection
				}
			}			
		};

		//add listener
		expandPChange.addActionListener(behaviorListener);
		expandVChange.addActionListener(behaviorListener);
		expandEChange.addActionListener(behaviorListener);

		//add to ui
		toolbar.add(expandPChange);
		toolbar.add(expandVChange);
		toolbar.add(expandEChange);
		return toolbar;
	}

	/* (non-Javadoc)
	 * @see tools.vitruv.framework.util.changevisualization.ChangeVisualization#setData(tools.vitruv.framework.util.changevisualization.ChangeDataSet)
	 */
	@Override
	public void setData(ChangeDataSet cds) {
		if(!(cds instanceof TreeChangeDataSet)) {
			throw new RuntimeException("Invalid ChangeDataSet");
		}

		//If something visualized so far, store its state 
		if(actualCds!=null) {
			actualCds.storeLayoutInfo(treeUI);
			actualCds.setUserInfo(VIEWPORT_RECTANGLE_KEY,treeScroller.getViewport().getViewRect());
		}

		//Update actual cds to the new one and set it as the tree model
		actualCds=(TreeChangeDataSet) cds;
		treeUI.setModel(new DefaultTreeModel((TreeNode)actualCds.getData()));

		//applyLayout returns tree if successfull, false is no saved layout exists
		if(!actualCds.applyLayout(treeUI)) {
			//If no layout information exists, apply the default setting of node expansion
			expandNodes();	
		}	

		//if this information is present, reset the scrollpanes view to the one before
		if(actualCds.hasUserInfo(VIEWPORT_RECTANGLE_KEY)) {
			treeScroller.getViewport().scrollRectToVisible((java.awt.Rectangle)actualCds.getUserInfo(VIEWPORT_RECTANGLE_KEY));
		}
	}

	/**
	 * Expand nodes depending on selected behavior
	 */
	private void expandNodes() {		
		TreeNode rootNode = (TreeNode)treeUI.getModel().getRoot();

		if(rootNode==null) return;

		//Store the selected path to reselect it afterwards
		TreePath selPath = treeUI.getSelectionModel().getSelectionPath();

		//Collapse all
		collapseAll();

		if(expandBehavior==ExpandBehavior.EXPAND_PROPAGATED_CHANGES) {
			//this means expand the root
			TreePath path=new TreePath(new TreeNode[] {
					rootNode
			});				
			treeUI.expandPath(path);			
		}else {		
			
			@SuppressWarnings("unchecked")
			Enumeration<TreeNode> rootChildren = rootNode.children();
			while(rootChildren.hasMoreElements()) {
				TreeNode propChangeNode=rootChildren.nextElement();
				if(expandBehavior==ExpandBehavior.EXPAND_VCHANGES) {
					//this means expand all propagated changes
					TreePath path=new TreePath(new TreeNode[] {
							rootNode,
							propChangeNode
					});				
					treeUI.expandPath(path);
				}else {					
					@SuppressWarnings("unchecked")
					Enumeration<TreeNode> propChangeChildren = propChangeNode.children();
					while(propChangeChildren.hasMoreElements()) {
						TreeNode vChangeNode=propChangeChildren.nextElement();
						TreePath path=new TreePath(new TreeNode[] {
								rootNode,
								propChangeNode,
								vChangeNode
						});				
						treeUI.expandPath(path);
					}
				}
			}
		}

		//reapply selection
		if(selPath!=null) {
			//Assure it is visible, which may not be the case depending on the selected expansion behaviour
			treeUI.expandPath(selPath);
			//make it selected
			treeUI.getSelectionModel().setSelectionPath(selPath);
		}
	}

	private void collapseAll() {
		int row = treeUI.getRowCount() - 1;
		while (row >= 0) {
			treeUI.collapseRow(row);
			row--;
		}
	}

}
