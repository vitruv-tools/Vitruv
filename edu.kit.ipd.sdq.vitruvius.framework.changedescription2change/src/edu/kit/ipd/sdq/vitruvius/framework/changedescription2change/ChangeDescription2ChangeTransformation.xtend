package edu.kit.ipd.sdq.vitruvius.framework.changedescription2change

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange
import java.util.List
import org.eclipse.emf.common.util.BasicEList
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.change.ChangeDescription
import org.eclipse.emf.ecore.EReference

class ChangeDescription2ChangeTransformation {
	
	def static List<EChange> transform(ChangeDescription changeDescription) {
		val eChanges = new BasicEList<EChange>
		changeDescription.objectChanges 
		
		// --shadow bullshit--
		// build a global result list that contains all "object changes" that changed a containment feature
		// for every object to attach: 
			// recursively find all changes to non default values 
			// create a change model element for the non-default value
			// add the new change to the list of containment or non-containment changes
			// add new objects that have to be created in order to set such a new value to a list of objects to add
			// add first all containment then all non-containment changes for this single object to attach to the global result list
		// end-for
		// add newly created objects to add to the list of "objects to attach" from the change description
		
		// build a second global result list that contains all "object changes" that changed a non-containment feature
		// remove all newly created non-containment changes from the first list and add them to the second list
		
		// --forward bullshit--
		// build a map m1 from changed objects to changes
		// copy the list that contains the "objects to attach" and our "objects to add"
		// for every change in the first list
			// iterate over all objects that are containment referenced from the changed object
				// if a referenced object is in the copied list 
					// remove it from the copied list
					// remember which container contains which new element in a map m2
				// end-if
			// end-iterate
		// end-for
		
		// create an empty result change list
		// create a new list of addable containers based on the containers mapped in m2
		// remove all "objects to attach" and our "objects to add" from the addable list
		// iterate over the addable list
			// remove the pivot container and add all changes mapped for it in m1 to the result
			// if m2 has an entry for the container 
				// add all mapped contained elements to the currently processed addable list
				// remove all containers without entries in m2 from the addable list
			// end-if
		// end-iterate
		// do a weird assert: exactly as many containers as were in m2 have been processed in total in the addable list (which was modified on the fly)
		// add all containment changes to the result
		// replace the first global list with the result
		
		// --deletion bullshit--
		// create an empty result change list
		// for every change in the first list
			// if the object affected by the change is in the list of "objects to detach" from the change description
				// add the change to the result
			// end-if
		// end-for
		// replace the first global list with the result
		
		// --shadow-deletion bullshit--
		// create a result change list base on the first global list
		// for every "object to detach" (comefrom recursion)
			// create a new list for additional changes
			// create a list for additional objects to detach
			// for every object referenced from the current object to detach
				// if the referenced object is not an "object to detach"
					// create a change model element for the referenced object
					// recursively find additional changes for the referenced object ("to detach")
					// add the new change to the list of additional changes
					// add the referenced object to the list of additional objects to detach
				// end-if
			// end-for
			// prepend the additional changes to the result list
		// end-for
		// modify the original "objects to detach" list by adding all additional objects to detach
		// store the result in a third list
		
		// perform another --deletion bullshit-- with the SECOND global list but replace the second list with the result
			
		// make an new list that starts with the elements of the third list and then has the elements of the second list
				
		val objectsToAttach = changeDescription.getObjectsToAttach
		objectsToAttach.forEach[recursivelyAddChangesForNonDefaultValues(it, eChanges)]
		// TODO MK inspire from old transformation
		val objectsToDetach = changeDescription.getObjectsToDetach
		val objectAndFeatureChangePairs = changeDescription.getObjectChanges().entrySet
		
		return null
	}
	
	def static void recursivelyAddChangesForNonDefaultValues(EObject eObject, List<EChange> eChanges) {
		if (hasNonDefaultValue(eObject)) {
			for (reference : eObject.eClass.EAllReferences) {
				if (hasChangeableUnderivedPersistedNotContainingNonDefaultValue(eObject, reference)) {
					eChanges.add(ChangeBridge.createChangeForReferencedObject(eObject, reference))
					getReferenceValueList(eObject, reference).forEach[recursivelyAddChangesForNonDefaultValues(it, eChanges)]
				}
			}
			// TODO MK attributes
			
		}
	}
	
	def static boolean hasNonDefaultValue(EObject eObject) {
		var hasNonDefaultValue = false
		for (feature : eObject.eClass.EAllStructuralFeatures) {
			if (isChangeableUnderivedPersistedNotContainingFeature(eObject, feature)) {
				hasNonDefaultValue = hasNonDefaultValue || hasNonDefaultValue(eObject, feature)
			}
		}
		return hasNonDefaultValue
	}
	
	def static boolean isChangeableUnderivedPersistedNotContainingFeature(EObject eObject, EStructuralFeature feature) {
        return feature.isChangeable() && !feature.isDerived() && !feature.isTransient() && feature != eObject.eContainingFeature();
	}
	
	def static boolean hasNonDefaultValue(EObject eObject, EStructuralFeature feature) {
		val value = eObject.eGet(feature)
		if (feature.many) {
			val eList = value as EList<?>
			return eList != null && !eList.isEmpty
		} else {
			// TODO MK equals for feature default value comparison okay or identity === needed?
			return value != feature.defaultValue
		}
	}
	
	def static boolean hasChangeableUnderivedPersistedNotContainingNonDefaultValue(EObject eObject, EStructuralFeature feature) {
		return isChangeableUnderivedPersistedNotContainingFeature(eObject, feature) && hasNonDefaultValue(eObject, feature)
	}
	
	def static EList<EObject> getReferenceValueList(EObject eObject, EReference reference) {
		return getValueList(eObject, reference) as EList<EObject>
	}
	
	def static EList<?> getValueList(EObject eObject, EStructuralFeature feature) {
		val value = eObject.eGet(feature)
		if (feature.many && value != null) {
			return value as EList<?>
		} else {
			return new BasicEList(#[value])
		}
	}
	
	def static boolean isContainmentFeature(EStructuralFeature feature) {
		// TODO MK move this method and also use it in change metamodel?
    	return feature instanceof EReference && (feature as EReference).isContainment
    }
}