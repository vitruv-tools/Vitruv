package edu.kit.ipd.sdq.vitruvius.framework.changedescription2change

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange
import edu.kit.ipd.sdq.vitruvius.framework.util.changes.ForwardChangeDescription
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

import static extension edu.kit.ipd.sdq.vitruvius.framework.changedescription2change.ChangeDescription2ChangeUtil.*
import static extension edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge.*
import java.util.Collections
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change
import java.util.ArrayList
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange

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
	var List<ForwardChangeDescription> changeDescriptions
	val List<EChange> eChanges

	new(List<ForwardChangeDescription> changeDescriptions) {
		this.changeDescriptions = changeDescriptions
		this.eChanges = new BasicEList<EChange>
	}

	public def List<Change> getChanges() {
		getChanges(null);
	}

	public def List<Change> getChanges(VURI uri) {
		return encloseObjectsInChange(transform(), uri);
	}

	private static def List<Change> encloseObjectsInChange(Iterable<EChange> eChanges, VURI uri) {
		val result = new ArrayList<Change>();
		for (EChange e : eChanges) {
			result.add(new EMFModelChange(e, uri));
		}
		return result;
	}

	private def List<EChange> transform() {
		if (changeDescriptions == null) {
			return eChanges
		} else {
			for (changeDescription : changeDescriptions) {
				eChanges += new SingleChangeDescription2ChangeTransformation(changeDescription).transform();
			}
						
			//changeDescription.resourceChanges?.forEach[addChangeForResourceChange(it)]

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
//			changeDescription.objectChanges?.forEach[addChangesForObjectChange(it)]
//
//			changeDescription.applyAndReverse
//			for (objectToAttach : changeDescription.getObjectsToAttach) {
//				//recursivelyAddChangesForNonDefaultValues(objectToAttach)
////				addChangeForObjectToAttach(objectToAttach, changeDescription.getNewContainer(objectToAttach))
//			}

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

}
