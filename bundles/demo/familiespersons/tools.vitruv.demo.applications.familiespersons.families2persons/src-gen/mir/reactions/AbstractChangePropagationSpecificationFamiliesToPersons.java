package mir.reactions;

import tools.vitruv.domains.families.FamiliesDomainProvider;
import tools.vitruv.framework.change.processing.impl.CompositeChangePropagationSpecification;
import tools.vitruv.domains.persons.PersonsDomainProvider;

/**
 * The {@link class tools.vitruv.framework.change.processing.impl.CompositeChangePropagationSpecification} for transformations between the metamodels Families and Persons.
 * To add further change processors overwrite the setup method.
 */
public abstract class AbstractChangePropagationSpecificationFamiliesToPersons extends CompositeChangePropagationSpecification {
	public AbstractChangePropagationSpecificationFamiliesToPersons() {
		super(new FamiliesDomainProvider().getDomain(), 
			new PersonsDomainProvider().getDomain());
		setup();
	}
	
	/**
	 * Adds the reactions change processors to this {@link AbstractChangePropagationSpecificationFamiliesToPersons}.
	 * For adding further change processors overwrite this method and call the super method at the right place.
	 */
	protected void setup() {
		this.addChangeMainprocessor(new mir.reactions.reactionsFamiliesToPersons.familiesToPersons.ExecutorFamiliesToPersons());
	}
	
}
