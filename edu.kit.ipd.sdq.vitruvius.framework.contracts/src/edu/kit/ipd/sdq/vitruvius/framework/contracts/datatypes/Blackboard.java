package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import java.util.List;

import org.eclipse.emf.common.command.Command;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.internal.InternalCorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;

/**
 * A new individual blackboard is used to synchronize a list of changes and kept in an archive for
 * later undoing.
 *
 * @author kramerm
 *
 */
public interface Blackboard {
    // TODO add getModelProviding? (to answer queries such as eAllInstances etc.)

    InternalCorrespondenceInstance getCorrespondenceInstance();

    void pushChanges(List<Change> changes);

    List<Change> popChangesForPreparation();

    List<Change> getChangesForTransformation();

    void pushCommands(List<Command> commands);

    List<Command> getAndArchiveCommandsForExecution();

    void pushCheckResult(CheckResult checkResult);

    CheckResult popCheckResult();

    Pair<List<Change>, List<Command>> getArchivedChangesAndCommandsForUndo();

    void setCorrespondenceInstance(InternalCorrespondenceInstance correspondenceInstance);

    void archiveChanges();

}
