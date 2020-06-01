package tools.vitruv.dsls.commonalities.generator

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.Collection
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.xtext.common.types.TypesFactory
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.reactions.builder.TypeProvider
import tools.vitruv.extensions.dslruntime.commonalities.EmfAccess

import static tools.vitruv.dsls.commonalities.generator.XbaseCollectionHelper.*

import static extension tools.vitruv.dsls.commonalities.generator.JvmTypeProviderHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.ReactionsHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.XbaseHelper.*

@Utility
package class EmfAccessExpressions {

	def private static eGetFeatureValue(extension TypeProvider typeProvider, XExpression object, String featureName) {
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			memberCallTarget = object
			feature = typeProvider.findMethod(EObject, 'eGet', 1, EStructuralFeature)
			explicitOperationCall = true
			memberCallArguments += getEFeature(typeProvider, object.copy, featureName)
		]
	}

	def private static eSetFeatureValue(extension TypeProvider typeProvider,  XExpression object, String featureName,
		XExpression newValue) {
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			memberCallTarget = object
			feature = typeProvider.findMethod(EObject, 'eSet')
			explicitOperationCall = true
			memberCallArguments += getEFeature(typeProvider, object.copy, featureName)
			memberCallArguments += newValue
		]
	}

	def private static eGetListFeatureValue(extension TypeProvider typeProvider, XExpression object,
		String featureName) {
		XbaseFactory.eINSTANCE.createXCastedExpression => [
			type = TypesFactory.eINSTANCE.createJvmParameterizedTypeReference => [
				type = typeProvider.findType(Collection)
				arguments += TypesFactory.eINSTANCE.createJvmParameterizedTypeReference => [
					type = typeProvider.findType(Object)
				]
			]
			target = eGetFeatureValue(typeProvider, object, featureName)
		]
	}

	def private static eAddListFeatureValue(extension TypeProvider typeProvider, XExpression object,
		String featureName, XExpression newValue) {
		val getList = eGetListFeatureValue(typeProvider, object, featureName)
		addToCollection(typeProvider, getList, newValue)
	}

	def private static eRemoveListFeatureValue(extension TypeProvider typeProvider, XExpression object,
		String featureName, XExpression newValue) {
		val getList = eGetListFeatureValue(typeProvider, object, featureName)
		removeFromCollection(typeProvider, getList, newValue)
	}

	def private static eSetListFeatureValue(extension TypeProvider typeProvider, XExpression object,
		String featureName, XExpression newValues) {
		val getList = eGetListFeatureValue(typeProvider, object, featureName)
		// See setListFeatureValue regarding why this check is required:
		return XbaseFactory.eINSTANCE.createXIfExpression => [
			^if = getList.notEquals(newValues, typeProvider)
			then = XbaseFactory.eINSTANCE.createXBlockExpression => [
				expressions += expressions(
					clearCollection(typeProvider, getList.copy),
					addAllToCollection(typeProvider, getList.copy, newValues.copy)
				)
			]
		]
	}

	def package static getFeatureValue(extension TypeProvider typeProvider, XExpression object,
		EStructuralFeature eFeature) {
		try {
			// try to guess the accessor:
			return getEFeatureValue(typeProvider, object, eFeature)
		} catch (NoSuchJvmElementException e) {
			// if that fails, use EMF's reflection:
			return eGetFeatureValue(typeProvider, object, eFeature.name)
		}
	}

	def package static setFeatureValue(extension TypeProvider typeProvider, XExpression object,
		EStructuralFeature eFeature, XExpression newValue) {
		try {
			// try to guess the accessor:
			val containingInstanceClassName = eFeature.EContainingClass.javaClassName
			if (containingInstanceClassName === null) {
				throw new RuntimeException('''Containing instance class name is null!''')
			}
			return XbaseFactory.eINSTANCE.createXAssignment => [
				assignable = object
				feature = typeProvider.findMethod(containingInstanceClassName, 'set' + eFeature.name.toFirstUpper)
				value = newValue
			]
		} catch (NoSuchJvmElementException e) {
			// if that fails, use EMF's reflection:
			return eSetFeatureValue(typeProvider, object, eFeature.name, newValue)
		}
	}

	def package static getListFeatureValue(extension TypeProvider typeProvider, XExpression object,
		EStructuralFeature eFeature) {
		try {
			// try to guess the accessor:
			return getEFeatureValue(typeProvider, object, eFeature)
		} catch (NoSuchJvmElementException e) {
			// if that fails, use EMF's reflection:
			return eGetListFeatureValue(typeProvider, object, eFeature.name)
		}
	}

	def package static addListFeatureValue(extension TypeProvider typeProvider, XExpression object,
		EStructuralFeature eFeature, XExpression newValue) {
		try {
			// try to guess the accessor:
			val getList = getEFeatureValue(typeProvider, object, eFeature)
			return addToCollection(typeProvider, getList, newValue)
		} catch (NoSuchJvmElementException e) {
			// if that fails, use EMF's reflection:
			return eAddListFeatureValue(typeProvider, object, eFeature.name, newValue)
		}
	}

	def package static removeListFeatureValue(extension TypeProvider typeProvider, XExpression object,
		EStructuralFeature eFeature, XExpression newValue) {
		try {
			// try to guess the accessor:
			val getList = getEFeatureValue(typeProvider, object, eFeature)
			return removeFromCollection(typeProvider, getList, newValue)
		} catch (NoSuchJvmElementException e) {
			// if that fails, use EMF's reflection:
			return eRemoveListFeatureValue(typeProvider, object, eFeature.name, newValue)
		}
	}

	def package static setListFeatureValue(extension TypeProvider typeProvider, XExpression object,
		EStructuralFeature eFeature, XExpression newValues) {
		try {
			// try to guess the accessor:
			val getList = getEFeatureValue(typeProvider, object, eFeature)
			// Replacing the values of a multi-valued feature involves clearing the old values and then adding all new
			// values. These two operations result in two separate changes being propagated. To avoid cyclic change
			// propagations Vitruv ignores any changes which did not actually change anything. Even though the values
			// might end up being the same as before, this check of Vitruv fails for these individual changes. We
			// therefore compare the new and old feature values ourselves before we apply the change.
			return XbaseFactory.eINSTANCE.createXIfExpression => [
				^if = getList.notEquals(newValues, typeProvider)
				then = XbaseFactory.eINSTANCE.createXBlockExpression => [
					expressions += expressions(
						clearCollection(typeProvider, getList.copy),
						addAllToCollection(typeProvider, getList.copy, newValues.copy)
					)
				]
			]
		} catch (NoSuchJvmElementException e) {
			// if that fails, use EMF's reflection:
			return eSetListFeatureValue(typeProvider, object, eFeature.name, newValues)
		}
	}

	// throws NoSuchJvmElementException on failure
	def private static getEFeatureValue(extension TypeProvider typeProvider, XExpression object,
		EStructuralFeature eFeature) {
		val containingInstanceClassName = eFeature.EContainingClass.javaClassName
		if (containingInstanceClassName === null) {
			throw new RuntimeException('''Containing instance class name is null!''')
		}
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			memberCallTarget = object
			feature = typeProvider.findMethod(containingInstanceClassName, 'get' + eFeature.name.toFirstUpper, 0)
		]
	}

	/*
	 * Adaptive feature operations, depending on whether the feature is multi-valued or not:
	 */

	def package static retrieveFeatureValue(extension TypeProvider typeProvider, XExpression object,
		EStructuralFeature eFeature) {
		if (eFeature.many) {
			getListFeatureValue(typeProvider, object, eFeature)
		} else {
			getFeatureValue(typeProvider, object, eFeature)
		}
	}

	def package static replaceFeatureValue(extension TypeProvider typeProvider, XExpression object,
		EStructuralFeature eFeature, XExpression newValue) {
		if (eFeature.many) {
			setListFeatureValue(typeProvider, object, eFeature, newValue)
		} else {
			setFeatureValue(typeProvider, object, eFeature, newValue)
		}
	}

	def package static insertFeatureValue(extension TypeProvider typeProvider, XExpression object,
		EStructuralFeature eFeature, XExpression newValue) {
		if (eFeature.many) {
			addListFeatureValue(typeProvider, object, eFeature, newValue)
		} else {
			setFeatureValue(typeProvider, object, eFeature, newValue)
		}
	}

	/*
	 * Reflective retrieval of EMF meta objects:
	 */

	def package static getEFeature(extension TypeProvider typeProvider, XExpression object, String featureName) {
		// TODO try to guess feature literal or package accessor?
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			memberCallTarget = XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
				memberCallTarget = object
				feature = typeProvider.findMethod(EObject, 'eClass')
			]
			feature = typeProvider.findMethod(EClass, 'getEStructuralFeature', String)
			explicitOperationCall = true
			memberCallArguments += XbaseFactory.eINSTANCE.createXStringLiteral => [
				value = featureName
			]
		]
	}

	def static getEPackage(extension TypeProvider typeProvider, EPackage ePackage) {
		val emfAccessType = typeProvider.findDeclaredType(EmfAccess)
		return emfAccessType.memberFeatureCall => [
			staticWithDeclaringType = true
			feature = emfAccessType.findMethod("getEPackage", String)
			memberCallArguments += stringLiteral(ePackage.nsURI)
		]
	}

	def static getEClass(extension TypeProvider typeProvider, EClass eClass) {
		val emfAccessType = typeProvider.findDeclaredType(EmfAccess)
		return emfAccessType.memberFeatureCall => [
			staticWithDeclaringType = true
			feature = emfAccessType.findMethod("getEClass", String, String)
			memberCallArguments += expressions(
				stringLiteral(eClass.EPackage.nsURI),
				stringLiteral(eClass.name)
			)
		]
	}

	def static getEFeature(extension TypeProvider typeProvider, EStructuralFeature eFeature) {
		val containingEClass = eFeature.EContainingClass
		val emfAccessType = typeProvider.findDeclaredType(EmfAccess)
		return emfAccessType.memberFeatureCall => [
			staticWithDeclaringType = true
			feature = emfAccessType.findMethod("getEFeature", String, String, String)
			memberCallArguments += expressions(
				stringLiteral(containingEClass.EPackage.nsURI),
				stringLiteral(containingEClass.name),
				stringLiteral(eFeature.name)
			)
		]
	}

	def static getEReference(extension TypeProvider typeProvider, EReference eReference) {
		val containingEClass = eReference.EContainingClass
		val emfAccessType = typeProvider.findDeclaredType(EmfAccess)
		return emfAccessType.memberFeatureCall => [
			staticWithDeclaringType = true
			feature = emfAccessType.findMethod("getEReference", String, String, String)
			memberCallArguments += expressions(
				stringLiteral(containingEClass.EPackage.nsURI),
				stringLiteral(containingEClass.name),
				stringLiteral(eReference.name)
			)
		]
	}

	def static getEAttribute(extension TypeProvider typeProvider, EAttribute eAttribute) {
		val containingEClass = eAttribute.EContainingClass
		val emfAccessType = typeProvider.findDeclaredType(EmfAccess)
		return emfAccessType.memberFeatureCall => [
			staticWithDeclaringType = true
			feature = emfAccessType.findMethod("getEAttribute", String, String, String)
			memberCallArguments += expressions(
				stringLiteral(containingEClass.EPackage.nsURI),
				stringLiteral(containingEClass.name),
				stringLiteral(eAttribute.name)
			)
		]
	}

	/*
	 * Containment operations:
	 */

	def static getEContainer(extension TypeProvider typeProvider, XExpression object) {
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			memberCallTarget = object
			explicitOperationCall = true
			feature = typeProvider.findMethod(EObject, 'eContainer', 0)
		]
	}

	def static getEContainmentFeature(extension TypeProvider typeProvider, XExpression object) {
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			memberCallTarget = object
			explicitOperationCall = true
			feature = typeProvider.findMethod(EObject, 'eContainmentFeature', 0)
		]
	}
}
