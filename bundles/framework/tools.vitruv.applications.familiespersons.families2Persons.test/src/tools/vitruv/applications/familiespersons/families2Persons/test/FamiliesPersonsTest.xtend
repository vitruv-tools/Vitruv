package tools.vitruv.applications.familiespersons.families2Persons.test

import edu.kit.ipd.sdq.metamodels.families.FamiliesFactory
import org.junit.Test

class FamiliesPersonsTest extends AbstractFamiliesToPersonsTest{
	private static val FAMILY_NAME = "Mustermann";
	private static val FIRST_NAME = "Max";
	private static val PERSONS_PATH = "model/persons.person";

	@Test
	public def void testCreateFamilyMember() {
		val family = FamiliesFactory.eINSTANCE.createFamily();
		family.lastName = FAMILY_NAME;
		rootElement.families.add(family);
		val member = FamiliesFactory.eINSTANCE.createMember();
		member.firstName = FIRST_NAME;
		member.familyFather = family;
		saveAndSynchronizeChanges(family);
		saveAndSynchronizeChanges(member);
		assertModelExists(PERSONS_PATH);
	}
}
