package tools.vitruv.framework.versioning

import tools.vitruv.framework.versioning.impl.BranchDiffCreatorImpl
import java.util.List

/**
 * 
 * 
 * @author Patrick Stoeckle <p.stoeckle@gmx.net>
 * @version 0.1.0
 * @since 2017-06-12
 */
interface BranchDiffCreator {
	BranchDiffCreator instance = BranchDiffCreatorImpl::init

	def BranchDiff createVersionDiff(List<ChangeMatch> sourceChanges, List<ChangeMatch> targetChanges)
}
