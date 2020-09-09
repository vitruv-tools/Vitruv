package tools.vitruv.framework.vsum;

import java.util.List;

import tools.vitruv.framework.change.description.PropagatedChange;
import tools.vitruv.framework.domains.VitruvDomain;

/**
 * The {@link PropagatedChangeListener} is an interface that can be registered at a {@link InternalVirtualModel} 
 * to get notified about propagated changes.
 *
 * @author Andreas Loeffler
 */
interface PropagatedChangeListener {

    /**
     * Whenever changes are made, they must are posted to this method.
     *
     * @param virtualModelName
     *            The name of the virtual model
     * @param sourceDomain
     *            The sourceDomain, may be null
     * @param targetDomain
     *            The targetDomain, may be null
     * @param propagationResult
     *            The list of performed {@link PropagatedChange}s
     */
    def void postChanges(String virtualModelName, VitruvDomain sourceDomain, VitruvDomain targetDomain,
            List<PropagatedChange> propagationResult);

}