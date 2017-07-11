package tools.vitruv.framework.versioning.impl

import java.util.List
import java.util.Map
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.ChangeMatch
import org.eclipse.xtend.lib.annotations.Data
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.change.description.VitruviusChange

@Data
class ChangeMatchImpl implements ChangeMatch {
	@Accessors(PUBLIC_GETTER)
	static val serialVersionUID = 1L
	VURI originalVURI
	TransactionalChange originalChange
	Map<VURI, List<VitruviusChange>> targetToCorrespondentChanges

	override getAllEChanges() {
		val orig = originalChange.EChanges
		val correspondent = targetToCorrespondentChanges.entrySet.map[value].flatten.map[EChanges].flatten.toList
		return (orig + correspondent).toList
	}

}
