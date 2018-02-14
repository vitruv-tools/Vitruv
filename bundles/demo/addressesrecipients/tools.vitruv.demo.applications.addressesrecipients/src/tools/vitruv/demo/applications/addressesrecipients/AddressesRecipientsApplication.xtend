package tools.vitruv.demo.applications.addressesrecipients

import tools.vitruv.framework.applications.AbstractVitruvApplication
import mir.reactions.reactionsAddressesToRecipients.adXre_L2R.ChangePropagationSpecificationAddressesToRecipients
import mir.reactions.reactionsRecipientsToAddresses.adXre_R2L.ChangePropagationSpecificationRecipientsToAddresses

class AddressesRecipientsApplication extends AbstractVitruvApplication {
	
	override getChangePropagationSpecifications() {
		return #{new ChangePropagationSpecificationAddressesToRecipients(), new ChangePropagationSpecificationRecipientsToAddresses()}
	}
	
	override getName() {
		return "AddressesXRecipients"
	}
	
}