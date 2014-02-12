package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations;

import de.uka.ipd.sdq.pcm.repository.OperationInterface;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.EObjectMappingTransformation;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.emftext.language.java.classifiers.ClassifiersFactory;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.ContainersFactory;

@SuppressWarnings("all")
public class OperationInterfaceMappingTransformation extends EObjectMappingTransformation {
  public Class<? extends Object> getClassOfMappedEObject() {
    return OperationInterface.class;
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
    org.emftext.language.java.containers.Package containingPackage = null;
    EList<CompilationUnit> _compilationUnits = containingPackage.getCompilationUnits();
    _compilationUnits.add(correspondingCompilationUnit);
    return null;
  }
  
  public EObject removeEObject(final EObject eObject) {
    final OperationInterface operationInterface = ((OperationInterface) eObject);
    return null;
  }
  
  public EObject updateEAttribute(final EObject eObject, final EAttribute affectedAttribute, final Object newValue) {
    final OperationInterface operationInterface = ((OperationInterface) eObject);
    return null;
  }
  
  public EObject updateEReference(final EObject eObject, final EReference affectedEReference, final EObject newValue) {
    final OperationInterface operationInterface = ((OperationInterface) eObject);
    return null;
  }
  
  public EObject updateEContainmentReference(final EObject eObject, final EReference afffectedEReference, final EObject newValue) {
    return null;
  }
}
