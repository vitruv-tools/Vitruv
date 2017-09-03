package tools.vitruv.dsls.commonalities.generator

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.commonalities.language.ParticipationClass

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

@Utility
class ReactionsGeneratorConventions {
	def static package String correspondingVariableName(ParticipationClass participationClass) '''
	corresponding_«participationClass.name»'''

	def static package String getCorrespondenceTag(ParticipationClass participationClass) {
		val commonalityFile = participationClass.containingCommonalityFile
		val commonality = commonalityFile.commonality
		'''«commonalityFile.concept.name».«commonality.name»/«participationClass.participation.name».«participationClass.name»'''
	}
}
