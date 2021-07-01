package tools.vitruv.applications.demo.familiespersons.tests.families2Persons

import edu.kit.ipd.sdq.metamodels.families.FamiliesFactory
import edu.kit.ipd.sdq.metamodels.families.FamilyRegister
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tools.vitruv.domains.demo.families.FamiliesDomainProvider
import tools.vitruv.domains.demo.persons.PersonsDomainProvider

import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.testutils.matchers.ModelMatchers.exists
import tools.vitruv.testutils.VitruvApplicationTest
import tools.vitruv.testutils.domains.DomainUtil
import tools.vitruv.applications.demo.familiespersons.families2persons.FamiliesToPersonsChangePropagationSpecification
import edu.kit.ipd.sdq.metamodels.persons.PersonRegister
import static org.junit.jupiter.api.Assertions.assertEquals
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne
import java.nio.file.Path
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.common.util.TreeIterator
import org.apache.log4j.Logger

class FamiliesPersonsTest extends VitruvApplicationTest {
	static val logger = Logger.getLogger(FamiliesPersonsTest);
	//Es gibt auch noch einen ModelPrinting
	
	static String FAMILY_NAME = "Mustermann"
	static String FIRST_NAME_FATHER = "Anton"
	static String FIRST_NAME_MOTHER = "Berta"
	static String FIRST_NAME_SON = "Charlie"
	static String FIRST_NAME_DAUGHTER = "Dalia"
	static Path PERSONS_MODEL = DomainUtil.getModelFileName('model/persons', new PersonsDomainProvider)
	static Path FAMILIES_MODEL = DomainUtil.getModelFileName('model/families', new FamiliesDomainProvider)

	override protected getChangePropagationSpecifications() {
		return #[new FamiliesToPersonsChangePropagationSpecification()]
	}

	@Test
	def createRoot() {
		resourceAt(FAMILIES_MODEL).propagate[contents += FamiliesFactory.eINSTANCE.createFamilyRegister]
		
		insertNewFamily("Mueller");
		insertNewFamily("Meier");
		insertNewFamily("Schulze");
		insertDadInExistingFamily("Meier","Anton")
		
		/*resourceAt(Path.of(file.path)).startRecordingChanges => [
				contents += EObject.from(createFileURI(file.path))
			]
			propagate */
		System.out.println("\n\n===START===\n")
		System.out.println("PERSONS_MODEL: " + PERSONS_MODEL);
		resourceAt(PERSONS_MODEL).allContents.forEach[x,i | System.out.println(i + ": " + x.toString())]
		System.out.println("FAMILIES_MODEL: " + FAMILIES_MODEL);
		resourceAt(FAMILIES_MODEL).allContents.forEach[x,i | System.out.println(i + ": " + x.toString())]	
		System.out.println("\n===END===\n\n")
		
		assertThat(resourceAt(PERSONS_MODEL), exists)
		//equals deeply
		//Person
		
		//Klasse Modelmatcher -> 
		//HAMQuest
		//assertThat(resourceAt(PERSONS_MODEL), anton Meier ,contains)
	}

	private def createNewFamily(String familieName) {
		FamiliesFactory.eINSTANCE.createFamily => [
			lastName = familieName
		]
	}
	
	def void insertNewFamily(String familieName) {
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val family = createNewFamily(familieName)
			families += family
		]
	}
	
	def void insertDadInExistingFamily(String familieName, String dadsFirstName){
		FamilyRegister.from(FAMILIES_MODEL).propagate [
			val family = families.findFirst[x | x.lastName.equals(familieName)]
			family.father = FamiliesFactory.eINSTANCE.createMember => [
				firstName = dadsFirstName
				familyFather = family
			]
		]		
	}

//===BEGIN=REGION=TESTS======================================================================================

//	@Test
//	def void createContainertestDeleteFamilyRegister() {
//		ass
//	}
//	@Test
//	def void testDeleteFamilyRegister() {
//		System.out.println(PERSONS_MODEL);
//		System.out.println(FAMILIES_MODEL);
//	}
//
//	@Test
//	def void testCreateFamilyFather() {
//		FamilyRegister.from(FAMILIES_MODEL).propagate [
//			val family = insertNewFamily(FAMILY_NAME)
//			families += family => [
//				father = FamiliesFactory.eINSTANCE.createMember => [
//					firstName = FIRST_NAME_FATHER
//					familyFather = family
//				]
//			]
//		]
//
//		assertThat(resourceAt(PERSONS_MODEL), exists)
//	}
//
//	@Test
//	def void testCreateFamilySon() {
//		FamilyRegister.from(FAMILIES_MODEL).propagate [
//			val family = insertNewFamily(FAMILY_NAME)
//			families += family => [
//				sons += FamiliesFactory.eINSTANCE.createMember => [
//					firstName = FIRST_NAME_SON
//					familySon = family
//				]
//			]
//		]
//
//		assertThat(resourceAt(PERSONS_MODEL), exists)
//	}
//
//	@Test
//	def void testCreateFamilyMother() {
//		FamilyRegister.from(FAMILIES_MODEL).propagate [
//			val family = insertNewFamily(FAMILY_NAME)
//			families += family => [
//				mother = FamiliesFactory.eINSTANCE.createMember => [
//					firstName = FIRST_NAME_MOTHER
//					familyMother = family
//				]
//			]
//		]
//
//		assertThat(resourceAt(PERSONS_MODEL), exists)
//	}
//
//	@Test
//	def void testCreateFamilyDaughter() {
//		FamilyRegister.from(FAMILIES_MODEL).propagate [
//			val family = insertNewFamily(FAMILY_NAME)
//			families += family => [
//				daughters += FamiliesFactory.eINSTANCE.createMember => [
//					firstName = FIRST_NAME_DAUGHTER
//					familyDaughter = family
//				]
//			]
//		]
//
//		assertThat(resourceAt(PERSONS_MODEL), exists)
//	}
//
//	@Test
//	def void testDeleteMember() {
//		FamilyRegister.from(FAMILIES_MODEL).propagate [
//			val family = insertNewFamily(FAMILY_NAME)
//			families += family => [
//				daughters += FamiliesFactory.eINSTANCE.createMember => [
//					firstName = FIRST_NAME_DAUGHTER
//					familyDaughter = family
//				]
//			]
//		]
//		FamilyRegister.from(FAMILIES_MODEL).propagate [
//			families.get(0).daughters.remove(0)
//		]
//
//		assertThat(resourceAt(PERSONS_MODEL), exists)
//	}
//
//	@Test
//	def void testChangeFirstName() {
//		val daughter = FamiliesFactory.eINSTANCE.createMember
//		FamilyRegister.from(FAMILIES_MODEL).propagate [
//			val family = insertNewFamily(FAMILY_NAME)
//			families += family => [
//				daughters += daughter => [
//					firstName = FIRST_NAME_DAUGHTER
//				]
//			]
//		]
//
//		FamilyRegister.from(FAMILIES_MODEL).families.claimOne.daughters.claimOne.propagate [
//			firstName = FIRST_NAME_MOTHER
//		]
//		val personsWithMothersName = PersonRegister.from(PERSONS_MODEL).persons.filter [
//			fullName.split(" ").get(0) == FIRST_NAME_MOTHER
//		]
//		assertEquals(1, personsWithMothersName.length)
//	}
//
//	@Test
//	def void testChangeLastName() {
//	}
//=====END=REGION=TESTS======================================================================================
}
