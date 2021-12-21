package tools.vitruv.framework.vsum.views.impl

import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.vsum.views.selection.ViewSelector

abstract class AbstractViewType<S extends ViewSelector> implements UpdatingViewType<S> {
	@Accessors(PUBLIC_GETTER)
    val String name

    new(String name) {
        this.name = name
    }
     
}
