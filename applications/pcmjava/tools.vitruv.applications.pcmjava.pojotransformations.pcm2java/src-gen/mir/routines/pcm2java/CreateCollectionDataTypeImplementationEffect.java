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
  private RoutinesFacade effectFacade;
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getCorrepondenceSourceInnerTypeClass(final CollectionDataType dataType) {
      DataType _innerType_CollectionDataType = dataType.getInnerType_CollectionDataType();
      return _innerType_CollectionDataType;
    }
    
    public boolean getCorrespondingModelElementsPreconditionDatatypesPackage(final CollectionDataType dataType, final org.emftext.language.java.classifiers.Class innerTypeClass, final org.emftext.language.java.containers.Package datatypesPackage) {
      String _name = datatypesPackage.getName();
      boolean _equals = Objects.equal(_name, "datatypes");
      return _equals;
    }
    
    public EObject getCorrepondenceSourceDatatypesPackage(final CollectionDataType dataType, final org.emftext.language.java.classifiers.Class innerTypeClass) {
      Repository _repository__DataType = dataType.getRepository__DataType();
      return _repository__DataType;
    }
    
    public void callRoutine1(final CollectionDataType dataType, final org.emftext.language.java.classifiers.Class innerTypeClass, final org.emftext.language.java.containers.Package datatypesPackage, @Extension final RoutinesFacade _routinesFacade) {
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
      _routinesFacade.createJavaClass(dataType, datatypesPackage, _entityName);
      String _name_1 = selectedClass.getName();
      _routinesFacade.addSuperTypeToDataType(dataType, innerTypeClassOrWrapper, _name_1);
    }
  }
  
  private CreateCollectionDataTypeImplementationEffect.EffectUserExecution userExecution;
  
  public CreateCollectionDataTypeImplementationEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final CollectionDataType dataType) {
    super(responseExecutionState, calledBy);
    				this.userExecution = new mir.routines.pcm2java.CreateCollectionDataTypeImplementationEffect.EffectUserExecution(getExecutionState(), this);
    				this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(getExecutionState(), this);
    				this.dataType = dataType;
  }
  
  private CollectionDataType dataType;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCollectionDataTypeImplementationEffect with input:");
    getLogger().debug("   CollectionDataType: " + this.dataType);
    
    org.emftext.language.java.classifiers.Class innerTypeClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceInnerTypeClass(dataType), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    initializeRetrieveElementState(innerTypeClass);
    org.emftext.language.java.containers.Package datatypesPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceDatatypesPackage(dataType, innerTypeClass), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> userExecution.getCorrespondingModelElementsPreconditionDatatypesPackage(dataType, innerTypeClass, _element), // correspondence precondition checker
    	null);
    if (datatypesPackage == null) {
    	return;
    }
    initializeRetrieveElementState(datatypesPackage);
    userExecution.callRoutine1(dataType, innerTypeClass, datatypesPackage, effectFacade);
    
    preprocessElementStates();
    postprocessElementStates();
  }
}
