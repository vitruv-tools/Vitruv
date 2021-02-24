package tools.vitruv.framework.vsum.helper

import java.util.List
import tools.vitruv.framework.change.description.PropagatedChange
import tools.vitruv.framework.change.description.VitruviusChange
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.domains.repository.VitruvDomainRepository

class ChangeDomainExtractor {
	VitruvDomainRepository domainRepository;
	
	new(VitruvDomainRepository domainRepository) {
		this.domainRepository = domainRepository;
	}
	
	/**
	 * Determines the target domain of a given list of {@link PropagatedChange}s.
	 * 
	 * @param changes The propagation result
	 * @return The target domain, null if none could be determined
	 */
	def getTargetDomain(List<PropagatedChange> changes) {
		if (changes === null) return null;

		return getDomain(changes.map[consequentialChanges]);
	}

	/**
	 * Determines the source domain of a given list of {@link PropagatedChange}s.
	 * 
	 * @param changes The propagation result
	 * @return The source domain, null if none could be determined
	 */
	def getSourceDomain(List<PropagatedChange> changes) {
		if (changes === null) return null;

		return getDomain(changes.map[originalChange]);
	}

	/**
	 * Determines the domain of a list of given {@link VitruviusChange}s.
	 * 
	 * @param changes The changes used to determine the domain
	 * @return The domain, null if none could be determined
	 */
	def private getDomain(List<VitruviusChange> changes) {
		if (changes === null) {
			return null
		}

		val affectedEObjects = changes.map[affectedAndReferencedEObjects].filterNull.flatten;
		for (EObject eObject : affectedEObjects) {
			if (this.domainRepository.hasDomain(eObject)) {
				return this.domainRepository.getDomain(eObject);
			}
		}

		// Either no eObjects exist or none of them were known at the metamodelRepository
		return null;
	}
}
