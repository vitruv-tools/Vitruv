package tools.vitruv.testutils.change.processing

import tools.vitruv.framework.propagation.ChangePropagationSpecification
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.propagation.impl.CompositeChangePropagationSpecification

class CombinedChangePropagationSpecification extends CompositeChangePropagationSpecification {
	new(VitruvDomain sourceDomain, VitruvDomain targetDomain, Iterable<ChangePropagationSpecification> processors) {
		super(sourceDomain, targetDomain)
		processors.forEach[addChangeMainprocessor(it)]
	}
}
