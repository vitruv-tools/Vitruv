package tools.vitruv.framework.change.echange.impl

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.resource.Resource

import static extension tools.vitruv.framework.change.echange.resolve.EChangeUnresolver.*
import tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange
import tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange
import tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import tools.vitruv.framework.change.echange.impl.TypeInferringAtomicEChangeFactoryImpl
import tools.vitruv.framework.change.echange.root.RootEChange
import tools.vitruv.framework.change.echange.TypeInferringUnresolvingAtomicEChangeFactory

class TypeInferringUnresolvingAtomicEChangeFactoryImpl extends TypeInferringAtomicEChangeFactoryImpl implements TypeInferringUnresolvingAtomicEChangeFactory {
	static def TypeInferringUnresolvingAtomicEChangeFactory init() {
		new TypeInferringUnresolvingAtomicEChangeFactoryImpl
	}

	private new() {
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
