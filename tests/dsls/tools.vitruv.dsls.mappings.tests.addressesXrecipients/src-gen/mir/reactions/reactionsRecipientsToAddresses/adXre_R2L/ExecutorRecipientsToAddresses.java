package mir.reactions.reactionsRecipientsToAddresses.adXre_R2L;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;

@SuppressWarnings("all")
public class ExecutorRecipientsToAddresses extends AbstractReactionsExecutor {
  public ExecutorRecipientsToAddresses() {
    super(new tools.vitruv.demo.domains.recipients.RecipientsDomainProvider().getDomain(), 
    	new tools.vitruv.demo.domains.addresses.AddressesDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.reactionsRecipientsToAddresses.adXre_R2L.AnyChangeReaction());
    this.addReaction(new mir.reactions.reactionsRecipientsToAddresses.adXre_R2L.RecipientsCreatedReaction());
    this.addReaction(new mir.reactions.reactionsRecipientsToAddresses.adXre_R2L.RecipientCreatedReaction());
    this.addReaction(new mir.reactions.reactionsRecipientsToAddresses.adXre_R2L.LocationCreatedReaction());
    this.addReaction(new mir.reactions.reactionsRecipientsToAddresses.adXre_R2L.CityCreatedReaction());
    this.addReaction(new mir.reactions.reactionsRecipientsToAddresses.adXre_R2L.RecipientsDeletedReaction());
    this.addReaction(new mir.reactions.reactionsRecipientsToAddresses.adXre_R2L.RecipientDeletedReaction());
    this.addReaction(new mir.reactions.reactionsRecipientsToAddresses.adXre_R2L.LocationDeletedReaction());
    this.addReaction(new mir.reactions.reactionsRecipientsToAddresses.adXre_R2L.CityDeletedReaction());
  }
}
