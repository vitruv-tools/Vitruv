package tools.vitruv.framework.vsum.views.impl

import java.util.ArrayList
import java.util.HashMap
import java.util.HashSet
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.domains.StateBasedChangeResolutionStrategy
import tools.vitruv.framework.vsum.views.ChangeableViewSource
import tools.vitruv.framework.vsum.views.View
import tools.vitruv.framework.vsum.views.ViewSelection
import tools.vitruv.framework.vsum.views.ViewSelector

import static com.google.common.base.Preconditions.checkArgument

import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil.isPathmap
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories

/**
 * A {@link View} that derives changes based on the changed state of its resources and allows to propagate them 
 * back to the underlying models using the {@link #commitChanges} method.
 */
class ChangeDerivingView extends AbstractModifiableView {
    val StateBasedChangeResolutionStrategy changeResolutionStrategy
    var StaticSnapshotView originalState
    var HashMap<Resource, Resource> originalStateResourceMapping

    protected new(ViewCreatingViewType<? extends ViewSelector> viewType, ChangeableViewSource viewSource,
        ViewSelection selection, StateBasedChangeResolutionStrategy changeResolutionStrategy) {
        checkArgument(viewType !== null, "view type must not be null")
        checkArgument(selection !== null, "view source must not be null")
        checkArgument(viewSource !== null, "view selection must not be null")
        checkArgument(changeResolutionStrategy !== null, "change resolution strategy must not be null")
        this.viewType = viewType
        this.selection = selection
        this.viewSource = viewSource
        this.changeResolutionStrategy = changeResolutionStrategy
        viewSource.addChangePropagationListener(this)
        viewResourceSet = new ResourceSetImpl().withGlobalFactories
        update()
    }

    override update() {
        originalState?.close
        super.update
        originalState = new StaticSnapshotView(viewType, viewSource, selection)
        originalStateResourceMapping = new HashMap
        viewResourceSet.resources.forEach[resource | originalStateResourceMapping.put(resource, originalState.viewResourceSet.resources.findFirst[URI === resource.URI])]
    }

    override commitChanges() {
        checkNotClosed()
        val propagatedChanges = new ArrayList()
        val allResources = new HashSet(originalStateResourceMapping.keySet)
        allResources.addAll(viewResourceSet.resources) // consider newly added resources
        for (changedResource: allResources.filter[!URI.isPathmap]) {
            val change = generateChange(changedResource, originalStateResourceMapping.get(changedResource))
            if (change.containsConcreteChange) {
                propagatedChanges += viewSource.propagateChange(change)
            }
        }
        viewChanged = false
        return propagatedChanges
    }

    override close() throws Exception {
        super.close
        originalState?.close
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
}
