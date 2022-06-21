package tools.vitruv.framework.views.impl

import java.util.ArrayList
import java.util.HashMap
import java.util.HashSet
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.xtend.lib.annotations.Delegate
import tools.vitruv.change.composite.description.VitruviusChange
import tools.vitruv.framework.views.CommittableView
import tools.vitruv.framework.views.View
import tools.vitruv.framework.views.changederivation.StateBasedChangeResolutionStrategy
import tools.vitruv.framework.views.util.ResourceCopier

import static com.google.common.base.Preconditions.checkArgument
import static com.google.common.base.Preconditions.checkState

import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil.isPathmap

/**
 * A {@link View} that derives changes based on the changed state of its resources and allows to propagate them 
 * back to the underlying models using the {@link #commitChanges} method.
 */
class ChangeDerivingView implements ModifiableView, CommittableView {
    @Delegate
    BasicView view

    val StateBasedChangeResolutionStrategy changeResolutionStrategy
    var ResourceSet originalStateViewResourceSet
    var HashMap<Resource, Resource> originalStateResourceMapping

    protected new(BasicView view, StateBasedChangeResolutionStrategy changeResolutionStrategy) {
        checkArgument(view !== null, "view must not be null")
        checkState(!view.isModified, "view must not be modified")
        checkState(!view.isOutdated, "view must not be outdated")
        checkArgument(changeResolutionStrategy !== null, "change resolution strategy must not be null")
        this.view = view
        this.changeResolutionStrategy = changeResolutionStrategy
        setupReferenceState
    }

    override update() {
        closeOriginalState
        view.update
        setupReferenceState
    }

    private def setupReferenceState() {
        originalStateViewResourceSet = new ResourceSetImpl
        ResourceCopier.copyViewResources(view.viewResourceSet.resources, originalStateViewResourceSet)
        originalStateResourceMapping = new HashMap
        view.viewResourceSet.resources.forEach[resource | originalStateResourceMapping.put(resource, originalStateViewResourceSet.resources.findFirst[URI === resource.URI])]
    }

    override commitChanges() {
        view.checkNotClosed()
        val propagatedChanges = new ArrayList()
        val allResources = new HashSet(originalStateResourceMapping.keySet)
        allResources.addAll(view.viewResourceSet.resources) // consider newly added resources
        for (changedResource : allResources.filter[!URI.isPathmap]) {
            val change = generateChange(changedResource, originalStateResourceMapping.get(changedResource))
            if (change.containsConcreteChange) {
                propagatedChanges += viewSource.propagateChange(change)
            }
        }
        view.viewChanged = false
        return propagatedChanges
    }

    override close() throws Exception {
        if (!isClosed) {
            closeOriginalState
        }
        view.close
    }

    private def VitruviusChange generateChange(Resource newState, Resource referenceState) {
        if (referenceState === null) {
            return changeResolutionStrategy.getChangeSequenceForCreated(newState)
        } else if (newState === null) {
            return changeResolutionStrategy.getChangeSequenceForDeleted(referenceState)
        } else {
            return changeResolutionStrategy.getChangeSequenceBetween(newState, referenceState)
        }
    }

    private def closeOriginalState() {
        originalStateViewResourceSet.resources.forEach[unload]
        originalStateViewResourceSet.resources.clear
    }

    override withChangeRecordingTrait() {
        val newView = view.withChangeRecordingTrait
        closeOriginalState
        return newView
    }

    override withChangeDerivingTrait(StateBasedChangeResolutionStrategy changeResolutionStrategy) {
        val newView = view.withChangeDerivingTrait(changeResolutionStrategy)
        closeOriginalState
        return newView
    }
}
