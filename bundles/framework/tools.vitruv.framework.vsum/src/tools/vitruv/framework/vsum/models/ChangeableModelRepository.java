package tools.vitruv.framework.vsum.models;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

import tools.vitruv.framework.change.description.PropagatedChange;
import tools.vitruv.framework.change.description.VitruviusChange;

/**
 * A model repository that can be changed by applying either
 * {@link VitruviusChange}s or updated states of {@link Resource}s.
 * {@link ChangePropagationListener} can be registered to receive notifications
 * about changes to the model repository.
 */
public interface ChangeableModelRepository {
	List<PropagatedChange> propagateChange(VitruviusChange change);

	/**
	 * Propagates delta-based changes as long as the location and the name of the
	 * resource was not changed. Should be used when there are no change sequences
	 * available and consistency needs to be preserved based on the changes between
	 * the previous and new state of a model.
	 * 
	 * @param newState is the {@link Resource} of the new state
	 */
	List<PropagatedChange> propagateChangedState(Resource newState);

	/**
	 * Propagates delta-based changes. Allows to change the location and the name of
	 * the resource. Should be used when there are no change sequences available and
	 * consistency needs to be preserved based on the changes between the previous
	 * and new state of a model.
	 * 
	 * @param newState    is the {@link Resource} of the new state
	 * @param oldLocation specifies the previous location of the resource to avoid
	 *                    problems with renaming or moving elements
	 */
	List<PropagatedChange> propagateChangedState(Resource newState, URI oldLocation);

	void addChangePropagationListener(ChangePropagationListener propagationListener);

	void removeChangePropagationListener(ChangePropagationListener propagationListener);
}
