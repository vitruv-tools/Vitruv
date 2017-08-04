package tools.vitruv.applications.addressesrecipients

import mir.reactions.AddressesToRecipientsChangePropagationSpecification
import tools.vitruv.framework.applications.AbstractVitruvApplication

class AddressesRecipientsApplication extends AbstractVitruvApplication {
	
	override getChangePropagationSpecifications() {
		return #{new AddressesToRecipientsChangePropagationSpecification()}
	}
	
	override getName() {
		return "AddressesXRecipients"
	}
	
}