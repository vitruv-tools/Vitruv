package mir.routines.ejbjava2pcm;

import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import java.io.IOException;
import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.commons.NamedElement;
import org.palladiosimulator.pcm.repository.Interface;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl;

@SuppressWarnings("all")
public class CreateOperationInterfaceEffect extends AbstractEffectRealization {
  public CreateOperationInterfaceEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final Repository repo, final NamedElement namedElement) {
    super(responseExecutionState, calledBy);
    				this.repo = repo;this.namedElement = namedElement;
  }
  
  private Repository repo;
  
  private NamedElement namedElement;
  
  private EObject getElement0(final Repository repo, final NamedElement namedElement, final OperationInterface operationInterface) {
    return operationInterface;
  }
  
  private EObject getElement1(final Repository repo, final NamedElement namedElement, final OperationInterface operationInterface) {
    return namedElement;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateOperationInterfaceEffect with input:");
    getLogger().debug("   Repository: " + this.repo);
    getLogger().debug("   NamedElement: " + this.namedElement);
    
    OperationInterface operationInterface = RepositoryFactoryImpl.eINSTANCE.createOperationInterface();
    initializeCreateElementState(operationInterface);
    
    addCorrespondenceBetween(getElement0(repo, namedElement, operationInterface), getElement1(repo, namedElement, operationInterface), "");
    preprocessElementStates();
    new mir.routines.ejbjava2pcm.CreateOperationInterfaceEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	repo, namedElement, operationInterface);
    postprocessElementStates();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final Repository repo, final NamedElement namedElement, final OperationInterface operationInterface) {
      String _name = namedElement.getName();
      operationInterface.setEntityName(_name);
      EList<Interface> _interfaces__Repository = repo.getInterfaces__Repository();
      _interfaces__Repository.add(operationInterface);
    }
  }
}
