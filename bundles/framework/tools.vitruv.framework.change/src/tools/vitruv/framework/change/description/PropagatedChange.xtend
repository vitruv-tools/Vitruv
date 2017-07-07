package tools.vitruv.framework.change.description

import org.eclipse.xtend.lib.annotations.Data

@Data
class PropagatedChange {
	private val VitruviusChange originalChange;
	private val VitruviusChange consequentialChanges;
}