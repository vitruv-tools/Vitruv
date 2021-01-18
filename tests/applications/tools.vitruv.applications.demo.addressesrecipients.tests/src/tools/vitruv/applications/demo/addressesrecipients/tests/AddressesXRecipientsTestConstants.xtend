package tools.vitruv.applications.demo.addressesrecipients.tests

import edu.kit.ipd.sdq.activextendannotations.Utility

import static extension tools.vitruv.applications.demo.addressesrecipients.tests.AddressesCreators.addresses
import static extension tools.vitruv.applications.demo.addressesrecipients.tests.RecipientsCreators.recipients

@Utility
class AddressesXRecipientsTestConstants {
	protected static val ADDRESSES_MODEL = 'root'.addresses
	protected static val RECIPIENTS_MODEL = 'root'.recipients
	protected static val TEST_NUMBER = 42
	protected static val TEST_STREET = 'Page St'
	protected static val TEST_ZIP_CODE = 'SW1P4EN'
}
