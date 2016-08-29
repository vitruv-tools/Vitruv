package mir.routines.pcm2java;

import com.google.common.base.Objects;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.change.echange.feature.reference.InsertEReference;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.Repository;

@SuppressWarnings("all")
public class CreatedCompositeDataTypeEffect extends AbstractEffectRealization {
  public CreatedCompositeDataTypeEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final InsertEReference<Repository, DataType> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private InsertEReference<Repository, DataType> change;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatedCompositeDataTypeEffect with input:");
    getLogger().debug("   InsertEReference: " + this.change);
    
    org.emftext.language.java.containers.Package datatypesPackage = getCorrespondingElement(
    	getCorrepondenceSourceDatatypesPackage(change), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> getCorrespondingModelElementsPreconditionDatatypesPackage(change, _element), // correspondence precondition checker
    	null);
    if (datatypesPackage == null) {
    	return;
    }
    initializeRetrieveElementState(datatypesPackage);
    
    preprocessElementStates();
    new mir.routines.pcm2java.CreatedCompositeDataTypeEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, datatypesPackage);
    postprocessElementStates();
  }
  
  private boolean getCorrespondingModelElementsPreconditionDatatypesPackage(final InsertEReference<Repository, DataType> change, final org.emftext.language.java.containers.Package datatypesPackage) {
    String _name = datatypesPackage.getName();
    boolean _equals = Objects.equal(_name, "datatypes");
    return _equals;
  }
  
  private EObject getCorrepondenceSourceDatatypesPackage(final InsertEReference<Repository, DataType> change) {
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
    
    private void executeUserOperations(final InsertEReference<Repository, DataType> change, final org.emftext.language.java.containers.Package datatypesPackage) {
      DataType _newValue = change.getNewValue();
      final CompositeDataType compositeDataType = ((CompositeDataType) _newValue);
      String _entityName = compositeDataType.getEntityName();
      this.effectFacade.callCreateJavaClass(compositeDataType, datatypesPackage, _entityName);
    }
  }
}
