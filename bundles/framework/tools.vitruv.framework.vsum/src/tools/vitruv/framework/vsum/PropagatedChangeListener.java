package tools.vitruv.framework.vsum;

import java.util.List;

import tools.vitruv.framework.change.description.PropagatedChange;
import tools.vitruv.framework.domains.VitruvDomain;

/**
 * The {@link PropagatedChangeListener} is the interface used to communicate with the change
 * visualization. Usually the instances are created through the factory method at
 * ChangeVisualization.
 *
 * @author Andreas Loeffler
 */
public interface PropagatedChangeListener {

    /**
     * Whenever changes are made, they must be posted with this method to the visualization-listener.
     *
     * @param sourceDomain
     *            The sourceDomain, may be null
     * @param targetModelInfo
     *            The targetDomain, may be null
     * @param propagationResult
     *            The results to visualize
     */
    void postChanges(VitruvDomain sourceDomain, VitruvDomain targetDomain, List<PropagatedChange> propagationResult);

}
