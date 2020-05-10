package tools.vitruv.dsls.commonalities.generator

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.commonalities.language.Commonality
import tools.vitruv.dsls.commonalities.language.CommonalityReferenceMapping
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.language.elements.Attribute
import tools.vitruv.dsls.commonalities.language.extensions.ParticipationContext
import tools.vitruv.dsls.commonalities.language.extensions.ParticipationContext.ContextClass

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

@Utility
package class ReactionsGeneratorConventions {

	public static val INTERMEDIATE = 'intermediate'
	public static val REFERENCED_INTERMEDIATE = 'referencedIntermediate'
	public static val REFERENCED_INTERMEDIATES = 'referencedIntermediates'
	public static val REFERENCING_INTERMEDIATE = 'referencingIntermediate'
	public static val REFERENCE_ROOT = 'referenceRoot'
	public static val INTERMEDIATE_ROOT = 'intermediateRoot'
	public static val START_OBJECT = 'startObject'
	public static val FOLLOW_ATTRIBUTE_REFERENCES = 'followAttributeReferences'
	public static val FOUND_MATCH_RESULT = 'foundMatchResult'
	public static val PARTICIPATION_OBJECTS = 'participationObjects'
	public static val PARTICIPATION_OBJECT = 'participationObject'
	public static val RESOURCE_BRIDGE = 'resourceBridge'
	public static val SINGLETON = 'singleton'

	private static val EXTERNAL_CLASS_PREFIX = 'external_'

	def static package String getName(ContextClass contextClass) {
		val participationClass = contextClass.participationClass
		if (contextClass.isExternal) {
			return EXTERNAL_CLASS_PREFIX + participationClass.name
		} else {
			return participationClass.name
		}
	}

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

	def static package String getReactionName(Commonality commonality) {
		return '''«commonality.concept.name»_«commonality.name»'''
	}

	def static package String getReactionName(Attribute attribute) {
		val classLike = attribute.classLikeContainer
		val packageLike = classLike.packageLikeContainer
		'''«packageLike.name»_«attribute.shortReactionName»'''
	}

	def static package String getShortReactionName(Attribute attribute) {
		val classLike = attribute.classLikeContainer
		'''«classLike.name.toFirstUpper»«attribute.name.toFirstUpper»'''
	}

	def static package String getReactionName(Participation participation) {
		val commonality = participation.containingCommonality
		'''«commonality.name»_«participation.name»'''
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
