package tools.vitruv.dsls.mappings.addressesXrecipients.tests

import edu.kit.ipd.sdq.activextendannotations.Utility
import edu.kit.ipd.sdq.metamodels.recipients.RecipientsFactory
import tools.vitruv.demo.domains.recipients.RecipientsDomainProvider
import tools.vitruv.testutils.domains.DomainUtil
import tools.vitruv.testutils.activeannotations.ModelCreators

@Utility
@ModelCreators(factory=RecipientsFactory, staticCreators=true, prefix="new")
class RecipientsCreators {
	static def recipients(String modelName) {
		DomainUtil.getModelFileName(modelName, new RecipientsDomainProvider)
	}
}
