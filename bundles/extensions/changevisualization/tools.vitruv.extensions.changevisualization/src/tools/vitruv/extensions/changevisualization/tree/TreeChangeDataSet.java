/**
 * 
 */
package tools.vitruv.extensions.changevisualization.tree;

import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.eclipse.emf.ecore.EStructuralFeature;

import tools.vitruv.extensions.changevisualization.ChangeDataSet;
import tools.vitruv.framework.change.description.PropagatedChange;
import tools.vitruv.framework.change.description.VitruviusChange;
import tools.vitruv.framework.change.echange.EChange;

/**
 * @author andreas
 *
 */
public class TreeChangeDataSet extends ChangeDataSet {

	/**
	 * @param cdsId
	 * @param className
	 * @param testName
	 * @param propagationResult
	 */
	public TreeChangeDataSet(String cdsId, String className, String testName,
			List<PropagatedChange> propagationResult) {
		super(cdsId, className, testName, propagationResult);
	}

	private DefaultMutableTreeNode rootNode;
	private Hashtable<String,Boolean> pathString2expanded=new Hashtable<String,Boolean>();
	private Vector<String> pathStrings=new Vector<String>();
	private boolean pathStringsOrdered=false;
	private String selectedPathString=null;

	@Override
	public Object getData() {
		return rootNode;
	}

	@Override
	protected void extractData(List<PropagatedChange> propagationResult) {
		rootNode=new DefaultMutableTreeNode(getCdsID());

		if(propagationResult==null) {			
			return;
		}
		
		setNrPChanges(propagationResult.size());//Count the propagated changes in the list
		
		for(PropagatedChange propChange:propagationResult) {			
			encodeTree(propChange);			
		}
	}

	private void encodeTree(PropagatedChange propChange) {		
		DefaultMutableTreeNode propChangeNode=new DefaultMutableTreeNode("Propagated Change "+getNrPChanges());

		DefaultMutableTreeNode origNode = encodeTree(ChangeType.ORIGINAL_CHANGE,propChange.getOriginalChange());
		setNrOChanges(getNrOChanges()+origNode.getChildCount());//Count the original changes
		propChangeNode.add(origNode);		

		DefaultMutableTreeNode consequentialNode = encodeTree(ChangeType.CONSEQUENTIAL_CHANGE,propChange.getConsequentialChanges());
		setNrCChanges(getNrCChanges()+consequentialNode.getChildCount());//Count the consequential changes
		propChangeNode.add(consequentialNode);		

		rootNode.add(propChangeNode);		
	}

	private DefaultMutableTreeNode encodeTree(ChangeType changeType, VitruviusChange change) {
		DefaultMutableTreeNode node=new DefaultMutableTreeNode();
		for(EChange eChange:change.getEChanges()) {
			encodeTree(eChange,node);
		}
		switch(changeType) {
		case ORIGINAL_CHANGE:
			node.setUserObject("Original Change"+(node.getChildCount()>1?"s":""));
			break;
		case CONSEQUENTIAL_CHANGE:
			node.setUserObject("Consequential Change"+(node.getChildCount()>1?"s":""));
			break;
		default:
			//this should never happens
			throw new RuntimeException("Unknown change type : "+changeType);
		}
		return node;
	}

	private void encodeTree(EChange eChange, DefaultMutableTreeNode parentNode) {		
		DefaultMutableTreeNode node=createEChangeNode(eChange);
		
		//Walk all accessible information and display
		//INFO:Many srclines will not stay here, but multi-feature-visualization is not fully implemented yet and combination
		//INFO:of old/newvalue is simulated here. After its implementation, more than half of the code is not needed anymore
		int oldValueIndex=-1;
		int newValueIndex=-1;
		String oldValue=null;
		String newValue=null;
		for (EStructuralFeature feature:eChange.eClass().getEAllStructuralFeatures()) {
			if(feature==null) {
				//Why are null features listed? this may/should never happen
				continue;
			}	

			Object obj=eChange.eGet(feature);
			if(obj==null) {
				obj="-";
				//Assures no null values exist in the FeatureNode-Constructor
			}

			if(feature.getName().equals("newValue")){
				newValueIndex=node.getChildCount();
				newValue=obj.toString();
			}else if(feature.getName().equals("oldValue")){
				oldValueIndex=node.getChildCount();
				oldValue=obj.toString();
			}

			DefaultMutableTreeNode featureNode=new DefaultMutableTreeNode(new FeatureNode(feature,obj));
			node.add(featureNode);
		}

		//If old and new exist, replace with a more intuitive visualization
		if(oldValueIndex!=-1&&newValueIndex!=-1) {
			int higher=Math.max(oldValueIndex, newValueIndex);
			node.remove(higher);
			int lower=Math.min(oldValueIndex, newValueIndex);
			node.remove(lower);
			DefaultMutableTreeNode featureNode=new DefaultMutableTreeNode(new FeatureNode("value changed","\""+oldValue+"\" ==> \""+newValue+"\""));
			node.add(featureNode);
		}


		parentNode.add(node);	

	}

	private DefaultMutableTreeNode createEChangeNode(EChange eChange) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(new ChangeNode(eChange));
		return node;
	}

	private void resetLayoutInfo() {
		pathString2expanded.clear();
		pathStrings.clear();
		selectedPathString=null;
	}

	private boolean hasLayoutInfo() {
		return pathString2expanded.size()>0;
	}

	private void storeLayout(String pathString, boolean expanded) {
		pathString2expanded.put(pathString, expanded);
		pathStrings.add(pathString);
		pathStringsOrdered=false;
	}

	private void setSelectedPathString(String pathString) {
		this.selectedPathString=pathString;
	}

	private Vector<String> getLayoutPathStrings() {
		if(!pathStringsOrdered) {			
			//We order the in such a way that resetting the expansion state works
			//in such way that always no node is set expanded while any of its parents
			//are not expanded yet
			java.util.Collections.sort(pathStrings,new Comparator<String>() {
				@Override
				public int compare(String p1, String p2) {
					//Storing the layout information is now wihtin this class, no ordering needed anymore
					//because the precedence is now completey under our control
					return 0;
				}				
			});
			pathStringsOrdered=true;
		}
		return pathStrings;
	}

	private boolean isExpanded(String pathString) {
		Boolean expanded=pathString2expanded.get(pathString);
		if(expanded==null) {
			expanded=false;
		}
		return expanded;
	}

	private String getSelectedPathString() {
		return selectedPathString;
	}

	public boolean applyLayout(JTree treeUI) {
		if(!hasLayoutInfo()) {
			return false;
		}

		Vector<String> pathStrings=getLayoutPathStrings();
		for(String pathString:pathStrings){
			boolean expanded=isExpanded(pathString);
			if(expanded) {
				//System.out.println(pathString+" ==> "+expanded);	
				treeUI.expandPath(new TreePath(getPath(treeUI,pathString)));
			}
		}
		if(getSelectedPathString()!=null) {
			System.out.println("Selected Path = "+getSelectedPathString());
			treeUI.getSelectionModel().setSelectionPath(new TreePath(getPath(treeUI,getSelectedPathString())));
		}

		return true;
	}	

	public void storeLayoutInfo(JTree treeUI) {
		//Is only called when an actualCds exists
		resetLayoutInfo();
		DefaultTreeModel model=(DefaultTreeModel) treeUI.getModel();		
		if(model==null||model.getRoot()==null) {
			//Nothing there to store
		}else {
			DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
			storeLayoutInfo(root,treeUI);
			TreePath selectedPath = treeUI.getSelectionPath();
			if(selectedPath!=null) {
				String selectedPathString=TreeChangeDataSet.getPathString(selectedPath,treeUI);
				setSelectedPathString(selectedPathString);
			}
		}

	}

	private void storeLayoutInfo(DefaultMutableTreeNode node, JTree treeUI) {
		TreeNode[] path = node.getPath();		
		String pathString=TreeChangeDataSet.getPathString(path,treeUI);
		boolean expanded=treeUI.isExpanded(new TreePath(path));
		storeLayout(pathString,expanded);
		for(int n=0;n<node.getChildCount();n++) {
			DefaultMutableTreeNode child=(DefaultMutableTreeNode) node.getChildAt(n);
			storeLayoutInfo(child,treeUI);
		}
	}	

	private static TreeNode[] getPath(JTree treeUI, String pathString) {
		String[] parts=pathString.split(Pattern.quote("|"));
		TreeNode[] path=new TreeNode[parts.length];
		TreeNode parent=(TreeNode) treeUI.getModel().getRoot();
		path[0]=parent;
		for(int n=1;n<parts.length;n++) {
			int index=Integer.parseInt(parts[n]);
			TreeNode node=(TreeNode)treeUI.getModel().getChild(parent,index);
			path[n]=node;
			parent=node;
		}
		return path;
	}

	private static String getPathString(TreePath path, JTree treeUI) {
		TreeNode[] pathNodes=new TreeNode[path.getPathCount()];
		for(int n=0;n<pathNodes.length;n++) {
			pathNodes[n]=(TreeNode) path.getPathComponent(n);
		}
		return getPathString(pathNodes,treeUI);
	}

	private static String getPathString(TreeNode[] path, JTree treeUI) {
		TreeNode parent=path[0];
		StringBuilder pathString=new StringBuilder("0");//parent is always the root here
		for(int n=1;n<path.length;n++) {
			TreeNode child=path[n];
			pathString.append("|"+treeUI.getModel().getIndexOfChild(parent, child));
			parent=child;
		}
		return pathString.toString();
	}	

}
