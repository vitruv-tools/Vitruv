package mir.routines.simpleChangesTests;

import allElementTypes.Root;
import allElementTypes.impl.AllElementTypesFactoryImpl;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.CreateRootEObject;
import java.io.IOException;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class CreateRootTestEffect extends AbstractEffectRealization {
  public CreateRootTestEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private CreateRootEObject<Root> change;
  
  private boolean isChangeSet;
  
  public void setChange(final CreateRootEObject<Root> change) {
    this.change = change;
    this.isChangeSet = true;
  }
  
  private EObject getElement0(final CreateRootEObject<Root> change, final Root newRoot) {
    return newRoot;
  }
  
  private EObject getElement1(final CreateRootEObject<Root> change, final Root newRoot) {
    Root _newValue = change.getNewValue();
    return _newValue;
  }
  
  public boolean allParametersSet() {
    return isChangeSet;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called routine CreateRootTestEffect with input:");
    getLogger().debug("   CreateRootEObject: " + this.change);
    
    if (isAborted()) {
    	return;
    }
    Root newRoot = AllElementTypesFactoryImpl.eINSTANCE.createRoot();
    initializeCreateElementState(newRoot);
    
    addCorrespondenceBetween(getElement0(change, newRoot), getElement1(change, newRoot), "");
    preProcessElements();
    new mir.routines.simpleChangesTests.CreateRootTestEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, newRoot);
    postProcessElements();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final CreateRootEObject<Root> change, final Root newRoot) {
      Root _newValue = change.getNewValue();
      String _id = _newValue.getId();
      newRoot.setId(_id);
      Root _newValue_1 = change.getNewValue();
      this.persistProjectRelative(_newValue_1, newRoot, "model/Further_Target_Test_Model");
    }
  }
}
