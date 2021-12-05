package tools.vitruv.applications.demo.familiespersons.tests.persons2families

import edu.kit.ipd.sdq.metamodels.families.FamiliesFactory
import edu.kit.ipd.sdq.metamodels.families.Family
import edu.kit.ipd.sdq.metamodels.families.FamilyRegister
import edu.kit.ipd.sdq.metamodels.families.Member
import edu.kit.ipd.sdq.metamodels.persons.Person
import edu.kit.ipd.sdq.metamodels.persons.PersonRegister
import edu.kit.ipd.sdq.metamodels.persons.PersonsFactory
import java.nio.file.Path
import org.apache.log4j.Logger
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import tools.vitruv.applications.demo.familiespersons.families2persons.FamiliesToPersonsChangePropagationSpecification
import tools.vitruv.applications.demo.familiespersons.persons2families.PersonsToFamiliesChangePropagationSpecification
import tools.vitruv.domains.demo.families.FamiliesDomainProvider
import tools.vitruv.domains.demo.persons.PersonsDomainProvider
import tools.vitruv.testutils.VitruvApplicationTest
import tools.vitruv.testutils.domains.DomainUtil

import static org.hamcrest.CoreMatchers.*
import static org.hamcrest.MatcherAssert.assertThat
import static org.junit.jupiter.api.Assertions.assertEquals
import static tools.vitruv.testutils.matchers.ModelMatchers.*

/**Test to validate the transfer of changes from the PersonModel to the FamilyModel.
 * @author Dirk Neumann 
 */
class PersonsToFamiliesTest extends VitruvApplicationTest {
	static val logger = Logger.getLogger(PersonsToFamiliesTest)
	TestInfo testInfo = null

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

	/**Before each test a new {@link PersonRegister} is created as starting point.
	 * This is checked by several assertions to ensure correct preconditions for the tests. 
	 */
	@BeforeEach
	def void insertRegister(TestInfo testInfo) {
		this.testInfo = testInfo
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
		// Father
		userInteraction.addNextSingleSelection(0)
		// Mother
		userInteraction.addNextSingleSelection(0)
		// Son in existing Family
		userInteraction.addNextSingleSelection(1)
		userInteraction.addNextSingleSelection(1)
		// Daughter in existing Family
		userInteraction.addNextSingleSelection(1)
		userInteraction.addNextSingleSelection(1)
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_DAD_1 + " " + LAST_NAME_1]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOM_1 + " " + LAST_NAME_2]
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON_1 + " " + LAST_NAME_2]
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAU_1 + " " + LAST_NAME_1]
		]
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
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		// Father
		userInteraction.addNextSingleSelection(0)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_DAD_1 + " " + LAST_NAME_1]
		]
		logger.trace(surroundingMethodName + " - propagation done")
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
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	@Test
	def void testCreateMale_Father_AutomaticNewFamily() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		createFamiliesForTesting()
		// Father
		userInteraction.addNextSingleSelection(0)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_2 + " " + LAST_NAME_1
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
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
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	@Test
	def void testCreateMale_Father_ChoosingNewFamily() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		createFamiliesForTesting()
		// Father
		userInteraction.addNextSingleSelection(0)
		// New Family
		userInteraction.addNextSingleSelection(0)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_2 + " " + LAST_NAME_2
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
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
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	@Test
	def void testCreateMale_Father_ChoosingExistingFamily() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		createFamiliesForTesting()
		// Father
		userInteraction.addNextSingleSelection(0)
		// First existing Family
		userInteraction.addNextSingleSelection(1)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_2 + " " + LAST_NAME_2
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
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
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	@Test
	def void testRename_Father_AutomaticNewFamily() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		createFamiliesForTesting()
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searchedDad = persons.findFirst[x|x.fullName.equals(FIRST_DAD_1 + " " + LAST_NAME_1)]
			searchedDad.fullName = FIRST_DAD_1 + " " + LAST_NAME_3
		]
		logger.trace(surroundingMethodName + " - propagation done")
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
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	@Test
	def void testRename_Father_ChoosingNewFamily() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		createFamiliesForTesting()
		// New Family
		userInteraction.addNextSingleSelection(0)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searchedDad = persons.findFirst[x|x.fullName.equals(FIRST_DAD_1 + " " + LAST_NAME_1)]
			searchedDad.fullName = FIRST_DAD_1 + " " + LAST_NAME_2
		]
		logger.trace(surroundingMethodName + " - propagation done")
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
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	@Test
	def void testRename_Father_ChoosingExistingFamily() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		createFamiliesForTesting()
		// First existing Family
		userInteraction.addNextSingleSelection(1)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searchedDad = persons.findFirst[x|x.fullName.equals(FIRST_DAD_1 + " " + LAST_NAME_1)]
			searchedDad.fullName = FIRST_DAD_1 + " " + LAST_NAME_2
		]
		logger.trace(surroundingMethodName + " - propagation done")
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
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	// ========== SON ==========
	@Test
	def void testCreateMale_Son_EmptyRegister() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		// Son
		userInteraction.addNextSingleSelection(1)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON_1 + " " + LAST_NAME_1]
		]
		logger.trace(surroundingMethodName + " - propagation done")
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
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	@Test
	def void testCreateMale_Son_AutomaticNewFamily() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		createFamiliesForTesting()
		// Son
		userInteraction.addNextSingleSelection(1)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_2 + " " + LAST_NAME_3
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
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
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	@Test
	def void testCreateMale_Son_ChoosingNewFamily() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		createFamiliesForTesting()
		// Son
		userInteraction.addNextSingleSelection(1)
		// New Family
		userInteraction.addNextSingleSelection(0)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_2 + " " + LAST_NAME_1
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
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
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	@Test
	def void testCreateMale_Son_ChoosingExistingFamily() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		createFamiliesForTesting()
		// Son
		userInteraction.addNextSingleSelection(1)
		// First existing Family
		userInteraction.addNextSingleSelection(1)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_2 + " " + LAST_NAME_2
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
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
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	@Test
	def void testRename_Son_AutomaticNewFamily() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		createFamiliesForTesting()
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searchedSon = persons.findFirst[x|x.fullName.equals(FIRST_SON_1 + " " + LAST_NAME_2)]
			searchedSon.fullName = FIRST_SON_1 + " " + LAST_NAME_3
		]
		logger.trace(surroundingMethodName + " - propagation done")
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
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	@Test
	def void testRename_Son_ChoosingNewFamily() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		createFamiliesForTesting()
		// New Family
		userInteraction.addNextSingleSelection(0)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searchedSon = persons.findFirst[x|x.fullName.equals(FIRST_SON_1 + " " + LAST_NAME_2)]
			searchedSon.fullName = FIRST_SON_1 + " " + LAST_NAME_1
		]
		logger.trace(surroundingMethodName + " - propagation done")
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
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	@Test
	def void testRename_Son_ChoosingExistingFamily() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		createFamiliesForTesting()
		// First existing Family
		userInteraction.addNextSingleSelection(1)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searchedSon = persons.findFirst[x|x.fullName.equals(FIRST_SON_1 + " " + LAST_NAME_2)]
			searchedSon.fullName = FIRST_SON_1 + " " + LAST_NAME_1
		]
		logger.trace(surroundingMethodName + " - propagation done")
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
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	// =====================================
	// CREATE FEMALE
	// =====================================
	// ========== MOTHER ==========
	@Test
	def void testCreateMale_Mother_EmptyRegister() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		// Mother
		userInteraction.addNextSingleSelection(0)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOM_1 + " " + LAST_NAME_1]
		]
		logger.trace(surroundingMethodName + " - propagation done")
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
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	@Test
	def void testCreateFemale_Mother_AutomaticNewFamily() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		createFamiliesForTesting()
		// Mother
		userInteraction.addNextSingleSelection(0)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_2 + " " + LAST_NAME_2
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
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
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	@Test
	def void testCreateFemale_Mother_ChoosingNewFamily() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		createFamiliesForTesting()
		// Mother
		userInteraction.addNextSingleSelection(0)
		// New Family
		userInteraction.addNextSingleSelection(0)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_2 + " " + LAST_NAME_1
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
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
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	@Test
	def void testCreateFemale_Mother_ChoosingExistingFamily() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		createFamiliesForTesting()
		// Mother
		userInteraction.addNextSingleSelection(0)
		// First existing Family
		userInteraction.addNextSingleSelection(1)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_2 + " " + LAST_NAME_1
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
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
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	@Test
	def void testRename_Mother_AutomaticNewFamily() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		createFamiliesForTesting()
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searchedMom = persons.findFirst[x|x.fullName.equals(FIRST_MOM_1 + " " + LAST_NAME_2)]
			searchedMom.fullName = FIRST_MOM_1 + " " + LAST_NAME_3
		]
		logger.trace(surroundingMethodName + " - propagation done")
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
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	@Test
	def void testRename_Mother_ChoosingNewFamily() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		createFamiliesForTesting()
		// New Family
		userInteraction.addNextSingleSelection(0)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searchedMom = persons.findFirst[x|x.fullName.equals(FIRST_MOM_1 + " " + LAST_NAME_2)]
			searchedMom.fullName = FIRST_MOM_1 + " " + LAST_NAME_1
		]
		logger.trace(surroundingMethodName + " - propagation done")
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
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	@Test
	def void testRename_Mother_ChoosingExistingFamily() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		createFamiliesForTesting()
		// First existing Family
		userInteraction.addNextSingleSelection(1)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searchedMom = persons.findFirst[x|x.fullName.equals(FIRST_MOM_1 + " " + LAST_NAME_2)]
			searchedMom.fullName = FIRST_MOM_1 + " " + LAST_NAME_1
		]
		logger.trace(surroundingMethodName + " - propagation done")
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
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	// ========== DAUGHTHER ==========
	@Test
	def void testCreateMale_Daughter_EmptyRegister() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		// Daughter
		userInteraction.addNextSingleSelection(1)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAU_1 + " " + LAST_NAME_1]
		]
		logger.trace(surroundingMethodName + " - propagation done")
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
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	@Test
	def void testCreateFemale_Daughter_AutomaticNewFamily() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		createFamiliesForTesting()
		// Daughter
		userInteraction.addNextSingleSelection(1)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAU_2 + " " + LAST_NAME_3
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
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
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	@Test
	def void testCreateFemale_Daughter_ChoosingNewFamily() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		createFamiliesForTesting()
		// Daughter
		userInteraction.addNextSingleSelection(1)
		// New Family
		userInteraction.addNextSingleSelection(0)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAU_2 + " " + LAST_NAME_1
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
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
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	@Test
	def void testCreateFemale_Daughter_ChoosingExistingFamily() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		createFamiliesForTesting()
		// Daughter
		userInteraction.addNextSingleSelection(1)
		// First Existing Family
		userInteraction.addNextSingleSelection(1)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAU_2 + " " + LAST_NAME_2
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
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
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	@Test
	def void testRename_Daughter_AutomaticNewFamily() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		createFamiliesForTesting()
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searchedDaughter = persons.findFirst[x|x.fullName.equals(FIRST_DAU_1 + " " + LAST_NAME_1)]
			searchedDaughter.fullName = FIRST_DAU_1 + " " + LAST_NAME_3
		]
		logger.trace(surroundingMethodName + " - propagation done")
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
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	@Test
	def void testRename_Daughter_ChoosingNewFamily() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		createFamiliesForTesting()
		// New Family
		userInteraction.addNextSingleSelection(0)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searchedDaughter = persons.findFirst[x|x.fullName.equals(FIRST_DAU_1 + " " + LAST_NAME_1)]
			searchedDaughter.fullName = FIRST_DAU_1 + " " + LAST_NAME_2
		]
		logger.trace(surroundingMethodName + " - propagation done")
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
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	@Test
	def void testRename_Daughter_ChoosingExistingFamily() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		createFamiliesForTesting()
		// First existing Family
		userInteraction.addNextSingleSelection(1)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searchedDaughter = persons.findFirst[x|x.fullName.equals(FIRST_DAU_1 + " " + LAST_NAME_1)]
			searchedDaughter.fullName = FIRST_DAU_1 + " " + LAST_NAME_2
		]
		logger.trace(surroundingMethodName + " - propagation done")
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
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	// ========== EDITING ==========
	/**Test the renaming of the firstname of a single person which should
	 * only effect this person and the corresponding {@link Member}.
	 */
	@Test
	def void testRenamingOfFirstname() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		createFamiliesForTesting()
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searchedDad = persons.findFirst[x|x.fullName.equals(FIRST_DAD_1 + " " + LAST_NAME_1)]
			searchedDad.fullName = FIRST_DAD_2 + " " + LAST_NAME_1

			val searchedMom = persons.findFirst[x|x.fullName.equals(FIRST_MOM_1 + " " + LAST_NAME_2)]
			searchedMom.fullName = FIRST_MOM_2 + " " + LAST_NAME_2

			val searchedSon = persons.findFirst[x|x.fullName.equals(FIRST_SON_1 + " " + LAST_NAME_2)]
			searchedSon.fullName = FIRST_SON_2 + " " + LAST_NAME_2

			val searchedDaughter = persons.findFirst[x|x.fullName.equals(FIRST_DAU_1 + " " + LAST_NAME_1)]
			searchedDaughter.fullName = FIRST_DAU_2 + " " + LAST_NAME_1
		]
		logger.trace(surroundingMethodName + " - propagation done")
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
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	// ========== EDITING ==========
	/**Test different special names which do not match the scheme firstname + " " + lastname.
	 * In the cases of more than two parts in the name separated by spaces, the last part is
	 * the lastname and everything else is the firstname.
	 * In the case of no spaces the name will be used as firstname and as lastname. 
	 */
	@Test
	def void testSpecialNames() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		createFamiliesForTesting()
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searchedDad = persons.findFirst[x|x.fullName.equals(FIRST_DAD_1 + " " + LAST_NAME_1)]
			searchedDad.fullName = "The Earl of Dorincourt"

			val searchedMom = persons.findFirst[x|x.fullName.equals(FIRST_MOM_1 + " " + LAST_NAME_2)]
			searchedMom.fullName = "Cindy aus Marzahn"

			val searchedSon = persons.findFirst[x|x.fullName.equals(FIRST_SON_1 + " " + LAST_NAME_2)]
			searchedSon.fullName = "Saruman"

			val searchedDaughter = persons.findFirst[x|x.fullName.equals(FIRST_DAU_1 + " " + LAST_NAME_1)]
			searchedDaughter.fullName = "Daenerys_Targaryen"
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += #[
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = "Dorincourt"
					father = FamiliesFactory.eINSTANCE.createMember => [firstName = "The Earl of"]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = "Marzahn"
					mother = FamiliesFactory.eINSTANCE.createMember => [firstName = "Cindy aus"]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = "Saruman"
					sons += FamiliesFactory.eINSTANCE.createMember => [firstName = "Saruman"]
				],
				FamiliesFactory.eINSTANCE.createFamily => [
					lastName = "Daenerys_Targaryen"
					daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = "Daenerys_Targaryen"]
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
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	// ========== DELETION ==========
	/**Test the deletion of a person when the corresponding {@link Family} still contains other
	 * {@link Member}s. In this case, this family and the remaining members should be untouched.
	 */
	@Test
	def void testDeletePerson_NotLastInFamily() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		createFamiliesForTesting()
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searchedDad = persons.findFirst[x|x.fullName.equals(FIRST_DAD_1 + " " + LAST_NAME_1)]
			persons.remove(searchedDad)

			val searchedSon = persons.findFirst[x|x.fullName.equals(FIRST_SON_1 + " " + LAST_NAME_2)]
			persons.remove(searchedSon)
		]
		logger.trace(surroundingMethodName + " - propagation done")
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
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Test the deletion of a person when the corresponding {@link Family} does not contain
	 * any other {@link Member}s anymore. Without members a family should not exist. (Design-Decision!)
	 * Therefore, the empty family gets deleted as well.
	 */
	@Test
	def void testDeletePerson_LastInFamily() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		testDeletePerson_NotLastInFamily()
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searchedMom = persons.findFirst[x|x.fullName.equals(FIRST_MOM_1 + " " + LAST_NAME_2)]
			persons.remove(searchedMom)

			val searchedDau = persons.findFirst[x|x.fullName.equals(FIRST_DAU_1 + " " + LAST_NAME_1)]
			persons.remove(searchedDau)
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister()
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister()
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Test the deletion of the {@link PersonRegister}. The corresponding {@link FamilyRegister}
	 * will be then deleted as well and all contained elements in both of the registers.
	 */
	@Test
	def void testDeletePersonsRegister() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		this.createFamiliesForTesting();
		logger.trace(surroundingMethodName + " - preparation done")
		resourceAt(PERSONS_MODEL).propagate[contents.clear()]
		logger.trace(surroundingMethodName + " - propagation done")
		assertEquals(0, resourceAt(FAMILIES_MODEL).contents.size())
		assertEquals(0, resourceAt(PERSONS_MODEL).contents.size())
		assertThat(resourceAt(FAMILIES_MODEL), not(exists))
		assertThat(resourceAt(PERSONS_MODEL), not(exists))
		logger.trace(surroundingMethodName + " - finished without errors")
	}
}
