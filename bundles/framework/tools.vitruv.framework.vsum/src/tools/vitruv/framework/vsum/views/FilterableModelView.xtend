package tools.vitruv.framework.vsum.views

import java.util.ArrayList
import java.util.Collection
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.framework.vsum.internal.ModelInstance

/**
 * A basic read-only view on a model that passes through only some of the model elements.
 *  IMPORTANT: This is a prototypical implementation for concept exploration and therefore only temporary.
 */
class FilterableModelView extends BasicModelView {
    val Collection<EObject> elementsToShow

    new(ModelInstance model, Iterable<EObject> elementsToShow) {
        super(model)
        this.elementsToShow = new ArrayList(elementsToShow.toList)
        update
    }

    override update() { // TODO TS duplicated code from super class
        if(elementsToShow === null) {
            super.update
        } else {
            modelChanged = false
            val resourceSet = new ResourceSetImpl()
            val uri = model.resource.URI
            resource = resourceSet.resourceFactoryRegistry.getFactory(uri).createResource(uri)
            val filteredContent = model.resource.contents.filter[elementsToShow.contains(it)].toList
            resource.contents.addAll(EcoreUtil.copyAll(filteredContent))
            resourceSet.resources += resource
        }
    }
}
