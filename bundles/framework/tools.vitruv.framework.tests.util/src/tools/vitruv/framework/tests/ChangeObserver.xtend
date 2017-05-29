package tools.vitruv.framework.tests

import java.util.List
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.util.datatypes.VURI

interface ChangeObserver {
	def void update(VURI vuri, List<TransactionalChange> changes)
}
