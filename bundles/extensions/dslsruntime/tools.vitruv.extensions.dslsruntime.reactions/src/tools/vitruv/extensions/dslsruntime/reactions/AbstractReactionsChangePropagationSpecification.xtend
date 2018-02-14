package tools.vitruv.extensions.dslsruntime.reactions

import tools.vitruv.framework.change.processing.impl.CompositeDecomposingChangePropagationSpecification
import tools.vitruv.framework.domains.VitruvDomain

/**
 * A {@link CompositeDecomposingChangePropagationSpecification} that contains the reactions change processor.
 * To add further change processors extend the implementing class and override the setup method.
 */
abstract class AbstractReactionsChangePropagationSpecification extends CompositeDecomposingChangePropagationSpecification {

	new(VitruvDomain sourceDomain, VitruvDomain targetDomain) {
		super(sourceDomain, targetDomain);
		this.setup();
	}

	/**
	 * Adds the reactions change processor to this {@link CompositeDecomposingChangePropagationSpecification}.
	 * For adding further change processors overwrite this method and call the super method at the right place.
	 */
	protected abstract def void setup();
}
