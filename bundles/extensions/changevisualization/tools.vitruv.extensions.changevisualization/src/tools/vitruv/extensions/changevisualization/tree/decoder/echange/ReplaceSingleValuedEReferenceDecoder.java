/**
 * 
 */
package tools.vitruv.extensions.changevisualization.tree.decoder.echange;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.extensions.changevisualization.utils.ModelHelper;
import tools.vitruv.framework.change.echange.EChange;

/**
 * @author andreas
 *
 */
public class ReplaceSingleValuedEReferenceDecoder extends AbstractChangeDecoder {

	public ReplaceSingleValuedEReferenceDecoder() {
		super("ReplaceSingleValuedEReference",new String[] {"oldValue","newValue","affectedFeature","affectedEObject"});
	}

	@Override
	protected String generateString(EChange eChange, Map<String, Object> structuralFeatures2values) {
		//At least oldValue or new Value must exist
		if(!structuralFeatures2values.containsKey("oldValue")&&!structuralFeatures2values.containsKey("newValue")) {
			return getFallbackString(eChange);
		}

		//affectedFeature and affectedEObject both must exist
		if(!structuralFeatures2values.containsKey("affectedFeature")||!structuralFeatures2values.containsKey("affectedEObject")) {
			return getFallbackString(eChange);
		}

		//Extract old/newValue		
		String oldValue=structuralFeatures2values.containsKey("oldValue")?structuralFeatures2values.get("oldValue").toString():"-";
		String newValue=structuralFeatures2values.containsKey("newValue")?structuralFeatures2values.get("newValue").toString():"-";

		//Extract the entityName of the eObject
		String eObjectName=extractAffectedEObjectName(structuralFeatures2values);
		if(eObjectName==null) {
			return getFallbackString(eChange);
		}

		//extract the name of the eAttribute
		String eAttributeName=extractAffectedFeatureName(structuralFeatures2values);		
		if(eAttributeName==null) {
			return getFallbackString(eChange);
		}

		//Create the result string
		return eChange.eClass().getName()+" : \""+eObjectName+"\" / \""+eAttributeName+"\" : \""+oldValue+"\" ==> \""+newValue+"\"";
	}	
}
