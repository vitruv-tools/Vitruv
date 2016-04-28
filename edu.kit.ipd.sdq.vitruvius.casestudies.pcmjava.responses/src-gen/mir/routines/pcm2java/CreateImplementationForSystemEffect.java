package mir.routines.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class CreateImplementationForSystemEffect extends AbstractEffectRealization {
  public CreateImplementationForSystemEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private org.palladiosimulator.pcm.system.System system;
  
  private boolean isSystemSet;
  
  public void setSystem(final org.palladiosimulator.pcm.system.System system) {
    this.system = system;
    this.isSystemSet = true;
  }
  
  private EObject getCorrepondenceSourceSystemPackage(final org.palladiosimulator.pcm.system.System system) {
    return system;
  }
  
  public boolean allParametersSet() {
    return isSystemSet;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called routine CreateImplementationForSystemEffect with input:");
    getLogger().debug("   System: " + this.system);
    
    org.emftext.language.java.containers.Package systemPackage = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceSystemPackage(system), // correspondence source supplier
    	(org.emftext.language.java.containers.Package _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	org.emftext.language.java.containers.Package.class,
    	false, true, false);
    if (isAborted()) {
    	return;
    }
    
    preProcessElements();
    new mir.routines.pcm2java.CreateImplementationForSystemEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	system, systemPackage);
    postProcessElements();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final org.palladiosimulator.pcm.system.System system, final org.emftext.language.java.containers.Package systemPackage) {
      String _entityName = system.getEntityName();
      String _plus = (_entityName + "Impl");
      this.effectFacade.callCreateJavaClass(system, systemPackage, _plus);
    }
  }
}
