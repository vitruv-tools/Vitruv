package tools.vitruv.dsls.commonalities.generator.reactions.attribute

import com.google.inject.Inject
import edu.kit.ipd.sdq.activextendannotations.Lazy
import java.util.List
import java.util.Set
import tools.vitruv.dsls.commonalities.generator.reactions.ReactionsSubGenerator
import tools.vitruv.dsls.commonalities.generator.reactions.attribute.AttributeMappingOperatorHelper.AttributeMappingOperatorContext
import tools.vitruv.dsls.commonalities.generator.reactions.participation.ParticipationObjectsRetrievalHelper
import tools.vitruv.dsls.commonalities.generator.util.guice.InjectingFactoryBase
import tools.vitruv.dsls.commonalities.language.CommonalityAttributeMapping
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.reactions.builder.FluentReactionsSegmentBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.ActionStatementBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineStartBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder

import static com.google.common.base.Preconditions.*
import static tools.vitruv.framework.util.XtendAssertHelper.*

import static extension tools.vitruv.dsls.commonalities.generator.reactions.ReactionsGeneratorConventions.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

class ParticipationAttributeChangeReactionsBuilder extends ReactionsSubGenerator {

	static class Factory extends InjectingFactoryBase {
		def createFor(Participation participation) {
			return new ParticipationAttributeChangeReactionsBuilder(participation).injectMembers
		}
	}

	@Inject extension AttributeMappingHelper attributeMappingHelper
	@Inject extension AttributeChangeReactionsHelper attributeChangeReactionsHelper
	@Inject extension ParticipationObjectsRetrievalHelper participationObjectsRetrievalHelper

	val Participation participation

	@Lazy val List<CommonalityAttributeMapping> relevantMappings = calculateRelevantMappings()
	@Lazy val Set<ParticipationClass> relevantParticipationClasses = calculateRelevantParticipationClasses()

	private new(Participation participation) {
		checkNotNull(participation, "participation is null")
		this.participation = participation
	}

	// Dummy constructor for Guice
	package new() {
		this.participation = null
		throw new IllegalStateException("Use the Factory to create instances of this class!")
	}

	private def calculateRelevantMappings() {
		return participation.relevantReadMappings.toList
	}

	private def calculateRelevantParticipationClasses() {
		return relevantMappings.flatMap [involvedParticipationClasses].toSet
	}

	def void generateReactions(FluentReactionsSegmentBuilder segment) {
		segment += relevantMappings.flatMap [reactionsForAttributeMappingRightChange]
	}

	private def reactionsForAttributeMappingRightChange(CommonalityAttributeMapping mapping) {
		val participationAttribute = mapping.participationAttribute 
		if (participationAttribute === null) return emptyList
		 
		// Assert: For every participation attribute there is at most one reading attribute mapping within the same
		// commonality attribute (ensured via validation).
		// However, there may be multiple reading attribute mappings for the same participation attribute across
		// different commonality attributes. A commonality attribute specific suffix avoids naming conflicts between
		// the generated attribute change reactions.
		val commonalityAttribute = mapping.declaringAttribute
		val reactionNameSuffix = commonalityAttribute.reactionNameSuffix
		return participationAttribute.getAttributeChangeReactions(reactionNameSuffix) [ changeType, it |
			call [buildAttributeChangedRoutine(mapping)]
		]
	}

	private def buildAttributeChangedRoutine(extension RoutineStartBuilder routineBuilder,
		CommonalityAttributeMapping mapping) {
		input [
			affectedEObject
		]
		.match [
			retrieveIntermediate(mapping)
			retrieveRelevantParticipationObjects()
		]
		.action [
			applyMapping(mapping)
		]
	}

	private def retrieveIntermediate(extension UndecidedMatcherStatementBuilder builder,
		CommonalityAttributeMapping mapping) {
		// Otherwise we would not generate an attribute change reaction:
		assertTrue(mapping.participationAttribute !== null)
		val participationClass = mapping.participationAttribute.participationClass
		vall(INTERMEDIATE).retrieve(commonality.changeClass)
			.correspondingTo.affectedEObject
			.taggedWith(participationClass.correspondenceTag)
	}

	private def retrieveRelevantParticipationObjects(extension UndecidedMatcherStatementBuilder matcherBuilder) {
		relevantParticipationClasses.forEach [ participationClass |
			matcherBuilder.retrieveAssertedParticipationObject(participationClass) [
				variable(INTERMEDIATE) // correspondence source
			]
		]
	}

	private def void applyMapping(extension ActionStatementBuilder actionBuilder,
		CommonalityAttributeMapping mapping) {
		update(INTERMEDIATE) [ extension typeProvider |
			val participationClassToObject = typeProvider.participationClassToOptionalObject
			val operatorContext = new AttributeMappingOperatorContext(typeProvider, [variable(INTERMEDIATE)],
					participationClassToObject)
			mapping.applyReadMapping(operatorContext)
		]
	}
}
