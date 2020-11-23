package tools.vitruv.dsls.mappings.addressesXrecipients.tests

import edu.kit.ipd.sdq.activextendannotations.Utility
import edu.kit.ipd.sdq.metamodels.recipients.RecipientsFactory
import tools.vitruv.demo.domains.recipients.RecipientsDomainProvider
import tools.vitruv.testutils.domains.DomainUtil

@Utility
class RecipientsCreators {
	static def recipients(String modelName) {
		DomainUtil.getModelFileName(modelName, new RecipientsDomainProvider)
	}

	static def newRecipients() {
		RecipientsFactory.eINSTANCE.createRecipients
	}

	static def newRecipient() {
		RecipientsFactory.eINSTANCE.createRecipient
	}

	static def newCity() {
		RecipientsFactory.eINSTANCE.createCity
	}

	static def newLocation() {
		RecipientsFactory.eINSTANCE.createLocation
	}
}
