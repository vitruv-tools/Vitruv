package mir.routines.simpleChangesTests;

import allElementTypes.NonRoot;
import allElementTypes.NonRootObjectContainerHelper;
import allElementTypes.Root;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
    super(reactionExecutionState, calledBy);
  }
  
  public void replaceSingleValuedPrimitiveTypeEAttribute(final Root rootElement, final Integer value) {
    mir.routines.simpleChangesTests.ReplaceSingleValuedPrimitiveTypeEAttributeRoutine effect = new mir.routines.simpleChangesTests.ReplaceSingleValuedPrimitiveTypeEAttributeRoutine(this.executionState, calledBy,
    	rootElement, value);
    effect.applyRoutine();
  }
  
  public void replaceSingleValuedEAttribute(final Root rootElement, final Integer value) {
    mir.routines.simpleChangesTests.ReplaceSingleValuedEAttributeRoutine effect = new mir.routines.simpleChangesTests.ReplaceSingleValuedEAttributeRoutine(this.executionState, calledBy,
    	rootElement, value);
    effect.applyRoutine();
  }
  
  public void replaceNonRootId(final NonRoot nonRoot, final String value) {
    mir.routines.simpleChangesTests.ReplaceNonRootIdRoutine effect = new mir.routines.simpleChangesTests.ReplaceNonRootIdRoutine(this.executionState, calledBy,
    	nonRoot, value);
    effect.applyRoutine();
  }
  
  public void insertNonRoot(final Root rootElement, final NonRoot insertedNonRoot) {
    mir.routines.simpleChangesTests.InsertNonRootRoutine effect = new mir.routines.simpleChangesTests.InsertNonRootRoutine(this.executionState, calledBy,
    	rootElement, insertedNonRoot);
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
  
  public void replaceSingleValuedNonContainmentReference(final Root rootElement, final NonRoot newReferencedElement) {
    mir.routines.simpleChangesTests.ReplaceSingleValuedNonContainmentReferenceRoutine effect = new mir.routines.simpleChangesTests.ReplaceSingleValuedNonContainmentReferenceRoutine(this.executionState, calledBy,
    	rootElement, newReferencedElement);
    effect.applyRoutine();
  }
  
  public void insertEAttribute(final Root rootElement, final Integer attributeValue) {
    mir.routines.simpleChangesTests.InsertEAttributeRoutine effect = new mir.routines.simpleChangesTests.InsertEAttributeRoutine(this.executionState, calledBy,
    	rootElement, attributeValue);
    effect.applyRoutine();
  }
  
  public void removeEAttribute(final Root rootElement, final Integer removedAttributeValue) {
    mir.routines.simpleChangesTests.RemoveEAttributeRoutine effect = new mir.routines.simpleChangesTests.RemoveEAttributeRoutine(this.executionState, calledBy,
    	rootElement, removedAttributeValue);
    effect.applyRoutine();
  }
  
  public void insertNonContainmentReference(final Root rootElement, final NonRoot insertedNonRoot) {
    mir.routines.simpleChangesTests.InsertNonContainmentReferenceRoutine effect = new mir.routines.simpleChangesTests.InsertNonContainmentReferenceRoutine(this.executionState, calledBy,
    	rootElement, insertedNonRoot);
    effect.applyRoutine();
  }
  
  public void removeNonContainmentReference(final Root rootElement, final NonRoot removedNonRoot) {
    mir.routines.simpleChangesTests.RemoveNonContainmentReferenceRoutine effect = new mir.routines.simpleChangesTests.RemoveNonContainmentReferenceRoutine(this.executionState, calledBy,
    	rootElement, removedNonRoot);
    effect.applyRoutine();
  }
  
  public void createRoot(final Root rootElement) {
    mir.routines.simpleChangesTests.CreateRootRoutine effect = new mir.routines.simpleChangesTests.CreateRootRoutine(this.executionState, calledBy,
    	rootElement);
    effect.applyRoutine();
  }
  
  public void deleteRoot(final Root rootElement) {
    mir.routines.simpleChangesTests.DeleteRootRoutine effect = new mir.routines.simpleChangesTests.DeleteRootRoutine(this.executionState, calledBy,
    	rootElement);
    effect.applyRoutine();
  }
  
  public void createNonRootObjectContainer(final Root rootElement, final NonRootObjectContainerHelper nonRootObjectContainer) {
    mir.routines.simpleChangesTests.CreateNonRootObjectContainerRoutine effect = new mir.routines.simpleChangesTests.CreateNonRootObjectContainerRoutine(this.executionState, calledBy,
    	rootElement, nonRootObjectContainer);
    effect.applyRoutine();
  }
  
  public void createNonRootInContainer(final NonRootObjectContainerHelper container, final NonRoot insertedNonRoot) {
    mir.routines.simpleChangesTests.CreateNonRootInContainerRoutine effect = new mir.routines.simpleChangesTests.CreateNonRootInContainerRoutine(this.executionState, calledBy,
    	container, insertedNonRoot);
    effect.applyRoutine();
  }
}
