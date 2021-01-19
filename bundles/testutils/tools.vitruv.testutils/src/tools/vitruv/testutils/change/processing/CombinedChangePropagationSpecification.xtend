package tools.vitruv.testutils.change.processing

import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import tools.vitruv.framework.change.processing.impl.CompositeDecomposingChangePropagationSpecification
import tools.vitruv.framework.domains.VitruvDomain

class CombinedChangePropagationSpecification extends CompositeDecomposingChangePropagationSpecification {
	new(VitruvDomain sourceDomain, VitruvDomain targetDomain, Iterable<ChangePropagationSpecification> processors) {
		super(sourceDomain, targetDomain)
		processors.forEach[addChangeMainprocessor(it)]
	}
}
