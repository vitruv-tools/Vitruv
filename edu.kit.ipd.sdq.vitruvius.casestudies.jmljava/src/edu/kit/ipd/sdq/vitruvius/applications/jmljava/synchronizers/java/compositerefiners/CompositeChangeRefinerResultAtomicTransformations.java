package edu.kit.ipd.sdq.vitruvius.applications.jmljava.synchronizers.java.compositerefiners;

import java.util.ArrayList;
import java.util.List;

import edu.kit.ipd.sdq.vitruvius.applications.jmljava.synchronizers.SynchronisationAbortedListener;
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.synchronizers.CSSynchronizer;
import edu.kit.ipd.sdq.vitruvius.framework.change.description.GeneralChange;
import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModel;
import edu.kit.ipd.sdq.vitruvius.framework.userinteraction.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.util.command.VitruviusRecordingCommand;

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
