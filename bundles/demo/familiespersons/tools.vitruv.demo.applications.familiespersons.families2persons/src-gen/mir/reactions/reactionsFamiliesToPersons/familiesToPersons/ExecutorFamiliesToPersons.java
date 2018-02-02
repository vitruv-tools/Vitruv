package mir.reactions.reactionsFamiliesToPersons.familiesToPersons;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;

@SuppressWarnings("all")
public class ExecutorFamiliesToPersons extends AbstractReactionsExecutor {
  public ExecutorFamiliesToPersons() {
    super(new tools.vitruv.domains.families.FamiliesDomainProvider().getDomain(), 
    	new tools.vitruv.domains.persons.PersonsDomainProvider().getDomain());
  }
  
  protected RoutinesFacadesProvider createRoutinesFacadesProvider() {
    return new mir.routines.familiesToPersons.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.reactionsFamiliesToPersons.familiesToPersons.CreatedFamilyRegisterReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("FamiliesToPersons"))));
    this.addReaction(new mir.reactions.reactionsFamiliesToPersons.familiesToPersons.DeletedFamilyRegisterReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("FamiliesToPersons"))));
    this.addReaction(new mir.reactions.reactionsFamiliesToPersons.familiesToPersons.CreatedFatherReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("FamiliesToPersons"))));
    this.addReaction(new mir.reactions.reactionsFamiliesToPersons.familiesToPersons.CreatedSonReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("FamiliesToPersons"))));
    this.addReaction(new mir.reactions.reactionsFamiliesToPersons.familiesToPersons.CreatedMotherReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("FamiliesToPersons"))));
    this.addReaction(new mir.reactions.reactionsFamiliesToPersons.familiesToPersons.CreatedDaughterReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("FamiliesToPersons"))));
    this.addReaction(new mir.reactions.reactionsFamiliesToPersons.familiesToPersons.DeletedMemberReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("FamiliesToPersons"))));
    this.addReaction(new mir.reactions.reactionsFamiliesToPersons.familiesToPersons.ChangedFirstNameReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("FamiliesToPersons"))));
    this.addReaction(new mir.reactions.reactionsFamiliesToPersons.familiesToPersons.ChangedLastNameReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("FamiliesToPersons"))));
  }
}
