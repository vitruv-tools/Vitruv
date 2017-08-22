package tools.vitruv.dsls.mappings.tests.addressesXrecipients

import edu.kit.ipd.sdq.mdsd.addresses.AddressesFactory
import edu.kit.ipd.sdq.mdsd.recipients.Recipients
import org.junit.Test
import static org.junit.Assert.*

class AddressesXRecipientsL2RTest extends AddressesXRecipientsTest {
	val rootMappingName = "AdRootXReRoot"
	
	@Test
	def createRoot() {
		val root = AddressesFactory.eINSTANCE.createAddresses()
		createAndSynchronizeModel("a",root)
		val otherRoot = getCorrespondingObjectsOfType(root, rootMappingName, Recipients)
		assertNotNull(otherRoot)
	}
}