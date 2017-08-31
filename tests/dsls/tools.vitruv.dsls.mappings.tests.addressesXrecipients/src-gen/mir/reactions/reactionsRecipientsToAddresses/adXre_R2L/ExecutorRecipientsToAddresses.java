package mir.reactions.reactionsRecipientsToAddresses.adXre_R2L;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;

@SuppressWarnings("all")
public class ExecutorRecipientsToAddresses extends AbstractReactionsExecutor {
  public ExecutorRecipientsToAddresses() {
    super(new tools.vitruv.demo.domains.recipients.RecipientsDomainProvider().getDomain(), 
    	new tools.vitruv.demo.domains.addresses.AddressesDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addReaction(mir.reactions.reactionsRecipientsToAddresses.adXre_R2L.AnyChangeReaction.getExpectedChangeType(), new mir.reactions.reactionsRecipientsToAddresses.adXre_R2L.AnyChangeReaction());
    this.addReaction(mir.reactions.reactionsRecipientsToAddresses.adXre_R2L.RecipientsCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsRecipientsToAddresses.adXre_R2L.RecipientsCreatedReaction());
    this.addReaction(mir.reactions.reactionsRecipientsToAddresses.adXre_R2L.RecipientCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsRecipientsToAddresses.adXre_R2L.RecipientCreatedReaction());
    this.addReaction(mir.reactions.reactionsRecipientsToAddresses.adXre_R2L.LocationCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsRecipientsToAddresses.adXre_R2L.LocationCreatedReaction());
    this.addReaction(mir.reactions.reactionsRecipientsToAddresses.adXre_R2L.CityCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsRecipientsToAddresses.adXre_R2L.CityCreatedReaction());
    this.addReaction(mir.reactions.reactionsRecipientsToAddresses.adXre_R2L.RecipientsDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsRecipientsToAddresses.adXre_R2L.RecipientsDeletedReaction());
    this.addReaction(mir.reactions.reactionsRecipientsToAddresses.adXre_R2L.RecipientDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsRecipientsToAddresses.adXre_R2L.RecipientDeletedReaction());
    this.addReaction(mir.reactions.reactionsRecipientsToAddresses.adXre_R2L.LocationDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsRecipientsToAddresses.adXre_R2L.LocationDeletedReaction());
    this.addReaction(mir.reactions.reactionsRecipientsToAddresses.adXre_R2L.CityDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsRecipientsToAddresses.adXre_R2L.CityDeletedReaction());
  }
}
