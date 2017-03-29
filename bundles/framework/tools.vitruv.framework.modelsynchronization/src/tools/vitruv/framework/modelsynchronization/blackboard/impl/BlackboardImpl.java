package tools.vitruv.framework.modelsynchronization.blackboard.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.resource.Resource;

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
    private Set<Resource> sourceModelResources;
    private Set<Resource> changedResources;

    public BlackboardImpl(final CorrespondenceModel correspondenceModel, final ModelRepository modelProviding) {
        this.state = BlackboardState.WAITING4COMMANDS;
        this.correspondenceModel = correspondenceModel;
        this.modelProviding = modelProviding;
        this.sourceModelResources = new HashSet<Resource>();
        this.changedResources = new HashSet<Resource>();
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

    @Override
    public void pushSourceModelResource(final Resource resource) {
        this.sourceModelResources.add(resource);
    }

    @Override
    public Set<Resource> getAndArchiveSourceModelResources() {
        Set<Resource> result = this.sourceModelResources;
        this.sourceModelResources = new HashSet<Resource>();
        return result;
    }

    @Override
    public void pushChangedResource(final Resource resource) {
        this.changedResources.add(resource);
    }

    @Override
    public Set<Resource> getAndArchiveChangedResources() {
        Set<Resource> result = this.changedResources;
        this.changedResources = new HashSet<Resource>();
        return result;
    }

}
