package mir.reactions.personsToFamilies;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsChangePropagationSpecification;

@SuppressWarnings("all")
public class PersonsToFamiliesChangePropagationSpecification extends AbstractReactionsChangePropagationSpecification {
  public PersonsToFamiliesChangePropagationSpecification() {
    super(new tools.vitruv.domains.persons.PersonsDomainProvider().getDomain(), 
    	new tools.vitruv.domains.families.FamiliesDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addChangeMainprocessor(new mir.reactions.personsToFamilies.ReactionsExecutor());
  }
}
