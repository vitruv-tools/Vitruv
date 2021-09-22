package tools.vitruv.applications.demo.familiespersons.tests.families2Persons

import edu.kit.ipd.sdq.metamodels.families.*;
import edu.kit.ipd.sdq.metamodels.families.impl.*;
import edu.kit.ipd.sdq.metamodels.persons.*;
import edu.kit.ipd.sdq.metamodels.persons.impl.*;
import java.nio.file.Path
import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.BasicEList
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tools.vitruv.applications.demo.familiespersons.families2persons.FamiliesToPersonsChangePropagationSpecification
import tools.vitruv.domains.demo.families.FamiliesDomainProvider
import tools.vitruv.domains.demo.persons.PersonsDomainProvider
import tools.vitruv.testutils.VitruvApplicationTest
import tools.vitruv.testutils.domains.DomainUtil
import tools.vitruv.testutils.matchers.ModelMatchers

import static org.hamcrest.CoreMatchers.*
import static org.hamcrest.MatcherAssert.assertThat
import static org.junit.jupiter.api.Assertions.assertEquals
import static tools.vitruv.testutils.matchers.ModelMatchers.*

class FamiliesPersonsTest extends VitruvApplicationTest {
	static val logger = Logger.getLogger(FamiliesPersonsTest);
// Es gibt auch noch einen ModelPrinting

	static String FAMILY_NAME_1 = "Meier1"
	static String FIRST_FATHER_1 = "Anton1"
	static String FIRST_MOTHER_1 = "Berta1"
	static String FIRST_SON_1 = "Chris1"
	static String FIRST_DAUGHTER_1 = "Daria1"	
	
	static String FAMILY_NAME_2 = "Schulze2"
	static String FIRST_FATHER_2 = "Adam2"
	static String FIRST_MOTHER_2 = "Birgit2"
	static String FIRST_SON_2 = "Charles2"
	static String FIRST_DAUGHTER_2 = "Daniela2"	

	static Path PERSONS_MODEL = DomainUtil.getModelFileName('model/persons', new PersonsDomainProvider)
	static Path FAMILIES_MODEL = DomainUtil.getModelFileName('model/families', new FamiliesDomainProvider)

	static Male DAD11 = PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_FATHER_1 + " " + FAMILY_NAME_1]
	static Female MOM11 = PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOTHER_1 + " " + FAMILY_NAME_1]
	static Male SON11 = PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON_1 + " " + FAMILY_NAME_1]
	static Female DAU11 = PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAUGHTER_1 + " " + FAMILY_NAME_1]
	
	static Male DAD12 = PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_FATHER_1 + " " + FAMILY_NAME_2]
	static Female MOM12 = PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOTHER_1 + " " + FAMILY_NAME_2]
	static Male SON12 = PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON_1 + " " + FAMILY_NAME_2]
	static Female DAU12 = PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAUGHTER_1 + " " + FAMILY_NAME_2]
	
	static Male DAD21 = PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_FATHER_2 + " " + FAMILY_NAME_1]
	static Female MOM21 = PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOTHER_2 + " " + FAMILY_NAME_1]
	static Male SON21 = PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON_2 + " " + FAMILY_NAME_1]
	static Female DAU21 = PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAUGHTER_2 + " " + FAMILY_NAME_1]

	override protected getChangePropagationSpecifications() {
		return #[new FamiliesToPersonsChangePropagationSpecification()]
	}

	def createRegister() {
		FamiliesFactory.eINSTANCE.createFamilyRegister
	}

	@BeforeEach
	def void insertRegister() {
//		val SimpleLayout layout = new SimpleLayout();
//		val ConsoleAppender consoleAppender = new ConsoleAppender(layout);
//		logger.addAppender(consoleAppender);
//		logger.setLevel(Level.INFO)
		logger.setLevel(Level.DEBUG)
		resourceAt(FAMILIES_MODEL).propagate[contents += createRegister()]
	}

	def createFamily(String familieName) {
		FamiliesFactory.eINSTANCE.createFamily => [lastName = familieName]
	}

	def void insertFamily(String familieName) {
		FamilyRegister.from(FAMILIES_MODEL).propagate[families += createFamily(familieName)]
	}

	@Test
	def void testInsertRegister() {
		// insertRegister(); -> Schon erledigt durch @Before Each
		assertThat(resourceAt(PERSONS_MODEL), exists);
		assertEquals(1, resourceAt(PERSONS_MODEL).contents.size);
		assertEquals(1, resourceAt(PERSONS_MODEL).allContents.size);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(0, resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size);
	}

	@Test
	def void testInsertNewFamily() {
		insertFamily(FAMILY_NAME_1)
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(1, resourceAt(PERSONS_MODEL).contents.size);
		assertEquals(1, resourceAt(PERSONS_MODEL).allContents.size);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(0, resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size);
	}

	def getExistingFamily(String lastName) {
		FamilyRegister.from(FAMILIES_MODEL).families.findFirst[x|x.lastName.equals(lastName)]
	}
	
	
	def void checkCorrectRegisters(FamilyRegister famEq, EList<EObject> allOf, EList<EObject> noneOf){
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
		val EList<EObject> e = new BasicEList<EObject>();
		castedPerReg.persons.forEach[x|e.addAll(x)]		
		assertThat(e, containsAllOf(allOf));
		assertThat(e, containsNoneOf(noneOf));
	} 

	@Test
	def void testInsertFamilyWithFather() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		val fam = createFamily(FAMILY_NAME_1);
		logger.info(name + " - preparation done")
		dprint()
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families += fam;
			fam.father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_1; familyFather = fam]
		]
		logger.info(name + " - propagation done")
		dprint()		
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_1]
			]
		]
		var EList<EObject> allOf = new BasicEList<EObject>();
		var EList<EObject> noneOf = new BasicEList<EObject>();
		allOf.addAll(DAD11)
		checkCorrectRegisters(famEq, allOf, noneOf)
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testInsertFamilyWithMother() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		val fam = createFamily(FAMILY_NAME_1);
		logger.info(name + " - preparation done")
		dprint()
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families += fam;
			fam.mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_1; familyMother = fam]
		]
		logger.info(name + " - propagation done")
		dprint()		
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_1]
			]
		]
		var EList<EObject> allOf = new BasicEList<EObject>();
		var EList<EObject> noneOf = new BasicEList<EObject>();
		allOf.addAll(MOM11)
		checkCorrectRegisters(famEq, allOf, noneOf)
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testInsertFamilyWithSon() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		val fam = createFamily(FAMILY_NAME_1);
		logger.info(name + " - preparation done")
		dprint()
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families += fam;
			fam.sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1; familySon = fam]
		]
		logger.info(name + " - propagation done")
		dprint()		
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
			]
		]
		var EList<EObject> allOf = new BasicEList<EObject>();
		var EList<EObject> noneOf = new BasicEList<EObject>();
		allOf.addAll(SON11)
		checkCorrectRegisters(famEq, allOf, noneOf)
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testInsertFamilyWithDaughter() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")
		val fam = createFamily(FAMILY_NAME_1);
		logger.info(name + " - preparation done")
		dprint()
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families += fam;
			fam.daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAUGHTER_1; familyDaughter = fam]
		]
		logger.info(name + " - propagation done")
		dprint()		
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAUGHTER_1]
			]
		]
		var EList<EObject> allOf = new BasicEList<EObject>();
		var EList<EObject> noneOf = new BasicEList<EObject>();
		allOf.addAll(DAU11)
		checkCorrectRegisters(famEq, allOf, noneOf)
		logger.info(name + " - finished without errors")
	}

	def void createFamilyBeforeTesting() {
		val fam = createFamily(FAMILY_NAME_1);
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families += fam;
			fam.father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_1; familyFather = fam;]
			fam.mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_1; familyMother = fam]
			fam.sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1; familySon = fam]
			fam.daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAUGHTER_1; familyDaughter = fam]
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
		var EList<EObject> allOf = new BasicEList<EObject>();
		var EList<EObject> noneOf = new BasicEList<EObject>();
		allOf.addAll(DAD11, MOM11, SON11, DAU11)		
		checkCorrectRegisters(famEq, allOf, noneOf)
	}

	def static boolean equalPersons(PersonImpl p1, PersonImpl p2) {
		val boolean bothNull = (p1 == null) && (p2 == null);
		val boolean bothNotNull = (p1 != null) && (p2 != null);
		val boolean bothSameType = bothNotNull && (p1.class == p2.class);

		val boolean bothNamesNull = bothNotNull && (p1.fullName == null) && (p2.fullName == null);
		val boolean bothNamesNotNull = bothNotNull && (p1.fullName != null) && (p2.fullName != null);
		val boolean bothNamesEqual = bothNotNull && bothNamesNotNull && p1.fullName.equals(p2.fullName);

		val boolean bothBirthdayNull = bothNotNull && (p1.birthday == null) && (p2.birthday == null);
		val boolean bothBirthdayNotNull = bothNotNull && (p1.birthday != null) && (p2.birthday != null);
		val boolean bothBirthdayEqual = bothNotNull && bothBirthdayNotNull && p1.birthday.equals(p2.birthday);

		return bothNull || ((bothNotNull && bothSameType) && (bothNamesNull || bothNamesEqual) &&
			(bothBirthdayNull || bothBirthdayEqual));
	}

	@Test
	def void testDeleteFatherFromFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")		
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();
		logger.info(name + " - preparation done")
		dprint()
		// ===== ACTUAL TEST =====
		// Propagate changes		
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val selectedFamily = families.findFirst[x|x.lastName.equals(FAMILY_NAME_1) && x.father.firstName.equals(FIRST_FATHER_1)]
			selectedFamily.father = null
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
		var EList<EObject> allOf = new BasicEList<EObject>();
		var EList<EObject> noneOf = new BasicEList<EObject>();
		allOf.addAll(MOM11, SON11, DAU11)
		noneOf.addAll(DAD11)
		checkCorrectRegisters(famEq, allOf, noneOf)
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testDeleteSonFromFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")		
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();
		logger.info(name + " - preparation done")
		dprint()
		// ===== ACTUAL TEST =====
		// Propagate changes	
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val selectedFamily = families.findFirst [ x |
				x.lastName.equals(FAMILY_NAME_1) && x.sons.exists[y|y.firstName.equals(FIRST_SON_1)]
			]
			val sonToDelete = selectedFamily.sons.findFirst[x|x.firstName.equals(FIRST_SON_1)]
			selectedFamily.sons.remove(sonToDelete)
		]
		logger.info(name + " - propagation done")
		dprint()
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_1]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_1]
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAUGHTER_1]
			]
		]
		var EList<EObject> allOf = new BasicEList<EObject>();
		var EList<EObject> noneOf = new BasicEList<EObject>();
		allOf.addAll(MOM11, DAD11, DAU11)
		noneOf.addAll(SON11)
		checkCorrectRegisters(famEq, allOf, noneOf)
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testDeleteMotherFromFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")		
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();
		logger.info(name + " - preparation done")
		dprint()
		// ===== ACTUAL TEST =====
		// Propagate changes		
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val selectedFamily = families.findFirst[x|x.lastName.equals(FAMILY_NAME_1) && x.mother.firstName.equals(FIRST_MOTHER_1)]
			selectedFamily.mother = null
		]
		logger.info(name + " - propagation done")
		dprint()
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_1]
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAUGHTER_1]
			]
		]
		var EList<EObject> allOf = new BasicEList<EObject>();
		var EList<EObject> noneOf = new BasicEList<EObject>();
		allOf.addAll(DAD11, SON11, DAU11)
		noneOf.addAll(MOM11)
		checkCorrectRegisters(famEq, allOf, noneOf)
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testDeleteDaughterFromFamily() {		
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")		
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();
		logger.info(name + " - preparation done")
		dprint()
		// ===== ACTUAL TEST =====
		// Propagate changes	
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val selectedFamily = families.findFirst [ x |
				x.lastName.equals(FAMILY_NAME_1) && x.daughters.exists[y|y.firstName.equals(FIRST_DAUGHTER_1)]
			]
			val daughterToDelete = selectedFamily.daughters.findFirst[x|x.firstName.equals(FIRST_DAUGHTER_1)]
			selectedFamily.daughters.remove(daughterToDelete)
		]
		logger.info(name + " - propagation done")
		dprint()
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_1]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_1]
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
			]
		]
		var EList<EObject> allOf = new BasicEList<EObject>();
		var EList<EObject> noneOf = new BasicEList<EObject>();
		allOf.addAll(MOM11, DAD11, SON11)
		noneOf.addAll(DAU11)
		checkCorrectRegisters(famEq, allOf, noneOf)
		logger.info(name + " - finished without errors")
	}

	@Test
	def void testChangeLastName() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")		
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();
		logger.info(name + " - preparation done")
		dprint()
		// ===== ACTUAL TEST =====
		// Propagate changes		
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val selectedFamily = families.findFirst[x|x.lastName.equals(FAMILY_NAME_1)]
			selectedFamily.lastName = FAMILY_NAME_2
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
		var EList<EObject> allOf = new BasicEList<EObject>();
		var EList<EObject> noneOf = new BasicEList<EObject>();
		allOf.addAll(DAD12, MOM12, SON12, DAU12)
		noneOf.addAll(DAD11, MOM11, SON11, DAU11)
		checkCorrectRegisters(famEq, allOf, noneOf)
		logger.info(name + " - finished without errors")
	}

	@Test
	def void testChangeFirstNameFather() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")		
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();
		logger.info(name + " - preparation done")
		dprint()
		// ===== ACTUAL TEST =====
		// Propagate changes		
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val selectedFamily = families.findFirst[x|x.lastName.equals(FAMILY_NAME_1) && x.father.firstName.equals(FIRST_FATHER_1)]
			selectedFamily.father.firstName = FIRST_FATHER_2
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
		var EList<EObject> allOf = new BasicEList<EObject>();
		var EList<EObject> noneOf = new BasicEList<EObject>();
		allOf.addAll(DAD21, MOM11, SON11, DAU11)
		noneOf.addAll(DAD11)
		checkCorrectRegisters(famEq, allOf, noneOf)
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testChangeFirstNameSon() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")		
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();
		logger.info(name + " - preparation done")
		dprint()
		// ===== ACTUAL TEST =====
		// Propagate changes		
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val selectedFamily = families.findFirst [ x |
				x.lastName.equals(FAMILY_NAME_1) && x.sons.exists[y|y.firstName.equals(FIRST_SON_1)]
			]
			val sonToChange = selectedFamily.sons.findFirst[x|x.firstName.equals(FIRST_SON_1)]
			sonToChange.firstName = FIRST_SON_2
		]
		logger.info(name + " - propagation done")
		dprint()
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_1]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_1]
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_2]
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAUGHTER_1]
			]
		]
		var EList<EObject> allOf = new BasicEList<EObject>();
		var EList<EObject> noneOf = new BasicEList<EObject>();
		allOf.addAll(DAD11, MOM11, SON21, DAU11)
		noneOf.addAll(SON11)
		checkCorrectRegisters(famEq, allOf, noneOf)
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testChangeFirstNameMother() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")		
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();
		logger.info(name + " - preparation done")
		dprint()
		// ===== ACTUAL TEST =====
		// Propagate changes		
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val selectedFamily = families.findFirst[x|x.lastName.equals(FAMILY_NAME_1) && x.mother.firstName.equals(FIRST_MOTHER_1)]
			selectedFamily.mother.firstName = FIRST_MOTHER_2
		]
		logger.info(name + " - propagation done")
		dprint()
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_1]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_2]
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAUGHTER_1]
			]
		]
		var EList<EObject> allOf = new BasicEList<EObject>();
		var EList<EObject> noneOf = new BasicEList<EObject>();
		allOf.addAll(DAD11, MOM21, SON11, DAU11)
		noneOf.addAll(MOM11)
		checkCorrectRegisters(famEq, allOf, noneOf)
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testChangeFirstNameDaughter() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")		
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();
		logger.info(name + " - preparation done")
		dprint()
		// ===== ACTUAL TEST =====
		// Propagate changes		
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val selectedFamily = families.findFirst [ x |
				x.lastName.equals(FAMILY_NAME_1) && x.daughters.exists[y|y.firstName.equals(FIRST_DAUGHTER_1)]
			]
			val daughterToChange = selectedFamily.daughters.findFirst[x|x.firstName.equals(FIRST_DAUGHTER_1)]
			daughterToChange.firstName = FIRST_DAUGHTER_2
		]
		logger.info(name + " - propagation done")
		dprint()
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_1]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_1]
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAUGHTER_2]
			]
		]
		var EList<EObject> allOf = new BasicEList<EObject>();
		var EList<EObject> noneOf = new BasicEList<EObject>();
		allOf.addAll(DAD11, MOM11, SON11, DAU21)
		noneOf.addAll(DAU11)
		checkCorrectRegisters(famEq, allOf, noneOf)
		logger.info(name + " - finished without errors")
	}

	@Test
	def void testInsertDadIfDadAlreadyExists_DiscardChanges() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")		
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();
		logger.info(name + " - preparation done")
		dprint()
		// ===== ACTUAL TEST =====
		// Propagate changes
		userInteraction.addNextSingleSelection(0)
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val fam = families.findFirst[x|x.lastName.equals(FAMILY_NAME_1)]
			fam.father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_2]
		]
		logger.info(name + " - propagation done")
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
		var EList<EObject> allOf = new BasicEList<EObject>();
		var EList<EObject> noneOf = new BasicEList<EObject>();
		allOf.addAll(DAD11, MOM11, SON11, DAU11)
		noneOf.addAll(DAD21)
		checkCorrectRegisters(famEq, allOf, noneOf)
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testInsertDadIfDadAlreadyExists_ReplaceExisting() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")		
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();
		logger.info(name + " - preparation done")
		dprint()
		// ===== ACTUAL TEST =====
		// Propagate changes
		userInteraction.addNextSingleSelection(1)
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val fam = families.findFirst[x|x.lastName.equals(FAMILY_NAME_1)]
			fam.father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_2]
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
		var EList<EObject> allOf = new BasicEList<EObject>();
		var EList<EObject> noneOf = new BasicEList<EObject>();
		allOf.addAll(DAD21, MOM11, SON11, DAU11)
		noneOf.addAll(DAD11)
		checkCorrectRegisters(famEq, allOf, noneOf)
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testInsertDadIfDadAlreadyExists_MoveToNewFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")		
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();
		logger.info(name + " - preparation done")
		dprint()
		// ===== ACTUAL TEST =====
		// Propagate changes
		userInteraction.addNextSingleSelection(2)
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val fam = families.findFirst[x|x.lastName.equals(FAMILY_NAME_1)]
			fam.father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_2]
		]
		logger.info(name + " - propagation done")
		dprint()
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_1]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_1]
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAUGHTER_1]
			]
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_2]
			]
		]
		var EList<EObject> allOf = new BasicEList<EObject>();
		var EList<EObject> noneOf = new BasicEList<EObject>();
		allOf.addAll(DAD11, MOM11, SON11, DAU11, DAD21)
		checkCorrectRegisters(famEq, allOf, noneOf)
		logger.info(name + " - finished without errors")		
	}



	@Test
	def void testInsertMomIfMomAlreadyExists_DiscardChanges() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")		
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();
		logger.info(name + " - preparation done")
		dprint()
		// ===== ACTUAL TEST =====
		// Propagate changes
		userInteraction.addNextSingleSelection(0)
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val fam = families.findFirst[x|x.lastName.equals(FAMILY_NAME_1)]
			fam.mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_2]
		]
		logger.info(name + " - propagation done")
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
		var EList<EObject> allOf = new BasicEList<EObject>();
		var EList<EObject> noneOf = new BasicEList<EObject>();
		allOf.addAll(DAD11, MOM11, SON11, DAU11)
		noneOf.addAll(MOM21)
		checkCorrectRegisters(famEq, allOf, noneOf)
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testInsertMomIfMomAlreadyExists_ReplaceExisting() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")		
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();
		logger.info(name + " - preparation done")
		dprint()
		// ===== ACTUAL TEST =====
		// Propagate changes
		userInteraction.addNextSingleSelection(1)
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val fam = families.findFirst[x|x.lastName.equals(FAMILY_NAME_1)]
			fam.mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_2]
		]
		logger.info(name + " - propagation done")
		dprint()
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_1]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_2]
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAUGHTER_1]
			]
		]
		var EList<EObject> allOf = new BasicEList<EObject>();
		var EList<EObject> noneOf = new BasicEList<EObject>();
		allOf.addAll(DAD11, MOM21, SON11, DAU11)
		noneOf.addAll(MOM11)
		checkCorrectRegisters(famEq, allOf, noneOf)
		logger.info(name + " - finished without errors")
	}
	@Test
	def void testInsertMomIfMomAlreadyExists_MoveToNewFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")		
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();
		logger.info(name + " - preparation done")
		dprint()
		// ===== ACTUAL TEST =====
		// Propagate changes
		userInteraction.addNextSingleSelection(2)
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val fam = families.findFirst[x|x.lastName.equals(FAMILY_NAME_1)]
			fam.mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_2]
		]
		logger.info(name + " - propagation done")
		dprint()
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_1]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_1]
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAUGHTER_1]
			]
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = FAMILY_NAME_1
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_2]
			]
		]
		var EList<EObject> allOf = new BasicEList<EObject>();
		var EList<EObject> noneOf = new BasicEList<EObject>();
		allOf.addAll(DAD11, MOM11, SON11, DAU11, MOM21)
		checkCorrectRegisters(famEq, allOf, noneOf)
		logger.info(name + " - finished without errors")		
	}



//FrameworkTestChange
	@Test
	def void testDeleteFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")		
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();
		logger.info(name + " - preparation done")
		dprint()
		// ===== ACTUAL TEST =====
		// Propagate changes
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families.removeIf([x|x.lastName.equals(FAMILY_NAME_1)])
		]
		logger.info(name + " - propagation done")
		dprint()
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister
		var EList<EObject> allOf = new BasicEList<EObject>();
		var EList<EObject> noneOf = new BasicEList<EObject>();
		noneOf.addAll(DAD11, MOM11, SON11, DAU11)		
		checkCorrectRegisters(famEq, allOf, noneOf)
		logger.info(name + " - finished without errors")		
	}

	@Test
	def void testDeleteFamilyRegister() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.info(name + " - begin")		
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();
		logger.info(name + " - preparation done")
		dprint()
		// ===== ACTUAL TEST =====
		// Propagate changes
		resourceAt(FAMILIES_MODEL).propagate[contents.clear()]		
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
