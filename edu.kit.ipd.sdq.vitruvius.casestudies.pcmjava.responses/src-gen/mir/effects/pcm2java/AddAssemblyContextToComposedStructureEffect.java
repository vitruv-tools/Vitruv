package mir.effects.pcm2java;

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.responses.pcm2java.Pcm2JavaHelper;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import java.io.IOException;
import mir.effects.pcm2java.EffectsFacade;
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
  
  public boolean allParametersSet() {
    return isCompositeComponentClassSet&&isAssemblyContextSet;
  }
  
  private EObject getCorrepondenceSourceConstructor(final org.emftext.language.java.classifiers.Class compositeComponentClass, final AssemblyContext assemblyContext) {
    return assemblyContext;
  }
  
  private EObject getCorrepondenceSourceEncapsulatedComponentJavaClass(final org.emftext.language.java.classifiers.Class compositeComponentClass, final AssemblyContext assemblyContext) {
    RepositoryComponent _encapsulatedComponent__AssemblyContext = assemblyContext.getEncapsulatedComponent__AssemblyContext();
    return _encapsulatedComponent__AssemblyContext;
  }
  
  private EObject getCorrepondenceSourceAssemblyContextField(final org.emftext.language.java.classifiers.Class compositeComponentClass, final AssemblyContext assemblyContext) {
    return assemblyContext;
  }
  
  private EObject getCorrepondenceSourceContextClassImport(final org.emftext.language.java.classifiers.Class compositeComponentClass, final AssemblyContext assemblyContext) {
    return assemblyContext;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called effect AddAssemblyContextToComposedStructureEffect with input:");
    getLogger().debug("   Class: " + this.compositeComponentClass);
    getLogger().debug("   AssemblyContext: " + this.assemblyContext);
    
    Field assemblyContextField = initializeCreateElementState(
    	() -> getCorrepondenceSourceAssemblyContextField(compositeComponentClass, assemblyContext), // correspondence source supplier
    	() -> MembersFactoryImpl.eINSTANCE.createField(), // element creation supplier
    	() -> null, // tag supplier
    	Field.class);
    NewConstructorCall newConstructorCall = initializeCreateElementState(
    	() -> getCorrepondenceSourceNewConstructorCall(compositeComponentClass, assemblyContext), // correspondence source supplier
    	() -> InstantiationsFactoryImpl.eINSTANCE.createNewConstructorCall(), // element creation supplier
    	() -> null, // tag supplier
    	NewConstructorCall.class);
    ClassifierImport contextClassImport = initializeCreateElementState(
    	() -> getCorrepondenceSourceContextClassImport(compositeComponentClass, assemblyContext), // correspondence source supplier
    	() -> ImportsFactoryImpl.eINSTANCE.createClassifierImport(), // element creation supplier
    	() -> null, // tag supplier
    	ClassifierImport.class);
    Constructor constructor = initializeCreateElementState(
    	() -> getCorrepondenceSourceConstructor(compositeComponentClass, assemblyContext), // correspondence source supplier
    	() -> MembersFactoryImpl.eINSTANCE.createConstructor(), // element creation supplier
    	() -> null, // tag supplier
    	Constructor.class);
    org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceEncapsulatedComponentJavaClass(compositeComponentClass, assemblyContext), // correspondence source supplier
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	org.emftext.language.java.classifiers.Class.class,	false);
    preProcessElements();
    new mir.effects.pcm2java.AddAssemblyContextToComposedStructureEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	compositeComponentClass, assemblyContext, assemblyContextField, newConstructorCall, contextClassImport, constructor, encapsulatedComponentJavaClass);
    postProcessElements();
  }
  
  private EObject getCorrepondenceSourceNewConstructorCall(final org.emftext.language.java.classifiers.Class compositeComponentClass, final AssemblyContext assemblyContext) {
    return assemblyContext;
  }
  
  private static class EffectUserExecution {
    private Blackboard blackboard;
    
    private UserInteracting userInteracting;
    
    private TransformationResult transformationResult;
    
    @Extension
    private EffectsFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      this.blackboard = responseExecutionState.getBlackboard();
      this.userInteracting = responseExecutionState.getUserInteracting();
      this.transformationResult = responseExecutionState.getTransformationResult();
      this.effectFacade = new EffectsFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final org.emftext.language.java.classifiers.Class compositeComponentClass, final AssemblyContext assemblyContext, final Field assemblyContextField, final NewConstructorCall newConstructorCall, final ClassifierImport contextClassImport, final Constructor constructor, final org.emftext.language.java.classifiers.Class encapsulatedComponentJavaClass) {
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
