package tools.vitruv.applications.demo.familiespersons.tests.families2Persons

import edu.kit.ipd.sdq.metamodels.families.FamiliesFactory
import edu.kit.ipd.sdq.metamodels.families.Family
import edu.kit.ipd.sdq.metamodels.families.FamilyRegister
import edu.kit.ipd.sdq.metamodels.families.Member
import edu.kit.ipd.sdq.metamodels.persons.Female
import edu.kit.ipd.sdq.metamodels.persons.Male
import edu.kit.ipd.sdq.metamodels.persons.Person
import edu.kit.ipd.sdq.metamodels.persons.PersonRegister
import edu.kit.ipd.sdq.metamodels.persons.PersonsFactory
import java.nio.file.Path
import java.util.stream.Stream
import org.apache.log4j.Logger
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import tools.vitruv.applications.demo.familiespersons.families2persons.FamiliesToPersonsChangePropagationSpecification
import tools.vitruv.applications.demo.familiespersons.families2persons.FamiliesToPersonsHelper
import tools.vitruv.applications.demo.familiespersons.persons2families.PersonsToFamiliesChangePropagationSpecification
import tools.vitruv.domains.demo.families.FamiliesDomainProvider
import tools.vitruv.domains.demo.persons.PersonsDomainProvider
import tools.vitruv.testutils.VitruvApplicationTest
import tools.vitruv.testutils.domains.DomainUtil

import static org.hamcrest.CoreMatchers.*
import static org.hamcrest.MatcherAssert.assertThat
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertThrows
import static tools.vitruv.testutils.matchers.ModelMatchers.*

enum MemberRole {
	Father,
	Mother,
	Son,
	Daughter
}

/**Test to validate the transfer of changes from the FamilyModel to the PersonModel.
 * @author Dirk Neumann
 */
class FamiliesPersonsTest extends VitruvApplicationTest {
	static val logger = Logger.getLogger(FamiliesPersonsTest)
	String nameOfTestMethod = null

	// First Set of reused static strings for the first names of the persons
	final static String FIRST_DAD_1 = "Anton"
	final static String FIRST_MOM_1 = "Berta"
	final static String FIRST_SON_1 = "Chris"
	final static String FIRST_DAU_1 = "Daria"

	// Second Set of reused static strings for the first names of the persons
	final static String FIRST_DAD_2 = "Adam"
	final static String FIRST_MOM_2 = "Birgit"
	final static String FIRST_SON_2 = "Charles"
	final static String FIRST_DAU_2 = "Daniela"

	// Set of reused static strings for the last names of the persons
	final static String LAST_NAME_1 = "Meier"
	final static String LAST_NAME_2 = "Schulze"
	final static String LAST_NAME_3 = "Müller"


	// Model Paths
	final static Path PERSONS_MODEL = DomainUtil.getModelFileName('model/persons', new PersonsDomainProvider)
	final static Path FAMILIES_MODEL = DomainUtil.getModelFileName('model/families', new FamiliesDomainProvider)

	/* Static reusable predefined Persons.
	 * The first number indicates from which string set (above) the forename is.
	 * the second number indicates from which string set (above) the lastname is.
	 */
	final static Male DAD11 = PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_DAD_1 + " " + LAST_NAME_1]
	final static Female MOM11 = PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOM_1 + " " + LAST_NAME_1]
	final static Male SON11 = PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON_1 + " " + LAST_NAME_1]
	final static Female DAU11 = PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAU_1 + " " + LAST_NAME_1]

	final static Male DAD12 = PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_DAD_1 + " " + LAST_NAME_2]
	final static Female MOM12 = PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOM_1 + " " + LAST_NAME_2]
	final static Male SON12 = PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON_1 + " " + LAST_NAME_2]
	final static Female DAU12 = PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAU_1 + " " + LAST_NAME_2]

	final static Male DAD21 = PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_DAD_2 + " " + LAST_NAME_1]
	final static Female MOM21 = PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOM_2 + " " + LAST_NAME_1]
	final static Male SON21 = PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON_2 + " " + LAST_NAME_1]
	final static Female DAU21 = PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAU_2 + " " + LAST_NAME_1]

	final static Male DAD22 = PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_DAD_2 + " " + LAST_NAME_2]
	final static Female MOM22 = PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOM_2 + " " + LAST_NAME_2]
	final static Male SON22 = PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON_2 + " " + LAST_NAME_2]
	final static Female DAU22 = PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAU_2 + " " + LAST_NAME_2]

	/**Set the correct set of reactions and routines for this test suite
	 */
	override protected getChangePropagationSpecifications() {
		return #[new FamiliesToPersonsChangePropagationSpecification(), new PersonsToFamiliesChangePropagationSpecification()]
	}

	/**Before each test a new {@link FamilyRegister} is created as starting point.
	 * This is checked by several assertions to ensure correct preconditions for the tests.
	 */
	@BeforeEach
	def void insertRegister(TestInfo testInfo) {
		this.nameOfTestMethod = testInfo.getDisplayName()
		val x = resourceAt(FAMILIES_MODEL)
		x.propagate[contents += FamiliesFactory.eINSTANCE.createFamilyRegister]
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(1, resourceAt(PERSONS_MODEL).contents.size)
		assertEquals(1, resourceAt(PERSONS_MODEL).allContents.size)
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister))
		assertEquals(0, resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size)
	}

	/**Creates a {@link Family} with the given familieName. Used by the "insertFamilyWith..."-tests
	 */
	def createFamily(String familieName) {
		FamiliesFactory.eINSTANCE.createFamily => [lastName = familieName]
	}

	/**Inserts a new {@link Family}. This should not have any effect on the Persons-Model.
	 */
	@Test
	def void testInsertNewFamily() {
		logger.trace(nameOfTestMethod + " - begin")
		val family = createFamily(LAST_NAME_1)
		logger.trace(nameOfTestMethod + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate[families += family]
		logger.trace(nameOfTestMethod + " - propagation done")
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister()
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(nameOfTestMethod + " - finished without errors")
	}

	/**Checks if the actual {@link FamilyRegister looks like the expected one.
	 */
	def void assertCorrectFamilyRegister(FamilyRegister expectedFamilyRegister){
		val familyModel = resourceAt(FAMILIES_MODEL)
		assertThat(familyModel, exists)
		assertEquals(1, familyModel.contents.size)
		val familyRegister = familyModel.contents.get(0)
		assertThat(familyRegister, instanceOf(FamilyRegister))
		val FamilyRegister castedFamilyRegister = familyRegister as FamilyRegister
		assertThat(castedFamilyRegister, equalsDeeply(expectedFamilyRegister))
	}

	/**Checks if the actual {@link PersonRegister looks like the expected one.
	 */
	def void assertCorrectPersonRegister(PersonRegister expectedPersonRegister){
		val personModel = resourceAt(PERSONS_MODEL)
		assertThat(personModel, exists)
		assertEquals(1, personModel.contents.size)
		val personRegister = personModel.contents.get(0)
		assertThat(personRegister, instanceOf(PersonRegister))
		val PersonRegister castedPersonRegister = personRegister as PersonRegister
		assertThat(castedPersonRegister, equalsDeeply(expectedPersonRegister))
	}

	/**Inserts a new {@link Family} and insert a father into it afterwards.
	 */
	@Test
	def void testInsertFamilyWithFather() {
		logger.trace(nameOfTestMethod + " - begin")
		val family = createFamily(LAST_NAME_1)
		logger.trace(nameOfTestMethod + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families += family
			family.father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
		]
		logger.trace(nameOfTestMethod + " - propagation done")
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_1 + " " + LAST_NAME_1
			]
		]
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(nameOfTestMethod + " - finished without errors")
	}

	/**Inserts a new {@link Family} and insert a mother into it afterwards.
	 */
	@Test
	def void testInsertFamilyWithMother() {
		logger.trace(nameOfTestMethod + " - begin")
		val family = createFamily(LAST_NAME_1)
		logger.trace(nameOfTestMethod + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families += family
			family.mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
		]
		logger.trace(nameOfTestMethod + " - propagation done")
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += MOM11
		]
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(nameOfTestMethod + " - finished without errors")
	}

	/**Inserts a new {@link Family} and insert a son into it afterwards.
	 */
	@Test
	def void testInsertFamilyWithSon() {
		logger.trace(nameOfTestMethod + " - begin")
		val family = createFamily(LAST_NAME_1)
		logger.trace(nameOfTestMethod + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families += family
			family.sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
		]
		logger.trace(nameOfTestMethod + " - propagation done")
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += SON11
		]
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(nameOfTestMethod + " - finished without errors")
	}

	/**Inserts a new {@link Family} and insert a daughter into it afterwards.
	 */
	@Test
	def void testInsertFamilyWithDaughter() {
		logger.trace(nameOfTestMethod + " - begin")
		val family = createFamily(LAST_NAME_1)
		logger.trace(nameOfTestMethod + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families += family
			family.daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
		]
		logger.trace(nameOfTestMethod + " - propagation done")
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += DAU11
		]
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(nameOfTestMethod + " - finished without errors")
	}

	/**Used to build the starting point for many other tests like deleting and renaming operations.
	 * Creates a {@link Family} including a father, a mother, a son and a daughter and maps this
	 * changes to the {@link PersonRegister} which then includes two {@link Male} and two {@link Female}.
	 */
	def void createOneFamilyBeforeTesting() {
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += #[DAD11, MOM11, SON11, DAU11]
		]
		assertCorrectPersonRegister(expectedPersonRegister)
	}

	/**Used to build an extended starting point for many other tests like replacing and moving members.
	 * Creates a {@link Family} including a father, a mother, a son and a daughter and maps this
	 * changes to the {@link PersonRegister} which then includes two {@link Male} and two {@link Female}.
	 */
	def void createTwoFamiliesBeforeTesting() {
		this.createOneFamilyBeforeTesting()
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_2
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_2]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_2]
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_2]
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_2]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += #[DAD11, MOM11, SON11, DAU11, DAD22, MOM22, SON22, DAU22]
		]
		assertCorrectPersonRegister(expectedPersonRegister)
	}

	/**Deletes a father from a {@link Family} and the corresponding {@link Male} from the {@link PersonRegister}.
	 */
	@Test
	def void testDeleteFatherFromFamily() {
		logger.trace(nameOfTestMethod + " - begin")
		this.createOneFamilyBeforeTesting()
		logger.trace(nameOfTestMethod + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val selectedFamily = families.findFirst [
				it.lastName.equals(LAST_NAME_1) && it.father.firstName.equals(FIRST_DAD_1)
			]
			selectedFamily.father = null
		]
		logger.trace(nameOfTestMethod + " - propagation done")
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += #[MOM11, SON11, DAU11]
		]
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(nameOfTestMethod + " - finished without errors")
	}

	/**Deletes a son from a {@link Family} and the corresponding {@link Male} from the {@link PersonRegister}.
	 */
	@Test
	def void testDeleteSonFromFamily() {
		logger.trace(nameOfTestMethod + " - begin")
		this.createOneFamilyBeforeTesting()
		logger.trace(nameOfTestMethod + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val selectedFamily = families.findFirst [
				it.lastName.equals(LAST_NAME_1) && it.sons.exists[it.firstName.equals(FIRST_SON_1)]
			]
			val sonToDelete = selectedFamily.sons.findFirst[it.firstName.equals(FIRST_SON_1)]
			selectedFamily.sons.remove(sonToDelete)
		]
		logger.trace(nameOfTestMethod + " - propagation done")
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += #[DAD11, MOM11, DAU11]
		]
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(nameOfTestMethod + " - finished without errors")
	}

	/**Deletes a mother from a {@link Family} and the corresponding {@link Female} from the {@link PersonRegister}.
	 */
	@Test
	def void testDeleteMotherFromFamily() {
		logger.trace(nameOfTestMethod + " - begin")
		this.createOneFamilyBeforeTesting()
		logger.trace(nameOfTestMethod + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val selectedFamily = families.findFirst [
				it.lastName.equals(LAST_NAME_1) && it.mother.firstName.equals(FIRST_MOM_1)
			]
			selectedFamily.mother = null
		]
		logger.trace(nameOfTestMethod + " - propagation done")
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += #[DAD11, SON11, DAU11]
		]
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(nameOfTestMethod + " - finished without errors")
	}

	/**Deletes a daughter from a {@link Family} and the corresponding {@link Female} from the {@link PersonRegister}.
	 */
	@Test
	def void testDeleteDaughterFromFamily() {
		logger.trace(nameOfTestMethod + " - begin")
		this.createOneFamilyBeforeTesting()
		logger.trace(nameOfTestMethod + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val selectedFamily = families.findFirst [
				it.lastName.equals(LAST_NAME_1) && it.daughters.exists[it.firstName.equals(FIRST_DAU_1)]
			]
			val daughterToDelete = selectedFamily.daughters.findFirst[it.firstName.equals(FIRST_DAU_1)]
			selectedFamily.daughters.remove(daughterToDelete)
		]
		logger.trace(nameOfTestMethod + " - propagation done")
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += #[DAD11, MOM11, SON11]
		]
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(nameOfTestMethod + " - finished without errors")
	}

	/**Changes the lastname of a {@link Family} and should edit the fullnames of
	 * all corresponding {@link Person}s from the {@link PersonRegister}.
	 */
	@Test
	def void testChangeLastName() {
		logger.trace(nameOfTestMethod + " - begin")
		this.createOneFamilyBeforeTesting()
		logger.trace(nameOfTestMethod + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val selectedFamily = families.findFirst[it.lastName.equals(LAST_NAME_1)]
			selectedFamily.lastName = LAST_NAME_2
		]
		logger.trace(nameOfTestMethod + " - propagation done")
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += #[DAD12, MOM12, SON12, DAU12]
		]
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(nameOfTestMethod + " - finished without errors")
	}

	/**Changes the firstname of a father of a {@link Family} and should edit the
	 * fullname of the corresponding {@link Male} in the {@link PersonRegister}.
	 */
	@Test
	def void testChangeFirstNameFather() {
		logger.trace(nameOfTestMethod + " - begin")
		this.createOneFamilyBeforeTesting()
		logger.trace(nameOfTestMethod + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val selectedFamily = families.findFirst [
				it.lastName.equals(LAST_NAME_1) && it.father.firstName.equals(FIRST_DAD_1)
			]
			selectedFamily.father.firstName = FIRST_DAD_2
		]
		logger.trace(nameOfTestMethod + " - propagation done")
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += #[DAD21, MOM11, SON11, DAU11]
		]
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(nameOfTestMethod + " - finished without errors")
	}

	/**Changes the firstname of a son of a {@link Family} and should edit the
	 * fullname of the corresponding {@link Male} in the {@link PersonRegister}.
	 */
	@Test
	def void testChangeFirstNameSon() {
		logger.trace(nameOfTestMethod + " - begin")
		this.createOneFamilyBeforeTesting()
		logger.trace(nameOfTestMethod + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val selectedFamily = families.findFirst [
				it.lastName.equals(LAST_NAME_1) && it.sons.exists[son|son.firstName.equals(FIRST_SON_1)]
			]
			val sonToChange = selectedFamily.sons.findFirst[it.firstName.equals(FIRST_SON_1)]
			sonToChange.firstName = FIRST_SON_2
		]
		logger.trace(nameOfTestMethod + " - propagation done")
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += #[DAD11, MOM11, SON21, DAU11]
		]
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(nameOfTestMethod + " - finished without errors")
	}

	/**Changes the firstname of a mother of a {@link Family} and should edit the
	 * fullname of the corresponding {@link Female} in the {@link PersonRegister}.
	 */
	@Test
	def void testChangeFirstNameMother() {
		logger.trace(nameOfTestMethod + " - begin")
		this.createOneFamilyBeforeTesting()
		logger.trace(nameOfTestMethod + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val selectedFamily = families.findFirst [
				it.lastName.equals(LAST_NAME_1) && it.mother.firstName.equals(FIRST_MOM_1)
			]
			selectedFamily.mother.firstName = FIRST_MOM_2
		]
		logger.trace(nameOfTestMethod + " - propagation done")
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += #[DAD11, MOM21, SON11, DAU11]
		]
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(nameOfTestMethod + " - finished without errors")
	}

	/**Changes the firstname of a daughter of a {@link Family} and should edit the
	 * fullname of the corresponding {@link Female} in the {@link PersonRegister}.
	 */
	@Test
	def void testChangeFirstNameDaughter() {
		logger.trace(nameOfTestMethod + " - begin")
		this.createOneFamilyBeforeTesting()
		logger.trace(nameOfTestMethod + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val selectedFamily = families.findFirst [
				it.lastName.equals(LAST_NAME_1) && it.daughters.exists[it.firstName.equals(FIRST_DAU_1)]
			]
			val daughterToChange = selectedFamily.daughters.findFirst[it.firstName.equals(FIRST_DAU_1)]
			daughterToChange.firstName = FIRST_DAU_2
		]
		logger.trace(nameOfTestMethod + " - propagation done")
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += #[DAD11, MOM11, SON11, DAU21]
		]
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(nameOfTestMethod + " - finished without errors")
	}

	// ========== REPLACING PARENTS ==========
	/**Replace the father with a new member which causes the original father to be moved
	 * to a new family with the same lastname in which he is the only member.
	 */
	@Test
	def void testReplaceFatherWithNewMember() {
		logger.trace(nameOfTestMethod + " - begin")
		this.createOneFamilyBeforeTesting()
		logger.trace(nameOfTestMethod + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val family = families.findFirst[it.lastName.equals(LAST_NAME_1)]
			family.father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_2]
		]
		logger.trace(nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_2]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += #[DAD21, MOM11, SON11, DAU11]
		]
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(nameOfTestMethod + " - finished without errors")
	}

	/**Replace the father with a father from a different family which causes the original father
	 * to be moved to a new family with the same lastname in which he is the only member.
	 * The replacing father will be removed from his original family in return.
	 */
	@Test
	def void testReplaceFatherWithExistingFather() {
		logger.trace(nameOfTestMethod + " - begin")
		this.createTwoFamiliesBeforeTesting()
		logger.trace(nameOfTestMethod + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val family1 = families.findFirst[it.lastName.equals(LAST_NAME_1)]
			val family2 = families.findFirst[it.lastName.equals(LAST_NAME_2)]
			family1.father = family2.father
		]
		logger.trace(nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_2]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
			]
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_2
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_2]
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_2]
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_2]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += #[DAD21, MOM11, SON11, DAU11, MOM22, SON22, DAU22]
		]
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(nameOfTestMethod + " - finished without errors")
	}

	/**Replace the father with a father from a different family which causes the original father
	 * to be moved to a new family with the same lastname in which he is the only member.
	 * The replacing father will be removed from his original family in return.
	 *
	 * In this version, the replacing father was the last member of his family before
	 * the replacing happens. Therefore, his old family will be deleted afterwards.
	 */
	@Test
	def void testReplaceFatherWithExistingPreviouslyLonlyFather() {
		logger.trace(nameOfTestMethod + " - begin")
		this.createOneFamilyBeforeTesting()
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_2
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_2]
			]
		]
		logger.trace(nameOfTestMethod + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val family1 = families.findFirst[it.lastName.equals(LAST_NAME_1)]
			val family2 = families.findFirst[it.lastName.equals(LAST_NAME_2)]
			family1.father = family2.father
		]
		logger.trace(nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_2]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += #[DAD21, MOM11, SON11, DAU11]
		]
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(nameOfTestMethod + " - finished without errors")
	}

	/**Replace the father with a son from a different family which causes the original father
	 * to be moved to a new family with the same lastname in which he is the only member.
	 * The replacing son will be removed from his original family in return.
	 */
	@Test
	def void testReplaceFatherWithExistingSon() {
		logger.trace(nameOfTestMethod + " - begin")
		this.createTwoFamiliesBeforeTesting()
		logger.trace(nameOfTestMethod + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val family1 = families.findFirst[it.lastName.equals(LAST_NAME_1)]
			val family2 = families.findFirst[it.lastName.equals(LAST_NAME_2)]
			family1.father = family2.sons.findFirst[son|son.firstName.equals(FIRST_SON_2)]
		]
		logger.trace(nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_2]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
			]
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_2
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_2]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_2]
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_2]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += #[SON21, MOM11, SON11, DAU11, DAD22, MOM22, DAU22]
		]
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(nameOfTestMethod + " - finished without errors")
	}

	/**Replace the mother with a new member which causes the original mother to be moved
	 * to a new family with the same lastname in which she is the only member.
	 */
	@Test
	def void testReplaceMotherWithNewMember() {
		logger.trace(nameOfTestMethod + " - begin")
		this.createOneFamilyBeforeTesting()
		logger.trace(nameOfTestMethod + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val family = families.findFirst[it.lastName.equals(LAST_NAME_1)]
			family.mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_2]
		]
		logger.trace(nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_2]
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += #[DAD11, MOM21, SON11, DAU11]
		]
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(nameOfTestMethod + " - finished without errors")
	}

	/**Replace the mother with a mother from a different family which causes the original mother
	 * to be moved to a new family with the same lastname in which she is the only member.
	 * The replacing mother will be removed from its original family in return.
	 */
	@Test
	def void testReplaceMotherWithExistingMother() {
		logger.trace(nameOfTestMethod + " - begin")
		this.createTwoFamiliesBeforeTesting()
		logger.trace(nameOfTestMethod + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val family1 = families.findFirst[it.lastName.equals(LAST_NAME_1)]
			val family2 = families.findFirst[it.lastName.equals(LAST_NAME_2)]
			family1.mother = family2.mother
		]
		logger.trace(nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_2]
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
			]
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_2
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_2]
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_2]
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_2]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += #[DAD11, MOM21, SON11, DAU11, DAD22, SON22, DAU22]
		]
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(nameOfTestMethod + " - finished without errors")
	}

	/**Replace the mother with a daughter from a different family which causes the original mother
	 * to be moved to a new family with the same lastname in which she is the only member.
	 * The replacing daughter will be removed from her original family in return.
	 */
	@Test
	def void testReplaceMotherWithExistingDaughter() {
		logger.trace(nameOfTestMethod + " - begin")
		this.createTwoFamiliesBeforeTesting()
		logger.trace(nameOfTestMethod + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val family1 = families.findFirst[it.lastName.equals(LAST_NAME_1)]
			val family2 = families.findFirst[it.lastName.equals(LAST_NAME_2)]
			family1.mother = family2.daughters.findFirst[daughter|daughter.firstName.equals(FIRST_DAU_2)]
		]
		logger.trace(nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_2]
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
			]
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_2
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_2]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_2]
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_2]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += #[DAD11, DAU21, SON11, DAU11, DAD22, MOM22, SON22]
		]
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(nameOfTestMethod + " - finished without errors")
	}

	/**Replace the mother with a daughter from a different family which causes the original mother
	 * to be moved to a new family with the same lastname in which she is the only member.
	 * The replacing daughter will be removed from her original family in return.
	 *
	 * In this version, the replacing daughter was the last member of her family before
	 * the replacing happens. Therefore, her old family will be deleted afterwards.
	 */
	@Test
	def void testReplaceMotherWithExistingPreviouslyLonlyDaughter() {
		logger.trace(nameOfTestMethod + " - begin")
		this.createOneFamilyBeforeTesting()
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_2
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_2]
			]
		]
		logger.trace(nameOfTestMethod + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val family1 = families.findFirst[it.lastName.equals(LAST_NAME_1)]
			val family2 = families.findFirst[it.lastName.equals(LAST_NAME_2)]
			family1.mother = family2.daughters.findFirst[daughter|daughter.firstName.equals(FIRST_DAU_2)]
		]
		logger.trace(nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_2]
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += #[DAD11, DAU21, SON11, DAU11]
		]
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(nameOfTestMethod + " - finished without errors")
	}

	// ========== MOVING MEMBERS - SAME POSITION ==========
	/**A father switches his family and stays a father.
	 */
	@Test
	def void testSwitchFamilySamePositionFather() {
		logger.trace(nameOfTestMethod + " - begin")
		this.createOneFamilyBeforeTesting()
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families += this.createFamily(LAST_NAME_2)
		]
		logger.trace(nameOfTestMethod + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val oldFamily = families.findFirst[it.lastName.equals(LAST_NAME_1)]
			val newFamily = families.findFirst[it.lastName.equals(LAST_NAME_2)]
			newFamily.father = oldFamily.father
		]
		logger.trace(nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
			]
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_2
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += #[DAD12, MOM11, SON11, DAU11]
		]
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(nameOfTestMethod + " - finished without errors")
	}

	/**A mother switches her family and stays a mother.
	 */
	@Test
	def void testSwitchFamilySamePositionMother() {
		logger.trace(nameOfTestMethod + " - begin")
		this.createOneFamilyBeforeTesting()
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families += this.createFamily(LAST_NAME_2)
		]
		logger.trace(nameOfTestMethod + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val oldFamily = families.findFirst[it.lastName.equals(LAST_NAME_1)]
			val newFamily = families.findFirst[it.lastName.equals(LAST_NAME_2)]
			newFamily.mother = oldFamily.mother
		]
		logger.trace(nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
			]
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_2
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += #[DAD11, MOM12, SON11, DAU11]
		]
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(nameOfTestMethod + " - finished without errors")
	}

	/**A son switches his family and stays a son.
	 */
	@Test
	def void testSwitchFamilySamePositionSon() {
		logger.trace(nameOfTestMethod + " - begin")
		this.createOneFamilyBeforeTesting()
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families += this.createFamily(LAST_NAME_2)
		]
		logger.trace(nameOfTestMethod + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val oldFamily = families.findFirst[it.lastName.equals(LAST_NAME_1)]
			val newFamily = families.findFirst[it.lastName.equals(LAST_NAME_2)]
			newFamily.sons += oldFamily.sons.findFirst[son|son.firstName.equals(FIRST_SON_1)]
		]
		logger.trace(nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
			]
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_2
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += #[DAD11, MOM11, SON12, DAU11]
		]
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(nameOfTestMethod + " - finished without errors")
	}

	/**A daughter switches her family and stays a daughter.
	 */
	@Test
	def void testSwitchFamilySamePositionDaughter() {
		logger.trace(nameOfTestMethod + " - begin")
		this.createOneFamilyBeforeTesting()
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families += this.createFamily(LAST_NAME_2)
		]
		logger.trace(nameOfTestMethod + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val oldFamily = families.findFirst[it.lastName.equals(LAST_NAME_1)]
			val newFamily = families.findFirst[it.lastName.equals(LAST_NAME_2)]
			newFamily.daughters += oldFamily.daughters.findFirst[daughter|daughter.firstName.equals(FIRST_DAU_1)]
		]
		logger.trace(nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
			]
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_2
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += #[DAD11, MOM11, SON11, DAU12]
		]
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(nameOfTestMethod + " - finished without errors")
	}

	/**Test to move around members of the families repeatedly to check if correspondences are maintained correctly.
	 */
	@Test
	def void testRepeatedlyMovingFathersBetweenFamilies() {
		//Defining some additional values for this test
		val String first_mom_3 = "Beate"
		val Male dad13 = PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_DAD_1 + " " + LAST_NAME_3]
		val Female mom33 = PersonsFactory.eINSTANCE.createFemale => [fullName = first_mom_3 + " " + LAST_NAME_3]

		logger.trace(nameOfTestMethod + " - begin")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val family1 = createFamily(LAST_NAME_1)
			val family2 = createFamily(LAST_NAME_2)
			val family3 = createFamily(LAST_NAME_3)
			families += #[family1, family2, family3]
			family1.father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
			family2.father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_2]

			family1.mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
			family2.mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_2]
			family3.mother = FamiliesFactory.eINSTANCE.createMember => [firstName = first_mom_3]
		]
		val PersonRegister expectedPersonRegisterAfterPreparation = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += #[DAD11, DAD22, MOM11, MOM22, mom33]
		]
		assertCorrectPersonRegister(expectedPersonRegisterAfterPreparation)
		logger.trace(nameOfTestMethod + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val family1 = families.findFirst[it.lastName.equals(LAST_NAME_1)]
			val family2 = families.findFirst[it.lastName.equals(LAST_NAME_2)]
			val family3 = families.findFirst[it.lastName.equals(LAST_NAME_3)]

			family3.father = family2.father
			family2.father = family1.father
			family1.father = family3.father
			family3.father = family2.father
			family2.father = family1.father
		]
		logger.trace(nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
			]
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_2
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_2]
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_2]
			]
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_3
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = first_mom_3]
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += #[dad13, DAD22, MOM11, MOM22, mom33]
		]
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(nameOfTestMethod + " - finished without errors")
	}

	// ========== MOVING MEMBERS - DIFFERENT POSITION ==========
	/**A son switches his family and becomes a father.
	 */
	@Test
	def void testSwitchFamilyDifferentPositionSonToFather() {
		logger.trace(nameOfTestMethod + " - begin")
		this.createOneFamilyBeforeTesting()
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families += this.createFamily(LAST_NAME_2)
		]
		logger.trace(nameOfTestMethod + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val oldFamily = families.findFirst[it.lastName.equals(LAST_NAME_1)]
			val newFamily = families.findFirst[it.lastName.equals(LAST_NAME_2)]
			newFamily.father = oldFamily.sons.findFirst[son|son.firstName.equals(FIRST_SON_1)]
		]
		logger.trace(nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
			]
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_2
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += #[DAD11, MOM11, SON12, DAU11]
		]
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(nameOfTestMethod + " - finished without errors")
	}

	/**A son switches his family and becomes a father.
	 * Version in which the son was the last member in his family before. Therefore, the old family
	 * of the son will be deleted as he becomes the father in the new family.
	 */
	@Test
	def void testSwitchFamilyDifferentPositionLonlySonToFather() {
		logger.trace(nameOfTestMethod + " - begin")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
			]
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_2
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_2]
			]
		]
		val PersonRegister expectedPersonRegisterAfterPreparation = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += #[SON11, MOM22]
		]
		assertCorrectPersonRegister(expectedPersonRegisterAfterPreparation)
		logger.trace(nameOfTestMethod + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val oldFamily = families.findFirst[it.lastName.equals(LAST_NAME_1)]
			val newFamily = families.findFirst[it.lastName.equals(LAST_NAME_2)]
			newFamily.father = oldFamily.sons.findFirst[son|son.firstName.equals(FIRST_SON_1)]
		]
		logger.trace(nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_2
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_2]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += #[SON12, MOM22]
		]
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(nameOfTestMethod + " - finished without errors")
	}

	/**A daughter switches her family and becomes a mother.
	 */
	@Test
	def void testSwitchFamilyDifferentPositionDaughterToMother() {
		logger.trace(nameOfTestMethod + " - begin")
		this.createOneFamilyBeforeTesting()
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families += this.createFamily(LAST_NAME_2)
		]
		logger.trace(nameOfTestMethod + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val oldFamily = families.findFirst[it.lastName.equals(LAST_NAME_1)]
			val newFamily = families.findFirst[it.lastName.equals(LAST_NAME_2)]
			newFamily.mother = oldFamily.daughters.findFirst[daughter|daughter.firstName.equals(FIRST_DAU_1)]
		]
		logger.trace(nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
			]
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_2
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += #[DAD11, MOM11, SON11, DAU12]
		]
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(nameOfTestMethod + " - finished without errors")
	}

	/**A daughter switches her family and becomes a mother.
	 * Version in which the daughter was the last member in her family before. Therefore, the old family
	 * of the daughter will be deleted as she becomes the mother in the new family.
	 */
	@Test
	def void testSwitchFamilyDifferentPositionLonlyDaughterToMother() {
		logger.trace(nameOfTestMethod + " - begin")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
			]
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_2
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_2]
			]
		]
		val PersonRegister expectedPersonRegisterAfterPreparation = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += #[DAU11, DAD22]
		]
		assertCorrectPersonRegister(expectedPersonRegisterAfterPreparation)
		logger.trace(nameOfTestMethod + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val oldFamily = families.findFirst[it.lastName.equals(LAST_NAME_1)]
			val newFamily = families.findFirst[it.lastName.equals(LAST_NAME_2)]
			newFamily.mother = oldFamily.daughters.findFirst[daughter|daughter.firstName.equals(FIRST_DAU_1)]
		]
		logger.trace(nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_2
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_2]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += #[DAD22, DAU12]
		]
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(nameOfTestMethod + " - finished without errors")
	}

	/**A father switches his family and becomes a son.
	 */
	@Test
	def void testSwitchFamilyDifferentPositionFatherToSon() {
		logger.trace(nameOfTestMethod + " - begin")
		this.createOneFamilyBeforeTesting()
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families += this.createFamily(LAST_NAME_2)
		]
		logger.trace(nameOfTestMethod + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val oldFamily = families.findFirst[it.lastName.equals(LAST_NAME_1)]
			val newFamily = families.findFirst[it.lastName.equals(LAST_NAME_2)]
			newFamily.sons += oldFamily.father
		]
		logger.trace(nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
			]
			families += FamiliesFactory.eINSTANCE.createFamily => [
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
				lastName = LAST_NAME_2
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += #[DAD12, MOM11, SON11, DAU11]
		]
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(nameOfTestMethod + " - finished without errors")
	}

	/**A mother switches her family and becomes a daughter.
	 */
	@Test
	def void testSwitchFamilyDifferentPositionMotherToDaughter() {
		logger.trace(nameOfTestMethod + " - begin")
		this.createOneFamilyBeforeTesting()
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families += this.createFamily(LAST_NAME_2)
		]
		logger.trace(nameOfTestMethod + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val oldFamily = families.findFirst[it.lastName.equals(LAST_NAME_1)]
			val newFamily = families.findFirst[it.lastName.equals(LAST_NAME_2)]
			newFamily.daughters += oldFamily.mother
		]
		logger.trace(nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
			]
			families += FamiliesFactory.eINSTANCE.createFamily => [
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
				lastName = LAST_NAME_2
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += #[DAD11, MOM12, SON11, DAU11]
		]
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(nameOfTestMethod + " - finished without errors")
	}

	// ========== CONFLICTING SEX ==========
	/**Test if exception is thrown when a former mother is assigned to be a father.
	 */
	@Test
	def void testExceptionSexChanges_AssignMotherToFather() {
		logger.trace(nameOfTestMethod + " - begin")
		this.createTwoFamiliesBeforeTesting()
		logger.trace(nameOfTestMethod + " - preparation done")
		val thrownExceptionAssignMotherToFather = assertThrows(UnsupportedOperationException, [
			FamilyRegister.from(FAMILIES_MODEL).propagate [
				val family1 = families.findFirst[family|family.lastName.equals(LAST_NAME_1)]
				val family2 = families.findFirst[family|family.lastName.equals(LAST_NAME_2)]
				family1.father = family2.mother
			]
		])
		logger.trace(nameOfTestMethod + " - propagation done")
		val String expectedMessage = "The position of a male family member can only be assigned to members with no or a male corresponding person."
		assertEquals(thrownExceptionAssignMotherToFather.message, expectedMessage)
		logger.trace(nameOfTestMethod + " - finished without errors")
	}

	/**Test if exception is thrown when a former daughter is assigned to be a son.
	 */
	@Test
	def void testExceptionSexChanges_AssignDaughterToSon() {
		logger.trace(nameOfTestMethod + " - begin")
		this.createTwoFamiliesBeforeTesting()
		logger.trace(nameOfTestMethod + " - preparation done")
		val thrownExceptionAssignDaughterToSon = assertThrows(UnsupportedOperationException, [
			FamilyRegister.from(FAMILIES_MODEL).propagate [
				val family1 = families.findFirst[family|family.lastName.equals(LAST_NAME_1)]
				val family2 = families.findFirst[family|family.lastName.equals(LAST_NAME_2)]
				family1.sons += family2.daughters.findFirst[daughter|daughter.firstName.equals(FIRST_DAU_2)]
			]
		])
		logger.trace(nameOfTestMethod + " - propagation done")
		val String expectedMessage = "The position of a male family member can only be assigned to members with no or a male corresponding person."
		assertEquals(thrownExceptionAssignDaughterToSon.message, expectedMessage)
		logger.trace(nameOfTestMethod + " - finished without errors")
	}

	/**Test if exception is thrown when a former father is assigned to be a mother.
	 */
	@Test
	def void testExceptionSexChanges_AssignFatherToMother() {
		logger.trace(nameOfTestMethod + " - begin")
		this.createTwoFamiliesBeforeTesting()
		logger.trace(nameOfTestMethod + " - preparation done")
		val thrownExceptionAssignFatherToMother = assertThrows(UnsupportedOperationException, [
			FamilyRegister.from(FAMILIES_MODEL).propagate [
				val family1 = families.findFirst[family|family.lastName.equals(LAST_NAME_1)]
				val family2 = families.findFirst[family|family.lastName.equals(LAST_NAME_2)]
				family1.mother = family2.father
			]
		])
		logger.trace(nameOfTestMethod + " - propagation done")
		val String expectedMessage = "The position of a female family member can only be assigned to members with no or a female corresponding person."
		assertEquals(thrownExceptionAssignFatherToMother.message, expectedMessage)
		logger.trace(nameOfTestMethod + " - finished without errors")
	}

	/**Test if exception is thrown when a former son is assigned to be a daughter.
	 */
	@Test
	def void testExceptionSexChanges_AssignSonToDaughter() {
		logger.trace(nameOfTestMethod + " - begin")
		this.createTwoFamiliesBeforeTesting()
		logger.trace(nameOfTestMethod + " - preparation done")
		val thrownExceptionAssignSonToDaughter = assertThrows(UnsupportedOperationException, [
			FamilyRegister.from(FAMILIES_MODEL).propagate [
				val family1 = families.findFirst[family|family.lastName.equals(LAST_NAME_1)]
				val family2 = families.findFirst[family|family.lastName.equals(LAST_NAME_2)]
				family1.daughters += family2.sons.findFirst[son|son.firstName.equals(FIRST_SON_2)]
			]
		])
		logger.trace(nameOfTestMethod + " - propagation done")
		val String expectedMessage = "The position of a female family member can only be assigned to members with no or a female corresponding person."
		assertEquals(thrownExceptionAssignSonToDaughter.message, expectedMessage)
		logger.trace(nameOfTestMethod + " - finished without errors")
	}

	// ========== NAMING ==========
	/**Unescapes escaped escape sequences for linefeed, carriage return and tabulator escape sequences.
	 * Unfortunately, <code>org.junit.jupiter.params.provider.Arguments.of(...)</code> is not able
	 * to deal with escape sequences like </code>\n</code>. Therefore, these sequences have to be escaped for
	 * the ParameterizedTest and then unescaped for the intended use.
	 */
	def String unescapeString(String string) {
		return string.replace("\\n", "\n").replace("\\r", "\r").replace("\\t", "\t")
	}

	@ParameterizedTest(name = "{index} => role={0}, escapedNewName={1}, expectedExceptionMessage={2}")
	@MethodSource("nameAndExceptionProvider")
	def void testExceptionRenamingMemberWithInvalidFirstName(MemberRole role, String escapedNewName, String expectedExceptionMessage) {
		logger.trace(nameOfTestMethod + " - begin")
		val unescapedNewName = if (escapedNewName !== null) unescapeString(escapedNewName) else null
		this.createOneFamilyBeforeTesting()
		logger.trace(nameOfTestMethod + " - preparation done")
		val thrownExceptionSetNullAsFirstName = assertThrows(IllegalStateException, [
			FamilyRegister.from(FAMILIES_MODEL).propagate [
				val family1 = families.findFirst[family|family.lastName.equals(LAST_NAME_1)]
				switch role {
					case MemberRole.Father: family1.father.firstName = unescapedNewName
					case MemberRole.Mother: family1.mother.firstName = unescapedNewName
					case MemberRole.Son: family1.sons.findFirst[son|son.firstName.equals(FIRST_SON_1)].firstName = unescapedNewName
					case MemberRole.Daughter: family1.daughters.findFirst[daughter|daughter.firstName.equals(FIRST_DAU_1)].firstName = unescapedNewName
				}
			]
		])
		logger.trace(nameOfTestMethod + " - propagation done")
		val String expectedMessage = expectedExceptionMessage
		assertEquals(thrownExceptionSetNullAsFirstName.message, expectedMessage)
		logger.trace(nameOfTestMethod + " - finished without errors")
	}

	@ParameterizedTest(name = "{index} => role={0}, escapedNewName={1}, expectedExceptionMessage={2}")
	@MethodSource("nameAndExceptionProvider")
	def void testExceptionCreationOfMemberWithInvalidFirstName(MemberRole role, String escapedNewName, String expectedExceptionMessage) {
		logger.trace(nameOfTestMethod + " - begin")
		val unescapedNewName = if (escapedNewName !== null) unescapeString(escapedNewName) else null
		this.createOneFamilyBeforeTesting()
		logger.trace(nameOfTestMethod + " - preparation done")
		val thrownExceptionSetNullAsFirstName = assertThrows(IllegalStateException, [
			FamilyRegister.from(FAMILIES_MODEL).propagate [
				val family1 = families.findFirst[family|family.lastName.equals(LAST_NAME_1)]
				val Member newMember = FamiliesFactory.eINSTANCE.createMember => [firstName = unescapedNewName]
				switch role {
					case MemberRole.Father: family1.father = newMember
					case MemberRole.Mother: family1.mother = newMember
					case MemberRole.Son: family1.sons += newMember
					case MemberRole.Daughter: family1.daughters += newMember
				}
			]
		])
		logger.trace(nameOfTestMethod + " - propagation done")
		val String expectedMessage = expectedExceptionMessage
		assertEquals(thrownExceptionSetNullAsFirstName.message, expectedMessage)
		logger.trace(nameOfTestMethod + " - finished without errors")
	}

	def static Stream<Arguments> nameAndExceptionProvider() {
		Stream.of(
			Arguments.of(MemberRole.Father, null, FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_NULL),
			Arguments.of(MemberRole.Father, "", FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_WHITESPACE),
			Arguments.of(MemberRole.Father, "\\n\\t\\r", FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_WHITESPACE),
			Arguments.of(MemberRole.Father, FIRST_DAD_1 + "\\n", FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES),
			Arguments.of(MemberRole.Father, FIRST_DAD_1 + "\\t", FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES),
			Arguments.of(MemberRole.Father, FIRST_DAD_1 + "\\r", FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES),
			Arguments.of(MemberRole.Mother, null, FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_NULL),
			Arguments.of(MemberRole.Mother, "", FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_WHITESPACE),
			Arguments.of(MemberRole.Mother, "\\t\\n\\r", FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_WHITESPACE),
			Arguments.of(MemberRole.Mother, FIRST_MOM_1 + "\\n", FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES),
			Arguments.of(MemberRole.Mother, FIRST_MOM_1 + "\\t", FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES),
			Arguments.of(MemberRole.Mother, FIRST_MOM_1 + "\\r", FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES),
			Arguments.of(MemberRole.Son, null, FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_NULL),
			Arguments.of(MemberRole.Son, "", FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_WHITESPACE),
			Arguments.of(MemberRole.Son, "\\n\\t\\r", FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_WHITESPACE),
			Arguments.of(MemberRole.Son, FIRST_SON_1 + "\\n", FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES),
			Arguments.of(MemberRole.Son, FIRST_SON_1 + "\\t", FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES),
			Arguments.of(MemberRole.Son, FIRST_SON_1 + "\\r", FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES),
			Arguments.of(MemberRole.Daughter, null, FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_NULL),
			Arguments.of(MemberRole.Daughter, "", FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_WHITESPACE),
			Arguments.of(MemberRole.Daughter, "\\t\\n\\r", FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_WHITESPACE),
			Arguments.of(MemberRole.Daughter, FIRST_DAU_1 + "\\n", FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES),
			Arguments.of(MemberRole.Daughter, FIRST_DAU_1 + "\\t", FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES),
			Arguments.of(MemberRole.Daughter, FIRST_DAU_1 + "\\r", FamiliesToPersonsHelper.EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES)
		)
	}

	/**Test the creation of a family without a lastname and the correct creation of corresponding
	 * persons without a white space as seperaotr attached to the firstname of the member.
	 */
	def void testCreatingFamilyWithEmptyLastName() {
		logger.trace(nameOfTestMethod + " - begin")
		logger.trace(nameOfTestMethod + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = ""
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
			]
		]
		logger.trace(nameOfTestMethod + " - propagation done")
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_DAD_1]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOM_1]
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON_1]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAU_1]
		]
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(nameOfTestMethod + " - finished without errors")
	}

	// ========== DELETION ==========
	/**Deletes all {@link Family}s with matching lastname from the {@link FamilyRegister}.
	 * All {@link Member}s which were contained in these families will be deleted together
	 * with there corresponding {@link Person}s in the {@link PersonRegister} as well.
	 * If only families without members are deleted, the {@link PersonRegister}
	 * will not be affected.
	 */
	@Test
	def void testDeleteAllFamiliesWithMatchingName() {
		logger.trace(nameOfTestMethod + " - begin")
		this.createOneFamilyBeforeTesting()
		logger.trace(nameOfTestMethod + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families.removeIf([it.lastName.equals(LAST_NAME_1)])
		]
		logger.trace(nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister()
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister()
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(nameOfTestMethod + " - finished without errors")
	}

	/**Deletes the {@link FamilyRegister} with all its contents which leads to
	 * the deletion of the corresponding {@link PersonRegister} with all its contents.
	 */
	@Test
	def void testDeleteFamilyRegister() {
		logger.trace(nameOfTestMethod + " - begin")
		this.createOneFamilyBeforeTesting()
		logger.trace(nameOfTestMethod + " - preparation done")
		resourceAt(FAMILIES_MODEL).propagate[contents.clear()]
		logger.trace(nameOfTestMethod + " - propagation done")
		assertEquals(0, resourceAt(FAMILIES_MODEL).contents.size())
		assertEquals(0, resourceAt(PERSONS_MODEL).contents.size())
		assertThat(resourceAt(FAMILIES_MODEL), not(exists))
		assertThat(resourceAt(PERSONS_MODEL), not(exists))
		logger.trace(nameOfTestMethod + " - finished without errors")
	}
}