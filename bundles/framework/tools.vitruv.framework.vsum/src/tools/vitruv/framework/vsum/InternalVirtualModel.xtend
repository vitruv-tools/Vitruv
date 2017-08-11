package tools.vitruv.framework.vsum

import java.io.File
import java.util.concurrent.Callable
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.vsum.modelsynchronization.ChangePropagationListener
import tools.vitruv.framework.vsum.impl.InternalTestVirtualModelImpl

interface InternalVirtualModel extends VirtualModel {
	static def InternalTestVirtualModel createInternalTestVirtualModel(
		File folder,
		UserInteracting userInteracting,
		VirtualModelConfiguration modelConfiguration
	) {
		new InternalTestVirtualModelImpl(folder, userInteracting, modelConfiguration)
	}

	def CorrespondenceModel getCorrespondenceModel()

	def void save()

	def void persistRootElement(VURI persistenceVuri, EObject rootElement)

	def void executeCommand(Callable<Void> command)

	def void addChangePropagationListener(ChangePropagationListener propagationListener)

	def void setUserInteractor(UserInteracting userInteractor)

	def UserInteracting getUserInteractor()
}
