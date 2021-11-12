package tools.vitruv.framework.vsum.views

import tools.vitruv.framework.vsum.VirtualModel
import tools.vitruv.framework.vsum.views.selection.BasicViewSelector
import tools.vitruv.framework.vsum.views.selection.ViewSelector

class BasicViewType implements ViewType {

    val String name
    val VirtualModel virtualModel

    new(String name, VirtualModel virtualModel) { // TODO TS: How should the viewtype access the models? inversion of control?
        this.name = name
        this.virtualModel = virtualModel // TODO
    }

    override createSelector() {
        return new BasicViewSelector(this, virtualModel.resourceSet.resources.map[allContents.toList].flatten.toList)
    }

    override createView(ViewSelector selector) {
        return new FilterableModelView(virtualModel.resourceSet.resources, selector.selectedElements, virtualModel)
    }

    override getName() {
        return name
    }

}
