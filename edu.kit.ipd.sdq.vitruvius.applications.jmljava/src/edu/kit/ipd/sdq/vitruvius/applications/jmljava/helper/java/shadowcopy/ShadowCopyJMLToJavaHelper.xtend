package edu.kit.ipd.sdq.vitruvius.applications.jmljava.helper.java.shadowcopy

import edu.kit.ipd.sdq.vitruvius.applications.jmljava.helper.JaMoPPConcreteSyntax
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.helper.Utilities
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.ConcreteSyntaxHelper
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.Expression
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLFactory
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLForAllExpression
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLMethodBehavior
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLMethodExpression
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLMethodSpecificationWithModifier
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLMultilineSpec
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLSpecificationOnlyElementWithModifier
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLSpecifiedElement
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLTypeExpressionWithModifier
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.MemberDeclWithModifier
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.MemberDeclaration
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.ModifierValue
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.ParenthesisExpression
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.RegularModifier
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.Type
import java.util.ArrayList
import java.util.List
import org.apache.commons.lang.StringUtils
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.emftext.language.java.members.ClassMethod
import org.emftext.language.java.members.Constructor
import org.emftext.language.java.members.Field
import org.emftext.language.java.members.Member
import org.emftext.language.java.members.MembersFactory
import org.emftext.language.java.members.MembersPackage
import org.emftext.language.java.modifiers.AnnotableAndModifiable
import org.emftext.language.java.statements.ForLoop
import org.emftext.language.java.statements.LocalVariableStatement
import org.emftext.language.java.statements.Statement
import org.emftext.language.java.statements.StatementListContainer
import org.emftext.language.java.statements.StatementsPackage
import org.emftext.language.java.types.TypesFactory
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.VisibilityModifierValue
import org.emftext.language.java.modifiers.ModifiersFactory

/**
 * Helper class for conversions in the shadow copy from JML to Java.
 */
class ShadowCopyJMLToJavaHelper extends ShadowCopyJavaJmlHelperBase {
	
	private static val LOGGER = Logger.getLogger(ShadowCopyJMLToJavaHelper)
	
	def createSpecContainingMethod(MemberDeclWithModifier memberDeclWithModifier, URI uriToSet) {
		val memberDeclaration = memberDeclWithModifier.memberdecl as MemberDeclaration
		if (memberDeclaration.field != null) {
			throw new IllegalArgumentException("Fields are not supported by this method.")
		}
		
		val memberDeclWithModifierCopy = Utilities.clone(memberDeclWithModifier);
		val memberDeclarationCopy = (memberDeclWithModifierCopy.memberdecl as MemberDeclaration)
        
        // TODO instead of clearing we should consider converting them (e.g. spec_public)
        if (memberDeclWithModifierCopy.getJmlModifiers() != null) {
        	memberDeclWithModifierCopy.getJmlModifiers().clear();
        }
        
        // make method not abstract anymore
        memberDeclWithModifierCopy.modifiers.removeAll(memberDeclWithModifierCopy.modifiers.filter(RegularModifier).filter[modifier == ModifierValue.ABSTRACT])
        memberDeclarationCopy.method.methodbody = JMLFactory.eINSTANCE.createBlock
        
        // adjust the name to avoid name clash with original method
        memberDeclarationCopy.method.identifier = memberDeclaration.method.identifier + randomString;
        
        // convert element
        val concreteSyntax = ConcreteSyntaxHelper.convertToConcreteSyntax(memberDeclWithModifierCopy)
        val javaMethod = JaMoPPConcreteSyntax.convertFromConcreteSyntax(concreteSyntax, MembersPackage.Literals.CLASS_METHOD, ClassMethod, uriToSet)
        javaMethod.typeReference = TypesFactory.eINSTANCE.createVoid
        return javaMethod
	}
	
	def createJavaMember(MemberDeclWithModifier memberDeclWithModifier, URI uriToSet) {
		val memberDeclWithModifierCopy = Utilities.clone(memberDeclWithModifier);
        
        // TODO instead of clearing we should consider converting them (e.g. spec_public)
        if (memberDeclWithModifierCopy.getJmlModifiers() != null) {
        	memberDeclWithModifierCopy.getJmlModifiers().clear();
        }
        
        // convert element
        val concreteSyntax = ConcreteSyntaxHelper.convertToConcreteSyntax(memberDeclWithModifierCopy)
        val memberDeclaration = memberDeclWithModifier.memberdecl as MemberDeclaration
        var Member javaMember = null
        if (memberDeclaration.method != null) {
        	javaMember = JaMoPPConcreteSyntax.convertFromConcreteSyntax(concreteSyntax, MembersPackage.Literals.CLASS_METHOD, ClassMethod, uriToSet)
        } else {
        	javaMember = JaMoPPConcreteSyntax.convertFromConcreteSyntax(concreteSyntax, MembersPackage.Literals.FIELD, Field, uriToSet)
        }
        
        val javaAnnotable = javaMember as AnnotableAndModifiable
        val jmlParent = Utilities.getParentOfType(memberDeclWithModifierCopy, JMLSpecificationOnlyElementWithModifier)
        if (jmlParent.modifier.exists[it.modifier == VisibilityModifierValue.STATIC]) {
	        javaAnnotable.annotationsAndModifiers.add(ModifiersFactory.eINSTANCE.createStatic)
        }
        
        return javaMember
	}
	
	def dispatch addJMLStatementsToMember(ClassMethod javaMember, List<JMLMethodSpecificationWithModifier> jmlExpressions, ShadowCopyCorrespondencesWritable correspondence) {
		addJMLStatementsToMemberInternal(javaMember, jmlExpressions, correspondence)
	}
	
	def dispatch addJMLStatementsToMember(Constructor javaMember, List<JMLMethodSpecificationWithModifier> jmlExpressions, ShadowCopyCorrespondencesWritable correspondence) {
		addJMLStatementsToMemberInternal(javaMember, jmlExpressions, correspondence)
	}
	
	def dispatch addJMLStatementsToMember(Field javaMember, List<JMLMethodSpecificationWithModifier> jmlExpressions, ShadowCopyCorrespondencesWritable correspondence) {
		val specDummyMethod = JaMoPPConcreteSyntax.convertFromConcreteSyntax(javaMember.name.javaFieldSpecificationMethodString, MembersPackage.Literals.CLASS_METHOD, ClassMethod, javaMember.eResource.URI)
		javaMember.containingConcreteClassifier.members += specDummyMethod
		addJMLStatementsToMemberInternal(specDummyMethod, jmlExpressions, correspondence)
	}
	
	private static def addJMLStatementsToMemberInternal(StatementListContainer statementContainer, List<JMLMethodSpecificationWithModifier> jmlMethodSpecificationsWithModifier, ShadowCopyCorrespondencesWritable correspondence) {
        val resultingJavaStatements = new ArrayList<Statement>();
        for (jmlMethodSpecificationWithModifier : jmlMethodSpecificationsWithModifier) {
        	val jmlMethodSpecificationExpressions = jmlMethodSpecificationWithModifier.spec.allContainedSpecs
        	for (jmlMethodSpecificationExpression : jmlMethodSpecificationExpressions) {
        		val convertedStatements = convert(jmlMethodSpecificationExpression, statementContainer.eResource.URI)
        		correspondence.addCorrespondence(jmlMethodSpecificationExpression, convertedStatements.get(0))
        		if (convertedStatements.size > 1) {
        			LOGGER.warn("More than one statement were produced during conversion!")
        		}
        		resultingJavaStatements.addAll(convertedStatements)
        	}
        }
        statementContainer.getStatements().addAll(0, resultingJavaStatements);
	}
	
	def getOldReplacementMethod(URI targetUri) {
		return jmlReplacementOldMethodString.convertToJava(MembersPackage.Literals.CLASS_METHOD, ClassMethod, targetUri)
	}
	
	def getFreshReplacementMethod(URI targetUri) {
		return jmlReplacementFreshMethodString.convertToJava(MembersPackage.Literals.CLASS_METHOD, ClassMethod, targetUri)
	}
	
	def getResultReplacementMethodFor(Type type, URI targetUri) {
		return type.jmlReplacementResultMethodString.convertToJava(MembersPackage.Literals.CLASS_METHOD, ClassMethod, targetUri)
	}
	
	def getTypeSpecificationsMethod(List<JMLTypeExpressionWithModifier> typeSpecs, URI targetUri, ShadowCopyCorrespondencesWritable correspondence) {
		val method = MembersFactory.eINSTANCE.createClassMethod
		method.typeReference = TypesFactory.eINSTANCE.createVoid
		method.name = getJavaTypeSpecificationsMethodName
		for (typeSpec : typeSpecs) {
			method.statements.add(typeSpec.spec.expr.convert(null, targetUri))
			correspondence.addCorrespondence(typeSpec.spec, method.statements.last)	
		}
		return method
	}
	
	private static def dispatch List<JMLMethodExpression> getAllContainedSpecs(JMLMethodBehavior spec) {
		return spec.specifications
	}
	
	private static def dispatch List<JMLMethodExpression> getAllContainedSpecs(JMLMethodExpression spec) {
		return #[spec]
	}
	
	private static def List<Statement> convert(JMLMethodExpression spec, URI targetUri) {
		val specElement = Utilities.getParentOfType(spec, JMLSpecifiedElement)
		var MemberDeclWithModifier memberDecl = specElement.element
		if (memberDecl == null) {
			memberDecl = (specElement as JMLMultilineSpec).modelElement.element.element
		}
		var Type correspondingType = null
		if (memberDecl.memberdecl instanceof MemberDeclaration) {
			correspondingType = (memberDecl.memberdecl as MemberDeclaration).type
		}
	
		val statements = new ArrayList<Statement>()
		statements.add(convert(spec.expr, correspondingType, targetUri));
		return statements
	}
	
	private static def Statement convert(Expression jmlExpression, Type correspondingType, URI targetUri) {
		if (jmlExpression instanceof ParenthesisExpression && (jmlExpression as ParenthesisExpression).expr instanceof JMLForAllExpression) {
			return convert(jmlExpression as ParenthesisExpression, correspondingType, targetUri)
		}
		var javaStatementString = ConcreteSyntaxHelper.convertToConcreteSyntax(jmlExpression)
		if (javaStatementString.contains("\\\\forall")) {
			throw new IllegalArgumentException("\\\\forall is only supported as single expression.")
		}
		javaStatementString = replaceUsualParts(javaStatementString, correspondingType);
		javaStatementString = getJavaAssignmentPrefix() + getRandomString() + " = " + javaStatementString + ";"
		return javaStatementString.convertToJavaStatement(targetUri)
	}
	
	private static def replaceUsualParts(String jmlExpressionString, Type correspondingType) {
		var javaStatementString = jmlExpressionString.replaceAll("\\\\old", getJmlReplacementOld())
		javaStatementString = javaStatementString.replaceAll("\\\\fresh", getJmlReplacementFresh())
		if (correspondingType != null) {
			javaStatementString = javaStatementString.replaceAll("\\\\result", getJmlReplacementResult(correspondingType))
		} else if (javaStatementString.contains("\\\\result")) {
			throw new IllegalStateException("A void method must NOT use the JML result keyword.")
		}	
		return javaStatementString	
	}
	
	private static def Statement convert(ParenthesisExpression jmlExpression, Type correspondingType, URI targetUri) {
		val jmlForallExpression = jmlExpression.expr as JMLForAllExpression
		
		val iniExpressionsConcrete = new ArrayList<String>()
		jmlForallExpression.initExpressions.forEach[iniExpressionsConcrete.add(ConcreteSyntaxHelper.convertToConcreteSyntax(it).replaceUsualParts(correspondingType))]
		
		val expressionConcrete = ConcreteSyntaxHelper.convertToConcreteSyntax(jmlForallExpression.expression).replaceUsualParts(correspondingType)
		
		if (jmlForallExpression.updateExpressions.size != 1) {
			throw new IllegalArgumentException("Not implemented.")
		}
		val updateExpression = ConcreteSyntaxHelper.convertToConcreteSyntax(jmlForallExpression.updateExpressions.get(0)).replaceUsualParts(correspondingType)
		
		val javaLoopConcreteSyntax = createJavaForLoopConcreteSyntax(iniExpressionsConcrete, expressionConcrete, updateExpression);
		
		return JaMoPPConcreteSyntax.convertFromConcreteSyntax(javaLoopConcreteSyntax, StatementsPackage.eINSTANCE.forLoop, ForLoop, targetUri);
	}
	
	private static def createJavaForLoopConcreteSyntax(ArrayList<String> iniExpressions, String condition, String update) '''
	for(�StringUtils.join(iniExpressions, ", ")�; �condition�; ) {
		boolean JML_�randomString� = (�update�);
	}
	'''
	
	private static def convertToJavaStatement(String javaStatementString, URI targetUri) {
        return javaStatementString.convertToJava(StatementsPackage.Literals.LOCAL_VARIABLE_STATEMENT, LocalVariableStatement, targetUri)
	}
	
	private static def <T extends EObject> T convertToJava(CharSequence javaStuff, EClass startSymbol, Class<T> eobjectType, URI targetUri) {
		return JaMoPPConcreteSyntax.convertFromConcreteSyntax(javaStuff, startSymbol, eobjectType, targetUri)
	}
	

	
}