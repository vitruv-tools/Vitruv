package tools.vitruv.framework.views.impl

import tools.vitruv.framework.views.ChangeableViewSource
import tools.vitruv.framework.views.View
import tools.vitruv.framework.views.ViewSource
import tools.vitruv.framework.views.selectors.DirectViewElementSelector
import tools.vitruv.framework.views.util.ResourceCopier

import static com.google.common.base.Preconditions.checkArgument

import static extension tools.vitruv.framework.views.util.ResourceCopier.requiresFullCopy

/**
 * A view type that allows creating views based on a basic element-wise selection mechanism
 * and providing a one-to-one (identity) mapping of elements within the {@link ViewSource}
 * to a created {@link View}.
 */
class IdentityMappingViewType extends AbstractViewType<DirectViewElementSelector> {

	new(String name) {
		super(name)
	}

	override createSelector(ChangeableViewSource viewSource) {
		return new DirectViewElementSelector(this, viewSource, viewSource.viewSourceModels.flatMap [
			if (requiresFullCopy) {
				#[contents.head] // We can only copy writable UML resources as a whole, so no option to select specific root elements
			} else {
				contents
			}
		].filterNull.toList)
	}

	override createView(DirectViewElementSelector selector) {
		checkArgument(selector.viewType === this, "cannot create view with selector for different view type")
		return new BasicView(selector.viewType, selector.viewSource, selector.selection)
	}

	override updateView(ModifiableView view) {
		view.modifyContents [ viewResourceSet |
			viewResourceSet.resources.forEach[unload]
			viewResourceSet.resources.clear
			val viewSources = view.viewSource.viewSourceModels
			val selection = view.selection
			val resourcesWithSelectedElements = viewSources.filter[contents.exists[selection.isViewObjectSelected(it)]]
			ResourceCopier.copyViewSourceResources(resourcesWithSelectedElements, viewResourceSet) [selection.isViewObjectSelected(it)]
		]
	}
}
