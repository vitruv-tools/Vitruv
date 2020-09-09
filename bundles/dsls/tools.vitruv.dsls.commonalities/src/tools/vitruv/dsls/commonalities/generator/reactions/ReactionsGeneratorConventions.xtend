package tools.vitruv.dsls.commonalities.generator.reactions

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.commonalities.language.Commonality
import tools.vitruv.dsls.commonalities.language.CommonalityAttribute
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.language.elements.Attribute
import tools.vitruv.dsls.commonalities.participation.ParticipationContext
import tools.vitruv.dsls.commonalities.participation.ParticipationContext.ContextClass

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

@Utility
class ReactionsGeneratorConventions {

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

	static val EXTERNAL_CLASS_PREFIX = 'external_'

	static def String getName(ContextClass contextClass) {
		val participationClass = contextClass.participationClass
		if (contextClass.isExternal) {
			return EXTERNAL_CLASS_PREFIX + participationClass.name
		} else {
			return participationClass.name
		}
	}

	static def String correspondingVariableName(ContextClass contextClass) {
		val participationClass = contextClass.participationClass
		if (contextClass.isExternal) {
			return contextClass.name
		} else {
			return participationClass.correspondingVariableName
		}
	}

	static def String correspondingVariableName(ParticipationClass participationClass) {
		'''corresponding_«participationClass.name»'''
	}

	static def String getCorrespondenceTag(ParticipationClass participationClass) {
		val commonality = participationClass.containingCommonality
		return '''«commonality.concept.name».«commonality.name»/«participationClass.participation.name».«
			participationClass.name»'''
	}

	static def String getResourceCorrespondenceTag(ParticipationClass resourceClass,
		ParticipationClass containedClass) {
		return '''resource'''
	}

	static def String getReactionName(Commonality commonality) {
		return '''«commonality.concept.name»_«commonality.name»'''
	}

	static def String getReactionName(Attribute attribute) {
		val classLike = attribute.classLikeContainer
		val packageLike = classLike.packageLikeContainer
		'''«packageLike.name»_«attribute.shortReactionName»'''
	}

	static def String getShortReactionName(Attribute attribute) {
		val classLike = attribute.classLikeContainer
		'''«classLike.name.toFirstUpper»«attribute.name.toFirstUpper»'''
	}

	static def String getReactionName(Participation participation) {
		val commonality = participation.containingCommonality
		'''«commonality.name»_«participation.name»'''
	}

	static def String getReactionNameSuffix(ParticipationContext participationContext) {
		if (!participationContext.isForReferenceMapping) return ""
		val participation = participationContext.participation
		val reference = participationContext.declaringReference
		val referencingCommonality = participationContext.referencingCommonality
		return '''_forReferenceMapping_«referencingCommonality.name»_«reference.name»_«participation.name»'''
	}

	static def String getReactionNameSuffix(CommonalityAttribute commonalityAttribute) {
		return '''_forCommonalityAttribute_«commonalityAttribute.name»'''
	}
}
