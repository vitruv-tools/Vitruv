package tools.vitruv.framework.vsum.views

import tools.vitruv.framework.util.datatypes.ModelInstance
import tools.vitruv.framework.vsum.views.selection.BasicViewSelector
import tools.vitruv.framework.vsum.views.selection.ViewSelector

class BasicViewType implements ViewType {

    val ModelInstance model
    val String name

    new(String name, ModelInstance model) {
        this.name = name
        this.model = model
    }

    override createSelector() {
        return new BasicViewSelector(this, model.resource.allContents.toList)
    }

    override createView(ViewSelector selector) {
        return new FilterableModelView(model, selector.selectedElements)
    }

    override getName() {
        return name
    }

}
