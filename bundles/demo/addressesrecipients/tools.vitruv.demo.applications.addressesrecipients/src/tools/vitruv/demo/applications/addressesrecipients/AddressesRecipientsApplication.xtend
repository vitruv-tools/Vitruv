package tools.vitruv.demo.applications.addressesrecipients

import tools.vitruv.framework.applications.AbstractVitruvApplication
import tools.vitruv.demo.applications.addressesrecipients.transformations.AddressesToRecipientsChangePropagationSpecification
import tools.vitruv.demo.applications.addressesrecipients.transformations.RecipientsToAddressesChangePropagationSpecification

class AddressesRecipientsApplication extends AbstractVitruvApplication {
	
	override getChangePropagationSpecifications() {
		return #{new AddressesToRecipientsChangePropagationSpecification(), new RecipientsToAddressesChangePropagationSpecification()}
	}
	
	override getName() {
		return "Addresses-Recipients"
	}
	
}