package mir.reactions.adXre_L2R;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
class ReactionsExecutor extends AbstractReactionsExecutor {
  public ReactionsExecutor() {
    super(new tools.vitruv.demo.domains.addresses.AddressesDomainProvider().getDomain(), 
    	new tools.vitruv.demo.domains.recipients.RecipientsDomainProvider().getDomain());
  }
  
  protected RoutinesFacadesProvider createRoutinesFacadesProvider() {
    return new mir.routines.adXre_L2R.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.adXre_L2R.AnyChangeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("adXre_L2R"))));
    this.addReaction(new mir.reactions.adXre_L2R.AddressesCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("adXre_L2R"))));
    this.addReaction(new mir.reactions.adXre_L2R.AddressCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("adXre_L2R"))));
    this.addReaction(new mir.reactions.adXre_L2R.AddressesDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("adXre_L2R"))));
    this.addReaction(new mir.reactions.adXre_L2R.AddressDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("adXre_L2R"))));
  }
}
