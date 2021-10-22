package tools.vitruv.framework.vsum.views

import java.util.ArrayList
import java.util.Collection
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.ResourceSet

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

    override protected filter(Collection<EObject> contents) {
        if(elementsToShow === null) {
            return contents
        }
        return contents.filter[elementsToShow.contains(it)].toList
    }
}
