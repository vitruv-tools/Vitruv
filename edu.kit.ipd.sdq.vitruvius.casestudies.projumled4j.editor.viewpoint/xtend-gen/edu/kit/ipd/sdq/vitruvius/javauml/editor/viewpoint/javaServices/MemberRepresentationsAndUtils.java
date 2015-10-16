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

import com.google.common.collect.Iterables;
import edu.kit.ipd.sdq.vitruvius.javauml.editor.viewpoint.javaServices.DataTypeRepresentations;
import java.util.Arrays;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.members.Constructor;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.modifiers.AnnotableAndModifiable;
import org.emftext.language.java.parameters.Parameter;
import org.emftext.language.java.parameters.Parametrizable;
import org.emftext.language.java.types.TypeReference;

/**
 * This class provides procedures for determining the string representations of class members
 * as well as currently unused names for methods or fields inside a class or interface.
 * 
 * @author Heiko Klare
 */
@SuppressWarnings("all")
public final class MemberRepresentationsAndUtils {
  private final static String NEW_METHOD_PREFIX = "newMethod";
  
  private final static String NEW_FIELD_PREFIX = "newField";
  
  /**
   * Returns the string representation of the visibility modifier for the given object or an
   * empty string if there is none.
   */
  public String _getModifierStringRepresentation(final EObject object) {
    return "";
  }
  
  public String _getModifierStringRepresentation(final AnnotableAndModifiable member) {
    boolean _isPrivate = member.isPrivate();
    if (_isPrivate) {
      return "-";
    } else {
      boolean _isProtected = member.isProtected();
      if (_isProtected) {
        return "#";
      } else {
        boolean _isPublic = member.isPublic();
        if (_isPublic) {
          return "+";
        } else {
          return " ";
        }
      }
    }
  }
  
  /**
   * Returns the UML style representation of a type which can be a double point
   * followed by the type or an empty string if the type is void.
   */
  private String _getUmlTypeString(final org.emftext.language.java.types.Void voidType) {
    return "";
  }
  
  private String _getUmlTypeString(final TypeReference typeReference) {
    DataTypeRepresentations _dataTypeRepresentations = new DataTypeRepresentations();
    String _typeToString = _dataTypeRepresentations.typeToString(typeReference);
    return (" : " + _typeToString);
  }
  
  /**
   * Returns the string representation of the given class member. In case of a field this is
   * the field name followed by the type, in case of a method it is the UML representation of
   * a method: the method name, followed by the parameter list and the return type.
   */
  public String _getRepresentation(final EObject member) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("\t");
    return _builder.toString();
  }
  
  private String getParameterRepresentation(final Parametrizable object) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Parameter> _parameters = object.getParameters();
      boolean _hasElements = false;
      for(final Parameter parameter : _parameters) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(",", "");
        }
        String _name = parameter.getName();
        _builder.append(_name, "");
        TypeReference _typeReference = parameter.getTypeReference();
        String _umlTypeString = this.getUmlTypeString(_typeReference);
        _builder.append(_umlTypeString, "");
      }
    }
    return _builder.toString();
  }
  
  public String _getRepresentation(final Method method) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = method.getName();
    _builder.append(_name, "");
    _builder.append("(");
    String _parameterRepresentation = this.getParameterRepresentation(method);
    _builder.append(_parameterRepresentation, "");
    _builder.append(")");
    TypeReference _typeReference = method.getTypeReference();
    String _umlTypeString = this.getUmlTypeString(_typeReference);
    _builder.append(_umlTypeString, "");
    return _builder.toString();
  }
  
  public String _getRepresentation(final Constructor method) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<<create>> ");
    String _name = method.getName();
    _builder.append(_name, "");
    _builder.append("(");
    String _parameterRepresentation = this.getParameterRepresentation(method);
    _builder.append(_parameterRepresentation, "");
    _builder.append(")");
    return _builder.toString();
  }
  
  public String _getRepresentation(final Field field) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = field.getName();
    _builder.append(_name, "");
    TypeReference _typeReference = field.getTypeReference();
    String _umlTypeString = this.getUmlTypeString(_typeReference);
    _builder.append(_umlTypeString, "");
    return _builder.toString();
  }
  
  private String getNewUnusedMemberName(final Iterable<? extends Member> existingMembers, final String memberPrefix) {
    boolean nameIsUnused = false;
    int counter = 0;
    while ((!nameIsUnused)) {
      {
        counter++;
        nameIsUnused = true;
        final String currentMethodName = (memberPrefix + Integer.valueOf(counter));
        for (final Member member : existingMembers) {
          String _name = member.getName();
          boolean _equals = currentMethodName.equals(_name);
          if (_equals) {
            nameIsUnused = false;
          }
        }
      }
    }
    return (memberPrefix + Integer.valueOf(counter));
  }
  
  /**
   * Returns a new unused method name for the given class.
   */
  public String getNewUnusedMethodName(final org.emftext.language.java.classifiers.Class classObject) {
    EList<Member> _members = classObject.getMembers();
    Iterable<Method> _filter = Iterables.<Method>filter(_members, Method.class);
    return this.getNewUnusedMemberName(_filter, MemberRepresentationsAndUtils.NEW_METHOD_PREFIX);
  }
  
  /**
   * Returns a new unused method name for the given interface.
   */
  public String getNewUnusedMethodName(final Interface interfaceObject) {
    EList<Member> _members = interfaceObject.getMembers();
    Iterable<Method> _filter = Iterables.<Method>filter(_members, Method.class);
    return this.getNewUnusedMemberName(_filter, MemberRepresentationsAndUtils.NEW_METHOD_PREFIX);
  }
  
  /**
   * Returns a new unused field name for the given class.
   */
  public String getNewUnusedFieldName(final org.emftext.language.java.classifiers.Class classObject) {
    EList<Member> _members = classObject.getMembers();
    Iterable<Field> _filter = Iterables.<Field>filter(_members, Field.class);
    return this.getNewUnusedMemberName(_filter, MemberRepresentationsAndUtils.NEW_FIELD_PREFIX);
  }
  
  public String getModifierStringRepresentation(final EObject member) {
    if (member instanceof AnnotableAndModifiable) {
      return _getModifierStringRepresentation((AnnotableAndModifiable)member);
    } else if (member != null) {
      return _getModifierStringRepresentation(member);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(member).toString());
    }
  }
  
  private String getUmlTypeString(final TypeReference voidType) {
    if (voidType instanceof org.emftext.language.java.types.Void) {
      return _getUmlTypeString((org.emftext.language.java.types.Void)voidType);
    } else if (voidType != null) {
      return _getUmlTypeString(voidType);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(voidType).toString());
    }
  }
  
  public String getRepresentation(final EObject field) {
    if (field instanceof Field) {
      return _getRepresentation((Field)field);
    } else if (field instanceof Constructor) {
      return _getRepresentation((Constructor)field);
    } else if (field instanceof Method) {
      return _getRepresentation((Method)field);
    } else if (field != null) {
      return _getRepresentation(field);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(field).toString());
    }
  }
}
