package tools.vitruv.framework.versioning

import java.util.List
import org.eclipse.xtext.xbase.lib.Functions.Function1
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.impl.ModelMergerImpl
import tools.vitruv.framework.change.description.PropagatedChange

interface ModelMerger {
	static def ModelMerger createModelMerger() {
		new ModelMergerImpl
	}

	def void init(BranchDiff branchDiff, Function1<Conflict, List<PropagatedChange>> cb)

	def void init(List<PropagatedChange> myChanges, List<PropagatedChange> theirChanges,
		Function1<Conflict, List<PropagatedChange>> cb)

	def void compute()

	def List<EChange> getResultingOriginalEChanges()

	def List<EChange> getResultingTriggeredEChanges()

}
