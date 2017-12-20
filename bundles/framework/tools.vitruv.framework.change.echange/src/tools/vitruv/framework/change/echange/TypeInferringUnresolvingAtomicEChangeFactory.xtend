package tools.vitruv.framework.change.echange

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource

import static extension tools.vitruv.framework.change.echange.resolve.EChangeUnresolver.*
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EReference
import tools.vitruv.framework.uuid.UuidGeneratorAndResolver
import org.eclipse.emf.ecore.EStructuralFeature

/**
 * Factory singleton class for elements of change models.
 * Creates only changes which EObjects are replaced by proxy elements.
 * Infers types (i.e. metaclasses and feature types) from parameters where possible.<br/>
 * 
 * Can be used by any transformation that creates change models.
 */
final class TypeInferringUnresolvingAtomicEChangeFactory extends TypeInferringAtomicEChangeFactory {
	val EChangeIdManager eChangeIdManager;
	
	new(UuidGeneratorAndResolver uuidProviderAndResolver) {
		this.eChangeIdManager = new EChangeIdManager(uuidProviderAndResolver);
	}
	
	def private setIds(EChange change) {
		eChangeIdManager.setOrGenerateIds(change);
	}
	
	override <A extends EObject> createCreateEObjectChange(A affectedEObject) {
		val result = super.<A>createCreateEObjectChange(affectedEObject)
		setIds(result);
		result.unresolve
		return result;
	}
	
	override <A extends EObject> createDeleteEObjectChange(A affectedEObject) {
		val result = super.<A>createDeleteEObjectChange(affectedEObject)
		setIds(result);
		result.unresolve
		return result;
	}
	
	override <A extends EObject,T> createInsertAttributeChange(A affectedEObject, EAttribute affectedAttribute, int index, T newValue) {
		val result = super.<A, T>createInsertAttributeChange(affectedEObject, affectedAttribute, index, newValue)
		setIds(result);
		result.unresolve
		return result;
	}
	
	override <A extends EObject,T extends EObject> createInsertReferenceChange(A affectedEObject, EReference affectedReference, T newValue, int index) {
		val result = super.<A, T>createInsertReferenceChange(affectedEObject, affectedReference, newValue, index)
		setIds(result);
		result.unresolve
		return result;
	}
	
	override <T extends EObject> createInsertRootChange(T newValue, Resource resource, int index) {
		val result = super.<T>createInsertRootChange(newValue, resource, index)
		setIds(result);
		result.unresolve
		return result;
	}
	
	override <A extends EObject,T> createRemoveAttributeChange(A affectedEObject, EAttribute affectedAttribute, int index, T oldValue) {
		val result = super.<A, T>createRemoveAttributeChange(affectedEObject, affectedAttribute, index, oldValue)
		setIds(result);
		result.unresolve
		return result;
	}
	
	override <A extends EObject,T extends EObject> createRemoveReferenceChange(A affectedEObject, EReference affectedReference, T oldValue, int index) {
		val result = super.<A, T>createRemoveReferenceChange(affectedEObject, affectedReference, oldValue, index)
		setIds(result);
		result.unresolve
		return result;
	}
	
	override <T extends EObject> createRemoveRootChange(T oldValue, Resource resource, int index) {
		val result = super.<T>createRemoveRootChange(oldValue, resource, index)
		setIds(result);
		result.unresolve
		return result;
	}
	
	override <A extends EObject,T> createReplaceSingleAttributeChange(A affectedEObject, EAttribute affectedAttribute, T oldValue, T newValue) {
		val result = super.<A, T>createReplaceSingleAttributeChange(affectedEObject, affectedAttribute, oldValue, newValue)
		setIds(result);
		result.unresolve
		return result;
	}
	
	override <A extends EObject,T extends EObject> createReplaceSingleReferenceChange(A affectedEObject, EReference affectedReference, T oldValue, T newValue) {
		val result = super.<A, T>createReplaceSingleReferenceChange(affectedEObject, affectedReference, oldValue, newValue)
		setIds(result);
		result.unresolve
		return result;
	}
	
	override <A extends EObject,F extends EStructuralFeature> createUnsetFeatureChange(A affectedEObject, F affectedFeature) {
		val result = super.<A, F>createUnsetFeatureChange(affectedEObject, affectedFeature)
		setIds(result);
		result.unresolve
		return result;
	}
}
