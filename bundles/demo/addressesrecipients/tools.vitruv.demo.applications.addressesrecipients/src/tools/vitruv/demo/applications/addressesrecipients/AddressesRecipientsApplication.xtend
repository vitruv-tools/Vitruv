package tools.vitruv.demo.applications.addressesrecipients

import tools.vitruv.framework.applications.AbstractVitruvApplication
import mir.reactions.adXre_L2R.AdXre_L2RChangePropagationSpecification
import mir.reactions.adXre_R2L.AdXre_R2LChangePropagationSpecification

class AddressesRecipientsApplication extends AbstractVitruvApplication {
	
	override getChangePropagationSpecifications() {
		return #{new AdXre_L2RChangePropagationSpecification(), new AdXre_R2LChangePropagationSpecification()}
	}
	
	override getName() {
		return "AddressesXRecipients"
	}
	
}