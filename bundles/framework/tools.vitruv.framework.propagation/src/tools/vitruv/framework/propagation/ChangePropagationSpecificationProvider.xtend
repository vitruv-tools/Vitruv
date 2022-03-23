package tools.vitruv.framework.propagation

import java.util.List

interface ChangePropagationSpecificationProvider extends Iterable<ChangePropagationSpecification> {
	def List<ChangePropagationSpecification> getChangePropagationSpecifications(Metamodel sourceMetamodel)
}
