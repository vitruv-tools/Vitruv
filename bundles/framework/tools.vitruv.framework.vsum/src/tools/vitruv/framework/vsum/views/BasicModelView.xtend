package tools.vitruv.framework.vsum.views

import java.util.Collection
import java.util.function.Consumer
import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.common.notify.Notifier
import org.eclipse.emf.common.notify.impl.AdapterImpl
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.framework.change.recording.ChangeRecorder
import tools.vitruv.framework.vsum.VirtualModel

import static com.google.common.base.Preconditions.checkNotNull

import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories

/**
 *
 * A basic view that passes by default the entirety of its underlying model as it is.
 * IMPORTANT: This is a prototypical implementation for concept exploration and therefore subject to change.
 */
class BasicModelView implements View, AutoCloseable {
    protected ResourceSet viewResourceSet
    val Collection<Resource> modelResources
    protected boolean modelChanged
    val ChangeRecorder changeRecorder
    val VirtualModel virtualModel

    new(Collection<Resource> modelResources, VirtualModel virtualModel) {
        this.modelResources = modelResources
        this.virtualModel = virtualModel
        modelResources.registerModelChangeListener
        update
        changeRecorder = new ChangeRecorder(viewResourceSet)
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

    override update() { // TODO TS: should this be delegated to the viewtype, so a view has no access on model resources?
        modelChanged = false
        viewResourceSet = checkNotNull(new ResourceSetImpl().withGlobalFactories, "Could not create view resource set!")
        for (resource : modelResources) {
            val uri = resource.URI
            val newResource = viewResourceSet.resourceFactoryRegistry.getFactory(uri).createResource(uri)
            newResource.contents.addAll(EcoreUtil.copyAll(resource.contents.filter))
            viewResourceSet.resources += newResource
        }
    }

    override <T extends Notifier> commitChanges(T notifier, Consumer<T> consumer) {
        /*
         * TODO TS: It seems there is two ways how to control recording:
         * 1. Always record but add or remove notifiers from the recorder to control when to record
         * 2. Only start/stop recording when actually required
         * What is the correct way here?
         */
        changeRecorder.addToRecording(notifier)
        changeRecorder.beginRecording

        val toSave = determineResource(notifier)
        consumer.accept(notifier)
        (toSave ?: determineResource(notifier))?.saveOrDelete()

        changeRecorder.endRecording
        changeRecorder.removeFromRecording(notifier)

        val propagatedChanges = virtualModel.propagateChange(changeRecorder.change)
        // TODO TS: the change publishing test view will renewResourceCache() here if required. When and why?
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

    def private determineResource(Notifier notifier) { // TODO TS: Copied from basic test view
        switch (notifier) {
            Resource: notifier
            EObject: notifier.eResource
            default: null
        }
    }

    def private saveOrDelete(Resource resource) { // TODO TS: Copied from basic test view
        if(resource.contents.isEmpty) {
            resource.delete(emptyMap)
        } else {
            resource.save(emptyMap)
        }
    }

    def private registerModelChangeListener(Collection<Resource> modelResources) {
        modelResources.forEach [
            eAdapters.add(createModelChangeListener)
            allContents.forEach[eAdapters.add(createModelChangeListener)] // TODO TS: Why is observing the resource not enough?
        ]
    }

    def private AdapterImpl createModelChangeListener() {
        new AdapterImpl {
            override notifyChanged(Notification notification) {
                modelChanged = true
            }
        }
    }

}
