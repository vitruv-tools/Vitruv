package tools.vitruv.framework.vsum.views

import java.util.ArrayList
import java.util.Collection
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil

/**
 * A basic read-only view on a model that passes through only some of the model elements.
 *  IMPORTANT: This is a prototypical implementation for concept exploration and therefore only temporary.
 */
class FilterableModelView extends BasicModelView {
    val Collection<EObject> elementsToShow

    new(ResourceSet modelResourceSet, Iterable<EObject> elementsToShow) {
        super(modelResourceSet)
        this.elementsToShow = new ArrayList(elementsToShow.toList)
        update
    }

    override update() { // TODO TS duplicated code from super class
        if(elementsToShow === null) {
            super.update
        } else {
            modelChanged = false
            viewResourceSet = new ResourceSetImpl()
            for (resource : modelResourceSet.resources) {
                val uri = resource.URI
                val newResource = viewResourceSet.resourceFactoryRegistry.getFactory(uri).createResource(uri)
                val filteredContent = resource.contents.filter[elementsToShow.contains(it)].toList
                newResource.contents.addAll(EcoreUtil.copyAll(filteredContent))
                viewResourceSet.resources += resource
            }
        }
    }
}
