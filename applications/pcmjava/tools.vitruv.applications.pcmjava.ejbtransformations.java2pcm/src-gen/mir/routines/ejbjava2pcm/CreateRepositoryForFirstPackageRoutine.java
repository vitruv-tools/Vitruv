package mir.routines.ejbjava2pcm;

import com.google.common.base.Objects;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm.EJBJava2PcmHelper;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateRepositoryForFirstPackageRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade effectFacade;
  
  private CreateRepositoryForFirstPackageRoutine.EffectUserExecution userExecution;
  
  private static class EffectUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public void callRoutine1(final org.emftext.language.java.containers.Package javaPackage, @Extension final RoutinesFacade _routinesFacade) {
      Repository _findRepository = EJBJava2PcmHelper.findRepository(this.correspondenceModel);
      boolean _equals = Objects.equal(null, _findRepository);
      if (_equals) {
        final Repository repository = RepositoryFactory.eINSTANCE.createRepository();
        String _name = javaPackage.getName();
        repository.setEntityName(_name);
        String _entityName = repository.getEntityName();
        String _plus = ("model/" + _entityName);
        String _plus_1 = (_plus + ".repository");
        this.persistProjectRelative(javaPackage, repository, _plus_1);
        List<EObject> _singletonList = Collections.<EObject>singletonList(repository);
        List<EObject> _singletonList_1 = Collections.<EObject>singletonList(javaPackage);
        this.correspondenceModel.createAndAddCorrespondence(_singletonList, _singletonList_1);
      }
    }
  }
  
  public CreateRepositoryForFirstPackageRoutine(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.containers.Package javaPackage) {
    super(responseExecutionState, calledBy);
    this.userExecution = new mir.routines.ejbjava2pcm.CreateRepositoryForFirstPackageRoutine.EffectUserExecution(getExecutionState(), this);
    this.effectFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(getExecutionState(), this);
    this.javaPackage = javaPackage;
  }
  
  private org.emftext.language.java.containers.Package javaPackage;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateRepositoryForFirstPackageRoutine with input:");
    getLogger().debug("   Package: " + this.javaPackage);
    
    userExecution.callRoutine1(javaPackage, effectFacade);
    
    postprocessElementStates();
  }
}
