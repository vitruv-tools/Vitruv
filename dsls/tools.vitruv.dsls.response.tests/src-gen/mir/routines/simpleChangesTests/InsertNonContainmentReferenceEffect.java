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
import tools.vitruv.dsls.response.tests.simpleChangesTests.SimpleChangesTestsExecutionMonitor;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class InsertNonContainmentReferenceEffect extends AbstractEffectRealization {
  public InsertNonContainmentReferenceEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final Root root, final NonRoot insertedNonRoot) {
    super(responseExecutionState, calledBy);
    				this.root = root;this.insertedNonRoot = insertedNonRoot;
  }
  
  private Root root;
  
  private NonRoot insertedNonRoot;
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    private void executeUserOperations(final Root root, final NonRoot insertedNonRoot, final Root targetElement) {
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
      SimpleChangesTestsExecutionMonitor _instance = SimpleChangesTestsExecutionMonitor.getInstance();
      _instance.set(SimpleChangesTestsExecutionMonitor.ChangeType.InsertNonContainmentEReference);
    }
  }
  
  private static class CallRoutinesUserExecution extends AbstractEffectRealization.UserExecution {
    public CallRoutinesUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.simpleChangesTests.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    @Extension
    private RoutinesFacade effectFacade;
  }
  
  private EObject getCorrepondenceSourceTargetElement(final Root root, final NonRoot insertedNonRoot) {
    return root;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine InsertNonContainmentReferenceEffect with input:");
    getLogger().debug("   Root: " + this.root);
    getLogger().debug("   NonRoot: " + this.insertedNonRoot);
    
    Root targetElement = getCorrespondingElement(
    	getCorrepondenceSourceTargetElement(root, insertedNonRoot), // correspondence source supplier
    	Root.class,
    	(Root _element) -> true, // correspondence precondition checker
    	null);
    if (targetElement == null) {
    	return;
    }
    initializeRetrieveElementState(targetElement);
    
    preprocessElementStates();
    new mir.routines.simpleChangesTests.InsertNonContainmentReferenceEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	root, insertedNonRoot, targetElement);
    postprocessElementStates();
  }
}
