package tools.vitruv.framework.change.models;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

import tools.vitruv.framework.change.description.PropagatedChange;
import tools.vitruv.framework.change.description.VitruviusChange;

/**
 * A model repository that can be changed by applying changes or updated
 * resources. To this end, {@link VitruviusChange}s or updated states of
 * {@link Resource}s can be passed. {@link ChangePropagationListener} can be
 * registered to receive notifications about changes to the model repository.
 */
public interface ChangeableModelRepository {
	/**
	 * Propagates the given change to the model repository. This will apply the
	 * given {@link VitruviusChange} to the underlying resources and may execute
	 * further mechanisms, such as preserving the consistency of the underlying
	 * models.
	 * 
	 * @param change the {@link VitruviusChange} to be applied, not
	 *               <code>null</code>
	 */
	List<PropagatedChange> propagateChange(VitruviusChange change);

	/**
	 * Updates the resources in the model repository such that it contains the new
	 * given state. To this end, the method extracts the changes between the old
	 * state of the {@link Resource} stored in the repository at the same location
	 * as the given {@code newState} (i.e., with the same {@link URI}) and the new
	 * one and applies them calling {@link #propagateChange}. If the repository does
	 * not contain a resource with the {@link URI} of {@code newState}, the resource
	 * is considered as created completely anew.
	 * 
	 * @param newState the {@link Resource} with a new state, not <code>null</code>
	 */
	List<PropagatedChange> propagateChangedState(Resource newState);

	/**
	 * Updates the resources in the model repository such that it contains the new
	 * given state that may have been moved from the given old location. The new
	 * resource state is given as {@link newState} and the old resource state within
	 * the repository is identified by the given {@link URI} {@link oldLocation}. To
	 * update the repository, the method extracts the changes between the old state
	 * stored in the repository at {@code oldLocation} and the new one given as
	 * {@code newState} and applies them calling {@link #propagateChange}. If the
	 * repository does not contain a resource at {@code oldLocation}, the given
	 * {@code newState} is considered as created completely anew. If
	 * {@code newState} is <code>null</code> but the repository contains a resource
	 * at {@code oldLocation}, it will be considered deleted. If both
	 * {@code newState} and {@code oldLocation} are <code>null</code>, an exception
	 * will be thrown.
	 * 
	 * @param newState    the {@link Resource} with a new state, may only be
	 *                    <code>null</code> if {@code oldLocation} is not
	 * @param oldLocation the {@link URI} of the previous location of the
	 *                    potentially renamed or deleted {@code Resource}, may only
	 *                    be <code>null</code> if {@code newState} is not
	 */
	List<PropagatedChange> propagateChangedState(Resource newState, URI oldLocation);

	void addChangePropagationListener(ChangePropagationListener propagationListener);

	void removeChangePropagationListener(ChangePropagationListener propagationListener);
}
