package mir.routines.pcm2java;

import com.google.common.base.Objects;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.Repository;

@SuppressWarnings("all")
public class RenameInterfaceEffect extends AbstractEffectRealization {
  public RenameInterfaceEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final OperationInterface interf) {
    super(responseExecutionState, calledBy);
    				this.interf = interf;
  }
  
  private OperationInterface interf;
  
  private boolean getCorrespondingModelElementsPreconditionContractsPackage(final OperationInterface interf, final org.emftext.language.java.containers.Package contractsPackage) {
    String _name = contractsPackage.getName();
    boolean _equals = Objects.equal(_name, "contracts");
    return _equals;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameInterfaceEffect with input:");
    getLogger().debug("   OperationInterface: " + this.interf);
    
    org.emftext.language.java.containers.Package contractsPackage = getCorrespondingElement(
    	getCorrepondenceSourceContractsPackage(interf), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> getCorrespondingModelElementsPreconditionContractsPackage(interf, _element), // correspondence precondition checker
    	null);
    if (contractsPackage == null) {
    	return;
    }
    initializeRetrieveElementState(contractsPackage);
    
    preprocessElementStates();
    new mir.routines.pcm2java.RenameInterfaceEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	interf, contractsPackage);
    postprocessElementStates();
  }
  
  private EObject getCorrepondenceSourceContractsPackage(final OperationInterface interf) {
    Repository _repository__Interface = interf.getRepository__Interface();
    return _repository__Interface;
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final OperationInterface interf, final org.emftext.language.java.containers.Package contractsPackage) {
      String _entityName = interf.getEntityName();
      this.effectFacade.callRenameJavaClassifier(interf, contractsPackage, _entityName);
    }
  }
}
