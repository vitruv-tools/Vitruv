package tools.vitruv.dsls.mappings.tests.addressesXrecipients

import edu.kit.ipd.sdq.mdsd.addresses.Address
import edu.kit.ipd.sdq.mdsd.addresses.Addresses
import edu.kit.ipd.sdq.mdsd.recipients.Location
import edu.kit.ipd.sdq.mdsd.recipients.Recipient
import edu.kit.ipd.sdq.mdsd.recipients.RecipientsFactory
import mir.reactions.RecipientsToAddressesChangePropagationSpecification
import org.junit.Test

import static org.junit.Assert.*
import edu.kit.ipd.sdq.mdsd.recipients.City
import org.eclipse.emf.ecore.EObject

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
		deleteAndAssertRoot(roots.key, roots.value)
	}
	
	@Test
	def void createChild() {
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
}