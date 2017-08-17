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
