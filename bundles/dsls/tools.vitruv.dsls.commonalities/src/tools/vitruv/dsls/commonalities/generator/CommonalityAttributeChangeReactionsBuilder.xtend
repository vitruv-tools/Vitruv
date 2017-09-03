package tools.vitruv.dsls.commonalities.generator

import java.util.Collection
import java.util.Collections
import java.util.List
import javax.inject.Inject
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XFeatureCall
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.language.AttributeDeclaration
import tools.vitruv.dsls.commonalities.language.AttributeMappingSpecifiation
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.elements.EClassAdapter
import tools.vitruv.dsls.commonalities.language.elements.EDataTypeAdapter
import tools.vitruv.dsls.reactions.builder.FluentReactionBuilder
import tools.vitruv.dsls.reactions.builder.FluentReactionsLanguageBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineTypeProvider
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder

import static com.google.common.base.Preconditions.*

import static extension tools.vitruv.dsls.commonalities.generator.JvmTypeProviderHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.ReactionsGeneratorConventions.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

package class CommonalityAttributeChangeReactionsBuilder {
	AttributeDeclaration attribute
	Participation targetParticipation
	List<AttributeMappingSpecifiation> relevantMappings
	extension GenerationContext generationContext
	@Inject FluentReactionsLanguageBuilder create

	def package forAttribute(AttributeDeclaration attribute) {
		this.attribute = attribute
		this
	}

	def package regardingParticipation(Participation targetParticipation) {
		this.targetParticipation = targetParticipation
		this
	}

	def package withGenerationContext(GenerationContext context) {
		this.generationContext = context
		this
	}

	def package Iterable<FluentReactionBuilder> getReactions() {
		checkState(attribute !== null, "No attribute to create reactions for was set!")
		checkState(targetParticipation !== null, "No participation to create reactions for was set!")
		checkState(generationContext !== null, "No generation context was set!")

		relevantMappings = attribute.mappings.filter [
			isWrite && it.participation == targetParticipation
		].toList

		if (relevantMappings.size === 0) return Collections.emptyList

		val attributeType = attribute.type
		switch (attributeType) {
			EDataTypeAdapter case !attribute.isMultiValued:
				Collections.singleton(singleAttributeSetReaction)
			EDataTypeAdapter case attribute.isMultiValued:
				#[multiAttributeAddReaction, multiAttributeRemoveReaction]
			EClassAdapter case !attribute.isMultiValued:
				Collections.singleton(singleReferenceSetReaction)
			EClassAdapter case attribute.isMultiValued:
				#[multiReferenceAddReaction, multiReferenceRemoveReaction]
		}
	}

	def private singleAttributeSetReaction() {
		create.reaction('''«commonality.name»«attribute.name.toFirstUpper»Change''')
			.afterAttributeReplacedAt(attribute.EFeatureToReference as EAttribute)
			.call [
				input [newValue].match [
					retrieveRelevantCorrespondences()
				]
				.action [
					for (mapping : relevantMappings) {
						val corresponding = mapping.correspondingVariableName
						update(corresponding) [
							setFeature(variable(corresponding), mapping.attribute.EFeatureToReference, newValue)
						]
					}
				]
		]
	}

	def private multiAttributeAddReaction() {
		create.reaction('''«commonality.name»«attribute.name.toFirstUpper»ElementInsert''')
			.afterAttributeInsertIn(attribute.EFeatureToReference as EAttribute)
			.call [
				input [newValue].match [
					retrieveRelevantCorrespondences()
				]
				.action [
					for (mapping : relevantMappings) {
						val corresponding = mapping.correspondingVariableName
						update(corresponding) [
							addToFeatureList(variable(corresponding), mapping.attribute.EFeatureToReference, newValue)
						]
					}
				]
			]
	}

	def private multiAttributeRemoveReaction() {
		create.reaction('''«commonality.name»«attribute.name.toFirstUpper»ElementRemove''')
			.afterAttributeInsertIn(attribute.EFeatureToReference as EAttribute)
			.call [
				input [newValue].match [
					retrieveRelevantCorrespondences()
				]
				.action [
					for (mapping : relevantMappings) {
						val corresponding = mapping.correspondingVariableName
						update(corresponding) [
							removeFromFeatureList(variable(corresponding), mapping.attribute.EFeatureToReference, newValue)
						]
					}
				]
			]
	}

	def private singleReferenceSetReaction() {
		create.reaction('''«commonality.name»«attribute.name.toFirstUpper»Change''')
			.afterElement.replacedAt(attribute.EFeatureToReference as EReference)
			.call [
				input [newValue].match [
					retrieveRelevantCorrespondences()
				]
				.action [
					for (mapping : relevantMappings) {
						val corresponding = mapping.correspondingVariableName
						update(corresponding) [
							setFeature(variable(corresponding), mapping.attribute.EFeatureToReference, newValue)
						]
					}
				]
			]
	}

	def private multiReferenceAddReaction() {
		create.reaction('''«commonality.name»«attribute.name.toFirstUpper»ElementInsert''')
			.afterAttributeInsertIn(attribute.EFeatureToReference as EAttribute)
			.call [
				input [newValue]
				.match [
					retrieveRelevantCorrespondences()
				]
				.action [
					for (mapping : relevantMappings) {
						val corresponding = mapping.correspondingVariableName
						update(corresponding) [
							addToFeatureList(variable(corresponding), mapping.attribute.EFeatureToReference, newValue)
						]
					}
				]
			]
	}

	def private multiReferenceRemoveReaction() {
		create.reaction('''«commonality.name»«attribute.name.toFirstUpper»ElementRemove''')
			.afterAttributeInsertIn(attribute.EFeatureToReference as EAttribute)
			.call [
				input [newValue]
				.match [
					retrieveRelevantCorrespondences()
				]
				.action [
					for (mapping : relevantMappings) {
						val corresponding = mapping.correspondingVariableName
						update(corresponding) [
							removeFromFeatureList(variable(corresponding), mapping.attribute.EFeatureToReference, newValue)
						]
					}
				]
			]
	}

	def private retrieveRelevantCorrespondences(extension UndecidedMatcherStatementBuilder matcherBuilder) {
		for (mapping : relevantMappings) {
			val participationClass = mapping.attribute.participationClass
			vall(participationClass.correspondingVariableName).retrieve(participationClass.changeClass).correspondingTo.
				affectedEObject.taggedWith(participationClass.correspondenceTag)
		}
	}

	def private correspondingVariableName(AttributeMappingSpecifiation mappingSpecification) {
		mappingSpecification.attribute.participationClass.correspondingVariableName
	}

	def private static setFeature(extension RoutineTypeProvider typeProvider, XExpression element,
		EStructuralFeature eFeature, XExpression newValue) {
		XbaseFactory.eINSTANCE.createXAssignment => [
			assignable = element
			feature = typeProvider.findMethod(eFeature.EContainingClass.instanceClassName, 'set' + eFeature.name.toFirstUpper)
			value = newValue
		]
	}

	def package static addToFeatureList(extension RoutineTypeProvider typeProvider, XFeatureCall element,
		EStructuralFeature eFeature, XExpression newValue) {
		XbaseFactory.eINSTANCE.createXBinaryOperation => [
			leftOperand = getEFeature(typeProvider, element, eFeature)
			feature = typeProvider.findMethod(CollectionExtensions, 'operator_add', Collection, typeVariable)
			rightOperand = newValue
		]
	}

	def package static removeFromFeatureList(extension RoutineTypeProvider typeProvider, XFeatureCall element,
		EStructuralFeature eFeature, XExpression newValue) {
		XbaseFactory.eINSTANCE.createXBinaryOperation => [
			leftOperand = getEFeature(typeProvider, element, eFeature)
			feature = typeProvider.findMethod(CollectionExtensions, 'operator_remove', Collection, typeVariable)
			rightOperand = newValue
		]
	}

	def private static getEFeature(extension RoutineTypeProvider typeProvider, XExpression element,
		EStructuralFeature eFeature) {
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			memberCallTarget = element
			feature = typeProvider.findMethod(eFeature.EContainingClass.instanceClassName, 'get' + eFeature.name.toFirstUpper)
		]
	}

}
