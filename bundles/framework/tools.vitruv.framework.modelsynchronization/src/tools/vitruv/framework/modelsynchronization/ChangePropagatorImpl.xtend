package tools.vitruv.framework.modelsynchronization

import java.util.ArrayList
import java.util.Collections
import java.util.HashSet
import java.util.List
import java.util.Queue
import java.util.Set
import org.apache.log4j.Logger
import com.google.common.collect.EvictingQueue
import tools.vitruv.framework.change.description.CompositeContainerChange
import tools.vitruv.framework.change.description.VitruviusChange
import tools.vitruv.framework.correspondence.CorrespondenceProviding
import tools.vitruv.framework.modelsynchronization.blackboard.impl.BlackboardImpl
import tools.vitruv.framework.modelsynchronization.commandexecution.CommandExecuting
import tools.vitruv.framework.modelsynchronization.commandexecution.CommandExecutingImpl
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.metamodel.MetamodelRepository
import tools.vitruv.framework.metamodel.ModelRepository
import tools.vitruv.framework.modelsynchronization.blackboard.Blackboard
import tools.vitruv.framework.util.command.EMFCommandBridge
import tools.vitruv.framework.change.processing.ChangePropagationSpecificationProvider
import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import tools.vitruv.framework.change.echange.compound.CompoundEChange
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange
import tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange
import org.eclipse.emf.ecore.resource.Resource

class ChangePropagatorImpl implements ChangePropagator {
	static final int BLACKBOARD_HITORY_SIZE = 2
	static Logger logger = Logger.getLogger(ChangePropagatorImpl.getSimpleName())
	final MetamodelRepository metamodelRepository;
	final ModelRepository modelProviding
	final ChangePropagationSpecificationProvider changePropagationProvider
	final CorrespondenceProviding correspondenceProviding
	final CommandExecuting commandExecuting
	Set<ChangePropagationListener> changePropagationListeners
	Queue<Blackboard> blackboardHistory

	new(ModelRepository modelProviding, ChangePropagationSpecificationProvider changePropagationProvider,
		MetamodelRepository metamodelRepository, CorrespondenceProviding correspondenceProviding) {
		this.modelProviding = modelProviding
		this.changePropagationProvider = changePropagationProvider
		this.correspondenceProviding = correspondenceProviding
		this.changePropagationListeners = new HashSet<ChangePropagationListener>()
		this.commandExecuting = new CommandExecutingImpl()
		this.metamodelRepository = metamodelRepository;
		this.blackboardHistory = EvictingQueue.create(BLACKBOARD_HITORY_SIZE)
	}

	override void addChangePropagationListener(ChangePropagationListener propagationListener) {
		if (propagationListener !== null) {
			this.changePropagationListeners.add(propagationListener)
		}
	}

	override void removeChangePropagationListener(ChangePropagationListener propagationListener) {
		this.changePropagationListeners.remove(propagationListener)
	}

	override synchronized List<List<VitruviusChange>> propagateChange(VitruviusChange change) {
		if (change == null || !change.containsConcreteChange()) {
			logger.info('''The change does not contain any changes to synchronize: «change»''')
			return Collections.emptyList()
		}
		if (!change.validate()) {
			throw new IllegalArgumentException('''Change contains changes from different models: «change»''')
		}
		
		startChangePropagation(change);
		change.applyBackward()
		var List<List<VitruviusChange>> result = new ArrayList<List<VitruviusChange>>()
		propagateSingleChange(change, result)
		finishChangePropagation(change)
		return result
	}

	private def void startChangePropagation(VitruviusChange change) {
		logger.info('''Started synchronizing change: «change»''')
		for (ChangePropagationListener syncListener : this.changePropagationListeners) {
			syncListener.startedChangePropagation()
		}	
	}

	private def void finishChangePropagation(VitruviusChange change) {
		for (ChangePropagationListener syncListener : this.changePropagationListeners) {
			syncListener.finishedChangePropagation()
		}
		logger.info('''Finished synchronizing change: «change»''')
	}

	private def dispatch void propagateSingleChange(CompositeContainerChange change, List<List<VitruviusChange>> commandExecutionChanges) {
		for (VitruviusChange innerChange : ((change as CompositeContainerChange)).getChanges()) {
			propagateSingleChange(innerChange, commandExecutionChanges)
		}
	}

	private def dispatch void propagateSingleChange(TransactionalChange change, List<List<VitruviusChange>> commandExecutionChanges) {
		change.applyForward();
		val changeMetamodel = metamodelRepository.getMetamodel(change.URI.fileExtension);
		for (propagationSpecification : changePropagationProvider.getChangePropagationSpecifications(changeMetamodel.URI)) {
			propagateChangeForChangePropagationSpecification(change, propagationSpecification, commandExecutionChanges);
		}
	}
	
	private def void propagateChangeForChangePropagationSpecification(TransactionalChange change, ChangePropagationSpecification propagationSpecification,
			List<List<VitruviusChange>> commandExecutionChanges) {
		val correspondenceModel = correspondenceProviding.getCorrespondenceModel(propagationSpecification.metamodelPair.first, propagationSpecification.metamodelPair.second);
		var Blackboard blackboard = new BlackboardImpl(correspondenceModel, this.modelProviding)
		// TODO HK: Clone the changes for each synchronization! Should even be cloned for
		// each consistency repair routines that uses it,
		// or: make them read only, i.e. give them a read-only interface!
		this.blackboardHistory.add(blackboard)
		blackboard.pushCommands(
			#[
				EMFCommandBridge.createVitruviusTransformationRecordingCommand([|
					return propagationSpecification.propagateChange(change, correspondenceModel);
				])
			])
		val resource = extractSourceResource(change);
		commandExecutionChanges.add(this.commandExecuting.executeCommands(blackboard, resource))	
	}
	
	private def Resource extractSourceResource(TransactionalChange change) {
		val atomicChanges = change.EChanges.map[
			if (it instanceof CompoundEChange) it.atomicChanges else #[it]
		].flatten
		val involvedObjects = atomicChanges.map[
			if (it instanceof FeatureEChange<?,?>) it.affectedEObject 
				else if (it instanceof EObjectAddedEChange<?>) it.newValue 
				else if (it instanceof EObjectSubtractedEChange<?>) it.oldValue].filter(EObject);
		val sourceResources = involvedObjects.map[eResource].filterNull;
		if (sourceResources.empty) {
			return null
		} else {
			// Return the first resource, as they should all be same 
			return sourceResources.get(0);
		}
	}
}
