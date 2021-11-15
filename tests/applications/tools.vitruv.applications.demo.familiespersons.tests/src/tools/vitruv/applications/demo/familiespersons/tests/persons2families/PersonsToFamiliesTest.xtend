package tools.vitruv.applications.demo.familiespersons.tests.persons2families

import edu.kit.ipd.sdq.metamodels.families.FamiliesFactory
import edu.kit.ipd.sdq.metamodels.families.Family
import edu.kit.ipd.sdq.metamodels.families.FamilyRegister
import edu.kit.ipd.sdq.metamodels.families.Member
import edu.kit.ipd.sdq.metamodels.persons.Person
import edu.kit.ipd.sdq.metamodels.persons.PersonRegister
import edu.kit.ipd.sdq.metamodels.persons.PersonsFactory
import java.nio.file.Path
import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tools.vitruv.applications.demo.familiespersons.persons2families.PersonsToFamiliesChangePropagationSpecification
import tools.vitruv.domains.demo.families.FamiliesDomainProvider
import tools.vitruv.domains.demo.persons.PersonsDomainProvider
import tools.vitruv.testutils.VitruvApplicationTest
import tools.vitruv.testutils.domains.DomainUtil

import static org.hamcrest.CoreMatchers.*
import static org.hamcrest.MatcherAssert.assertThat
import static org.junit.jupiter.api.Assertions.assertEquals
import static tools.vitruv.testutils.matchers.ModelMatchers.*
import org.junit.jupiter.api.TestInfo

/**Test to validate the transfer of changes from the PersonModel to the FamilyModel.
 * @author Dirk Neumann   
 */
class PersonsToFamiliesTest extends VitruvApplicationTest {  
	static val logger = Logger.getLogger(PersonsToFamiliesTest);	

	// First Set of reused static strings
	final static String LAST_NAME_1 = "Meier"
	final static String FIRST_DAD_1 = "Anton"
	final static String FIRST_MOM_1 = "Berta"
	final static String FIRST_SON_1 = "Chris"
	final static String FIRST_DAU_1 = "Daria"

	// Second Set of reused static strings
	final static String LAST_NAME_2 = "Schulze"
	final static String FIRST_DAD_2 = "Adam"
	final static String FIRST_MOM_2 = "Birgit"
	final static String FIRST_SON_2 = "Charles"
	final static String FIRST_DAU_2 = "Daniela"

	// Model Paths
	final static Path PERSONS_MODEL = DomainUtil.getModelFileName('model/persons', new PersonsDomainProvider)
	final static Path FAMILIES_MODEL = DomainUtil.getModelFileName('model/families', new FamiliesDomainProvider)

	/**Set the correct set of reations and routines for this test suite
	 */
	override protected getChangePropagationSpecifications() {
		return #[new PersonsToFamiliesChangePropagationSpecification()]
	}

	/**Before each test a new {@link PersonRegister} is created as starting point.
	 * This is checked by several assertions to ensure correct preconditions for the tests. 
	 */
	@BeforeEach
	def void insertRegister() {
		logger.setLevel(Level.DEBUG)
		resourceAt(PERSONS_MODEL).propagate[contents += PersonsFactory.eINSTANCE.createPersonRegister]
		assertThat(resourceAt(FAMILIES_MODEL), exists);
		assertEquals(1, resourceAt(FAMILIES_MODEL).contents.size);
		assertEquals(1, resourceAt(FAMILIES_MODEL).allContents.size);
		assertThat(resourceAt(FAMILIES_MODEL).contents.get(0), instanceOf(FamilyRegister));
		assertEquals(0, resourceAt(FAMILIES_MODEL).contents.get(0).eAllContents().size);
	}

	/**Check up method to first check the correct basic structure of both models and then compare
	 * the resulting {@link PersonRegister} and {@link FamilyRegister} with predefined ones.
	 * Basically encapsulate the assertion part of each test.
	 */
	def void checkCorrectRegisters(FamilyRegister comparisonFamilyRegister, PersonRegister comparisonPersonRegister) {
		val personModel = resourceAt(PERSONS_MODEL)
		val familiesModel = resourceAt(FAMILIES_MODEL)
		assertThat(personModel, exists)
		assertThat(familiesModel, exists)
		assertEquals(1, personModel.contents.size);
		assertEquals(1, familiesModel.contents.size);
		val familyRegister = familiesModel.contents.get(0)
		assertThat(familyRegister, instanceOf(FamilyRegister));
		val FamilyRegister castedFamilyRegister = familyRegister as FamilyRegister
		assertThat(castedFamilyRegister, equalsDeeply(comparisonFamilyRegister));
		val personRegister = personModel.contents.get(0)
		assertThat(personRegister, instanceOf(PersonRegister));
		val PersonRegister castedPersonRegister = personRegister as PersonRegister
		assertThat(castedPersonRegister, equalsDeeply(comparisonPersonRegister));
	}
	
	/**Create a {@link Family} that already contains a father for further testing.
	 */
	def void prepareFatherInNewFamily() {
		userInteraction.addNextSingleSelection(0)
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_1 + " " + LAST_NAME_1
			]
		]
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_1 + " " + LAST_NAME_1
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
	}	
	/**Create a {@link Family} that already contains a son for further testing.
	 */
	def void prepareSonInNewFamily(){
		userInteraction.addNextSingleSelection(1)
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_1 + " " + LAST_NAME_1
			]
		]
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_1 + " " + LAST_NAME_1
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
	}
	/**Create a {@link Family} that already contains a mother for further testing.
	 */
	def void prepareMotherInNewFamily() {
		userInteraction.addNextSingleSelection(0)
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_1 + " " + LAST_NAME_1
			]
		]
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_1 + " " + LAST_NAME_1
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
	}
	/**Create a {@link Family} that already contains a daughter for further testing.
	 */
	def void prepareDaughterInNewFamily(){
		userInteraction.addNextSingleSelection(1)
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAU_1 + " " + LAST_NAME_1
			]
		]
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAU_1 + " " + LAST_NAME_1
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
	}

	// =====================================
	// CREATE MALE
	// =====================================
	// ========== FATHER ==========
	/**Test the creation of a father in a new family.
	 */
	@Test
	def void testCreateMale_Father_InsertInNewFamily(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		// Father
		userInteraction.addNextSingleSelection(0)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_1 + " " + LAST_NAME_1
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_1 + " " + LAST_NAME_1
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Test the insertion of a father in an existing family with correct lastname.
	 */
	@Test
	def void testCreateMale_Father_InsertInMatchingFamily_NoFather(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		prepareMotherInNewFamily()
		// Father
		userInteraction.addNextSingleSelection(0)
		// Select Family
		userInteraction.addNextSingleSelection(1)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_1 + " " + LAST_NAME_1
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_1 + " " + LAST_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_1 + " " + LAST_NAME_1
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Test the insertion of a father in an existing family with correct lastname
	 * and with existing father. Users decides to abort the process.
	 */
	@Test
	def void testCreateMale_Father_InsertInMatchingFamily_HasFather_DiscardChanges(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		prepareFatherInNewFamily()
		// Father
		userInteraction.addNextSingleSelection(0)
		// Select Family
		userInteraction.addNextSingleSelection(1)
		// DiscardChanges
		userInteraction.addNextSingleSelection(0)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_2 + " " + LAST_NAME_1
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_1 + " " + LAST_NAME_1
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Test the insertion of a father in an existing family with correct lastname
	 * and with existing father. Users decides to replace existing father.
	 */
	@Test
	def void testCreateMale_Father_InsertInMatchingFamily_HasFather_ReplaceFather(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		prepareFatherInNewFamily()
		// Father
		userInteraction.addNextSingleSelection(0)
		// Select Family
		userInteraction.addNextSingleSelection(1)
		// ReplaceFather
		userInteraction.addNextSingleSelection(1)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_2 + " " + LAST_NAME_1
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_2]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_2 + " " + LAST_NAME_1
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Test the insertion of a father in an existing family with correct lastname
	 * and with existing father. Users decides to insert new father in new family.
	 */
	@Test
	def void testCreateMale_Father_InsertInMatchingFamily_HasFather_InsertInNewFamily(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		prepareFatherInNewFamily()
		// Father
		userInteraction.addNextSingleSelection(0)
		// Select Family
		userInteraction.addNextSingleSelection(1)
		// InsertInNewFamily
		userInteraction.addNextSingleSelection(2)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_2 + " " + LAST_NAME_1
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
			]
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_2]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_1 + " " + LAST_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_2 + " " + LAST_NAME_1
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Test the insertion of a father in an existing family with different lastname.
	 * Users decides to abort the process.
	 */
	@Test
	def void testCreateMale_Father_InsertInDifferentlyNamedFamily_NoFather_DiscardChanges(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		prepareMotherInNewFamily()
		// Father
		userInteraction.addNextSingleSelection(0)
		// Select Family
		userInteraction.addNextSingleSelection(1)
		// DiscardChanges
		userInteraction.addNextSingleSelection(0)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_1 + " " + LAST_NAME_2
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_1 + " " + LAST_NAME_1
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Test the insertion of a father in an existing family with different lastname.
	 * Users decides to change the lastname of the father and then to insert him.
	 */
	@Test
	def void testCreateMale_Father_InsertInDifferentlyNamedFamily_NoFather_RenamePerson(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		prepareMotherInNewFamily()
		// Father
		userInteraction.addNextSingleSelection(0)
		// Select Family
		userInteraction.addNextSingleSelection(1)
		// RenamePerson
		userInteraction.addNextSingleSelection(1)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_1 + " " + LAST_NAME_2
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_1 + " " + LAST_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_1 + " " + LAST_NAME_1
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Test the insertion of a father in an existing family with different lastname.
	 * Users decides to rename the family and then to insert the father.
	 */
	@Test
	def void testCreateMale_Father_InsertInDifferentlyNamedFamily_NoFather_RenameFamily(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		prepareMotherInNewFamily()
		// Father
		userInteraction.addNextSingleSelection(0)
		// Select Family
		userInteraction.addNextSingleSelection(1)
		// RenameFamily
		userInteraction.addNextSingleSelection(2)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_1 + " " + LAST_NAME_2
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_2
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_1 + " " + LAST_NAME_2
			]
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_1 + " " + LAST_NAME_2
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Test the insertion of a father in an existing family with different lastname.
	 * Users decides to create a new family and then to insert the father in the new one.
	 */
	@Test
	def void testCreateMale_Father_InsertInDifferentlyNamedFamily_NoFather_InsertInNewFamily(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		prepareMotherInNewFamily()
		// Father
		userInteraction.addNextSingleSelection(0)
		// Select Family
		userInteraction.addNextSingleSelection(1)
		// InsertInNewFamily
		userInteraction.addNextSingleSelection(3)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_1 + " " + LAST_NAME_2
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_2
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
			]
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_1 + " " + LAST_NAME_2
			]
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_1 + " " + LAST_NAME_1
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Test the insertion of a father in an existing family with different lastname which also
	 * already has a father. Users decides to abort the process.
	 */
	@Test
	def void testCreateMale_Father_InsertInDifferentlyNamedFamily_HasFather_DiscardChanges(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		prepareFatherInNewFamily()
		// Father
		userInteraction.addNextSingleSelection(0)
		// Select Family
		userInteraction.addNextSingleSelection(1)
		// DiscardChanges
		userInteraction.addNextSingleSelection(0)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_2 + " " + LAST_NAME_2
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_1 + " " + LAST_NAME_1
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Test the insertion of a father in an existing family with different lastname which also
	 * already has a father. Users decides to replace the existing father but when the decision
	 * about either the father or the family has to be renamed, the user aborts the process.
	 */
	@Test
	def void testCreateMale_Father_InsertInDifferentlyNamedFamily_HasFather_ReplaceFather_DiscardChanges(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		prepareFatherInNewFamily()
		// Father
		userInteraction.addNextSingleSelection(0)
		// Select Family
		userInteraction.addNextSingleSelection(1)
		// ReplaceFather
		userInteraction.addNextSingleSelection(1)
		// DiscardChanges
		userInteraction.addNextSingleSelection(0)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_2 + " " + LAST_NAME_2
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_1 + " " + LAST_NAME_1
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Test the insertion of a father in an existing family with different lastname which also
	 * already has a father. Users decides to replace the existing father and when the decision
	 * about either the father or the family has to be renamed, the user chooses to rename the father.
	 */
	@Test
	def void testCreateMale_Father_InsertInDifferentlyNamedFamily_HasFather_ReplaceFather_RenamePerson(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		prepareFatherInNewFamily()
		// Father
		userInteraction.addNextSingleSelection(0)
		// Select Family
		userInteraction.addNextSingleSelection(1)
		// ReplaceFather
		userInteraction.addNextSingleSelection(1)
		// RenamePerson
		userInteraction.addNextSingleSelection(1)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_2 + " " + LAST_NAME_2
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_2]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_2 + " " + LAST_NAME_1
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Test the insertion of a father in an existing family with different lastname which also
	 * already has a father. Users decides to replace the existing father and when the decision
	 * about either the father or the family has to be renamed, the user chooses to rename the family.
	 */
	@Test
	def void testCreateMale_Father_InsertInDifferentlyNamedFamily_HasFather_ReplaceFather_RenameFamily(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		prepareFatherInNewFamily()
		// Father
		userInteraction.addNextSingleSelection(0)
		// Select Family
		userInteraction.addNextSingleSelection(1)
		// ReplaceFather
		userInteraction.addNextSingleSelection(1)
		// RenameFamily
		userInteraction.addNextSingleSelection(2)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_2 + " " + LAST_NAME_2
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_2
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_2]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_2 + " " + LAST_NAME_2
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Test the insertion of a father in an existing family with different lastname which also
	 * already has a father. Users decides to replace the existing father but when the decision
	 * about either the father or the family has to be renamed, the user chooses to create instead
	 * a new family for the new father.
	 */
	@Test
	def void testCreateMale_Father_InsertInDifferentlyNamedFamily_HasFather_ReplaceFather_InsertInNewFamily(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		prepareFatherInNewFamily()
		// Father
		userInteraction.addNextSingleSelection(0)
		// Select Family
		userInteraction.addNextSingleSelection(1)
		// ReplaceFather
		userInteraction.addNextSingleSelection(1)
		// InsertInNewFamily
		userInteraction.addNextSingleSelection(3)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_2 + " " + LAST_NAME_2
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
			]
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_2
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_2]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_1 + " " + LAST_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_2 + " " + LAST_NAME_2
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Test the insertion of a father in an existing family with different lastname which also
	 * already has a father. Users decides to create instead a new family for the new father.
	 */
	@Test
	def void testCreateMale_Father_InsertInDifferentlyNamedFamily_HasFather_InsertInNewFamily(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		prepareFatherInNewFamily()
		// Father
		userInteraction.addNextSingleSelection(0)
		// Select Family
		userInteraction.addNextSingleSelection(1)
		// InsertInNewFamily
		userInteraction.addNextSingleSelection(2)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_2 + " " + LAST_NAME_2
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
			]
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_2
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_2]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_1 + " " + LAST_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_2 + " " + LAST_NAME_2
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	// ========== SON ==========
	/**Test the creation of a new son into a new family.
	 */
	@Test
	def void testCreateMale_Son_InsertInNewFamily(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		// Son
		userInteraction.addNextSingleSelection(1)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_1 + " " + LAST_NAME_1
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_1 + " " + LAST_NAME_1
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Test the insertion of a new son into an existing family with a matching lastname.
	 */
	@Test
	def void testCreateMale_Son_InsertInMatchingFamily(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		prepareSonInNewFamily()
		// Son
		userInteraction.addNextSingleSelection(1)
		// SelectFamily
		userInteraction.addNextSingleSelection(1)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_2 + " " + LAST_NAME_1
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_2]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_1 + " " + LAST_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_2 + " " + LAST_NAME_1
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Test the insertion of a new son into an existing family with a different lastname.
	 * User decides to abort the process. 
	 */
	@Test
	def void testCreateMale_Son_InsertInDifferentlyNamedFamily_DiscardChanges(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		prepareSonInNewFamily()
		// Son
		userInteraction.addNextSingleSelection(1)
		// SelectFamily
		userInteraction.addNextSingleSelection(1)
		// DiscardChanges
		userInteraction.addNextSingleSelection(0)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_2 + " " + LAST_NAME_2
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_1 + " " + LAST_NAME_1
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Test the insertion of a new son into an existing family with a different lastname.
	 * User decides to rename the new son and insert him afterwards. 
	 */
	@Test
	def void testCreateMale_Son_InsertInDifferentlyNamedFamily_RenamePerson(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		prepareSonInNewFamily()
		// Son
		userInteraction.addNextSingleSelection(1)
		// SelectFamily
		userInteraction.addNextSingleSelection(1)
		// RenamePerson
		userInteraction.addNextSingleSelection(1)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_2 + " " + LAST_NAME_2
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_2]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_1 + " " + LAST_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_2 + " " + LAST_NAME_1
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Test the insertion of a new son into an existing family with a different lastname.
	 * User decides to rename the family and then insert the new son into the family. 
	 */
	@Test
	def void testCreateMale_Son_InsertInDifferentlyNamedFamily_RenameFamily(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		prepareSonInNewFamily()
		// Son
		userInteraction.addNextSingleSelection(1)
		// SelectFamily
		userInteraction.addNextSingleSelection(1)
		// RenameFamily
		userInteraction.addNextSingleSelection(2)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_2 + " " + LAST_NAME_2
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_2
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_2]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_1 + " " + LAST_NAME_2
			]
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_2 + " " + LAST_NAME_2
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Test the insertion of a new son into an existing family with a different lastname.
	 * User decides to create a new family with the same lastname as the son has and then 
	 * inserts the new son into this new family. 
	 */
	@Test
	def void testCreateMale_Son_InsertInDifferentlyNamedFamily_InsertInNewFamily(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		prepareSonInNewFamily()
		// Son
		userInteraction.addNextSingleSelection(1)
		// SelectFamily
		userInteraction.addNextSingleSelection(1)
		// InsertInNewFamily
		userInteraction.addNextSingleSelection(3)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_2 + " " + LAST_NAME_2
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
			]
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_2
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_2]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_1 + " " + LAST_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_2 + " " + LAST_NAME_2
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	// =====================================
	// CREATE FEMALE
	// =====================================
	// ========== MOTHER ==========	
	/**Test the creation of a father in a new family.
	 */
	@Test
	def void testCreateFemale_Mother_InsertInNewFamily(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		userInteraction.addNextSingleSelection(0)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_1 + " " + LAST_NAME_1
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_1 + " " + LAST_NAME_1
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Test the insertion of a father in an existing family with correct lastname.
	 */
	@Test
	def void testCreateFemale_Mother_InsertInMatchingFamily_NoMother(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		prepareFatherInNewFamily()
		userInteraction.addNextSingleSelection(0)
		userInteraction.addNextSingleSelection(1)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_1 + " " + LAST_NAME_1
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_1 + " " + LAST_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_1 + " " + LAST_NAME_1
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Test the insertion of a mother in an existing family with correct lastname
	 * and with existing mother. Users decides to abort the process.
	 */
	@Test
	def void testCreateFemale_Mother_InsertInMatchingFamily_HasMother_DiscardChanges(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		prepareMotherInNewFamily()
		// Mother
		userInteraction.addNextSingleSelection(0)
		// Select Family
		userInteraction.addNextSingleSelection(1)
		// DiscardChanges
		userInteraction.addNextSingleSelection(0)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_2 + " " + LAST_NAME_1
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_1 + " " + LAST_NAME_1
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Test the insertion of a mother in an existing family with correct lastname
	 * and with existing mother. Users decides to replace existing mother.
	 */
	@Test
	def void testCreateFemale_Mother_InsertInMatchingFamily_HasMother_ReplaceMother(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		prepareMotherInNewFamily()	
		// Mother
		userInteraction.addNextSingleSelection(0)
		// Select Family
		userInteraction.addNextSingleSelection(1)
		// ReplaceMother
		userInteraction.addNextSingleSelection(1)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_2 + " " + LAST_NAME_1
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_2]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_2 + " " + LAST_NAME_1
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Test the insertion of a mother in an existing family with correct lastname
	 * and with existing mother. Users decides to insert new mother in new family.
	 */
	@Test
	def void testCreateFemale_Mother_InsertInMatchingFamily_HasMother_InsertInNewFamily(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		prepareMotherInNewFamily()
		// Mother
		userInteraction.addNextSingleSelection(0)
		// Select Family
		userInteraction.addNextSingleSelection(1)
		// InsertInNewFamily
		userInteraction.addNextSingleSelection(2)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_2 + " " + LAST_NAME_1
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
			]
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_2]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_1 + " " + LAST_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_2 + " " + LAST_NAME_1
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Test the insertion of a mother in an existing family with different lastname.
	 * Users decides to abort the process.
	 */
	@Test
	def void testCreateFemale_Mother_InsertInDifferentlyNamedFamily_NoMother_DiscardChanges(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		prepareFatherInNewFamily()
		// Mother
		userInteraction.addNextSingleSelection(0)
		// Select Family
		userInteraction.addNextSingleSelection(1)
		// DiscardChanges
		userInteraction.addNextSingleSelection(0)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_1 + " " + LAST_NAME_2
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_1 + " " + LAST_NAME_1
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Test the insertion of a mother in an existing family with different lastname.
	 * Users decides to change the lastname of the mother and then to insert her.
	 */
	@Test
	def void testCreateFemale_Mother_InsertInDifferentlyNamedFamily_NoMother_RenamePerson(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		prepareFatherInNewFamily()
		// Mother
		userInteraction.addNextSingleSelection(0)
		// Select Family
		userInteraction.addNextSingleSelection(1)
		// RenamePerson
		userInteraction.addNextSingleSelection(1)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_1 + " " + LAST_NAME_2
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_1 + " " + LAST_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_1 + " " + LAST_NAME_1
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Test the insertion of a mother in an existing family with different lastname.
	 * Users decides to rename the family and then to insert the mother.
	 */
	@Test
	def void testCreateFemale_Mother_InsertInDifferentlyNamedFamily_NoMother_RenameFamily(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		prepareFatherInNewFamily()
		// Mother
		userInteraction.addNextSingleSelection(0)
		// Select Family
		userInteraction.addNextSingleSelection(1)
		// RenameFamily
		userInteraction.addNextSingleSelection(2)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_1 + " " + LAST_NAME_2
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_2
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_1 + " " + LAST_NAME_2
			]
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_1 + " " + LAST_NAME_2
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Test the insertion of a mother in an existing family with different lastname.
	 * Users decides to create a new family and then to insert the mother in the new one.
	 */
	@Test
	def void testCreateFemale_Mother_InsertInDifferentlyNamedFamily_NoMother_InsertInNewFamily(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		prepareFatherInNewFamily()
		// Mother
		userInteraction.addNextSingleSelection(0)
		// Select Family
		userInteraction.addNextSingleSelection(1)
		// InsertInNewFamily
		userInteraction.addNextSingleSelection(3)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_1 + " " + LAST_NAME_2
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
			]
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_2
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_1 + " " + LAST_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_1 + " " + LAST_NAME_2
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Test the insertion of a mother in an existing family with different lastname which also
	 * already has a mother. Users decides to abort the process.
	 */
	@Test
	def void testCreateFemale_Mother_InsertInDifferentlyNamedFamily_HasMother_DiscardChanges(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		prepareMotherInNewFamily()
		// Mother
		userInteraction.addNextSingleSelection(0)
		// Select Family
		userInteraction.addNextSingleSelection(1)
		// DiscardChanges
		userInteraction.addNextSingleSelection(0)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_2 + " " + LAST_NAME_2
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_1 + " " + LAST_NAME_1
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Test the insertion of a mother in an existing family with different lastname which also
	 * already has a mother. Users decides to replace the existing mother but when the decision
	 * about either the mother or the family has to be renamed, the user aborts the process.
	 */
	@Test
	def void testCreateFemale_Mother_InsertInDifferentlyNamedFamily_HasMother_ReplaceMother_DiscardChanges(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		prepareMotherInNewFamily()
		// Mother
		userInteraction.addNextSingleSelection(0)
		// Select Family
		userInteraction.addNextSingleSelection(1)
		// ReplaceMother
		userInteraction.addNextSingleSelection(1)
		// DiscardChanges
		userInteraction.addNextSingleSelection(0)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_2 + " " + LAST_NAME_2
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_1 + " " + LAST_NAME_1
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Test the insertion of a mother in an existing family with different lastname which also
	 * already has a mother. Users decides to replace the existing mother and when the decision
	 * about either the mother or the family has to be renamed, the user chooses to rename the mother.
	 */
	@Test
	def void testCreateFemale_Mother_InsertInDifferentlyNamedFamily_HasMother_ReplaceMother_RenamePerson(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		prepareMotherInNewFamily()
		// Mother
		userInteraction.addNextSingleSelection(0)
		// Select Family
		userInteraction.addNextSingleSelection(1)
		// ReplaceMother
		userInteraction.addNextSingleSelection(1)
		// RenamePerson
		userInteraction.addNextSingleSelection(1)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_2 + " " + LAST_NAME_2
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_2]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_2 + " " + LAST_NAME_1
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Test the insertion of a mother in an existing family with different lastname which also
	 * already has a mother. Users decides to replace the existing mother and when the decision
	 * about either the mother or the family has to be renamed, the user chooses to rename the family.
	 */
	@Test
	def void testCreateFemale_Mother_InsertInDifferentlyNamedFamily_HasMother_ReplaceMother_RenameFamily(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		prepareMotherInNewFamily()
		// Mother
		userInteraction.addNextSingleSelection(0)
		// Select Family
		userInteraction.addNextSingleSelection(1)
		// ReplaceMother
		userInteraction.addNextSingleSelection(1)
		// RenameFamily
		userInteraction.addNextSingleSelection(2)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_2 + " " + LAST_NAME_2
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_2
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_2]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_2 + " " + LAST_NAME_2
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Test the insertion of a mother in an existing family with different lastname which also
	 * already has a mother. Users decides to replace the existing mother but when the decision
	 * about either the mother or the family has to be renamed, the user chooses to create instead
	 * a new family for the new mother.
	 */
	@Test
	def void testCreateFemale_Mother_InsertInDifferentlyNamedFamily_HasMother_ReplaceMother_InsertInNewFamily(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		prepareMotherInNewFamily()
		// Mother
		userInteraction.addNextSingleSelection(0)
		// Select Family
		userInteraction.addNextSingleSelection(1)
		// ReplaceMother
		userInteraction.addNextSingleSelection(1)
		// InserInNewFamily
		userInteraction.addNextSingleSelection(3)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_2 + " " + LAST_NAME_2
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
			]
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_2
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_2]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_1 + " " + LAST_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_2 + " " + LAST_NAME_2
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Test the insertion of a mother in an existing family with different lastname which also
	 * already has a mother. Users decides to create instead a new family for the new mother.
	 */
	@Test
	def void testCreateFemale_Mother_InsertInDifferentlyNamedFamily_HasMother_InsertInNewFamily(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		prepareMotherInNewFamily()
		// Mother
		userInteraction.addNextSingleSelection(0)
		// Select Family
		userInteraction.addNextSingleSelection(1)
		// InserInNewFamily
		userInteraction.addNextSingleSelection(2)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_2 + " " + LAST_NAME_2
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
			]
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_2
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_2]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_1 + " " + LAST_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_2 + " " + LAST_NAME_2
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	// ========== DAUGHTER ==========
	/**Test the creation of a new daughter into a new family.
	 */
	@Test
	def void testCreateFemale_Daughter_InsertInNewFamily(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		// Daughter
		userInteraction.addNextSingleSelection(1)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAU_1 + " " + LAST_NAME_1
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAU_1 + " " + LAST_NAME_1
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Test the insertion of a new daughter into an existing family with a matching lastname.
	 */
	@Test
	def void testCreateFemale_Daughter_InsertInMatchingFamily(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		prepareDaughterInNewFamily()
		// Daughter
		userInteraction.addNextSingleSelection(1)
		// SelectFamily
		userInteraction.addNextSingleSelection(1)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAU_2 + " " + LAST_NAME_1
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_2]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAU_1 + " " + LAST_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAU_2 + " " + LAST_NAME_1
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Test the insertion of a new daughter into an existing family with a different lastname.
	 * User decides to abort the process. 
	 */
	@Test
	def void testCreateFemale_Daughter_InsertInDifferentlyNamedFamily_DiscardChanges(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		prepareDaughterInNewFamily()
		// Daughter
		userInteraction.addNextSingleSelection(1)
		// SelectFamily
		userInteraction.addNextSingleSelection(1)
		// DiscardChanges
		userInteraction.addNextSingleSelection(0)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAU_2 + " " + LAST_NAME_2
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAU_1 + " " + LAST_NAME_1
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Test the insertion of a new daughter into an existing family with a different lastname.
	 * User decides to rename the new daughter and insert her afterwards. 
	 */
	@Test
	def void testCreateFemale_Daughter_InsertInDifferentlyNamedFamily_RenamePerson(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		prepareDaughterInNewFamily()
		// Daughter
		userInteraction.addNextSingleSelection(1)
		// SelectFamily
		userInteraction.addNextSingleSelection(1)
		// RenamePerson
		userInteraction.addNextSingleSelection(1)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAU_2 + " " + LAST_NAME_2
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_2]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAU_1 + " " + LAST_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAU_2 + " " + LAST_NAME_1
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Test the insertion of a new daughter into an existing family with a different lastname.
	 * User decides to rename the family and then insert the new daughter into the family. 
	 */
	@Test
	def void testCreateFemale_Daughter_InsertInDifferentlyNamedFamily_RenameFamily(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		prepareDaughterInNewFamily()
		// Daughter
		userInteraction.addNextSingleSelection(1)
		// SelectFamily
		userInteraction.addNextSingleSelection(1)
		// RenameFamily
		userInteraction.addNextSingleSelection(2)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAU_2 + " " + LAST_NAME_2
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_2
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_2]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAU_1 + " " + LAST_NAME_2
			]
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAU_2 + " " + LAST_NAME_2
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Test the insertion of a new daughter into an existing family with a different lastname.
	 * User decides to create a new family with the same lastname as the daughter has and then 
	 * inserts the new daughter into this new family. 
	 */
	@Test
	def void testCreateFemale_Daughter_InsertInDifferentlyNamedFamily_InsertInNewFamily(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		prepareDaughterInNewFamily()
		// Daughter
		userInteraction.addNextSingleSelection(1)
		// SelectFamily
		userInteraction.addNextSingleSelection(1)
		// InsertInNewFamily
		userInteraction.addNextSingleSelection(3)
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAU_2 + " " + LAST_NAME_2
			]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
			]
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_2
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_2]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAU_1 + " " + LAST_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAU_2 + " " + LAST_NAME_2
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	// ========== CREATE FAMILY ==========
	/**Test the concatination of multiple other tests to create a family which then builds the
	 * starting point for other tests in which a family in the {@link FamilyRegister} is needed.
	 */
	@Test
	def void createFamilyForTesting() {
		// Father
		prepareFatherInNewFamily() 
		//Mother
		userInteraction.addNextSingleSelection(0)
		// Insert in first family
		userInteraction.addNextSingleSelection(1)
		// Son
		userInteraction.addNextSingleSelection(1)
		// Insert in first family
		userInteraction.addNextSingleSelection(1)
		// Daughter
		userInteraction.addNextSingleSelection(1)
		// Insert in first family
		userInteraction.addNextSingleSelection(1)
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_1 + " " + LAST_NAME_1
			]
		]
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_1 + " " + LAST_NAME_1
			]
		]
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAU_1 + " " + LAST_NAME_1
			]
		]
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_1 + " " + LAST_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_1 + " " + LAST_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_1 + " " + LAST_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAU_1 + " " + LAST_NAME_1
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
	}

	// ========== EDITING ==========	
	/**Test the renaming of the firstname of a single person which should only effect this person
	 * and the corresponding {@link Member} in a {@link Family} in the {@link FamilyRegister}.
	 */
	@Test
	def void testRenamingOfFirstname(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		createFamilyForTesting()
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searched = persons.findFirst[x|x.fullName.equals(FIRST_DAD_1 + " " + LAST_NAME_1)]
			searched.fullName = FIRST_DAD_2 + " " + searched.fullName.split(" ").get(1)
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_2]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_2 + " " + LAST_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_1 + " " + LAST_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_1 + " " + LAST_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAU_1 + " " + LAST_NAME_1
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Test the renaming of the lastname of a single person which should effect this person,
	 * the {@link Family} which contains the corresponding {@link Member} to the person and
	 * all {@link Person}s which correspond to the renamed family since their lastnames should
	 * change as well.
	 */
	@Test
	def void testRenamingOfLastname(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		createFamilyForTesting()
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searched = persons.findFirst[x|x.fullName.equals(FIRST_DAD_1 + " " + LAST_NAME_1)]
			searched.fullName = FIRST_DAD_1 + " " + LAST_NAME_2
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_2
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_1 + " " + LAST_NAME_2
			]
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_1 + " " + LAST_NAME_2
			]
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_1 + " " + LAST_NAME_2
			]
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAU_1 + " " + LAST_NAME_2
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	// ========== DELETION ==========
	/**Test the deletion of a person when the corresponding {@link Family} still contains other
	 * {@link Member}s. In this case, this family and the remaining members should be untouched.
	 */
	@Test
	def void testDeletePerson_NotLastInFamily(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		createFamilyForTesting()
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searched = persons.findFirst[x|x.fullName.equals(FIRST_DAD_1 + " " + LAST_NAME_1)]
			persons.remove(searched)
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
			]
		]
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOM_1 + " " + LAST_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_1 + " " + LAST_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAU_1 + " " + LAST_NAME_1
			]
		]
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Test the deletion of a person when the corresponding {@link Family} does not contain
	 * any other {@link Member}s anymore. Without members a family should not exist. (Design-Decision!)
	 * Therefore, the empty family gets deleted as well.
	 */
	@Test
	def void testDeletePerson_LastInFamily(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		prepareFatherInNewFamily()
		logger.trace(surroundingMethodName + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searched = persons.findFirst[x|x.fullName.equals(FIRST_DAD_1 + " " + LAST_NAME_1)]
			persons.remove(searched)
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister comparisonFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => []
		val PersonRegister comparisonPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => []
		checkCorrectRegisters(comparisonFamilyRegister, comparisonPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Test the deletion of the {@link PersonRegister}. The corresponding {@link FamilyRegister}
	 * will be then deleted as well and all contained elements in both of the registers.
	 */
	@Test
	def void testDeletePersonsRegister(TestInfo testInfo) {
		val String surroundingMethodName = testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		this.createFamilyForTesting();
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
