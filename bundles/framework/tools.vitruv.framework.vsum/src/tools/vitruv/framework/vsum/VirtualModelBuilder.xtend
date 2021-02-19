package tools.vitruv.framework.vsum

import tools.vitruv.framework.domains.repository.VitruvDomainRepository
import java.util.Set
import tools.vitruv.framework.domains.VitruvDomain
import static com.google.common.base.Preconditions.checkState
import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import java.util.HashSet
import java.io.File
import tools.vitruv.framework.userinteraction.InternalUserInteractor
import java.nio.file.Path
import tools.vitruv.framework.domains.repository.VitruvDomainRepositoryImpl
import tools.vitruv.framework.change.processing.ChangePropagationSpecificationRepository
import tools.vitruv.framework.userinteraction.InteractionResultProvider
import tools.vitruv.framework.userinteraction.UserInteractionFactory
import tools.vitruv.framework.vsum.helper.VsumFileSystemLayout

class VirtualModelBuilder {
	var VitruvDomainRepository domainRepository = null
	val Set<VitruvDomain> domains = new HashSet
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
			if (existingPropagationSpecification == changePropagationSpecification) return this
			
			if (existingPropagationSpecification.sourceDomain.equals(changePropagationSpecification.sourceDomain)
				&& existingPropagationSpecification.targetDomain.equals(changePropagationSpecification.targetDomain)
			) {
				throw new IllegalArgumentException(
					'''This virtual model configuration already contains the change propagation specification «
						existingPropagationSpecification» between «existingPropagationSpecification.sourceDomain» and «
						existingPropagationSpecification.targetDomain»!'''
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
		val changeSpecificationRepository = new ChangePropagationSpecificationRepository(changePropagationSpecifications)
		for (changePropagationSpecification : changePropagationSpecifications) {
			checkState(domainRepository.contains(changePropagationSpecification.sourceDomain),
 				"The change propagation specification’s source domain ‹%s› has not been configured: %s",
 				changePropagationSpecification.sourceDomain, changePropagationSpecification)
			checkState(domainRepository.contains(changePropagationSpecification.targetDomain),
 				"The change propagation specification’s target domain ‹%s› has not been configured: %s",
 				 changePropagationSpecification.targetDomain, changePropagationSpecification)
		}
		for (changePropagationSpecification : changePropagationSpecifications) {
			changePropagationSpecification.userInteractor = this.userInteractor
		}

		val fileSystemLayout = new VsumFileSystemLayout(storageFolder)
		fileSystemLayout.prepare()
		val vsum = new VirtualModelImpl(fileSystemLayout, userInteractor, domainRepository, changeSpecificationRepository)
		VirtualModelManager.instance.putVirtualModel(vsum)
		return vsum
	}
}