package edu.kit.ipd.sdq.vitruvius.framework.contracts.change.processable.impl

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.AbstractURIHaving
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.processable.ProcessableConcreteChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI

abstract class ProcessableConcreteChangeImpl extends AbstractURIHaving implements ProcessableConcreteChange {
	
	new(VURI uri) {
		super(uri)
	}
	
	override containsConcreteChange() {
		return true;
	}
	
	override validate() {
		return containsConcreteChange && URI != null;
	}
	
}