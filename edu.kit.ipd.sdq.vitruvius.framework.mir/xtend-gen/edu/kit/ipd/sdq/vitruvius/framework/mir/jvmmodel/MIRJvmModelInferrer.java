package edu.kit.ipd.sdq.vitruvius.framework.mir.jvmmodel;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl.JavaInvariantInvoker;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl.JavaPredicateEvaluator;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl.JavaResponseInvoker;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl.OCLInvariant;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl.OCLPredicateEvaluator;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.Invariant;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.WhenEvaluator;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.reflection.DynamicInvariant;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.reflection.DynamicResponse;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.reflection.MappingOperation;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.BaseMapping;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.EClassParameter;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRFile;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MappableElement;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Mapping;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedEClass;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedFeature;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.OCLBlock;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Response;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.ResponseAction;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.SubMapping;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.When;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Where;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtend2.lib.StringConcatenationClient;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.common.types.JvmAnnotationReference;
import org.eclipse.xtext.common.types.JvmField;
import org.eclipse.xtext.common.types.JvmFormalParameter;
import org.eclipse.xtext.common.types.JvmGenericType;
import org.eclipse.xtext.common.types.JvmMember;
import org.eclipse.xtext.common.types.JvmOperation;
import org.eclipse.xtext.common.types.JvmTypeReference;
import org.eclipse.xtext.xbase.XBlockExpression;
import org.eclipse.xtext.xbase.XExpression;
import org.eclipse.xtext.xbase.jvmmodel.AbstractModelInferrer;
import org.eclipse.xtext.xbase.jvmmodel.IJvmDeclaredTypeAcceptor;
import org.eclipse.xtext.xbase.jvmmodel.JvmTypesBuilder;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.eclipse.xtext.xbase.lib.StringExtensions;

/**
 * <p>Infers a JVM model from the source model.</p>
 * 
 * <p>The JVM model should contain all elements that would appear in the Java code
 * which is generated from the source model. Other models link against the JVM model rather than the source model.</p>
 */
@SuppressWarnings("all")
public class MIRJvmModelInferrer extends AbstractModelInferrer {
  @Inject
  @Extension
  private JvmTypesBuilder _jvmTypesBuilder;
  
  private int responseCounter;
  
  private int invariantCounter;
  
  private int mappingCounter;
  
  private int whenCounter;
  
  private final static String INVARIANT_TYPE = Invariant.class.getName();
  
  private final static String RESPONSE_INVOKER_TYPE = JavaResponseInvoker.class.getName();
  
  private final static String INVARIANT_INVOKER_TYPE = JavaInvariantInvoker.class.getName();
  
  private final static String EOBJECT_TYPE = EObject.class.getName();
  
  private final static String CONTEXT_VAR_NAME = "context";
  
  private final static String WHEN_PREFIX = "WHEN_";
  
  private final static String WHERE_PREFIX = "WHERE_";
  
  private List<String> whenCreateStatements;
  
  private List<String> whereCreateStatements;
  
  protected void _infer(final MIRFile element, final IJvmDeclaredTypeAcceptor acceptor, final boolean isPreIndexingPhase) {
    if ((!isPreIndexingPhase)) {
      final String pkgName = element.getGeneratedPackage();
      String _elvis = null;
      String _generatedClass = element.getGeneratedClass();
      if (_generatedClass != null) {
        _elvis = _generatedClass;
      } else {
        _elvis = "MIRChangeListener";
      }
      final String changeListenerName = _elvis;
      final String changeListenerNameFQN = ((pkgName + ".") + changeListenerName);
      final String mappingsClassNameFQN = (((pkgName + ".") + changeListenerName) + "Mappings");
      EcoreUtil2.resolveAll(element);
      this.mappingCounter = 0;
      this.whenCounter = 0;
      ArrayList<String> _newArrayList = CollectionLiterals.<String>newArrayList();
      this.whenCreateStatements = _newArrayList;
      ArrayList<String> _newArrayList_1 = CollectionLiterals.<String>newArrayList();
      this.whereCreateStatements = _newArrayList_1;
      final Map<Mapping, List<String>> mappingToWhenEvaluation = CollectionLiterals.<Mapping, List<String>>newHashMap();
      final Map<Mapping, List<String>> mappingToWhereEvaluation = CollectionLiterals.<Mapping, List<String>>newHashMap();
      EList<Mapping> _mappings = element.getMappings();
      this.createWhenEvaluationStatements(_mappings, pkgName, acceptor, mappingToWhenEvaluation);
      EList<Mapping> _mappings_1 = element.getMappings();
      this.createWhereEvaluationStatements(_mappings_1, pkgName, acceptor, mappingToWhereEvaluation);
      JvmGenericType _class = this._jvmTypesBuilder.toClass(element, mappingsClassNameFQN);
      final Procedure1<JvmGenericType> _function = new Procedure1<JvmGenericType>() {
        public void apply(final JvmGenericType mappingClass) {
          EList<Mapping> _mappings = element.getMappings();
          final Consumer<Mapping> _function = new Consumer<Mapping>() {
            public void accept(final Mapping mapping) {
              MIRJvmModelInferrer.this.createMappingMethods(mapping, acceptor, mappingClass, mappingToWhenEvaluation, null);
            }
          };
          _mappings.forEach(_function);
        }
      };
      acceptor.<JvmGenericType>accept(_class, _function);
      this.invariantCounter = 0;
      final ArrayList<String> createInvariantStatements = new ArrayList<String>();
      EList<edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Invariant> _invariants = element.getInvariants();
      final Consumer<edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Invariant> _function_1 = new Consumer<edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Invariant>() {
        public void accept(final edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Invariant invariant) {
          EObject _predicate = invariant.getPredicate();
          boolean _notEquals = (!Objects.equal(_predicate, null));
          if (_notEquals) {
            EObject _predicate_1 = invariant.getPredicate();
            final String stmnt = MIRJvmModelInferrer.this.createInvariant(_predicate_1, pkgName, invariant, acceptor);
            createInvariantStatements.add(stmnt);
          }
        }
      };
      _invariants.forEach(_function_1);
      this.responseCounter = 0;
      final ArrayList<String> createResponseStatements = new ArrayList<String>();
      EList<Response> _responses = element.getResponses();
      final Consumer<Response> _function_2 = new Consumer<Response>() {
        public void accept(final Response response) {
          final String stmnt = MIRJvmModelInferrer.this.createResponse(response, pkgName, acceptor);
          createResponseStatements.add(stmnt);
        }
      };
      _responses.forEach(_function_2);
      JvmGenericType _class_1 = this._jvmTypesBuilder.toClass(element, changeListenerNameFQN);
      final Procedure1<JvmGenericType> _function_3 = new Procedure1<JvmGenericType>() {
        public void apply(final JvmGenericType changeListener) {
          EList<JvmMember> _members = changeListener.getMembers();
          JvmTypeReference _typeRef = MIRJvmModelInferrer.this._typeReferenceBuilder.typeRef(Void.TYPE);
          final Procedure1<JvmOperation> _function = new Procedure1<JvmOperation>() {
            public void apply(final JvmOperation it) {
              StringConcatenationClient _client = new StringConcatenationClient() {
                @Override
                protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
                  _builder.append("\t");
                  _builder.append("// create and link when statements");
                  _builder.newLine();
                  _builder.append("\t");
                  String _join = IterableExtensions.join(MIRJvmModelInferrer.this.whenCreateStatements, "\n");
                  _builder.append(_join, "\t");
                  _builder.newLineIfNotEmpty();
                  _builder.newLine();
                  _builder.append("\t");
                  _builder.append("// mappings");
                  _builder.newLine();
                  _builder.append("\t");
                  _builder.append("// setMappingsClass(");
                  _builder.append(mappingsClassNameFQN, "\t");
                  _builder.append(".class);");
                  _builder.newLineIfNotEmpty();
                  _builder.newLine();
                  _builder.append("\t");
                  _builder.append("// create invariants");
                  _builder.newLine();
                  _builder.append("\t");
                  final Function1<String, String> _function = new Function1<String, String>() {
                    public String apply(final String it) {
                      return (("addInvariant(" + it) + ");");
                    }
                  };
                  List<String> _map = ListExtensions.<String, String>map(createInvariantStatements, _function);
                  String _join_1 = IterableExtensions.join(_map, "\n");
                  _builder.append(_join_1, "\t");
                  _builder.newLineIfNotEmpty();
                  _builder.append("\t");
                  _builder.newLine();
                  _builder.append("\t");
                  _builder.append("// create responses");
                  _builder.newLine();
                  _builder.append("\t");
                  final Function1<String, String> _function_1 = new Function1<String, String>() {
                    public String apply(final String it) {
                      return (("addResponse(" + it) + ");");
                    }
                  };
                  List<String> _map_1 = ListExtensions.<String, String>map(createResponseStatements, _function_1);
                  String _join_2 = IterableExtensions.join(_map_1, "\n");
                  _builder.append(_join_2, "\t");
                  _builder.newLineIfNotEmpty();
                }
              };
              MIRJvmModelInferrer.this._jvmTypesBuilder.setBody(it, _client);
            }
          };
          JvmOperation _method = MIRJvmModelInferrer.this._jvmTypesBuilder.toMethod(element, "setup", _typeRef, _function);
          MIRJvmModelInferrer.this._jvmTypesBuilder.<JvmOperation>operator_add(_members, _method);
          for (int i = 0; (i < MIRJvmModelInferrer.this.whenCounter); i++) {
            EList<JvmMember> _members_1 = changeListener.getMembers();
            JvmTypeReference _typeRef_1 = MIRJvmModelInferrer.this._typeReferenceBuilder.typeRef(WhenEvaluator.class);
            JvmField _field = MIRJvmModelInferrer.this._jvmTypesBuilder.toField(element, (MIRJvmModelInferrer.WHEN_PREFIX + Integer.valueOf(i)), _typeRef_1);
            MIRJvmModelInferrer.this._jvmTypesBuilder.<JvmField>operator_add(_members_1, _field);
          }
        }
      };
      acceptor.<JvmGenericType>accept(_class_1, _function_3);
    }
  }
  
  public void createWhenEvaluationStatements(final EList<Mapping> mappingList, final String pkgName, final IJvmDeclaredTypeAcceptor acceptor, final Map<Mapping, List<String>> whenMap) {
    final Consumer<Mapping> _function = new Consumer<Mapping>() {
      public void accept(final Mapping mapping) {
        final ArrayList<String> whenEvaluationStatements = CollectionLiterals.<String>newArrayList();
        EList<When> _whens = mapping.getWhens();
        for (final When when : _whens) {
          EList<MappableElement> _mappedElements = mapping.getMappedElements();
          MappableElement _get = _mappedElements.get(0);
          String _createWhenClass = MIRJvmModelInferrer.this.createWhenClass(when, _get, pkgName, acceptor);
          whenEvaluationStatements.add(_createWhenClass);
        }
        whenMap.put(mapping, whenEvaluationStatements);
        EList<Mapping> _withs = mapping.getWiths();
        MIRJvmModelInferrer.this.createWhenEvaluationStatements(_withs, pkgName, acceptor, whenMap);
      }
    };
    mappingList.forEach(_function);
  }
  
  public void createWhereEvaluationStatements(final EList<Mapping> mappingList, final String pkgName, final IJvmDeclaredTypeAcceptor acceptor, final Map<Mapping, List<String>> whereMap) {
  }
  
  public String createWhereClass(final Where where, final MappableElement element, final String string, final IJvmDeclaredTypeAcceptor acceptor) {
    return null;
  }
  
  /**
   * Generic method for when and where is createPredicate.
   */
  public String createWhenClass__(final When when, final MappableElement context, final String pkgName, final IJvmDeclaredTypeAcceptor acceptor) {
    return null;
  }
  
  public String createWhenClass(final When when, final MappableElement context, final String pkgName, final IJvmDeclaredTypeAcceptor acceptor) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("this.");
    _builder.append(MIRJvmModelInferrer.WHEN_PREFIX, "");
    _builder.append(this.whenCounter, "");
    final String currentNameVarName = _builder.toString();
    EObject _predicate = when.getPredicate();
    if ((_predicate instanceof XBlockExpression)) {
      EObject _predicate_1 = when.getPredicate();
      final XBlockExpression predicate = ((XBlockExpression) _predicate_1);
      final String whenClassName = ((pkgName + ".whens.When") + Integer.valueOf(this.whenCounter));
      final Procedure1<JvmGenericType> _function = new Procedure1<JvmGenericType>() {
        public void apply(final JvmGenericType whenClass) {
          EList<JvmMember> _members = whenClass.getMembers();
          JvmTypeReference _typeRef = MIRJvmModelInferrer.this._typeReferenceBuilder.typeRef(Boolean.TYPE);
          final Procedure1<JvmOperation> _function = new Procedure1<JvmOperation>() {
            public void apply(final JvmOperation checkMethod) {
              EList<JvmFormalParameter> _parameters = checkMethod.getParameters();
              JvmFormalParameter _parameter = MIRJvmModelInferrer.this.toParameter(when, context);
              MIRJvmModelInferrer.this._jvmTypesBuilder.<JvmFormalParameter>operator_add(_parameters, _parameter);
              MIRJvmModelInferrer.this._jvmTypesBuilder.setBody(checkMethod, predicate);
            }
          };
          JvmOperation _method = MIRJvmModelInferrer.this._jvmTypesBuilder.toMethod(when, "check", _typeRef, _function);
          MIRJvmModelInferrer.this._jvmTypesBuilder.<JvmOperation>operator_add(_members, _method);
        }
      };
      JvmGenericType _class = this._jvmTypesBuilder.toClass(when, whenClassName, _function);
      acceptor.<JvmGenericType>accept(_class);
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append(currentNameVarName, "");
      _builder_1.append(" = new ");
      String _name = JavaPredicateEvaluator.class.getName();
      _builder_1.append(_name, "");
      _builder_1.append("(");
      _builder_1.append(whenClassName, "");
      _builder_1.append(".class);");
      this.whenCreateStatements.add(_builder_1.toString());
      this.whenCounter++;
    } else {
      EObject _predicate_2 = when.getPredicate();
      if ((_predicate_2 instanceof OCLBlock)) {
        EObject _predicate_3 = when.getPredicate();
        final OCLBlock predicate_1 = ((OCLBlock) _predicate_3);
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append(currentNameVarName, "");
        _builder_2.append(" = new ");
        String _name_1 = OCLPredicateEvaluator.class.getName();
        _builder_2.append(_name_1, "");
        _builder_2.append("(");
        _builder_2.newLineIfNotEmpty();
        _builder_2.append("\t");
        _builder_2.append("\"");
        String _oclString = predicate_1.getOclString();
        _builder_2.append(_oclString, "\t");
        _builder_2.append("\",");
        _builder_2.newLineIfNotEmpty();
        _builder_2.append("\t");
        _builder_2.append("null, null");
        _builder_2.newLine();
        _builder_2.append(");");
        this.whenCreateStatements.add(_builder_2.toString());
        this.whenCounter++;
      }
    }
    StringConcatenation _builder_3 = new StringConcatenation();
    _builder_3.append(currentNameVarName, "");
    _builder_3.append(".check(source, target)");
    return _builder_3.toString();
  }
  
  protected void _createMappingMethods(final BaseMapping mapping, final IJvmDeclaredTypeAcceptor acceptor, final JvmGenericType mappingClass, final Map<Mapping, List<String>> mappingToWhenEvaluation, final Mapping parentMapping) {
    final List<String> whenEvaluationStatements = mappingToWhenEvaluation.get(mapping);
    EList<MappableElement> _mappedElements = mapping.getMappedElements();
    MappableElement _get = _mappedElements.get(0);
    final NamedEClass leftClass = ((NamedEClass) _get);
    EList<MappableElement> _mappedElements_1 = mapping.getMappedElements();
    MappableElement _get_1 = _mappedElements_1.get(1);
    final NamedEClass rightClass = ((NamedEClass) _get_1);
    this.createCreateMethod(mappingClass, whenEvaluationStatements, mapping, leftClass, rightClass, acceptor, null);
    this.createCreateMethod(mappingClass, whenEvaluationStatements, mapping, rightClass, leftClass, acceptor, null);
    this.mappingCounter++;
    EList<Mapping> _withs = mapping.getWiths();
    final Consumer<Mapping> _function = new Consumer<Mapping>() {
      public void accept(final Mapping subMapping) {
        MIRJvmModelInferrer.this.createMappingMethods(subMapping, acceptor, mappingClass, mappingToWhenEvaluation, mapping);
      }
    };
    _withs.forEach(_function);
  }
  
  protected void _createMappingMethods(final SubMapping mapping, final IJvmDeclaredTypeAcceptor acceptor, final JvmGenericType mappingClass, final Map<Mapping, List<String>> mappingToWhenEvaluation, final Mapping parentMapping) {
    final List<String> whenEvaluationStatements = mappingToWhenEvaluation.get(mapping);
    EList<MappableElement> _mappedElements = mapping.getMappedElements();
    MappableElement _get = _mappedElements.get(0);
    final NamedFeature leftFeature = ((NamedFeature) _get);
    EList<MappableElement> _mappedElements_1 = mapping.getMappedElements();
    MappableElement _get_1 = _mappedElements_1.get(1);
    final NamedFeature rightFeature = ((NamedFeature) _get_1);
    this.createUpdateMethod(mappingClass, whenEvaluationStatements, mapping, leftFeature, rightFeature, acceptor, parentMapping);
    this.createUpdateMethod(mappingClass, whenEvaluationStatements, mapping, rightFeature, leftFeature, acceptor, parentMapping);
    this.mappingCounter++;
    EList<Mapping> _withs = mapping.getWiths();
    final Consumer<Mapping> _function = new Consumer<Mapping>() {
      public void accept(final Mapping subMapping) {
        MIRJvmModelInferrer.this.createMappingMethods(subMapping, acceptor, mappingClass, mappingToWhenEvaluation, mapping);
      }
    };
    _withs.forEach(_function);
  }
  
  public int createCreateMethod(final JvmGenericType type, final Collection<String> whenEvaluationStatements, final BaseMapping mapping, final NamedEClass left, final NamedEClass right, final IJvmDeclaredTypeAcceptor acceptor, final Mapping parentMapping) {
    int _xblockexpression = (int) 0;
    {
      EClass _representedEClass = left.getRepresentedEClass();
      final String leftTypeName = _representedEClass.getInstanceTypeName();
      EClass _representedEClass_1 = right.getRepresentedEClass();
      final String rightTypeName = _representedEClass_1.getInstanceTypeName();
      EList<JvmMember> _members = type.getMembers();
      String _typeIdentifier = this.toTypeIdentifier(left);
      String _plus = ("create_" + _typeIdentifier);
      String _plus_1 = (_plus + "_");
      String _plus_2 = (_plus_1 + Integer.valueOf(this.mappingCounter));
      JvmTypeReference _typeRef = this._typeReferenceBuilder.typeRef(rightTypeName);
      final Procedure1<JvmOperation> _function = new Procedure1<JvmOperation>() {
        public void apply(final JvmOperation it) {
          EList<JvmAnnotationReference> _annotations = it.getAnnotations();
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("CREATE;");
          EClass _representedEClass = left.getRepresentedEClass();
          String _instanceTypeName = _representedEClass.getInstanceTypeName();
          _builder.append(_instanceTypeName, "");
          _builder.append(";");
          EClass _representedEClass_1 = right.getRepresentedEClass();
          String _instanceTypeName_1 = _representedEClass_1.getInstanceTypeName();
          _builder.append(_instanceTypeName_1, "");
          JvmAnnotationReference _annotationRef = MIRJvmModelInferrer.this._annotationTypesBuilder.annotationRef(MappingOperation.class, _builder.toString());
          MIRJvmModelInferrer.this._jvmTypesBuilder.<JvmAnnotationReference>operator_add(_annotations, _annotationRef);
          EList<JvmFormalParameter> _parameters = it.getParameters();
          JvmTypeReference _typeRef = MIRJvmModelInferrer.this._typeReferenceBuilder.typeRef(leftTypeName);
          JvmFormalParameter _parameter = MIRJvmModelInferrer.this._jvmTypesBuilder.toParameter(left, "source", _typeRef);
          MIRJvmModelInferrer.this._jvmTypesBuilder.<JvmFormalParameter>operator_add(_parameters, _parameter);
          StringConcatenationClient _client = new StringConcatenationClient() {
            @Override
            protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
              _builder.append("// check when predicate: ");
              _builder.newLine();
              _builder.append("// if !((");
              final Function1<String, String> _function = new Function1<String, String>() {
                public String apply(final String it) {
                  return (("(" + it) + ")");
                }
              };
              Iterable<String> _map = IterableExtensions.<String, String>map(whenEvaluationStatements, _function);
              String _join = IterableExtensions.join(_map, "\n\t// && ");
              _builder.append(_join, "");
              _builder.append("))");
              _builder.newLineIfNotEmpty();
              _builder.append("//   return null; // not the right mapping");
              _builder.newLine();
              _builder.append("//");
              _builder.newLine();
              _builder.append("// ");
              _builder.append(MIRJvmModelInferrer.EOBJECT_TYPE, "");
              _builder.append(" target = newInstance(");
              _builder.append(rightTypeName, "");
              _builder.append(");");
              _builder.newLineIfNotEmpty();
              _builder.append("// createCorrespondence(source, target);");
              _builder.newLine();
              _builder.append("// return correspondence");
              _builder.newLine();
              _builder.newLine();
              _builder.append("return null;");
              _builder.newLine();
            }
          };
          MIRJvmModelInferrer.this._jvmTypesBuilder.setBody(it, _client);
        }
      };
      JvmOperation _method = this._jvmTypesBuilder.toMethod(mapping, _plus_2, _typeRef, _function);
      this._jvmTypesBuilder.<JvmOperation>operator_add(_members, _method);
      _xblockexpression = this.mappingCounter++;
    }
    return _xblockexpression;
  }
  
  public int createUpdateMethod(final JvmGenericType type, final List<String> whenEvaluationStatements, final SubMapping mapping, final NamedFeature leftNamedFeature, final NamedFeature rightNamedFeature, final IJvmDeclaredTypeAcceptor acceptor, final Mapping parentMapping) {
    int _xblockexpression = (int) 0;
    {
      final EStructuralFeature leftFeature = leftNamedFeature.getRepresentedFeature();
      final EStructuralFeature rightFeature = rightNamedFeature.getRepresentedFeature();
      EClassifier _eType = leftFeature.getEType();
      final String leftFeatureTypeName = _eType.getInstanceTypeName();
      EClassifier _eType_1 = rightFeature.getEType();
      final String rightFeatureTypeName = _eType_1.getInstanceTypeName();
      NamedEClass _containingNamedEClass = leftNamedFeature.getContainingNamedEClass();
      final EClass leftClass = _containingNamedEClass.getRepresentedEClass();
      NamedEClass _containingNamedEClass_1 = rightNamedFeature.getContainingNamedEClass();
      final EClass rightClass = _containingNamedEClass_1.getRepresentedEClass();
      final String leftTypeName = leftClass.getInstanceTypeName();
      final String rightTypeName = rightClass.getInstanceTypeName();
      EList<JvmMember> _members = type.getMembers();
      String _name = leftClass.getName();
      String _plus = ("update_" + _name);
      String _plus_1 = (_plus + "_");
      String _name_1 = leftFeature.getName();
      String _plus_2 = (_plus_1 + _name_1);
      String _plus_3 = (_plus_2 + "_");
      String _plus_4 = (_plus_3 + Integer.valueOf(this.mappingCounter));
      JvmTypeReference _typeRef = this._typeReferenceBuilder.typeRef(Void.TYPE);
      final Procedure1<JvmOperation> _function = new Procedure1<JvmOperation>() {
        public void apply(final JvmOperation it) {
          EList<JvmFormalParameter> _parameters = it.getParameters();
          JvmTypeReference _typeRef = MIRJvmModelInferrer.this._typeReferenceBuilder.typeRef(leftTypeName);
          JvmFormalParameter _parameter = MIRJvmModelInferrer.this._jvmTypesBuilder.toParameter(leftNamedFeature, "context", _typeRef);
          MIRJvmModelInferrer.this._jvmTypesBuilder.<JvmFormalParameter>operator_add(_parameters, _parameter);
          EList<JvmFormalParameter> _parameters_1 = it.getParameters();
          JvmTypeReference _typeRef_1 = MIRJvmModelInferrer.this._typeReferenceBuilder.typeRef(leftFeatureTypeName);
          JvmFormalParameter _parameter_1 = MIRJvmModelInferrer.this._jvmTypesBuilder.toParameter(leftNamedFeature, "newValue", _typeRef_1);
          MIRJvmModelInferrer.this._jvmTypesBuilder.<JvmFormalParameter>operator_add(_parameters_1, _parameter_1);
          StringConcatenationClient _client = new StringConcatenationClient() {
            @Override
            protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
              _builder.append("// check correspondence:");
              _builder.newLine();
              _builder.append("// rightContext = forceGetCorrespondence(context)");
              _builder.newLine();
              _builder.append("// if !(rightContext instanceof ");
              _builder.append(rightTypeName, "");
              _builder.append(")");
              _builder.newLineIfNotEmpty();
              _builder.append("//   return; // not the wanted corresponding type");
              _builder.newLine();
              _builder.append("//");
              _builder.newLine();
              _builder.append("// check when predicate: ");
              _builder.newLine();
              _builder.append("// if !(");
              final Function1<String, String> _function = new Function1<String, String>() {
                public String apply(final String it) {
                  return (("(" + it) + ")");
                }
              };
              List<String> _map = ListExtensions.<String, String>map(whenEvaluationStatements, _function);
              String _join = IterableExtensions.join(_map, "\n\t// && ");
              _builder.append(_join, "");
              _builder.append(")");
              _builder.newLineIfNotEmpty();
              _builder.append("//   return null; // not the right correspondence");
              _builder.newLine();
              _builder.append("//");
              _builder.newLine();
              _builder.append("// right.");
              String _name = rightFeature.getName();
              _builder.append(_name, "");
              _builder.append(" = (");
              _builder.append(rightFeatureTypeName, "");
              _builder.append(") context.");
              String _name_1 = leftFeature.getName();
              _builder.append(_name_1, "");
              _builder.newLineIfNotEmpty();
            }
          };
          MIRJvmModelInferrer.this._jvmTypesBuilder.setBody(it, _client);
        }
      };
      JvmOperation _method = this._jvmTypesBuilder.toMethod(mapping, _plus_4, _typeRef, _function);
      this._jvmTypesBuilder.<JvmOperation>operator_add(_members, _method);
      _xblockexpression = this.mappingCounter++;
    }
    return _xblockexpression;
  }
  
  protected String _createInvariant(final OCLBlock oclBlock, final String pkgName, final edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Invariant inv, final IJvmDeclaredTypeAcceptor acceptor) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("new ");
    String _name = OCLInvariant.class.getName();
    _builder.append(_name, "");
    _builder.append("(");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    EClass _context = inv.getContext();
    String _instanceTypeName = _context.getInstanceTypeName();
    String _typeNameToEClassStatement = this.typeNameToEClassStatement(_instanceTypeName);
    _builder.append(_typeNameToEClassStatement, "\t");
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("\"");
    String _name_1 = inv.getName();
    _builder.append(_name_1, "\t");
    _builder.append("\",");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("\"");
    String _oclString = oclBlock.getOclString();
    _builder.append(_oclString, "\t");
    _builder.append("\")");
    return _builder.toString();
  }
  
  protected String _createInvariant(final XBlockExpression xbaseBlock, final String pkgName, final edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Invariant inv, final IJvmDeclaredTypeAcceptor acceptor) {
    final String invariantPkgName = ((pkgName + ".") + "invariants");
    String _name = inv.getName();
    String _firstUpper = StringExtensions.toFirstUpper(_name);
    final String invariantClassName = ((((invariantPkgName + ".Invariant_") + Integer.valueOf(this.invariantCounter)) + "_") + _firstUpper);
    JvmGenericType _class = this._jvmTypesBuilder.toClass(inv, invariantClassName);
    final Procedure1<JvmGenericType> _function = new Procedure1<JvmGenericType>() {
      public void apply(final JvmGenericType invariantClass) {
        EList<JvmAnnotationReference> _annotations = invariantClass.getAnnotations();
        JvmAnnotationReference _annotationRef = MIRJvmModelInferrer.this._annotationTypesBuilder.annotationRef(DynamicInvariant.class);
        MIRJvmModelInferrer.this._jvmTypesBuilder.<JvmAnnotationReference>operator_add(_annotations, _annotationRef);
        EList<JvmMember> _members = invariantClass.getMembers();
        JvmTypeReference _typeRef = MIRJvmModelInferrer.this._typeReferenceBuilder.typeRef(Boolean.TYPE);
        final Procedure1<JvmOperation> _function = new Procedure1<JvmOperation>() {
          public void apply(final JvmOperation it) {
            EList<JvmFormalParameter> _parameters = it.getParameters();
            EClass _context = inv.getContext();
            EClass _context_1 = inv.getContext();
            String _instanceTypeName = _context_1.getInstanceTypeName();
            JvmTypeReference _typeRef = MIRJvmModelInferrer.this._typeReferenceBuilder.typeRef(_instanceTypeName);
            JvmFormalParameter _parameter = MIRJvmModelInferrer.this._jvmTypesBuilder.toParameter(_context, MIRJvmModelInferrer.CONTEXT_VAR_NAME, _typeRef);
            MIRJvmModelInferrer.this._jvmTypesBuilder.<JvmFormalParameter>operator_add(_parameters, _parameter);
            MIRJvmModelInferrer.this._jvmTypesBuilder.setBody(it, xbaseBlock);
          }
        };
        JvmOperation _method = MIRJvmModelInferrer.this._jvmTypesBuilder.toMethod(inv, JavaInvariantInvoker.CHECK_METHOD_NAME, _typeRef, _function);
        MIRJvmModelInferrer.this._jvmTypesBuilder.<JvmOperation>operator_add(_members, _method);
      }
    };
    acceptor.<JvmGenericType>accept(_class, _function);
    this.invariantCounter++;
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("java.util.Arrays.asList(new ");
    _builder.append(MIRJvmModelInferrer.INVARIANT_TYPE, "");
    _builder.append(".Parameter[] {");
    String _plus = (_builder.toString() + "\n");
    EList<EClassParameter> _params = inv.getParams();
    final Function1<EClassParameter, String> _function_1 = new Function1<EClassParameter, String>() {
      public String apply(final EClassParameter it) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("\t");
        _builder.append("new ");
        _builder.append(MIRJvmModelInferrer.INVARIANT_TYPE, "\t");
        _builder.append(".Parameter(");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t\t\t\t");
        _builder.append("\"");
        String _name = it.getName();
        _builder.append(_name, "\t\t\t\t\t");
        _builder.append("\",");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t\t\t\t");
        EClass _type = it.getType();
        String _instanceTypeName = _type.getInstanceTypeName();
        String _typeNameToEClassStatement = MIRJvmModelInferrer.this.typeNameToEClassStatement(_instanceTypeName);
        _builder.append(_typeNameToEClassStatement, "\t\t\t\t\t");
        _builder.append(")");
        return _builder.toString();
      }
    };
    List<String> _map = ListExtensions.<EClassParameter, String>map(_params, _function_1);
    String _join = IterableExtensions.join(_map, "\n");
    String _plus_1 = (_plus + _join);
    final String signatureCreationJava = (_plus_1 + "\n})");
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("new ");
    _builder_1.append(MIRJvmModelInferrer.INVARIANT_INVOKER_TYPE, "");
    _builder_1.append("(");
    _builder_1.newLineIfNotEmpty();
    _builder_1.append("\t");
    _builder_1.append(invariantClassName, "\t");
    _builder_1.append(".class,");
    _builder_1.newLineIfNotEmpty();
    _builder_1.append("\t");
    _builder_1.append("\"");
    String _name_1 = inv.getName();
    _builder_1.append(_name_1, "\t");
    _builder_1.append("\",");
    _builder_1.newLineIfNotEmpty();
    _builder_1.append("\t");
    EClass _context = inv.getContext();
    String _instanceTypeName = _context.getInstanceTypeName();
    String _typeNameToEClassStatement = this.typeNameToEClassStatement(_instanceTypeName);
    _builder_1.append(_typeNameToEClassStatement, "\t");
    _builder_1.append(",");
    _builder_1.newLineIfNotEmpty();
    _builder_1.append("\t");
    _builder_1.append(signatureCreationJava, "\t");
    _builder_1.newLineIfNotEmpty();
    _builder_1.append(")");
    return _builder_1.toString();
  }
  
  public String typeNameToEClassStatement(final String typeName) {
    String _xblockexpression = null;
    {
      final String[] splittedName = typeName.split("\\.");
      int _size = ((List<String>)Conversions.doWrapArray(splittedName)).size();
      int _minus = (_size - 1);
      final String tail = splittedName[_minus];
      int _size_1 = ((List<String>)Conversions.doWrapArray(splittedName)).size();
      int _minus_1 = (_size_1 - 2);
      final String pkgName = splittedName[_minus_1];
      int _size_2 = ((List<String>)Conversions.doWrapArray(splittedName)).size();
      int _minus_2 = (_size_2 - 1);
      List<String> _subList = ((List<String>)Conversions.doWrapArray(splittedName)).subList(0, _minus_2);
      String _join = IterableExtensions.join(_subList, ".");
      String _plus = (_join + ".");
      String _firstUpper = StringExtensions.toFirstUpper(pkgName);
      String _plus_1 = (_plus + _firstUpper);
      String _plus_2 = (_plus_1 + "Package.eINSTANCE.get");
      String _plus_3 = (_plus_2 + tail);
      _xblockexpression = (_plus_3 + "()");
    }
    return _xblockexpression;
  }
  
  public String translateResponseAction(final ResponseAction ra) {
    String _name = edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.ResponseAction.class.getName();
    String _plus = (_name + ".");
    String _name_1 = ra.getName();
    return (_plus + _name_1);
  }
  
  public String createResponse(final Response resp, final String pkgName, final IJvmDeclaredTypeAcceptor acceptor) {
    final String responsePkgName = ((pkgName + ".") + "responses");
    final String responseClassName = (((responsePkgName + ".") + "Response_") + Integer.valueOf(this.responseCounter));
    JvmGenericType _class = this._jvmTypesBuilder.toClass(resp, responseClassName);
    final Procedure1<JvmGenericType> _function = new Procedure1<JvmGenericType>() {
      public void apply(final JvmGenericType responseClass) {
        EList<JvmAnnotationReference> _annotations = responseClass.getAnnotations();
        JvmAnnotationReference _annotationRef = MIRJvmModelInferrer.this._annotationTypesBuilder.annotationRef(DynamicResponse.class);
        MIRJvmModelInferrer.this._jvmTypesBuilder.<JvmAnnotationReference>operator_add(_annotations, _annotationRef);
        EList<JvmMember> _members = responseClass.getMembers();
        JvmTypeReference _typeRef = MIRJvmModelInferrer.this._typeReferenceBuilder.typeRef(Void.TYPE);
        final Procedure1<JvmOperation> _function = new Procedure1<JvmOperation>() {
          public void apply(final JvmOperation it) {
            MappableElement _context = resp.getContext();
            boolean _notEquals = (!Objects.equal(_context, null));
            if (_notEquals) {
              EList<JvmFormalParameter> _parameters = it.getParameters();
              MappableElement _context_1 = resp.getContext();
              MappableElement _context_2 = resp.getContext();
              EClass _representedEClass = ((NamedEClass) _context_2).getRepresentedEClass();
              String _instanceTypeName = _representedEClass.getInstanceTypeName();
              JvmTypeReference _typeRef = MIRJvmModelInferrer.this._typeReferenceBuilder.typeRef(_instanceTypeName);
              JvmFormalParameter _parameter = MIRJvmModelInferrer.this._jvmTypesBuilder.toParameter(_context_1, 
                MIRJvmModelInferrer.CONTEXT_VAR_NAME, _typeRef);
              MIRJvmModelInferrer.this._jvmTypesBuilder.<JvmFormalParameter>operator_add(_parameters, _parameter);
            }
            edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Invariant _inv = resp.getInv();
            boolean _notEquals_1 = (!Objects.equal(_inv, null));
            if (_notEquals_1) {
              EList<JvmFormalParameter> _parameters_1 = it.getParameters();
              edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Invariant _inv_1 = resp.getInv();
              EList<EClassParameter> _params = _inv_1.getParams();
              final Function1<EClassParameter, JvmFormalParameter> _function = new Function1<EClassParameter, JvmFormalParameter>() {
                public JvmFormalParameter apply(final EClassParameter it) {
                  String _name = it.getName();
                  EClass _type = it.getType();
                  String _instanceTypeName = _type.getInstanceTypeName();
                  JvmTypeReference _typeRef = MIRJvmModelInferrer.this._typeReferenceBuilder.typeRef(_instanceTypeName);
                  return MIRJvmModelInferrer.this._jvmTypesBuilder.toParameter(it, _name, _typeRef);
                }
              };
              List<JvmFormalParameter> _map = ListExtensions.<EClassParameter, JvmFormalParameter>map(_params, _function);
              MIRJvmModelInferrer.this._jvmTypesBuilder.<JvmFormalParameter>operator_add(_parameters_1, _map);
            }
            XExpression _restoreAction = resp.getRestoreAction();
            boolean _notEquals_2 = (!Objects.equal(_restoreAction, null));
            if (_notEquals_2) {
              XExpression _restoreAction_1 = resp.getRestoreAction();
              MIRJvmModelInferrer.this._jvmTypesBuilder.setBody(it, _restoreAction_1);
            }
          }
        };
        JvmOperation _method = MIRJvmModelInferrer.this._jvmTypesBuilder.toMethod(resp, JavaResponseInvoker.DISPATCH_METHOD_NAME, _typeRef, _function);
        MIRJvmModelInferrer.this._jvmTypesBuilder.<JvmOperation>operator_add(_members, _method);
      }
    };
    acceptor.<JvmGenericType>accept(_class, _function);
    this.responseCounter++;
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("new ");
    _builder.append(MIRJvmModelInferrer.RESPONSE_INVOKER_TYPE, "");
    _builder.append("(");
    _builder.newLineIfNotEmpty();
    _builder.append("   \t\t\t");
    _builder.append(responseClassName, "   \t\t\t");
    _builder.append(".class,");
    _builder.newLineIfNotEmpty();
    _builder.append("   \t\t\t");
    ResponseAction _action = resp.getAction();
    String _translateResponseAction = this.translateResponseAction(_action);
    _builder.append(_translateResponseAction, "   \t\t\t");
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    _builder.append("   \t\t\t");
    MappableElement _context = resp.getContext();
    EClass _representedEClass = ((NamedEClass) _context).getRepresentedEClass();
    String _instanceTypeName = _representedEClass.getInstanceTypeName();
    String _typeNameToEClassStatement = this.typeNameToEClassStatement(_instanceTypeName);
    _builder.append(_typeNameToEClassStatement, "   \t\t\t");
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t");
    _builder.append("\"");
    edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Invariant _inv = resp.getInv();
    String _name = _inv.getName();
    _builder.append(_name, "\t\t\t");
    _builder.append("\"");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append(")");
    return _builder.toString();
  }
  
  protected JvmTypeReference _toParameterType(final NamedFeature object) {
    JvmTypeReference _xblockexpression = null;
    {
      EStructuralFeature fref = object.getRepresentedFeature();
      EClassifier _eType = fref.getEType();
      String typeName = _eType.getInstanceTypeName();
      _xblockexpression = this._typeReferenceBuilder.typeRef(typeName);
    }
    return _xblockexpression;
  }
  
  protected JvmTypeReference _toParameterType(final NamedEClass object) {
    EClass _representedEClass = object.getRepresentedEClass();
    String _instanceTypeName = _representedEClass.getInstanceTypeName();
    return this._typeReferenceBuilder.typeRef(_instanceTypeName);
  }
  
  protected JvmFormalParameter _toParameter(final When w, final MappableElement object) {
    String _name = object.getName();
    JvmTypeReference _parameterType = this.toParameterType(object);
    return this._jvmTypesBuilder.toParameter(w, _name, _parameterType);
  }
  
  public String toTypeIdentifier(final NamedEClass object) {
    EClass _representedEClass = object.getRepresentedEClass();
    String _name = _representedEClass.getName();
    return this.<String>orIfNull(_name, "unnamed");
  }
  
  protected String _toIdentifier(final NamedFeature object) {
    EStructuralFeature _representedFeature = object.getRepresentedFeature();
    String _name = _representedFeature.getName();
    String _orIfNull = this.<String>orIfNull(_name, "unnamed");
    String _plus = (_orIfNull + "_");
    String _name_1 = object.getName();
    String _orIfNull_1 = this.<String>orIfNull(_name_1, "unnamed");
    return (_plus + _orIfNull_1);
  }
  
  protected String _toIdentifier(final NamedEClass object) {
    EClass _eClass = object.eClass();
    String _name = _eClass.getName();
    String _orIfNull = this.<String>orIfNull(_name, "unnamed");
    String _plus = (_orIfNull + "_");
    String _name_1 = object.getName();
    String _orIfNull_1 = this.<String>orIfNull(_name_1, "unnamed");
    return (_plus + _orIfNull_1);
  }
  
  protected String _toIdentifier(final EClass eClass) {
    EPackage _ePackage = eClass.getEPackage();
    String _name = _ePackage.getName();
    String _name_1 = eClass.getName();
    String _plus = (_name + _name_1);
    return _plus.replaceAll("\\.", "_");
  }
  
  public <T extends Object> T orIfNull(final T a, final T b) {
    T _xifexpression = null;
    boolean _equals = Objects.equal(a, null);
    if (_equals) {
      _xifexpression = b;
    } else {
      _xifexpression = a;
    }
    return _xifexpression;
  }
  
  public void infer(final EObject element, final IJvmDeclaredTypeAcceptor acceptor, final boolean isPreIndexingPhase) {
    if (element instanceof MIRFile) {
      _infer((MIRFile)element, acceptor, isPreIndexingPhase);
      return;
    } else if (element != null) {
      _infer(element, acceptor, isPreIndexingPhase);
      return;
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(element, acceptor, isPreIndexingPhase).toString());
    }
  }
  
  public void createMappingMethods(final Mapping mapping, final IJvmDeclaredTypeAcceptor acceptor, final JvmGenericType mappingClass, final Map<Mapping, List<String>> mappingToWhenEvaluation, final Mapping parentMapping) {
    if (mapping instanceof BaseMapping) {
      _createMappingMethods((BaseMapping)mapping, acceptor, mappingClass, mappingToWhenEvaluation, parentMapping);
      return;
    } else if (mapping instanceof SubMapping) {
      _createMappingMethods((SubMapping)mapping, acceptor, mappingClass, mappingToWhenEvaluation, parentMapping);
      return;
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(mapping, acceptor, mappingClass, mappingToWhenEvaluation, parentMapping).toString());
    }
  }
  
  public String createInvariant(final EObject xbaseBlock, final String pkgName, final edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Invariant inv, final IJvmDeclaredTypeAcceptor acceptor) {
    if (xbaseBlock instanceof XBlockExpression) {
      return _createInvariant((XBlockExpression)xbaseBlock, pkgName, inv, acceptor);
    } else if (xbaseBlock instanceof OCLBlock) {
      return _createInvariant((OCLBlock)xbaseBlock, pkgName, inv, acceptor);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(xbaseBlock, pkgName, inv, acceptor).toString());
    }
  }
  
  public JvmTypeReference toParameterType(final MappableElement object) {
    if (object instanceof NamedEClass) {
      return _toParameterType((NamedEClass)object);
    } else if (object instanceof NamedFeature) {
      return _toParameterType((NamedFeature)object);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(object).toString());
    }
  }
  
  public JvmFormalParameter toParameter(final When w, final MappableElement object) {
    return _toParameter(w, object);
  }
  
  public String toIdentifier(final EObject eClass) {
    if (eClass instanceof EClass) {
      return _toIdentifier((EClass)eClass);
    } else if (eClass instanceof NamedEClass) {
      return _toIdentifier((NamedEClass)eClass);
    } else if (eClass instanceof NamedFeature) {
      return _toIdentifier((NamedFeature)eClass);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(eClass).toString());
    }
  }
}
