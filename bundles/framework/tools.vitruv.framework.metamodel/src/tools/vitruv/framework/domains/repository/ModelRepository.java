package tools.vitruv.framework.domains.repository;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;

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
     * Executes the function on the {@link ResourceSet} of the model repository.
     * @param function The {@link Consumer} to be executed
     */
    void executeOnResourceSet(final Consumer<ResourceSet> function);
}
