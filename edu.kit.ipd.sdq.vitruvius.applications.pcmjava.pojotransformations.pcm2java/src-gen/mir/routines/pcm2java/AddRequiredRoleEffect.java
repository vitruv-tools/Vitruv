package mir.routines.pcm2java;

import com.google.common.collect.Iterables;
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.pojotransformations.pcm2java.Pcm2JavaHelper;
import edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.imports.ClassifierImport;
import org.emftext.language.java.imports.impl.ImportsFactoryImpl;
import org.emftext.language.java.members.Constructor;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.members.impl.MembersFactoryImpl;
import org.emftext.language.java.types.NamespaceClassifierReference;
import org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;

@SuppressWarnings("all")
public class AddRequiredRoleEffect extends AbstractEffectRealization {
  public AddRequiredRoleEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final OperationRequiredRole requiredRole) {
    super(responseExecutionState, calledBy);
    				this.requiredRole = requiredRole;
  }
  
  private OperationRequiredRole requiredRole;
  
  private EObject getCorrepondenceSourceRequiredInterface(final OperationRequiredRole requiredRole) {
    OperationInterface _requiredInterface__OperationRequiredRole = requiredRole.getRequiredInterface__OperationRequiredRole();
    return _requiredInterface__OperationRequiredRole;
  }
  
  private EObject getElement0(final OperationRequiredRole requiredRole, final Interface requiredInterface, final org.emftext.language.java.classifiers.Class javaClass, final ClassifierImport requiredInterfaceImport, final Field requiredInterfaceField) {
    return requiredInterfaceImport;
  }
  
  private EObject getElement1(final OperationRequiredRole requiredRole, final Interface requiredInterface, final org.emftext.language.java.classifiers.Class javaClass, final ClassifierImport requiredInterfaceImport, final Field requiredInterfaceField) {
    return requiredInterfaceField;
  }
  
  private EObject getCorrepondenceSourceJavaClass(final OperationRequiredRole requiredRole) {
    InterfaceRequiringEntity _requiringEntity_RequiredRole = requiredRole.getRequiringEntity_RequiredRole();
    return _requiringEntity_RequiredRole;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine AddRequiredRoleEffect with input:");
    getLogger().debug("   OperationRequiredRole: " + this.requiredRole);
    
    Interface requiredInterface = getCorrespondingElement(
    	getCorrepondenceSourceRequiredInterface(requiredRole), // correspondence source supplier
    	Interface.class,
    	(Interface _element) -> true, // correspondence precondition checker
    	null);
    if (requiredInterface == null) {
    	return;
    }
    initializeRetrieveElementState(requiredInterface);
    org.emftext.language.java.classifiers.Class javaClass = getCorrespondingElement(
    	getCorrepondenceSourceJavaClass(requiredRole), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    if (javaClass == null) {
    	return;
    }
    initializeRetrieveElementState(javaClass);
    ClassifierImport requiredInterfaceImport = ImportsFactoryImpl.eINSTANCE.createClassifierImport();
    initializeCreateElementState(requiredInterfaceImport);
    Field requiredInterfaceField = MembersFactoryImpl.eINSTANCE.createField();
    initializeCreateElementState(requiredInterfaceField);
    
    addCorrespondenceBetween(getElement0(requiredRole, requiredInterface, javaClass, requiredInterfaceImport, requiredInterfaceField), getElement2(requiredRole, requiredInterface, javaClass, requiredInterfaceImport, requiredInterfaceField), "");
    addCorrespondenceBetween(getElement1(requiredRole, requiredInterface, javaClass, requiredInterfaceImport, requiredInterfaceField), getElement3(requiredRole, requiredInterface, javaClass, requiredInterfaceImport, requiredInterfaceField), "");
    preprocessElementStates();
    new mir.routines.pcm2java.AddRequiredRoleEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	requiredRole, requiredInterface, javaClass, requiredInterfaceImport, requiredInterfaceField);
    postprocessElementStates();
  }
  
  private EObject getElement2(final OperationRequiredRole requiredRole, final Interface requiredInterface, final org.emftext.language.java.classifiers.Class javaClass, final ClassifierImport requiredInterfaceImport, final Field requiredInterfaceField) {
    return requiredRole;
  }
  
  private EObject getElement3(final OperationRequiredRole requiredRole, final Interface requiredInterface, final org.emftext.language.java.classifiers.Class javaClass, final ClassifierImport requiredInterfaceImport, final Field requiredInterfaceField) {
    return requiredRole;
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final OperationRequiredRole requiredRole, final Interface requiredInterface, final org.emftext.language.java.classifiers.Class javaClass, final ClassifierImport requiredInterfaceImport, final Field requiredInterfaceField) {
      final NamespaceClassifierReference typeRef = Pcm2JavaHelper.createNamespaceClassifierReference(requiredInterface);
      Pcm2JavaHelper.addImportToCompilationUnitOfClassifier(requiredInterfaceImport, javaClass, requiredInterface);
      final String requiredRoleName = requiredRole.getEntityName();
      NamespaceClassifierReference _copy = EcoreUtil.<NamespaceClassifierReference>copy(typeRef);
      Pcm2JavaHelper.createPrivateField(requiredInterfaceField, _copy, requiredRoleName);
      EList<Member> _members = javaClass.getMembers();
      _members.add(requiredInterfaceField);
      EList<Member> _members_1 = javaClass.getMembers();
      Iterable<Constructor> _filter = Iterables.<Constructor>filter(_members_1, Constructor.class);
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(_filter);
      if (_isNullOrEmpty) {
        Pcm2JavaHelper.addConstructorToClass(javaClass);
      }
      EList<Member> _members_2 = javaClass.getMembers();
      Iterable<Constructor> _filter_1 = Iterables.<Constructor>filter(_members_2, Constructor.class);
      for (final Constructor ctor : _filter_1) {
        this.effectFacade.callAddParameterAndAssignmentToConstructor(requiredRole, ctor, typeRef, requiredInterfaceField, requiredRoleName);
      }
    }
  }
}
