/*package simpleAtoB.generated;*/

import org.eclipse.emf.ecore.EObject;
import java.util.Map;
import java.util.HashMap;

import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.EcoreHelper;

class SimpleAtoB {
	/*
	 * Generated:
	 *     * Methods to check if mapping with index i holds (predicateCheck_i)
	 *     * check mapping methods for every class mapping (checkMapping*_i)
	 *     * Feature add and delete methods for every feature mapping
	 *
	 * Generic delete method:
	 *     1) Delete subtree recursively (starting with the leafs)
	 *     2) Delete corresponding (also starting with leafs)
	 *     3) Delete element intself
	 *     4) Trigger "mapping update checks" for every possibly
	 *        affected model element
	 */
	
	private Map<EObject, Integer> currentMappingID;
	
	public SimpleAtoB() {
		currentMappingID = new HashMap<EObject, Integer>();
	}
	
	public void checkElement(EObject context) {
		Integer lastMappingID = currentMappingID.get(context);
		Integer newMappingID = getMappingID(context);
		
		// only perform an action, if the state has changed
		if (lastMappingID != newMappingID) {
			if (lastMappingID == null) {
				// case 1: new model element
				
				callCreateMethod(newMappingID, context);
			} else if (newMappingID == null) {
				// case 2: deleted model element
				
				// delete corresponding ...
			} else {
				// case 3: different mapping
				
				// delete corresponding ...
				callCreateMethod(newMappingID, context);
			}
		}
	}
	
	public Integer getMappingID(EObject context) {if (predicateCheck_0(context))
		return 0;
	if (predicateCheck_1(context))
		return 1;
	return null;
	}
	
	public void callCreateMethod(int mappingID, EObject context) {
		switch (mappingID) {
	case 0:
		createB_0(context);
		break;
	case 1:
		createA_1(context);
		break;
			default:
				throw new IllegalArgumentException("Unknown mapping id: " + mappingID);
		}
	}
					
	// predicate checks
	/**
	 * @generated
	 */
	public boolean predicateCheck_0(EObject eObject_context) {
			if (!(eObject_context instanceof SimpleAtoBTest_SimplestB.B))
				return false;
				
			// type cast from EObject
			SimpleAtoBTest_SimplestB.B context = (SimpleAtoBTest_SimplestB.B) eObject_context;
		
			
			// all predicates hold
			return true;
	}
	/**
	 * @generated
	 */
	public boolean predicateCheck_1(EObject eObject_context) {
			if (!(eObject_context instanceof SimpleAtoBTest_SimplestA.A))
				return false;
				
			// type cast from EObject
			SimpleAtoBTest_SimplestA.A context = (SimpleAtoBTest_SimplestA.A) eObject_context;
		
			
			// all predicates hold
			return true;
	}
	
	// object creation
	/**
	 * @generated
	 */
	public void createB_0(EObject context) {
		SimpleAtoBTest_SimplestA.A result =
			/* create A */ (null);
			
		/* create correspondence betweeen context and result */;
			
		/* call initializers (where-declarations) */
	}
	/**
	 * @generated
	 */
	public void createA_1(EObject context) {
		SimpleAtoBTest_SimplestB.B result =
			/* create B */ (null);
			
		/* create correspondence betweeen context and result */;
			
		/* call initializers (where-declarations) */
	}
	
	// feature change methods
}
