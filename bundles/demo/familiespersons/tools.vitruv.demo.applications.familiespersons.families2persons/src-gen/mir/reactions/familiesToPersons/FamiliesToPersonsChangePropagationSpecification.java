package mir.reactions.familiesToPersons;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsChangePropagationSpecification;

@SuppressWarnings("all")
public class FamiliesToPersonsChangePropagationSpecification extends AbstractReactionsChangePropagationSpecification {
  public FamiliesToPersonsChangePropagationSpecification() {
    super(new tools.vitruv.domains.families.FamiliesDomainProvider().getDomain(), 
    	new tools.vitruv.domains.persons.PersonsDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addChangeMainprocessor(new mir.reactions.familiesToPersons.ReactionsExecutor());
  }
}
