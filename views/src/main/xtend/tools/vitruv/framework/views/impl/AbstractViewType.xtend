package tools.vitruv.framework.views.impl

import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.views.ViewSelector
import org.eclipse.emf.ecore.EPackage

import static com.google.common.base.Preconditions.checkArgument

abstract package class AbstractViewType<S extends ViewSelector, Id> implements ViewCreatingViewType<S, Id> {
	@Accessors(PUBLIC_GETTER)
	val String name

  @Accessors(PUBLIC_GETTER)
	val EPackage metamodel

	new(String name) {
		checkArgument(name !== null, "view type name must not be null")
		this.name = name
    this.metamodel = null
	}

  new(String name, EPackage metamodel) {
		checkArgument(name !== null, "view type name must not be null")
    checkArgument(metamodel !== null, "view type metamodel must not be null")
		this.name = name
    this.metamodel = metamodel
	}
}
