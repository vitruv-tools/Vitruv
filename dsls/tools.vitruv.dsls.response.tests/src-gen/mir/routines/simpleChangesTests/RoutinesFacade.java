package mir.routines.simpleChangesTests;

import allElementTypes.NonRoot;
import allElementTypes.NonRootObjectContainerHelper;
import allElementTypes.Root;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  public void replaceSingleValuedEAttribute(final Root root, final Integer value) {
    mir.routines.simpleChangesTests.ReplaceSingleValuedEAttributeRoutine effect = new mir.routines.simpleChangesTests.ReplaceSingleValuedEAttributeRoutine(this.executionState, calledBy,
    	root, value);
    effect.applyRoutine();
  }
  
  public void replaceNonRootId(final NonRoot nonRoot, final String value) {
    mir.routines.simpleChangesTests.ReplaceNonRootIdRoutine effect = new mir.routines.simpleChangesTests.ReplaceNonRootIdRoutine(this.executionState, calledBy,
    	nonRoot, value);
    effect.applyRoutine();
  }
  
  public void insertNonRoot(final Root root, final NonRoot insertedNonRoot) {
    mir.routines.simpleChangesTests.InsertNonRootRoutine effect = new mir.routines.simpleChangesTests.InsertNonRootRoutine(this.executionState, calledBy,
    	root, insertedNonRoot);
    effect.applyRoutine();
  }
  
  public void removeNonRoot(final NonRoot removedNonRoot) {
    mir.routines.simpleChangesTests.RemoveNonRootRoutine effect = new mir.routines.simpleChangesTests.RemoveNonRootRoutine(this.executionState, calledBy,
    	removedNonRoot);
    effect.applyRoutine();
  }
  
  public void deleteNonRootEObjectSingle(final NonRoot containedObject) {
    mir.routines.simpleChangesTests.DeleteNonRootEObjectSingleRoutine effect = new mir.routines.simpleChangesTests.DeleteNonRootEObjectSingleRoutine(this.executionState, calledBy,
    	containedObject);
    effect.applyRoutine();
  }
  
  public void createNonRootEObjectSingle(final Root sourceRoot, final NonRoot containedObject) {
    mir.routines.simpleChangesTests.CreateNonRootEObjectSingleRoutine effect = new mir.routines.simpleChangesTests.CreateNonRootEObjectSingleRoutine(this.executionState, calledBy,
    	sourceRoot, containedObject);
    effect.applyRoutine();
  }
  
  public void replaceSingleValuedNonContainmentReference(final Root root, final NonRoot newReferencedElement) {
    mir.routines.simpleChangesTests.ReplaceSingleValuedNonContainmentReferenceRoutine effect = new mir.routines.simpleChangesTests.ReplaceSingleValuedNonContainmentReferenceRoutine(this.executionState, calledBy,
    	root, newReferencedElement);
    effect.applyRoutine();
  }
  
  public void insertEAttribute(final Root root, final Integer attributeValue) {
    mir.routines.simpleChangesTests.InsertEAttributeRoutine effect = new mir.routines.simpleChangesTests.InsertEAttributeRoutine(this.executionState, calledBy,
    	root, attributeValue);
    effect.applyRoutine();
  }
  
  public void removeEAttribute(final Root root, final Integer removedAttributeValue) {
    mir.routines.simpleChangesTests.RemoveEAttributeRoutine effect = new mir.routines.simpleChangesTests.RemoveEAttributeRoutine(this.executionState, calledBy,
    	root, removedAttributeValue);
    effect.applyRoutine();
  }
  
  public void insertNonContainmentReference(final Root root, final NonRoot insertedNonRoot) {
    mir.routines.simpleChangesTests.InsertNonContainmentReferenceRoutine effect = new mir.routines.simpleChangesTests.InsertNonContainmentReferenceRoutine(this.executionState, calledBy,
    	root, insertedNonRoot);
    effect.applyRoutine();
  }
  
  public void removeNonContainmentReference(final Root root, final NonRoot removedNonRoot) {
    mir.routines.simpleChangesTests.RemoveNonContainmentReferenceRoutine effect = new mir.routines.simpleChangesTests.RemoveNonContainmentReferenceRoutine(this.executionState, calledBy,
    	root, removedNonRoot);
    effect.applyRoutine();
  }
  
  public void createRoot(final Root root) {
    mir.routines.simpleChangesTests.CreateRootRoutine effect = new mir.routines.simpleChangesTests.CreateRootRoutine(this.executionState, calledBy,
    	root);
    effect.applyRoutine();
  }
  
  public void deleteRoot(final Root root) {
    mir.routines.simpleChangesTests.DeleteRootRoutine effect = new mir.routines.simpleChangesTests.DeleteRootRoutine(this.executionState, calledBy,
    	root);
    effect.applyRoutine();
  }
  
  public void createNonRootObjectContainer(final Root root, final NonRootObjectContainerHelper nonRootObjectContainer) {
    mir.routines.simpleChangesTests.CreateNonRootObjectContainerRoutine effect = new mir.routines.simpleChangesTests.CreateNonRootObjectContainerRoutine(this.executionState, calledBy,
    	root, nonRootObjectContainer);
    effect.applyRoutine();
  }
  
  public void createNonRootInContainer(final NonRootObjectContainerHelper container, final NonRoot insertedNonRoot) {
    mir.routines.simpleChangesTests.CreateNonRootInContainerRoutine effect = new mir.routines.simpleChangesTests.CreateNonRootInContainerRoutine(this.executionState, calledBy,
    	container, insertedNonRoot);
    effect.applyRoutine();
  }
}
