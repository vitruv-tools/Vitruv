package tools.vitruv.extensions.changevisualization.tree.decoder;

import tools.vitruv.framework.change.echange.EChange;

/**
 * Interface to implement for ChangeDecoders that want to realize a eChange individual text to display in the tree
 * 
 * @author Andreas Loeffler
 */
public interface ChangeDecoder {

	/**
	 * Creates a simple and short text suitable for display in a JLabel.
	 *  
	 * @param eChange The eChange whose Information should be decoded
	 * @return The simple Info text, must not be null
	 */
	String decode(EChange echange);
	
}
