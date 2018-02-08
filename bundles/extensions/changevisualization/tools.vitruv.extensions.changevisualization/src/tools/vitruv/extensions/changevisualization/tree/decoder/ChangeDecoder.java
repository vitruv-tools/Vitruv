/**
 * 
 */
package tools.vitruv.extensions.changevisualization.tree.decoder;

import tools.vitruv.framework.change.echange.EChange;

/**
 * @author andreas
 *
 */
public interface ChangeDecoder {

	/**
	 * Creates a simple and short text suitable for display in a JLabel.
	 *  
	 * @param obj The Object whose Information should be decoded
	 * @return The simple Info text, must not be null
	 */
	String decode(EChange echange);
	
}
