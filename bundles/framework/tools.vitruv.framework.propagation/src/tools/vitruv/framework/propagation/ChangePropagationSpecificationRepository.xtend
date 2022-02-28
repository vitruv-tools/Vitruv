package tools.vitruv.framework.propagation

import java.util.Map
import java.util.List
import java.util.ArrayList
import tools.vitruv.framework.domains.VitruvDomain
import java.util.HashMap

class ChangePropagationSpecificationRepository implements ChangePropagationSpecificationProvider {
	Map<VitruvDomain, List<ChangePropagationSpecification>> sourceDomainToPropagationSpecifications
	Map<String, List<ChangePropagationSpecification>> sourceMetamodelNsUriToPropagationSpecifications

	new(Iterable<ChangePropagationSpecification> specifications) {
		sourceDomainToPropagationSpecifications = specifications.groupBy[sourceDomain]
		sourceMetamodelNsUriToPropagationSpecifications = new HashMap
		for (specification : specifications) {
			for (nsUri : specification.sourceDomain.nsUris) {
				sourceMetamodelNsUriToPropagationSpecifications.computeIfAbsent(nsUri, [new ArrayList]).add(
					specification)
			}
		}
	}

	override List<ChangePropagationSpecification> getChangePropagationSpecifications(VitruvDomain sourceDomain) {
		val result = sourceDomainToPropagationSpecifications.get(sourceDomain)
		return if(result !== null) new ArrayList(result) else emptyList
	}

	override List<ChangePropagationSpecification> getChangePropagationSpecifications(String sourceMetamodelRootNsUri) {
		val result = sourceMetamodelNsUriToPropagationSpecifications.get(sourceMetamodelRootNsUri)
		return if(result !== null) new ArrayList(result) else emptyList
	}

	override iterator() {
		return sourceDomainToPropagationSpecifications.values.flatten.toList.iterator()
	}
}
