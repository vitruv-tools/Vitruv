package tools.vitruv.framework.change.copy

import java.util.List
import java.util.Set

import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.copy.impl.ChangeCopierImpl

/**
 * @author Patrick Stoeckle <p.stoeckle@gmx.net>
 * @since 2017-06-14
 * @version 0.2.0 
 */
interface ChangeCopier {
	/**
	 * 
	 * @param changeToCopy
	 * @param vuri
	 * @return
	 */
	def List<VitruviusChange> copyEMFModelChangeToList(VitruviusChange changeToCopy)

	def VitruviusChange copyEMFModelChangeToSingleChange(VitruviusChange changeToCopy)

	def VitruviusChange copyEChanges(EChange changeToCopy)

	static def ChangeCopier createEChangeCopier(Set<Pair<String, String>> replacePairs) {
		return new ChangeCopierImpl(replacePairs)
	}
}
