package tools.vitruvius.applications.jmljava.correspondences

import tools.vitruvius.applications.jmljava.helper.StringOperationsJML
import tools.vitruvius.applications.jmljava.helper.StringOperationsJaMoPP
import tools.vitruvius.applications.jmljava.helper.Utilities
import tools.vitruvius.domains.jml.language.jML.ClassDeclaration
import tools.vitruvius.domains.jml.language.jML.ClassifierDeclarationWithModifier
import tools.vitruvius.domains.jml.language.jML.DeclaredException
import tools.vitruvius.domains.jml.language.jML.GenericMethodOrConstructorDecl
import tools.vitruvius.domains.jml.language.jML.ImportDeclaration
import tools.vitruvius.domains.jml.language.jML.InterfaceDeclaration
import tools.vitruvius.domains.jml.language.jML.JMLSpecifiedElement
import tools.vitruvius.domains.jml.language.jML.MemberDeclWithModifier
import tools.vitruvius.domains.jml.language.jML.MemberDeclaration
import tools.vitruvius.domains.jml.language.jML.Modifier
import tools.vitruvius.domains.jml.language.jML.RegularModifier
import tools.vitruvius.domains.jml.language.jML.Type
import java.util.ArrayList
import java.util.List
import org.emftext.language.java.annotations.AnnotationInstance
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.classifiers.Interface
import org.emftext.language.java.imports.Import
import org.emftext.language.java.imports.StaticImport
import org.emftext.language.java.members.Constructor
import org.emftext.language.java.members.Field
import org.emftext.language.java.members.Method
import org.emftext.language.java.types.NamespaceClassifierReference
import org.emftext.language.java.types.TypeReference

/**
 * Model element finder, which takes an element to match and a list of candidates.
 * In fact this utility class implements filter functions on a set of candidates.
 */
class MatchingModelElementsFinder {

	public static def dispatch findMatchingClassifier(Class clazz, List<ClassifierDeclarationWithModifier> candidates) {
		return candidates.filter[classOrInterfaceDeclaration instanceof ClassDeclaration].findFirst[
			StringOperationsJML.getIdentifier(classOrInterfaceDeclaration) == clazz.name]
	}

	public static def dispatch findMatchingClassifier(Interface interfaze,
		List<ClassifierDeclarationWithModifier> candidates) {
		return candidates.filter[classOrInterfaceDeclaration instanceof InterfaceDeclaration].findFirst[
			StringOperationsJML.getIdentifier(classOrInterfaceDeclaration) == interfaze.name]
	}

	public static def findMatchingImport(Import javaImport, Iterable<ImportDeclaration> candidates) {
		val javaStringRepresentation = StringOperationsJaMoPP.getStringRepresentation(javaImport)
		val javaStatic = javaImport instanceof StaticImport
		return candidates.findFirst[
			static == javaStatic && qualifiedname.appendIfNecessary(".*", wildcard).equals(javaStringRepresentation)]
	}

	private static def appendIfNecessary(String text, String textToAppend, boolean append) {
		if (!append) {
			return text;
		}
		return text + textToAppend;
	}

	public static def findMatchingException(NamespaceClassifierReference javaException,
		Iterable<DeclaredException> candidates) {
		val javaRepresentation = StringOperationsJaMoPP.getStringRepresentation(javaException, 0)
		return candidates.findFirst[javaRepresentation.equals(StringOperationsJML.getStringRepresentation(it))]
	}

	/**
	 * Finds a corresponding NON model method.
	 */
	public static def findMatchingMethod(Method javaMethod, Iterable<JMLSpecifiedElement> candidates) {
		val jmlCandidates = candidates.filter[element.memberdecl instanceof MemberDeclaration].filter[(element.memberdecl as MemberDeclaration).method != null]
		val jmlCandidates2 = candidates.filter[element.memberdecl instanceof GenericMethodOrConstructorDecl].filter[(element.memberdecl as GenericMethodOrConstructorDecl).method != null]
		val jmlCandidatesFinal = new ArrayList<JMLSpecifiedElement>()
		jmlCandidatesFinal.addAll(jmlCandidates)
		jmlCandidatesFinal.addAll(jmlCandidates2)
		return jmlCandidatesFinal.findFirst[Utilities.corresponds(javaMethod, element)]
	}

	/**
	 * Finds a corresponding NON model constructor.
	 */
	def static findMatchingConstructor(Constructor constructor, Iterable<JMLSpecifiedElement> candidates) {
		val jmlCandidates = candidates.filter[element.memberdecl instanceof tools.vitruvius.domains.jml.language.jML.Constructor]
		return jmlCandidates.findFirst[Utilities.corresponds(constructor, element)]
	}

	/**
	 * Finds a corresponding NON model field.
	 */
	public static def findMatchingField(Field javaField, Iterable<JMLSpecifiedElement> candidates) {
		val jmlCandidates = candidates.filter[element.memberdecl instanceof MemberDeclaration].filter[(element.memberdecl as MemberDeclaration).field != null]
		return jmlCandidates.findFirst[
			(element.memberdecl as MemberDeclaration).field.variabledeclarator.exists[identifier.equals(javaField.name)]]
	}

	public static def findMatchingType(TypeReference javaType, int arrayDimensions, Iterable<Type> candidates) {
		val javaStringRepresentation = StringOperationsJaMoPP.getStringRepresentation(javaType, arrayDimensions)
		return candidates.findFirst[javaStringRepresentation.equals(StringOperationsJML.getStringRepresentation(it))]
	}

	public static def dispatch findMatchingModifier(org.emftext.language.java.modifiers.Modifier modifier,
		List<Modifier> candidates) {
		val javaModifierName = modifier.class.interfaces.get(0).simpleName.toLowerCase
		return candidates.filter(RegularModifier).findFirst[it.modifier.literal == javaModifierName]
	}

	public static def dispatch findMatchingModifier(AnnotationInstance annotation, List<Modifier> candidates) {
		// TODO process annotations
		if (annotation.annotation.name.equals("SuppressWarnings")) {
			return null
		}
		throw new IllegalArgumentException("Annotations are not supported at the moment.")
	}


	/**
	 * This does not care about whether the candidate is a model or regular element.
	 */
	public static def dispatch findMatchingMember(Method javaMember, Iterable<MemberDeclWithModifier> candidates) {
		if (candidates == null) {
			return null;
		}
		val jmlMethods = candidates.filter[memberdecl instanceof MemberDeclaration].filter[(memberdecl as MemberDeclaration).method != null]
		return jmlMethods.findFirst[Utilities.corresponds(javaMember, it)]
	}
	
	/**
	 * This does not care about whether the candidate is a model or regular element.
	 */
	public static def dispatch findMatchingMember(Field javaMember, Iterable<MemberDeclWithModifier> candidates) {
		if (candidates == null) {
			return null;
		}
		val jmlFields = candidates.filter[memberdecl instanceof MemberDeclaration].filter[(memberdecl as MemberDeclaration).field != null]
		return jmlFields.findFirst[(memberdecl as MemberDeclaration).field.variabledeclarator.exists[javaMember.name.equals(identifier)]]
	}

	
}
