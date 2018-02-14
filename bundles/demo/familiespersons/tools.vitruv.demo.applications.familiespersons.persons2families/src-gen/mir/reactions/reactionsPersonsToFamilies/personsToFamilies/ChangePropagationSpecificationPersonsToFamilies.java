package mir.reactions.reactionsPersonsToFamilies.personsToFamilies;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsChangePropagationSpecification;

@SuppressWarnings("all")
public class ChangePropagationSpecificationPersonsToFamilies extends AbstractReactionsChangePropagationSpecification {
  public ChangePropagationSpecificationPersonsToFamilies() {
    super(new tools.vitruv.domains.persons.PersonsDomainProvider().getDomain(), 
    	new tools.vitruv.domains.families.FamiliesDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addChangeMainprocessor(new mir.reactions.reactionsPersonsToFamilies.personsToFamilies.ExecutorPersonsToFamilies());
  }
}
