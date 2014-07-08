package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java;

import com.google.common.collect.Sets;
import de.uka.ipd.sdq.pcm.repository.OperationInterface;
import de.uka.ipd.sdq.pcm.repository.Repository;
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.EObjectMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.JaMoPPPCMNamespace;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EObjectCorrespondence;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.emftext.language.java.classifiers.ClassifiersFactory;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.commons.NamedElement;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.ContainersFactory;

@SuppressWarnings("all")
public class OperationInterfaceMappingTransformation extends EObjectMappingTransformation {
  public Class<?> getClassOfMappedEObject() {
    return OperationInterface.class;
  }
  
  public void setCorrespondenceForFeatures() {
    OperationInterface _createOperationInterface = RepositoryFactory.eINSTANCE.createOperationInterface();
    EClass _eClass = _createOperationInterface.eClass();
    EList<EAttribute> _eAllAttributes = _eClass.getEAllAttributes();
    final Function1<EAttribute, Boolean> _function = new Function1<EAttribute, Boolean>() {
      public Boolean apply(final EAttribute attribute) {
        String _name = attribute.getName();
        return Boolean.valueOf(_name.equalsIgnoreCase(JaMoPPPCMNamespace.PCM_ATTRIBUTE_ENTITY_NAME));
      }
    };
    Iterable<EAttribute> _filter = IterableExtensions.<EAttribute>filter(_eAllAttributes, _function);
    Iterator<EAttribute> _iterator = _filter.iterator();
    EAttribute opInterfaceNameAttribute = _iterator.next();
    Interface _createInterface = ClassifiersFactory.eINSTANCE.createInterface();
    EClass _eClass_1 = _createInterface.eClass();
    EList<EAttribute> _eAllAttributes_1 = _eClass_1.getEAllAttributes();
    final Function1<EAttribute, Boolean> _function_1 = new Function1<EAttribute, Boolean>() {
      public Boolean apply(final EAttribute attribute) {
        String _name = attribute.getName();
        return Boolean.valueOf(_name.equalsIgnoreCase(JaMoPPPCMNamespace.JAMOPP_ATTRIBUTE_NAME));
      }
    };
    Iterable<EAttribute> _filter_1 = IterableExtensions.<EAttribute>filter(_eAllAttributes_1, _function_1);
    Iterator<EAttribute> _iterator_1 = _filter_1.iterator();
    EAttribute interfaceNameAttribute = _iterator_1.next();
    this.featureCorrespondenceMap.put(opInterfaceNameAttribute, interfaceNameAttribute);
  }
  
  public EObject[] addEObject(final EObject eObject) {
    final OperationInterface operationInterface = ((OperationInterface) eObject);
    Interface correspondingInterface = ClassifiersFactory.eINSTANCE.createInterface();
    String _entityName = operationInterface.getEntityName();
    correspondingInterface.setName(_entityName);
    CompilationUnit correspondingCompilationUnit = ContainersFactory.eINSTANCE.createCompilationUnit();
    String _entityName_1 = operationInterface.getEntityName();
    String _plus = (_entityName_1 + ".java");
    correspondingCompilationUnit.setName(_plus);
    EList<ConcreteClassifier> _classifiers = correspondingCompilationUnit.getClassifiers();
    _classifiers.add(correspondingInterface);
    Repository _repository__Interface = operationInterface.getRepository__Interface();
    final org.emftext.language.java.containers.Package containingPackage = this.correspondenceInstance.<org.emftext.language.java.containers.Package>claimUniqueCorrespondingEObjectByType(_repository__Interface, org.emftext.language.java.containers.Package.class);
    EList<CompilationUnit> _compilationUnits = containingPackage.getCompilationUnits();
    _compilationUnits.add(correspondingCompilationUnit);
    EList<String> _namespaces = correspondingCompilationUnit.getNamespaces();
    EList<String> _namespaces_1 = containingPackage.getNamespaces();
    _namespaces.addAll(_namespaces_1);
    Repository _repository__Interface_1 = operationInterface.getRepository__Interface();
    final Correspondence parrentCorrespondence = this.correspondenceInstance.claimUniqueOrNullCorrespondenceForEObject(_repository__Interface_1);
    final EObjectCorrespondence eObjectCorrespondence = CorrespondenceFactory.eINSTANCE.createEObjectCorrespondence();
    eObjectCorrespondence.setElementA(operationInterface);
    eObjectCorrespondence.setElementB(correspondingInterface);
    eObjectCorrespondence.setParent(parrentCorrespondence);
    this.correspondenceInstance.addSameTypeCorrespondence(eObjectCorrespondence);
    final EObjectCorrespondence eObjectCorrespondence4CompilationUnit = CorrespondenceFactory.eINSTANCE.createEObjectCorrespondence();
    eObjectCorrespondence4CompilationUnit.setElementA(operationInterface);
    eObjectCorrespondence4CompilationUnit.setElementB(correspondingCompilationUnit);
    eObjectCorrespondence.setParent(parrentCorrespondence);
    this.correspondenceInstance.addSameTypeCorrespondence(eObjectCorrespondence4CompilationUnit);
    return this.toArray(correspondingCompilationUnit);
  }
  
  public EObject[] removeEObject(final EObject eObject) {
    final OperationInterface operationInterface = ((OperationInterface) eObject);
    final Set<EObject> correspondingObjects = this.correspondenceInstance.claimCorrespondingEObjects(operationInterface);
    for (final EObject correspondingObject : correspondingObjects) {
      EcoreUtil.remove(correspondingObject);
    }
    this.correspondenceInstance.removeAllCorrespondences(operationInterface);
    return null;
  }
  
  public EObject[] updateEAttribute(final EObject eObject, final EAttribute affectedAttribute, final Object newValue) {
    final OperationInterface operationInterface = ((OperationInterface) eObject);
    final EStructuralFeature affectedInterfaceFeature = this.featureCorrespondenceMap.claimValueForKey(affectedAttribute);
    final CompilationUnit jaMoPPInterfaceCompilationUnit = this.correspondenceInstance.<CompilationUnit>claimUniqueCorrespondingEObjectByType(operationInterface, CompilationUnit.class);
    String _plus = (newValue + ".java");
    jaMoPPInterfaceCompilationUnit.eSet(affectedInterfaceFeature, _plus);
    final Interface jaMoPPInterface = this.correspondenceInstance.<Interface>claimUniqueCorrespondingEObjectByType(operationInterface, Interface.class);
    EStructuralFeature structuralFeature = super.featureCorrespondenceMap.get(affectedAttribute);
    jaMoPPInterface.eSet(structuralFeature, newValue);
    jaMoPPInterfaceCompilationUnit.eSet(structuralFeature, newValue);
    return ((EObject[])Conversions.unwrapArray(Collections.<NamedElement>unmodifiableSet(Sets.<NamedElement>newHashSet(jaMoPPInterface, jaMoPPInterfaceCompilationUnit)), EObject.class));
  }
  
  public EObject[] updateEReference(final EObject eObject, final EReference affectedEReference, final Object newValue) {
    final OperationInterface operationInterface = ((OperationInterface) eObject);
    return null;
  }
  
  public EObject[] updateEContainmentReference(final EObject eObject, final EReference afffectedEReference, final Object newValue) {
    return null;
  }
}
