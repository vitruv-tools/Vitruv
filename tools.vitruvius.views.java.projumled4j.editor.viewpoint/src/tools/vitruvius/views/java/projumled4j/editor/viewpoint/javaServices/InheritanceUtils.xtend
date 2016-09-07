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

import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.types.NamespaceClassifierReference
import org.eclipse.emf.common.util.EList
import org.emftext.language.java.classifiers.Interface
import org.eclipse.emf.common.util.BasicEList
import org.emftext.language.java.types.TypeReference

/**
 * This class provides procedure for calculating inheritance relationships between classes and interfaces.
 * 
 * @author Heiko Klare
 */
public final class InheritanceUtils {
	/**
	 * Returns the super class of the given {@link Class} if existing, null otherwise.
	 */
	public def Class getSuperClass(Class ofClass) {
		val superClass = ofClass.extends;
		if (superClass instanceof NamespaceClassifierReference) {
			val classifierReferences = (superClass as NamespaceClassifierReference).classifierReferences;
			if (!classifierReferences.nullOrEmpty) {
				val superClassTarget = classifierReferences.get(0).target;
				if (superClassTarget instanceof Class) {
					return superClassTarget as Class;
				}
			}
		}
		return null;		
	}
	
	/**
	 * Returns the {@link Interface}s referenced by the given list of {@link TypeReferences}s.
	 */
	private def EList<Interface> getInterfaces(EList<TypeReference> typeReferences) {
		val result = new BasicEList<Interface>();
		for (TypeReference potentialInterfaceReference : typeReferences) {
			if (potentialInterfaceReference instanceof NamespaceClassifierReference) {
				val classifierReferences = (potentialInterfaceReference as NamespaceClassifierReference).classifierReferences;
				if (!classifierReferences.nullOrEmpty) {
					val interfaceTarget = classifierReferences.get(0).target;
					if (interfaceTarget instanceof Interface) {
						result.add(interfaceTarget as Interface);
					}
				}
			}
		}
		return result;		
	}
	
	/** 
	 * Returns the {@link Interface}s implemented by the given {@link Class}.
	 */
	public def EList<Interface> getImplementedInterfaces(Class ofClass) {
		val interfaceReferences = ofClass.implements;
		return interfaceReferences.interfaces;		
	}
	
	/**
	 * Returns the interfaces the given one is extending.
	 */
	public def EList<Interface> getImplementedInterfaces(Interface ofInterface) {
		val interfaceReferences = ofInterface.extends;
		return interfaceReferences.interfaces;	
	}
	
}