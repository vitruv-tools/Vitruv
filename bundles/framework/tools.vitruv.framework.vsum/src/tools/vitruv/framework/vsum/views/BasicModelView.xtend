package tools.vitruv.framework.vsum.views

import java.util.Collection
import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.common.notify.impl.AdapterImpl
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil

/**
 * A basic read-only view that passes by default the entirety of its underlying model as it is.
 * IMPORTANT: This is a prototypical implementation for concept exploration and therefore only temporary.
 */
class BasicModelView implements View {
    protected ResourceSet viewResourceSet
    protected Collection<Resource> modelResources
    protected boolean modelChanged

    new(Collection<Resource> modelResources) {
        this.modelResources = modelResources
        modelResources.registerModelChangeListener
        update
    }

    override rootObjects() {
        modelResources.map[contents].flatten.toList
    }

    override isModified() { // TODO TS: Alternatively this could be done via a model change listener, what is better?
        return viewResourceSet.resources.stream.anyMatch[isModified]
    }

    override hasVSUMChanged() {
        return modelChanged
    }

    override update() { // TODO TS: should be delegated to viewtype, so a view has no access on model resources
        modelChanged = false
        viewResourceSet = new ResourceSetImpl()
        for (resource : modelResources) {
            val uri = resource.URI
            val newResource = viewResourceSet.resourceFactoryRegistry.getFactory(uri).createResource(uri)
            newResource.contents.addAll(EcoreUtil.copyAll(resource.contents.filter))
            viewResourceSet.resources += resource
        }
    }

    def protected Collection<EObject> filter(Collection<EObject> contents) {
        return contents // Default: Do not filter at all.
    }

    override commitChanges() {
        return false // read only // TODO TS: should be done like in tests
    }

    def private registerModelChangeListener(Collection<Resource> modelResources) {
        modelResources.forEach [
            eAdapters.add(new AdapterImpl {
                override notifyChanged(Notification notification) {
                    modelChanged = true
                }
            })
        ]
    }
}
