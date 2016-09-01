package mir.routines.ejbjava2pcm;

import edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import java.io.IOException;
import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.ClassMethod;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;
import org.palladiosimulator.pcm.seff.impl.SeffFactoryImpl;

@SuppressWarnings("all")
public class CreateSEFFForClassMethodEffect extends AbstractEffectRealization {
  public CreateSEFFForClassMethodEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final BasicComponent basicComponent, final OperationSignature opSignature, final ClassMethod classMethod) {
    super(responseExecutionState, calledBy);
    				this.basicComponent = basicComponent;this.opSignature = opSignature;this.classMethod = classMethod;
  }
  
  private BasicComponent basicComponent;
  
  private OperationSignature opSignature;
  
  private ClassMethod classMethod;
  
  private EObject getElement0(final BasicComponent basicComponent, final OperationSignature opSignature, final ClassMethod classMethod, final ResourceDemandingSEFF seff) {
    return seff;
  }
  
  private EObject getElement1(final BasicComponent basicComponent, final OperationSignature opSignature, final ClassMethod classMethod, final ResourceDemandingSEFF seff) {
    return classMethod;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateSEFFForClassMethodEffect with input:");
    getLogger().debug("   BasicComponent: " + this.basicComponent);
    getLogger().debug("   OperationSignature: " + this.opSignature);
    getLogger().debug("   ClassMethod: " + this.classMethod);
    
    ResourceDemandingSEFF seff = SeffFactoryImpl.eINSTANCE.createResourceDemandingSEFF();
    initializeCreateElementState(seff);
    
    addCorrespondenceBetween(getElement0(basicComponent, opSignature, classMethod, seff), getElement1(basicComponent, opSignature, classMethod, seff), "");
    preprocessElementStates();
    new mir.routines.ejbjava2pcm.CreateSEFFForClassMethodEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	basicComponent, opSignature, classMethod, seff);
    postprocessElementStates();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final BasicComponent basicComponent, final OperationSignature opSignature, final ClassMethod classMethod, final ResourceDemandingSEFF seff) {
      seff.setDescribedService__SEFF(opSignature);
      EList<ServiceEffectSpecification> _serviceEffectSpecifications__BasicComponent = basicComponent.getServiceEffectSpecifications__BasicComponent();
      _serviceEffectSpecifications__BasicComponent.add(seff);
    }
  }
}
