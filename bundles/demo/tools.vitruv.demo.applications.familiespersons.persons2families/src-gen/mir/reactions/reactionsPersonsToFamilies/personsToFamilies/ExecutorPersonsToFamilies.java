package mir.reactions.reactionsPersonsToFamilies.personsToFamilies;

import tools.vitruv.domains.families.FamiliesDomainProvider;
import tools.vitruv.domains.persons.PersonsDomainProvider;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;

@SuppressWarnings("all")
public class ExecutorPersonsToFamilies extends AbstractReactionsExecutor {
  public ExecutorPersonsToFamilies() {
    super(new PersonsDomainProvider().getDomain(), 
    	new FamiliesDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addReaction(mir.reactions.reactionsPersonsToFamilies.personsToFamilies.CreatedPersonRegisterReaction.getExpectedChangeType(), new mir.reactions.reactionsPersonsToFamilies.personsToFamilies.CreatedPersonRegisterReaction());
    this.addReaction(mir.reactions.reactionsPersonsToFamilies.personsToFamilies.DeletedPersonRegisterReaction.getExpectedChangeType(), new mir.reactions.reactionsPersonsToFamilies.personsToFamilies.DeletedPersonRegisterReaction());
    this.addReaction(mir.reactions.reactionsPersonsToFamilies.personsToFamilies.CreatedMaleReaction.getExpectedChangeType(), new mir.reactions.reactionsPersonsToFamilies.personsToFamilies.CreatedMaleReaction());
    this.addReaction(mir.reactions.reactionsPersonsToFamilies.personsToFamilies.CreatedFemaleReaction.getExpectedChangeType(), new mir.reactions.reactionsPersonsToFamilies.personsToFamilies.CreatedFemaleReaction());
    this.addReaction(mir.reactions.reactionsPersonsToFamilies.personsToFamilies.ChangedFullNameReaction.getExpectedChangeType(), new mir.reactions.reactionsPersonsToFamilies.personsToFamilies.ChangedFullNameReaction());
    this.addReaction(mir.reactions.reactionsPersonsToFamilies.personsToFamilies.DeletePersonReaction.getExpectedChangeType(), new mir.reactions.reactionsPersonsToFamilies.personsToFamilies.DeletePersonReaction());
  }
}
