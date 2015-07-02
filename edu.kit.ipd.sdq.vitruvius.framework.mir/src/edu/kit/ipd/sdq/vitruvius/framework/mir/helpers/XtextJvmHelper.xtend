package edu.kit.ipd.sdq.vitruvius.framework.mir.helpers

import edu.kit.ipd.sdq.vitruvius.framework.mir.inferrer.InferrenceEMFHelper
import java.util.HashSet
import org.eclipse.emf.common.util.WrappedException
import org.eclipse.emf.ecore.EPackage
import org.eclipse.xtext.common.types.JvmDeclaredType
import org.eclipse.xtext.common.types.JvmFormalParameter
import org.eclipse.xtext.common.types.JvmIdentifiableElement
import org.eclipse.xtext.common.types.JvmOperation
import org.eclipse.xtext.xbase.XBooleanLiteral
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XFeatureCall
import org.eclipse.xtext.xbase.XMemberFeatureCall
import org.eclipse.xtext.xbase.XNullLiteral
import org.eclipse.xtext.xbase.XNumberLiteral
import org.eclipse.xtext.xbase.XStringLiteral
import org.eclipse.xtext.xbase.XTypeLiteral

class XtextJvmHelper {
	/**
	 * Returns true if equality for the given literal is checked with "==" instead of
	 * ".equals(...)"
	 */
	public static def boolean needsSimpleEqualityOperator(XExpression expression) {
		return
			(expression instanceof XBooleanLiteral)
			|| (expression instanceof XNumberLiteral)
			|| (expression instanceof XNullLiteral)
			|| (expression instanceof XTypeLiteral) 
	}
	
	/**
	 * Returns true if the given expression is a literal
	 */
	public static def boolean isXExpressionLiteral(XExpression expression) {
		return
			(expression instanceof XStringLiteral)
			|| (expression instanceof XBooleanLiteral)
			|| (expression instanceof XNumberLiteral)
			|| (expression instanceof XNullLiteral)
			|| (expression instanceof XTypeLiteral) 
	}
	
	public static def dispatch EPackage inferPackage(XExpression expression) {
		throw new UnsupportedOperationException("Unknown expression type for package inference: " + expression + " (" + expression.class + ")")
	}
	
	/**
	 * Infers the used package from the given feature call.
	 */
	public static def dispatch EPackage inferPackage(XFeatureCall featureCall) {
		val feature = featureCall.feature 
		if (feature instanceof JvmFormalParameter) {
			val parameterType = feature.parameterType.type
			if (parameterType instanceof JvmDeclaredType) {
				val ePackageRegistry = InferrenceEMFHelper.getPackageRegistry
				for (pkgKey : new HashSet<String>(ePackageRegistry.keySet)) {
					// apparently, getEPackage sometimes throws an exception for
					// valid pkgKeys
					try {
						val pkgIterator = ePackageRegistry.getEPackage(pkgKey) as EPackage
						if (pkgIterator.class.name.startsWith(parameterType.packageName)) {
							println(pkgIterator)
							return pkgIterator
						}
					} catch (WrappedException e) {
						println("Exception while iterating EPackages: " + e.toString)
					}
				}
			}
		}
		return null
	}
	
	public static def dispatch EPackage inferPackage(XMemberFeatureCall featureCall) {
		return inferPackage(featureCall.memberCallTarget)
	}
	
	public static def dispatch getJavaRepresentation(XExpression expression) {
		throw new UnsupportedOperationException("Unknown expression type for Java representation: " + expression + " (" + expression.class + ")")
	}
	
	public static def dispatch getJavaRepresentation(XFeatureCall featureCall) {
		return featureCall.feature.simpleName
	}
	
	/**
	 * Tries to find an operation that is a getter and corresponds to the given
	 * setter operation.
	 */	
	public static def dispatch JvmOperation findGetterFor(JvmIdentifiableElement element) {
		throw new UnsupportedOperationException("Unhandled setter type: " + element.class + " (" + element + ")")
	}

	/**
	 * Tries to find an operation that is a getter and corresponds to the given
	 * setter operation.
	 */
	public static def dispatch JvmOperation findGetterFor(JvmOperation setterOperation) {
		if (!setterOperation.simpleName.startsWith("set") || !(setterOperation.parameters.size == 1)) {
			throw new UnsupportedOperationException("Not a setter method: " + setterOperation) 
		}
		
		val strippedName = setterOperation.simpleName.substring(3).toFirstLower
		val getterName = "get" + strippedName.toFirstUpper
		
		val isGetterName =
			if (strippedName.startsWith("is"))
				strippedName
			else
				"is" + strippedName.toFirstUpper
				
		if (!(setterOperation.eContainer instanceof JvmDeclaredType))
			throw new IllegalStateException("Operation not contained in JvmDeclaredType but " + setterOperation.eContainer.class)
			
		val containerType = setterOperation.eContainer as JvmDeclaredType
		
		val getterMethod =
			containerType.members
				.filter(JvmOperation)
				.findFirst [ (simpleName.equals(getterName) || simpleName.equals(isGetterName)) && parameters.size == 0 ]
		
		if (getterMethod == null) {
			throw new IllegalStateException("No corresponding getter method found for " + setterOperation.simpleName)
		}
		
		return getterMethod
	}
	
	/**
	 * Tries to find an operation that is a setter and corresponds to the given
	 * getter operation.
	 */
	public static def dispatch JvmOperation findSetterFor(JvmIdentifiableElement element) {
		throw new UnsupportedOperationException("Unhandled getter type: " + element.class + " (" + element + ")")
	}
	
	/**
	 * Tries to find an operation that is a setter and corresponds to the given
	 * getter operation.
	 */
	public static def dispatch JvmOperation findSetterFor(JvmOperation getterOperation) {
		val strippedName =
			if (getterOperation.simpleName.startsWith("get"))
				getterOperation.simpleName.substring(3)
			else if (getterOperation.simpleName.startsWith("is")) // TODO: test for boolean
				getterOperation.simpleName.substring(2)
			else
				null
		
		if ((strippedName == null) || !(getterOperation.parameters.size == 0)) {
			throw new UnsupportedOperationException("Not a getter method: " + getterOperation) 
		}
		
		val setterName = "set" + strippedName
		val isSetterName = "setIs" + strippedName
		
		if (!(getterOperation.eContainer instanceof JvmDeclaredType))
			throw new IllegalStateException("Operation not contained in JvmDeclaredType but " + getterOperation.eContainer.class)
			
		val containerType = getterOperation.eContainer as JvmDeclaredType
		val setterMethod =
			containerType.members
				.filter(JvmOperation)
				.findFirst[
					(simpleName.equals(setterName) || simpleName.equals(isSetterName))
						&& parameters.size == 1
				]
		
		if (setterMethod == null) {
			throw new IllegalStateException("No corresponding setter method found for " + getterOperation.simpleName)
		}
		
		return setterMethod
	}
}