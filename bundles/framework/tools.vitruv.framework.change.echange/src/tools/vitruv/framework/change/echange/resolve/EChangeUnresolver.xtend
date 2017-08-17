package tools.vitruv.framework.change.echange.resolve

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
import tools.vitruv.framework.change.echange.AtomicEChange
import tools.vitruv.framework.change.echange.EChange

/**
 * Utility class to unresolve a given EChange.
 */
public class EChangeUnresolver {
	private new() {}
	
	/**
	 * Creates the a proxy object of a given EObject.
	 * @param resolvedObject The resolved EObject whose proxy should be created.
	 * @return The proxy object of the given EObject.
	 */
	def static public <A extends EObject> A createProxy(A resolvedObject) {
		if (resolvedObject !== null) {
			// TODO: Elbert S. Change when eobjects are removed recursively
			val proxy = EcoreUtil.copy(resolvedObject) as InternalEObject
			//val proxy = EcoreUtil.create(resolvedObject.eClass) as InternalEObject
			proxy.eSetProxyURI(EcoreUtil.getURI(resolvedObject))
			return proxy as A			
		}
		return null
	}

	/**
	 * Unresolves the attributes of the {@link RootEChange} class.
	 * @param The RootEChange.
	 */
	def static public unresolveRootEChange(RootEChange change) {
		change.resource = null
	}
	
	/**
	 * Unresolves the attributes of the {@link FeatureEChange} class.
	 * @param The FeatureEChange.
	 */
	def static public <A extends EObject, F extends EStructuralFeature> void unresolveFeatureEChange(FeatureEChange<A,F> change) {
		change.affectedEObject = createProxy(change.affectedEObject)
	}
	
	/** 
	 * Unresolves the attributes of the {@link EObjectAddedEChange} class.
	 * @param The EObjectAddedEChange.
	 */
	def static public <T extends EObject> void unresolveEObjectAddedEChange(EObjectAddedEChange<T> change) {
		change.newValue = createProxy(change.newValue)
	}	

	/** 
	 * Unresolves the attributes of the {@link EObjectSubtractedEChange} class.
	 * @param The EObjectSubtractedEChange.
	 */	
	def static public <T extends EObject> void unresolveEObjectSubtractedEChange(EObjectSubtractedEChange<T> change) {
		change.oldValue = createProxy(change.oldValue)
	}

	/** 
	 * Unresolves the atomic changes of the {@link CompoundEChange} class.
	 * @param The CompoundEChange.
	 */		
	def static public void unresolveCompoundEChange(CompoundEChange change) {
		for (AtomicEChange c : change.atomicChanges) {
			c.unresolve
		}		
	}
	
	/** 
	 * Unresolves the attributes of the {@link ExplicitUnsetEFeature} class.
	 * @param The ExplicitUnsetEFeature change.
	 */		
	def static public <A extends EObject, F extends EStructuralFeature> void unresolveExplicitUnsetEFeature(ExplicitUnsetEFeature<A, F> change) {
		change.affectedEObject = createProxy(change.affectedEObject)
	}
	
	def dispatch public static void unresolve(EChange change) {
	}
	
	/**
	 * Dispatch method for {@link CompoundEChange} to unresolve it.
	 * @param change The CompoundEChange.
	 */	
	def dispatch public static void unresolve(CompoundEChange change) {
		change.unresolveCompoundEChange
	}
	/**
	 * Dispatch method for {@link ExplicitUnsetEFeature} to unresolve it.
	 * @param change The ExplicitUnsetEFeature.
	 */	
	def dispatch public static void unresolve(ExplicitUnsetEFeature<EObject, EStructuralFeature> change) {
		change.unresolveExplicitUnsetEFeature
		change.unresolveCompoundEChange
	}
	/**
	 * Dispatch method for {@link InsertRootEObject} to unresolve it.
	 * @param change The InsertRootEObject.
	 */	
	def dispatch public static void unresolve(InsertRootEObject<EObject> change) {
		change.unresolveRootEChange
		change.newValue = createProxy(change.newValue)
	}	
	/**
	 * Dispatch method for {@link RemoveRootEObject} to unresolve it.
	 * @param change The RemoveRootEObject.
	 */
	def dispatch public static void unresolve(RemoveRootEObject<EObject> change) {
		change.unresolveRootEChange
		change.oldValue = createProxy(change.oldValue)
	}
	/**
	 * Dispatch method for {@link EObjectExistenceEChange} to unresolve it.
	 * @param change The EObjectExistenceEChange.
	 */	
	def dispatch public static void unresolve(EObjectExistenceEChange<EObject> change) {
		change.affectedEObject = createProxy(change.affectedEObject)
		change.stagingArea = null
	}	
	/**
	 * Dispatch method for {@link ReplaceSingleValuedEReference} to unresolve it.
	 * @param change The ReplaceSingleValuedEReference.
	 */	
	def dispatch public static void unresolve(ReplaceSingleValuedEReference<EObject, EObject> change) {
		change.unresolveEObjectAddedEChange
		change.unresolveEObjectSubtractedEChange
		change.unresolveFeatureEChange
	}
	
	/**
	 * Dispatch method for {@link InsertEReference} to unresolve it.
	 * @param change The InsertEReference.
	 */	
	def dispatch public static void unresolve(InsertEReference<EObject, EObject> change) {
		change.unresolveEObjectAddedEChange
		change.unresolveFeatureEChange		
	}
	
	/**
	 * Dispatch method for {@link RemoveEReference} to unresolve it.
	 * @param change The RemoveEReference.
	 */	
	def dispatch public static void unresolve(RemoveEReference<EObject, EObject> change) {
		change.unresolveEObjectSubtractedEChange
		change.unresolveFeatureEChange		
	}
	
	/**
	 * Dispatch method for {@link FeatureEChange} to unresolve it.
	 * @param change The FeatureEChange.
	 */
	def dispatch public static void unresolve(FeatureEChange<EObject, EStructuralFeature> change) {
		change.unresolveFeatureEChange
	}
}