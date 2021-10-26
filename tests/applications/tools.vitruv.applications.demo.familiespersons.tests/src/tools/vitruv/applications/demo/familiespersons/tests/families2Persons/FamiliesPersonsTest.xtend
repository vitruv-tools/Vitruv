package tools.vitruv.applications.demo.familiespersons.tests.families2Persons

import edu.kit.ipd.sdq.metamodels.families.FamiliesFactory
import edu.kit.ipd.sdq.metamodels.families.FamilyRegister
import edu.kit.ipd.sdq.metamodels.persons.Female
import edu.kit.ipd.sdq.metamodels.persons.Male
import edu.kit.ipd.sdq.metamodels.persons.PersonRegister
import edu.kit.ipd.sdq.metamodels.persons.PersonsFactory
import edu.kit.ipd.sdq.metamodels.persons.impl.PersonImpl
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

import static org.hamcrest.CoreMatchers.*
import static org.hamcrest.MatcherAssert.assertThat
import static org.junit.jupiter.api.Assertions.assertEquals
import static tools.vitruv.testutils.matchers.ModelMatchers.*
import static tools.vitruv.testutils.matchers.ModelMatchers.containsAllOf
import static tools.vitruv.testutils.matchers.ModelMatchers.containsNoneOf
import edu.kit.ipd.sdq.metamodels.families.Family

/**Test to validate the transfer of changes from the FamilyModel to the PersonModel.
 * @author Dirk Neumann   
 */
class FamiliesPersonsTest extends VitruvApplicationTest {
	static val logger = Logger.getLogger(FamiliesPersonsTest);

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

	/* Static reusable predefined Persons.
	 * The first number indicates from which string set (above) the forename is.
	 * the second number indicates from which string set (above) the lastname is. 
	 */ 
	final static Male DAD11 = PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_FATHER_1 + " " + FAMILY_NAME_1]
	final static Female MOM11 = PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOTHER_1 + " " + FAMILY_NAME_1]
	final static Male SON11 = PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON_1 + " " + FAMILY_NAME_1]
	final static Female DAU11 = PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAUGHTER_1 + " " + FAMILY_NAME_1]
	
	final static Male DAD12 = PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_FATHER_1 + " " + FAMILY_NAME_2]
	final static Female MOM12 = PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOTHER_1 + " " + FAMILY_NAME_2]
	final static Male SON12 = PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON_1 + " " + FAMILY_NAME_2]
	final static Female DAU12 = PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAUGHTER_1 + " " + FAMILY_NAME_2]
	
	final static Male DAD21 = PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_FATHER_2 + " " + FAMILY_NAME_1]
	final static Female MOM21 = PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_MOTHER_2 + " " + FAMILY_NAME_1]
	final static Male SON21 = PersonsFactory.eINSTANCE.createMale => [fullName = FIRST_SON_2 + " " + FAMILY_NAME_1]
	final static Female DAU21 = PersonsFactory.eINSTANCE.createFemale => [fullName = FIRST_DAUGHTER_2 + " " + FAMILY_NAME_1]

	/**Set the correct set of reations and routines for this test suite
	 */
	override protected getChangePropagationSpecifications() {
		return #[new FamiliesToPersonsChangePropagationSpecification()]
	}

	/**Before each test a new {@link FamilyRegister} is created as starting point.
	 * This is checked by several assertions to ensure correct preconditions for the tests. 
	 */
	@BeforeEach
	def void insertRegister() {
		logger.setLevel(Level.DEBUG)
		resourceAt(FAMILIES_MODEL).propagate[contents += FamiliesFactory.eINSTANCE.createFamilyRegister]
		assertThat(resourceAt(PERSONS_MODEL), exists);
		assertEquals(1, resourceAt(PERSONS_MODEL).contents.size);
		assertEquals(1, resourceAt(PERSONS_MODEL).allContents.size);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(0, resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size);
	}

	//Creates a family with the given lastname. Used by the "insertFamilyWith"-tests
	/**Creates a {@link Family} with the given familieName. Used by the "insertFamilyWith..."-tests
	 */
	def createFamily(String familieName) {
		FamiliesFactory.eINSTANCE.createFamily => [lastName = familieName]
	}

	/**Insert a new {@link Family}. This should not have any effect on the Persons-Model.
	 */
	@Test
	def void testInsertNewFamily() {		
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		val fam = createFamily(FAMILY_NAME_1);
		logger.trace(name + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate[families += createFamily(FAMILY_NAME_1)]
		logger.trace(name + " - propagation done")
		assertThat(resourceAt(PERSONS_MODEL), exists)
		assertEquals(1, resourceAt(PERSONS_MODEL).contents.size);
		assertEquals(1, resourceAt(PERSONS_MODEL).allContents.size);
		assertThat(resourceAt(PERSONS_MODEL).contents.get(0), instanceOf(PersonRegister));
		assertEquals(0, resourceAt(PERSONS_MODEL).contents.get(0).eAllContents().size);
		logger.trace(name + " - finished without errors")		
	}	
	
	/**Check up method to first check the correct basic structure of both models and then compare
	 * the resulting {@link FamilyRegister} with a predefined one and to check for the existence
	 * and abscence of predefined {@link Persons}. Basically encapsulate the assertion part of each test.
	 */
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

	/**Insert a new {@link Family} and insert a father into it afterwards.
	 */
	@Test
	def void testInsertFamilyWithFather() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		val fam = createFamily(FAMILY_NAME_1);
		logger.trace(name + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families += fam;
			fam.father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_1; familyFather = fam]
		]
		logger.trace(name + " - propagation done")	
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
		logger.trace(name + " - finished without errors")
	}
	/**Insert a new {@link Family} and insert a mother into it afterwards.
	 */
	@Test
	def void testInsertFamilyWithMother() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		val fam = createFamily(FAMILY_NAME_1);
		logger.trace(name + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families += fam;
			fam.mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_1; familyMother = fam]
		]
		logger.trace(name + " - propagation done")
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
		logger.trace(name + " - finished without errors")
	}
	/**Insert a new {@link Family} and insert a son into it afterwards.
	 */
	@Test
	def void testInsertFamilyWithSon() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		val fam = createFamily(FAMILY_NAME_1);
		logger.trace(name + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families += fam;
			fam.sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1; familySon = fam]
		]
		logger.trace(name + " - propagation done")
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
		logger.trace(name + " - finished without errors")
	}
	/**Insert a new {@link Family} and insert a daughter into it afterwards.
	 */
	@Test
	def void testInsertFamilyWithDaughter() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")
		val fam = createFamily(FAMILY_NAME_1);
		logger.trace(name + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families += fam;
			fam.daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAUGHTER_1; familyDaughter = fam]
		]
		logger.trace(name + " - propagation done")
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
		logger.trace(name + " - finished without errors")
	}

	/**This is used to build the starting point for many other tests like deleting and renaming operations.
	 * Creates a {@link Family} including a father, a mother, a son and a daughter and maps this
	 * changes to the {@link PersonRegister} which then includes two {@link Male} and two {@link Female}.
	 */
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

	/**Deletes a father from a {@link Family} and the corresponding {@link Male} from the {@link PersonRegister}.
	 */
	@Test
	def void testDeleteFatherFromFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")		
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();
		logger.trace(name + " - preparation done")
		// ===== ACTUAL TEST =====
		// Propagate changes		
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val selectedFamily = families.findFirst[x|x.lastName.equals(FAMILY_NAME_1) && x.father.firstName.equals(FIRST_FATHER_1)]
			selectedFamily.father = null
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
		var EList<EObject> allOf = new BasicEList<EObject>();
		var EList<EObject> noneOf = new BasicEList<EObject>();
		allOf.addAll(MOM11, SON11, DAU11)
		noneOf.addAll(DAD11)
		checkCorrectRegisters(famEq, allOf, noneOf)
		logger.trace(name + " - finished without errors")
	}
	/**Deletes a son from a {@link Family} and the corresponding {@link Male} from the {@link PersonRegister}.
	 */	
	@Test
	def void testDeleteSonFromFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")		
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();
		logger.trace(name + " - preparation done")
		// ===== ACTUAL TEST =====
		// Propagate changes	
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val selectedFamily = families.findFirst [ x |
				x.lastName.equals(FAMILY_NAME_1) && x.sons.exists[y|y.firstName.equals(FIRST_SON_1)]
			]
			val sonToDelete = selectedFamily.sons.findFirst[x|x.firstName.equals(FIRST_SON_1)]
			selectedFamily.sons.remove(sonToDelete)
		]
		logger.trace(name + " - propagation done")
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
		logger.trace(name + " - finished without errors")
	}
	/**Deletes a mother from a {@link Family} and the corresponding {@link Female} from the {@link PersonRegister}.
	 */	
	@Test
	def void testDeleteMotherFromFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")		
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();
		logger.trace(name + " - preparation done")
		// ===== ACTUAL TEST =====
		// Propagate changes		
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val selectedFamily = families.findFirst[x|x.lastName.equals(FAMILY_NAME_1) && x.mother.firstName.equals(FIRST_MOTHER_1)]
			selectedFamily.mother = null
		]
		logger.trace(name + " - propagation done")
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
		logger.trace(name + " - finished without errors")
	}
	/**Deletes a daughter from a {@link Family} and the corresponding {@link Female} from the {@link PersonRegister}.
	 */
	@Test
	def void testDeleteDaughterFromFamily() {		
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")		
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();
		logger.trace(name + " - preparation done")
		// ===== ACTUAL TEST =====
		// Propagate changes	
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val selectedFamily = families.findFirst [ x |
				x.lastName.equals(FAMILY_NAME_1) && x.daughters.exists[y|y.firstName.equals(FIRST_DAUGHTER_1)]
			]
			val daughterToDelete = selectedFamily.daughters.findFirst[x|x.firstName.equals(FIRST_DAUGHTER_1)]
			selectedFamily.daughters.remove(daughterToDelete)
		]
		logger.trace(name + " - propagation done")
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
		logger.trace(name + " - finished without errors")
	}

	/**Changes the lastname of a {@link Family} and should edit the fullnames of
	 * all corresponding {@link Person}s from the {@link PersonRegister}.
	 */
	@Test
	def void testChangeLastName() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")		
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();
		logger.trace(name + " - preparation done")
		// ===== ACTUAL TEST =====
		// Propagate changes		
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val selectedFamily = families.findFirst[x|x.lastName.equals(FAMILY_NAME_1)]
			selectedFamily.lastName = FAMILY_NAME_2
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
		var EList<EObject> allOf = new BasicEList<EObject>();
		var EList<EObject> noneOf = new BasicEList<EObject>();
		allOf.addAll(DAD12, MOM12, SON12, DAU12)
		noneOf.addAll(DAD11, MOM11, SON11, DAU11)
		checkCorrectRegisters(famEq, allOf, noneOf)
		logger.trace(name + " - finished without errors")
	}

	/**Changes the firstname of a father of a {@link Family} and should edit the 
	 * fullname of the corresponding {@link Male} in the {@link PersonRegister}.
	 */
	@Test
	def void testChangeFirstNameFather() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")		
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();
		logger.trace(name + " - preparation done")
		// ===== ACTUAL TEST =====
		// Propagate changes		
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val selectedFamily = families.findFirst[x|x.lastName.equals(FAMILY_NAME_1) && x.father.firstName.equals(FIRST_FATHER_1)]
			selectedFamily.father.firstName = FIRST_FATHER_2
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
		var EList<EObject> allOf = new BasicEList<EObject>();
		var EList<EObject> noneOf = new BasicEList<EObject>();
		allOf.addAll(DAD21, MOM11, SON11, DAU11)
		noneOf.addAll(DAD11)
		checkCorrectRegisters(famEq, allOf, noneOf)
		logger.trace(name + " - finished without errors")
	}
	/**Changes the firstname of a son of a {@link Family} and should edit the 
	 * fullname of the corresponding {@link Male} in the {@link PersonRegister}.
	 */
	@Test
	def void testChangeFirstNameSon() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")		
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();
		logger.trace(name + " - preparation done")
		// ===== ACTUAL TEST =====
		// Propagate changes		
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val selectedFamily = families.findFirst [ x |
				x.lastName.equals(FAMILY_NAME_1) && x.sons.exists[y|y.firstName.equals(FIRST_SON_1)]
			]
			val sonToChange = selectedFamily.sons.findFirst[x|x.firstName.equals(FIRST_SON_1)]
			sonToChange.firstName = FIRST_SON_2
		]
		logger.trace(name + " - propagation done")
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
		logger.trace(name + " - finished without errors")
	}
	/**Changes the firstname of a mother of a {@link Family} and should edit the 
	 * fullname of the corresponding {@link Female} in the {@link PersonRegister}.
	 */
	@Test
	def void testChangeFirstNameMother() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")		
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();
		logger.trace(name + " - preparation done")
		// ===== ACTUAL TEST =====
		// Propagate changes		
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val selectedFamily = families.findFirst[x|x.lastName.equals(FAMILY_NAME_1) && x.mother.firstName.equals(FIRST_MOTHER_1)]
			selectedFamily.mother.firstName = FIRST_MOTHER_2
		]
		logger.trace(name + " - propagation done")
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
		logger.trace(name + " - finished without errors")
	}
	/**Changes the firstname of a daughter of a {@link Family} and should edit the 
	 * fullname of the corresponding {@link Female} in the {@link PersonRegister}.
	 */
	@Test
	def void testChangeFirstNameDaughter() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")		
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();
		logger.trace(name + " - preparation done")
		// ===== ACTUAL TEST =====
		// Propagate changes		
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val selectedFamily = families.findFirst [ x |
				x.lastName.equals(FAMILY_NAME_1) && x.daughters.exists[y|y.firstName.equals(FIRST_DAUGHTER_1)]
			]
			val daughterToChange = selectedFamily.daughters.findFirst[x|x.firstName.equals(FIRST_DAUGHTER_1)]
			daughterToChange.firstName = FIRST_DAUGHTER_2
		]
		logger.trace(name + " - propagation done")
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
		logger.trace(name + " - finished without errors")
	}

	/**Tries to insert a new father into a {@link Family} which already has a father.
	 * In this scenario the user decides to discard the changes. Therefore all changes
	 * in the {@link FamilyRegister} should be reverted and the {@link PersonsRegister}  
	 * should not be edited. The rest of the family stays the same as well.
	 */
	@Test
	def void testInsertDadIfDadAlreadyExists_DiscardChanges() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")		
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();
		logger.trace(name + " - preparation done")
		// ===== ACTUAL TEST =====
		// Propagate changes
		userInteraction.addNextSingleSelection(0)
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val fam = families.findFirst[x|x.lastName.equals(FAMILY_NAME_1)]
			fam.father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_2]
		]
		logger.trace(name + " - propagation done")
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
		logger.trace(name + " - finished without errors")
	}
	/**Tries to insert a new father into a {@link Family} which already has a father.
	 * In this scenario the user decides to replace the existing father. Therefore the
	 * previous father will be deleted from the {@link FamilyRegister} and replaced by 
	 * the new one. In the {@link PersonsRegister} the old {@link Male} should be either
	 * deleted and replaced by a new and matching {@link Male} or the old one is renamed.
	 * Currently, the the old {@link Male} is deleted and replaced by a new {@link Male}.
	 * Here, one should mind the differences concerning the birthday attribute of the {@link Male}
	 * in the {@link PersonRegister}. Renaming would keep the birthday
	 * which is already covered by a previous test case.
	 * The rest of the family stays the same.
	 */
	@Test
	def void testInsertDadIfDadAlreadyExists_ReplaceExisting() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")		
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();
		logger.trace(name + " - preparation done")
		// ===== ACTUAL TEST =====
		// Propagate changes
		userInteraction.addNextSingleSelection(1)
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val fam = families.findFirst[x|x.lastName.equals(FAMILY_NAME_1)]
			fam.father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_2]
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
		var EList<EObject> allOf = new BasicEList<EObject>();
		var EList<EObject> noneOf = new BasicEList<EObject>();
		allOf.addAll(DAD21, MOM11, SON11, DAU11)
		noneOf.addAll(DAD11)
		checkCorrectRegisters(famEq, allOf, noneOf)
		logger.trace(name + " - finished without errors")
	}
	/**Tries to insert a new father into a {@link Family} which already has a father.
	 * In this scenario the user decides to not replace the existing father but to insert
	 * the new father into a new family. This family will receive the same lastname but
	 * will not contain any of the other {@link Member}s from the old family, nor copies of them.
	 * Therefore changes concerning the old {@link Family} will be reverted.
	 */
	@Test
	def void testInsertDadIfDadAlreadyExists_MoveToNewFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")		
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();
		logger.trace(name + " - preparation done")
		// ===== ACTUAL TEST =====
		// Propagate changes
		userInteraction.addNextSingleSelection(2)
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val fam = families.findFirst[x|x.lastName.equals(FAMILY_NAME_1)]
			fam.father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_FATHER_2]
		]
		logger.trace(name + " - propagation done")
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
		logger.trace(name + " - finished without errors")		
	}

	/**Tries to insert a new mother into a {@link Family} which already has a mother.
	 * In this scenario the user decides to discard the changes. Therefore all changes
	 * in the {@link FamilyRegister} should be reverted and the {@link PersonsRegister}  
	 * should not be edited. The rest of the family stays the same as well.
	 */
	@Test
	def void testInsertMomIfMomAlreadyExists_DiscardChanges() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")		
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();
		logger.trace(name + " - preparation done")
		// ===== ACTUAL TEST =====
		// Propagate changes
		userInteraction.addNextSingleSelection(0)
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val fam = families.findFirst[x|x.lastName.equals(FAMILY_NAME_1)]
			fam.mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_2]
		]
		logger.trace(name + " - propagation done")
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
		logger.trace(name + " - finished without errors")
	}
	/**Tries to insert a new mother into a {@link Family} which already has a mother.
	 * In this scenario the user decides to replace the existing mother. Therefore the
	 * previous mother will be deleted from the {@link FamilyRegister} and replaced by 
	 * the new one. In the {@link PersonsRegister} the old {@link Female} should be either
	 * deleted and replaced by a new and matching {@link Female} or the old one is renamed.
	 * Currently, the the old {@link Female} is deleted and replaced by a new {@link Female}.
	 * Here, one should mind the differences concerning the birthday attribute of the {@link Female}
	 * in the {@link PersonRegister}. Renaming would keep the birthday
	 * which is already covered by a previous test case.
	 * The rest of the family stays the same.
	 */
	@Test
	def void testInsertMomIfMomAlreadyExists_ReplaceExisting() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")		
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();
		logger.trace(name + " - preparation done")
		// ===== ACTUAL TEST =====
		// Propagate changes
		userInteraction.addNextSingleSelection(1)
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val fam = families.findFirst[x|x.lastName.equals(FAMILY_NAME_1)]
			fam.mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_2]
		]
		logger.trace(name + " - propagation done")
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
		logger.trace(name + " - finished without errors")
	}
	/**Tries to insert a new mother into a {@link Family} which already has a mother.
	 * In this scenario the user decides to not replace the existing mother but to insert
	 * the new mother into a new family. This family will receive the same lastname but
	 * will not contain any of the other {@link Member}s from the old family, nor copies of them.
	 * Therefore changes concerning the old {@link Family} will be reverted.
	 */
	@Test
	def void testInsertMomIfMomAlreadyExists_MoveToNewFamily() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")		
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();
		logger.trace(name + " - preparation done")
		// ===== ACTUAL TEST =====
		// Propagate changes
		userInteraction.addNextSingleSelection(2)
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val fam = families.findFirst[x|x.lastName.equals(FAMILY_NAME_1)]
			fam.mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOTHER_2]
		]
		logger.trace(name + " - propagation done")
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
		logger.trace(name + " - finished without errors")		
	}

	/**Deletes all {@link Family}s with matching lastname from the {@link FamilyRegister}.
	 * All {@link Member}s which were contained in these families will be deleted together
	 * with there corresponding {@link Person}s in the {@link PersonRegister} as well.
	 * If only families without members are deleted, the {@link PersonRegister} 
	 * will not be affected.
	 */
	@Test
	def void testDeleteAllFamiliesWithMatchingName() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")		
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();
		logger.trace(name + " - preparation done")
		// ===== ACTUAL TEST =====
		// Propagate changes
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families.removeIf([x|x.lastName.equals(FAMILY_NAME_1)])
		]
		logger.trace(name + " - propagation done")
		val FamilyRegister famEq = FamiliesFactory.eINSTANCE.createFamilyRegister
		var EList<EObject> allOf = new BasicEList<EObject>();
		var EList<EObject> noneOf = new BasicEList<EObject>();
		noneOf.addAll(DAD11, MOM11, SON11, DAU11)		
		checkCorrectRegisters(famEq, allOf, noneOf)
		logger.trace(name + " - finished without errors")		
	}
	/**Deletes the {@link FamilyRegister} with all its contents which leads to
	 * the deletion of the corresponding {@link PersonRegister} with all its contents.
	 */
	@Test
	def void testDeleteFamilyRegister() {
		val String name = new Object() {}.getClass().getEnclosingMethod().getName().toFirstUpper();
		logger.trace(name + " - begin")		
		// ===== PRECONDITION =====
		// Set up the family
		this.createFamilyBeforeTesting();
		logger.trace(name + " - preparation done")
		// ===== ACTUAL TEST =====
		// Propagate changes
		resourceAt(FAMILIES_MODEL).propagate[contents.clear()]		
		logger.trace(name + " - propagation done")
		assertEquals(0, resourceAt(FAMILIES_MODEL).contents.size())
		assertEquals(0, resourceAt(PERSONS_MODEL).contents.size())
		assertThat(resourceAt(FAMILIES_MODEL), not(exists))
		assertThat(resourceAt(PERSONS_MODEL), not(exists))
		logger.trace(name + " - finished without errors")
	}
}
