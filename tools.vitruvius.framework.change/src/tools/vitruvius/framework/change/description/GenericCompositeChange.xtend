package tools.vitruvius.framework.change.description

import java.util.List
import tools.vitruvius.framework.change.description.VitruviusChange

interface GenericCompositeChange<C extends VitruviusChange> extends VitruviusChange {
	def List<C> getChanges();
	def void addChange(C change);
}