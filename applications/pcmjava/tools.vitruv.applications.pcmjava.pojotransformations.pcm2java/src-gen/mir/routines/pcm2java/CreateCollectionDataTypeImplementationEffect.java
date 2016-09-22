package mir.routines.pcm2java;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import java.io.IOException;
import java.util.AbstractCollection;
import java.util.ArrayList;
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
import tools.vitruv.applications.pcmjava.pojotransformations.pcm2java.Pcm2JavaHelper;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruv.framework.userinteraction.UserInteractionType;

@SuppressWarnings("all")
public class CreateCollectionDataTypeImplementationEffect extends AbstractEffectRealization {
  public CreateCollectionDataTypeImplementationEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final CollectionDataType dataType) {
    super(responseExecutionState, calledBy);
    				this.dataType = dataType;
  }
  
  private CollectionDataType dataType;
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
  }
  
  private static class CallRoutinesUserExecution extends AbstractEffectRealization.UserExecution {
    public CallRoutinesUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    @Extension
    private RoutinesFacade effectFacade;
    
    private void executeUserOperations(final CollectionDataType dataType, final org.emftext.language.java.classifiers.Class innerTypeClass, final org.emftext.language.java.containers.Package datatypesPackage) {
      DataType _innerType_CollectionDataType = dataType.getInnerType_CollectionDataType();
      final TypeReference innerTypeRef = Pcm2JavaHelper.createTypeReference(_innerType_CollectionDataType, innerTypeClass);
      TypeReference innerTypeClassOrWrapper = innerTypeRef;
      if ((innerTypeRef instanceof PrimitiveType)) {
        TypeReference _wrapperTypeReferenceForPrimitiveType = Pcm2JavaHelper.getWrapperTypeReferenceForPrimitiveType(innerTypeRef);
        innerTypeClassOrWrapper = _wrapperTypeReferenceForPrimitiveType;
      }
      Set<Class<?>> collectionDataTypes = new HashSet<Class<?>>();
      Iterables.<Class<?>>addAll(collectionDataTypes, Collections.<Class<? extends AbstractCollection>>unmodifiableList(CollectionLiterals.<Class<? extends AbstractCollection>>newArrayList(ArrayList.class, LinkedList.class, Vector.class, Stack.class, HashSet.class)));
      int _size = collectionDataTypes.size();
      final List<String> collectionDataTypeNames = new ArrayList<String>(_size);
      for (final Class<?> collectionDataType : collectionDataTypes) {
        String _name = collectionDataType.getName();
        collectionDataTypeNames.add(_name);
      }
      final String selectTypeMsg = "Please select type (or interface) that should be used for the type";
      final int selectedType = this.userInteracting.selectFromMessage(UserInteractionType.MODAL, selectTypeMsg, ((String[])Conversions.unwrapArray(collectionDataTypeNames, String.class)));
      final Set<Class<?>> _converted_collectionDataTypes = (Set<Class<?>>)collectionDataTypes;
      final Class<?> selectedClass = ((Class<?>[])Conversions.unwrapArray(_converted_collectionDataTypes, Class.class))[selectedType];
      String _entityName = dataType.getEntityName();
      this.effectFacade.callCreateJavaClass(dataType, datatypesPackage, _entityName);
      String _name_1 = selectedClass.getName();
      this.effectFacade.callAddSuperTypeToDataType(dataType, innerTypeClassOrWrapper, _name_1);
    }
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCollectionDataTypeImplementationEffect with input:");
    getLogger().debug("   CollectionDataType: " + this.dataType);
    
    org.emftext.language.java.classifiers.Class innerTypeClass = getCorrespondingElement(
    	getCorrepondenceSourceInnerTypeClass(dataType), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    initializeRetrieveElementState(innerTypeClass);
    org.emftext.language.java.containers.Package datatypesPackage = getCorrespondingElement(
    	getCorrepondenceSourceDatatypesPackage(dataType), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> getCorrespondingModelElementsPreconditionDatatypesPackage(dataType, _element), // correspondence precondition checker
    	null);
    if (datatypesPackage == null) {
    	return;
    }
    initializeRetrieveElementState(datatypesPackage);
    
    preprocessElementStates();
    new mir.routines.pcm2java.CreateCollectionDataTypeImplementationEffect.CallRoutinesUserExecution(getExecutionState(), this).executeUserOperations(
    	dataType, innerTypeClass, datatypesPackage);
    postprocessElementStates();
  }
  
  private EObject getCorrepondenceSourceInnerTypeClass(final CollectionDataType dataType) {
    DataType _innerType_CollectionDataType = dataType.getInnerType_CollectionDataType();
    return _innerType_CollectionDataType;
  }
  
  private boolean getCorrespondingModelElementsPreconditionDatatypesPackage(final CollectionDataType dataType, final org.emftext.language.java.containers.Package datatypesPackage) {
    String _name = datatypesPackage.getName();
    boolean _equals = Objects.equal(_name, "datatypes");
    return _equals;
  }
  
  private EObject getCorrepondenceSourceDatatypesPackage(final CollectionDataType dataType) {
    Repository _repository__DataType = dataType.getRepository__DataType();
    return _repository__DataType;
  }
}
