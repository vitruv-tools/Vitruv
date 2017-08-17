package tools.vitruv.framework.versioning

import java.util.List
import java.util.Map
import tools.vitruv.framework.versioning.impl.ConflictDetectorImpl
import tools.vitruv.framework.change.echange.EChange

/**
 * 
 * 
 * @author Patrick Stoeckle <p.stoeckle@gmx.net>
 * @version 0.1.0
 * @since 2017-06-12
 */
interface ConflictDetector {
	static def ConflictDetector createConflictDetector() {
		return new ConflictDetectorImpl
	}

	def void addMap(Map<String, String> rootToRootMap)

	def void addIdToIdPair(Pair<String, String> idPair)

	def void init(BranchDiff branchDiff)

	def void compute()

	def List<Conflict> getConflicts()

	def List<EChange> getCommonConflictFreeOriginalEChanges()

	def List<EChange> getCommonConflictFreeTriggeredEChanges()

	def List<EChange> getMyConflictFreeOriginalEChanges()

	def List<EChange> getMyConflictFreeTriggeredEChanges()

	def List<EChange> getTheirConflictFreeOriginalEChanges()

	def List<EChange> getTheirConflictFreeTriggeredEChanges()

	def List<EChange> getConflictFreeOriginalEChanges()

}
