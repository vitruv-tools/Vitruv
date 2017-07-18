package tools.vitruv.framework.versioning.impl

import tools.vitruv.framework.versioning.Reapplier
import java.util.List
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.vsum.VirtualModel

class ReapplierImpl implements Reapplier {

	override reapply(List<PropagatedChange> changesToRollBack, List<EChange> echangesToReapply,
		VirtualModel virtualModel) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

}
