package tools.vitruv.framework.tests

import java.util.List
import tools.vitruv.framework.change.description.TransactionalChange

interface ChangeObserver {
	def void update(List<TransactionalChange> changes)
}
