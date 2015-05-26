package edu.kit.ipd.sdq.vitruvius.framework.mir.inferrer

import com.google.inject.Inject
import com.google.inject.Provider
import java.util.HashMap
import java.util.HashSet
import java.util.Map
import org.eclipse.emf.ecore.EPackage
import org.eclipse.xtext.common.types.JvmDeclaredType
import org.eclipse.xtext.common.types.JvmFormalParameter
import org.eclipse.xtext.common.types.JvmIdentifiableElement
import org.eclipse.xtext.common.types.JvmOperation
import org.eclipse.xtext.xbase.XAssignment
import org.eclipse.xtext.xbase.XBinaryOperation
import org.eclipse.xtext.xbase.XBlockExpression
import org.eclipse.xtext.xbase.XBooleanLiteral
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XFeatureCall
import org.eclipse.xtext.xbase.XMemberFeatureCall
import org.eclipse.xtext.xbase.XNullLiteral
import org.eclipse.xtext.xbase.XNumberLiteral
import org.eclipse.xtext.xbase.XStringLiteral
import org.eclipse.xtext.xbase.XTypeLiteral
import org.eclipse.xtext.xbase.compiler.XbaseCompiler
import com.google.inject.Singleton

@Singleton
class XBlockInferenceHelper {
	@Inject private XbaseCompiler compiler 
	
	private Map<XExpression, Map<EPackage, TreeAppendableClosure>> assignmentClosures
	private Map<XExpression, Map<EPackage, TreeAppendableClosure>> equalityClosures
	
	@Inject Provider<TreeAppendableClosure> closureProvider;
	@Inject Provider<TreeAppendableConjunctionClosure> conjunctionClosureProvider;
	
	new() {
		reset
	}
	
	public def void reset() {
		assignmentClosures = newHashMap
		equalityClosures = newHashMap		
	}
	
	/**
	 * Returns a {@link TreeAppendableClosure} that corresponds to the given expression (block)
	 * and the package as argument.
	 * <p>
	 * The closure is created if it not already exists for this helper.
	 */
	public def TreeAppendableClosure getAssignmentClosure(XExpression expression, EPackage pkg) {
		if (!assignmentClosures.containsKey(expression))
			assignmentClosures.put(expression, new HashMap<EPackage, TreeAppendableClosure>());
		
		val closureMap = assignmentClosures.get(expression);
		
		if (!closureMap.containsKey(pkg))
			closureMap.put(pkg, closureProvider.get());

//		println(hashCode + ".getAssignmentClosure: " + expression.hashCode + " => " + closureMap.get(pkg).hashCode)

		return closureMap.get(pkg);
	}
	
	/**
	 * Returns a {@link TreeAppendableClosure} that corresponds to the given expression (block)
	 * and the package as argument.
	 * <p>
	 * The closure is created if it not already exists for this helper.
	 * <p>
	 * For equality closures, a {@link TreeAppendableConjunctionClosure} is used.
	 */
	public def TreeAppendableClosure getEqualityClosure(XExpression expression, EPackage pkg) {
		if (!equalityClosures.containsKey(expression))
			equalityClosures.put(expression, new HashMap<EPackage, TreeAppendableClosure>());
		
		val closureMap = equalityClosures.get(expression);
		
		if (!closureMap.containsKey(pkg))
			closureMap.put(pkg, conjunctionClosureProvider.get());
			
//		println(hashCode + ".getEqualityClosure: " + expression.hashCode + " => " + closureMap.get(pkg).hashCode)
		
		return closureMap.get(pkg);
	}
	
	/**
	 * Infers all corresponding assignments and equality for the given {@link XExpression}.
	 */
	public def dispatch void inferBlock(XExpression expression) {
		println("Unhandled expression type: " + expression)
	}
	
	/**
	 * Infers all corresponding assignments and equality for the given {@link XExpression}.
	 */
	public def dispatch void inferBlock(XBlockExpression block) {
		for (expression : block.expressions) {
			inferCode(block, expression)
		}
	}
	
	/**
	 * Infers all corresponding assignments and equality for the given {@link XExpression}.
	 * <p>
	 * The passed {@link XBlockExpression} is used to assign the infered code to the correct
	 * closures.
	 */
	public def dispatch void inferCode(XBlockExpression block, XExpression expression) {
		println("Unhandled expression: " + expression)
	}
	
	/**
	 * Returns true if equality for the given literal is checked with "==" instead of
	 * ".equals(...)"
	 */
	private def boolean needsSimpleEqualityOperator(XExpression expression) {
		return
			(expression instanceof XBooleanLiteral)
			|| (expression instanceof XNumberLiteral)
			|| (expression instanceof XNullLiteral)
			|| (expression instanceof XTypeLiteral) 
	}
	
	/**
	 * Returns true if the given expression is a literal
	 */
	private def boolean isXExpressionLiteral(XExpression expression) {
		return
			(expression instanceof XStringLiteral)
			|| (expression instanceof XBooleanLiteral)
			|| (expression instanceof XNumberLiteral)
			|| (expression instanceof XNullLiteral)
			|| (expression instanceof XTypeLiteral) 
	}
	
	public def dispatch void inferCode(XBlockExpression block, XAssignment assignment) {
		if (!assignment.value.isXExpressionLiteral) {
			throw new IllegalArgumentException("Can't infer equality for assignment with non-literal right hand side")
		}
		
		val lhsPackage = inferPackage(assignment.assignable)
		
		println("inferred package: " + lhsPackage + "(for " + assignment +")")
		
		// equalities from assignment
		val equalityClosure = getEqualityClosure(block, lhsPackage)
		
		val lhsTargetJava = getJavaRepresentation(assignment.assignable)
		val setter = assignment.feature
		val getter = findGetterFor(setter)
		val lhsFeatureJava = getter.simpleName + "()"

		val lhsJava = lhsTargetJava + "." + lhsFeatureJava
		
		// equality for primitive types is checked with ==
		if (needsSimpleEqualityOperator(assignment.value)) {
			equalityClosure.addClosure [
				it.append(lhsJava + " == ")
					compiler.toJavaExpression(assignment.value, it)
			]
		} else {		
			equalityClosure.addClosure [
				it.append(lhsJava + ".equals(")
					compiler.toJavaExpression(assignment.value, it)
				it.append(")")
			]
		}
		
		// assignments from assignment
		val assignmentClosure = getAssignmentClosure(block, lhsPackage)
		assignmentClosure.addXExpression(assignment)
	}
	
	public def dispatch void inferCode(XBlockExpression block, XBinaryOperation comparison) {
		if (!comparison.feature.isEquals) {
			throw new UnsupportedOperationException("Not an equals operator: " + comparison.feature)
		}
		
		if (!(comparison.leftOperand instanceof XMemberFeatureCall)) {
			throw new UnsupportedOperationException("Not a feature call: " + comparison.leftOperand)
		}
		
		val leftOperand = (comparison.leftOperand as XMemberFeatureCall)
		
		val lhsPackage = inferPackage(leftOperand)
		println("inferred package: " + lhsPackage + "(for " + comparison +")")
		
		val equalityClosure = getEqualityClosure(block, lhsPackage)
		equalityClosure.addClosure [
			compiler.toJavaExpression(comparison, it)
		]
		
		val assignmentClosure = getAssignmentClosure(block, lhsPackage)
		val getter = leftOperand.feature
		val setter = findSetterFor(getter)
		val lhsTargetJava = getJavaRepresentation(leftOperand.memberCallTarget)
		val lhsJava = lhsTargetJava + "." + setter.simpleName
		
		assignmentClosure.addClosure [
			it.append(lhsJava + "(")
				compiler.toJavaExpression(comparison.rightOperand, it)
			it.append(");")
			it.newLine
		]
	}
	
	def boolean isEquals(JvmIdentifiableElement element) {
		return element.simpleName.equals("operator_equals") // TODO: find all the operator extensions and use them instead of name
	}
	
	def dispatch EPackage inferPackage(XExpression expression) {
		throw new UnsupportedOperationException("Unknown expression type for package inference: " + expression + " (" + expression.class + ")")
	}
	
	/**
	 * Infers the used package from the given feature call.
	 */
	def dispatch EPackage inferPackage(XFeatureCall featureCall) {
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
					} catch (org.eclipse.emf.common.util.WrappedException e) {
						println("Exception while iterating EPackages: " + e.toString)
					}
				}
			}
		}
		return null
	}
	
	def dispatch EPackage inferPackage(XMemberFeatureCall featureCall) {
		return inferPackage(featureCall.memberCallTarget)
	}
	
	private def dispatch getJavaRepresentation(XExpression expression) {
		throw new UnsupportedOperationException("Unknown expression type for Java representation: " + expression + " (" + expression.class + ")")
	}
	
	private def dispatch getJavaRepresentation(XFeatureCall featureCall) {
		return featureCall.feature.simpleName
	}
	
	/**
	 * Tries to find an operation that is a getter and corresponds to the given
	 * setter operation.
	 */	
	def dispatch JvmOperation findGetterFor(JvmIdentifiableElement element) {
		throw new UnsupportedOperationException("Unhandled setter type: " + element.class + " (" + element + ")")
	}

	/**
	 * Tries to find an operation that is a getter and corresponds to the given
	 * setter operation.
	 */
	def dispatch JvmOperation findGetterFor(JvmOperation setterOperation) {
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
	private def dispatch JvmOperation findSetterFor(JvmIdentifiableElement element) {
		throw new UnsupportedOperationException("Unhandled getter type: " + element.class + " (" + element + ")")
	}
	
	/**
	 * Tries to find an operation that is a setter and corresponds to the given
	 * getter operation.
	 */
	private def dispatch JvmOperation findSetterFor(JvmOperation getterOperation) {
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