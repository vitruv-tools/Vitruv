package mir.routines.pcm2java;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.members.ClassMethod;
import org.palladiosimulator.pcm.repository.Signature;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class UpdateSEFFImplementingMethodNameEffect extends AbstractEffectRealization {
  private RoutinesFacade effectFacade;
  
  private UpdateSEFFImplementingMethodNameEffect.EffectUserExecution userExecution;
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getElement1(final ServiceEffectSpecification seff, final ClassMethod classMethod) {
      return classMethod;
    }
    
    public void update0Element(final ServiceEffectSpecification seff, final ClassMethod classMethod) {
      Signature _describedService__SEFF = seff.getDescribedService__SEFF();
      String _entityName = _describedService__SEFF.getEntityName();
      classMethod.setName(_entityName);
    }
    
    public EObject getCorrepondenceSourceClassMethod(final ServiceEffectSpecification seff) {
      return seff;
    }
  }
  
  public UpdateSEFFImplementingMethodNameEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final ServiceEffectSpecification seff) {
    super(responseExecutionState, calledBy);
    				this.userExecution = new mir.routines.pcm2java.UpdateSEFFImplementingMethodNameEffect.EffectUserExecution(getExecutionState(), this);
    				this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(getExecutionState(), this);
    				this.seff = seff;
  }
  
  private ServiceEffectSpecification seff;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine UpdateSEFFImplementingMethodNameEffect with input:");
    getLogger().debug("   ServiceEffectSpecification: " + this.seff);
    
    ClassMethod classMethod = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceClassMethod(seff), // correspondence source supplier
    	ClassMethod.class,
    	(ClassMethod _element) -> true, // correspondence precondition checker
    	null);
    if (classMethod == null) {
    	return;
    }
    initializeRetrieveElementState(classMethod);
    // val updatedElement userExecution.getElement1(seff, classMethod);
    userExecution.update0Element(seff, classMethod);
    
    postprocessElementStates();
  }
}
