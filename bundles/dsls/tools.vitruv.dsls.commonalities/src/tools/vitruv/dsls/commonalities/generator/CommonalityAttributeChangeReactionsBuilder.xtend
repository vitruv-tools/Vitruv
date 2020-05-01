package tools.vitruv.dsls.commonalities.generator

import com.google.inject.Inject
import edu.kit.ipd.sdq.activextendannotations.Lazy
import java.util.List
import java.util.function.Function
import org.eclipse.xtext.xbase.XAbstractFeatureCall
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.language.CommonalityAttribute
import tools.vitruv.dsls.commonalities.language.CommonalityAttributeMapping
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.reactions.builder.FluentReactionsSegmentBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineStartBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder
import tools.vitruv.dsls.reactions.builder.TypeProvider

import static com.google.common.base.Preconditions.*

import static extension tools.vitruv.dsls.commonalities.generator.EmfAccessExpressions.*
import static extension tools.vitruv.dsls.commonalities.generator.ReactionsGeneratorConventions.*
import static extension tools.vitruv.dsls.commonalities.generator.XbaseHelper.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.ParticipationContextHelper.*

package class CommonalityAttributeChangeReactionsBuilder extends ReactionsSubGenerator {

	static class Factory extends InjectingFactoryBase {
		def createFor(CommonalityAttribute attribute, Participation targetParticipation) {
			return new CommonalityAttributeChangeReactionsBuilder(attribute, targetParticipation).injectMembers
		}
	}

	@Inject extension AttributeChangeReactionsHelper attributeChangeReactionsHelper
	@Inject extension ParticipationObjectsRetrievalHelper participationObjectsRetrievalHelper

	val CommonalityAttribute attribute
	val Participation targetParticipation
	@Lazy val List<CommonalityAttributeMapping> relevantMappings = calculateRelevantMappings()

	private new(CommonalityAttribute attribute, Participation targetParticipation) {
		checkNotNull(attribute, "attribute is null")
		checkNotNull(targetParticipation, "targetParticipation is null")
		this.attribute = attribute
		this.targetParticipation = targetParticipation
	}

	// Dummy constructor for Guice
	package new() {
		this.attribute = null
		this.targetParticipation = null
		throw new IllegalStateException("Use the Factory to create instances of this class!")
	}

	def private calculateRelevantMappings() {
		return attribute.mappings.filter [
			isWrite && it.participation == targetParticipation
		].toList
	}

	def package void generateReactions(FluentReactionsSegmentBuilder segment) {
		if (relevantMappings.size === 0) return;
		segment += reactionsForCommonalityAttributeChange
	}

	def private reactionsForCommonalityAttributeChange() {
		return attribute.getAttributeChangeReactions [ changeType , it |
			call [
				switch (changeType) {
					case VALUE_REPLACED: {
						buildSingleFeatureSetRoutine
					}
					case VALUE_INSERTED: {
						buildMultiFeatureAddRoutine
					}
					case VALUE_REMOVED: {
						buildMultiFeatureRemoveRoutine
					}
				}
			]
		]
	}

	def private applyAttributeChange(extension TypeProvider typeProvider, CommonalityAttributeMapping mapping,
		Function<XAbstractFeatureCall, XExpression> expressionBuilder) {
		val participationClass = mapping.attribute.participationClass
		val corresponding = participationClass.correspondingVariableName
		val objectVar = variable(corresponding)
		if (participationClass.isRootClass) {
			// object is optional:
			return XbaseFactory.eINSTANCE.createXIfExpression => [
				^if = objectVar.optionalIsPresent(typeProvider)
				then = expressionBuilder.apply(objectVar.copy.optionalGet(typeProvider))
			]
		} else {
			return expressionBuilder.apply(objectVar)
		}
	}

	def private buildSingleFeatureSetRoutine(extension RoutineStartBuilder routineBuilder) {
		input [
			affectedEObject
			newValue
		]
		.match [
			retrieveRelevantCorrespondences()
		]
		.action [
			for (mapping : relevantMappings) {
				val corresponding = mapping.correspondingVariableName
				update(corresponding) [
					applyAttributeChange(mapping) [ objectVar |
						setFeatureValue(objectVar, mapping.participationEFeature, newValue)
					]
				]
			}
		]
	}

	def private buildMultiFeatureAddRoutine(extension RoutineStartBuilder routineBuilder) {
		input [
			affectedEObject
			newValue
		]
		.match [
			retrieveRelevantCorrespondences()
		]
		.action [
			for (mapping : relevantMappings) {
				val corresponding = mapping.correspondingVariableName
				update(corresponding) [
					applyAttributeChange(mapping) [ objectVar |
						addToListFeatureValue(objectVar, mapping.participationEFeature, newValue)
					]
				]
			}
		]
	}

	def private buildMultiFeatureRemoveRoutine(extension RoutineStartBuilder routineBuilder) {
		input [
			affectedEObject
			oldValue
		]
		.match [
			retrieveRelevantCorrespondences()
		]
		.action [
			for (mapping : relevantMappings) {
				val corresponding = mapping.correspondingVariableName
				update(corresponding) [
					applyAttributeChange(mapping) [ objectVar |
						removeFromListFeatureValue(objectVar, mapping.participationEFeature, oldValue)
					]
				]
			}
		]
	}

	def private retrieveRelevantCorrespondences(extension UndecidedMatcherStatementBuilder matcherBuilder) {
		for (mapping : relevantMappings) {
			val participationClass = mapping.attribute.participationClass
			matcherBuilder.retrieveParticipationObject(participationClass) [
				affectedEObject // correspondence source
			]
		}
	}

	def private getCorrespondingVariableName(CommonalityAttributeMapping mapping) {
		return mapping.attribute.participationClass.correspondingVariableName
	}
}
