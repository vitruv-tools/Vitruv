package tools.vitruvius.applications.jmljava.helper

import org.apache.commons.lang.StringUtils
import org.emftext.language.java.classifiers.Classifier
import org.emftext.language.java.imports.ClassifierImport
import org.emftext.language.java.imports.PackageImport
import org.emftext.language.java.imports.StaticClassifierImport
import org.emftext.language.java.imports.StaticMemberImport
import org.emftext.language.java.members.Constructor
import org.emftext.language.java.members.Method
import org.emftext.language.java.parameters.OrdinaryParameter
import org.emftext.language.java.parameters.VariableLengthParameter
import org.emftext.language.java.references.ReferenceableElement
import org.emftext.language.java.types.NamespaceClassifierReference
import org.emftext.language.java.types.PrimitiveType
import org.emftext.language.java.classifiers.ConcreteClassifier

/**
 * Converts a JaMoPP model element to a string representation.
 * This representation is not necessarily the concrete syntax
 * but might be anything appropriate to fulfil the need in the
 * place it is used.
 * Use the {@link JaMoPPConcreteSyntax} class for strict
 * concrete syntax conversions.
 */
class StringOperationsJaMoPP {

	static def dispatch getStringRepresentation(ClassifierImport imprt) {
		return imprt.namespaces.getNamespaceAsString + "." + imprt.classifier.name
	}

	static def dispatch getStringRepresentation(StaticClassifierImport imprt) {
		return imprt.namespaces.getNamespaceAsString + ".*"
	}

	static def dispatch getStringRepresentation(PackageImport imprt) {
		return imprt.namespaces.getNamespaceAsString + ".*"
	}

	static def dispatch getStringRepresentation(StaticMemberImport imprt) {
		return imprt.namespaces.getNamespaceAsString + "." + getNameList(imprt.staticMembers)
	}
	
	
	/**
	* Returns the qualified name of this concrete classifier.
	* 
	* This method is part of JaMoPP (https://github.com/DevBoost/JaMoPP) Rev. fbc0471
	* org.emftext.language.java.extensions.classifiers.ConcreteClassifierExtension.java
	* Licensed under EPL v1.0
	* Contributors:
	*   Software Technology Group - TU Dresden, Germany;
	*   DevBoost GmbH - Berlin, Germany
	*/
	static def getQualifiedName(ConcreteClassifier c) {
		val qualifiedName = new StringBuilder();
		val packageParts = c.getContainingPackageName();
		for (String packagePart : packageParts) {
			qualifiedName.append(packagePart);
			qualifiedName.append(".");
		}
		qualifiedName.append(c.getName());
		return qualifiedName.toString();
	}

	private static def getNameList(Iterable<ReferenceableElement> elements) {
		val strBuilder = new StringBuilder()
		elements.forEach[strBuilder.append(name + ",")]
		strBuilder.deleteCharAt(strBuilder.length - 1)
		return strBuilder.toString
	}

	static def dispatch String getStringRepresentation(Method javaMethod) {
		val javaIdentifier = new StringBuilder();
		javaIdentifier.append(javaMethod.typeReference.getStringRepresentation(javaMethod.arrayDimension))
		javaIdentifier.append(javaMethod.name)
		javaMethod.parameters.forEach[
			javaIdentifier.append(typeReference.getStringRepresentation(arrayDimension))]
		return javaIdentifier.toString
	}
	
	static def dispatch String getStringRepresentation(Constructor javaConstructor) {
		val representation = new StringBuilder()
		representation.append(javaConstructor.name)
		representation.append('(')
		javaConstructor.parameters.forEach[representation.append(typeReference.getStringRepresentation(arrayDimension))]
		representation.append(')')
		return representation.toString
	}

	static def dispatch String getStringRepresentation(PrimitiveType pt, long arrayDimensions) {
		return pt.class.interfaces.get(0).simpleName.toLowerCase + StringUtils.repeat("[]", arrayDimensions as int)
	}

	static def dispatch String getStringRepresentation(NamespaceClassifierReference ncr, long arrayDimensions) {
		val strBuilder = new StringBuilder()
		ncr.classifierReferences.forEach[strBuilder.append(target.name)]
		strBuilder.append(StringUtils.repeat("[]", arrayDimensions as int))
		return strBuilder.toString
	}

	static def dispatch String getStringRepresentation(Classifier pt, long arrayDimensions) {
		return pt.name + StringUtils.repeat("[]", arrayDimensions as int)
	}

	static def dispatch String getStringRepresentation(OrdinaryParameter param) {
		return param.typeReference.target.getStringRepresentation(param.arrayDimension)
	}

	static def dispatch String getStringRepresentation(VariableLengthParameter param) {
		return param.typeReference.target.getStringRepresentation(param.arrayDimension) + "..."
	}

	static def String getNamespaceAsString(Iterable<String> namespaces) {
		return StringUtils.join(namespaces, '.');
	}
}
