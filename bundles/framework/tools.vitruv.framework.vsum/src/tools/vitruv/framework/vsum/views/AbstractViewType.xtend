package tools.vitruv.framework.vsum.views

import tools.vitruv.framework.vsum.VirtualModel
import tools.vitruv.framework.vsum.views.selection.ViewSelector

import org.eclipse.xtend.lib.annotations.Accessors

package abstract class AbstractViewType implements UpdatingViewType {

	@Accessors(PUBLIC_GETTER)
    val String name
    // TODO Instance variable must not be protected
    protected val VirtualModel virtualModel

    new(String name, VirtualModel virtualModel) { // TODO TS: How should the viewtype access the models? inversion of control?
        this.name = name
        this.virtualModel = virtualModel
    }

    override createView(ViewSelector selector) {
        return new BasicModelView(this, selector, virtualModel)
    }
     
}
