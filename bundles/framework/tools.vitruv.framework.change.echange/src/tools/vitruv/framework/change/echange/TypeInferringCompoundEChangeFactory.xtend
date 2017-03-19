package tools.vitruv.framework.change.echange

import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import tools.vitruv.framework.change.echange.compound.CompoundFactory
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot
import tools.vitruv.framework.change.echange.compound.CreateAndInsertRoot
import tools.vitruv.framework.change.echange.compound.CreateAndReplaceAndDeleteNonRoot
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEFeature
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteNonRoot
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteRoot
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import tools.vitruv.framework.change.echange.feature.attribute.SubtractiveAttributeEChange
import tools.vitruv.framework.change.echange.feature.list.InsertInListEChange
import tools.vitruv.framework.change.echange.feature.list.RemoveFromListEChange

class TypeInferringCompoundEChangeFactory {
	var protected TypeInferringAtomicEChangeFactory atomicFactory
	
	new(TypeInferringAtomicEChangeFactory atomicFactory) {
		this.atomicFactory = atomicFactory
	}
	
	def <T extends EObject> CreateAndInsertRoot<T> createCreateAndInsertRootChange(T affectedEObject, String resourceUri, int index) {
		val c = CompoundFactory.eINSTANCE.createCreateAndInsertRoot();
		c.createChange = atomicFactory.createCreateEObjectChange(affectedEObject);
		c.insertChange = atomicFactory.createInsertRootChange(affectedEObject, resourceUri, index);
		return c
	}
	
	def <T extends EObject> RemoveAndDeleteRoot<T> createRemoveAndDeleteRootChange(T affectedEObject, String resourceUri, int index) {
		val c = CompoundFactory.eINSTANCE.createRemoveAndDeleteRoot();
		c.deleteChange = atomicFactory.createDeleteEObjectChange(affectedEObject);
		c.removeChange = atomicFactory.createRemoveRootChange(affectedEObject, resourceUri, index);
		return c
	}
	
	def <A extends EObject, T extends EObject> CreateAndInsertNonRoot<A,T> createCreateAndInsertNonRootChange(A affectedEObject, EReference reference, T newValue, int index) {
		val c = CompoundFactory.eINSTANCE.createCreateAndInsertNonRoot();
		c.createChange = atomicFactory.createCreateEObjectChange(newValue);
		c.insertChange = atomicFactory.createInsertReferenceChange(affectedEObject, reference, newValue, index);
		return c
	}
	
	def <A extends EObject, T extends EObject> RemoveAndDeleteNonRoot<A,T> createRemoveAndDeleteNonRootChange(A affectedEObject, EReference reference, T oldValue, int index) {
		val c = CompoundFactory.eINSTANCE.createRemoveAndDeleteNonRoot();
		c.deleteChange = atomicFactory.createDeleteEObjectChange(oldValue);
		c.removeChange = atomicFactory.createRemoveReferenceChange(affectedEObject, reference, oldValue, index);
		return c
	}
	
	def <A extends EObject, T extends EObject> CreateAndReplaceAndDeleteNonRoot<A,T> createCreateAndReplaceAndDeleteNonRootChange(A affectedEObject, EReference reference, T oldValue, T newValue) {
		val c = CompoundFactory.eINSTANCE.createCreateAndReplaceAndDeleteNonRoot();
		c.deleteChange = atomicFactory.createDeleteEObjectChange(oldValue);
		c.createChange = atomicFactory.createCreateEObjectChange(newValue);
		c.replaceChange = atomicFactory.createReplaceSingleReferenceChange(affectedEObject, reference, oldValue, newValue);
		return c
	}
	
	def <A extends EObject, T extends Object> ExplicitUnsetEFeature<A, T> createExplicitUnsetChange(List<SubtractiveAttributeEChange<A,T>> changes) {
		val c = CompoundFactory.eINSTANCE.createExplicitUnsetEFeature();
		for (change : changes) {
			c.subtractiveChanges.add(change)
		}
		return c
	}
	
	def <T extends Object, S extends SubtractiveEChange<T>> createCompoundSubtractionChange(List<S> changes) {
		val c = CompoundFactory.eINSTANCE.createCompoundSubtraction()
		for (change : changes) {
			c.subtractiveChanges.add(change)
		}
		return c
	}
	
	def <T extends Object, S extends AdditiveEChange<T>> createCompoundAdditionChange(List<S> changes) {
		val c = CompoundFactory.eINSTANCE.createCompoundAddition()
		for (change : changes) {
			c.additiveChanges.add(change)
		}
		return c
	}
	
	def <A extends EObject, F extends EStructuralFeature, T extends Object, R extends RemoveFromListEChange<A, F, T> & FeatureEChange<A, F> & SubtractiveEChange<T>, I extends InsertInListEChange<A, F, T> & FeatureEChange<A, F> & AdditiveEChange<T>> createReplaceInEListChange(R removeChange, I insertChange) {
		val c = CompoundFactory.eINSTANCE.<A, F, T, R, I>createReplaceInEList()
		c.removeChange = removeChange
		c.insertChange = insertChange
		return c
	}
	
}