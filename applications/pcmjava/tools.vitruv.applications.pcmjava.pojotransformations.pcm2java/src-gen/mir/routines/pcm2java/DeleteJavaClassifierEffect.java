package mir.routines.pcm2java;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.containers.CompilationUnit;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteJavaClassifierEffect extends AbstractEffectRealization {
  private RoutinesFacade effectFacade;
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getElement1(final NamedElement sourceElement, final ConcreteClassifier javaClassifier, final CompilationUnit compilationUnit) {
      return javaClassifier;
    }
    
    public EObject getElement2(final NamedElement sourceElement, final ConcreteClassifier javaClassifier, final CompilationUnit compilationUnit) {
      return compilationUnit;
    }
    
    public EObject getCorrepondenceSourceJavaClassifier(final NamedElement sourceElement) {
      return sourceElement;
    }
    
    public EObject getCorrepondenceSourceCompilationUnit(final NamedElement sourceElement, final ConcreteClassifier javaClassifier) {
      return sourceElement;
    }
  }
  
  private DeleteJavaClassifierEffect.EffectUserExecution userExecution;
  
  public DeleteJavaClassifierEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final NamedElement sourceElement) {
    super(responseExecutionState, calledBy);
    				this.userExecution = new mir.routines.pcm2java.DeleteJavaClassifierEffect.EffectUserExecution(getExecutionState(), this);
    				this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(getExecutionState(), this);
    				this.sourceElement = sourceElement;
  }
  
  private NamedElement sourceElement;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteJavaClassifierEffect with input:");
    getLogger().debug("   NamedElement: " + this.sourceElement);
    
    ConcreteClassifier javaClassifier = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJavaClassifier(sourceElement), // correspondence source supplier
    	ConcreteClassifier.class,
    	(ConcreteClassifier _element) -> true, // correspondence precondition checker
    	null);
    if (javaClassifier == null) {
    	return;
    }
    initializeRetrieveElementState(javaClassifier);
    CompilationUnit compilationUnit = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompilationUnit(sourceElement, javaClassifier), // correspondence source supplier
    	CompilationUnit.class,
    	(CompilationUnit _element) -> true, // correspondence precondition checker
    	null);
    if (compilationUnit == null) {
    	return;
    }
    initializeRetrieveElementState(compilationUnit);
    deleteObject(userExecution.getElement1(sourceElement, javaClassifier, compilationUnit));
    
    deleteObject(userExecution.getElement2(sourceElement, javaClassifier, compilationUnit));
    
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
