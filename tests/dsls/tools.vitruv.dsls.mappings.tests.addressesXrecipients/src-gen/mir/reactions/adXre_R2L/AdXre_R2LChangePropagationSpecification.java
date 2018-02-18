package mir.reactions.adXre_R2L;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsChangePropagationSpecification;

@SuppressWarnings("all")
public class AdXre_R2LChangePropagationSpecification extends AbstractReactionsChangePropagationSpecification {
  public AdXre_R2LChangePropagationSpecification() {
    super(new tools.vitruv.demo.domains.recipients.RecipientsDomainProvider().getDomain(), 
    	new tools.vitruv.demo.domains.addresses.AddressesDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addChangeMainprocessor(new mir.reactions.adXre_R2L.ReactionsExecutor());
  }
}
