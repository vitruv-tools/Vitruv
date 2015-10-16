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

import java.util.Arrays;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.types.Char;
import org.emftext.language.java.types.ClassifierReference;
import org.emftext.language.java.types.Int;
import org.emftext.language.java.types.NamespaceClassifierReference;

/**
 * This class provides procedures for getting string representations of data types.
 * 
 * @author Heiko Klare
 */
@SuppressWarnings("all")
public final class DataTypeRepresentations {
  private final Logger logger = LogManager.getLogger(DataTypeRepresentations.class);
  
  /**
   * Returns the string representation of the specified object or "void" if elements
   * of the given type are not supported
   */
  public String _typeToString(final EObject object) {
    Class<? extends EObject> _class = object.getClass();
    String _plus = ("Tried to convert an unsupported type to string: " + _class);
    this.logger.error(_plus);
    return "void";
  }
  
  public String _typeToString(final Int integer) {
    return "int";
  }
  
  public String _typeToString(final org.emftext.language.java.types.Float fl) {
    return "float";
  }
  
  public String _typeToString(final org.emftext.language.java.types.Double dou) {
    return "double";
  }
  
  public String _typeToString(final org.emftext.language.java.types.Long lo) {
    return "long";
  }
  
  public String _typeToString(final org.emftext.language.java.types.Boolean boo) {
    return "boolean";
  }
  
  public String _typeToString(final org.emftext.language.java.types.Void v) {
    return "void";
  }
  
  public String _typeToString(final Char c) {
    return "char";
  }
  
  public String _typeToString(final org.emftext.language.java.types.Byte b) {
    return "byte";
  }
  
  public String _typeToString(final org.emftext.language.java.types.Short s) {
    return "short";
  }
  
  public String _typeToString(final ClassifierReference classifierReference) {
    Classifier _target = classifierReference.getTarget();
    return _target.getName();
  }
  
  public String _typeToString(final NamespaceClassifierReference typeReference) {
    final EList<ClassifierReference> classifierReferences = typeReference.getClassifierReferences();
    boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(classifierReferences);
    if (_isNullOrEmpty) {
      this.logger.error("No classifier reference was specified for a type reference.");
      return "void";
    } else {
      ClassifierReference _get = classifierReferences.get(0);
      return this.typeToString(_get);
    }
  }
  
  public String typeToString(final EObject boo) {
    if (boo instanceof org.emftext.language.java.types.Boolean) {
      return _typeToString((org.emftext.language.java.types.Boolean)boo);
    } else if (boo instanceof org.emftext.language.java.types.Byte) {
      return _typeToString((org.emftext.language.java.types.Byte)boo);
    } else if (boo instanceof Char) {
      return _typeToString((Char)boo);
    } else if (boo instanceof org.emftext.language.java.types.Double) {
      return _typeToString((org.emftext.language.java.types.Double)boo);
    } else if (boo instanceof org.emftext.language.java.types.Float) {
      return _typeToString((org.emftext.language.java.types.Float)boo);
    } else if (boo instanceof Int) {
      return _typeToString((Int)boo);
    } else if (boo instanceof org.emftext.language.java.types.Long) {
      return _typeToString((org.emftext.language.java.types.Long)boo);
    } else if (boo instanceof org.emftext.language.java.types.Short) {
      return _typeToString((org.emftext.language.java.types.Short)boo);
    } else if (boo instanceof org.emftext.language.java.types.Void) {
      return _typeToString((org.emftext.language.java.types.Void)boo);
    } else if (boo instanceof ClassifierReference) {
      return _typeToString((ClassifierReference)boo);
    } else if (boo instanceof NamespaceClassifierReference) {
      return _typeToString((NamespaceClassifierReference)boo);
    } else if (boo != null) {
      return _typeToString(boo);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(boo).toString());
    }
  }
}
