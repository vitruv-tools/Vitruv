package tools.vitruv.framework.versioning

import java.util.List
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.impl.ReapplierImpl
import tools.vitruv.framework.vsum.VirtualModel

interface Reapplier {
	static def Reapplier createReapplier() {
		new ReapplierImpl
	}

	def List<PropagatedChange> reapply(
		List<PropagatedChange> changesToRollBack,
		List<EChange> echangesToReapply,
		VirtualModel virtualModel
	)
}
