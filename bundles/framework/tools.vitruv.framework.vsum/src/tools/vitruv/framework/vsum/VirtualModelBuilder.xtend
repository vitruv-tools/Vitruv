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
import tools.vitruv.framework.domains.TuidAwareVitruvDomain
import tools.vitruv.framework.change.processing.ChangePropagationSpecificationRepository
import tools.vitruv.framework.userinteraction.InteractionResultProvider
import tools.vitruv.framework.userinteraction.UserInteractionFactory

class VirtualModelBuilder {
	var VitruvDomainRepository domainRepository = null
	val Set<VitruvDomain> domains = new HashSet
	val Set<ChangePropagationSpecification> changePropagationSpecifications = new HashSet()
	var File storageFolder
	var InternalUserInteractor userInteractor
	
	def VirtualModelBuilder withDomainRepository(VitruvDomainRepository repository) {
		checkState(domains.isEmpty, "You must not configure a domain provider registry after configuring individual domains!")
		checkState(domainRepository === null || domainRepository == repository, "There is already another domain repository configured: %s", domainRepository)

		this.domainRepository = repository
		return this
	}
	
	def VirtualModelBuilder withStorageFolder(File folder) {
		checkState(storageFolder === null || storageFolder == folder, "There is already another storage folder set: %s", storageFolder)
		storageFolder = folder
		return this
	}
	
	def VirtualModelBuilder withStorageFolder(Path folder) {
		withStorageFolder(folder.toFile)
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

	def InternalVirtualModel build() {
		checkState(storageFolder !== null, "No storage folder was configured!")
		checkState(userInteractor !== null, "No user interactor was configured!")
		if (domainRepository === null) {
			checkState(!domains.isEmpty, "No domains were configured!")
			domainRepository = new VitruvDomainRepositoryImpl(domains)
		}
		for (tuidDomain : domainRepository.filter(TuidAwareVitruvDomain)) {
			tuidDomain.registerAtTuidManagement()
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

		new VirtualModelImpl(storageFolder, userInteractor, domainRepository, changeSpecificationRepository)
	}
}