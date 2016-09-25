package mir.routines.pcm2java;

import com.google.common.base.Objects;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateCompositeDataTypeImplementationEffect extends AbstractEffectRealization {
  private RoutinesFacade effectFacade;
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public boolean getCorrespondingModelElementsPreconditionDatatypesPackage(final CompositeDataType dataType, final org.emftext.language.java.containers.Package datatypesPackage) {
      String _name = datatypesPackage.getName();
      boolean _equals = Objects.equal(_name, "datatypes");
      return _equals;
    }
    
    public EObject getCorrepondenceSourceDatatypesPackage(final CompositeDataType dataType) {
      Repository _repository__DataType = dataType.getRepository__DataType();
      return _repository__DataType;
    }
    
    public void callRoutine1(final CompositeDataType dataType, final org.emftext.language.java.containers.Package datatypesPackage, @Extension final RoutinesFacade _routinesFacade) {
      String _entityName = dataType.getEntityName();
      _routinesFacade.createJavaClass(dataType, datatypesPackage, _entityName);
    }
  }
  
  private CreateCompositeDataTypeImplementationEffect.EffectUserExecution userExecution;
  
  public CreateCompositeDataTypeImplementationEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final CompositeDataType dataType) {
    super(responseExecutionState, calledBy);
    				this.userExecution = new mir.routines.pcm2java.CreateCompositeDataTypeImplementationEffect.EffectUserExecution(getExecutionState(), this);
    				this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(getExecutionState(), this);
    				this.dataType = dataType;
  }
  
  private CompositeDataType dataType;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCompositeDataTypeImplementationEffect with input:");
    getLogger().debug("   CompositeDataType: " + this.dataType);
    
    org.emftext.language.java.containers.Package datatypesPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceDatatypesPackage(dataType), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> userExecution.getCorrespondingModelElementsPreconditionDatatypesPackage(dataType, _element), // correspondence precondition checker
    	null);
    if (datatypesPackage == null) {
    	return;
    }
    initializeRetrieveElementState(datatypesPackage);
    preprocessElementStates();
    userExecution.callRoutine1(
    	dataType, datatypesPackage, effectFacade);
    postprocessElementStates();
  }
  
  private static class CallRoutinesUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public CallRoutinesUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
  }
}
