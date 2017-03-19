package tools.vitruv.framework.change.echange

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange
import tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange

class TypeInferringUnresolvedAtomicEChangeFactory extends TypeInferringAtomicEChangeFactory {
	def private <A extends EObject> A createProxy(A resolvedObject) {
		val proxy = EcoreUtil.copy(resolvedObject) as InternalEObject
		proxy.eSetProxyURI(EcoreUtil.getURI(resolvedObject))
		return proxy as A
	}
	
	override protected <A extends EObject, F extends EStructuralFeature> void setFeatureChangeFeatures(FeatureEChange<A,F> c, A affectedEObject, F affectedFeature) {
		if (affectedEObject != null) {
			c.affectedEObject = createProxy(affectedEObject)
		} else {
			c.affectedEObject = affectedEObject			
		}
		c.affectedFeature = affectedFeature
	}
	
	override protected <T extends EObject> void setNewValue(EObjectAddedEChange<T> c, T newReferenceValue) {
		if (newReferenceValue != null) {
			c.newValue = createProxy(newReferenceValue)
		} else {
			c.newValue = newReferenceValue
		}
	}	
	
	override protected <T extends EObject> void setOldValue(EObjectSubtractedEChange<T> c, T oldReferenceValue) {
		if (oldReferenceValue != null) {
			c.oldValue = createProxy(oldReferenceValue)
		} else {
			c.oldValue = oldReferenceValue
		}
	}
	
	override protected <A extends EObject> void setEObjectExistenceChange(EObjectExistenceEChange<A> c, A affectedEObject) {
		if (affectedEObject != null) {
			c.affectedEObject = createProxy(affectedEObject)
		} else {
			c.affectedEObject = affectedEObject;
		}
	}
}