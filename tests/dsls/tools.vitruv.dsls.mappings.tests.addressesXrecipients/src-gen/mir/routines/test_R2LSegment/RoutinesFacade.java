package mir.routines.test_R2LSegment;

import edu.kit.ipd.sdq.metamodels.recipients.Recipients;
import mir.routines.test_R2LSegment.OnAdRootXReRootRecipientsDeletedRepairRoutine;
import mir.routines.test_R2LSegment.OnAdRootXReRootRecipientsInsertedAsRootRepairRoutine;
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
  
  public boolean onAdRootXReRootRecipientsInsertedAsRootRepair(final Recipients affectedEObject) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    OnAdRootXReRootRecipientsInsertedAsRootRepairRoutine routine = new OnAdRootXReRootRecipientsInsertedAsRootRepairRoutine(_routinesFacade, _reactionExecutionState, _caller, affectedEObject);
    return routine.applyRoutine();
  }
  
  public boolean onAdRootXReRootRecipientsDeletedRepair(final Recipients affectedEObject) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    OnAdRootXReRootRecipientsDeletedRepairRoutine routine = new OnAdRootXReRootRecipientsDeletedRepairRoutine(_routinesFacade, _reactionExecutionState, _caller, affectedEObject);
    return routine.applyRoutine();
  }
}
