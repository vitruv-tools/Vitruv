package mir.routines.pcm2java;

import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.containers.CompilationUnit;
import org.palladiosimulator.pcm.core.entity.NamedElement;

@SuppressWarnings("all")
public class DeleteJavaClassifierEffect extends AbstractEffectRealization {
  public DeleteJavaClassifierEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final NamedElement sourceElement) {
    super(responseExecutionState, calledBy);
    				this.sourceElement = sourceElement;
  }
  
  private NamedElement sourceElement;
  
  private EObject getElement0(final NamedElement sourceElement, final ConcreteClassifier javaClassifier, final CompilationUnit compilationUnit) {
    return javaClassifier;
  }
  
  private EObject getElement1(final NamedElement sourceElement, final ConcreteClassifier javaClassifier, final CompilationUnit compilationUnit) {
    return compilationUnit;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteJavaClassifierEffect with input:");
    getLogger().debug("   NamedElement: " + this.sourceElement);
    
    ConcreteClassifier javaClassifier = getCorrespondingElement(
    	getCorrepondenceSourceJavaClassifier(sourceElement), // correspondence source supplier
    	ConcreteClassifier.class,
    	(ConcreteClassifier _element) -> true, // correspondence precondition checker
    	null);
    if (javaClassifier == null) {
    	return;
    }
    initializeRetrieveElementState(javaClassifier);
    CompilationUnit compilationUnit = getCorrespondingElement(
    	getCorrepondenceSourceCompilationUnit(sourceElement), // correspondence source supplier
    	CompilationUnit.class,
    	(CompilationUnit _element) -> true, // correspondence precondition checker
    	null);
    if (compilationUnit == null) {
    	return;
    }
    initializeRetrieveElementState(compilationUnit);
    deleteObject(getElement0(sourceElement, javaClassifier, compilationUnit));
    deleteObject(getElement1(sourceElement, javaClassifier, compilationUnit));
    
    preprocessElementStates();
    postprocessElementStates();
  }
  
  private EObject getCorrepondenceSourceJavaClassifier(final NamedElement sourceElement) {
    return sourceElement;
  }
  
  private EObject getCorrepondenceSourceCompilationUnit(final NamedElement sourceElement) {
    return sourceElement;
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
  }
}
