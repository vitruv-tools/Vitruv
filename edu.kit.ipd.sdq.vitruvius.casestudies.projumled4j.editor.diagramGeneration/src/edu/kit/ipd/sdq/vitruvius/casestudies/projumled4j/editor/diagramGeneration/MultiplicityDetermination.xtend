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

package edu.kit.ipd.sdq.vitruvius.casestudies.projumled4j.editor.diagramGeneration

import org.emftext.language.java.types.TypeReference
import org.emftext.language.java.classifiers.Classifier
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.classifiers.Interface
import org.emftext.language.java.types.NamespaceClassifierReference
import org.emftext.language.java.members.Field
import org.emftext.language.java.modifiers.Final
import org.emftext.language.java.generics.QualifiedTypeArgument
import org.emftext.language.java.generics.TypeArgument
import org.emftext.language.java.classifiers.ConcreteClassifier
import java.util.ArrayList
import org.emftext.language.java.types.ClassifierReference
import java.util.Collection
import java.util.List
import org.eclipse.emf.common.util.EList
import org.emftext.language.java.generics.TypeParameter

/** 
 * This class provides functions for determining the multiplicities of associations
 * by extracting information about collection interface implementation, arrays and 
 * final modifiers.
 */
class MultiplicityDetermination {
	private static final String COLLECTION_TYPE = "Iterable";
	
	public def int determineTargetLowerMultiplicity(Field field) {
		if (isFieldFinal(field) && !isFieldMultiValued(field)) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public def int determineTargetUpperMultiplicity(Field field) {
		if (isFieldMultiValued(field)) {
			return -1;
		} else {
			return 1;
		}
	}
	
	public def boolean isFieldFinal(Field field) {
		return !field.annotationsAndModifiers.filter(Final).empty
	}
	
	public def boolean isFieldCollection(Field field) {
		val referencedClassifier = getReferencedClassifier(field.typeReference);
		if (referencedClassifier instanceof ConcreteClassifier) {
			return referencedClassifier.isCollection
		} else {
			return false;
		}
	}
	
	public def boolean isFieldMultiValued(Field field) {
		return field.isFieldCollection || field.arrayDimension > 0;
	}
	
	private def boolean isCollection(ConcreteClassifier classifier) {
		if (classifier.getName().equals(COLLECTION_TYPE)) {
			return true;
		}
		
		// Our field must have exactly one type parameter, the one of the collection type,
		// otherwise we do not want to see this field as a pure collection
		if (classifier.typeParameters.size != 1) {
			return false;
		}
		
		val typeReferences = new ArrayList<TypeReference>();	
		if (classifier instanceof Class) {
			for (TypeReference interfaceReference : classifier.implements) {
				typeReferences += interfaceReference;
			}
			if (classifier.extends != null) {
				typeReferences += classifier.extends;
			}
		} else if (classifier instanceof Interface) {
			for (TypeReference interfaceReference : classifier.extends) {
				typeReferences += interfaceReference;
			}			
		}
		
		val classifierReferences = new ArrayList<ClassifierReference>();
		for (TypeReference typeRef : typeReferences) {
			classifierReferences += typeRef.classifierReferences;
		}
		
		// If we have one type parameter and the same as one of the super references
		// that is itself implementing the collection interface, we see this classifier
		// as a pure collection
		for (superReference : classifierReferences) {
			val implementedTypeParameterNames = new ArrayList<String>();
			for (TypeArgument typeArgument : superReference.typeArguments) {
				val typeArgumentReferences = (typeArgument as QualifiedTypeArgument).typeReference.classifierReferences;
				if (typeArgumentReferences.size > 0) {
					val typeTarget = typeArgumentReferences.get(0).target;
					if (typeTarget instanceof TypeParameter) {
						implementedTypeParameterNames += (typeTarget as TypeParameter).name	
					}
				}
			}
			if (implementedTypeParameterNames.size == 1 && 
				(superReference.target as ConcreteClassifier).isCollection &&
				classifier.typeParameters.get(0).name.equals(implementedTypeParameterNames.get(0))) {
				return true;
			}
		}
		
		return false;
	}
	
	public def Iterable<ClassifierReference> getClassifierReferences(TypeReference typeRef) {
		if (typeRef instanceof NamespaceClassifierReference) {
			return typeRef.classifierReferences;
		} else if (typeRef instanceof ClassifierReference) {
			val result = new ArrayList<ClassifierReference>();
			result.add(typeRef);
			return result; 
		}
	}
	
	private def Classifier getReferencedClassifier(TypeReference typeReference) {
		if (typeReference instanceof NamespaceClassifierReference) {
			val reference = typeReference as NamespaceClassifierReference; 
			val referenceType = reference.getTarget();
			if (referenceType instanceof Classifier) {
				return referenceType as Classifier;
			}
		}
		return null;
	}
}