package tools.vitruv.framework.versioning

import java.util.Collection
import org.eclipse.xtend.lib.annotations.Data
import tools.vitruv.framework.util.datatypes.VURI

/**
 * Data class to store the relationship of source and correspondent targets of consistency preserving 
 * changes.
 * 
 * @author Patrick Stoeckle
 * @version 0.1.0
 * @since 2017-05-30
 */
@Data
class SourceTargetPair {
	VURI source
	Collection<VURI> targets
}
