package tools.vitruv.framework.versioning

import java.util.List
import java.util.Set

import org.eclipse.xtext.xbase.lib.Functions.Function1

import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.impl.ModelMergerImpl

interface ModelMerger {
	static def ModelMerger createModelMerger() {
		new ModelMergerImpl
	}

	def void init(
		BranchDiff branchDiff,
		Function1<Conflict, List<EChange>> originalCallback,
		Function1<Conflict, List<EChange>> targetCallback
	)

	/**
	 * The original callback is a function which takes a conflict and returns a list
	 * of {@link EChange}s. For the prototype, this could be and function which return either 
	 * the changes of the source or the original changes of the target. 
	 * For a real application, the original callback should call a dialog or 
	 * something, through which the user could select the changes which should remain.
	 * TODO PS The target callback does nothing because {@link PropagatedChange}s cannot be 
	 * reapplied.
	 * @param originalCallback 
	 * @param targetCallback 
	 */
	def void init(
		BranchDiff branchDiff,
		Function1<Conflict, List<EChange>> originalCallback,
		Function1<Conflict, List<EChange>> targetCallback,
		Set<Pair<String, String>> idPairs
	)

	def void compute()

	def List<EChange> getResultingOriginalEChanges()

	def List<EChange> getResultingTriggeredEChanges()

}
