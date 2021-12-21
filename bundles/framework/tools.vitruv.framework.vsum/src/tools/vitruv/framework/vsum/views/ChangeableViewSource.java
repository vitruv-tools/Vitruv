package tools.vitruv.framework.vsum.views;

import tools.vitruv.framework.vsum.models.ChangeableModelRepository;

/**
 * A specific {@link ViewSource} that can be changed, such that views are able
 * to perform modifications to the underlying models.
 */
public interface ChangeableViewSource extends ViewSource, ChangeableModelRepository {

}
