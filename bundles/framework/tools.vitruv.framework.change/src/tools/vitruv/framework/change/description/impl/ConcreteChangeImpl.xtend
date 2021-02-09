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
		eChange.changedVuri
	}
	
	override getChangedVURIs() {
		listOfNotNull(changedVURI)
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
		return eChange.affectedEObjects
	}

	override getAffectedEObjectIds() {
		return eChange.affectedEObjectIds
	}

	override getAffectedEObjects() {
		return affectedNotReferencedEObjects + eChange.referencedEObjects
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

	private def dispatch List<String> getAffectedEObjectIds(EChange eChange) {
		return emptyList
	}

	private def dispatch List<String> getAffectedEObjectIds(InsertRootEObject<?> eChange) {
		return List.of(eChange.newValueID)
	}

	private def dispatch List<String> getAffectedEObjectIds(RemoveRootEObject<?> eChange) {
		return List.of(eChange.oldValueID)
	}

	private def dispatch List<String> getAffectedEObjectIds(InsertEReference<?, ?> eChange) {
		return List.of(eChange.affectedEObjectID, eChange.newValueID)
	}

	private def dispatch List<String> getAffectedEObjectIds(RemoveEReference<?, ?> eChange) {
		return List.of(eChange.affectedEObjectID, eChange.oldValueID)
	}

	private def dispatch List<String> getAffectedEObjectIds(ReplaceSingleValuedEReference<?, ?> eChange) {
		return List.of(eChange.affectedEObjectID, eChange.newValueID, eChange.oldValueID)
	}

	private def dispatch List<String> getAffectedEObjectIds(FeatureEChange<?, ?> eChange) {
		return List.of(eChange.affectedEObjectID)
	}

	private def dispatch List<String> getAffectedEObjectIds(EObjectExistenceEChange<?> eChange) {
		return List.of(eChange.affectedEObjectID)
	}

	private def dispatch List<EObject> getAffectedEObjects(EChange eChange) {
		return emptyList
	}

	private def dispatch List<EObject> getAffectedEObjects(InsertRootEObject<?> eChange) {
		return listOfNotNull(eChange.newValue)
	}

	private def dispatch List<EObject> getAffectedEObjects(RemoveRootEObject<?> eChange) {
		return List.of(eChange.oldValue)
	}

	private def dispatch List<EObject> getAffectedEObjects(InsertEReference<?, ?> eChange) {
		return List.of(eChange.affectedEObject)
	}

	private def dispatch List<EObject> getAffectedEObjects(RemoveEReference<?, ?> eChange) {
		return List.of(eChange.affectedEObject)
	}

	private def dispatch List<EObject> getAffectedEObjects(ReplaceSingleValuedEReference<?, ?> eChange) {
		return List.of(eChange.affectedEObject)
	}

	private def dispatch List<EObject> getAffectedEObjects(FeatureEChange<?, ?> eChange) {
		listOfNotNull(eChange.affectedEObject)
	}

	private def dispatch List<EObject> getAffectedEObjects(EObjectExistenceEChange<?> eChange) {
		listOfNotNull(eChange.affectedEObject)
	}

	private def dispatch List<EObject> getReferencedEObjects(EChange eChange) {
		return emptyList
	}

	private def dispatch List<EObject> getReferencedEObjects(InsertEReference<?, ?> eChange) {
		return List.of(eChange.newValue)
	}

	private def dispatch List<EObject> getReferencedEObjects(RemoveEReference<?, ?> eChange) {
		return List.of(eChange.oldValue)
	}

	private def dispatch List<EObject> getReferencedEObjects(ReplaceSingleValuedEReference<?, ?> eChange) {
		return listOfNotNull(eChange.affectedEObject, eChange.oldValue, eChange.newValue)
	}
	
	private def dispatch getChangedVuri(FeatureEChange<?, ?> eChange) {
		eChange.affectedEObject?.objectVuri
	}
	
	private def dispatch getChangedVuri(EObjectExistenceEChange<?> eChange) {
		eChange.affectedEObject?.objectVuri
	}
	
	private def dispatch getChangedVuri(RootEChange eChange) {
		VURI.getInstance(eChange.uri)
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
	
	def private static <T> List<T> listOfNotNull(T element) {
		element !== null ? List.of(element) : emptyList
	}
	
	def private static <T> List<T> listOfNotNull(T element1, T element2) {
		if (element1 === null) listOfNotNull(element2)
		else if (element2 === null) List.of(element1)
		else List.of(element1, element2)
	}
	
	def private static <T> List<T> listOfNotNull(T element1, T element2, T element3) {
		if (element1 === null) listOfNotNull(element2, element3)
		else if (element2 === null) listOfNotNull(element1, element3)
		else if (element3 === null) List.of(element1, element2)
		else List.of(element1, element2, element3)
	}

    override String toString() '''
		«class.simpleName»:
			«EChange»
	'''
}