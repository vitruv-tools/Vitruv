package tools.vitruv.framework.vsum.views

import java.util.ArrayList
import java.util.Collection
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.util.datatypes.ModelInstance

/**
 * A basic read-only view on a model that passes through only some of the model elements.
 */
class FilterableModelView extends BasicModelView {
    val Collection<EObject> elementsToShow

    new(ModelInstance model, Iterable<EObject> elementsToShow) {
        super(model)
        this.elementsToShow = new ArrayList(elementsToShow.toList)
        update
    }

    override update() {
        // TODO TS implement filtering update method
    }

}
