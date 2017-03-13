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

import static tools.vitruv.framework.change.echange.TypeInferringAtomicEChangeFactory.*

class TypeInferringCompoundEChangeFactory {
	def static <T extends EObject> CreateAndInsertRoot<T> createCreateAndInsertRootChange(T affectedEObject, String resourceUri, int index, boolean unresolve) {
		val c = CompoundFactory.eINSTANCE.createCreateAndInsertRoot();
		c.createChange = createCreateEObjectChange(affectedEObject, unresolve);
		c.insertChange = createInsertRootChange(affectedEObject, resourceUri, index, unresolve);
		return c
	}
	
	def static <T extends EObject> RemoveAndDeleteRoot<T> createRemoveAndDeleteRootChange(T affectedEObject, String resourceUri, int index, boolean unresolve) {
		val c = CompoundFactory.eINSTANCE.createRemoveAndDeleteRoot();
		c.deleteChange = createDeleteEObjectChange(affectedEObject, unresolve);
		c.removeChange = createRemoveRootChange(affectedEObject, resourceUri, index, unresolve);
		return c
	}
	
	def static <A extends EObject, T extends EObject> CreateAndInsertNonRoot<A,T> createCreateAndInsertNonRootChange(A affectedEObject, EReference reference, T newValue, int index, boolean unresolve) {
		val c = CompoundFactory.eINSTANCE.createCreateAndInsertNonRoot();
		c.createChange = createCreateEObjectChange(newValue, unresolve);
		c.insertChange = createInsertReferenceChange(affectedEObject, reference, newValue, index, unresolve);
		return c
	}
	
	def static <A extends EObject, T extends EObject> RemoveAndDeleteNonRoot<A,T> createRemoveAndDeleteNonRootChange(A affectedEObject, EReference reference, T oldValue, int index, boolean unresolve) {
		val c = CompoundFactory.eINSTANCE.createRemoveAndDeleteNonRoot();
		c.deleteChange = createDeleteEObjectChange(oldValue, unresolve);
		c.removeChange = createRemoveReferenceChange(affectedEObject, reference, oldValue, index, unresolve);
		return c
	}
	
	def static <A extends EObject, T extends EObject> CreateAndReplaceAndDeleteNonRoot<A,T> createCreateAndReplaceAndDeleteNonRootChange(A affectedEObject, EReference reference, T oldValue, T newValue, boolean unresolve) {
		val c = CompoundFactory.eINSTANCE.createCreateAndReplaceAndDeleteNonRoot();
		c.deleteChange = createDeleteEObjectChange(oldValue, unresolve);
		c.createChange = createCreateEObjectChange(newValue, unresolve);
		c.replaceChange = createReplaceSingleReferenceChange(affectedEObject, reference, oldValue, newValue, unresolve);
		return c
	}
	
	def static <A extends EObject, T extends Object> ExplicitUnsetEFeature<A, T> createExplicitUnsetChange(List<SubtractiveAttributeEChange<A,T>> changes) {
		val c = CompoundFactory.eINSTANCE.createExplicitUnsetEFeature();
		for (change : changes) {
			c.subtractiveChanges.add(change)
		}
		return c
	}
	
	def static <T extends Object, S extends SubtractiveEChange<T>> createCompoundSubtractionChange(List<S> changes) {
		val c = CompoundFactory.eINSTANCE.createCompoundSubtraction()
		for (change : changes) {
			c.subtractiveChanges.add(change)
		}
		return c
	}
	
	def static <T extends Object, S extends AdditiveEChange<T>> createCompoundAdditionChange(List<S> changes) {
		val c = CompoundFactory.eINSTANCE.createCompoundAddition()
		for (change : changes) {
			c.additiveChanges.add(change)
		}
		return c
	}
	
	def static <A extends EObject, F extends EStructuralFeature, T extends Object, R extends RemoveFromListEChange<A, F, T> & FeatureEChange<A, F> & SubtractiveEChange<T>, I extends InsertInListEChange<A, F, T> & FeatureEChange<A, F> & AdditiveEChange<T>> createReplaceInEListChange(R removeChange, I insertChange) {
		val c = CompoundFactory.eINSTANCE.<A, F, T, R, I>createReplaceInEList()
		c.removeChange = removeChange
		c.insertChange = insertChange
		return c
	}
	
}