package edu.kit.ipd.sdq.vitruvius.framework.mir.scoping;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.BaseMapping;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Import;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MappableElement;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedEClass;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.NamedFeature;
import java.util.Collections;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.mwe2.language.scoping.QualifiedNameProvider;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.SimpleScope;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.scoping.XImportSectionNamespaceScopeProvider;

@SuppressWarnings("all")
public class MIRScopeProviderDelegate extends XImportSectionNamespaceScopeProvider {
  @Inject
  private QualifiedNameProvider qualifiedNameProvider;
  
  public IScope getScope(final EObject context, final EReference reference) {
    IScope _xblockexpression = null;
    {
      EClassifier _eType = reference.getEType();
      EClass _eClass = EcorePackage.eINSTANCE.getEClass();
      boolean _equals = _eType.equals(_eClass);
      if (_equals) {
        return this.getEClassScope(context);
      } else {
        EClassifier _eType_1 = reference.getEType();
        EClass _eStructuralFeature = EcorePackage.eINSTANCE.getEStructuralFeature();
        boolean _equals_1 = _eType_1.equals(_eStructuralFeature);
        if (_equals_1) {
          return this.getEFeatureScope(context);
        } else {
          boolean _and = false;
          if (!(context instanceof NamedFeature)) {
            _and = false;
          } else {
            EReference _namedFeature_ContainingNamedEClass = MIRPackage.eINSTANCE.getNamedFeature_ContainingNamedEClass();
            boolean _equals_2 = reference.equals(_namedFeature_ContainingNamedEClass);
            _and = _equals_2;
          }
          if (_and) {
            return this.getContainingNamedEClassScope(((NamedFeature) context));
          }
        }
      }
      _xblockexpression = super.getScope(context, reference);
    }
    return _xblockexpression;
  }
  
  public Iterable<EObject> getAllContentsOfEClass(final Resource res, final EClass namedParent, final boolean allContents) {
    List<EObject> _xifexpression = null;
    if (allContents) {
      TreeIterator<EObject> _allContents = res.getAllContents();
      _xifexpression = IteratorExtensions.<EObject>toList(_allContents);
    } else {
      _xifexpression = res.getContents();
    }
    List<EObject> contents = _xifexpression;
    final Function1<EObject, Boolean> _function = new Function1<EObject, Boolean>() {
      public Boolean apply(final EObject it) {
        EClass _eClass = it.eClass();
        return Boolean.valueOf(_eClass.equals(namedParent));
      }
    };
    return IterableExtensions.<EObject>filter(contents, _function);
  }
  
  public SimpleScope createQualifiedEClassScope(final Resource res) {
    EClass _import = MIRPackage.eINSTANCE.getImport();
    Iterable<EObject> _allContentsOfEClass = this.getAllContentsOfEClass(res, _import, true);
    List<EObject> contents = IterableExtensions.<EObject>toList(_allContentsOfEClass);
    Iterable<EObject> _filterNull = IterableExtensions.<EObject>filterNull(contents);
    final Function1<EObject, List<IEObjectDescription>> _function = new Function1<EObject, List<IEObjectDescription>>() {
      public List<IEObjectDescription> apply(final EObject importEObject) {
        final Import import_ = ((Import) importEObject);
        final String importName = import_.getName();
        boolean _and = false;
        boolean _notEquals = (!Objects.equal(importName, null));
        if (!_notEquals) {
          _and = false;
        } else {
          EPackage _package = import_.getPackage();
          boolean _notEquals_1 = (!Objects.equal(_package, null));
          _and = _notEquals_1;
        }
        if (_and) {
          EPackage _package_1 = import_.getPackage();
          final EList<EClassifier> classifiers = _package_1.getEClassifiers();
          final Function1<EClassifier, IEObjectDescription> _function = new Function1<EClassifier, IEObjectDescription>() {
            public IEObjectDescription apply(final EClassifier classifier) {
              QualifiedName _create = QualifiedName.create(importName);
              QualifiedName _fullyQualifiedName = MIRScopeProviderDelegate.this.qualifiedNameProvider.getFullyQualifiedName(classifier);
              QualifiedName _skipFirst = _fullyQualifiedName.skipFirst(1);
              QualifiedName _append = _create.append(_skipFirst);
              return EObjectDescription.create(_append, classifier);
            }
          };
          return ListExtensions.<EClassifier, IEObjectDescription>map(classifiers, _function);
        } else {
          return Collections.<IEObjectDescription>unmodifiableList(CollectionLiterals.<IEObjectDescription>newArrayList());
        }
      }
    };
    Iterable<List<IEObjectDescription>> classifierDescriptions = IterableExtensions.<EObject, List<IEObjectDescription>>map(_filterNull, _function);
    Iterable<IEObjectDescription> _flatten = Iterables.<IEObjectDescription>concat(classifierDescriptions);
    SimpleScope resultScope = new SimpleScope(IScope.NULLSCOPE, _flatten);
    return resultScope;
  }
  
  public SimpleScope getEClassScope(final EObject context) {
    Resource res = context.eResource();
    return this.createQualifiedEClassScope(res);
  }
  
  public IScope getEFeatureScope(final EObject context) {
    boolean _equals = Objects.equal(context, null);
    if (_equals) {
      return IScope.NULLSCOPE;
    }
    BaseMapping parentBaseMapping = EcoreUtil2.<BaseMapping>getContainerOfType(context, BaseMapping.class);
    Iterable<List<IEObjectDescription>> _xifexpression = null;
    boolean _and = false;
    boolean _notEquals = (!Objects.equal(parentBaseMapping, null));
    if (!_notEquals) {
      _and = false;
    } else {
      _and = (parentBaseMapping instanceof BaseMapping);
    }
    if (_and) {
      EList<MappableElement> _mappedElements = parentBaseMapping.getMappedElements();
      Iterable<NamedEClass> _filter = Iterables.<NamedEClass>filter(_mappedElements, NamedEClass.class);
      final Function1<NamedEClass, List<IEObjectDescription>> _function = new Function1<NamedEClass, List<IEObjectDescription>>() {
        public List<IEObjectDescription> apply(final NamedEClass namedClass) {
          EClass _representedEClass = namedClass.getRepresentedEClass();
          EList<EStructuralFeature> _eAllStructuralFeatures = _representedEClass.getEAllStructuralFeatures();
          final Function1<EStructuralFeature, IEObjectDescription> _function = new Function1<EStructuralFeature, IEObjectDescription>() {
            public IEObjectDescription apply(final EStructuralFeature it) {
              IEObjectDescription _xblockexpression = null;
              {
                String _name = it.getName();
                final QualifiedName qualifiedName = QualifiedName.create(_name);
                IEObjectDescription _xifexpression = null;
                boolean _notEquals = (!Objects.equal(qualifiedName, null));
                if (_notEquals) {
                  _xifexpression = EObjectDescription.create(qualifiedName, it);
                }
                _xblockexpression = _xifexpression;
              }
              return _xblockexpression;
            }
          };
          return ListExtensions.<EStructuralFeature, IEObjectDescription>map(_eAllStructuralFeatures, _function);
        }
      };
      _xifexpression = IterableExtensions.<NamedEClass, List<IEObjectDescription>>map(_filter, _function);
    } else {
      _xifexpression = Collections.<List<IEObjectDescription>>unmodifiableList(CollectionLiterals.<List<IEObjectDescription>>newArrayList());
    }
    Iterable<List<IEObjectDescription>> featureDescriptions = _xifexpression;
    Iterable<IEObjectDescription> _flatten = Iterables.<IEObjectDescription>concat(featureDescriptions);
    SimpleScope resultScope = new SimpleScope(IScope.NULLSCOPE, _flatten);
    return resultScope;
  }
  
  public IScope getContainingNamedEClassScope(final NamedFeature feature) {
    IScope resultScope = IScope.NULLSCOPE;
    for (EObject container = feature.eContainer(); (!Objects.equal(container, null)); container = container.eContainer()) {
      if ((container instanceof BaseMapping)) {
        final BaseMapping mapping = ((BaseMapping) container);
        EList<MappableElement> _mappedElements = mapping.getMappedElements();
        Iterable<NamedEClass> _filter = Iterables.<NamedEClass>filter(_mappedElements, NamedEClass.class);
        final Function1<NamedEClass, Boolean> _function = new Function1<NamedEClass, Boolean>() {
          public Boolean apply(final NamedEClass it) {
            String _name = it.getName();
            return Boolean.valueOf((!Objects.equal(_name, null)));
          }
        };
        Iterable<NamedEClass> _filter_1 = IterableExtensions.<NamedEClass>filter(_filter, _function);
        final Function1<NamedEClass, IEObjectDescription> _function_1 = new Function1<NamedEClass, IEObjectDescription>() {
          public IEObjectDescription apply(final NamedEClass namedClass) {
            String _name = namedClass.getName();
            return EObjectDescription.create(_name, namedClass);
          }
        };
        Iterable<IEObjectDescription> _map = IterableExtensions.<NamedEClass, IEObjectDescription>map(_filter_1, _function_1);
        SimpleScope _simpleScope = new SimpleScope(resultScope, _map);
        resultScope = _simpleScope;
      }
    }
    return resultScope;
  }
}
