package tools.vitruv.framework.versioning

import java.io.Serializable
import java.util.List
import java.util.Map
import tools.vitruv.framework.change.description.ChangeCloner
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.impl.ChangeMatchImpl

interface ChangeMatch extends Serializable {
	
	static def createChangeMatch(VURI originalVURI, TransactionalChange originalChange,
		Map<VURI, List<VitruviusChange>> targetToCorrespondentChanges) {
		new ChangeMatchImpl(originalVURI, originalChange, targetToCorrespondentChanges)
	}

	static def createChangeMatch(PropagatedChange propagatedChange) {
		val cloner = new ChangeCloner
		val originalChange = propagatedChange.originalChange
		val newChange = cloner.clone(originalChange)
		val Map<VURI, List<VitruviusChange>> t = #{
			propagatedChange.consequentialChanges.URI -> #[propagatedChange.consequentialChanges]
		}
		return new ChangeMatchImpl(newChange.URI, newChange, t)
	}

	def VURI getOriginalVURI()

	def VitruviusChange getOriginalChange()

	def Map<VURI, List<VitruviusChange>> getTargetToCorrespondentChanges()

	def List<EChange> getAllEChanges()
}
