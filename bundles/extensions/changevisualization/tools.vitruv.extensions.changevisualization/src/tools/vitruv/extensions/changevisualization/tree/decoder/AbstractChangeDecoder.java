package tools.vitruv.extensions.changevisualization.tree.decoder;

import java.util.Hashtable;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EStructuralFeature;

import tools.vitruv.framework.change.echange.EChange;

/**
 * A basic change decoder which ensures that only change events of a given eClassName are processed
 * and that all required structural features exist. Their values might be null, but the feature itself must exist.
 * Subclasses must implement generateString() to create the actual result of the decoder in case all requirements are met
 * and should not override decode(EChange echange)
 * 
 * @author Andreas Loeffler
 */
public abstract class AbstractChangeDecoder implements ChangeDecoder {

	/**
	 * the required eClass name
	 */
	private final String eClassName;

	/**
	 * A List of all required structural features
	 */
	private final List<String> requiredFeatures;

	/**
	 * Creates and ChangeDecoder that ensures only change events of the given eClassName are processed
	 * and that all required structural features exist. If duplicate values of required structural features
	 * exist, this decoder cannot work and will always return the fallback result (eClass name).
	 *  
	 * @param eClassName
	 * @param requiredFeatures
	 */
	protected AbstractChangeDecoder(String eClassName, String[] requiredFeatures) {
		this.eClassName=eClassName;
		this.requiredFeatures=java.util.Arrays.asList(requiredFeatures);
	}

	@Override
	public String decode(EChange echange) {
		//If null as echange gets here or an echange with no eclass, return a fallback-error. this should never happen.
		//I prefer not throwing RuntimeExceptions or any other candidates that might exit the vm "just" because of this
		if(echange==null||echange.eClass()==null) {
			return "null value given";
		}

		//If the wrong eChanges get here, return the eClass name as fallback
		if(!eClassName.equals(echange.eClass().getName())) {
			return getFallbackString(echange);
		}

		//extract all structural features required
		Hashtable<String,Object> structuralFeatures2values=extractRequiredStructuralFeatures(echange);
		//All found null values have been added by the called method under the given key, remove them.
		//Further details can be found in extractRequiredStructuralFeatures
		int nullValues=(Integer)structuralFeatures2values.remove("NULL_VALUES_FOUND");		

		//If not all requiredFeatures where found, return the eClassname as fallback
		if(structuralFeatures2values.size()+nullValues!=requiredFeatures.size()) {
			return getFallbackString(echange);
		}

		//let the subclass derive the string from the required features.
		//values might be null, then they are not in the hashtable, but at least the feature existed
		return generateString(echange,structuralFeatures2values);
	}	

	private Hashtable<String, Object> extractRequiredStructuralFeatures(EChange echange) {
		//Generate the result hashtable
		Hashtable<String,Object> structuralFeatures2values=new Hashtable<String,Object>();		

		//It is necessary to count null values, since they will not occur in the hashtable. Without this information
		//it is not possible to decide whether all required structural features existed
		int nullValues=0;

		//Get all structural features existent
		EList<EStructuralFeature> features = echange.eClass().getEAllStructuralFeatures();

		//Walk them and process the required ones
		for(EStructuralFeature feature:features) {
			if(feature==null) continue;
			if(requiredFeatures.contains(feature.getName())){
				Object value=echange.eGet(feature);
				if(value==null) {
					nullValues++;
				}else {
					structuralFeatures2values.put(feature.getName(),value);
				}
			}
		}

		//the number of found null values is temporarily stored in the hashtable
		//and gets removed after this method returns
		structuralFeatures2values.put("NULL_VALUES_FOUND",nullValues);

		return structuralFeatures2values;
	}

	/**
	 * Generates the fallback String if anything went wrong
	 * @param echange
	 * @return
	 */
	protected String getFallbackString(EChange echange) {
		return echange.eClass().getName();
	}

	/**
	 * ChangeDecoder subclassing AbstractChangeDecoder should only implement this method and not override decode(Echange echange)
	 * This way the correct eClass is assured as well as all required structural features exist. If any requirement is not met,
	 * the eClass name of the eChange is returned as a fallback value.
	 * 
	 * @param eChange The eChange object decoded
	 * @param structuralFeatures2values The extracted values of all required structural features
	 * 
	 * @return The string used for display in a treenode of a jtree
	 */
	protected abstract String generateString(EChange eChange,Hashtable<String,Object> structuralFeatures2values);	

}
