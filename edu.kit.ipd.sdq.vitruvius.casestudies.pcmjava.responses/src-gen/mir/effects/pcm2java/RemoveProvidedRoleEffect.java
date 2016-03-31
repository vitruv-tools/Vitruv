package mir.effects.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.CorrespondenceFailHandlerFactory;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import java.io.IOException;
import mir.effects.pcm2java.EffectsFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.imports.ClassifierImport;
import org.emftext.language.java.types.NamespaceClassifierReference;
import org.palladiosimulator.pcm.repository.ProvidedRole;

@SuppressWarnings("all")
public class RemoveProvidedRoleEffect extends AbstractEffectRealization {
  public RemoveProvidedRoleEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private ProvidedRole providedRole;
  
  private boolean isProvidedRoleSet;
  
  public void setProvidedRole(final ProvidedRole providedRole) {
    this.providedRole = providedRole;
    this.isProvidedRoleSet = true;
  }
  
  private EObject getCorrepondenceSourceRequiredInterfaceImport(final ProvidedRole providedRole) {
    return providedRole;
  }
  
  public boolean allParametersSet() {
    return isProvidedRoleSet;
  }
  
  private EObject getCorrepondenceSourceNamespaceClassifierReference(final ProvidedRole providedRole) {
    return providedRole;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called effect RemoveProvidedRoleEffect with input:");
    getLogger().debug("   ProvidedRole: " + this.providedRole);
    
    ClassifierImport requiredInterfaceImport = initializeDeleteElementState(
    	() -> getCorrepondenceSourceRequiredInterfaceImport(providedRole), // correspondence source supplier
    	(ClassifierImport _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	ClassifierImport.class,
    	CorrespondenceFailHandlerFactory.createDefaultUserDialogHandler(false));
    NamespaceClassifierReference namespaceClassifierReference = initializeDeleteElementState(
    	() -> getCorrepondenceSourceNamespaceClassifierReference(providedRole), // correspondence source supplier
    	(NamespaceClassifierReference _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	NamespaceClassifierReference.class,
    	CorrespondenceFailHandlerFactory.createCustomUserDialogHandler(false, "Due to old TUIDs remove cannot be performed"));
    preProcessElements();
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
  }
}
