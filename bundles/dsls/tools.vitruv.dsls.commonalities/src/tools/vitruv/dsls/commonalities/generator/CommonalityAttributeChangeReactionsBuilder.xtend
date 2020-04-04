package tools.vitruv.dsls.commonalities.generator

import com.google.inject.Inject
import java.util.List
import java.util.function.Function
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EReference
import org.eclipse.xtext.xbase.XAbstractFeatureCall
import org.eclipse.xtext.xbase.XExpression
import tools.vitruv.dsls.commonalities.language.CommonalityAttribute
import tools.vitruv.dsls.commonalities.language.CommonalityAttributeMapping
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.elements.EClassAdapter
import tools.vitruv.dsls.commonalities.language.elements.EDataTypeAdapter
import tools.vitruv.dsls.reactions.builder.FluentReactionsSegmentBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder
import tools.vitruv.dsls.reactions.builder.TypeProvider

import static com.google.common.base.Preconditions.*

import static extension tools.vitruv.dsls.commonalities.generator.EmfAccessExpressions.*
import static extension tools.vitruv.dsls.commonalities.generator.ReactionsGeneratorConventions.*
import static extension tools.vitruv.dsls.commonalities.generator.XbaseHelper.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

package class CommonalityAttributeChangeReactionsBuilder extends ReactionsSubGenerator {

	static class Factory extends InjectingFactoryBase {
		def createFor(CommonalityAttribute attribute, Participation targetParticipation) {
			return new CommonalityAttributeChangeReactionsBuilder(attribute, targetParticipation).injectMembers
		}
	}

	@Inject extension ParticipationContextHelper participationContextHelper

	val CommonalityAttribute attribute
	val Participation targetParticipation
	List<CommonalityAttributeMapping> relevantMappings

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

	def package void generateReactions(FluentReactionsSegmentBuilder segment) {
		relevantMappings = attribute.mappings.filter [
			isWrite && participation == targetParticipation
		].toList
		if (relevantMappings.size === 0) return;

		switch attribute.type {
			EDataTypeAdapter case !attribute.isMultiValued:
				segment += singleAttributeSetReaction
			EDataTypeAdapter case attribute.isMultiValued:
				segment += #[multiAttributeAddReaction, multiAttributeRemoveReaction]
			EClassAdapter case !attribute.isMultiValued:
				segment += singleReferenceSetReaction
			EClassAdapter case attribute.isMultiValued:
				segment += #[multiReferenceAddReaction, multiReferenceRemoveReaction]
		}
	}

	def private applyAttributeChange(extension TypeProvider typeProvider, CommonalityAttributeMapping mapping,
		Function<XAbstractFeatureCall, XExpression> expressionBuilder) {
		val participationClass = mapping.attribute.participationClass
		val corresponding = participationClass.correspondingVariableName
		val objectVar = variable(corresponding)
		if (participationClass.isRootClass) {
			// object is optional:
			ifOptionalPresent(typeProvider, objectVar, expressionBuilder.apply(optionalGet(typeProvider, objectVar.copy)))
		} else {
			expressionBuilder.apply(objectVar)
		}
	}

	def private singleAttributeSetReaction() {
		create.reaction('''«attribute.commonalityAttributeReactionName»Change''')
			.afterAttributeReplacedAt(attribute.correspondingEFeature as EAttribute)
			.call [
				input [newValue]
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
		]
	}

	def private multiAttributeAddReaction() {
		create.reaction('''«attribute.commonalityAttributeReactionName»Insert''')
			.afterAttributeInsertIn(attribute.correspondingEFeature as EAttribute)
			.call [
				input [newValue]
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
			]
	}

	def private multiAttributeRemoveReaction() {
		create.reaction('''«attribute.commonalityAttributeReactionName»Remove''')
			.afterAttributeRemoveFrom(attribute.correspondingEFeature as EAttribute)
			.call [
				input [oldValue]
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
			]
	}

	def private singleReferenceSetReaction() {
		create.reaction('''«attribute.commonalityAttributeReactionName»Change''')
			.afterElement.replacedAt(attribute.correspondingEFeature as EReference)
			.call [
				input [newValue]
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
			]
	}

	def private multiReferenceAddReaction() {
		create.reaction('''«attribute.commonalityAttributeReactionName»ElementInsert''')
			.afterElement.insertedIn(attribute.correspondingEFeature as EReference)
			.call [
				input [newValue]
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
			]
	}

	def private multiReferenceRemoveReaction() {
		create.reaction('''«attribute.commonalityAttributeReactionName»ElementRemove''')
			.afterElement.removedFrom(attribute.correspondingEFeature as EReference)
			.call [
				input [oldValue]
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
			]
	}

	def private retrieveRelevantCorrespondences(extension UndecidedMatcherStatementBuilder matcherBuilder) {
		for (mapping : relevantMappings) {
			val participationClass = mapping.attribute.participationClass
			if (participationClass.isRootClass) {
				// Note: Depending on the context in which the participation
				// exists, the participation's root object(s) may not exist.
				vall(participationClass.correspondingVariableName).retrieveOptional(participationClass.changeClass)
					.correspondingTo.affectedEObject
					.taggedWith(participationClass.correspondenceTag)
			} else {
				vall(participationClass.correspondingVariableName).retrieveAsserted(participationClass.changeClass)
					.correspondingTo.affectedEObject
					.taggedWith(participationClass.correspondenceTag)
			}
		}
	}

	def private correspondingVariableName(CommonalityAttributeMapping mappingSpecification) {
		mappingSpecification.attribute.participationClass.correspondingVariableName
	}
}
