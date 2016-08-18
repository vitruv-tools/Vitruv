package mir.routines.pcm2java;

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.packagemapping.responses.pcm2java.Pcm2JavaHelper;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.InsertEReference;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.imports.ClassifierImport;
import org.emftext.language.java.imports.impl.ImportsFactoryImpl;
import org.emftext.language.java.instantiations.NewConstructorCall;
import org.emftext.language.java.instantiations.impl.InstantiationsFactoryImpl;
import org.emftext.language.java.members.Constructor;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.members.impl.MembersFactoryImpl;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.ComposedStructure;
import org.palladiosimulator.pcm.repository.RepositoryComponent;

@SuppressWarnings("all")
public class AddedAssemblyContextToComposedStructureEffect extends AbstractEffectRealization {
  public AddedAssemblyContextToComposedStructureEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final InsertEReference<ComposedStructure, AssemblyContext> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private InsertEReference<ComposedStructure, AssemblyContext> change;
  
  private EObject getElement0(final InsertEReference<ComposedStructure, AssemblyContext> change, final org.emftext.language.java.classifiers.Class compositeComponentJavaClass, final org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass, final Field assemblyContextField, final NewConstructorCall newConstructorCall, final ClassifierImport contextClassImport, final Constructor constructor) {
    return assemblyContextField;
  }
  
  private EObject getElement1(final InsertEReference<ComposedStructure, AssemblyContext> change, final org.emftext.language.java.classifiers.Class compositeComponentJavaClass, final org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass, final Field assemblyContextField, final NewConstructorCall newConstructorCall, final ClassifierImport contextClassImport, final Constructor constructor) {
    return newConstructorCall;
  }
  
  private EObject getCorrepondenceSourceEncapsulatedComponentJavaClass(final InsertEReference<ComposedStructure, AssemblyContext> change) {
    AssemblyContext _newValue = change.getNewValue();
    RepositoryComponent _encapsulatedComponent__AssemblyContext = _newValue.getEncapsulatedComponent__AssemblyContext();
    return _encapsulatedComponent__AssemblyContext;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine AddedAssemblyContextToComposedStructureEffect with input:");
    getLogger().debug("   InsertEReference: " + this.change);
    
    org.emftext.language.java.classifiers.Class compositeComponentJavaClass = getCorrespondingElement(
    	getCorrepondenceSourceCompositeComponentJavaClass(change), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    if (compositeComponentJavaClass == null) {
    	return;
    }
    initializeRetrieveElementState(compositeComponentJavaClass);
    org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass = getCorrespondingElement(
    	getCorrepondenceSourceEncapsulatedComponentJavaClass(change), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    if (encapsulatedComponentJavaClass == null) {
    	return;
    }
    initializeRetrieveElementState(encapsulatedComponentJavaClass);
    Field assemblyContextField = MembersFactoryImpl.eINSTANCE.createField();
    initializeCreateElementState(assemblyContextField);
    NewConstructorCall newConstructorCall = InstantiationsFactoryImpl.eINSTANCE.createNewConstructorCall();
    initializeCreateElementState(newConstructorCall);
    ClassifierImport contextClassImport = ImportsFactoryImpl.eINSTANCE.createClassifierImport();
    initializeCreateElementState(contextClassImport);
    Constructor constructor = MembersFactoryImpl.eINSTANCE.createConstructor();
    initializeCreateElementState(constructor);
    
    addCorrespondenceBetween(getElement0(change, compositeComponentJavaClass, encapsulatedComponentJavaClass, assemblyContextField, newConstructorCall, contextClassImport, constructor), getElement4(change, compositeComponentJavaClass, encapsulatedComponentJavaClass, assemblyContextField, newConstructorCall, contextClassImport, constructor), "");
    addCorrespondenceBetween(getElement1(change, compositeComponentJavaClass, encapsulatedComponentJavaClass, assemblyContextField, newConstructorCall, contextClassImport, constructor), getElement5(change, compositeComponentJavaClass, encapsulatedComponentJavaClass, assemblyContextField, newConstructorCall, contextClassImport, constructor), "");
    addCorrespondenceBetween(getElement2(change, compositeComponentJavaClass, encapsulatedComponentJavaClass, assemblyContextField, newConstructorCall, contextClassImport, constructor), getElement6(change, compositeComponentJavaClass, encapsulatedComponentJavaClass, assemblyContextField, newConstructorCall, contextClassImport, constructor), "");
    addCorrespondenceBetween(getElement3(change, compositeComponentJavaClass, encapsulatedComponentJavaClass, assemblyContextField, newConstructorCall, contextClassImport, constructor), getElement7(change, compositeComponentJavaClass, encapsulatedComponentJavaClass, assemblyContextField, newConstructorCall, contextClassImport, constructor), "");
    preprocessElementStates();
    new mir.routines.pcm2java.AddedAssemblyContextToComposedStructureEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, compositeComponentJavaClass, encapsulatedComponentJavaClass, assemblyContextField, newConstructorCall, contextClassImport, constructor);
    postprocessElementStates();
  }
  
  private EObject getCorrepondenceSourceCompositeComponentJavaClass(final InsertEReference<ComposedStructure, AssemblyContext> change) {
    ComposedStructure _affectedEObject = change.getAffectedEObject();
    return _affectedEObject;
  }
  
  private EObject getElement4(final InsertEReference<ComposedStructure, AssemblyContext> change, final org.emftext.language.java.classifiers.Class compositeComponentJavaClass, final org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass, final Field assemblyContextField, final NewConstructorCall newConstructorCall, final ClassifierImport contextClassImport, final Constructor constructor) {
    AssemblyContext _newValue = change.getNewValue();
    return _newValue;
  }
  
  private EObject getElement5(final InsertEReference<ComposedStructure, AssemblyContext> change, final org.emftext.language.java.classifiers.Class compositeComponentJavaClass, final org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass, final Field assemblyContextField, final NewConstructorCall newConstructorCall, final ClassifierImport contextClassImport, final Constructor constructor) {
    AssemblyContext _newValue = change.getNewValue();
    return _newValue;
  }
  
  private EObject getElement2(final InsertEReference<ComposedStructure, AssemblyContext> change, final org.emftext.language.java.classifiers.Class compositeComponentJavaClass, final org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass, final Field assemblyContextField, final NewConstructorCall newConstructorCall, final ClassifierImport contextClassImport, final Constructor constructor) {
    return contextClassImport;
  }
  
  private EObject getElement3(final InsertEReference<ComposedStructure, AssemblyContext> change, final org.emftext.language.java.classifiers.Class compositeComponentJavaClass, final org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass, final Field assemblyContextField, final NewConstructorCall newConstructorCall, final ClassifierImport contextClassImport, final Constructor constructor) {
    return constructor;
  }
  
  private EObject getElement6(final InsertEReference<ComposedStructure, AssemblyContext> change, final org.emftext.language.java.classifiers.Class compositeComponentJavaClass, final org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass, final Field assemblyContextField, final NewConstructorCall newConstructorCall, final ClassifierImport contextClassImport, final Constructor constructor) {
    AssemblyContext _newValue = change.getNewValue();
    return _newValue;
  }
  
  private EObject getElement7(final InsertEReference<ComposedStructure, AssemblyContext> change, final org.emftext.language.java.classifiers.Class compositeComponentJavaClass, final org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass, final Field assemblyContextField, final NewConstructorCall newConstructorCall, final ClassifierImport contextClassImport, final Constructor constructor) {
    AssemblyContext _newValue = change.getNewValue();
    return _newValue;
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final InsertEReference<ComposedStructure, AssemblyContext> change, final org.emftext.language.java.classifiers.Class compositeComponentJavaClass, final org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass, final Field assemblyContextField, final NewConstructorCall newConstructorCall, final ClassifierImport contextClassImport, final Constructor constructor) {
      final AssemblyContext assemblyContext = change.getNewValue();
      final TypeReference typeRef = Pcm2JavaHelper.createNamespaceClassifierReference(encapsulatedComponentJavaClass);
      String _entityName = assemblyContext.getEntityName();
      Pcm2JavaHelper.createPrivateField(assemblyContextField, typeRef, _entityName);
      EList<Member> _members = compositeComponentJavaClass.getMembers();
      _members.add(assemblyContextField);
      Pcm2JavaHelper.addConstructorToClass(constructor, compositeComponentJavaClass);
      Pcm2JavaHelper.addImportToCompilationUnitOfClassifier(contextClassImport, compositeComponentJavaClass, encapsulatedComponentJavaClass);
      Pcm2JavaHelper.createNewForFieldInConstructor(newConstructorCall, constructor, assemblyContextField);
    }
  }
}
