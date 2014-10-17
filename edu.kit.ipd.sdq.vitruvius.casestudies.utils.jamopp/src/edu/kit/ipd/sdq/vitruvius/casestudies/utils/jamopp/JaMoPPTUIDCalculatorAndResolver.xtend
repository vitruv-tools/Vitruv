package edu.kit.ipd.sdq.vitruvius.casestudies.utils.jamopp

import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.HierarchicalTUIDCalculatorAndResolver
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.emftext.language.java.classifiers.Classifier
import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.containers.JavaRoot
import org.emftext.language.java.containers.Package
import org.emftext.language.java.expressions.AssignmentExpression
import org.emftext.language.java.imports.Import
import org.emftext.language.java.imports.StaticImport
import org.emftext.language.java.members.Constructor
import org.emftext.language.java.members.Field
import org.emftext.language.java.members.Method
import org.emftext.language.java.modifiers.Modifier
import org.emftext.language.java.parameters.Parameter
import org.emftext.language.java.statements.ExpressionStatement
import org.emftext.language.java.types.ClassifierReference
import org.emftext.language.java.types.NamespaceClassifierReference
import org.emftext.language.java.types.PrimitiveType
import org.emftext.language.java.references.SelfReference
import org.emftext.language.java.literals.This
import org.emftext.language.java.references.IdentifierReference
import org.emftext.language.java.operators.Assignment
import org.emftext.language.java.instantiations.NewConstructorCall
import org.emftext.language.java.expressions.ConditionalExpression

/**
 * TUID calculator and resolver for the JaMoPP meta-model.
 */
class JaMoPPTUIDCalculatorAndResolver extends HierarchicalTUIDCalculatorAndResolver<JavaRoot> {

	private val static Logger logger = Logger.getLogger(JaMoPPTUIDCalculatorAndResolver);

	private val String TUIDIdentifier = JaMoPPTUIDCalculatorAndResolver.simpleName
	private val String CLASSIFIER_SELECTOR = "classifier"
	private val String IMPORT_SELECTOR = "import"
	private val String METHOD_SELECTOR = "method"
	private val String FIELD_SELECTOR = "field"
	private val String CONSTRUCTOR_SELECTOR = "constructor"
	private val String ASSIGNMENT_EXPRESSION_SELECTOR = "assignmentExpression"
	private val String EXPRESSION_STATEMENT_SELECTOR = "expressionStatement"
	private val String SELF_REFERENCE_SELECTOR = "selfReference"
	private val String IDENTIFIER_REFERENCE_SELECTOR = "identifierReference"
	private val String NEW_CONSTRUCTOR_CALL_SELECTOR = "newConstructorCall"
	private val String CONDITIONAL_EXPRESSION_SELECTOR = "conditionalExpression"

	// ============================================================================
	// Base class stuff
	// ============================================================================
	override protected getTUIDIdentifier() {
		return TUIDIdentifier
	}

	override protected getRootObjectClass() {
		return JavaRoot
	}

	override protected hasId(EObject obj, String indidivualId) throws IllegalArgumentException {
		return obj.calculateIndividualTUID == indidivualId
	}

	override protected String calculateIndividualTUIDDelegator(EObject obj) {
		return obj.calculateIndividualTUID
	}

	// ============================================================================
	// TUID generation
	// ============================================================================
	private def dispatch String calculateIndividualTUID(Package jaMoPPPackage) {
		return ""
	}

	private def dispatch String calculateIndividualTUID(CompilationUnit compilationUnit) {
		return ""

	//		var String className = null;
	//
	//		/**
	//         * if compilation.getName == null (which can happen) we use the name of the first classifier
	//         * in the compilation unit as name. If there are no classifiers in the compilation unit we
	//         * use <null> as name
	//         */
	//		if (null != compilationUnit.getName()) {
	//			className = compilationUnit.getName();
	//		} else if (0 != compilationUnit.getClassifiers().size()) {
	//			className = compilationUnit.getClassifiers().get(0).getName() + ".java";
	//		} else {
	//			logger.warn("Could not determine a name for compilation unit: " + compilationUnit);
	//		}
	//		val boolean alreadyContainsNamespace = (1 < StringUtils.countMatches(className, "."));
	//		if(alreadyContainsNamespace){
	//			return className;
	//		}
	//		return getNamespaceAsString(compilationUnit) + "." + className;
	}

	private def dispatch String calculateIndividualTUID(Classifier classifier) {
		return CLASSIFIER_SELECTOR + SUBDIVIDER + classifier.getName()
	}

	private def dispatch String calculateIndividualTUID(Method method) {
		val tuid = new StringBuilder()
		tuid.append(METHOD_SELECTOR)
		tuid.append(SUBDIVIDER + getNameFrom(method.typeReference) + SUBDIVIDER + method.name)
		method.parameters.forEach[tuid.append(SUBDIVIDER + getNameFrom(typeReference))]
		return tuid.toString
	}

	private def dispatch String calculateIndividualTUID(Field field) {
		return FIELD_SELECTOR + SUBDIVIDER + field.name
	}

	private def dispatch String calculateIndividualTUID(Import importStatement) {
		var tuid = IMPORT_SELECTOR
		if (importStatement instanceof StaticImport) {
			tuid += "static" + SUBDIVIDER
		}
		return tuid + StringOperationsJaMoPP.getStringRepresentation(importStatement)
	}

	private def dispatch String calculateIndividualTUID(Modifier modifier) {
		return modifier.class.interfaces.get(0).simpleName;
	}

	private def dispatch String calculateIndividualTUID(Parameter param) {
		return param.name
	}

	private def dispatch String calculateIndividualTUID(NamespaceClassifierReference ref) {
		val tuid = new StringBuilder()
		tuid.append(ref.eContainingFeature.name)
		ref.classifierReferences.forEach[tuid.append(target.name)]
		return tuid.toString
	}

	private def dispatch String calculateIndividualTUID(PrimitiveType pt) {
		return pt.class.interfaces.get(0).simpleName
	}

	private def dispatch String calculateIndividualTUID(Constructor constuctor) {
		val tuid = new StringBuilder
		tuid.append(CONSTRUCTOR_SELECTOR).append(SUBDIVIDER).append(constuctor.name)
		constuctor.parameters.forEach[tuid.append(SUBDIVIDER + getNameFrom(typeReference))]
		return tuid.toString
	}

	private def dispatch String calculateIndividualTUID(ExpressionStatement expressionStatement) {
		val tuid = new StringBuilder
		tuid.append(EXPRESSION_STATEMENT_SELECTOR).append(SUBDIVIDER);
		tuid.append(expressionStatement.expression.calculateIndividualTUID);
		return tuid.toString
	}

	private def dispatch String calculateIndividualTUID(AssignmentExpression assignmentExpression) {
		val tuid = new StringBuilder
		tuid.append(ASSIGNMENT_EXPRESSION_SELECTOR).append(SUBDIVIDER)
		if (null != assignmentExpression.child) {
			tuid.append(assignmentExpression.child.calculateIndividualTUID).append(SUBDIVIDER)
		}
		if (null != assignmentExpression.assignmentOperator) {
			tuid.append(assignmentExpression.assignmentOperator.calculateIndividualTUID).append(SUBDIVIDER)
		}
		if (null != assignmentExpression.value) {
			tuid.append(assignmentExpression.value.calculateIndividualTUID)
		}
		return tuid.toString
	}

	private def dispatch String calculateIndividualTUID(SelfReference selfReference) {
		val tuid = new StringBuilder
		tuid.append(SELF_REFERENCE_SELECTOR)
		tuid.append(selfReference.self.calculateIndividualTUID).append(SUBDIVIDER)
		tuid.append(selfReference.next.calculateIndividualTUID)
		return tuid.toString
	}

	private def dispatch String calculateIndividualTUID(This thisRef) {
		return "this"
	}

	private def dispatch String calculateIndividualTUID(IdentifierReference identifierReference) {
		val tuid = new StringBuilder
		tuid.append(IDENTIFIER_REFERENCE_SELECTOR)
		tuid.append(identifierReference.target.calculateIndividualTUID)
		return tuid.toString
	}

	private def dispatch String calculateIndividualTUID(Assignment assignment) {
		return "="
	}

	private def dispatch String calculateIndividualTUID(NewConstructorCall newConstructorCall) {
		val tuid = new StringBuilder
		tuid.append(NEW_CONSTRUCTOR_CALL_SELECTOR).append(SUBDIVIDER)
		tuid.append(getNameFrom(newConstructorCall.typeReference)).append(SUBDIVIDER)

		//TODO: add arguments
		return tuid.toString
	}

	// actually we should not need this method. However, JaMoPP sometimes creates a ConditionalExpression instead 
	// of an AssignementExpression. Traverse the childs until the type reference is not null
	private def dispatch String calculateIndividualTUID(ConditionalExpression conditionalExpression) {
		val tuid = new StringBuilder
		tuid.append(CONDITIONAL_EXPRESSION_SELECTOR)
		if (null == conditionalExpression.type) {
			tuid.append(SUBDIVIDER).append("null")
		} else{
			tuid.append(SUBDIVIDER).append(conditionalExpression.type.calculateIndividualTUID)
		}
		return tuid.toString
	}

	private def dispatch String calculateIndividualTUID(EObject obj) {
		throw new IllegalArgumentException("Invalid type given " + obj.class.simpleName);
	}

	// ============================================================================
	// Helpers
	// ============================================================================
	private def dispatch String getNameFrom(NamespaceClassifierReference namespaceClassifierReference) {
		var name = "";
		var int i = 0;
		for (ClassifierReference cr : namespaceClassifierReference.getClassifierReferences()) {
			if (i > 0) {
				name += i
			}
			name += getNameFrom(cr);
			i++;
		}
		return name;
	}

	private def dispatch String getNameFrom(PrimitiveType primitiveType) {
		return primitiveType.eClass().getName().replaceAll("Impl", "");
	}

	private def dispatch String getNameFrom(ClassifierReference classifierReference) {
		return classifierReference.target.name
	}

	private def String getNamespaceAsString(CompilationUnit cu) {
		return StringOperationsJaMoPP.getNamespaceAsString(cu.namespaces)
	}

}
