package tools.vitruv.framework.vsum

import tools.vitruv.framework.util.datatypes.ModelInstance
import tools.vitruv.framework.util.datatypes.VURI
import java.util.function.Consumer
import java.util.concurrent.Callable
import tools.vitruv.framework.util.command.VitruviusRecordingCommand
import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.util.command.VitruviusRecordingCommandExecutor
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.util.command.ResourceAccess

interface ModelRepository extends VitruviusRecordingCommandExecutor, ResourceAccess {
	def ModelInstance getModel(VURI modelVuri);
	def void forceReloadModelIfExisting(VURI modelVuri);
    def void saveAllModels();
    
    def void createRecordingCommandAndExecuteCommandOnTransactionalDomain(Callable<Void> callable);
    def void executeRecordingCommandOnTransactionalDomain(VitruviusRecordingCommand command);
    
    /**
     * Executes the function on the {@link ResourceSet} of the model repository.
     * @param function The {@link Consumer} to be executed
     */
    def void executeOnResourceSet(Consumer<ResourceSet> function);
    
    def void startRecording();
    def Iterable<TransactionalChange> endRecording();
}
