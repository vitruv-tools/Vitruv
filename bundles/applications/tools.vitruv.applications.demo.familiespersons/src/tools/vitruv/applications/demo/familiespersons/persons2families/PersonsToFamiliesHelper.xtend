package tools.vitruv.applications.demo.familiespersons.persons2families

import edu.kit.ipd.sdq.metamodels.families.Family
import tools.vitruv.framework.userinteraction.UserInteractor
import tools.vitruv.framework.userinteraction.UserInteractionOptions.WindowModality
import edu.kit.ipd.sdq.metamodels.families.FamilyRegister
import edu.kit.ipd.sdq.metamodels.persons.Person
import edu.kit.ipd.sdq.metamodels.persons.Male
import java.util.ArrayList
import java.util.Collection
import edu.kit.ipd.sdq.activextendannotations.Utility

@Utility
class PersonsToFamiliesHelper {
	private new() {}
	
	def static String stringifyFamily(Family family) {
		val StringBuilder builder = new StringBuilder()
		builder.append(family.lastName + ": ")
		if (family.father !== null) { builder.append("F: " + family.father.firstName + ";") }
		if (family.mother !== null) { builder.append("M: " + family.mother.firstName + ";") }
		if (family.sons !== null && family.sons.size > 0) { builder.append(family.sons.join("S: (", ", ", ")", [it.firstName])) }
		if (family.daughters !== null && family.daughters.size > 0) { builder.append(family.daughters.join("D: (", ", ", ")", [it.firstName])) }
		return builder.toString()
	}
	
	/** Sets up an user interaction to ask the user if he would like to create a parent or a child in the families model
	 *  Returns true <==> new member is meant to be a child
	 *  Returns false <==> new member is meant to be a parent
	 */
	def static boolean doesUserPreferChildOverParent(UserInteractor userInteractor, Person newPerson) {
		
		val StringBuilder parentOrChildMessageBuilder = new StringBuilder()
		parentOrChildMessageBuilder.append("You have inserted ")
		parentOrChildMessageBuilder.append(newPerson.fullName)
		parentOrChildMessageBuilder.append(" into the persons register which results into the creation of a corresponding member into the family register.")
		parentOrChildMessageBuilder.append(" Is this member supposed to be a parent or a child in the family register?")
		
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
	
	/** Returns all families from the {@code familyRegister} with the same last name as the {@code newPerson}.
	 */
	def static Iterable<Family> getMatchingFamilies(FamilyRegister familyRegister, Person newPerson) {		
		val String newPersonLastname = newPerson.fullName.split(" ").last		
		return familyRegister.families.filter[it.lastName.equals(newPersonLastname)]
	}
	
	/** Returns all families from the {@code unfilteredFamilies} which have a parent of the same sex as the {@code newPerson}.
	 */
	def static Iterable<Family> getFamiliesWithoutParent(Iterable<Family> unfilteredFamilies, Person newPerson) {		
		return unfilteredFamilies.filter[if(newPerson instanceof Male) it.father === null else it.mother === null]
	}
	
	/** Sets up an user interaction to ask the user if he wants to insert the corresponding member to the {@code newPerson}
	 *  either into a new family or into one of the {@code selectableFamilies} and if so, in which. 
	 */
	def static Family askUserWhichFamilyToInsertTheMemberIn(UserInteractor userInteractor, Person newPerson, Iterable<Family> selectableFamilies) {
		// Let user select the family
		var StringBuilder whichFamilyMessageBuilder = new StringBuilder()
		whichFamilyMessageBuilder.append("Please choose whether you want to create a new family or insert ")
		whichFamilyMessageBuilder.append(newPerson.fullName)
		whichFamilyMessageBuilder.append(" into one of the existing families.")
		
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