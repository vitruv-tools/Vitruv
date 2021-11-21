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
import org.apache.log4j.Level
import org.apache.log4j.Logger
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
import org.junit.jupiter.api.TestInfo

/**Test to validate the transfer of changes from the FamilyModel to the PersonModel.
 * @author Dirk Neumann
 */
class FamiliesPersonsTest extends VitruvApplicationTest {
	static val logger = Logger.getLogger(FamiliesPersonsTest)

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

	/**Set the correct set of reactions and routines for this test suite
	 */
	override protected getChangePropagationSpecifications() {
		return #[new FamiliesToPersonsChangePropagationSpecification()]
	}

	TestInfo testInfo = null

	/**Before each test a new {@link FamilyRegister} is created as starting point.
	 * This is checked by several assertions to ensure correct preconditions for the tests. 
	 */
	@BeforeEach
	def void insertRegister(TestInfo testInfo) {
		this.testInfo = testInfo
		println(this.testInfo.getDisplayName())
		resourceAt(FAMILIES_MODEL).propagate[contents += FamiliesFactory.eINSTANCE.createFamilyRegister]
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

	/**Insert a new {@link Family}. This should not have any effect on the Persons-Model.
	 */
	@Test
	def void testInsertNewFamily() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		val family = createFamily(LAST_NAME_1)
		logger.trace(surroundingMethodName + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate[families += family]
		logger.trace(surroundingMethodName + " - propagation done")
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister()		
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}
	
	/**Check if the actual {@link FamilyRegister looks like the expected one.
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
	
	/**Check if the actual {@link PersonRegister looks like the expected one.
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
	
	/**Insert a new {@link Family} and insert a father into it afterwards.
	 */
	@Test
	def void testInsertFamilyWithFather() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		val family = createFamily(LAST_NAME_1)
		logger.trace(surroundingMethodName + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families += family
			family.father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += PersonsFactory.eINSTANCE.createMale => [
				fullName = FIRST_DAD_1 + " " + LAST_NAME_1 
			]
		]
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Insert a new {@link Family} and insert a mother into it afterwards.
	 */
	@Test
	def void testInsertFamilyWithMother() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		val family = createFamily(LAST_NAME_1)
		logger.trace(surroundingMethodName + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families += family
			family.mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += MOM11
		]
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Insert a new {@link Family} and insert a son into it afterwards.
	 */
	@Test
	def void testInsertFamilyWithSon() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		val family = createFamily(LAST_NAME_1)
		logger.trace(surroundingMethodName + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families += family
			family.sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += SON11
		]
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Insert a new {@link Family} and insert a daughter into it afterwards.
	 */
	@Test
	def void testInsertFamilyWithDaughter() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		val family = createFamily(LAST_NAME_1)
		logger.trace(surroundingMethodName + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families += family
			family.daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += DAU11
		]
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**This is used to build the starting point for many other tests like deleting and renaming operations.
	 * Creates a {@link Family} including a father, a mother, a son and a daughter and maps this
	 * changes to the {@link PersonRegister} which then includes two {@link Male} and two {@link Female}.
	 */
	def void createFamilyBeforeTesting() {
		val family = createFamily(LAST_NAME_1)
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families += family
			family.father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
			family.mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
			family.sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
			family.daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += #[DAD11, MOM11, SON11, DAU11]
		]
		assertCorrectPersonRegister(expectedPersonRegister)
	}

	/**Deletes a father from a {@link Family} and the corresponding {@link Male} from the {@link PersonRegister}.
	 */
	@Test
	def void testDeleteFatherFromFamily() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		this.createFamilyBeforeTesting()
		logger.trace(surroundingMethodName + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val selectedFamily = families.findFirst [
				it.lastName.equals(LAST_NAME_1) && it.father.firstName.equals(FIRST_DAD_1)
			]
			selectedFamily.father = null
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += #[MOM11, SON11, DAU11]
		]
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Deletes a son from a {@link Family} and the corresponding {@link Male} from the {@link PersonRegister}.
	 */
	@Test
	def void testDeleteSonFromFamily() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		this.createFamilyBeforeTesting()
		logger.trace(surroundingMethodName + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val selectedFamily = families.findFirst [
				it.lastName.equals(LAST_NAME_1) && it.sons.exists[it.firstName.equals(FIRST_SON_1)]
			]
			val sonToDelete = selectedFamily.sons.findFirst[it.firstName.equals(FIRST_SON_1)]
			selectedFamily.sons.remove(sonToDelete)
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += #[DAD11, MOM11, DAU11]
		]
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Deletes a mother from a {@link Family} and the corresponding {@link Female} from the {@link PersonRegister}.
	 */
	@Test
	def void testDeleteMotherFromFamily() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		this.createFamilyBeforeTesting()
		logger.trace(surroundingMethodName + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val selectedFamily = families.findFirst [
				it.lastName.equals(LAST_NAME_1) && it.mother.firstName.equals(FIRST_MOM_1)
			]
			selectedFamily.mother = null
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += #[DAD11, SON11, DAU11]
		]
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Deletes a daughter from a {@link Family} and the corresponding {@link Female} from the {@link PersonRegister}.
	 */
	@Test
	def void testDeleteDaughterFromFamily() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		this.createFamilyBeforeTesting()
		logger.trace(surroundingMethodName + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val selectedFamily = families.findFirst [
				it.lastName.equals(LAST_NAME_1) && it.daughters.exists[it.firstName.equals(FIRST_DAU_1)]
			]
			val daughterToDelete = selectedFamily.daughters.findFirst[it.firstName.equals(FIRST_DAU_1)]
			selectedFamily.daughters.remove(daughterToDelete)
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += #[DAD11, MOM11, SON11]
		]
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Changes the lastname of a {@link Family} and should edit the fullnames of
	 * all corresponding {@link Person}s from the {@link PersonRegister}.
	 */
	@Test
	def void testChangeLastName() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		this.createFamilyBeforeTesting()
		logger.trace(surroundingMethodName + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val selectedFamily = families.findFirst[it.lastName.equals(LAST_NAME_1)]
			selectedFamily.lastName = LAST_NAME_2
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += #[DAD12, MOM12, SON12, DAU12]
		]
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Changes the firstname of a father of a {@link Family} and should edit the 
	 * fullname of the corresponding {@link Male} in the {@link PersonRegister}.
	 */
	@Test
	def void testChangeFirstNameFather() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		this.createFamilyBeforeTesting()
		logger.trace(surroundingMethodName + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val selectedFamily = families.findFirst [
				it.lastName.equals(LAST_NAME_1) && it.father.firstName.equals(FIRST_DAD_1)
			]
			selectedFamily.father.firstName = FIRST_DAD_2
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += #[DAD21, MOM11, SON11, DAU11]
		]
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Changes the firstname of a son of a {@link Family} and should edit the 
	 * fullname of the corresponding {@link Male} in the {@link PersonRegister}.
	 */
	@Test
	def void testChangeFirstNameSon() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		this.createFamilyBeforeTesting()
		logger.trace(surroundingMethodName + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val selectedFamily = families.findFirst [
				it.lastName.equals(LAST_NAME_1) && it.sons.exists[y|y.firstName.equals(FIRST_SON_1)]
			]
			val sonToChange = selectedFamily.sons.findFirst[it.firstName.equals(FIRST_SON_1)]
			sonToChange.firstName = FIRST_SON_2
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += #[DAD11, MOM11, SON21, DAU11]
		]
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Changes the firstname of a mother of a {@link Family} and should edit the 
	 * fullname of the corresponding {@link Female} in the {@link PersonRegister}.
	 */
	@Test
	def void testChangeFirstNameMother() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		this.createFamilyBeforeTesting()
		logger.trace(surroundingMethodName + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val selectedFamily = families.findFirst [
				it.lastName.equals(LAST_NAME_1) && it.mother.firstName.equals(FIRST_MOM_1)
			]
			selectedFamily.mother.firstName = FIRST_MOM_2
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += #[DAD11, MOM21, SON11, DAU11]
		]
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Changes the firstname of a daughter of a {@link Family} and should edit the 
	 * fullname of the corresponding {@link Female} in the {@link PersonRegister}.
	 */
	@Test
	def void testChangeFirstNameDaughter() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		this.createFamilyBeforeTesting()
		logger.trace(surroundingMethodName + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val selectedFamily = families.findFirst [
				it.lastName.equals(LAST_NAME_1) && it.daughters.exists[it.firstName.equals(FIRST_DAU_1)]
			]
			val daughterToChange = selectedFamily.daughters.findFirst[it.firstName.equals(FIRST_DAU_1)]
			daughterToChange.firstName = FIRST_DAU_2
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += #[DAD11, MOM11, SON11, DAU21]
		]
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Tries to insert a new father into a {@link Family} which already has a father.
	 * In this scenario the user decides to discard the changes. Therefore all changes
	 * in the {@link FamilyRegister} should be reverted and the {@link PersonsRegister} 
	 * should not be edited. The rest of the family stays the same as well.
	 */
	@Test
	def void testInsertDadIfDadAlreadyExists_DiscardChanges() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		this.createFamilyBeforeTesting()
		logger.trace(surroundingMethodName + " - preparation done")
		userInteraction.addNextSingleSelection(0)
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val family = families.findFirst[it.lastName.equals(LAST_NAME_1)]
			family.father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_2]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
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
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
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
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		this.createFamilyBeforeTesting()
		logger.trace(surroundingMethodName + " - preparation done")
		userInteraction.addNextSingleSelection(1)
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val family = families.findFirst[it.lastName.equals(LAST_NAME_1)]
			family.father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_2]
		]
		logger.trace(surroundingMethodName + " - propagation done")
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
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Tries to insert a new father into a {@link Family} which already has a father.
	 * In this scenario the user decides to not replace the existing father but to insert
	 * the new father into a new family. This family will receive the same lastname but
	 * will not contain any of the other {@link Member}s from the old family, nor copies of them.
	 * Therefore changes concerning the old {@link Family} will be reverted.
	 */
	@Test
	def void testInsertDadIfDadAlreadyExists_MoveToNewFamily() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		this.createFamilyBeforeTesting()
		logger.trace(surroundingMethodName + " - preparation done")
		userInteraction.addNextSingleSelection(2)
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val family = families.findFirst[it.lastName.equals(LAST_NAME_1)]
			family.father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_2]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
			]
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_2]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += #[DAD11, DAD21, MOM11, SON11, DAU11]
		]
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Tries to insert a new mother into a {@link Family} which already has a mother.
	 * In this scenario the user decides to discard the changes. Therefore all changes
	 * in the {@link FamilyRegister} should be reverted and the {@link PersonsRegister} 
	 * should not be edited. The rest of the family stays the same as well.
	 */
	@Test
	def void testInsertMomIfMomAlreadyExists_DiscardChanges() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		this.createFamilyBeforeTesting()
		logger.trace(surroundingMethodName + " - preparation done")
		userInteraction.addNextSingleSelection(0)
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val family = families.findFirst[it.lastName.equals(LAST_NAME_1)]
			family.mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_2]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
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
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
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
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		this.createFamilyBeforeTesting()
		logger.trace(surroundingMethodName + " - preparation done")
		userInteraction.addNextSingleSelection(1)
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val family = families.findFirst[it.lastName.equals(LAST_NAME_1)]
			family.mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_2]
		]
		logger.trace(surroundingMethodName + " - propagation done")
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
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Tries to insert a new mother into a {@link Family} which already has a mother.
	 * In this scenario the user decides to not replace the existing mother but to insert
	 * the new mother into a new family. This family will receive the same lastname but
	 * will not contain any of the other {@link Member}s from the old family, nor copies of them.
	 * Therefore changes concerning the old {@link Family} will be reverted.
	 */
	@Test
	def void testInsertMomIfMomAlreadyExists_MoveToNewFamily() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		this.createFamilyBeforeTesting()
		logger.trace(surroundingMethodName + " - preparation done")
		userInteraction.addNextSingleSelection(2)
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val family = families.findFirst[it.lastName.equals(LAST_NAME_1)]
			family.mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_2]
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister => [
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				father = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAD_1]
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_1]
				sons += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_SON_1]
				daughters += FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_DAU_1]
			]
			families += FamiliesFactory.eINSTANCE.createFamily => [
				lastName = LAST_NAME_1
				mother = FamiliesFactory.eINSTANCE.createMember => [firstName = FIRST_MOM_2]
			]
		]
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister => [
			persons += #[DAD11, MOM11, MOM21, SON11, DAU11]
		]
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Deletes all {@link Family}s with matching lastname from the {@link FamilyRegister}.
	 * All {@link Member}s which were contained in these families will be deleted together
	 * with there corresponding {@link Person}s in the {@link PersonRegister} as well.
	 * If only families without members are deleted, the {@link PersonRegister} 
	 * will not be affected.
	 */
	@Test
	def void testDeleteAllFamiliesWithMatchingName() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		this.createFamilyBeforeTesting()
		logger.trace(surroundingMethodName + " - preparation done")
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			families.removeIf([it.lastName.equals(LAST_NAME_1)])
		]
		logger.trace(surroundingMethodName + " - propagation done")
		val FamilyRegister expectedFamilyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister()
		val PersonRegister expectedPersonRegister = PersonsFactory.eINSTANCE.createPersonRegister()
		assertCorrectFamilyRegister(expectedFamilyRegister)
		assertCorrectPersonRegister(expectedPersonRegister)
		logger.trace(surroundingMethodName + " - finished without errors")
	}

	/**Deletes the {@link FamilyRegister} with all its contents which leads to
	 * the deletion of the corresponding {@link PersonRegister} with all its contents.
	 */
	@Test
	def void testDeleteFamilyRegister() {
		val String surroundingMethodName = this.testInfo.getDisplayName()
		logger.trace(surroundingMethodName + " - begin")
		this.createFamilyBeforeTesting()
		logger.trace(surroundingMethodName + " - preparation done")
		resourceAt(FAMILIES_MODEL).propagate[contents.clear()]
		logger.trace(surroundingMethodName + " - propagation done")
		assertEquals(0, resourceAt(FAMILIES_MODEL).contents.size())
		assertEquals(0, resourceAt(PERSONS_MODEL).contents.size())
		assertThat(resourceAt(FAMILIES_MODEL), not(exists))
		assertThat(resourceAt(PERSONS_MODEL), not(exists))
		logger.trace(surroundingMethodName + " - finished without errors")
	}
}