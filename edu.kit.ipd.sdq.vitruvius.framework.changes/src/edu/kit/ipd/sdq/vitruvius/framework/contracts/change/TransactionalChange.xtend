package edu.kit.ipd.sdq.vitruvius.framework.contracts.change

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VitruviusChange

interface TransactionalChange extends ConcreteChange, GenericCompositeChange<VitruviusChange> {
	
}