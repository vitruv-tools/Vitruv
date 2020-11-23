package tools.vitruv.dsls.mappings.addressesXrecipients.tests

import edu.kit.ipd.sdq.metamodels.recipients.Location
import edu.kit.ipd.sdq.metamodels.recipients.Recipient
import mir.reactions.adXre_L2R.AdXre_L2RChangePropagationSpecification
import org.junit.jupiter.api.Test

import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.testutils.matchers.ModelMatchers.contains
import static tools.vitruv.testutils.matchers.ModelMatchers.doesNotExist
import edu.kit.ipd.sdq.metamodels.addresses.Addresses
import static extension org.eclipse.emf.ecore.util.EcoreUtil.remove
import static extension tools.vitruv.testutils.matchers.CorrespondenceMatchers.*
import static tools.vitruv.testutils.matchers.ModelMatchers.equalsDeeply
import edu.kit.ipd.sdq.metamodels.recipients.Recipients
import static tools.vitruv.testutils.matchers.ModelMatchers.ignoringFeatures
import edu.kit.ipd.sdq.metamodels.recipients.City
import static tools.vitruv.dsls.mappings.addressesXrecipients.tests.AddressesCreators.*
import static tools.vitruv.dsls.mappings.addressesXrecipients.tests.RecipientsCreators.*

class AddressesXRecipientsL2RTest extends AddressesXRecipientsTest {
	override protected createChangePropagationSpecifications() {
		return #[new AdXre_L2RChangePropagationSpecification()]
	}

	@Test
	def void createRoot() {
		createAndSynchronizeModel(ADDRESSES_MODEL, newAddresses)
		assertThat(resourceAt(RECIPIENTS_MODEL), contains(newRecipients, ignoringFeatures('id')))
		assertThat(Addresses.from(ADDRESSES_MODEL),
			hasOneCorrespondence(equalsDeeply(Recipients.from(RECIPIENTS_MODEL))))
	}

	@Test
	def void createAndDeleteRoot() {
		createRoot()
		Addresses.from(ADDRESSES_MODEL).record[remove()]
		saveAndSynchronizeChanges(ADDRESSES_MODEL)
		assertThat(resourceAt(ADDRESSES_MODEL), doesNotExist)
		assertThat(resourceAt(RECIPIENTS_MODEL), doesNotExist)
	}

	@Test
	def void createChild() {
		createRoot()
		val address = newAddress
		saveAndSynchronizeChanges(Addresses.from(ADDRESSES_MODEL).record [
			addresses += address
		])
		assertThat(address, hasNoCorrespondences)

		// "initial address model" (see Table 7.4 in dx.doi.org/10.5445/IR/1000069284)
		saveAndSynchronizeChanges(address.record[number = TEST_NUMBER])
		assertThat(address, hasNoCorrespondences)

		// "address model after 1st change" (Table 7.4)
		saveAndSynchronizeChanges(address.record[street = TEST_STREET])
		assertThat(address, hasNoCorrespondences)

		// "address model after 2nd change" (Table 7.4)
		saveAndSynchronizeChanges(address.record[zipCode = TEST_ZIP_CODE])
		val expectedRecipient = newRecipient => [
			business = true
			locatedIn = newCity => [
				zipCode = TEST_ZIP_CODE
			]
			locatedAt = newLocation => [
				number = TEST_NUMBER
				street = TEST_STREET
			]
		]
		assertThat(address,
			hasOneCorrespondence(ofType(Recipient), equalsDeeply(expectedRecipient, ignoringFeatures('id', 'parent'))))
		assertThat(resourceAt(RECIPIENTS_MODEL), contains(newRecipients => [
			recipients += expectedRecipient
		], ignoringFeatures('id', 'parent')))
	}

	@Test
	def void createAndDeleteChild() {
		createChild()
		saveAndSynchronizeChanges(Addresses.from(ADDRESSES_MODEL).record [
			addresses.get(0).remove()
		])
		assertThat(resourceAt(RECIPIENTS_MODEL), contains(newRecipients, ignoringFeatures('id')))
	}

	@Test
	def void createAndModifyChildNumber() {
		createChild()
		val address = Addresses.from(ADDRESSES_MODEL).addresses.get(0)
		saveAndSynchronizeChanges(address.record[number = TEST_NUMBER * 2])
		assertThat(address, hasOneCorrespondence(ofType(Location), equalsDeeply(newLocation => [
			number = TEST_NUMBER * 2
			street = TEST_STREET
		], ignoringFeatures('id', 'parent'))))

		saveAndSynchronizeChanges(address.record[number = -TEST_NUMBER])
		assertThat(resourceAt(RECIPIENTS_MODEL), contains(newRecipients, ignoringFeatures('id')))
	}

	@Test
	def void createAndModifyChildZipCode() {
		createChild()
		val address = Addresses.from(ADDRESSES_MODEL).addresses.get(0)
		saveAndSynchronizeChanges(address.record[zipCode = TEST_ZIP_CODE + TEST_ZIP_CODE])
		assertThat(address, hasOneCorrespondence(ofType(City), equalsDeeply(newCity => [
			zipCode = TEST_ZIP_CODE + TEST_ZIP_CODE
		], ignoringFeatures('id', 'parent'))))

		saveAndSynchronizeChanges(address.record[zipCode = null])
		assertThat(resourceAt(RECIPIENTS_MODEL), contains(newRecipients, ignoringFeatures('id')))
	}
}
