package tools.vitruv.framework.propagation

import java.util.Map
import java.util.List
import java.util.ArrayList
import tools.vitruv.framework.domains.VitruvDomain

class ChangePropagationSpecificationRepository implements ChangePropagationSpecificationProvider {
	Map<VitruvDomain, List<ChangePropagationSpecification>> sourceDomainToPropagationSpecifications

	new(Iterable<ChangePropagationSpecification> specifications) {
		sourceDomainToPropagationSpecifications = specifications.groupBy [sourceDomain]
	}
	
	override List<ChangePropagationSpecification> getChangePropagationSpecifications(VitruvDomain sourceDomain) {
		val result = sourceDomainToPropagationSpecifications.get(sourceDomain)
		return if (result !== null) new ArrayList(result) else emptyList
	}
	
	override iterator() {
		return sourceDomainToPropagationSpecifications.values.flatten.toList.iterator()
	}
}