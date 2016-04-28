package mir.routines.pcm2java;

import com.google.common.collect.Iterables;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.responses.pcm2java.Pcm2JavaHelper;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
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
  public AddRequiredRoleEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private OperationRequiredRole requiredRole;
  
  private boolean isRequiredRoleSet;
  
  public void setRequiredRole(final OperationRequiredRole requiredRole) {
    this.requiredRole = requiredRole;
    this.isRequiredRoleSet = true;
  }
  
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
  
  public boolean allParametersSet() {
    return isRequiredRoleSet;
  }
  
  private EObject getCorrepondenceSourceJavaClass(final OperationRequiredRole requiredRole) {
    InterfaceRequiringEntity _requiringEntity_RequiredRole = requiredRole.getRequiringEntity_RequiredRole();
    return _requiringEntity_RequiredRole;
  }
  
  private EObject getElement2(final OperationRequiredRole requiredRole, final Interface requiredInterface, final org.emftext.language.java.classifiers.Class javaClass, final ClassifierImport requiredInterfaceImport, final Field requiredInterfaceField) {
    return requiredRole;
  }
  
  private EObject getElement3(final OperationRequiredRole requiredRole, final Interface requiredInterface, final org.emftext.language.java.classifiers.Class javaClass, final ClassifierImport requiredInterfaceImport, final Field requiredInterfaceField) {
    return requiredRole;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called routine AddRequiredRoleEffect with input:");
    getLogger().debug("   OperationRequiredRole: " + this.requiredRole);
    
    Interface requiredInterface = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceRequiredInterface(requiredRole), // correspondence source supplier
    	(Interface _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	Interface.class,
    	false, true, false);
    org.emftext.language.java.classifiers.Class javaClass = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceJavaClass(requiredRole), // correspondence source supplier
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	org.emftext.language.java.classifiers.Class.class,
    	false, true, false);
    if (isAborted()) {
    	return;
    }
    ClassifierImport requiredInterfaceImport = ImportsFactoryImpl.eINSTANCE.createClassifierImport();
    initializeCreateElementState(requiredInterfaceImport);
    Field requiredInterfaceField = MembersFactoryImpl.eINSTANCE.createField();
    initializeCreateElementState(requiredInterfaceField);
    
    addCorrespondenceBetween(getElement0(requiredRole, requiredInterface, javaClass, requiredInterfaceImport, requiredInterfaceField), getElement2(requiredRole, requiredInterface, javaClass, requiredInterfaceImport, requiredInterfaceField), "");
    addCorrespondenceBetween(getElement1(requiredRole, requiredInterface, javaClass, requiredInterfaceImport, requiredInterfaceField), getElement3(requiredRole, requiredInterface, javaClass, requiredInterfaceImport, requiredInterfaceField), "");
    preProcessElements();
    new mir.routines.pcm2java.AddRequiredRoleEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	requiredRole, requiredInterface, javaClass, requiredInterfaceImport, requiredInterfaceField);
    postProcessElements();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new RoutinesFacade(responseExecutionState, calledBy);
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
