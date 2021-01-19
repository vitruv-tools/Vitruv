package tools.vitruv.applications.demo.familiespersons.tests.persons2families

import edu.kit.ipd.sdq.metamodels.families.Member
import edu.kit.ipd.sdq.metamodels.persons.PersonRegister
import edu.kit.ipd.sdq.metamodels.persons.PersonsFactory
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tools.vitruv.domains.demo.families.FamiliesDomainProvider
import tools.vitruv.domains.demo.persons.PersonsDomainProvider

import static org.hamcrest.CoreMatchers.is
import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.testutils.matchers.ModelMatchers.exists
import org.junit.jupiter.api.Disabled
import tools.vitruv.testutils.VitruvApplicationTest
import tools.vitruv.testutils.domains.DomainUtil
import tools.vitruv.applications.demo.familiespersons.persons2families.PersonsToFamiliesChangePropagationSpecification

class PersonsToFamiliesTest extends VitruvApplicationTest {
	static val MALE_PERSON_NAME = "Max Mustermann"
	static val SECOND_MALE_PERSON_NAME = "Bernd Mustermann"
	static val FEMALE_PERSON_NAME = "Erika Mustermann"
	static val FAMILIES_MODEL = DomainUtil.getModelFileName('model/families', new FamiliesDomainProvider)
	static val PERSONS_MODEL = DomainUtil.getModelFileName('model/model', new PersonsDomainProvider)

	override protected getChangePropagationSpecifications() {
		return #[new PersonsToFamiliesChangePropagationSpecification()]
	}

	@BeforeEach
	def createRoot() {
		resourceAt(PERSONS_MODEL).propagate[contents += PersonsFactory.eINSTANCE.createPersonRegister]

		assertThat(resourceAt(FAMILIES_MODEL), exists)
	}

	@Test
	@Disabled("The personsToFamilies is broken")
	def void testCreateMalePerson() {
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = MALE_PERSON_NAME
			]
		]

		assertThat(resourceAt(FAMILIES_MODEL), exists);
	}

	@Test
	def void testDeletePersonRegister() {
	}

	@Test
	@Disabled("The personsToFamilies is broken")
	def void testCreateMale() {
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = MALE_PERSON_NAME
			]
		]

		assertThat(resourceAt(FAMILIES_MODEL), exists);
	}

	@Test
	@Disabled("The personsToFamilies is broken")
	def void testCreateFemale() {
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FEMALE_PERSON_NAME
			]
		]

		assertThat(resourceAt(FAMILIES_MODEL), exists);
	}

	@Test
	@Disabled("The personsToFamilies is broken")
	def void testChangeFirstName() {
		val person = PersonsFactory.eINSTANCE.createMale => [
			fullName = MALE_PERSON_NAME
		]
		PersonRegister.from(PERSONS_MODEL).propagate[persons += person]
		person.propagate[fullName = SECOND_MALE_PERSON_NAME]
		val Iterable<Member> members = correspondenceModel.getCorrespondingEObjects(#[person]).filter(Member)

		assertThat(members.length, is(1))
		val member = members.get(0)
		assertThat(member.firstName, is(person.fullName.split(" ").get(0)))
	}
}
