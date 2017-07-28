package tools.vitruv.framework.change.copy

import java.util.List
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.echange.EChange

/**
 * @author Patrick Stoeckle <p.stoeckle@gmx.net>
 * @since 2017-06-14
 * @version 0.2.0 
 */
interface EChangeCopier {
	/**
	 * 
	 * @param changeToCopy
	 * @param vuri
	 * @return
	 */
	def List<VitruviusChange> copyEMFModelChangeToList(VitruviusChange changeToCopy)

	def VitruviusChange copyEMFModelChangeToSingleChange(VitruviusChange changeToCopy)

	def VitruviusChange copyEChanges(EChange changeToCopy)

	def EChange copy(EChange e)

}
