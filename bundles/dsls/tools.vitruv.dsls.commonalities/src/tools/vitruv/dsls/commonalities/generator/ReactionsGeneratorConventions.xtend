package tools.vitruv.dsls.commonalities.generator

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.commonalities.language.CommonalityAttribute
import tools.vitruv.dsls.commonalities.language.CommonalityAttributeMapping
import tools.vitruv.dsls.commonalities.language.CommonalityReference
import tools.vitruv.dsls.commonalities.language.CommonalityReferenceMapping
import tools.vitruv.dsls.commonalities.language.ParticipationAttribute
import tools.vitruv.dsls.commonalities.language.ParticipationClass

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

@Utility
package class ReactionsGeneratorConventions {
	def static package String correspondingVariableName(ParticipationClass participationClass) '''
	corresponding_«participationClass.name»'''

	def static package String getCorrespondenceTag(ParticipationClass participationClass) {
		val commonalityFile = participationClass.containingCommonalityFile
		val commonality = commonalityFile.commonality
		'''«commonalityFile.concept.name».«commonality.name»/«participationClass.participation.name».«participationClass.name»'''
	}
	
	def static package String getParticipationAttributeReactionName(CommonalityAttributeMapping mapping) {
		mappingAttributeReactionName(mapping.attribute)
	}
	
	def static package String getParticipationAttributeReactionName(CommonalityReferenceMapping mapping) {
		mappingAttributeReactionName(mapping.reference)
	}
	
	def static private String mappingAttributeReactionName(ParticipationAttribute attribute) {
		'''«attribute.participationClass.name.toFirstUpper»«attribute.attribute.name.toFirstUpper»'''
	}
	
	def static package String getCommonalityAttributeReactionName(CommonalityAttribute attribute) {
		'''«attribute.containingCommonalityFile.commonality.name.toFirstUpper»«attribute.name.toFirstUpper»'''
	}
	
	def static package String getCommonalityAttributeReactionName(CommonalityReference reference) {
		'''«reference.containingCommonalityFile.commonality.name.toFirstUpper»«reference.name.toFirstUpper»'''
	}
}
