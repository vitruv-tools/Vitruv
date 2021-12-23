package tools.vitruv.applications.demo.insurancepersons.tests.insurance2persons

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tools.vitruv.domains.demo.persons.PersonsDomainProvider

import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.testutils.matchers.ModelMatchers.exists
import tools.vitruv.testutils.VitruvApplicationTest
import tools.vitruv.testutils.domains.DomainUtil
import tools.vitruv.applications.demo.insurancepersons.insurance2persons.InsuranceToPersonsChangePropagationSpecification
import tools.vitruv.domains.demo.insurance.InsuranceDomainProvider
import edu.kit.ipd.sdq.metamodels.insurance.InsuranceFactory

class InsurancePersonsTest extends VitruvApplicationTest {
	static val PERSONS_MODEL = DomainUtil.getModelFileName('model/model', new PersonsDomainProvider)
	static val INSURANCE_MODEL = DomainUtil.getModelFileName('model/model', new InsuranceDomainProvider)

	override protected getChangePropagationSpecifications() {
		return #[new InsuranceToPersonsChangePropagationSpecification()]
	}

	@BeforeEach
	def void createRoot() {
		resourceAt(INSURANCE_MODEL).propagate[contents += InsuranceFactory.eINSTANCE.createInsuranceDatabase]
	}

	@Test
	def void testCreateInsuranceDatabase() {
		assertThat(resourceAt(PERSONS_MODEL), exists)
	}

}
