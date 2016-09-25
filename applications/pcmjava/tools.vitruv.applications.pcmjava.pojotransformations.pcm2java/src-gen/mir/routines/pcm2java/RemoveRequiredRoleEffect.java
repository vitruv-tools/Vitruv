package mir.routines.pcm2java;

import com.google.common.collect.Iterables;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.imports.ClassifierImport;
import org.emftext.language.java.members.Constructor;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.Member;
import org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity;
import org.palladiosimulator.pcm.repository.RequiredRole;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RemoveRequiredRoleEffect extends AbstractEffectRealization {
  private RoutinesFacade effectFacade;
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getCorrepondenceSourceRequiredInterfaceField(final RequiredRole requiredRole, final InterfaceRequiringEntity requiringEntity, final ClassifierImport requiredInterfaceImport) {
      return requiredRole;
    }
    
    public EObject getCorrepondenceSourceRequiredInterfaceImport(final RequiredRole requiredRole, final InterfaceRequiringEntity requiringEntity) {
      return requiredRole;
    }
    
    public EObject getElement1(final RequiredRole requiredRole, final InterfaceRequiringEntity requiringEntity, final ClassifierImport requiredInterfaceImport, final Field requiredInterfaceField, final org.emftext.language.java.classifiers.Class javaClass) {
      return requiredInterfaceImport;
    }
    
    public EObject getCorrepondenceSourceJavaClass(final RequiredRole requiredRole, final InterfaceRequiringEntity requiringEntity, final ClassifierImport requiredInterfaceImport, final Field requiredInterfaceField) {
      return requiringEntity;
    }
    
    public EObject getElement2(final RequiredRole requiredRole, final InterfaceRequiringEntity requiringEntity, final ClassifierImport requiredInterfaceImport, final Field requiredInterfaceField, final org.emftext.language.java.classifiers.Class javaClass) {
      return requiredInterfaceField;
    }
    
    public void callRoutine1(final RequiredRole requiredRole, final InterfaceRequiringEntity requiringEntity, final ClassifierImport requiredInterfaceImport, final Field requiredInterfaceField, final org.emftext.language.java.classifiers.Class javaClass, @Extension final RoutinesFacade _routinesFacade) {
      EList<Member> _members = javaClass.getMembers();
      Iterable<Constructor> _filter = Iterables.<Constructor>filter(_members, Constructor.class);
      for (final Constructor ctor : _filter) {
        {
          String _entityName = requiredRole.getEntityName();
          _routinesFacade.removeParameterToFieldAssignmentFromConstructor(ctor, _entityName);
          _routinesFacade.removeCorrespondingParameterFromConstructor(ctor, requiredRole);
        }
      }
    }
  }
  
  private RemoveRequiredRoleEffect.EffectUserExecution userExecution;
  
  public RemoveRequiredRoleEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final RequiredRole requiredRole, final InterfaceRequiringEntity requiringEntity) {
    super(responseExecutionState, calledBy);
    				this.userExecution = new mir.routines.pcm2java.RemoveRequiredRoleEffect.EffectUserExecution(getExecutionState(), this);
    				this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(getExecutionState(), this);
    				this.requiredRole = requiredRole;this.requiringEntity = requiringEntity;
  }
  
  private RequiredRole requiredRole;
  
  private InterfaceRequiringEntity requiringEntity;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveRequiredRoleEffect with input:");
    getLogger().debug("   RequiredRole: " + this.requiredRole);
    getLogger().debug("   InterfaceRequiringEntity: " + this.requiringEntity);
    
    ClassifierImport requiredInterfaceImport = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceRequiredInterfaceImport(requiredRole, requiringEntity), // correspondence source supplier
    	ClassifierImport.class,
    	(ClassifierImport _element) -> true, // correspondence precondition checker
    	null);
    if (requiredInterfaceImport == null) {
    	return;
    }
    initializeRetrieveElementState(requiredInterfaceImport);
    Field requiredInterfaceField = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceRequiredInterfaceField(requiredRole, requiringEntity, requiredInterfaceImport), // correspondence source supplier
    	Field.class,
    	(Field _element) -> true, // correspondence precondition checker
    	null);
    if (requiredInterfaceField == null) {
    	return;
    }
    initializeRetrieveElementState(requiredInterfaceField);
    org.emftext.language.java.classifiers.Class javaClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJavaClass(requiredRole, requiringEntity, requiredInterfaceImport, requiredInterfaceField), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    if (javaClass == null) {
    	return;
    }
    initializeRetrieveElementState(javaClass);
    deleteObject(userExecution.getElement1(requiredRole, requiringEntity, requiredInterfaceImport, requiredInterfaceField, javaClass));
    
    deleteObject(userExecution.getElement2(requiredRole, requiringEntity, requiredInterfaceImport, requiredInterfaceField, javaClass));
    
    preprocessElementStates();
    userExecution.callRoutine1(
    	requiredRole, requiringEntity, requiredInterfaceImport, requiredInterfaceField, javaClass, effectFacade);
    postprocessElementStates();
  }
  
  private static class CallRoutinesUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public CallRoutinesUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
  }
}
