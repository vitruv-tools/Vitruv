package tools.vitruv.change.composite.description

import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend.lib.annotations.Data

@Data
class PropagatedChange {
	val VitruviusChange<EObject> originalChange
	val VitruviusChange<EObject> consequentialChanges
	
	override toString() '''
	Original change:
		«originalChange»
	Consequential change:
		«consequentialChanges»
	'''
	
}