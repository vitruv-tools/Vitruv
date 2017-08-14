package tools.vitruv.framework.change.description

import tools.vitruv.framework.change.description.PropagatedChange

interface PropagatedChangeWithCorrespondent extends PropagatedChange {
	def PropagatedChangeWithCorrespondent getCorrespondent()

	def void setCorrespondent(PropagatedChangeWithCorrespondent propagatedChangeWithCorrespondent)
}
