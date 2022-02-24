package tools.vitruv.framework.views;

/**
 * A Vitruv view type on the virtual model, providing a view selector and allows
 * creating views.
 * 
 * @param <S> the type of view selector this view type uses
 */
public interface ViewType<S extends ViewSelector> {
	/**
	 * Returns the name of the view type.
	 */
	String getName();

	/**
	 * Returns the view selector of the view type, which allows configuring views
	 * based on the given {@link ChangeableViewSource}.
	 * 
	 * @param viewSource the {@link ChangeableViewSource} from which views shall be
	 *                   derived.
	 * @returns a {@link ViewSelector} for this view type
	 */
	S createSelector(ChangeableViewSource viewSource);
}
