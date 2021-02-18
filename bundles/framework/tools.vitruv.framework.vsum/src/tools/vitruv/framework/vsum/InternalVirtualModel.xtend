package tools.vitruv.framework.vsum

import tools.vitruv.framework.util.datatypes.VURI
import org.eclipse.emf.ecore.EObject
import java.util.concurrent.Callable
import tools.vitruv.framework.vsum.modelsynchronization.ChangePropagationListener
import tools.vitruv.framework.variability.vave.VaveModel
import tools.vitruv.framework.correspondence.CorrespondenceModel


interface InternalVirtualModel extends VirtualModel {
	def CorrespondenceModel getCorrespondenceModel()
	def VaveModel getVaveModel()
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
	def void addPropagatedChangeListener(PropagatedChangeListener propagatedChangeListener)
	def void removePropagatedChangeListener(PropagatedChangeListener propagatedChangeListener)
	def void dispose()
}