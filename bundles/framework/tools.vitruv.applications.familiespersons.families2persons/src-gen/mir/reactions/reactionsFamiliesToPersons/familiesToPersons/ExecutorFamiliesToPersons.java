package mir.reactions.reactionsFamiliesToPersons.familiesToPersons;

import tools.vitruv.domains.families.FamiliesDomainProvider;
import tools.vitruv.domains.persons.PersonsDomainProvider;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
public class ExecutorFamiliesToPersons extends AbstractReactionsExecutor {
  public ExecutorFamiliesToPersons(final UserInteracting userInteracting) {
    super(userInteracting,
    	new FamiliesDomainProvider().getDomain(), 
    	new PersonsDomainProvider().getDomain());
  }
  
  protected void setup() {
    tools.vitruv.framework.userinteraction.UserInteracting userInteracting = getUserInteracting();
    this.addReaction(mir.reactions.reactionsFamiliesToPersons.familiesToPersons.CreatedFamilyRegisterReaction.getExpectedChangeType(), new mir.reactions.reactionsFamiliesToPersons.familiesToPersons.CreatedFamilyRegisterReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsFamiliesToPersons.familiesToPersons.DeletedFamilyRegisterReaction.getExpectedChangeType(), new mir.reactions.reactionsFamiliesToPersons.familiesToPersons.DeletedFamilyRegisterReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsFamiliesToPersons.familiesToPersons.CreatedFatherReaction.getExpectedChangeType(), new mir.reactions.reactionsFamiliesToPersons.familiesToPersons.CreatedFatherReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsFamiliesToPersons.familiesToPersons.CreatedSonReaction.getExpectedChangeType(), new mir.reactions.reactionsFamiliesToPersons.familiesToPersons.CreatedSonReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsFamiliesToPersons.familiesToPersons.CreatedMotherReaction.getExpectedChangeType(), new mir.reactions.reactionsFamiliesToPersons.familiesToPersons.CreatedMotherReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsFamiliesToPersons.familiesToPersons.CreatedDaughterReaction.getExpectedChangeType(), new mir.reactions.reactionsFamiliesToPersons.familiesToPersons.CreatedDaughterReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsFamiliesToPersons.familiesToPersons.DeletedMemberReaction.getExpectedChangeType(), new mir.reactions.reactionsFamiliesToPersons.familiesToPersons.DeletedMemberReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsFamiliesToPersons.familiesToPersons.ChangedFirstNameReaction.getExpectedChangeType(), new mir.reactions.reactionsFamiliesToPersons.familiesToPersons.ChangedFirstNameReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsFamiliesToPersons.familiesToPersons.ChangedLastNameReaction.getExpectedChangeType(), new mir.reactions.reactionsFamiliesToPersons.familiesToPersons.ChangedLastNameReaction(userInteracting));
  }
}
