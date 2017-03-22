package tools.vitruv.framework.change.echange

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange
import tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange
import tools.vitruv.framework.change.echange.root.RootEChange
import org.eclipse.emf.ecore.resource.Resource

/**
 * Factory singleton class for elements of change models.
 * Creates only changes which EObjects are replaced by proxy elements.
 * Infers types (i.e. metaclasses and feature types) from parameters where possible.<br/>
 * 
 * Can be used by any transformation that creates change models.
 */
class TypeInferringUnresolvingAtomicEChangeFactory extends TypeInferringAtomicEChangeFactory {
	private static TypeInferringUnresolvingAtomicEChangeFactory instance
	
	private new() {}
	
	/**
	 * Gets the singleton instance of the factory which unresolves the changes.
	 * @return The singleton instance.
	 */
	def public static TypeInferringUnresolvingAtomicEChangeFactory getInstance() {
		if (instance == null) {
			instance = new TypeInferringUnresolvingAtomicEChangeFactory()
		}
		return instance
	}
	
	/**
	 * Creates the proxy object of a given resolved EObject.
	 * @param resolvedObject The resolved object.
	 * @return The proxy object of the given resolved EObject.
	 */
	def private <A extends EObject> A createProxy(A resolvedObject) {
		val proxy = EcoreUtil.copy(resolvedObject) as InternalEObject
		proxy.eSetProxyURI(EcoreUtil.getURI(resolvedObject))
		return proxy as A
	}

	override protected setRootChangeFeatures(RootEChange change, Resource resource, int index) {
		change.uri = resource.URI.toString
		change.resource = null
		change.index = index
	}
	
	override protected <A extends EObject, F extends EStructuralFeature> void setFeatureChangeFeatures(FeatureEChange<A,F> change, A affectedEObject, F affectedFeature) {
		if (affectedEObject != null) {
			change.affectedEObject = createProxy(affectedEObject)
		} else {
			change.affectedEObject = affectedEObject			
		}
		change.affectedFeature = affectedFeature
	}
	
	override protected <T extends EObject> void setNewValue(EObjectAddedEChange<T> change, T newReferenceValue) {
		if (newReferenceValue != null) {
			change.newValue = createProxy(newReferenceValue)
		} else {
			change.newValue = newReferenceValue
		}
	}	
	
	override protected <T extends EObject> void setOldValue(EObjectSubtractedEChange<T> change, T oldReferenceValue) {
		if (oldReferenceValue != null) {
			change.oldValue = createProxy(oldReferenceValue)
		} else {
			change.oldValue = oldReferenceValue
		}
	}
	
	override protected <A extends EObject> void setEObjectExistenceChange(EObjectExistenceEChange<A> change, A affectedEObject, Resource resource) {
		if (affectedEObject != null) {
			change.affectedEObject = createProxy(affectedEObject)
		} else {
			change.affectedEObject = affectedEObject;
		}
		change.stagingArea = null
	}
}