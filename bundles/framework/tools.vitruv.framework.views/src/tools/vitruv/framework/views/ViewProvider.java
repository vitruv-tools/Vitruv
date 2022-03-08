package tools.vitruv.framework.views;

/**
 * A provider for views, i.e. this object contains the source models to create a
 * view from and provides according selectors for given view types to
 * instantiate a view from.
 */
public interface ViewProvider {
	/**
	 * Returns a view selector for the given {@link ViewType} based on the source
	 * models covered by this object.
	 * 
	 * @param viewType the {@link ViewType} to create a selector for
	 * @returns a {@link ViewSelector} for given view type and the source models
	 *          covered by this object
	 */
	<S extends ViewSelector> S createSelector(ViewType<S> viewType);
}
