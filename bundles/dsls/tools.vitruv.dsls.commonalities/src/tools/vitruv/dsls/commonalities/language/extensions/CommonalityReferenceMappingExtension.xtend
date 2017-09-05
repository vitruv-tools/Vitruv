package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.commonalities.language.CommonalityReference
import tools.vitruv.dsls.commonalities.language.CommonalityReferenceMapping
import tools.vitruv.dsls.commonalities.language.ReferenceEqualitySpecification
import tools.vitruv.dsls.commonalities.language.ReferenceReadSpecification
import tools.vitruv.dsls.commonalities.language.ReferenceSetSpecification

import static extension tools.vitruv.dsls.commonalities.language.extensions.ParticipationClassExtension.*

@Utility class CommonalityReferenceMappingExtension {
	def static dispatch isRead(ReferenceReadSpecification referenceMappingSpecification) {
		true
	}
	
	def static dispatch isRead(ReferenceEqualitySpecification referenceMappingSpecification) {
		true
	}
	
	def static dispatch isRead(ReferenceSetSpecification referenceMappingSpecification) {
		false
	}
	
	def static dispatch isWrite(ReferenceReadSpecification referenceMappingSpecification) {
		false
	}
	
	def static dispatch isWrite(ReferenceEqualitySpecification referenceMappingSpecification) {
		true
	}
	
	def static dispatch isWrite(ReferenceSetSpecification referenceMappingSpecification) {
		true
	}
	
	def static getParticipation(CommonalityReferenceMapping mapping) {
		mapping.reference.participationClass.participation
	}
	
	def static getDeclaringReference(CommonalityReferenceMapping mapping) {
		val result = mapping.eContainer
		if (result instanceof CommonalityReference) {
			return result
		}
		throw new IllegalStateException('''Found the «CommonalityReferenceMapping.simpleName» ‹«mapping»› «
		»not inside a «CommonalityReference.simpleName»!''')
	}
	
}