package tools.vitruv.framework.change.echange

import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.change.echange.compound.CreateAndInsertRoot
import tools.vitruv.framework.change.echange.compound.CompoundFactory
import static tools.vitruv.framework.change.echange.TypeInferringAtomicEChangeFactory.*;
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteRoot
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot
import org.eclipse.emf.ecore.EReference
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteNonRoot
import tools.vitruv.framework.change.echange.compound.CreateAndReplaceAndDeleteNonRoot
import tools.vitruv.framework.change.echange.feature.attribute.SubtractiveAttributeEChange
import java.util.List
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEAttribute
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEReference
import org.eclipse.emf.ecore.EAttribute

class TypeInferringCompoundEChangeFactory {
	def static <T extends EObject> CreateAndInsertRoot<T> createCreateAndInsertRootChange(T affectedEObject, String resourceUri) {
		val c = CompoundFactory.eINSTANCE.createCreateAndInsertRoot();
		c.createChange = createCreateEObjectChange(affectedEObject);
		c.insertChange = createInsertRootChange(affectedEObject, resourceUri);
		return c
	}
	
	def static <T extends EObject> RemoveAndDeleteRoot<T> createRemoveAndDeleteRootChange(T affectedEObject, String resourceUri) {
		val c = CompoundFactory.eINSTANCE.createRemoveAndDeleteRoot();
		c.deleteChange = createDeleteEObjectChange(affectedEObject);
		c.removeChange = createRemoveRootChange(affectedEObject, resourceUri);
		return c
	}
	
	def static <A extends EObject, T extends EObject> CreateAndInsertNonRoot<A,T> createCreateAndInsertNonRootChange(A affectedEObject, EReference reference, T newValue, int index) {
		val c = CompoundFactory.eINSTANCE.createCreateAndInsertNonRoot();
		c.createChange = createCreateEObjectChange(newValue);
		c.insertChange = createInsertReferenceChange(affectedEObject, reference, newValue, index);
		return c
	}
	
	def static <A extends EObject, T extends EObject> RemoveAndDeleteNonRoot<A,T> createRemoveAndDeleteNonRootChange(A affectedEObject, EReference reference, T oldValue, int index) {
		val c = CompoundFactory.eINSTANCE.createRemoveAndDeleteNonRoot();
		c.deleteChange = createDeleteEObjectChange(oldValue);
		c.removeChange = createRemoveReferenceChange(affectedEObject, reference, oldValue, index);
		return c
	}
	
	def static <A extends EObject, T extends EObject> CreateAndReplaceAndDeleteNonRoot<A,T> createCreateAndReplaceAndDeleteNonRootChange(A affectedEObject, EReference reference, T oldValue, T newValue) {
		val c = CompoundFactory.eINSTANCE.createCreateAndReplaceAndDeleteNonRoot();
		c.deleteChange = createDeleteEObjectChange(oldValue);
		c.createChange = createCreateEObjectChange(newValue);
		c.replaceChange = createReplaceSingleReferenceChange(affectedEObject, reference, oldValue, newValue);
		return c
	}
	
	def static <A extends EObject, T extends Object> ExplicitUnsetEAttribute<A, T> createExplicitUnsetEAttributeChange(A affectedEObject, EAttribute affectedAttribute, List<SubtractiveAttributeEChange<A,T>> changes) {
		val c = CompoundFactory.eINSTANCE.createExplicitUnsetEAttribute();
		c.affectedEObject = affectedEObject;
		c.affectedFeature = affectedAttribute;
		for (change : changes) {
			c.subtractiveChanges.add(change);
		}
		return c;
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