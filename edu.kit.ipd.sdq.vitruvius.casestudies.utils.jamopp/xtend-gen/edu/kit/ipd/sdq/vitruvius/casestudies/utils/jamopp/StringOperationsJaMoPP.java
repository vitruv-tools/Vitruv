package edu.kit.ipd.sdq.vitruvius.casestudies.utils.jamopp;

import java.util.Arrays;
import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.commons.Commentable;
import org.emftext.language.java.imports.ClassifierImport;
import org.emftext.language.java.imports.PackageImport;
import org.emftext.language.java.imports.StaticClassifierImport;
import org.emftext.language.java.imports.StaticMemberImport;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.parameters.Parameter;
import org.emftext.language.java.parameters.VariableLengthParameter;
import org.emftext.language.java.references.ReferenceableElement;
import org.emftext.language.java.types.ClassifierReference;
import org.emftext.language.java.types.NamespaceClassifierReference;
import org.emftext.language.java.types.PrimitiveType;
import org.emftext.language.java.types.Type;
import org.emftext.language.java.types.TypeReference;

/**
 * Converts a JaMoPP model element to a string representation.
 * This representation is not necessarily the concrete syntax
 * but might be anything appropriate to fulfil the need in the
 * place it is used.
 * Use the {@link JaMoPPConcreteSyntax} class for strict
 * concrete syntax conversions.
 */
@SuppressWarnings("all")
public class StringOperationsJaMoPP {
  protected static String _getStringRepresentation(final ClassifierImport imprt) {
    EList<String> _namespaces = imprt.getNamespaces();
    String _namespaceAsString = StringOperationsJaMoPP.getNamespaceAsString(_namespaces);
    String _plus = (_namespaceAsString + ".");
    ConcreteClassifier _classifier = imprt.getClassifier();
    String _name = _classifier.getName();
    return (_plus + _name);
  }
  
  protected static String _getStringRepresentation(final StaticClassifierImport imprt) {
    EList<String> _namespaces = imprt.getNamespaces();
    String _namespaceAsString = StringOperationsJaMoPP.getNamespaceAsString(_namespaces);
    return (_namespaceAsString + ".*");
  }
  
  protected static String _getStringRepresentation(final PackageImport imprt) {
    EList<String> _namespaces = imprt.getNamespaces();
    String _namespaceAsString = StringOperationsJaMoPP.getNamespaceAsString(_namespaces);
    return (_namespaceAsString + ".*");
  }
  
  protected static String _getStringRepresentation(final StaticMemberImport imprt) {
    EList<String> _namespaces = imprt.getNamespaces();
    String _namespaceAsString = StringOperationsJaMoPP.getNamespaceAsString(_namespaces);
    String _plus = (_namespaceAsString + ".");
    EList<ReferenceableElement> _staticMembers = imprt.getStaticMembers();
    String _nameList = StringOperationsJaMoPP.getNameList(_staticMembers);
    return (_plus + _nameList);
  }
  
  private static String getNameList(final Iterable<ReferenceableElement> elements) {
    final StringBuilder strBuilder = new StringBuilder();
    final Procedure1<ReferenceableElement> _function = new Procedure1<ReferenceableElement>() {
      public void apply(final ReferenceableElement it) {
        String _name = it.getName();
        String _plus = (_name + ",");
        strBuilder.append(_plus);
      }
    };
    IterableExtensions.<ReferenceableElement>forEach(elements, _function);
    int _length = strBuilder.length();
    int _minus = (_length - 1);
    strBuilder.deleteCharAt(_minus);
    return strBuilder.toString();
  }
  
  protected static String _getStringRepresentation(final Method javaMethod) {
    final StringBuilder javaIdentifier = new StringBuilder();
    TypeReference _typeReference = javaMethod.getTypeReference();
    long _arrayDimension = javaMethod.getArrayDimension();
    String _stringRepresentation = StringOperationsJaMoPP.getStringRepresentation(_typeReference, _arrayDimension);
    javaIdentifier.append(_stringRepresentation);
    String _name = javaMethod.getName();
    javaIdentifier.append(_name);
    EList<Parameter> _parameters = javaMethod.getParameters();
    final Procedure1<Parameter> _function = new Procedure1<Parameter>() {
      public void apply(final Parameter it) {
        TypeReference _typeReference = it.getTypeReference();
        long _arrayDimension = it.getArrayDimension();
        String _stringRepresentation = StringOperationsJaMoPP.getStringRepresentation(_typeReference, _arrayDimension);
        javaIdentifier.append(_stringRepresentation);
      }
    };
    IterableExtensions.<Parameter>forEach(_parameters, _function);
    return javaIdentifier.toString();
  }
  
  protected static String _getStringRepresentation(final PrimitiveType pt, final long arrayDimensions) {
    Class<? extends PrimitiveType> _class = pt.getClass();
    Class<?>[] _interfaces = _class.getInterfaces();
    Class<?> _get = _interfaces[0];
    String _simpleName = _get.getSimpleName();
    String _lowerCase = _simpleName.toLowerCase();
    String _repeat = StringUtils.repeat("[]", ((int) arrayDimensions));
    return (_lowerCase + _repeat);
  }
  
  protected static String _getStringRepresentation(final NamespaceClassifierReference ncr, final long arrayDimensions) {
    final StringBuilder strBuilder = new StringBuilder();
    EList<ClassifierReference> _classifierReferences = ncr.getClassifierReferences();
    final Procedure1<ClassifierReference> _function = new Procedure1<ClassifierReference>() {
      public void apply(final ClassifierReference it) {
        Classifier _target = it.getTarget();
        String _name = _target.getName();
        StringBuilder _append = strBuilder.append(_name);
        String _repeat = StringUtils.repeat("[]", ((int) arrayDimensions));
        /* (_append + _repeat); */
      }
    };
    IterableExtensions.<ClassifierReference>forEach(_classifierReferences, _function);
    return strBuilder.toString();
  }
  
  protected static String _getStringRepresentation(final Classifier pt, final long arrayDimensions) {
    String _name = pt.getName();
    String _repeat = StringUtils.repeat("[]", ((int) arrayDimensions));
    return (_name + _repeat);
  }
  
  protected static String _getStringRepresentation(final OrdinaryParameter param) {
    TypeReference _typeReference = param.getTypeReference();
    Type _target = _typeReference.getTarget();
    long _arrayDimension = param.getArrayDimension();
    return StringOperationsJaMoPP.getStringRepresentation(_target, _arrayDimension);
  }
  
  protected static String _getStringRepresentation(final VariableLengthParameter param) {
    TypeReference _typeReference = param.getTypeReference();
    Type _target = _typeReference.getTarget();
    long _arrayDimension = param.getArrayDimension();
    String _stringRepresentation = StringOperationsJaMoPP.getStringRepresentation(_target, _arrayDimension);
    return (_stringRepresentation + "...");
  }
  
  public static String getNamespaceAsString(final Iterable<String> namespaces) {
    return StringUtils.join(((Object[])Conversions.unwrapArray(namespaces, Object.class)), ".");
  }
  
  public static String getStringRepresentation(final Commentable param) {
    if (param instanceof OrdinaryParameter) {
      return _getStringRepresentation((OrdinaryParameter)param);
    } else if (param instanceof VariableLengthParameter) {
      return _getStringRepresentation((VariableLengthParameter)param);
    } else if (param instanceof StaticClassifierImport) {
      return _getStringRepresentation((StaticClassifierImport)param);
    } else if (param instanceof StaticMemberImport) {
      return _getStringRepresentation((StaticMemberImport)param);
    } else if (param instanceof ClassifierImport) {
      return _getStringRepresentation((ClassifierImport)param);
    } else if (param instanceof PackageImport) {
      return _getStringRepresentation((PackageImport)param);
    } else if (param instanceof Method) {
      return _getStringRepresentation((Method)param);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(param).toString());
    }
  }
  
  public static String getStringRepresentation(final Commentable pt, final long arrayDimensions) {
    if (pt instanceof Classifier) {
      return _getStringRepresentation((Classifier)pt, arrayDimensions);
    } else if (pt instanceof NamespaceClassifierReference) {
      return _getStringRepresentation((NamespaceClassifierReference)pt, arrayDimensions);
    } else if (pt instanceof PrimitiveType) {
      return _getStringRepresentation((PrimitiveType)pt, arrayDimensions);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(pt, arrayDimensions).toString());
    }
  }
}
