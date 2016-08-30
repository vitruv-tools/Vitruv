package edu.kit.ipd.sdq.vitruvius.framework.change.description

import edu.kit.ipd.sdq.vitruvius.framework.change.description.VitruviusChange

interface TransactionalChange extends ConcreteChange, GenericCompositeChange<VitruviusChange> {
	
}