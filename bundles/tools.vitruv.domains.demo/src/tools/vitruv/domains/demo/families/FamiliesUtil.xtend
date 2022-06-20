package tools.vitruv.domains.demo.families

import edu.kit.ipd.sdq.activextendannotations.Utility
import edu.kit.ipd.sdq.metamodels.families.Family
import edu.kit.ipd.sdq.metamodels.families.FamilyRegister
import edu.kit.ipd.sdq.metamodels.families.Member
import java.util.ArrayList
import java.util.Collection

@Utility
class FamiliesUtil {
	
	def static boolean isChild(Member member) {
		return (member.familySon ?: member.familyDaughter) !== null
	}
		
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
	
	/** Returns the eContainer of a Family casted as FamilyRegister, if it is contained in a FamilyRegister.
	 *  @param family The family to obtain the FamilyRegister from.
	 *  @return <code>family.eContainer</code> as FamilyRegister, if it actually is one;
	 *  @throws UnsupportedOperationException, if <code>family.eContainer</code> is not a FamilyRegister 
	 *  to indicate that the case of eContainers of other types is case is not implemented/ supported yet.  
	 */
	def static FamilyRegister getRegister(Family family) {
		if (family.eContainer instanceof FamilyRegister) {
			return family.eContainer as FamilyRegister
		} else {
			throw new UnsupportedOperationException("The reaction is only implemented for the case of a FamilyRegister being the eContainer of a Family.") 
		}
	}
}