package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java;

import com.google.common.base.Objects;
import de.uka.ipd.sdq.pcm.repository.BasicComponent;
import de.uka.ipd.sdq.pcm.repository.Repository;
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.EObjectMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.JaMoPPPCMNamespace;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EObjectCorrespondence;
import java.util.Iterator;
import java.util.Set;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.xbase.lib.Conversions;
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
  private final static Logger logger = Logger.getLogger(BasicComponentMappingTransformation.class.getSimpleName());
  
  public Class<?> getClassOfMappedEObject() {
    return BasicComponent.class;
  }
  
  public EObject[] addEObject(final EObject eObject) {
    final BasicComponent basicComponent = ((BasicComponent) eObject);
    Repository _repository__RepositoryComponent = basicComponent.getRepository__RepositoryComponent();
    final org.emftext.language.java.containers.Package rootPackage = this.correspondenceInstance.<org.emftext.language.java.containers.Package>claimUniqueCorrespondingEObjectByType(_repository__RepositoryComponent, org.emftext.language.java.containers.Package.class);
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
    final Correspondence parentCorrespondence = this.correspondenceInstance.claimUniqueOrNullCorrespondenceForEObject(rootPackage);
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
    this.correspondenceInstance.addSameTypeCorrespondence(basicComponent2Package);
    this.correspondenceInstance.addSameTypeCorrespondence(basicComponent2CompilationUnit);
    this.correspondenceInstance.addSameTypeCorrespondence(basicComponent2Class);
    return this.toArray(jaMoPPCompilationUnit);
  }
  
  public EObject[] removeEObject(final EObject eObject) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
  
  public EObject[] updateEAttribute(final EObject eObject, final EAttribute affectedAttribute, final Object newValue) {
    boolean _containsKey = this.featureCorrespondenceMap.containsKey(affectedAttribute);
    boolean _not = (!_containsKey);
    if (_not) {
      BasicComponentMappingTransformation.logger.info(("no feature correspondence found for affected Attribute: " + affectedAttribute));
      return null;
    }
    Set<EObject> correspondingEObjects = this.correspondenceInstance.getAllCorrespondingEObjects(eObject);
    boolean _equals = Objects.equal(null, correspondingEObjects);
    if (_equals) {
      BasicComponentMappingTransformation.logger.info(("No corresponding objects found for " + eObject));
    }
    for (final EObject correspondingObject : correspondingEObjects) {
      EClass _eClass = correspondingObject.eClass();
      EStructuralFeature _get = this.featureCorrespondenceMap.get(affectedAttribute);
      _eClass.eSet(_get, newValue);
    }
    return ((EObject[])Conversions.unwrapArray(correspondingEObjects, EObject.class));
  }
  
  public EObject[] updateEReference(final EObject eObject, final EReference affectedEReference, final Object newValue) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
  
  public EObject[] updateEContainmentReference(final EObject eObject, final EReference afffectedEReference, final Object newValue) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
  
  public void setCorrespondenceForFeatures() {
    BasicComponent _createBasicComponent = RepositoryFactory.eINSTANCE.createBasicComponent();
    EClass _eClass = _createBasicComponent.eClass();
    EList<EAttribute> _eAllAttributes = _eClass.getEAllAttributes();
    final Function1<EAttribute, Boolean> _function = new Function1<EAttribute, Boolean>() {
      public Boolean apply(final EAttribute attribute) {
        String _name = attribute.getName();
        return Boolean.valueOf(_name.equalsIgnoreCase(JaMoPPPCMNamespace.PCM_ATTRIBUTE_ENTITY_NAME));
      }
    };
    Iterable<EAttribute> _filter = IterableExtensions.<EAttribute>filter(_eAllAttributes, _function);
    Iterator<EAttribute> _iterator = _filter.iterator();
    EAttribute basicComponentNameAttribute = _iterator.next();
    org.emftext.language.java.classifiers.Class _createClass = ClassifiersFactory.eINSTANCE.createClass();
    EClass _eClass_1 = _createClass.eClass();
    EList<EAttribute> _eAllAttributes_1 = _eClass_1.getEAllAttributes();
    final Function1<EAttribute, Boolean> _function_1 = new Function1<EAttribute, Boolean>() {
      public Boolean apply(final EAttribute attribute) {
        String _name = attribute.getName();
        return Boolean.valueOf(_name.equalsIgnoreCase(JaMoPPPCMNamespace.JAMOPP_ATTRIBUTE_NAME));
      }
    };
    Iterable<EAttribute> _filter_1 = IterableExtensions.<EAttribute>filter(_eAllAttributes_1, _function_1);
    Iterator<EAttribute> _iterator_1 = _filter_1.iterator();
    EAttribute classNameAttribute = _iterator_1.next();
    org.emftext.language.java.containers.Package _createPackage = ContainersFactory.eINSTANCE.createPackage();
    EClass _eClass_2 = _createPackage.eClass();
    EList<EAttribute> _eAllAttributes_2 = _eClass_2.getEAllAttributes();
    final Function1<EAttribute, Boolean> _function_2 = new Function1<EAttribute, Boolean>() {
      public Boolean apply(final EAttribute attribute) {
        String _name = attribute.getName();
        return Boolean.valueOf(_name.equalsIgnoreCase(JaMoPPPCMNamespace.JAMOPP_ATTRIBUTE_NAME));
      }
    };
    Iterable<EAttribute> _filter_2 = IterableExtensions.<EAttribute>filter(_eAllAttributes_2, _function_2);
    Iterator<EAttribute> _iterator_2 = _filter_2.iterator();
    EAttribute packageNameAttribute = _iterator_2.next();
    this.featureCorrespondenceMap.put(basicComponentNameAttribute, classNameAttribute);
    this.featureCorrespondenceMap.put(basicComponentNameAttribute, packageNameAttribute);
  }
}
