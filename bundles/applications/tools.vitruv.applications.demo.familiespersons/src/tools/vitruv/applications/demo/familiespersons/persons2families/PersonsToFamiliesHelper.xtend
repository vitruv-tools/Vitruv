package tools.vitruv.applications.demo.familiespersons.persons2families

import edu.kit.ipd.sdq.activextendannotations.Utility
import edu.kit.ipd.sdq.metamodels.families.Family
import edu.kit.ipd.sdq.metamodels.persons.Male
import edu.kit.ipd.sdq.metamodels.persons.Person
import edu.kit.ipd.sdq.metamodels.persons.PersonRegister
import java.util.ArrayList
import java.util.Collection
import tools.vitruv.framework.userinteraction.UserInteractionOptions.WindowModality
import tools.vitruv.framework.userinteraction.UserInteractor

@Utility
class PersonsToFamiliesHelper {
	private new() {}
	
	/** @return the eContainer of a person casted as PersonRegister, if it actually is one.
	 *  @return null, else.
	 */
	def static PersonRegister getRegister(Person person) {
		return if (person.eContainer instanceof PersonRegister)	person.eContainer as PersonRegister else null
	}
	
	def static String stringifyFamily(Family family) {
		val StringBuilder builder = new StringBuilder().append(family.lastName).append(": ")
		if (family.father !== null) { builder.append("F: ").append(family.father.firstName).append(";") }
		if (family.mother !== null) { builder.append("M: ").append(family.mother.firstName).append(";") }
		if (family.sons !== null && family.sons.size > 0) { builder.append(family.sons.join("S: (", ", ", ")", [it.firstName])) }
		if (family.daughters !== null && family.daughters.size > 0) { builder.append(family.daughters.join("D: (", ", ", ")", [it.firstName])) }
		return builder.toString()
	}
	
	def static String getFirstname(Person person) {		 
		val Iterable<String> nameParts = person.fullName.split(" ")
		var String firstName = null
		if (nameParts.size() == 1) {
			firstName = person.fullName
		} else {
			firstName = nameParts.take(nameParts.size() - 1).join(" ")
		}
		return firstName
	}
	
	def static String getLastname(Person person) {
		if (!person.fullName.contains(" ")) {
			return ""
		} else {
			return person.fullName.split(" ").last
		}
	}
	
	/** Sets up an user interaction to ask the user if he would like to create a parent or a child in the families model
	 *  @return true <==> new member is meant to be a child
	 *  @return false <==> new member is meant to be a parent
	 */
	def static boolean doesUserPreferChildOverParent(UserInteractor userInteractor, Person newPerson) {
		
		val StringBuilder parentOrChildMessageBuilder = new StringBuilder()
			.append("You have inserted ")
			.append(newPerson.fullName)
			.append(" into the persons register which results into the creation of a corresponding member into the family register.")
			.append(" Is this member supposed to be a parent or a child in the family register?")
		
		var Iterable<String> parentOrChildOptions = #["Parent", "Child"]
		
		val int parentOrChildSelection = userInteractor
			.singleSelectionDialogBuilder				
			.message(parentOrChildMessageBuilder.toString())
			.choices(parentOrChildOptions)
			.title("Parent or Child?")
			.windowModality(WindowModality.MODAL)
			.startInteraction()
		
		return parentOrChildSelection == parentOrChildOptions.toList.indexOf("Child")
	}
	
	/** Sets up an user interaction to ask the user if he would like to create a parent or a child in the families model
	 *  @return true <==> new member is meant to be a child
	 *  @return false <==> new member is meant to be a parent
	 */
	def static boolean doesUserPreferChildOverParentDuringRenaming(UserInteractor userInteractor, String oldFullname, String newFullname, boolean wasChildBefore) {
		
		val StringBuilder parentOrChildMessageBuilder = new StringBuilder()
			.append("You have renamed ")
			.append(oldFullname)
			.append(" to ")
			.append(newFullname)
			.append(", which might cause the corresponding member in the families model to change its position inside a family.")
			.append(" Which position should this member, who was a ")
			.append(if (wasChildBefore) "child" else "parent")
			.append(" before, have after the renaming?")
		
		var Iterable<String> parentOrChildOptions = #["Parent", "Child"]
		
		val int parentOrChildSelection = userInteractor
			.singleSelectionDialogBuilder				
			.message(parentOrChildMessageBuilder.toString())
			.choices(parentOrChildOptions)
			.title("Parent or Child?")
			.windowModality(WindowModality.MODAL)
			.startInteraction()
		
		return parentOrChildSelection == parentOrChildOptions.toList.indexOf("Child")
	}
	
	/** @param person  Person whose last name is used for the filter. 
	 *  @return Lambda to filter out all families whose last name is different from the newPerson's.
	 */
	def static (Family)=>boolean sameLastname(Person person) {				
		val String newPersonLastname = person.lastname		
		return [Family p|p.lastName.equals(newPersonLastname)]
	}
	
	/** @param person  Person whose sex determines whether families with father or with mother are filtered out. 
	 *  @return Lambda to filter out all families which already have a parent with the same sex as the newPerson.
	 */
	def static (Family)=>boolean noParent(Person person) {
		return [Family p| if(person instanceof Male) p.father === null else p.mother === null]
	}
	
	/** Sets up an user interaction to ask the user if he wants to insert the corresponding member to the {@code newPerson}
	 *  either into a new family or into one of the {@code selectableFamilies} and if so, in which. 
	 *  @return chosen family, if existing family was selected
	 *  @return null, if users wants to create and insert into a new family 
	 */
	def static Family askUserWhichFamilyToInsertTheMemberIn(UserInteractor userInteractor, Person newPerson, Iterable<Family> selectableFamilies) {
		// Let user select the family
		var StringBuilder whichFamilyMessageBuilder = new StringBuilder()
			.append("Please choose whether you want to create a new family or insert ")
			.append(newPerson.fullName)
			.append(" into one of the existing families.")
		
		// Prepare options to select from 
		val Collection<String> whichFamilyOptions = new ArrayList<String>()
		whichFamilyOptions.^add("insert in a new family")
		whichFamilyOptions.addAll(selectableFamilies.map[stringifyFamily(it)])				
		
		// Start interaction
		val whichFamilyIndex = userInteractor
			.singleSelectionDialogBuilder
			.message(whichFamilyMessageBuilder.toString())
			.choices(whichFamilyOptions)
			.title("New or Existing Family?")					
			.windowModality(WindowModality.MODAL)
			.startInteraction()
			
		return if (whichFamilyIndex === 0) null else selectableFamilies.get(whichFamilyIndex-1)		
	}
}