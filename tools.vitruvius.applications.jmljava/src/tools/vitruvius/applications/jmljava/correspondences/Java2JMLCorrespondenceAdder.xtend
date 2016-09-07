package tools.vitruvius.applications.jmljava.correspondences

import tools.vitruvius.domains.jml.language.jML.ClassifierDeclarationWithModifier
import tools.vitruvius.domains.jml.language.jML.DeclaredException
import tools.vitruvius.domains.jml.language.jML.FormalParameterDecl
import tools.vitruvius.domains.jml.language.jML.GenericMethodOrConstructorDecl
import tools.vitruvius.domains.jml.language.jML.ImportDeclaration
import tools.vitruvius.domains.jml.language.jML.JMLSpecifiedElement
import tools.vitruvius.domains.jml.language.jML.MemberDeclaration
import tools.vitruvius.domains.jml.language.jML.MethodDeclaration
import tools.vitruvius.domains.jml.language.jML.Modifiable
import tools.vitruvius.domains.jml.language.jML.Modifier
import tools.vitruvius.domains.jml.language.jML.NormalClassDeclaration
import tools.vitruvius.domains.jml.language.jML.NormalInterfaceDeclaration
import tools.vitruvius.applications.jmljava.helper.JaMoPPConcreteSyntax
import tools.vitruvius.applications.jmljava.helper.StringOperationsJaMoPP
import tools.vitruvius.framework.correspondence.CorrespondenceModel
import tools.vitruvius.framework.correspondence.Correspondence
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.classifiers.ConcreteClassifier
import org.emftext.language.java.classifiers.Interface
import org.emftext.language.java.commons.NamedElement
import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.imports.Import
import org.emftext.language.java.members.Constructor
import org.emftext.language.java.members.Field
import org.emftext.language.java.members.Method
import org.emftext.language.java.modifiers.AnnotableAndModifiable
import org.emftext.language.java.parameters.Parameter
import org.emftext.language.java.types.NamespaceClassifierReference

import static extension tools.vitruvius.framework.correspondence.CorrespondenceModelUtil.*

class Java2JMLCorrespondenceAdder {
	
	private static val LOGGER = Logger.getLogger(Java2JMLCorrespondenceAdder)

	static def addCorrespondencesForCompilationUnit(CompilationUnit javaRoot, tools.vitruvius.domains.jml.language.jML.CompilationUnit jmlRoot, CorrespondenceModel ci) {
		ci.createAndAddCorrespondence(javaRoot, jmlRoot)
		
		LOGGER.debug(
			"Adding root element correspondence between " + javaRoot.name + " and " +
				jmlRoot.name)

		for (javaImport : javaRoot.imports) {
			val jmlImport = MatchingModelElementsFinder.findMatchingImport(javaImport, jmlRoot.importdeclaration)
			if (jmlImport != null) {
				addCorrespondences(javaImport, jmlImport, ci)
			} else {
				LOGGER.warn("No matching JML import for Java import (" + StringOperationsJaMoPP.getStringRepresentation(javaImport) + ") found.")
			}
		}
		
		for (javaClassifier : javaRoot.classifiers.filter(ConcreteClassifier)) {
			val jmlClass = MatchingModelElementsFinder.findMatchingClassifier(javaClassifier, jmlRoot.typedeclaration)
			if (jmlClass != null) {
				addCorrespondences(javaClassifier, jmlClass, ci)
			} else {
				LOGGER.warn("No matching JML classifier for Java classifier (" + StringOperationsJaMoPP.getQualifiedName(javaClassifier) + ") found.")
			}
		}
	}

	static def dispatch Void addCorrespondences(Interface javaInterface, ClassifierDeclarationWithModifier cdwm, CorrespondenceModel ci) {
		// class declaration with modifier
		val cdwmCorr = ci.createAndAddCorrespondence(javaInterface, cdwm)
		
		// modifiers
		addCorrespondencesForModifier(javaInterface, cdwm, ci, cdwmCorr)
		
		// class declaration
		val nid = cdwm.classOrInterfaceDeclaration as NormalInterfaceDeclaration
		val nidCorr = ci.createAndAddCorrespondence(javaInterface, nid)
		
		// implements
		for (implementedType : javaInterface.extends) {
			val jmlImplementedType = MatchingModelElementsFinder.findMatchingType(implementedType, 0, nid.implementedTypes)
			if (jmlImplementedType != null) {
				ci.createAndAddCorrespondence(implementedType, jmlImplementedType)
			} else {
				LOGGER.warn("No matching JML implemented type for Java implemented type (" + JaMoPPConcreteSyntax.convertToConcreteSyntax(implementedType) + ") found.")
			}
		}
		
		//TODO type parameters (if needed)
		// For types of fields, parameter, methods and so on this is not important since a change of a type parameter
		// (e.g. List<String> -> List<Integer>) can be represented as change of the whole value.
		
		val relevantElements = nid.bodyDeclarations.filter(JMLSpecifiedElement).filter[element != null]
			
		javaInterface.fields.forEach[javaField | addCorrespondencesNullableJMLElement(javaField, MatchingModelElementsFinder.findMatchingField(javaField, relevantElements), ci)]
		javaInterface.methods.forEach[javaMethod | addCorrespondencesNullableJMLElement(javaMethod, MatchingModelElementsFinder.findMatchingMethod(javaMethod, relevantElements), ci)]
		
		return null
	}

	static def dispatch Void addCorrespondences(Class javaClass, ClassifierDeclarationWithModifier cdwm, CorrespondenceModel ci) {
		// class declaration with modifier
		val cdwmCorr = ci.createAndAddCorrespondence(javaClass, cdwm)
		
		// modifiers
		addCorrespondencesForModifier(javaClass, cdwm, ci, cdwmCorr)
		
		// class declaration
		val ncd = cdwm.classOrInterfaceDeclaration as NormalClassDeclaration
		val ncdCorr = ci.createAndAddCorrespondence(javaClass, ncd)
		
		// implements
		
		for (implementedType : javaClass.implements) {
			val jmlImplementedType = MatchingModelElementsFinder.findMatchingType(implementedType, 0, ncd.implementedTypes)
			if (jmlImplementedType != null) {
				ci.createAndAddCorrespondence(implementedType, jmlImplementedType)
			} else {
				LOGGER.warn("No matching JML implemented type for Java implemented type (" + JaMoPPConcreteSyntax.convertToConcreteSyntax(implementedType) + ") found.")
			}
		}
		
		// extends
		if (javaClass.extends != null && ncd.superType != null) {
			ci.createAndAddCorrespondence(javaClass.extends, ncd.superType)
		}
		
		//TODO type parameters (if needed)
		// For types of fields, parameter, methods and so on this is not important since a change of a type parameter
		// (e.g. List<String> -> List<Integer>) can be represented as change of the whole value.
		
		val relevantElements = ncd.bodyDeclarations.filter(JMLSpecifiedElement).filter[element != null]
			
		javaClass.fields.forEach[javaField | addCorrespondencesNullableJMLElement(javaField, MatchingModelElementsFinder.findMatchingField(javaField, relevantElements), ci)]
		javaClass.methods.filter[!javaClass.members.filter(Constructor).toList.contains(it)].forEach[javaMethod | addCorrespondencesNullableJMLElement(javaMethod, MatchingModelElementsFinder.findMatchingMethod(javaMethod, relevantElements), ci)]
		javaClass.members.filter(Constructor).forEach[javaConstructor | addCorrespondencesNullableJMLElement(javaConstructor, MatchingModelElementsFinder.findMatchingConstructor(javaConstructor, relevantElements), ci)]
		
		return null
	}
	
	static def Void addCorrespondencesNullableJMLElement(NamedElement javaObject, EObject jmlObject, CorrespondenceModel ci) {
		if (jmlObject == null) {
			LOGGER.warn("No matching JML element for Java element (" + StringOperationsJaMoPP.getQualifiedName(javaObject.containingConcreteClassifier) + "." + javaObject.name + ") found.")
			return null
		}
		return addCorrespondences(javaObject, jmlObject, ci)
	}
	
	static def dispatch Void addCorrespondences(Method javaMethod, JMLSpecifiedElement jmlSpecifiedElement, CorrespondenceModel ci) {
		var topLevelCorr = ci.createAndAddCorrespondence(javaMethod, jmlSpecifiedElement)
		val jmlMemberDeclWithModifier = jmlSpecifiedElement.element
		
		// member declaration with modifier
		val jmlMemberDeclWithModifierCorrespondence = ci.createAndAddCorrespondence(javaMethod, jmlMemberDeclWithModifier)

		// modifiers
		addCorrespondencesForModifier(javaMethod, jmlMemberDeclWithModifier, ci, jmlMemberDeclWithModifierCorrespondence)
		
		var MethodDeclaration jmlMethodDeclaration = null
		var Correspondence memberDeclarationCorrespondence = null
		if (jmlMemberDeclWithModifier.memberdecl instanceof MemberDeclaration) {
			// member declaration
			val jmlMemberDecl = jmlMemberDeclWithModifier.memberdecl as MemberDeclaration
			memberDeclarationCorrespondence = ci.createAndAddCorrespondence(javaMethod, jmlMemberDecl)
			
			// return type
			if (javaMethod.typeReference != null && jmlMemberDecl.type != null) {
				ci.createAndAddCorrespondence(javaMethod.typeReference, jmlMemberDecl.type)
			}
			
			jmlMethodDeclaration = jmlMemberDecl.method
		} else if (jmlMemberDeclWithModifier.memberdecl instanceof GenericMethodOrConstructorDecl) {
			// member declaration
			val jmlMemberDecl = jmlMemberDeclWithModifier.memberdecl as GenericMethodOrConstructorDecl
			memberDeclarationCorrespondence = ci.createAndAddCorrespondence(javaMethod, jmlMemberDecl)
			
			// return type
			if (javaMethod.typeReference != null && jmlMemberDecl.type != null) {
				ci.createAndAddCorrespondence(javaMethod.typeReference, jmlMemberDecl.type)
			}
			
			jmlMethodDeclaration = jmlMemberDecl.method
		}

		
		// method declaration
		val methodDeclarationCorrespondence = ci.createAndAddCorrespondence(javaMethod, jmlMethodDeclaration)

		// exceptions
		for (javaException : javaMethod.exceptions) {
			val jmlException = MatchingModelElementsFinder.findMatchingException(javaException, jmlMethodDeclaration.exceptions)
			if (jmlException != null) {
				ci.createAndAddCorrespondence(javaException, jmlException)
			} else {
				LOGGER.warn("No matching JML exception for Java exception (" + StringOperationsJaMoPP.getStringRepresentation(javaException, 0) + ") found.")
			}
		}
		
		// parameters
		for (var i = 0; i < javaMethod.parameters.size; i++) {
			addCorrespondences(javaMethod.parameters.get(i), jmlMethodDeclaration.parameters.get(i), ci)
		}
		
		return null
	}
	
	static def dispatch Void addCorrespondences(Constructor javaConstructor, JMLSpecifiedElement jmlSpecifiedElement, CorrespondenceModel ci) {
		var topLevelCorr = ci.createAndAddCorrespondence(javaConstructor, jmlSpecifiedElement)
		val jmlMemberDeclWithModifier = jmlSpecifiedElement.element
		
		// member declaration with modifier
		val jmlMemberDeclWithModifierCorrespondence = ci.createAndAddCorrespondence(javaConstructor, jmlMemberDeclWithModifier)

		// modifiers
		addCorrespondencesForModifier(javaConstructor, jmlMemberDeclWithModifier, ci, jmlMemberDeclWithModifierCorrespondence)
		
		// member declaration
		val jmlMemberDecl = jmlMemberDeclWithModifier.memberdecl as tools.vitruvius.domains.jml.language.jML.Constructor
		val memberDeclarationCorrespondence = ci.createAndAddCorrespondence(javaConstructor, jmlMemberDecl)
		
		// exceptions
		for (javaException : javaConstructor.exceptions) {
			val jmlException = MatchingModelElementsFinder.findMatchingException(javaException, jmlMemberDecl.exceptions)
			if (jmlException != null) {
				ci.createAndAddCorrespondence(javaException, jmlException)
			} else {
				LOGGER.warn("No matching JML exception for Java exception (" + StringOperationsJaMoPP.getStringRepresentation(javaException, 0) + ") found.")
			}
		}
		
		// parameters
		for (var i = 0; i < javaConstructor.parameters.size; i++) {
			addCorrespondences(javaConstructor.parameters.get(i), jmlMemberDecl.parameters.get(i), ci)
		}
		
		return null
	}
	
	static def dispatch Void addCorrespondences(Field javaField, JMLSpecifiedElement jmlSpecifiedElement, CorrespondenceModel ci) {
		var topLevelCorr = ci.createAndAddCorrespondence(javaField, jmlSpecifiedElement)
		val jmlMemberDeclWithModifier = jmlSpecifiedElement.element
		
		// member declaration with modifier
		val jmlMemberDeclWithModifierCorrespondence = ci.createAndAddCorrespondence(javaField, jmlMemberDeclWithModifier)
		
		// modifiers
		addCorrespondencesForModifier(javaField, jmlMemberDeclWithModifier, ci, jmlMemberDeclWithModifierCorrespondence)
		
		// member declaration
		val jmlMemberDecl = jmlMemberDeclWithModifier.memberdecl as MemberDeclaration
		val memberDeclarationCorrespondence = ci.createAndAddCorrespondence(javaField, jmlMemberDecl)
		
		// type
		if (javaField.typeReference != null) {
			ci.createAndAddCorrespondence(javaField.typeReference, jmlMemberDecl.type)
		}
		
		// field declaration
		val jmlFieldDeclaration = (jmlMemberDeclWithModifier.memberdecl as MemberDeclaration).field
		ci.createAndAddCorrespondence(javaField, jmlFieldDeclaration)
		
		for (variableDeclarator : jmlFieldDeclaration.variabledeclarator) {
			var EObject javaObject;
			if (javaField.name.equals(variableDeclarator.identifier)) {
				javaObject = javaField
			} else {
				javaObject = javaField.additionalFields.findFirst[name.equals(variableDeclarator.identifier)]
			}
			
			if (javaObject != null) {
				ci.createAndAddCorrespondence(javaField, variableDeclarator)
			} else {
				LOGGER.warn("No matching JML field for Java field (" + StringOperationsJaMoPP.getQualifiedName(javaField.containingConcreteClassifier) + "." + javaField.name + ") or one of its additional fields found.")
			}
		}
		
		return null
	}
	
	static def dispatch Void addCorrespondences(Parameter javaParameter, FormalParameterDecl jmlParameter, CorrespondenceModel ci) {
		val parameterCorrespondence = ci.createAndAddCorrespondence(javaParameter, jmlParameter)
		
		ci.createAndAddCorrespondence(javaParameter.typeReference, jmlParameter.type)
		
		addCorrespondencesForModifier(javaParameter, jmlParameter, ci, parameterCorrespondence)
		
		return null
	}
	
	static def dispatch Void addCorrespondences(NamespaceClassifierReference javaException, DeclaredException jmlException, CorrespondenceModel ci) {
		ci.createAndAddCorrespondence(javaException, jmlException)
		return null
	}
	
	static def dispatch Void addCorrespondences(Import javaImport, ImportDeclaration jmlImport, CorrespondenceModel ci) {
		ci.createAndAddCorrespondence(javaImport, jmlImport)
		return null
	}
	
	static def dispatch Void addCorrespondences(org.emftext.language.java.modifiers.Modifier javaModifier, Modifier jmlModifier, CorrespondenceModel ci) {
		ci.createAndAddCorrespondence(javaModifier, jmlModifier)
		return null
	}
	
	private static def Void addCorrespondencesForModifier(AnnotableAndModifiable javaAnnotable, Modifiable jmlModifiable, CorrespondenceModel ci, Correspondence parentCorr) {
		// process modifiers
		for (javaModifier : javaAnnotable.annotationsAndModifiers) {
			val jmlModifier = MatchingModelElementsFinder.findMatchingModifier(javaModifier, jmlModifiable.modifiers)
			if (jmlModifier != null) {
				ci.createAndAddCorrespondence(javaModifier, jmlModifier)
			} else {
				LOGGER.warn("No matching JML modifier for Java modifier (" + javaModifier + ") found.")
			}
		}
		return null
	}

}