package mir.routines.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.impl.ClassifiersFactoryImpl;
import org.emftext.language.java.modifiers.ModifiersFactory;
import org.emftext.language.java.modifiers.Public;
import org.palladiosimulator.pcm.core.entity.NamedElement;

@SuppressWarnings("all")
public class CreateJavaClassEffect extends AbstractEffectRealization {
  public CreateJavaClassEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
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
  
  private EObject getElement0(final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className, final org.emftext.language.java.classifiers.Class javaClass) {
    return javaClass;
  }
  
  private EObject getElement1(final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className, final org.emftext.language.java.classifiers.Class javaClass) {
    return sourceElementMappedToClass;
  }
  
  public boolean allParametersSet() {
    return isSourceElementMappedToClassSet&&isContainingPackageSet&&isClassNameSet;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called routine CreateJavaClassEffect with input:");
    getLogger().debug("   NamedElement: " + this.sourceElementMappedToClass);
    getLogger().debug("   Package: " + this.containingPackage);
    getLogger().debug("   String: " + this.className);
    
    if (isAborted()) {
    	return;
    }
    org.emftext.language.java.classifiers.Class javaClass = ClassifiersFactoryImpl.eINSTANCE.createClass();
    initializeCreateElementState(javaClass);
    
    addCorrespondenceBetween(getElement0(sourceElementMappedToClass, containingPackage, className, javaClass), getElement1(sourceElementMappedToClass, containingPackage, className, javaClass), "");
    preProcessElements();
    new mir.routines.pcm2java.CreateJavaClassEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	sourceElementMappedToClass, containingPackage, className, javaClass);
    postProcessElements();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className, final org.emftext.language.java.classifiers.Class javaClass) {
      javaClass.setName(className);
      Public _createPublic = ModifiersFactory.eINSTANCE.createPublic();
      javaClass.addModifier(_createPublic);
      this.effectFacade.callCreateCompilationUnit(sourceElementMappedToClass, javaClass, containingPackage);
    }
  }
}
