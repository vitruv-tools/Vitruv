package tools.vitruv.framework.versioning.impl

import org.eclipse.xtend.lib.annotations.Data
import tools.vitruv.framework.versioning.Conflict

@Data
class ConflictImpl implements Conflict {
	int originalChangesLevenshteinDistance

}
