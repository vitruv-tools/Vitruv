package tools.vitruv.framework.propagation

import java.util.List
import tools.vitruv.framework.domains.VitruvDomain

interface ChangePropagationSpecificationProvider extends Iterable<ChangePropagationSpecification> {
	def List<ChangePropagationSpecification> getChangePropagationSpecifications(VitruvDomain sourceDomain)

	def List<ChangePropagationSpecification> getChangePropagationSpecifications(String sourceMetamodelRootNsUri)
}
