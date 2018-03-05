/**
 * 
 */
package tools.vitruv.extensions.changevisualization.tree.decoder.echange;

import java.util.Map;

import tools.vitruv.framework.change.echange.EChange;

/**
 * @author andreas
 *
 */
public class CreateEObjectDecoder extends AbstractChangeDecoder {

	public CreateEObjectDecoder() {
		super("CreateEObject",new String[] {"affectedEObjectID","affectedEObject"});
	}

	@Override
	protected String generateString(EChange eChange, Map<String, Object> structuralFeatures2values) {
		//affectedEObject and affectedEObjectID must exist
		if(!structuralFeatures2values.containsKey("affectedEObjectID")||!structuralFeatures2values.containsKey("affectedEObject")) {
			return getFallbackString(eChange);
		}
	
		//Extract the entityName of the eObject
		String eObjectName=extractAffectedEObjectName(structuralFeatures2values);
		if(eObjectName==null) {
			return getFallbackString(eChange);
		}
		
		//The id of the affected eObject
		String id=(String) structuralFeatures2values.get("affectedEObjectID");

		//Create the result string
		return eChange.eClass().getName()+" : \""+eObjectName+"\" / \""+id+"\"";
	}

}
