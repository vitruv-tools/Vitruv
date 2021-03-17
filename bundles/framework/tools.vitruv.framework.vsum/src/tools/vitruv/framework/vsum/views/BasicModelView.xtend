package tools.vitruv.framework.vsum.views

import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.common.notify.impl.AdapterImpl
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.framework.util.datatypes.ModelInstance

/**
 * A basic read-only view that passes the entirety of its underlying model as it is.
 */
class BasicModelView implements View {
    val ModelInstance model
    Resource resource
    boolean modelChanged

    new(ModelInstance model) {
        this.model = model
        model.resource.eAdapters.add(new AdapterImpl {
            override notifyChanged(Notification notification) {
                modelChanged = true
            }
        })
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
        val resourceSet = new ResourceSetImpl()
        val uri = model.resource.URI
        resource = resourceSet.resourceFactoryRegistry.getFactory(uri).createResource(uri)
        resource.contents.addAll(EcoreUtil.copyAll(model.resource.contents))
        resourceSet.resources += resource
    }

    override commit() {
        return false // read only 
    }

}
