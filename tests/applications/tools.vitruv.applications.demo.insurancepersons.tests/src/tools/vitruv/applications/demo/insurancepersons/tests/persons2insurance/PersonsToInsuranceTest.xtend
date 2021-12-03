package tools.vitruv.applications.demo.insurancepersons.tests.persons2insurance

import edu.kit.ipd.sdq.metamodels.persons.PersonsFactory
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tools.vitruv.domains.demo.persons.PersonsDomainProvider

import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.testutils.matchers.ModelMatchers.exists
import tools.vitruv.testutils.VitruvApplicationTest
import tools.vitruv.testutils.domains.DomainUtil
import tools.vitruv.domains.demo.insurance.InsuranceDomainProvider
import tools.vitruv.applications.demo.insurancepersons.persons2insurance.PersonsToInsuranceChangePropagationSpecification

class PersonsToInsuranceTest extends VitruvApplicationTest {
	static val INSURANCE_MODEL = DomainUtil.getModelFileName('model/model', new InsuranceDomainProvider)
	static val PERSONS_MODEL = DomainUtil.getModelFileName('model/model', new PersonsDomainProvider)

	override protected getChangePropagationSpecifications() {
		return #[new PersonsToInsuranceChangePropagationSpecification()]
	}

	@BeforeEach
	def void createRoot() {
		resourceAt(PERSONS_MODEL).propagate[contents += PersonsFactory.eINSTANCE.createPersonRegister]
	}

	@Test
	def testCreatePersonsModel() {
		assertThat(resourceAt(INSURANCE_MODEL), exists)
	}

}
