/*******************************************************************************
 * Copyright (c) 2015 Heiko Klare (Karlsruhe Institute of Technology)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Heiko Klare - initial API and implementation and/or initial documentation
 *******************************************************************************/ 
 
package tools.vitruvius.views.java.projumled4j.editor.viewpoint.javaServices

import org.emftext.language.java.types.NamespaceClassifierReference
import org.eclipse.emf.ecore.EObject
import org.apache.log4j.LogManager;
import org.emftext.language.java.types.Int
import org.emftext.language.java.types.Void
import org.emftext.language.java.types.Double
import org.emftext.language.java.types.Float
import org.emftext.language.java.types.Boolean
import org.emftext.language.java.types.Char
import org.emftext.language.java.types.Long
import org.emftext.language.java.types.Byte
import org.emftext.language.java.types.Short
import org.emftext.language.java.types.ClassifierReference
import org.emftext.language.java.generics.QualifiedTypeArgument
import tools.vitruvius.views.java.projumled4j.util.JamoppUtils

/**
 * This class provides procedures for getting string representations of data types.
 * 
 * @author Heiko Klare
 */
public final class DataTypeRepresentations {
	val logger = LogManager.getLogger(typeof(DataTypeRepresentations));
	
	/**
	 * Returns the string representation of the specified object or "void" if elements
	 * of the given type are not supported
	 */
	public def dispatch String typeToString(EObject object) {
		logger.error("Tried to convert an unsupported type to string: " + object.class);
		return "void";
	}
	
	public def dispatch String typeToString(Int integer) {
		return "int"
	}
	
	public def dispatch String typeToString(Float fl) {
		return "float"
	}
	
	public def dispatch String typeToString(Double dou) {
		return "double"
	}
	
	public def dispatch String typeToString(Long lo) {
		return "long"
	}
	
	public def dispatch String typeToString(Boolean boo) {
		return "boolean"
	}
	
	public def dispatch String typeToString(Void v) {
		return "void"
	}
	
	public def dispatch String typeToString(Char c) {
		return "char"
	}
	
	public def dispatch String typeToString(Byte b) {
		return "byte"
	}
	
	public def dispatch String typeToString(Short s) {
		return "short"
	}
	
	public def dispatch String typeToString(ClassifierReference classifierReference) {
		var suffix = "";
		if (!classifierReference.typeArguments.empty) {
			suffix =
			'''<«FOR typeArgument : classifierReference.typeArguments.filter(QualifiedTypeArgument) SEPARATOR ", "»«
					»«JamoppUtils.getReferencedClassifier(typeArgument.typeReference).name»«
				ENDFOR»>'''
		}
		return classifierReference.target.name + suffix;
	}
	
	public def dispatch String typeToString(NamespaceClassifierReference typeReference) {
		val classifierReferences = typeReference.getClassifierReferences();
		if (classifierReferences.nullOrEmpty) {
			logger.error("No classifier reference was specified for a type reference.");
			return "void";
		} else {
			return classifierReferences.get(0).typeToString();
		}
	}
}