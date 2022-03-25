package tools.vitruv.framework.vsum

import java.io.File
import java.nio.file.Path
import java.util.Collection
import java.util.HashSet
import java.util.Set
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.domains.repository.VitruvDomainRepository
import tools.vitruv.framework.domains.repository.VitruvDomainRepositoryImpl
import tools.vitruv.framework.propagation.ChangePropagationSpecification
import tools.vitruv.framework.propagation.ChangePropagationSpecificationRepository
import tools.vitruv.framework.userinteraction.InteractionResultProvider
import tools.vitruv.framework.userinteraction.InternalUserInteractor
import tools.vitruv.framework.userinteraction.UserInteractionFactory
import tools.vitruv.framework.views.ViewType
import tools.vitruv.framework.views.ViewTypeRepository
import tools.vitruv.framework.vsum.helper.VsumFileSystemLayout
import tools.vitruv.framework.vsum.internal.InternalVirtualModel
import tools.vitruv.framework.vsum.internal.VirtualModelImpl

import static com.google.common.base.Preconditions.checkState

class VirtualModelBuilder {
	var VitruvDomainRepository domainRepository = null
	val Set<VitruvDomain> domains = new HashSet()
	val Set<ViewType<?>> viewTypes = new HashSet()
	val Set<ChangePropagationSpecification> changePropagationSpecifications = new HashSet()
	var Path storageFolder
	var InternalUserInteractor userInteractor
	
	def VirtualModelBuilder withDomainRepository(VitruvDomainRepository repository) {
		checkState(domains.isEmpty, "You must not configure a domain provider registry after configuring individual domains!")
		checkState(domainRepository === null || domainRepository == repository, "There is already another domain repository configured: %s", domainRepository)

		this.domainRepository = repository
		return this
	}
	
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
	
	def VirtualModelBuilder withDomains(VitruvDomain... domains) {
		for (domain : domains) withDomain(domain)
		return this
	}
	
	def VirtualModelBuilder withDomains(Iterable<VitruvDomain> domains) {
		for (domain : domains) withDomain(domain)
		return this
	}
	
	def VirtualModelBuilder withDomain(VitruvDomain domain) {
		checkState(domainRepository === null, "You must not configure individual domains after a domain repository was set!")
		if (domains.contains(domain)) return this
		
		for (existingDomain : domains) {
			for (nsURI : domain.nsUris) {
				if (existingDomain.nsUris.contains(nsURI)) {
					throw new IllegalArgumentException(
						'''This virtual model configuration already contains the domain «domain» registering the nsURI «nsURI»!'''
					)
				}
			}
			for (fileExtension : domain.fileExtensions) {
				if (existingDomain.fileExtensions.contains(fileExtension)) {
					throw new IllegalArgumentException(
						'''This virtual model configuration already contains the domain «domain» registering the file extension «fileExtension»!'''
					)
				}
			}
		}
		
		domains += domain
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
		if (domainRepository === null) {
			checkState(!domains.isEmpty, "No domains were configured!")
			domainRepository = new VitruvDomainRepositoryImpl(domains)
		}
		val viewTypeRepository = new ViewTypeRepository()
		viewTypes.forEach[viewTypeRepository.register(it)]
		val changeSpecificationRepository = new ChangePropagationSpecificationRepository(changePropagationSpecifications)
		for (changePropagationSpecification : changePropagationSpecifications) {
			changePropagationSpecification.userInteractor = this.userInteractor
		}

		val fileSystemLayout = new VsumFileSystemLayout(storageFolder)
		fileSystemLayout.prepare()
		val vsum = new VirtualModelImpl(fileSystemLayout, userInteractor, domainRepository, viewTypeRepository, changeSpecificationRepository)
		vsum.loadExistingModels()
		return vsum
	}
}