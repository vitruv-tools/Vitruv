package tools.vitruv.framework.change.echange.resolve

import tools.vitruv.framework.uuid.UuidResolver
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.framework.change.echange.EChange
import static com.google.common.base.Preconditions.checkArgument
import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.framework.change.echange.command.ApplyEChangeSwitch
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange
import tools.vitruv.framework.change.echange.root.InsertRootEObject
import tools.vitruv.framework.change.echange.root.RemoveRootEObject
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.change.echange.feature.reference.UpdateReferenceEChange
import tools.vitruv.framework.change.echange.feature.reference.SubtractiveReferenceEChange

/**
 * Utility class for applying and resolving a given EChange.
 */
@Utility
class EChangeResolverAndApplicator {
	static def EChange resolveBefore(EChange eChange, UuidResolver uuidResolver) {
		return resolveCopy(eChange, uuidResolver)
	}

	static def void applyForward(EChange eChange, UuidResolver uuidResolver) {
		executeUpdatingIds(eChange, uuidResolver, true)
	}

	static def void applyBackward(EChange eChange, UuidResolver uuidResolver) {
		executeUpdatingIds(eChange, uuidResolver, false)
	}

	static def void applyBackward(EChange eChange) {
		ApplyEChangeSwitch.applyEChange(eChange, false)
	}
	
	static def void executeUpdatingIds(EChange eChange, UuidResolver uuidResolver, boolean forward) {
		val affectedObject = eChange.affectedEObject
		val affectedId = uuidResolver.getAndUpdateId(affectedObject)
		val oldObject = eChange.oldContainedEObject
		ApplyEChangeSwitch.applyEChange(eChange, forward)
		if (eChange.isContainmentChange || affectedId != uuidResolver.getAndUpdateId(affectedObject)) {
			affectedObject.updateIds(uuidResolver)
		}
		if (oldObject !== null) {
			oldObject.updateIds(uuidResolver)
		}
	}
	
	private static def boolean isContainmentChange(EChange eChange) {
		if (eChange instanceof UpdateReferenceEChange) {
			return eChange.containment
		}
		return false
	}
	
	private static def getAffectedEObject(EChange eChange) {
		switch (eChange) {
			FeatureEChange<?, ?>: eChange.affectedEObject
			EObjectExistenceEChange<?>: eChange.affectedEObject
			InsertRootEObject<?>: eChange.newValue
			RemoveRootEObject<?>: eChange.oldValue
		}
	}
	
	private static def EObject getOldContainedEObject(EChange eChange) {
		switch (eChange) {
			SubtractiveReferenceEChange<?, ?>: if (eChange.affectedFeature.containment) eChange.oldValue
		}
	}
	
	private static def void updateIds(EObject object, UuidResolver uuidResolver) {
		uuidResolver.getAndUpdateId(object)
		object.eContents.forEach[updateIds(uuidResolver)]
	}

	/**
	 * Creates a copy of the change and resolves it using the given {@link UuidResolver}.
	 * 
	 * @param change					The {@link EChange} which shall be resolved.
	 * @param uuidResolver 				The {@link UuidResolver} to resolve {@link EObject}s from
	 * @return 							Returns a resolved copy of the change. If the copy could not be resolved 
	 * 									an {@link IllegalStateException} is thrown
	 * @throws IllegalArgumentException The change is already resolved.
	 * @throws IllegalStateException 	The change cannot be resolved.
	 */
	def private static EChange resolveCopy(EChange change, UuidResolver uuidResolver) {
		checkArgument(!change.isResolved, "change must not be resolved when trying to resolve")
		var EChange copy = EcoreUtil.copy(change)
		new AtomicEChangeResolver(uuidResolver).resolve(copy)
		return copy
	}
	
}
