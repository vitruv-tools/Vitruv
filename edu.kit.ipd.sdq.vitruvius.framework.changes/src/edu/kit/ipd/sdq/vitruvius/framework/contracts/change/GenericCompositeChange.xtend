package edu.kit.ipd.sdq.vitruvius.framework.contracts.change

import java.util.List
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change

interface GenericCompositeChange<C extends Change> extends Change {
	def List<C> getChanges();
	def void addChange(C change);
}