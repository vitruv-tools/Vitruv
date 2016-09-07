package tools.vitruvius.applications.jmljava.synchronizers.java

import tools.vitruvius.applications.jmljava.helper.StringOperationsJaMoPP
import tools.vitruvius.domains.jml.language.jML.JMLFactory
import tools.vitruvius.domains.jml.language.jML.MemberDeclaration
import tools.vitruvius.domains.jml.language.jML.MethodDeclaration
import tools.vitruvius.domains.jml.language.jML.Modifiable
import tools.vitruvius.domains.jml.language.jML.ModifierValue
import tools.vitruvius.domains.jml.language.jML.Type
import org.apache.log4j.Logger
import org.emftext.language.java.arrays.ArrayTypeable
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.imports.Import
import org.emftext.language.java.imports.StaticImport
import org.emftext.language.java.members.Field
import org.emftext.language.java.members.Method
import org.emftext.language.java.modifiers.AnnotableAndModifiable
import org.emftext.language.java.modifiers.Modifier
import org.emftext.language.java.parameters.Parameter
import org.emftext.language.java.types.NamespaceClassifierReference
import org.emftext.language.java.types.PrimitiveType
import org.emftext.language.java.types.TypedElement
import org.emftext.language.java.types.Void

class CommonSynchronizerTasks {
	
	static val LOGGER = Logger.getLogger(CommonSynchronizerTasks)
	
	static def createJMLClass(Class javaClass) {
		val classifierDeclWithModifier = JMLFactory.eINSTANCE.createClassifierDeclarationWithModifier
		
		addModifiersTo(javaClass, classifierDeclWithModifier)
		
		val normalClassDecl = JMLFactory.eINSTANCE.createNormalClassDeclaration
		classifierDeclWithModifier.classOrInterfaceDeclaration = normalClassDecl
		
		normalClassDecl.identifier = javaClass.name
		if (javaClass.extends != null) {
			normalClassDecl.superType = createJMLType(javaClass.extends, 0)
		}
		javaClass.implements.forEach[normalClassDecl.implementedTypes += createJMLType(0)]
		
		//TODO type parameters
		
		javaClass.fields.forEach[normalClassDecl.bodyDeclarations += createJMLField]
		javaClass.methods.forEach[normalClassDecl.bodyDeclarations += createJMLMethod]
		
		return classifierDeclWithModifier
	}
	
	private static def createMemberDeclWithModifier(AnnotableAndModifiable javaAnnotable, ArrayTypeable javaArrTyp, TypedElement javaTyped) {
		// top level element
		val jmlMemberDeclWithModifier = JMLFactory.eINSTANCE.createMemberDeclWithModifierRegular()

		// modifiers
		addModifiersTo(javaAnnotable, jmlMemberDeclWithModifier)
		
		// second level element
		val jmlMemberDeclaration = JMLFactory.eINSTANCE.createMemberDeclaration()
		jmlMemberDeclWithModifier.memberdecl = jmlMemberDeclaration
		
		// type
		jmlMemberDeclaration.type = CommonSynchronizerTasks.createJMLType(javaTyped.typeReference, javaArrTyp.arrayDimension)
		
		return jmlMemberDeclWithModifier
	}
	
	static def createJMLField(Field javaField) {
		// top level element
		val jmlSpecElement = JMLFactory.eINSTANCE.createJMLSinglelineSpec
		val jmlMemberDeclWithModifier = createMemberDeclWithModifier(javaField, javaField, javaField)
		jmlSpecElement.element = jmlMemberDeclWithModifier

		val varDecl = JMLFactory.eINSTANCE.createVariableDeclarator()
		varDecl.identifier = javaField.name

		val fieldDeclaration = JMLFactory.eINSTANCE.createFieldDeclaration()
		fieldDeclaration.variabledeclarator.add(varDecl)

		(jmlMemberDeclWithModifier.memberdecl as MemberDeclaration).field = fieldDeclaration
			
		if (javaField.initialValue != null) {
			LOGGER.warn("Field initializers are not supported at the moment and are omitted.")
		}
		
		return jmlSpecElement
	}
	
	static def createJMLMethod(Method javaMethod) {
		// top level element
		val jmlSpecElement = JMLFactory.eINSTANCE.createJMLSinglelineSpec
		val jmlMemberDeclWithModifier = createMemberDeclWithModifier(javaMethod, javaMethod, javaMethod)
		jmlSpecElement.element = jmlMemberDeclWithModifier
		
		// third level elements
		val jmlMethodDeclaration = JMLFactory.eINSTANCE.createMethodDeclaration()
		(jmlMemberDeclWithModifier.memberdecl as MemberDeclaration).method = jmlMethodDeclaration
		jmlMethodDeclaration.identifier = javaMethod.name

		// exceptions
		addExceptionsToMethod(javaMethod, jmlMethodDeclaration)
		
		// parameters
		addParametersToMethod(javaMethod, jmlMethodDeclaration)
		
		return jmlSpecElement
	}
	
	static def createJMLType(Object javaType, long arrayDimensions) {
		var Type jmlType;
		
		if (javaType instanceof PrimitiveType) {
			if (javaType instanceof Void) {
				jmlType = null
			} else {
				val primitiveTypeStr = StringOperationsJaMoPP.getStringRepresentation(javaType as PrimitiveType, 0)
				val primitiveType = JMLFactory.eINSTANCE.createPrimitiveTypeWithBrackets()
				primitiveType.primitivetype = tools.vitruvius.domains.jml.language.jML.PrimitiveType.get(primitiveTypeStr)
				jmlType = primitiveType
			}
		} else if (javaType instanceof NamespaceClassifierReference) {
			val javaClassifierReferences = (javaType as NamespaceClassifierReference).classifierReferences
			if (javaClassifierReferences.size != 1) {
				throw new IllegalArgumentException("A new return type, which consists of multiple types, is not supported.")
			}
			val javaClassifier = javaClassifierReferences.get(0).target
			val classifierType = JMLFactory.eINSTANCE.createClassOrInterfaceTypeWithBrackets()
			classifierType.type.add(JMLFactory.eINSTANCE.createClassifierType())
			classifierType.type.get(0).identifier = javaClassifier.name
			jmlType = classifierType
		} else {
			throw new IllegalArgumentException("The type of the new return type " + javaType + " is not supported.")
		}
		
		// add brackets to new type
		for (var i = 0; i < arrayDimensions; i++) {
			jmlType.brackets += JMLFactory.eINSTANCE.createBrackets()
		}

		return jmlType
	}
	
	static def addModifiersTo(AnnotableAndModifiable javaElement, Modifiable jmlMemberDecl) {
		javaElement.modifiers.forEach[jmlMemberDecl.modifiers += createJMLModifier(it)]
	}
	
	static def createJMLModifier(Modifier javaModifier) {
		val regularModifier = JMLFactory.eINSTANCE.createRegularModifier()
		regularModifier.modifier = ModifierValue.get(javaModifier.class.interfaces.get(0).simpleName.toLowerCase)
		return regularModifier
	}
	
	static def addParametersToMethod(Method javaMethod, MethodDeclaration jmlMethodDeclaration) {
		for (param : javaMethod.parameters) {
			jmlMethodDeclaration.parameters += createJMLParameter(param)
		}
	}
	
	static def createJMLParameter(Parameter javaParameter) {
		val jmlFormalParameterDecl = JMLFactory.eINSTANCE.createFormalParameterDecl()
		jmlFormalParameterDecl.identifier = javaParameter.name
		jmlFormalParameterDecl.type = CommonSynchronizerTasks.createJMLType(javaParameter.typeReference, javaParameter.arrayDimension)
		
		for (modifier : javaParameter.modifiers) {
			jmlFormalParameterDecl.modifiers += createJMLModifier(modifier)
		}
		return jmlFormalParameterDecl
	}
	
	static def addExceptionsToMethod(Method javaMethod, MethodDeclaration jmlMethodDeclaration) {
		javaMethod.exceptions.forEach[jmlMethodDeclaration.exceptions += createJMLException(it)]
	}
	
	def static createJMLException(NamespaceClassifierReference reference) {
		val declaredException = JMLFactory.eINSTANCE.createDeclaredException()
		
		if (reference.classifierReferences.size > 1) {
			throw new IllegalArgumentException("A classifier reference, which consists of multiple references, is not supported.")
		}
		
		declaredException.name = reference.classifierReferences.get(0).target.name
		return declaredException
	}
	
	static def createJMLImportDeclaration(Import javaImport) {
		val jmlImportDeclaration = JMLFactory.eINSTANCE.createImportDeclaration()
		jmlImportDeclaration.static = javaImport instanceof StaticImport
		jmlImportDeclaration.qualifiedname = StringOperationsJaMoPP.getStringRepresentation(javaImport)
		if (jmlImportDeclaration.qualifiedname.endsWith(".*")) {
			jmlImportDeclaration.qualifiedname = jmlImportDeclaration.qualifiedname.substring(0, jmlImportDeclaration.qualifiedname.lastIndexOf(".*"))
			jmlImportDeclaration.wildcard = true
		}
		return jmlImportDeclaration
	}
	
}