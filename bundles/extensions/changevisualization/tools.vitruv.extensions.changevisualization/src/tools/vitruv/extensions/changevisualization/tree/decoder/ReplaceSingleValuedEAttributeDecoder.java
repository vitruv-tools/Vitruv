package tools.vitruv.extensions.changevisualization.tree.decoder;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.extensions.changevisualization.utils.ModelHelper;
import tools.vitruv.framework.change.echange.EChange;

/**
 * A ChangeDecoder for ReplaceSingleValuedEAttribute - changes
 * 
 * @author Andreas Loeffler
 */
public class ReplaceSingleValuedEAttributeDecoder extends AbstractChangeDecoder {

	/**
	 * Generates a new ReplaceSingleValuedEAttributeDecoder
	 */
	public ReplaceSingleValuedEAttributeDecoder() {
		super("ReplaceSingleValuedEAttribute",new String[] {"oldValue","newValue","affectedFeature","affectedEObject"});		
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
		String eObjectName=extractEObjectName(eChange,structuralFeatures2values);
		if(eObjectName==null) {
			return getFallbackString(eChange);
		}

		//extract the name of the eAttribute
		String eAttributeName=extractEAttributeName(eChange,structuralFeatures2values);		
		if(eAttributeName==null) {
			return getFallbackString(eChange);
		}

		//Create the result string
		return eChange.eClass().getName()+" : "+eObjectName+" / "+eAttributeName+" : \""+oldValue+"\" ==> \""+newValue+"\"";
	}

	/**
	 * Extracts the name of the eAttribute that was changed
	 * 
	 * @param eChange The eChange
	 * @param structuralFeatures2values The relevant structural features
	 * @return The name of the affected eAttribute
	 */
	private String extractEAttributeName(EChange eChange, Map<String, Object> structuralFeatures2values) {
		Object eAttribute=structuralFeatures2values.get("affectedFeature");
		if(eAttribute==null||!(eAttribute instanceof EObject)) {
			return null;
		}
		eAttribute=ModelHelper.getStructuralFeature((EObject)eAttribute,"name");
		return String.valueOf(eAttribute);
	}

	/**
	 * Extracts the name of the eObject that was changed
	 * 
	 * @param eChange The eChange
	 * @param structuralFeatures2values The relevant structural features
	 * @return The name of the affected eObject
	 */
	private String extractEObjectName(EChange eChange, Map<String, Object> structuralFeatures2values) {
		Object eObject=structuralFeatures2values.get("affectedEObject");
		if(eObject==null||!(eObject instanceof EObject)) {
			return null;
		}
		eObject=ModelHelper.getStructuralFeature((EObject)eObject,"entityName");
		return String.valueOf(eObject);
	}	

}
