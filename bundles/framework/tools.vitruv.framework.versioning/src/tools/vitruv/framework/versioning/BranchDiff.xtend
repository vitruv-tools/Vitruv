package tools.vitruv.framework.versioning

import java.util.List
import tools.vitruv.framework.versioning.commit.ChangeMatch

/**
 * 
 * 
 * @author Patrick Stoeckle <p.stoeckle@gmx.net>
 * @version 0.1.0
 * @since 2017-06-12
 */
interface BranchDiff {
	def List<ChangeMatch> getBaseChanges()

	def List<ChangeMatch> getCompareChanges()
}
