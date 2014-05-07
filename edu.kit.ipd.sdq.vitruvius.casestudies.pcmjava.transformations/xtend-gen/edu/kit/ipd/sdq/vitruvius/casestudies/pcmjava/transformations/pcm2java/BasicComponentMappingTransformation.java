package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java;

import de.uka.ipd.sdq.pcm.repository.BasicComponent;
import de.uka.ipd.sdq.pcm.repository.Repository;
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.EObjectMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EObjectCorrespondence;
import java.util.Iterator;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.emftext.language.java.classifiers.ClassifiersFactory;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.ContainersFactory;
import org.emftext.language.java.modifiers.ModifiersFactory;
import org.emftext.language.java.modifiers.Public;

@SuppressWarnings("all")
public class BasicComponentMappingTransformation extends EObjectMappingTransformation {
  public Class<? extends Object> getClassOfMappedEObject() {
    return BasicComponent.class;
  }
  
  public EObject addEObject(final EObject eObject) {
    final BasicComponent basicComponent = ((BasicComponent) eObject);
    Repository _repository__RepositoryComponent = basicComponent.getRepository__RepositoryComponent();
    final org.emftext.language.java.containers.Package rootPackage = this.correspondenceInstance.<org.emftext.language.java.containers.Package>claimCorrespondingEObjectByTypeIfUnique(_repository__RepositoryComponent, org.emftext.language.java.containers.Package.class);
    final org.emftext.language.java.containers.Package jaMoPPPackage = ContainersFactory.eINSTANCE.createPackage();
    String _entityName = basicComponent.getEntityName();
    jaMoPPPackage.setName(_entityName);
    EList<String> _namespaces = jaMoPPPackage.getNamespaces();
    EList<String> _namespaces_1 = rootPackage.getNamespaces();
    _namespaces.addAll(_namespaces_1);
    EList<String> _namespaces_2 = jaMoPPPackage.getNamespaces();
    String _name = jaMoPPPackage.getName();
    _namespaces_2.add(_name);
    final org.emftext.language.java.classifiers.Class jaMoPPClass = ClassifiersFactory.eINSTANCE.createClass();
    String _entityName_1 = basicComponent.getEntityName();
    String _plus = (_entityName_1 + "Impl");
    jaMoPPClass.setName(_plus);
    Public _createPublic = ModifiersFactory.eINSTANCE.createPublic();
    jaMoPPClass.addModifier(_createPublic);
    final CompilationUnit jaMoPPCompilationUnit = ContainersFactory.eINSTANCE.createCompilationUnit();
    String _name_1 = jaMoPPClass.getName();
    String _plus_1 = (_name_1 + ".java");
    jaMoPPCompilationUnit.setName(_plus_1);
    EList<ConcreteClassifier> _classifiers = jaMoPPCompilationUnit.getClassifiers();
    _classifiers.add(jaMoPPClass);
    EList<CompilationUnit> _compilationUnits = jaMoPPPackage.getCompilationUnits();
    _compilationUnits.add(jaMoPPCompilationUnit);
    EList<String> _namespaces_3 = jaMoPPCompilationUnit.getNamespaces();
    EList<String> _namespaces_4 = jaMoPPPackage.getNamespaces();
    _namespaces_3.addAll(_namespaces_4);
    final Correspondence parentCorrespondence = this.correspondenceInstance.getCorrespondeceForEObjectIfUnique(rootPackage);
    final EObjectCorrespondence basicComponent2Package = CorrespondenceFactory.eINSTANCE.createEObjectCorrespondence();
    basicComponent2Package.setElementA(basicComponent);
    basicComponent2Package.setElementB(jaMoPPPackage);
    basicComponent2Package.setParent(parentCorrespondence);
    final EObjectCorrespondence basicComponent2Class = CorrespondenceFactory.eINSTANCE.createEObjectCorrespondence();
    basicComponent2Class.setElementA(basicComponent);
    basicComponent2Class.setElementB(jaMoPPClass);
    basicComponent2Class.setParent(parentCorrespondence);
    final EObjectCorrespondence basicComponent2CompilationUnit = CorrespondenceFactory.eINSTANCE.createEObjectCorrespondence();
    basicComponent2CompilationUnit.setElementA(basicComponent);
    basicComponent2CompilationUnit.setElementB(jaMoPPCompilationUnit);
    basicComponent2CompilationUnit.setParent(parentCorrespondence);
    return jaMoPPCompilationUnit;
  }
  
  public EObject removeEObject(final EObject eObject) {
    UnsupportedOperationException _unsupportedOperationException = new UnsupportedOperationException("TODO: auto-generated method stub");
    throw _unsupportedOperationException;
  }
  
  public EObject updateEAttribute(final EObject eObject, final EAttribute affectedAttribute, final Object newValue) {
    UnsupportedOperationException _unsupportedOperationException = new UnsupportedOperationException("TODO: auto-generated method stub");
    throw _unsupportedOperationException;
  }
  
  public EObject updateEReference(final EObject eObject, final EReference affectedEReference, final Object newValue) {
    UnsupportedOperationException _unsupportedOperationException = new UnsupportedOperationException("TODO: auto-generated method stub");
    throw _unsupportedOperationException;
  }
  
  public EObject updateEContainmentReference(final EObject eObject, final EReference afffectedEReference, final Object newValue) {
    UnsupportedOperationException _unsupportedOperationException = new UnsupportedOperationException("TODO: auto-generated method stub");
    throw _unsupportedOperationException;
  }
  
  public void setCorrespondenceForFeatures() {
    BasicComponent _createBasicComponent = RepositoryFactory.eINSTANCE.createBasicComponent();
    EClass _eClass = _createBasicComponent.eClass();
    EList<EAttribute> _eAllAttributes = _eClass.getEAllAttributes();
    final Function1<EAttribute,Boolean> _function = new Function1<EAttribute,Boolean>() {
      public Boolean apply(final EAttribute attribute) {
        String _name = attribute.getName();
        boolean _equalsIgnoreCase = _name.equalsIgnoreCase("entityName");
        return Boolean.valueOf(_equalsIgnoreCase);
      }
    };
    Iterable<EAttribute> _filter = IterableExtensions.<EAttribute>filter(_eAllAttributes, _function);
    Iterator<EAttribute> _iterator = _filter.iterator();
    EAttribute basicComponentNameAttribute = _iterator.next();
    org.emftext.language.java.classifiers.Class _createClass = ClassifiersFactory.eINSTANCE.createClass();
    EClass _eClass_1 = _createClass.eClass();
    EList<EAttribute> _eAllAttributes_1 = _eClass_1.getEAllAttributes();
    final Function1<EAttribute,Boolean> _function_1 = new Function1<EAttribute,Boolean>() {
      public Boolean apply(final EAttribute attribute) {
        String _name = attribute.getName();
        boolean _equalsIgnoreCase = _name.equalsIgnoreCase("name");
        return Boolean.valueOf(_equalsIgnoreCase);
      }
    };
    Iterable<EAttribute> _filter_1 = IterableExtensions.<EAttribute>filter(_eAllAttributes_1, _function_1);
    Iterator<EAttribute> _iterator_1 = _filter_1.iterator();
    EAttribute classNameAttribute = _iterator_1.next();
    org.emftext.language.java.containers.Package _createPackage = ContainersFactory.eINSTANCE.createPackage();
    EClass _eClass_2 = _createPackage.eClass();
    EList<EAttribute> _eAllAttributes_2 = _eClass_2.getEAllAttributes();
    final Function1<EAttribute,Boolean> _function_2 = new Function1<EAttribute,Boolean>() {
      public Boolean apply(final EAttribute attribute) {
        String _name = attribute.getName();
        boolean _equalsIgnoreCase = _name.equalsIgnoreCase("name");
        return Boolean.valueOf(_equalsIgnoreCase);
      }
    };
    Iterable<EAttribute> _filter_2 = IterableExtensions.<EAttribute>filter(_eAllAttributes_2, _function_2);
    Iterator<EAttribute> _iterator_2 = _filter_2.iterator();
    EAttribute packageNameAttribute = _iterator_2.next();
    this.featureCorrespondenceMap.put(basicComponentNameAttribute, classNameAttribute);
    this.featureCorrespondenceMap.put(basicComponentNameAttribute, packageNameAttribute);
  }
}
