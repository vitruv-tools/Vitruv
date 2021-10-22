package tools.vitruv.framework.vsum.views

import java.util.Collection
import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.common.notify.impl.AdapterImpl
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil

/**
 * A basic read-only view that passes by default the entirety of its underlying model as it is.
 * IMPORTANT: This is a prototypical implementation for concept exploration and therefore only temporary.
 */
class BasicModelView implements View {
    protected ResourceSet viewResourceSet
    protected ResourceSet modelResourceSet
    protected boolean modelChanged

    new(ResourceSet modelResourceSet) {
        this.modelResourceSet = modelResourceSet
        registerModelChangeListener
        update
    }

    override getResource() {
        return resource
    }

    override isModified() {
        return resource.modified
    }

    override hasVSUMChanged() {
        return modelChanged
    }

    override update() {
        modelChanged = false
        viewResourceSet = new ResourceSetImpl()
        for (resource : modelResourceSet.resources) {
            val uri = resource.URI
            val newResource = viewResourceSet.resourceFactoryRegistry.getFactory(uri).createResource(uri)
            newResource.contents.addAll(EcoreUtil.copyAll(resource.contents.filter))
            viewResourceSet.resources += resource
        }
    }

    def protected Collection<EObject> filter(Collection<EObject> contents) {
        return contents // Default: Do not filter at all.
    }

    override commit() {
        return false // read only 
    }

    def private registerModelChangeListener() {
        viewResourceSet.eAdapters.add(new AdapterImpl {
            override notifyChanged(Notification notification) {
                modelChanged = true
            }
        })
    }

}
