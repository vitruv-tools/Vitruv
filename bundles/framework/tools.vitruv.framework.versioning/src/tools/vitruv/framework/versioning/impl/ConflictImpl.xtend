package tools.vitruv.framework.versioning.impl

import tools.vitruv.framework.versioning.Conflict
import org.eclipse.xtend.lib.annotations.Data
import tools.vitruv.framework.versioning.ConflictType
import tools.vitruv.framework.versioning.ConflictSolvability

@Data
abstract class ConflictImpl implements Conflict {
	ConflictType type
	ConflictSolvability solvability
}
