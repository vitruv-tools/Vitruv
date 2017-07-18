package tools.vitruv.framework.versioning.impl

import com.google.common.collect.ListMultimap
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.Data
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.versioning.ChangeMatch

@Data
class ChangeMatchImpl implements ChangeMatch {
	@Accessors(PUBLIC_GETTER)
	static val serialVersionUID = 1L
	VURI originalVURI
	VitruviusChange originalChange
	ListMultimap<VURI, VitruviusChange> targetToCorrespondentChanges

	override getAllEChanges() {
		val orig = originalChange.EChanges
		val correspondent = targetToCorrespondentChanges.asMap.entrySet.map[value].flatten.map[EChanges].flatten.toList
		return (orig + correspondent).toList
	}

}
