package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.pcm2java;

import de.uka.ipd.sdq.pcm.repository.OperationInterface;
import de.uka.ipd.sdq.pcm.repository.Repository;
import de.uka.ipd.sdq.pcm.repository.RepositoryFactory;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.EObjectMappingTransformation;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.Correspondence;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.CorrespondenceFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.EObjectCorrespondence;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.emftext.language.java.JavaPackage;
import org.emftext.language.java.classifiers.ClassifiersFactory;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.ContainersFactory;
import org.emftext.language.refactoring.rolemapping.RoleMapping;
import org.emftext.refactoring.interpreter.IRefactorer;
import org.emftext.refactoring.interpreter.RefactorerFactory;
import org.emftext.refactoring.registry.rolemapping.IRoleMappingRegistry;
import org.modelrefactoring.jamopp.test.valueprovider.TestValueProviderFactory;

@SuppressWarnings("all")
public class OperationInterfaceMappingTransformation extends EObjectMappingTransformation {
  public Class<? extends Object> getClassOfMappedEObject() {
    return OperationInterface.class;
  }
  
  public void setCorrespondenceForFeatures() {
    OperationInterface _createOperationInterface = RepositoryFactory.eINSTANCE.createOperationInterface();
    EClass _eClass = _createOperationInterface.eClass();
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
    EAttribute opInterfaceNameAttribute = _iterator.next();
    Interface _createInterface = ClassifiersFactory.eINSTANCE.createInterface();
    EClass _eClass_1 = _createInterface.eClass();
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
    EAttribute interfaceNameAttribute = _iterator_1.next();
    this.featureCorrespondenceMap.put(opInterfaceNameAttribute, interfaceNameAttribute);
  }
  
  public EObject addEObject(final EObject eObject) {
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
    final org.emftext.language.java.containers.Package containingPackage = this.correspondenceInstance.<org.emftext.language.java.containers.Package>claimCorrespondingEObjectByTypeIfUnique(_repository__Interface, org.emftext.language.java.containers.Package.class);
    EList<CompilationUnit> _compilationUnits = containingPackage.getCompilationUnits();
    _compilationUnits.add(correspondingCompilationUnit);
    EList<String> _namespaces = correspondingCompilationUnit.getNamespaces();
    EList<String> _namespaces_1 = containingPackage.getNamespaces();
    _namespaces.addAll(_namespaces_1);
    Repository _repository__Interface_1 = operationInterface.getRepository__Interface();
    final Correspondence parrentCorrespondence = this.correspondenceInstance.getCorrespondeceForEObjectIfUnique(_repository__Interface_1);
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
    return correspondingCompilationUnit;
  }
  
  public EObject removeEObject(final EObject eObject) {
    final OperationInterface operationInterface = ((OperationInterface) eObject);
    final Set<EObject> correspondingObjects = this.correspondenceInstance.claimCorrespondingEObjects(operationInterface);
    for (final EObject correspondingObject : correspondingObjects) {
      EcoreUtil.remove(correspondingObject);
    }
    this.correspondenceInstance.removeAllCorrespondingInstances(operationInterface);
    return null;
  }
  
  public EObject updateEAttribute(final EObject eObject, final EAttribute affectedAttribute, final Object newValue) {
    try {
      final OperationInterface operationInterface = ((OperationInterface) eObject);
      final EStructuralFeature affectedInterfaceFeature = this.featureCorrespondenceMap.claimValueForKey(affectedAttribute);
      final CompilationUnit jaMoPPInterfaceCompilationUnit = this.correspondenceInstance.<CompilationUnit>claimCorrespondingEObjectByTypeIfUnique(operationInterface, CompilationUnit.class);
      String _plus = (newValue + ".java");
      jaMoPPInterfaceCompilationUnit.eSet(affectedInterfaceFeature, _plus);
      final Interface jaMoPPInterface = this.correspondenceInstance.<Interface>claimCorrespondingEObjectByTypeIfUnique(operationInterface, Interface.class);
      final Map<String,RoleMapping> roleMappings = IRoleMappingRegistry.INSTANCE.getRoleMappingsForUri(JavaPackage.eNS_URI);
      jaMoPPInterface.eSet(affectedInterfaceFeature, newValue);
      Resource _eResource = jaMoPPInterface.eResource();
      RoleMapping _get = roleMappings.get("RenameElement");
      final IRefactorer refactorer = RefactorerFactory.eINSTANCE.getRefactorer(_eResource, _get);
      TestValueProviderFactory _testValueProviderFactory = new TestValueProviderFactory();
      final TestValueProviderFactory jaMoPPValueProviderFactory = _testValueProviderFactory;
      String _string = newValue.toString();
      jaMoPPValueProviderFactory.setNewMethodName(_string);
      BasicEList<EObject> _basicEList = new BasicEList<EObject>();
      final BasicEList<EObject> elementToRefactor = _basicEList;
      elementToRefactor.add(jaMoPPInterface);
      refactorer.setValueProviderFactory(jaMoPPValueProviderFactory);
      refactorer.setInput(elementToRefactor);
      final EObject refactoredEObj = refactorer.refactor();
      List<Resource> _resourcesToSave = refactorer.getResourcesToSave();
      for (final Resource resourceToSave : _resourcesToSave) {
        resourceToSave.save(null);
      }
      return refactoredEObj;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public EObject updateEReference(final EObject eObject, final EReference affectedEReference, final Object newValue) {
    final OperationInterface operationInterface = ((OperationInterface) eObject);
    return null;
  }
  
  public EObject updateEContainmentReference(final EObject eObject, final EReference afffectedEReference, final Object newValue) {
    return null;
  }
}
