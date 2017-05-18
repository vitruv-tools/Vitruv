package tools.vitruv.application.familiespersons.persons2families.test

import org.junit.Test
import edu.kit.ipd.sdq.metamodels.persons.PersonsFactory

class PersonsToFamiliesTest extends AbstractPersonsToFamiliesTest{
	private static val MALE_PERSON_NAME = "Max Mustermann";
	private static val FAMILIES_PATH = "model/families.families";

	@Test
	public def void testCreateMalePerson() {
		val malePerson = PersonsFactory.eINSTANCE.createMale();
		malePerson.fullName = MALE_PERSON_NAME;
		rootElement.persons.add(malePerson);
		saveAndSynchronizeChanges(malePerson);
		assertModelExists(FAMILIES_PATH);
	}
}