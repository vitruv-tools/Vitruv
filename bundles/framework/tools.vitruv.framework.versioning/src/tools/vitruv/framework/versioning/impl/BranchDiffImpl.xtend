package tools.vitruv.framework.versioning.impl

import java.util.List
import org.eclipse.xtend.lib.annotations.Data
import tools.vitruv.framework.versioning.BranchDiff
import tools.vitruv.framework.versioning.ChangeMatch

/**
 * Data class to save changes in 
 * 
 * @author Patrick Stoeckle
 * @version 0.1.0
 * @since 2017-06-12
 */
@Data
class BranchDiffImpl implements BranchDiff {
	List<ChangeMatch> baseChanges
	List<ChangeMatch> compareChanges
}
