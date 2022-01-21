package tools.vitruv.applications.demo.familiespersons.persons2families

import edu.kit.ipd.sdq.metamodels.families.Family

class PersonsToFamiliesHelper {
	new() {}
	
	def static String stringifyFamily(Family family){
		var returnString = family.lastName + ": "
		if (family.father !== null) { returnString += "F: " + family.father.firstName + ";" }
		if (family.mother !== null) { returnString += "M: " + family.mother.firstName + ";" }
		if (family.sons !== null && family.sons.size > 0) { returnString += family.sons.join("S: (", ", ", ")", [it.firstName]) }
		if (family.daughters !== null && family.daughters.size > 0) { returnString += family.daughters.join("S: (", ", ", ")", [it.firstName]) }
		return returnString
	}
}