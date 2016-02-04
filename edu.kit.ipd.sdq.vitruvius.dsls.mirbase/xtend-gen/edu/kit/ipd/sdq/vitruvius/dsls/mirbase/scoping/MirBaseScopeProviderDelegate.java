package edu.kit.ipd.sdq.vitruvius.dsls.mirbase.scoping;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import edu.kit.ipd.sdq.vitruvius.dsls.mirbase.mirBase.FeatureOfElement;
import edu.kit.ipd.sdq.vitruvius.dsls.mirbase.mirBase.MetamodelImport;
import edu.kit.ipd.sdq.vitruvius.dsls.mirbase.mirBase.MirBasePackage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
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
public class MirBaseScopeProviderDelegate extends XImportSectionNamespaceScopeProvider {
  private final static Logger LOGGER = Logger.getLogger(MirBaseScopeProviderDelegate.class);
  
  public <T extends Object> IScope createScope(final IScope parentScope, final Iterator<T> elements, final Function<T, IEObjectDescription> descriptionCreation) {
    final Function1<T, IEObjectDescription> _function = (T it) -> {
      return descriptionCreation.apply(it);
    };
    Iterator<IEObjectDescription> _map = IteratorExtensions.<T, IEObjectDescription>map(elements, _function);
    Iterator<IEObjectDescription> _filterNull = IteratorExtensions.<IEObjectDescription>filterNull(_map);
    List<IEObjectDescription> _list = IteratorExtensions.<IEObjectDescription>toList(_filterNull);
    return new SimpleScope(parentScope, _list);
  }
  
  @Override
  public IScope getScope(final EObject context, final EReference reference) {
    IScope _xblockexpression = null;
    {
      boolean _equals = reference.equals(MirBasePackage.Literals.FEATURE_OF_ELEMENT__FEATURE);
      if (_equals) {
        EClass _element = null;
        if (((FeatureOfElement) context)!=null) {
          _element=((FeatureOfElement) context).getElement();
        }
        return this.createEStructuralFeatureScope(_element);
      } else {
        boolean _or = false;
        boolean _equals_1 = reference.equals(MirBasePackage.Literals.FEATURE_OF_ELEMENT__ELEMENT);
        if (_equals_1) {
          _or = true;
        } else {
          boolean _equals_2 = reference.equals(MirBasePackage.Literals.MODEL_ELEMENT__ELEMENT);
          _or = _equals_2;
        }
        if (_or) {
          Resource _eResource = context.eResource();
          return this.createQualifiedEClassScope(_eResource);
        } else {
          boolean _equals_3 = reference.equals(MirBasePackage.Literals.METAMODEL_REFERENCE__MODEL);
          if (_equals_3) {
            Resource _eResource_1 = context.eResource();
            return this.createImportsScope(_eResource_1);
          }
        }
      }
      _xblockexpression = super.getScope(context, reference);
    }
    return _xblockexpression;
  }
  
  public IScope createImportsScope(final Resource resource) {
    Iterable<MetamodelImport> _metamodelImports = this.getMetamodelImports(resource);
    Iterator<MetamodelImport> _iterator = _metamodelImports.iterator();
    final Function<MetamodelImport, IEObjectDescription> _function = (MetamodelImport it) -> {
      String _name = it.getName();
      return EObjectDescription.create(_name, it);
    };
    return this.<MetamodelImport>createScope(IScope.NULLSCOPE, _iterator, _function);
  }
  
  public boolean hasQualifiedName(final EObject eObject) {
    IQualifiedNameProvider _qualifiedNameProvider = this.getQualifiedNameProvider();
    final QualifiedName qn = _qualifiedNameProvider.getFullyQualifiedName(eObject);
    boolean _and = false;
    boolean _notEquals = (!Objects.equal(qn, null));
    if (!_notEquals) {
      _and = false;
    } else {
      boolean _isEmpty = qn.isEmpty();
      boolean _not = (!_isEmpty);
      _and = _not;
    }
    return _and;
  }
  
  public IScope createEStructuralFeatureScope(final EClass eClass) {
    IScope _xifexpression = null;
    boolean _notEquals = (!Objects.equal(eClass, null));
    if (_notEquals) {
      EList<EStructuralFeature> _eAllStructuralFeatures = eClass.getEAllStructuralFeatures();
      Iterator<EStructuralFeature> _iterator = _eAllStructuralFeatures.iterator();
      final Function<EStructuralFeature, IEObjectDescription> _function = (EStructuralFeature it) -> {
        String _name = it.getName();
        return EObjectDescription.create(_name, it);
      };
      _xifexpression = this.<EStructuralFeature>createScope(IScope.NULLSCOPE, _iterator, _function);
    } else {
      return IScope.NULLSCOPE;
    }
    return _xifexpression;
  }
  
  /**
   * Returns all elements with the given EClass inside the Resource res.
   */
  public Iterable<EObject> getAllContentsOfEClass(final Resource res, final EClass namedParent, final boolean allContents) {
    List<EObject> _xifexpression = null;
    if (allContents) {
      TreeIterator<EObject> _allContents = res.getAllContents();
      _xifexpression = IteratorExtensions.<EObject>toList(_allContents);
    } else {
      _xifexpression = res.getContents();
    }
    List<EObject> contents = _xifexpression;
    final Function1<EObject, Boolean> _function = (EObject it) -> {
      EClass _eClass = it.eClass();
      return Boolean.valueOf(_eClass.equals(namedParent));
    };
    return IterableExtensions.<EObject>filter(contents, _function);
  }
  
  /**
   * Returns all packages that have been imported by import statements
   * in the given resource.
   */
  public Iterable<MetamodelImport> getMetamodelImports(final Resource res) {
    EClass _metamodelImport = MirBasePackage.eINSTANCE.getMetamodelImport();
    Iterable<EObject> _allContentsOfEClass = this.getAllContentsOfEClass(res, _metamodelImport, true);
    List<EObject> contents = IterableExtensions.<EObject>toList(_allContentsOfEClass);
    Iterable<MetamodelImport> _filter = Iterables.<MetamodelImport>filter(contents, MetamodelImport.class);
    final Function1<MetamodelImport, Boolean> _function = (MetamodelImport it) -> {
      EPackage _package = it.getPackage();
      return Boolean.valueOf((!Objects.equal(_package, null)));
    };
    Iterable<MetamodelImport> _filter_1 = IterableExtensions.<MetamodelImport>filter(_filter, _function);
    final Function1<MetamodelImport, MetamodelImport> _function_1 = (MetamodelImport it) -> {
      MetamodelImport _xblockexpression = null;
      {
        String _elvis = null;
        String _name = it.getName();
        if (_name != null) {
          _elvis = _name;
        } else {
          EPackage _package = it.getPackage();
          String _name_1 = _package.getName();
          _elvis = _name_1;
        }
        it.setName(_elvis);
        _xblockexpression = it;
      }
      return _xblockexpression;
    };
    final Iterable<MetamodelImport> validImports = IterableExtensions.<MetamodelImport, MetamodelImport>map(_filter_1, _function_1);
    return validImports;
  }
  
  /**
   * Create an {@link IScope} that represents all {@link EClass}es
   * that are referencable inside the {@link Resource} via {@link Import}s
   * by a fully qualified name.
   * 
   * @see MIRScopeProviderDelegate#createQualifiedEClassifierScope(Resource)
   */
  public SimpleScope createQualifiedEClassScope(final Resource res) {
    Iterable<MetamodelImport> _metamodelImports = this.getMetamodelImports(res);
    final Function1<MetamodelImport, Iterable<IEObjectDescription>> _function = (MetamodelImport import_) -> {
      EPackage _package = import_.getPackage();
      String _name = import_.getName();
      return this.collectObjectDescriptions(_package, true, true, false, _name);
    };
    Iterable<Iterable<IEObjectDescription>> _map = IterableExtensions.<MetamodelImport, Iterable<IEObjectDescription>>map(_metamodelImports, _function);
    final Iterable<IEObjectDescription> classifierDescriptions = Iterables.<IEObjectDescription>concat(_map);
    SimpleScope resultScope = new SimpleScope(IScope.NULLSCOPE, classifierDescriptions);
    return resultScope;
  }
  
  private Iterable<IEObjectDescription> collectObjectDescriptions(final EPackage pckg, final boolean includeSubpackages, final boolean includeAbstract, final boolean useSimpleNames, final String packagePrefix) {
    Iterable<EClass> classes = this.collectEClasses(pckg, includeSubpackages);
    final Function1<EClass, Boolean> _function = (EClass it) -> {
      boolean _or = false;
      if (includeAbstract) {
        _or = true;
      } else {
        boolean _isAbstract = it.isAbstract();
        boolean _not = (!_isAbstract);
        _or = _not;
      }
      return Boolean.valueOf(_or);
    };
    Iterable<EClass> _filter = IterableExtensions.<EClass>filter(classes, _function);
    final Function1<EClass, IEObjectDescription> _function_1 = (EClass it) -> {
      return this.createEObjectDescription(it, useSimpleNames, packagePrefix);
    };
    final Iterable<IEObjectDescription> result = IterableExtensions.<EClass, IEObjectDescription>map(_filter, _function_1);
    return result;
  }
  
  private Iterable<EClass> collectEClasses(final EPackage pckg, final boolean includeSubpackages) {
    ArrayList<EClass> recursiveResult = CollectionLiterals.<EClass>newArrayList();
    if (includeSubpackages) {
      EList<EPackage> _eSubpackages = pckg.getESubpackages();
      final Function1<EPackage, Iterable<EClass>> _function = (EPackage it) -> {
        return this.collectEClasses(it, includeSubpackages);
      };
      List<Iterable<EClass>> _map = ListExtensions.<EPackage, Iterable<EClass>>map(_eSubpackages, _function);
      Iterable<EClass> _flatten = Iterables.<EClass>concat(_map);
      Iterables.<EClass>addAll(recursiveResult, _flatten);
    }
    EList<EClassifier> _eClassifiers = pckg.getEClassifiers();
    final Iterable<EClass> result = Iterables.<EClass>filter(_eClassifiers, EClass.class);
    return Iterables.<EClass>concat(recursiveResult, result);
  }
  
  /**
   * Creates and returns a {@link EObjectDescription} with simple name
   * or in case of a qualified name with the given package prefix.
   */
  private IEObjectDescription createEObjectDescription(final EClassifier classifier, final boolean useSimpleName, final String packagePrefix) {
    if (useSimpleName) {
      String _name = classifier.getName();
      return EObjectDescription.create(_name, classifier);
    } else {
      IQualifiedNameProvider _qualifiedNameProvider = this.getQualifiedNameProvider();
      QualifiedName _fullyQualifiedName = _qualifiedNameProvider.getFullyQualifiedName(classifier);
      QualifiedName qualifiedName = _fullyQualifiedName.skipFirst(1);
      boolean _notEquals = (!Objects.equal(packagePrefix, null));
      if (_notEquals) {
        QualifiedName _create = QualifiedName.create(packagePrefix);
        QualifiedName _append = _create.append(qualifiedName);
        qualifiedName = _append;
      }
      return EObjectDescription.create(qualifiedName, classifier);
    }
  }
}
