package tools.vitruv.dsls.mappings.addressesXrecipients.tests

import edu.kit.ipd.sdq.metamodels.addresses.Addresses
import edu.kit.ipd.sdq.metamodels.recipients.Recipients
import mir.reactions.adXre_R2L.AdXre_R2LChangePropagationSpecification
import org.junit.jupiter.api.Test
import tools.vitruv.testutils.VitruvApplicationTest

import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.dsls.mappings.addressesXrecipients.tests.AddressesCreators.*
import static tools.vitruv.dsls.mappings.addressesXrecipients.tests.AddressesXRecipientsTestConstants.*
import static tools.vitruv.dsls.mappings.addressesXrecipients.tests.RecipientsCreators.*
import static tools.vitruv.testutils.matchers.ModelMatchers.contains
import static tools.vitruv.testutils.matchers.ModelMatchers.doesNotExist
import static tools.vitruv.testutils.matchers.ModelMatchers.equalsDeeply
import static tools.vitruv.testutils.matchers.ModelMatchers.ignoringFeatures

import static extension org.eclipse.emf.ecore.util.EcoreUtil.remove
import static extension tools.vitruv.testutils.matchers.CorrespondenceMatchers.hasNoCorrespondences
import static extension tools.vitruv.testutils.matchers.CorrespondenceMatchers.hasOneCorrespondence

class AddressesXRecipientsR2LTest extends VitruvApplicationTest {
	override protected getChangePropagationSpecifications() {
		#[new AdXre_R2LChangePropagationSpecification()]
	}

	@Test
	def void createRoot() {
		resourceAt(RECIPIENTS_MODEL).propagate[contents += newRecipients]
		assertThat(resourceAt(ADDRESSES_MODEL), contains(newAddresses, ignoringFeatures('id')))
		assertThat(Recipients.from(RECIPIENTS_MODEL),
			hasOneCorrespondence(equalsDeeply(Addresses.from(ADDRESSES_MODEL))))
	}

	@Test
	def void createAndDeleteRoot() {
		createRoot()
		Recipients.from(RECIPIENTS_MODEL).propagate[remove()]
		assertThat(resourceAt(RECIPIENTS_MODEL), doesNotExist)
		assertThat(resourceAt(ADDRESSES_MODEL), doesNotExist)
	}

	@Test
	def void createChild() {
		createRoot()

		val recipient = newRecipient => [business = true]
		Recipients.from(RECIPIENTS_MODEL).propagate[recipients += recipient]
		assertThat(recipient, hasNoCorrespondences)

		// "initial recipient model" (see Table 7.5 in dx.doi.org/10.5445/IR/1000069284)
		val location = newLocation
		recipient.propagate[locatedAt = location]
		assertThat(recipient, hasNoCorrespondences)
		assertThat(location, hasNoCorrespondences)
		location.propagate[number = TEST_NUMBER]
		assertThat(recipient, hasNoCorrespondences)
		assertThat(location, hasNoCorrespondences)
		location.propagate[street = TEST_STREET]
		assertThat(recipient, hasNoCorrespondences)
		assertThat(location, hasNoCorrespondences)

		// "recipient model after 2nd change" (Table 7.5)
		val city = newCity
		recipient.propagate[locatedIn = city]
		assertThat(recipient, hasNoCorrespondences)
		assertThat(city, hasNoCorrespondences)

		// "recipient model after 3rd change" (Table 7.5)
		city.propagate[zipCode = TEST_ZIP_CODE]
		val expectedAddress = newAddress => [
			street = TEST_STREET
			number = TEST_NUMBER
			zipCode = TEST_ZIP_CODE
		]
		assertThat(resourceAt(ADDRESSES_MODEL), contains(newAddresses => [addresses += expectedAddress]))
		assertThat(recipient, hasOneCorrespondence(equalsDeeply(expectedAddress)))
		assertThat(location, hasOneCorrespondence(equalsDeeply(expectedAddress)))
		assertThat(city, hasOneCorrespondence(equalsDeeply(expectedAddress)))
	}

	@Test
	def void createAndDeleteChild() {
		createChild()
		Recipients.from(RECIPIENTS_MODEL).propagate [
			recipients.get(0).remove()
		]
		assertThat(resourceAt(ADDRESSES_MODEL), contains(newAddresses))
	}

	@Test
	def void createAndDeleteGrandChild1() {
		createChild()
		Recipients.from(RECIPIENTS_MODEL).propagate [
			recipients.get(0).locatedAt.remove()
		]
		assertThat(resourceAt(ADDRESSES_MODEL), contains(newAddresses))
	}

	@Test
	def void createAndDeleteGrandChild2() {
		createChild()
		Recipients.from(RECIPIENTS_MODEL).propagate [
			recipients.get(0).locatedIn.remove()
		]
		assertThat(resourceAt(ADDRESSES_MODEL), contains(newAddresses))
	}

	@Test
	def void createAndModifyChildNumber() {
		createChild()
		val recipient = Recipients.from(RECIPIENTS_MODEL).recipients.get(0)
		recipient.propagate [
			locatedAt.number = TEST_NUMBER * 2
		]
		assertThat(recipient, hasOneCorrespondence(equalsDeeply(newAddress => [
			street = TEST_STREET
			number = TEST_NUMBER * 2
			zipCode = TEST_ZIP_CODE
		], ignoringFeatures('parent'))))

		recipient.propagate [
			locatedAt.number = -TEST_NUMBER
		]
		assertThat(resourceAt(ADDRESSES_MODEL), contains(newAddresses))
	}

	@Test
	def void createAndModifyChildZipCode() {
		createChild()
		val recipient = Recipients.from(RECIPIENTS_MODEL).recipients.get(0)
		recipient.propagate [
			locatedIn.zipCode = TEST_ZIP_CODE + TEST_ZIP_CODE
		]
		assertThat(recipient, hasOneCorrespondence(equalsDeeply(newAddress => [
			street = TEST_STREET
			number = TEST_NUMBER
			zipCode = TEST_ZIP_CODE + TEST_ZIP_CODE
		], ignoringFeatures('parent'))))

		recipient.propagate [
			locatedAt.number = -TEST_NUMBER
		]
		assertThat(resourceAt(ADDRESSES_MODEL), contains(newAddresses))
	}
}
