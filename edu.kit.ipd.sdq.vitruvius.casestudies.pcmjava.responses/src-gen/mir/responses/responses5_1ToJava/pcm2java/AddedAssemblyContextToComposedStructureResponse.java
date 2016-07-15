package mir.responses.responses5_1ToJava.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.ComposedStructure;

@SuppressWarnings("all")
class AddedAssemblyContextToComposedStructureResponse extends AbstractResponseRealization {
  public AddedAssemblyContextToComposedStructureResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return CreateNonRootEObjectInList.class;
  }
  
  private boolean checkChangeProperties(final CreateNonRootEObjectInList<AssemblyContext> change) {
    EObject changedElement = change.getOldAffectedEObject();
    // Check model element type
    if (!(changedElement instanceof ComposedStructure)) {
    	return false;
    }
    
    // Check feature
    if (!change.getAffectedFeature().getName().equals("assemblyContexts__ComposedStructure")) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof CreateNonRootEObjectInList<?>)) {
    	return false;
    }
    CreateNonRootEObjectInList typedChange = (CreateNonRootEObjectInList)change;
    if (!checkChangeProperties(typedChange)) {
    	return false;
    }
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  public void executeResponse(final EChange change) {
    CreateNonRootEObjectInList<AssemblyContext> typedChange = (CreateNonRootEObjectInList<AssemblyContext>)change;
    mir.routines.pcm2java.AddedAssemblyContextToComposedStructureEffect effect = new mir.routines.pcm2java.AddedAssemblyContextToComposedStructureEffect(this.executionState, this, typedChange);
    effect.applyRoutine();
  }
}
