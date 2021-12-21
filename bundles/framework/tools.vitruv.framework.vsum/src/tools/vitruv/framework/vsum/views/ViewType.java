package tools.vitruv.framework.vsum.views;

import tools.vitruv.framework.vsum.views.selection.ViewSelector;

/**
 * A Vitruv viewtype on the virtual model, providing a view selector and allows
 * creating views.
 * 
 * @param <S> the type of view selector this view type uses
 */
public interface ViewType<S extends ViewSelector> {
	/**
	 * Returns the uniquely identifying name of the view type.
	 */
	String getName();

	/**
	 * Returns the view selector of the viewtype, which allows configuring views
	 * based on the given {@link ChangeableViewSource}.
	 * 
	 * @param viewSource the {@link ChangeableViewSource} from which views shall be
	 *                   derived.
	 * @returns a {@link ViewSelector} for this viewtype
	 */
	S createSelector(ChangeableViewSource viewSource);
}
