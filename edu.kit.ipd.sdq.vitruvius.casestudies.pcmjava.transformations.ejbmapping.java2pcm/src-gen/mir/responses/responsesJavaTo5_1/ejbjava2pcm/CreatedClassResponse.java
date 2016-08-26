package mir.responses.responsesJavaTo5_1.ejbjava2pcm;

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.ejbmapping.java2pcm.EJBAnnotationHelper;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.InsertEReference;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.containers.CompilationUnit;

@SuppressWarnings("all")
class CreatedClassResponse extends AbstractResponseRealization {
  public CreatedClassResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  private boolean checkTriggerPrecondition(final InsertEReference<CompilationUnit, ConcreteClassifier> change) {
    ConcreteClassifier _newValue = change.getNewValue();
    boolean _isEJBClass = EJBAnnotationHelper.isEJBClass(_newValue);
    return _isEJBClass;
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return InsertEReference.class;
  }
  
  private boolean checkChangeProperties(final InsertEReference<CompilationUnit, ConcreteClassifier> change) {
    EObject changedElement = change.getAffectedEObject();
    // Check model element type
    if (!(changedElement instanceof CompilationUnit)) {
    	return false;
    }
    
    // Check feature
    if (!change.getAffectedFeature().getName().equals("classifiers")) {
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
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  public void executeResponse(final EChange change) {
    InsertEReference<CompilationUnit, ConcreteClassifier> typedChange = (InsertEReference<CompilationUnit, ConcreteClassifier>)change;
    mir.routines.ejbjava2pcm.CreatedClassEffect effect = new mir.routines.ejbjava2pcm.CreatedClassEffect(this.executionState, this, typedChange);
    effect.applyRoutine();
  }
}
