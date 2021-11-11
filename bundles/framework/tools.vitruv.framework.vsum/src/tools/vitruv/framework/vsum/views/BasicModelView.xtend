package tools.vitruv.framework.vsum.views

import java.util.Collection
import org.eclipse.emf.common.notify.Notifier
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.framework.change.recording.ChangeRecorder
import tools.vitruv.framework.vsum.ChangePropagationAbortCause
import tools.vitruv.framework.vsum.VirtualModel

import static com.google.common.base.Preconditions.checkNotNull

import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories

/**
 * A basic view that passes by default the entirety of its underlying model as it is.
 * IMPORTANT: This is a prototypical implementation for concept exploration and therefore subject to change.
 */
class BasicModelView implements View, AutoCloseable {
    protected ResourceSet viewResourceSet
    val Collection<Resource> modelResources
    protected boolean modelChanged
    ChangeRecorder changeRecorder
    val VirtualModel virtualModel

    new(Collection<Resource> modelResources, VirtualModel virtualModel) {
        this.modelResources = modelResources
        this.virtualModel = virtualModel
        virtualModel.addChangePropagationListener(this)
        // changeRecorder = new ChangeRecorder(viewResourceSet)
        update
    }

    override rootObjects() {
        viewResourceSet.resources.map[contents].flatten.toList
    }

    override isModified() { // TODO TS: Alternatively this could be done via a model change listener, what is better?
        return viewResourceSet.resources.stream.anyMatch[isModified]
    }

    override hasVSUMChanged() {
        return modelChanged
    }

    override update() { // TODO TS: delegated to the viewtype, so a view has no access on model resources
        if(changeRecorder !== null) {
            if(changeRecorder.isRecording) {
                changeRecorder.endRecording
            }
            if(!changeRecorder.change.EChanges.empty) {
                throw new UnsupportedOperationException("Cannot update from model when view is modified.")
            }
        }
        modelChanged = false
        viewResourceSet = new ResourceSetImpl().withGlobalFactories
        for (modelResource : modelResources) {
            val uri = modelResource.URI
            val viewResource = checkNotNull(
                viewResourceSet.resourceFactoryRegistry?.getFactory(uri)?.createResource(uri),
                "Cannot create view resource: " + uri)
            viewResource.contents.addAll(EcoreUtil.copyAll(modelResource.contents.filter))
            viewResourceSet.resources += viewResource
        }
        changeRecorder = new ChangeRecorder(viewResourceSet)
        changeRecorder.beginRecording
    }

    override <T extends Notifier> commitChanges(T notifier) {
        changeRecorder.endRecording
        // val toSave = determineResource(notifier)  // from basic test view
        // (toSave ?: determineResource(notifier))?.saveOrDelete() // from basic test view
        val propagatedChanges = virtualModel.propagateChange(changeRecorder.change)
        update // view shall not be dirty, thus update on commit
        return propagatedChanges
    }

    override close() throws Exception {
        changeRecorder.close
    }

    /**
     * Filters objects from the model resources when updating the view from the model.
     * Can be overridden in subclasses in order to only show selected elements.
     */
    def protected Collection<EObject> filter(Iterable<EObject> contents) {
        return contents.toList // Default: Do not filter at all.
    }

    override abortedChangePropagation(ChangePropagationAbortCause cause) {
        // do nothing
    }

    override finishedChangePropagation() {
        modelChanged = true
    }

    override startedChangePropagation() {
        // do nothing
    }

}
