package edu.kit.ipd.sdq.vitruvius.applications.jmljava.metamodels

import edu.kit.ipd.sdq.vitruvius.applications.jmljava.helper.StringOperationsJML
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.ConcreteSyntaxHelper
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.ClassOrInterfaceDeclaration
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.ClassOrInterfaceTypeWithBrackets
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.ClassifierDeclarationWithModifier
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.CompilationUnit
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.Constructor
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.DeclaredException
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.FieldDeclaration
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.FormalParameterDecl
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.ImportDeclaration
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLMultilineSpec
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLSpecificationOnlyElement
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLSpecificationOnlyElementWithModifier
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLSpecifiedElement
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLTypeExpression
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLTypeExpressionWithModifier
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.MemberDecl
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.MemberDeclWithModifierRegular
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.MemberDeclWithModifierSpec
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.MemberDeclaration
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.MethodDeclaration
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.NormalClassDeclaration
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.NormalInterfaceDeclaration
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.PackageDeclaration
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.RegularModifier
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.VariableDeclarator
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.impl.PrimitiveTypeWithBracketsImpl
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.GenericMethodOrConstructorDecl
import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLPackage
import edu.kit.ipd.sdq.vitruvius.framework.tuid.HierarchicalTUIDCalculatorAndResolver
import edu.kit.ipd.sdq.vitruvius.framework.tuid.TUIDCalculatorUtilities

/**
 * TUID calculator and resolver for the JML meta-model.
 */
class JMLTUIDCalculatorAndResolver extends HierarchicalTUIDCalculatorAndResolver<CompilationUnit> {

	private static val TYPE_SELECTOR = "type"
	private static val PACKAGE_SELECTOR = "package"
	private static val IMPORT_SELECTOR = "import"
	private static val MODIFIER_SELECTOR = "modifier"
	private static val MEMBERDECL_REGULAR = "MemberDeclWithModifierRegular"
	private static val MEMBERDECL_SPEC = "MemberDeclWithModifierSpec"
	private static val MEMBERDECLARATION_SELECTOR = "memberdeclaration"
	private static val CONSTRUCTOR_SELECTOR = Constructor.simpleName
	private static val GENERICMETHODORCONSTRUCTORDECL_SELECTOR = GenericMethodOrConstructorDecl.simpleName
	private static val FIELD_SELECTOR = "field"
	private static val METHOD_SELECTOR = "method"
	private static val EXCEPTION_SELECTOR = "exception"
	private static val VARIALBEDECLARATOR_SELECTOR = "variabledeclarator"
	private static val JMLTYPESPECS_SELECTOR = "jmltypespecs"
	private static val JMLSPECONLYELEMENTWITHMODIFIER = "speconlyelementwithmodifier"
	private static val JMLSPECONLYELEMENT = "speconlyelement"
	private static val JMLTYPEEXPRWMODIFIER = "jmltypeexpressionwithmodifier"
	private static val JMLTYPEEXPR = "jmltypeexpression"

	private static val Logger LOGGER = Logger.getLogger(JMLTUIDCalculatorAndResolver);
	
	new() {
		super(JMLPackage.eINSTANCE.getNsURI())
	}
	
	// ============================================================================
	// Base class stuff
	// ============================================================================

	override protected getRootObjectClass() {
		return CompilationUnit
	}

	override protected hasId(EObject obj, String indidivualId) throws IllegalArgumentException {
		val calculatedId = calculateIndividualTUID(obj)
		return calculatedId.equals(indidivualId)
	}

	override protected calculateIndividualTUIDDelegator(EObject obj) {
		return calculateIndividualTUID(obj)
	}

	// ============================================================================
	// TUID generation
	// ============================================================================
	private def dispatch String calculateIndividualTUID(CompilationUnit cu) {
		try {
			return cu.name
		} catch (IllegalStateException e) {
			LOGGER.warn("Unable to construct TUID for " + CompilationUnit.getSimpleName())
			return null
		}
	}

	private def dispatch calculateIndividualTUID(PackageDeclaration pd) {
		return PACKAGE_SELECTOR
	}

	private def dispatch calculateIndividualTUID(ImportDeclaration id) {
		return IMPORT_SELECTOR + SUBDIVIDER + id.qualifiedname
	}
	
	private def dispatch calculateIndividualTUID(ClassifierDeclarationWithModifier cdwm) {
		return cdwm.classOrInterfaceDeclaration.identifier;
	}

	private def dispatch calculateIndividualTUID(NormalClassDeclaration ncd) {
		return TYPE_SELECTOR + SUBDIVIDER + ncd.identifier
	}

	private def dispatch String calculateIndividualTUID(NormalInterfaceDeclaration nid) {
		return TYPE_SELECTOR + SUBDIVIDER + nid.identifier
	}

	private def dispatch String calculateIndividualTUID(RegularModifier regularModifier) {
		return MODIFIER_SELECTOR + SUBDIVIDER + regularModifier.modifier.literal
	}
	
	private def dispatch String calculateIndividualTUID(MemberDeclWithModifierRegular memberDeclWithModifier) {
		return MEMBERDECL_REGULAR
	}
	
	private def dispatch String calculateIndividualTUID(MemberDeclWithModifierSpec memberDeclWithModifier) {
		return MEMBERDECL_SPEC
	}
	
	private def dispatch String calculateIndividualTUID(JMLSpecifiedElement element) {
		if (element.element != null) {
			return element.element.memberdecl.calculateInternalTUID
		}
		if (element.jmlTypeSpecifications.size > 0) {
			return JMLTYPESPECS_SELECTOR + SUBDIVIDER + element.jmlTypeSpecifications.get(0).calculateInternalTUID;
		}
		if (element instanceof JMLMultilineSpec) {
			return (element as JMLMultilineSpec).modelElement.element.element.memberdecl.calculateInternalTUID
		}
		throw new IllegalArgumentException("Given element " + element + " is broken.")
	}
	
	private def dispatch String calculateIndividualTUID(JMLTypeExpressionWithModifier expr) {
		return JMLTYPEEXPRWMODIFIER
	}
	
	private def dispatch String calculateIndividualTUID(JMLTypeExpression expr) {
		return JMLTYPEEXPR
	}
	
	private def dispatch String calculateIndividualTUID(JMLSpecificationOnlyElementWithModifier jmlsoewm) {
		return JMLSPECONLYELEMENTWITHMODIFIER
	}
	
	private def dispatch String calculateIndividualTUID(JMLSpecificationOnlyElement specOnlyElement) {
		return JMLSPECONLYELEMENT
	}

	private def dispatch String calculateIndividualTUID(MemberDeclaration memberDecl) {
		return MEMBERDECLARATION_SELECTOR
	}
	
	private def dispatch String calculateIndividualTUID(GenericMethodOrConstructorDecl memberDecl) {
		return GENERICMETHODORCONSTRUCTORDECL_SELECTOR
	}

	private def dispatch String calculateIndividualTUID(FieldDeclaration fd) {
		return FIELD_SELECTOR
	}

	private def dispatch String calculateIndividualTUID(MethodDeclaration md) {
		return METHOD_SELECTOR
	}
	
	private def dispatch String calculateIndividualTUID(Constructor constructor) {
		return CONSTRUCTOR_SELECTOR
	}

	private def dispatch String calculateIndividualTUID(FormalParameterDecl fpd) {
		return fpd.identifier
	}

	private def dispatch String calculateIndividualTUID(VariableDeclarator vd) {
		return TUIDCalculatorUtilities.getGenericIndividualIdentifier(vd, VARIALBEDECLARATOR_SELECTOR + SUBDIVIDER);
	}

	private def dispatch String calculateIndividualTUID(DeclaredException de) {
		return EXCEPTION_SELECTOR + SUBDIVIDER + de.name
	}

	private def dispatch String calculateIndividualTUID(PrimitiveTypeWithBracketsImpl ptwbi) {
		return ptwbi.primitivetype.literal
	}

	private def dispatch String calculateIndividualTUID(ClassOrInterfaceTypeWithBrackets coitwb) {
		val tuid = new StringBuilder()
		coitwb.type.forEach[tuid.append(identifier + ".")]
		tuid.deleteCharAt(tuid.length - 1)
		return tuid.toString
	}

	// ============================================================================
	// Helpers
	// ============================================================================
	private def getIdentifier(ClassOrInterfaceDeclaration decl) {
		return StringOperationsJML.getIdentifier(decl)
	}

	private def dispatch String calculateInternalTUID(MemberDeclaration md) {
		if (md.field != null) {
			return MEMBERDECLARATION_SELECTOR + SUBDIVIDER + md.field.variabledeclarator.get(0).identifier
		} else if (md.method != null) {
			return MEMBERDECLARATION_SELECTOR + SUBDIVIDER + StringOperationsJML.getStringRepresentation(md.method)
		}
		return null
	}
	
	private def dispatch String calculateInternalTUID(GenericMethodOrConstructorDecl md) {
		if (md.method != null) {
			return GENERICMETHODORCONSTRUCTORDECL_SELECTOR + SUBDIVIDER + StringOperationsJML.getStringRepresentation(md.method)
		} else if (md.constructor != null) {
			return GENERICMETHODORCONSTRUCTORDECL_SELECTOR + SUBDIVIDER + StringOperationsJML.getStringRepresentation(md.constructor)
		}
	}
	
	private def dispatch String calculateInternalTUID(Constructor constructor) {
		if (constructor.eContainer instanceof GenericMethodOrConstructorDecl) {
			return CONSTRUCTOR_SELECTOR
		}
		return CONSTRUCTOR_SELECTOR + SUBDIVIDER + StringOperationsJML.getStringRepresentation(constructor)
	}

	private def dispatch String calculateInternalTUID(JMLTypeExpressionWithModifier expr) {
		return ConcreteSyntaxHelper.convertToConcreteSyntax(expr)
	}

	private def dispatch String calculateInternalTUID(MemberDecl md) {
		throw new IllegalArgumentException("Not implemented yet.")
	}
	
	override hasTUID(EObject eObject) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

}
