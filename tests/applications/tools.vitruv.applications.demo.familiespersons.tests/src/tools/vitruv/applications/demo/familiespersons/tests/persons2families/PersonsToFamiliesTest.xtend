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
	
	static Male DAD_1 = PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_FATHER_1 + " " + FAMILY_NAME_1]
	static Female MOM_1 = PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOTHER_1 + " " + FAMILY_NAME_1]
	static Male SON_1 = PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON_1 + " " + FAMILY_NAME_1]
	static Female DAU_1 = PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAUGHTER_1 + " " + FAMILY_NAME_1]

//	static val MALE_PERSON_NAME = "Max Mustermann"
//	static val SECOND_MALE_PERSON_NAME = "Bernd Mustermann"
//	static val FEMALE_PERSON_NAME = "Erika Mustermann"
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
		val pm = resourceAt(PERSONS_MODEL)
		val fm = resourceAt(FAMILIES_MODEL)
		assertThat(pm, exists)
		assertThat(fm, exists)
		assertEquals(1, pm.contents.size);
		assertEquals(1, fm.contents.size);
		assertEquals(2, pm.allContents.size);
		assertEquals(3, fm.allContents.size);
		assertThat(pm.contents.get(0), instanceOf(PersonRegister));
		assertThat(fm.contents.get(0), instanceOf(FamilyRegister));
		val FamilyRegister eq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_1]
			]
		]
		val famReg = fm.contents.get(0)
		assertThat(famReg, instanceOf(FamilyRegister));
		val FamilyRegister castedFamReg = famReg as FamilyRegister
		assertThat(castedFamReg, equalsDeeply(eq));
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
		val pm = resourceAt(PERSONS_MODEL)
		val fm = resourceAt(FAMILIES_MODEL)
		assertThat(pm, exists)
		assertThat(fm, exists)
		assertEquals(1, pm.contents.size);
		assertEquals(1, fm.contents.size);
		assertEquals(3, pm.allContents.size);
		assertEquals(4, fm.allContents.size);
		assertThat(pm.contents.get(0), instanceOf(PersonRegister));
		assertThat(fm.contents.get(0), instanceOf(FamilyRegister));
		val FamilyRegister eq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_1]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_1]
			]
		]
		val famReg = fm.contents.get(0)
		assertThat(famReg, instanceOf(FamilyRegister));
		val FamilyRegister castedFamReg = famReg as FamilyRegister
		assertThat(castedFamReg, equalsDeeply(eq));
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
		val pm = resourceAt(PERSONS_MODEL)
		val fm = resourceAt(FAMILIES_MODEL)
		assertThat(pm, exists)
		assertThat(fm, exists)
		assertEquals(1, pm.contents.size);
		assertEquals(1, fm.contents.size);
		assertEquals(2, pm.allContents.size);
		assertEquals(3, fm.allContents.size);
		assertThat(pm.contents.get(0), instanceOf(PersonRegister));
		assertThat(fm.contents.get(0), instanceOf(FamilyRegister));
		val FamilyRegister eq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_1]
			]
		]
		val famReg = fm.contents.get(0)
		assertThat(famReg, instanceOf(FamilyRegister));
		val FamilyRegister castedFamReg = famReg as FamilyRegister
		assertThat(castedFamReg, equalsDeeply(eq));
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
		val pm = resourceAt(PERSONS_MODEL)
		val fm = resourceAt(FAMILIES_MODEL)
		assertThat(pm, exists)
		assertThat(fm, exists)
		assertEquals(1, pm.contents.size);
		assertEquals(1, fm.contents.size);
		assertEquals(2, pm.allContents.size);
		assertEquals(3, fm.allContents.size);
		assertThat(pm.contents.get(0), instanceOf(PersonRegister));
		assertThat(fm.contents.get(0), instanceOf(FamilyRegister));		
		val FamilyRegister eq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_2]
			]
		]
		val famReg = fm.contents.get(0)
		assertThat(famReg, instanceOf(FamilyRegister));
		val FamilyRegister castedFamReg = famReg as FamilyRegister
		assertThat(castedFamReg, equalsDeeply(eq));
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
		val pm = resourceAt(PERSONS_MODEL)
		val fm = resourceAt(FAMILIES_MODEL)
		assertThat(pm, exists)
		assertThat(fm, exists)
		assertEquals(1, pm.contents.size);
		assertEquals(1, fm.contents.size);
		assertEquals(3, pm.allContents.size);
		assertEquals(5, fm.allContents.size);
		assertThat(pm.contents.get(0), instanceOf(PersonRegister));
		assertThat(fm.contents.get(0), instanceOf(FamilyRegister));
		val FamilyRegister eq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_1]
			]
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_2]
			]
		]
		val famReg = fm.contents.get(0)
		assertThat(famReg, instanceOf(FamilyRegister));
		val FamilyRegister castedFamReg = famReg as FamilyRegister
		assertThat(castedFamReg, equalsDeeply(eq));
		logger.info(name + " - finished without errors")
	}
	
	@Test
	def void testCreateMale_Father_InsertInDifferentlyNamedFamily_NoFather_DiscardChanges() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		logger.info(name + " - preparation done")
		dprint()
		logger.info(name + " - propagation done")
		dprint()
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateMale_Father_InsertInDifferentlyNamedFamily_NoFather_RenamePerson() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		logger.info(name + " - preparation done")
		dprint()
		logger.info(name + " - propagation done")
		dprint()
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateMale_Father_InsertInDifferentlyNamedFamily_NoFather_RenameFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		logger.info(name + " - preparation done")
		dprint()
		logger.info(name + " - propagation done")
		dprint()
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateMale_Father_InsertInDifferentlyNamedFamily_NoFather_InsertInNewFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		logger.info(name + " - preparation done")
		dprint()
		logger.info(name + " - propagation done")
		dprint()
		logger.info(name + " - finished without errors")
	}
	
	@Test
	def void testCreateMale_Father_InsertInDifferentlyNamedFamily_HasFather_DiscardChanges() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		logger.info(name + " - preparation done")
		dprint()
		logger.info(name + " - propagation done")
		dprint()
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateMale_Father_InsertInDifferentlyNamedFamily_HasFather_ReplaceFather_DiscardChanges() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		logger.info(name + " - preparation done")
		dprint()
		logger.info(name + " - propagation done")
		dprint()
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateMale_Father_InsertInDifferentlyNamedFamily_HasFather_ReplaceFather_RenamePerson() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		logger.info(name + " - preparation done")
		dprint()
		logger.info(name + " - propagation done")
		dprint()
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateMale_Father_InsertInDifferentlyNamedFamily_HasFather_ReplaceFather_RenameFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		logger.info(name + " - preparation done")
		dprint()
		logger.info(name + " - propagation done")
		dprint()
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateMale_Father_InsertInDifferentlyNamedFamily_HasFather_ReplaceFather_InsertInNewFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		logger.info(name + " - preparation done")
		dprint()
		logger.info(name + " - propagation done")
		dprint()
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateMale_Father_InsertInDifferentlyNamedFamily_HasFather_InsertInNewFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		logger.info(name + " - preparation done")
		dprint()
		logger.info(name + " - propagation done")
		dprint()
		logger.info(name + " - finished without errors")
	}
	
	//========== SON ==========
	@Test
	def void testCreateMale_Son_InsertInNewFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		logger.info(name + " - preparation done")
		dprint()
		logger.info(name + " - propagation done")
		dprint()
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateMale_Son_InsertInMatchingFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		logger.info(name + " - preparation done")
		dprint()
		logger.info(name + " - propagation done")
		dprint()
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateMale_Son_InsertInDifferentlyNamedFamily_DiscardChanges() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		logger.info(name + " - preparation done")
		dprint()
		logger.info(name + " - propagation done")
		dprint()
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateMale_Son_InsertInDifferentlyNamedFamily_RenamePerson() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		logger.info(name + " - preparation done")
		dprint()
		logger.info(name + " - propagation done")
		dprint()
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateMale_Son_InsertInDifferentlyNamedFamily_RenameFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		logger.info(name + " - preparation done")
		dprint()
		logger.info(name + " - propagation done")
		dprint()
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateMale_Son_InsertInDifferentlyNamedFamily_InsertInNewFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		logger.info(name + " - preparation done")
		dprint()
		logger.info(name + " - propagation done")
		dprint()
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
		val pm = resourceAt(PERSONS_MODEL)
		val fm = resourceAt(FAMILIES_MODEL)
		assertThat(pm, exists)
		assertThat(fm, exists)		
		assertEquals(1, pm.contents.size);
		assertEquals(1, fm.contents.size);
		assertEquals(2, pm.allContents.size);
		assertEquals(3, fm.allContents.size);
		assertThat(pm.contents.get(0), instanceOf(PersonRegister));
		assertThat(fm.contents.get(0), instanceOf(FamilyRegister));
		val FamilyRegister eq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_1]
			]
		]
		val famReg = fm.contents.get(0)
		assertThat(famReg, instanceOf(FamilyRegister));
		val FamilyRegister castedFamReg = famReg as FamilyRegister
		assertThat(castedFamReg, equalsDeeply(eq));
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
		val pm = resourceAt(PERSONS_MODEL)
		val fm = resourceAt(FAMILIES_MODEL)
		assertThat(pm, exists)
		assertThat(fm, exists)
		assertEquals(1, pm.contents.size);
		assertEquals(1, fm.contents.size);
		assertEquals(3, pm.allContents.size);
		assertEquals(4, fm.allContents.size);
		assertThat(pm.contents.get(0), instanceOf(PersonRegister));
		assertThat(fm.contents.get(0), instanceOf(FamilyRegister));
		val FamilyRegister eq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_1]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_1]
			]
		]
		val famReg = fm.contents.get(0)
		assertThat(famReg, instanceOf(FamilyRegister));
		val FamilyRegister castedFamReg = famReg as FamilyRegister
		assertThat(castedFamReg, equalsDeeply(eq));
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
		val pm = resourceAt(PERSONS_MODEL)
		val fm = resourceAt(FAMILIES_MODEL)
		assertThat(pm, exists)
		assertThat(fm, exists)
		assertEquals(1, pm.contents.size);
		assertEquals(1, fm.contents.size);
		assertEquals(2, pm.allContents.size);
		assertEquals(3, fm.allContents.size);
		assertThat(pm.contents.get(0), instanceOf(PersonRegister));
		assertThat(fm.contents.get(0), instanceOf(FamilyRegister));
		val FamilyRegister eq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_1]
			]
		]
		val famReg = fm.contents.get(0)
		assertThat(famReg, instanceOf(FamilyRegister));
		val FamilyRegister castedFamReg = famReg as FamilyRegister
		assertThat(castedFamReg, equalsDeeply(eq));
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
		val pm = resourceAt(PERSONS_MODEL)
		val fm = resourceAt(FAMILIES_MODEL)
		assertThat(pm, exists)
		assertThat(fm, exists)
		assertEquals(1, pm.contents.size);
		assertEquals(1, fm.contents.size);
		assertEquals(2, pm.allContents.size);
		assertEquals(3, fm.allContents.size);
		assertThat(pm.contents.get(0), instanceOf(PersonRegister));
		assertThat(fm.contents.get(0), instanceOf(FamilyRegister));		
		val FamilyRegister eq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_2]
			]
		]
		val famReg = fm.contents.get(0)
		assertThat(famReg, instanceOf(FamilyRegister));
		val FamilyRegister castedFamReg = famReg as FamilyRegister
		assertThat(castedFamReg, equalsDeeply(eq));
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
		val pm = resourceAt(PERSONS_MODEL)
		val fm = resourceAt(FAMILIES_MODEL)
		assertThat(pm, exists)
		assertThat(fm, exists)
		assertEquals(1, pm.contents.size);
		assertEquals(1, fm.contents.size);
		assertEquals(3, pm.allContents.size);
		assertEquals(5, fm.allContents.size);
		assertThat(pm.contents.get(0), instanceOf(PersonRegister));
		assertThat(fm.contents.get(0), instanceOf(FamilyRegister));
		val FamilyRegister eq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_1]
			]
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_2]
			]
		]
		val famReg = fm.contents.get(0)
		assertThat(famReg, instanceOf(FamilyRegister));
		val FamilyRegister castedFamReg = famReg as FamilyRegister
		assertThat(castedFamReg, equalsDeeply(eq));
		logger.info(name + " - finished without errors")
	}
	
	@Test
	def void testCreateFemale_Mother_InsertInDifferentlyNamedFamily_NoMother_DiscardChanges() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		logger.info(name + " - preparation done")
		dprint()
		logger.info(name + " - propagation done")
		dprint()
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateFemale_Mother_InsertInDifferentlyNamedFamily_NoMother_RenamePerson() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		logger.info(name + " - preparation done")
		dprint()
		logger.info(name + " - propagation done")
		dprint()
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateFemale_Mother_InsertInDifferentlyNamedFamily_NoMother_RenameFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		logger.info(name + " - preparation done")
		dprint()
		logger.info(name + " - propagation done")
		dprint()
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateFemale_Mother_InsertInDifferentlyNamedFamily_NoMother_InsertInNewFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		logger.info(name + " - preparation done")
		dprint()
		logger.info(name + " - propagation done")
		dprint()
		logger.info(name + " - finished without errors")
	}
	
	@Test
	def void testCreateFemale_Mother_InsertInDifferentlyNamedFamily_HasMother_DiscardChanges() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		logger.info(name + " - preparation done")
		dprint()
		logger.info(name + " - propagation done")
		dprint()
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateFemale_Mother_InsertInDifferentlyNamedFamily_HasMother_ReplaceMother_DiscardChanges() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		logger.info(name + " - preparation done")
		dprint()
		logger.info(name + " - propagation done")
		dprint()
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateFemale_Mother_InsertInDifferentlyNamedFamily_HasMother_ReplaceMother_RenamePerson() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		logger.info(name + " - preparation done")
		dprint()
		logger.info(name + " - propagation done")
		dprint()
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateFemale_Mother_InsertInDifferentlyNamedFamily_HasMother_ReplaceMother_RenameFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		logger.info(name + " - preparation done")
		dprint()
		logger.info(name + " - propagation done")
		dprint()
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateFemale_Mother_InsertInDifferentlyNamedFamily_HasMother_ReplaceMother_InsertInNewFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		logger.info(name + " - preparation done")
		dprint()
		logger.info(name + " - propagation done")
		dprint()
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateFemale_Mother_InsertInDifferentlyNamedFamily_HasMother_InsertInNewFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		logger.info(name + " - preparation done")
		dprint()
		logger.info(name + " - propagation done")
		dprint()
		logger.info(name + " - finished without errors")
	}
	
	//========== DAUGHTER ==========
	@Test
	def void testCreateFemale_Daughter_InsertInNewFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		logger.info(name + " - preparation done")
		dprint()
		logger.info(name + " - propagation done")
		dprint()
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateFemale_Daughter_InsertInMatchingFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		logger.info(name + " - preparation done")
		dprint()
		logger.info(name + " - propagation done")
		dprint()
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateFemale_Daughter_InsertInDifferentlyNamedFamily_DiscardChanges() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		logger.info(name + " - preparation done")
		dprint()
		logger.info(name + " - propagation done")
		dprint()
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateFemale_Daughter_InsertInDifferentlyNamedFamily_RenamePerson() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		logger.info(name + " - preparation done")
		dprint()
		logger.info(name + " - propagation done")
		dprint()
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateFemale_Daughter_InsertInDifferentlyNamedFamily_RenameFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		logger.info(name + " - preparation done")
		dprint()
		logger.info(name + " - propagation done")
		dprint()
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testCreateFemale_Daughter_InsertInDifferentlyNamedFamily_InsertInNewFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		logger.info(name + " - preparation done")
		dprint()
		logger.info(name + " - propagation done")
		dprint()
		logger.info(name + " - finished without errors")
	}



	def dprint() {
		dprint(FamilyRegister.from(FAMILIES_MODEL), PersonRegister.from(PERSONS_MODEL))
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








//
//	@Test
//	def void testDeletePersonRegister() {
//	}
//
//	@Test
////	@Disabled("The personsToFamilies is broken")
//	def void testCreateFemale() {
//		PersonRegister.from(PERSONS_MODEL).propagate [
//			persons += PersonsFactory.eINSTANCE.createFemale => [
//				fullName = FEMALE_PERSON_NAME
//			]
//		]
//
//		assertThat(resourceAt(FAMILIES_MODEL), exists);
//	}
//
//	@Test
////	@Disabled("The personsToFamilies is broken")
//	def void testChangeFirstName() {
//		val person = PersonsFactory.eINSTANCE.createMale => [
//			fullName = MALE_PERSON_NAME
//		]
//		PersonRegister.from(PERSONS_MODEL).propagate[persons += person]
//		person.propagate[fullName = SECOND_MALE_PERSON_NAME]
//		
//		val personFirstName = person.fullName.split(" ").get(0)
//		val membersWithFirstName = FamilyRegister.from(FAMILIES_MODEL).families
//			.flatMap[daughters + sons + List.of(mother) + List.of(father)]
//			.filter[personFirstName == it?.firstName]
//		assertEquals(1, membersWithFirstName.length)
//	}
}
