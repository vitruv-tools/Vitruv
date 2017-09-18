package tools.vitruv.framework.change.echange

import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.change.echange.root.InsertRootEObject
import tools.vitruv.framework.change.echange.root.RemoveRootEObject
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference
import java.util.Collections

class EChangeInformationExtractor {
	static def Iterable<EObject> getInvolvedEObjects(EChange change) {
		(Collections.singleton(change.affectedEObject) + change.referencedEObjects).filterNull;
	}
	
	static def dispatch EObject getAffectedEObject(EChange eChange) {
		return null
	}
	
	static def dispatch EObject getAffectedEObject(InsertRootEObject<EObject> eChange) {
		eChange.newValue
	}

	static def dispatch EObject getAffectedEObject(RemoveRootEObject<EObject> eChange) {
		eChange.oldValue
	}
	
	static def dispatch EObject getAffectedEObject(FeatureEChange<EObject, ?> eChange) {
		eChange.affectedEObject
	}
	
	static def dispatch EObject getAffectedEObject(EObjectExistenceEChange<EObject> eChange) {
		eChange.affectedEObject
	}
	
	static def dispatch Iterable<EObject> getReferencedEObjects(EChange eChange) {
		return Collections.emptyList
	}
	
	static def dispatch Iterable<EObject> getReferencedEObjects(InsertEReference<EObject, EObject> eChange) {
		return Collections.singletonList(eChange.newValue)
	}
	
	static def dispatch Iterable<EObject> getReferencedEObjects(RemoveEReference<EObject, EObject> eChange) {
		return Collections.singletonList(eChange.oldValue)
	}
	
	static def dispatch Iterable<EObject> getReferencedEObjects(ReplaceSingleValuedEReference<EObject, EObject> eChange) {
		return #[eChange.affectedEObject, eChange.oldValue, eChange.newValue]
	}
}