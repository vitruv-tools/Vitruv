package edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.java.compositerefiners;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.command.Command;

import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.CSSynchronizer;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.SynchronisationAbortedListener;
import edu.kit.ipd.sdq.vitruvius.framework.modelsynchronization.blackboard.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.change.description.GeneralChange;
import edu.kit.ipd.sdq.vitruvius.framework.userinteraction.UserInteracting;

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
    public List<Command> apply(final CSSynchronizer transformationExecuting, final Blackboard blackboard,
            final UserInteracting ui, final SynchronisationAbortedListener abortListener) {
        final List<Command> commands = new ArrayList<Command>(this.changes.size());
        for (final GeneralChange change : this.changes) {
            final Command command = transformationExecuting.transformEMFModelChange2Command(change, blackboard);
            commands.add(command);
        }
        return commands;
    }
}
