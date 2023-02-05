package tools.vitruv.framework.views.changederivation

import java.util.HashMap
import java.util.Map
import org.eclipse.emf.common.notify.Notifier
import org.eclipse.emf.common.util.BasicMonitor
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.compare.EMFCompare
import org.eclipse.emf.compare.match.impl.MatchEngineFactoryImpl
import org.eclipse.emf.compare.match.impl.MatchEngineFactoryRegistryImpl
import org.eclipse.emf.compare.merge.BatchMerger
import org.eclipse.emf.compare.merge.IMerger
import org.eclipse.emf.compare.scope.DefaultComparisonScope
import org.eclipse.emf.compare.utils.UseIdentifiers
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.change.atomic.eobject.CreateEObject
import tools.vitruv.change.atomic.eobject.EObjectAddedEChange
import tools.vitruv.change.atomic.eobject.EObjectExistenceEChange
import tools.vitruv.change.atomic.eobject.EObjectSubtractedEChange
import tools.vitruv.change.atomic.feature.FeatureEChange
import tools.vitruv.change.composite.description.TransactionalChange
import tools.vitruv.change.composite.recording.ChangeRecorder
import tools.vitruv.framework.views.util.ResourceCopier

import static com.google.common.base.Preconditions.checkArgument
import static com.google.common.base.Preconditions.checkState
import static edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil.isPathmap

import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceUtil.getReferencedProxies

/**
 * This default strategy for diff based state changes uses EMFCompare to resolve a 
 * diff to a sequence of individual changes.
 */
class DefaultStateBasedChangeResolutionStrategy implements StateBasedChangeResolutionStrategy {
    /** The identifier matching behavior used by this strategy */
    public val UseIdentifiers useIdentifiers

    /**
     * Creates a new instance with the default identifier matching behavior 
     * which is match by identifier when available.
     */
    new() {
        this(UseIdentifiers.WHEN_AVAILABLE)
    }

    /**
     * Creates a new instance with the provided identifier matching behavior.
     * @param useIdentifiers The identifier matching behavior to use.
     */
    new(UseIdentifiers useIdentifiers) {
        this.useIdentifiers = useIdentifiers
    }

    private def checkNoProxies(Resource resource, String stateNotice) {
        val proxies = resource.referencedProxies
        checkArgument(proxies.empty, "%s '%s' should not contain proxies, but contains the following: %s", stateNotice,
            resource.URI, String.join(", ", proxies.map[toString]))
    }

    override getChangeSequenceBetween(Resource newState, Resource oldState) {
        checkArgument(oldState !== null && newState !== null, "old state or new state must not be null!")
        newState.checkNoProxies("new state")
        oldState.checkNoProxies("old state")
        val monitoredResourceSet = new ResourceSetImpl()
        val resourceCopier = new ResourceCopier()
        val currentStateCopy = resourceCopier.copyViewResource(oldState, monitoredResourceSet)
        val change = currentStateCopy.record [
            if (oldState.URI != newState.URI) {
                currentStateCopy.URI = newState.URI
            }
            compareStatesAndReplayChanges(newState, currentStateCopy)
        ]
        return transformChangeToOriginalResourceSet(change, resourceCopier)
    }

    override getChangeSequenceForCreated(Resource newState) {
        checkArgument(newState !== null, "new state must not be null!")
        newState.checkNoProxies("new state")
        // It is possible that root elements are automatically generated during resource creation (e.g., Java packages).
        // Thus, we create the resource and then monitor the re-insertion of the elements
        val monitoredResourceSet = new ResourceSetImpl()
        val newResource = monitoredResourceSet.createResource(newState.URI)
        newResource.contents.clear()
        val change = newResource.record [
            newResource.contents += EcoreUtil.copyAll(newState.contents)
        ]
        return transformChangeToOriginalResourceSet(change, new ResourceCopier());
    }

    override getChangeSequenceForDeleted(Resource oldState) {
        checkArgument(oldState !== null, "old state must not be null!")
        oldState.checkNoProxies("old state")
        // Setup resolver and copy state:
        val monitoredResourceSet = new ResourceSetImpl()
        val resourceCopier = new ResourceCopier()
        val currentStateCopy = resourceCopier.copyViewResource(oldState, monitoredResourceSet)
        val change = currentStateCopy.record [
            currentStateCopy.contents.clear()
        ]
        return transformChangeToOriginalResourceSet(change, resourceCopier)
    }

    private def <T extends Notifier> record(Resource resource, ()=>void function) {
    	try (val changeRecorder = new ChangeRecorder(resource.resourceSet)) {
            changeRecorder.beginRecording
            changeRecorder.addToRecording(resource)
            function.apply()
            return changeRecorder.endRecording.unresolve
        }
    }

    /**
     * Compares states using EMFCompare and replays the changes to the current state.
     */
    private def compareStatesAndReplayChanges(Notifier newState, Notifier currentState) {
        val scope = new DefaultComparisonScope(newState, currentState, null)
        val emfCompare = (EMFCompare.builder => [
            matchEngineFactoryRegistry = MatchEngineFactoryRegistryImpl.createStandaloneInstance => [
                add(new MatchEngineFactoryImpl(useIdentifiers))
            ]
        ]).build
        val differences = emfCompare.compare(scope).differences
        // Replay the EMF compare differences
        val mergerRegistry = IMerger.RegistryImpl.createStandaloneInstance()
        val merger = new BatchMerger(mergerRegistry)
        merger.copyAllLeftToRight(differences, new BasicMonitor)
    }

	/**
	 * Replaces all elements in the change, which are in the {@code monitoredResourceSet},
	 * with their counterparts in the originally provided resource set.
	 */
    private def TransactionalChange transformChangeToOriginalResourceSet(TransactionalChange change, ResourceCopier copier) {
    	var Map<EObject, EObject> createdObjects = new HashMap()
    	for (eChange : change.EChanges) {
			switch eChange {
				CreateEObject<EObject>:
					eChange.affectedEObject = getOriginalForCopy(copier, eChange.affectedEObject, createdObjects, true)
				EObjectExistenceEChange<EObject>:
					eChange.affectedEObject = getOriginalForCopy(copier, eChange.affectedEObject, createdObjects, false)
				FeatureEChange<EObject,?>:
					eChange.affectedEObject = getOriginalForCopy(copier, eChange.affectedEObject, createdObjects, false)
			}
			switch eChange {
				EObjectSubtractedEChange<EObject>:
					if (eChange.oldValue !== null) {
						eChange.oldValue = getOriginalForCopy(copier, eChange.oldValue, createdObjects, false)
					}
			}
			switch eChange {
				EObjectAddedEChange<EObject>:
					if (eChange.newValue !== null) {
						eChange.newValue = getOriginalForCopy(copier, eChange.newValue, createdObjects, true)
					}
			}
		}
    	return change
    }

    private def <T extends EObject> T getOriginalForCopy(ResourceCopier copier, T copy, Map<EObject, EObject> createdObjects, boolean isCreateChange) {
    	if (isReadOnlyEObject(copy)) {
    		return copy
    	}
    	var original = copier.getOriginalForCopy(copy)
    	if (original === null) {
    		original = createdObjects.get(copy)
    		if (original === null && isCreateChange) {
    			original = EcoreUtil.create(copy.eClass) as T
    			createdObjects.put(copy, original)
    		}
    	}
    	checkState(original !== null, "could not find original for %s", copy)
    	checkState(original.eClass == copy.eClass, "mismatching classes for original %s and copy %s", original, copy)
    	return original as T
    }

    private def isReadOnlyEObject(EObject eObject) {
		return eObject.eResource() !== null && eObject.eResource().getURI() !== null
				&& isReadOnlyUri(eObject.eResource().getURI());
	}
	
	private def isReadOnlyUri(URI uri) {
		return isPathmap(uri) || uri.isArchive();
	}
}
