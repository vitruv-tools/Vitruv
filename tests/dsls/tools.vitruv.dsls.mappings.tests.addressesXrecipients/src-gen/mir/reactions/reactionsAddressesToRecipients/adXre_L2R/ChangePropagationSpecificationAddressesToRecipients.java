package mir.reactions.reactionsAddressesToRecipients.adXre_L2R;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsChangePropagationSpecification;

@SuppressWarnings("all")
public class ChangePropagationSpecificationAddressesToRecipients extends AbstractReactionsChangePropagationSpecification {
  public ChangePropagationSpecificationAddressesToRecipients() {
    super(new tools.vitruv.demo.domains.addresses.AddressesDomainProvider().getDomain(), 
    	new tools.vitruv.demo.domains.recipients.RecipientsDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addChangeMainprocessor(new mir.reactions.reactionsAddressesToRecipients.adXre_L2R.ExecutorAddressesToRecipients());
  }
}
