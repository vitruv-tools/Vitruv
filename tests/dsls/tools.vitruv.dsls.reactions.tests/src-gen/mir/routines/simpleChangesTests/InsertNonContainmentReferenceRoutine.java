package mir.routines.simpleChangesTests;

import allElementTypes.NonRoot;
import allElementTypes.NonRootObjectContainerHelper;
import allElementTypes.Root;
import com.google.common.base.Objects;
import java.io.IOException;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import tools.vitruv.dsls.reactions.tests.simpleChangesTests.SimpleChangesTestsExecutionMonitor;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class InsertNonContainmentReferenceRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private InsertNonContainmentReferenceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceTargetElement(final Root rootElement, final NonRoot insertedNonRoot) {
      return rootElement;
    }
    
    public EObject getElement1(final Root rootElement, final NonRoot insertedNonRoot, final Root targetElement) {
      return targetElement;
    }
    
    public void update0Element(final Root rootElement, final NonRoot insertedNonRoot, final Root targetElement) {
      NonRootObjectContainerHelper _nonRootObjectContainerHelper = targetElement.getNonRootObjectContainerHelper();
      EList<NonRoot> _nonRootObjectsContainment = _nonRootObjectContainerHelper.getNonRootObjectsContainment();
      final Function1<NonRoot, Boolean> _function = (NonRoot it) -> {
        String _id = it.getId();
        String _id_1 = insertedNonRoot.getId();
        return Boolean.valueOf(Objects.equal(_id, _id_1));
      };
      final NonRoot addedNonRoot = IterableExtensions.<NonRoot>findFirst(_nonRootObjectsContainment, _function);
      EList<NonRoot> _multiValuedNonContainmentEReference = targetElement.getMultiValuedNonContainmentEReference();
      _multiValuedNonContainmentEReference.add(addedNonRoot);
    }
    
    public void callRoutine1(final Root rootElement, final NonRoot insertedNonRoot, final Root targetElement, @Extension final RoutinesFacade _routinesFacade) {
      SimpleChangesTestsExecutionMonitor _instance = SimpleChangesTestsExecutionMonitor.getInstance();
      _instance.set(SimpleChangesTestsExecutionMonitor.ChangeType.InsertNonContainmentEReference);
    }
  }
  
  public InsertNonContainmentReferenceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Root rootElement, final NonRoot insertedNonRoot) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.simpleChangesTests.InsertNonContainmentReferenceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.simpleChangesTests.RoutinesFacade(getExecutionState(), this);
    this.rootElement = rootElement;this.insertedNonRoot = insertedNonRoot;
  }
  
  private Root rootElement;
  
  private NonRoot insertedNonRoot;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine InsertNonContainmentReferenceRoutine with input:");
    getLogger().debug("   Root: " + this.rootElement);
    getLogger().debug("   NonRoot: " + this.insertedNonRoot);
    
    Root targetElement = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceTargetElement(rootElement, insertedNonRoot), // correspondence source supplier
    	Root.class,
    	(Root _element) -> true, // correspondence precondition checker
    	null);
    if (targetElement == null) {
    	return;
    }
    initializeRetrieveElementState(targetElement);
    // val updatedElement userExecution.getElement1(rootElement, insertedNonRoot, targetElement);
    userExecution.update0Element(rootElement, insertedNonRoot, targetElement);
    
    userExecution.callRoutine1(rootElement, insertedNonRoot, targetElement, actionsFacade);
    
    postprocessElementStates();
  }
}
