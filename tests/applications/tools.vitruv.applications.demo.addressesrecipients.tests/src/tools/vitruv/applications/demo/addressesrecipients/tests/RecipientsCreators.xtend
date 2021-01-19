package tools.vitruv.applications.demo.addressesrecipients.tests

import edu.kit.ipd.sdq.metamodels.recipients.RecipientsFactory
import tools.vitruv.domains.demo.recipients.RecipientsDomainProvider
import tools.vitruv.testutils.domains.DomainUtil
import tools.vitruv.testutils.activeannotations.ModelCreators

@ModelCreators(factory=RecipientsFactory, staticCreators=true, prefix="new")
class RecipientsCreators {
	static def recipients(String modelName) {
		DomainUtil.getModelFileName(modelName, new RecipientsDomainProvider)
	}
}
