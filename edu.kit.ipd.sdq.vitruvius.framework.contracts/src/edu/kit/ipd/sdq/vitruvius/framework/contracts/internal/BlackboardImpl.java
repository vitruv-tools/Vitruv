package edu.kit.ipd.sdq.vitruvius.framework.contracts.internal;

import java.util.List;
import java.util.Stack;

import org.eclipse.emf.common.command.Command;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CheckResult;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;

public class BlackboardImpl implements Blackboard {

    private InternalCorrespondenceInstance correspondenceInstance;
    private Stack<List<Change>> changes;
    private Stack<List<Command>> commands;
    private List<Change> archivedChanges;
    private List<Command> archivedCommands;

    public BlackboardImpl() {
        this.changes = new Stack<List<Change>>();
        this.commands = new Stack<List<Command>>();
    }

    @Override
    public InternalCorrespondenceInstance getCorrespondenceInstance() {
        return this.correspondenceInstance;
    }

    @Override
    public void pushChanges(final List<Change> changes) {
        this.changes.push(changes);
    }

    @Override
    public List<Change> popChangesForPreparation() {
        return this.changes.pop();
    }

    @Override
    public List<Change> getChangesForTransformation() {
        return this.changes.peek();
    }

    @Override
    public void pushCommands(final List<Command> commands) {
        this.commands.push(commands);
    }

    @Override
    public List<Command> getAndArchiveCommandsForExecution() {
        this.archivedCommands = this.commands.pop();
        return this.archivedCommands;
    }

    @Override
    public void pushCheckResult(final CheckResult checkResult) {
        // TODO Auto-generated method stub

    }

    @Override
    public CheckResult popCheckResult() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Pair<List<Change>, List<Command>> getArchivedChangesAndCommandsForUndo() {
        return new Pair<List<Change>, List<Command>>(this.archivedChanges, this.archivedCommands);
    }

    @Override
    public void setCorrespondenceInstance(final InternalCorrespondenceInstance correspondenceInstance) {
        this.correspondenceInstance = correspondenceInstance;

    }

    @Override
    public void archiveChanges() {
        this.archivedChanges = this.changes.pop();
    }

}
