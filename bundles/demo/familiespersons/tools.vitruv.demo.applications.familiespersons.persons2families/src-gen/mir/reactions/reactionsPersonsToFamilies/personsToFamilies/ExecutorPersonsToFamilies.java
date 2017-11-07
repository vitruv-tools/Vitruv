package mir.reactions.reactionsPersonsToFamilies.personsToFamilies;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;

@SuppressWarnings("all")
public class ExecutorPersonsToFamilies extends AbstractReactionsExecutor {
  public ExecutorPersonsToFamilies() {
    super(new tools.vitruv.domains.persons.PersonsDomainProvider().getDomain(), 
    	new tools.vitruv.domains.families.FamiliesDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.reactionsPersonsToFamilies.personsToFamilies.CreatedPersonRegisterReaction());
    this.addReaction(new mir.reactions.reactionsPersonsToFamilies.personsToFamilies.DeletedPersonRegisterReaction());
    this.addReaction(new mir.reactions.reactionsPersonsToFamilies.personsToFamilies.CreatedMaleReaction());
    this.addReaction(new mir.reactions.reactionsPersonsToFamilies.personsToFamilies.CreatedFemaleReaction());
    this.addReaction(new mir.reactions.reactionsPersonsToFamilies.personsToFamilies.ChangedFullNameReaction());
    this.addReaction(new mir.reactions.reactionsPersonsToFamilies.personsToFamilies.DeletePersonReaction());
  }
}
