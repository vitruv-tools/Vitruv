package edu.kit.ipd.sdq.vitruvius.applications.jmljava.helper.java.shadowcopy

import edu.kit.ipd.sdq.vitruvius.applications.jmljava.helper.JaMoPPConcreteSyntax
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.ConcreteSyntaxHelper
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.Block
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.BlockStatement
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.Expression
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLExpressionHaving
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLForAllExpression
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLMethodBehavior
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLMethodExpression
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLMethodSpecificationWithModifier
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLMultilineSpec
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLSpecifiedElement
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLTypeExpressionWithModifier
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.MemberDeclaration
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.ParenthesisExpression
import java.util.ArrayList
import java.util.List
import org.apache.commons.lang.StringUtils
import org.emftext.language.java.expressions.ExpressionList
import org.emftext.language.java.expressions.NestedExpression
import org.emftext.language.java.members.ClassMethod
import org.emftext.language.java.members.Constructor
import org.emftext.language.java.members.Field
import org.emftext.language.java.statements.ForLoop
import org.emftext.language.java.statements.LocalVariableStatement
import org.emftext.language.java.statements.Statement

/**
 * Helper class for conversions in the shadow copy from Java to JML.
 */
class ShadowCopyJavaToJMLHelper extends ShadowCopyJavaJmlHelperBase {

	def dispatch updateByShadowObject(JMLSpecifiedElement jmlSpecifiedElement, ClassMethod shadowMember, ShadowCopyCorrespondencesWritable correspondences) {
		var updated = updateByShadowObject(jmlSpecifiedElement.jmlSpecifications, correspondences)

		// model method (update body too)
		if (jmlSpecifiedElement.element == null && jmlSpecifiedElement instanceof JMLMultilineSpec && (jmlSpecifiedElement as JMLMultilineSpec).modelElement != null) {
			
			val methodStatements = shadowMember.statements.subList(shadowMember.getIndexOfFirstNonDummyStatement(correspondences),
				shadowMember.statements.size)
			val methodBody = ((jmlSpecifiedElement as JMLMultilineSpec).modelElement.element.element.memberdecl as MemberDeclaration).method.methodbody as Block
			methodBody.blockstatement.clear()
			methodStatements.forEach [
				methodBody.blockstatement += ConcreteSyntaxHelper.convertFromConcreteSyntax(
					JaMoPPConcreteSyntax.convertToConcreteSyntax(it), BlockStatement)];
				updated = true
		} 

		return updated
	}

	def dispatch updateByShadowObject(JMLSpecifiedElement jmlSpecifiedElement, Field shadowMember, ShadowCopyCorrespondencesWritable correspondences) {
		return updateByShadowObject(jmlSpecifiedElement.jmlSpecifications, correspondences)
	}
	
	def dispatch updateByShadowObject(JMLSpecifiedElement jmlSpecifiedElement, Constructor shadowMember, ShadowCopyCorrespondencesWritable correspondences) {
		return updateByShadowObject(jmlSpecifiedElement.jmlSpecifications, correspondences)
	}
	
	def updateTypeSpecsByShadowObject(List<JMLTypeExpressionWithModifier> typeSpecs, ClassMethod shadowMember, ShadowCopyCorrespondences correspondences) {
		val exprs = new ArrayList<JMLExpressionHaving>()
		typeSpecs.forEach[exprs.add(spec)]
		return exprs.updateByShadowObjectInternal(correspondences)
	}
	
	private static def dispatch List<JMLExpressionHaving> getAllContainedSpecs(JMLMethodBehavior spec) {
		val exprs = new ArrayList<JMLExpressionHaving>()
		spec.specifications.forEach[exprs.add(it)]
		return exprs
	}
	
	private static def dispatch List<JMLExpressionHaving> getAllContainedSpecs(JMLMethodExpression spec) {
		return #[spec as JMLExpressionHaving]
	}

	private static def updateByShadowObject(List<JMLMethodSpecificationWithModifier> specifications, ShadowCopyCorrespondences correspondences) {
		val expressions = new ArrayList<JMLExpressionHaving>()
		specifications.forEach[expressions.addAll(spec.allContainedSpecs)]
		return expressions.updateByShadowObjectInternal(correspondences)
	}

	private static def updateByShadowObjectInternal(List<JMLExpressionHaving> expressions, ShadowCopyCorrespondences correspondences) {
		for (JMLExpressionHaving jmlExpr : expressions) {
			val javaDummyStatement = correspondences.get(jmlExpr)
			if (jmlExpr.expr instanceof ParenthesisExpression && (jmlExpr.expr as ParenthesisExpression).expr instanceof JMLForAllExpression) {
				jmlExpr.expr = (javaDummyStatement as ForLoop).convertForLoopDummyExpression
			} else {
				jmlExpr.expr = javaDummyStatement.convert
			}
		}
		return expressions.size > 0 //updated
	}

	private def static convertForLoopDummyExpression(ForLoop dummyStatement) {
		val initExpressions = new ArrayList<String>()
		if (dummyStatement.init instanceof ExpressionList) {
			(dummyStatement.init as ExpressionList).expressions.forEach[initExpressions.add(JaMoPPConcreteSyntax.convertToConcreteSyntax(it))]	
		} else {
			initExpressions.add(JaMoPPConcreteSyntax.convertToConcreteSyntax(dummyStatement.init));
		}
		
		val conditionExpression = JaMoPPConcreteSyntax.convertToConcreteSyntax(dummyStatement.condition);
		
		val updateExpression = (((dummyStatement.statement as org.emftext.language.java.statements.Block).statements.get(0) as LocalVariableStatement).variable.initialValue as NestedExpression).expression
		val updateExpressionConcreteSytanx = JaMoPPConcreteSyntax.convertToConcreteSyntax(updateExpression)
		
		val concreteSyntax = createJMLForallLoopConcreteSyntax(initExpressions, conditionExpression, updateExpressionConcreteSytanx)
		return concreteSyntax.replaceUsualElements.convertToJMLExpression
	}
	
	private static def String createJMLForallLoopConcreteSyntax(List<String> initExpressions, String conditionalExpression, String updateExpression) '''
	(\forall �StringUtils.join(initExpressions, ", ")�; �conditionalExpression�; �updateExpression�)'''

	private def static getIndexOfFirstNonDummyStatement(ClassMethod shadowObject, ShadowCopyCorrespondences corr) {
		val firstOtherStatement = shadowObject.statements.findFirst[corr.get(it) == null]
		if (firstOtherStatement == null) {
			return shadowObject.statements.size
		}
		return shadowObject.statements.indexOf(firstOtherStatement)
	}

	private def static convert(Statement javaStatement) {
		var javaExpressionString = JaMoPPConcreteSyntax.convertToConcreteSyntax(javaStatement)
		return javaExpressionString.replaceUsualElements.convertToJMLExpression
	}
	
	private static def replaceUsualElements(String concreteSyntax) {
		var javaExpressionString = concreteSyntax.replaceFirst(javaAssignmentPrefix + "[^=]+=\\s*([^;]+);", "$1");
		javaExpressionString = javaExpressionString.replaceAll(jmlReplacementOld, "\\\\old")
		javaExpressionString = javaExpressionString.replaceAll(jmlReplacementFresh, "\\\\fresh")
		javaExpressionString = javaExpressionString.replaceAll(jmlReplacementPrefixResult + "_[^)]+\\)",
			"\\\\result")
		return javaExpressionString
	}

	private static def convertToJMLExpression(String javaExpression) {
		return ConcreteSyntaxHelper.convertFromConcreteSyntax(javaExpression, Expression)
	}

}
	