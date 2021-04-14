package tools.vitruv.framework.change.description.impl

import tools.vitruv.framework.change.echange.EChange
import java.util.List
import tools.vitruv.framework.change.interaction.UserInteractionBase
import java.util.ArrayList
import static com.google.common.base.Preconditions.checkNotNull
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference
import tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import tools.vitruv.framework.change.echange.root.RemoveRootEObject
import tools.vitruv.framework.change.echange.root.InsertRootEObject
import tools.vitruv.framework.change.description.ConcreteChange
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.emf.ecore.InternalEObject
import tools.vitruv.framework.change.echange.root.RootEChange
import tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange
import tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange
import tools.vitruv.framework.change.echange.eobject.CreateEObject
import tools.vitruv.framework.change.echange.eobject.DeleteEObject
import tools.vitruv.framework.change.echange.feature.UnsetFeature
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute
import tools.vitruv.framework.change.echange.feature.reference.AdditiveReferenceEChange
import tools.vitruv.framework.change.echange.feature.reference.SubtractiveReferenceEChange
import tools.vitruv.framework.change.echange.feature.attribute.InsertEAttributeValue
import tools.vitruv.framework.change.echange.feature.attribute.RemoveEAttributeValue
import java.util.Set
import tools.vitruv.framework.change.echange.feature.attribute.UpdateAttributeEChange
import org.eclipse.emf.common.util.URI
import static com.google.common.base.Preconditions.checkState
import tools.vitruv.framework.change.echange.resolve.EChangeUnresolver
import static extension tools.vitruv.framework.change.echange.resolve.EChangeResolverAndApplicator.*
import tools.vitruv.framework.change.id.IdResolver

class ConcreteChangeImpl implements ConcreteChange {
	var EChange eChange
	val List<UserInteractionBase> userInteractions = new ArrayList()

	new(EChange eChange) {
		this.eChange = checkNotNull(eChange, "eChange")
	}

	override containsConcreteChange() {
		return true
	}
	
	override getChangedURI() {
		switch(eChange) {
			FeatureEChange<?, ?>: eChange.affectedEObject?.objectUri
			EObjectExistenceEChange<?>: eChange.affectedEObject?.objectUri
			RootEChange: URI.createURI(eChange.uri)
		}
	}
	
	override getChangedURIs() {
		setOfNotNull(changedURI)
	}

	override getEChange() {
		return eChange
	}

	protected def setEChange(EChange eChange) {
		this.eChange = eChange
	}
	
	override resolveAndApply(IdResolver idResolver) {
		// TODO HK Make a copy of the complete change instead of replacing it internally
		val resolvedChange = this.EChange.resolveBefore(idResolver)
		checkState(resolvedChange !== null, "Failed to resolve this change: %s", this.EChange)
		this.EChange = resolvedChange
		this.EChange.applyForward(idResolver)
	}

	override unresolve() {
		EChanges.forEach [EChangeUnresolver.unresolve(it)]	
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

	private def getObjectUri(EObject object) {
		val objectResource = object.eResource
		if (objectResource !== null) {
			objectResource.URI
		} else if (object.eIsProxy) {
			// being an InternalEObject is effectively enforced by EMF, so the cast is fine
			val proxyURI = (object as InternalEObject).eProxyURI
			if (proxyURI !== null && proxyURI.segmentCount > 0) {
				proxyURI.trimFragment // remove fragment to get resource URI
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

    override String toString() {
    	switch (change : eChange) {
    		InsertRootEObject<?>: '''insert «change.newValueString» at «change.uri» (index «change.index»)'''
    		RemoveRootEObject<?>: '''remove «change.oldValueString» from «change.uri» (index «change.index»)'''
    		CreateEObject<?>: '''create «change.affectedObjectString»'''
    		DeleteEObject<?>: '''delete «change.affectedObjectString»'''
    		UnsetFeature<?, ?>: '''«change.affectedFeatureString» = «'\u2205' /* empty set */»'''
    		ReplaceSingleValuedEAttribute<?, ?>:
    			'''«change.affectedFeatureString» = «change.newValue» (was «change.oldValue»)'''
    		ReplaceSingleValuedEReference<?, ?>: 
    			'''«change.affectedFeatureString» = «change.newValueString» (was «change.oldValueString»)'''
    		InsertEAttributeValue<?, ?>:
	    		'''«change.affectedFeatureString» += «change.newValue» (index «change.index»)'''
	    	InsertEReference<?, ?>:
	    		'''«change.affectedFeatureString» += «change.newValueString» (index «change.index»)'''
	    	RemoveEAttributeValue<?, ?>:
		    	'''«change.affectedFeatureString» -= «change.oldValue» (index «change.index»)'''
		    RemoveEReference<?, ?>:
		    	'''«change.affectedFeatureString» -= «change.oldValueString» (index «change.index»)'''
    	}
    }
    
	def private getNewValueString(EObjectAddedEChange<?> change) {
		formatValueString(change.newValue, change.newValueID)
	}
	
	def private getOldValueString(EObjectSubtractedEChange<?> change) {
		formatValueString(change.oldValue, change.oldValueID)
	}
	
	def private getAffectedObjectString(EObjectExistenceEChange<?> change) {
		formatValueString(change.affectedEObject, change.affectedEObjectID)
	}
	
	def private getAffectedObjectString(FeatureEChange<?, ?> change) {
		formatValueString(change.affectedEObject, change.affectedEObjectID)
	}
	
	def private getAffectedFeatureString(FeatureEChange<?, ?> change) {
		'''«change.affectedObjectString».«change.affectedFeature.name»'''
	}
	
	def private newValueString(AdditiveReferenceEChange<?, ?> change) {
		formatValueString(change.newValue, change.newValueID)
	}
	
	def private oldValueString(SubtractiveReferenceEChange<?, ?> change) {
		formatValueString(change.oldValue, change.oldValueID)
	}
	
	def private formatValueString(Object value, String id) {
		if (value !== null) {
			'''«value» (id=«id»)'''
		} else {
			'''id=«id»'''
		}
	}

}