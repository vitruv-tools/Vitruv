package tools.vitruv.framework.change.description.impl

import tools.vitruv.framework.change.echange.EChange
import org.apache.log4j.Logger
import java.util.List
import tools.vitruv.framework.change.interaction.UserInteractionBase
import java.util.ArrayList
import static com.google.common.base.Preconditions.checkNotNull
import tools.vitruv.framework.uuid.UuidResolver
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference
import tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import tools.vitruv.framework.change.echange.root.RemoveRootEObject
import tools.vitruv.framework.change.echange.root.InsertRootEObject
import tools.vitruv.framework.change.description.ConcreteChange
import tools.vitruv.framework.change.description.VitruviusChange
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.framework.util.datatypes.VURI
import org.eclipse.emf.ecore.InternalEObject
import tools.vitruv.framework.change.echange.root.RootEChange
import java.util.Set
import tools.vitruv.framework.change.echange.feature.attribute.UpdateAttributeEChange

class ConcreteChangeImpl implements ConcreteChange {
	static val logger = Logger.getLogger(ConcreteChangeImpl)
	var EChange eChange
	val List<UserInteractionBase> userInteractions = new ArrayList()

	new(EChange eChange) {
		this.eChange = checkNotNull(eChange, "eChange")
	}

	override containsConcreteChange() {
		return true
	}
	
	override getChangedVURI() {
		switch(eChange) {
			FeatureEChange<?, ?>: eChange.affectedEObject?.objectVuri
			EObjectExistenceEChange<?>: eChange.affectedEObject?.objectVuri
			RootEChange: VURI.getInstance(eChange.uri)
		}
	}
	
	override getChangedVURIs() {
		setOfNotNull(changedVURI)
	}

	override getEChange() {
		return eChange
	}

	protected def setEChange(EChange eChange) {
		this.eChange = eChange
	}
	
	override resolveBeforeAndApplyForward(UuidResolver uuidResolver) {
		logger.warn("The resolveBeforeAndapplyForward method is not implemented for " + this.class.simpleName + " yet.")
	}

	override resolveAfterAndApplyBackward(UuidResolver uuidResolver) {
		logger.warn("The resolveAfterAndApplyBackward method is not implemented for " + this.class.simpleName + " yet.")
	}

	override unresolveIfApplicable() {
		// Do nothing	
	}

	def getAffectedNotReferencedEObjects() {
		switch (eChange) {
			FeatureEChange<?, ?>: Set.of(eChange.affectedEObject)
			EObjectExistenceEChange<?>: Set.of(eChange.affectedEObject)
			InsertRootEObject<?>: Set.of(eChange.newValue)
			RemoveRootEObject<?>: Set.of(eChange.oldValue)
		}
	}

	override getAffectedEObjects() {
		switch (eChange) {
			FeatureEChange<?, ?>: Set.of(eChange.affectedEObject)
			EObjectExistenceEChange<?>: Set.of(eChange.affectedEObject)
			InsertRootEObject<?>: Set.of(eChange.newValue)
			RemoveRootEObject<?>: Set.of(eChange.oldValue)
		}
	}
	
	override getAffectedEObjectIds() {
		switch (eChange) {
			FeatureEChange<?, ?>: Set.of(eChange.affectedEObjectID)
			EObjectExistenceEChange<?>: Set.of(eChange.affectedEObjectID)
			InsertRootEObject<?>: Set.of(eChange.newValueID)
			RemoveRootEObject<?>: Set.of(eChange.oldValueID)
		}
	}

	override getAffectedAndReferencedEObjects() {
		switch (eChange) {
			UpdateAttributeEChange<?>: Set.of(eChange.affectedEObject)
			ReplaceSingleValuedEReference<?, ?>:
				setOfNotNull(eChange.affectedEObject, eChange.oldValue, eChange.newValue)
			InsertEReference<?, ?>: Set.of(eChange.affectedEObject, eChange.newValue)
			RemoveEReference<?, ?>: Set.of(eChange.affectedEObject, eChange.oldValue)
			EObjectExistenceEChange<?>: Set.of(eChange.affectedEObject)
			InsertRootEObject<?>: Set.of(eChange.newValue)
			RemoveRootEObject<?>: Set.of(eChange.oldValue)
		}
	}
	
	override getAffectedAndReferencedEObjectIds() {
		switch (eChange) {
			UpdateAttributeEChange<?>: Set.of(eChange.affectedEObjectID)
			ReplaceSingleValuedEReference<?, ?>: 
				setOfNotNull(eChange.affectedEObjectID, eChange.oldValueID, eChange.newValueID)
			InsertEReference<?, ?>: Set.of(eChange.affectedEObjectID, eChange.newValueID)
			RemoveEReference<?, ?>: Set.of(eChange.affectedEObjectID, eChange.oldValueID)
			EObjectExistenceEChange<?>: Set.of(eChange.affectedEObjectID)
			InsertRootEObject<?>: Set.of(eChange.newValueID)
			RemoveRootEObject<?>: Set.of(eChange.oldValueID)
		}
	}

	override getUserInteractions() {
		return userInteractions
	}

	override setUserInteractions(Iterable<UserInteractionBase> userInteractions) {
		checkNotNull(userInteractions, "Interactions must not be null")
		this.userInteractions.clear()
		this.userInteractions += userInteractions
	}
	
	def protected getClonedEChange() {
		EcoreUtil.copy(eChange)
	}
		
	override ConcreteChangeImpl copy() {
		new ConcreteChangeImpl(clonedEChange)
	}

	override equals(Object obj) {
		if (obj === this) true
		else if (obj === null) false
		else if (obj instanceof ConcreteChange) {
			eChange == obj.EChange
		} 
		else false
	}
	
	override hashCode() {
		eChange.hashCode()
	}

	override boolean changedEObjectEquals(VitruviusChange change) {
		if (change instanceof ConcreteChange) {
			EcoreUtil.equals(EChange.involvedEObjects, change.EChange.involvedEObjects)
		} else false
	}
	
	private def getObjectVuri(EObject object) {
		val objectResource = object.eResource
		if (objectResource !== null) {
			VURI.getInstance(objectResource.URI)
		} else if (object.eIsProxy) {
			// being an InternalEObject is effectively enforced by EMF, so the cast is fine
			val proxyURI = (object as InternalEObject).eProxyURI
			if (proxyURI !== null && proxyURI.segmentCount > 0) {
				VURI.getInstance(proxyURI)
			} else null
		} else null
	}
	
	def private static <T> Set<T> setOfNotNull(T element) {
		element !== null ? Set.of(element) : emptySet
	}
	
	def private static <T> Set<T> setOfNotNull(T element1, T element2) {
		if (element1 === null) setOfNotNull(element2)
		else if (element2 === null) Set.of(element1)
		else Set.of(element1, element2)
	}
	
	def private static <T> Set<T> setOfNotNull(T element1, T element2, T element3) {
		if (element1 === null) setOfNotNull(element2, element3)
		else if (element2 === null) setOfNotNull(element1, element3)
		else if (element3 === null) Set.of(element1, element2)
		else Set.of(element1, element2, element3)
	}

    override String toString() '''
		«class.simpleName»:
			«EChange»
	'''
}