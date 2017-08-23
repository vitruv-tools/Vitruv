package tools.vitruv.dsls.mappings.tests.addressesXrecipients

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.demo.domains.addresses.AddressesDomainProvider
import tools.vitruv.demo.domains.addresses.AddressesNamespace
import tools.vitruv.demo.domains.recipients.RecipientsDomainProvider
import tools.vitruv.demo.domains.recipients.RecipientsNamespace
import tools.vitruv.extensions.dslsruntime.reactions.helper.ReactionsCorrespondenceHelper
import tools.vitruv.framework.tests.VitruviusApplicationTest

import static org.junit.Assert.*

abstract class AddressesXRecipientsTest extends VitruviusApplicationTest {
	val modelFolder = "model/"
	
	override protected cleanup() {
		// empty
	}
	
	override protected setup() {
		// empty
	}
	
	override protected getVitruvDomains() {
		return #[new AddressesDomainProvider().domain, new RecipientsDomainProvider().domain]
	}

	def String getAddressesModelPath(String fileName) {
		return modelFolder + fileName + "." + AddressesNamespace.FILE_EXTENSION
	}
	
	def String getRecipientsModelPath(String fileName) {
		return modelFolder + fileName + "." + RecipientsNamespace.FILE_EXTENSION
	}
	
	def String getRootMappingName() '''AdRootXReRootMapping'''
	
	def String getChildMappingName() '''AddressXRecipientLocationCityMapping'''
	
	def String getRootModelName() '''rootModel'''
	
	// TODO MK: move getCorrespondingObjectsOfType to a new ReactionsTest super class
	def <T> Iterable<T> getCorrespondingObjectsOfType(EObject eObject, String tag, Class<T> clazz) {
		return ReactionsCorrespondenceHelper.getCorrespondingObjectsOfType(correspondenceModel, eObject, tag, clazz)
	}
	
	def <T> T syncAndAssertRoot(EObject root, String path, Class<T> typeOfCorrespondingRoot) {
		createAndSynchronizeModel(path, root)
		val correspondingRoot = getCorrespondingObjectsOfType(root, rootMappingName, typeOfCorrespondingRoot)?.get(0)
		assertNotNull(correspondingRoot)
		return correspondingRoot
	}
	
	def void deleteAndAssertRoot(EObject root, EObject correspondingRoot) {
		val resource = root.eResource
		val correspondingResource = correspondingRoot.eResource
		assertNotNull(correspondingResource)
		EcoreUtil.remove(root)
		saveAndSynchronizeChanges(resource)
		assertNull(correspondingRoot.eResource)
	}
}