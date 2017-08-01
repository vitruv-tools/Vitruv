package mir.reactions;

import tools.vitruv.domains.recipients.RecipientsDomainProvider;
import tools.vitruv.framework.change.processing.impl.CompositeChangePropagationSpecification;
import tools.vitruv.domains.addresses.AddressesDomainProvider;

/**
 * The {@link class tools.vitruv.framework.change.processing.impl.CompositeChangePropagationSpecification} for transformations between the metamodels Addresses and Recipients.
 * To add further change processors overwrite the setup method.
 */
public abstract class AbstractChangePropagationSpecificationAddressesToRecipients extends CompositeChangePropagationSpecification {
	public AbstractChangePropagationSpecificationAddressesToRecipients() {
		super(new AddressesDomainProvider().getDomain(), 
			new RecipientsDomainProvider().getDomain());
		setup();
	}
	
	/**
	 * Adds the reactions change processors to this {@link AbstractChangePropagationSpecificationAddressesToRecipients}.
	 * For adding further change processors overwrite this method and call the super method at the right place.
	 */
	protected void setup() {
		this.addChangeMainprocessor(new mir.reactions.reactionsAddressesToRecipients.ad2re.ExecutorAddressesToRecipients());
	}
	
}
