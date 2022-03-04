package tools.vitruv.framework.views.impl

import java.util.ArrayList
import java.util.HashMap
import java.util.HashSet
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtend.lib.annotations.Delegate
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.views.CommittableView
import tools.vitruv.framework.views.View
import tools.vitruv.framework.views.changederivation.StateBasedChangeResolutionStrategy

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
    var BasicView originalState
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
        originalState.close
        view.update
        setupReferenceState
    }

    private def setupReferenceState() {
        originalState = new BasicView(view.viewType, viewSource, selection)
        originalStateResourceMapping = new HashMap
        view.viewResourceSet.resources.forEach[resource | originalStateResourceMapping.put(resource, originalState.viewResourceSet.resources.findFirst[URI === resource.URI])]
    }

    override commitChanges() {
        view.checkNotClosed()
        val propagatedChanges = new ArrayList()
        val allResources = new HashSet(originalStateResourceMapping.keySet)
        allResources.addAll(view.viewResourceSet.resources) // consider newly added resources
        for (changedResource: allResources.filter[!URI.isPathmap]) {
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
            originalState.close
        }
        view.close
    }

    private def VitruviusChange generateChange(Resource newState, Resource referenceState) {
        if (referenceState === null) {
            return changeResolutionStrategy.getChangeSequenceForCreated(newState)
        }
        else if (newState === null) {
            return changeResolutionStrategy.getChangeSequenceForDeleted(referenceState)
        }
        else {
            return changeResolutionStrategy.getChangeSequenceBetween(newState, referenceState)
        }
    }

    override withChangeRecordingTrait() {
        val newView = view.withChangeRecordingTrait
        originalState.close
        return newView
    }

    override withChangeDerivingTrait(StateBasedChangeResolutionStrategy changeResolutionStrategy) {
        val newView = view.withChangeDerivingTrait(changeResolutionStrategy)
        originalState.close
        return newView
    }
}
