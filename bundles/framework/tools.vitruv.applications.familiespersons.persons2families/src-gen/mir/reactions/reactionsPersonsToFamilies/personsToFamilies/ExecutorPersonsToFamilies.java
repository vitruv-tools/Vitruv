package mir.reactions.reactionsPersonsToFamilies.personsToFamilies;

import tools.vitruv.domains.families.FamiliesDomainProvider;
import tools.vitruv.domains.persons.PersonsDomainProvider;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
public class ExecutorPersonsToFamilies extends AbstractReactionsExecutor {
  public ExecutorPersonsToFamilies(final UserInteracting userInteracting) {
    super(userInteracting,
    	new PersonsDomainProvider().getDomain(), 
    	new FamiliesDomainProvider().getDomain());
  }
  
  protected void setup() {
    tools.vitruv.framework.userinteraction.UserInteracting userInteracting = getUserInteracting();
    this.addReaction(mir.reactions.reactionsPersonsToFamilies.personsToFamilies.CreatedPersonRegisterReaction.getExpectedChangeType(), new mir.reactions.reactionsPersonsToFamilies.personsToFamilies.CreatedPersonRegisterReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPersonsToFamilies.personsToFamilies.DeletedPersonRegisterReaction.getExpectedChangeType(), new mir.reactions.reactionsPersonsToFamilies.personsToFamilies.DeletedPersonRegisterReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPersonsToFamilies.personsToFamilies.CreatedMaleReaction.getExpectedChangeType(), new mir.reactions.reactionsPersonsToFamilies.personsToFamilies.CreatedMaleReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPersonsToFamilies.personsToFamilies.CreatedFemaleReaction.getExpectedChangeType(), new mir.reactions.reactionsPersonsToFamilies.personsToFamilies.CreatedFemaleReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPersonsToFamilies.personsToFamilies.ChangedFullNameReaction.getExpectedChangeType(), new mir.reactions.reactionsPersonsToFamilies.personsToFamilies.ChangedFullNameReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsPersonsToFamilies.personsToFamilies.DeletePersonReaction.getExpectedChangeType(), new mir.reactions.reactionsPersonsToFamilies.personsToFamilies.DeletePersonReaction(userInteracting));
  }
}
