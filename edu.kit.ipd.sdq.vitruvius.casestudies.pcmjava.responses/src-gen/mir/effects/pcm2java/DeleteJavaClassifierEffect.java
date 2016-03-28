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
  
  public boolean allParametersSet() {
    return isSourceElementSet;
  }
  
  private EObject getCorrepondenceSourceJavaClassifier(final NamedElement sourceElement) {
    return sourceElement;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called effect DeleteJavaClassifierEffect with input:");
    getLogger().debug("   NamedElement: " + this.sourceElement);
    
    ConcreteClassifier javaClassifier = initializeDeleteElementState(
    	() -> getCorrepondenceSourceJavaClassifier(sourceElement), // correspondence source supplier
    	(ConcreteClassifier _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	ConcreteClassifier.class,	false);
    CompilationUnit compilationUnit = initializeDeleteElementState(
    	() -> getCorrepondenceSourceCompilationUnit(sourceElement), // correspondence source supplier
    	(CompilationUnit _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	CompilationUnit.class,	false);
    preProcessElements();
    postProcessElements();
  }
  
  private EObject getCorrepondenceSourceCompilationUnit(final NamedElement sourceElement) {
    return sourceElement;
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
  }
}
