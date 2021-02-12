package tools.vitruv.framework.change.description

import java.util.List
import tools.vitruv.framework.change.description.VitruviusChange

interface CompositeChange<C extends VitruviusChange> extends VitruviusChange {
	def List<C> getChanges()
	override CompositeChange<C> copy()
}