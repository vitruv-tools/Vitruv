package tools.vitruv.framework.versioning

import java.util.List
import org.eclipse.xtend.lib.annotations.Data

/**
 * Data class to save changes in 
 * 
 * @author Patrick Stoeckle
 * @version 0.1.0
 * @since 2017-06-12
 */
@Data
package class ChangePair {
	List<ChangeMatch> baseChanges
	List<ChangeMatch> compareChanges
}
