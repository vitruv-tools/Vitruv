package tools.vitruv.framework.change.description

import tools.vitruv.framework.change.echange.EChange

interface ConcreteChange extends TransactionalChange {
	def EChange getEChange();
}