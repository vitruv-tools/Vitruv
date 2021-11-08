package tools.vitruv.framework.vsum.views

import java.util.ArrayList
import java.util.Collection
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.vsum.VirtualModel

/**
 * A naive view on a model that passes through only some of the model elements.
 * TODO TS: IMPORTANT, this is a prototypical implementation for concept exploration and therefore subject to change.
 */
class FilterableModelView extends BasicModelView {
    val Collection<EObject> elementsToShow

    new(Collection<Resource> modelResources, Iterable<EObject> elementsToShow, VirtualModel virtualModel) {
        super(modelResources, virtualModel)
        this.elementsToShow = new ArrayList(elementsToShow.toList)
        update
    }

    override protected filter(Iterable<EObject> contents) {
        if(elementsToShow === null) {
            return contents.toList
        }
        return contents.filter[elementsToShow.contains(it)].toList
    }
}
