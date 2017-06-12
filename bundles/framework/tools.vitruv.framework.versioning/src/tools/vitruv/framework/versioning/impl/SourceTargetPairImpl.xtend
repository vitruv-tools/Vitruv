package tools.vitruv.framework.versioning.impl

import java.util.Collection
import org.eclipse.xtend.lib.annotations.Data
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.SourceTargetPair

/**
 * Data class to store the relationship of source and correspondent targets of consistency preserving 
 * changes.
 * 
 * @author Patrick Stoeckle <p.stoeckle@gmx.net>
 * @version 0.1.0
 * @since 2017-05-30
 */
@Data
class SourceTargetPairImpl implements SourceTargetPair {
	VURI source
	Collection<VURI> targets
}
