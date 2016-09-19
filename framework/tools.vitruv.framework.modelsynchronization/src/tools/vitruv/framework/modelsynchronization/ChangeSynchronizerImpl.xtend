package tools.vitruv.framework.modelsynchronization

import java.util.ArrayList
import java.util.Arrays
import java.util.Collections
import java.util.HashSet
import java.util.List
import java.util.Queue
import java.util.Set
import org.apache.log4j.Logger
import com.google.common.collect.EvictingQueue
import tools.vitruv.framework.change.description.CompositeContainerChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.change.processing.Change2CommandTransforming
import tools.vitruv.framework.change.processing.Change2CommandTransformingProviding
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.correspondence.CorrespondenceProviding
import tools.vitruv.framework.metamodel.Metamodel
import tools.vitruv.framework.metamodel.ModelProviding
import tools.vitruv.framework.modelsynchronization.blackboard.Blackboard
import tools.vitruv.framework.modelsynchronization.blackboard.impl.BlackboardImpl
import tools.vitruv.framework.modelsynchronization.commandexecution.CommandExecuting
import tools.vitruv.framework.modelsynchronization.commandexecution.CommandExecutingImpl
import tools.vitruv.framework.util.datatypes.VURI

class ChangeSynchronizerImpl implements ChangeSynchronizing {
	static final int BLACKBOARD_HITORY_SIZE = 2
	static Logger logger = Logger.getLogger(ChangeSynchronizerImpl.getSimpleName())
	final ModelProviding modelProviding
	final Change2CommandTransformingProviding change2CommandTransformingProviding
	final CorrespondenceProviding correspondenceProviding
	final CommandExecuting commandExecuting
	Set<SynchronisationListener> synchronizationListeners
	Queue<Blackboard> blackboardHistory

	new(ModelProviding modelProviding, Change2CommandTransformingProviding change2CommandTransformingProviding,
		CorrespondenceProviding correspondenceProviding) {
		this.modelProviding = modelProviding
		this.change2CommandTransformingProviding = change2CommandTransformingProviding
		this.correspondenceProviding = correspondenceProviding
		this.synchronizationListeners = new HashSet<SynchronisationListener>()
		this.commandExecuting = new CommandExecutingImpl()
		this.blackboardHistory = EvictingQueue.create(BLACKBOARD_HITORY_SIZE)
	}

	override void addSynchronizationListener(SynchronisationListener synchronizationListener) {
		if (synchronizationListener !== null) {
			this.synchronizationListeners.add(synchronizationListener)
		}
	}

	override void removeSynchronizationListener(SynchronisationListener synchronizationListener) {
		this.synchronizationListeners.remove(synchronizationListener)
	}

	override synchronized List<List<VitruviusChange>> synchronizeChange(VitruviusChange change) {
		if (change === null || !change.containsConcreteChange()) {
			logger.warn('''The change does not contain any changes to synchronize.«change»''')
			return Collections.emptyList()
		}
		if (!change.validate()) {
			logger.error('''Change contains changes from different models.«change»''')
			return Collections.emptyList()
		}
		for (SynchronisationListener syncListener : this.synchronizationListeners) {
			syncListener.syncStarted()
		}
		logger.debug('''Synchronizing change: «change»''')
		var VURI sourceModelVURI = change.getURI()
		// FIXME HK: This is all strange: the sourceModelVURI is taken from the first change,
		// although they can be from different ones. Why not make it for each change independently?
		var Set<CorrespondenceModel> correspondenceModels = this.correspondenceProviding.
			getOrCreateAllCorrespondenceModels(sourceModelVURI)
		change.applyBackward()
		var List<List<VitruviusChange>> commandExecutionChanges = new ArrayList<List<VitruviusChange>>()
		synchronizeSingleChange(change, correspondenceModels, commandExecutionChanges)
		// TODO: check invariants and execute undo if necessary
		for (SynchronisationListener syncListener : this.synchronizationListeners) {
			syncListener.syncFinished()
		}
		logger.debug('''Finished synchronizing change: «change»''')
		return commandExecutionChanges
	}

	def private void synchronizeSingleChange(VitruviusChange change, Set<CorrespondenceModel> correspondenceModels,
		List<List<VitruviusChange>> commandExecutionChanges) {
		if (change instanceof CompositeContainerChange) {
			for (VitruviusChange innerChange : ((change as CompositeContainerChange)).getChanges()) {
				synchronizeSingleChange(innerChange, correspondenceModels, commandExecutionChanges)
			}
		} else {
			change.applyForward()
			for (CorrespondenceModel correspondenceModel : correspondenceModels) {
				var Metamodel mmA = correspondenceModel.getMapping().getMetamodelA()
				var Metamodel mmB = correspondenceModel.getMapping().getMetamodelB()
				// assume mmaA is source metamodel
				var VURI sourceMMURI = mmA.getURI()
				var VURI targetMMURI = mmB.getURI()
				if (!Arrays.asList(mmA.getFileExtensions()).contains(change.getURI().getFileExtension())) {
					var VURI tmpURI = sourceMMURI
					sourceMMURI = targetMMURI
					targetMMURI = tmpURI
				}
				var Change2CommandTransforming change2CommandTransforming = this.change2CommandTransformingProviding.
					getChange2CommandTransforming(sourceMMURI, targetMMURI)
				var Blackboard blackboard = new BlackboardImpl(correspondenceModel, this.modelProviding,
					this.correspondenceProviding)
				// TODO HK: Clone the changes for each synchronization! Should even be cloned for
				// each response that uses it,
				// or: make them read only, i.e. give them a read-only interface!
				blackboard.pushChanges(Collections.singletonList(change))
				this.blackboardHistory.add(blackboard)
				blackboard.pushCommands(
					change2CommandTransforming.transformChange2Commands(
						blackboard.getAndArchiveChangesForTransformation().get(0), correspondenceModel))
				commandExecutionChanges.add(this.commandExecuting.executeCommands(blackboard))
			}
		}
	}
}
