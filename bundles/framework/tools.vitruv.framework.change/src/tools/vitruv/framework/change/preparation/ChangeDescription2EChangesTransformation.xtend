package tools.vitruv.framework.change.preparation

import tools.vitruv.framework.change.echange.EChange
import java.util.List
import java.util.Map.Entry
import org.eclipse.emf.common.util.BasicEList
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.change.ChangeKind
import org.eclipse.emf.ecore.change.FeatureChange
import org.eclipse.emf.ecore.change.ListChange
import org.eclipse.emf.ecore.change.ResourceChange

import static extension tools.vitruv.framework.change.preparation.EMFModelChangeTransformationUtil.*
import static extension tools.vitruv.framework.util.bridges.CollectionBridge.*
import java.util.Collections
import tools.vitruv.framework.change.echange.feature.reference.AdditiveReferenceEChange
import tools.vitruv.framework.change.echange.feature.reference.UpdateReferenceEChange
import org.eclipse.emf.ecore.change.ChangeDescription
import java.util.ArrayList
import tools.vitruv.framework.change.echange.feature.attribute.SubtractiveAttributeEChange

public class ChangeDescription2EChangesTransformation {

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
	var ChangeDescription changeDescription
	val List<EChange> eChanges

	new(ChangeDescription changeDescription) {
		this.changeDescription = changeDescription
		this.eChanges = new BasicEList<EChange>
	}

	public def List<EChange> transform() {
		if (changeDescription == null) {
			return eChanges
		} else {

			changeDescription.resourceChanges?.forEach[addChangeForResourceChange(it)]

			// make the flat attach part of the change description deep by recursively creating changes for all non-default values
//			for (objectToAttach : changeDescription.getObjectsToAttach) {
//				recursivelyAddChangesForNonDefaultValues(objectToAttach)
//				addChangeForObjectToAttach(objectToAttach, changeDescription.getNewContainer(objectToAttach))
//			}
			// make the flat deletion part of the change description deep by recursively creating changes for all referenced objects
			/*for (objectToDetach : changeDescription.getObjectsToDetach) {
			 * 	recursivelyAddChangesForIndirectlyDeletedObjects(objectToDetach)
			 * 	addChangeForObjectToDetach(objectToDetach, changeDescription.getNewContainer(objectToDetach))
			 }*/
			// add all first level changes
			changeDescription.objectChanges?.forEach[addChangesForObjectChange(it)]

			for (objectToAttach : changeDescription.getObjectsToAttach) {
				recursivelyAddChangesForNonDefaultValues(objectToAttach)
//				addChangeForObjectToAttach(objectToAttach, changeDescription.getNewContainer(objectToAttach))
			}
			//changeDescription.applyAndReverse

			// sort changes: first deletions, then additions, then containment, then non-containment
			// val sortedEChanges = sortChanges(eChanges)
			// now that we have model elements to describe the forward change,
			// we do not need the forward change description,
			// but we need the model itself to be in the state after the change.
			// Therefore, we apply the change again which also reverts the change description that is no longer needed
//			changeDescription.applyAndReverse
			return eChanges
		}
	}

	def private void addChangeForResourceChange(ResourceChange resourceChange) {
		val resourceURI = resourceChange.resourceURI
		for (listChange : resourceChange.listChanges) {
			switch listChange.kind.value {
				case ChangeKind.ADD: addChangeForAddResourceChange(resourceChange, listChange, resourceURI)
				case ChangeKind.REMOVE: addChangeForRemoveResourceChange(resourceChange, listChange, resourceURI)
			}
		}
	}

	def private void addChangeForAddResourceChange(ResourceChange resourceChange, ListChange listChange, String resourceURI) {
		for (rootToAdd : listChange.referenceValues) {
			var oldRootContainer = rootToAdd.eContainer
			var oldRootResource = rootToAdd.eResource
			eChanges.add(
				EMFModelChangeTransformationUtil.createInsertRootChange(rootToAdd, oldRootContainer, oldRootResource,
					resourceURI))
		}
	}

	def private void addChangeForRemoveResourceChange(ResourceChange resourceChange, ListChange listChange,
		String resourceURI) {
		val rootElementListIndex = listChange.index
		// The resource is also in the state before the change was applied (like the model elements).
		// Therefore, we are able to obtain the removed root Element from it.
		val rootToRemove = resourceChange.resource.contents.get(rootElementListIndex)
		var newRootContainer = null//changeDescription.getNewContainer(rootToRemove)
		var newRootResource = null//changeDescription.getNewResource(rootToRemove)
		eChanges.add(
			EMFModelChangeTransformationUtil.createRemoveRootChange(rootToRemove, newRootContainer, newRootResource,
				resourceURI))
	}

	def private void recursivelyAddChangesForNonDefaultValues(EObject eObject) {
		if (eObject.hasNonDefaultValue()) {
			val metaclass = eObject.eClass
			for (feature : metaclass.EAllStructuralFeatures.filter(EAttribute)) {
				if (eObject.hasChangeableUnderivedPersistedNotContainingNonDefaultValue(feature)) {
					val recursiveChanges = EMFModelChangeTransformationUtil.createAdditiveChangesForValue(eObject, feature);
					eChanges.addAll(recursiveChanges);
				}
			}
			for (feature : metaclass.EAllStructuralFeatures.filter(EReference)) {
				if (eObject.hasChangeableUnderivedPersistedNotContainingNonDefaultValue(feature)) {
					val recursiveChanges = EMFModelChangeTransformationUtil.createAdditiveCreateChangesForValue(eObject, feature);
					eChanges.addAll(recursiveChanges);
					for (change : recursiveChanges.filter(AdditiveReferenceEChange)) {
						if ((change as UpdateReferenceEChange<?>).affectedFeature.containment) recursivelyAddChangesForNonDefaultValues(change.newValue as EObject);
					}
				}
			}
		}
	}

//	def private boolean isNotRootAndNotAlreadyProcessed(EObject eObject, EObject newContainer) {
//		val wasOrIsRoot = wasRootBeforeChange(eObject) || isRootAfterChange(eObject, newContainer)
//		if (wasOrIsRoot) {
//			var rootChangeForObjectProcessed = false
//			for (eChange : eChanges) {
//				rootChangeForObjectProcessed = rootChangeForObjectProcessed || switch eChange {
//					InsertRootEObject<?>: eChange.newValue == eObject
//					RemoveRootEObject<?>: eChange.oldValue == eObject
//					default: false
//				}
//			}
//			if (!rootChangeForObjectProcessed) {
//				throw new RuntimeException("No resource change was processed for this root element: '" + eObject + "'")
//			}
//		}
//		return !wasOrIsRoot
//	}

//	def private void recursivelyAddChangesForIndirectlyDeletedObjects(EObject parent) {
//		for (containmentReference : parent.eClass.EAllContainments) {
//			for (child : parent.getReferenceValueList(containmentReference)) {
//				eChanges.add(
//					EMFModelChangeTransformationUtil.
//						createSubtractiveEChangeForReferencedObject(parent, child, containmentReference))
//				recursivelyAddChangesForIndirectlyDeletedObjects(child)
//			}
//		}
//	}

	def private void addChangesForObjectChange(Entry<EObject, EList<FeatureChange>> entry) {
		val affectedEObject = entry.key
		val featureChanges = entry.value
		featureChanges.forEach[eChanges.addAll(createChangesForFeatureChange(affectedEObject, it.feature, it))]
	}

	def private dispatch List<? extends EChange> createChangesForFeatureChange(EObject affectedEObject,
		EReference affectedReference, FeatureChange featureChange) {
		if (affectedReference.isMany) {
			val listChanges = featureChange.listChanges
			val resultChanges = listChanges.mapFixed [
				createChangeForMultiReferenceChange(affectedEObject, affectedReference, it.index, it.kind,
					it.referenceValues)
			].flatten.toList
			val elementsReferencedAfterChange = featureChange.referenceValue
			if (elementsReferencedAfterChange == null && listChanges != null && listChanges.isEmpty) {
				val elementsReferencedBeforeChange = affectedEObject.getReferenceValueList(affectedReference)
				for (var index = 0; index < elementsReferencedBeforeChange.size; index++) {
					var elementReferencedBeforeChange = elementsReferencedBeforeChange.get(index)
					resultChanges.addAll(
						createChangeForMultiReferenceChange(affectedEObject, affectedReference, index,
							ChangeKind.REMOVE_LITERAL, #[elementReferencedBeforeChange]))
				}
			}
			return resultChanges
		} else {
			return createChangeForSingleReferenceChange(affectedEObject, affectedReference,
				featureChange.referenceValue)
		}
	}

	def List<EChange> createChangeForSingleReferenceChange(EObject affectedEObject, EReference affectedReference,
		EObject newReferenceValue) {
		val oldReferenceValue = affectedEObject.getReferenceValueList(affectedReference).claimNotMany();
		return #[createReplaceSingleValuedReferenceChange(affectedEObject, affectedReference, oldReferenceValue, newReferenceValue, false)];
	}

	def private List<EChange> createChangeForMultiReferenceChange(EObject affectedEObject, EReference affectedReference,
		int index, ChangeKind changeKind, List<EObject> referenceValues) {
		switch changeKind {
			case ChangeKind.ADD_LITERAL: referenceValues.mapFixed [
				EMFModelChangeTransformationUtil.createInsertReferenceChange(affectedEObject, affectedReference, index, it, false)
			]
			case ChangeKind.REMOVE_LITERAL: createChangeForRemoveReferenceChange(affectedEObject, affectedReference,
				index, affectedEObject.getReferenceValueList(affectedReference))
			default: Collections.emptyList()
		}
	}

	def private List<EChange> createChangeForRemoveReferenceChange(EObject affectedEObject,
		EReference affectedReference, int index, List<EObject> referenceValues) {
		val resultList = newArrayList()
		// for (referenceValue : referenceValues) {
		val referenceValue = referenceValues.get(index);
		var newContainer = null//changeDescription.getNewContainer(referenceValue)
		var newResource = null//changeDescription.getNewResource(referenceValue)
		resultList.add(
			EMFModelChangeTransformationUtil.createRemoveReferenceChange(affectedEObject, affectedReference, index,
				referenceValue, newContainer, newResource))
		// }
		return resultList
	}

	def private dispatch List<EChange> createChangesForFeatureChange(EObject affectedEObject,
		EAttribute affectedAttribute, FeatureChange featureChange) {
		if (affectedAttribute.isMany) {
			val listChanges = featureChange.listChanges
			val resultChanges = listChanges.mapFixed [
				createChangeForMultiAttributeChange(affectedEObject, affectedAttribute, it.index, it.kind, it.values)
			].flatten.toList
			val elementsReferencedAfterChange = featureChange.referenceValue
			if (elementsReferencedAfterChange == null && listChanges != null && listChanges.isEmpty) {
				val elementsReferencedBeforeChange = affectedEObject.getReferenceValueList(affectedAttribute)
				for (var index = 0; index < elementsReferencedBeforeChange.size; index++) {
					var elementReferencedBeforeChange = elementsReferencedBeforeChange.get(index)
					resultChanges.addAll(
						createChangeForMultiAttributeChange(affectedEObject, affectedAttribute, index,
							ChangeKind.REMOVE_LITERAL, #[elementReferencedBeforeChange]))
				}
			}
			if (affectedAttribute.isUnsettable && !featureChange.isSet) {
				val subtractiveChanges = resultChanges.filter(SubtractiveAttributeEChange);
				val List<SubtractiveAttributeEChange<EObject, Object>> typedChanges = new ArrayList<SubtractiveAttributeEChange<EObject, Object>>();
				for (change : subtractiveChanges) {
					typedChanges.add(change);
				}
				return #[createExplicitUnsetChange(typedChanges)];
			}
			return resultChanges
		} else {
			val SubtractiveAttributeEChange<EObject,Object> change = createChangeForSingleAttributeChange(affectedEObject, affectedAttribute, featureChange.value)
			if (affectedAttribute.isUnsettable && !featureChange.isSet) {
				return #[createExplicitUnsetChange(#[change])];
			}
			
			return #[change];
		}
	}

	def SubtractiveAttributeEChange<EObject, Object> createChangeForSingleAttributeChange(EObject affectedEObject, EAttribute affectedAttribute,
		Object newValue) {
		val oldReferenceValue = affectedEObject.getReferenceValueList(affectedAttribute).claimNotMany();
		return createReplaceSingleValuedAttributeChange(affectedEObject, affectedAttribute, oldReferenceValue, newValue);
	}

	def private List<EChange> createChangeForMultiAttributeChange(EObject affectedEObject, EAttribute affectedAttribute,
		int index, ChangeKind changeKind, List<Object> values) {
		switch changeKind {
			case ChangeKind.ADD_LITERAL : {
				val result = new ArrayList<EChange>();
				for (var i = 0; i < values.size; i++) {
					result += createInsertAttributeChange(affectedEObject, affectedAttribute, index + i, values.get(i))
				}
				return result;
			}
			case ChangeKind.REMOVE_LITERAL: 
				// Somehow, the change does not contain information about the removed values, so we cannot iterate them
				#[createRemoveAttributeChange(affectedEObject, affectedAttribute, index,
					affectedEObject.getReferenceValueList(affectedAttribute).get(index))]
			default: Collections.emptyList()
		}
	}


//	def private static List<EChange> sortChanges(List<EChange> changes) {
//		val deletions = new BasicEList
//		val additions = new BasicEList
//		val containmentChanges = new BasicEList
//		val rootChanges = new BasicEList
//		val remainingChanges = new BasicEList
//		for (change : changes) {
//			val target = switch change {
//				SubtractiveEReferenceChange<?> case change.isDelete: deletions
//				AdditiveEReferenceChange<?> case change.isIsCreate: additions
//				InsertRootEObject<?>,
//				RemoveRootEObject<?>: rootChanges
//				UpdateEReference<?> case change.isContainment: containmentChanges
//				default: remainingChanges
//			}
//			target.add(change)
//		}
//		val sortedChanges = new BasicEList(changes.size)
//		sortedChanges.addAll(deletions)
//		sortedChanges.addAll(additions)
//		sortedChanges.addAll(rootChanges)
//		sortedChanges.addAll(containmentChanges)
//		sortedChanges.addAll(remainingChanges)
//		return sortedChanges
//	}
}
