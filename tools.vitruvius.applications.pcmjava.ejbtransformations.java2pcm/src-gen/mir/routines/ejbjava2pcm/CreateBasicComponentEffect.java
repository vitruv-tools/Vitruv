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
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl;

@SuppressWarnings("all")
public class CreateBasicComponentEffect extends AbstractEffectRealization {
  public CreateBasicComponentEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final Repository repo, final NamedElement namedElement) {
    super(responseExecutionState, calledBy);
    				this.repo = repo;this.namedElement = namedElement;
  }
  
  private Repository repo;
  
  private NamedElement namedElement;
  
  private EObject getElement0(final Repository repo, final NamedElement namedElement, final BasicComponent basicComponent) {
    return basicComponent;
  }
  
  private EObject getElement1(final Repository repo, final NamedElement namedElement, final BasicComponent basicComponent) {
    return namedElement;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateBasicComponentEffect with input:");
    getLogger().debug("   Repository: " + this.repo);
    getLogger().debug("   NamedElement: " + this.namedElement);
    
    BasicComponent basicComponent = RepositoryFactoryImpl.eINSTANCE.createBasicComponent();
    initializeCreateElementState(basicComponent);
    
    addCorrespondenceBetween(getElement0(repo, namedElement, basicComponent), getElement1(repo, namedElement, basicComponent), "");
    preprocessElementStates();
    new mir.routines.ejbjava2pcm.CreateBasicComponentEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	repo, namedElement, basicComponent);
    postprocessElementStates();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final Repository repo, final NamedElement namedElement, final BasicComponent basicComponent) {
      String _name = namedElement.getName();
      basicComponent.setEntityName(_name);
      EList<RepositoryComponent> _components__Repository = repo.getComponents__Repository();
      _components__Repository.add(basicComponent);
    }
  }
}
