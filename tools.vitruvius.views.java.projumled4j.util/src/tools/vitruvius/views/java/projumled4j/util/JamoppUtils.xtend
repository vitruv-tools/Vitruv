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

package tools.vitruvius.views.java.projumled4j.util

import org.emftext.language.java.members.Field
import org.emftext.language.java.classifiers.ConcreteClassifier
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.classifiers.Interface
import org.emftext.language.java.types.TypeReference
import java.util.ArrayList
import org.emftext.language.java.types.ClassifierReference
import org.emftext.language.java.types.NamespaceClassifierReference
import org.emftext.language.java.generics.TypeParameter
import org.emftext.language.java.generics.QualifiedTypeArgument
import org.emftext.language.java.generics.TypeArgument
import org.emftext.language.java.classifiers.Classifier
import org.emftext.language.java.modifiers.Final

/** 
 * This utils provide functionality for extracting information from JaMoPP models.
 */
public final class JamoppUtils {
	private static final String GENERIC_COLLECTION_TYPE = "Iterable";
	
	public static def boolean isFieldFinal(Field field) {
		return !field.annotationsAndModifiers.filter(Final).empty
	}
	
	public static def boolean isFieldCollection(Field field) {
		val referencedClassifier = getReferencedClassifier(field.typeReference);
		if (referencedClassifier instanceof ConcreteClassifier) {
			return referencedClassifier.isCollection
		} else {
			return false;
		}
	}
	
	public static def boolean isFieldMultiValued(Field field) {
		return field.isFieldCollection || field.arrayDimension > 0;
	}
	
	private static def boolean isCollection(ConcreteClassifier classifier) {
		if (classifier.getName().equals(GENERIC_COLLECTION_TYPE)) {
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
			classifierReferences += typeRef.classifierReference;
		}
		
		// If we have one type parameter and the same as one of the super references
		// that is itself implementing the collection interface, we see this classifier
		// as a pure collection
		for (superReference : classifierReferences) {
			val implementedTypeParameterNames = new ArrayList<String>();
			for (TypeArgument typeArgument : superReference.typeArguments) {
				val typeArgumentReference = (typeArgument as QualifiedTypeArgument).typeReference.classifierReference;
				val typeTarget = typeArgumentReference.target;
				if (typeTarget instanceof TypeParameter) {
					implementedTypeParameterNames += (typeTarget as TypeParameter).name	
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
	
	public static def Classifier getCollectionTarget(TypeReference collectionReference) {
		val referencedClassifier = getReferencedClassifier(collectionReference);
		if (!(referencedClassifier instanceof ConcreteClassifier)) {
			throw new IllegalStateException("Reference is no collection because the classifier is not valid");
		}

		val classifierReference = collectionReference.getClassifierReference;
		if (classifierReference.typeArguments.size != 1) {
			throw new IllegalStateException("Reference is no collection because there is more than one type argument");
		}
		
		val typeArgument = classifierReference.getTypeArguments().get(0);
		if (typeArgument instanceof QualifiedTypeArgument) {
			return getReferencedClassifier(typeArgument.getTypeReference());
		}
	}
	
	public static def ClassifierReference getClassifierReference(TypeReference typeRef) {
		if (typeRef instanceof NamespaceClassifierReference) {
			if (typeRef.classifierReferences.size != 1) {
				throw new IllegalStateException("TypeReference has more than one classifier reference");
			}
			return typeRef.classifierReferences.get(0);
		} else if (typeRef instanceof ClassifierReference) {
			return typeRef;
		}
	}
	
	public static def Classifier getReferencedClassifier(TypeReference typeReference) {
		val classifierReference = getClassifierReference(typeReference);
		return classifierReference?.target;
	}
}