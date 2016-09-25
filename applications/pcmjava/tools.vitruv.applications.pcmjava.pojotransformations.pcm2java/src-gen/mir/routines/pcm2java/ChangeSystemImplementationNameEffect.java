package mir.routines.pcm2java;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeSystemImplementationNameEffect extends AbstractEffectRealization {
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getCorrepondenceSourceSystemPackage(final org.palladiosimulator.pcm.system.System system) {
      return system;
    }
  }
  
  private ChangeSystemImplementationNameEffect.EffectUserExecution userExecution;
  
  public ChangeSystemImplementationNameEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final org.palladiosimulator.pcm.system.System system) {
    super(responseExecutionState, calledBy);
    				this.userExecution = new mir.routines.pcm2java.ChangeSystemImplementationNameEffect.EffectUserExecution(getExecutionState(), this);
    				this.system = system;
  }
  
  private org.palladiosimulator.pcm.system.System system;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeSystemImplementationNameEffect with input:");
    getLogger().debug("   System: " + this.system);
    
    org.emftext.language.java.containers.Package systemPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceSystemPackage(system), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> true, // correspondence precondition checker
    	null);
    if (systemPackage == null) {
    	return;
    }
    initializeRetrieveElementState(systemPackage);
    preprocessElementStates();
    new mir.routines.pcm2java.ChangeSystemImplementationNameEffect.CallRoutinesUserExecution(getExecutionState(), this).executeUserOperations(
    	system, systemPackage);
    postprocessElementStates();
  }
  
  private static class CallRoutinesUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public CallRoutinesUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    public void executeUserOperations(final org.palladiosimulator.pcm.system.System system, final org.emftext.language.java.containers.Package systemPackage) {
      String _entityName = system.getEntityName();
      this.effectFacade.renameJavaPackage(system, null, _entityName, null);
      String _entityName_1 = system.getEntityName();
      String _plus = (_entityName_1 + "Impl");
      this.effectFacade.renameJavaClassifier(system, systemPackage, _plus);
    }
  }
}
