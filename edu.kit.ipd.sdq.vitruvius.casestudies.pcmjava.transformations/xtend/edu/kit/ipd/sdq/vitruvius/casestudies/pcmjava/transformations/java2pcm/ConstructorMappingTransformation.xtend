package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.java2pcm

import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.emftext.language.java.members.Constructor

/**
 * The class is responsible for mapping a constructor. Since a constructor is only slightly different than a method we inherit from method.
 * TODO: change the inheritance hierarchy: Actually method mapping transformation should extend constructor mapping transformation since it does more than the constructor mapping
 */
class ConstructorMappingTransformation extends MethodMappingTransformation {
	override getClassOfMappedEObject() {
		return Constructor
	}

	override setCorrespondenceForFeatures() {
		super.setCorrespondenceForFeatures
	}

	override createEObject(EObject eObject) {
		return null
	}
	
	override removeEObject(EObject eObject) {
		return null
	}

	override updateSingleValuedEAttribute(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue) {
		return super.updateSingleValuedEAttribute(affectedEObject, affectedAttribute, oldValue, newValue)
	}

	/**
     *  called when a parameter has been added
     *  If the type of the parameter is a component interface or another 
     *  component an OperationRrovidedRole is added  
     */
	override createNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject newValue, int index, EObject[] newCorrespondingEObjects) {
		return super.createNonRootEObjectInList(newAffectedEObject, oldAffectedEObject, affectedReference, newValue,
			index, newCorrespondingEObjects)
	}

	/**
	 * called when a parameter has been deleted/changed
	 * If the parameter had a correspondence to an OperationRequiredRole delete it as well
	 */
	override deleteNonRootEObjectInList(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject oldValue, int index, EObject[] oldCorrespondingEObjectsToDelete) {
		super.deleteNonRootEObjectInList(newAffectedEObject, oldAffectedEObject, affectedReference, oldValue, index,
			oldCorrespondingEObjectsToDelete)
	}

}
