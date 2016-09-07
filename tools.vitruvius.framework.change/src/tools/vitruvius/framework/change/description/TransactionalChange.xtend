package tools.vitruvius.framework.change.description

import tools.vitruvius.framework.change.description.VitruviusChange

interface TransactionalChange extends ConcreteChange, GenericCompositeChange<VitruviusChange> {
	
}