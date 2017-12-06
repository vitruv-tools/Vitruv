package mir.reactions;

import tools.vitruv.framework.change.processing.ChangePropagationSpecification;
import tools.vitruv.framework.change.processing.impl.CompositeChangePropagationSpecification;
import java.util.List;
import java.util.function.Supplier;
import tools.vitruv.demo.domains.addresses.AddressesDomainProvider;
import java.util.Arrays;
import tools.vitruv.demo.domains.recipients.RecipientsDomainProvider;

/**
 * The {@link class tools.vitruv.framework.change.processing.impl.CompositeChangePropagationSpecification} for transformations between the metamodels Addresses and Recipients.
 * To add further change processors override the setup method.
 *
 * <p> This file is generated! Do not edit it but extend it by inheriting from it!
 * 
 * <p> Generated from template version 1
 */
public class AddressesToRecipientsChangePropagationSpecification extends CompositeChangePropagationSpecification {
	
	private final List<Supplier<? extends ChangePropagationSpecification>> executors = Arrays.asList(
		// begin generated executor list
		mir.reactions.reactionsAddressesToRecipients.adXre_L2R.ExecutorAddressesToRecipients::new
		// end generated executor list
	);
	
	public AddressesToRecipientsChangePropagationSpecification() {
		super(new AddressesDomainProvider().getDomain(), 
			new RecipientsDomainProvider().getDomain());
		setup();
	}
	
	/**
	 * Adds the reactions change processors to this {@link AddressesToRecipientsChangePropagationSpecification}.
	 * For adding further change processors overwrite this method and call the super method at the right place.
	 */
	protected void setup() {
		for (final Supplier<? extends ChangePropagationSpecification> executorSupplier : executors) {
			this.addChangeMainprocessor(executorSupplier.get());
		}	
	}
}
