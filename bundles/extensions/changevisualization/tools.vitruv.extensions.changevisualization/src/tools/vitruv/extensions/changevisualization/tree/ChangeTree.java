/**
 * 
 */
package tools.vitruv.extensions.changevisualization.tree;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
 * @author andreas
 *
 */
public class ChangeTree extends ChangeComponent {

	private static enum ExpandBehavior{
		EXPAND_PROPAGETED_CHANGES,
		EXPAND_VCHANGES,
		EXPAND_ECHANGES
	}

	private JTree treeUI;
	private JTextArea detailsUI;

	private ExpandBehavior expandBehavior=ExpandBehavior.EXPAND_PROPAGETED_CHANGES;
	
	//private String highlightString=null;
	
	private final ChangeNodeRenderer changeEventTreeRenderer=new ChangeNodeRenderer();

	private MouseListener ml=new MouseAdapter() {
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

		private void processRightclick(MouseEvent e) {
			if(e.getClickCount()!=1) return;
			DefaultMutableTreeNode node=determineNode(e.getPoint());
			if(node==null) return;
			Object userObject = node.getUserObject();
			if(userObject==null) return;
			if(!(userObject instanceof FeatureNode)) return;
			FeatureNode featureNode=(FeatureNode)node.getUserObject();			
			//String details=featureNode.getDetails();
			//if(details==null) return;
			//JOptionPane.showMessageDialog(null, details,"Zusatzdaten",JOptionPane.INFORMATION_MESSAGE);			
		}

		private DefaultMutableTreeNode determineNode(Point p) {
			TreePath selPath = treeUI.getPathForLocation(p.x, p.y);
			if (selPath!=null) {
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) ((DefaultMutableTreeNode) selPath.getLastPathComponent());
				return selectedNode;
			}else {        
				return null;
			}
		}

		private void processLeftclick(MouseEvent e) {
			if(e.getClickCount()!=2) return;
			DefaultMutableTreeNode node=determineNode(e.getPoint());
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
			treeUI.repaint();
		}
	};
	private JSplitPane detailsSplitpane;
	private JScrollPane detailsScroller;
	private TreeChangeDataSet actualCds=null;
	private JScrollPane treeScroller;

	public ChangeTree() {
		super(new BorderLayout());
		createUI();

		treeUI.addMouseListener(ml);

		treeUI.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				//reset details display to default behavior
				detailsUI.setText("");
				int divLocation=detailsSplitpane.getDividerLocation();
				detailsSplitpane.setRightComponent(detailsScroller);
				
				//We have single tree selection mode
				TreePath path = e.getNewLeadSelectionPath();				
				if(path!=null) {					
					DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
					if(selectedNode!=null) {
						Object userObj=selectedNode.getUserObject();
						if(userObj!=null&&userObj instanceof FeatureNode) {
							String details=((FeatureNode)userObj).getDetails();
							Component detailsComp=((FeatureNode)userObj).getDetailsUI();
							if(details!=null) {
								detailsUI.setText(details);
							}else if(detailsComp!=null) {								
								detailsSplitpane.setRightComponent(detailsComp);
							}
						}
					}
				}
				
				detailsSplitpane.setDividerLocation(divLocation);
			}			
		});
		
		treeUI.setCellRenderer(changeEventTreeRenderer);

		//On my setup increase the fontSize
		{
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice[] gs = ge.getScreenDevices();
			if(gs!=null&&gs.length==3) {
				//treeUI.setFont(treeUI.getFont().deriveFont(18f));
			}
		}
	}	

	private void createUI() {
		detailsSplitpane=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

		treeUI=new JTree();
		treeUI.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		//treeUI.setRootVisible(false);

		treeScroller=new JScrollPane(treeUI);
		detailsSplitpane.add(treeScroller);
		//treeScroller.addMouseWheelListener(this);

		detailsUI=new JTextArea();	
		detailsUI.setEditable(false);
		detailsScroller=new JScrollPane(detailsUI);
		//JSplitPane scrollerPane=new JSplitPanel(JSplitPane.HO);		
		detailsScroller.setBorder(BorderFactory.createTitledBorder("Details"));
		//scrollerPane.setPreferredSize(new java.awt.Dimension(500,500));
		//scrollerPane.add(scroller,BorderLayout.CENTER);
		detailsSplitpane.add(detailsScroller);//Pane);		

		add(detailsSplitpane,BorderLayout.CENTER);
		detailsSplitpane.setDividerLocation(1100);

		add(createToolbar(),BorderLayout.SOUTH);
	}

	private Component createToolbar() {
		JPanel toolbar=new JPanel(new FlowLayout());

		toolbar.add(new JLabel(" Default expand behavior : "));

		//create expand behavior selectors
		final JCheckBox expandPChange=new JCheckBox("Expand root",expandBehavior==ExpandBehavior.EXPAND_PROPAGETED_CHANGES);
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
						expandBehavior=ExpandBehavior.EXPAND_PROPAGETED_CHANGES;
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
		
		if(actualCds!=null) {
			actualCds.storeLayoutInfo(treeUI);
			actualCds.setUserInfo("VIEWPORT_RECTANGLE",treeScroller.getViewport().getViewRect());
		}
		actualCds=(TreeChangeDataSet) cds;
		treeUI.setModel(new DefaultTreeModel((TreeNode)actualCds.getData()));
		
		if(!actualCds.applyLayout(treeUI)) {
			//If no layout information exists, apply the default setting of node expansion
			expandNodes();	
		}	
		
		//if this information is present, reset the scrollpanes view to the one before
		if(actualCds.hasUserInfo("VIEWPORT_RECTANGLE")) {
			treeScroller.getViewport().scrollRectToVisible((java.awt.Rectangle)actualCds.getUserInfo("VIEWPORT_RECTANGLE"));
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

		if(expandBehavior==ExpandBehavior.EXPAND_PROPAGETED_CHANGES) {
			//this means expand the root
			TreePath path=new TreePath(new TreeNode[] {
					rootNode
			});				
			treeUI.expandPath(path);			
		}else {		
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
			//Make it visible
			treeUI.expandPath(selPath);
			//make it selected
			//treeUI.getSelectionModel().setSelectionPath(selPath);
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
