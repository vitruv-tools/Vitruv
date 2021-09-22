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

class PersonsToFamiliesTest extends VitruvApplicationTest {
	static val logger = Logger.getLogger(PersonsToFamiliesTest);
	static String FAMILY_NAME_1 = "Meier"
	static String FIRST_FATHER_1 = "Anton"
	static String FIRST_MOTHER_1 = "Berta"
	static String FIRST_SON_1 = "Chris"
	static String FIRST_DAUGHTER_1 = "Daria"	
	
	static String FAMILY_NAME_2 = "Schulze"
	static String FIRST_FATHER_2 = "Adam"
	static String FIRST_MOTHER_2 = "Birgit"
	static String FIRST_SON_2 = "Charles"
	static String FIRST_DAUGHTER_2 = "Daniela"	
	
	static val FAMILIES_MODEL = DomainUtil.getModelFileName('model/families', new FamiliesDomainProvider)
	static val PERSONS_MODEL = DomainUtil.getModelFileName('model/model', new PersonsDomainProvider)

	override protected getChangePropagationSpecifications() {
		return #[new PersonsToFamiliesChangePropagationSpecification()]
	}

	def createRegister() {
		PersonsFactory.eINSTANCE.createPersonRegister
	}
	
	@BeforeEach
	def void insertRegister() {
//		val SimpleLayout layout = new SimpleLayout();
//		val ConsoleAppender consoleAppender = new ConsoleAppender(layout);
//		logger.addAppender(consoleAppender);
//		logger.setLevel(Level.INFO)
		logger.setLevel(Level.DEBUG)
		resourceAt(PERSONS_MODEL).propagate[contents += createRegister()]
	}
	
	@Test
	def void testInsertRegister() {
		// insertRegister(); -> Schon erledigt durch @Before Each
		assertThat(resourceAt(FAMILIES_MODEL), exists);
		assertEquals(1, resourceAt(FAMILIES_MODEL).contents.size);
		assertEquals(1, resourceAt(FAMILIES_MODEL).allContents.size);
		assertThat(resourceAt(FAMILIES_MODEL).contents.get(0), instanceOf(FamilyRegister));
		assertEquals(0, resourceAt(FAMILIES_MODEL).contents.get(0).eAllContents().size);
	}		
	
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
	@Test
	def void testCreateMale_Father_InsertInNewFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		//Father
		userInteraction.addNextSingleSelection(0)
		logger.info(name + " - preparation done")
		dprint()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_1 + " " + FAMILY_NAME_1
			]
		]
		logger.info(name + " - propagation done")
		dprint()
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
		logger.info(name + " - finished without errors")
	}
	
	@Test
	def void testCreateMale_Father_InsertInMatchingFamily_NoFather() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		testCreateFemale_Mother_InsertInNewFamily()
		//Father
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		logger.info(name + " - preparation done")
		dprint()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_1 + " " + FAMILY_NAME_1
			]
		]
		logger.info(name + " - propagation done")
		dprint()
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
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateMale_Father_InsertInMatchingFamily_HasFather_DiscardChanges() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		testCreateMale_Father_InsertInNewFamily()
		//Father
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//DiscardChanges
		userInteraction.addNextSingleSelection(0)
		logger.info(name + " - preparation done")
		dprint()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_2 + " " + FAMILY_NAME_1
			]
		]
		logger.info(name + " - propagation done")
		dprint()
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
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateMale_Father_InsertInMatchingFamily_HasFather_ReplaceFather() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		testCreateMale_Father_InsertInNewFamily()
		//Father
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//ReplaceFather
		userInteraction.addNextSingleSelection(1)
		logger.info(name + " - preparation done")
		dprint()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_2 + " " + FAMILY_NAME_1
			]
		]
		logger.info(name + " - propagation done")
		dprint()
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
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateMale_Father_InsertInMatchingFamily_HasFather_InsertInNewFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		testCreateMale_Father_InsertInNewFamily()
		//Father
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//InsertInNewFamily
		userInteraction.addNextSingleSelection(2)
		logger.info(name + " - preparation done")
		dprint()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_2 + " " + FAMILY_NAME_1
			]
		]
		logger.info(name + " - propagation done")
		dprint()
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
		logger.info(name + " - finished without errors")
	}
	
	@Test
	def void testCreateMale_Father_InsertInDifferentlyNamedFamily_NoFather_DiscardChanges() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		testCreateFemale_Mother_InsertInNewFamily()
		//Father
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//DiscardChanges
		userInteraction.addNextSingleSelection(0)
		logger.info(name + " - preparation done")
		dprint()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_1 + " " + FAMILY_NAME_2
			]
		]
		logger.info(name + " - propagation done")
		dprint()
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
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateMale_Father_InsertInDifferentlyNamedFamily_NoFather_RenamePerson() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		testCreateFemale_Mother_InsertInNewFamily()
		//Father
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//RenamePerson
		userInteraction.addNextSingleSelection(1)
		logger.info(name + " - preparation done")
		dprint()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_1 + " " + FAMILY_NAME_2
			]
		]
		logger.info(name + " - propagation done")
		dprint()
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
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateMale_Father_InsertInDifferentlyNamedFamily_NoFather_RenameFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		testCreateFemale_Mother_InsertInNewFamily()
		//Father
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//RenameFamily
		userInteraction.addNextSingleSelection(2)
		logger.info(name + " - preparation done")
		dprint()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_1 + " " + FAMILY_NAME_2
			]
		]
		logger.info(name + " - propagation done")
		dprint()	
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
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateMale_Father_InsertInDifferentlyNamedFamily_NoFather_InsertInNewFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		testCreateFemale_Mother_InsertInNewFamily()
		//Father
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//InsertInNewFamily
		userInteraction.addNextSingleSelection(3)
		logger.info(name + " - preparation done")
		dprint()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_1 + " " + FAMILY_NAME_2
			]
		]
		logger.info(name + " - propagation done")
		dprint()
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
		logger.info(name + " - finished without errors")
	}
	
	@Test
	def void testCreateMale_Father_InsertInDifferentlyNamedFamily_HasFather_DiscardChanges() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		testCreateMale_Father_InsertInNewFamily()
		//Father
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//DiscardChanges
		userInteraction.addNextSingleSelection(0)
		logger.info(name + " - preparation done")
		dprint()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_2 + " " + FAMILY_NAME_2
			]
		]
		logger.info(name + " - propagation done")
		dprint()
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
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateMale_Father_InsertInDifferentlyNamedFamily_HasFather_ReplaceFather_DiscardChanges() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		testCreateMale_Father_InsertInNewFamily()
		//Father
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//ReplaceFather
		userInteraction.addNextSingleSelection(1)
		//DiscardChanges
		userInteraction.addNextSingleSelection(0)
		logger.info(name + " - preparation done")
		dprint()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_2 + " " + FAMILY_NAME_2
			]
		]
		logger.info(name + " - propagation done")
		dprint()
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
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateMale_Father_InsertInDifferentlyNamedFamily_HasFather_ReplaceFather_RenamePerson() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		testCreateMale_Father_InsertInNewFamily()
		//Father
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//ReplaceFather
		userInteraction.addNextSingleSelection(1)
		//RenamePerson
		userInteraction.addNextSingleSelection(1)
		logger.info(name + " - preparation done")
		dprint()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_2 + " " + FAMILY_NAME_2
			]
		]
		logger.info(name + " - propagation done")
		dprint()
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
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateMale_Father_InsertInDifferentlyNamedFamily_HasFather_ReplaceFather_RenameFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		testCreateMale_Father_InsertInNewFamily()
		//Father
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//ReplaceFather
		userInteraction.addNextSingleSelection(1)
		//RenameFamily
		userInteraction.addNextSingleSelection(2)
		logger.info(name + " - preparation done")
		dprint()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_2 + " " + FAMILY_NAME_2
			]
		]
		logger.info(name + " - propagation done")
		dprint()
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
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateMale_Father_InsertInDifferentlyNamedFamily_HasFather_ReplaceFather_InsertInNewFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		testCreateMale_Father_InsertInNewFamily()
		//Father
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//ReplaceFather
		userInteraction.addNextSingleSelection(1)
		//InsertInNewFamily
		userInteraction.addNextSingleSelection(3)
		logger.info(name + " - preparation done")
		dprint()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_2 + " " + FAMILY_NAME_2
			]
		]
		logger.info(name + " - propagation done")
		dprint()
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
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateMale_Father_InsertInDifferentlyNamedFamily_HasFather_InsertInNewFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		testCreateMale_Father_InsertInNewFamily()
		//Father
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//InsertInNewFamily
		userInteraction.addNextSingleSelection(2)
		logger.info(name + " - preparation done")
		dprint()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_FATHER_2 + " " + FAMILY_NAME_2
			]
		]
		logger.info(name + " - propagation done")
		dprint()
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
		logger.info(name + " - finished without errors")
	}
	
	//========== SON ==========
	@Test
	def void testCreateMale_Son_InsertInNewFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		//Son
		userInteraction.addNextSingleSelection(1)
		logger.info(name + " - preparation done")
		dprint()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_1 + " " + FAMILY_NAME_1
			]
		]
		logger.info(name + " - propagation done")
		dprint()
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
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateMale_Son_InsertInMatchingFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		testCreateMale_Son_InsertInNewFamily()
		//Son
		userInteraction.addNextSingleSelection(1)
		//SelectFamily
		userInteraction.addNextSingleSelection(1)
		logger.info(name + " - preparation done")
		dprint()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_2 + " " + FAMILY_NAME_1
			]
		]
		logger.info(name + " - propagation done")
		dprint()
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
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateMale_Son_InsertInDifferentlyNamedFamily_DiscardChanges() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		testCreateMale_Son_InsertInNewFamily()
		//Son
		userInteraction.addNextSingleSelection(1)
		//SelectFamily
		userInteraction.addNextSingleSelection(1)
		//DiscardChanges
		userInteraction.addNextSingleSelection(0)
		logger.info(name + " - preparation done")
		dprint()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_2 + " " + FAMILY_NAME_2
			]
		]
		logger.info(name + " - propagation done")
		dprint()
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
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateMale_Son_InsertInDifferentlyNamedFamily_RenamePerson() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		testCreateMale_Son_InsertInNewFamily()
		//Son
		userInteraction.addNextSingleSelection(1)
		//SelectFamily
		userInteraction.addNextSingleSelection(1)
		//RenamePerson
		userInteraction.addNextSingleSelection(1)
		logger.info(name + " - preparation done")
		dprint()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_2 + " " + FAMILY_NAME_2
			]
		]
		logger.info(name + " - propagation done")
		dprint()
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
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateMale_Son_InsertInDifferentlyNamedFamily_RenameFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		testCreateMale_Son_InsertInNewFamily()
		//Son
		userInteraction.addNextSingleSelection(1)
		//SelectFamily
		userInteraction.addNextSingleSelection(1)
		//RenameFamily
		userInteraction.addNextSingleSelection(2)
		logger.info(name + " - preparation done")
		dprint()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_2 + " " + FAMILY_NAME_2
			]
		]
		logger.info(name + " - propagation done")
		dprint()		
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
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateMale_Son_InsertInDifferentlyNamedFamily_InsertInNewFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		testCreateMale_Son_InsertInNewFamily()
		//Son
		userInteraction.addNextSingleSelection(1)
		//SelectFamily
		userInteraction.addNextSingleSelection(1)
		//InsertInNewFamily
		userInteraction.addNextSingleSelection(3)
		logger.info(name + " - preparation done")
		dprint()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_SON_2 + " " + FAMILY_NAME_2
			]
		]
		logger.info(name + " - propagation done")
		dprint()		
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
		logger.info(name + " - finished without errors")
	}


	//=====================================
	//             CREATE FEMALE
	//=====================================
	
	//========== MOTHER ==========
	@Test
	def void testCreateFemale_Mother_InsertInNewFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		userInteraction.addNextSingleSelection(0)
		logger.info(name + " - preparation done")
		dprint()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_1 + " " + FAMILY_NAME_1
			]
		]		
		logger.info(name + " - propagation done")
		dprint()		
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
		logger.info(name + " - finished without errors")
	}
	
	@Test
	def void testCreateFemale_Mother_InsertInMatchingFamily_NoMother() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		testCreateMale_Father_InsertInNewFamily()
		userInteraction.addNextSingleSelection(0)
		userInteraction.addNextSingleSelection(1)
		logger.info(name + " - preparation done")
		dprint()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_1 + " " + FAMILY_NAME_1
			]
		]
		logger.info(name + " - propagation done")
		dprint()
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
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateFemale_Mother_InsertInMatchingFamily_HasMother_DiscardChanges() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		testCreateFemale_Mother_InsertInNewFamily()
		//Mother
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//DiscardChanges
		userInteraction.addNextSingleSelection(0)
		logger.info(name + " - preparation done")
		dprint()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_2 + " " + FAMILY_NAME_1
			]
		]
		logger.info(name + " - propagation done")
		dprint()
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
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateFemale_Mother_InsertInMatchingFamily_HasMother_ReplaceMother() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		testCreateFemale_Mother_InsertInNewFamily()
		//Mother
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//ReplaceMother
		userInteraction.addNextSingleSelection(1)
		logger.info(name + " - preparation done")
		dprint()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_2 + " " + FAMILY_NAME_1
			]
		]
		logger.info(name + " - propagation done")
		dprint()
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
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateFemale_Mother_InsertInMatchingFamily_HasMother_InsertInNewFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		testCreateFemale_Mother_InsertInNewFamily()
		//Mother
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//InsertInNewFamily
		userInteraction.addNextSingleSelection(2)
		logger.info(name + " - preparation done")
		dprint()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_2 + " " + FAMILY_NAME_1
			]
		]
		logger.info(name + " - propagation done")
		dprint()
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
		logger.info(name + " - finished without errors")
	}
	
	@Test
	def void testCreateFemale_Mother_InsertInDifferentlyNamedFamily_NoMother_DiscardChanges() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		testCreateMale_Father_InsertInNewFamily()
		//Mother
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//DiscardChanges
		userInteraction.addNextSingleSelection(0)
		logger.info(name + " - preparation done")
		dprint()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_1 + " " + FAMILY_NAME_2
			]
		]
		logger.info(name + " - propagation done")
		dprint()
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
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateFemale_Mother_InsertInDifferentlyNamedFamily_NoMother_RenamePerson() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		testCreateMale_Father_InsertInNewFamily()
		//Mother
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//RenamePerson
		userInteraction.addNextSingleSelection(1)
		logger.info(name + " - preparation done")
		dprint()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_1 + " " + FAMILY_NAME_2
			]
		]
		logger.info(name + " - propagation done")
		dprint()
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
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateFemale_Mother_InsertInDifferentlyNamedFamily_NoMother_RenameFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		testCreateMale_Father_InsertInNewFamily()
		//Mother
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//RenameFamily
		userInteraction.addNextSingleSelection(2)
		logger.info(name + " - preparation done")
		dprint()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_1 + " " + FAMILY_NAME_2
			]
		]
		logger.info(name + " - propagation done")
		dprint()
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
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateFemale_Mother_InsertInDifferentlyNamedFamily_NoMother_InsertInNewFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		testCreateMale_Father_InsertInNewFamily()
		//Mother
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//InsertInNewFamily
		userInteraction.addNextSingleSelection(3)
		logger.info(name + " - preparation done")
		dprint()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_1 + " " + FAMILY_NAME_2
			]
		]
		logger.info(name + " - propagation done")
		dprint()
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
		logger.info(name + " - finished without errors")
	}
	
	@Test
	def void testCreateFemale_Mother_InsertInDifferentlyNamedFamily_HasMother_DiscardChanges() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		testCreateFemale_Mother_InsertInNewFamily()
		//Mother
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//DiscardChanges
		userInteraction.addNextSingleSelection(0)
		logger.info(name + " - preparation done")
		dprint()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_2 + " " + FAMILY_NAME_2
			]
		]
		logger.info(name + " - propagation done")
		dprint()
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
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateFemale_Mother_InsertInDifferentlyNamedFamily_HasMother_ReplaceMother_DiscardChanges() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		testCreateFemale_Mother_InsertInNewFamily()
		//Mother
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//ReplaceMother
		userInteraction.addNextSingleSelection(1)
		//DiscardChanges
		userInteraction.addNextSingleSelection(0)
		logger.info(name + " - preparation done")
		dprint()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_2 + " " + FAMILY_NAME_2
			]
		]
		logger.info(name + " - propagation done")
		dprint()
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
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateFemale_Mother_InsertInDifferentlyNamedFamily_HasMother_ReplaceMother_RenamePerson() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		testCreateFemale_Mother_InsertInNewFamily()
		//Mother
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//ReplaceMother
		userInteraction.addNextSingleSelection(1)
		//RenamePerson
		userInteraction.addNextSingleSelection(1)
		logger.info(name + " - preparation done")
		dprint()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_2 + " " + FAMILY_NAME_2
			]
		]
		logger.info(name + " - propagation done")
		dprint()
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
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateFemale_Mother_InsertInDifferentlyNamedFamily_HasMother_ReplaceMother_RenameFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		testCreateFemale_Mother_InsertInNewFamily()
		//Mother
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//ReplaceMother
		userInteraction.addNextSingleSelection(1)
		//RenameFamily
		userInteraction.addNextSingleSelection(2)
		logger.info(name + " - preparation done")
		dprint()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_2 + " " + FAMILY_NAME_2
			]
		]
		logger.info(name + " - propagation done")
		dprint()
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
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateFemale_Mother_InsertInDifferentlyNamedFamily_HasMother_ReplaceMother_InsertInNewFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		testCreateFemale_Mother_InsertInNewFamily()
		//Mother
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//ReplaceMother
		userInteraction.addNextSingleSelection(1)
		//InserInNewFamily
		userInteraction.addNextSingleSelection(3)
		logger.info(name + " - preparation done")
		dprint()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_2 + " " + FAMILY_NAME_2				
			]
		]
		logger.info(name + " - propagation done")
		dprint()
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
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateFemale_Mother_InsertInDifferentlyNamedFamily_HasMother_InsertInNewFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		testCreateFemale_Mother_InsertInNewFamily()
		//Mother
		userInteraction.addNextSingleSelection(0)
		//Select Family
		userInteraction.addNextSingleSelection(1)
		//InserInNewFamily
		userInteraction.addNextSingleSelection(2)
		logger.info(name + " - preparation done")
		dprint()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_MOTHER_2 + " " + FAMILY_NAME_2
			]
		]
		logger.info(name + " - propagation done")
		dprint()
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
		logger.info(name + " - finished without errors")
	}
	
	//========== DAUGHTER ==========
	@Test
	def void testCreateFemale_Daughter_InsertInNewFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		//Daughter
		userInteraction.addNextSingleSelection(1)
		logger.info(name + " - preparation done")
		dprint()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAUGHTER_1 + " " + FAMILY_NAME_1
			]
		]
		logger.info(name + " - propagation done")
		dprint()
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
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateFemale_Daughter_InsertInMatchingFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		testCreateFemale_Daughter_InsertInNewFamily()
		//Daughter
		userInteraction.addNextSingleSelection(1)
		//SelectFamily
		userInteraction.addNextSingleSelection(1)
		logger.info(name + " - preparation done")
		dprint()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAUGHTER_2 + " " + FAMILY_NAME_1
			]
		]
		logger.info(name + " - propagation done")
		dprint()
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
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateFemale_Daughter_InsertInDifferentlyNamedFamily_DiscardChanges() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		testCreateFemale_Daughter_InsertInNewFamily()
		//Daughter
		userInteraction.addNextSingleSelection(1)
		//SelectFamily
		userInteraction.addNextSingleSelection(1)
		//DiscardChanges
		userInteraction.addNextSingleSelection(0)
		logger.info(name + " - preparation done")
		dprint()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAUGHTER_2 + " " + FAMILY_NAME_2
			]
		]
		logger.info(name + " - propagation done")
		dprint()
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
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateFemale_Daughter_InsertInDifferentlyNamedFamily_RenamePerson() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		testCreateFemale_Daughter_InsertInNewFamily()
		//Daughter
		userInteraction.addNextSingleSelection(1)
		//SelectFamily
		userInteraction.addNextSingleSelection(1)
		//RenamePerson
		userInteraction.addNextSingleSelection(1)
		logger.info(name + " - preparation done")
		dprint()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAUGHTER_2 + " " + FAMILY_NAME_2
			]
		]
		logger.info(name + " - propagation done")
		dprint()
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
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateFemale_Daughter_InsertInDifferentlyNamedFamily_RenameFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		testCreateFemale_Daughter_InsertInNewFamily()
		//Daughter
		userInteraction.addNextSingleSelection(1)
		//SelectFamily
		userInteraction.addNextSingleSelection(1)
		//RenameFamily
		userInteraction.addNextSingleSelection(2)
		logger.info(name + " - preparation done")
		dprint()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAUGHTER_2 + " " + FAMILY_NAME_2
			]
		]
		logger.info(name + " - propagation done")
		dprint()
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
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateFemale_Daughter_InsertInDifferentlyNamedFamily_InsertInNewFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		testCreateFemale_Daughter_InsertInNewFamily()
		//Daughter
		userInteraction.addNextSingleSelection(1)
		//SelectFamily
		userInteraction.addNextSingleSelection(1)
		//InsertInNewFamily
		userInteraction.addNextSingleSelection(3)
		logger.info(name + " - preparation done")
		dprint()
		PersonRegister.from(PERSONS_MODEL).propagate [
			persons += PersonsFactory.eINSTANCE.createFemale => [
				fullName = FIRST_DAUGHTER_2 + " " + FAMILY_NAME_2
			]
		]
		logger.info(name + " - propagation done")
		dprint()
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
		logger.info(name + " - finished without errors")
	}


	//========== CREATE FAMILY ==========
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
		dprint()
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
		dprint()
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
	@Test
	def void testRenamingOfFirstname(){
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		createFamilyForTesting()
		logger.info(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searched = persons.findFirst[x|x.fullName.equals(FIRST_FATHER_1 + " " + FAMILY_NAME_1)]
			searched.fullName = FIRST_FATHER_2 + " " + searched.fullName.split(" ").get(1)
		]		
		logger.info(name + " - propagation done")
		dprint()
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
		logger.info(name + " - finished without errors")		
	}
	@Test
	def void testRenamingOfLastname(){
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		createFamilyForTesting()
		logger.info(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searched = persons.findFirst[x|x.fullName.equals(FIRST_FATHER_1 + " " + FAMILY_NAME_1)]
			searched.fullName = FIRST_FATHER_1 + " " + FAMILY_NAME_2
		]		
		logger.info(name + " - propagation done")
		dprint()
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
		logger.info(name + " - finished without errors")		
	}

	//========== DELETION ==========
	@Test
	def void testDeletePerson_NotLastInFamily(){
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		createFamilyForTesting()
		logger.info(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searched = persons.findFirst[x|x.fullName.equals(FIRST_FATHER_1 + " " + FAMILY_NAME_1)]
			persons.remove(searched)
		]		
		logger.info(name + " - propagation done")
		dprint()
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
		logger.info(name + " - finished without errors")		
	} 
	@Test
	def void testDeletePerson_LastInFamily(){
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		testCreateMale_Father_InsertInNewFamily()
		logger.info(name + " - preparation done")
		PersonRegister.from(PERSONS_MODEL).propagate [
			val searched = persons.findFirst[x|x.fullName.equals(FIRST_FATHER_1 + " " + FAMILY_NAME_1)]
			persons.remove(searched)
		]		
		logger.info(name + " - propagation done")
		dprint()
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => []
		val PersonRegister perEq = PersonsFactory.eINSTANCE.createPersonRegister => []
		checkCorrectRegisters(famEq, perEq)
		logger.info(name + " - finished without errors")		
	} 
	@Test
	def void testDeletePersonsRegister() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")	
		this.createFamilyForTesting();
		logger.info(name + " - preparation done")
		dprint()
		resourceAt(PERSONS_MODEL).propagate[contents.clear()]		
		logger.info(name + " - propagation done")
		dprint()
		assertEquals(0, resourceAt(FAMILIES_MODEL).contents.size())
		assertEquals(0, resourceAt(PERSONS_MODEL).contents.size())
		assertThat(resourceAt(FAMILIES_MODEL), not(exists))
		assertThat(resourceAt(PERSONS_MODEL), not(exists))
		logger.info(name + " - finished without errors")
	}

	def dprint() {
		val fsize = resourceAt(FAMILIES_MODEL).contents.size()
		val psize = resourceAt(PERSONS_MODEL).contents.size()
		if(fsize == 0){
			logger.debug('\nResource at ' + FAMILIES_MODEL + ' is empty.')
		}
		if(psize == 0){
			logger.debug('\nResource at ' + PERSONS_MODEL + ' is empty.')
		}
		if(fsize > 0 && psize > 0){		
			dprint(FamilyRegister.from(FAMILIES_MODEL), PersonRegister.from(PERSONS_MODEL))
		}
	}	
	def dprint(FamilyRegister fr, PersonRegister pr) {
		var String fs = ''
		var String ps = ''
		if (fr != null)
			fs = '''FamilyRegister «IF fr.id != null»«fr.id»«ELSE»<no id>«ENDIF»
«FOR f : fr.families SEPARATOR '\n'»	Family «f.lastName»
«IF f.father != null»		Father «f.father.firstName»«ENDIF»
«IF f.mother != null»		Mother «f.mother.firstName»«ENDIF»
«FOR s : f.sons BEFORE '		Sons (' SEPARATOR ', ' AFTER ')'»«s.firstName»«ENDFOR»
«FOR d : f.daughters BEFORE '		Daughters (' SEPARATOR ', ' AFTER ')'»«d.firstName»«ENDFOR»«ENDFOR»''';
		if (pr != null)
			ps = '''PersonRegister «IF pr.id != null»«pr.id»«ELSE»<no id>«ENDIF»
«FOR p : pr.persons»
«IF p instanceof Male»	Male: «ELSE»	Female: «ENDIF»«p.fullName» («IF p.birthday != null»«p.birthday»«ELSE»-«ENDIF»)
«ENDFOR»''';
		logger.debug('\n' + fs + '\n' + ps)
	}
}
