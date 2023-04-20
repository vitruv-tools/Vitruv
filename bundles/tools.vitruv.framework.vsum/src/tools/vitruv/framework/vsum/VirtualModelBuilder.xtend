package tools.vitruv.framework.vsum

import java.io.File
import java.nio.file.Path
import java.util.Collection
import java.util.HashSet
import java.util.Set
import tools.vitruv.change.propagation.ChangePropagationSpecification
import tools.vitruv.change.propagation.ChangePropagationSpecificationRepository
import tools.vitruv.change.interaction.InteractionResultProvider
import tools.vitruv.change.interaction.InternalUserInteractor
import tools.vitruv.change.interaction.UserInteractionFactory
import tools.vitruv.framework.views.ViewType
import tools.vitruv.framework.views.ViewTypeRepository
import tools.vitruv.framework.vsum.helper.VsumFileSystemLayout
import tools.vitruv.framework.vsum.internal.InternalVirtualModel
import tools.vitruv.framework.vsum.internal.VirtualModelImpl
import tools.vitruv.change.propagation.ProjectMarker

import static com.google.common.base.Preconditions.checkState

class VirtualModelBuilder {
	val Set<ViewType<?>> viewTypes = new HashSet()
	val Set<ChangePropagationSpecification> changePropagationSpecifications = new HashSet()
	var Path storageFolder
	var InternalUserInteractor userInteractor
	
	def VirtualModelBuilder withStorageFolder(File folder) {
		withStorageFolder(folder.toPath)
	}
	
	def VirtualModelBuilder withStorageFolder(Path folder) {
		checkState(storageFolder === null || storageFolder == folder, "There is already another storage folder set: %s", storageFolder)
		storageFolder = folder
		return this
	}
	
	def VirtualModelBuilder withUserInteractorForResultProvider(InteractionResultProvider resultProvider) {
		withUserInteractor(UserInteractionFactory.instance.createUserInteractor(resultProvider))
	}
	
	def VirtualModelBuilder withUserInteractor(InternalUserInteractor userInteractor) {
		checkState(this.userInteractor === null || this.userInteractor == userInteractor,
			"There is already another user interactor set: %s", this.userInteractor)
		this.userInteractor = userInteractor
		return this
	}
	
	def VirtualModelBuilder withViewType(ViewType<?> viewType) {
		viewTypes += viewType
		return this
	}
	
	def VirtualModelBuilder withViewTypes(Collection<ViewType<?>> viewTypes) {
		for (viewType : viewTypes) withViewType(viewType)
		return this
	}
	
	def VirtualModelBuilder withChangePropagationSpecifications(ChangePropagationSpecification... changePropagationSpecifications) {
		for(spec : changePropagationSpecifications) withChangePropagationSpecification(spec)
		return this
	}
	
	def VirtualModelBuilder withChangePropagationSpecifications(Iterable<ChangePropagationSpecification> changePropagationSpecifications) {
		for(spec : changePropagationSpecifications) withChangePropagationSpecification(spec)
		return this
	}
	
	def VirtualModelBuilder withChangePropagationSpecification(ChangePropagationSpecification changePropagationSpecification) {
		if (changePropagationSpecifications.contains(changePropagationSpecification)) return this
		
		for (existingPropagationSpecification : changePropagationSpecifications) {
			if(existingPropagationSpecification == changePropagationSpecification) return this

			if (existingPropagationSpecification.sourceMetamodelDescriptor.equals(
				changePropagationSpecification.sourceMetamodelDescriptor) &&
				existingPropagationSpecification.targetMetamodelDescriptor.equals(
					changePropagationSpecification.targetMetamodelDescriptor)) {
				throw new IllegalArgumentException(
					'''This virtual model configuration already contains the change propagation specification «existingPropagationSpecification» between «existingPropagationSpecification.sourceMetamodelDescriptor» and «existingPropagationSpecification.targetMetamodelDescriptor»!'''
				)
			}
		}
		
		changePropagationSpecifications += changePropagationSpecification
		return this
	}

	def InternalVirtualModel buildAndInitialize() {
		checkState(storageFolder !== null, "No storage folder was configured!")
		checkState(userInteractor !== null, "No user interactor was configured!")
		val viewTypeRepository = new ViewTypeRepository()
		viewTypes.forEach[viewTypeRepository.register(it)]
		val changeSpecificationRepository = new ChangePropagationSpecificationRepository(changePropagationSpecifications)

		val fileSystemLayout = new VsumFileSystemLayout(storageFolder)
		fileSystemLayout.prepare()
		val vsum = new VirtualModelImpl(fileSystemLayout, userInteractor, viewTypeRepository, changeSpecificationRepository)
		vsum.loadExistingModels()
		try {
			ProjectMarker.getProjectRootFolder(storageFolder)
		} catch (IllegalStateException exception) {
			ProjectMarker.markAsProjectRootFolder(storageFolder)
		}
		return vsum
	}
}