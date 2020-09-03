package tools.vitruv.applications.familiespersons.families2Persons.test

import edu.kit.ipd.sdq.metamodels.families.FamiliesFactory
import org.junit.Test
import tools.vitruv.framework.correspondence.CorrespondenceModelUtil
import static org.junit.Assert.*
import edu.kit.ipd.sdq.metamodels.persons.Person

class FamiliesPersonsTest extends AbstractFamiliesToPersonsTest {
	static val FAMILY_NAME = "Mustermann";
	static val FIRST_NAME_FATHER = "Max";
	static val FIRST_NAME_SON = "Sohn";
	static val FIRST_NAME_DAUGHTER = "Tochter";
	static val FIRST_NAME_MOTHER = "Erika"
	static val PERSONS_PATH = "model/persons.persons";

	@Test
	def void testCreateFamilyRegister() {
		val familyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister
		saveAndSynchronizeChanges(familyRegister);
		assertModelExists(PERSONS_PATH);
	}

	@Test
	def void testDeleteFamilyRegister() {
	}

	@Test
	def void testCreateFamilyFather() {
		val family = FamiliesFactory.eINSTANCE.createFamily();
		family.lastName = FAMILY_NAME;
		rootElement.families.add(family);
		val member = FamiliesFactory.eINSTANCE.createMember();
		member.firstName = FIRST_NAME_FATHER;
		member.familyFather = family;
		family.father = member;
		saveAndSynchronizeChanges(family);
		saveAndSynchronizeChanges(member);
		assertModelExists(PERSONS_PATH);
	}

	@Test
	def void testCreateFamilySon() {
		val family = FamiliesFactory.eINSTANCE.createFamily();
		family.lastName = FAMILY_NAME;
		rootElement.families.add(family);
		val member = FamiliesFactory.eINSTANCE.createMember();
		member.firstName = FIRST_NAME_SON;
		member.familySon = family;
		family.sons.add(member);
		saveAndSynchronizeChanges(family);
		saveAndSynchronizeChanges(member);
		assertModelExists(PERSONS_PATH);
	}

	@Test
	def void testCreateFamilyMother() {
		val family = FamiliesFactory.eINSTANCE.createFamily();
		family.lastName = FAMILY_NAME;
		rootElement.families.add(family);
		val member = FamiliesFactory.eINSTANCE.createMember();
		member.firstName = FIRST_NAME_MOTHER;
		member.familyMother = family;
		family.mother = member;
		saveAndSynchronizeChanges(family);
		saveAndSynchronizeChanges(member);
		assertModelExists(PERSONS_PATH);
	}

	@Test
	def void testCreateFamilyDaughter() {
		val family = FamiliesFactory.eINSTANCE.createFamily();
		family.lastName = FAMILY_NAME;
		rootElement.families.add(family);
		val member = FamiliesFactory.eINSTANCE.createMember();
		member.firstName = FIRST_NAME_DAUGHTER;
		member.familyDaughter = family;
		family.daughters.add(member);
		saveAndSynchronizeChanges(family);
		saveAndSynchronizeChanges(member);
		assertModelExists(PERSONS_PATH);
	}

	@Test
	def void testDeleteMember() {
		val family = FamiliesFactory.eINSTANCE.createFamily();
		family.lastName = FAMILY_NAME;
		rootElement.families.add(family);
		val member = FamiliesFactory.eINSTANCE.createMember();
		member.firstName = FIRST_NAME_DAUGHTER;
		member.familyDaughter = family;
		family.daughters.add(member);
		saveAndSynchronizeChanges(family);
		saveAndSynchronizeChanges(member);

		family.daughters.remove(member);
		saveAndSynchronizeChanges(family);
		assertModelNotExists(PERSONS_PATH)
	}

	@Test
	def void testChangeFirstName() {
		val family = FamiliesFactory.eINSTANCE.createFamily();
		family.lastName = FAMILY_NAME;
		rootElement.families.add(family);
		val member = FamiliesFactory.eINSTANCE.createMember();
		member.firstName = FIRST_NAME_DAUGHTER;
		member.familyDaughter = family;
		family.daughters.add(member);
		saveAndSynchronizeChanges(family);
		saveAndSynchronizeChanges(member);
		member.firstName = FIRST_NAME_MOTHER
		val Iterable<Person> corrObjs = CorrespondenceModelUtil.getCorrespondingEObjects(this.correspondenceModel, member).filter(Person);
		assertEquals(corrObjs.length, 1)
		val corrMember = corrObjs.get(0)
		assertEquals(corrMember.fullName.split(" ").get(0), FIRST_NAME_MOTHER)
	}

	@Test
	def void testChangeLastName() {
	}
}
