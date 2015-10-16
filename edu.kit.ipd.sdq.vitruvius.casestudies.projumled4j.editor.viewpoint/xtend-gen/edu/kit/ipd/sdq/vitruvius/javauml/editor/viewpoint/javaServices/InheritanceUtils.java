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

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.types.ClassifierReference;
import org.emftext.language.java.types.NamespaceClassifierReference;
import org.emftext.language.java.types.TypeReference;

/**
 * This class provides procedure for calculating inheritance relationships between classes and interfaces.
 * 
 * @author Heiko Klare
 */
@SuppressWarnings("all")
public final class InheritanceUtils {
  /**
   * Returns the super class of the given {@link Class} if existing, null otherwise.
   */
  public org.emftext.language.java.classifiers.Class getSuperClass(final org.emftext.language.java.classifiers.Class ofClass) {
    final TypeReference superClass = ofClass.getExtends();
    if ((superClass instanceof NamespaceClassifierReference)) {
      final EList<ClassifierReference> classifierReferences = ((NamespaceClassifierReference) superClass).getClassifierReferences();
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(classifierReferences);
      boolean _not = (!_isNullOrEmpty);
      if (_not) {
        ClassifierReference _get = classifierReferences.get(0);
        final Classifier superClassTarget = _get.getTarget();
        if ((superClassTarget instanceof org.emftext.language.java.classifiers.Class)) {
          return ((org.emftext.language.java.classifiers.Class) superClassTarget);
        }
      }
    }
    return null;
  }
  
  /**
   * Returns the {@link Interface}s referenced by the given list of {@link TypeReferences}s.
   */
  private EList<Interface> getInterfaces(final EList<TypeReference> typeReferences) {
    final BasicEList<Interface> result = new BasicEList<Interface>();
    for (final TypeReference potentialInterfaceReference : typeReferences) {
      if ((potentialInterfaceReference instanceof NamespaceClassifierReference)) {
        final EList<ClassifierReference> classifierReferences = ((NamespaceClassifierReference) potentialInterfaceReference).getClassifierReferences();
        boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(classifierReferences);
        boolean _not = (!_isNullOrEmpty);
        if (_not) {
          ClassifierReference _get = classifierReferences.get(0);
          final Classifier interfaceTarget = _get.getTarget();
          if ((interfaceTarget instanceof Interface)) {
            result.add(((Interface) interfaceTarget));
          }
        }
      }
    }
    return result;
  }
  
  /**
   * Returns the {@link Interface}s implemented by the given {@link Class}.
   */
  public EList<Interface> getImplementedInterfaces(final org.emftext.language.java.classifiers.Class ofClass) {
    final EList<TypeReference> interfaceReferences = ofClass.getImplements();
    return this.getInterfaces(interfaceReferences);
  }
  
  /**
   * Returns the interfaces the given one is extending.
   */
  public EList<Interface> getImplementedInterfaces(final Interface ofInterface) {
    final EList<TypeReference> interfaceReferences = ofInterface.getExtends();
    return this.getInterfaces(interfaceReferences);
  }
}
