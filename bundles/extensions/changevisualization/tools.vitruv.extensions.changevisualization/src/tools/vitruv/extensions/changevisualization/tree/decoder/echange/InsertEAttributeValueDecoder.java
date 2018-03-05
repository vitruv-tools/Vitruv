/**
 * 
 */
package tools.vitruv.extensions.changevisualization.tree.decoder.echange;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.change.echange.EChange;

/**
 * @author andreas
 *
 */
public class InsertEAttributeValueDecoder extends AbstractChangeDecoder {
	
	/*
	 * The maximum length of newValue-Strings to display
	 */
	private static final int MAX_LENGTH=60;

	public InsertEAttributeValueDecoder() {
		super("InsertEAttributeValue",new String[] {"affectedFeature","affectedEObject","affectedEObjectID","newValue"});
	}

	@Override
	protected String generateString(EChange eChange, Map<String, Object> structuralFeatures2values) {
		
		//affectedFeature must exist
		if(!structuralFeatures2values.containsKey("affectedFeature")) {
			return getFallbackString(eChange);
		}
		
		//one of affectedEObject or affectedEObjectID must exist
		if(!structuralFeatures2values.containsKey("affectedEObjectID")&&!structuralFeatures2values.containsKey("affectedEObject")) {
			return getFallbackString(eChange);
		}

		//Extract newValue		
		String newValue=structuralFeatures2values.containsKey("newValue")?structuralFeatures2values.get("newValue").toString():"-";
		
		//newName is sometimes realy long, I truncate it in the list
		if(newValue!=null&&newValue.length()>MAX_LENGTH) {
			newValue=newValue.substring(0,MAX_LENGTH)+"...";
		}

		//Extract the entityName of the eObject
		String displayName=extractAffectedEObjectName(structuralFeatures2values);
		if(displayName==null||displayName.equals("null")) {
			//If name is not existent, i use the id
			displayName=(String) structuralFeatures2values.get("affectedEObjectID");
			if(displayName==null) {
				//If no name and no id can be determined, return the fallback string
				return getFallbackString(eChange);
			}
		}

		//extract the name of the eAttribute
		String eAttributeName=extractAffectedFeatureName(structuralFeatures2values);		
		if(eAttributeName==null) {
			return getFallbackString(eChange);
		}

		//Create the result string
		return eChange.eClass().getName()+" : \""+displayName+"\" / \""+eAttributeName+"\" : \""+newValue+"\"";
	}
}
