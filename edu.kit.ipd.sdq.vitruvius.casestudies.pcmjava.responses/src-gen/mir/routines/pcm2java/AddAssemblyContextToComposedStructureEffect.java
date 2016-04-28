package mir.routines.pcm2java;

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.responses.pcm2java.Pcm2JavaHelper;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
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
import org.palladiosimulator.pcm.repository.RepositoryComponent;

@SuppressWarnings("all")
public class AddAssemblyContextToComposedStructureEffect extends AbstractEffectRealization {
  public AddAssemblyContextToComposedStructureEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private org.emftext.language.java.classifiers.Class compositeComponentClass;
  
  private AssemblyContext assemblyContext;
  
  private boolean isCompositeComponentClassSet;
  
  private boolean isAssemblyContextSet;
  
  public void setCompositeComponentClass(final org.emftext.language.java.classifiers.Class compositeComponentClass) {
    this.compositeComponentClass = compositeComponentClass;
    this.isCompositeComponentClassSet = true;
  }
  
  public void setAssemblyContext(final AssemblyContext assemblyContext) {
    this.assemblyContext = assemblyContext;
    this.isAssemblyContextSet = true;
  }
  
  private EObject getElement0(final org.emftext.language.java.classifiers.Class compositeComponentClass, final AssemblyContext assemblyContext, final org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass, final Field assemblyContextField, final NewConstructorCall newConstructorCall, final ClassifierImport contextClassImport, final Constructor constructor) {
    return assemblyContextField;
  }
  
  private EObject getElement1(final org.emftext.language.java.classifiers.Class compositeComponentClass, final AssemblyContext assemblyContext, final org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass, final Field assemblyContextField, final NewConstructorCall newConstructorCall, final ClassifierImport contextClassImport, final Constructor constructor) {
    return newConstructorCall;
  }
  
  public boolean allParametersSet() {
    return isCompositeComponentClassSet&&isAssemblyContextSet;
  }
  
  private EObject getCorrepondenceSourceEncapsulatedComponentJavaClass(final org.emftext.language.java.classifiers.Class compositeComponentClass, final AssemblyContext assemblyContext) {
    RepositoryComponent _encapsulatedComponent__AssemblyContext = assemblyContext.getEncapsulatedComponent__AssemblyContext();
    return _encapsulatedComponent__AssemblyContext;
  }
  
  private EObject getElement4(final org.emftext.language.java.classifiers.Class compositeComponentClass, final AssemblyContext assemblyContext, final org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass, final Field assemblyContextField, final NewConstructorCall newConstructorCall, final ClassifierImport contextClassImport, final Constructor constructor) {
    return assemblyContext;
  }
  
  private EObject getElement5(final org.emftext.language.java.classifiers.Class compositeComponentClass, final AssemblyContext assemblyContext, final org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass, final Field assemblyContextField, final NewConstructorCall newConstructorCall, final ClassifierImport contextClassImport, final Constructor constructor) {
    return assemblyContext;
  }
  
  private EObject getElement2(final org.emftext.language.java.classifiers.Class compositeComponentClass, final AssemblyContext assemblyContext, final org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass, final Field assemblyContextField, final NewConstructorCall newConstructorCall, final ClassifierImport contextClassImport, final Constructor constructor) {
    return contextClassImport;
  }
  
  private EObject getElement3(final org.emftext.language.java.classifiers.Class compositeComponentClass, final AssemblyContext assemblyContext, final org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass, final Field assemblyContextField, final NewConstructorCall newConstructorCall, final ClassifierImport contextClassImport, final Constructor constructor) {
    return constructor;
  }
  
  private EObject getElement6(final org.emftext.language.java.classifiers.Class compositeComponentClass, final AssemblyContext assemblyContext, final org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass, final Field assemblyContextField, final NewConstructorCall newConstructorCall, final ClassifierImport contextClassImport, final Constructor constructor) {
    return assemblyContext;
  }
  
  private EObject getElement7(final org.emftext.language.java.classifiers.Class compositeComponentClass, final AssemblyContext assemblyContext, final org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass, final Field assemblyContextField, final NewConstructorCall newConstructorCall, final ClassifierImport contextClassImport, final Constructor constructor) {
    return assemblyContext;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called routine AddAssemblyContextToComposedStructureEffect with input:");
    getLogger().debug("   Class: " + this.compositeComponentClass);
    getLogger().debug("   AssemblyContext: " + this.assemblyContext);
    
    org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceEncapsulatedComponentJavaClass(compositeComponentClass, assemblyContext), // correspondence source supplier
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	org.emftext.language.java.classifiers.Class.class,
    	false, true, false);
    if (isAborted()) {
    	return;
    }
    Field assemblyContextField = MembersFactoryImpl.eINSTANCE.createField();
    initializeCreateElementState(assemblyContextField);
    NewConstructorCall newConstructorCall = InstantiationsFactoryImpl.eINSTANCE.createNewConstructorCall();
    initializeCreateElementState(newConstructorCall);
    ClassifierImport contextClassImport = ImportsFactoryImpl.eINSTANCE.createClassifierImport();
    initializeCreateElementState(contextClassImport);
    Constructor constructor = MembersFactoryImpl.eINSTANCE.createConstructor();
    initializeCreateElementState(constructor);
    
    addCorrespondenceBetween(getElement0(compositeComponentClass, assemblyContext, encapsulatedComponentJavaClass, assemblyContextField, newConstructorCall, contextClassImport, constructor), getElement4(compositeComponentClass, assemblyContext, encapsulatedComponentJavaClass, assemblyContextField, newConstructorCall, contextClassImport, constructor), "");
    addCorrespondenceBetween(getElement1(compositeComponentClass, assemblyContext, encapsulatedComponentJavaClass, assemblyContextField, newConstructorCall, contextClassImport, constructor), getElement5(compositeComponentClass, assemblyContext, encapsulatedComponentJavaClass, assemblyContextField, newConstructorCall, contextClassImport, constructor), "");
    addCorrespondenceBetween(getElement2(compositeComponentClass, assemblyContext, encapsulatedComponentJavaClass, assemblyContextField, newConstructorCall, contextClassImport, constructor), getElement6(compositeComponentClass, assemblyContext, encapsulatedComponentJavaClass, assemblyContextField, newConstructorCall, contextClassImport, constructor), "");
    addCorrespondenceBetween(getElement3(compositeComponentClass, assemblyContext, encapsulatedComponentJavaClass, assemblyContextField, newConstructorCall, contextClassImport, constructor), getElement7(compositeComponentClass, assemblyContext, encapsulatedComponentJavaClass, assemblyContextField, newConstructorCall, contextClassImport, constructor), "");
    preProcessElements();
    new mir.routines.pcm2java.AddAssemblyContextToComposedStructureEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	compositeComponentClass, assemblyContext, encapsulatedComponentJavaClass, assemblyContextField, newConstructorCall, contextClassImport, constructor);
    postProcessElements();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final org.emftext.language.java.classifiers.Class compositeComponentClass, final AssemblyContext assemblyContext, final org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass, final Field assemblyContextField, final NewConstructorCall newConstructorCall, final ClassifierImport contextClassImport, final Constructor constructor) {
      final TypeReference typeRef = Pcm2JavaHelper.createNamespaceClassifierReference(encapsulatedComponentJavaClass);
      String _entityName = assemblyContext.getEntityName();
      Pcm2JavaHelper.createPrivateField(assemblyContextField, typeRef, _entityName);
      EList<Member> _members = compositeComponentClass.getMembers();
      _members.add(assemblyContextField);
      Pcm2JavaHelper.addConstructorToClass(constructor, compositeComponentClass);
      Pcm2JavaHelper.addImportToCompilationUnitOfClassifier(contextClassImport, compositeComponentClass, encapsulatedComponentJavaClass);
      Pcm2JavaHelper.createNewForFieldInConstructor(newConstructorCall, constructor, assemblyContextField);
    }
  }
}
