package mir.routines.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
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
  
  private EObject getElement0(final ProvidedRole providedRole, final ClassifierImport requiredInterfaceImport, final NamespaceClassifierReference namespaceClassifierReference) {
    return requiredInterfaceImport;
  }
  
  private EObject getCorrepondenceSourceRequiredInterfaceImport(final ProvidedRole providedRole) {
    return providedRole;
  }
  
  private EObject getElement1(final ProvidedRole providedRole, final ClassifierImport requiredInterfaceImport, final NamespaceClassifierReference namespaceClassifierReference) {
    return namespaceClassifierReference;
  }
  
  public boolean allParametersSet() {
    return isProvidedRoleSet;
  }
  
  private EObject getCorrepondenceSourceNamespaceClassifierReference(final ProvidedRole providedRole) {
    return providedRole;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called routine RemoveProvidedRoleEffect with input:");
    getLogger().debug("   ProvidedRole: " + this.providedRole);
    
    ClassifierImport requiredInterfaceImport = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceRequiredInterfaceImport(providedRole), // correspondence source supplier
    	(ClassifierImport _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	ClassifierImport.class,
    	true, false, false);
    NamespaceClassifierReference namespaceClassifierReference = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceNamespaceClassifierReference(providedRole), // correspondence source supplier
    	(NamespaceClassifierReference _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	NamespaceClassifierReference.class,
    	true, false, false);
    if (isAborted()) {
    	return;
    }
    markObjectDelete(getElement0(providedRole, requiredInterfaceImport, namespaceClassifierReference));
    markObjectDelete(getElement1(providedRole, requiredInterfaceImport, namespaceClassifierReference));
    
    preProcessElements();
    postProcessElements();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new RoutinesFacade(responseExecutionState, calledBy);
    }
  }
}
