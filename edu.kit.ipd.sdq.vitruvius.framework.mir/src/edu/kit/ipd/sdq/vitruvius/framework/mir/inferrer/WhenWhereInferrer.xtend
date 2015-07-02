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

import static edu.kit.ipd.sdq.vitruvius.framework.mir.helpers.XtextJvmHelper.*

@Singleton
class WhenWhereInferrer {
	@Inject private XbaseCompiler compiler
	@Inject private extension ClosureProvider
	
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
	

	
	public def dispatch void inferCode(XBlockExpression block, XAssignment assignment) {
		if (!isXExpressionLiteral(assignment.value)) {
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
}