package tools.vitruv.framework.vsum

import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.correspondence.CorrespondenceModel
import org.eclipse.emf.ecore.EObject
import java.util.concurrent.Callable
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.modelsynchronization.ChangePropagationListener

interface InternalVirtualModel extends VirtualModel {
	def CorrespondenceModel getCorrespondenceModel(VURI metamodel1, VURI metamodel2);
	def void save();
	def void createModel(VURI vuri, EObject rootEObject);
	def void executeCommand(Callable<Void> command);
	def void addChangePropagationListener(ChangePropagationListener synchronizationListener);
	def void setUserInteractor(UserInteracting userInteractor);
}