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
import edu.kit.ipd.sdq.mdsd.recipients.Recipient
import edu.kit.ipd.sdq.mdsd.recipients.Recipients
import edu.kit.ipd.sdq.mdsd.recipients.Location
import edu.kit.ipd.sdq.mdsd.addresses.Address
import edu.kit.ipd.sdq.mdsd.recipients.City
import edu.kit.ipd.sdq.mdsd.addresses.Addresses

abstract class AddressesXRecipientsTest extends VitruviusApplicationTest {
	override protected cleanup() {
		// empty
	}
	
	override protected setup() {
		// empty
	}
	
	override protected getVitruvDomains() {
		return #[new AddressesDomainProvider().domain, new RecipientsDomainProvider().domain]
	}

	protected def String getAddressesModelPath(String fileName) {
		return modelFolder + fileName + "." + AddressesNamespace.FILE_EXTENSION
	}
	
	protected def String getRecipientsModelPath(String fileName) {
		return modelFolder + fileName + "." + RecipientsNamespace.FILE_EXTENSION
	}
	
	protected def String getModelFolder() '''model'''
	protected def String getRootModelName() '''rootModel'''
	
	protected def String getRootMappingName() '''AdRootXReRootMapping'''
	protected def String getChildMappingName() '''AddressXRecipientLocationCityMapping'''
	
	protected def int getNumber() { 42	}
	protected def String getStreet() '''Page St'''
	protected def String getZipCode() '''SW1P4EN'''
	
	// TODO MK: move getCorrespondingObjectsOfType to a new ReactionsTest super class
	protected def <T> Iterable<T> getCorrespondingObjectsOfType(EObject eObject, String tag, Class<T> clazz) {
		return ReactionsCorrespondenceHelper.getCorrespondingObjectsOfType(correspondenceModel, eObject, tag, clazz)
	}
	
	protected def <T> T syncAndAssertRoot(EObject root, String path, Class<T> typeOfCorrespondingRoot) {
		createAndSynchronizeModel(path, root)
		val correspondingRoot = getCorrespondingObjectsOfType(root, rootMappingName, typeOfCorrespondingRoot)?.get(0)
		assertNotNull(correspondingRoot)
		return correspondingRoot
	}
	
	protected def void deleteAndAssertRoot(EObject root, EObject correspondingRoot) {
		val resource = root.eResource
		val correspondingResource = correspondingRoot.eResource
		assertNotNull(correspondingResource)
		EcoreUtil.remove(root)
		saveAndSynchronizeChanges(resource)
		assertNull(correspondingRoot.eResource)
	}
	
	protected def <T> getCorrespondingChild(EObject object, Class<T> clazz) {
		return getCorrespondingObjectsOfType(object, childMappingName, clazz)
	}
	
	protected def void assertAddress(Address address, Addresses supposedParent) {
		// check containment
		assertTrue(supposedParent.addresses?.contains(address))
		// no features to check
	}
	
	protected def void assertRecipient(Recipient recipient, Recipients supposedParent) {
		// check containment
		assertTrue(supposedParent.recipients?.contains(recipient))
		// check features
		assertTrue(recipient.business)
	}
	
	protected def void assertLocation(Location location, Recipient supposedLocationParent, Address address, Addresses supposedAddressParent) {
		// check containment
		assertAddress(address, supposedAddressParent)
		assertEquals(location, supposedLocationParent.locatedAt)
		// check features
		assertEquals(address.number, location.number)
		assertEquals(address.street, location.street)
	}
	
	protected def void assertCity(City city, Recipient supposedCityParent, Address address, Addresses supposedAddressParent) {
		// check containment
		assertAddress(address, supposedAddressParent)
		assertEquals(city, supposedCityParent.locatedIn)
		// check features
		assertEquals(address.zipCode, city.zipCode)
	}
}