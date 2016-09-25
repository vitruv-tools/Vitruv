package mir.routines.pcm2java;

import com.google.common.base.Objects;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameInterfaceEffect extends AbstractEffectRealization {
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public boolean getCorrespondingModelElementsPreconditionContractsPackage(final OperationInterface interf, final org.emftext.language.java.containers.Package contractsPackage) {
      String _name = contractsPackage.getName();
      boolean _equals = Objects.equal(_name, "contracts");
      return _equals;
    }
    
    public EObject getCorrepondenceSourceContractsPackage(final OperationInterface interf) {
      Repository _repository__Interface = interf.getRepository__Interface();
      return _repository__Interface;
    }
  }
  
  private RenameInterfaceEffect.EffectUserExecution userExecution;
  
  public RenameInterfaceEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final OperationInterface interf) {
    super(responseExecutionState, calledBy);
    				this.userExecution = new mir.routines.pcm2java.RenameInterfaceEffect.EffectUserExecution(getExecutionState(), this);
    				this.interf = interf;
  }
  
  private OperationInterface interf;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameInterfaceEffect with input:");
    getLogger().debug("   OperationInterface: " + this.interf);
    
    org.emftext.language.java.containers.Package contractsPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceContractsPackage(interf), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> userExecution.getCorrespondingModelElementsPreconditionContractsPackage(interf, _element), // correspondence precondition checker
    	null);
    if (contractsPackage == null) {
    	return;
    }
    initializeRetrieveElementState(contractsPackage);
    preprocessElementStates();
    new mir.routines.pcm2java.RenameInterfaceEffect.CallRoutinesUserExecution(getExecutionState(), this).executeUserOperations(
    	interf, contractsPackage);
    postprocessElementStates();
  }
  
  private static class CallRoutinesUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public CallRoutinesUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    public void executeUserOperations(final OperationInterface interf, final org.emftext.language.java.containers.Package contractsPackage) {
      String _entityName = interf.getEntityName();
      this.effectFacade.renameJavaClassifier(interf, contractsPackage, _entityName);
    }
  }
}
