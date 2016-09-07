package mir.responses.responsesJavaTo5_1.packageMappingIntegration;

import tools.vitruv.extensions.dslsruntime.response.AbstractResponseRealization;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.containers.JavaRoot;
import org.emftext.language.java.imports.Import;

@SuppressWarnings("all")
class AddImportResponseResponse extends AbstractResponseRealization {
  public AddImportResponseResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return InsertEReference.class;
  }
  
  private boolean checkChangeProperties(final InsertEReference<JavaRoot, Import> change) {
    EObject changedElement = change.getAffectedEObject();
    // Check model element type
    if (!(changedElement instanceof JavaRoot)) {
    	return false;
    }
    
    // Check feature
    if (!change.getAffectedFeature().getName().equals("imports")) {
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
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  public void executeResponse(final EChange change) {
    InsertEReference<JavaRoot, Import> typedChange = (InsertEReference<JavaRoot, Import>)change;
    mir.routines.packageMappingIntegration.AddImportResponseEffect effect = new mir.routines.packageMappingIntegration.AddImportResponseEffect(this.executionState, this, typedChange);
    effect.applyRoutine();
  }
}
