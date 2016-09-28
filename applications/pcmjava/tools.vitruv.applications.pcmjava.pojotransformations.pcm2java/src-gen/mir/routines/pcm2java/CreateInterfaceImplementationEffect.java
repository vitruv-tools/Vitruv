package mir.routines.pcm2java;

import com.google.common.base.Objects;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Interface;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateInterfaceImplementationEffect extends AbstractEffectRealization {
  private RoutinesFacade effectFacade;
  
  private CreateInterfaceImplementationEffect.EffectUserExecution userExecution;
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public boolean getCorrespondingModelElementsPreconditionContractsPackage(final Interface interf, final org.emftext.language.java.containers.Package contractsPackage) {
      String _name = contractsPackage.getName();
      boolean _equals = Objects.equal(_name, "contracts");
      return _equals;
    }
    
    public EObject getCorrepondenceSourceContractsPackage(final Interface interf) {
      Repository _repository__Interface = interf.getRepository__Interface();
      return _repository__Interface;
    }
    
    public void callRoutine1(final Interface interf, final org.emftext.language.java.containers.Package contractsPackage, @Extension final RoutinesFacade _routinesFacade) {
      String _entityName = interf.getEntityName();
      _routinesFacade.createJavaInterface(interf, contractsPackage, _entityName);
    }
  }
  
  public CreateInterfaceImplementationEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final Interface interf) {
    super(responseExecutionState, calledBy);
    				this.userExecution = new mir.routines.pcm2java.CreateInterfaceImplementationEffect.EffectUserExecution(getExecutionState(), this);
    				this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(getExecutionState(), this);
    				this.interf = interf;
  }
  
  private Interface interf;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateInterfaceImplementationEffect with input:");
    getLogger().debug("   Interface: " + this.interf);
    
    org.emftext.language.java.containers.Package contractsPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceContractsPackage(interf), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> userExecution.getCorrespondingModelElementsPreconditionContractsPackage(interf, _element), // correspondence precondition checker
    	null);
    if (contractsPackage == null) {
    	return;
    }
    initializeRetrieveElementState(contractsPackage);
    userExecution.callRoutine1(interf, contractsPackage, effectFacade);
    
    postprocessElementStates();
  }
}
