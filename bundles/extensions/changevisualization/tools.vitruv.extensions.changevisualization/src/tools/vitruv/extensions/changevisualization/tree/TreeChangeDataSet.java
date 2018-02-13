package tools.vitruv.extensions.changevisualization.tree;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.eclipse.emf.ecore.EStructuralFeature;

import tools.vitruv.extensions.changevisualization.ChangeDataSet;
import tools.vitruv.extensions.changevisualization.tree.decoder.MultipleFeatureProcessor;
import tools.vitruv.extensions.changevisualization.tree.decoder.OldValueNewValueProcessor;
import tools.vitruv.framework.change.description.PropagatedChange;
import tools.vitruv.framework.change.description.VitruviusChange;
import tools.vitruv.framework.change.echange.EChange;

/**
 * TreeChangeDataSet processes propagation results and extracts the data necessary for visualization
 * as a tree. It also stores layout information and applies existing layout data to trees
 * 
 * @author Andreas Loeffler
 */
public class TreeChangeDataSet extends ChangeDataSet {

	public static final String PROPAGATED_CHANGE_STRING="Propagated Change";
	public static final String ORIGINAL_CHANGE_STRING="Original Change";
	public static final String CONSEQUENTIAL_CHANGE_STRING="Consequential Change";
	
	/**
	 * Processors which apply multiple structural feature enhancements to structural feature leaf nodes
	 */
	private static List<MultipleFeatureProcessor> multipleFeatureProcessors=new Vector<MultipleFeatureProcessor>();
	
	//register additional multiple feature processors
	static {		
		//processor that combines oldValue / newValue pairs
		registerMultipleFeatureProcessor(new OldValueNewValueProcessor());	

		//info: i will implement a way to autoload all classes extending MultipleFeatureProcessor which reside in the decoder package
		//until then, they get registered manually
	}

	/**
	 * Can be called to register new processors implementing multiple structural feature enhancements
	 * 
	 * @param dec The processor for multi feature enhancements
	 */
	public static void registerMultipleFeatureProcessor(MultipleFeatureProcessor proc) {
		multipleFeatureProcessors.add(proc);
	}

	/**
	 * Identifies a TreeNode[] (==TreePath) of a given JTree with the help of a pathString.
	 * 
	 * @param treeUI The JTree
	 * @param pathString The pathString
	 * @return TreeNode[] identified by the pathString
	 */
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

	/**
	 * Converts a given TreePath of a JTree to a String. This String identifies the
	 * path and is used as the key to store and reset layout information
	 * 
	 * @param path The TreePath
	 * @param treeUI The JTree
	 * @return String identifying the path
	 */
	private static String getPathString(TreePath path, JTree treeUI) {
		TreeNode[] pathNodes=new TreeNode[path.getPathCount()];
		for(int n=0;n<pathNodes.length;n++) {
			pathNodes[n]=(TreeNode) path.getPathComponent(n);
		}
		return getPathString(pathNodes,treeUI);
	}

	/**
	 * Converts a given TreeNode[] of a JTree to a String. This String identifies the
	 * path and is used as the key to store and reset layout information
	 * 
	 * @param path The TreePath in form of a TreeNode[]
	 * @param treeUI The JTree
	 * @return String identifying the path
	 */
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

	/**
	 * The root node of the tree model
	 */
	private DefaultMutableTreeNode rootNode;

	/**
	 * Stores if a given Node is expanded in the ui. The Node is identified by a TreePath, and that by its pathString.
	 */
	private Map<String,Boolean> pathString2expanded=new Hashtable<String,Boolean>();

	/**
	 * List of all registered pathStrings
	 */
	private List<String> pathStrings=new Vector<String>();	


	/**
	 * Stores the actual selected Node, if any
	 */
	private String selectedPathString=null;


	/**
	 * Constructs a new TreeChangeDataSet with the given ID and className. The information is extracted from the given
	 * propation result.
	 * 
	 * @param cdsId The ID
	 * @param propagationResult The propation result
	 */
	public TreeChangeDataSet(String cdsId, List<PropagatedChange> propagationResult) {
		super(cdsId,propagationResult);
	}

	@Override
	public Object getData() {
		return rootNode;
	}

	/**
	 * Applies layout information to a given jtree's node, if this information exists
	 * 
	 * @param treeUI The JTree
	 * @return True if layout information existed (and has been applied), false otherwise
	 */
	public boolean applyLayout(JTree treeUI) {		
		if(!hasLayoutInfo()) {
			return false;
		}

		//Walk all path strings and reset the expansion state
		for(String pathString:pathStrings){
			boolean expanded=isExpanded(pathString);
			if(expanded) {
				treeUI.expandPath(new TreePath(getPath(treeUI,pathString)));
			}
		}

		//Set the selected path, if any 
		if(selectedPathString!=null) {
			treeUI.getSelectionModel().setSelectionPath(new TreePath(getPath(treeUI,selectedPathString)));
		}

		return true;
	}	

	/**
	 * Stores the layout information for this cds-nodes in the given JTree
	 * @param treeUI The JTree
	 */
	public void storeLayoutInfo(JTree treeUI) {
		//remove old layout information, if existent
		resetLayoutInfo();

		DefaultTreeModel model=(DefaultTreeModel) treeUI.getModel();		
		if(model==null||model.getRoot()==null) {
			//Nothing there to store
		}else {
			//Store layout information for the nodes
			DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
			storeLayoutInfo(root,treeUI);

			//Store the selected path, if existent
			TreePath selectedPath = treeUI.getSelectionPath();
			if(selectedPath!=null) {
				selectedPathString=TreeChangeDataSet.getPathString(selectedPath,treeUI);				
			}
		}

	}

	//Begin of all methods needed to extract the eChange info from the propagation result

	@Override
	protected void extractData(List<PropagatedChange> propagationResult) {

		//Create the root node
		rootNode=new DefaultMutableTreeNode(getCdsID());

		if(propagationResult==null) {
			//If no data exists, thats all
			return;
		}

		//Count the propagated changes in the list
		setNrPChanges(propagationResult.size());

		//Walk all propagated changes and create its nodes
		for(PropagatedChange propChange:propagationResult) {			
			encodeTree(propChange);			
		}
	}

	/**
	 * Creates the node for the given propagated change and appends it to the root node.
	 * Calls methods that create the original/consequential change child nodes for the propagted change 
	 * @param propChange The propagated change
	 */
	private void encodeTree(PropagatedChange propChange) {		
		//Create a propagated change childnode
		DefaultMutableTreeNode propChangeNode=new DefaultMutableTreeNode(PROPAGATED_CHANGE_STRING+" "+getNrPChanges());

		//Process original changes
		DefaultMutableTreeNode origNode = encodeTree(ChangeType.ORIGINAL_CHANGE,propChange.getOriginalChange());
		setNrOChanges(getNrOChanges()+origNode.getChildCount());//Count the original changes
		propChangeNode.add(origNode);		

		//Process consequential changes
		DefaultMutableTreeNode consequentialNode = encodeTree(ChangeType.CONSEQUENTIAL_CHANGE,propChange.getConsequentialChanges());
		setNrCChanges(getNrCChanges()+consequentialNode.getChildCount());//Count the consequential changes
		propChangeNode.add(consequentialNode);		

		//Add the propagated change node the root node
		rootNode.add(propChangeNode);		
	}

	/**
	 * Creates the node that represent original changes or consequential changes.
	 * It calls a method that creates the child nodes representing eChanges
	 *  
	 * @param changeType The type of change
	 * @param change The vitruvius change that gets encoded
	 * @return A TreeNode representing the given arguments
	 */
	private DefaultMutableTreeNode encodeTree(ChangeType changeType, VitruviusChange change) {
		//Create the change-node
		DefaultMutableTreeNode node=new DefaultMutableTreeNode();

		//Walk all eChanges and create their sub-trees
		for(EChange eChange:change.getEChanges()) {
			encodeTree(eChange,node);
		}

		//Depending on the type of change and its echange-count, set the correct change node name
		switch(changeType) {
		case ORIGINAL_CHANGE:
			node.setUserObject(ORIGINAL_CHANGE_STRING+(node.getChildCount()>1?"s":""));
			break;
		case CONSEQUENTIAL_CHANGE:
			node.setUserObject(CONSEQUENTIAL_CHANGE_STRING+(node.getChildCount()>1?"s":""));
			break;
		default:
			//this should never happens
			throw new RuntimeException("Unknown change type : "+changeType);
		}

		return node;
	}

	/**
	 * Creates a node representing an eChange and the nodes for the eChanges structural features.
	 * Multiple feature processors are applied here before the new eChange node is added to the parentNode.
	 *  
	 * @param eChange The eChange
	 * @param parentNode The parent node to add the new eChange node to
	 */	
	private void encodeTree(EChange eChange, DefaultMutableTreeNode parentNode) {	
		//Create the eChange node
		DefaultMutableTreeNode node=createEChangeNode(eChange);
				
		Map<String,Integer> featureName2index=new Hashtable<String,Integer>();
		List<Object> featureValues=new Vector<Object>();

		//Encode Structural Feature infos
		encodeStructuralFeatures(eChange,node,featureName2index,featureValues);	
		
		//Apply multiple structural feature processors
		applyMultipleFeatureProcessors(eChange,node,featureName2index,featureValues);		
		//Attention: Any code that comes after applyMultipleFeatureProcessor should be aware that there may now be nodes
		//that dont exist as basic structural features but are the result of multipleFeatureProcessors

		parentNode.add(node);	

	}

	/**
	 * Applies the registered multiple structural feature decoders until a stable state is reached.
	 * MultipleFeatureProcessors can potentially rely on the results of other processors, which therefore have to run first.
	 * This is accomplished by rerunning the process until no changes occurred (=stable state reached)
	 * 
	 * @param eChange The eChange whose structural features are processed
	 * @param parentNode The parent node 
	 * @param featureName2index FeatureName ==> Index
	 * @param featureValues Feature Values (The value of each feature is found at featureName2index index)
	 */
	private void applyMultipleFeatureProcessors(EChange eChange, DefaultMutableTreeNode parentNode,
			Map<String, Integer> featureName2index, List<Object> featureValues) {
		
		//Needed for multiple feature processors that rely on the result of other processors to work correctly
		//if anything happened, again is set to true and the whole process is started over with the resulting features and nodes
		boolean again=true;
		
		while(again) {
			again=false;//is set to true if any processor accepted the features and changed something			
			//if no processor accepted anything, we are finished and while is exited because again is still false
			
			for(MultipleFeatureProcessor proc:multipleFeatureProcessors) {
				if(proc.accepts(featureName2index.keySet())) {
					again=true; //rerun while
					
					//The process method updates all relevant information. For example removes the replace child nodes
					//or similar stuff. This can lead to "race conditions" if different multi feature decoders use at least
					//partially overlapping features which are then removed by the one first called. This cannot be prevented
					//and is regarded as a programming fault. If this should not be the case sometime in the future, one has to implement
					//a more advanced sequence algorithm here
					proc.process(eChange,parentNode,featureName2index,featureValues);
				}
			}			
		}
	}

	/**
	 * Creates nodes for all structural features a given eChange has. 
	 * featureName2index and featureValues is build up during creation to reflect the final state
	 *  
	 * @param eChange The eChange
	 * @param parentNode The parentNode to add the child nodes to
	 * @param @param featureName2index FeatureName ==> Index of the features value in featureValues
	 * @param featureValues Feature Values
	 */
	private void encodeStructuralFeatures(EChange eChange, DefaultMutableTreeNode parentNode, Map<String, Integer> featureName2index,
			List<Object> featureValues) {
		int index=0;
		for (EStructuralFeature feature:eChange.eClass().getEAllStructuralFeatures()) {
			if(feature==null) {
				continue;
			}	
			Object obj=eChange.eGet(feature);
			if(obj==null) {
				//Assures no null values exist in the FeatureNode-Constructor
				obj="-";				
			}
			DefaultMutableTreeNode featureNode=new DefaultMutableTreeNode(new FeatureNode(feature,obj));
			parentNode.add(featureNode);
			featureName2index.put(feature.getName(),index);
			featureValues.add(obj);
			index++;
		}
	}

	/**
	 * Create a node for a given eChange
	 * @param eChange The eChange
	 * @return The created TreeNode
	 */
	private DefaultMutableTreeNode createEChangeNode(EChange eChange) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(new ChangeNode(eChange));
		return node;
	}

	//End of all methods needed to extract the eChange info from the propagation result

	//All methods below are needed for layout processing

	/**
	 * Stores the layout information for a given node of the jtree
	 * @param node The node
	 * @param treeUI The jtree
	 */
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

	/**
	 * Resets the layout iformation. This method is called when new information gets stored
	 */
	private void resetLayoutInfo() {
		pathString2expanded.clear();
		pathStrings.clear();
		selectedPathString=null;
	}

	/**
	 * Returns whether layout information for this cds exists
	 * @return True if layout information exists
	 */
	private boolean hasLayoutInfo() {
		return pathString2expanded.size()>0;
	}

	/**
	 * Stores the information if the given pathString is expanded in the ui
	 * @param pathString The pathString identifying a node
	 * @param expanded Expansion state of the Node
	 */
	private void storeLayout(String pathString, boolean expanded) {
		pathString2expanded.put(pathString, expanded);
		pathStrings.add(pathString);
	}

	/**
	 * Checks whether a given pathString was expanded in the ui
	 * @param pathString The pathString
	 * @return True if expanded
	 */	
	private boolean isExpanded(String pathString) {
		Boolean expanded=pathString2expanded.get(pathString);
		if(expanded==null) {
			expanded=false;
		}
		return expanded;
	}	

}
