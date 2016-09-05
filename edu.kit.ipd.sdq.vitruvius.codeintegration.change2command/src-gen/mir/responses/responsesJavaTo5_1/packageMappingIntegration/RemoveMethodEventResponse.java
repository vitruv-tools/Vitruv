package mir.responses.responsesJavaTo5_1.packageMappingIntegration;

import edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.response.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.change.echange.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.change.echange.feature.reference.RemoveEReference;
import edu.kit.ipd.sdq.vitruvius.framework.userinteraction.UserInteracting;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.members.Method;

@SuppressWarnings("all")
class RemoveMethodEventResponse extends AbstractResponseRealization {
  public RemoveMethodEventResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  private boolean checkTriggerPrecondition(final RemoveEReference<ConcreteClassifier, Member> change) {
    Member _oldValue = change.getOldValue();
    if ((_oldValue instanceof Method)) {
      return true;
    }
    return false;
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return RemoveEReference.class;
  }
  
  private boolean checkChangeProperties(final RemoveEReference<ConcreteClassifier, Member> change) {
    EObject changedElement = change.getAffectedEObject();
    // Check model element type
    if (!(changedElement instanceof ConcreteClassifier)) {
    	return false;
    }
    
    // Check feature
    if (!change.getAffectedFeature().getName().equals("members")) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof RemoveEReference<?, ?>)) {
    	return false;
    }
    RemoveEReference typedChange = (RemoveEReference)change;
    if (!checkChangeProperties(typedChange)) {
    	return false;
    }
    if (!checkTriggerPrecondition(typedChange)) {
    	return false;
    }
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  public void executeResponse(final EChange change) {
    RemoveEReference<ConcreteClassifier, Member> typedChange = (RemoveEReference<ConcreteClassifier, Member>)change;
    mir.routines.packageMappingIntegration.RemoveMethodEventEffect effect = new mir.routines.packageMappingIntegration.RemoveMethodEventEffect(this.executionState, this, typedChange);
    effect.applyRoutine();
  }
}
