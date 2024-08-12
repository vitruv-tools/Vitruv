package tools.vitruv.framework.views.impl;

import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.framework.views.ChangeableViewSource;
import tools.vitruv.framework.views.ViewSelector;
import tools.vitruv.framework.views.ViewType;

/**
 * A specific view type that is able to create and update views. This is not its
 * public interface but only for internal usage by views and their selectors.
 * 
 * @param <S>  the type of view selector this view type uses.
 * @param <Id> the type of Id the changes to commit must have.
 */
public interface ViewCreatingViewType<S extends ViewSelector, Id> extends ViewType<S> {
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

	/**
	 * Commits the changes made to the view and its containing elements to the
	 * underlying {@link ChangeableViewSource}. Since view elements do not
	 * necessarily correspond to elements of the underlying view source, the view
	 * type is responsible for transforming the given {@link VitruviusChange} such
	 * that the underlying view source can process it.
	 * 
	 * @param view       is the modified view.
	 * @param viewChange are the changes performed to the view.
	 */
	void commitViewChanges(ModifiableView view, VitruviusChange<Id> viewChange);
}
