package tools.vitruv.framework.versioning

import java.io.Serializable
import java.util.List
import tools.vitruv.framework.change.description.ChangeCloner
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.impl.ChangeMatchImpl
import com.google.common.collect.ListMultimap
import com.google.common.collect.ArrayListMultimap

interface ChangeMatch extends Serializable {

	static def createChangeMatch(VURI originalVURI, TransactionalChange originalChange,
		ListMultimap<VURI, VitruviusChange> targetToCorrespondentChanges) {
		new ChangeMatchImpl(originalVURI, originalChange, targetToCorrespondentChanges)
	}

	static def ChangeMatch createChangeMatch(PropagatedChange propagatedChange) {
		val cloner = new ChangeCloner
		val originalChange = propagatedChange.originalChange
		val newChange = cloner.clone(originalChange)
		val ListMultimap<VURI, VitruviusChange> t = ArrayListMultimap::create
		if (propagatedChange.consequentialChanges.URI !== null)
			t.put(propagatedChange.consequentialChanges.URI, propagatedChange.consequentialChanges)
		return new ChangeMatchImpl(newChange.URI, newChange, t)
	}

	def VURI getOriginalVURI()

	def VitruviusChange getOriginalChange()

	def ListMultimap<VURI, VitruviusChange> getTargetToCorrespondentChanges()

	def List<EChange> getAllEChanges()
}
