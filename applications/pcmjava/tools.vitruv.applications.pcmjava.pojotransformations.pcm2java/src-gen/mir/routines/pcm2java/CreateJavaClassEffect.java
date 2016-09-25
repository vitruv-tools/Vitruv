package mir.routines.pcm2java;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.impl.ClassifiersFactoryImpl;
import org.emftext.language.java.modifiers.ModifiersFactory;
import org.emftext.language.java.modifiers.Public;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateJavaClassEffect extends AbstractEffectRealization {
  private RoutinesFacade effectFacade;
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getElement1(final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className, final org.emftext.language.java.classifiers.Class javaClass) {
      return javaClass;
    }
    
    public void updateJavaClassElement(final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className, final org.emftext.language.java.classifiers.Class javaClass) {
      javaClass.setName(className);
      Public _createPublic = ModifiersFactory.eINSTANCE.createPublic();
      javaClass.addModifier(_createPublic);
    }
    
    public EObject getElement2(final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className, final org.emftext.language.java.classifiers.Class javaClass) {
      return sourceElementMappedToClass;
    }
    
    public void callRoutine1(final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className, final org.emftext.language.java.classifiers.Class javaClass, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createCompilationUnit(sourceElementMappedToClass, javaClass, containingPackage);
    }
  }
  
  private CreateJavaClassEffect.EffectUserExecution userExecution;
  
  public CreateJavaClassEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className) {
    super(responseExecutionState, calledBy);
    				this.userExecution = new mir.routines.pcm2java.CreateJavaClassEffect.EffectUserExecution(getExecutionState(), this);
    				this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(getExecutionState(), this);
    				this.sourceElementMappedToClass = sourceElementMappedToClass;this.containingPackage = containingPackage;this.className = className;
  }
  
  private NamedElement sourceElementMappedToClass;
  
  private org.emftext.language.java.containers.Package containingPackage;
  
  private String className;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateJavaClassEffect with input:");
    getLogger().debug("   NamedElement: " + this.sourceElementMappedToClass);
    getLogger().debug("   Package: " + this.containingPackage);
    getLogger().debug("   String: " + this.className);
    
    org.emftext.language.java.classifiers.Class javaClass = ClassifiersFactoryImpl.eINSTANCE.createClass();
    initializeCreateElementState(javaClass);
    userExecution.updateJavaClassElement(sourceElementMappedToClass, containingPackage, className, javaClass);
    
    addCorrespondenceBetween(userExecution.getElement1(sourceElementMappedToClass, containingPackage, className, javaClass), userExecution.getElement2(sourceElementMappedToClass, containingPackage, className, javaClass), "");
    
    preprocessElementStates();
    userExecution.callRoutine1(
    	sourceElementMappedToClass, containingPackage, className, javaClass, effectFacade);
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
