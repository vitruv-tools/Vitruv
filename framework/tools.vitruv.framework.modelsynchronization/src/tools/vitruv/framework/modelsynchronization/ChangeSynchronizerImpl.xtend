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
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.metamodel.Mapping

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
		if (change == null || !change.validate()) {
			throw new IllegalArgumentException('''Change is null or contains changes from different models: «change»''')
		}
		if (!change.containsConcreteChange()) {
			logger.info('''The change does not contain any changes to synchronize.«change»''')
			return Collections.emptyList()
		}
		startSynchronization(change);
		change.applyBackward()
		var List<List<VitruviusChange>> result = new ArrayList<List<VitruviusChange>>()
		synchronizeSingleChange(change, result)
		finishSynchronization(change)
		return result
	}

	private def void startSynchronization(VitruviusChange change) {
		logger.info('''Started synchronizing change: «change»''')
		for (SynchronisationListener syncListener : this.synchronizationListeners) {
			syncListener.syncStarted()
		}	
	}

	private def void finishSynchronization(VitruviusChange change) {
		for (SynchronisationListener syncListener : this.synchronizationListeners) {
			syncListener.syncFinished()
		}
		logger.info('''Finished synchronizing change: «change»''')
	}

	private def dispatch void synchronizeSingleChange(CompositeContainerChange change, List<List<VitruviusChange>> commandExecutionChanges) {
		for (VitruviusChange innerChange : ((change as CompositeContainerChange)).getChanges()) {
			synchronizeSingleChange(innerChange, commandExecutionChanges)
		}
	}

	private def dispatch void synchronizeSingleChange(TransactionalChange change, List<List<VitruviusChange>> commandExecutionChanges) {
		change.applyForward()
		for (CorrespondenceModel correspondenceModel : change.URI.correspondenceModels) {
			synchronizeChangeForCorrespondenceModel(change, correspondenceModel, commandExecutionChanges);
		}
	}
	
	private def void synchronizeChangeForCorrespondenceModel(TransactionalChange change, CorrespondenceModel correspondenceModel,
			List<List<VitruviusChange>> commandExecutionChanges) {
		val mapping = correspondenceModel.mapping;
		var Change2CommandTransforming change2CommandTransforming = this.change2CommandTransformingProviding.
			getChange2CommandTransforming(change.getSourceMetamodel(mapping).URI, change.getTargetMetamodel(mapping).URI)
		var Blackboard blackboard = new BlackboardImpl(correspondenceModel, this.modelProviding)
		// TODO HK: Clone the changes for each synchronization! Should even be cloned for
		// each response that uses it,
		// or: make them read only, i.e. give them a read-only interface!
		this.blackboardHistory.add(blackboard)
		blackboard.pushCommands(change2CommandTransforming.transformChange2Commands(change, correspondenceModel))
		commandExecutionChanges.add(this.commandExecuting.executeCommands(blackboard))		
	}
	
	private def Iterable<CorrespondenceModel> getCorrespondenceModels(VURI vuri) {
		return this.correspondenceProviding.getOrCreateAllCorrespondenceModels(vuri)
	}
		
	private def Metamodel getSourceMetamodel(VitruviusChange change, Mapping mapping) {
		if (mapping.metamodelA.isChangeSourceMetamodel(change)) {
			return mapping.metamodelA
		} else if (mapping.metamodelB.isChangeSourceMetamodel(change)) {
			return mapping.metamodelB
		} else {
			throw new IllegalStateException('''None if the metamodels of mapping «mapping» is the metamodel of the model changed in: «change»''')
		}
	}
	
	private def Metamodel getTargetMetamodel(VitruviusChange change, Mapping mapping) {
		if (!mapping.metamodelA.isChangeSourceMetamodel(change)) {
			return mapping.metamodelA
		} else {
			// Even if metamodelB is the metamodel of the changed model (i.e. metamodelA and metamodelB are the same)
			// we return it, as we have transformations within one metamodel then.
			return mapping.metamodelB
		}
	}
	
	private def boolean isChangeSourceMetamodel(Metamodel metamodel, VitruviusChange change) {
		return Arrays.asList(metamodel.getFileExtensions()).contains(change.getURI().getFileExtension());
	}
}
