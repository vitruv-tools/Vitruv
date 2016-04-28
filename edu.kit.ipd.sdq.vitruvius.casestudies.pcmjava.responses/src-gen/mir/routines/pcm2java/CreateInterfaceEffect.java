package mir.routines.pcm2java;

import com.google.common.base.Objects;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Interface;

@SuppressWarnings("all")
public class CreateInterfaceEffect extends AbstractEffectRealization {
  public CreateInterfaceEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private CreateNonRootEObjectInList<Interface> change;
  
  private boolean isChangeSet;
  
  public void setChange(final CreateNonRootEObjectInList<Interface> change) {
    this.change = change;
    this.isChangeSet = true;
  }
  
  public boolean allParametersSet() {
    return isChangeSet;
  }
  
  private boolean getCorrespondingModelElementsPreconditionContractsPackage(final CreateNonRootEObjectInList<Interface> change, final org.emftext.language.java.containers.Package contractsPackage) {
    String _name = contractsPackage.getName();
    boolean _equals = Objects.equal(_name, "contracts");
    return _equals;
  }
  
  private EObject getCorrepondenceSourceContractsPackage(final CreateNonRootEObjectInList<Interface> change) {
    EObject _newAffectedEObject = change.getNewAffectedEObject();
    return _newAffectedEObject;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called routine CreateInterfaceEffect with input:");
    getLogger().debug("   CreateNonRootEObjectInList: " + this.change);
    
    org.emftext.language.java.containers.Package contractsPackage = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceContractsPackage(change), // correspondence source supplier
    	(org.emftext.language.java.containers.Package _element) -> getCorrespondingModelElementsPreconditionContractsPackage(change, _element), // correspondence precondition checker
    	() -> null, // tag supplier
    	org.emftext.language.java.containers.Package.class,
    	false, true, false);
    if (isAborted()) {
    	return;
    }
    
    preProcessElements();
    new mir.routines.pcm2java.CreateInterfaceEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, contractsPackage);
    postProcessElements();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final CreateNonRootEObjectInList<Interface> change, final org.emftext.language.java.containers.Package contractsPackage) {
      final Interface interf = change.getNewValue();
      String _entityName = interf.getEntityName();
      this.effectFacade.callCreateJavaInterface(interf, contractsPackage, _entityName);
    }
  }
}
