package tools.vitruv.applications.familiespersons.families2Persons.test

import edu.kit.ipd.sdq.metamodels.families.FamiliesFactory
import edu.kit.ipd.sdq.metamodels.families.FamilyRegister
import edu.kit.ipd.sdq.metamodels.persons.Person
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tools.vitruv.applications.familiespersons.families2persons.FamiliesToPersonsChangePropagationSpecification
import tools.vitruv.domains.families.FamiliesDomainProvider
import tools.vitruv.domains.persons.PersonsDomainProvider
import tools.vitruv.framework.correspondence.CorrespondenceModelUtil

import static org.hamcrest.CoreMatchers.is
import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.testutils.matchers.ModelMatchers.exists
import tools.vitruv.testutils.VitruvApplicationTest
import tools.vitruv.testutils.domains.DomainUtil

class FamiliesPersonsTest extends VitruvApplicationTest {
	static val FAMILY_NAME = "Mustermann"
	static val FIRST_NAME_FATHER = "Max"
	static val FIRST_NAME_SON = "Sohn"
	static val FIRST_NAME_DAUGHTER = "Tochter"
	static val FIRST_NAME_MOTHER = "Erika"
	static val PERSONS_MODEL = DomainUtil.getModelFileName('model/persons', new PersonsDomainProvider)
	static val FAMILIES_MODEL = DomainUtil.getModelFileName('model/model', new FamiliesDomainProvider)

	override protected getChangePropagationSpecifications() {
		return #[new FamiliesToPersonsChangePropagationSpecification()]
	}

	@BeforeEach
	def createRoot() {
		resourceAt(FAMILIES_MODEL).propagate[contents += FamiliesFactory.eINSTANCE.createFamilyRegister]
		assertThat(resourceAt(PERSONS_MODEL), exists)
	}

	@Test
	def void testDeleteFamilyRegister() {
		// TODO
	}

	private def ourFamily() {
		FamiliesFactory.eINSTANCE.createFamily => [
			lastName = FAMILY_NAME
		]
	}

	@Test
	def void testCreateFamilyFather() {
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val family = ourFamily()
			families += family => [
				father = FamiliesFactory.eINSTANCE.createMember => [
					firstName = FIRST_NAME_FATHER
					familyFather = family
				]
			]
		]

		assertThat(resourceAt(PERSONS_MODEL), exists)
	}

	@Test
	def void testCreateFamilySon() {
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val family = ourFamily()
			families += family => [
				sons += FamiliesFactory.eINSTANCE.createMember => [
					firstName = FIRST_NAME_SON
					familySon = family
				]
			]
		]

		assertThat(resourceAt(PERSONS_MODEL), exists)
	}

	@Test
	def void testCreateFamilyMother() {
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val family = ourFamily()
			families += family => [
				mother = FamiliesFactory.eINSTANCE.createMember => [
					firstName = FIRST_NAME_MOTHER
					familyMother = family
				]
			]
		]

		assertThat(resourceAt(PERSONS_MODEL), exists)
	}

	@Test
	def void testCreateFamilyDaughter() {
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val family = ourFamily()
			families += family => [
				daughters += FamiliesFactory.eINSTANCE.createMember => [
					firstName = FIRST_NAME_DAUGHTER
					familyDaughter = family
				]
			]
		]

		assertThat(resourceAt(PERSONS_MODEL), exists)
	}

	@Test
	def void testDeleteMember() {
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val family = ourFamily()
			families += family => [
				daughters += FamiliesFactory.eINSTANCE.createMember => [
					firstName = FIRST_NAME_DAUGHTER
					familyDaughter = family
				]
			]
		]
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families.get(0).daughters.remove(0)
		]

		assertThat(resourceAt(PERSONS_MODEL), exists)
	}

	@Test
	def void testChangeFirstName() {
		val daughter = FamiliesFactory.eINSTANCE.createMember
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val family = ourFamily()
			families += family => [
				daughters += daughter => [
					firstName = FIRST_NAME_DAUGHTER
					familyDaughter = family
				]
			]
		]

		daughter.propagate[firstName = FIRST_NAME_MOTHER]
		val corrObjs = CorrespondenceModelUtil.getCorrespondingEObjects(correspondenceModel, daughter).filter(Person)
		assertThat(corrObjs.length, is(1))
		val corrMember = corrObjs.get(0)
		assertThat(corrMember.fullName.split(" ").get(0), is(FIRST_NAME_MOTHER))
	}

	@Test
	def void testChangeLastName() {
	}
}
