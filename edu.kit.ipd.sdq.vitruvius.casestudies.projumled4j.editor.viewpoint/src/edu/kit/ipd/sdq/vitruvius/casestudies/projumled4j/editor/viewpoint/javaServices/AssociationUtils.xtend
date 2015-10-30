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
 
package edu.kit.ipd.sdq.vitruvius.casestudies.projumled4j.editor.viewpoint.javaServices

import org.emftext.language.java.members.Field
import org.emftext.language.java.annotations.AnnotationInstance
import org.eclipse.emf.ecore.EObject
import org.emftext.language.java.types.NamespaceClassifierReference
import org.emftext.language.java.classifiers.Annotation
import org.emftext.language.java.literals.DecimalIntegerLiteral
import org.emftext.language.java.expressions.Expression
import org.emftext.language.java.expressions.UnaryExpression
import org.emftext.language.java.operators.Subtraction
import org.emftext.language.java.annotations.AnnotationParameterList
import org.emftext.language.java.references.IdentifierReference
import org.emftext.language.java.members.EnumConstant
import edu.kit.ipd.sdq.vitruvius.casestudies.projumled4j.annotations.Association
import org.emftext.language.java.classifiers.Classifier
import org.emftext.language.java.types.TypeReference
import org.emftext.language.java.generics.QualifiedTypeArgument
import org.emftext.language.java.generics.TypeParameter
import org.emftext.language.java.generics.TypeArgument
import java.util.ArrayList
import org.emftext.language.java.types.ClassifierReference
import org.emftext.language.java.classifiers.Interface
import org.emftext.language.java.classifiers.ConcreteClassifier
import org.emftext.language.java.classifiers.Class

/**
 * This class specifies utility methods for determining if a a field represents an association
 * (specified by an annotation) and which kind of association it represents. Furthermore it provides
 * procedures for getting string representations of multiplicities.
 * 
 * @author Heiko Klare    
 */
public final class AssociationUtils {
	private static final String MEMBER_SOURCE_LOWER_MULTIPLITY = "sourceLowerMultiplicity";
	private static final String MEMBER_SOURCE_UPPER_MULTIPLITY = "sourceUpperMultiplicity";
	private static final String MEMBER_TARGET_LOWER_MULTIPLITY = "targetLowerMultiplicity";
	private static final String MEMBER_TARGET_UPPER_MULTIPLITY = "targetUpperMultiplicity";
	private static final String MEMBER_TYPE = "type";
	private static final String MEMBER_TYPE_AGGREGATION = "Aggregation";
	private static final String MEMBER_TYPE_COMPOSITION = "Composition";
	private static final String ASSOCIATION_TAG = "Association";
	private static final String COLLECTION_TYPE = "Collection";
	
	public def dispatch Classifier getAssociationTarget(EObject object) {
		return null;
	}
	
	public def dispatch Classifier getAssociationTarget(Field field) {
		var referencedClassifier = field.typeReference.referencedClassifier
		if (isFieldCollection(field)) {
			if (field.getTypeReference() instanceof NamespaceClassifierReference) {
				val classifierReference = field.getTypeReference() as NamespaceClassifierReference;
				val typeArgument = classifierReference.getClassifierReferences().get(0).getTypeArguments().get(0);
				if (typeArgument instanceof QualifiedTypeArgument) {
					referencedClassifier = getReferencedClassifier(typeArgument.getTypeReference());
				}
			}
		}
		return referencedClassifier;
	}
	
	/**
	 * Returns true if the specified objects represents any association (can also e an aggregation/composition), 
	 * false otherwise.
	 */
	public def dispatch boolean isAnyAssociation(EObject object) {
		return false;
	}
	
	public def dispatch boolean isAnyAssociation(Field field) {
		return field.determineAssociationType() != null;
	}
	
	/**
	 * Returns true if the specified objects represents an association (means no aggregation, no composition), 
	 * false otherwise.
	 */
	public def dispatch boolean isAssociation(EObject object) {
		return false;
	}
	
	public def dispatch boolean isAssociation(Field field) {
		return Association.Type.Association.equals(field.determineAssociationType());
	}

	/**
	 * Returns true if the specified objects represents an aggregation, false otherwise.
	 */
	public def dispatch boolean isAggregation(EObject object) {
		return false;
	}
	
	public def dispatch boolean isAggregation(Field field) {
		return Association.Type.Aggregation.equals(field.determineAssociationType());
	}

	/**
	 * Returns true if the specified objects represents an compositions, false otherwise.
	 */
	public def dispatch boolean isComposition(EObject object) {
		return false;
	}
	
	public def dispatch boolean isComposition(Field field) {
		return Association.Type.Composition.equals(field.determineAssociationType());
	}
	
	private def Association.Type determineAssociationType(Field field) {
		// Determine if the field has an association annotation
		if (field.typeReference instanceof NamespaceClassifierReference && 
			field.annotationsAndModifiers.filter(AnnotationInstance).exists([annotationInstance | annotationInstance.annotation.name.equals(ASSOCIATION_TAG)])) {
				val annotationParameters = field.associationAnnotation.parameter as AnnotationParameterList;
			// Look if there are parameters, otherwise the default values are set
			if (annotationParameters != null) {
				// Get the type parameter
				val settings = annotationParameters.settings.filter([param | param.attribute.name.equals(MEMBER_TYPE)]);
				if (!settings.empty) {
					val annotationAttributeValue = settings.get(0).value;
					if (annotationAttributeValue instanceof IdentifierReference) {
						val reference = annotationAttributeValue.next;
						if (reference instanceof IdentifierReference) {
							// Get the enum type value to determine the association type 
							val enumConst = (reference as IdentifierReference).target as EnumConstant;
							if (enumConst.name.equals(MEMBER_TYPE_AGGREGATION)) {
								return Association.Type.Aggregation;
							} else if (enumConst.name.equals(MEMBER_TYPE_COMPOSITION)) {
								return Association.Type.Composition;
							}	
						}
					}
				}
			}
			// Return default value
			return Association.Type.Association;	
		}
		// This is just a field, no association
		return null;
	}
	
	private def AnnotationInstance getAssociationAnnotation(Field field) {
		val potentialAnnotations = field.annotationsAndModifiers.filter(AnnotationInstance).filter([a | a.annotation.name.equals(ASSOCIATION_TAG)]);
		if (!potentialAnnotations.nullOrEmpty) {
			return potentialAnnotations.get(0) as AnnotationInstance	
		}
		return null;
	}
	
	/**
	 * Returns the string representations of the multiplicity of the association
	 * source represented by the specified object or an empty string, if
	 * there is none.
	 */
	public def dispatch String getSourceMultiplicityRepresentation(EObject obj) {
		return ""
	}
			
	public def dispatch String getSourceMultiplicityRepresentation(Field field) {
		return getMultiplicityRepresentation(field.sourceLowerMultiplicity, field.sourceUpperMultiplicity);
	}
	
	/**
	 * Returns the string representations of the multiplicity of the association 
	 * target represented by the specified object or an empty string, if
	 * there is none.
	 */
	public def dispatch String getTargetMultiplicityRepresentation(EObject obj) {
		return ""
	}
	
	public def dispatch String getTargetMultiplicityRepresentation(Field field) {
		return getMultiplicityRepresentation(field.targetLowerMultiplicity, field.targetUpperMultiplicity);
	}
	
	private def int getIntegerValue(AnnotationInstance annotation, String memberName) {
		// Get the annotation member with the specified name
		val potentialAttributes = (annotation.annotation as Annotation).members.filter([m | m.name.equals(memberName)]);
		if (potentialAttributes.nullOrEmpty) {
			return 0;
		}
		// Get the annotation parameters (user defined values for annotation attributes)
		val annotationParameters = annotation.parameter as AnnotationParameterList;
		if (annotationParameters != null) {
			// Get the user defined value for specified member
			val settings = annotationParameters.settings.filter([param | param.attribute.name.equals(memberName)]);
			if (!settings.empty) {
				val annotationAttributeValue = settings.get(0).value
				if (annotationAttributeValue instanceof DecimalIntegerLiteral) {
					return (annotationAttributeValue as DecimalIntegerLiteral).value;	
				}
			}
		}
		// If no user defined value was found, return the default value
		return memberName.defaultMultiplicity
	}
	
	private def int getDefaultMultiplicity(String memberName) {
		switch memberName {
			case MEMBER_SOURCE_LOWER_MULTIPLITY,
			case MEMBER_TARGET_LOWER_MULTIPLITY: 
				return 0
			case MEMBER_SOURCE_UPPER_MULTIPLITY,
			case MEMBER_TARGET_UPPER_MULTIPLITY:
				return -1
		}
		return 0;
	}
	
	private def int getSourceLowerMultiplicity(Field field) {
		return field.associationAnnotation.getIntegerValue(MEMBER_SOURCE_LOWER_MULTIPLITY);
	}
	
	private def int getSourceUpperMultiplicity(Field field) {
		return field.associationAnnotation.getIntegerValue(MEMBER_SOURCE_UPPER_MULTIPLITY);
	}
	
	private def int getTargetLowerMultiplicity(Field field) {
		return field.associationAnnotation.getIntegerValue(MEMBER_TARGET_LOWER_MULTIPLITY);
	}
	
	private def int getTargetUpperMultiplicity(Field field) {
		return field.associationAnnotation.getIntegerValue(MEMBER_TARGET_UPPER_MULTIPLITY);
	}
	

	private def dispatch int getValue(Expression expr) {
		throw new UnsupportedOperationException();
	}
	
	private def dispatch int getValue(DecimalIntegerLiteral integerLiteral) {
		return integerLiteral.decimalValue.intValue
	}
	
	private def dispatch int getValue(UnaryExpression unaryExpression) {
		if (unaryExpression.operators.get(0) instanceof Subtraction) {
			 return -unaryExpression.child.value;
		} else {
			throw new UnsupportedOperationException("UnaryExpression of type: " + unaryExpression.operators.get(0));
		}
	}
	
	private def String getMultiplicityRepresentation(int lowerValue, int upperValue) {
		if (lowerValue == upperValue) {
			return lowerValue.toString;
		} else if (lowerValue == 0 && upperValue == -1) {
			return "*";
		} else {
			return lowerValue.multiplicityValueRepresentation + ".." + upperValue.multiplicityValueRepresentation;
		}
	}
	
	private def String getMultiplicityValueRepresentation(int value) {
		if (value == -1) {
			return "*";
		} else {
			return value.toString;
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
}