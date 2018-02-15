package tools.vitruv.dsls.commonalities.ui.executiontests

import tools.vitruv.framework.change.processing.impl.CompositeChangePropagationSpecification
import tools.vitruv.framework.domains.VitruvDomain
import tools.vitruv.framework.change.processing.ChangePropagationSpecification

class CombinedChangePropagationSpecification extends CompositeChangePropagationSpecification {

	new(VitruvDomain sourceDomain, VitruvDomain targetDomain, Iterable<ChangePropagationSpecification> processors) {
		super(sourceDomain, targetDomain)
		processors.forEach[addChangeMainprocessor(it)]
	}
}
