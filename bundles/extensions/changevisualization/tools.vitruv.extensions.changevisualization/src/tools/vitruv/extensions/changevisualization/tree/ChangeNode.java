/**
 * 
 */
package tools.vitruv.extensions.changevisualization.tree;

import tools.vitruv.framework.change.echange.EChange;

/**
 * Collects all information regarding EChange-Nodes
 * 
 * @author andreas
 *
 */
public class ChangeNode {

	private String name;

	/**
	 * @param eChange 
	 * 
	 */
	public ChangeNode(EChange eChange) {
		name=eChange.eClass().getName();
		//Using ChangeDecoder not implemented yet, setting the name of the eClass so far
	}
	
	public String toString() {
		return name;
	}
	
}
