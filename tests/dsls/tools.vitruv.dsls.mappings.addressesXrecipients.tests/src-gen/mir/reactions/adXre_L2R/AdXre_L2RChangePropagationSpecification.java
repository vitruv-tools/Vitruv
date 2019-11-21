package mir.reactions.adXre_L2R;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsChangePropagationSpecification;
import tools.vitruv.framework.change.processing.ChangePropagationSpecification;

@SuppressWarnings("all")
public class AdXre_L2RChangePropagationSpecification extends AbstractReactionsChangePropagationSpecification implements ChangePropagationSpecification {
  public AdXre_L2RChangePropagationSpecification() {
    super(new tools.vitruv.demo.domains.addresses.AddressesDomainProvider().getDomain(), 
    	new tools.vitruv.demo.domains.recipients.RecipientsDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addChangeMainprocessor(new mir.reactions.adXre_L2R.ReactionsExecutor());
  }
}
