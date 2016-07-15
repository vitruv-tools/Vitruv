package mir.routines.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectInList;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.ClassMethod;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;

@SuppressWarnings("all")
public class DeletedSeffEffect extends AbstractEffectRealization {
  public DeletedSeffEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final DeleteNonRootEObjectInList<ServiceEffectSpecification> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private DeleteNonRootEObjectInList<ServiceEffectSpecification> change;
  
  private EObject getElement0(final DeleteNonRootEObjectInList<ServiceEffectSpecification> change, final ClassMethod classMethod) {
    return classMethod;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine DeletedSeffEffect with input:");
    getLogger().debug("   DeleteNonRootEObjectInList: " + this.change);
    
    ClassMethod classMethod = getCorrespondingElement(
    	getCorrepondenceSourceClassMethod(change), // correspondence source supplier
    	ClassMethod.class,
    	(ClassMethod _element) -> true, // correspondence precondition checker
    	null);
    if (classMethod == null) {
    	return;
    }
    initializeRetrieveElementState(classMethod);
    deleteObject(getElement0(change, classMethod));
    
    preprocessElementStates();
    postprocessElementStates();
  }
  
  private EObject getCorrepondenceSourceClassMethod(final DeleteNonRootEObjectInList<ServiceEffectSpecification> change) {
    ServiceEffectSpecification _oldValue = change.getOldValue();
    return _oldValue;
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
  }
}
