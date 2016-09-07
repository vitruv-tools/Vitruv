package mir.routines.ejbjava2pcm;

import tools.vitruvius.applications.pcmjava.ejbtransformations.java2pcm.EJBJava2PcmHelper;
import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruvius.framework.change.echange.feature.reference.InsertEReference;

import java.io.IOException;
import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.containers.CompilationUnit;
import org.palladiosimulator.pcm.repository.Repository;

@SuppressWarnings("all")
public class CreatedInterfaceEffect extends AbstractEffectRealization {
  public CreatedInterfaceEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final InsertEReference<CompilationUnit, ConcreteClassifier> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private InsertEReference<CompilationUnit, ConcreteClassifier> change;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatedInterfaceEffect with input:");
    getLogger().debug("   InsertEReference: " + this.change);
    
    
    preprocessElementStates();
    new mir.routines.ejbjava2pcm.CreatedInterfaceEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
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
    
    private void executeUserOperations(final InsertEReference<CompilationUnit, ConcreteClassifier> change) {
      final Repository repo = EJBJava2PcmHelper.findRepository(this.correspondenceModel);
      ConcreteClassifier _newValue = change.getNewValue();
      this.effectFacade.callCreateOperationInterface(repo, _newValue);
    }
  }
}
