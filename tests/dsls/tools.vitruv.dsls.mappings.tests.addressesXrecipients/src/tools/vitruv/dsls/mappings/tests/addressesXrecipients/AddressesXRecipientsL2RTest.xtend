package tools.vitruv.dsls.mappings.tests.addressesXrecipients

import edu.kit.ipd.sdq.metamodels.addresses.Address
import edu.kit.ipd.sdq.metamodels.addresses.AddressesFactory
import edu.kit.ipd.sdq.metamodels.recipients.City
import edu.kit.ipd.sdq.metamodels.recipients.Location
import edu.kit.ipd.sdq.metamodels.recipients.Recipient
import edu.kit.ipd.sdq.metamodels.recipients.Recipients
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
		deleteAndAssertCorrespondingDeletion(roots.key, roots.value)
	}
		
	@Test
	def void createChild() {
		createAndSyncChild()
	}
	
	private def Pair<Address,Recipient> createAndSyncChild() {
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
		assertChild(child, parent, correspondingParent)
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
	
	@Test
	def void createAndDeleteChild() {
		val pair = createAndSyncChild()
		val address = pair.key
		val recipient = pair.value
		val location = recipient.locatedAt
		val city = recipient.locatedIn
		deleteAndAssertCorrespondingDeletion(address, recipient, location, city)
	}
	
	@Test 
	def void createAndModifyChildNumber() {
		val pair = createAndSyncChild()
		val address = pair.key
		val newNumber = number + number
		address.number = newNumber
		saveAndSynchronizeChanges(address)
		val recipient = pair.value
		val location = recipient.locatedAt
		assertChild(address, address.parent, recipient.parent)
		assertEquals(location.number, newNumber)
		
		address.number = -number
		val city = recipient.locatedIn
		syncAndAssertCorrespondingDeletion(address, recipient, location, city)
	}
	
	@Test 
	def void createAndModifyChildZipCode() {
		val pair = createAndSyncChild()
		val address = pair.key
		val newZipCode = zipCode + zipCode
		address.zipCode = newZipCode
		saveAndSynchronizeChanges(address)
		val recipient = pair.value
		val city = recipient.locatedIn
		assertChild(address, address.parent, recipient.parent)
		assertEquals(city.zipCode, newZipCode)
		
		address.zipCode = null
		val location = recipient.locatedAt
		syncAndAssertCorrespondingDeletion(address, recipient, location, city)
	}
}