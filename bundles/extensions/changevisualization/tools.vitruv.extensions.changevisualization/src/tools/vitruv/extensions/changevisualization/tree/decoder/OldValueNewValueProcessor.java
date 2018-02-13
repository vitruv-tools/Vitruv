package tools.vitruv.extensions.changevisualization.tree.decoder;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;

import tools.vitruv.extensions.changevisualization.tree.FeatureNode;
import tools.vitruv.framework.change.echange.EChange;

/**
 * This multiple feature processors replaces pairs of oldValue and newValue structural features. 
 * 
 * @author Andreas Loeffler
 */
public class OldValueNewValueProcessor extends MultipleFeatureProcessor {

	/**
	 * The String identifying the old value feature 
	 */
	public static final String OLD_VALUE_SF="oldValue";
	
	/**
	 * The String identifying the new value feature 
	 */
	public static final String NEW_VALUE_SF="newValue";
	
	/**
	 * Constructs an OldValueNewValueProcessor
	 */
	public OldValueNewValueProcessor() {
		super(new String[]{OLD_VALUE_SF,NEW_VALUE_SF});
	}

	@Override
	public void process(final EChange eChange, final DefaultMutableTreeNode parentNode, Map<String, Integer> featureName2index,
			List<Object> featureValues) {
		
		//Get the relevant values
		Object oldValue=featureValues.get(featureName2index.get(OLD_VALUE_SF));
		Object newValue=featureValues.get(featureName2index.get(NEW_VALUE_SF));
		
		//Removes the old nodes consistently
		List<String> featuresToRemove=new Vector<String>();
		featuresToRemove.add(OLD_VALUE_SF);
		featuresToRemove.add(NEW_VALUE_SF);
		removeNodes(featuresToRemove,parentNode,featureName2index,featureValues);
		
		//Create the new node
		DefaultMutableTreeNode newNode= new DefaultMutableTreeNode(new FeatureNode("value changed","\""+oldValue+"\" ==> \""+newValue+"\""));
		
		//Add the new node consistently
		addNode(parentNode,newNode,featureName2index,featureValues);		
	}	

}
