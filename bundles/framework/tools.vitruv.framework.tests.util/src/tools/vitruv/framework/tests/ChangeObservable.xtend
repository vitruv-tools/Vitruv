package tools.vitruv.framework.tests

import tools.vitruv.framework.change.description.TransactionalChange
import java.util.List

interface ChangeObservable {
	def void registerObserver(ChangeObserver observer)

	def void unRegisterObserver(ChangeObserver observer)

	def void notifyObservers(List<TransactionalChange> changes)
}
