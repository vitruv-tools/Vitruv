package mir.routines.pcm2java;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.members.impl.MembersFactoryImpl;
import org.emftext.language.java.types.TypesFactory;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.seff.ResourceDemandingInternalBehaviour;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateMethodForResourceDemandingBehaviorRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateMethodForResourceDemandingBehaviorRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void updateJavaMethodElement(final ResourceDemandingInternalBehaviour behavior, final org.emftext.language.java.classifiers.Class componentClass, final ClassMethod javaMethod) {
      String _entityName = behavior.getEntityName();
      javaMethod.setName(_entityName);
      org.emftext.language.java.types.Void _createVoid = TypesFactory.eINSTANCE.createVoid();
      javaMethod.setTypeReference(_createVoid);
    }
    
    public EObject getElement1(final ResourceDemandingInternalBehaviour behavior, final org.emftext.language.java.classifiers.Class componentClass, final ClassMethod javaMethod) {
      return javaMethod;
    }
    
    public void update0Element(final ResourceDemandingInternalBehaviour behavior, final org.emftext.language.java.classifiers.Class componentClass, final ClassMethod javaMethod) {
      EList<Member> _members = componentClass.getMembers();
      _members.add(javaMethod);
    }
    
    public EObject getCorrepondenceSourceComponentClass(final ResourceDemandingInternalBehaviour behavior) {
      BasicComponent _basicComponent_ResourceDemandingInternalBehaviour = behavior.getBasicComponent_ResourceDemandingInternalBehaviour();
      return _basicComponent_ResourceDemandingInternalBehaviour;
    }
    
    public EObject getElement2(final ResourceDemandingInternalBehaviour behavior, final org.emftext.language.java.classifiers.Class componentClass, final ClassMethod javaMethod) {
      return behavior;
    }
    
    public EObject getElement3(final ResourceDemandingInternalBehaviour behavior, final org.emftext.language.java.classifiers.Class componentClass, final ClassMethod javaMethod) {
      return componentClass;
    }
  }
  
  public CreateMethodForResourceDemandingBehaviorRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final ResourceDemandingInternalBehaviour behavior) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2java.CreateMethodForResourceDemandingBehaviorRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcm2java.RoutinesFacade(getExecutionState(), this);
    this.behavior = behavior;
  }
  
  private ResourceDemandingInternalBehaviour behavior;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateMethodForResourceDemandingBehaviorRoutine with input:");
    getLogger().debug("   ResourceDemandingInternalBehaviour: " + this.behavior);
    
    org.emftext.language.java.classifiers.Class componentClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceComponentClass(behavior), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    if (componentClass == null) {
    	return;
    }
    initializeRetrieveElementState(componentClass);
    ClassMethod javaMethod = MembersFactoryImpl.eINSTANCE.createClassMethod();
    initializeCreateElementState(javaMethod);
    userExecution.updateJavaMethodElement(behavior, componentClass, javaMethod);
    
    addCorrespondenceBetween(userExecution.getElement1(behavior, componentClass, javaMethod), userExecution.getElement2(behavior, componentClass, javaMethod), "");
    
    // val updatedElement userExecution.getElement3(behavior, componentClass, javaMethod);
    userExecution.update0Element(behavior, componentClass, javaMethod);
    
    postprocessElementStates();
  }
}
