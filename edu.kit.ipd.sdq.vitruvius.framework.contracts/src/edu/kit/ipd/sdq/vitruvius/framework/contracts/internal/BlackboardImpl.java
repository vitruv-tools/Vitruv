package edu.kit.ipd.sdq.vitruvius.framework.contracts.internal;

import java.util.List;

import org.eclipse.emf.common.command.Command;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CheckResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;

public class BlackboardImpl implements Blackboard {

    @Override
    public InternalCorrespondenceInstance getCorrespondenceInstance() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void pushChanges(List<Change> changes) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<Change> popChangesForPreparation() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Change> getAndArchiveChangesForTransformation() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void pushCommands(List<Command> commands) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<Command> getAndArchiveCommandsForExecution() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void pushCheckResult(CheckResult checkResult) {
        // TODO Auto-generated method stub

    }

    @Override
    public CheckResult popCheckResult() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Pair<List<Change>, List<Command>> getArchivedChangesAndCommandsForUndo() {
        // TODO Auto-generated method stub
        return null;
    }

}
