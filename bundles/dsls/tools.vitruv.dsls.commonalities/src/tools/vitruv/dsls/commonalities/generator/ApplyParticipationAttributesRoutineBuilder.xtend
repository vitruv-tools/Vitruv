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
import tools.vitruv.extensions.dslruntime.commonalities.matching.ParticipationObjects

import static com.google.common.base.Preconditions.*
import static tools.vitruv.dsls.commonalities.generator.EmfAccessExpressions.*

import static extension tools.vitruv.dsls.commonalities.generator.ReactionsGeneratorConventions.*
import static extension tools.vitruv.dsls.commonalities.generator.XbaseHelper.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.ParticipationContextHelper.*

package class ApplyParticipationAttributesRoutineBuilder extends ReactionsSubGenerator {

	@GenerationScoped
	static class Provider extends ReactionsSegmentScopedProvider<ApplyParticipationAttributesRoutineBuilder> {

		protected override createFor(FluentReactionsSegmentBuilder segment) {
			return new ApplyParticipationAttributesRoutineBuilder(segment).injectMembers
		}

		def getApplyParticipationAttributesRoutine(FluentReactionsSegmentBuilder segment, Participation participation) {
			return this.getFor(segment).getApplyAttributesRoutine(participation)
		}
	}

	@Inject extension ParticipationObjectsHelper participationObjectsHelper

	val Map<Participation, FluentRoutineBuilder> applyParticipationAttributesRoutines = new HashMap

	private new(FluentReactionsSegmentBuilder segment) {
		checkNotNull(segment, "segment is null")
		// Note: The reactions segment is unused here. But having the provider
		// require it ensures that we only create one instance of this class
		// per reactions segment.
	}

	// Dummy constructor for Guice
	package new() {
		throw new IllegalStateException("Use the Provider to get instances of this class!")
	}

	def getApplyAttributesRoutine(Participation participation) {
		checkNotNull(participation, "participation is null")
		return applyParticipationAttributesRoutines.computeIfAbsent(participation) [
			val commonality = participation.containingCommonality
			create.routine('''applyParticipationAttributes_«participation.reactionName»''')
				.input [
					model(commonality.changeClass, INTERMEDIATE)
					plain(ParticipationObjects, PARTICIPATION_OBJECTS)
				]
				.action [
					update(INTERMEDIATE) [ extension typeProvider |
						XbaseFactory.eINSTANCE.createXBlockExpression => [
							val participationObjectVars = participation.getParticipationObjectVars(
								variable(PARTICIPATION_OBJECTS), typeProvider)
							expressions += participationObjectVars.values
							participation.relevantMappings.forEach [ mapping |
								val intermediate = variable(INTERMEDIATE)
								val participationClass = mapping.attribute.participationClass
								val participationObjectVar = participationObjectVars.get(participationClass).featureCall
								// Since the participation may exist in different contexts with different root objects,
								// the participation object may not be available for root participation classes:
								if (participationClass.isRootClass) {
									expressions += XbaseFactory.eINSTANCE.createXIfExpression => [
										it.^if = participationObjectVar.copy.notEqualsNull(typeProvider)
										it.then = mapping.applyAttribute(typeProvider, intermediate, participationObjectVar)
									]
								} else {
									expressions += mapping.applyAttribute(typeProvider, intermediate, participationObjectVar)
								}
							]
						]
					]
				]
		]
	}

	def private getRelevantMappings(Participation participation) {
		val commonality = participation.containingCommonality
		return commonality.attributes.flatMap[mappings].filter[isRead && it.participation == participation]
	}

	def private XExpression applyAttribute(CommonalityAttributeMapping mapping, TypeProvider typeProvider,
		XFeatureCall intermediate, XAbstractFeatureCall participationObject) {
		if (mapping.attribute.isMultiValued) {
			return setListFeatureValue(typeProvider, intermediate, mapping.commonalityEFeature,
				getListFeatureValue(typeProvider, participationObject, mapping.participationEFeature))
		} else {
			return setFeatureValue(typeProvider, intermediate, mapping.commonalityEFeature,
				getFeatureValue(typeProvider, participationObject, mapping.participationEFeature))
		}
	}
}
