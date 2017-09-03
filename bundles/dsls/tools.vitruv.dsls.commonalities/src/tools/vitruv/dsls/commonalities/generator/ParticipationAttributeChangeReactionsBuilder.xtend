package tools.vitruv.dsls.commonalities.generator

import com.google.inject.Inject
import java.util.Collection
import java.util.Collections
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XFeatureCall
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.language.AttributeMappingSpecifiation
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.elements.EClassAdapter
import tools.vitruv.dsls.commonalities.language.elements.EDataTypeAdapter
import tools.vitruv.dsls.reactions.builder.FluentReactionBuilder
import tools.vitruv.dsls.reactions.builder.FluentReactionsLanguageBuilder
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineTypeProvider
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.UndecidedMatcherStatementBuilder

import static com.google.common.base.Preconditions.*

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import static extension tools.vitruv.dsls.commonalities.generator.JvmTypeProviderHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.XbaseHelper.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

package class ParticipationAttributeChangeReactionsBuilder {
	@Inject FluentReactionsLanguageBuilder create
	extension GenerationContext generationContext
	Participation targetParticipation

	def package forParticipation(Participation targetParticipation) {
		this.targetParticipation = targetParticipation
		this
	}

	def package withGenerationContext(GenerationContext context) {
		this.generationContext = context
		this
	}

	def package Iterable<FluentReactionBuilder> getReactions() {
		checkState(targetParticipation !== null, "No participation to create reactions for was set!")
		checkState(generationContext !== null, "No generation context was set!")

		commonality.attributes.flatMap[mappings].filter [
			isRead && attribute.participationClass.participation == targetParticipation
		].flatMap[reactionsForAttributeMappingRightChange]
	}

	def private reactionsForAttributeMappingRightChange(AttributeMappingSpecifiation mapping) {
		val attributeType = mapping.attribute.type
		switch (attributeType) {
			EDataTypeAdapter case !mapping.attribute.isMultiValued:
				Collections.singleton(singleAttributeSetReaction(mapping))
			EDataTypeAdapter case mapping.attribute.isMultiValued:
				#[multiAttributeAddReaction(mapping), multiAttributeRemoveReaction(mapping)]
			EClassAdapter case !mapping.attribute.isMultiValued:
				Collections.singleton(singleReferenceSetReaction(mapping))
			EClassAdapter case mapping.attribute.isMultiValued:
				#[multiReferenceAddReaction(mapping), multiReferenceRemoveReaction(mapping)]
		}
	}

	def private singleAttributeSetReaction(AttributeMappingSpecifiation mapping) {
		create.reaction('''«mapping.participation.name»«mapping.attribute.name.toFirstUpper»Change''')
			.afterAttributeReplacedAt(mapping.participationAttributeChangeClass, mapping.participationEAttribute)
			.call [
				input [newValue]
				.match [
					retrieveIntermediate()
				]
				.action [
					update('intermediate') [
						setFeature(variable('intermediate'), mapping.declaringAttribute.name, newValue)
					]
				]
			]
	}

	def private multiAttributeAddReaction(AttributeMappingSpecifiation mapping) {
		create.reaction('''«mapping.participation.name»«mapping.attribute.name.toFirstUpper»ElementInsert''')
			.afterAttributeInsertIn(mapping.participationAttributeChangeClass, mapping.participationEAttribute)
			.call [
				input [newValue].match [
					retrieveIntermediate()
				].action [
					update('intermediate') [
						addToFeatureList(variable('intermediate'), mapping.declaringAttribute.name, newValue)
					]
				]
			]
	}

	def private multiAttributeRemoveReaction(AttributeMappingSpecifiation mapping) {
		create.reaction('''«mapping.participation.name»«mapping.attribute.name.toFirstUpper»ElementRemove''')
			.afterAttributeRemoveFrom(mapping.participationAttributeChangeClass, mapping.participationEAttribute)
			.call [
				input [newValue]
				.match [
					retrieveIntermediate()
				]
				.action [
					update('intermediate') [
						removeFromFeatureList(variable('intermediate'), mapping.declaringAttribute.name, newValue)
					]
				]
			]
	}

	def private singleReferenceSetReaction(AttributeMappingSpecifiation mapping) {
		create.reaction('''«mapping.participation.name»«mapping.attribute.name.toFirstUpper»Change''')
			.afterElement.replacedAt(mapping.participationAttributeChangeClass, mapping.participationEReference)
			.call [
				input [newValue]
				.match [
					retrieveIntermediate()
				]
				.action [
					update('intermediate') [
						setFeature(variable('intermediate'), mapping.declaringAttribute.name, newValue)
					]
				]
			]
	}

	def private multiReferenceAddReaction(AttributeMappingSpecifiation mapping) {
		create.reaction('''«mapping.participation.name»«mapping.attribute.name.toFirstUpper»ElementInsert''')
			.afterElement.insertedIn(mapping.participationAttributeChangeClass, mapping.participationEReference)
			.call [
				input [newValue]
				.match [
					retrieveIntermediate()
				]
				.action [
					update('intermediate') [
						addToFeatureList(variable('intermediate'), mapping.declaringAttribute.name, newValue)
					]
				]
			]
	}

	def private multiReferenceRemoveReaction(AttributeMappingSpecifiation mapping) {
		create.reaction('''«mapping.participation.name»«mapping.attribute.name.toFirstUpper»ElementRemove''').
			afterElement.removedFrom(mapping.participationAttributeChangeClass, mapping.participationEReference)
			.call [
				input [newValue]
				.match [
					retrieveIntermediate()
				]
				.action [
					update('intermediate') [
						removeFromFeatureList(variable('intermediate'), mapping.declaringAttribute.name, newValue)
					]
				]
			]
	}
	
	def private getParticipationAttributeChangeClass(AttributeMappingSpecifiation mapping) {
		mapping.attribute.participationClass.changeClass
	}
	
	def private getParticipationEReference(AttributeMappingSpecifiation mapping) {
		mapping.attribute.EFeatureToReference as EReference
	}
	
	def private getParticipationEAttribute(AttributeMappingSpecifiation mapping) {
		mapping.attribute.EFeatureToReference as EAttribute
	}

	def private retrieveIntermediate(extension UndecidedMatcherStatementBuilder builder) {
		vall('intermediate').retrieve(commonalityFile.changeClass).correspondingTo.affectedEObject
	}

	def private static setFeature(extension RoutineTypeProvider typeProvider, XFeatureCall element,
		String attributeName, XExpression newValue) {
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			memberCallTarget = element.newFeatureCall
			feature = typeProvider.findMethod(EObject, 'eSet')
			explicitOperationCall = true
			memberCallArguments += getEFeature(typeProvider, element, attributeName)
			memberCallArguments += newValue
		]
	}

	def private static addToFeatureList(extension RoutineTypeProvider typeProvider, XFeatureCall element,
		String attributeName, XExpression newValue) {
		XbaseFactory.eINSTANCE.createXBinaryOperation => [
			leftOperand = getEFeature(typeProvider, element, attributeName)
			feature = typeProvider.findMethod(CollectionExtensions, 'operator_add', Collection, typeVariable)
			rightOperand = newValue
		]
	}

	def private static removeFromFeatureList(extension RoutineTypeProvider typeProvider, XFeatureCall element,
		String attributeName, XExpression newValue) {
		XbaseFactory.eINSTANCE.createXBinaryOperation => [
			leftOperand = getEFeature(typeProvider, element, attributeName)
			feature = typeProvider.findMethod(CollectionExtensions, 'operator_remove', Collection, typeVariable)
			rightOperand = newValue
		]
	}

	def private static getEFeature(extension RoutineTypeProvider typeProvider, XFeatureCall element,
		String featureName) {
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			memberCallTarget = XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
				memberCallTarget = element.newFeatureCall
				feature = typeProvider.findMethod(EObject, 'eClass')
			]
			feature = typeProvider.findMethod(EClass, 'getEStructuralFeature', String)
			explicitOperationCall = true
			memberCallArguments += XbaseFactory.eINSTANCE.createXStringLiteral => [
				value = featureName
			]
		]
	}
}
