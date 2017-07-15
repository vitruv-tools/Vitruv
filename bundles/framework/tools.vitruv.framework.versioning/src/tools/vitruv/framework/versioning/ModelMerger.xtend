package tools.vitruv.framework.versioning

import java.util.List
import org.eclipse.xtext.xbase.lib.Functions.Function1
import tools.vitruv.framework.change.echange.EChange

interface ModelMerger {

	def List<EChange> mergeModels(BranchDiff branchDiff, Function1<Conflict, List<ChangeMatch>> cb)

	def List<EChange> mergeModels(List<ChangeMatch> myChanges, List<ChangeMatch> theirChanges,
		Function1<Conflict, List<ChangeMatch>> cb)
}
