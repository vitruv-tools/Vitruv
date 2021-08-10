package tools.vitruv.framework.vsum.views

import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.common.notify.impl.AdapterImpl
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.framework.vsum.ModelInstance

/**
 * A basic read-only view that passes by default the entirety of its underlying model as it is.
 * IMPORTANT: This is a prototypical implementation for concept exploration and therefore only temporary.
 */
class BasicModelView implements View { // TODO TS use resource sets or a collection of resources instead of model instances, register monitor resources
    protected val ModelInstance model
    protected Resource resource
    protected boolean modelChanged

    new(ModelInstance model) {
        this.model = model
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
        val resourceSet = new ResourceSetImpl()
        val uri = model.resource.URI
        resource = resourceSet.resourceFactoryRegistry.getFactory(uri).createResource(uri)
        resource.contents.addAll(EcoreUtil.copyAll(model.resource.contents))
        resourceSet.resources += resource
    }

    override commit() {
        return false // read only 
    }

    def private registerModelChangeListener() {
        model.resource.eAdapters.add(new AdapterImpl {
            override notifyChanged(Notification notification) {
                modelChanged = true
            }
        })
    }

}
