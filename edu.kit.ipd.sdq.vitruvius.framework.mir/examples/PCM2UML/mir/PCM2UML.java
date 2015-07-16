/*package mir.pcm2uml;*/

import org.eclipse.emf.ecore.EObject;
import java.util.Map;
import java.util.HashMap;

import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.EcoreHelper;

class PCM2UML {
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
	
	public PCM2UML() {
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
	if (predicateCheck_2(context))
		return 2;
	if (predicateCheck_3(context))
		return 3;
	if (predicateCheck_4(context))
		return 4;
	if (predicateCheck_5(context))
		return 5;
	if (predicateCheck_6(context))
		return 6;
	if (predicateCheck_7(context))
		return 7;
	if (predicateCheck_8(context))
		return 8;
	if (predicateCheck_9(context))
		return 9;
	if (predicateCheck_10(context))
		return 10;
	if (predicateCheck_11(context))
		return 11;
	return null;
	}
	
	public void callCreateMethod(int mappingID, EObject context) {
		switch (mappingID) {
	case 0:
		createOperation_0(context);
		break;
	case 1:
		createDataType_1(context);
		break;
	case 2:
		createParameter_2(context);
		break;
	case 3:
		createInterface_3(context);
		break;
	case 4:
		createOperationSignature_4(context);
		break;
	case 5:
		createDataType_5(context);
		break;
	case 6:
		createParameter_6(context);
		break;
	case 7:
		createOperationInterface_7(context);
		break;
	case 8:
		createNamedElement_8(context);
		break;
	case 9:
		createPackage_9(context);
		break;
	case 10:
		createInterface_10(context);
		break;
	case 11:
		createRepository_11(context);
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
			if (!(eObject_context instanceof org.eclipse.uml2.uml.Operation))
				return false;
				
			// type cast from EObject
			org.eclipse.uml2.uml.Operation context = (org.eclipse.uml2.uml.Operation) eObject_context;
		
			if (!(/* RFCWECls */ ((/* check if context.(reverse ownedOperation) corresponds
					with a OperationInterface */ false)
			))) return false;
			
			// all predicates hold
			return true;
	}
	/**
	 * @generated
	 */
	public boolean predicateCheck_1(EObject eObject_context) {
			if (!(eObject_context instanceof org.eclipse.uml2.uml.DataType))
				return false;
				
			// type cast from EObject
			org.eclipse.uml2.uml.DataType context = (org.eclipse.uml2.uml.DataType) eObject_context;
		
			if (!(/* RFCWECls */ ((/* check if context.(reverse type) corresponds
					with a OperationSignature */ false)
			
			 &&(/* check if context.(reverse type).(reverse ownedOperation) corresponds
					with a OperationInterface */ false)
			))) return false;
			
			// all predicates hold
			return true;
	}
	/**
	 * @generated
	 */
	public boolean predicateCheck_2(EObject eObject_context) {
			if (!(eObject_context instanceof org.eclipse.uml2.uml.Parameter))
				return false;
				
			// type cast from EObject
			org.eclipse.uml2.uml.Parameter context = (org.eclipse.uml2.uml.Parameter) eObject_context;
		
			if (!(/* RFCWECls */ ((/* check if context.(reverse ownedParameter) corresponds
					with a OperationSignature */ false)
			
			 &&(/* check if context.(reverse ownedParameter).(reverse ownedOperation) corresponds
					with a OperationInterface */ false)
			))) return false;
			
			// all predicates hold
			return true;
	}
	/**
	 * @generated
	 */
	public boolean predicateCheck_3(EObject eObject_context) {
			if (!(eObject_context instanceof org.eclipse.uml2.uml.Interface))
				return false;
				
			// type cast from EObject
			org.eclipse.uml2.uml.Interface context = (org.eclipse.uml2.uml.Interface) eObject_context;
		
			
			// all predicates hold
			return true;
	}
	/**
	 * @generated
	 */
	public boolean predicateCheck_4(EObject eObject_context) {
			if (!(eObject_context instanceof org.palladiosimulator.pcm.repository.OperationSignature))
				return false;
				
			// type cast from EObject
			org.palladiosimulator.pcm.repository.OperationSignature context = (org.palladiosimulator.pcm.repository.OperationSignature) eObject_context;
		
			if (!(/* RFCWECls */ ((/* check if context.(reverse signatures__OperationInterface) corresponds
					with a Interface */ false)
			))) return false;
			
			// all predicates hold
			return true;
	}
	/**
	 * @generated
	 */
	public boolean predicateCheck_5(EObject eObject_context) {
			if (!(eObject_context instanceof org.palladiosimulator.pcm.repository.DataType))
				return false;
				
			// type cast from EObject
			org.palladiosimulator.pcm.repository.DataType context = (org.palladiosimulator.pcm.repository.DataType) eObject_context;
		
			if (!(/* RFCWECls */ ((/* check if context.(reverse returnType__OperationSignature) corresponds
					with a Operation */ false)
			
			 &&(/* check if context.(reverse returnType__OperationSignature).(reverse signatures__OperationInterface) corresponds
					with a Interface */ false)
			))) return false;
			
			// all predicates hold
			return true;
	}
	/**
	 * @generated
	 */
	public boolean predicateCheck_6(EObject eObject_context) {
			if (!(eObject_context instanceof org.palladiosimulator.pcm.repository.Parameter))
				return false;
				
			// type cast from EObject
			org.palladiosimulator.pcm.repository.Parameter context = (org.palladiosimulator.pcm.repository.Parameter) eObject_context;
		
			if (!(/* RFCWECls */ ((/* check if context.(reverse parameters__OperationSignature) corresponds
					with a Operation */ false)
			
			 &&(/* check if context.(reverse parameters__OperationSignature).(reverse signatures__OperationInterface) corresponds
					with a Interface */ false)
			))) return false;
			
			// all predicates hold
			return true;
	}
	/**
	 * @generated
	 */
	public boolean predicateCheck_7(EObject eObject_context) {
			if (!(eObject_context instanceof org.palladiosimulator.pcm.repository.OperationInterface))
				return false;
				
			// type cast from EObject
			org.palladiosimulator.pcm.repository.OperationInterface context = (org.palladiosimulator.pcm.repository.OperationInterface) eObject_context;
		
			
			// all predicates hold
			return true;
	}
	/**
	 * @generated
	 */
	public boolean predicateCheck_8(EObject eObject_context) {
			if (!(eObject_context instanceof org.eclipse.uml2.uml.NamedElement))
				return false;
				
			// type cast from EObject
			org.eclipse.uml2.uml.NamedElement context = (org.eclipse.uml2.uml.NamedElement) eObject_context;
		
			if (!(/* RFCWECls */ ((/* check if context.(reverse member) corresponds
					with a Repository */ false)
			))) return false;
			
			// all predicates hold
			return true;
	}
	/**
	 * @generated
	 */
	public boolean predicateCheck_9(EObject eObject_context) {
			if (!(eObject_context instanceof org.eclipse.uml2.uml.Package))
				return false;
				
			// type cast from EObject
			org.eclipse.uml2.uml.Package context = (org.eclipse.uml2.uml.Package) eObject_context;
		
			
			// all predicates hold
			return true;
	}
	/**
	 * @generated
	 */
	public boolean predicateCheck_10(EObject eObject_context) {
			if (!(eObject_context instanceof org.palladiosimulator.pcm.repository.Interface))
				return false;
				
			// type cast from EObject
			org.palladiosimulator.pcm.repository.Interface context = (org.palladiosimulator.pcm.repository.Interface) eObject_context;
		
			if (!(/* RFCWECls */ ((/* check if context.(reverse interfaces__Repository) corresponds
					with a Package */ false)
			))) return false;
			
			// all predicates hold
			return true;
	}
	/**
	 * @generated
	 */
	public boolean predicateCheck_11(EObject eObject_context) {
			if (!(eObject_context instanceof org.palladiosimulator.pcm.repository.Repository))
				return false;
				
			// type cast from EObject
			org.palladiosimulator.pcm.repository.Repository context = (org.palladiosimulator.pcm.repository.Repository) eObject_context;
		
			
			// all predicates hold
			return true;
	}
	
	// object creation
	/**
	 * @generated
	 */
	public void createOperation_0(EObject context) {
		org.palladiosimulator.pcm.repository.OperationSignature result =
			/* create OperationSignature */ (null);
			
		/* create correspondence betweeen context and result */;
			
		/* call initializers (where-declarations) */
	}
	/**
	 * @generated
	 */
	public void createDataType_1(EObject context) {
		org.palladiosimulator.pcm.repository.DataType result =
			/* create DataType */ (null);
			
		/* create correspondence betweeen context and result */;
			
		/* call initializers (where-declarations) */
	}
	/**
	 * @generated
	 */
	public void createParameter_2(EObject context) {
		org.palladiosimulator.pcm.repository.Parameter result =
			/* create Parameter */ (null);
			
		/* create correspondence betweeen context and result */;
			
		/* call initializers (where-declarations) */
	}
	/**
	 * @generated
	 */
	public void createInterface_3(EObject context) {
		org.palladiosimulator.pcm.repository.OperationInterface result =
			/* create OperationInterface */ (null);
			
		/* create correspondence betweeen context and result */;
			
		/* call initializers (where-declarations) */
	}
	/**
	 * @generated
	 */
	public void createOperationSignature_4(EObject context) {
		org.eclipse.uml2.uml.Operation result =
			/* create Operation */ (null);
			
		/* create correspondence betweeen context and result */;
			
		/* call initializers (where-declarations) */
	}
	/**
	 * @generated
	 */
	public void createDataType_5(EObject context) {
		org.eclipse.uml2.uml.DataType result =
			/* create DataType */ (null);
			
		/* create correspondence betweeen context and result */;
			
		/* call initializers (where-declarations) */
	}
	/**
	 * @generated
	 */
	public void createParameter_6(EObject context) {
		org.eclipse.uml2.uml.Parameter result =
			/* create Parameter */ (null);
			
		/* create correspondence betweeen context and result */;
			
		/* call initializers (where-declarations) */
	}
	/**
	 * @generated
	 */
	public void createOperationInterface_7(EObject context) {
		org.eclipse.uml2.uml.Interface result =
			/* create Interface */ (null);
			
		/* create correspondence betweeen context and result */;
			
		/* call initializers (where-declarations) */
	}
	/**
	 * @generated
	 */
	public void createNamedElement_8(EObject context) {
		org.palladiosimulator.pcm.repository.Interface result =
			/* create Interface */ (null);
			
		/* create correspondence betweeen context and result */;
			
		/* call initializers (where-declarations) */
	}
	/**
	 * @generated
	 */
	public void createPackage_9(EObject context) {
		org.palladiosimulator.pcm.repository.Repository result =
			/* create Repository */ (null);
			
		/* create correspondence betweeen context and result */;
			
		/* call initializers (where-declarations) */
	}
	/**
	 * @generated
	 */
	public void createInterface_10(EObject context) {
		org.eclipse.uml2.uml.NamedElement result =
			/* create NamedElement */ (null);
			
		/* create correspondence betweeen context and result */;
			
		/* call initializers (where-declarations) */
	}
	/**
	 * @generated
	 */
	public void createRepository_11(EObject context) {
		org.eclipse.uml2.uml.Package result =
			/* create Package */ (null);
			
		/* create correspondence betweeen context and result */;
			
		/* call initializers (where-declarations) */
	}
	
	// feature change methods
	public void add_Operation_ownedOperation(org.eclipse.uml2.uml.Operation context,
		org.eclipse.uml2.uml.Operation elementToAdd) {
		
		// correspondence for elementToAdd should already exist
		// get correspondence elementToAdd_correspondence
		
		// get correspondence context_correspondence
		// context_correspondence.signatures__OperationInterface += elementToAdd_correspondence
	}
	
	public void remove_Operation_ownedOperation(org.eclipse.uml2.uml.Operation context,
		org.eclipse.uml2.uml.Operation deletedElement) {
	
		// correspondence for deletedElement should already exist
		// get correspondence deletedElement_correspondence
		
		// get correspondence context_correspondence
		// context_correspondence.signatures__OperationInterface -= deletedElement_correspondence
	}
	public void add_DataType_type(org.eclipse.uml2.uml.DataType context,
		org.eclipse.uml2.uml.Type elementToAdd) {
		
		// correspondence for elementToAdd should already exist
		// get correspondence elementToAdd_correspondence
		
		// get correspondence context_correspondence
		// context_correspondence.returnType__OperationSignature += elementToAdd_correspondence
	}
	
	public void remove_DataType_type(org.eclipse.uml2.uml.DataType context,
		org.eclipse.uml2.uml.Type deletedElement) {
	
		// correspondence for deletedElement should already exist
		// get correspondence deletedElement_correspondence
		
		// get correspondence context_correspondence
		// context_correspondence.returnType__OperationSignature -= deletedElement_correspondence
	}
	public void add_Parameter_ownedParameter(org.eclipse.uml2.uml.Parameter context,
		org.eclipse.uml2.uml.Parameter elementToAdd) {
		
		// correspondence for elementToAdd should already exist
		// get correspondence elementToAdd_correspondence
		
		// get correspondence context_correspondence
		// context_correspondence.parameters__OperationSignature += elementToAdd_correspondence
	}
	
	public void remove_Parameter_ownedParameter(org.eclipse.uml2.uml.Parameter context,
		org.eclipse.uml2.uml.Parameter deletedElement) {
	
		// correspondence for deletedElement should already exist
		// get correspondence deletedElement_correspondence
		
		// get correspondence context_correspondence
		// context_correspondence.parameters__OperationSignature -= deletedElement_correspondence
	}
	public void add_OperationSignature_signatures__OperationInterface(org.palladiosimulator.pcm.repository.OperationSignature context,
		org.palladiosimulator.pcm.repository.OperationSignature elementToAdd) {
		
		// correspondence for elementToAdd should already exist
		// get correspondence elementToAdd_correspondence
		
		// get correspondence context_correspondence
		// context_correspondence.ownedOperation += elementToAdd_correspondence
	}
	
	public void remove_OperationSignature_signatures__OperationInterface(org.palladiosimulator.pcm.repository.OperationSignature context,
		org.palladiosimulator.pcm.repository.OperationSignature deletedElement) {
	
		// correspondence for deletedElement should already exist
		// get correspondence deletedElement_correspondence
		
		// get correspondence context_correspondence
		// context_correspondence.ownedOperation -= deletedElement_correspondence
	}
	public void add_DataType_returnType__OperationSignature(org.palladiosimulator.pcm.repository.DataType context,
		org.palladiosimulator.pcm.repository.DataType elementToAdd) {
		
		// correspondence for elementToAdd should already exist
		// get correspondence elementToAdd_correspondence
		
		// get correspondence context_correspondence
		// context_correspondence.type += elementToAdd_correspondence
	}
	
	public void remove_DataType_returnType__OperationSignature(org.palladiosimulator.pcm.repository.DataType context,
		org.palladiosimulator.pcm.repository.DataType deletedElement) {
	
		// correspondence for deletedElement should already exist
		// get correspondence deletedElement_correspondence
		
		// get correspondence context_correspondence
		// context_correspondence.type -= deletedElement_correspondence
	}
	public void add_Parameter_parameters__OperationSignature(org.palladiosimulator.pcm.repository.Parameter context,
		org.palladiosimulator.pcm.repository.Parameter elementToAdd) {
		
		// correspondence for elementToAdd should already exist
		// get correspondence elementToAdd_correspondence
		
		// get correspondence context_correspondence
		// context_correspondence.ownedParameter += elementToAdd_correspondence
	}
	
	public void remove_Parameter_parameters__OperationSignature(org.palladiosimulator.pcm.repository.Parameter context,
		org.palladiosimulator.pcm.repository.Parameter deletedElement) {
	
		// correspondence for deletedElement should already exist
		// get correspondence deletedElement_correspondence
		
		// get correspondence context_correspondence
		// context_correspondence.ownedParameter -= deletedElement_correspondence
	}
	public void add_NamedElement_member(org.eclipse.uml2.uml.NamedElement context,
		org.eclipse.uml2.uml.NamedElement elementToAdd) {
		
		// correspondence for elementToAdd should already exist
		// get correspondence elementToAdd_correspondence
		
		// get correspondence context_correspondence
		// context_correspondence.interfaces__Repository += elementToAdd_correspondence
	}
	
	public void remove_NamedElement_member(org.eclipse.uml2.uml.NamedElement context,
		org.eclipse.uml2.uml.NamedElement deletedElement) {
	
		// correspondence for deletedElement should already exist
		// get correspondence deletedElement_correspondence
		
		// get correspondence context_correspondence
		// context_correspondence.interfaces__Repository -= deletedElement_correspondence
	}
	public void add_Interface_interfaces__Repository(org.palladiosimulator.pcm.repository.Interface context,
		org.palladiosimulator.pcm.repository.Interface elementToAdd) {
		
		// correspondence for elementToAdd should already exist
		// get correspondence elementToAdd_correspondence
		
		// get correspondence context_correspondence
		// context_correspondence.member += elementToAdd_correspondence
	}
	
	public void remove_Interface_interfaces__Repository(org.palladiosimulator.pcm.repository.Interface context,
		org.palladiosimulator.pcm.repository.Interface deletedElement) {
	
		// correspondence for deletedElement should already exist
		// get correspondence deletedElement_correspondence
		
		// get correspondence context_correspondence
		// context_correspondence.member -= deletedElement_correspondence
	}
}
