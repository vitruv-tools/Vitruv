package edu.kit.ipd.sdq.vitruvius.framework.changedescription2change

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange
import java.util.List
import org.eclipse.emf.common.util.BasicEList
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.change.ChangeDescription
import java.util.Map.Entry
import org.eclipse.emf.ecore.change.FeatureChange
import org.eclipse.emf.ecore.change.ListChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.SubtractiveEReferenceChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.AdditiveEReferenceChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.UpdateEReference

class ChangeDescription2ChangeTransformation {
	
		// BEGIN LONG VERSION OF REVERSE-ENGINEERED OLD MONITOR
		// --shadow bullshit-- = make the flat attach part of the change description deep by recursively creating changes for all non-default values (and listing the additional objects to attach, but without any effects)
		// build a global result list that contains all "object changes" that changed a containment feature
		// for every object to attach: 
			// recursively find all changes to non default values 
			// create a change model element for the non-default value
			// add the new change to the list of containment or non-containment changes
			// add new objects that have to be created in order to set such a new value to a list of objects to add
			// add first all containment then all non-containment changes for this single object to attach to the global result list
		// end-for
		// add newly created objects to add to the list of "objects to attach" from the change description
		//
		// build a second global result list that contains all "object changes" that changed a non-containment feature
		// remove all newly created non-containment changes from the first list and add them to the second list
		
		// --forward bullshit-- = "order" changes by changed objects (and do things without effects: map containers to containees, make a list of objects without containers, addable shit)
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
		//		
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
		
		// --deletion bullshit-- = make a list of changes for the "objects to detach"
		// create an empty result change list
		// for every change in the first list
			// if the object affected by the change is in the list of "objects to detach" from the change description
				// add the change to the result
			// end-if
		// end-for
		// replace the first global list with the result
		
		// --shadow-deletion bullshit-- = make the flat deletion part of the change description deep by recursively creating changes for all contained objects (and listing the additional objects to detach, but without any effects)
		// create a result change list base on the first global list
		// for every "object to detach" (comefrom recursion)
			// create a new list for additional changes
			// create a list for additional objects to detach
			// for every object containment referenced from the current object to detach
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
			
		// --order-bullshit-- = order changes: first deletions, then containment, then non-containment
		// make an new list that starts with the elements of the third list and then has the elements of the second list
		// END LONG VERSION OF REVERSE-ENGINEERED OLD MONITOR
	
	
		// BEGIN SHORT VERSION OF REVERSE-ENGINEERED OLD MONITOR
		// --shadow bullshit-- = make the flat attach part of the change description deep by recursively creating changes for all non-default values (and listing the additional objects to attach, but without any effects)
		// --forward bullshit-- = "order" all changes by changed objects (and do things without effects: map containers to containees, make a list of objects without containers, addable shit)
		// --deletion bullshit-- = make a list of changes for the "objects to detach"
		// --shadow-deletion bullshit-- = make the flat deletion part of the change description deep by recursively creating changes for all contained objects (and listing the additional objects to detach, but without any effects)
		// --order-bullshit-- = order changes: first deletions, then containment, then non-containment
		// END LONG VERSION OF REVERSE-ENGINEERED OLD MONITOR
		
	def static List<EChange> transform(ChangeDescription changeDescription) {
		val List<EChange> eChanges = new BasicEList<EChange>				
		if (changeDescription == null) {
			return eChanges
		} else {
			// FIXME MK turn resource changes into normal changes: nothing else but the change mm will be needed anymore
//			 changeDescription.resourceChanges?.forEach[...]
//
			// make the flat attach part of the change description deep by recursively creating changes for all non-default values
			val objectsToAttach = changeDescription.getObjectsToAttach
			objectsToAttach?.forEach[recursivelyAddChangesForNonDefaultValues(it, eChanges)]
			objectsToAttach?.forEach[addChangeForObjectToAttach(it, eChanges)]
			// make the flat deletion part of the change description deep by recursively creating changes for all referenced objects
			val objectsToDetach = changeDescription.getObjectsToDetach
			objectsToDetach?.forEach[recursivelyAddChangesForDeletedObjects(it, eChanges)]
			objectsToDetach?.forEach[addChangeForObjectToDetach(it, eChanges)]
			// add all first level changes
			changeDescription.objectChanges?.forEach[addConvertedChanges(it, eChanges)]
			// sort changes: first deletions, then additions, then containment, then non-containment
			val sortedEChanges = sortChanges(eChanges)
			return sortedEChanges
		}
	}
	
	def private static void recursivelyAddChangesForNonDefaultValues(EObject eObject, List<EChange> eChanges) {
		if (hasNonDefaultValue(eObject)) {
			val metaclass = eObject.eClass
			for (feature : metaclass.EAllStructuralFeatures) {
				if (hasChangeableUnderivedPersistedNotContainingNonDefaultValue(eObject, feature)) {
					eChanges.add(createChangeForValue(eObject, feature))
					getReferenceValueList(eObject, feature).forEach[recursivelyAddChangesForNonDefaultValues(it, eChanges)]
				}
			}
		}
	}
	
	def private static dispatch EChange createChangeForValue(EObject eObject,  EReference reference) {
		return EChangeBridge.createAdditiveEChangeForReferencedObject(eObject, reference)
	}
	
	def private static dispatch EChange createChangeForValue(EObject eObject,  EAttribute attribute) {
		return EChangeBridge.createAdditiveEChangeForAttributeValue(eObject, attribute)
	}
	
	def private static boolean hasNonDefaultValue(EObject eObject) {
		var hasNonDefaultValue = false
		for (feature : eObject.eClass.EAllStructuralFeatures) {
			if (isChangeableUnderivedPersistedNotContainingFeature(eObject, feature)) {
				hasNonDefaultValue = hasNonDefaultValue || hasNonDefaultValue(eObject, feature)
			}
		}
		return hasNonDefaultValue
	}
	
	def private static boolean isChangeableUnderivedPersistedNotContainingFeature(EObject eObject, EStructuralFeature feature) {
        return feature.isChangeable() && !feature.isDerived() && !feature.isTransient() && feature != eObject.eContainingFeature();
	}
	
	def private static boolean hasNonDefaultValue(EObject eObject, EStructuralFeature feature) {
		val value = eObject.eGet(feature)
		if (feature.many) {
			val eList = value as EList<?>
			return eList != null && !eList.isEmpty
		} else {
			// TODO MK is equals for feature default value comparison okay or is identity (===) needed?
			return value != feature.defaultValue
		}
	}
	
	def private static boolean hasChangeableUnderivedPersistedNotContainingNonDefaultValue(EObject eObject, EStructuralFeature feature) {
		return isChangeableUnderivedPersistedNotContainingFeature(eObject, feature) && hasNonDefaultValue(eObject, feature)
	}
	
	def private static dispatch EList<EObject> getReferenceValueList(EObject eObject, EStructuralFeature feature) {
		return new BasicEList
	}
	
	def private static dispatch EList<EObject> getReferenceValueList(EObject eObject, EReference reference) {
		return getValueList(eObject, reference) as EList<EObject>
	}
	
	def private static EList<?> getValueList(EObject eObject, EStructuralFeature feature) {
		val value = eObject.eGet(feature)
		if (feature.many && value != null) {
			return value as EList<?>
		} else {
			return new BasicEList(#[value])
		}
	}
	
	def private static boolean isContainmentFeature(EStructuralFeature feature) {
		// TODO MK move this method and also use it in change metamodel?
    	return feature instanceof EReference && (feature as EReference).isContainment
    }
    
	def static addChangeForObjectToAttach(EObject objectToAttach, List<EChange> eChanges) {
		eChanges.add(EChangeBridge.createAdditiveEChangeForEObject(objectToAttach))
	}
    
	def private static void recursivelyAddChangesForDeletedObjects(EObject eObject, List<EChange> eChanges) {
		for (containmentReference : eObject.eClass.EAllContainments) {
			eChanges.add(EChangeBridge.createSubtractiveEChangeForReferencedObject(eObject, containmentReference))
			getReferenceValueList(eObject,containmentReference).forEach[recursivelyAddChangesForDeletedObjects(it, eChanges)]
		}
	}
	
	def private static void addConvertedChanges(Entry<EObject, EList<FeatureChange>> entry, List<EChange> eChanges) {
		val affectedEObject = entry.key
		val featureChanges = entry.value
		featureChanges.forEach[eChanges.addAll(convertChange(affectedEObject, it.feature, it.value, it.referenceValue, it.listChanges))]
	}
	
	def private static List<EChange> convertChange(EObject affectedEObject, EStructuralFeature feature, Object object, EObject object2, EList<ListChange> list) {
		// FIXME MK CONVERSION LOGIC
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	def static addChangeForObjectToDetach(EObject objectToDetach, List<EChange> eChanges) {
		eChanges.add(EChangeBridge.createSubtractiveEChangeForEObject(objectToDetach))
	}
		
	def private static List<EChange> sortChanges(List<EChange> changes) {
		val deletions = new BasicEList
		val additions = new BasicEList
		val containmentChanges = new BasicEList
		val remainingChanges = new BasicEList
		for (change : changes) {
			if (change instanceof SubtractiveEReferenceChange
				&& (change as SubtractiveEReferenceChange).isDelete
			) {
				deletions.add(change)
			} else if (change instanceof AdditiveEReferenceChange<?>
				&& (change as AdditiveEReferenceChange<?>).isCreate
			) {
				additions.add(change)
			} else if (change instanceof UpdateEReference<?>
				&& (change as UpdateEReference<?>).isContainment
			) {
				containmentChanges.add(change)
			} else {
				remainingChanges.add(change)
			}
		}
		val sortedChanges = new BasicEList(changes.size)
		sortedChanges.addAll(deletions)
		sortedChanges.addAll(additions)
		sortedChanges.addAll(containmentChanges)
		sortedChanges.addAll(remainingChanges)
		return sortedChanges
	}
}