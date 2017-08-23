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
	
	def String getRootMappingName() '''AdRootXReRoot'''
	
	def String getChildMappingName() '''AddressXRecipientLocationCity'''
	
	def String getRootModelName() '''rootModel'''
	
	// TODO MK: move getCorrespondingObjectsOfType to a new ReactionsTest super class
	def <T> Iterable<T> getCorrespondingObjectsOfType(EObject eObject, String tag, Class<T> clazz) {
		return ReactionsCorrespondenceHelper.getCorrespondingObjectsOfType(correspondenceModel, eObject, tag, clazz)
	}
	
	def <T> T syncAndAssertRoot(EObject root, String path, Class<T> typeOfOtherRoot) {
		createAndSynchronizeModel(path, root)
		val otherRoot = getCorrespondingObjectsOfType(root, rootMappingName, typeOfOtherRoot)?.get(0)
		assertNotNull(otherRoot)
		return otherRoot
	}
	
	def void deleteAndAssertRoot(EObject root, EObject otherRoot) {
		val resource = root.eResource
		val otherResource = otherRoot.eResource
		assertNotNull(otherResource)
		EcoreUtil.remove(root)
		saveAndSynchronizeChanges(resource)
		assertNull(otherRoot.eResource)
	}
}