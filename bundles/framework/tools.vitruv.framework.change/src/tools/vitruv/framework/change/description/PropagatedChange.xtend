package tools.vitruv.framework.change.description

import org.eclipse.xtend.lib.annotations.Data

@Data
class PropagatedChange {
	val VitruviusChange originalChange
	val VitruviusChange consequentialChanges
	
	override toString() '''
	Original change:
		«originalChange»
	Consequential change:
		«consequentialChanges»
	'''
	
}