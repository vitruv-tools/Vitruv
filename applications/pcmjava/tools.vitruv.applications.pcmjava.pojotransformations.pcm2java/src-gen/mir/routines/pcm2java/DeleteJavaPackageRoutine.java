package mir.routines.pcm2java;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteJavaPackageRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private DeleteJavaPackageRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final NamedElement sourceElementMappedToPackage, final String packageName, final String expectedTag, final org.emftext.language.java.containers.Package javaPackage) {
      return javaPackage;
    }
    
    public String getRetrieveTag1(final NamedElement sourceElementMappedToPackage, final String packageName, final String expectedTag) {
      return expectedTag;
    }
    
    public EObject getCorrepondenceSourceJavaPackage(final NamedElement sourceElementMappedToPackage, final String packageName, final String expectedTag) {
      return sourceElementMappedToPackage;
    }
  }
  
  public DeleteJavaPackageRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final NamedElement sourceElementMappedToPackage, final String packageName, final String expectedTag) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2java.DeleteJavaPackageRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcm2java.RoutinesFacade(getExecutionState(), this);
    this.sourceElementMappedToPackage = sourceElementMappedToPackage;this.packageName = packageName;this.expectedTag = expectedTag;
  }
  
  private NamedElement sourceElementMappedToPackage;
  
  private String packageName;
  
  private String expectedTag;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteJavaPackageRoutine with input:");
    getLogger().debug("   NamedElement: " + this.sourceElementMappedToPackage);
    getLogger().debug("   String: " + this.packageName);
    getLogger().debug("   String: " + this.expectedTag);
    
    org.emftext.language.java.containers.Package javaPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJavaPackage(sourceElementMappedToPackage, packageName, expectedTag), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(sourceElementMappedToPackage, packageName, expectedTag));
    if (javaPackage == null) {
    	return;
    }
    initializeRetrieveElementState(javaPackage);
    deleteObject(userExecution.getElement1(sourceElementMappedToPackage, packageName, expectedTag, javaPackage));
    
    postprocessElementStates();
  }
}
