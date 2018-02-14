package mir.reactions.reactionsRecipientsToAddresses.adXre_R2L;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsChangePropagationSpecification;

@SuppressWarnings("all")
public class ChangePropagationSpecificationRecipientsToAddresses extends AbstractReactionsChangePropagationSpecification {
  public ChangePropagationSpecificationRecipientsToAddresses() {
    super(new tools.vitruv.demo.domains.recipients.RecipientsDomainProvider().getDomain(), 
    	new tools.vitruv.demo.domains.addresses.AddressesDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addChangeMainprocessor(new mir.reactions.reactionsRecipientsToAddresses.adXre_R2L.ExecutorRecipientsToAddresses());
  }
}
