package tools.vitruv.dsls.commonalities.ui.tests.util

import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import tools.vitruv.framework.change.processing.impl.CompositeDecomposingChangePropagationSpecification
import tools.vitruv.framework.domains.VitruvDomain

package class CombinedChangePropagationSpecification extends CompositeDecomposingChangePropagationSpecification {

	new(VitruvDomain sourceDomain, VitruvDomain targetDomain, Iterable<ChangePropagationSpecification> processors) {
		super(sourceDomain, targetDomain)
		processors.forEach[addChangeMainprocessor(it)]
	}
}
