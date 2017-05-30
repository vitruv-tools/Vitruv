package tools.vitruv.framework.versioning

import java.util.List
import java.util.Map

import org.eclipse.xtend.lib.annotations.Data

import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.util.datatypes.VURI

@Data
class ChangeMatch {
	VURI originalVURI
	TransactionalChange originalChange
	Map<VURI, List<TransactionalChange>> targetToCorrespondentChanges
}
