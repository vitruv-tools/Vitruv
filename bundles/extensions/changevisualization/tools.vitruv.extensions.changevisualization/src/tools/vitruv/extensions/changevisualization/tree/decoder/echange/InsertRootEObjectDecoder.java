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
public class InsertRootEObjectDecoder extends AbstractChangeDecoder {

	public InsertRootEObjectDecoder() {
		super("InsertRootEObject",new String[] {"newValue","newValueID"});
	}

	@Override
	protected String generateString(EChange eChange, Map<String, Object> structuralFeatures2values) {
		//newValue and newValueID must exist
		if(!structuralFeatures2values.containsKey("newValueID")||!structuralFeatures2values.containsKey("newValue")) {
			return getFallbackString(eChange);
		}
	
		
		//Extract the entityName of the eObject
		String newValue=extractEObjectName((EObject) structuralFeatures2values.get("newValue"));
		if(newValue==null) {
			return getFallbackString(eChange);
		}
		
		String newValueID=(String) structuralFeatures2values.get("newValueID");

		//Create the result string
		return eChange.eClass().getName()+" : \""+newValue+"\" / \""+newValueID+"\"";
	}

}
