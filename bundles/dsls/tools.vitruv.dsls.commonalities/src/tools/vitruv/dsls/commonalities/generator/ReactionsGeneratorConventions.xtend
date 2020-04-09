package tools.vitruv.dsls.commonalities.generator

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.commonalities.generator.ParticipationContextHelper.ParticipationContext
import tools.vitruv.dsls.commonalities.language.Commonality
import tools.vitruv.dsls.commonalities.language.CommonalityAttribute
import tools.vitruv.dsls.commonalities.language.CommonalityAttributeMapping
import tools.vitruv.dsls.commonalities.language.CommonalityReference
import tools.vitruv.dsls.commonalities.language.CommonalityReferenceMapping
import tools.vitruv.dsls.commonalities.language.ParticipationAttribute
import tools.vitruv.dsls.commonalities.language.ParticipationClass

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

@Utility
package class ReactionsGeneratorConventions {

	public static val INTERMEDIATE = 'intermediate'
	public static val REFERENCED_INTERMEDIATE = 'referencedIntermediate'
	public static val REFERENCING_INTERMEDIATE = 'referencingIntermediate'
	public static val PARTICIPATION_CONTEXT_ROOT = 'participationContextRoot'
	public static val START_OBJECT = 'startObject'
	public static val FOUND_MATCH_RESULT = 'foundMatchResult'
	public static val PARTICIPATION_OBJECTS = 'participationObjects'
	public static val PARTICIPATION_OBJECT = 'participationObject'
	public static val RESOURCE_BRIDGE = 'resourceBridge'
	public static val SINGLETON = 'singleton'

	def static package String correspondingVariableName(ParticipationClass participationClass) {
		'''corresponding_«participationClass.name»'''
	}

	def static package String getCorrespondenceTag(ParticipationClass participationClass) {
		return participationClass.getCorrespondenceTag(participationClass.containingCommonality)
	}

	// Note: In the context of commonality reference mappings we may create
	// correspondences between the referenced commonality instance and root
	// participation objects, which were declared inside the (possibly
	// different) referencing commonality.
	def static package String getCorrespondenceTag(ParticipationClass participationClass, Commonality commonality) {
		'''«commonality.concept.name».«commonality.name»/«participationClass.participation.name».«participationClass.name»'''
	}

	def static package String getParticipationAttributeReactionName(CommonalityAttributeMapping mapping) {
		mappingAttributeReactionName(mapping.attribute)
	}

	def static package String getParticipationAttributeReactionName(CommonalityReferenceMapping mapping) {
		mappingAttributeReactionName(mapping.reference)
	}

	def static private String mappingAttributeReactionName(ParticipationAttribute attribute) {
		'''«attribute.participationClass.participation.name»_«attribute.participationClass.name.toFirstUpper»«attribute.attribute.name.toFirstUpper»'''
	}

	def static package String getCommonalityAttributeReactionName(CommonalityAttribute attribute) {
		'''«attribute.containingCommonalityFile.concept.name»_«attribute.containingCommonalityFile.commonality.name.toFirstUpper»«attribute.name.toFirstUpper»'''
	}

	def static package String getCommonalityAttributeReactionName(CommonalityReference reference) {
		'''«reference.containingCommonalityFile.concept.name»_«reference.containingCommonalityFile.commonality.name.toFirstUpper»«reference.name.toFirstUpper»'''
	}

	def static package String getReactionName(CommonalityReferenceMapping mapping) {
		return '''«mapping.declaringReference.name»_«mapping.participation.name»'''
	}

	def static package String getReactionNameSuffix(ParticipationContext participationContext) {
		val referenceMapping = participationContext.referenceMapping
		if (referenceMapping === null) return ""
		else return '''_forReferenceMapping_«referenceMapping.reactionName»'''
	}
}
