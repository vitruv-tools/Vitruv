package mir.routines.ejbjava2pcm;

import java.io.IOException;
import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.commons.NamedElement;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateBasicComponentRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateBasicComponentRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getElement1(final Repository repo, final NamedElement namedElement, final BasicComponent basicComponent) {
      return basicComponent;
    }
    
    public void update0Element(final Repository repo, final NamedElement namedElement, final BasicComponent basicComponent) {
      EList<RepositoryComponent> _components__Repository = repo.getComponents__Repository();
      _components__Repository.add(basicComponent);
    }
    
    public void updateBasicComponentElement(final Repository repo, final NamedElement namedElement, final BasicComponent basicComponent) {
      String _name = namedElement.getName();
      basicComponent.setEntityName(_name);
    }
    
    public EObject getElement2(final Repository repo, final NamedElement namedElement, final BasicComponent basicComponent) {
      return namedElement;
    }
    
    public EObject getElement3(final Repository repo, final NamedElement namedElement, final BasicComponent basicComponent) {
      return repo;
    }
  }
  
  public CreateBasicComponentRoutine(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final Repository repo, final NamedElement namedElement) {
    super(responseExecutionState, calledBy);
    this.userExecution = new mir.routines.ejbjava2pcm.CreateBasicComponentRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(getExecutionState(), this);
    this.repo = repo;this.namedElement = namedElement;
  }
  
  private Repository repo;
  
  private NamedElement namedElement;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateBasicComponentRoutine with input:");
    getLogger().debug("   Repository: " + this.repo);
    getLogger().debug("   NamedElement: " + this.namedElement);
    
    BasicComponent basicComponent = RepositoryFactoryImpl.eINSTANCE.createBasicComponent();
    initializeCreateElementState(basicComponent);
    userExecution.updateBasicComponentElement(repo, namedElement, basicComponent);
    
    addCorrespondenceBetween(userExecution.getElement1(repo, namedElement, basicComponent), userExecution.getElement2(repo, namedElement, basicComponent), "");
    
    // val updatedElement userExecution.getElement3(repo, namedElement, basicComponent);
    userExecution.update0Element(repo, namedElement, basicComponent);
    
    postprocessElementStates();
  }
}
