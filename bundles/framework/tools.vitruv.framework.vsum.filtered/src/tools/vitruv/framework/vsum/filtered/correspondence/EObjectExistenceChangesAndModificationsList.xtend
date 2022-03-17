package tools.vitruv.framework.vsum.filtered.correspondence

import java.util.ArrayList
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.xtend.lib.annotations.Delegate
import tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange
import tools.vitruv.framework.change.echange.feature.list.UpdateSingleListEntryEChange
import tools.vitruv.framework.change.echange.feature.single.ReplaceSingleValuedFeatureEChange

import static extension tools.vitruv.framework.vsum.filtered.internal.Util.getChangedObject
import java.util.Optional

class EObjectExistenceChangesAndModificationsList<T1 extends EObjectExistenceEChange<EObject>, T2 extends UpdateSingleListEntryEChange<EObject, EReference>, T3 extends ReplaceSingleValuedFeatureEChange<EObject, EStructuralFeature, Object>> implements List<EObjectExistenceChangesAndModifications<T1, T2, T3>> {
	@Delegate List<EObjectExistenceChangesAndModifications<T1, T2, T3>> correspondences = new ArrayList()

	private def getOrCreate(EObject eobject) {
		val created = correspondences.stream.filter[it.existenceModifiedHere(eobject)].findFirst
		if (created.isPresent) return created
		else if (eobject !== null){
			val newExistenceChanged = new EObjectExistenceChangesAndModifications(eobject)
			correspondences.add(newExistenceChanged)
			return Optional.of(newExistenceChanged)
		}
		return Optional.empty
		
	}
	
	def isPresent(EObject eobject) {
		correspondences.stream.anyMatch()[it.existenceModifiedHere(eobject)]
	}

	def add(T1 existenceChange) {
		getOrCreate(existenceChange.affectedEObject).ifPresent[it.existenceChange.add(existenceChange)]
	}

	def add(T2 updateChange) {
		getOrCreate(updateChange.changedObject as EObject).ifPresent[it.updateChangesIndexBased.add(updateChange)]
	}

	def add(T3 updateChange) {
		getOrCreate(updateChange.affectedEObject).ifPresent[it.updateChangesReferenceBased.add(updateChange)]
		if (updateChange.newValue instanceof EObject) getOrCreate(updateChange.newValue as EObject).ifPresent[it.updateChangesReferenceBased.add(updateChange)]
		if (updateChange.oldValue instanceof EObject) getOrCreate(updateChange.oldValue as EObject).ifPresent[it.updateChangesReferenceBased.add(updateChange)]
	}
	
	override toString() {
		return correspondences.toString
	}
	
}
