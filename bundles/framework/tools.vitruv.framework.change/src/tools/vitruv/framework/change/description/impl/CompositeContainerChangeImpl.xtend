package tools.vitruv.framework.change.description.impl

import tools.vitruv.framework.change.description.CompositeContainerChange
import tools.vitruv.framework.change.description.VitruviusChange

class CompositeContainerChangeImpl extends AbstractCompositeChangeImpl<VitruviusChange> implements CompositeContainerChange {
	
	override toString() '''
		Container change:
		«FOR change : changes»  «change»«ENDFOR»'''
	
}