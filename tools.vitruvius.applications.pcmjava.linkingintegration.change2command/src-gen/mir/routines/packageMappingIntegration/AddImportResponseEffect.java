package mir.routines.packageMappingIntegration;

import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruvius.framework.change.echange.feature.reference.InsertEReference;
import java.io.IOException;
import mir.routines.packageMappingIntegration.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.containers.JavaRoot;
import org.emftext.language.java.imports.Import;

@SuppressWarnings("all")
public class AddImportResponseEffect extends AbstractEffectRealization {
  public AddImportResponseEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final InsertEReference<JavaRoot, Import> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private InsertEReference<JavaRoot, Import> change;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine AddImportResponseEffect with input:");
    getLogger().debug("   InsertEReference: " + this.change);
    
    
    preprocessElementStates();
    new mir.routines.packageMappingIntegration.AddImportResponseEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change);
    postprocessElementStates();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.packageMappingIntegration.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final InsertEReference<JavaRoot, Import> change) {
    }
  }
}
