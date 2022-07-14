package tools.vitruv.framework.vsum.internal

import java.nio.file.Path
import java.util.LinkedList
import java.util.List
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.URI
import org.eclipse.xtend.lib.annotations.Delegate
import tools.vitruv.change.composite.description.PropagatedChange
import tools.vitruv.change.composite.description.VitruviusChange
import tools.vitruv.change.composite.propagation.ChangePropagationListener
import tools.vitruv.change.propagation.ChangePropagationSpecificationProvider
import tools.vitruv.change.propagation.impl.ChangePropagator
import tools.vitruv.change.interaction.InternalUserInteractor
import tools.vitruv.framework.views.ViewSelector
import tools.vitruv.framework.views.ViewType
import tools.vitruv.framework.views.ViewTypeProvider
import tools.vitruv.framework.views.ViewTypeRepository
import tools.vitruv.framework.vsum.helper.VsumFileSystemLayout

import static com.google.common.base.Preconditions.checkArgument
import static com.google.common.base.Preconditions.checkNotNull
import tools.vitruv.change.propagation.ChangePropagationMode

class VirtualModelImpl implements InternalVirtualModel {
	static val Logger LOGGER = Logger.getLogger(VirtualModelImpl)
	val ModelRepository resourceRepository
	@Delegate val ViewTypeProvider viewTypeRepository
	val ChangePropagator changePropagator
	val VsumFileSystemLayout fileSystemLayout
	val List<ChangePropagationListener> changePropagationListeners = new LinkedList()

	new(VsumFileSystemLayout fileSystemLayout, InternalUserInteractor userInteractor,
		ViewTypeRepository viewTypeRepository,
		ChangePropagationSpecificationProvider changePropagationSpecificationProvider) {
		this.fileSystemLayout = fileSystemLayout
		this.viewTypeRepository = viewTypeRepository
		resourceRepository = new ResourceRepositoryImpl(fileSystemLayout)
		changePropagator = new ChangePropagator(
			resourceRepository,
			changePropagationSpecificationProvider,
			userInteractor
		)
		VirtualModelRegistry.instance.registerVirtualModel(this)
	}

	def loadExistingModels() {
		this.resourceRepository.loadExistingModels()
	}

	override synchronized getCorrespondenceModel() {
		this.resourceRepository.correspondenceModel
	}

	override synchronized getModelInstance(URI modelUri) {
		this.resourceRepository.getModel(modelUri)
	}

	private def synchronized save() {
		this.resourceRepository.saveOrDeleteModels()
	}

	override synchronized propagateChange(VitruviusChange change) {
		checkNotNull(change, "change to propagate")
		checkArgument(change.containsConcreteChange, "This change contains no concrete changes:%s%s",
			System.lineSeparator, change)
		val unresolvedChange = change.unresolve()

		LOGGER.info("Start change propagation")
		startChangePropagation(unresolvedChange)

		val result = changePropagator.propagateChange(unresolvedChange)
		save()

		if (LOGGER.isTraceEnabled) {
			LOGGER.trace('''
				Propagated changes:
				«FOR propagatedChange : result»
					Propagated Change:
					«propagatedChange»
				«ENDFOR»
			''')
		}

		finishChangePropagation(unresolvedChange, result)
		LOGGER.info("Finished change propagation")
		return result
	}

	private def void startChangePropagation(VitruviusChange change) {
		if(LOGGER.isDebugEnabled) LOGGER.debug('''Started synchronizing change: «change»''')
		changePropagationListeners.forEach[startedChangePropagation(change)]
	}

	private def void finishChangePropagation(VitruviusChange inputChange, Iterable<PropagatedChange> generatedChanges) {
		changePropagationListeners.forEach[finishedChangePropagation(generatedChanges)]
		if(LOGGER.isDebugEnabled) LOGGER.debug('''Finished synchronizing change: «inputChange»''')
	}

	override Path getFolder() {
		return fileSystemLayout.vsumProjectFolder
	}

	/**
	 * Registers the given {@link ChangePropagationListener}.
	 * The listener must not be <code>null</code>.
	 */
	override synchronized void addChangePropagationListener(ChangePropagationListener propagationListener) {
		this.changePropagationListeners.add(checkNotNull(propagationListener, "propagationListener"))
	}

	/**
	 * Unregisters the given {@link ChangePropagationListener}.
	 * The listener must not be <code>null</code>.
	 */
	override synchronized void removeChangePropagationListener(ChangePropagationListener propagationListener) {
		this.changePropagationListeners.remove(checkNotNull(propagationListener, "propagationListener"))
	}

	/**
	 * Returns the name of the virtual model.
	 * 
	 * @return The name of the virtual model
	 */
	def getName() {
		folder.fileName.toString
	}

	override void dispose() {
		resourceRepository.close()
		VirtualModelRegistry.instance.deregisterVirtualModel(this)
	}

	override getViewSourceModels() {
		resourceRepository.modelResources
	}

	override <S extends ViewSelector> createSelector(ViewType<S> viewType) {
		/* Note that ViewType.createSelector() accepts a ChangeableViewSource, which 
		 * VirtualModelImpl implements but not its publicly used interface VitualModel. 
		 * Thus calling viewType.createSelector(virtualModel) with virtualModel having
		 * the static type VirtualModel is not possible, i.e., this method hides
		 * implementation details and is not a convenience method.
		 */
		viewType.createSelector(this)
	}
	
	override setChangePropagationMode(ChangePropagationMode changePropagationMode) {
		changePropagator.changePropagationMode = changePropagationMode
	}
	
}
