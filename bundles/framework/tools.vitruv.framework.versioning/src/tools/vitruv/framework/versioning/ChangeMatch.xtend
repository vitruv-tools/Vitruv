package tools.vitruv.framework.versioning

import org.eclipse.xtend.lib.annotations.Data
import tools.vitruv.framework.change.description.TransactionalChange
import java.util.List
import tools.vitruv.framework.util.datatypes.VURI
import java.util.Map

@Data
class ChangeMatch {
	VURI originalVURI
	TransactionalChange originalChange
	Map<VURI, List<TransactionalChange>> targetToCorrespondentChanges
}
