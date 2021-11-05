package tools.vitruv.framework.vsum.views

import java.util.ArrayList
import java.util.Collection
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource

/**
 * A naive view on a model that passes through only some of the model elements.
 * TODO TS: IMPORTANT, this is a prototypical implementation for concept exploration and therefore only temporary.
 */
class FilterableModelView extends BasicModelView {
    val Collection<EObject> elementsToShow

    new(Collection<Resource> modelResources, Iterable<EObject> elementsToShow) {
        super(modelResources)
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
