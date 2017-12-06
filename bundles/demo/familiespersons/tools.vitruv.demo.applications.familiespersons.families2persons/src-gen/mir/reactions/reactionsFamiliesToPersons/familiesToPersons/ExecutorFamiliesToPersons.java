package mir.reactions.reactionsFamiliesToPersons.familiesToPersons;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;

@SuppressWarnings("all")
public class ExecutorFamiliesToPersons extends AbstractReactionsExecutor {
  public ExecutorFamiliesToPersons() {
    super(new tools.vitruv.domains.families.FamiliesDomainProvider().getDomain(), 
    	new tools.vitruv.domains.persons.PersonsDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.reactionsFamiliesToPersons.familiesToPersons.CreatedFamilyRegisterReaction());
    this.addReaction(new mir.reactions.reactionsFamiliesToPersons.familiesToPersons.DeletedFamilyRegisterReaction());
    this.addReaction(new mir.reactions.reactionsFamiliesToPersons.familiesToPersons.CreatedFatherReaction());
    this.addReaction(new mir.reactions.reactionsFamiliesToPersons.familiesToPersons.CreatedSonReaction());
    this.addReaction(new mir.reactions.reactionsFamiliesToPersons.familiesToPersons.CreatedMotherReaction());
    this.addReaction(new mir.reactions.reactionsFamiliesToPersons.familiesToPersons.CreatedDaughterReaction());
    this.addReaction(new mir.reactions.reactionsFamiliesToPersons.familiesToPersons.DeletedMemberReaction());
    this.addReaction(new mir.reactions.reactionsFamiliesToPersons.familiesToPersons.ChangedFirstNameReaction());
    this.addReaction(new mir.reactions.reactionsFamiliesToPersons.familiesToPersons.ChangedLastNameReaction());
  }
}
