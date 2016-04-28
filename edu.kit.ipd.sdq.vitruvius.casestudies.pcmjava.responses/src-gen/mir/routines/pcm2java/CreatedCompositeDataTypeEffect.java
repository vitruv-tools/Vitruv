package mir.routines.pcm2java;

import com.google.common.base.Objects;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.DataType;

@SuppressWarnings("all")
public class CreatedCompositeDataTypeEffect extends AbstractEffectRealization {
  public CreatedCompositeDataTypeEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private CreateNonRootEObjectInList<DataType> change;
  
  private boolean isChangeSet;
  
  public void setChange(final CreateNonRootEObjectInList<DataType> change) {
    this.change = change;
    this.isChangeSet = true;
  }
  
  public boolean allParametersSet() {
    return isChangeSet;
  }
  
  private boolean getCorrespondingModelElementsPreconditionDatatypesPackage(final CreateNonRootEObjectInList<DataType> change, final org.emftext.language.java.containers.Package datatypesPackage) {
    String _name = datatypesPackage.getName();
    boolean _equals = Objects.equal(_name, "datatypes");
    return _equals;
  }
  
  private EObject getCorrepondenceSourceDatatypesPackage(final CreateNonRootEObjectInList<DataType> change) {
    EObject _newAffectedEObject = change.getNewAffectedEObject();
    return _newAffectedEObject;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called routine CreatedCompositeDataTypeEffect with input:");
    getLogger().debug("   CreateNonRootEObjectInList: " + this.change);
    
    org.emftext.language.java.containers.Package datatypesPackage = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceDatatypesPackage(change), // correspondence source supplier
    	(org.emftext.language.java.containers.Package _element) -> getCorrespondingModelElementsPreconditionDatatypesPackage(change, _element), // correspondence precondition checker
    	() -> null, // tag supplier
    	org.emftext.language.java.containers.Package.class,
    	false, true, false);
    if (isAborted()) {
    	return;
    }
    
    preProcessElements();
    new mir.routines.pcm2java.CreatedCompositeDataTypeEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, datatypesPackage);
    postProcessElements();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final CreateNonRootEObjectInList<DataType> change, final org.emftext.language.java.containers.Package datatypesPackage) {
      DataType _newValue = change.getNewValue();
      final CompositeDataType compositeDataType = ((CompositeDataType) _newValue);
      String _entityName = compositeDataType.getEntityName();
      this.effectFacade.callCreateJavaClass(compositeDataType, datatypesPackage, _entityName);
    }
  }
}
