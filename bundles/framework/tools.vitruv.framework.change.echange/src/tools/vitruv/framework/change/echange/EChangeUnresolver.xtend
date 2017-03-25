package tools.vitruv.framework.change.echange

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.framework.change.echange.compound.CompoundEChange
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEFeature
import tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange
import tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange
import tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference
import tools.vitruv.framework.change.echange.root.InsertRootEObject
import tools.vitruv.framework.change.echange.root.RemoveRootEObject
import tools.vitruv.framework.change.echange.root.RootEChange

class EChangeUnresolver {
	def static package <A extends EObject> A createProxy(A resolvedObject) {
		val proxy = EcoreUtil.copy(resolvedObject) as InternalEObject
		proxy.eSetProxyURI(EcoreUtil.getURI(resolvedObject))
		return proxy as A
	}

	def static package unresolveRootEChange(RootEChange change) {
		change.resource = null
	}
	
	def static package <A extends EObject, F extends EStructuralFeature> void unresolveFeatureEChange(FeatureEChange<A,F> change) {
		if (change.affectedEObject != null) {
			change.affectedEObject = createProxy(change.affectedEObject)
		}
	}
	
	def static package <T extends EObject> void unresolveEObjectAddedEChange(EObjectAddedEChange<T> change) {
		if (change.newValue != null) {
			change.newValue = createProxy(change.newValue)
		}
	}	
	
	def static package <T extends EObject> void unresolveEObjectSubtractedEChange(EObjectSubtractedEChange<T> change) {
		if (change.oldValue != null) {
			change.oldValue = createProxy(change.oldValue)
		}
	}

	def static package void unresolveCompoundEChange(CompoundEChange change) {
		for (AtomicEChange c : change.atomicChanges) {
			c.unresolve
		}		
	}
	
	def static package <A extends EObject, F extends EStructuralFeature> void unresolveExplicitUnsetEFeature(ExplicitUnsetEFeature<A, F> change) {
		if (change.affectedEObject != null) {
			change.affectedEObject = createProxy(change.affectedEObject)
		}
	}
		
	def dispatch public static void unresolve(EChange change) {
		throw new UnsupportedOperationException()
	}
	
	def dispatch public static void unresolve(CompoundEChange change) {
		change.unresolveCompoundEChange
	}
	
	def dispatch public static void unresolve(ExplicitUnsetEFeature<EObject, EStructuralFeature> change) {
		change.unresolveExplicitUnsetEFeature
		change.unresolveCompoundEChange
	}
	
	def dispatch public static void unresolve(InsertRootEObject<EObject> change) {
		change.unresolveRootEChange
		if (change.newValue != null) {
			change.newValue = createProxy(change.newValue)
		}
	}
	def dispatch public static void unresolve(RemoveRootEObject<EObject> change) {
		change.unresolveRootEChange
		if (change.oldValue != null) {
			change.oldValue = createProxy(change.oldValue)
		}
	}
	
	def dispatch public static void unresolve(EObjectExistenceEChange<EObject> change) {
		if (change.affectedEObject != null) {
			change.affectedEObject = createProxy(change.affectedEObject)
		}
		change.stagingArea = null
	}	
	
	def dispatch public static void unresolve(ReplaceSingleValuedEReference<EObject, EObject> change) {
		change.unresolveEObjectAddedEChange
		change.unresolveEObjectSubtractedEChange
		change.unresolveFeatureEChange
	}
	
	def dispatch public static void unresolve(InsertEReference<EObject, EObject> change) {
		change.unresolveEObjectAddedEChange
		change.unresolveFeatureEChange		
	}
	
	def dispatch public static void unresolve(RemoveEReference<EObject, EObject> change) {
		change.unresolveEObjectSubtractedEChange
		change.unresolveFeatureEChange		
	}
	
	def dispatch public static void unresolve(FeatureEChange<EObject, EStructuralFeature> change) {
		change.unresolveFeatureEChange
	}
}