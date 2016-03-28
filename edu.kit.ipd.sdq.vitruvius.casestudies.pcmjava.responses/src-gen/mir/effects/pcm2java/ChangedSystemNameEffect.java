package mir.effects.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.UpdateSingleValuedEAttribute;
import java.io.IOException;
import mir.effects.pcm2java.EffectsFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class ChangedSystemNameEffect extends AbstractEffectRealization {
  public ChangedSystemNameEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private UpdateSingleValuedEAttribute<String> change;
  
  private boolean isChangeSet;
  
  public void setChange(final UpdateSingleValuedEAttribute<String> change) {
    this.change = change;
    this.isChangeSet = true;
  }
  
  private EObject getCorrepondenceSourceSystemPackage(final UpdateSingleValuedEAttribute<String> change) {
    EObject _newAffectedEObject = change.getNewAffectedEObject();
    return _newAffectedEObject;
  }
  
  public boolean allParametersSet() {
    return isChangeSet;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called effect ChangedSystemNameEffect with input:");
    getLogger().debug("   UpdateSingleValuedEAttribute: " + this.change);
    
    org.emftext.language.java.containers.Package systemPackage = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceSystemPackage(change), // correspondence source supplier
    	(org.emftext.language.java.containers.Package _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	org.emftext.language.java.containers.Package.class,	false);
    preProcessElements();
    new mir.effects.pcm2java.ChangedSystemNameEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, systemPackage);
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
    
    private void executeUserOperations(final UpdateSingleValuedEAttribute<String> change, final org.emftext.language.java.containers.Package systemPackage) {
      EObject _newAffectedEObject = change.getNewAffectedEObject();
      final org.palladiosimulator.pcm.system.System system = ((org.palladiosimulator.pcm.system.System) _newAffectedEObject);
      String _entityName = system.getEntityName();
      this.effectFacade.callRenameJavaPackage(system, null, _entityName, null);
      String _entityName_1 = system.getEntityName();
      String _plus = (_entityName_1 + "Impl");
      this.effectFacade.callRenameJavaClassifier(system, systemPackage, _plus);
    }
  }
}
