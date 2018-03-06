package mir.reactions.familiesToPersons;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsChangePropagationSpecification;
import tools.vitruv.framework.change.processing.ChangePropagationSpecification;

@SuppressWarnings("all")
public class FamiliesToPersonsChangePropagationSpecification extends AbstractReactionsChangePropagationSpecification implements ChangePropagationSpecification {
  public FamiliesToPersonsChangePropagationSpecification() {
    super(new tools.vitruv.domains.families.FamiliesDomainProvider().getDomain(), 
    	new tools.vitruv.domains.persons.PersonsDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addChangeMainprocessor(new mir.reactions.familiesToPersons.ReactionsExecutor());
  }
}
