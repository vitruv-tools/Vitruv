package mir.reactions.reactionsAddressesToRecipients.adXre_L2R;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;

@SuppressWarnings("all")
public class ExecutorAddressesToRecipients extends AbstractReactionsExecutor {
  public ExecutorAddressesToRecipients() {
    super(new tools.vitruv.demo.domains.addresses.AddressesDomainProvider().getDomain(), 
    	new tools.vitruv.demo.domains.recipients.RecipientsDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addReaction(mir.reactions.reactionsAddressesToRecipients.adXre_L2R.AnyChangeReaction.getExpectedChangeType(), new mir.reactions.reactionsAddressesToRecipients.adXre_L2R.AnyChangeReaction());
    this.addReaction(mir.reactions.reactionsAddressesToRecipients.adXre_L2R.AddressesCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsAddressesToRecipients.adXre_L2R.AddressesCreatedReaction());
    this.addReaction(mir.reactions.reactionsAddressesToRecipients.adXre_L2R.AddressCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsAddressesToRecipients.adXre_L2R.AddressCreatedReaction());
    this.addReaction(mir.reactions.reactionsAddressesToRecipients.adXre_L2R.AddressesDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsAddressesToRecipients.adXre_L2R.AddressesDeletedReaction());
    this.addReaction(mir.reactions.reactionsAddressesToRecipients.adXre_L2R.AddressDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsAddressesToRecipients.adXre_L2R.AddressDeletedReaction());
  }
}
