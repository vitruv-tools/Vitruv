package tools.vitruv.applications.demo.familiespersons.tests.persons2families

import edu.kit.ipd.sdq.metamodels.persons.PersonRegister
import edu.kit.ipd.sdq.metamodels.persons.PersonsFactory
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tools.vitruv.domains.demo.families.FamiliesDomainProvider
import tools.vitruv.domains.demo.persons.PersonsDomainProvider

import static org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Disabled
import tools.vitruv.testutils.VitruvApplicationTest
import tools.vitruv.testutils.domains.DomainUtil
import tools.vitruv.applications.demo.familiespersons.persons2families.PersonsToFamiliesChangePropagationSpecification
import edu.kit.ipd.sdq.metamodels.families.FamilyRegister
import java.util.List
import static org.junit.jupiter.api.Assertions.assertEquals
import edu.kit.ipd.sdq.metamodels.persons.Male
import edu.kit.ipd.sdq.metamodels.persons.Female
import org.apache.log4j.Logger
import org.apache.log4j.Level
import static tools.vitruv.testutils.matchers.ModelMatchers.*
import static org.hamcrest.CoreMatchers.*
import edu.kit.ipd.sdq.metamodels.families.FamiliesFactory
import edu.kit.ipd.sdq.metamodels.families.Family
import java.nio.file.Path

/**Test to validate the transfer of changes from the PersonModel to the FamilyModel.
 * @author Dirk Neumann   
 */
class PersonsToFamiliesTest extends VitruvApplicationTest {
	static val logger = Logger.getLogger(PersonsToFamiliesTest);
	
	//First Set of reused static strings
	final static String FAMILY_NAME_1 = "Meier"
	final static String FIRST_FATHER_1 = "Anton"
	final static String FIRST_MOTHER_1 = "Berta"
	final static String FIRST_SON_1 = "Chris"
	final static String FIRST_DAUGHTER_1 = "Daria"	
	
	//Second Set of reused static strings
	final static String FAMILY_NAME_2 = "Schulze"
	final static String FIRST_FATHER_2 = "Adam"
	final static String FIRST_MOTHER_2 = "Birgit"
	final static String FIRST_SON_2 = "Charles"
	final static String FIRST_DAUGHTER_2 = "Daniela"	

	//Model Paths
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
	def void checkCorrectRegisters(FamilyRegister famEq, PersonRegister perEq){
		val pm = resourceAt(PERSONS_MODEL)
		val fm = resourceAt(FAMILIES_MODEL)
		assertThat(pm, exists)
		assertThat(fm, exists)
		assertEquals(1, pm.contents.size);
		assertEquals(1, fm.contents.size);
		val famReg = fm.contents.get(0)
		assertThat(famReg, instanceOf(FamilyRegister));
		val FamilyRegister castedFamReg = famReg as FamilyRegister
		assertThat(castedFamReg, equalsDeeply(famEq));
		val perReg = pm.contents.get(0)
		assertThat(perReg, instanceOf(PersonRegister));
		val PersonRegister castedPerReg = perReg as PersonRegister
		assertThat(castedPerReg, equalsDeeply(perEq));
	}
	
	
	//=====================================
	//             CREATE MALE
	//=====================================
	
	//========== FATHER ==========
	/**Test the creation of a father in a new family.
	 */
	@Test
	def void testCreateMale_Father_InsertInNewFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		//Father
		userInteraction.addNextSingleSelection(0)
		logger.trace(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_1 + " " + FAMILY_NAME_1
			]
		]
		logger.trace(name + " - propagation done")
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_1]
			]
		]
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_1 + " " + FAMILY_NAME_1
			]
		]
		checkCorrectRegisters(famEq, perEq)
		logger.trace(name + " - finished without errors")
	}
	
	/**Test the insertion of a father in an existing family with correct lastname.
	 */
	@Test
	def void testCreateMale_Father_InsertInMatchingFamily_NoFather() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		testCreateFemale_Mother_InsertInNewFamily()
		//Father
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		logger.trace(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_1 + " " + FAMILY_NAME_1
			]
		]
		logger.trace(name + " - propagation done")
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_1]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_1]
			]
		]
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_1 + " " + FAMILY_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_1 + " " + FAMILY_NAME_1
			]
		]		
		checkCorrectRegisters(famEq, perEq)		
		logger.trace(name + " - finished without errors")
	}
	/**Test the insertion of a father in an existing family with correct lastname
	 * and with existing father. Users decides to abort the process.
	 */
	@Test
	def void testCreateMale_Father_InsertInMatchingFamily_HasFather_DiscardChanges() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		testCreateMale_Father_InsertInNewFamily()
		//Father
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//DiscardChanges
		userInteraction.addNextSingleSelection(0)
		logger.trace(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_2 + " " + FAMILY_NAME_1
			]
		]
		logger.trace(name + " - propagation done")
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_1]
			]
		]
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_1 + " " + FAMILY_NAME_1
			]
		]
		checkCorrectRegisters(famEq, perEq)
		logger.trace(name + " - finished without errors")
	}
	/**Test the insertion of a father in an existing family with correct lastname
	 * and with existing father. Users decides to replace existing father.
	 */
	@Test
	def void testCreateMale_Father_InsertInMatchingFamily_HasFather_ReplaceFather() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		testCreateMale_Father_InsertInNewFamily()
		//Father
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//ReplaceFather
		userInteraction.addNextSingleSelection(1)
		logger.trace(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_2 + " " + FAMILY_NAME_1
			]
		]
		logger.trace(name + " - propagation done")
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_2]
			]
		]
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_2 + " " + FAMILY_NAME_1
			]
		]
		checkCorrectRegisters(famEq, perEq)
		logger.trace(name + " - finished without errors")
	}
	/**Test the insertion of a father in an existing family with correct lastname
	 * and with existing father. Users decides to insert new father in new family.
	 */
	@Test
	def void testCreateMale_Father_InsertInMatchingFamily_HasFather_InsertInNewFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		testCreateMale_Father_InsertInNewFamily()
		//Father
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//InsertInNewFamily
		userInteraction.addNextSingleSelection(2)
		logger.trace(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_2 + " " + FAMILY_NAME_1
			]
		]
		logger.trace(name + " - propagation done")
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_1]
			]
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_2]
			]
		]
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_1 + " " + FAMILY_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_2 + " " + FAMILY_NAME_1
			]
		]
		checkCorrectRegisters(famEq, perEq)
		logger.trace(name + " - finished without errors")
	}
	
	/**Test the insertion of a father in an existing family with different lastname.
	 * Users decides to abort the process.
	 */
	@Test
	def void testCreateMale_Father_InsertInDifferentlyNamedFamily_NoFather_DiscardChanges() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		testCreateFemale_Mother_InsertInNewFamily()
		//Father
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//DiscardChanges
		userInteraction.addNextSingleSelection(0)
		logger.trace(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_1 + " " + FAMILY_NAME_2
			]
		]
		logger.trace(name + " - propagation done")
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_1]
			]
		]
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_1 + " " + FAMILY_NAME_1
			]
		]
		checkCorrectRegisters(famEq, perEq)
		logger.trace(name + " - finished without errors")
	}	
	/**Test the insertion of a father in an existing family with different lastname.
	 * Users decides to change the lastname of the father and then to insert him.
	 */
	@Test
	def void testCreateMale_Father_InsertInDifferentlyNamedFamily_NoFather_RenamePerson() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		testCreateFemale_Mother_InsertInNewFamily()
		//Father
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//RenamePerson
		userInteraction.addNextSingleSelection(1)
		logger.trace(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_1 + " " + FAMILY_NAME_2
			]
		]
		logger.trace(name + " - propagation done")
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_1]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_1]
			]
		]
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_1 + " " + FAMILY_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_1 + " " + FAMILY_NAME_1
			]
		]
		checkCorrectRegisters(famEq, perEq)
		logger.trace(name + " - finished without errors")
	}
	/**Test the insertion of a father in an existing family with different lastname.
	 * Users decides to rename the family and then to insert the father.
	 */
	@Test
	def void testCreateMale_Father_InsertInDifferentlyNamedFamily_NoFather_RenameFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		testCreateFemale_Mother_InsertInNewFamily()
		//Father
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//RenameFamily
		userInteraction.addNextSingleSelection(2)
		logger.trace(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_1 + " " + FAMILY_NAME_2
			]
		]
		logger.trace(name + " - propagation done")	
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_2
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_1]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_1]
			]
		]
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_1 + " " + FAMILY_NAME_2
			]
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_1 + " " + FAMILY_NAME_2
			]
		]
		checkCorrectRegisters(famEq, perEq)
		logger.trace(name + " - finished without errors")
	}
	/**Test the insertion of a father in an existing family with different lastname.
	 * Users decides to create a new family and then to insert the father in the new one.
	 */
	@Test
	def void testCreateMale_Father_InsertInDifferentlyNamedFamily_NoFather_InsertInNewFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		testCreateFemale_Mother_InsertInNewFamily()
		//Father
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//InsertInNewFamily
		userInteraction.addNextSingleSelection(3)
		logger.trace(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_1 + " " + FAMILY_NAME_2
			]
		]
		logger.trace(name + " - propagation done")
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_2
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_1]
			]
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_1]
			]
		]
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_1 + " " + FAMILY_NAME_2
			]
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_1 + " " + FAMILY_NAME_1
			]
		]
		checkCorrectRegisters(famEq, perEq)
		logger.trace(name + " - finished without errors")
	}
	
	/**Test the insertion of a father in an existing family with different lastname which also
	 * already has a father. Users decides to abort the process.
	 */
	@Test
	def void testCreateMale_Father_InsertInDifferentlyNamedFamily_HasFather_DiscardChanges() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		testCreateMale_Father_InsertInNewFamily()
		//Father
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//DiscardChanges
		userInteraction.addNextSingleSelection(0)
		logger.trace(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_2 + " " + FAMILY_NAME_2
			]
		]
		logger.trace(name + " - propagation done")
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_1]
			]
		]
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_1 + " " + FAMILY_NAME_1
			]
		]
		checkCorrectRegisters(famEq, perEq)
		logger.trace(name + " - finished without errors")
	}
	/**Test the insertion of a father in an existing family with different lastname which also
	 * already has a father. Users decides to replace the existing father but when the decision
	 * about either the father or the family has to be renamed, the user aborts the process.
	 */
	@Test
	def void testCreateMale_Father_InsertInDifferentlyNamedFamily_HasFather_ReplaceFather_DiscardChanges() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		testCreateMale_Father_InsertInNewFamily()
		//Father
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//ReplaceFather
		userInteraction.addNextSingleSelection(1)
		//DiscardChanges
		userInteraction.addNextSingleSelection(0)
		logger.trace(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_2 + " " + FAMILY_NAME_2
			]
		]
		logger.trace(name + " - propagation done")
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_1]
			]
		]
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_1 + " " + FAMILY_NAME_1
			]
		]
		checkCorrectRegisters(famEq, perEq)
		logger.trace(name + " - finished without errors")
	}
	/**Test the insertion of a father in an existing family with different lastname which also
	 * already has a father. Users decides to replace the existing father and when the decision
	 * about either the father or the family has to be renamed, the user chooses to rename the father.
	 */
	@Test
	def void testCreateMale_Father_InsertInDifferentlyNamedFamily_HasFather_ReplaceFather_RenamePerson() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		testCreateMale_Father_InsertInNewFamily()
		//Father
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//ReplaceFather
		userInteraction.addNextSingleSelection(1)
		//RenamePerson
		userInteraction.addNextSingleSelection(1)
		logger.trace(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_2 + " " + FAMILY_NAME_2
			]
		]
		logger.trace(name + " - propagation done")
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_2]
			]
		]
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_2 + " " + FAMILY_NAME_1
			]
		]
		checkCorrectRegisters(famEq, perEq)
		logger.trace(name + " - finished without errors")
	}
	/**Test the insertion of a father in an existing family with different lastname which also
	 * already has a father. Users decides to replace the existing father and when the decision
	 * about either the father or the family has to be renamed, the user chooses to rename the family.
	 */
	@Test
	def void testCreateMale_Father_InsertInDifferentlyNamedFamily_HasFather_ReplaceFather_RenameFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		testCreateMale_Father_InsertInNewFamily()
		//Father
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//ReplaceFather
		userInteraction.addNextSingleSelection(1)
		//RenameFamily
		userInteraction.addNextSingleSelection(2)
		logger.trace(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_2 + " " + FAMILY_NAME_2
			]
		]
		logger.trace(name + " - propagation done")
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_2
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_2]
			]
		]
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_2 + " " + FAMILY_NAME_2
			]
		]
		checkCorrectRegisters(famEq, perEq)
		logger.trace(name + " - finished without errors")
	}
	/**Test the insertion of a father in an existing family with different lastname which also
	 * already has a father. Users decides to replace the existing father but when the decision
	 * about either the father or the family has to be renamed, the user chooses to create instead
	 * a new family for the new father.
	 */
	@Test
	def void testCreateMale_Father_InsertInDifferentlyNamedFamily_HasFather_ReplaceFather_InsertInNewFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		testCreateMale_Father_InsertInNewFamily()
		//Father
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//ReplaceFather
		userInteraction.addNextSingleSelection(1)
		//InsertInNewFamily
		userInteraction.addNextSingleSelection(3)
		logger.trace(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_2 + " " + FAMILY_NAME_2
			]
		]
		logger.trace(name + " - propagation done")
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_1]
			]
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_2
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_2]
			]
		]
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_1 + " " + FAMILY_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_2 + " " + FAMILY_NAME_2
			]
		]
		checkCorrectRegisters(famEq, perEq)
		logger.trace(name + " - finished without errors")
	}
	/**Test the insertion of a father in an existing family with different lastname which also
	 * already has a father. Users decides to create instead a new family for the new father.
	 */
	@Test
	def void testCreateMale_Father_InsertInDifferentlyNamedFamily_HasFather_InsertInNewFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		testCreateMale_Father_InsertInNewFamily()
		//Father
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//InsertInNewFamily
		userInteraction.addNextSingleSelection(2)
		logger.trace(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_2 + " " + FAMILY_NAME_2
			]
		]
		logger.trace(name + " - propagation done")
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_1]
			]
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_2
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_2]
			]
		]
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_1 + " " + FAMILY_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_2 + " " + FAMILY_NAME_2
			]
		]
		checkCorrectRegisters(famEq, perEq)
		logger.trace(name + " - finished without errors")
	}
	
	//========== SON ==========
	/**Test the creation of a new son into a new family.
	 */
	@Test
	def void testCreateMale_Son_InsertInNewFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		//Son
		userInteraction.addNextSingleSelection(1)
		logger.trace(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_1 + " " + FAMILY_NAME_1
			]
		]
		logger.trace(name + " - propagation done")
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
			]
		]
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_1 + " " + FAMILY_NAME_1
			]
		]
		checkCorrectRegisters(famEq, perEq)
		logger.trace(name + " - finished without errors")
	}
	/**Test the insertion of a new son into an existing family with a matching lastname.
	 */
	@Test	
	def void testCreateMale_Son_InsertInMatchingFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		testCreateMale_Son_InsertInNewFamily()
		//Son
		userInteraction.addNextSingleSelection(1)
		//SelectFamily
		userInteraction.addNextSingleSelection(1)
		logger.trace(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_2 + " " + FAMILY_NAME_1
			]
		]
		logger.trace(name + " - propagation done")
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_2]
			]
		]
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_1 + " " + FAMILY_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_2 + " " + FAMILY_NAME_1
			]
		]
		checkCorrectRegisters(famEq, perEq)
		logger.trace(name + " - finished without errors")
	}
	/**Test the insertion of a new son into an existing family with a different lastname.
	 * User decides to abort the process. 
	 */
	@Test
	def void testCreateMale_Son_InsertInDifferentlyNamedFamily_DiscardChanges() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		testCreateMale_Son_InsertInNewFamily()
		//Son
		userInteraction.addNextSingleSelection(1)
		//SelectFamily
		userInteraction.addNextSingleSelection(1)
		//DiscardChanges
		userInteraction.addNextSingleSelection(0)
		logger.trace(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_2 + " " + FAMILY_NAME_2
			]
		]
		logger.trace(name + " - propagation done")
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
			]
		]
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_1 + " " + FAMILY_NAME_1
			]
		]
		checkCorrectRegisters(famEq, perEq)
		logger.trace(name + " - finished without errors")
	}
	/**Test the insertion of a new son into an existing family with a different lastname.
	 * User decides to rename the new son and insert him afterwards. 
	 */
	@Test
	def void testCreateMale_Son_InsertInDifferentlyNamedFamily_RenamePerson() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		testCreateMale_Son_InsertInNewFamily()
		//Son
		userInteraction.addNextSingleSelection(1)
		//SelectFamily
		userInteraction.addNextSingleSelection(1)
		//RenamePerson
		userInteraction.addNextSingleSelection(1)
		logger.trace(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_2 + " " + FAMILY_NAME_2
			]
		]
		logger.trace(name + " - propagation done")
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_2]
			]
		]
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_1 + " " + FAMILY_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_2 + " " + FAMILY_NAME_1
			]
		]
		checkCorrectRegisters(famEq, perEq)
		logger.trace(name + " - finished without errors")
	}
	/**Test the insertion of a new son into an existing family with a different lastname.
	 * User decides to rename the family and then insert the new son into the family. 
	 */
	@Test
	def void testCreateMale_Son_InsertInDifferentlyNamedFamily_RenameFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		testCreateMale_Son_InsertInNewFamily()
		//Son
		userInteraction.addNextSingleSelection(1)
		//SelectFamily
		userInteraction.addNextSingleSelection(1)
		//RenameFamily
		userInteraction.addNextSingleSelection(2)
		logger.trace(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_2 + " " + FAMILY_NAME_2
			]
		]
		logger.trace(name + " - propagation done")		
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_2
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_2]
			]
		]
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_1 + " " + FAMILY_NAME_2
			]
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_2 + " " + FAMILY_NAME_2
			]
		]
		checkCorrectRegisters(famEq, perEq)
		logger.trace(name + " - finished without errors")
	}
	/**Test the insertion of a new son into an existing family with a different lastname.
	 * User decides to create a new family with the same lastname as the son has and then 
	 * inserts the new son into this new family. 
	 */
	@Test
	def void testCreateMale_Son_InsertInDifferentlyNamedFamily_InsertInNewFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		testCreateMale_Son_InsertInNewFamily()
		//Son
		userInteraction.addNextSingleSelection(1)
		//SelectFamily
		userInteraction.addNextSingleSelection(1)
		//InsertInNewFamily
		userInteraction.addNextSingleSelection(3)
		logger.trace(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_2 + " " + FAMILY_NAME_2
			]
		]
		logger.trace(name + " - propagation done")		
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
			]
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_2
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_2]
			]
		]
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_1 + " " + FAMILY_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_2 + " " + FAMILY_NAME_2
			]
		]
		checkCorrectRegisters(famEq, perEq)
		logger.trace(name + " - finished without errors")
	}


	//=====================================
	//             CREATE FEMALE
	//=====================================
	
	//========== MOTHER ==========
	/**Test the creation of a father in a new family.
	 */
	@Test
	def void testCreateFemale_Mother_InsertInNewFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		userInteraction.addNextSingleSelection(0)
		logger.trace(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_1 + " " + FAMILY_NAME_1
			]
		]		
		logger.trace(name + " - propagation done")		
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_1]
			]
		]
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_1 + " " + FAMILY_NAME_1
			]
		]
		checkCorrectRegisters(famEq, perEq)
		logger.trace(name + " - finished without errors")
	}
	
	/**Test the insertion of a father in an existing family with correct lastname.
	 */
	@Test
	def void testCreateFemale_Mother_InsertInMatchingFamily_NoMother() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		testCreateMale_Father_InsertInNewFamily()
		userInteraction.addNextSingleSelection(0)
		userInteraction.addNextSingleSelection(1)
		logger.trace(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_1 + " " + FAMILY_NAME_1
			]
		]
		logger.trace(name + " - propagation done")
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_1]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_1]
			]
		]
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_1 + " " + FAMILY_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_1 + " " + FAMILY_NAME_1
			]
		]
		checkCorrectRegisters(famEq, perEq)
		logger.trace(name + " - finished without errors")
	}
	/**Test the insertion of a mother in an existing family with correct lastname
	 * and with existing mother. Users decides to abort the process.
	 */
	@Test
	def void testCreateFemale_Mother_InsertInMatchingFamily_HasMother_DiscardChanges() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		testCreateFemale_Mother_InsertInNewFamily()
		//Mother
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//DiscardChanges
		userInteraction.addNextSingleSelection(0)
		logger.trace(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_2 + " " + FAMILY_NAME_1
			]
		]
		logger.trace(name + " - propagation done")
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_1]
			]
		]
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_1 + " " + FAMILY_NAME_1
			]
		]
		checkCorrectRegisters(famEq, perEq)
		logger.trace(name + " - finished without errors")
	}
	/**Test the insertion of a mother in an existing family with correct lastname
	 * and with existing mother. Users decides to replace existing mother.
	 */
	@Test
	def void testCreateFemale_Mother_InsertInMatchingFamily_HasMother_ReplaceMother() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		testCreateFemale_Mother_InsertInNewFamily()
		//Mother
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//ReplaceMother
		userInteraction.addNextSingleSelection(1)
		logger.trace(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_2 + " " + FAMILY_NAME_1
			]
		]
		logger.trace(name + " - propagation done")
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_2]
			]
		]
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_2 + " " + FAMILY_NAME_1
			]
		]
		checkCorrectRegisters(famEq, perEq)
		logger.trace(name + " - finished without errors")
	}
	 /**Test the insertion of a mother in an existing family with correct lastname
	 * and with existing mother. Users decides to insert new mother in new family.
	 */
	@Test
	def void testCreateFemale_Mother_InsertInMatchingFamily_HasMother_InsertInNewFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		testCreateFemale_Mother_InsertInNewFamily()
		//Mother
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//InsertInNewFamily
		userInteraction.addNextSingleSelection(2)
		logger.trace(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_2 + " " + FAMILY_NAME_1
			]
		]
		logger.trace(name + " - propagation done")
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_1]
			]
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_2]
			]
		]
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_1 + " " + FAMILY_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_2 + " " + FAMILY_NAME_1
			]
		]
		checkCorrectRegisters(famEq, perEq)
		logger.trace(name + " - finished without errors")
	}
	
	/**Test the insertion of a mother in an existing family with different lastname.
	 * Users decides to abort the process.
	 */
	@Test
	def void testCreateFemale_Mother_InsertInDifferentlyNamedFamily_NoMother_DiscardChanges() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		testCreateMale_Father_InsertInNewFamily()
		//Mother
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//DiscardChanges
		userInteraction.addNextSingleSelection(0)
		logger.trace(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_1 + " " + FAMILY_NAME_2
			]
		]
		logger.trace(name + " - propagation done")
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_1]
			]
		]
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_1 + " " + FAMILY_NAME_1
			]
		]
		checkCorrectRegisters(famEq, perEq)
		logger.trace(name + " - finished without errors")
	}
	/**Test the insertion of a mother in an existing family with different lastname.
	 * Users decides to change the lastname of the mother and then to insert her.
	 */
	@Test
	def void testCreateFemale_Mother_InsertInDifferentlyNamedFamily_NoMother_RenamePerson() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		testCreateMale_Father_InsertInNewFamily()
		//Mother
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//RenamePerson
		userInteraction.addNextSingleSelection(1)
		logger.trace(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_1 + " " + FAMILY_NAME_2
			]
		]
		logger.trace(name + " - propagation done")
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_1]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_1]
			]
		]
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_1 + " " + FAMILY_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_1 + " " + FAMILY_NAME_1
			]
		]
		checkCorrectRegisters(famEq, perEq)
		logger.trace(name + " - finished without errors")
	}
	/**Test the insertion of a mother in an existing family with different lastname.
	 * Users decides to rename the family and then to insert the mother.
	 */
	@Test
	def void testCreateFemale_Mother_InsertInDifferentlyNamedFamily_NoMother_RenameFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		testCreateMale_Father_InsertInNewFamily()
		//Mother
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//RenameFamily
		userInteraction.addNextSingleSelection(2)
		logger.trace(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_1 + " " + FAMILY_NAME_2
			]
		]
		logger.trace(name + " - propagation done")
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_2
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_1]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_1]
			]
		]
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_1 + " " + FAMILY_NAME_2
			]
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_1 + " " + FAMILY_NAME_2
			]
		]
		checkCorrectRegisters(famEq, perEq)
		logger.trace(name + " - finished without errors")
	}
	/**Test the insertion of a mother in an existing family with different lastname.
	 * Users decides to create a new family and then to insert the mother in the new one.
	 */
	@Test
	def void testCreateFemale_Mother_InsertInDifferentlyNamedFamily_NoMother_InsertInNewFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		testCreateMale_Father_InsertInNewFamily()
		//Mother
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//InsertInNewFamily
		userInteraction.addNextSingleSelection(3)
		logger.trace(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_1 + " " + FAMILY_NAME_2
			]
		]
		logger.trace(name + " - propagation done")
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_1]
			]
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_2
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_1]
			]
		]
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_1 + " " + FAMILY_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_1 + " " + FAMILY_NAME_2
			]
		]
		checkCorrectRegisters(famEq, perEq)
		logger.trace(name + " - finished without errors")
	}
	
	/**Test the insertion of a mother in an existing family with different lastname which also
	 * already has a mother. Users decides to abort the process.
	 */
	@Test
	def void testCreateFemale_Mother_InsertInDifferentlyNamedFamily_HasMother_DiscardChanges() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		testCreateFemale_Mother_InsertInNewFamily()
		//Mother
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//DiscardChanges
		userInteraction.addNextSingleSelection(0)
		logger.trace(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_2 + " " + FAMILY_NAME_2
			]
		]
		logger.trace(name + " - propagation done")
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_1]
			]
		]
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_1 + " " + FAMILY_NAME_1
			]
		]
		checkCorrectRegisters(famEq, perEq)
		logger.trace(name + " - finished without errors")
	}
	/**Test the insertion of a mother in an existing family with different lastname which also
	 * already has a mother. Users decides to replace the existing mother but when the decision
	 * about either the mother or the family has to be renamed, the user aborts the process.
	 */
	@Test
	def void testCreateFemale_Mother_InsertInDifferentlyNamedFamily_HasMother_ReplaceMother_DiscardChanges() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		testCreateFemale_Mother_InsertInNewFamily()
		//Mother
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//ReplaceMother
		userInteraction.addNextSingleSelection(1)
		//DiscardChanges
		userInteraction.addNextSingleSelection(0)
		logger.trace(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_2 + " " + FAMILY_NAME_2
			]
		]
		logger.trace(name + " - propagation done")
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_1]
			]
		]
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_1 + " " + FAMILY_NAME_1
			]
		]
		checkCorrectRegisters(famEq, perEq)
		logger.trace(name + " - finished without errors")
	}
	/**Test the insertion of a mother in an existing family with different lastname which also
	 * already has a mother. Users decides to replace the existing mother and when the decision
	 * about either the mother or the family has to be renamed, the user chooses to rename the mother.
	 */
	@Test
	def void testCreateFemale_Mother_InsertInDifferentlyNamedFamily_HasMother_ReplaceMother_RenamePerson() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		testCreateFemale_Mother_InsertInNewFamily()
		//Mother
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//ReplaceMother
		userInteraction.addNextSingleSelection(1)
		//RenamePerson
		userInteraction.addNextSingleSelection(1)
		logger.trace(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_2 + " " + FAMILY_NAME_2
			]
		]
		logger.trace(name + " - propagation done")
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_2]
			]
		]
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_2 + " " + FAMILY_NAME_1
			]
		]
		checkCorrectRegisters(famEq, perEq)
		logger.trace(name + " - finished without errors")
	}
	/**Test the insertion of a mother in an existing family with different lastname which also
	 * already has a mother. Users decides to replace the existing mother and when the decision
	 * about either the mother or the family has to be renamed, the user chooses to rename the family.
	 */
	@Test
	def void testCreateFemale_Mother_InsertInDifferentlyNamedFamily_HasMother_ReplaceMother_RenameFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		testCreateFemale_Mother_InsertInNewFamily()
		//Mother
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//ReplaceMother
		userInteraction.addNextSingleSelection(1)
		//RenameFamily
		userInteraction.addNextSingleSelection(2)
		logger.trace(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_2 + " " + FAMILY_NAME_2
			]
		]
		logger.trace(name + " - propagation done")
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_2
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_2]
			]
		]
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_2 + " " + FAMILY_NAME_2
			]
		]
		checkCorrectRegisters(famEq, perEq)
		logger.trace(name + " - finished without errors")
	}
	/**Test the insertion of a mother in an existing family with different lastname which also
	 * already has a mother. Users decides to replace the existing mother but when the decision
	 * about either the mother or the family has to be renamed, the user chooses to create instead
	 * a new family for the new mother.
	 */
	@Test
	def void testCreateFemale_Mother_InsertInDifferentlyNamedFamily_HasMother_ReplaceMother_InsertInNewFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		testCreateFemale_Mother_InsertInNewFamily()
		//Mother
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//ReplaceMother
		userInteraction.addNextSingleSelection(1)
		//InserInNewFamily
		userInteraction.addNextSingleSelection(3)
		logger.trace(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_2 + " " + FAMILY_NAME_2				
			]
		]
		logger.trace(name + " - propagation done")
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_1]
			]
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_2
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_2]
			]
		]
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_1 + " " + FAMILY_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_2 + " " + FAMILY_NAME_2
			]
		]
		checkCorrectRegisters(famEq, perEq)
		logger.trace(name + " - finished without errors")
	}
	/**Test the insertion of a mother in an existing family with different lastname which also
	 * already has a mother. Users decides to create instead a new family for the new mother.
	 */
	@Test
	def void testCreateFemale_Mother_InsertInDifferentlyNamedFamily_HasMother_InsertInNewFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		testCreateFemale_Mother_InsertInNewFamily()
		//Mother
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//InserInNewFamily
		userInteraction.addNextSingleSelection(2)
		logger.trace(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_2 + " " + FAMILY_NAME_2
			]
		]
		logger.trace(name + " - propagation done")
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_1]
			]
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_2
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_2]
			]
		]
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_1 + " " + FAMILY_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_2 + " " + FAMILY_NAME_2
			]
		]
		checkCorrectRegisters(famEq, perEq)
		logger.trace(name + " - finished without errors")
	}
	
	//========== DAUGHTER ==========
	/**Test the creation of a new daughter into a new family.
	 */
	@Test
	def void testCreateFemale_Daughter_InsertInNewFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		//Daughter
		userInteraction.addNextSingleSelection(1)
		logger.trace(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAUGHTER_1 + " " + FAMILY_NAME_1
			]
		]
		logger.trace(name + " - propagation done")
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAUGHTER_1]
			]
		]
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAUGHTER_1 + " " + FAMILY_NAME_1
			]
		]
		checkCorrectRegisters(famEq, perEq)
		logger.trace(name + " - finished without errors")
	}
	/**Test the insertion of a new daughter into an existing family with a matching lastname.
	 */
	@Test
	def void testCreateFemale_Daughter_InsertInMatchingFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		testCreateFemale_Daughter_InsertInNewFamily()
		//Daughter
		userInteraction.addNextSingleSelection(1)
		//SelectFamily
		userInteraction.addNextSingleSelection(1)
		logger.trace(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAUGHTER_2 + " " + FAMILY_NAME_1
			]
		]
		logger.trace(name + " - propagation done")
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAUGHTER_1]
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAUGHTER_2]
			]
		]
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAUGHTER_1 + " " + FAMILY_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAUGHTER_2 + " " + FAMILY_NAME_1
			]
		]
		checkCorrectRegisters(famEq, perEq)
		logger.trace(name + " - finished without errors")
	}
	/**Test the insertion of a new daughter into an existing family with a different lastname.
	 * User decides to abort the process. 
	 */
	@Test
	def void testCreateFemale_Daughter_InsertInDifferentlyNamedFamily_DiscardChanges() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		testCreateFemale_Daughter_InsertInNewFamily()
		//Daughter
		userInteraction.addNextSingleSelection(1)
		//SelectFamily
		userInteraction.addNextSingleSelection(1)
		//DiscardChanges
		userInteraction.addNextSingleSelection(0)
		logger.trace(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAUGHTER_2 + " " + FAMILY_NAME_2
			]
		]
		logger.trace(name + " - propagation done")
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAUGHTER_1]
			]
		]
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAUGHTER_1 + " " + FAMILY_NAME_1
			]
		]
		checkCorrectRegisters(famEq, perEq)
		logger.trace(name + " - finished without errors")
	}
	/**Test the insertion of a new daughter into an existing family with a different lastname.
	 * User decides to rename the new daughter and insert her afterwards. 
	 */
	@Test
	def void testCreateFemale_Daughter_InsertInDifferentlyNamedFamily_RenamePerson() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		testCreateFemale_Daughter_InsertInNewFamily()
		//Daughter
		userInteraction.addNextSingleSelection(1)
		//SelectFamily
		userInteraction.addNextSingleSelection(1)
		//RenamePerson
		userInteraction.addNextSingleSelection(1)
		logger.trace(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAUGHTER_2 + " " + FAMILY_NAME_2
			]
		]
		logger.trace(name + " - propagation done")
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAUGHTER_1]
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAUGHTER_2]
			]
		]
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAUGHTER_1 + " " + FAMILY_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAUGHTER_2 + " " + FAMILY_NAME_1
			]
		]
		checkCorrectRegisters(famEq, perEq)
		logger.trace(name + " - finished without errors")
	}
	/**Test the insertion of a new daughter into an existing family with a different lastname.
	 * User decides to rename the family and then insert the new daughter into the family. 
	 */
	@Test
	def void testCreateFemale_Daughter_InsertInDifferentlyNamedFamily_RenameFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		testCreateFemale_Daughter_InsertInNewFamily()
		//Daughter
		userInteraction.addNextSingleSelection(1)
		//SelectFamily
		userInteraction.addNextSingleSelection(1)
		//RenameFamily
		userInteraction.addNextSingleSelection(2)
		logger.trace(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAUGHTER_2 + " " + FAMILY_NAME_2
			]
		]
		logger.trace(name + " - propagation done")
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_2
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAUGHTER_1]
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAUGHTER_2]
			]
		]
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAUGHTER_1 + " " + FAMILY_NAME_2
			]
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAUGHTER_2 + " " + FAMILY_NAME_2
			]
		]
		checkCorrectRegisters(famEq, perEq)
		logger.trace(name + " - finished without errors")
	}
	/**Test the insertion of a new daughter into an existing family with a different lastname.
	 * User decides to create a new family with the same lastname as the daughter has and then 
	 * inserts the new daughter into this new family. 
	 */
	@Test
	def void testCreateFemale_Daughter_InsertInDifferentlyNamedFamily_InsertInNewFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		testCreateFemale_Daughter_InsertInNewFamily()
		//Daughter
		userInteraction.addNextSingleSelection(1)
		//SelectFamily
		userInteraction.addNextSingleSelection(1)
		//InsertInNewFamily
		userInteraction.addNextSingleSelection(3)
		logger.trace(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAUGHTER_2 + " " + FAMILY_NAME_2
			]
		]
		logger.trace(name + " - propagation done")
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAUGHTER_1]
			]
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_2
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAUGHTER_2]
			]
		]
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAUGHTER_1 + " " + FAMILY_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAUGHTER_2 + " " + FAMILY_NAME_2
			]
		]
		checkCorrectRegisters(famEq, perEq)
		logger.trace(name + " - finished without errors")
	}


	//========== CREATE FAMILY ==========
	/**Test the concatination of multiple other tests to create a family which then builds the
	 * starting point for other tests in which a family in the {@link FamilyRegister} is needed.
	 */
	@Test
	def void createFamilyForTesting(){
		//Create father and mother 
		testCreateFemale_Mother_InsertInMatchingFamily_NoMother()
		//Son
		userInteraction.addNextSingleSelection(1)
		//Insert in first family
		userInteraction.addNextSingleSelection(1)
		//Daughter
		userInteraction.addNextSingleSelection(1)
		//Insert in first family
		userInteraction.addNextSingleSelection(1)
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_1 + " " + FAMILY_NAME_1
			]
		]
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAUGHTER_1 + " " + FAMILY_NAME_1
			]
		]
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_1]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_1]
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAUGHTER_1]
			]
		]
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_1 + " " + FAMILY_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_1 + " " + FAMILY_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_1 + " " + FAMILY_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAUGHTER_1 + " " + FAMILY_NAME_1
			]
		]
		checkCorrectRegisters(famEq, perEq)
	}
	
	//========== EDITING ==========	
	/**Test the renaming of the firstname of a single person which should only effect this person
	 * and the corresponding {@link Member} in a {@link Family} in the {@link FamilyRegister}.
	 */
	@Test
	def void testRenamingOfFirstname(){
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		createFamilyForTesting()
		logger.trace(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searched = persons.findFirst[x|x.fullName.equals(FIRST_FATHER_1 + " " + FAMILY_NAME_1)]
			searched.fullName = FIRST_FATHER_2 + " " + searched.fullName.split(" ").get(1)
		]		
		logger.trace(name + " - propagation done")
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_2]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_1]
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAUGHTER_1]
			]
		]
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_2 + " " + FAMILY_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_1 + " " + FAMILY_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_1 + " " + FAMILY_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAUGHTER_1 + " " + FAMILY_NAME_1
			]
		]
		checkCorrectRegisters(famEq, perEq)
		logger.trace(name + " - finished without errors")		
	}
	/**Test the renaming of the lastname of a single person which should effect this person,
	 * the {@link Family} which contains the corresponding {@link Member} to the person and
	 * all {@link Person}s which correspond to the renamed family since their lastnames should
	 * change as well.
	 */
	@Test
	def void testRenamingOfLastname(){
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		createFamilyForTesting()
		logger.trace(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searched = persons.findFirst[x|x.fullName.equals(FIRST_FATHER_1 + " " + FAMILY_NAME_1)]
			searched.fullName = FIRST_FATHER_1 + " " + FAMILY_NAME_2
		]		
		logger.trace(name + " - propagation done")
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_2
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_1]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_1]
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAUGHTER_1]
			]
		]
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_1 + " " + FAMILY_NAME_2
			]
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_1 + " " + FAMILY_NAME_2
			]
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_1 + " " + FAMILY_NAME_2
			]
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAUGHTER_1 + " " + FAMILY_NAME_2
			]
		]
		checkCorrectRegisters(famEq, perEq)
		logger.trace(name + " - finished without errors")		
	}

	//========== DELETION ==========
	/**Test the deletion of a person when the corresponding {@link Family} still contains other
	 * {@link Member}s. In this case, this family and the remaining members should be untouched.
	 */
	@Test
	def void testDeletePerson_NotLastInFamily(){
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		createFamilyForTesting()
		logger.trace(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searched = persons.findFirst[x|x.fullName.equals(FIRST_FATHER_1 + " " + FAMILY_NAME_1)]
			persons.remove(searched)
		]		
		logger.trace(name + " - propagation done")
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_1]
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAUGHTER_1]
			]
		]
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_1 + " " + FAMILY_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_1 + " " + FAMILY_NAME_1
			]
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAUGHTER_1 + " " + FAMILY_NAME_1
			]
		]
		checkCorrectRegisters(famEq, perEq)
		logger.trace(name + " - finished without errors")		
	} 
	/**Test the deletion of a person when the corresponding {@link Family} does not contain
	 * any other {@link Member}s anymore. Without members a family should not exist. (Design-Decision!)
	 * Therefore, the empty family gets deleted as well.
	 */
	@Test
	def void testDeletePerson_LastInFamily(){
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		testCreateMale_Father_InsertInNewFamily()
		logger.trace(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searched = persons.findFirst[x|x.fullName.equals(FIRST_FATHER_1 + " " + FAMILY_NAME_1)]
			persons.remove(searched)
		]		
		logger.trace(name + " - propagation done")
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => []
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => []
		checkCorrectRegisters(famEq, perEq)
		logger.trace(name + " - finished without errors")		
	} 
	/**Test the deletion of the {@link PersonRegister}. The corresponding {@link FamilyRegister}
	 * will be then deleted as well and all contained elements in both of the registers.
	 */
	@Test
	def void testDeletePersonsRegister() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")	
		this.createFamilyForTesting();
		logger.trace(name + " - preparation done")
		resourceAt(PERSONS_MODEL).propagate[contents.clear()]		
		logger.trace(name + " - propagation done")
		assertEquals(0, resourceAt(FAMILIES_MODEL).contents.size())
		assertEquals(0, resourceAt(PERSONS_MODEL).contents.size())
		assertThat(resourceAt(FAMILIES_MODEL), not(exists))
		assertThat(resourceAt(PERSONS_MODEL), not(exists))
		logger.trace(name + " - finished without errors")
	}
}
