package tools.vitruv.framework.versioning.impl

import tools.vitruv.framework.versioning.impl.ConflictImpl
import tools.vitruv.framework.versioning.MultiChangeConflict
import org.eclipse.xtend.lib.annotations.Data
import org.eclipse.emf.common.util.EList
import tools.vitruv.framework.versioning.ChangeMatch
import tools.vitruv.framework.change.echange.EChange
import java.util.List

@Data
class MultiChangeConflictImpl extends ConflictImpl implements MultiChangeConflict {
	EChange sourceRepresentative
	EChange targetRepresentative
	List<EChange> sourceChanges
	List<EChange> targetChanges

	override resolveConflict(EList<ChangeMatch> acceptedLocalChangeMatches,
		EList<ChangeMatch> rejectedRemoteOperations) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

}
