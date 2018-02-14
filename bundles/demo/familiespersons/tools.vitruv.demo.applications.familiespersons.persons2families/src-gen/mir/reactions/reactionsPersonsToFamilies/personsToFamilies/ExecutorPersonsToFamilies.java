package mir.reactions.reactionsPersonsToFamilies.personsToFamilies;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;

@SuppressWarnings("all")
class ExecutorPersonsToFamilies extends AbstractReactionsExecutor {
  public ExecutorPersonsToFamilies() {
    super(new tools.vitruv.domains.persons.PersonsDomainProvider().getDomain(), 
    	new tools.vitruv.domains.families.FamiliesDomainProvider().getDomain());
  }
  
  protected RoutinesFacadesProvider createRoutinesFacadesProvider() {
    return new mir.routines.personsToFamilies.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.reactionsPersonsToFamilies.personsToFamilies.CreatedPersonRegisterReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PersonsToFamilies"))));
    this.addReaction(new mir.reactions.reactionsPersonsToFamilies.personsToFamilies.DeletedPersonRegisterReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PersonsToFamilies"))));
    this.addReaction(new mir.reactions.reactionsPersonsToFamilies.personsToFamilies.CreatedMaleReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PersonsToFamilies"))));
    this.addReaction(new mir.reactions.reactionsPersonsToFamilies.personsToFamilies.CreatedFemaleReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PersonsToFamilies"))));
    this.addReaction(new mir.reactions.reactionsPersonsToFamilies.personsToFamilies.ChangedFullNameReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PersonsToFamilies"))));
    this.addReaction(new mir.reactions.reactionsPersonsToFamilies.personsToFamilies.DeletePersonReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("PersonsToFamilies"))));
  }
}
