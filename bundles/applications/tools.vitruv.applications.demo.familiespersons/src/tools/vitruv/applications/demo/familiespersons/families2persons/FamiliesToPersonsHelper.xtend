package tools.vitruv.applications.demo.familiespersons.families2persons

import edu.kit.ipd.sdq.metamodels.families.Family
import edu.kit.ipd.sdq.metamodels.families.Member
import edu.kit.ipd.sdq.metamodels.families.FamilyRegister
import java.util.Collection
import java.util.ArrayList

class FamiliesToPersonsHelper {
	new() {}
	
	def static Family getFamily(Member member) {
		return member.familyFather ?: member.familyMother ?: member.familySon ?: member.familyDaughter
	}
	
	def static Iterable<Member> getMembers(Family family) {
		val Collection<Member> members = new ArrayList<Member>
		if (family.father !== null) {
			members.^add(family.father)
		}
		if (family.mother !== null) {
			members.^add(family.mother)
		}
		members.addAll(family.sons)
		members.addAll(family.daughters)
		return members
	}
	
	def static FamilyRegister getRegister(Member member) {
		return member.getFamily().getRegister()
	}
	
	def static FamilyRegister getRegister(Family family) {
		return family.eContainer as FamilyRegister
	}
}