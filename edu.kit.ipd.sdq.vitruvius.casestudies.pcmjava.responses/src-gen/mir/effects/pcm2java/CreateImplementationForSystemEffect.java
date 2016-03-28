package mir.effects.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import java.io.IOException;
import mir.effects.pcm2java.EffectsFacade;
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
    getLogger().debug("Called effect CreateImplementationForSystemEffect with input:");
    getLogger().debug("   System: " + this.system);
    
    org.emftext.language.java.containers.Package systemPackage = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceSystemPackage(system), // correspondence source supplier
    	(org.emftext.language.java.containers.Package _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	org.emftext.language.java.containers.Package.class,	false);
    preProcessElements();
    new mir.effects.pcm2java.CreateImplementationForSystemEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	system, systemPackage);
    postProcessElements();
  }
  
  private static class EffectUserExecution {
    private Blackboard blackboard;
    
    private UserInteracting userInteracting;
    
    private TransformationResult transformationResult;
    
    @Extension
    private EffectsFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      this.blackboard = responseExecutionState.getBlackboard();
      this.userInteracting = responseExecutionState.getUserInteracting();
      this.transformationResult = responseExecutionState.getTransformationResult();
      this.effectFacade = new EffectsFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final org.palladiosimulator.pcm.system.System system, final org.emftext.language.java.containers.Package systemPackage) {
      String _entityName = system.getEntityName();
      String _plus = (_entityName + "Impl");
      this.effectFacade.callCreateJavaClass(system, systemPackage, _plus);
    }
  }
}
