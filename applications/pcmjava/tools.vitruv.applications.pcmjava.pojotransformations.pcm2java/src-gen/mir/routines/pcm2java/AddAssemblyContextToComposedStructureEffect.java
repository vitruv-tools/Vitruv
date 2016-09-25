package mir.routines.pcm2java;

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
import tools.vitruv.applications.pcmjava.pojotransformations.pcm2java.Pcm2JavaHelper;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddAssemblyContextToComposedStructureEffect extends AbstractEffectRealization {
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getElement10(final ComposedStructure composedStructure, final AssemblyContext assemblyContext, final org.emftext.language.java.classifiers.Class compositeComponentJavaClass, final org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass, final Field assemblyContextField, final NewConstructorCall newConstructorCall, final ClassifierImport contextClassImport, final Constructor constructor) {
      return constructor;
    }
    
    public EObject getCorrepondenceSourceEncapsulatedComponentJavaClass(final ComposedStructure composedStructure, final AssemblyContext assemblyContext, final org.emftext.language.java.classifiers.Class compositeComponentJavaClass) {
      RepositoryComponent _encapsulatedComponent__AssemblyContext = assemblyContext.getEncapsulatedComponent__AssemblyContext();
      return _encapsulatedComponent__AssemblyContext;
    }
    
    public void update0Element(final ComposedStructure composedStructure, final AssemblyContext assemblyContext, final org.emftext.language.java.classifiers.Class compositeComponentJavaClass, final org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass, final Field assemblyContextField, final NewConstructorCall newConstructorCall, final ClassifierImport contextClassImport, final Constructor constructor) {
      EList<Member> _members = compositeComponentJavaClass.getMembers();
      _members.add(assemblyContextField);
      Pcm2JavaHelper.addConstructorToClass(constructor, compositeComponentJavaClass);
      Pcm2JavaHelper.addImportToCompilationUnitOfClassifier(contextClassImport, compositeComponentJavaClass, encapsulatedComponentJavaClass);
    }
    
    public EObject getElement8(final ComposedStructure composedStructure, final AssemblyContext assemblyContext, final org.emftext.language.java.classifiers.Class compositeComponentJavaClass, final org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass, final Field assemblyContextField, final NewConstructorCall newConstructorCall, final ClassifierImport contextClassImport, final Constructor constructor) {
      return assemblyContext;
    }
    
    public EObject getElement9(final ComposedStructure composedStructure, final AssemblyContext assemblyContext, final org.emftext.language.java.classifiers.Class compositeComponentJavaClass, final org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass, final Field assemblyContextField, final NewConstructorCall newConstructorCall, final ClassifierImport contextClassImport, final Constructor constructor) {
      return compositeComponentJavaClass;
    }
    
    public EObject getElement6(final ComposedStructure composedStructure, final AssemblyContext assemblyContext, final org.emftext.language.java.classifiers.Class compositeComponentJavaClass, final org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass, final Field assemblyContextField, final NewConstructorCall newConstructorCall, final ClassifierImport contextClassImport, final Constructor constructor) {
      return assemblyContext;
    }
    
    public EObject getElement7(final ComposedStructure composedStructure, final AssemblyContext assemblyContext, final org.emftext.language.java.classifiers.Class compositeComponentJavaClass, final org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass, final Field assemblyContextField, final NewConstructorCall newConstructorCall, final ClassifierImport contextClassImport, final Constructor constructor) {
      return constructor;
    }
    
    public void update1Element(final ComposedStructure composedStructure, final AssemblyContext assemblyContext, final org.emftext.language.java.classifiers.Class compositeComponentJavaClass, final org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass, final Field assemblyContextField, final NewConstructorCall newConstructorCall, final ClassifierImport contextClassImport, final Constructor constructor) {
      Pcm2JavaHelper.createNewForFieldInConstructor(newConstructorCall, constructor, assemblyContextField);
    }
    
    public EObject getElement1(final ComposedStructure composedStructure, final AssemblyContext assemblyContext, final org.emftext.language.java.classifiers.Class compositeComponentJavaClass, final org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass, final Field assemblyContextField, final NewConstructorCall newConstructorCall, final ClassifierImport contextClassImport, final Constructor constructor) {
      return assemblyContextField;
    }
    
    public EObject getCorrepondenceSourceCompositeComponentJavaClass(final ComposedStructure composedStructure, final AssemblyContext assemblyContext) {
      return composedStructure;
    }
    
    public EObject getElement4(final ComposedStructure composedStructure, final AssemblyContext assemblyContext, final org.emftext.language.java.classifiers.Class compositeComponentJavaClass, final org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass, final Field assemblyContextField, final NewConstructorCall newConstructorCall, final ClassifierImport contextClassImport, final Constructor constructor) {
      return assemblyContext;
    }
    
    public EObject getElement5(final ComposedStructure composedStructure, final AssemblyContext assemblyContext, final org.emftext.language.java.classifiers.Class compositeComponentJavaClass, final org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass, final Field assemblyContextField, final NewConstructorCall newConstructorCall, final ClassifierImport contextClassImport, final Constructor constructor) {
      return contextClassImport;
    }
    
    public EObject getElement2(final ComposedStructure composedStructure, final AssemblyContext assemblyContext, final org.emftext.language.java.classifiers.Class compositeComponentJavaClass, final org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass, final Field assemblyContextField, final NewConstructorCall newConstructorCall, final ClassifierImport contextClassImport, final Constructor constructor) {
      return assemblyContext;
    }
    
    public void updateAssemblyContextFieldElement(final ComposedStructure composedStructure, final AssemblyContext assemblyContext, final org.emftext.language.java.classifiers.Class compositeComponentJavaClass, final org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass, final Field assemblyContextField) {
      final TypeReference typeRef = Pcm2JavaHelper.createNamespaceClassifierReference(encapsulatedComponentJavaClass);
      String _entityName = assemblyContext.getEntityName();
      Pcm2JavaHelper.createPrivateField(assemblyContextField, typeRef, _entityName);
    }
    
    public EObject getElement3(final ComposedStructure composedStructure, final AssemblyContext assemblyContext, final org.emftext.language.java.classifiers.Class compositeComponentJavaClass, final org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass, final Field assemblyContextField, final NewConstructorCall newConstructorCall, final ClassifierImport contextClassImport, final Constructor constructor) {
      return newConstructorCall;
    }
  }
  
  private AddAssemblyContextToComposedStructureEffect.EffectUserExecution userExecution;
  
  public AddAssemblyContextToComposedStructureEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final ComposedStructure composedStructure, final AssemblyContext assemblyContext) {
    super(responseExecutionState, calledBy);
    				this.userExecution = new mir.routines.pcm2java.AddAssemblyContextToComposedStructureEffect.EffectUserExecution(getExecutionState(), this);
    				this.composedStructure = composedStructure;this.assemblyContext = assemblyContext;
  }
  
  private ComposedStructure composedStructure;
  
  private AssemblyContext assemblyContext;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine AddAssemblyContextToComposedStructureEffect with input:");
    getLogger().debug("   ComposedStructure: " + this.composedStructure);
    getLogger().debug("   AssemblyContext: " + this.assemblyContext);
    
    org.emftext.language.java.classifiers.Class compositeComponentJavaClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompositeComponentJavaClass(composedStructure, assemblyContext), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    if (compositeComponentJavaClass == null) {
    	return;
    }
    initializeRetrieveElementState(compositeComponentJavaClass);
    org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceEncapsulatedComponentJavaClass(composedStructure, assemblyContext, compositeComponentJavaClass), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    if (encapsulatedComponentJavaClass == null) {
    	return;
    }
    initializeRetrieveElementState(encapsulatedComponentJavaClass);
    Field assemblyContextField = MembersFactoryImpl.eINSTANCE.createField();
    initializeCreateElementState(assemblyContextField);
    userExecution.updateAssemblyContextFieldElement(composedStructure, assemblyContext, compositeComponentJavaClass, encapsulatedComponentJavaClass, assemblyContextField);
    
    NewConstructorCall newConstructorCall = InstantiationsFactoryImpl.eINSTANCE.createNewConstructorCall();
    initializeCreateElementState(newConstructorCall);
    
    ClassifierImport contextClassImport = ImportsFactoryImpl.eINSTANCE.createClassifierImport();
    initializeCreateElementState(contextClassImport);
    
    Constructor constructor = MembersFactoryImpl.eINSTANCE.createConstructor();
    initializeCreateElementState(constructor);
    
    addCorrespondenceBetween(userExecution.getElement1(composedStructure, assemblyContext, compositeComponentJavaClass, encapsulatedComponentJavaClass, assemblyContextField, newConstructorCall, contextClassImport, constructor), userExecution.getElement2(composedStructure, assemblyContext, compositeComponentJavaClass, encapsulatedComponentJavaClass, assemblyContextField, newConstructorCall, contextClassImport, constructor), "");
    
    addCorrespondenceBetween(userExecution.getElement3(composedStructure, assemblyContext, compositeComponentJavaClass, encapsulatedComponentJavaClass, assemblyContextField, newConstructorCall, contextClassImport, constructor), userExecution.getElement4(composedStructure, assemblyContext, compositeComponentJavaClass, encapsulatedComponentJavaClass, assemblyContextField, newConstructorCall, contextClassImport, constructor), "");
    
    addCorrespondenceBetween(userExecution.getElement5(composedStructure, assemblyContext, compositeComponentJavaClass, encapsulatedComponentJavaClass, assemblyContextField, newConstructorCall, contextClassImport, constructor), userExecution.getElement6(composedStructure, assemblyContext, compositeComponentJavaClass, encapsulatedComponentJavaClass, assemblyContextField, newConstructorCall, contextClassImport, constructor), "");
    
    addCorrespondenceBetween(userExecution.getElement7(composedStructure, assemblyContext, compositeComponentJavaClass, encapsulatedComponentJavaClass, assemblyContextField, newConstructorCall, contextClassImport, constructor), userExecution.getElement8(composedStructure, assemblyContext, compositeComponentJavaClass, encapsulatedComponentJavaClass, assemblyContextField, newConstructorCall, contextClassImport, constructor), "");
    
    // val updatedElement userExecution.getElement9(composedStructure, assemblyContext, compositeComponentJavaClass, encapsulatedComponentJavaClass, assemblyContextField, newConstructorCall, contextClassImport, constructor);
    userExecution.update0Element(composedStructure, assemblyContext, compositeComponentJavaClass, encapsulatedComponentJavaClass, assemblyContextField, newConstructorCall, contextClassImport, constructor);
    
    // val updatedElement userExecution.getElement10(composedStructure, assemblyContext, compositeComponentJavaClass, encapsulatedComponentJavaClass, assemblyContextField, newConstructorCall, contextClassImport, constructor);
    userExecution.update1Element(composedStructure, assemblyContext, compositeComponentJavaClass, encapsulatedComponentJavaClass, assemblyContextField, newConstructorCall, contextClassImport, constructor);
    
    preprocessElementStates();
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
