package tools.vitruv.framework.views.impl;

import tools.vitruv.framework.views.ViewType;
import tools.vitruv.framework.views.ViewSelector;

/**
 * A specific view type that is able to create and update views. This is not its
 * public interface but only for internal usage by views and their selectors.
 * 
 * @param <S> the type of view selector this view type uses
 */
public interface ViewCreatingViewType<S extends ViewSelector> extends ViewType<S> {
	/**
	 * Creates a view for the given {@link ViewSelector}. The selector must have
	 * been created by calling the {@link #createSelector} method of the same
	 * {@link ViewCreatingViewType}.
	 * 
	 * @param selector the {@link ViewSelector} to create a view for
	 * @return a {@link ModifiableView} with elements according to the selector.
	 */
	ModifiableView createView(S selector);

	/**
	 * Updates a view that is created from this view type to ensure it is consistent
	 * with the virtual model.
	 * 
	 * @param view is the view to be updated.
	 */
	void updateView(ModifiableView view);
}
