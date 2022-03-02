package tools.vitruv.framework.views.impl

import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.views.ViewSelector

import static com.google.common.base.Preconditions.checkArgument

abstract package class AbstractViewType<S extends ViewSelector> implements ViewCreatingViewType<S> {
	@Accessors(PUBLIC_GETTER)
	val String name

	new(String name) {
		checkArgument(name !== null, "view type name must not be null")
		this.name = name
	}
}
