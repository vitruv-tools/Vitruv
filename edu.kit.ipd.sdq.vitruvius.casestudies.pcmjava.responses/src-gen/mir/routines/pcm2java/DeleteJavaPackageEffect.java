package mir.routines.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
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
  
  private EObject getElement0(final NamedElement sourceElementMappedToPackage, final String packageName, final String expectedTag, final org.emftext.language.java.containers.Package javaPackage) {
    return javaPackage;
  }
  
  public boolean allParametersSet() {
    return isSourceElementMappedToPackageSet&&isPackageNameSet&&isExpectedTagSet;
  }
  
  private String getRetrieveTag0(final NamedElement sourceElementMappedToPackage, final String packageName, final String expectedTag) {
    return expectedTag;
  }
  
  private EObject getCorrepondenceSourceJavaPackage(final NamedElement sourceElementMappedToPackage, final String packageName, final String expectedTag) {
    return sourceElementMappedToPackage;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called routine DeleteJavaPackageEffect with input:");
    getLogger().debug("   NamedElement: " + this.sourceElementMappedToPackage);
    getLogger().debug("   String: " + this.packageName);
    getLogger().debug("   String: " + this.expectedTag);
    
    org.emftext.language.java.containers.Package javaPackage = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceJavaPackage(sourceElementMappedToPackage, packageName, expectedTag), // correspondence source supplier
    	(org.emftext.language.java.containers.Package _element) -> true, // correspondence precondition checker
    	() -> getRetrieveTag0(sourceElementMappedToPackage, packageName, expectedTag), // tag supplier
    	org.emftext.language.java.containers.Package.class,
    	false, true, false);
    if (isAborted()) {
    	return;
    }
    markObjectDelete(getElement0(sourceElementMappedToPackage, packageName, expectedTag, javaPackage));
    
    preProcessElements();
    postProcessElements();
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
