package mir.routines.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.containers.CompilationUnit;
import org.palladiosimulator.pcm.core.entity.NamedElement;

@SuppressWarnings("all")
public class DeleteJavaClassifierEffect extends AbstractEffectRealization {
  public DeleteJavaClassifierEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private NamedElement sourceElement;
  
  private boolean isSourceElementSet;
  
  public void setSourceElement(final NamedElement sourceElement) {
    this.sourceElement = sourceElement;
    this.isSourceElementSet = true;
  }
  
  private EObject getElement0(final NamedElement sourceElement, final ConcreteClassifier javaClassifier, final CompilationUnit compilationUnit) {
    return javaClassifier;
  }
  
  private EObject getElement1(final NamedElement sourceElement, final ConcreteClassifier javaClassifier, final CompilationUnit compilationUnit) {
    return compilationUnit;
  }
  
  public boolean allParametersSet() {
    return isSourceElementSet;
  }
  
  private EObject getCorrepondenceSourceJavaClassifier(final NamedElement sourceElement) {
    return sourceElement;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called routine DeleteJavaClassifierEffect with input:");
    getLogger().debug("   NamedElement: " + this.sourceElement);
    
    ConcreteClassifier javaClassifier = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceJavaClassifier(sourceElement), // correspondence source supplier
    	(ConcreteClassifier _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	ConcreteClassifier.class,
    	false, true, false);
    CompilationUnit compilationUnit = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceCompilationUnit(sourceElement), // correspondence source supplier
    	(CompilationUnit _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	CompilationUnit.class,
    	false, true, false);
    if (isAborted()) {
    	return;
    }
    markObjectDelete(getElement0(sourceElement, javaClassifier, compilationUnit));
    markObjectDelete(getElement1(sourceElement, javaClassifier, compilationUnit));
    
    preProcessElements();
    postProcessElements();
  }
  
  private EObject getCorrepondenceSourceCompilationUnit(final NamedElement sourceElement) {
    return sourceElement;
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new RoutinesFacade(responseExecutionState, calledBy);
    }
  }
}
