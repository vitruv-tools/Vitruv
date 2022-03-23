package tools.vitruv.framework.propagation

import java.util.List
import tools.vitruv.framework.change.Metamodel

interface ChangePropagationSpecificationProvider extends Iterable<ChangePropagationSpecification> {
	def List<ChangePropagationSpecification> getChangePropagationSpecifications(Metamodel sourceMetamodel)
}
