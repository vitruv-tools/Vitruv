package mir.routines.pcm2java;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.members.Constructor;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.parameters.Parameter;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RemoveCorrespondingParameterFromConstructorRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RemoveCorrespondingParameterFromConstructorRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getElement1(final Constructor ctor, final NamedElement correspondenceSource, final OrdinaryParameter param) {
      return param;
    }
    
    public boolean getCorrespondingModelElementsPreconditionParam(final Constructor ctor, final NamedElement correspondenceSource, final OrdinaryParameter param) {
      EList<Parameter> _parameters = ctor.getParameters();
      boolean _contains = _parameters.contains(param);
      return _contains;
    }
    
    public EObject getCorrepondenceSourceParam(final Constructor ctor, final NamedElement correspondenceSource) {
      return correspondenceSource;
    }
  }
  
  public RemoveCorrespondingParameterFromConstructorRoutine(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final Constructor ctor, final NamedElement correspondenceSource) {
    super(responseExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2java.RemoveCorrespondingParameterFromConstructorRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcm2java.RoutinesFacade(getExecutionState(), this);
    this.ctor = ctor;this.correspondenceSource = correspondenceSource;
  }
  
  private Constructor ctor;
  
  private NamedElement correspondenceSource;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveCorrespondingParameterFromConstructorRoutine with input:");
    getLogger().debug("   Constructor: " + this.ctor);
    getLogger().debug("   NamedElement: " + this.correspondenceSource);
    
    OrdinaryParameter param = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceParam(ctor, correspondenceSource), // correspondence source supplier
    	OrdinaryParameter.class,
    	(OrdinaryParameter _element) -> userExecution.getCorrespondingModelElementsPreconditionParam(ctor, correspondenceSource, _element), // correspondence precondition checker
    	null);
    if (param == null) {
    	return;
    }
    initializeRetrieveElementState(param);
    deleteObject(userExecution.getElement1(ctor, correspondenceSource, param));
    
    postprocessElementStates();
  }
}
