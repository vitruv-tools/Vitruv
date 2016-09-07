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

import org.emftext.language.java.members.Field
import org.emftext.language.java.modifiers.AnnotableAndModifiable
import org.emftext.language.java.types.TypeReference
import org.emftext.language.java.types.Void
import org.eclipse.emf.ecore.EObject
import org.emftext.language.java.members.Method
import org.emftext.language.java.members.Constructor
import org.emftext.language.java.parameters.Parametrizable
import org.emftext.language.java.members.Member
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.classifiers.Interface
import org.emftext.language.java.arrays.ArrayTypeable
import org.emftext.language.java.types.TypedElement
import org.emftext.language.java.parameters.Parameter
import org.emftext.language.java.parameters.VariableLengthParameter

/**
 * This class provides procedures for determining the string representations of class members 
 * as well as currently unused names for methods or fields inside a class or interface.
 * 
 * @author Heiko Klare
 */
public final class MemberRepresentationsAndUtils {
	private static final String NEW_METHOD_PREFIX = "newMethod";
	private static final String NEW_FIELD_PREFIX = "newField";

	/**
	 * Returns the string representation of the visibility modifier for the given object or an 
	 * empty string if there is none. 
	 */
	public def dispatch String getModifierStringRepresentation(EObject object) {
		return "";
	}

	public def dispatch String getModifierStringRepresentation(AnnotableAndModifiable member) {
		if (member.isPrivate) {
			return "-";
		} else if (member.isProtected) {
			return "#";
		} else if (member.isPublic) {
			return "+";
		} else {
			return " ";
		}
	}

	/**
	 * Returns the UML style representation of a type which can be a double point
	 * followed by the type or an empty string if the type is void.
	 */
	private def dispatch getUmlTypeString(Void voidType) {
		return "";
	}

	private def dispatch getUmlTypeString(Field field) {
		return field.typeElementUmlTypeString + field.arrayString;
	}
	
	private def dispatch getUmlTypeString(Parameter param) {
		return param.typeElementUmlTypeString + param.arrayString;
	}
	
	private def dispatch getUmlTypeString(VariableLengthParameter param) {
		return param.typeElementUmlTypeString + param.arrayString + "...";
	}
	
	private def dispatch getUmlTypeString(Method method) {
		return method.typeElementUmlTypeString + method.arrayString;
	}
	
	private def getTypeElementUmlTypeString(TypedElement typedElement) {
		return typedElement.typeReference.basicUmlTypeString;		
	}
	
	private def dispatch getBasicUmlTypeString(TypeReference typeReference) {
		return " : " + new DataTypeRepresentations().typeToString(typeReference);
	}
	
	private def dispatch getBasicUmlTypeString(Void voidType) {
		return "";
	}

	private def String getArrayString(ArrayTypeable arrayType) {
		var result = "";
		for (var i = 0; i < arrayType.arrayDimensionsAfter.size() + arrayType.arrayDimensionsBefore.size(); i++) {
			result += "[]";
		}
		return result;
	}
	
	/**
	 * Returns the string representation of the given class member. In case of a field this is
	 * the field name followed by the type, in case of a method it is the UML representation of
	 * a method: the method name, followed by the parameter list and the return type. 
	 */
	public def dispatch String getRepresentation(EObject member) '''	'''

	private def String getParameterRepresentation(Parametrizable object) '''
	«FOR parameter : object.parameters SEPARATOR ', '»«parameter.name»«parameter.umlTypeString»«ENDFOR»'''

	public def dispatch String getRepresentation(Method method) '''
	«method.name»(«method.parameterRepresentation»)«method.umlTypeString»'''

	public def dispatch String getRepresentation(Constructor method) '''
	<<create>> «method.name»(«method.parameterRepresentation»)'''

	public def dispatch String getRepresentation(Field field) '''
	«field.name»«field.umlTypeString»'''

	public def dispatch String getRepresentation(Interface interf) {
		val namespacesBeforeName = ("<<interface>>".length - interf.name.length);
		val namespacesBeforeStereotype = - namespacesBeforeName;
		var beforeName = "";
		for (var i = 0; i < namespacesBeforeName; i++) {
			beforeName = beforeName + " ";
		}
		var beforeStereotype = "";
		for (var i = 0; i < namespacesBeforeStereotype; i++) {
			beforeStereotype = beforeStereotype + " ";
		}
		return '''
			«beforeStereotype»<<interface>>
			«beforeName»«interf.name»'''
	}


	private def String getNewUnusedMemberName(Iterable<? extends Member> existingMembers, String memberPrefix) {
		var nameIsUnused = false;
		var counter = 0;
		while (!nameIsUnused) {
			counter++;
			nameIsUnused = true;
			val currentMethodName = memberPrefix + counter;
			for (Member member : existingMembers) {
				if (currentMethodName.equals(member.name)) {
					nameIsUnused = false;
				}
			}
		}

		return memberPrefix + counter;
	}

	/** 
	 * Returns a new unused method name for the given class.
	 */
	public def String getNewUnusedMethodName(Class classObject) {
		return getNewUnusedMemberName(classObject.members.filter(Method), NEW_METHOD_PREFIX);
	}

	/** 
	 * Returns a new unused method name for the given interface.
	 */
	public def String getNewUnusedMethodName(Interface interfaceObject) {
		return getNewUnusedMemberName(interfaceObject.members.filter(Method), NEW_METHOD_PREFIX);
	}

	/** 
	 * Returns a new unused field name for the given class.
	 */
	public def String getNewUnusedFieldName(Class classObject) {
		return getNewUnusedMemberName(classObject.members.filter(Field), NEW_FIELD_PREFIX);
	}
}