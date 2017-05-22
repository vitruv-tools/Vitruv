package tools.vitruv.applications.familiespersons.families2Persons.test

import edu.kit.ipd.sdq.metamodels.families.FamiliesFactory
import org.junit.Test

class FamiliesPersonsTest extends AbstractFamiliesToPersonsTest {
	private static val FAMILY_NAME = "Mustermann";
	private static val FIRST_NAME_FATHER = "Max";
	private static val FIRST_NAME_SON = "Sohn";
	private static val FIRST_NAME_DAUGHTER = "Tochter";
	private static val FIRST_NAME_MOTHER = "Erika"
	private static val PERSONS_PATH = "model/persons.person";

	@Test
	public def void testCreateFamilyRegister() {
		val familyRegister = FamiliesFactory.eINSTANCE.createFamilyRegister
		saveAndSynchronizeChanges(familyRegister);
		assertModelExists(PERSONS_PATH);
	}

	@Test
	public def void testCreateFamilyFather() {
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
	public def void testCreateFamilyMother() {
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
	public def void testCreateFamilySon() {
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
	public def void testCreateFamilyDaughter() {
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
}
