package tools.vitruv.framework.views.impl

import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import tools.vitruv.framework.views.ChangeableViewSource
import tools.vitruv.framework.views.ViewSelection
import tools.vitruv.framework.views.ViewSelector

import static com.google.common.base.Preconditions.checkArgument
import static com.google.common.base.Preconditions.checkState

import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories

/**
 * A read-only {@link View} that creates a static snapshot of its state on creation.
 */
package class StaticSnapshotView extends AbstractModifiableView {
    boolean isInitialized

    protected new(ViewCreatingViewType<? extends ViewSelector> viewType, ChangeableViewSource viewSource,
        ViewSelection selection) {
        checkArgument(viewType !== null, "view type must not be null")
        checkArgument(selection !== null, "view source must not be null")
        checkArgument(viewSource !== null, "view selection must not be null")
        this.viewType = viewType
        this.selection = selection
        this.viewSource = viewSource
        viewSource.addChangePropagationListener(this)
        viewResourceSet = new ResourceSetImpl().withGlobalFactories
        update()
        isInitialized = true
    }

    override modifyContents((ResourceSet)=>void modificationFunction) {
        checkState(!isInitialized, "view is already initialized")
        super.modifyContents(modificationFunction)
    }

    override update() {
        checkState(!isInitialized, "view is already initialized")
        super.update()
    }

    override commitChanges() {
        throw new UnsupportedOperationException("Unsupported operation")
    }

    override moveRoot(EObject object, URI newLocation) {
        throw new UnsupportedOperationException("Unsupported operation")
    }

    override registerRoot(EObject object, URI persistAt) {
        throw new UnsupportedOperationException("Unsupported operation")
    }
}
