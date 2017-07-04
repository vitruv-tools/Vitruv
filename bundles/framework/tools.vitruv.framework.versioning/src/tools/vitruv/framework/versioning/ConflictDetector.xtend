package tools.vitruv.framework.versioning

import java.util.List
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

	def List<Conflict> detectConlicts(BranchDiff branchDiff)
}
