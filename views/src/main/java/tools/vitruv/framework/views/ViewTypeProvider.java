package tools.vitruv.framework.views;

import java.util.Collection;

/**
 * A provider for view types.
 */
public interface ViewTypeProvider {
	/**
	 * Returns the view types covered by this provider.
	 * 
	 * @return a collection of {@link ViewType}s
	 */
	public Collection<ViewType<?>> getViewTypes();
}
