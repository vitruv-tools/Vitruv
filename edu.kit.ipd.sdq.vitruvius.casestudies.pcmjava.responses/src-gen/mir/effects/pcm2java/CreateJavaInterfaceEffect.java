package mir.effects.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import java.io.IOException;
import mir.effects.pcm2java.EffectsFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.classifiers.impl.ClassifiersFactoryImpl;
import org.emftext.language.java.modifiers.ModifiersFactory;
import org.emftext.language.java.modifiers.Public;
import org.palladiosimulator.pcm.core.entity.NamedElement;

@SuppressWarnings("all")
public class CreateJavaInterfaceEffect extends AbstractEffectRealization {
  public CreateJavaInterfaceEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private NamedElement sourceElementMappedToClass;
  
  private org.emftext.language.java.containers.Package containingPackage;
  
  private String className;
  
  private boolean isSourceElementMappedToClassSet;
  
  private boolean isContainingPackageSet;
  
  private boolean isClassNameSet;
  
  public void setSourceElementMappedToClass(final NamedElement sourceElementMappedToClass) {
    this.sourceElementMappedToClass = sourceElementMappedToClass;
    this.isSourceElementMappedToClassSet = true;
  }
  
  public void setContainingPackage(final org.emftext.language.java.containers.Package containingPackage) {
    this.containingPackage = containingPackage;
    this.isContainingPackageSet = true;
  }
  
  public void setClassName(final String className) {
    this.className = className;
    this.isClassNameSet = true;
  }
  
  public boolean allParametersSet() {
    return isSourceElementMappedToClassSet&&isContainingPackageSet&&isClassNameSet;
  }
  
  private EObject getCorrepondenceSourceJavaInterface(final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className) {
    return sourceElementMappedToClass;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called effect CreateJavaInterfaceEffect with input:");
    getLogger().debug("   NamedElement: " + this.sourceElementMappedToClass);
    getLogger().debug("   Package: " + this.containingPackage);
    getLogger().debug("   void: " + this.className);
    
    Interface javaInterface = initializeCreateElementState(
    	() -> getCorrepondenceSourceJavaInterface(sourceElementMappedToClass, containingPackage, className), // correspondence source supplier
    	() -> ClassifiersFactoryImpl.eINSTANCE.createInterface(), // element creation supplier
    	() -> null, // tag supplier
    	Interface.class);
    preProcessElements();
    new mir.effects.pcm2java.CreateJavaInterfaceEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	sourceElementMappedToClass, containingPackage, className, javaInterface);
    postProcessElements();
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
    
    private void executeUserOperations(final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className, final Interface javaInterface) {
      javaInterface.setName(className);
      Public _createPublic = ModifiersFactory.eINSTANCE.createPublic();
      javaInterface.addModifier(_createPublic);
      this.effectFacade.callCreateCompilationUnit(sourceElementMappedToClass, javaInterface, containingPackage);
    }
  }
}
