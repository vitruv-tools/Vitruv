package tools.vitruv.dsls.mappings.addressesXrecipients.tests

import edu.kit.ipd.sdq.activextendannotations.Utility
import edu.kit.ipd.sdq.metamodels.addresses.AddressesFactory
import tools.vitruv.testutils.util.DomainUtil
import tools.vitruv.demo.domains.addresses.AddressesDomainProvider

@Utility
class AddressesCreator {
	static def addresses(String modelName) {
		DomainUtil.getModelFileName(modelName, new AddressesDomainProvider)
	}

	static def newAddresses() {
		AddressesFactory.eINSTANCE.createAddresses
	}

	static def newAddress() {
		AddressesFactory.eINSTANCE.createAddress
	}
}
