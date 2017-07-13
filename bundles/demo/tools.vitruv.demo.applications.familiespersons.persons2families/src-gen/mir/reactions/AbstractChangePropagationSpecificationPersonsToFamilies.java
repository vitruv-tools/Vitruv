package mir.reactions;

import tools.vitruv.domains.families.FamiliesDomainProvider;
import tools.vitruv.framework.change.processing.impl.CompositeChangePropagationSpecification;
import tools.vitruv.domains.persons.PersonsDomainProvider;

/**
 * The {@link class tools.vitruv.framework.change.processing.impl.CompositeChangePropagationSpecification} for transformations between the metamodels Persons and Families.
 * To add further change processors overwrite the setup method.
 */
public abstract class AbstractChangePropagationSpecificationPersonsToFamilies extends CompositeChangePropagationSpecification {
	public AbstractChangePropagationSpecificationPersonsToFamilies() {
		super(new PersonsDomainProvider().getDomain(), 
			new FamiliesDomainProvider().getDomain());
		setup();
	}
	
	/**
	 * Adds the reactions change processors to this {@link AbstractChangePropagationSpecificationPersonsToFamilies}.
	 * For adding further change processors overwrite this method and call the super method at the right place.
	 */
	protected void setup() {
		this.addChangeMainprocessor(new mir.reactions.reactionsPersonsToFamilies.personsToFamilies.ExecutorPersonsToFamilies());
	}
	
}
