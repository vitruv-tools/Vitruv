package mir.reactions.familiesToPersons;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
class ReactionsExecutor extends AbstractReactionsExecutor {
  public ReactionsExecutor() {
    super(new tools.vitruv.domains.families.FamiliesDomainProvider().getDomain(), 
    	new tools.vitruv.domains.persons.PersonsDomainProvider().getDomain());
  }
  
  protected RoutinesFacadesProvider createRoutinesFacadesProvider() {
    return new mir.routines.familiesToPersons.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.familiesToPersons.CreatedFamilyRegisterReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("familiesToPersons"))));
    this.addReaction(new mir.reactions.familiesToPersons.DeletedFamilyRegisterReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("familiesToPersons"))));
    this.addReaction(new mir.reactions.familiesToPersons.CreatedFatherReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("familiesToPersons"))));
    this.addReaction(new mir.reactions.familiesToPersons.CreatedSonReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("familiesToPersons"))));
    this.addReaction(new mir.reactions.familiesToPersons.CreatedMotherReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("familiesToPersons"))));
    this.addReaction(new mir.reactions.familiesToPersons.CreatedDaughterReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("familiesToPersons"))));
    this.addReaction(new mir.reactions.familiesToPersons.DeletedMemberReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("familiesToPersons"))));
    this.addReaction(new mir.reactions.familiesToPersons.ChangedFirstNameReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("familiesToPersons"))));
    this.addReaction(new mir.reactions.familiesToPersons.ChangedLastNameReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("familiesToPersons"))));
  }
}
