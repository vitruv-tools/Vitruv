package tools.vitruv.framework.domains.repository

import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.domains.repository.VitruvDomainRepository
import edu.kit.ipd.sdq.activextendannotations.Utility

@Utility
class DomainAwareResourceSet {
	def static ResourceSet awareOfDomains(ResourceSet resourceSet, VitruvDomainRepository domains) {
		resourceSet.resourceFactoryRegistry = new DomainAwareResource.Factory.Registry(
			resourceSet.resourceFactoryRegistry,
			domains
		)
		return resourceSet
	}
}
