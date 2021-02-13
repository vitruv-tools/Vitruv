package tools.vitruv.framework.vsum

import tools.vitruv.framework.util.datatypes.ModelInstance
import tools.vitruv.framework.util.datatypes.VURI
import java.util.function.Consumer
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.uuid.UuidResolver
import tools.vitruv.framework.util.command.ResourceAccess

interface ModelRepository extends ResourceAccess {
	def ModelInstance getModel(VURI modelVuri);
	def void forceReloadModelIfExisting(VURI modelVuri);
    def void saveAllModels();
    
    /**
     * Executes the function on the {@link UuidResolver} of the model repository.
     * @param function The {@link Consumer} to be executed
     */
    def void executeOnUuidResolver(Consumer<UuidResolver> function);
    
    def void startRecording();
    def Iterable<? extends TransactionalChange> endRecording();
}
