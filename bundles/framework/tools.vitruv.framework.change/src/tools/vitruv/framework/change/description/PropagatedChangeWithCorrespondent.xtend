package tools.vitruv.framework.change.description

import tools.vitruv.framework.change.description.PropagatedChange

interface PropagatedChangeWithCorrespondent extends PropagatedChange {
	// TODO BEGIN The unresolved PropagatedChanges are used for the versioning, but to roll back the 
	// changes, it is necessary to get for an unresolved change the correspondent resolved change. 
	// Therefore the ID of an unresolved and a correspondent resolved change are the same and both 
	// changes are saved in a map.
	// This is no longer necessary if unresolved changes can be rolled back.
	def PropagatedChangeWithCorrespondent getCorrespondent()

	def void setCorrespondent(PropagatedChangeWithCorrespondent propagatedChangeWithCorrespondent)
}
