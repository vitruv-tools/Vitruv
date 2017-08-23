package tools.vitruv.dsls.mappings.tests.addressesXrecipients

import edu.kit.ipd.sdq.mdsd.addresses.AddressesFactory
import edu.kit.ipd.sdq.mdsd.recipients.Recipients
import org.junit.Test
import static org.junit.Assert.*
import mir.reactions.AddressesToRecipientsChangePropagationSpecification
import edu.kit.ipd.sdq.mdsd.recipients.Recipient
import edu.kit.ipd.sdq.mdsd.addresses.Address
import edu.kit.ipd.sdq.mdsd.recipients.Location
import edu.kit.ipd.sdq.mdsd.recipients.City

class AddressesXRecipientsL2RTest extends AddressesXRecipientsTest {
	
	override protected createChangePropagationSpecifications() {
		return #[new AddressesToRecipientsChangePropagationSpecification()]
	}
	
	@Test
	def void createRoot() {
		createAndSyncRoot()
	}
	
	private def createAndSyncRoot() {
		val root = AddressesFactory.eINSTANCE.createAddresses()
		val path = getAddressesModelPath(rootModelName)
		val correspondingRoot = syncAndAssertRoot(root, path, Recipients)
		return new Pair(root, correspondingRoot)
	}
	
	@Test
	def void createAndDeleteRoot() {
		val roots = createAndSyncRoot()
		deleteAndAssertRoot(roots.key, roots.value)
	}
		
	@Test
	def void createChild() {
		val roots = createAndSyncRoot()
		val parent = roots.key
		val correspondingParent = roots.value
		val child = AddressesFactory.eINSTANCE.createAddress()
		parent.addresses.add(child)
		saveAndAssertNoCorrespondences(child)
		child.number = 42
		saveAndAssertNoCorrespondences(child)	
		child.street = "Page St"
		saveAndAssertNoCorrespondences(child)
		child.zipCode = "SW1P4EN"
		saveAndSynchronizeChanges(child)
		// RECIPIENT
		val correspondingRecipient = getCorresponding(child, Recipient)?.get(0)
		// check existence
		assertNotNull(correspondingRecipient)
		// check containment
		assertEquals(correspondingRecipient, correspondingParent.recipients?.get(0))
		// check features
		assertTrue(correspondingRecipient.business)
		// LOCATION
		val correspondingLocation = getCorresponding(child, Location)?.get(0)
		// check existence
		assertNotNull(correspondingLocation)
		// check containment
		assertEquals(correspondingLocation, correspondingRecipient.locatedAt)
		// check features
		assertEquals(child.number, correspondingLocation.number)
		assertEquals(child.street, correspondingLocation.street)
		// CITY
		val correspondingCity = getCorresponding(child, City)?.get(0)
		// check existence
		assertNotNull(correspondingCity)
		// check containment
		assertEquals(correspondingCity, correspondingRecipient.locatedIn)
		// check features
		assertEquals(child.zipCode, correspondingCity.zipCode)
	}
	
	private def saveAndAssertNoCorrespondences(Address address) {
		saveAndSynchronizeChanges(address)
		assertNoCorrespondences(address)
	}
	
	private def assertNoCorrespondences(Address address) {
		val correspondingRecipients = getCorresponding(address, Recipient)
		assertTrue(correspondingRecipients.empty)
		val correspondingLocations = getCorresponding(address, Location)
		assertTrue(correspondingLocations.empty)
		val correspondingCities = getCorresponding(address, City)
		assertTrue(correspondingCities.empty)
	}
	
	private def <T> getCorresponding(Address address, Class<T> clazz) {
		return getCorrespondingObjectsOfType(address, childMappingName, clazz)
	}

}