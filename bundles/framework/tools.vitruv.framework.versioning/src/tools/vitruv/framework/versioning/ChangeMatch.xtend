package tools.vitruv.framework.versioning

import java.util.List
import java.util.Map

import org.eclipse.xtend.lib.annotations.Data

import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.util.datatypes.VURI

/**
 * Data class to track original and correspondent changes.
 * 
 * @author Patrick Stoeckle
 * @version 0.1.0
 * @since 2017-05-30
 */
@Data
class ChangeMatch {
	VURI originalVURI
	TransactionalChange originalChange
	Map<VURI, List<TransactionalChange>> targetToCorrespondentChanges
}
