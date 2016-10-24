package mir.routines.ejbjava2pcm;

import java.io.IOException;
import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.commons.NamedElement;
import org.palladiosimulator.pcm.repository.Interface;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateOperationInterfaceRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateOperationInterfaceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getElement1(final Repository repo, final NamedElement namedElement, final OperationInterface operationInterface) {
      return operationInterface;
    }
    
    public void update0Element(final Repository repo, final NamedElement namedElement, final OperationInterface operationInterface) {
      EList<Interface> _interfaces__Repository = repo.getInterfaces__Repository();
      _interfaces__Repository.add(operationInterface);
    }
    
    public EObject getElement2(final Repository repo, final NamedElement namedElement, final OperationInterface operationInterface) {
      return namedElement;
    }
    
    public EObject getElement3(final Repository repo, final NamedElement namedElement, final OperationInterface operationInterface) {
      return operationInterface;
    }
    
    public void updateOperationInterfaceElement(final Repository repo, final NamedElement namedElement, final OperationInterface operationInterface) {
      String _name = namedElement.getName();
      operationInterface.setEntityName(_name);
    }
  }
  
  public CreateOperationInterfaceRoutine(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final Repository repo, final NamedElement namedElement) {
    super(responseExecutionState, calledBy);
    this.userExecution = new mir.routines.ejbjava2pcm.CreateOperationInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(getExecutionState(), this);
    this.repo = repo;this.namedElement = namedElement;
  }
  
  private Repository repo;
  
  private NamedElement namedElement;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateOperationInterfaceRoutine with input:");
    getLogger().debug("   Repository: " + this.repo);
    getLogger().debug("   NamedElement: " + this.namedElement);
    
    OperationInterface operationInterface = RepositoryFactoryImpl.eINSTANCE.createOperationInterface();
    initializeCreateElementState(operationInterface);
    userExecution.updateOperationInterfaceElement(repo, namedElement, operationInterface);
    
    addCorrespondenceBetween(userExecution.getElement1(repo, namedElement, operationInterface), userExecution.getElement2(repo, namedElement, operationInterface), "");
    
    // val updatedElement userExecution.getElement3(repo, namedElement, operationInterface);
    userExecution.update0Element(repo, namedElement, operationInterface);
    
    postprocessElementStates();
  }
}
