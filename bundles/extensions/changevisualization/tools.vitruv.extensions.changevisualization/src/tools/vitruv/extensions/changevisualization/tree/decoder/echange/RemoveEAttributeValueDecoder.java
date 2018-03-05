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
public class RemoveEAttributeValueDecoder extends AbstractChangeDecoder {

	/*
	 * The maximum length of newValue-Strings to display
	 */
	private static final int MAX_LENGTH=60;

	public RemoveEAttributeValueDecoder() {
		super("RemoveEAttributeValue",new String[] {"affectedFeature","affectedEObject","affectedEObjectID","oldValue"});
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

		//Extract oldValue		
		String oldValue=structuralFeatures2values.containsKey("oldValue")?structuralFeatures2values.get("oldValue").toString():"-";
		
		//oldName is sometimes realy long, I truncate it in the list
		if(oldValue!=null&&oldValue.length()>MAX_LENGTH) {
			oldValue=oldValue.substring(0,MAX_LENGTH)+"...";
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
		return eChange.eClass().getName()+" : \""+displayName+"\" / \""+eAttributeName+"\" : \""+oldValue+"\"";
	}

}
