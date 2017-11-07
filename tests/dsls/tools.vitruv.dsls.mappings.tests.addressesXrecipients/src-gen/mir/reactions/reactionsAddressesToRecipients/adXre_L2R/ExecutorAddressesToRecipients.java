package mir.reactions.reactionsAddressesToRecipients.adXre_L2R;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;

@SuppressWarnings("all")
public class ExecutorAddressesToRecipients extends AbstractReactionsExecutor {
  public ExecutorAddressesToRecipients() {
    super(new tools.vitruv.demo.domains.addresses.AddressesDomainProvider().getDomain(), 
    	new tools.vitruv.demo.domains.recipients.RecipientsDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.reactionsAddressesToRecipients.adXre_L2R.AnyChangeReaction());
    this.addReaction(new mir.reactions.reactionsAddressesToRecipients.adXre_L2R.AddressesCreatedReaction());
    this.addReaction(new mir.reactions.reactionsAddressesToRecipients.adXre_L2R.AddressCreatedReaction());
    this.addReaction(new mir.reactions.reactionsAddressesToRecipients.adXre_L2R.AddressesDeletedReaction());
    this.addReaction(new mir.reactions.reactionsAddressesToRecipients.adXre_L2R.AddressDeletedReaction());
  }
}
