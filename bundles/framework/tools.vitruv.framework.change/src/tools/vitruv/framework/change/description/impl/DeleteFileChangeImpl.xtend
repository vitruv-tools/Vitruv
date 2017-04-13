package tools.vitruv.framework.change.description.impl

import tools.vitruv.framework.change.description.DeleteFileChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.util.datatypes.VURI

class DeleteFileChangeImpl extends ConcreteChangeImpl implements DeleteFileChange {
	new(EChange eChange, VURI vuri) {
		super(eChange, vuri)
	}
}