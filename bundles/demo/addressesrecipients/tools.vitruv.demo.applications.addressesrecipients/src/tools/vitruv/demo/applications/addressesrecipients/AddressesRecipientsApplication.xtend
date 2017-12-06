package tools.vitruv.demo.applications.addressesrecipients

import mir.reactions.AddressesToRecipientsChangePropagationSpecification
import tools.vitruv.framework.applications.AbstractVitruvApplication
import mir.reactions.RecipientsToAddressesChangePropagationSpecification

class AddressesRecipientsApplication extends AbstractVitruvApplication {
	
	override getChangePropagationSpecifications() {
		return #{new AddressesToRecipientsChangePropagationSpecification(), new RecipientsToAddressesChangePropagationSpecification()}
	}
	
	override getName() {
		return "AddressesXRecipients"
	}
	
}