package tools.vitruv.framework.vsum.views

import tools.vitruv.framework.vsum.views.ViewType

/**
 * A view type that is able to update a given {@link ModifiableView}. 
 */
interface UpdatingViewType extends ViewType {
	/**
     * Updates a view that is created from this viewtype to ensure it is consistent with the virtual model.
     * @param view is the view to be updated.
     */
    def void updateView(ModifiableView view)
}