package edu.kit.ipd.sdq.vitruvius.applications.jmljava.synchronizers.java.compositerefiners

import edu.kit.ipd.sdq.vitruvius.applications.jmljava.changesynchronizer.ChangeBuilder
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.helper.java.shadowcopy.ShadowCopyFactory
import edu.kit.ipd.sdq.vitruvius.framework.change.description.CompositeChange
import edu.kit.ipd.sdq.vitruvius.framework.change.description.GeneralChange
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectInList
import java.util.ArrayList
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.emftext.language.java.modifiers.Modifier

abstract class JavaModifiersChangeRefinerBase extends CompositeChangeRefinerBase {

	protected new(ShadowCopyFactory shadowCopyFactory) {
		super(shadowCopyFactory)
	}

	override match(CompositeChange change) {
		val firstLevelCompositeChanges = change.changes.filter(CompositeChange)
		if (firstLevelCompositeChanges.size == change.changes.size) {
			return firstLevelCompositeChanges.forall[matchInternal]
		}

		return change.matchInternal
	}

	private def matchInternal(CompositeChange change) {

		val modelChanges = change.changes.filter(EMFModelChange)
		if (modelChanges.size != change.changes.size) {
			return false
		}

		var CompositeChangeRefinerBase.AddDeleteContainer addAndDeleteChanges = null
		try {
			addAndDeleteChanges = change.addAndDeleteChanges
		} catch (IllegalArgumentException e) {
			return false
		}

		val addChanges = addAndDeleteChanges.addChanges
		val deleteChanges = addAndDeleteChanges.deleteChanges

		if (addChanges.size != deleteChanges.size) {
			return false
		}

		if (addChanges.exists[!typeCheck(affectedEObjectClass, Modifier)]) {
			return false;
		}

		if (deleteChanges.exists[!typeCheck(affectedEObjectClass, Modifier)]) {
			return false;
		}

		return true
	}

	override refine(CompositeChange change) {
		val firstLevelCompositeChanges = change.changes.filter(CompositeChange)
		if (firstLevelCompositeChanges.size == change.changes.size) {
			val changes = new ArrayList<EMFModelChange>()
			change.changes.forEach[changes.addAll(processInternal(it as CompositeChange))]
			return new CompositeChangeRefinerResultAtomicTransformations(changes);
		}

		return new CompositeChangeRefinerResultAtomicTransformations(change.processInternal)
	}

	private def processInternal(CompositeChange change) {
		val addAndDeleteChanges = change.addAndDeleteChanges
		val addChanges = addAndDeleteChanges.addChanges
		val deleteChanges = new ArrayList<DeleteNonRootEObjectInList<EObject>>(addAndDeleteChanges.deleteChanges)

		val changes = new ArrayList<EMFModelChange>()

		for (addChange : addChanges) {
			val matchingDeleteChange = addChange.findMatchingDeleteChange(deleteChanges)
			if (matchingDeleteChange == null) {

				// add not matched create change
				changes.add(addChange.getEMFChange)
			} else {
				deleteChanges.remove(matchingDeleteChange)
				val replaceChange = ChangeBuilder.createReplaceChangeInList(matchingDeleteChange.oldAffectedEObject,
					addChange.newAffectedEObject, addChange.affectedFeature, addChange.newValue,
					matchingDeleteChange.oldValue)
				changes.add(replaceChange)
			}
		}

		// add not matched delete Changes
		deleteChanges.forEach[changes.add(getEMFChange)]

		return changes
	}

	protected abstract def Class<?> getAffectedEObjectClass();

	protected abstract def DeleteNonRootEObjectInList<EObject> findMatchingDeleteChange(
		CreateNonRootEObjectInList<EObject> addChange, List<DeleteNonRootEObjectInList<EObject>> deleteChanges)

}
