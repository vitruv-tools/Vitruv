package mir.routines.pcm2java;

import com.google.common.base.Objects;
import edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.change.echange.feature.reference.InsertEReference;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Interface;
import org.palladiosimulator.pcm.repository.Repository;

@SuppressWarnings("all")
public class CreatedInterfaceEffect extends AbstractEffectRealization {
  public CreatedInterfaceEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final InsertEReference<Repository, Interface> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private InsertEReference<Repository, Interface> change;
  
  private boolean getCorrespondingModelElementsPreconditionContractsPackage(final InsertEReference<Repository, Interface> change, final org.emftext.language.java.containers.Package contractsPackage) {
    String _name = contractsPackage.getName();
    boolean _equals = Objects.equal(_name, "contracts");
    return _equals;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatedInterfaceEffect with input:");
    getLogger().debug("   InsertEReference: " + this.change);
    
    org.emftext.language.java.containers.Package contractsPackage = getCorrespondingElement(
    	getCorrepondenceSourceContractsPackage(change), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> getCorrespondingModelElementsPreconditionContractsPackage(change, _element), // correspondence precondition checker
    	null);
    if (contractsPackage == null) {
    	return;
    }
    initializeRetrieveElementState(contractsPackage);
    
    preprocessElementStates();
    new mir.routines.pcm2java.CreatedInterfaceEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, contractsPackage);
    postprocessElementStates();
  }
  
  private EObject getCorrepondenceSourceContractsPackage(final InsertEReference<Repository, Interface> change) {
    Repository _affectedEObject = change.getAffectedEObject();
    return _affectedEObject;
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final InsertEReference<Repository, Interface> change, final org.emftext.language.java.containers.Package contractsPackage) {
      final Interface interf = change.getNewValue();
      String _entityName = interf.getEntityName();
      this.effectFacade.callCreateJavaInterface(interf, contractsPackage, _entityName);
    }
  }
}
