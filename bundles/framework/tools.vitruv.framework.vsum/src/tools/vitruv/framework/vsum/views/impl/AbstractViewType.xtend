package tools.vitruv.framework.vsum.views.impl

import tools.vitruv.framework.vsum.VirtualModel

import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.vsum.views.selection.ViewSelector

abstract class AbstractViewType<S extends ViewSelector> implements UpdatingViewType<S> {

	@Accessors(PUBLIC_GETTER)
    val String name
    // TODO Instance variable must not be protected
    protected val VirtualModel virtualModel

    new(String name, VirtualModel virtualModel) { // TODO TS: How should the viewtype access the models? inversion of control?
        this.name = name
        this.virtualModel = virtualModel
    }
     
}
