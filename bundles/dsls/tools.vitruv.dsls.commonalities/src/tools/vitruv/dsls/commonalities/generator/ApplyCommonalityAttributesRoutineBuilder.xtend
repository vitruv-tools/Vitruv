package tools.vitruv.dsls.commonalities.generator

import com.google.inject.Inject
import java.util.HashMap
import java.util.Map
import org.eclipse.xtext.xbase.XAbstractFeatureCall
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XFeatureCall
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.language.CommonalityAttributeMapping
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.reactions.builder.FluentReactionsSegmentBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder
import tools.vitruv.dsls.reactions.builder.TypeProvider

import static com.google.common.base.Preconditions.*
import static tools.vitruv.dsls.commonalities.generator.EmfAccessExpressions.*

import static extension tools.vitruv.dsls.commonalities.generator.ReactionsGeneratorConventions.*
import static extension tools.vitruv.dsls.commonalities.generator.XbaseHelper.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.ParticipationContextHelper.*

package class ApplyCommonalityAttributesRoutineBuilder extends ReactionsSubGenerator {

	@GenerationScoped
	static class Provider extends ReactionsSegmentScopedProvider<ApplyCommonalityAttributesRoutineBuilder> {

		protected override createFor(FluentReactionsSegmentBuilder segment) {
			return new ApplyCommonalityAttributesRoutineBuilder(segment).injectMembers
		}

		def getApplyAttributesRoutine(FluentReactionsSegmentBuilder segment, Participation participation) {
			return getFor(segment).getApplyAttributesRoutine(participation)
		}
	}

	@Inject extension ParticipationObjectsRetrievalHelper participationObjectsRetrievalHelper

	val Map<Participation, FluentRoutineBuilder> routines = new HashMap

	private new(FluentReactionsSegmentBuilder segment) {
		checkNotNull(segment, "segment is null")
		// Note: The reactions segment is unused here. But having the provider
		// require it ensures that we only create one instance of this class
		// per reactions segment.
	}

	// Dummy constructor for Guice
	package new() {
		throw new IllegalStateException("Use the Factory to create instances of this class!")
	}

	def getApplyAttributesRoutine(Participation participation) {
		return routines.computeIfAbsent(participation) [
			val commonality = participation.containingCommonality
			create.routine('''applyCommonalityAttributes_«participation.reactionName»''')
				.input [
					model(commonality.changeClass, INTERMEDIATE)
				]
				.match [
					participation.classes.forEach [ participationClass |
						retrieveAssertedParticipationObject(participationClass) [
							variable(INTERMEDIATE) // correspondence source
						]
					]
				]
				.action [
					execute [ extension typeProvider |
						XbaseFactory.eINSTANCE.createXBlockExpression => [
							participation.relevantMappings.forEach [ mapping |
								join(mapping.applyMapping(typeProvider))
							]
						]
					]
				]
		]
	}

	def private getRelevantMappings(Participation participation) {
		val commonality = participation.containingCommonality
		return commonality.attributes.flatMap[mappings].filter[isWrite && it.participation == participation]
	}

	def private XExpression applyMapping(CommonalityAttributeMapping mapping, extension TypeProvider typeProvider) {
		return XbaseFactory.eINSTANCE.createXBlockExpression => [
			val intermediate = variable(INTERMEDIATE)
			val participationClass = mapping.attribute.participationClass
			val participationObjectVar = variable(participationClass.correspondingVariableName)
			// Since the participation may exist in different contexts with
			// different root objects, the participation object may not be
			// available for root participation classes:
			if (participationClass.isRootClass) {
				expressions += XbaseFactory.eINSTANCE.createXIfExpression => [
					^if = participationObjectVar.optionalIsPresent(typeProvider)
					val participationObject = participationObjectVar.copy.optionalGet(typeProvider)
					then = mapping.applyAttribute(typeProvider, intermediate, participationObject)
				]
			} else {
				expressions += mapping.applyAttribute(typeProvider, intermediate, participationObjectVar)
			}
		]
	}

	def private XExpression applyAttribute(CommonalityAttributeMapping mapping, TypeProvider typeProvider,
		XFeatureCall intermediate, XAbstractFeatureCall participationObject) {
		if (mapping.attribute.isMultiValued) {
			return setListFeatureValue(typeProvider, participationObject, mapping.participationEFeature,
				getListFeatureValue(typeProvider, intermediate, mapping.commonalityEFeature))
		} else {
			return setFeatureValue(typeProvider, participationObject, mapping.participationEFeature,
				getFeatureValue(typeProvider, intermediate, mapping.commonalityEFeature))
		}
	}
}
