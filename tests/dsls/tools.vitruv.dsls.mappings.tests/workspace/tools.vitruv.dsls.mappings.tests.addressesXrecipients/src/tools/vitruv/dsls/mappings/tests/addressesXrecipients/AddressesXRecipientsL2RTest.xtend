package tools.vitruv.dsls.mappings.tests.addressesXrecipients

import edu.kit.ipd.sdq.mdsd.addresses.Address
import edu.kit.ipd.sdq.mdsd.addresses.AddressesFactory
import edu.kit.ipd.sdq.mdsd.recipients.City
import edu.kit.ipd.sdq.mdsd.recipients.Location
import edu.kit.ipd.sdq.mdsd.recipients.Recipient
import edu.kit.ipd.sdq.mdsd.recipients.Recipients
import mir.reactions.AddressesToRecipientsChangePropagationSpecification
import org.junit.Test

import static org.junit.Assert.*

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
		// "initial address model" (see Table 7.4 in dx.doi.org/10.5445/IR/1000069284)
		
		saveAndAssertNoAddressCorrespondences(child)
		child.number = number
		saveAndAssertNoAddressCorrespondences(child)	
		child.street = street
		// "address model after 1st change" (Table 7.4)
		
		saveAndAssertNoAddressCorrespondences(child)
		child.zipCode = zipCode
		// "address model after 2nd change" (Table 7.4)
		
		saveAndSynchronizeChanges(child)
		// check recipient
		val correspondingRecipient = getCorrespondingChild(child, Recipient)?.get(0)
		assertNotNull(correspondingRecipient)
		assertRecipient(correspondingRecipient, correspondingParent)
		// check location
		val correspondingLocation = getCorrespondingChild(child, Location)?.get(0)
		assertNotNull(correspondingLocation)
		assertLocation(correspondingLocation, correspondingRecipient, child, parent)
		// check city
		val correspondingCity = getCorrespondingChild(child, City)?.get(0)
		assertNotNull(correspondingCity)
		assertCity(correspondingCity, correspondingRecipient, child, parent)
	}
	
	private def saveAndAssertNoAddressCorrespondences(Address address) {
		saveAndSynchronizeChanges(address)
		assertNoAddressCorrespondences(address)
	}
	
	private def assertNoAddressCorrespondences(Address address) {
		val correspondingRecipients = getCorrespondingChild(address, Recipient)
		assertTrue(correspondingRecipients.empty)
		val correspondingLocations = getCorrespondingChild(address, Location)
		assertTrue(correspondingLocations.empty)
		val correspondingCities = getCorrespondingChild(address, City)
		assertTrue(correspondingCities.empty)
	}
}