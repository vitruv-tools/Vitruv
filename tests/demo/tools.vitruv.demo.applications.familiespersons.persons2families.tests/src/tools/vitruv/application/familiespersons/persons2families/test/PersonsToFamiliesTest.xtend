package tools.vitruv.application.familiespersons.persons2families.test

import org.junit.Test
import edu.kit.ipd.sdq.metamodels.persons.PersonsFactory
import edu.kit.ipd.sdq.metamodels.families.Member
import static org.junit.Assert.*


class PersonsToFamiliesTest extends AbstractPersonsToFamiliesTest{
	static val MALE_PERSON_NAME = "Max Mustermann";
	static val SECOND_MALE_PERSON_NAME = "Bernd Mustermann";
	static val FEMALE_PERSON_NAME = "Erika Mustermann";
	static val FAMILIES_PATH = "model/families.families";

	@Test
	def void testCreateMalePerson() {
		val malePerson = PersonsFactory.eINSTANCE.createMale();
		malePerson.fullName = MALE_PERSON_NAME;
		rootElement.persons.add(malePerson);
		saveAndSynchronizeChanges(malePerson);
		assertModelExists(FAMILIES_PATH);
	}
	
	@Test
	def void testCreatePersonRegister(){
		val personRegister = PersonsFactory.eINSTANCE.createPersonRegister
		saveAndSynchronizeChanges(personRegister);
		assertModelExists(FAMILIES_PATH);
	}
	
	@Test
	def void testDeletePersonRegister(){
		
	}
	
	@Test
	def void testCreateMale(){
		val person = PersonsFactory.eINSTANCE.createMale
		person.fullName = MALE_PERSON_NAME
		rootElement.persons.add(person)
		saveAndSynchronizeChanges(person)
		assertModelExists(FAMILIES_PATH)
	}
	
	@Test
	def void testCreateFemale(){
		val person = PersonsFactory.eINSTANCE.createFemale
		person.fullName = FEMALE_PERSON_NAME
		rootElement.persons.add(person)
		saveAndSynchronizeChanges(person)
		assertModelExists(FAMILIES_PATH)
	}
	
	@Test
	def void testChangeFirstName(){
		val person = PersonsFactory.eINSTANCE.createMale
		person.fullName = MALE_PERSON_NAME
		rootElement.persons.add(person)
		saveAndSynchronizeChanges(person)
		person.fullName = SECOND_MALE_PERSON_NAME
		saveAndSynchronizeChanges(person)
		val Iterable<Member> members = correspondenceModel.getCorrespondingEObjects(#[person]).filter(Member)
		assertEquals(members.length, 1)
		val member = members.get(0)
		assertEquals(member.firstName, person.fullName.split(" ").get(0))
	}
}