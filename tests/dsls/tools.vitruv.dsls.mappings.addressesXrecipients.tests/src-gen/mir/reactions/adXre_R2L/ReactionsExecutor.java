package mir.reactions.adXre_R2L;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
class ReactionsExecutor extends AbstractReactionsExecutor {
  public ReactionsExecutor() {
    super(new tools.vitruv.demo.domains.recipients.RecipientsDomainProvider().getDomain(), 
    	new tools.vitruv.demo.domains.addresses.AddressesDomainProvider().getDomain());
  }
  
  protected RoutinesFacadesProvider createRoutinesFacadesProvider() {
    return new mir.routines.adXre_R2L.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.adXre_R2L.AnyChangeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("adXre_R2L"))));
    this.addReaction(new mir.reactions.adXre_R2L.RecipientsCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("adXre_R2L"))));
    this.addReaction(new mir.reactions.adXre_R2L.RecipientCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("adXre_R2L"))));
    this.addReaction(new mir.reactions.adXre_R2L.LocationCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("adXre_R2L"))));
    this.addReaction(new mir.reactions.adXre_R2L.CityCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("adXre_R2L"))));
    this.addReaction(new mir.reactions.adXre_R2L.RecipientsDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("adXre_R2L"))));
    this.addReaction(new mir.reactions.adXre_R2L.RecipientDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("adXre_R2L"))));
    this.addReaction(new mir.reactions.adXre_R2L.LocationDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("adXre_R2L"))));
    this.addReaction(new mir.reactions.adXre_R2L.CityDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("adXre_R2L"))));
  }
}
