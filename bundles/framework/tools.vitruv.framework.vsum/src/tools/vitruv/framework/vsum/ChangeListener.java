package tools.vitruv.framework.vsum;

import java.util.List;

import tools.vitruv.framework.change.description.PropagatedChange;

/**
 * The {@link ChangeListener} is the interface used to communicate with the change visualization.
 *
 * @author Andreas Löffler
 */
public interface ChangeListener {

    /**
     * Whenever changes are made, they must be posted with this method to the visualization-listener. An
     * equal name is used to group all propagation results of one model into a single ui element.
     *
     * @param name
     *            An equal name is used to group different changes of one model
     * @param propagationResult
     *            The results to visualize
     */
    void postChanges(String name, List<PropagatedChange> propagationResult);

}
