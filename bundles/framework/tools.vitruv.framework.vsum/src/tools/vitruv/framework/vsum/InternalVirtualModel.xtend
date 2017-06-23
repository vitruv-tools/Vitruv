package tools.vitruv.framework.vsum

import java.util.concurrent.Callable
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.modelsynchronization.ChangePropagationListener
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.util.datatypes.VURI

interface InternalVirtualModel extends VirtualModel {
	def CorrespondenceModel getCorrespondenceModel()

	def void save()

	def void persistRootElement(VURI persistenceVuri, EObject rootElement)

	def void executeCommand(Callable<Void> command)

	def void addChangePropagationListener(ChangePropagationListener propagationListener)

	def void setUserInteractor(UserInteracting userInteractor)
}
