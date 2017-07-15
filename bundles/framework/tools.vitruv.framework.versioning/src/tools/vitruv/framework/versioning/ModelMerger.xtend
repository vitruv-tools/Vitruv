package tools.vitruv.framework.versioning

import java.util.List
import org.eclipse.xtext.xbase.lib.Functions.Function1
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.impl.ModelMergerImpl

interface ModelMerger {
	static def ModelMerger createModelMerger() {
		new ModelMergerImpl
	}

	def void init(BranchDiff branchDiff, Function1<Conflict, List<ChangeMatch>> cb)

	def void init(List<ChangeMatch> myChanges, List<ChangeMatch> theirChanges,
		Function1<Conflict, List<ChangeMatch>> cb)

	def void compute()

	def List<EChange> getResultingSourceEChanges()

	def List<EChange> getResultingTargetEChanges()

}
