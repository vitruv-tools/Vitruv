package tools.vitruv.framework.change.description.impl

import tools.vitruv.framework.change.description.CreateFileChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.util.datatypes.VURI

class CreateFileChangeImpl extends ConcreteChangeImpl implements CreateFileChange {
	public new(EChange eChange, VURI vuri) {
		super(eChange, vuri)
	}
}