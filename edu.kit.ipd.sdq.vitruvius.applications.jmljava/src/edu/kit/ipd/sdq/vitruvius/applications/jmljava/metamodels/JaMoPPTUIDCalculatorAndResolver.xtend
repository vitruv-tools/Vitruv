package edu.kit.ipd.sdq.vitruvius.applications.jmljava.metamodels

import edu.kit.ipd.sdq.vitruvius.applications.jmljava.helper.StringOperationsJaMoPP
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.emftext.language.java.classifiers.Classifier
import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.imports.Import
import org.emftext.language.java.imports.StaticImport
import org.emftext.language.java.members.Field
import org.emftext.language.java.members.Method
import org.emftext.language.java.modifiers.Modifier
import org.emftext.language.java.parameters.Parameter
import org.emftext.language.java.types.ClassifierReference
import org.emftext.language.java.types.NamespaceClassifierReference
import org.emftext.language.java.types.PrimitiveType
import org.emftext.language.java.members.Constructor
import edu.kit.ipd.sdq.vitruvius.framework.tuid.HierarchicalTUIDCalculatorAndResolver

/**
 * TUID calculator and resolver for the JaMoPP meta-model.
 */
class JaMoPPTUIDCalculatorAndResolver extends HierarchicalTUIDCalculatorAndResolver<CompilationUnit> {

	private val static Logger logger = Logger.getLogger(JaMoPPTUIDCalculatorAndResolver);
	private val String CLASSIFIER_SELECTOR = "classifier"
	private val String IMPORT_SELECTOR = "import"
	private val String METHOD_SELECTOR = "method"
	private val String FIELD_SELECTOR = "field"
	private val String CONSTRUCTOR_SELECTOR = Constructor.simpleName
	private static val String NS_URI = "http://www.emftext.org/java"
	
	new() {
		super(NS_URI)
	}

	// ============================================================================
	// Base class stuff
	// ============================================================================

	override protected getRootObjectClass() {
		return CompilationUnit
	}
	
	override protected hasId(EObject obj, String indidivualId) throws IllegalArgumentException {
		return obj.calculateIndividualTUID == indidivualId
	}
	
	override protected calculateIndividualTUIDDelegator(EObject obj) {
		return obj.calculateIndividualTUID
	}

	// ============================================================================
	// TUID generation
	// ============================================================================

	private def dispatch calculateIndividualTUID(CompilationUnit compilationUnit) {
		var String className = null;

		/**
         * if compilation.getName == null (which can happen) we use the name of the first classifier
         * in the compilation unit as name. If there are no classifiers in the compilation unit we
         * use <null> as name
         */
		if (null != compilationUnit.getName()) {
			className = compilationUnit.getName();
			if (!className.endsWith(".java")) {
				className = className + ".java";
			}
		} else if (0 != compilationUnit.getClassifiers().size()) {
			className = compilationUnit.getClassifiers().get(0).getName() + ".java";
		} else {
			logger.warn("Could not determine a name for compilation unit: " + compilationUnit);
		}

		return getNamespaceAsString(compilationUnit) + "." + className;
	}

	private def dispatch calculateIndividualTUID(Classifier classifier) {
		return CLASSIFIER_SELECTOR + SUBDIVIDER + classifier.getName()
	}
	
	private def dispatch calculateIndividualTUID(Method method) {
		val tuid = new StringBuilder()
		tuid.append(METHOD_SELECTOR)
		tuid.append(SUBDIVIDER + getNameFrom(method.typeReference) + SUBDIVIDER + method.name)
		method.parameters.forEach[tuid.append(SUBDIVIDER + getNameFrom(typeReference))]
		return tuid.toString
	}
	
	private def dispatch calculateIndividualTUID(Constructor constructor) {
		val tuid = new StringBuilder()
		tuid.append(CONSTRUCTOR_SELECTOR)
		tuid.append(constructor.name)
		constructor.parameters.forEach[tuid.append(SUBDIVIDER + getNameFrom(typeReference))]
		return tuid.toString
	}
	
	private def dispatch calculateIndividualTUID(Field field) {
		return FIELD_SELECTOR + SUBDIVIDER + field.name
	}
	
	private def dispatch calculateIndividualTUID(Import importStatement) {
		var tuid = IMPORT_SELECTOR
		if (importStatement instanceof StaticImport) {
			tuid += "static" + SUBDIVIDER
		}
		return tuid + StringOperationsJaMoPP.getStringRepresentation(importStatement)
	}
	
	private def dispatch calculateIndividualTUID(Modifier modifier) {
		return modifier.class.interfaces.get(0).simpleName;
	}
	
	private def dispatch calculateIndividualTUID(Parameter param) {
		return param.name
	}
	
	private def dispatch calculateIndividualTUID(NamespaceClassifierReference ref) {
		val tuid = new StringBuilder()
		tuid.append(ref.eContainingFeature.name)
		ref.classifierReferences.forEach[tuid.append(target.name)]
		return tuid.toString
	}

	private def dispatch calculateIndividualTUID(PrimitiveType pt) {
		return pt.class.interfaces.get(0).simpleName
	}
	
	private def dispatch calculateIndividualTUID(EObject obj) {
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
	
	override hasTUID(EObject eObject) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

}
