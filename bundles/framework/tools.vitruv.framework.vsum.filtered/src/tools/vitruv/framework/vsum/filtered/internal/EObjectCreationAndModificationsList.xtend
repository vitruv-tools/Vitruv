package tools.vitruv.framework.vsum.filtered.internal

import java.util.ArrayList
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import tools.vitruv.framework.change.echange.eobject.CreateEObject
import tools.vitruv.framework.change.echange.feature.list.UpdateSingleListEntryEChange
import tools.vitruv.framework.change.echange.feature.single.ReplaceSingleValuedFeatureEChange

import static extension tools.vitruv.framework.vsum.filtered.internal.Util.getChangedObject

class EObjectCreationAndModificationsList<T1 extends CreateEObject<EObject>, T2 extends UpdateSingleListEntryEChange<EObject, EReference>, T3 extends ReplaceSingleValuedFeatureEChange<EObject, EStructuralFeature, Object>> implements Iterable<EObjectCreationAndModifications<T1, T2, T3>> {
	List<EObjectCreationAndModifications<T1, T2, T3>> correspondences = new ArrayList()

	def get(EObject eobject) {
		correspondences.stream.filter[it.isCreatedHere(eobject)].findFirst
	}

	def add(T1 existenceChange) {
		get(existenceChange.affectedEObject).ifPresentOrElse([], [correspondences.add(new EObjectCreationAndModifications(existenceChange))])
	}

	def add(T2 updateChange) {
		get(updateChange.changedObject as EObject).ifPresent[it.updateChangesIndexBased.add(updateChange)]
	}

	def add(T3 updateChange) {
		get(updateChange.affectedEObject).ifPresent[it.updateChangesReferenceBased.add(updateChange)]
		if (updateChange.newValue instanceof EObject) get(updateChange.newValue as EObject).ifPresent[it.updateChangesReferenceBased.add(updateChange)]
		if (updateChange.oldValue instanceof EObject) get(updateChange.oldValue as EObject).ifPresent[it.updateChangesReferenceBased.add(updateChange)]
	}

	override iterator() {
		correspondences.iterator
	}

	override toString() {
		correspondences.toString
	}
	
	def stream() {
		correspondences.stream
	}

}
