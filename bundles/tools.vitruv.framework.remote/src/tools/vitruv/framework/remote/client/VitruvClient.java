package tools.vitruv.framework.remote.client;

import tools.vitruv.framework.views.ViewProvider;
import tools.vitruv.framework.views.ViewTypeProvider;

/**
 * A vitruv client can remotely access the available {@link tools.vitruv.framework.views.ViewType}s of a vitruvius instance and query
 * {@link tools.vitruv.framework.views.ViewSelector}s in order to create remotely editable {@link tools.vitruv.framework.views.View}s.
 */
public interface VitruvClient extends ViewTypeProvider, ViewProvider {
}
