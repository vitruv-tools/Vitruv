package tools.vitruv.framework.change.echange

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange
import tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange
import tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import tools.vitruv.framework.change.echange.root.RootEChange

import static extension tools.vitruv.framework.change.echange.resolve.EChangeUnresolver.*

/**
 * Factory singleton class for elements of change models.
 * Creates only changes which EObjects are replaced by proxy elements.
 * Infers types (i.e. metaclasses and feature types) from parameters where possible.<br/>
 * 
 * Can be used by any transformation that creates change models.
 */
final class TypeInferringUnresolvingAtomicEChangeFactory extends TypeInferringAtomicEChangeFactory {
	private static TypeInferringUnresolvingAtomicEChangeFactory instance

	private new() {
	}

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

	override protected setRootChangeFeatures(RootEChange change, Resource resource, int index) {
		super.setRootChangeFeatures(change, resource, index)
		change.unresolveRootEChange
	}

	override protected <A extends EObject, F extends EStructuralFeature> void setFeatureChangeFeatures(
		FeatureEChange<A, F> change, A affectedEObject, F affectedFeature) {
		super.setFeatureChangeFeatures(change, affectedEObject, affectedFeature)
		change.unresolveFeatureEChange
	}

	override protected <T extends EObject> void setNewValue(EObjectAddedEChange<T> change, T newValue) {
		super.setNewValue(change, newValue)
		change.unresolveEObjectAddedEChange
	}

	override protected <T extends EObject> void setOldValue(EObjectSubtractedEChange<T> change, T oldValue) {
		super.setOldValue(change, oldValue)
		change.unresolveEObjectSubtractedEChange
	}

	override protected <A extends EObject> void setEObjectExistenceChange(EObjectExistenceEChange<A> change,
		A affectedEObject, Resource resource) {
		super.setEObjectExistenceChange(change, affectedEObject, resource)
		change.unresolve
	}
}
