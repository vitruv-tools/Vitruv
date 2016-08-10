package edu.kit.ipd.sdq.vitruvius.framework.contracts.internal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VitruviusChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CheckResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstanceDecorator;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.CorrespondenceProviding;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ModelProviding;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;

public class BlackboardImpl implements Blackboard {

    private BlackboardState state;
    private final CorrespondenceInstanceDecorator correspondenceInstance;
    private final ModelProviding modelProviding;
    private List<VitruviusChange> changes;
    private List<Command> commands;
    private List<VitruviusChange> archivedChanges;
    private List<Command> archivedCommands;
    private CheckResult checkResult;
    private CorrespondenceProviding correspondenceProviding;
    private Map<EObject, TUID> tuidMap;

    public BlackboardImpl(final CorrespondenceInstanceDecorator correspondenceInstance,
            final ModelProviding modelProviding, final CorrespondenceProviding correspondenceProviding) {
        this.state = BlackboardState.WAITING4CHANGES;
        this.correspondenceInstance = correspondenceInstance;
        this.modelProviding = modelProviding;
        this.correspondenceProviding = correspondenceProviding;
        this.tuidMap = new HashMap<EObject, TUID>();
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
    public CorrespondenceInstanceDecorator getCorrespondenceInstance() {
        return this.correspondenceInstance;
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
    public void pushCommands(final List<Command> commands) {
        checkTransitionFromTo(BlackboardState.WAITING4COMMANDS, BlackboardState.WAITING4EXECUTION, "push commands");
        this.commands = commands;
    }

    @Override
    public List<Command> getAndArchiveCommandsForExecution() {
        checkTransitionFromTo(BlackboardState.WAITING4EXECUTION, BlackboardState.WAITING4CHECK,
                "get and archive commands for execution");
        this.archivedCommands = this.commands;
        return this.archivedCommands;
    }

    @Override
    public void pushCheckResult(final CheckResult checkResult) {
        checkTransitionFromTo(BlackboardState.WAITING4CHECK, BlackboardState.WAITING4UNDO, "push check result");
        this.checkResult = checkResult;
    }

    @Override
    public CheckResult getCheckResult() {
        checkTransitionFromTo(BlackboardState.WAITING4UNDO, BlackboardState.WAITING4UNDO,
                "pop check result for evaluation");
        return this.checkResult;
    }

    @Override
    public Pair<List<VitruviusChange>, List<Command>> getArchivedChangesAndCommandsForUndo() {
        checkTransitionFromTo(BlackboardState.WAITING4UNDO, BlackboardState.WAITING4REDO,
                "get archived changes and commands for undo");
        // no need to clear check result and archives as transition checking ensures setting before
        // getting
        return new Pair<List<VitruviusChange>, List<Command>>(this.archivedChanges, this.archivedCommands);
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

    @Override
    public void pushTUID(final EObject eObject, final TUID tuid) {
        this.tuidMap.put(eObject, tuid);
    }

    @Override
    public Map<EObject, TUID> popTUIDMap() {
        Map<EObject, TUID> result = this.tuidMap;
        this.tuidMap = new HashMap<EObject, TUID>();
        return result;
    }
}
