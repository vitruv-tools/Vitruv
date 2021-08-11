package tools.vitruv.domains.demo.families

import edu.kit.ipd.sdq.activextendannotations.Utility
import edu.kit.ipd.sdq.metamodels.families.Member
import edu.kit.ipd.sdq.metamodels.families.Family
import java.util.List

@Utility
class FamiliesUtil {
	def static getFamily(Member member) {
		return member.familyDaughter  ?: member.familySon ?: member.familyMother ?: member.familyFather 
	}
	
	def static getMembers(Family family) {
		return (family.daughters + family.sons + List.of(family.mother) + List.of(family.father)).filterNull.toList 
	}
}