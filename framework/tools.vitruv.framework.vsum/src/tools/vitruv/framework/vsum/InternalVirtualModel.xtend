package tools.vitruv.framework.vsum

import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.metamodel.ModelInstance
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.tuid.TUID
import java.util.concurrent.Callable
import tools.vitruv.framework.modelsynchronization.SynchronisationListener
import tools.vitruv.framework.userinteraction.UserInteracting

interface InternalVirtualModel extends VirtualModel {
	def String getName();
	def CorrespondenceModel getCorrespondenceModel(VURI metamodel1, VURI metamodel2);
	def Iterable<ModelInstance> getAllModelInstances();
	def void saveModelInstance(VURI modelVuri);
	def void saveModelInstanceWithRoot(VURI vuri, EObject rootEObject, TUID oldRootTuid);
	def boolean existsModelInstance(VURI vuri);
	def void reloadModelInstance(VURI vuri);
	def void executeCommand(Callable<Void> command);
	def void addChangeSynchronizationListener(SynchronisationListener synchronizationListener);
	def void setUserInteractor(UserInteracting userInteractor);
}