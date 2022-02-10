package tools.vitruv.domains.demo.families

import edu.kit.ipd.sdq.activextendannotations.Utility
import edu.kit.ipd.sdq.metamodels.families.Family
import edu.kit.ipd.sdq.metamodels.families.FamilyRegister
import edu.kit.ipd.sdq.metamodels.families.Member
import java.util.ArrayList
import java.util.Collection

@Utility
final class FamiliesUtil {
	private new () {}
		
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
		return member.family.register
	}
	
	/** Returns the eContainer of a Family casted as FamilyRegister if it actually is one.
	 *  Returns null if the Family is contained in any other type of eContainer. 
	 */
	def static FamilyRegister getRegister(Family family) {
		return if (family.eContainer instanceof FamilyRegister)	family.eContainer as FamilyRegister else null
	}
}