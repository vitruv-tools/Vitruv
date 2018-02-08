/**
 * 
 */
package tools.vitruv.extensions.changevisualization.tree.decoder;

import java.awt.Component;

/**
 * @author andreas
 *
 */
public class ObjectFeatureDecoder implements FeatureDecoder{

	@Override 	
	public Class getDecodedClass(){
		return Object.class;
	}
		
	@Override
	public String decodeSimple(Object obj) {
		return obj.toString();
	}

	@Override
	public String decodeDetailed(Object obf) {
		return null;
	}

	@Override
	public Component decodeDetailedUI(Object obf) {
		return null;
	}		

}
