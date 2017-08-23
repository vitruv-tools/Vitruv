package tools.vitruv.dsls.mappings.tests.addressesXrecipients

import org.junit.Test
import edu.kit.ipd.sdq.mdsd.recipients.RecipientsFactory
import edu.kit.ipd.sdq.mdsd.addresses.Addresses
import static org.junit.Assert.*
import mir.reactions.RecipientsToAddressesChangePropagationSpecification

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
}