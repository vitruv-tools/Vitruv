package tools.vitruv.dsls.commonalities.generator

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.Collection
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.xtext.common.types.TypesFactory
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XFeatureCall
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineTypeProvider

import static extension tools.vitruv.dsls.commonalities.generator.JvmTypeProviderHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.XbaseHelper.*

@Utility
class EmfAccessExpressions {
	def package static eSetFeature(extension RoutineTypeProvider typeProvider, XFeatureCall element,
		String attributeName, XExpression newValue) {
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			memberCallTarget = element.newFeatureCall
			feature = typeProvider.findMethod(EObject, 'eSet')
			explicitOperationCall = true
			memberCallArguments += getEFeature(typeProvider, element, attributeName)
			memberCallArguments += newValue
		]
	}

	def package static eAddToFeatureList(extension RoutineTypeProvider typeProvider, XFeatureCall element,
		String attributeName, XExpression newValue) {
		val featureListVariable = getFeatureList(typeProvider, element, attributeName)
		XbaseFactory.eINSTANCE.createXBlockExpression => [
			expressions += expressions(
				featureListVariable,
				XbaseFactory.eINSTANCE.createXBinaryOperation => [
					leftOperand = XbaseFactory.eINSTANCE.createXFeatureCall => [
						feature = featureListVariable
					]
					feature = typeProvider.findMethod(CollectionExtensions, 'operator_add', Collection, typeVariable)
					rightOperand = newValue
				]
			)
		]
	}

	def package static eRemoveFromFeatureList(extension RoutineTypeProvider typeProvider, XFeatureCall element,
		String attributeName, XExpression newValue) {
		val featureListVariable = getFeatureList(typeProvider, element, attributeName)
		XbaseFactory.eINSTANCE.createXBlockExpression => [
			expressions += expressions(
				featureListVariable,
				XbaseFactory.eINSTANCE.createXBinaryOperation => [
					leftOperand = XbaseFactory.eINSTANCE.createXFeatureCall => [
						feature = featureListVariable
					]
					feature = typeProvider.findMethod(CollectionExtensions, 'operator_remove', Collection, typeVariable)
					rightOperand = newValue
				]
			)
		]
	}

	def package static setFeature(extension RoutineTypeProvider typeProvider, XFeatureCall element,
		EStructuralFeature eFeature, XExpression newValue) {
		try {
			// trying to guess the accessors …
			val containingInstanceClassName = eFeature.EContainingClass.instanceClassName
			if (containingInstanceClassName !== null) {
				return XbaseFactory.eINSTANCE.createXAssignment => [
					assignable = element
					feature = typeProvider.findMethod(containingInstanceClassName, 'set' + eFeature.name.toFirstUpper)
					value = newValue
				]
			}
		} catch (NoSuchJvmElementException e) {
		}
		// if that fails or is not possible, use EMF’s reflection
		return eSetFeature(typeProvider, element, eFeature.name, newValue)
	}

	def package static addToFeatureList(extension RoutineTypeProvider typeProvider, XFeatureCall element,
		EStructuralFeature eFeature, XExpression newValue) {
		try {
			// trying to guess the accesors …
			XbaseFactory.eINSTANCE.createXBinaryOperation => [
				leftOperand = getEFeature(typeProvider, element, eFeature)
				feature = typeProvider.findMethod(CollectionExtensions, 'operator_add', Collection, typeVariable)
				rightOperand = newValue
			]
		} catch (NoSuchJvmElementException e) {
			// if that files, use EMF’s reflection
			return eAddToFeatureList(typeProvider, element, eFeature.name, newValue)
		}
	}

	def package static removeFromFeatureList(extension RoutineTypeProvider typeProvider, XFeatureCall element,
		EStructuralFeature eFeature, XExpression newValue) {
		try {
			// trying to guess the accesors …
			XbaseFactory.eINSTANCE.createXBinaryOperation => [
				leftOperand = getEFeature(typeProvider, element, eFeature)
				feature = typeProvider.findMethod(CollectionExtensions, 'operator_remove', Collection, typeVariable)
				rightOperand = newValue
			]
		} catch (NoSuchJvmElementException e) {
			// if that files, use EMF’s reflection
			return eRemoveFromFeatureList(typeProvider, element, eFeature.name, newValue)
		}
	}

	def private static getFeatureList(extension RoutineTypeProvider typeProvider, XFeatureCall element,
		String featureListName) {
		XbaseFactory.eINSTANCE.createXVariableDeclaration => [
			name = featureListName
			right = XbaseFactory.eINSTANCE.createXCastedExpression => [
				type = TypesFactory.eINSTANCE.createJvmParameterizedTypeReference => [
					type = typeProvider.findType(Collection)
					arguments += TypesFactory.eINSTANCE.createJvmParameterizedTypeReference => [
						type = typeProvider.findType(Object)
					]
				]
				target = XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
					memberCallTarget = element.newFeatureCall
					feature = typeProvider.findMethod(EObject, 'eGet', 1, EStructuralFeature)
					explicitOperationCall = true
					memberCallArguments += getEFeature(typeProvider, element, featureListName)
				]
			]
		]
	}

	def private static getEFeature(extension RoutineTypeProvider typeProvider, XFeatureCall element,
		String featureName) {
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			memberCallTarget = XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
				memberCallTarget = element.newFeatureCall
				feature = typeProvider.findMethod(EObject, 'eClass')
				concreteSyntaxFeatureName	
			]
			feature = typeProvider.findMethod(EClass, 'getEStructuralFeature', String)
			explicitOperationCall = true
			memberCallArguments += XbaseFactory.eINSTANCE.createXStringLiteral => [
				value = featureName
			]
		]
	}

	def private static getEFeature(extension RoutineTypeProvider typeProvider, XFeatureCall element,
		EStructuralFeature eFeature) {
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			memberCallTarget = element
			feature = typeProvider.findMethod(eFeature.EContainingClass.instanceClassName,
				'get' + eFeature.name.toFirstUpper, 0)
		]
	}
}
