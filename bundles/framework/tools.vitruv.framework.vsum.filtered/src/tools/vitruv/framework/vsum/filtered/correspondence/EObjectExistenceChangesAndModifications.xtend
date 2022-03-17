package tools.vitruv.framework.vsum.filtered.correspondence

import java.util.ArrayList
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange
import tools.vitruv.framework.change.echange.feature.list.UpdateSingleListEntryEChange
import tools.vitruv.framework.change.echange.feature.single.ReplaceSingleValuedFeatureEChange
import java.util.Objects

class EObjectExistenceChangesAndModifications<T1 extends EObjectExistenceEChange<EObject>, T2 extends UpdateSingleListEntryEChange<EObject, EReference>, T3 extends ReplaceSingleValuedFeatureEChange<EObject, EStructuralFeature, Object>> {
	public val EObject existenceChanged
	public val List<T1> existenceChange
	public val List<T2> updateChangesIndexBased
	public val List<T3> updateChangesReferenceBased

	new(EObject existenceChanged) {
		Objects.requireNonNull(existenceChanged)
		this.existenceChanged = existenceChanged
		this.existenceChange = new ArrayList()
		this.updateChangesIndexBased = new ArrayList()
		this.updateChangesReferenceBased = new ArrayList()
	}

	/**
	 * @param object the object that was created in a change saved in this object
	 */
	def existenceModifiedHere(EObject object) {
		existenceChanged.equals(object)
	}

	override toString() {
		existenceChange + " / " + updateChangesIndexBased + " / " + updateChangesReferenceBased
	}

}
