package tools.vitruv.dsls.commonalities.generator

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.Collection
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.xtext.common.types.TypesFactory
import org.eclipse.xtext.xbase.XAbstractFeatureCall
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.reactions.builder.TypeProvider

import static tools.vitruv.dsls.commonalities.generator.XbaseCollectionHelper.*

import static extension tools.vitruv.dsls.commonalities.generator.JvmTypeProviderHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.ReactionsHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.XbaseHelper.*

@Utility
package class EmfAccessExpressions {

	def private static eGetFeatureValue(extension TypeProvider typeProvider, XAbstractFeatureCall element,
		String featureName) {
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			memberCallTarget = element
			feature = typeProvider.findMethod(EObject, 'eGet', 1, EStructuralFeature)
			explicitOperationCall = true
			memberCallArguments += getEFeature(typeProvider, element, featureName)
		]
	}

	def private static eSetFeatureValue(extension TypeProvider typeProvider, XAbstractFeatureCall element,
		String featureName, XExpression newValue) {
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			memberCallTarget = element
			feature = typeProvider.findMethod(EObject, 'eSet')
			explicitOperationCall = true
			memberCallArguments += getEFeature(typeProvider, element, featureName)
			memberCallArguments += newValue
		]
	}

	def private static eGetListFeatureValue(extension TypeProvider typeProvider, XAbstractFeatureCall element,
		String featureName) {
		XbaseFactory.eINSTANCE.createXCastedExpression => [
			type = TypesFactory.eINSTANCE.createJvmParameterizedTypeReference => [
				type = typeProvider.findType(Collection)
				arguments += TypesFactory.eINSTANCE.createJvmParameterizedTypeReference => [
					type = typeProvider.findType(Object)
				]
			]
			target = eGetFeatureValue(typeProvider, element, featureName)
		]
	}

	def private static eAddToListFeatureValue(extension TypeProvider typeProvider, XAbstractFeatureCall element,
		String featureName, XExpression newValue) {
		val getList = eGetListFeatureValue(typeProvider, element, featureName)
		addToCollection(typeProvider, getList, newValue)
	}

	def private static eRemoveFromListFeatureValue(extension TypeProvider typeProvider, XAbstractFeatureCall element,
		String featureName, XExpression newValue) {
		val getList = eGetListFeatureValue(typeProvider, element, featureName)
		removeFromCollection(typeProvider, getList, newValue)
	}

	def private static eSetListFeatureValue(extension TypeProvider typeProvider, XAbstractFeatureCall element,
		String featureName, XExpression newValues) {
		val getList = eGetListFeatureValue(typeProvider, element, featureName)
		XbaseFactory.eINSTANCE.createXBlockExpression => [
			expressions += expressions(
				clearCollection(typeProvider, getList),
				addAllToCollection(typeProvider, getList.copy, newValues)
			)
		]
	}

	def package static getFeatureValue(extension TypeProvider typeProvider, XAbstractFeatureCall element,
		EStructuralFeature eFeature) {
		try {
			// try to guess the accessor:
			return getEFeatureValue(typeProvider, element, eFeature)
		} catch (NoSuchJvmElementException e) {
			// if that fails, use EMF's reflection:
			return eGetFeatureValue(typeProvider, element, eFeature.name)
		}
	}

	def package static setFeatureValue(extension TypeProvider typeProvider, XAbstractFeatureCall element,
		EStructuralFeature eFeature, XExpression newValue) {
		try {
			// try to guess the accessor:
			val containingInstanceClassName = eFeature.EContainingClass.javaClassName
			if (containingInstanceClassName === null) {
				throw new RuntimeException('''Containing instance class name is null!''')
			}
			return XbaseFactory.eINSTANCE.createXAssignment => [
				assignable = element
				feature = typeProvider.findMethod(containingInstanceClassName, 'set' + eFeature.name.toFirstUpper)
				value = newValue
			]
		} catch (NoSuchJvmElementException e) {
			// if that fails, use EMF's reflection:
			return eSetFeatureValue(typeProvider, element, eFeature.name, newValue)
		}
	}

	def package static getListFeatureValue(extension TypeProvider typeProvider, XAbstractFeatureCall element,
		EStructuralFeature eFeature) {
		try {
			// try to guess the accessor:
			return getEFeatureValue(typeProvider, element, eFeature)
		} catch (NoSuchJvmElementException e) {
			// if that fails, use EMF's reflection:
			return eGetListFeatureValue(typeProvider, element, eFeature.name)
		}
	}

	def package static addToListFeatureValue(extension TypeProvider typeProvider, XAbstractFeatureCall element,
		EStructuralFeature eFeature, XExpression newValue) {
		try {
			// try to guess the accessor:
			val getList = getEFeatureValue(typeProvider, element, eFeature)
			return addToCollection(typeProvider, getList, newValue)
		} catch (NoSuchJvmElementException e) {
			// if that fails, use EMF's reflection:
			return eAddToListFeatureValue(typeProvider, element, eFeature.name, newValue)
		}
	}

	def package static removeFromListFeatureValue(extension TypeProvider typeProvider, XAbstractFeatureCall element,
		EStructuralFeature eFeature, XExpression newValue) {
		try {
			// try to guess the accessor:
			val getList = getEFeatureValue(typeProvider, element, eFeature)
			return removeFromCollection(typeProvider, getList, newValue)
		} catch (NoSuchJvmElementException e) {
			// if that fails, use EMF's reflection:
			return eRemoveFromListFeatureValue(typeProvider, element, eFeature.name, newValue)
		}
	}

	def package static setListFeatureValue(extension TypeProvider typeProvider, XAbstractFeatureCall element,
		EStructuralFeature eFeature, XExpression newValues) {
		try {
			// try to guess the accessor:
			val getList = getEFeatureValue(typeProvider, element, eFeature)
			XbaseFactory.eINSTANCE.createXBlockExpression => [
				expressions += expressions(
					clearCollection(typeProvider, getList),
					addAllToCollection(typeProvider, getList.copy, newValues)
				)
			]
		} catch (NoSuchJvmElementException e) {
			// if that fails, use EMF's reflection:
			return eSetListFeatureValue(typeProvider, element, eFeature.name, newValues)
		}
	}

	// throws NoSuchJvmElementException on failure
	def private static getEFeatureValue(extension TypeProvider typeProvider, XAbstractFeatureCall element,
		EStructuralFeature eFeature) {
		val containingInstanceClassName = eFeature.EContainingClass.javaClassName
		if (containingInstanceClassName === null) {
			throw new RuntimeException('''Containing instance class name is null!''')
		}
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			memberCallTarget = element
			feature = typeProvider.findMethod(containingInstanceClassName, 'get' + eFeature.name.toFirstUpper, 0)
		]
	}

	def package static getEFeature(extension TypeProvider typeProvider, XAbstractFeatureCall element,
		String featureName) {
		// TODO try to guess feature literal or package accessor?
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			memberCallTarget = XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
				memberCallTarget = element
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
}
