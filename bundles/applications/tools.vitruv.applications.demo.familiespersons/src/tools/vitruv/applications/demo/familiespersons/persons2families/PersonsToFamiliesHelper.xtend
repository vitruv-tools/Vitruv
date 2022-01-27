package tools.vitruv.applications.demo.familiespersons.persons2families

import edu.kit.ipd.sdq.metamodels.families.Family
import tools.vitruv.framework.userinteraction.UserInteractor
import tools.vitruv.framework.userinteraction.UserInteractionOptions.WindowModality
import edu.kit.ipd.sdq.metamodels.families.FamilyRegister
import edu.kit.ipd.sdq.metamodels.persons.Person
import edu.kit.ipd.sdq.metamodels.persons.Male
import java.util.ArrayList
import java.util.Collection

class PersonsToFamiliesHelper {
	new() {}
	
	def static String stringifyFamily(Family family){
		var returnString = family.lastName + ": "
		if (family.father !== null) { returnString += "F: " + family.father.firstName + ";" }
		if (family.mother !== null) { returnString += "M: " + family.mother.firstName + ";" }
		if (family.sons !== null && family.sons.size > 0) { returnString += family.sons.join("S: (", ", ", ")", [it.firstName]) }
		if (family.daughters !== null && family.daughters.size > 0) { returnString += family.daughters.join("D: (", ", ", ")", [it.firstName]) }
		return returnString
	}
	
	def static boolean askForParentOrChild(UserInteractor userInteractor){
		var String parentOrChildMessage = "You are about to insert a new member into the family register."
			parentOrChildMessage += " Is this member a parent of a family or a child?"
			var Iterable<String> parentOrChildOptions = #["Parent", "Child"]
			val int parentOrChildSelection = userInteractor
				.singleSelectionDialogBuilder				
				.message(parentOrChildMessage)
				.choices(parentOrChildOptions)
				.title("Parent or Child?")
				.windowModality(WindowModality.MODAL)
				.startInteraction()
			return parentOrChildSelection == parentOrChildOptions.toList.indexOf("Child")
	}
	
	def static Iterable<Family> getMatchingFamilies(Person newPerson, FamilyRegister familyRegister, boolean insertChild){
		
		val String newPersonLastname = newPerson.fullName.split(" ").last		
		var Iterable<Family> matchingFamilies = familyRegister.families.filter[it.lastName.equals(newPersonLastname)]
		if (!insertChild) {
			if (newPerson instanceof Male) {
				matchingFamilies = matchingFamilies.filter[it.father === null]					
			} else {
				matchingFamilies = matchingFamilies.filter[it.mother === null]
			}
		}
		return matchingFamilies
	}
	
	def static Family askForFamilySelection(UserInteractor userInteractor, Person newPerson, Iterable<Family> selectableFamilies) {
		// Let user select the family
		var String whichFamilyMessage = "Please choose whether you want to create a new family or insert " 
			+ newPerson.fullName + " into one of the existing families."
		
		// Prepare options to select from 
		val Collection<String> whichFamilyOptions = new ArrayList<String>()
		whichFamilyOptions.^add("insert in a new family")
		selectableFamilies.forEach[whichFamilyOptions.^add(stringifyFamily(it))]				
		
		// Start interaction
		val whichFamilyIndex = userInteractor
			.singleSelectionDialogBuilder
			.message(whichFamilyMessage)
			.choices(whichFamilyOptions)
			.title("New or Existing Family?")					
			.windowModality(WindowModality.MODAL)
			.startInteraction()
			
		return if (whichFamilyIndex === 0) null else selectableFamilies.get(whichFamilyIndex-1)		
	}
}