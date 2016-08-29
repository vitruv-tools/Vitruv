package mir.routines.pcm2java;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.responses.pcm2java.Pcm2JavaHelper;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.UserInteractionType;
import edu.kit.ipd.sdq.vitruvius.framework.changes.echange.feature.reference.InsertEReference;
import java.io.IOException;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.types.PrimitiveType;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.Repository;

@SuppressWarnings("all")
public class CreatedCollectionDataTypeEffect extends AbstractEffectRealization {
  public CreatedCollectionDataTypeEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final InsertEReference<Repository, DataType> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private InsertEReference<Repository, DataType> change;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatedCollectionDataTypeEffect with input:");
    getLogger().debug("   InsertEReference: " + this.change);
    
    org.emftext.language.java.classifiers.Class innerTypeClass = getCorrespondingElement(
    	getCorrepondenceSourceInnerTypeClass(change), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    initializeRetrieveElementState(innerTypeClass);
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
    new mir.routines.pcm2java.CreatedCollectionDataTypeEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, innerTypeClass, datatypesPackage);
    postprocessElementStates();
  }
  
  private EObject getCorrepondenceSourceInnerTypeClass(final InsertEReference<Repository, DataType> change) {
    DataType _newValue = change.getNewValue();
    DataType _innerType_CollectionDataType = ((CollectionDataType) _newValue).getInnerType_CollectionDataType();
    return _innerType_CollectionDataType;
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
    
    private void executeUserOperations(final InsertEReference<Repository, DataType> change, final org.emftext.language.java.classifiers.Class innerTypeClass, final org.emftext.language.java.containers.Package datatypesPackage) {
      DataType _newValue = change.getNewValue();
      final CollectionDataType dataType = ((CollectionDataType) _newValue);
      DataType _innerType_CollectionDataType = dataType.getInnerType_CollectionDataType();
      final TypeReference innerTypeRef = Pcm2JavaHelper.createTypeReference(_innerType_CollectionDataType, innerTypeClass);
      TypeReference innerTypeClassOrWrapper = innerTypeRef;
      if ((innerTypeRef instanceof PrimitiveType)) {
        TypeReference _wrapperTypeReferenceForPrimitiveType = Pcm2JavaHelper.getWrapperTypeReferenceForPrimitiveType(innerTypeRef);
        innerTypeClassOrWrapper = _wrapperTypeReferenceForPrimitiveType;
      }
      Set<Class<? extends Collection>> collectionDataTypes = new HashSet<Class<? extends Collection>>();
      Iterables.<Class<? extends Collection>>addAll(collectionDataTypes, Collections.<Class<? extends AbstractCollection>>unmodifiableList(CollectionLiterals.<Class<? extends AbstractCollection>>newArrayList(ArrayList.class, LinkedList.class, Vector.class, Stack.class, HashSet.class)));
      int _size = collectionDataTypes.size();
      final List<String> collectionDataTypeNames = new ArrayList<String>(_size);
      for (final Class<? extends Collection> collectionDataType : collectionDataTypes) {
        String _name = collectionDataType.getName();
        collectionDataTypeNames.add(_name);
      }
      final String selectTypeMsg = "Please select type (or interface) that should be used for the type";
      final int selectedType = this.userInteracting.selectFromMessage(UserInteractionType.MODAL, selectTypeMsg, ((String[])Conversions.unwrapArray(collectionDataTypeNames, String.class)));
      final Set<Class<? extends Collection>> _converted_collectionDataTypes = (Set<Class<? extends Collection>>)collectionDataTypes;
      final Class<? extends Collection> selectedClass = ((Class<? extends Collection>[])Conversions.unwrapArray(_converted_collectionDataTypes, Class.class))[selectedType];
      String _entityName = dataType.getEntityName();
      this.effectFacade.callCreateJavaClass(dataType, datatypesPackage, _entityName);
      String _name_1 = selectedClass.getName();
      this.effectFacade.callAddSuperTypeToDataType(dataType, innerTypeClassOrWrapper, _name_1);
    }
  }
}
