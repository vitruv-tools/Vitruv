package edu.kit.ipd.sdq.vitruvius.casestudies.utils.jamopp;

import com.google.common.base.Objects;
import edu.kit.ipd.sdq.vitruvius.casestudies.utils.jamopp.StringOperationsJaMoPP;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.HierarchicalTUIDCalculatorAndResolver;
import java.util.Arrays;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.JavaRoot;
import org.emftext.language.java.imports.Import;
import org.emftext.language.java.imports.StaticImport;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.modifiers.Modifier;
import org.emftext.language.java.parameters.Parameter;
import org.emftext.language.java.types.ClassifierReference;
import org.emftext.language.java.types.NamespaceClassifierReference;
import org.emftext.language.java.types.PrimitiveType;
import org.emftext.language.java.types.TypeReference;

/**
 * TUID calculator and resolver for the JaMoPP meta-model.
 */
@SuppressWarnings("all")
public class JaMoPPTUIDCalculatorAndResolver extends HierarchicalTUIDCalculatorAndResolver<JavaRoot> {
  private final static Logger logger = Logger.getLogger(JaMoPPTUIDCalculatorAndResolver.class);
  
  private final String TUIDIdentifier = JaMoPPTUIDCalculatorAndResolver.class.getSimpleName();
  
  private final String CLASSIFIER_SELECTOR = "classifier";
  
  private final String IMPORT_SELECTOR = "import";
  
  private final String METHOD_SELECTOR = "method";
  
  private final String FIELD_SELECTOR = "field";
  
  protected String getTUIDIdentifier() {
    return this.TUIDIdentifier;
  }
  
  protected Class<JavaRoot> getRootObjectClass() {
    return JavaRoot.class;
  }
  
  protected boolean hasId(final EObject obj, final String indidivualId) throws IllegalArgumentException {
    String _calculateIndividualTUID = this.calculateIndividualTUID(obj);
    return Objects.equal(_calculateIndividualTUID, indidivualId);
  }
  
  protected String calculateIndividualTUIDDelegator(final EObject obj) {
    return this.calculateIndividualTUID(obj);
  }
  
  private String _calculateIndividualTUID(final org.emftext.language.java.containers.Package jaMoPPPackage) {
    return "";
  }
  
  private String _calculateIndividualTUID(final CompilationUnit compilationUnit) {
    String className = null;
    String _name = compilationUnit.getName();
    boolean _notEquals = (!Objects.equal(null, _name));
    if (_notEquals) {
      String _name_1 = compilationUnit.getName();
      className = _name_1;
    } else {
      EList<ConcreteClassifier> _classifiers = compilationUnit.getClassifiers();
      int _size = _classifiers.size();
      boolean _notEquals_1 = (0 != _size);
      if (_notEquals_1) {
        EList<ConcreteClassifier> _classifiers_1 = compilationUnit.getClassifiers();
        ConcreteClassifier _get = _classifiers_1.get(0);
        String _name_2 = _get.getName();
        String _plus = (_name_2 + ".java");
        className = _plus;
      } else {
        JaMoPPTUIDCalculatorAndResolver.logger.warn(("Could not determine a name for compilation unit: " + compilationUnit));
      }
    }
    int _countMatches = StringUtils.countMatches(className, ".");
    final boolean alreadyContainsNamespace = (1 < _countMatches);
    if (alreadyContainsNamespace) {
      return className;
    }
    String _namespaceAsString = this.getNamespaceAsString(compilationUnit);
    String _plus_1 = (_namespaceAsString + ".");
    return (_plus_1 + className);
  }
  
  private String _calculateIndividualTUID(final Classifier classifier) {
    String _name = classifier.getName();
    return ((this.CLASSIFIER_SELECTOR + HierarchicalTUIDCalculatorAndResolver.SUBDIVIDER) + _name);
  }
  
  private String _calculateIndividualTUID(final Method method) {
    final StringBuilder tuid = new StringBuilder();
    tuid.append(this.METHOD_SELECTOR);
    TypeReference _typeReference = method.getTypeReference();
    String _nameFrom = this.getNameFrom(_typeReference);
    String _plus = (HierarchicalTUIDCalculatorAndResolver.SUBDIVIDER + _nameFrom);
    String _plus_1 = (_plus + HierarchicalTUIDCalculatorAndResolver.SUBDIVIDER);
    String _name = method.getName();
    String _plus_2 = (_plus_1 + _name);
    tuid.append(_plus_2);
    EList<Parameter> _parameters = method.getParameters();
    final Procedure1<Parameter> _function = new Procedure1<Parameter>() {
      public void apply(final Parameter it) {
        TypeReference _typeReference = it.getTypeReference();
        String _nameFrom = JaMoPPTUIDCalculatorAndResolver.this.getNameFrom(_typeReference);
        String _plus = (HierarchicalTUIDCalculatorAndResolver.SUBDIVIDER + _nameFrom);
        tuid.append(_plus);
      }
    };
    IterableExtensions.<Parameter>forEach(_parameters, _function);
    return tuid.toString();
  }
  
  private String _calculateIndividualTUID(final Field field) {
    String _name = field.getName();
    return ((this.FIELD_SELECTOR + HierarchicalTUIDCalculatorAndResolver.SUBDIVIDER) + _name);
  }
  
  private String _calculateIndividualTUID(final Import importStatement) {
    String tuid = this.IMPORT_SELECTOR;
    if ((importStatement instanceof StaticImport)) {
      String _tuid = tuid;
      tuid = (_tuid + ("static" + HierarchicalTUIDCalculatorAndResolver.SUBDIVIDER));
    }
    String _stringRepresentation = StringOperationsJaMoPP.getStringRepresentation(importStatement);
    return (tuid + _stringRepresentation);
  }
  
  private String _calculateIndividualTUID(final Modifier modifier) {
    Class<? extends Modifier> _class = modifier.getClass();
    Class<?>[] _interfaces = _class.getInterfaces();
    Class<?> _get = _interfaces[0];
    return _get.getSimpleName();
  }
  
  private String _calculateIndividualTUID(final Parameter param) {
    return param.getName();
  }
  
  private String _calculateIndividualTUID(final NamespaceClassifierReference ref) {
    final StringBuilder tuid = new StringBuilder();
    EStructuralFeature _eContainingFeature = ref.eContainingFeature();
    String _name = _eContainingFeature.getName();
    tuid.append(_name);
    EList<ClassifierReference> _classifierReferences = ref.getClassifierReferences();
    final Procedure1<ClassifierReference> _function = new Procedure1<ClassifierReference>() {
      public void apply(final ClassifierReference it) {
        Classifier _target = it.getTarget();
        String _name = _target.getName();
        tuid.append(_name);
      }
    };
    IterableExtensions.<ClassifierReference>forEach(_classifierReferences, _function);
    return tuid.toString();
  }
  
  private String _calculateIndividualTUID(final PrimitiveType pt) {
    Class<? extends PrimitiveType> _class = pt.getClass();
    Class<?>[] _interfaces = _class.getInterfaces();
    Class<?> _get = _interfaces[0];
    return _get.getSimpleName();
  }
  
  private String _calculateIndividualTUID(final EObject obj) {
    Class<? extends EObject> _class = obj.getClass();
    String _simpleName = _class.getSimpleName();
    String _plus = ("Invalid type given " + _simpleName);
    throw new IllegalArgumentException(_plus);
  }
  
  private String _getNameFrom(final NamespaceClassifierReference namespaceClassifierReference) {
    String name = "";
    int i = 0;
    EList<ClassifierReference> _classifierReferences = namespaceClassifierReference.getClassifierReferences();
    for (final ClassifierReference cr : _classifierReferences) {
      {
        if ((i > 0)) {
          String _name = name;
          name = (_name + Integer.valueOf(i));
        }
        String _name_1 = name;
        String _nameFrom = this.getNameFrom(cr);
        name = (_name_1 + _nameFrom);
        i++;
      }
    }
    return name;
  }
  
  private String _getNameFrom(final PrimitiveType primitiveType) {
    EClass _eClass = primitiveType.eClass();
    String _name = _eClass.getName();
    return _name.replaceAll("Impl", "");
  }
  
  private String _getNameFrom(final ClassifierReference classifierReference) {
    Classifier _target = classifierReference.getTarget();
    return _target.getName();
  }
  
  private String getNamespaceAsString(final CompilationUnit cu) {
    EList<String> _namespaces = cu.getNamespaces();
    return StringOperationsJaMoPP.getNamespaceAsString(_namespaces);
  }
  
  private String calculateIndividualTUID(final EObject field) {
    if (field instanceof Field) {
      return _calculateIndividualTUID((Field)field);
    } else if (field instanceof Parameter) {
      return _calculateIndividualTUID((Parameter)field);
    } else if (field instanceof Classifier) {
      return _calculateIndividualTUID((Classifier)field);
    } else if (field instanceof CompilationUnit) {
      return _calculateIndividualTUID((CompilationUnit)field);
    } else if (field instanceof org.emftext.language.java.containers.Package) {
      return _calculateIndividualTUID((org.emftext.language.java.containers.Package)field);
    } else if (field instanceof Method) {
      return _calculateIndividualTUID((Method)field);
    } else if (field instanceof Import) {
      return _calculateIndividualTUID((Import)field);
    } else if (field instanceof Modifier) {
      return _calculateIndividualTUID((Modifier)field);
    } else if (field instanceof NamespaceClassifierReference) {
      return _calculateIndividualTUID((NamespaceClassifierReference)field);
    } else if (field instanceof PrimitiveType) {
      return _calculateIndividualTUID((PrimitiveType)field);
    } else if (field != null) {
      return _calculateIndividualTUID(field);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(field).toString());
    }
  }
  
  private String getNameFrom(final TypeReference classifierReference) {
    if (classifierReference instanceof ClassifierReference) {
      return _getNameFrom((ClassifierReference)classifierReference);
    } else if (classifierReference instanceof NamespaceClassifierReference) {
      return _getNameFrom((NamespaceClassifierReference)classifierReference);
    } else if (classifierReference instanceof PrimitiveType) {
      return _getNameFrom((PrimitiveType)classifierReference);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(classifierReference).toString());
    }
  }
}
