package tools.vitruv.framework.metamodel;

import java.util.concurrent.Callable;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.util.command.VitruviusRecordingCommand;
import tools.vitruv.framework.util.datatypes.VURI;

public interface ModelProviding {
    ModelInstance getAndLoadModelInstanceOriginal(VURI uri);

    void saveAllModels();

    /**
     * Convenience method
     */
    void createRecordingCommandAndExecuteCommandOnTransactionalDomain(Callable<Void> callable);
    
    void executeRecordingCommandOnTransactionalDomain(VitruviusRecordingCommand command);
    
    void deleteModelInstanceOriginal(VURI vuri);

    void createModelInstance(VURI vuri, EObject rootEObject);

    void forceReloadModelInstanceOriginalIfExisting(VURI modelURI);

}
