package tools.vitruv.applications.demo.addressesrecipients.tests

import edu.kit.ipd.sdq.metamodels.addresses.AddressesFactory
import tools.vitruv.domains.demo.addresses.AddressesDomainProvider
import tools.vitruv.testutils.domains.DomainUtil
import tools.vitruv.testutils.activeannotations.ModelCreators

@ModelCreators(factory=AddressesFactory, staticCreators=true, prefix="new")
class AddressesCreators {
	static def addresses(String modelName) {
		DomainUtil.getModelFileName(modelName, new AddressesDomainProvider)
	}
}
