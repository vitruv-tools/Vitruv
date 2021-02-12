package tools.vitruv.framework.change.description.impl

import tools.vitruv.framework.change.description.CompositeContainerChange
import tools.vitruv.framework.change.description.VitruviusChange
import java.util.Collection
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*

class CompositeContainerChangeImpl extends AbstractCompositeChangeImpl<VitruviusChange> implements CompositeContainerChange {
	new(Collection<? extends VitruviusChange> changes) {
		super(changes)
	}
	
	override toString() '''
		Composite container change:
			«FOR change : changes»
				«change»
			«ENDFOR»
		'''
			
	override CompositeContainerChangeImpl copy() {
		new CompositeContainerChangeImpl(changes.mapFixed [copy()])
	}
}