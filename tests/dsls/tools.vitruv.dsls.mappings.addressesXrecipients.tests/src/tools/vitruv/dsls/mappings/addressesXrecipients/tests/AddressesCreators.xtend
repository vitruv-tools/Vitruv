package tools.vitruv.dsls.mappings.addressesXrecipients.tests

import edu.kit.ipd.sdq.metamodels.addresses.AddressesFactory
import tools.vitruv.demo.domains.addresses.AddressesDomainProvider
import tools.vitruv.testutils.domains.DomainUtil
import tools.vitruv.testutils.activeannotations.ModelCreators

@ModelCreators(factory=AddressesFactory, staticCreators=true, prefix="new")
class AddressesCreators {
	static def addresses(String modelName) {
		DomainUtil.getModelFileName(modelName, new AddressesDomainProvider)
	}
}
