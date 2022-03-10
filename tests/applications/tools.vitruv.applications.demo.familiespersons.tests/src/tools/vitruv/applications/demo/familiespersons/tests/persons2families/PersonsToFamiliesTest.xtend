package tools.vitruv.applications.demo.familiespersons.tests.persons2families

import edu.kit.ipd.sdq.metamodels.families.FamiliesFactory
import edu.kit.ipd.sdq.metamodels.families.Family
import edu.kit.ipd.sdq.metamodels.families.FamilyRegister
import edu.kit.ipd.sdq.metamodels.families.Member
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
import tools.vitruv.applications.demo.familiespersons.persons2families.PersonsToFamiliesChangePropagationSpecification
import tools.vitruv.applications.demo.familiespersons.persons2families.PersonsToFamiliesHelper
import tools.vitruv.domains.demo.families.FamiliesDomainProvider
import tools.vitruv.domains.demo.persons.PersonsDomainProvider
import tools.vitruv.testutils.TestUserInteraction.MultipleChoiceInteractionDescription
import tools.vitruv.testutils.VitruvApplicationTest
import tools.vitruv.testutils.domains.DomainUtil

import static org.hamcrest.CoreMatchers.*
import static org.hamcrest.MatcherAssert.assertThat
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertThrows
import static org.junit.jupiter.api.Assertions.assertTrue
import static tools.vitruv.testutils.matchers.ModelMatchers.*

enum PositionPreference {
	Parent,
	Child
}

enum FamilyPreference {
	New,
	Existing
}
/**Test to validate the transfer of changes from the PersonModel to the FamilyModel.
 * @author Dirk Neumann
 */
class PersonsToFamiliesTest extends VitruvApplicationTest {
	static val logger = Logger.getLogger(PersonsToFamiliesTest)
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

	/**Set the correct set of reactions and routines for this test suite
	 */
	override protected getChangePropagationSpecifications() {
		return #[new FamiliesToPersonsChangePropagationSpecification(),
			new PersonsToFamiliesChangePropagationSpecification()]
	}


	var boolean preferParent = false

	def void decideParentOrChild(PositionPreference preference) {
		val String parentChildTitle = "Parent or Child?"
		this.preferParent = preference === PositionPreference.Parent
		userInteraction.onMultipleChoiceSingleSelection[title.equals(parentChildTitle)].respondWithChoiceAt(if (preference === PositionPreference.Parent) 0 else 1)
	}

	def void decideNewOrExistingFamily(FamilyPreference preference) {
		//If we want to insert in a new family, we choose 0 anyway
		//If we choose an existing family, but do not specify in which,
		//we probably want to choose the first one we find which is at index 1,
		//since index 0 is to choose a new family
		decideNewOrExistingFamily(preference, if (preference === FamilyPreference.New) 0 else 1)
	}


	val String newOrExistingFamilyTitle = "New or Existing Family?"

	def void decideNewOrExistingFamily(FamilyPreference preference, int familyIndex) {
		userInteraction
			.onMultipleChoiceSingleSelection[assertFamilyOptions(it)]
			.respondWithChoiceAt(if (preference === FamilyPreference.New) 0 else familyIndex)
	}

	def boolean assertFamilyOptions(MultipleChoiceInteractionDescription interactionDescription) {
		//First option is always a new family
		assertEquals(interactionDescription.choices.get(0), "insert in a new family")
		val tail = interactionDescription.choices.drop(1)
		//There must be a second option otherwise there would not be an interaction
		assertTrue(tail.size > 0)
		val familyName = tail.get(0).split(":").get(0)
		//All other options have to offer families with the same name
		tail.forEach[familyOption|familyOption.split(":").get(0).equals(familyName)]

		if (preferParent) {
			//If we want to insert a parent, each offered family has to not have this kind of parent
			//Therefore all families either must not have a father or must not have a mother
			val noFathers = tail.forall[!it.matches(".*F:.*;.*")]
			val noMothers = tail.forall[!it.matches(".*M:.*;.*")]
			assertTrue(noFathers || noMothers)
		}

		return interactionDescription.title.equals(newOrExistingFamilyTitle)
	}


	/**Before each test a new {@link PersonRegister} is created as starting point.
	 * This is checked by several assertions to ensure correct preconditions for the tests.
	 */
	@BeforeEach
	def void insertRegister(TestInfo testInfo) {
		this.nameOfTestMethod = testInfo.getDisplayName()
		resourceAt(PERSONS_MODEL).propagate[contents += PersonsFactory.eINSTANCE.createPersonRegister]
		assertThat(resourceAt(FAMILIES_MODEL), exists);
		assertEquals(1, resourceAt(FAMILIES_MODEL).contents.size);
		assertEquals(1, resourceAt(FAMILIES_MODEL).allContents.size);
		assertThat(resourceAt(FAMILIES_MODEL).contents.get(0), instanceOf(FamilyRegister));
		assertEquals(0, resourceAt(FAMILIES_MODEL).contents.get(0).eAllContents().size);
	}

	/**Check if the actual {@link FamilyRegister looks like the expected one.
	 */
	def void assertCorrectFamilyRegister(FamilyRegister expectedFamilyRegister) {
		val familyModel = resourceAt(FAMILIES_MODEL)
		assertThat(familyModel, exists)
		assertEquals(1, familyModel.contents.size)
		val familyRegister = familyModel.contents.get(0)
		assertThat(familyRegister, instanceOf(FamilyRegister))
		val FamilyRegister castedFamilyRegister = familyRegister as FamilyRegister
		assertThat(castedFamilyRegister, equalsDeeply(expectedFamilyRegister))
	}

	/**Check if the actual {@link PersonRegister looks like the expected one.
	 */
	def void assertCorrectPersonRegister(PersonRegister expectedPersonRegister) {
		val personModel = resourceAt(PERSONS_MODEL)
		assertThat(personModel, exists)
		assertEquals(1, personModel.contents.size)
		val personRegister = personModel.contents.get(0)
		assertThat(personRegister, instanceOf(PersonRegister))
		val PersonRegister castedPersonRegister = personRegister as PersonRegister
		assertThat(castedPersonRegister, equalsDeeply(expectedPersonRegister))
	}

	/**Create two families which then build the starting point for other tests
	 * in which families in the {@link FamilyRegister} are needed.
	 */
	@Test
	def void createFamiliesForTesting() {
		decideParentOrChild(PositionPreference.Parent)
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_DAD_1 + " " + LAST_NAME_1]
		]
		decideParentOrChild(PositionPreference.Parent)
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOM_1 + " " + LAST_NAME_2]
		]
		decideParentOrChild(PositionPreference.Child)
		decideNewOrExistingFamily(FamilyPreference.Existing, 1)
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON_1 + " " + LAST_NAME_2]
		]
		decideParentOrChild(PositionPreference.Child)
		decideNewOrExistingFamily(FamilyPreference.Existing, 1)
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAU_1 + " " + LAST_NAME_1]
		]
		userInteraction.assertAllInteractionsOccurred()

		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += #[
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_1
					father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
					daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_2
					mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
					sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_DAD_1 + " " + LAST_NAME_1]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOM_1 + " " + LAST_NAME_2]
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON_1 + " " + LAST_NAME_2]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAU_1 + " " + LAST_NAME_1]
		]
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
	}

	// =====================================
	// CREATE MALE
	// =====================================
	// ========== FATHER ==========
	@Test
	def void testCreateMale_Father_EmptyRegister() {
		logger.trace(this.nameOfTestMethod + " - begin")
		// Father
		decideParentOrChild(PositionPreference.Parent)
		logger.trace(this.nameOfTestMethod + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_DAD_1 + " " + LAST_NAME_1]
		]
		userInteraction.assertAllInteractionsOccurred()
		logger.trace(this.nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_DAD_1 + " " + LAST_NAME_1]
		]
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(this.nameOfTestMethod + " - finished without errors")
	}

	@Test
	def void testCreateMale_Father_AutomaticNewFamily() {
		logger.trace(this.nameOfTestMethod + " - begin")
		createFamiliesForTesting()
		// Father
		decideParentOrChild(PositionPreference.Parent)
		logger.trace(this.nameOfTestMethod + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_2 + " " + LAST_NAME_1
			]
		]
		userInteraction.assertAllInteractionsOccurred()
		logger.trace(this.nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += #[
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_1
					father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
					daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_2
					mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
					sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_1
					father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_2]
				]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_DAD_1 + " " + LAST_NAME_1]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOM_1 + " " + LAST_NAME_2]
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON_1 + " " + LAST_NAME_2]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAU_1 + " " + LAST_NAME_1]
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_DAD_2 + " " + LAST_NAME_1]
		]
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(this.nameOfTestMethod + " - finished without errors")
	}

	@Test
	def void testCreateMale_Father_ChoosingNewFamily() {
		logger.trace(this.nameOfTestMethod + " - begin")
		createFamiliesForTesting()
		// Father
		decideParentOrChild(PositionPreference.Parent)
		// New Family
		decideNewOrExistingFamily(FamilyPreference.New)
		logger.trace(this.nameOfTestMethod + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_2 + " " + LAST_NAME_2
			]
		]
		userInteraction.assertAllInteractionsOccurred()
		logger.trace(this.nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += #[
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_1
					father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
					daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_2
					mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
					sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_2
					father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_2]
				]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister()
		expectedPersonRegister.persons.addAll(
			#[
				PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_DAD_1 + " " + LAST_NAME_1],
				PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOM_1 + " " + LAST_NAME_2],
				PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON_1 + " " + LAST_NAME_2],
				PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAU_1 + " " + LAST_NAME_1],
				PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_DAD_2 + " " + LAST_NAME_2]
			]
		)
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(this.nameOfTestMethod + " - finished without errors")
	}

	@Test
	def void testCreateMale_Father_ChoosingExistingFamily() {
		logger.trace(this.nameOfTestMethod + " - begin")
		createFamiliesForTesting()
		// Father
		decideParentOrChild(PositionPreference.Parent)
		// First existing Family
		decideNewOrExistingFamily(FamilyPreference.Existing)
		logger.trace(this.nameOfTestMethod + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_2 + " " + LAST_NAME_2
			]
		]
		userInteraction.assertAllInteractionsOccurred()
		logger.trace(this.nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += #[
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_1
					father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
					daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_2
					father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_2]
					mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
					sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister()
		expectedPersonRegister.persons.addAll(
			#[
				PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_DAD_1 + " " + LAST_NAME_1],
				PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOM_1 + " " + LAST_NAME_2],
				PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON_1 + " " + LAST_NAME_2],
				PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAU_1 + " " + LAST_NAME_1],
				PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_DAD_2 + " " + LAST_NAME_2]
			]
		)
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(this.nameOfTestMethod + " - finished without errors")
	}

	@Test
	def void testRename_Father_AutomaticNewFamily() {
		logger.trace(this.nameOfTestMethod + " - begin")
		createFamiliesForTesting()
		// Father
		decideParentOrChild(PositionPreference.Parent)
		logger.trace(this.nameOfTestMethod + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searchedDad = persons.findFirst[person|person.fullName.equals(FIRST_DAD_1 + " " + LAST_NAME_1)]
			searchedDad.fullName = FIRST_DAD_1 + " " + LAST_NAME_3
		]
		logger.trace(this.nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += #[
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_1
					daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_2
					mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
					sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_3
					father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
				]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_DAD_1 + " " + LAST_NAME_3]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOM_1 + " " + LAST_NAME_2]
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON_1 + " " + LAST_NAME_2]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAU_1 + " " + LAST_NAME_1]
		]
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(this.nameOfTestMethod + " - finished without errors")
	}

	@Test
	def void testRename_Father_ChoosingNewFamily() {
		logger.trace(this.nameOfTestMethod + " - begin")
		createFamiliesForTesting()
		// Father
		decideParentOrChild(PositionPreference.Parent)
		// New Family
		decideNewOrExistingFamily(FamilyPreference.New)
		logger.trace(this.nameOfTestMethod + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searchedDad = persons.findFirst[person|person.fullName.equals(FIRST_DAD_1 + " " + LAST_NAME_1)]
			searchedDad.fullName = FIRST_DAD_1 + " " + LAST_NAME_2
		]
		userInteraction.assertAllInteractionsOccurred()
		logger.trace(this.nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += #[
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_1
					daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_2
					mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
					sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_2
					father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
				]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_DAD_1 + " " + LAST_NAME_2]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOM_1 + " " + LAST_NAME_2]
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON_1 + " " + LAST_NAME_2]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAU_1 + " " + LAST_NAME_1]
		]
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(this.nameOfTestMethod + " - finished without errors")
	}

	@Test
	def void testRename_Father_ChoosingExistingFamily() {
		logger.trace(this.nameOfTestMethod + " - begin")
		createFamiliesForTesting()
		// Father
		decideParentOrChild(PositionPreference.Parent)
		// First existing Family
		decideNewOrExistingFamily(FamilyPreference.Existing)
		logger.trace(this.nameOfTestMethod + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searchedDad = persons.findFirst[person|person.fullName.equals(FIRST_DAD_1 + " " + LAST_NAME_1)]
			searchedDad.fullName = FIRST_DAD_1 + " " + LAST_NAME_2
		]
		userInteraction.assertAllInteractionsOccurred()
		logger.trace(this.nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += #[
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_1
					daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_2
					father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
					mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
					sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_DAD_1 + " " + LAST_NAME_2]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOM_1 + " " + LAST_NAME_2]
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON_1 + " " + LAST_NAME_2]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAU_1 + " " + LAST_NAME_1]
		]
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(this.nameOfTestMethod + " - finished without errors")
	}

	// ========== SON ==========
	@Test
	def void testCreateMale_Son_EmptyRegister() {
		logger.trace(this.nameOfTestMethod + " - begin")
		// Son
		decideParentOrChild(PositionPreference.Child)
		logger.trace(this.nameOfTestMethod + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON_1 + " " + LAST_NAME_1]
		]
		userInteraction.assertAllInteractionsOccurred()
		logger.trace(this.nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON_1 + " " + LAST_NAME_1]
		]
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(this.nameOfTestMethod + " - finished without errors")
	}

	@Test
	def void testCreateMale_Son_AutomaticNewFamily() {
		logger.trace(this.nameOfTestMethod + " - begin")
		createFamiliesForTesting()
		// Son
		decideParentOrChild(PositionPreference.Child)
		logger.trace(this.nameOfTestMethod + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_2 + " " + LAST_NAME_3
			]
		]
		userInteraction.assertAllInteractionsOccurred()
		logger.trace(this.nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += #[
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_1
					father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
					daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_2
					mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
					sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_3
					sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_2]
				]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister()
		expectedPersonRegister.persons.addAll(
			#[
				PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_DAD_1 + " " + LAST_NAME_1],
				PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOM_1 + " " + LAST_NAME_2],
				PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON_1 + " " + LAST_NAME_2],
				PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAU_1 + " " + LAST_NAME_1],
				PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON_2 + " " + LAST_NAME_3]
			]
		)
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(this.nameOfTestMethod + " - finished without errors")
	}

	@Test
	def void testCreateMale_Son_ChoosingNewFamily() {
		logger.trace(this.nameOfTestMethod + " - begin")
		createFamiliesForTesting()
		// Son
		decideParentOrChild(PositionPreference.Child)
		// New Family
		decideNewOrExistingFamily(FamilyPreference.New)
		logger.trace(this.nameOfTestMethod + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_2 + " " + LAST_NAME_1
			]
		]
		userInteraction.assertAllInteractionsOccurred()
		logger.trace(this.nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += #[
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_1
					father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
					daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_2
					mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
					sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_1
					sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_2]
				]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister()
		expectedPersonRegister.persons.addAll(
			#[
				PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_DAD_1 + " " + LAST_NAME_1],
				PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOM_1 + " " + LAST_NAME_2],
				PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON_1 + " " + LAST_NAME_2],
				PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAU_1 + " " + LAST_NAME_1],
				PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON_2 + " " + LAST_NAME_1]
			]
		)
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(this.nameOfTestMethod + " - finished without errors")
	}

	@Test
	def void testCreateMale_Son_ChoosingExistingFamily() {
		logger.trace(this.nameOfTestMethod + " - begin")
		createFamiliesForTesting()
		// Son
		decideParentOrChild(PositionPreference.Child)
		// First existing Family
		decideNewOrExistingFamily(FamilyPreference.Existing)
		logger.trace(this.nameOfTestMethod + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_2 + " " + LAST_NAME_2
			]
		]
		userInteraction.assertAllInteractionsOccurred()
		logger.trace(this.nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += #[
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_1
					father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
					daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_2
					mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
					sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
					sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_2]
				]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister()
		expectedPersonRegister.persons.addAll(
			#[
				PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_DAD_1 + " " + LAST_NAME_1],
				PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOM_1 + " " + LAST_NAME_2],
				PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON_1 + " " + LAST_NAME_2],
				PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAU_1 + " " + LAST_NAME_1],
				PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON_2 + " " + LAST_NAME_2]
			]
		)
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(this.nameOfTestMethod + " - finished without errors")
	}

	@Test
	def void testRename_Son_AutomaticNewFamily() {
		logger.trace(this.nameOfTestMethod + " - begin")
		createFamiliesForTesting()
		// Son
		decideParentOrChild(PositionPreference.Child)
		logger.trace(this.nameOfTestMethod + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searchedSon = persons.findFirst[person|person.fullName.equals(FIRST_SON_1 + " " + LAST_NAME_2)]
			searchedSon.fullName = FIRST_SON_1 + " " + LAST_NAME_3
		]
		logger.trace(this.nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += #[
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_1
					father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
					daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_2
					mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_3
					sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_DAD_1 + " " + LAST_NAME_1]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOM_1 + " " + LAST_NAME_2]
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON_1 + " " + LAST_NAME_3]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAU_1 + " " + LAST_NAME_1]
		]
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(this.nameOfTestMethod + " - finished without errors")
	}

	@Test
	def void testRename_Son_ChoosingNewFamily() {
		logger.trace(this.nameOfTestMethod + " - begin")
		createFamiliesForTesting()
		// Son
		decideParentOrChild(PositionPreference.Child)
		// New Family
		decideNewOrExistingFamily(FamilyPreference.New)
		logger.trace(this.nameOfTestMethod + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searchedSon = persons.findFirst[person|person.fullName.equals(FIRST_SON_1 + " " + LAST_NAME_2)]
			searchedSon.fullName = FIRST_SON_1 + " " + LAST_NAME_1
		]
		userInteraction.assertAllInteractionsOccurred()
		logger.trace(this.nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += #[
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_1
					father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
					daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_2
					mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_1
					sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_DAD_1 + " " + LAST_NAME_1]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOM_1 + " " + LAST_NAME_2]
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON_1 + " " + LAST_NAME_1]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAU_1 + " " + LAST_NAME_1]
		]
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(this.nameOfTestMethod + " - finished without errors")
	}

	@Test
	def void testRename_Son_ChoosingExistingFamily() {
		logger.trace(this.nameOfTestMethod + " - begin")
		createFamiliesForTesting()
		// Son
		decideParentOrChild(PositionPreference.Child)
		// First existing Family
		decideNewOrExistingFamily(FamilyPreference.Existing)
		logger.trace(this.nameOfTestMethod + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searchedSon = persons.findFirst[person|person.fullName.equals(FIRST_SON_1 + " " + LAST_NAME_2)]
			searchedSon.fullName = FIRST_SON_1 + " " + LAST_NAME_1
		]
		userInteraction.assertAllInteractionsOccurred()
		logger.trace(this.nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += #[
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_1
					father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
					sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
					daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_2
					mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
				]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_DAD_1 + " " + LAST_NAME_1]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOM_1 + " " + LAST_NAME_2]
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON_1 + " " + LAST_NAME_1]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAU_1 + " " + LAST_NAME_1]
		]
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(this.nameOfTestMethod + " - finished without errors")
	}

	// =====================================
	// CREATE FEMALE
	// =====================================
	// ========== MOTHER ==========
	@Test
	def void testCreateMale_Mother_EmptyRegister() {
		logger.trace(this.nameOfTestMethod + " - begin")
		// Mother
		decideParentOrChild(PositionPreference.Parent)
		logger.trace(this.nameOfTestMethod + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOM_1 + " " + LAST_NAME_1]
		]
		userInteraction.assertAllInteractionsOccurred()
		logger.trace(this.nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOM_1 + " " + LAST_NAME_1]
		]
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(this.nameOfTestMethod + " - finished without errors")
	}

	@Test
	def void testCreateFemale_Mother_AutomaticNewFamily() {
		logger.trace(this.nameOfTestMethod + " - begin")
		createFamiliesForTesting()
		// Mother
		decideParentOrChild(PositionPreference.Parent)
		logger.trace(this.nameOfTestMethod + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_2 + " " + LAST_NAME_2
			]
		]
		userInteraction.assertAllInteractionsOccurred()
		logger.trace(this.nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += #[
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_1
					father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
					daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_2
					mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
					sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_2
					mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_2]
				]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister()
		expectedPersonRegister.persons.addAll(
			#[
				PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_DAD_1 + " " + LAST_NAME_1],
				PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOM_1 + " " + LAST_NAME_2],
				PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON_1 + " " + LAST_NAME_2],
				PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAU_1 + " " + LAST_NAME_1],
				PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOM_2 + " " + LAST_NAME_2]
			]
		)
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(this.nameOfTestMethod + " - finished without errors")
	}

	@Test
	def void testCreateFemale_Mother_ChoosingNewFamily() {
		logger.trace(this.nameOfTestMethod + " - begin")
		createFamiliesForTesting()
		// Mother
		decideParentOrChild(PositionPreference.Parent)
		// New Family
		decideNewOrExistingFamily(FamilyPreference.New)
		logger.trace(this.nameOfTestMethod + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_2 + " " + LAST_NAME_1
			]
		]
		userInteraction.assertAllInteractionsOccurred()
		logger.trace(this.nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += #[
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_1
					father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
					daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_2
					mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
					sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_1
					mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_2]
				]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister()
		expectedPersonRegister.persons.addAll(
			#[
				PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_DAD_1 + " " + LAST_NAME_1],
				PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOM_1 + " " + LAST_NAME_2],
				PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON_1 + " " + LAST_NAME_2],
				PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAU_1 + " " + LAST_NAME_1],
				PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOM_2 + " " + LAST_NAME_1]
			]
		)
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(this.nameOfTestMethod + " - finished without errors")
	}

	@Test
	def void testCreateFemale_Mother_ChoosingExistingFamily() {
		logger.trace(this.nameOfTestMethod + " - begin")
		createFamiliesForTesting()
		// Mother
		decideParentOrChild(PositionPreference.Parent)
		// First existing Family
		decideNewOrExistingFamily(FamilyPreference.Existing)
		logger.trace(this.nameOfTestMethod + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_2 + " " + LAST_NAME_1
			]
		]
		userInteraction.assertAllInteractionsOccurred()
		logger.trace(this.nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += #[
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_1
					father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
					mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_2]
					daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_2
					mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
					sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister()
		expectedPersonRegister.persons.addAll(
			#[
				PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_DAD_1 + " " + LAST_NAME_1],
				PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOM_1 + " " + LAST_NAME_2],
				PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON_1 + " " + LAST_NAME_2],
				PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAU_1 + " " + LAST_NAME_1],
				PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOM_2 + " " + LAST_NAME_1]
			]
		)
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(this.nameOfTestMethod + " - finished without errors")
	}

	@Test
	def void testRename_Mother_AutomaticNewFamily() {
		logger.trace(this.nameOfTestMethod + " - begin")
		createFamiliesForTesting()
		// Mother
		decideParentOrChild(PositionPreference.Parent)
		logger.trace(this.nameOfTestMethod + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searchedMom = persons.findFirst[person|person.fullName.equals(FIRST_MOM_1 + " " + LAST_NAME_2)]
			searchedMom.fullName = FIRST_MOM_1 + " " + LAST_NAME_3
		]
		logger.trace(this.nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += #[
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_1
					father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
					daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_2
					sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_3
					mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
				]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_DAD_1 + " " + LAST_NAME_1]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOM_1 + " " + LAST_NAME_3]
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON_1 + " " + LAST_NAME_2]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAU_1 + " " + LAST_NAME_1]
		]
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(this.nameOfTestMethod + " - finished without errors")
	}

	@Test
	def void testRename_Mother_ChoosingNewFamily() {
		logger.trace(this.nameOfTestMethod + " - begin")
		createFamiliesForTesting()
		// Mother
		decideParentOrChild(PositionPreference.Parent)
		// New Family
		decideNewOrExistingFamily(FamilyPreference.New)
		logger.trace(this.nameOfTestMethod + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searchedMom = persons.findFirst[person|person.fullName.equals(FIRST_MOM_1 + " " + LAST_NAME_2)]
			searchedMom.fullName = FIRST_MOM_1 + " " + LAST_NAME_1
		]
		userInteraction.assertAllInteractionsOccurred()
		logger.trace(this.nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += #[
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_1
					father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
					daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_2
					sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_1
					mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
				]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_DAD_1 + " " + LAST_NAME_1]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOM_1 + " " + LAST_NAME_1]
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON_1 + " " + LAST_NAME_2]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAU_1 + " " + LAST_NAME_1]
		]
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(this.nameOfTestMethod + " - finished without errors")
	}

	@Test
	def void testRename_Mother_ChoosingExistingFamily() {
		logger.trace(this.nameOfTestMethod + " - begin")
		createFamiliesForTesting()
		// Mother
		decideParentOrChild(PositionPreference.Parent)
		// First existing Family
		decideNewOrExistingFamily(FamilyPreference.Existing)
		logger.trace(this.nameOfTestMethod + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searchedMom = persons.findFirst[person|person.fullName.equals(FIRST_MOM_1 + " " + LAST_NAME_2)]
			searchedMom.fullName = FIRST_MOM_1 + " " + LAST_NAME_1
		]
		userInteraction.assertAllInteractionsOccurred()
		logger.trace(this.nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += #[
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_1
					father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
					mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
					daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_2
					sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_DAD_1 + " " + LAST_NAME_1]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOM_1 + " " + LAST_NAME_1]
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON_1 + " " + LAST_NAME_2]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAU_1 + " " + LAST_NAME_1]
		]
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(this.nameOfTestMethod + " - finished without errors")
	}

	// ========== DAUGHTHER ==========
	@Test
	def void testCreateMale_Daughter_EmptyRegister() {
		logger.trace(this.nameOfTestMethod + " - begin")
		// Daugther
		decideParentOrChild(PositionPreference.Child)
		logger.trace(this.nameOfTestMethod + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAU_1 + " " + LAST_NAME_1]
		]
		userInteraction.assertAllInteractionsOccurred()
		logger.trace(this.nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAU_1 + " " + LAST_NAME_1]
		]
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(this.nameOfTestMethod + " - finished without errors")
	}

	@Test
	def void testCreateFemale_Daughter_AutomaticNewFamily() {
		logger.trace(this.nameOfTestMethod + " - begin")
		createFamiliesForTesting()
		// Daugther
		decideParentOrChild(PositionPreference.Child)
		logger.trace(this.nameOfTestMethod + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAU_2 + " " + LAST_NAME_3
			]
		]
		userInteraction.assertAllInteractionsOccurred()
		logger.trace(this.nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += #[
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_1
					father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
					daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_2
					mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
					sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_3
					daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_2]
				]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister()
		expectedPersonRegister.persons.addAll(
			#[
				PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_DAD_1 + " " + LAST_NAME_1],
				PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOM_1 + " " + LAST_NAME_2],
				PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON_1 + " " + LAST_NAME_2],
				PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAU_1 + " " + LAST_NAME_1],
				PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAU_2 + " " + LAST_NAME_3]
			]
		)
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(this.nameOfTestMethod + " - finished without errors")
	}

	@Test
	def void testCreateFemale_Daughter_ChoosingNewFamily() {
		logger.trace(this.nameOfTestMethod + " - begin")
		createFamiliesForTesting()
		// Daugther
		decideParentOrChild(PositionPreference.Child)
		// New Family
		decideNewOrExistingFamily(FamilyPreference.New)
		logger.trace(this.nameOfTestMethod + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAU_2 + " " + LAST_NAME_1
			]
		]
		userInteraction.assertAllInteractionsOccurred()
		logger.trace(this.nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += #[
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_1
					father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
					daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_2
					mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
					sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_1
					daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_2]
				]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister()
		expectedPersonRegister.persons.addAll(
			#[
				PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_DAD_1 + " " + LAST_NAME_1],
				PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOM_1 + " " + LAST_NAME_2],
				PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON_1 + " " + LAST_NAME_2],
				PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAU_1 + " " + LAST_NAME_1],
				PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAU_2 + " " + LAST_NAME_1]
			]
		)
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(this.nameOfTestMethod + " - finished without errors")
	}

	@Test
	def void testCreateFemale_Daughter_ChoosingExistingFamily() {
		logger.trace(this.nameOfTestMethod + " - begin")
		createFamiliesForTesting()
		// Daugther
		decideParentOrChild(PositionPreference.Child)
		// First existing Family
		decideNewOrExistingFamily(FamilyPreference.Existing)
		logger.trace(this.nameOfTestMethod + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAU_2 + " " + LAST_NAME_2
			]
		]
		userInteraction.assertAllInteractionsOccurred()
		logger.trace(this.nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += #[
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_1
					father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
					daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_2
					mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
					sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
					daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_2]
				]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister()
		expectedPersonRegister.persons.addAll(
			#[
				PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_DAD_1 + " " + LAST_NAME_1],
				PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOM_1 + " " + LAST_NAME_2],
				PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON_1 + " " + LAST_NAME_2],
				PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAU_1 + " " + LAST_NAME_1],
				PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAU_2 + " " + LAST_NAME_2]
			]
		)
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(this.nameOfTestMethod + " - finished without errors")
	}

	@Test
	def void testRename_Daughter_AutomaticNewFamily() {
		logger.trace(this.nameOfTestMethod + " - begin")
		createFamiliesForTesting()
		// Daugther
		decideParentOrChild(PositionPreference.Child)
		logger.trace(this.nameOfTestMethod + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searchedDaughter = persons.findFirst[person|person.fullName.equals(FIRST_DAU_1 + " " + LAST_NAME_1)]
			searchedDaughter.fullName = FIRST_DAU_1 + " " + LAST_NAME_3
		]
		logger.trace(this.nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += #[
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_1
					father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_2
					mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
					sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_3
					daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
				]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_DAD_1 + " " + LAST_NAME_1]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOM_1 + " " + LAST_NAME_2]
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON_1 + " " + LAST_NAME_2]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAU_1 + " " + LAST_NAME_3]
		]
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(this.nameOfTestMethod + " - finished without errors")
	}

	@Test
	def void testRename_Daughter_ChoosingNewFamily() {
		logger.trace(this.nameOfTestMethod + " - begin")
		createFamiliesForTesting()
		// Daugther
		decideParentOrChild(PositionPreference.Child)
		// New Family
		decideNewOrExistingFamily(FamilyPreference.New)
		logger.trace(this.nameOfTestMethod + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searchedDaughter = persons.findFirst[person|person.fullName.equals(FIRST_DAU_1 + " " + LAST_NAME_1)]
			searchedDaughter.fullName = FIRST_DAU_1 + " " + LAST_NAME_2
		]
		userInteraction.assertAllInteractionsOccurred()
		logger.trace(this.nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += #[
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_1
					father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_2
					mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
					sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_2
					daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
				]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_DAD_1 + " " + LAST_NAME_1]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOM_1 + " " + LAST_NAME_2]
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON_1 + " " + LAST_NAME_2]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAU_1 + " " + LAST_NAME_2]
		]
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(this.nameOfTestMethod + " - finished without errors")
	}

	@Test
	def void testRename_Daughter_ChoosingExistingFamily() {
		logger.trace(this.nameOfTestMethod + " - begin")
		createFamiliesForTesting()
		// Daugther
		decideParentOrChild(PositionPreference.Child)
		// First existing Family
		decideNewOrExistingFamily(FamilyPreference.Existing)
		logger.trace(this.nameOfTestMethod + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searchedDaughter = persons.findFirst[person|person.fullName.equals(FIRST_DAU_1 + " " + LAST_NAME_1)]
			searchedDaughter.fullName = FIRST_DAU_1 + " " + LAST_NAME_2
		]
		userInteraction.assertAllInteractionsOccurred()
		logger.trace(this.nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += #[
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_1
					father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_2
					mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
					sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
					daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
				]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_DAD_1 + " " + LAST_NAME_1]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOM_1 + " " + LAST_NAME_2]
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON_1 + " " + LAST_NAME_2]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAU_1 + " " + LAST_NAME_2]
		]
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(this.nameOfTestMethod + " - finished without errors")
	}

	// ========== EXCEPTIONS ==========
	/**Unescapes escaped escape-sequences for linefeed, carriage return and tabulator escape-sequences.
	 * Unfortunately, <code>org.junit.jupiter.params.provider.Arguments.of(...)</code> is not able
	 * to deal with escape-sequences like </code>\n</code>. Therefore, these sequences have to be escaped for
	 * the ParameterizedTest and then unescaped for the intended use.
	 */
	def String unescapeString(String string) {
		return string.replace("\\n", "\n").replace("\\r", "\r").replace("\\t", "\t")
	}

	/**Test that error is thrown when trying to rename a {@link Person} with an empty name.
	 */
	@ParameterizedTest(name = " {index} => escapedNewName= {0}, expectedExceptionMessage= {1}")
	@MethodSource("nameAndExceptionProvider")
	def void testException_CreateWithInvalidFullname(String escapedNewName, String expectedExceptionMessage) {
		logger.trace(this.nameOfTestMethod + " - begin")
		val unescapedNewName = if (escapedNewName !== null) unescapeString(escapedNewName) else null
		logger.trace(this.nameOfTestMethod + " - preparation done")
		val thrownException = assertThrows(IllegalStateException, [
			PersonRegister.from(PERSONS_MODEL).propagate [
				persons += PersonsFactory.eINSTANCE.createMale => [fullName = unescapedNewName]
			]
		])
		logger.trace(this.nameOfTestMethod + " - propagation done")
		assertEquals(thrownException.message, expectedExceptionMessage)
		logger.trace(this.nameOfTestMethod + " - finished without errors")
	}

	/**Test that error is thrown when trying to rename a {@link Person} with an empty name.
	 */
	@ParameterizedTest(name = " {index} => escapedNewName= {0}, expectedExceptionMessage= {1}")
	@MethodSource("nameAndExceptionProvider")
	def void testException_RenameWithInvalidFullname(String escapedNewName, String expectedExceptionMessage) {
		logger.trace(this.nameOfTestMethod + " - begin")
		val unescapedNewName = if (escapedNewName !== null) unescapeString(escapedNewName) else null
		this.createFamiliesForTesting()
		logger.trace(this.nameOfTestMethod + " - preparation done")
		val thrownException = assertThrows(IllegalStateException, [
			PersonRegister.from(PERSONS_MODEL).propagate [
				val searchedDad = persons.findFirst[person|person.fullName.equals(FIRST_DAD_1 + " " + LAST_NAME_1)]
				searchedDad.fullName = unescapedNewName
			]
		])
		logger.trace(this.nameOfTestMethod + " - propagation done")
		assertEquals(thrownException.message, expectedExceptionMessage)
		logger.trace(this.nameOfTestMethod + " - finished without errors")
	}

	def static Stream<Arguments> nameAndExceptionProvider() {
		Stream.of(
			Arguments.of(null, PersonsToFamiliesHelper.EXCEPTION_MESSAGE_FIRSTNAME_NULL),
			Arguments.of("", PersonsToFamiliesHelper.EXCEPTION_MESSAGE_FIRSTNAME_WHITESPACE),
			Arguments.of("\\n\\t\\r", PersonsToFamiliesHelper.EXCEPTION_MESSAGE_FIRSTNAME_WHITESPACE),
			Arguments.of(FIRST_DAD_1 + "\\n", PersonsToFamiliesHelper.EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES),
			Arguments.of(FIRST_DAD_1 + "\\t", PersonsToFamiliesHelper.EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES),
			Arguments.of(FIRST_DAD_1 + "\\r", PersonsToFamiliesHelper.EXCEPTION_MESSAGE_FIRSTNAME_ESCAPES)
		)
	}

	// ========== EDITING ==========
	/**Test the renaming of the firstname of a single person which should
	 * only effect this person and the corresponding {@link Member}.
	 */
	@Test
	def void testRenamingOfFirstname() {
		logger.trace(this.nameOfTestMethod + " - begin")
		createFamiliesForTesting()
		logger.trace(this.nameOfTestMethod + " - preparation done")


		// Father
		decideParentOrChild(PositionPreference.Parent)
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searchedDad = persons.findFirst[person|person.fullName.equals(FIRST_DAD_1 + " " + LAST_NAME_1)]
			searchedDad.fullName = FIRST_DAD_2 + " " + LAST_NAME_1
		]
		// Mother
		decideParentOrChild(PositionPreference.Parent)
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searchedMom = persons.findFirst[person|person.fullName.equals(FIRST_MOM_1 + " " + LAST_NAME_2)]
			searchedMom.fullName = FIRST_MOM_2 + " " + LAST_NAME_2
		]
		// Son
		decideParentOrChild(PositionPreference.Child)
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searchedSon = persons.findFirst[person|person.fullName.equals(FIRST_SON_1 + " " + LAST_NAME_2)]
			searchedSon.fullName = FIRST_SON_2 + " " + LAST_NAME_2
		]
		// Daugther
		decideParentOrChild(PositionPreference.Child)
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searchedDaughter = persons.findFirst[person|person.fullName.equals(FIRST_DAU_1 + " " + LAST_NAME_1)]
			searchedDaughter.fullName = FIRST_DAU_2 + " " + LAST_NAME_1
		]
		logger.trace(this.nameOfTestMethod + " - propagation done")
		userInteraction.assertAllInteractionsOccurred()
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += #[
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_1
					father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_2]
					daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_2]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_2
					mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_2]
					sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_2]
				]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_DAD_2 + " " + LAST_NAME_1]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOM_2 + " " + LAST_NAME_2]
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON_2 + " " + LAST_NAME_2]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAU_2 + " " + LAST_NAME_1]
		]
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(this.nameOfTestMethod + " - finished without errors")
	}

	/**Test different special names which do not match the scheme firstname + " " + lastname.
	 * In the cases of more than two parts in the name separated by spaces, the last part is
	 * the lastname and everything else is the firstname.
	 * In the case of no spaces the name will be used as firstname and as lastname.
	 */
	@Test
	def void testSpecialNames() {
		logger.trace(this.nameOfTestMethod + " - begin")
		createFamiliesForTesting()
		logger.trace(this.nameOfTestMethod + " - preparation done")
		// Father
		decideParentOrChild(PositionPreference.Parent)
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searchedDad = persons.findFirst[person|person.fullName.equals(FIRST_DAD_1 + " " + LAST_NAME_1)]
			searchedDad.fullName = "The Earl of Dorincourt"
		]
		// Mother
		decideParentOrChild(PositionPreference.Parent)
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searchedMom = persons.findFirst[person|person.fullName.equals(FIRST_MOM_1 + " " + LAST_NAME_2)]
			searchedMom.fullName = "Cindy aus Marzahn"
		]
		// Son
		decideParentOrChild(PositionPreference.Child)
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searchedSon = persons.findFirst[person|person.fullName.equals(FIRST_SON_1 + " " + LAST_NAME_2)]
			searchedSon.fullName = "Saruman"
		]
		// Daugther
		decideParentOrChild(PositionPreference.Child)
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searchedDaughter = persons.findFirst[person|person.fullName.equals(FIRST_DAU_1 + " " + LAST_NAME_1)]
			searchedDaughter.fullName = "Daenerys_Targaryen"
		]
		logger.trace(this.nameOfTestMethod + " - propagation done")
		userInteraction.assertAllInteractionsOccurred()
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += #[
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = ""
					daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = "Daenerys_Targaryen"]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = ""
					sons += FamiliesFactory.eINSTANCE.createMember => [firstName = "Saruman"]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = "Dorincourt"
					father = FamiliesFactory.eINSTANCE.createMember => [firstName = "The Earl of"]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = "Marzahn"
					mother = FamiliesFactory.eINSTANCE.createMember => [firstName = "Cindy aus"]
				]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = "The Earl of Dorincourt"]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = "Cindy aus Marzahn"]
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = "Saruman"]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = "Daenerys_Targaryen"]
		]
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(this.nameOfTestMethod + " - finished without errors")
	}

	// ========== DELETION ==========
	/**Test the deletion of a person when the corresponding {@link Family} still contains other
	 * {@link Member}s. In this case, this family and the remaining members should be untouched.
	 */
	@Test
	def void testDeletePerson_NotLastInFamily() {
		logger.trace(this.nameOfTestMethod + " - begin")
		createFamiliesForTesting()
		logger.trace(this.nameOfTestMethod + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searchedDad = persons.findFirst[person|person.fullName.equals(FIRST_DAD_1 + " " + LAST_NAME_1)]
			persons.remove(searchedDad)

			val searchedSon = persons.findFirst[person|person.fullName.equals(FIRST_SON_1 + " " + LAST_NAME_2)]
			persons.remove(searchedSon)
		]
		logger.trace(this.nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += #[
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_1
					daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = LAST_NAME_2
					mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
				]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOM_1 + " " + LAST_NAME_2]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAU_1 + " " + LAST_NAME_1]
		]
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(this.nameOfTestMethod + " - finished without errors")
	}

	/**Test the deletion of a person when the corresponding {@link Family} does not contain
	 * any other {@link Member}s anymore. Without members a family should not exist. (Design-Decision!)
	 * Therefore, the empty family gets deleted as well.
	 */
	@Test
	def void testDeletePerson_LastInFamily() {
		logger.trace(this.nameOfTestMethod + " - begin")
		testDeletePerson_NotLastInFamily()
		logger.trace(this.nameOfTestMethod + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searchedMom = persons.findFirst[person|person.fullName.equals(FIRST_MOM_1 + " " + LAST_NAME_2)]
			persons.remove(searchedMom)

			val searchedDau = persons.findFirst[person|person.fullName.equals(FIRST_DAU_1 + " " + LAST_NAME_1)]
			persons.remove(searchedDau)
		]
		logger.trace(this.nameOfTestMethod + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister()
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister()
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(this.nameOfTestMethod + " - finished without errors")
	}

	/**Test the deletion of the {@link PersonRegister}. The corresponding {@link FamilyRegister}
	 * will be then deleted as well and all contained elements in both of the registers.
	 */
	@Test
	def void testDeletePersonsRegister() {
		logger.trace(this.nameOfTestMethod + " - begin")
		this.createFamiliesForTesting();
		logger.trace(this.nameOfTestMethod + " - preparation done")
		resourceAt(PERSONS_MODEL).propagate[contents.clear()]
		logger.trace(this.nameOfTestMethod + " - propagation done")
		assertEquals(0, resourceAt(FAMILIES_MODEL).contents.size())
		assertEquals(0, resourceAt(PERSONS_MODEL).contents.size())
		assertThat(resourceAt(FAMILIES_MODEL), not(exists))
		assertThat(resourceAt(PERSONS_MODEL), not(exists))
		logger.trace(this.nameOfTestMethod + " - finished without errors")
	}
}
