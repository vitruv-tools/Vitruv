package mir.responses.responses5_1ToJava.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;

@SuppressWarnings("all")
class CreatedParameterResponse extends AbstractResponseRealization {
  public CreatedParameterResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getTrigger() {
    return CreateNonRootEObjectInList.class;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!checkChangeType(change)) {
    	return false;
    }
    if (!checkChangedObject(change)) {
    	return false;
    }
    CreateNonRootEObjectInList typedChange = (CreateNonRootEObjectInList)change;
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  private boolean checkChangeType(final EChange change) {
    return change instanceof CreateNonRootEObjectInList<?>;
  }
  
  public void executeResponse(final EChange change) {
    CreateNonRootEObjectInList<Parameter> typedChange = (CreateNonRootEObjectInList<Parameter>)change;
    mir.routines.pcm2java.CreatedParameterEffect effect = new mir.routines.pcm2java.CreatedParameterEffect(this.executionState, this);
    effect.setChange(typedChange);
    effect.applyEffect();
  }
  
  private boolean checkChangedObject(final EChange change) {
    CreateNonRootEObjectInList<?> typedChange = (CreateNonRootEObjectInList<?>)change;
    EObject changedElement = typedChange.getOldAffectedEObject();
    if (!typedChange.getAffectedFeature().getName().equals("parameters__OperationSignature")) {
    	return false;
    }
    return changedElement instanceof OperationSignature;
  }
}
