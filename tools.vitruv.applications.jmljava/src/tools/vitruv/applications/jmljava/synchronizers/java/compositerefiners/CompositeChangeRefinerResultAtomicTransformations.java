package tools.vitruv.applications.jmljava.synchronizers.java.compositerefiners;

import java.util.ArrayList;
import java.util.List;

import tools.vitruv.applications.jmljava.synchronizers.SynchronisationAbortedListener;
import tools.vitruv.applications.jmljava.synchronizers.CSSynchronizer;
import tools.vitruv.framework.change.description.GeneralChange;
import tools.vitruv.framework.correspondence.CorrespondenceModel;
import tools.vitruv.framework.userinteraction.UserInteracting;
import tools.vitruv.framework.util.command.VitruviusRecordingCommand;

/**
 * Composite change refiner result, which contains atomic changes only. The atomic changes can be
 * processed in isolation.
 *
 * @author Stephan Seifermann
 *
 */
public class CompositeChangeRefinerResultAtomicTransformations implements CompositeChangeRefinerResult {

    private final List<GeneralChange> changes;

    /**
     * Constructor.
     *
     * @param changes
     *            The atomic changes.
     */
    public CompositeChangeRefinerResultAtomicTransformations(final List<GeneralChange> changes) {
        this.changes = changes;
    }

    @Override
    public List<VitruviusRecordingCommand> apply(final CSSynchronizer transformationExecuting, final CorrespondenceModel correspondenceModel,
            final UserInteracting ui, final SynchronisationAbortedListener abortListener) {
        final List<VitruviusRecordingCommand> commands = new ArrayList<VitruviusRecordingCommand>(this.changes.size());
        for (final GeneralChange change : this.changes) {
            final VitruviusRecordingCommand command = transformationExecuting.transformEMFModelChange2Command(change, correspondenceModel);
            commands.add(command);
        }
        return commands;
    }
}
