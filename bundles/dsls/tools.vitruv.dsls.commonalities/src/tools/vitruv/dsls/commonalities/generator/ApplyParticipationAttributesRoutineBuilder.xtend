package tools.vitruv.dsls.commonalities.generator

import com.google.inject.Inject
import org.eclipse.xtext.xbase.XAbstractFeatureCall
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XFeatureCall
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.language.Commonality
import tools.vitruv.dsls.commonalities.language.CommonalityAttributeMapping
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.reactions.builder.TypeProvider
import tools.vitruv.extensions.dslruntime.commonalities.ParticipationMatcher.ParticipationObjects

import static com.google.common.base.Preconditions.*
import static tools.vitruv.dsls.commonalities.generator.EmfAccessExpressions.*
import static tools.vitruv.dsls.commonalities.generator.ReactionsGeneratorConventions.*

import static extension tools.vitruv.dsls.commonalities.generator.XbaseHelper.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.ParticipationContextHelper.*

package class ApplyParticipationAttributesRoutineBuilder extends ReactionsSubGenerator {

	static class Factory extends InjectingFactoryBase {
		def createFor(Participation participation) {
			return new ApplyParticipationAttributesRoutineBuilder(participation).injectMembers
		}
	}

	@Inject extension ParticipationObjectsHelper participationObjectsHelper

	val Participation participation
	val Commonality commonality

	private new(Participation participation) {
		checkNotNull(participation, "participation is null")
		this.participation = participation
		this.commonality = participation.containingCommonality
	}

	// Dummy constructor for Guice
	package new() {
		this.participation = null
		this.commonality = null
		throw new IllegalStateException("Use the Factory to create instances of this class!")
	}

	def getRoutine() {
		return getApplyAttributesRoutine();
	}

	def private getRelevantMappings() {
		return commonality.attributes.flatMap[mappings].filter[isRead && it.participation == participation]
	}

	def private getApplyAttributesRoutine() {
		return create.routine('''applyParticipationAttributes_«commonality.name»_«participation.name»''')
			.input [
				model(commonality.changeClass, INTERMEDIATE)
				plain(ParticipationObjects, PARTICIPATION_OBJECTS)
			]
			.action [
				update(INTERMEDIATE) [ extension typeProvider |
					val participationObjectVars = participation.getParticipationObjectVars(variable(PARTICIPATION_OBJECTS), typeProvider)
					XbaseFactory.eINSTANCE.createXBlockExpression => [
						expressions += participationObjectVars.values
						for (CommonalityAttributeMapping mapping : relevantMappings) {
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
						}
					]
				]
			]
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
