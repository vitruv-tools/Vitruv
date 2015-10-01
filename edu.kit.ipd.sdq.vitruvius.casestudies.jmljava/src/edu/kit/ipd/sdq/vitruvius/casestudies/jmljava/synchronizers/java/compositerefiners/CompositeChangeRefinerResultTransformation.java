package edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.java.compositerefiners;

import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.emf.common.command.Command;

import com.google.common.collect.Lists;

import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.CSSynchronizer;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.CustomTransformation;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.SynchronisationAbortedListener;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.bridges.EMFCommandBridge;

/**
 * Base class for composite change refiner results, which are custom transformations.
 *
 * @author Stephan Seifermann
 *
 */
public class CompositeChangeRefinerResultTransformation implements CompositeChangeRefinerResult {

    private final CustomTransformation transformation;

    /**
     * Constructor.
     *
     * @param transformation
     *            The transformation, which shall be executed.
     */
    public CompositeChangeRefinerResultTransformation(final CustomTransformation transformation) {
        this.transformation = transformation;
    }

    @Override
    public List<Command> apply(final CSSynchronizer transformationExecuting, final Blackboard blackboard,
            final UserInteracting ui, final SynchronisationAbortedListener abortListener) {
        final Command command = EMFCommandBridge
                .createVitruviusTransformationRecordingCommand(new Callable<TransformationResult>() {
                    @Override
                    public TransformationResult call() {
                        return CompositeChangeRefinerResultTransformation.this.transformation.execute(blackboard, ui,
                                abortListener);
                    }
                });
        return Lists.newArrayList(command);

    }

}
