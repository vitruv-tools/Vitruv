package edu.kit.ipd.sdq.vitruvius.framework.contracts.change.recorded.impl

import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.recorded.RecordedConcreteChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.AbstractURIHaving
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI

abstract class RecordedConcreteChangeImpl extends AbstractURIHaving implements RecordedConcreteChange {
	
	new(VURI uri) {
		super(uri)
	}
	
	override containsConcreteChange() {
		return true;
	}
	
	override validate() {
		return containsConcreteChange() && URI != null;
	}
	
}