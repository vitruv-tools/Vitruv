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
import org.emftext.language.java.types.NamespaceClassifierReference
import org.emftext.language.java.members.Field
import org.emftext.language.java.modifiers.Final

/** 
 * This class provides functions for determining the multiplicities of associations
 * by extracting information about collection interface implementation, arrays and 
 * final modifiers.
 */
class MultiplicityDetermination {
	private static final String COLLECTION_TYPE = "Collection";
	
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
		if (referencedClassifier == null) {
			return false;
		}
		
		return referencedClassifier.isCollection
	}
	
	public def boolean isFieldMultiValued(Field field) {
		return field.isFieldCollection || field.arrayDimension > 0;
	}
	
	private def boolean isCollection(Classifier classifier) {
		return classifier.getName().equals(COLLECTION_TYPE) || classifier.allSuperClassifiers.exists[superclass | superclass.isCollection];
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