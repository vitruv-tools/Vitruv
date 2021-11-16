package tools.vitruv.framework.vsum.views

import tools.vitruv.framework.vsum.VirtualModel
import tools.vitruv.framework.vsum.views.selection.BasicViewSelector
import tools.vitruv.framework.vsum.views.selection.ViewSelector

/**
 * A basic view type that allows creating views but has no meaningful selection mechanism.
 * IMPORTANT: This is a prototypical implementation for concept exploration and therefore subject to change.
 */
class BasicViewType extends AbstractViewType {

    new(String name, VirtualModel virtualModel) {
        super(name, virtualModel)
    }

    override createSelector() { // TODO TS: Maybe return a "null selector"
        return new BasicViewSelector(this, virtualModel.resourceSet.resources.map[allContents.toList].flatten.toList)
    }

    override createView(ViewSelector selector) {
        return new BasicModelView(this, virtualModel) // selector does not matter here
    }
}
