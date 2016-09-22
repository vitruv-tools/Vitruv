package mir.routines.pcm2java;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.members.impl.MembersFactoryImpl;
import org.emftext.language.java.types.TypesFactory;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.seff.ResourceDemandingInternalBehaviour;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateMethodForResourceDemandingBehaviorEffect extends AbstractEffectRealization {
  public CreateMethodForResourceDemandingBehaviorEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final ResourceDemandingInternalBehaviour behavior) {
    super(responseExecutionState, calledBy);
    				this.behavior = behavior;
  }
  
  private ResourceDemandingInternalBehaviour behavior;
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    private void executeUserOperations(final ResourceDemandingInternalBehaviour behavior, final org.emftext.language.java.classifiers.Class componentClass, final ClassMethod javaMethod) {
      String _entityName = behavior.getEntityName();
      javaMethod.setName(_entityName);
      org.emftext.language.java.types.Void _createVoid = TypesFactory.eINSTANCE.createVoid();
      javaMethod.setTypeReference(_createVoid);
      EList<Member> _members = componentClass.getMembers();
      _members.add(javaMethod);
    }
  }
  
  private static class CallRoutinesUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public CallRoutinesUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
  }
  
  private EObject getElement0(final ResourceDemandingInternalBehaviour behavior, final org.emftext.language.java.classifiers.Class componentClass, final ClassMethod javaMethod) {
    return javaMethod;
  }
  
  private EObject getElement1(final ResourceDemandingInternalBehaviour behavior, final org.emftext.language.java.classifiers.Class componentClass, final ClassMethod javaMethod) {
    return behavior;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateMethodForResourceDemandingBehaviorEffect with input:");
    getLogger().debug("   ResourceDemandingInternalBehaviour: " + this.behavior);
    
    org.emftext.language.java.classifiers.Class componentClass = getCorrespondingElement(
    	getCorrepondenceSourceComponentClass(behavior), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    if (componentClass == null) {
    	return;
    }
    initializeRetrieveElementState(componentClass);
    ClassMethod javaMethod = MembersFactoryImpl.eINSTANCE.createClassMethod();
    initializeCreateElementState(javaMethod);
    
    addCorrespondenceBetween(getElement0(behavior, componentClass, javaMethod), getElement1(behavior, componentClass, javaMethod), "");
    preprocessElementStates();
    new mir.routines.pcm2java.CreateMethodForResourceDemandingBehaviorEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	behavior, componentClass, javaMethod);
    postprocessElementStates();
  }
  
  private EObject getCorrepondenceSourceComponentClass(final ResourceDemandingInternalBehaviour behavior) {
    BasicComponent _basicComponent_ResourceDemandingInternalBehaviour = behavior.getBasicComponent_ResourceDemandingInternalBehaviour();
    return _basicComponent_ResourceDemandingInternalBehaviour;
  }
}
