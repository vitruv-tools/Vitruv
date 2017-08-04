package tools.vitruv.framework.vsum

import tools.vitruv.framework.util.datatypes.ModelInstance
import tools.vitruv.framework.util.datatypes.VURI
import java.util.function.Consumer
import org.eclipse.emf.ecore.EObject
import java.util.concurrent.Callable
import tools.vitruv.framework.util.command.VitruviusRecordingCommand
import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.util.command.VitruviusRecordingCommandExecutor
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.description.VitruviusChange
import java.util.List

interface ModelRepository extends VitruviusRecordingCommandExecutor {
	def void persistRootElement(VURI persistenceVuri, EObject rootElement)

	def ModelInstance getModel(VURI modelVuri)

	def void forceReloadModelIfExisting(VURI modelVuri)

	def void saveAllModels()

	def void createRecordingCommandAndExecuteCommandOnTransactionalDomain(Callable<Void> callable)

	def void executeRecordingCommandOnTransactionalDomain(VitruviusRecordingCommand command)

	/**
	 * Executes the function on the {@link ResourceSet} of the model repository.
	 * @param function The {@link Consumer} to be executed
	 */
	def void executeOnResourceSet(Consumer<ResourceSet> function)

	def void startRecording()

	def Iterable<TransactionalChange> endRecording()

	def List<? extends VitruviusChange> getLastResolvedChanges()

	def List<? extends VitruviusChange> getLastUnresolvedChanges()

	def boolean unresolveChanges()

	def void setCurrentVURI(VURI original)
}
