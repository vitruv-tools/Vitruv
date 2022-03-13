package tools.vitruv.framework.vsum.filtered.internal

import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import tools.vitruv.framework.change.echange.feature.list.InsertInListEChange
import tools.vitruv.framework.change.echange.feature.list.RemoveFromListEChange
import tools.vitruv.framework.change.echange.feature.list.UpdateSingleListEntryEChange
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference

/**
 * Switch to create commands for all EChange classes.
 * The commands applies the EChanges forward.
 */
class Util {
	def package dispatch static EObject getCommands(UpdateSingleListEntryEChange<EObject, EReference> change) {
		return (change.affectedEObject.eGet(change.affectedFeature) as List<EObject>).get(change.index)
	}

	def package dispatch static EObject getCommands(RemoveFromListEChange<EObject, EReference, Object> change) {
		change.affectedEObject
	}

	def package dispatch static EObject getCommands(InsertInListEChange<EObject, EReference, Object> change) {
		change.affectedEObject
	}

	def static Object getChangedObject(UpdateSingleListEntryEChange<EObject, EReference> entry) {
		if (entry instanceof InsertEReference) {
			return entry.newValue
		} else if (entry instanceof RemoveEReference) {
			return entry.oldValue
		}
	}

	def static void setChangedObjectID(UpdateSingleListEntryEChange<EObject, EReference> entry, String newID) {
		if (entry instanceof InsertEReference) {
			entry.newValueID = newID
		} else if (entry instanceof RemoveEReference) {
			entry.oldValueID = newID
		}
	}

	def static String getChangedObjectID(UpdateSingleListEntryEChange<EObject, EReference> entry) {
		if (entry instanceof InsertEReference) {
			entry.newValueID
		} else if (entry instanceof RemoveEReference) {
			entry.oldValueID
		}
	}

}
