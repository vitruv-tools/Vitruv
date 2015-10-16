/**
 * Copyright (c) 2015 Heiko Klare (Karlsruhe Institute of Technology)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Heiko Klare - initial API and implementation and/or initial documentation
 */
package edu.kit.ipd.sdq.vitruvius.javauml.editor.viewpoint.javaServices;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import edu.kit.ipd.sdq.vitruvius.javauml.annotations.Association;
import java.math.BigInteger;
import java.util.Arrays;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.emftext.language.java.annotations.AnnotationAttributeSetting;
import org.emftext.language.java.annotations.AnnotationInstance;
import org.emftext.language.java.annotations.AnnotationParameter;
import org.emftext.language.java.annotations.AnnotationParameterList;
import org.emftext.language.java.annotations.AnnotationValue;
import org.emftext.language.java.classifiers.Annotation;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.expressions.Expression;
import org.emftext.language.java.expressions.UnaryExpression;
import org.emftext.language.java.expressions.UnaryExpressionChild;
import org.emftext.language.java.literals.DecimalIntegerLiteral;
import org.emftext.language.java.members.EnumConstant;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.modifiers.AnnotationInstanceOrModifier;
import org.emftext.language.java.operators.Subtraction;
import org.emftext.language.java.operators.UnaryOperator;
import org.emftext.language.java.references.IdentifierReference;
import org.emftext.language.java.references.Reference;
import org.emftext.language.java.references.ReferenceableElement;
import org.emftext.language.java.types.NamespaceClassifierReference;
import org.emftext.language.java.types.TypeReference;

/**
 * This class specifies utility methods for determining if a a field represents an association
 * (specified by an annotation) and which kind of association it represents. Furthermore it provides
 * procedures for getting string representations of multiplicities.
 * 
 * @author Heiko Klare
 */
@SuppressWarnings("all")
public final class AssociationUtils {
  private final static String MEMBER_SOURCE_LOWER_MULTIPLITY = "sourceLowerMultiplicity";
  
  private final static String MEMBER_SOURCE_UPPER_MULTIPLITY = "sourceUpperMultiplicity";
  
  private final static String MEMBER_TARGET_LOWER_MULTIPLITY = "targetLowerMultiplicity";
  
  private final static String MEMBER_TARGET_UPPER_MULTIPLITY = "targetUpperMultiplicity";
  
  private final static String MEMBER_TYPE = "type";
  
  private final static String MEMBER_TYPE_AGGREGATION = "Aggregation";
  
  private final static String MEMBER_TYPE_COMPOSITION = "Composition";
  
  private final static String ASSOCIATION_TAG = "Association";
  
  /**
   * Returns true if the specified objects represents any association (can also e an aggregation/composition),
   * false otherwise.
   */
  public boolean _isAnyAssociation(final EObject object) {
    return false;
  }
  
  public boolean _isAnyAssociation(final Field field) {
    Association.Type _determineAssociationType = this.determineAssociationType(field);
    return (!Objects.equal(_determineAssociationType, null));
  }
  
  /**
   * Returns true if the specified objects represents an association (means no aggregation, no composition),
   * false otherwise.
   */
  public boolean _isAssociation(final EObject object) {
    return false;
  }
  
  public boolean _isAssociation(final Field field) {
    Association.Type _determineAssociationType = this.determineAssociationType(field);
    return Association.Type.Association.equals(_determineAssociationType);
  }
  
  /**
   * Returns true if the specified objects represents an aggregation, false otherwise.
   */
  public boolean _isAggregation(final EObject object) {
    return false;
  }
  
  public boolean _isAggregation(final Field field) {
    Association.Type _determineAssociationType = this.determineAssociationType(field);
    return Association.Type.Aggregation.equals(_determineAssociationType);
  }
  
  /**
   * Returns true if the specified objects represents an compositions, false otherwise.
   */
  public boolean _isComposition(final EObject object) {
    return false;
  }
  
  public boolean _isComposition(final Field field) {
    Association.Type _determineAssociationType = this.determineAssociationType(field);
    return Association.Type.Composition.equals(_determineAssociationType);
  }
  
  private Association.Type determineAssociationType(final Field field) {
    boolean _and = false;
    TypeReference _typeReference = field.getTypeReference();
    if (!(_typeReference instanceof NamespaceClassifierReference)) {
      _and = false;
    } else {
      EList<AnnotationInstanceOrModifier> _annotationsAndModifiers = field.getAnnotationsAndModifiers();
      Iterable<AnnotationInstance> _filter = Iterables.<AnnotationInstance>filter(_annotationsAndModifiers, AnnotationInstance.class);
      final Function1<AnnotationInstance, Boolean> _function = (AnnotationInstance annotationInstance) -> {
        Classifier _annotation = annotationInstance.getAnnotation();
        String _name = _annotation.getName();
        return Boolean.valueOf(_name.equals(AssociationUtils.ASSOCIATION_TAG));
      };
      boolean _exists = IterableExtensions.<AnnotationInstance>exists(_filter, _function);
      _and = _exists;
    }
    if (_and) {
      AnnotationInstance _associationAnnotation = this.getAssociationAnnotation(field);
      AnnotationParameter _parameter = _associationAnnotation.getParameter();
      final AnnotationParameterList annotationParameters = ((AnnotationParameterList) _parameter);
      boolean _notEquals = (!Objects.equal(annotationParameters, null));
      if (_notEquals) {
        EList<AnnotationAttributeSetting> _settings = annotationParameters.getSettings();
        final Function1<AnnotationAttributeSetting, Boolean> _function_1 = (AnnotationAttributeSetting param) -> {
          InterfaceMethod _attribute = param.getAttribute();
          String _name = _attribute.getName();
          return Boolean.valueOf(_name.equals(AssociationUtils.MEMBER_TYPE));
        };
        final Iterable<AnnotationAttributeSetting> settings = IterableExtensions.<AnnotationAttributeSetting>filter(_settings, _function_1);
        boolean _isEmpty = IterableExtensions.isEmpty(settings);
        boolean _not = (!_isEmpty);
        if (_not) {
          AnnotationAttributeSetting _get = ((AnnotationAttributeSetting[])Conversions.unwrapArray(settings, AnnotationAttributeSetting.class))[0];
          final AnnotationValue annotationAttributeValue = _get.getValue();
          if ((annotationAttributeValue instanceof IdentifierReference)) {
            final Reference reference = ((IdentifierReference)annotationAttributeValue).getNext();
            if ((reference instanceof IdentifierReference)) {
              ReferenceableElement _target = ((IdentifierReference) reference).getTarget();
              final EnumConstant enumConst = ((EnumConstant) _target);
              String _name = enumConst.getName();
              boolean _equals = _name.equals(AssociationUtils.MEMBER_TYPE_AGGREGATION);
              if (_equals) {
                return Association.Type.Aggregation;
              } else {
                String _name_1 = enumConst.getName();
                boolean _equals_1 = _name_1.equals(AssociationUtils.MEMBER_TYPE_COMPOSITION);
                if (_equals_1) {
                  return Association.Type.Composition;
                }
              }
            }
          }
        }
      }
      return Association.Type.Association;
    }
    return null;
  }
  
  private AnnotationInstance getAssociationAnnotation(final Field field) {
    EList<AnnotationInstanceOrModifier> _annotationsAndModifiers = field.getAnnotationsAndModifiers();
    Iterable<AnnotationInstance> _filter = Iterables.<AnnotationInstance>filter(_annotationsAndModifiers, AnnotationInstance.class);
    final Function1<AnnotationInstance, Boolean> _function = (AnnotationInstance a) -> {
      Classifier _annotation = a.getAnnotation();
      String _name = _annotation.getName();
      return Boolean.valueOf(_name.equals(AssociationUtils.ASSOCIATION_TAG));
    };
    final Iterable<AnnotationInstance> potentialAnnotations = IterableExtensions.<AnnotationInstance>filter(_filter, _function);
    boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(potentialAnnotations);
    boolean _not = (!_isNullOrEmpty);
    if (_not) {
      AnnotationInstance _get = ((AnnotationInstance[])Conversions.unwrapArray(potentialAnnotations, AnnotationInstance.class))[0];
      return ((AnnotationInstance) _get);
    }
    return null;
  }
  
  /**
   * Returns the string representations of the multiplicity of the association
   * source represented by the specified object or an empty string, if
   * there is none.
   */
  public String _getSourceMultiplicityRepresentation(final EObject obj) {
    return "";
  }
  
  public String _getSourceMultiplicityRepresentation(final Field field) {
    int _sourceLowerMultiplicity = this.getSourceLowerMultiplicity(field);
    int _sourceUpperMultiplicity = this.getSourceUpperMultiplicity(field);
    return this.getMultiplicityRepresentation(_sourceLowerMultiplicity, _sourceUpperMultiplicity);
  }
  
  /**
   * Returns the string representations of the multiplicity of the association
   * target represented by the specified object or an empty string, if
   * there is none.
   */
  public String _getTargetMultiplicityRepresentation(final EObject obj) {
    return "";
  }
  
  public String _getTargetMultiplicityRepresentation(final Field field) {
    int _targetLowerMultiplicity = this.getTargetLowerMultiplicity(field);
    int _targetUpperMultiplicity = this.getTargetUpperMultiplicity(field);
    return this.getMultiplicityRepresentation(_targetLowerMultiplicity, _targetUpperMultiplicity);
  }
  
  private int getIntegerValue(final AnnotationInstance annotation, final String memberName) {
    Classifier _annotation = annotation.getAnnotation();
    EList<Member> _members = ((Annotation) _annotation).getMembers();
    final Function1<Member, Boolean> _function = (Member m) -> {
      String _name = m.getName();
      return Boolean.valueOf(_name.equals(memberName));
    };
    final Iterable<Member> potentialAttributes = IterableExtensions.<Member>filter(_members, _function);
    boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(potentialAttributes);
    if (_isNullOrEmpty) {
      return 0;
    }
    AnnotationParameter _parameter = annotation.getParameter();
    final AnnotationParameterList annotationParameters = ((AnnotationParameterList) _parameter);
    boolean _notEquals = (!Objects.equal(annotationParameters, null));
    if (_notEquals) {
      EList<AnnotationAttributeSetting> _settings = annotationParameters.getSettings();
      final Function1<AnnotationAttributeSetting, Boolean> _function_1 = (AnnotationAttributeSetting param) -> {
        InterfaceMethod _attribute = param.getAttribute();
        String _name = _attribute.getName();
        return Boolean.valueOf(_name.equals(memberName));
      };
      final Iterable<AnnotationAttributeSetting> settings = IterableExtensions.<AnnotationAttributeSetting>filter(_settings, _function_1);
      boolean _isEmpty = IterableExtensions.isEmpty(settings);
      boolean _not = (!_isEmpty);
      if (_not) {
        AnnotationAttributeSetting _get = ((AnnotationAttributeSetting[])Conversions.unwrapArray(settings, AnnotationAttributeSetting.class))[0];
        final AnnotationValue annotationAttributeValue = _get.getValue();
        if ((annotationAttributeValue instanceof DecimalIntegerLiteral)) {
          return this.getValue(((DecimalIntegerLiteral) annotationAttributeValue));
        }
      }
    }
    return this.getDefaultMultiplicity(memberName);
  }
  
  private int getDefaultMultiplicity(final String memberName) {
    switch (memberName) {
      case AssociationUtils.MEMBER_SOURCE_LOWER_MULTIPLITY:
      case AssociationUtils.MEMBER_TARGET_LOWER_MULTIPLITY:
        return 0;
      case AssociationUtils.MEMBER_SOURCE_UPPER_MULTIPLITY:
      case AssociationUtils.MEMBER_TARGET_UPPER_MULTIPLITY:
        return (-1);
    }
    return 0;
  }
  
  private int getSourceLowerMultiplicity(final Field field) {
    AnnotationInstance _associationAnnotation = this.getAssociationAnnotation(field);
    return this.getIntegerValue(_associationAnnotation, AssociationUtils.MEMBER_SOURCE_LOWER_MULTIPLITY);
  }
  
  private int getSourceUpperMultiplicity(final Field field) {
    AnnotationInstance _associationAnnotation = this.getAssociationAnnotation(field);
    return this.getIntegerValue(_associationAnnotation, AssociationUtils.MEMBER_SOURCE_UPPER_MULTIPLITY);
  }
  
  private int getTargetLowerMultiplicity(final Field field) {
    AnnotationInstance _associationAnnotation = this.getAssociationAnnotation(field);
    return this.getIntegerValue(_associationAnnotation, AssociationUtils.MEMBER_TARGET_LOWER_MULTIPLITY);
  }
  
  private int getTargetUpperMultiplicity(final Field field) {
    AnnotationInstance _associationAnnotation = this.getAssociationAnnotation(field);
    return this.getIntegerValue(_associationAnnotation, AssociationUtils.MEMBER_TARGET_UPPER_MULTIPLITY);
  }
  
  private int _getValue(final Expression expr) {
    throw new UnsupportedOperationException();
  }
  
  private int _getValue(final DecimalIntegerLiteral integerLiteral) {
    BigInteger _decimalValue = integerLiteral.getDecimalValue();
    return _decimalValue.intValue();
  }
  
  private int _getValue(final UnaryExpression unaryExpression) {
    EList<UnaryOperator> _operators = unaryExpression.getOperators();
    UnaryOperator _get = _operators.get(0);
    if ((_get instanceof Subtraction)) {
      UnaryExpressionChild _child = unaryExpression.getChild();
      int _value = this.getValue(_child);
      return (-_value);
    } else {
      EList<UnaryOperator> _operators_1 = unaryExpression.getOperators();
      UnaryOperator _get_1 = _operators_1.get(0);
      String _plus = ("UnaryExpression of type: " + _get_1);
      throw new UnsupportedOperationException(_plus);
    }
  }
  
  private String getMultiplicityRepresentation(final int lowerValue, final int upperValue) {
    if ((lowerValue == upperValue)) {
      return Integer.valueOf(lowerValue).toString();
    } else {
      if (((lowerValue == 0) && (upperValue == (-1)))) {
        return "*";
      } else {
        String _multiplicityValueRepresentation = this.getMultiplicityValueRepresentation(lowerValue);
        String _plus = (_multiplicityValueRepresentation + "..");
        String _multiplicityValueRepresentation_1 = this.getMultiplicityValueRepresentation(upperValue);
        return (_plus + _multiplicityValueRepresentation_1);
      }
    }
  }
  
  private String getMultiplicityValueRepresentation(final int value) {
    if ((value == (-1))) {
      return "*";
    } else {
      return Integer.valueOf(value).toString();
    }
  }
  
  public boolean isAnyAssociation(final EObject field) {
    if (field instanceof Field) {
      return _isAnyAssociation((Field)field);
    } else if (field != null) {
      return _isAnyAssociation(field);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(field).toString());
    }
  }
  
  public boolean isAssociation(final EObject field) {
    if (field instanceof Field) {
      return _isAssociation((Field)field);
    } else if (field != null) {
      return _isAssociation(field);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(field).toString());
    }
  }
  
  public boolean isAggregation(final EObject field) {
    if (field instanceof Field) {
      return _isAggregation((Field)field);
    } else if (field != null) {
      return _isAggregation(field);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(field).toString());
    }
  }
  
  public boolean isComposition(final EObject field) {
    if (field instanceof Field) {
      return _isComposition((Field)field);
    } else if (field != null) {
      return _isComposition(field);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(field).toString());
    }
  }
  
  public String getSourceMultiplicityRepresentation(final EObject field) {
    if (field instanceof Field) {
      return _getSourceMultiplicityRepresentation((Field)field);
    } else if (field != null) {
      return _getSourceMultiplicityRepresentation(field);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(field).toString());
    }
  }
  
  public String getTargetMultiplicityRepresentation(final EObject field) {
    if (field instanceof Field) {
      return _getTargetMultiplicityRepresentation((Field)field);
    } else if (field != null) {
      return _getTargetMultiplicityRepresentation(field);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(field).toString());
    }
  }
  
  private int getValue(final Expression integerLiteral) {
    if (integerLiteral instanceof DecimalIntegerLiteral) {
      return _getValue((DecimalIntegerLiteral)integerLiteral);
    } else if (integerLiteral instanceof UnaryExpression) {
      return _getValue((UnaryExpression)integerLiteral);
    } else if (integerLiteral != null) {
      return _getValue(integerLiteral);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(integerLiteral).toString());
    }
  }
}
