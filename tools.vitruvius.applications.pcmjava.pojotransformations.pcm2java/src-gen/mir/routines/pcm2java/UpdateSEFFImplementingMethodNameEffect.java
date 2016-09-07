package mir.routines.pcm2java;

import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.ClassMethod;
import org.palladiosimulator.pcm.repository.Signature;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;

@SuppressWarnings("all")
public class UpdateSEFFImplementingMethodNameEffect extends AbstractEffectRealization {
  public UpdateSEFFImplementingMethodNameEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final ServiceEffectSpecification seff) {
    super(responseExecutionState, calledBy);
    				this.seff = seff;
  }
  
  private ServiceEffectSpecification seff;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine UpdateSEFFImplementingMethodNameEffect with input:");
    getLogger().debug("   ServiceEffectSpecification: " + this.seff);
    
    ClassMethod classMethod = getCorrespondingElement(
    	getCorrepondenceSourceClassMethod(seff), // correspondence source supplier
    	ClassMethod.class,
    	(ClassMethod _element) -> true, // correspondence precondition checker
    	null);
    if (classMethod == null) {
    	return;
    }
    initializeRetrieveElementState(classMethod);
    
    preprocessElementStates();
    new mir.routines.pcm2java.UpdateSEFFImplementingMethodNameEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	seff, classMethod);
    postprocessElementStates();
  }
  
  private EObject getCorrepondenceSourceClassMethod(final ServiceEffectSpecification seff) {
    return seff;
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final ServiceEffectSpecification seff, final ClassMethod classMethod) {
      Signature _describedService__SEFF = seff.getDescribedService__SEFF();
      String _entityName = _describedService__SEFF.getEntityName();
      classMethod.setName(_entityName);
    }
  }
}
