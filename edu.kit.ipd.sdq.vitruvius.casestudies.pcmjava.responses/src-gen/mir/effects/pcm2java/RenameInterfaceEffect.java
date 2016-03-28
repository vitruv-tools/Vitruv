package mir.effects.pcm2java;

import com.google.common.base.Objects;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import java.io.IOException;
import mir.effects.pcm2java.EffectsFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.Repository;

@SuppressWarnings("all")
public class RenameInterfaceEffect extends AbstractEffectRealization {
  public RenameInterfaceEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private OperationInterface interf;
  
  private boolean isInterfSet;
  
  public void setInterf(final OperationInterface interf) {
    this.interf = interf;
    this.isInterfSet = true;
  }
  
  public boolean allParametersSet() {
    return isInterfSet;
  }
  
  private boolean getCorrespondingModelElementsPreconditionContractsPackage(final OperationInterface interf, final org.emftext.language.java.containers.Package contractsPackage) {
    String _name = contractsPackage.getName();
    boolean _equals = Objects.equal(_name, "contracts");
    return _equals;
  }
  
  private EObject getCorrepondenceSourceContractsPackage(final OperationInterface interf) {
    Repository _repository__Interface = interf.getRepository__Interface();
    return _repository__Interface;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called effect RenameInterfaceEffect with input:");
    getLogger().debug("   OperationInterface: " + this.interf);
    
    org.emftext.language.java.containers.Package contractsPackage = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceContractsPackage(interf), // correspondence source supplier
    	(org.emftext.language.java.containers.Package _element) -> getCorrespondingModelElementsPreconditionContractsPackage(interf, _element), // correspondence precondition checker
    	() -> null, // tag supplier
    	org.emftext.language.java.containers.Package.class,	false);
    preProcessElements();
    new mir.effects.pcm2java.RenameInterfaceEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	interf, contractsPackage);
    postProcessElements();
  }
  
  private static class EffectUserExecution {
    private Blackboard blackboard;
    
    private UserInteracting userInteracting;
    
    private TransformationResult transformationResult;
    
    @Extension
    private EffectsFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      this.blackboard = responseExecutionState.getBlackboard();
      this.userInteracting = responseExecutionState.getUserInteracting();
      this.transformationResult = responseExecutionState.getTransformationResult();
      this.effectFacade = new EffectsFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final OperationInterface interf, final org.emftext.language.java.containers.Package contractsPackage) {
      String _entityName = interf.getEntityName();
      this.effectFacade.callRenameJavaClassifier(interf, contractsPackage, _entityName);
    }
  }
}
