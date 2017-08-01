package mir.reactions.reactionsAddressesToRecipients.ad2re;

import tools.vitruv.domains.addresses.AddressesDomainProvider;
import tools.vitruv.domains.recipients.RecipientsDomainProvider;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;

@SuppressWarnings("all")
public class ExecutorAddressesToRecipients extends AbstractReactionsExecutor {
  public ExecutorAddressesToRecipients() {
    super(new AddressesDomainProvider().getDomain(), 
    	new RecipientsDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addReaction(mir.reactions.reactionsAddressesToRecipients.ad2re.AnyChangeReaction.getExpectedChangeType(), new mir.reactions.reactionsAddressesToRecipients.ad2re.AnyChangeReaction());
    this.addReaction(mir.reactions.reactionsAddressesToRecipients.ad2re.AddressCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsAddressesToRecipients.ad2re.AddressCreatedReaction());
    this.addReaction(mir.reactions.reactionsAddressesToRecipients.ad2re.AddressDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsAddressesToRecipients.ad2re.AddressDeletedReaction());
  }
}
