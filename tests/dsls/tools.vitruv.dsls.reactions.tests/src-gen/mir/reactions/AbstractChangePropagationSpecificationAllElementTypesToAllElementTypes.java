package mir.reactions;

import tools.vitruv.framework.change.processing.impl.CompositeChangePropagationSpecification;
import tools.vitruv.framework.testutils.domains.AllElementTypesDomainProvider;

/**
 * The {@link class tools.vitruv.framework.change.processing.impl.CompositeChangePropagationSpecification} for transformations between the metamodels AllElementTypes and AllElementTypes.
 * To add further change processors overwrite the setup method.
 */
public abstract class AbstractChangePropagationSpecificationAllElementTypesToAllElementTypes extends CompositeChangePropagationSpecification {
	public AbstractChangePropagationSpecificationAllElementTypesToAllElementTypes() {
		super(new tools.vitruv.framework.userinteraction.impl.UserInteractor());
		setMetamodels(new AllElementTypesDomainProvider().getDomain(), new AllElementTypesDomainProvider().getDomain());
		setup();
	}
	
	/**
	 * Adds the reactions change processors to this {@link AbstractChangePropagationSpecificationAllElementTypesToAllElementTypes}.
	 * For adding further change processors overwrite this method and call the super method at the right place.
	 */
	protected void setup() {
		this.addChangeMainprocessor(new mir.reactions.reactionsAllElementTypesToAllElementTypes.simpleChangesTests.ExecutorAllElementTypesToAllElementTypes(getUserInteracting()));
	}
	
}
