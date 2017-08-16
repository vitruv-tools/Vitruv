package tools.vitruv.framework.vsum

import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.correspondence.CorrespondenceModel
import org.eclipse.emf.ecore.EObject
import java.util.concurrent.Callable
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.vsum.modelsynchronization.ChangePropagationListener

interface InternalVirtualModel extends VirtualModel {
	def CorrespondenceModel getCorrespondenceModel();
	def void save();
	def void persistRootElement(VURI persistenceVuri, EObject rootElement);
	def void executeCommand(Callable<Void> command);
	def void addChangePropagationListener(ChangePropagationListener propagationListener);
	def void setUserInteractor(UserInteracting userInteractor);
}