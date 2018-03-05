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
public class RemoveEReferenceDecoder extends AbstractChangeDecoder {

	public RemoveEReferenceDecoder() {
		super("RemoveEReference",new String[] {"oldValue","oldValueID"});
	}

	@Override
	protected String generateString(EChange eChange, Map<String, Object> structuralFeatures2values) {
		//oldValue and oldValueID must exist
		if(!structuralFeatures2values.containsKey("oldValueID")||!structuralFeatures2values.containsKey("oldValue")) {
			return getFallbackString(eChange);
		}
	
		
		//Extract the entityName of the eObject
		String oldValue=extractEObjectName((EObject) structuralFeatures2values.get("oldValue"));
		if(oldValue==null) {
			return getFallbackString(eChange);
		}
		
		String oldValueID=(String) structuralFeatures2values.get("oldValueID");

		//Create the result string
		return eChange.eClass().getName()+" : \""+oldValue+"\" / \""+oldValueID+"\"";
	}

}
