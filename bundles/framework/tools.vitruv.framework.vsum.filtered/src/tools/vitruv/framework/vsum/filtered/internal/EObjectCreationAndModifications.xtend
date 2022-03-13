package tools.vitruv.framework.vsum.filtered.internal

import java.util.ArrayList
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import tools.vitruv.framework.change.echange.eobject.CreateEObject
import tools.vitruv.framework.change.echange.feature.list.UpdateSingleListEntryEChange
import tools.vitruv.framework.change.echange.feature.single.ReplaceSingleValuedFeatureEChange

class EObjectCreationAndModifications<T1 extends CreateEObject<EObject>, T2 extends UpdateSingleListEntryEChange<EObject, EReference>, T3 extends ReplaceSingleValuedFeatureEChange<EObject, EStructuralFeature, Object>> {
	public val T1 createEObject
	public var List<T2> updateChangesIndexBased
	public var List<T3> updateChangesReferenceBased

	new(T1 existenceChange) {
		this.createEObject = existenceChange
		this.updateChangesIndexBased = new ArrayList()
		this.updateChangesReferenceBased = new ArrayList()
	}

	/**
	 * @param object the object that was created in a change saved in this object
	 */
	def isCreatedHere(EObject object) {
		createEObject.affectedEObject.equals(object)
	}

	override toString() {
		createEObject + " / " + updateChangesIndexBased + " / " + updateChangesReferenceBased
	}

}
