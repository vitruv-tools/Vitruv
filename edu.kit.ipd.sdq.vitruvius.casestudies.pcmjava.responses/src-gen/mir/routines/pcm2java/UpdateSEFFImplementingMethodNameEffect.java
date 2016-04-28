package mir.routines.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.ClassMethod;
import org.palladiosimulator.pcm.repository.Signature;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;

@SuppressWarnings("all")
public class UpdateSEFFImplementingMethodNameEffect extends AbstractEffectRealization {
  public UpdateSEFFImplementingMethodNameEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private ServiceEffectSpecification seff;
  
  private boolean isSeffSet;
  
  public void setSeff(final ServiceEffectSpecification seff) {
    this.seff = seff;
    this.isSeffSet = true;
  }
  
  public boolean allParametersSet() {
    return isSeffSet;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called routine UpdateSEFFImplementingMethodNameEffect with input:");
    getLogger().debug("   ServiceEffectSpecification: " + this.seff);
    
    ClassMethod classMethod = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceClassMethod(seff), // correspondence source supplier
    	(ClassMethod _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	ClassMethod.class,
    	false, true, false);
    if (isAborted()) {
    	return;
    }
    
    preProcessElements();
    new mir.routines.pcm2java.UpdateSEFFImplementingMethodNameEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	seff, classMethod);
    postProcessElements();
  }
  
  private EObject getCorrepondenceSourceClassMethod(final ServiceEffectSpecification seff) {
    return seff;
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final ServiceEffectSpecification seff, final ClassMethod classMethod) {
      Signature _describedService__SEFF = seff.getDescribedService__SEFF();
      String _entityName = _describedService__SEFF.getEntityName();
      classMethod.setName(_entityName);
    }
  }
}
