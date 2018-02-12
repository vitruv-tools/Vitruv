/**
 * 
 */
package tools.vitruv.extensions.changevisualization.tree.decoder;

import java.awt.Component;

/**
 * Interface for FeatureDecoders to implement.
 * 
 * @author Andreas Loeffler
 */
public interface FeatureDecoder {

	/**
	 * Returns the class with elements this decoder is suitable to decode
	 * 
	 * @return The class to decode
	 */
	Class<?> getDecodedClass();

	/**
	 * Creates a simple and short text suitable for display in a JLabel.
	 *  
	 * @param obj The Object whose Information should be decoded
	 * @return The simple Info text, must not be null
	 */
	String decodeSimple(Object obj);

	/**
	 * Creates a detailed String suitable for display in a JTextArea. May contain multiple lines.
	 * Either implement (return not null) decodeDetailedUI or decodeDetailed or none.
	 * 
	 * @param obf The Object whose Information should be decoded
	 * @return The detailed Info text, may be null if the simple text is already sufficient
	 */
	String decodeDetailed(Object obf);


	/**
	 * Takes a given object and creates a Component that uses a individual display if the default behaviour of
	 * to show a detailed String is not sufficient. Either implement (return not null) 
	 * decodeDetailedUI or decodeDetailed or none.
	 * 
	 * @param obf The Object whose Information should be decoded
	 * @return A component displaying the detailed information,may be null
	 */
	Component decodeDetailedUI(Object obf);	

}
