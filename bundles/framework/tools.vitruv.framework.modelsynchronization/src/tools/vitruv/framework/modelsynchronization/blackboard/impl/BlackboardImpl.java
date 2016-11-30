package tools.vitruv.framework.modelsynchronization.blackboard.impl;

import java.util.List;

import tools.vitruv.framework.correspondence.CorrespondenceModel;
import tools.vitruv.framework.metamodel.ModelRepository;
import tools.vitruv.framework.modelsynchronization.blackboard.Blackboard;
import tools.vitruv.framework.util.command.VitruviusRecordingCommand;

public class BlackboardImpl implements Blackboard {

    private BlackboardState state;
    private final CorrespondenceModel correspondenceModel;
    private final ModelRepository modelProviding;
    private List<VitruviusRecordingCommand> commands;
    private List<VitruviusRecordingCommand> archivedCommands;

    public BlackboardImpl(final CorrespondenceModel correspondenceModel, final ModelRepository modelProviding) {
        this.state = BlackboardState.WAITING4COMMANDS;
        this.correspondenceModel = correspondenceModel;
        this.modelProviding = modelProviding;
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
    public ModelRepository getModelProviding() {
        return this.modelProviding;
    }

    @Override
    public void pushCommands(final List<VitruviusRecordingCommand> commands) {
        checkTransitionFromTo(BlackboardState.WAITING4COMMANDS, BlackboardState.WAITING4EXECUTION, "push commands");
        this.commands = commands;
    }

    @Override
    public List<VitruviusRecordingCommand> getAndArchiveCommandsForExecution() {
        checkTransitionFromTo(BlackboardState.WAITING4EXECUTION, BlackboardState.FINISHED,
                "get and archive commands for execution");
        this.archivedCommands = this.commands;
        return this.archivedCommands;
    }

}
