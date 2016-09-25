package mir.routines.ejbjava2pcm;

import java.io.IOException;
import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.members.ClassMethod;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;
import org.palladiosimulator.pcm.seff.impl.SeffFactoryImpl;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateSEFFForClassMethodRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade effectFacade;
  
  private CreateSEFFForClassMethodRoutine.EffectUserExecution userExecution;
  
  private static class EffectUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getElement1(final BasicComponent basicComponent, final OperationSignature opSignature, final ClassMethod classMethod, final ResourceDemandingSEFF seff) {
      return seff;
    }
    
    public void update0Element(final BasicComponent basicComponent, final OperationSignature opSignature, final ClassMethod classMethod, final ResourceDemandingSEFF seff) {
      seff.setDescribedService__SEFF(opSignature);
      EList<ServiceEffectSpecification> _serviceEffectSpecifications__BasicComponent = basicComponent.getServiceEffectSpecifications__BasicComponent();
      _serviceEffectSpecifications__BasicComponent.add(seff);
    }
    
    public EObject getElement2(final BasicComponent basicComponent, final OperationSignature opSignature, final ClassMethod classMethod, final ResourceDemandingSEFF seff) {
      return classMethod;
    }
    
    public EObject getElement3(final BasicComponent basicComponent, final OperationSignature opSignature, final ClassMethod classMethod, final ResourceDemandingSEFF seff) {
      return seff;
    }
  }
  
  public CreateSEFFForClassMethodRoutine(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final BasicComponent basicComponent, final OperationSignature opSignature, final ClassMethod classMethod) {
    super(responseExecutionState, calledBy);
    this.userExecution = new mir.routines.ejbjava2pcm.CreateSEFFForClassMethodRoutine.EffectUserExecution(getExecutionState(), this);
    this.effectFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(getExecutionState(), this);
    this.basicComponent = basicComponent;this.opSignature = opSignature;this.classMethod = classMethod;
  }
  
  private BasicComponent basicComponent;
  
  private OperationSignature opSignature;
  
  private ClassMethod classMethod;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateSEFFForClassMethodRoutine with input:");
    getLogger().debug("   BasicComponent: " + this.basicComponent);
    getLogger().debug("   OperationSignature: " + this.opSignature);
    getLogger().debug("   ClassMethod: " + this.classMethod);
    
    ResourceDemandingSEFF seff = SeffFactoryImpl.eINSTANCE.createResourceDemandingSEFF();
    initializeCreateElementState(seff);
    
    addCorrespondenceBetween(userExecution.getElement1(basicComponent, opSignature, classMethod, seff), userExecution.getElement2(basicComponent, opSignature, classMethod, seff), "");
    
    // val updatedElement userExecution.getElement3(basicComponent, opSignature, classMethod, seff);
    userExecution.update0Element(basicComponent, opSignature, classMethod, seff);
    
    postprocessElementStates();
  }
}
