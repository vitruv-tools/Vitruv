package tools.vitruv.framework.change.propagation;

import java.util.List;
import java.util.Optional;

import tools.vitruv.framework.change.description.PropagatedChange;
import tools.vitruv.framework.change.description.VitruviusChange;
import tools.vitruv.framework.change.echange.id.IdResolver;

/**
 * A model repository that can be changed by applying @{link VitruviusChange}s.
 * {@link ChangePropagationListener}s can be registered to 
 * receive notifications about changes to the model repository.
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
	
	List<PropagatedChange> propagateChange(VitruviusChange change, Optional<IdResolver> idResolver);

	void addChangePropagationListener(ChangePropagationListener propagationListener);

	void removeChangePropagationListener(ChangePropagationListener propagationListener);
}
