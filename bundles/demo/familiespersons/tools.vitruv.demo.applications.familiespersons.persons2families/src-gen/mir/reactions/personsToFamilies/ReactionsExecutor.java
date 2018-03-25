package mir.reactions.personsToFamilies;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
class ReactionsExecutor extends AbstractReactionsExecutor {
  public ReactionsExecutor() {
    super(new tools.vitruv.domains.persons.PersonsDomainProvider().getDomain(), 
    	new tools.vitruv.domains.families.FamiliesDomainProvider().getDomain());
  }
  
  protected RoutinesFacadesProvider createRoutinesFacadesProvider() {
    return new mir.routines.personsToFamilies.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.personsToFamilies.CreatedPersonRegisterReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("personsToFamilies"))));
    this.addReaction(new mir.reactions.personsToFamilies.DeletedPersonRegisterReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("personsToFamilies"))));
    this.addReaction(new mir.reactions.personsToFamilies.CreatedMaleReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("personsToFamilies"))));
    this.addReaction(new mir.reactions.personsToFamilies.CreatedFemaleReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("personsToFamilies"))));
    this.addReaction(new mir.reactions.personsToFamilies.ChangedFullNameReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("personsToFamilies"))));
    this.addReaction(new mir.reactions.personsToFamilies.DeletePersonReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("personsToFamilies"))));
  }
}
