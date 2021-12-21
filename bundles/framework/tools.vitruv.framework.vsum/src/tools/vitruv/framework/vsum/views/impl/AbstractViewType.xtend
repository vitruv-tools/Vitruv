package tools.vitruv.framework.vsum.views.impl

import tools.vitruv.framework.vsum.views.ViewSelector

import org.eclipse.xtend.lib.annotations.Accessors

abstract class AbstractViewType<S extends ViewSelector> implements ViewCreatingViewType<S> {
	@Accessors(PUBLIC_GETTER)
    val String name
    
    new(String name) {
        this.name = name
    }
}
