package tools.vitruv.framework.tests

import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.util.datatypes.VURI

interface ChangeObservable {
	def void registerObserver(ChangeObserver observer)

	def void unRegisterObserver(ChangeObserver observer)

	def void notifyObservers(VURI vuri, TransactionalChange change)
}
