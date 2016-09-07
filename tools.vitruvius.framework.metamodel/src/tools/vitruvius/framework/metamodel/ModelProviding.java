package tools.vitruvius.framework.metamodel;

import java.util.concurrent.Callable;

import org.eclipse.emf.ecore.EObject;

import tools.vitruvius.framework.tuid.TUID;
import tools.vitruvius.framework.util.command.VitruviusRecordingCommand;
import tools.vitruvius.framework.util.datatypes.VURI;

public interface ModelProviding {
    ModelInstance getAndLoadModelInstanceOriginal(VURI uri);

    void saveExistingModelInstanceOriginal(VURI vuri);

    /**
     * Convenience method
     */
    void createRecordingCommandAndExecuteCommandOnTransactionalDomain(Callable<Void> callable);
    
    void executeRecordingCommandOnTransactionalDomain(VitruviusRecordingCommand command);
    
    void detachTransactionalEditingDomain();

    void deleteModelInstanceOriginal(VURI vuri);

    void saveModelInstanceOriginalWithEObjectAsOnlyContent(VURI vuri, EObject rootEObject, TUID oldTUID);

    void forceReloadModelInstanceOriginalIfExisting(VURI modelURI);

}
