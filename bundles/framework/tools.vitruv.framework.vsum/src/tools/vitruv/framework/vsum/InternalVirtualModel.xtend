package tools.vitruv.framework.vsum

import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.correspondence.CorrespondenceModel
import org.eclipse.emf.ecore.EObject
import java.util.concurrent.Callable
import tools.vitruv.framework.vsum.modelsynchronization.ChangePropagationListener
import tools.vitruv.framework.userinteraction.UserInteractor

interface InternalVirtualModel extends VirtualModel {
	def CorrespondenceModel getCorrespondenceModel()
	def void save()
	def void persistRootElement(VURI persistenceVuri, EObject rootElement)
	def void executeCommand(Callable<Void> command)
	def void executeCommand(Runnable command) {
		executeCommand [
			command.run()
			return null
		]
	}
	def void addChangePropagationListener(ChangePropagationListener propagationListener)
	def void removeChangePropagationListener(ChangePropagationListener propagationListener)
	def void setUserInteractor(UserInteractor userInteractor)
	def void addPropagatedChangeListener(PropagatedChangeListener propagatedChangeListener)
	def void removePropagatedChangeListener(PropagatedChangeListener propagatedChangeListener)
	def void dispose()
}