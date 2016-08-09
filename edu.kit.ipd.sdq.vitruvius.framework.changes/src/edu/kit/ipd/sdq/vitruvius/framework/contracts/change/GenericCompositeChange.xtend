package edu.kit.ipd.sdq.vitruvius.framework.contracts.change

import java.util.List
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VitruviusChange

interface GenericCompositeChange<C extends VitruviusChange> extends VitruviusChange {
	def List<C> getChanges();
	def void addChange(C change);
}