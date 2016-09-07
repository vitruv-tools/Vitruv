package mir.routines.ejbjava2pcm;

import com.google.common.base.Objects;
import tools.vitruvius.applications.pcmjava.ejbtransformations.java2pcm.EJBJava2PcmHelper;
import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruvius.framework.change.echange.root.InsertRootEObject;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

@SuppressWarnings("all")
public class CreatedFirstPackageEffect extends AbstractEffectRealization {
  public CreatedFirstPackageEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final InsertRootEObject<org.emftext.language.java.containers.Package> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private InsertRootEObject<org.emftext.language.java.containers.Package> change;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatedFirstPackageEffect with input:");
    getLogger().debug("   InsertRootEObject: " + this.change);
    
    
    preprocessElementStates();
    new mir.routines.ejbjava2pcm.CreatedFirstPackageEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change);
    postprocessElementStates();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final InsertRootEObject<org.emftext.language.java.containers.Package> change) {
      Repository _findRepository = EJBJava2PcmHelper.findRepository(this.correspondenceModel);
      boolean _equals = Objects.equal(null, _findRepository);
      if (_equals) {
        final Repository repository = RepositoryFactory.eINSTANCE.createRepository();
        org.emftext.language.java.containers.Package _newValue = change.getNewValue();
        String _name = _newValue.getName();
        repository.setEntityName(_name);
        org.emftext.language.java.containers.Package _newValue_1 = change.getNewValue();
        String _entityName = repository.getEntityName();
        String _plus = ("model/" + _entityName);
        String _plus_1 = (_plus + ".repository");
        this.persistProjectRelative(_newValue_1, repository, _plus_1);
        List<EObject> _singletonList = Collections.<EObject>singletonList(repository);
        org.emftext.language.java.containers.Package _newValue_2 = change.getNewValue();
        List<EObject> _singletonList_1 = Collections.<EObject>singletonList(_newValue_2);
        this.correspondenceModel.createAndAddCorrespondence(_singletonList, _singletonList_1);
      }
    }
  }
}
