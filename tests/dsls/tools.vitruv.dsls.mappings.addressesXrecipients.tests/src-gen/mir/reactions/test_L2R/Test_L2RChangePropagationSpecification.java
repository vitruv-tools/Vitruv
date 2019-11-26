package mir.reactions.test_L2R;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsChangePropagationSpecification;
import tools.vitruv.framework.change.processing.ChangePropagationSpecification;

@SuppressWarnings("all")
public class Test_L2RChangePropagationSpecification extends AbstractReactionsChangePropagationSpecification implements ChangePropagationSpecification {
  public Test_L2RChangePropagationSpecification() {
    super(new tools.vitruv.demo.domains.addresses.AddressesDomainProvider().getDomain(), 
    	new tools.vitruv.demo.domains.recipients.RecipientsDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addChangeMainprocessor(new mir.reactions.test_L2R.ReactionsExecutor());
  }
}
