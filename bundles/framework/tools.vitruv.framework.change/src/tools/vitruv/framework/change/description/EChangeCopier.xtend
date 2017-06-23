package tools.vitruv.framework.change.description

import java.util.List
import tools.vitruv.framework.change.description.impl.EMFModelChangeImpl
import tools.vitruv.framework.util.datatypes.VURI

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
	def List<TransactionalChange> copyEMFModelChange(EMFModelChangeImpl changeToCopy, VURI vuri)
}
