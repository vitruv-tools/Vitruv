package mir.routines.pcm2java;

import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruvius.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.ClassMethod;
import org.palladiosimulator.pcm.seff.ResourceDemandingInternalBehaviour;

@SuppressWarnings("all")
public class RenameResourceDemandingInternalBehaviorEffect extends AbstractEffectRealization {
  public RenameResourceDemandingInternalBehaviorEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final ReplaceSingleValuedEAttribute<ResourceDemandingInternalBehaviour, String> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private ReplaceSingleValuedEAttribute<ResourceDemandingInternalBehaviour, String> change;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameResourceDemandingInternalBehaviorEffect with input:");
    getLogger().debug("   ReplaceSingleValuedEAttribute: " + this.change);
    
    ClassMethod javaMethod = getCorrespondingElement(
    	getCorrepondenceSourceJavaMethod(change), // correspondence source supplier
    	ClassMethod.class,
    	(ClassMethod _element) -> true, // correspondence precondition checker
    	null);
    if (javaMethod == null) {
    	return;
    }
    initializeRetrieveElementState(javaMethod);
    
    preprocessElementStates();
    new mir.routines.pcm2java.RenameResourceDemandingInternalBehaviorEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, javaMethod);
    postprocessElementStates();
  }
  
  private EObject getCorrepondenceSourceJavaMethod(final ReplaceSingleValuedEAttribute<ResourceDemandingInternalBehaviour, String> change) {
    ResourceDemandingInternalBehaviour _affectedEObject = change.getAffectedEObject();
    return _affectedEObject;
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final ReplaceSingleValuedEAttribute<ResourceDemandingInternalBehaviour, String> change, final ClassMethod javaMethod) {
      String _newValue = change.getNewValue();
      javaMethod.setName(_newValue);
    }
  }
}
