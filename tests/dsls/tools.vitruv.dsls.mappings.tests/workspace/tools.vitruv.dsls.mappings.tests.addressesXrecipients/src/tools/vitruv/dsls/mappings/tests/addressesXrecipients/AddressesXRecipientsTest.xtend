package tools.vitruv.dsls.mappings.tests.addressesXrecipients

import mir.reactions.AddressesToRecipientsChangePropagationSpecification
import tools.vitruv.demo.domains.addresses.AddressesDomainProvider
import tools.vitruv.demo.domains.recipients.RecipientsDomainProvider
import tools.vitruv.framework.tests.VitruviusApplicationTest
import tools.vitruv.demo.domains.addresses.AddressesNamespace
import tools.vitruv.demo.domains.recipients.RecipientsNamespace
import org.eclipse.emf.ecore.EObject
import tools.vitruv.extensions.dslsruntime.reactions.helper.ReactionsCorrespondenceHelper

abstract class AddressesXRecipientsTest extends VitruviusApplicationTest {
	
	override protected cleanup() {
		// empty
	}
	
	override protected setup() {
		// empty
	}
	
	override protected createChangePropagationSpecifications() {
		return #[new AddressesToRecipientsChangePropagationSpecification()]
	}
	
	override protected getVitruvDomains() {
		return #[new AddressesDomainProvider().domain, new RecipientsDomainProvider().domain]
	}
	
	private def getModelFolder() '''model/'''

	def getAddressesModelPath(String fileName) {
		return modelFolder + fileName + AddressesNamespace.FILE_EXTENSION
	}
	
	def getRecipientsModelPath(String fileName) {
		return modelFolder + fileName + RecipientsNamespace.FILE_EXTENSION
	}
	
	// TODO MK: move getCorrespondingObjectsOfType to a new ReactionsTest super class
	def <T> Iterable<T> getCorrespondingObjectsOfType(EObject eObject, String tag, Class<T> clazz) {
		return ReactionsCorrespondenceHelper.getCorrespondingObjectsOfType(correspondenceModel, eObject, tag, clazz)
	}
}