package tools.vitruv.applications.demo.familiespersons.families2persons

import edu.kit.ipd.sdq.activextendannotations.Utility
import edu.kit.ipd.sdq.metamodels.families.Family
import edu.kit.ipd.sdq.metamodels.families.Member
import static extension tools.vitruv.domains.demo.families.FamiliesUtil.getFamily

@Utility
class FamiliesToPersonsHelper {
	private new() {}
	
	/** Returns the name of a Person corresponding to a Member. By convention, Persons whose fullname
	 *  do not contain a space (" ") to separate firstname and lastname are inserted into a Family with
	 *  an empty lastname. Therefore, Persons have to be named accordingly to keep both models consistent.
	 *     
	 *  @param member Member from which a corresponding Person should be given a correct name.
	 *  @return Name that the corresponding Person from the persons model should have.
	 */
	def static String getPersonName(Member member) {
		val Family family = member.family
		val StringBuilder personNameBuilder = new StringBuilder(member.firstName)			
		if (!family.lastName.equals("")) {
			personNameBuilder.append(" ").append(family.lastName)
		}
		return personNameBuilder.toString()			 
	}
}