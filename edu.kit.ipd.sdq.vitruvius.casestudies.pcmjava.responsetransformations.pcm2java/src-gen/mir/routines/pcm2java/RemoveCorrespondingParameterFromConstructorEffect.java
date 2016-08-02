package mir.routines.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.Constructor;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.parameters.Parameter;
import org.palladiosimulator.pcm.core.entity.NamedElement;

@SuppressWarnings("all")
public class RemoveCorrespondingParameterFromConstructorEffect extends AbstractEffectRealization {
  public RemoveCorrespondingParameterFromConstructorEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final Constructor ctor, final NamedElement correspondenceSource) {
    super(responseExecutionState, calledBy);
    				this.ctor = ctor;this.correspondenceSource = correspondenceSource;
  }
  
  private Constructor ctor;
  
  private NamedElement correspondenceSource;
  
  private EObject getElement0(final Constructor ctor, final NamedElement correspondenceSource, final OrdinaryParameter param) {
    return param;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveCorrespondingParameterFromConstructorEffect with input:");
    getLogger().debug("   Constructor: " + this.ctor);
    getLogger().debug("   NamedElement: " + this.correspondenceSource);
    
    OrdinaryParameter param = getCorrespondingElement(
    	getCorrepondenceSourceParam(ctor, correspondenceSource), // correspondence source supplier
    	OrdinaryParameter.class,
    	(OrdinaryParameter _element) -> getCorrespondingModelElementsPreconditionParam(ctor, correspondenceSource, _element), // correspondence precondition checker
    	null);
    if (param == null) {
    	return;
    }
    initializeRetrieveElementState(param);
    deleteObject(getElement0(ctor, correspondenceSource, param));
    
    preprocessElementStates();
    postprocessElementStates();
  }
  
  private boolean getCorrespondingModelElementsPreconditionParam(final Constructor ctor, final NamedElement correspondenceSource, final OrdinaryParameter param) {
    EList<Parameter> _parameters = ctor.getParameters();
    boolean _contains = _parameters.contains(param);
    return _contains;
  }
  
  private EObject getCorrepondenceSourceParam(final Constructor ctor, final NamedElement correspondenceSource) {
    return correspondenceSource;
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
  }
}
