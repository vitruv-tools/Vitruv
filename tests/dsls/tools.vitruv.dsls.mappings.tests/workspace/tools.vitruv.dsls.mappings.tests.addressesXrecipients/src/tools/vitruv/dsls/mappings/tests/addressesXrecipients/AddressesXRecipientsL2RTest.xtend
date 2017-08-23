package tools.vitruv.dsls.mappings.tests.addressesXrecipients

import edu.kit.ipd.sdq.mdsd.addresses.AddressesFactory
import edu.kit.ipd.sdq.mdsd.recipients.Recipients
import org.junit.Test
import static org.junit.Assert.*
import mir.reactions.AddressesToRecipientsChangePropagationSpecification

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
		val otherRoot = syncAndAssertRoot(root, path, Recipients)
		return new Pair(root,otherRoot)
	}
	
	@Test
	def void createAndDeleteRoot() {
		val roots = createAndSyncRoot()
		deleteAndAssertRoot(roots.key, roots.value)
	}
}