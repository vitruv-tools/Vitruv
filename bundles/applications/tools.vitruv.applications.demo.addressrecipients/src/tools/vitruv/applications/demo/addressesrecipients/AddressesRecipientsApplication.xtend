package tools.vitruv.applications.demo.addressesrecipients

import tools.vitruv.framework.applications.AbstractVitruvApplication
import tools.vitruv.applications.demo.addressesrecipients.transformations.AddressesToRecipientsChangePropagationSpecification
import tools.vitruv.applications.demo.addressesrecipients.transformations.RecipientsToAddressesChangePropagationSpecification

class AddressesRecipientsApplication extends AbstractVitruvApplication {
	
	override getChangePropagationSpecifications() {
		return #{new AddressesToRecipientsChangePropagationSpecification(), new RecipientsToAddressesChangePropagationSpecification()}
	}
	
	override getName() {
		return "Addresses-Recipients"
	}
	
}