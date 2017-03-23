package tools.vitruv.framework.change.echange

import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.change.echange.compound.CompoundFactory
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot
import tools.vitruv.framework.change.echange.compound.CreateAndInsertRoot
import tools.vitruv.framework.change.echange.compound.CreateAndReplaceAndDeleteNonRoot
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteNonRoot
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteRoot
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import tools.vitruv.framework.change.echange.feature.attribute.SubtractiveAttributeEChange
import tools.vitruv.framework.change.echange.feature.list.InsertInListEChange
import tools.vitruv.framework.change.echange.feature.list.RemoveFromListEChange
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEAttribute
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEReference
import org.eclipse.emf.ecore.EAttribute

class TypeInferringCompoundEChangeFactory {

	private TypeInferringAtomicEChangeFactory atomicFactory
	private static TypeInferringCompoundEChangeFactory instance
	private static TypeInferringCompoundEChangeFactory unresolvingInstance
	
	private new(TypeInferringAtomicEChangeFactory atomicFactory) {
		this.atomicFactory = atomicFactory
	}
	
	def public static TypeInferringCompoundEChangeFactory getInstance() {
		if (instance == null) {
			instance = new TypeInferringCompoundEChangeFactory(TypeInferringAtomicEChangeFactory.instance)
		}
		return instance		
	}
	
	def public static TypeInferringCompoundEChangeFactory getUnresolvingInstance() {
		if (unresolvingInstance == null) {
			unresolvingInstance = new TypeInferringCompoundEChangeFactory(TypeInferringUnresolvingAtomicEChangeFactory.instance)
		}
		return unresolvingInstance
	}
	
	def <T extends EObject> CreateAndInsertRoot<T> createCreateAndInsertRootChange(T affectedEObject, Resource resource, int index) {
		val c = CompoundFactory.eINSTANCE.createCreateAndInsertRoot();

		c.createChange = atomicFactory.createCreateEObjectChange(affectedEObject, resource);
		c.insertChange = atomicFactory.createInsertRootChange(affectedEObject, resource, index);
		return c
	}
	

	def <T extends EObject> RemoveAndDeleteRoot<T> createRemoveAndDeleteRootChange(T affectedEObject, Resource resource, int index) {
		val c = CompoundFactory.eINSTANCE.createRemoveAndDeleteRoot();

		c.deleteChange = atomicFactory.createDeleteEObjectChange(affectedEObject, resource);
		c.removeChange = atomicFactory.createRemoveRootChange(affectedEObject, resource, index);
		return c
	}
	

	def <A extends EObject, T extends EObject> CreateAndInsertNonRoot<A,T> createCreateAndInsertNonRootChange(A affectedEObject, EReference reference, T newValue, int index, Resource resource) {
		val c = CompoundFactory.eINSTANCE.createCreateAndInsertNonRoot();

		c.createChange = atomicFactory.createCreateEObjectChange(newValue, resource);
		c.insertChange = atomicFactory.createInsertReferenceChange(affectedEObject, reference, newValue, index);
		return c
	}
	

	def <A extends EObject, T extends EObject> RemoveAndDeleteNonRoot<A,T> createRemoveAndDeleteNonRootChange(A affectedEObject, EReference reference, T oldValue, int index, Resource resource) {
		val c = CompoundFactory.eINSTANCE.createRemoveAndDeleteNonRoot();

		c.deleteChange = atomicFactory.createDeleteEObjectChange(oldValue, resource);
		c.removeChange = atomicFactory.createRemoveReferenceChange(affectedEObject, reference, oldValue, index);
		return c
	}
	

	def <A extends EObject, T extends EObject> CreateAndReplaceAndDeleteNonRoot<A,T> createCreateAndReplaceAndDeleteNonRootChange(A affectedEObject, EReference reference, T oldValue, T newValue, Resource resource) {
		val c = CompoundFactory.eINSTANCE.createCreateAndReplaceAndDeleteNonRoot();

		c.deleteChange = atomicFactory.createDeleteEObjectChange(oldValue, resource);
		c.createChange = atomicFactory.createCreateEObjectChange(newValue, resource);
		c.replaceChange = atomicFactory.createReplaceSingleReferenceChange(affectedEObject, reference, oldValue, newValue);
		return c
	}
	

	def static <A extends EObject, T extends Object> ExplicitUnsetEAttribute<A, T> createExplicitUnsetEAttributeChange(A affectedEObject, EAttribute affectedAttribute, List<SubtractiveAttributeEChange<A,T>> changes) {
		val c = CompoundFactory.eINSTANCE.createExplicitUnsetEAttribute()
		c.affectedEObject = affectedEObject
		c.affectedFeature = affectedAttribute
		for (change : changes) {
			c.subtractiveChanges.add(change)

		}

		return c
	}
	
	def static <A extends EObject, T extends EObject> ExplicitUnsetEReference<A> createExplicitUnsetEReferenceChange(A affectedEObject, EReference affectedReference, List<EChange> changes) {
		val c = CompoundFactory.eINSTANCE.createExplicitUnsetEReference();
		c.affectedEObject = affectedEObject;
		c.affectedFeature = affectedReference;
		for (change : changes) {
			c.changes.add(change);
		}
		return c;
	}
	
}