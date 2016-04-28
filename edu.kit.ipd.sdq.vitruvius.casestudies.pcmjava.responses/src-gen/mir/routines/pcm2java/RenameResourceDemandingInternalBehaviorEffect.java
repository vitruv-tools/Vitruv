package mir.routines.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.UpdateSingleValuedEAttribute;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.ClassMethod;

@SuppressWarnings("all")
public class RenameResourceDemandingInternalBehaviorEffect extends AbstractEffectRealization {
  public RenameResourceDemandingInternalBehaviorEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private UpdateSingleValuedEAttribute<String> change;
  
  private boolean isChangeSet;
  
  public void setChange(final UpdateSingleValuedEAttribute<String> change) {
    this.change = change;
    this.isChangeSet = true;
  }
  
  public boolean allParametersSet() {
    return isChangeSet;
  }
  
  private EObject getCorrepondenceSourceJavaMethod(final UpdateSingleValuedEAttribute<String> change) {
    EObject _newAffectedEObject = change.getNewAffectedEObject();
    return _newAffectedEObject;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called routine RenameResourceDemandingInternalBehaviorEffect with input:");
    getLogger().debug("   UpdateSingleValuedEAttribute: " + this.change);
    
    ClassMethod javaMethod = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceJavaMethod(change), // correspondence source supplier
    	(ClassMethod _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	ClassMethod.class,
    	false, true, false);
    if (isAborted()) {
    	return;
    }
    
    preProcessElements();
    new mir.routines.pcm2java.RenameResourceDemandingInternalBehaviorEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, javaMethod);
    postProcessElements();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final UpdateSingleValuedEAttribute<String> change, final ClassMethod javaMethod) {
      String _newValue = change.getNewValue();
      javaMethod.setName(_newValue);
    }
  }
}
