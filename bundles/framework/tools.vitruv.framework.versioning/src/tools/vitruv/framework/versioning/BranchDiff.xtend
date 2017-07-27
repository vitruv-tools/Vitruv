package tools.vitruv.framework.versioning

import java.util.List
import tools.vitruv.framework.change.description.PropagatedChange

/**
 * 
 * 
 * @author Patrick Stoeckle <p.stoeckle@gmx.net>
 * @version 0.1.0
 * @since 2017-06-12
 */
interface BranchDiff {
	def List<PropagatedChange> getBaseChanges()

	def List<PropagatedChange> getCompareChanges()
}
