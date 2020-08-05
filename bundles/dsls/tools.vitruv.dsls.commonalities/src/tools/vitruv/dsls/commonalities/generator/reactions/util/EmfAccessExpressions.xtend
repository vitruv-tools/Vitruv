package tools.vitruv.dsls.commonalities.generator.reactions.util

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.List
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.xtext.common.types.TypesFactory
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.commonalities.util.EMFJavaTypesUtil
import tools.vitruv.dsls.reactions.builder.TypeProvider
import tools.vitruv.extensions.dslruntime.commonalities.EmfAccess

import static tools.vitruv.dsls.commonalities.generator.reactions.util.XbaseCollectionHelper.*

import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.JvmTypeProviderHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.ReactionsHelper.*
import static extension tools.vitruv.dsls.commonalities.generator.reactions.util.XbaseHelper.*

@Utility
class EmfAccessExpressions {

	private static def eGetFeatureValue(extension TypeProvider typeProvider, XExpression object, EStructuralFeature eFeature) {
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			memberCallTarget = object
			feature = typeProvider.findMethod(EObject, 'eGet', 1, EStructuralFeature)
			explicitOperationCall = true
			memberCallArguments += getEFeature(typeProvider, object.copy, eFeature)
		]
	}

	private static def eSetFeatureValue(extension TypeProvider typeProvider,  XExpression object, EStructuralFeature eFeature,
		XExpression newValue) {
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			memberCallTarget = object
			feature = typeProvider.findMethod(EObject, 'eSet')
			explicitOperationCall = true
			memberCallArguments += getEFeature(typeProvider, object.copy, eFeature)
			memberCallArguments += newValue
		]
	}

	private static def eGetListFeatureValue(extension TypeProvider typeProvider, XExpression object,
		EStructuralFeature eFeature) {
		XbaseFactory.eINSTANCE.createXCastedExpression => [
			type = TypesFactory.eINSTANCE.createJvmParameterizedTypeReference => [
				type = typeProvider.findType(List)
				arguments += typeProvider.jvmTypeReferenceBuilder.typeRef(eFeature.EType.javaClassName)
			]
			target = eGetFeatureValue(typeProvider, object, eFeature)
		]
	}

	private static def eAddListFeatureValue(extension TypeProvider typeProvider, XExpression object,
		EStructuralFeature eFeature, XExpression newValue) {
		val getList = eGetListFeatureValue(typeProvider, object, eFeature)
		addToCollection(typeProvider, getList, newValue)
	}

	private static def eRemoveListFeatureValue(extension TypeProvider typeProvider, XExpression object,
		EStructuralFeature eFeature, XExpression newValue) {
		val getList = eGetListFeatureValue(typeProvider, object, eFeature)
		removeFromCollection(typeProvider, getList, newValue)
	}

	private static def eSetListFeatureValue(extension TypeProvider typeProvider, XExpression object,
		EStructuralFeature eFeature, XExpression newValues) {
		val getList = eGetListFeatureValue(typeProvider, object, eFeature)
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

	static def getFeatureValue(extension TypeProvider typeProvider, XExpression object,
		EStructuralFeature eFeature) {
		try {
			// try to guess the accessor:
			return getEFeatureValue(typeProvider, object, eFeature)
		} catch (NoSuchJvmElementException e) {
			// if that fails, use EMF's reflection:
			return XbaseFactory.eINSTANCE.createXCastedExpression => [
				// For primitive types this cast does not get properly translated to Java code. We therefore cast to
				// their corresponding primitive wrapper types instead.
				val eType = EMFJavaTypesUtil.wrapJavaPrimitiveTypes(eFeature.EType)
				type = typeProvider.jvmTypeReferenceBuilder.typeRef(eType.javaClassName)
				target = eGetFeatureValue(typeProvider, object, eFeature)
			]
		}
	}

	static def setFeatureValue(extension TypeProvider typeProvider, XExpression object,
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
			return eSetFeatureValue(typeProvider, object, eFeature, newValue)
		}
	}

	static def getListFeatureValue(extension TypeProvider typeProvider, XExpression object,
		EStructuralFeature eFeature) {
		try {
			// try to guess the accessor:
			return getEFeatureValue(typeProvider, object, eFeature)
		} catch (NoSuchJvmElementException e) {
			// if that fails, use EMF's reflection:
			return eGetListFeatureValue(typeProvider, object, eFeature)
		}
	}

	static def addListFeatureValue(extension TypeProvider typeProvider, XExpression object,
		EStructuralFeature eFeature, XExpression newValue) {
		try {
			// try to guess the accessor:
			val getList = getEFeatureValue(typeProvider, object, eFeature)
			return addToCollection(typeProvider, getList, newValue)
		} catch (NoSuchJvmElementException e) {
			// if that fails, use EMF's reflection:
			return eAddListFeatureValue(typeProvider, object, eFeature, newValue)
		}
	}

	static def removeListFeatureValue(extension TypeProvider typeProvider, XExpression object,
		EStructuralFeature eFeature, XExpression newValue) {
		try {
			// try to guess the accessor:
			val getList = getEFeatureValue(typeProvider, object, eFeature)
			return removeFromCollection(typeProvider, getList, newValue)
		} catch (NoSuchJvmElementException e) {
			// if that fails, use EMF's reflection:
			return eRemoveListFeatureValue(typeProvider, object, eFeature, newValue)
		}
	}

	static def setListFeatureValue(extension TypeProvider typeProvider, XExpression object,
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
			return eSetListFeatureValue(typeProvider, object, eFeature, newValues)
		}
	}

	// Tries to guess the accessor method name.
	// Throws NoSuchJvmElementException on failure.
	private static def getEFeatureValue(extension TypeProvider typeProvider, XExpression object,
		EStructuralFeature eFeature) {
		val containingInstanceClassName = eFeature.EContainingClass.javaClassName
		if (containingInstanceClassName === null) {
			throw new RuntimeException('''Containing instance class name is null!''')
		}
		val instanceClassName = eFeature.EType.instanceClassName
		val isBooleanType = (instanceClassName == boolean.name || instanceClassName == Boolean.name)
		var List<String> candidateAccessorPrefixes
		if (isBooleanType) {
			// Empty prefix example: UML2::Class#isFinalSpecialization(boolean)
			candidateAccessorPrefixes = #['is', '']
		} else {
			candidateAccessorPrefixes = #['get']
		}

		// Plural form, eg. UML2::BehavioralFeature#getOwnedParameters
		val candidateAccessorSuffixes = #['', 's']

		val candidateAccessors = candidateAccessorPrefixes.flatMap [ accessorPrefix |
			val prefixedAccessorName = accessorPrefix + (accessorPrefix.empty ? eFeature.name : eFeature.name.toFirstUpper)
			return candidateAccessorSuffixes.map [ accessorSuffix |
				val accessorName = prefixedAccessorName + accessorSuffix
				return accessorName
			]
		]

		val accessorMethod = candidateAccessors.map [ accessorName |
			try {
				return typeProvider.findMethod(containingInstanceClassName, accessorName, 0)
			} catch (NoSuchJvmElementException e) {
				return null
			}
		].filterNull.head
		if (accessorMethod === null) {
			throw new NoSuchJvmElementException('''Could not guess accessor for feature '«eFeature.name»'.''')
		}

		return XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			memberCallTarget = object
			feature = accessorMethod
		]
	}

	/*
	 * Adaptive feature operations, depending on whether the feature is multi-valued or not:
	 */

	static def retrieveFeatureValue(extension TypeProvider typeProvider, XExpression object,
		EStructuralFeature eFeature) {
		if (eFeature.many) {
			getListFeatureValue(typeProvider, object, eFeature)
		} else {
			getFeatureValue(typeProvider, object, eFeature)
		}
	}

	static def replaceFeatureValue(extension TypeProvider typeProvider, XExpression object,
		EStructuralFeature eFeature, XExpression newValue) {
		if (eFeature.many) {
			setListFeatureValue(typeProvider, object, eFeature, newValue)
		} else {
			setFeatureValue(typeProvider, object, eFeature, newValue)
		}
	}

	static def insertFeatureValue(extension TypeProvider typeProvider, XExpression object,
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

	static def getEFeature(extension TypeProvider typeProvider, XExpression object, EStructuralFeature eFeature) {
		// TODO try to guess feature literal or package accessor?
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			memberCallTarget = XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
				memberCallTarget = object
				feature = typeProvider.findMethod(EObject, 'eClass')
			]
			feature = typeProvider.findMethod(EClass, 'getEStructuralFeature', String)
			explicitOperationCall = true
			memberCallArguments += XbaseFactory.eINSTANCE.createXStringLiteral => [
				value = eFeature.name
			]
		]
	}

	static def getEPackage(extension TypeProvider typeProvider, EPackage ePackage) {
		val emfAccessType = typeProvider.findDeclaredType(EmfAccess)
		return emfAccessType.memberFeatureCall => [
			staticWithDeclaringType = true
			feature = emfAccessType.findMethod('getEPackage', String)
			memberCallArguments += stringLiteral(ePackage.nsURI)
		]
	}

	static def getEClass(extension TypeProvider typeProvider, EClass eClass) {
		val emfAccessType = typeProvider.findDeclaredType(EmfAccess)
		return emfAccessType.memberFeatureCall => [
			staticWithDeclaringType = true
			feature = emfAccessType.findMethod('getEClass', String, String)
			memberCallArguments += expressions(
				stringLiteral(eClass.EPackage.nsURI),
				stringLiteral(eClass.name)
			)
		]
	}

	static def getEFeature(extension TypeProvider typeProvider, EStructuralFeature eFeature) {
		val containingEClass = eFeature.EContainingClass
		val emfAccessType = typeProvider.findDeclaredType(EmfAccess)
		return emfAccessType.memberFeatureCall => [
			staticWithDeclaringType = true
			feature = emfAccessType.findMethod('getEFeature', String, String, String)
			memberCallArguments += expressions(
				stringLiteral(containingEClass.EPackage.nsURI),
				stringLiteral(containingEClass.name),
				stringLiteral(eFeature.name)
			)
		]
	}

	static def getEReference(extension TypeProvider typeProvider, EReference eReference) {
		val containingEClass = eReference.EContainingClass
		val emfAccessType = typeProvider.findDeclaredType(EmfAccess)
		return emfAccessType.memberFeatureCall => [
			staticWithDeclaringType = true
			feature = emfAccessType.findMethod('getEReference', String, String, String)
			memberCallArguments += expressions(
				stringLiteral(containingEClass.EPackage.nsURI),
				stringLiteral(containingEClass.name),
				stringLiteral(eReference.name)
			)
		]
	}

	static def getEAttribute(extension TypeProvider typeProvider, EAttribute eAttribute) {
		val containingEClass = eAttribute.EContainingClass
		val emfAccessType = typeProvider.findDeclaredType(EmfAccess)
		return emfAccessType.memberFeatureCall => [
			staticWithDeclaringType = true
			feature = emfAccessType.findMethod('getEAttribute', String, String, String)
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

	static def getEContainer(extension TypeProvider typeProvider, XExpression object) {
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			memberCallTarget = object
			explicitOperationCall = true
			feature = typeProvider.findMethod(EObject, 'eContainer', 0)
		]
	}

	static def getEContainmentFeature(extension TypeProvider typeProvider, XExpression object) {
		XbaseFactory.eINSTANCE.createXMemberFeatureCall => [
			memberCallTarget = object
			explicitOperationCall = true
			feature = typeProvider.findMethod(EObject, 'eContainmentFeature', 0)
		]
	}
}
