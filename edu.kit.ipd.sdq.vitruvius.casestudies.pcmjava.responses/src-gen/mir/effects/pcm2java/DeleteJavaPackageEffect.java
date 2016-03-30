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
import org.palladiosimulator.pcm.core.entity.NamedElement;

@SuppressWarnings("all")
public class DeleteJavaPackageEffect extends AbstractEffectRealization {
  public DeleteJavaPackageEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private NamedElement sourceElementMappedToPackage;
  
  private String packageName;
  
  private String expectedTag;
  
  private boolean isSourceElementMappedToPackageSet;
  
  private boolean isPackageNameSet;
  
  private boolean isExpectedTagSet;
  
  public void setSourceElementMappedToPackage(final NamedElement sourceElementMappedToPackage) {
    this.sourceElementMappedToPackage = sourceElementMappedToPackage;
    this.isSourceElementMappedToPackageSet = true;
  }
  
  public void setPackageName(final String packageName) {
    this.packageName = packageName;
    this.isPackageNameSet = true;
  }
  
  public void setExpectedTag(final String expectedTag) {
    this.expectedTag = expectedTag;
    this.isExpectedTagSet = true;
  }
  
  private String getTagJavaPackage(final NamedElement sourceElementMappedToPackage, final String packageName, final String expectedTag) {
    return expectedTag;
  }
  
  public boolean allParametersSet() {
    return isSourceElementMappedToPackageSet&&isPackageNameSet&&isExpectedTagSet;
  }
  
  private EObject getCorrepondenceSourceJavaPackage(final NamedElement sourceElementMappedToPackage, final String packageName, final String expectedTag) {
    return sourceElementMappedToPackage;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called effect DeleteJavaPackageEffect with input:");
    getLogger().debug("   NamedElement: " + this.sourceElementMappedToPackage);
    getLogger().debug("   String: " + this.packageName);
    getLogger().debug("   String: " + this.expectedTag);
    
    org.emftext.language.java.containers.Package javaPackage = initializeDeleteElementState(
    	() -> getCorrepondenceSourceJavaPackage(sourceElementMappedToPackage, packageName, expectedTag), // correspondence source supplier
    	(org.emftext.language.java.containers.Package _element) -> true, // correspondence precondition checker
    	() -> getTagJavaPackage(sourceElementMappedToPackage, packageName, expectedTag), // tag supplier
    	org.emftext.language.java.containers.Package.class,	false);
    preProcessElements();
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
  }
}
