package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import java.util.List;

import org.eclipse.emf.common.command.Command;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;

/*
 * @startuml doc-files/Blackboard.png
 *
 * [*] --> Ready
 *
 * state Ready {
 *
 * [*] --> Waiting4Changes
 *
 * Waiting4Changes --> Waiting4Transformation : pushChanges
 *
 * Waiting4Transformation --> Waiting4Changes : popChangesForPreparation
 *
 * Waiting4Transformation --> Waiting4Commands : getAndArchiveChangesForTransformation
 *
 * Waiting4Commands --> Waiting4Execution : pushCommands
 *
 * Waiting4Execution --> Waiting4Check : getAndArchiveCommandsForExecution
 *
 * Waiting4Check --> Waiting4Undo : pushCheckResult
 *
 * Waiting4Undo --> Waiting4Undo : getCheckResult
 *
 * Waiting4Undo --> Waiting4Redo : getArchivedChangesAndCommandsForUndo
 *
 * Waiting4Redo --> Waiting4Execution : unarchiveChangesAndCommandsForRedo
 *
 * }
 *
 * Ready --> Ready : getCorrespondenceInstance, getModelProviding
 *
 * @enduml
 */
/**
 * Blackboard managing all data that is necessary to propagate a list of changes that occurred in
 * model instances of a metamodel A to model instances of a metamodel B. A new individual blackboard
 * is used to propagate other changes to model instances of B or the same changes to model instances
 * of another metamodel C. All blackboards are kept in an archive for later undoing and redoing.
 * <p>
 * The method invocation protocol for each blackboard is depicted in the following state chart:
 * <p>
 * <img src="doc-files/Blackboard.png">
 *
 * @author kramerm
 *
 */
public interface Blackboard {
    CorrespondenceInstance getCorrespondenceInstance();

    ModelProviding getModelProviding();

    void pushChanges(List<Change> changes);

    List<Change> popChangesForPreparation();

    List<Change> getAndArchiveChangesForTransformation();

    void pushCommands(List<Command> commands);

    List<Command> getAndArchiveCommandsForExecution();

    void pushCheckResult(CheckResult checkResult);

    CheckResult getCheckResult();

    Pair<List<Change>, List<Command>> getArchivedChangesAndCommandsForUndo();

    void unarchiveChangesAndCommandsForRedo();
}
