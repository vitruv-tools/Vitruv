package tools.vitruv.dsls.mappings.tests.addressesXrecipients

import edu.kit.ipd.sdq.metamodels.addresses.Address
import edu.kit.ipd.sdq.metamodels.addresses.Addresses
import edu.kit.ipd.sdq.metamodels.recipients.Recipient
import edu.kit.ipd.sdq.metamodels.recipients.RecipientsFactory
import mir.reactions.RecipientsToAddressesChangePropagationSpecification
import org.eclipse.emf.ecore.EObject
import org.junit.Test

import static org.junit.Assert.*

class AddressesXRecipientsR2LTest extends AddressesXRecipientsTest {
	
	override protected createChangePropagationSpecifications() {
		return #[new RecipientsToAddressesChangePropagationSpecification()]
	}
	
	@Test
	def void createRoot() {
		createAndSyncRoot()
	}
	
	private def createAndSyncRoot() {
		val root = RecipientsFactory.eINSTANCE.createRecipients()
		val path = getRecipientsModelPath(rootModelName)
		val otherRoot = syncAndAssertRoot(root, path, Addresses)
		return new Pair(root, otherRoot)
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
	
	private def Pair<Recipient,Address> createAndSyncChild() {
		val roots = createAndSyncRoot()
		val parent = roots.key
		val correspondingParent = roots.value
		val child = RecipientsFactory.eINSTANCE.createRecipient()
		parent.recipients.add(child)
		child.business = true
		// "initial recipient model" (see Table 7.5 in dx.doi.org/10.5445/IR/1000069284)
		
		saveAndAssertNoRecipientCorrespondences(child)
		val grandChild1 = RecipientsFactory.eINSTANCE.createLocation()
		child.locatedAt = grandChild1
		saveAndAssertNoGrandChildNorRecipientCorrespondences(grandChild1)
		grandChild1.number = number
		saveAndAssertNoGrandChildNorRecipientCorrespondences(grandChild1)	
		grandChild1.street = street
		// "recipient model after 1st change" (Table 7.5)
		
		saveAndAssertNoGrandChildNorRecipientCorrespondences(grandChild1)
		val grandChild2 = RecipientsFactory.eINSTANCE.createCity()
		child.locatedIn = grandChild2
		// "recipient model after 2nd change" (Table 7.5)
		
		saveAndAssertNoGrandChildNorRecipientCorrespondences(grandChild1)
		grandChild2.zipCode = zipCode
		// "recipient model after 3rd change" (Table 7.5)

		saveAndSynchronizeChanges(grandChild2)
		// check location
		val addressCorrespondingToRecipient = getCorrespondingChild(child, Address)?.get(0)
		val addressCorrespondingToLocation = getCorrespondingChild(grandChild1, Address)?.get(0)
		assertNotNull(addressCorrespondingToRecipient)
		assertEquals(addressCorrespondingToRecipient, addressCorrespondingToLocation)
		assertLocation(grandChild1, child, addressCorrespondingToLocation, correspondingParent)
		// check city
		val addressCorrespondingToCity = getCorrespondingChild(grandChild2, Address)?.get(0)
		assertEquals(addressCorrespondingToRecipient, addressCorrespondingToCity)
		assertCity(grandChild2, child, addressCorrespondingToCity, correspondingParent)
		
		return new Pair(child, addressCorrespondingToRecipient)
	}
	
	private def saveAndAssertNoRecipientCorrespondences(Recipient recipient) {
		saveAndSynchronizeChanges(recipient)
		assertNoRecipientCorrespondences(recipient)
	}
	
	private def void assertNoRecipientCorrespondences(Recipient recipient) {
		val correspondingAddresses = getCorrespondingChild(recipient, Address)
		assertTrue(correspondingAddresses.empty)
	}
	
	private def saveAndAssertNoGrandChildNorRecipientCorrespondences(EObject grandChild) {
		saveAndSynchronizeChanges(grandChild)
		val correspondingAddresses = getCorrespondingChild(grandChild, Address)
		assertTrue(correspondingAddresses.empty)
		assertNoRecipientCorrespondences(grandChild.eContainer as Recipient)
	}
	
	@Test
	def void createAndDeleteChild() {
		val pair = createAndSyncChild()
		val recipient = pair.key
		val address = pair.value
		deleteAndAssertCorrespondingDeletion(recipient, address)
	}
	
	@Test
	def void createAndDeleteGrandChild1() {
		val pair = createAndSyncChild()
		val location = pair.key.locatedAt
		val address = pair.value
		deleteAndAssertCorrespondingDeletion(location, address)
	}
	
	@Test
	def void createAndDeleteGrandChild2() {
		val pair = createAndSyncChild()
		val city = pair.key.locatedIn
		val address = pair.value		
		deleteAndAssertCorrespondingDeletion(city, address)
	}
	
	@Test 
	def void createAndModifyChildNumber() {
		val pair = createAndSyncChild()
		val recipient = pair.key
		val newNumber = number + number
		val location = recipient.locatedAt
		location.number = newNumber
		saveAndSynchronizeChanges(location)
		val address = pair.value
		assertEquals(address.number, newNumber)
		
		location.number = -number
		syncAndAssertCorrespondingDeletion(location, address)
	}
	
	@Test 
	def void createAndModifyChildZipCode() {
		val pair = createAndSyncChild()
		val recipient = pair.key
		val newZipCode = zipCode + zipCode
		val city = recipient.locatedIn
		city.zipCode = newZipCode
		saveAndSynchronizeChanges(city)
		val address = pair.value
		assertEquals(address.zipCode, newZipCode)
		
		city.zipCode = null
		syncAndAssertCorrespondingDeletion(city, address)
	}
}