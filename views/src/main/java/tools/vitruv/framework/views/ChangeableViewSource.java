package tools.vitruv.framework.views;

import tools.vitruv.change.composite.propagation.ChangeableModelRepository;

/**
 * A specific {@link ViewSource} that can be changed, such that views are able
 * to perform modifications to the underlying models.
 */
public interface ChangeableViewSource extends ViewSource, ChangeableModelRepository {

}
