package tools.vitruvius.framework.modelsynchronization.blackboard.impl;

import java.util.List;

import tools.vitruvius.framework.change.description.VitruviusChange;
import tools.vitruvius.framework.correspondence.CorrespondenceModel;
import tools.vitruvius.framework.correspondence.CorrespondenceProviding;
import tools.vitruvius.framework.metamodel.ModelProviding;
import tools.vitruvius.framework.modelsynchronization.blackboard.Blackboard;
import tools.vitruvius.framework.util.command.VitruviusRecordingCommand;
import tools.vitruvius.framework.util.datatypes.Pair;

public class BlackboardImpl implements Blackboard {

    private BlackboardState state;
    private final CorrespondenceModel correspondenceModel;
    private final ModelProviding modelProviding;
    private List<VitruviusChange> changes;
    private List<VitruviusRecordingCommand> commands;
    private List<VitruviusChange> archivedChanges;
    private List<VitruviusRecordingCommand> archivedCommands;
    private CorrespondenceProviding correspondenceProviding;

    public BlackboardImpl(final CorrespondenceModel correspondenceModel, final ModelProviding modelProviding,
            final CorrespondenceProviding correspondenceProviding) {
        this.state = BlackboardState.WAITING4CHANGES;
        this.correspondenceModel = correspondenceModel;
        this.modelProviding = modelProviding;
        this.correspondenceProviding = correspondenceProviding;
    }

    private void checkTransitionFromTo(final BlackboardState expectedSource, final BlackboardState target,
            final String actionDescription) {
        if (this.state == expectedSource) {
            this.state = target;
        } else {
            throw new IllegalStateException("Cannot " + actionDescription + " in blackboard state '" + this.state
                    + "! Expected source for this transition: '" + expectedSource + "'");
        }
    }

    @Override
    public CorrespondenceModel getCorrespondenceModel() {
        return this.correspondenceModel;
    }

    @Override
    public ModelProviding getModelProviding() {
        return this.modelProviding;
    }

    @Override
    public void pushChanges(final List<VitruviusChange> changes) {
        checkTransitionFromTo(BlackboardState.WAITING4CHANGES, BlackboardState.WAITING4TRANSFORMATION, "push changes");
        this.changes = changes;
    }

    @Override
    public List<VitruviusChange> popChangesForPreparation() {
        checkTransitionFromTo(BlackboardState.WAITING4TRANSFORMATION, BlackboardState.WAITING4CHANGES,
                "pop changes for preparation");
        // no need to set changes to null as transition checking ensures setting before getting
        return this.changes;
    }

    @Override
    public List<VitruviusChange> getAndArchiveChangesForTransformation() {
        checkTransitionFromTo(BlackboardState.WAITING4TRANSFORMATION, BlackboardState.WAITING4COMMANDS,
                "get changes for transformation");
        this.archivedChanges = this.changes;
        return this.archivedChanges;
    }

    @Override
    public void pushCommands(final List<VitruviusRecordingCommand> commands) {
        checkTransitionFromTo(BlackboardState.WAITING4COMMANDS, BlackboardState.WAITING4EXECUTION, "push commands");
        this.commands = commands;
    }

    @Override
    public List<VitruviusRecordingCommand> getAndArchiveCommandsForExecution() {
        checkTransitionFromTo(BlackboardState.WAITING4EXECUTION, BlackboardState.WAITING4CHECK,
                "get and archive commands for execution");
        this.archivedCommands = this.commands;
        return this.archivedCommands;
    }

    @Override
    public Pair<List<VitruviusChange>, List<VitruviusRecordingCommand>> getArchivedChangesAndCommandsForUndo() {
        checkTransitionFromTo(BlackboardState.WAITING4UNDO, BlackboardState.WAITING4REDO,
                "get archived changes and commands for undo");
        // no need to clear check result and archives as transition checking ensures setting before
        // getting
        return new Pair<List<VitruviusChange>, List<VitruviusRecordingCommand>>(this.archivedChanges,
                this.archivedCommands);
    }

    @Override
    public void unarchiveChangesAndCommandsForRedo() {
        checkTransitionFromTo(BlackboardState.WAITING4REDO, BlackboardState.WAITING4EXECUTION,
                "unarchive changes and commands for redo");
        this.changes = this.archivedChanges;
        this.commands = this.archivedCommands;
    }

    @Override
    public CorrespondenceProviding getCorrespondenceProviding() {
        return this.correspondenceProviding;
    }

}
