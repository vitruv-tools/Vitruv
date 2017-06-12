package tools.vitruv.framework.versioning

import java.io.Serializable
import java.util.List
import java.util.Map
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.util.datatypes.VURI

/**
 * Data class to track original and correspondent changes.
 * 
 * @author Patrick Stoeckle <p.stoeckle@gmx.net>
 * @version 0.1.0
 * @since 2017-06-12
 */
interface ChangeMatch extends Serializable {
	def VURI getOriginalVURI()

	def TransactionalChange getOriginalChange()

	def Map<VURI, List<TransactionalChange>> getTargetToCorrespondentChanges()
}
