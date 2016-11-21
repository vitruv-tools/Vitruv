package mir.reactions.reactionsUMLToJava.umlToJava;

import mir.routines.umlToJava.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class CreatedUmlClassReaction extends AbstractReactionRealization {
  public CreatedUmlClassReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  private boolean checkTriggerPrecondition(final InsertEReference<Model, PackageableElement> change) {
    PackageableElement _newValue = change.getNewValue();
    return (_newValue instanceof org.eclipse.uml2.uml.Class);
  }
  
  public void executeReaction(final EChange change) {
    InsertEReference<org.eclipse.uml2.uml.Model, org.eclipse.uml2.uml.PackageableElement> typedChange = (InsertEReference<org.eclipse.uml2.uml.Model, org.eclipse.uml2.uml.PackageableElement>)change;
    mir.routines.umlToJava.RoutinesFacade routinesFacade = new mir.routines.umlToJava.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsUMLToJava.umlToJava.CreatedUmlClassReaction.ActionUserExecution userExecution = new mir.reactions.reactionsUMLToJava.umlToJava.CreatedUmlClassReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(typedChange, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return InsertEReference.class;
  }
  
  private boolean checkChangeProperties(final InsertEReference<Model, PackageableElement> change) {
    EObject changedElement = change.getAffectedEObject();
    // Check model element type
    if (!(changedElement instanceof Model)) {
    	return false;
    }
    
    // Check feature
    if (!change.getAffectedFeature().getName().equals("packagedElement")) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof InsertEReference<?, ?>)) {
    	return false;
    }
    InsertEReference typedChange = (InsertEReference)change;
    if (!checkChangeProperties(typedChange)) {
    	return false;
    }
    if (!checkTriggerPrecondition(typedChange)) {
    	return false;
    }
    getLogger().debug("Passed precondition check of reaction " + this.getClass().getName());
    return true;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final InsertEReference<Model, PackageableElement> change, @Extension final RoutinesFacade _routinesFacade) {
      PackageableElement _newValue = change.getNewValue();
      _routinesFacade.createJavaClass(((org.eclipse.uml2.uml.Class) _newValue));
    }
  }
}
