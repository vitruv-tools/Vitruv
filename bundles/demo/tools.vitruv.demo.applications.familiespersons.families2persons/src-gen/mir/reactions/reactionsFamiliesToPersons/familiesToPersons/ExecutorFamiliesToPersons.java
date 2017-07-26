package mir.reactions.reactionsFamiliesToPersons.familiesToPersons;

import tools.vitruv.domains.families.FamiliesDomainProvider;
import tools.vitruv.domains.persons.PersonsDomainProvider;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;

@SuppressWarnings("all")
public class ExecutorFamiliesToPersons extends AbstractReactionsExecutor {
  public ExecutorFamiliesToPersons() {
    super(new FamiliesDomainProvider().getDomain(), 
    	new PersonsDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addReaction(mir.reactions.reactionsFamiliesToPersons.familiesToPersons.CreatedFamilyRegisterReaction.getExpectedChangeType(), new mir.reactions.reactionsFamiliesToPersons.familiesToPersons.CreatedFamilyRegisterReaction());
    this.addReaction(mir.reactions.reactionsFamiliesToPersons.familiesToPersons.DeletedFamilyRegisterReaction.getExpectedChangeType(), new mir.reactions.reactionsFamiliesToPersons.familiesToPersons.DeletedFamilyRegisterReaction());
    this.addReaction(mir.reactions.reactionsFamiliesToPersons.familiesToPersons.CreatedFatherReaction.getExpectedChangeType(), new mir.reactions.reactionsFamiliesToPersons.familiesToPersons.CreatedFatherReaction());
    this.addReaction(mir.reactions.reactionsFamiliesToPersons.familiesToPersons.CreatedSonReaction.getExpectedChangeType(), new mir.reactions.reactionsFamiliesToPersons.familiesToPersons.CreatedSonReaction());
    this.addReaction(mir.reactions.reactionsFamiliesToPersons.familiesToPersons.CreatedMotherReaction.getExpectedChangeType(), new mir.reactions.reactionsFamiliesToPersons.familiesToPersons.CreatedMotherReaction());
    this.addReaction(mir.reactions.reactionsFamiliesToPersons.familiesToPersons.CreatedDaughterReaction.getExpectedChangeType(), new mir.reactions.reactionsFamiliesToPersons.familiesToPersons.CreatedDaughterReaction());
    this.addReaction(mir.reactions.reactionsFamiliesToPersons.familiesToPersons.DeletedMemberReaction.getExpectedChangeType(), new mir.reactions.reactionsFamiliesToPersons.familiesToPersons.DeletedMemberReaction());
    this.addReaction(mir.reactions.reactionsFamiliesToPersons.familiesToPersons.ChangedFirstNameReaction.getExpectedChangeType(), new mir.reactions.reactionsFamiliesToPersons.familiesToPersons.ChangedFirstNameReaction());
    this.addReaction(mir.reactions.reactionsFamiliesToPersons.familiesToPersons.ChangedLastNameReaction.getExpectedChangeType(), new mir.reactions.reactionsFamiliesToPersons.familiesToPersons.ChangedLastNameReaction());
  }
}
