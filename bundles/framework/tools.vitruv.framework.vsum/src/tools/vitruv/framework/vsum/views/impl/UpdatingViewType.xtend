package tools.vitruv.framework.vsum.views.impl

import tools.vitruv.framework.vsum.views.ViewType
import tools.vitruv.framework.vsum.views.selection.ViewSelector

/**
 * A view type that is able to update a given {@link ModifiableView}. 
 */
interface UpdatingViewType<S extends ViewSelector> extends ViewType<S> {
	/**
     * Updates a view that is created from this viewtype to ensure it is consistent with the virtual model.
     * @param view is the view to be updated.
     */
    def void updateView(ModifiableView view)
}