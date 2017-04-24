package tools.vitruv.framework.domains.repository;

import java.util.concurrent.Callable;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.change.description.TransactionalChange;
import tools.vitruv.framework.util.command.VitruviusRecordingCommand;
import tools.vitruv.framework.util.datatypes.ModelInstance;
import tools.vitruv.framework.util.datatypes.VURI;

public interface ModelRepository {
	void persistRootElement(VURI persistenceVuri, EObject rootElement);
	ModelInstance getModel(VURI modelVuri);
	void forceReloadModelIfExisting(VURI modelVuri);
    void saveAllModels();
    
    void createRecordingCommandAndExecuteCommandOnTransactionalDomain(Callable<Void> callable);
    void executeRecordingCommandOnTransactionalDomain(VitruviusRecordingCommand command);
    
    /**
     * Applies a unresolved change forward to the affected model in the repository.
     * The EChanges in the change will be resolved in the process.
     * @param change The change which shall be applied to the model.
     */
    void applyChangeForwardOnModel(TransactionalChange change);
}
