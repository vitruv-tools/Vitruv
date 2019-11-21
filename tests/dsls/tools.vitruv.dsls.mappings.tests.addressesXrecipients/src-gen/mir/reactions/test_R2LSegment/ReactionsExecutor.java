package mir.reactions.test_R2LSegment;

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
    return new mir.routines.test_R2LSegment.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.test_R2LSegment.OnAdRootXReRootRecipientsInsertedAsRootReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("test_R2LSegment"))));
    this.addReaction(new mir.reactions.test_R2LSegment.OnAdRootXReRootRecipientsDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("test_R2LSegment"))));
  }
}
