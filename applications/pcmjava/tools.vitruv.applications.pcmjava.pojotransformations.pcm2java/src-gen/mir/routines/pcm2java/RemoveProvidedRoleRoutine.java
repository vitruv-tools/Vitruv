package mir.routines.pcm2java;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.imports.ClassifierImport;
import org.emftext.language.java.types.NamespaceClassifierReference;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RemoveProvidedRoleRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RemoveProvidedRoleRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getCorrepondenceSourceRequiredInterfaceImport(final ProvidedRole providedRole) {
      return providedRole;
    }
    
    public EObject getElement1(final ProvidedRole providedRole, final ClassifierImport requiredInterfaceImport, final NamespaceClassifierReference namespaceClassifierReference) {
      return requiredInterfaceImport;
    }
    
    public EObject getElement2(final ProvidedRole providedRole, final ClassifierImport requiredInterfaceImport, final NamespaceClassifierReference namespaceClassifierReference) {
      return namespaceClassifierReference;
    }
    
    public EObject getCorrepondenceSourceNamespaceClassifierReference(final ProvidedRole providedRole, final ClassifierImport requiredInterfaceImport) {
      return providedRole;
    }
  }
  
  public RemoveProvidedRoleRoutine(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final ProvidedRole providedRole) {
    super(responseExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2java.RemoveProvidedRoleRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcm2java.RoutinesFacade(getExecutionState(), this);
    this.providedRole = providedRole;
  }
  
  private ProvidedRole providedRole;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveProvidedRoleRoutine with input:");
    getLogger().debug("   ProvidedRole: " + this.providedRole);
    
    ClassifierImport requiredInterfaceImport = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceRequiredInterfaceImport(providedRole), // correspondence source supplier
    	ClassifierImport.class,
    	(ClassifierImport _element) -> true, // correspondence precondition checker
    	null);
    if (requiredInterfaceImport == null) {
    	return;
    }
    initializeRetrieveElementState(requiredInterfaceImport);
    NamespaceClassifierReference namespaceClassifierReference = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceNamespaceClassifierReference(providedRole, requiredInterfaceImport), // correspondence source supplier
    	NamespaceClassifierReference.class,
    	(NamespaceClassifierReference _element) -> true, // correspondence precondition checker
    	null);
    if (namespaceClassifierReference == null) {
    	return;
    }
    initializeRetrieveElementState(namespaceClassifierReference);
    deleteObject(userExecution.getElement1(providedRole, requiredInterfaceImport, namespaceClassifierReference));
    
    deleteObject(userExecution.getElement2(providedRole, requiredInterfaceImport, namespaceClassifierReference));
    
    postprocessElementStates();
  }
}
