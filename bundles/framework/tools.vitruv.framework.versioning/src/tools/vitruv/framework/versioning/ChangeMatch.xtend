package tools.vitruv.framework.versioning

import java.io.Serializable
import java.util.List
import java.util.Map
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.impl.ChangeMatchImpl
import tools.vitruv.framework.change.echange.EChange

interface ChangeMatch extends Serializable {
	static def createChangeMatch(VURI originalVURI, TransactionalChange originalChange,
		Map<VURI, List<TransactionalChange>> targetToCorrespondentChanges) {
		new ChangeMatchImpl(originalVURI, originalChange, targetToCorrespondentChanges)
	}

	def VURI getOriginalVURI()

	def TransactionalChange getOriginalChange()

	def Map<VURI, List<TransactionalChange>> getTargetToCorrespondentChanges()

	def List<EChange> getAllEChanges()
}
