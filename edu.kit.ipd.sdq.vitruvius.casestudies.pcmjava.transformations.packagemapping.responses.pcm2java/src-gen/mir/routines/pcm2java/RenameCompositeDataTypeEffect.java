package mir.routines.pcm2java;

import com.google.common.base.Objects;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.Repository;

@SuppressWarnings("all")
public class RenameCompositeDataTypeEffect extends AbstractEffectRealization {
  public RenameCompositeDataTypeEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final CompositeDataType compositeDataType) {
    super(responseExecutionState, calledBy);
    				this.compositeDataType = compositeDataType;
  }
  
  private CompositeDataType compositeDataType;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameCompositeDataTypeEffect with input:");
    getLogger().debug("   CompositeDataType: " + this.compositeDataType);
    
    org.emftext.language.java.containers.Package datatypesPackage = getCorrespondingElement(
    	getCorrepondenceSourceDatatypesPackage(compositeDataType), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> getCorrespondingModelElementsPreconditionDatatypesPackage(compositeDataType, _element), // correspondence precondition checker
    	null);
    if (datatypesPackage == null) {
    	return;
    }
    initializeRetrieveElementState(datatypesPackage);
    
    preprocessElementStates();
    new mir.routines.pcm2java.RenameCompositeDataTypeEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	compositeDataType, datatypesPackage);
    postprocessElementStates();
  }
  
  private boolean getCorrespondingModelElementsPreconditionDatatypesPackage(final CompositeDataType compositeDataType, final org.emftext.language.java.containers.Package datatypesPackage) {
    String _name = datatypesPackage.getName();
    boolean _equals = Objects.equal(_name, "datatypes");
    return _equals;
  }
  
  private EObject getCorrepondenceSourceDatatypesPackage(final CompositeDataType compositeDataType) {
    Repository _repository__DataType = compositeDataType.getRepository__DataType();
    return _repository__DataType;
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final CompositeDataType compositeDataType, final org.emftext.language.java.containers.Package datatypesPackage) {
      String _entityName = compositeDataType.getEntityName();
      this.effectFacade.callRenameJavaClassifier(compositeDataType, datatypesPackage, _entityName);
    }
  }
}
