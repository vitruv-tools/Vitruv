package tools.vitruv.framework.change.description

import tools.vitruv.framework.change.description.VitruviusChange

interface TransactionalChange extends ConcreteChange, GenericCompositeChange<VitruviusChange> {
	
}