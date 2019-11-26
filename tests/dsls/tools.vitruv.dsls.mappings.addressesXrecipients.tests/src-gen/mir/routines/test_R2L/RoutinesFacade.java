package mir.routines.test_R2L;

import edu.kit.ipd.sdq.metamodels.addresses.Addresses;
import edu.kit.ipd.sdq.metamodels.recipients.Recipients;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadeExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final RoutinesFacadesProvider routinesFacadesProvider, final ReactionsImportPath reactionsImportPath, final RoutinesFacadeExecutionState executionState) {
    super(routinesFacadesProvider, reactionsImportPath, executionState);
  }
  
  public boolean adRootXReRoot_BidirectionalUpdate(final Recipients rRoot_) {
    mir.routines.test_R2L.RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.test_R2L.AdRootXReRoot_BidirectionalUpdateRoutine routine = new mir.routines.test_R2L.AdRootXReRoot_BidirectionalUpdateRoutine(_routinesFacade, _reactionExecutionState, _caller, rRoot_);
    return routine.applyRoutine();
  }
  
  public boolean adRootXReRoot_CreateMapping(final Recipients rRoot_) {
    mir.routines.test_R2L.RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.test_R2L.AdRootXReRoot_CreateMappingRoutine routine = new mir.routines.test_R2L.AdRootXReRoot_CreateMappingRoutine(_routinesFacade, _reactionExecutionState, _caller, rRoot_);
    return routine.applyRoutine();
  }
  
  public boolean adRootXReRoot_DeleteMapping(final Addresses aRoot_) {
    mir.routines.test_R2L.RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.test_R2L.AdRootXReRoot_DeleteMappingRoutine routine = new mir.routines.test_R2L.AdRootXReRoot_DeleteMappingRoutine(_routinesFacade, _reactionExecutionState, _caller, aRoot_);
    return routine.applyRoutine();
  }
  
  public boolean adRootXReRoot_BidirectionalCheck(final EObject affectedEObject, final String routineName) {
    mir.routines.test_R2L.RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.test_R2L.AdRootXReRoot_BidirectionalCheckRoutine routine = new mir.routines.test_R2L.AdRootXReRoot_BidirectionalCheckRoutine(_routinesFacade, _reactionExecutionState, _caller, affectedEObject, routineName);
    return routine.applyRoutine();
  }
  
  public boolean adRootXReRoot_ElementCreatedCheck(final EObject affectedEObject) {
    mir.routines.test_R2L.RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.test_R2L.AdRootXReRoot_ElementCreatedCheckRoutine routine = new mir.routines.test_R2L.AdRootXReRoot_ElementCreatedCheckRoutine(_routinesFacade, _reactionExecutionState, _caller, affectedEObject);
    return routine.applyRoutine();
  }
  
  public boolean adRootXReRoot_ElementDeletedCheck(final EObject affectedEObject) {
    mir.routines.test_R2L.RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.test_R2L.AdRootXReRoot_ElementDeletedCheckRoutine routine = new mir.routines.test_R2L.AdRootXReRoot_ElementDeletedCheckRoutine(_routinesFacade, _reactionExecutionState, _caller, affectedEObject);
    return routine.applyRoutine();
  }
  
  public boolean adRootXReRoot_ElementUpdatedCheck(final EObject affectedEObject) {
    mir.routines.test_R2L.RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.test_R2L.AdRootXReRoot_ElementUpdatedCheckRoutine routine = new mir.routines.test_R2L.AdRootXReRoot_ElementUpdatedCheckRoutine(_routinesFacade, _reactionExecutionState, _caller, affectedEObject);
    return routine.applyRoutine();
  }
}
