package tools.vitruv.framework.versioning

import tools.vitruv.framework.change.description.PropagatedChange
import java.util.List
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.vsum.VirtualModel

interface Reapplier {
	def List<PropagatedChange> reapply(
		List<PropagatedChange> changesToRollBack,
		List<EChange> echangesToReapply,
		VirtualModel virtualModel
	)
}
