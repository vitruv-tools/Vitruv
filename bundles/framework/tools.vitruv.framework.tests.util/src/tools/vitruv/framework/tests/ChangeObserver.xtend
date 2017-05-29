package tools.vitruv.framework.tests

import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.util.datatypes.VURI

interface ChangeObserver {
	def void update(VURI vuri, TransactionalChange change)
}
