package mir.reactions.reactionsFamiliesToPersons.familiesToPersons;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsChangePropagationSpecification;

@SuppressWarnings("all")
public class ChangePropagationSpecificationFamiliesToPersons extends AbstractReactionsChangePropagationSpecification {
  public ChangePropagationSpecificationFamiliesToPersons() {
    super(new tools.vitruv.domains.families.FamiliesDomainProvider().getDomain(), 
    	new tools.vitruv.domains.persons.PersonsDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addChangeMainprocessor(new mir.reactions.reactionsFamiliesToPersons.familiesToPersons.ExecutorFamiliesToPersons());
  }
}
