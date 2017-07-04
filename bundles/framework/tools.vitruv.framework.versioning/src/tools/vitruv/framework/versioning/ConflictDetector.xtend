package tools.vitruv.framework.versioning

import tools.vitruv.framework.versioning.conflict.Conflict
import java.util.Map
import tools.vitruv.framework.versioning.impl.ConflictDetectorImpl

/**
 * 
 * 
 * @author Patrick Stoeckle <p.stoeckle@gmx.net>
 * @version 0.1.0
 * @since 2017-06-12
 */
interface ConflictDetector {
	static ConflictDetector instance = ConflictDetectorImpl::init

	def void addMap(Map<String, String> rootToRootMap)

	def Conflict detectConlicts(BranchDiff branchDiff)
}
