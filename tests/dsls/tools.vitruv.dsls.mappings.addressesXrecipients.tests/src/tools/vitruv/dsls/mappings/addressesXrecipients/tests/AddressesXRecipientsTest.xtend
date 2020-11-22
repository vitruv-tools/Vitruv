package tools.vitruv.dsls.mappings.addressesXrecipients.tests

import tools.vitruv.demo.domains.addresses.AddressesDomainProvider
import tools.vitruv.demo.domains.recipients.RecipientsDomainProvider
import tools.vitruv.testutils.VitruvApplicationTest
import static extension tools.vitruv.dsls.mappings.addressesXrecipients.tests.AddressesCreator.addresses
import static extension tools.vitruv.dsls.mappings.addressesXrecipients.tests.RecipientsCreator.recipients

abstract class AddressesXRecipientsTest extends VitruvApplicationTest {
	override protected getVitruvDomains() {
		#[new AddressesDomainProvider().domain, new RecipientsDomainProvider().domain]
	}

	protected static val ADDRESSES_MODEL = 'root'.addresses
	protected static val RECIPIENTS_MODEL = 'root'.recipients
	protected static val TEST_NUMBER = 42
	protected static val TEST_STREET = 'Page St'
	protected static val TEST_ZIP_CODE = 'SW1P4EN'
}
