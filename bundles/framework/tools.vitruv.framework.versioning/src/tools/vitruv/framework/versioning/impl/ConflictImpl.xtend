package tools.vitruv.framework.versioning.impl

import org.eclipse.xtend.lib.annotations.Data
import tools.vitruv.framework.versioning.Conflict
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.Graph

@Data
class ConflictImpl implements Conflict {
	int originalChangesLevenshteinDistance

	Graph<EChange> eChangeDependencyGraph

}
