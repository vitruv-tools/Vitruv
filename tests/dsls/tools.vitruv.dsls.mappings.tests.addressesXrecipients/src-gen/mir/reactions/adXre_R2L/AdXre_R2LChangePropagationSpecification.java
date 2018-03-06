package mir.reactions.adXre_R2L;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsChangePropagationSpecification;
import tools.vitruv.framework.change.processing.ChangePropagationSpecification;

@SuppressWarnings("all")
public class AdXre_R2LChangePropagationSpecification extends AbstractReactionsChangePropagationSpecification implements ChangePropagationSpecification {
  public AdXre_R2LChangePropagationSpecification() {
    super(new tools.vitruv.demo.domains.recipients.RecipientsDomainProvider().getDomain(), 
    	new tools.vitruv.demo.domains.addresses.AddressesDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addChangeMainprocessor(new mir.reactions.adXre_R2L.ReactionsExecutor());
  }
}
